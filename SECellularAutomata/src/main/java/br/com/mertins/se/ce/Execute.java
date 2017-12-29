package br.com.mertins.se.ce;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class Execute {

    public static void main(String[] args) throws IOException {
        File fileImg = new File("src/main/resources/images/FRONT_59b97a303dbc0e51496444b8.png");
        BufferedImage bufferedImage = ImageIO.read(fileImg);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[][] imageT0 = new int[height][width];
        int[][] imageT1 = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                imageT0[row][col] = bufferedImage.getRGB(col, row);
            }
        }
    }
}
