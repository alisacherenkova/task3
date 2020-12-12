package com.company.drawers;


import com.company.RealPoint;

public class Arc {
    private double x;
    private double y;
    private int width;
    private int height;
    private int startAngle;
    private int arcAngle;
    private RealPoint p;

    public Arc(double x, double y, int width, int height, int startAngle, int arcAngle) {
        this.p = new RealPoint(x, y);
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(int arcAngle) {
        this.arcAngle = arcAngle;
    }

    public RealPoint getP() {
        return p;
    }

    public void setP(RealPoint p) {
        this.p = p;
    }
}
