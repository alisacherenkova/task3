package com.company.screen;

import com.company.RealPoint;

public class ScreenConverter {
    private double xR, yR, wR, hR;
    private int wS, hS;

    public ScreenConverter(double xR, double yR, double wR, double hR, int wS, int hS) {
        this.xR = xR;
        this.yR = yR;
        this.wR = wR;
        this.hR = hR;
        this.wS = wS;
        this.hS = hS;
    }

    public ScreenPoint r2s(RealPoint p){
        double x = (p.getX() - xR) * wS / wR;
        double y = (yR - p.getY()) * hS / hR;
        return  new ScreenPoint((int)x, (int)y);
    }

    public RealPoint converterScreen2Real(ScreenPoint screenPoint){
        double x = xR + (screenPoint.getX() * wR / wS);
        double y = yR - (screenPoint.getY() * hR / hS);
        return new RealPoint(x, y);
    }

    public double getxR() {
        return xR;
    }

    public void setxR(double xR) {
        this.xR = xR;
    }

    public double getyR() {
        return yR;
    }

    public void setyR(double yR) {
        this.yR = yR;
    }

    public double getwR() {
        return wR;
    }

    public void setwR(double wR) {
        this.wR = wR;
    }

    public double gethR() {
        return hR;
    }

    public void sethR(double hR) {
        this.hR = hR;
    }

    public int getwS() {
        return wS;
    }

    public void setwS(int wS) {
        this.wS = wS;
    }

    public int gethS() {
        return hS;
    }

    public void sethS(int hS) {
        this.hS = hS;
    }
}
