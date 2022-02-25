package ru.vsu.cs.PoryadinAV;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PolygonGraphics {

    public void drawPolygon(Polygon polygon, Graphics2D g2d) throws IOException {
        double[] xPoints = polygon.getXPoints();
        double[] yPoints = polygon.getYPoints();
        g2d.drawPolygon(calculateFromCoordsToPixels(xPoints),
                calculateFromCoordsToPixels(yPoints), xPoints.length);
    }

    private int[] calculateFromCoordsToPixels(double[] points) {
        int[] newPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            newPoints[i] = 240 + (int) Math.round(20 * points[i]);
        }

        return newPoints;
    }
}
