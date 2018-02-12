package br.com.mertins.se.ca;

import br.com.mertins.se.ca.util.Generic;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class Waterfall {

    private Properties properties;
    private String fileName;
    private String folderDest;
    private int[][] imageT0;
    private int width;
    private int height;
    private int type;

    private void init(Properties properties, File file) throws IOException {
        this.properties = properties;
        this.folderDest = ((String) properties.get("waterfall_folderDest")).trim();
        BufferedImage bufferedImage = ImageIO.read(file);
        this.fileName = Generic.removeExtensionFile(file.getName());
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.type = bufferedImage.getType();
        this.imageT0 = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.imageT0[row][col] = bufferedImage.getRGB(col, row);
            }
        }
    }

    private void process() throws IOException {
        BackgroundElimination back = new BackgroundElimination();
        back.init(imageT0, type);
        int[][] imgBack = back.process();

        EdgeDetection edgeDetec = new EdgeDetection();
        edgeDetec.init(imgBack, type, properties);
        int[][] imgEdge = edgeDetec.process();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newImage.setRGB(col, row, imgEdge[row][col]);
            }
        }
        ImageIO.write(newImage, "png", new File(String.format("%s%s%s_waterfall.png", folderDest, File.separator, fileName)));
    }

    public static void process(Properties properties) throws IOException {

        String aval = ((String) properties.get("waterfall_aval")).trim().toLowerCase();
        switch (aval) {
            case "file":
                Waterfall waterfallUnique = new Waterfall();
                waterfallUnique.init(properties, new File(((String) properties.get("waterfall_file")).trim()));
                waterfallUnique.process();
                break;
            case "folder":
                File folderOrig = new File(((String) properties.get("waterfall_originfolder")).trim());
                for (File file : folderOrig.listFiles()) {
                    Waterfall waterfall = new Waterfall();
                    waterfall.init(properties, file);
                    waterfall.process();
                }
                break;
        }

    }
}
