package com.company;

import com.company.drawers.Arc;
import com.company.drawers.BufferImagePixelDrawer;
import com.company.drawers.DDALineDrawer;
import com.company.drawers.GraphicsArcDrawer;
import com.company.interfaces.IArcDrawer;
import com.company.interfaces.LineDrawer;
import com.company.interfaces.PixelDrawer;
import com.company.screen.ScreenConverter;
import com.company.screen.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {

    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);

    private List<Line> allLines = new ArrayList<>();
    BufferedImage bi;
    Graphics2D gr;
    PixelDrawer pd;
    LineDrawer ld;


    private List<List<RealPoint>> polygons = new ArrayList<>();
    private List<RealPoint> currentPoints = new ArrayList<>();
    private List<List<RealPoint>> roundedPolygons = new ArrayList<>();
    private ArrayList<Polygon> polygonList;
    //private ArrayList<Color> colorList;

    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);


        //setBackground(Color.white);
        //setForeground(Color.black);
        //инициализация трех списков
//        currentPoints = new ArrayList<>();
//        polygonList = new ArrayList<>();
//        polygons = new ArrayList<>();
        //colorList = new ArrayList<>();

        /*addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }
        });*/

        /*addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

            }
        });*/

    }


    @Override
    public void paint(Graphics g) {
        bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        gr = bi.createGraphics();
        sc.setwS(getWidth());
        sc.sethS(getHeight());
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();
        pd = new BufferImagePixelDrawer(bi);
        ld = new DDALineDrawer(pd);
        /**/

        ld.drawLine(sc.r2s(xAxis.getP1()), sc.r2s(xAxis.getP2()));
        ld.drawLine(sc.r2s(yAxis.getP1()), sc.r2s(yAxis.getP2()));
        /**/
        for (List<RealPoint> l : polygons) {
            drawPolygon(l, ld, g);
        }
        if (!currentPoints.isEmpty()) {
            drawPolygon(currentPoints, ld, g);
        }
        IArcDrawer ad = new GraphicsArcDrawer(gr);
        for (List<RealPoint> l : roundedPolygons) {
            rounding(sc, ld, ad, l, 10);
//            repaint();
        }
        drawAll(ld);
//        if (isBackSpace) {
//            gr.drawString("бибу", 300, 300);
//            repaint();
//            for (List<RealPoint> points : polygons) {
//                drawRoundedPolygon(sc, ld, ad, points, 2);
//                repaint();
//            }
//            isBackSpace = false;
//        }
        g.drawImage(bi, 0, 0, null);
    }


    private void drawAll(LineDrawer ld) {
        drawOneLine(ld, xAxis);
        drawOneLine(ld, yAxis);

        for (Line l : allLines) {
            drawOneLine(ld, l);
        }

        if (currentNewLine != null) {
            drawOneLine(ld, currentNewLine);
        }
    }

    private void drawOneLine(LineDrawer ld, Line line) {
        ld.drawLine(sc.r2s(line.getP1()), sc.r2s(line.getP2()));
    }

    private ScreenPoint prevPosition = null;

    private Line currentNewLine = null;


    @Override
    public void mouseDragged(MouseEvent e) {
        /*ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        if(prevPosition != null){
            ScreenPoint delta = new ScreenPoint(
                    -currentPosition.getX() + prevPosition.getX(),
                    -currentPosition.getY() + prevPosition.getY());
            RealPoint deltaReal = sc.converterScreen2Real(delta);

            sc.setxR(deltaReal.getX());
            sc.setyR(deltaReal.getY());
            prevPosition = currentPosition;
        }
        if(currentNewLine != null){
            currentNewLine.setP2(sc.converterScreen2Real(currentPosition));
        }
        repaint();*/
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocus();
        currentPoints.add(sc.converterScreen2Real(new ScreenPoint(e.getX(), e.getY())));
        if (currentPoints.size() > 1) {
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
       /* if(e.getButton() == MouseEvent.BUTTON3){
            prevPosition = new ScreenPoint(e.getX(), e.getY());
        }
        else if(e.getButton() == MouseEvent.BUTTON1){
            ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
            currentNewLine = new Line(sc.converterScreen2Real(current), sc.converterScreen2Real(current));

        }

        repaint();*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*if(e.getButton() == MouseEvent.BUTTON3){
            prevPosition = null;
        }
        else if(e.getButton() == MouseEvent.BUTTON1){
            if(currentNewLine != null)
                allLines.add(currentNewLine);
            currentNewLine = null;

        }
        prevPosition = null;
*/
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double step = (clicks < 0) ? 0.9 : 1.1;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale = scale * step;
        }
        sc.setwR(sc.getwR() * scale);
        sc.sethR(sc.gethR() * scale);
        repaint();
    }




