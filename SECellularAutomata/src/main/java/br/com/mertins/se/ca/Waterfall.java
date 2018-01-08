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

    public void init(Properties properties) throws IOException {
        this.properties = properties;
        String file = ((String) properties.get("waterfall_file")).trim();
        this.folderDest = ((String) properties.get("waterfall_folderDest")).trim();

        File fileImg = new File(file);
        BufferedImage bufferedImage = ImageIO.read(fileImg);
        this.fileName = Generic.removeExtensionFile(fileImg.getName());

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

    public void process() throws IOException {
        BackgroundElimination back = new BackgroundElimination();
        back.init(imageT0, type);
        int[][] imgBack = back.process();

        EdgeDetection edgeDetec = new EdgeDetection();
        edgeDetec.init(imgBack, type, properties);
        int[][] imgEdge = edgeDetec.process();

        BufferedImage newImage = new BufferedImage(width, height, type);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                newImage.setRGB(col, row, imgEdge[row][col]);
            }
        }
        ImageIO.write(newImage, "png", new File(String.format("%s%s%s_waterfall.png", folderDest, File.separator, fileName)));
    }
}
