package ru.vsu.cs.PoryadinAV;

import java.util.Arrays;

public class Polygon {
    private Point[] points;
    private double area = -1;
    private double perimeter = -1;
    private double[] sides;

    public Polygon(Point[] points) {
        this.points = points;
        this.sides = new double[points.length];
    }

    public double[] getXPoints() {
        double[] xPoints = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x();
        }

        return xPoints;
    }

    public double[] getYPoints() {
        double[] yPoints = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            yPoints[i] = points[i].y();
        }

        return yPoints;
    }

    public Point getPoint(int number) {
        return points[number];
    }

    public double getArea() { // Расчет площади по формуле Гаусса
        if (this.area != -1) {
            return this.area;
        }

        area = 0;
        for (int i = 0; i < points.length; i++) {
            if (i + 1 != points.length) {
                area += points[i].x() * points[i + 1].y();
                area -= points[i + 1].x() * points[i].y();
            } else {
                area += points[i].x() * points[0].y();
                area -= points[0].x() * points[i].y();
            }
        }
        area = 1.0 / 2 * Math.abs(area);

        return area;
    }

    public double getPerimeter() {
        if (this.perimeter != -1) {
            return this.perimeter;
        }

        perimeter = Arrays.stream(this.getSides()).sum();

        return perimeter;
    }

    public double[] getSides() {
        if (!Arrays.equals(this.sides, new double[points.length])) {
            return this.sides;
        }


        for (int i = 0; i < points.length; i++) {
            if (i + 1 != points.length) {
                sides[i] = Math.pow((points[i + 1].x() - points[i].x()), 2) +
                        Math.pow((points[i + 1].y() - points[i].y()), 2);
            } else {
                sides[i] = Math.pow((points[0].x() - points[i].x()), 2) +
                        Math.pow((points[0].y() - points[i].y()), 2);
            }
        }

        return sides;
    }

    public Polygon calculateRectangleDescribingThisPolygon() {
        double maxX = points[0].x();
        double maxY = points[0].y();
        double minX = points[0].x();
        double minY = points[0].y();

        for (Point point : points) {
            maxX = Math.max(point.x(), maxX);
            maxY = Math.max(point.y(), maxY);
            minX = Math.min(point.x(), minX);
            minY = Math.min(point.y(), minY);
        }

        return new Polygon(new Point[]{new Point(minX, maxY), new Point(maxX, maxY),
                new Point(maxX, minY), new Point(minX, minY)});
    }

    public void movePolygon(int x, int y) {
        Point[] movedPolygon = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            movedPolygon[i] = new Point(points[i].x() + x, points[i].y() + y);
        }
        points = movedPolygon;
    }

}
