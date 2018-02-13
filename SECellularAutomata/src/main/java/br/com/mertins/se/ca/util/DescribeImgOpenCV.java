package br.com.mertins.se.ca.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 * Referência https://www.youtube.com/watch?v=WEzm7L5zoZE
 * http://cogcomp.org/Data/Car/
 *
 * Executar codigo java que produz bg.txt e cars.info
 * https://github.com/lucmertins/OpenCVJava
 *
 * Gerar vector opencv_createsamples -info cars.info -num 550 -w 48 -h 24 -vec
 * cars.vec
 *
 * Visualizar opencv_createsamples -vec cars.vec -w 48 -h 24
 *
 *
 * opencv_traincascade -data data -vec cars.vec -bg bg.txt -numPos 500 -numNeg
 * 500 -numStages 50 -w 48 -h 24 -featureType LBP ou opencv_traincascade -data
 * data -vec cars.vec -bg bg.txt -numPos 500 -numNeg 500 -numStages 50 -w 48 -h
 * 24 -featureType HAAR
 *
 * @author mertins
 */
public class DescribeImgOpenCV {

    public void positives() throws IOException {
        File folder = new File("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/positiveSameSize");
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
        System.out.printf("maxProp[%f]  minProp[%f]  minHeight[%d]  maxHeight[%d]  minWidth[%d]  minHeight[%d] \n", maxProp, minProp, minHeight, maxHeight, minWidth, maxWidth);
        writer.close();
    }

    public void negatives() throws IOException {
        File folder = new File("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/negativeReduzidaQuantSizeMenor");
        PrintWriter writer = new PrintWriter(new FileWriter("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/bg.txt"));
        for (File file : folder.listFiles()) {
            writer.format("%s\n", file.getAbsoluteFile());
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        DescribeImgOpenCV desc = new DescribeImgOpenCV();
        desc.positives();
        desc.negatives();

    }
}
