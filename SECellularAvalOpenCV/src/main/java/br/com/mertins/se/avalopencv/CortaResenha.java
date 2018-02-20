package br.com.mertins.se.avalopencv;

import br.com.mertins.se.ca.util.Generic;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

public class CortaResenha {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void individual() {

        String tipo = "S";
        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%sdata/cascade.xml", tipo));
        Mat posImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/rpTeste3.png", tipo));
        String fileCut = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/rpTeste3_cut.png", tipo);

        MatOfRect resenhaDetect = new MatOfRect();
        resenhaDetector.detectMultiScale(posImg, resenhaDetect);
        System.out.printf("Pos Detected %s resenha\n", resenhaDetect.toArray().length);

        Rect rectCrop = null;
        for (Rect rect : resenhaDetect.toArray()) {
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
            Mat imageCrop = new Mat(posImg, rectCrop);
            Imgcodecs.imwrite(fileCut, imageCrop);
            imageCrop.release();
        }
        resenhaDetect.release();
        posImg.release();
    }

    public static void folder() {
        int count = 0;
        int numfiles = 0;
        String tipo = "AC";
        File folder = new File("/home/mertins/temp/ImgABCCC/SEimg3/FRONTVARBACKCOLOR");
        String folderDest = "/home/mertins/temp/ImgABCCC/SEimg3/AUTOMATIC_CUT";
        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%sdata/cascade.xml", tipo));
        for (File file : folder.listFiles()) {
            Mat posImg = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_UNCHANGED);
            MatOfRect resenhaDetect = new MatOfRect();
            resenhaDetector.detectMultiScale(posImg, resenhaDetect);
            if (resenhaDetect.toArray().length == 0) {
                System.out.printf("## Não detectou no arquivo %s\n", file.getAbsolutePath());
            } else {
                Rect rectCrop = null;
                int numRes = 1;
                for (Rect rect : resenhaDetect.toArray()) {
                    rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
                    Mat imageCrop = new Mat(posImg, rectCrop);
                    Imgcodecs.imwrite(String.format("%s%s%s_%d.png", folderDest, File.separator, Generic.removeExtensionFile(file.getName()), numRes++), imageCrop);
                    imageCrop.release();
                }

                System.out.printf("** Detectou no arquivo %s\n", file.getAbsolutePath());
                count += resenhaDetect.toArray().length;
                if (count % 10 == 0) {
                    System.out.printf("Resenhas [%d]  Arquivos avaliados [%d]\n", count, numfiles);
                }
            }
            numfiles++;
            resenhaDetect.release();
            posImg.release();

        }
        System.out.printf("Resenhas detectadas [%d] em arquivos [%d] avaliados\n", count, numfiles);

    }

    public static void main(String[] args) {
//        CortaResenha.individual();
        CortaResenha.folder();
    }
}
