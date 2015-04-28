package grapher;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Grapher {
    //  Everything is static so all the communicatey things work
    //  Tradeoff of elegant versus speedcoding
    private static final int
            WIDTH = 800,
            HEIGHT = 600;
    private static final String
            FILENAME = "data.csv";
    private static JFrame frame;
    private static ArrayList<Point> data;
    private static GUI gui;
    
    private static BufferedReader infile;
    private static PrintWriter outfile;
    
    public static void main(String[] args) {
        //  Prepare the window
        frame = new javax.swing.JFrame("Grapher");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);

        //  Add GUI to the frame
        gui = new GUI(WIDTH, HEIGHT);
        frame.add(gui);

        //  Workaround for a known Windows bug that can crash the program
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
        
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_S:
                        try {
                            outfile = new PrintWriter("data.csv");
                            outfile.print(gui.stringify());
                            outfile.close();
                        }
                        catch (IOException ex) {
                            System.out.println("Failed to print to output file.");
                        }
                        break;
                }
            }
        });

        //  Make the window appear
        frame.setVisible(true);
        
        data = new ArrayList();
        float maxx = 0,
            maxy = 0;
        try {
            infile = new BufferedReader(new FileReader(FILENAME));
            while (infile.ready()) {
                String line = infile.readLine();
                String[] values = line.split(",");
                if (values.length != 2) {
                    System.out.println("Improperly formatted CSV.");
                    System.exit(0);
                }
                float dep = Float.parseFloat(values[0]);
                float ind = Float.parseFloat(values[1]);
                maxx = Math.max(GUI.RANGEX, Math.max(maxx, dep));
                maxy = Math.max(GUI.RANGEY, Math.max(maxy, ind));
                data.add(newPoint(new Point(dep, ind)));
            }
            infile.close();
        } catch (IOException e) {
            System.out.println("No file to read from. Starting from nothing...");
        }
        System.out.println("Added " + data.size() + " points.");
        //  Update the range in case it changed
        GUI.RANGEX = maxx;
        //GUI.RANGEY = maxy;

        //  Assigns and renders
        gui.setData(data);
        
        
        //  Just about the sloppiest thing you could possibly imagine
        try {
            arduinotest.SerialTest.main(args);
        }
        catch (Exception e) {
            System.out.println("Cannot listen to serial port. Not updating...");
        }
    }
    
    /**
     * Converts a point with the formula provided
     * @param p The point to convert
     * @return The converted point
     */
    private static Point newPoint(Point p) {
        //  conversion goes here
        return p;
    }
    
    /**
     * Take data input, parse it, and then add it to the graph
     * @param input The data to parse
     */
    public static void parseData(String input) {
        String[] split = input.split(",");
        float time = Float.parseFloat(split[0]);
        float salinity = Float.parseFloat(split[1]);
        float temperature = Float.parseFloat(split[2]);
        System.out.println("Time: " + time + "s. Salinity: " + salinity + ". Temperature: " + temperature + "C.");
        gui.addPoint(newPoint(new Point(time, salinity)));
    }
}
