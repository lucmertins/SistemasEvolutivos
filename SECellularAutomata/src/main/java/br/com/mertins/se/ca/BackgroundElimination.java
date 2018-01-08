package br.com.mertins.se.ca;

import br.com.mertins.se.ca.util.Generic;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class BackgroundElimination {

    public void process(Properties properties) throws IOException {
        String file = ((String) properties.get("backgroundelimination_file")).trim();
        String folderDest = ((String) properties.get("backgroundelimination_folderDest")).trim();

        File fileImg = new File(file);
        BufferedImage bufferedImage = ImageIO.read(fileImg);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int type = bufferedImage.getType();
        int[][] imageT0 = new int[height][width];

        int rgbBackground = bufferedImage.getRGB(0, 0);
        Color backgroundColor = new Color(rgbBackground);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgbTarget = bufferedImage.getRGB(col, row);
                Color targetColor = new Color(rgbTarget);
                int red = Math.abs(targetColor.getRed() - backgroundColor.getRed());
                int green = Math.abs(targetColor.getGreen() - backgroundColor.getGreen());
                int blue = Math.abs(targetColor.getBlue() - backgroundColor.getBlue());
                Color result = new Color(red, green, blue);
                imageT0[row][col] = result.getRGB();
            }
        }
        BufferedImage newImage = new BufferedImage(width, height, type);
        for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    newImage.setRGB(col, row, imageT0[row][col]);
                }
            }
        ImageIO.write(newImage, "png", new File(String.format("%s%s%s_background.png", folderDest, File.separator, Generic.removeExtensionFile(fileImg.getName()))));

    }

}
