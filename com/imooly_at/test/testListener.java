package com.imooly_at.test;

/**
 * Created by qianqiang on 14-12-30.
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class testListener {
    public static void main(String args[]) {
        new PaintFrame("My PaintBoader");
    }
}

class PaintFrame extends Frame {
    ArrayList points = null;

    PaintFrame(String s) {
        super(s);
        points = new ArrayList();
        setLayout(null);
        setBounds(300, 300, 400, 300);
        this.setBackground(new Color(204, 204, 255));
        setVisible(true);
        this.addMouseListener(new Monitor());
    }

    public void paint(Graphics g) {
        Iterator i = points.iterator();
        while (i.hasNext()) {
            Point p = (Point) i.next();
            g.setColor(Color.blue);
            g.fillOval(p.x, p.y, 10, 10);
        }
    }

    public void addPoint(Point p) {
        points.add(p);
    }
}

class Monitor extends MouseAdapter {
    public void mousePressed(MouseEvent me) {
        PaintFrame pframe = (PaintFrame) me.getSource();
        pframe.addPoint(new Point(me.getX(), me.getY()));
        pframe.repaint();
    }
}