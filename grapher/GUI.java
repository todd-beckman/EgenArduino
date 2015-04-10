package grapher;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
public class GUI extends javax.swing.JPanel {
    public static Color
        BACKGROUND_COLOR = Color.BLACK,
        TEXT_COLOR = Color.WHITE;
    public static float
        RANGEX = 120,
        RANGEY = 120;
    public static int
        POINTSIZE = 2;
    ArrayList<Point> data;
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
    }
    
    Color[] c = {
        Color.RED,
        Color.GREEN,
        Color.BLUE,
    };
    int i = 0;
    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        clear(g);
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
        int width = POINTSIZE * Math.max(1, (int)(this.w / this.h)),
            height = POINTSIZE * Math.max(1, (int)(this.h / this.w));
        g.fillOval(
            (int)(p.x / RANGEX * this.w) - height / 2,
            (int)(this.h - p.y / RANGEY * this.h) - width / 2,
            width,
            height
        );
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
