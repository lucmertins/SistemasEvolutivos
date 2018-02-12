package br.com.mertins.se.ca.util;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR_PRE;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import static java.awt.image.BufferedImage.TYPE_BYTE_GRAY;
import static java.awt.image.BufferedImage.TYPE_BYTE_INDEXED;
import static java.awt.image.BufferedImage.TYPE_CUSTOM;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE;
import static java.awt.image.BufferedImage.TYPE_INT_BGR;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.awt.image.BufferedImage.TYPE_USHORT_555_RGB;
import static java.awt.image.BufferedImage.TYPE_USHORT_565_RGB;
import static java.awt.image.BufferedImage.TYPE_USHORT_GRAY;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author mertins
 */
public class AuxiliarViewPixel {

    public static void main(String[] args) throws IOException {
        File fileImg1 = new File("/home/mertins/temp/SEimg1/origem/FRONT_59b97ed03dbc0e5149768bac.png");
        BufferedImage bufferedImage1 = ImageIO.read(fileImg1);
        int width1 = bufferedImage1.getWidth();
        int height1 = bufferedImage1.getHeight();
        int type1 = bufferedImage1.getType();
        int[][] imageT01 = new int[height1][width1];
        for (int row = 0; row < height1; row++) {
            for (int col = 0; col < width1; col++) {
                imageT01[row][col] = bufferedImage1.getRGB(col, row);
            }
        }

        File fileImg2 = new File("/home/mertins/temp/SEimg1/origem/FRONT_59b98f8b3dbc0e5149b98ffc.png");
        BufferedImage bufferedImage2 = ImageIO.read(fileImg2);
        int width2 = bufferedImage2.getWidth();
        int height2 = bufferedImage2.getHeight();
        int type2 = bufferedImage2.getType();
        int[][] imageT02 = new int[height2][width2];
        for (int row = 0; row < height2; row++) {
            for (int col = 0; col < width2; col++) {
                imageT02[row][col] = bufferedImage2.getRGB(col, row);
            }
        }

        System.out.printf("Tipo [%s]     [%s]\n", esclareceType(type1), esclareceType(type2));

    }

    public static String esclareceType(int type) {
        switch (type) {
            case TYPE_3BYTE_BGR:
                return "TYPE_3BYTE_BGR";
            case TYPE_4BYTE_ABGR:
                return "TYPE_4BYTE_ABGR";
            case TYPE_4BYTE_ABGR_PRE:
                return "YPE_4BYTE_ABGR_PRE";
            case TYPE_BYTE_BINARY:
                return "TYPE_BYTE_BINARY";
            case TYPE_BYTE_GRAY:
                return "TYPE_BYTE_GRAY";
            case TYPE_BYTE_INDEXED:
                return "TYPE_BYTE_INDEXED";
            case TYPE_CUSTOM:
                return "TYPE_CUSTOM";
            case TYPE_INT_ARGB:
                return "TYPE_INT_ARGB";
            case TYPE_INT_ARGB_PRE:
                return "TYPE_INT_ARGB_PRE";
            case TYPE_INT_BGR:
                return "TYPE_INT_BGR";
            case TYPE_INT_RGB:
                return "TYPE_INT_RGB";
            case TYPE_USHORT_555_RGB:
                return "TYPE_USHORT_555_RGB";
            case TYPE_USHORT_565_RGB:
                return "TYPE_USHORT_565_RGB";
            case TYPE_USHORT_GRAY:
                return "TYPE_USHORT_GRAY";
            default:
                return "Sem tipo";
        }
    }
}
