/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author petricioiurobert
 */
public class PaintTools {

    public enum Tool {

        Brush, Pencil, SquareBrush, Circle, Rectangle, Line
    }
    
    Tool t;
    Graphics2D g;
    int toolHeight, toolWidth;
    Color c;
    Point point;
    Point startPoint;
    Graphics2D image;
    

    public void setGraphics(Graphics2D g, BufferedImage bi) {
        this.g = g;
        g.setColor(c);
        image = bi.createGraphics();
        image.setColor(Color.white);
        image.fillRect(0, 0, 650, 700);
        image.setColor(c);
    }

    public void setColor(Color c) {
        this.g.setColor(c);
        image.setColor(c);
    }

    public PaintTools(Tool t) {
        this.t = t;
        toolWidth = 10;
        toolHeight = 10;
    }

    public void draw(Point p) {
        switch (t) {
            case Brush:
                drawBrush((int) p.getX(), (int) p.getY());
                break;
            case Pencil:
                drawBrush((int) p.getX(), (int) p.getY());
                break;
            case SquareBrush:
                drawSquareBrush((int) p.getX(), (int) p.getY());
        }
    }

    public void draw(Point p, int width, int height) {
        switch (t) {
            case Circle:
                drawCircle((int) p.getX(), (int) p.getY(), width, height);
                break;
            case Rectangle:
                drawRectangle((int)p.getX(), (int)p.getY(), width, height);
                break;
        }
    }
    public void draw(Point p1, Point p2) {
        switch (t) {
            case Line:
                g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
                image.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
                break;
        }
    }

    public void setStartPoint(Point p) {
        this.startPoint = p;
    }

    public void setSize (int size){
        toolWidth = size;
        toolHeight = size;
        g.setStroke(new BasicStroke(size));
        image.setStroke(new BasicStroke(size));
    }

    public void drawBrush(int xCoord, int yCoord) {
        g.fillOval(xCoord - toolWidth/2, yCoord - toolHeight/2, toolWidth, toolHeight);
        image.fillOval(xCoord - toolWidth/2, yCoord - toolHeight/2, toolWidth, toolHeight);   
    }

    public void drawCircle(int xCoord, int yCoord, int width, int height) {
        g.drawOval(xCoord, yCoord, width, height);
        image.drawOval(xCoord, yCoord, width, height);
    }
    public void drawRectangle(int xCoord, int yCoord, int width, int height) {
        g.drawRect(xCoord, yCoord, width, height);
        image.drawRect(xCoord, yCoord, width, height);
    }
    public Graphics2D getGraphics2D(){
        return this.g;
    }
    public void drawSquareBrush(int xCoord, int yCoord) {
        g.fillRect(xCoord-toolWidth/2, yCoord-toolHeight/2, toolWidth, toolHeight);
        image.fillRect(xCoord - toolWidth/2, yCoord - toolHeight/2, toolWidth, toolHeight);
    }

    public void setTool(Tool t) {
        this.t = t;

    }
    public void fillScreen() {
        g.fillRect(0,0,650,700);
        image.fillRect(0, 0, 650, 700);
    }
}