//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        //нарисованный полигон сохранять в лист
//        for (int x = 0; x < polygonList.size(); x++) {
//            //g.setColor(colorList.get(x));
//            g.fillPolygon(polygonList.get(x));
//        }
//
//        //нарисовать точки ридиусом 2
//        g.setColor(Color.black);
//        for (Point p: currentPoints) {
//            g.fillOval(p.x - radiusPoint, p.y - radiusPoint, radiusPoint * 2, radiusPoint * 2);
//        }
//
//        //рисовать линии между точками
//        for (int x = 0; x < currentPoints.size(); x++) {
//            //если это последняя точка, то провести линию между ней и непрвой точкой
//            if (x == currentPoints.size() - 1) {
//                g.drawLine(currentPoints.get(x).x, currentPoints.get(x).y,
//                        currentPoints.get(0).x, currentPoints.get(0).y);
//            }
//            //а если нет, то провести линию между текущей точкой и следующей
//            else {
//                g.drawLine(currentPoints.get(x).x, currentPoints.get(x).y,
//                        currentPoints.get(x+1).x, currentPoints.get(x+1).y);
//            }
//        }
//    }

// спиздили
//    public void fillPolygon() {
//        int[] xPoints = new int[pointList.size()];
//        int[] yPoints = new int[pointList.size()];
//
//        for (int i = 0; i < xPoints.length; i++) {
//            xPoints[i] = pointList.get(i).x;
//        }
//        for (int i = 0; i < yPoints.length; i++) {
//            yPoints[i] = pointList.get(i).y;
//        }
//
//        polygon = new Polygon(xPoints, yPoints, xPoints.length);
//    }

