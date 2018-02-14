package br.com.mertins.se.avalopencv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 * @author mertins
 */
public class DescribeImages {

    private String tipo = "AC";

    public void positives() throws IOException {
        File folder = new File(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%spositiveSameSize",tipo));
        PrintWriter writer = new PrintWriter(new FileWriter("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/resenhas.info"));
        double minProp = Double.MAX_VALUE;
        double maxProp = Double.MIN_VALUE;

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        int minWidth = Integer.MAX_VALUE;
        int maxWidth = Integer.MIN_VALUE;

        for (File file : folder.listFiles()) {
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            double prop = (double) width / (double) height;
            writer.format("%s 1 0 0 %d %d\n", file.getAbsoluteFile(), width, height);
            minProp = Math.min(minProp, prop);
            maxProp = Math.max(maxProp, prop);
            minHeight = Math.min(minHeight, height);
            maxHeight = Math.max(maxHeight, height);
            minWidth = Math.min(minWidth, width);
            maxWidth = Math.max(maxWidth, width);

        }
        System.out.printf("Positive    maxProp[%f]  minProp[%f]  minHeight[%d]  maxHeight[%d]  minWidth[%d]  minHeight[%d] \n", maxProp, minProp, minHeight, maxHeight, minWidth, maxWidth);
        writer.close();
    }

    public void negatives() throws IOException {
        File folder = new File(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%snegative",tipo));
        PrintWriter writer = new PrintWriter(new FileWriter("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/bg.txt"));
        double minProp = Double.MAX_VALUE;
        double maxProp = Double.MIN_VALUE;

        int minHeight = Integer.MAX_VALUE;
        int maxHeight = Integer.MIN_VALUE;
        int minWidth = Integer.MAX_VALUE;
        int maxWidth = Integer.MIN_VALUE;
        for (File file : folder.listFiles()) {
            writer.format("%s\n", file.getAbsoluteFile());
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            double prop = (double) width / (double) height;
            minProp = Math.min(minProp, prop);
            maxProp = Math.max(maxProp, prop);
            minHeight = Math.min(minHeight, height);
            maxHeight = Math.max(maxHeight, height);
            minWidth = Math.min(minWidth, width);
            maxWidth = Math.max(maxWidth, width);

        }
        System.out.printf("Negative    maxProp[%f]  minProp[%f]  minHeight[%d]  maxHeight[%d]  minWidth[%d]  minHeight[%d] \n", maxProp, minProp, minHeight, maxHeight, minWidth, maxWidth);

        writer.close();
    }

    public static void main(String[] args) throws IOException {
        DescribeImages desc = new DescribeImages();
        desc.positives();
        desc.negatives();

    }
}
