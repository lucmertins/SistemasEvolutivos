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
public class EdgeDetection {

    private String folderDest;
    private Boolean save;
    private String fileName;
    private Integer maxGeneration;
    private Neighborhood.Type neighborhoodType;
    private Integer threshold;
    private int width;
    private int height;
    private int type;
    private int[][] imageT0 = new int[height][width];

    public void init(Properties properties) throws IOException {
        String file = ((String) properties.get("edgedetection_file")).trim();
        this.folderDest = ((String) properties.get("edgedetection_folderDest")).trim();
        this.save = Boolean.valueOf(((String) properties.get("edgedetection_savefile")).trim());
        this.maxGeneration = Integer.valueOf((String) properties.get("edgedetection_maxgeneration"));
        this.neighborhoodType = Neighborhood.Type.valueOf(((String) properties.get("edgedetection_neighborhood")).toUpperCase());
        this.threshold = Integer.valueOf((String) properties.get("edgedetection_threshold"));
        File fileImg = new File(file);
        this.fileName = Generic.removeExtensionFile(fileImg.getName());
        BufferedImage bufferedImage = ImageIO.read(fileImg);
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

    public void init(int[][] image, int type, Properties properties) {
        this.imageT0 = image;
        this.height = image.length;
        this.width = image[0].length;
        this.type = type;
        this.save = Boolean.FALSE;
        this.maxGeneration = Integer.valueOf((String) properties.get("edgedetection_maxgeneration"));
        this.neighborhoodType = Neighborhood.Type.valueOf(((String) properties.get("edgedetection_neighborhood")).toUpperCase());
        this.threshold = Integer.valueOf((String) properties.get("edgedetection_threshold"));
    }

    public int[][] process() throws IOException {
        int[][] imageT1 = new int[height][width];

        for (int geracao = 1; geracao <= maxGeneration; geracao++) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Integer[] neighborhood = Neighborhood.process(neighborhoodType, row, col, imageT0);
                    int totalAbs = 0;
                    for (Integer value : neighborhood) {
                        totalAbs += modulo(imageT0[row][col], value);
                    }
                    imageT1[row][col] = totalAbs < threshold ? 0 : imageT0[row][col];
                }
            }
            if (save) {
                BufferedImage newImage = new BufferedImage(width, height, type);
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        newImage.setRGB(col, row, imageT1[row][col]);
                    }
                }
                ImageIO.write(newImage, "png", new File(String.format("%s%s%s_edgedetec_%d.png", folderDest, File.separator, fileName, geracao)));
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    imageT0[row][col] = imageT1[row][col];
                }
            }
        }
        return imageT1;
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
