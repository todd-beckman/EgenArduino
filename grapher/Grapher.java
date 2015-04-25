package grapher;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
//import javax.swing.TimerTask;

public class Grapher {

    public static int
            WIDTH = 800,
            HEIGHT = 600,
            INTERVAL = 100;
    public static String
            FILENAME = "data.csv";

    public static void main(String[] args) {

        //  INITIALIZE PHASE    //
        //  Prepare the window
        javax.swing.JFrame frame = new javax.swing.JFrame("Grapher");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);

        //  Add GUI to the frame
        GUI gui = new GUI(WIDTH, HEIGHT);
        frame.add(gui);

        //  Fix for a known Windows bug that can crash the program when focus is lost
        frame.addFocusListener(new FocusListener() {

            //	Do nothing
            @Override
            public void focusGained(FocusEvent e) {
            }

            //	Request focus
            @Override
            public void focusLost(FocusEvent e) {
                e.getComponent().requestFocus();
            }
        });

        //  Make sure the window can close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        //  Make the window appear
        frame.setVisible(true);
        
        //  Set up a timer
        Timer t = new Timer(INTERVAL, (ActionEvent ae) -> {
            
            //  Allow the loop to proceed
            synchronized(frame) {
                frame.notifyAll();
            }
        });
        t.start();

        //  Prepare for the loop
        BufferedReader file;
        ArrayList<Point> data = new ArrayList();
        synchronized(frame) {
            while (true) {
                int maxx = 0,
                    maxy = 0;

                //  GET INPUT PHASE     //
                
                data.clear();
                try {
                    file = new BufferedReader(new FileReader(FILENAME));
                    while (file.ready()) {
                        String line = file.readLine();
                        String[] values = line.split(",");
                        if (values.length != 2) {
                            System.out.println("Improperly formatted CSV.");
                            System.exit(0);
                        }
                        float dep = Float.parseFloat(values[0]);
                        float ind = Float.parseFloat(values[1]);
                        maxx =
                                Math.max((int)GUI.RANGEX,
                                Math.max(maxx, (int)dep));
                        maxy =
                                Math.max((int)GUI.RANGEY,
                                Math.max(maxy, (int)ind));
                        data.add(new Point(
                                dep,
                                ind
                        ));
                    }
                    file.close();
                } catch (IOException e) {
                    System.out.println("Couldn't read file.");
                    System.exit(0);
                }

                //  UPDATE PHASE        //

                gui.setData(data);

                //  OUTPUT PHASE        //

                //  Update the range in case it changed
                GUI.RANGEX = maxx;
                GUI.RANGEY = maxy;
                
                //  Render
                gui.repaint();
                
                //  Don't start the next loop until ready
                try {
                    frame.wait();
                }
                catch (InterruptedException e) {
                    //  move on
                }
            }
        }
    }
}
