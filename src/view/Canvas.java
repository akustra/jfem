package view;

import beam.Beams;
import java.awt.BasicStroke;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import static java.awt.RenderingHints.*;

public class Canvas extends javax.swing.JPanel
        implements MouseListener, MouseMotionListener, MouseWheelListener {

    public Beams beams;

    private Object antialias = VALUE_ANTIALIAS_ON;
    private Object rendering = VALUE_RENDER_QUALITY;
    //
    private boolean clearSurface = true;
    private boolean clearOnce = true;
    private boolean initSurface = true;
    //
    private BufferedImage image;
    private int imageWidth, imageHeight;
    //
    private double translationX = 0.0f;
    private double translationY = 0.0f;
    private double translationZ = 100.0f;
    //
    private AffineTransform transform = new AffineTransform(0, 0, -1, 0, 0, -1);

    public final Point2D screenToWorld(Point2D screenPoint) {
        Point2D.Double result = new Point2D.Double();
        result.x = ((screenPoint.getX() - translationX) / translationZ);
        result.y = ((screenPoint.getY() - translationY) / translationZ);
        return result;
    }

    public Canvas() {
        super();

        setBackground(Color.white);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int width = getWidth();
        int height = getHeight();

        if (imageWidth != width || imageHeight != height) {
            initSurface = true;
            imageWidth = width;
            imageHeight = height;
        }

        if (image == null || initSurface) {
            image = createBufferedImage((Graphics2D) g, width, height);
            clearOnce = true;
        }

        if (initSurface) {
            initSurface = false;
        }

        Graphics2D g2 = createGraphics2D(width, height, image, g);

        // transformations
        transform.setToIdentity();
        transform.translate(translationX, translationY);
        transform.scale(translationZ, -translationZ);

        g2.setTransform(transform);

        g2.setStroke(new BasicStroke(1.2f / (float) translationZ));

        // render
        Painter.drawBeams(g2, beams);

        g2.dispose();

        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    private BufferedImage createBufferedImage(Graphics2D g2d, int width, int height) {
        return g2d.getDeviceConfiguration().createCompatibleImage(width, height);
    }

    private Graphics2D createGraphics2D(int width, int height, BufferedImage image, Graphics g) {
        Graphics2D g2d = null;

        if (image != null) {
            g2d = image.createGraphics();
        } else {
            g2d = (Graphics2D) g;
        }

        g2d.setBackground(getBackground());

        g2d.setRenderingHint(KEY_ANTIALIASING, antialias);
        g2d.setRenderingHint(KEY_RENDERING, rendering);

        if (clearSurface || clearOnce) {
            g2d.clearRect(0, 0, width, height);
            clearOnce = false;
        }

        return g2d;
    }

    private int lastPositionX;
    private int lastPositionY;

    private Point2D mousePosition = new Point2D.Double();

    private double minZoom = 0.01;
    private double zoomStep = 10;

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
        Point2D temp = screenToWorld(new Point2D.Double(e.getX(), e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();

        lastPositionX = e.getX();
        lastPositionY = e.getY();
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        requestFocusInWindow();

        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        requestFocusInWindow();

        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            int newX = e.getX() - lastPositionX;
            int newY = e.getY() - lastPositionY;
            lastPositionX += newX;
            lastPositionY += newY;
            translationX += newX;
            translationY += newY;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        requestFocusInWindow();

        Point2D temp = screenToWorld(new Point2D.Double(e.getX(), e.getY()));
        mousePosition.setLocation(temp);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        requestFocusInWindow();

        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            translationZ += zoomStep * e.getWheelRotation();
            translationZ = Math.max(minZoom, translationZ);

            repaint();
        }
    }
}
