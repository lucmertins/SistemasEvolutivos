package br.com.mertins.se.ca.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class AdjustImg {

    private final int defaultWidth;
    private final int defaultHeight;

    public AdjustImg() {
        this(100,100);
    }

    public AdjustImg(int defaultWidth, int defaultHeight) {
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    public BufferedImage scaleAndCut(BufferedImage bufferedImage) {
        if (bufferedImage.getWidth() > this.defaultWidth) {
            double proporcao = (double) this.defaultWidth / (double) bufferedImage.getWidth();
            AffineTransform trans = new AffineTransform();
            trans.scale(proporcao, proporcao);
            AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
            bufferedImage = op.filter(bufferedImage, null);
            double difHeight = bufferedImage.getHeight() - this.defaultHeight;
            if (difHeight > 0) {
                // cortar imagem nas bordas superior/inferior
                int top = (int) difHeight / 2;
                bufferedImage = bufferedImage.getSubimage(0, top, this.defaultWidth, this.defaultHeight);
            } else if (difHeight < 0) {
                // adicionar imagem adequada ao tamanho
                BufferedImage newBufferedImg = new BufferedImage(defaultWidth, defaultHeight, bufferedImage.getType());
                Graphics2D graphics = newBufferedImg.createGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, newBufferedImg.getWidth(), newBufferedImg.getHeight());
                graphics.drawImage(bufferedImage, 0, 0, null);
                bufferedImage = newBufferedImg;
            }
        } else {
            // imagem menor ou igual em largura. Avaliar altura.
            double difHeight = bufferedImage.getHeight() - this.defaultHeight;
            if (difHeight > 0) {
                // se imagem > em altura, cortar
                int top = (int) difHeight / 2;
                bufferedImage = bufferedImage.getSubimage(0, top, bufferedImage.getWidth(), this.defaultHeight);
            }
            BufferedImage newBufferedImg = new BufferedImage(defaultWidth, defaultHeight, bufferedImage.getType());
            Graphics2D graphics = newBufferedImg.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, newBufferedImg.getWidth(), newBufferedImg.getHeight());
            graphics.drawImage(bufferedImage, 0, 0, null);
            bufferedImage = newBufferedImg;
        }
        return bufferedImage;
    }

    public BufferedImage rotate(BufferedImage bufferedImage) {
        AffineTransform trans = AffineTransform.getTranslateInstance(bufferedImage.getHeight(), bufferedImage.getWidth());
        trans.concatenate(AffineTransform.getRotateInstance(Math.toRadians(-90)));
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
        bufferedImage = op.filter(bufferedImage, null).getSubimage(defaultHeight, 0, defaultHeight, defaultWidth);
        return bufferedImage;
    }

    public static void main(String[] args) throws IOException {
        AdjustImg adj = new AdjustImg();
        File folderOrig = new File("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/negativeReduzidaQuant");
        File folderDest = new File("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/negativeReduzidaQuantSizeMenor");
        for (File file : folderOrig.listFiles()) {
            BufferedImage bufferImgOri = ImageIO.read(file);
            BufferedImage bufferImgNew = adj.scaleAndCut(bufferImgOri);
            ImageIO.write(bufferImgNew, "png", new File(String.format("%s%s%s", folderDest, File.separator, file.getName())));
        }
    }

}
