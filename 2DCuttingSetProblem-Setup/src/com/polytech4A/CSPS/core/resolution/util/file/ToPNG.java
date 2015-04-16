package com.polytech4A.CSPS.core.resolution.util.file;

import com.polytech4A.CSPS.core.model.Image;
import com.polytech4A.CSPS.core.model.Pattern;
import com.polytech4A.CSPS.core.model.Vector;
import com.polytech4A.CSPS.core.resolution.Resolution;
import com.polytech4A.CSPS.core.util.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Alexandre
 *         09/04/2015
 */
public class ToPNG extends FileMethod {

    /**
     * Font size for legend.
     */
    public static int mFontSize = 30;
    /**
     * Draw border on images or not.
     */
    public static boolean DRAW_BORDER = true;
    /**
     * In grey of in colors.
     */
    public static boolean ALL_IN_GRAY = true;
    /**
     * Pattern Legend or Not.
     */
    public static boolean PATTERN_LEGEND = true;
    /**
     * Background color.
     */
    public static Color BACKGROUND = Color.WHITE;
    /**
     * Greyscale minimum.
     */
    public static int GREY_MIN = 20;
    /**
     * Greyscale maximum.
     */
    public static int GREY_MAX = 200;
    /**
     * Saturation for colored mod.
     */
    public static float SATURATION = 0.9f;
    /**
     * Brightness for colored mod.
     */
    public static float BRIGHTNESS = 0.7f;

    /**
     * Void Constructor.
     */
    public ToPNG() {
        super("png", "png");
    }

    /**
     * Get a grey color.
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color
     */
    public static Color getGrayColor(int pId, int pNb) {
        int gray_val;

        if (pNb == 1)
            gray_val = 125;
        else {
            gray_val = GREY_MIN + (((GREY_MAX - GREY_MIN) * pId) / (pNb - 1));
        }
        return new Color(gray_val, gray_val, gray_val, 250);
    }

    /**
     * Get a color.
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color
     */
    public static Color getColoredColor(int pId, int pNb) {
        float color_val;

        if (pNb == 1)
            color_val = 0;
        else {
            color_val = (float) pId / (pNb - 1);
        }
        return Color.getHSBColor(color_val, SATURATION, BRIGHTNESS);
    }

    /**
     * Get a color or a gray
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color color in grey or not.
     */
    public static Color getColor(int pId, int pNb) {
        if (ALL_IN_GRAY) {
            return getGrayColor(pId, pNb);
        } else {
            return getColoredColor(pId, pNb);
        }
    }

    /**
     * Text color in function of the background.
     *
     * @param pBackgroundColor Color in RGB
     * @return Color Color of the background (Black or White)
     */
    public static Color getTextColor(Color pBackgroundColor) {
        int red = pBackgroundColor.getRed();
        int green = pBackgroundColor.getGreen();
        int blue = pBackgroundColor.getBlue();

        if ((red * 299 + green * 587 + blue * 114) / 1000 < 125) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Draw a rectangle on the buffered image img
     *
     * @param img    Image onto print Rectangle.
     * @param x      horizontal origin position.
     * @param y      vertical origin position.
     * @param width  horizontal size.
     * @param height vertical size
     * @param label  label of Rectangle.
     * @param color  background color of rectangle.
     */
    public static void drawRectangle(BufferedImage img, int x, int y, int width,
                                     int height, String label, Color color) {

        Graphics2D graph = img.createGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(color);

        graph.fill(new Rectangle(x, y, width, height));

        if (DRAW_BORDER) {
            graph.setPaint(Color.red);
            int border_size = 2;
            for (int i = 0; i < border_size; i++) {
                // top
                graph.drawLine(x, y + i, x + width - 1, y + i);
                // bottom
                graph.drawLine(x, y + (height - 1) - i, x + (width - 1),
                        y + (height - 1) - i);
                // right
                graph.drawLine(x + (width - 1) - i, y, x + (width - 1) - i,
                        y + (height - 1));
                // left
                graph.drawLine(x + i, y, x + i, y + (height - 1));
            }

        }

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, mFontSize);
        graph.setFont(font);

        Rectangle2D textDimensions = graph.getFontMetrics()
                .getStringBounds(label, graph);
        int stringLen = (int) textDimensions.getWidth();
        int start_x = width / 2 - stringLen / 2;

        graph.setPaint(getTextColor(color));
        graph.drawString(label, x + start_x, y + (height / 2) + 30);

        graph.dispose();
    }

    /**
     * Save Method.
     *
     * @param filename   Name of the file.
     * @param resolution Solution et contexte.
     */
    @Override
    public void save(String filename, Resolution resolution) {
        String subDir = filename;
        String baseFilename = filename;
        Date date = new Date();
        mkdir(subDir, date);
        ArrayList<Pattern> patterns = resolution.getSolution().getPatterns();
        int coeff = (int) Math.ceil(800 * resolution.getContext().getScale() / Math
                .max(patterns.get(0).getSize().getWidth(),
                        patterns.get(0).getSize().getHeight()));
        Long y = 0L;
        int length;
        for (Pattern cur_patt : patterns) {
            filename = getFilename(subDir, baseFilename, date, y);
            BufferedImage img = new BufferedImage(
                    (int) (patterns.get(0).getSize().getWidth() * coeff),
                    (int) (patterns.get(0).getSize().getHeight() * coeff),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(BACKGROUND);
            graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
            int num;
            length = cur_patt.getListImg().size();
            for (Image placedBox : cur_patt.getListImg()) {
                num = placedBox.getId().intValue();
                for (Vector position : placedBox.getPositions()) {
                    if (position != null) {
                        Vector size = placedBox.getSize();
                        if (!placedBox.isRotated()) {
                            drawRectangle(img, (int) (position.getWidth() * coeff),
                                    (int) (position.getHeight() * coeff),
                                    (int) (size.getWidth() * coeff),
                                    (int) (size.getHeight() * coeff),
                                    String.valueOf(num),
                                    getColor(num, length));
                        } else {
                            drawRectangle(img, (int) (position.getWidth() * coeff),
                                    (int) (position.getHeight() * coeff),
                                    (int) (size.getHeight() * coeff),
                                    (int) (size.getWidth() * coeff),
                                    String.valueOf(num),
                                    getColor(num, length));
                        }
                    }
                }
            }

            try {
                File file = new File(filename);
                if (!file.exists()) {
                    file.mkdirs();
                    file.createNewFile();
                }
                ImageIO.write(img, "png", file);
                Log.log.info("Create new file " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            y++;
        }
    }
}