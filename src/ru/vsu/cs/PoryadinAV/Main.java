package ru.vsu.cs.PoryadinAV;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import ru.vsu.cs.PoryadinAV.utils.ArrayUtils;
import ru.vsu.cs.PoryadinAV.utils.SwingUtils;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new PolygonFrame().setVisible(true);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
    }

    public static void printImageToFile(File file, BufferedImage image) throws IOException {
        ImageIO.write(image, "jpg", file);
    }

    public static Polygon readPolygonFromFile(String path) {
        double[][] polygonInMatrix = ArrayUtils.readDoubleArray2FromFile(path);
        Point[] polygonPoints = new Point[polygonInMatrix.length];

        for (int i = 0; i < polygonInMatrix.length; i++){
            polygonPoints[i] = new Point(polygonInMatrix[i][0], polygonInMatrix[i][1]);
        }

        return new Polygon(polygonPoints);
    }
}
