
package Widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author echo
 */
public class TextFieldGradient extends JTextField {

    private Paint paint;

    private Shape shape;

    private boolean fokus;

    private Paint glass;

    public TextFieldGradient() {
        setForeground(Color.WHITE);
        setOpaque(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));

        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                setFokus(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setFokus(false);
            }
        });
    }

    public boolean isFokus() {
        return fokus;
    }

    public void setFokus(boolean fokus) {
        this.fokus = fokus;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D) g.create();

        shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), getHeight(), getHeight());

        if (isFokus()) {
            paint = new GradientPaint(0, 0, Color.BLUE, getWidth(), 0, Color.white);
        } else {
            paint = new GradientPaint(0, 0, Color.BLUE, getWidth(), 0, Color.white);
        }

        gd.setPaint(paint);
        gd.fill(shape);

        super.paintComponent(g);

        glass = new GradientPaint(0, 0, new Color(1F, 1F, 1F, 0.5F), 0, getHeight(), new Color(1F, 1F, 1F, 0F));
        gd.setPaint(glass);
        gd.fill(shape);

        gd.dispose();
    }
}
