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
        g.setColor(c[(i++) % 3]);
        fillRect(g, 0.25f, 0.25f, 0.5f, 0.5f);
    }
    
    private void clear(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        fillRect(g, 0, 0, 1, 1);
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
