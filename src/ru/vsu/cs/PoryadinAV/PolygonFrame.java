package ru.vsu.cs.PoryadinAV;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.cs.PoryadinAV.utils.SwingUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PolygonFrame extends JFrame {
    private JPanel panelMain;
    private JLabel labelImg;
    private JButton buttonSaveToFile;
    private JButton buttonLoadPolygon;
    private JLabel labelArea;
    private JLabel labelPerimeter;
    private JButton buttonCalculateRectangleDescribingThisPolygon;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    private PolygonGraphics polygonGraphics;
    private Polygon polygon;
    private BufferedImage img;
    private Graphics2D g2d;

    public PolygonFrame() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        polygonGraphics = new PolygonGraphics();
        img = new BufferedImage(480, 480, BufferedImage.TYPE_INT_BGR);
        g2d = img.createGraphics();

        this.pack();

        buttonLoadPolygon.addActionListener(e -> {
            if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                polygon = Main.readPolygonFromFile(fileChooserOpen.getSelectedFile().getPath());
                update();

                labelArea.setText("Площадь фигуры: " + String.format("%.3f", polygon.getArea()));
                labelPerimeter.setText("Периметр фигуры: " + String.format("%.3f", polygon.getPerimeter()));
            }

        });

        buttonSaveToFile.addActionListener(e -> {
            if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                ImageIcon image = (ImageIcon) labelImg.getIcon();
                BufferedImage img = (BufferedImage) image.getImage();

                String fileName = fileChooserSave.getSelectedFile().getPath();
                if (!fileName.toLowerCase().endsWith(".jpg")) {
                    fileName += ".jpg";
                }

                File file = new File(fileName);
                try {
                    Main.printImageToFile(file, img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonCalculateRectangleDescribingThisPolygon.addActionListener(e -> {
            try {
                Polygon rectangleDescribingThisPolygon = polygon.calculateRectangleDescribingThisPolygon();
                polygonGraphics.drawPolygon(rectangleDescribingThisPolygon, g2d);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            labelImg.setIcon(new ImageIcon(img));
        });

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    switch (e.getKeyCode()) {
                        case 37 -> {
                            polygon.movePolygon(-1, 0);
                            update();
                        }
                        case 38 -> {
                            polygon.movePolygon(0, -1);
                            update();
                        }
                        case 39 -> {
                            polygon.movePolygon(1, 0);
                            update();
                        }
                        case 40 -> {
                            polygon.movePolygon(0, 1);
                            update();
                        }
                    }
                }

                return false;
            }
        });
    }

    private void update() {
        try {
            Image srcImage = ImageIO.read(new File("./CoordPlosk.png"));
            g2d.setColor(Color.BLACK);
            g2d.drawImage(srcImage, 0, 0, null);
            polygonGraphics.drawPolygon(polygon, g2d);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        labelImg.setIcon(new ImageIcon(img));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(3, 4, new Insets(10, 10, 10, 10), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Размер треугольника:");
        panelMain.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Вложенность (кол-во уровней):");
        panelMain.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelMain.add(spacer1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelMain.add(scrollPane1, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelImg = new JLabel();
        labelImg.setHorizontalAlignment(2);
        labelImg.setVerticalAlignment(1);
        scrollPane1.setViewportView(labelImg);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
