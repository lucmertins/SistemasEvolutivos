package br.com.mertins.se.ca;

import br.com.mertins.se.ca.util.Generic;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class BackgroundElimination {

    private String fileName;
    private String folderDest;
    private Boolean save;
    private int[][] imageT0;
    private int width;
    private int height;
    private int type;

    public void init(Properties properties) throws IOException {
        String file = ((String) properties.get("backgroundelimination_file")).trim();
        this.folderDest = ((String) properties.get("backgroundelimination_folderDest")).trim();
        this.save = Boolean.valueOf(((String) properties.get("backgroundelimination_savefile")).trim());
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

    public void init(int[][] image, int type) {
        this.imageT0 = image;
        this.height = image.length;
        this.width = image[0].length;
        this.type = type;
        this.save = Boolean.FALSE;
    }

    public int[][] process() throws IOException {
        int[][] imageT1 = new int[height][width];
        int rgbBackground = detectBackground();
        Color backgroundColor = new Color(rgbBackground);
//        int rgbBackground = imageT0[0][0];
//        Color backgroundColor = new Color(rgbBackground);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgbTarget = imageT0[row][col];
                Color targetColor = new Color(rgbTarget);
                int red = Math.abs(targetColor.getRed() - backgroundColor.getRed());
                int green = Math.abs(targetColor.getGreen() - backgroundColor.getGreen());
                int blue = Math.abs(targetColor.getBlue() - backgroundColor.getBlue());
                Color result = new Color(red, green, blue);
                imageT1[row][col] = result.getRGB();
            }
        }
        if (save) {
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    newImage.setRGB(col, row, imageT1[row][col]);
                }
            }
            ImageIO.write(newImage, "png", new File(String.format("%s%s%s_background.png", folderDest, File.separator, fileName)));
        }
        return imageT1;
    }

    private Integer detectBackground() {
        int numLinha = height * 25 / 100;
        Map<Integer, Integer> colorCount = new HashMap<>();
        for (int row = 0; row < numLinha; row++) {
            for (int col = 0; col < width; col++) {
                Integer color = this.imageT0[row][col];
                avaliaCores(color, colorCount);
            }
        }
        return colorCount.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    private void avaliaCores(Integer newColor, Map<Integer, Integer> colorCount) {
        Integer best = null;
        Integer bestModulo = Integer.MAX_VALUE;
        for (Integer savedColor : colorCount.keySet()) {
            int modulo = modulo(newColor, savedColor);
            if (modulo < 50 && modulo < bestModulo) {
                best = savedColor;
                bestModulo = modulo;
            }
        }
        if (best == null) {
            colorCount.put(newColor, 1);
        } else {
            colorCount.put(best, colorCount.get(best) + 1);
        }
    }

    private int modulo(Integer newColor, Integer savedColor) {
        Color targetColor = new Color(newColor);
        Color neighborhoodColor = savedColor == null ? Color.WHITE : new Color(savedColor);
        int blue = Math.abs(targetColor.getBlue() - neighborhoodColor.getBlue());
        int red = Math.abs(targetColor.getRed() - neighborhoodColor.getRed());
        int green = Math.abs(targetColor.getGreen() - neighborhoodColor.getGreen());
        return blue + red + green;
    }
}
