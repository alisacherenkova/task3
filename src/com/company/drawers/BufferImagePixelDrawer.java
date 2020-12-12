package com.company.drawers;

import com.company.interfaces.PixelDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferImagePixelDrawer implements PixelDrawer {
    private BufferedImage bi;

    public BufferImagePixelDrawer(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public void drawPixel(int x, int y, Color c) {
        if(x >= 0 && y >= 0 && x < bi.getWidth() && y < bi.getHeight())
            bi.setRGB(x, y, c.getRGB());
    }
}
