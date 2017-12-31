package br.com.mertins.se.ca;

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
public class EdgeDetection {

    public void process(Properties properties) throws IOException {
        String file = ((String) properties.get("edgedetection_file")).trim();
        String folderDest = ((String) properties.get("edgedetection_folderDest")).trim();
        File fileImg = new File(file);
        BufferedImage bufferedImage = ImageIO.read(fileImg);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int type = bufferedImage.getType();
        int[][] imageT0 = new int[height][width];
        int[][] imageT1 = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                imageT1[row][col] = bufferedImage.getRGB(col, row);
            }
        }

        for (int geracao = 1; geracao < 10; geracao++) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    imageT0[row][col] = imageT1[row][col];
                }
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Integer[] vonNeumann = vonNeumannneighborhood(row, col, imageT0);
                    int totalAbs = 0;
                    for (Integer value : vonNeumann) {
                        totalAbs += modulo(imageT0[row][col], value);
                    }
                    imageT1[row][col] = totalAbs < 20 ? 0 : imageT0[row][col];
//                System.out.printf("cor [%s]  vector [%s]  totalAbs [%d]\n ", mycolor.toString(), showVector(vonNeumann), totalAbs);
                }
            }
            BufferedImage newImage = new BufferedImage(width, height, type);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    newImage.setRGB(col, row, imageT1[row][col]);
                }
            }
            ImageIO.write(newImage, "png", new File(String.format("%s%s%s_%d.png", folderDest, File.separator, fileImg.getName(), geracao)));
        }
    }

    private Integer[] vonNeumannneighborhood(int row, int col, int[][] image) {
        Integer[] values = new Integer[5];
        values[0] = col > 0 ? image[row][col - 1] : null;
        values[1] = col < image[0].length - 1 ? image[row][col + 1] : null;
        values[2] = image[row][col];
        values[3] = row > 0 ? image[row - 1][col] : null;
        values[4] = row < image.length - 1 ? image[row + 1][col] : null;
        return values;
    }

    private int modulo(Integer target, Integer neighborhood) {
        Color targetColor = new Color(target);
        Color neighborhoodColor = neighborhood == null ? Color.WHITE : new Color(neighborhood);
        int blue = Math.abs(targetColor.getBlue() - neighborhoodColor.getBlue());
        int red = Math.abs(targetColor.getRed() - neighborhoodColor.getRed());
        int green = Math.abs(targetColor.getGreen() - neighborhoodColor.getGreen());
        return blue + red + green;
    }

    private String showVector(Integer[] values) {
        StringBuilder sb = new StringBuilder("{");
        for (Integer value : values) {
            sb.append(value);
            sb.append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("}");
        return sb.toString();
    }

}