//    public void drawPolygon() {
//        int[] X = new int[currentPoints.size()];
//        int[] Y = new int[currentPoints.size()];
//
//        //построить массивы координат
//        for (int i = 0; i < currentPoints.size(); i++) {
//            X[i] = currentPoints.get(i).x;
//            Y[i] = currentPoints.get(i).y;
//        }
//
//        //добавить новый полигон в лист
//        polygonList.add(new Polygon(X, Y, currentPoints.size()));
//        //Add a corresponding color to the color list.
//        //colorList.add(genNewColor());
//        //сбросить текущие точки
//        currentPoints.clear();
//    }

    public static final int radiusPoint = 5;

    public void drawPolygon(List<RealPoint> points, LineDrawer ld, Graphics g) {
        RealPoint point = points.get(0);
        for (RealPoint p: currentPoints) {
           //g.fillOval(p.getX() - radiusPoint, p.getY() - radiusPoint, radiusPoint * 2, radiusPoint * 2);
        }
        for (int i = 0; i < points.size(); i++) {
            if (i < points.size() - 1) {
                ld.drawLine(sc.r2s(points.get(i)), sc.r2s(points.get(i + 1)));
                g.setColor(Color.black);

            } else {
                ld.drawLine(sc.r2s(points.get(i)), sc.r2s(point));
            }
        }
    }

    public void rounding(ScreenConverter sc, LineDrawer ld, IArcDrawer ad,/*Graphics g,*/  List<RealPoint> tops, double r) {

        Map<Integer, RealPoint> points = new HashMap<>();
        int count = 0;
        if (tops.size() > 1) {
            for (int i = 0; i < tops.size(); i++) {
                double x1;
                double y1;
                double x2;
                double y2;
                double x3;
                double y3;
                if (i == 0) {
                    x1 = tops.get(tops.size() - 1).getX();
                    y1 = tops.get(tops.size() - 1).getY();
                    x2 = tops.get(i).getX();
                    y2 = tops.get(i).getY();
                    x3 = tops.get(i + 1).getX();
                    y3 = tops.get(i + 1).getY();
                } else if (i == tops.size() - 1) {
                    x1 = tops.get(i - 1).getX();
                    y1 = tops.get(i - 1).getY();
                    x2 = tops.get(i).getX();
                    y2 = tops.get(i).getY();
                    x3 = tops.get(0).getX();
                    y3 = tops.get(0).getY();
                } else {
                    x1 = tops.get(i - 1).getX();
                    y1 = tops.get(i - 1).getY();
                    x2 = tops.get(i).getX();
                    y2 = tops.get(i).getY();
                    x3 = tops.get(i + 1).getX();
                    y3 = tops.get(i + 1).getY();
                }

                double dx1 = x2 - x1;
                double dy1 = y2 - y1;
                double dx2 = x2 - x3;
                double dy2 = y2 - y3;
                //Angle between vector 1 and vector 2 divided by 2
                double angle = (Math.atan2(dy1, dx1) - Math.atan2(dy2, dx2)) / 2;

                // The length of segment between angular point and the
                // points of intersection with the circle of a given radius
                double segment = r / Math.abs(Math.tan(angle));

                //Check the segment
                double length1 = Math.sqrt(dx1 * dx1 + dy1 * dy1);
                double length2 = Math.sqrt(dx2 * dx2 + dy2 * dy2);

                double length = Math.min(length1, length2);

                if (segment > length) {
                    segment = length;
                    r = length * Math.abs(Math.tan(angle));
                }

                double p1X = x2 - dx1 * segment / length1;
                double p1Y = y2 - dy1 * segment / length1;
                double p2X = x2 - dx2 * segment / length2;
                double p2Y = y2 - dy2 * segment / length2;

                RealPoint p1 = new RealPoint(p1X, p1Y);
                RealPoint p2 = new RealPoint(p2X, p2Y);
                count++;
                points.put(count, p1);
                count++;
                points.put(count, p2);

                // Calculation of the coordinates of the circle
                // center by the addition of angular vectors.
                double dx = x2 * 2 - p1X - p2X;
                double dy = y2 * 2 - p1Y - p2Y;

                double l = Math.sqrt(dx * dx + dy * dy);
                double d = Math.sqrt(segment * segment + r * r);

                double circlePointX = x2 - dx * d / l;
                double circlePointY = y2 - dy * d / l;

                //StartAngle and EndAngle of arc
                double startAngle = Math.atan2(p1Y - circlePointY, p1X - circlePointX);
                double endAngle = Math.atan2(p2Y - circlePointY, p2X - circlePointX);

                double sweepAngle = endAngle - startAngle;
                // sweepAngle = sweepAngle * 180 / Math.PI;
                if (sweepAngle < 0) {
                    startAngle = endAngle;
                    sweepAngle = -sweepAngle;
                }
                if (sweepAngle > Math.PI) {
                    sweepAngle = -(sweepAngle + 2 * (Math.PI - sweepAngle));

                }


                double left = circlePointX - r;
                double top = circlePointY + r;
                RealPoint pCoordinate = new RealPoint(left, top);
                Arc p = new Arc((sc.r2s(pCoordinate)).getX(), (sc.r2s(pCoordinate)).getY(), (int) (2 * r * sc.getwS() / sc.getwR()), (int) (2 * r * sc.gethS() / sc.gethR()), (int) (startAngle * 180 / Math.PI), (int) (sweepAngle * 180 / Math.PI));
                ad.drawArc(p);
//                g.drawArc((sc.r2s(pCoordinate)).getX(), (sc.r2s(pCoordinate)).getY(), (int) (2 * r * sc.getwS() / sc.getwR()), (int) (2 * r * sc.gethS() / sc.gethR()), (int) (startAngle * 180 / Math.PI), (int) (sweepAngle * 180 / Math.PI));
            }

            for (int i = 1; i <= points.size(); i = i + 2) {
                Line l;
                if (i == 1) {
                    l = new Line(points.get(points.size()).getX(), points.get(points.size()).getY(), points.get(i).getX(), points.get(i).getY());
                } else {
                    l = new Line(points.get(i - 1).getX(), points.get(i - 1).getY(), points.get(i).getX(), points.get(i).getY());
                }

                ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
            }
        }

    }

    /*public boolean checkInside(RealPoint rp) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = tops.size() - 1; i < tops.size(); j = i++) {
            if ((tops.get(i).getY() > rp.getY()) != (tops.get(j).getY() > rp.getY()) &&
                    (rp.getX() < (tops.get(j).getX() - tops.get(i).getX()) * (rp.getY() - tops.get(i).getY()) / (tops.get(j).getY() - tops.get(i).getY()) + tops.get(i).getX())) {
                result = !result;
            }
        }
//        boolean result = false;
//        int j = tops.size() - 1;
//        for (int i = 0; i < tops.size(); i++) {
//            if ((tops.get(i).getY() < rp.getY() && tops.get(j).getY() >= rp.getY() || tops.get(j).getY() < rp.getY() && tops.get(i).getY() >= rp.getY()) &&
//                    (tops.get(i).getX() + (rp.getY() - tops.get(i).getY()) / (tops.get(j).getY() - tops.get(i).getY()) * (tops.get(i).getX() - tops.get(i).getX()) < rp.getX()))
//                result = !result;
//            j = i;
//        }

        return result;
    }*/


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //если нажата backspace, удалить последний многоульник
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            polygons.add(currentPoints);
            roundedPolygons.addAll(polygons);
            polygons.clear();
            repaint();
//            polygonList.remove(polygonList.size() - 1);
//            repaint();
        }
        //если не нажата, добавлять новый многоульник
        else if (e.getKeyCode() == 32) {
            polygons.add(currentPoints);
            repaint();
            currentPoints = new ArrayList<>();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}