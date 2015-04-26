package grapher;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
public class GUI extends javax.swing.JPanel {
    public static Color
        BACKGROUND_COLOR = Color.WHITE,
        TEXT_COLOR = Color.BLACK;
    public static float
        RANGEX = 10,
        RANGEY = 100;
    public static int
        LABELPADDING = 30,
        POINTSIZE = 5;
    public ArrayList<Point> data;
    float w, h;
    public GUI(int width, int height) {
        w = (float)width;
        h = (float)height;
        //  Empty graph to start
        data = new ArrayList();
        
        //  Prepare the actual gui
        setSize(width, height);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                w = (float)getWidth();
                h = (float)getHeight();
            }
        });
    }
    
    /**
     * Sets the data to be graphed
     * @param data The data to be graphed
     */
    public void setData(ArrayList<Point> data) {
        this.data = data;
        repaint();
    }
    
    /**
     * Adds a point to the graph
     * @param p 
     */
    public void addPoint(Point p) {
        data.add(p);
        RANGEX = Math.max(RANGEX, p.x);
        RANGEY = Math.max(RANGEY, p.y);
        repaint();
    }
    /**
     * Turn the current data set into a string for printing or writing to file
     * @return The data
     */
    public String stringify() {
        String str = "";
        for (Point p : data) {
            str += p.x + "," + p.y + "\n";
        }
        return str;
    }
    
    Color[] c = {
        Color.RED,
        Color.GREEN,
        Color.BLUE,
    };
    int i = 0;
    float amaxx, amaxy;
    @Override
    public void paint(Graphics graphics) {
        amaxx = RANGEX - LABELPADDING;
        amaxy = RANGEY - LABELPADDING;
        Graphics2D g = (Graphics2D)graphics;
        clear(g);
        g.setColor(TEXT_COLOR);
        g.drawLine(0, (int)this.h - LABELPADDING, (int)this.w, (int)this.h - LABELPADDING);
        g.drawLine(LABELPADDING, 0, LABELPADDING, (int)this.h);
        for (Point p : data) {
            g.setColor(c[(i++) % 3]);
            drawPoint(g, p);
        }
    }
    
    private void clear(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        fillRect(g, 0, 0, 1, 1);
    }
    
    private void drawPoint(Graphics2D g, Point p) {
        int width = this.h < 0.01f ? 1 : POINTSIZE * Math.max(1, (int)(this.w / this.h)),
            height = this.w < 0.01f ? 1 : POINTSIZE * Math.max(1, (int)(this.h / this.w));
        int x = LABELPADDING + (int)(p.x / amaxx * this.w) - width / 2;
        int y = LABELPADDING + (int)(this.h - p.y / amaxy * this.h) + height / 2;
        g.fillOval(x, y, width, height);
        g.drawString("" + p.x, x - width / 2, (int)this.h - LABELPADDING + height / 2);
        g.drawString("" + p.y, 0, y + height / 2);
    }
    
    private void fillRect(Graphics2D g, float x, float y, float w, float h) {
        g.fillRect(
            (int)(x * this.w),
            (int)(y * this.h),
            (int)(w * this.w),
            (int)(h * this.h)
        );
    }
}
