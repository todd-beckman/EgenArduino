package grapher;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Grapher {

    public static int
            WIDTH = 800,
            HEIGHT = 600;
    public static String
            FILENAME = "data.csv";
    public static long
            DELAY = 100l,
            INTERVAL = 32l;

    public static void main(String[] args) {

        //  INITIALIZE PHASE    //
        //  Prepare the window
        java.awt.Frame frame = new java.awt.Frame("Grapher");
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
        
        java.util.Timer t = new Timer();
        t.scheduleAtFixedRate(
            new TimerTask() {
                @Override
                public void run() {
                    synchronized(frame) {
                        frame.notifyAll();
                    }
                }
            },
            DELAY,
            INTERVAL
        );

        //  Prepare for the loop
        BufferedReader file;
        ArrayList<Point> data = new ArrayList();
        synchronized(frame) {
            while (true) {

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
                        data.add(new Point(
                                Float.parseFloat(values[0]),
                                Float.parseFloat(values[1])
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

                gui.repaint();
                
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
