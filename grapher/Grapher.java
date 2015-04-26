package grapher;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Grapher {
    //  Everything is static so all the communicatey things work
    //  Tradeoff of elegant versus speedcoding
    public static int
            WIDTH = 800,
            HEIGHT = 600,
            INTERVAL = 100;
    public static String
            FILENAME = "data.csv";
    public static JFrame frame;
    public static ArrayList<Point> data;
    public static GUI gui;
    
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

        //  Make the window appear
        frame.setVisible(true);
        
        BufferedReader file;
        data = new ArrayList();
        int maxx = 0,
            maxy = 0;
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
                data.add(newPoint(new Point(dep, ind)));
            }
            file.close();
        } catch (IOException e) {
            System.out.println("No file to read from. Starting from nothing...");
        }
        //  Update the range in case it changed
        GUI.RANGEX = maxx;
        GUI.RANGEY = maxy;

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
     * Converts the point and adds it to the list
     * @param p The point to add (float, float)
     */
    public static void addPoint(Point p) {
        gui.addPoint(newPoint(p));
    }
}
