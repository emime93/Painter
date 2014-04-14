/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painter;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.EventListener;
import javax.swing.JComponent;

/**
 *
 * @author petricioiurobert
 */
public class Picture extends Canvas implements MouseMotionListener, MouseListener {

    PaintTools tool;
    BufferedImage bi;

    public Picture(PaintTools t, BufferedImage bi) {

        tool = t;

        setPreferredSize(new Dimension(650, 700));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.bi = bi;
    }

    @Override
    public void paint(Graphics g) {
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return super.getMinimumSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return super.getMaximumSize();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tool.draw(e.getPoint());

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tool.draw(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point p = e.getPoint();

        if (tool.t == PaintTools.Tool.Circle || tool.t == PaintTools.Tool.Rectangle) {
            int width = (int) Point2D.distance(tool.startPoint.getX(), tool.startPoint.getX(), p.getX(), p.getX());
            int height = (int) Point2D.distance(tool.startPoint.getY(), tool.startPoint.getY(), p.getY(), p.getY());

            if (p.getX() > tool.startPoint.getX() && p.getY() > tool.startPoint.getY()) {
                tool.draw(tool.startPoint, width, height);
            }
            if (p.getX() < tool.startPoint.getX() && p.getY() < tool.startPoint.getY()) {
                tool.draw(p, width, height);
            }
            if (p.getX() > tool.startPoint.getX() && p.getY() < tool.startPoint.getY()) {
                Point p1 = new Point();
                p1.setLocation(tool.startPoint.getX(), tool.startPoint.getY() - height);
                tool.draw(p1, width, height);
            }
            if (p.getX() < tool.startPoint.getX() && p.getY() > tool.startPoint.getY()) {
                Point p1 = new Point();
                p1.setLocation(p.getX() - width, p.getY());
                tool.draw(p1, width, height);
            }
        }
        if (tool.t == PaintTools.Tool.Line) {
            tool.draw(tool.startPoint, p);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            Point p;
            p = e.getPoint();
            tool.setStartPoint(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
