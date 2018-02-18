package br.com.mertins.se.avalopencv;

import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class AvalResenhas {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void individual() {

        String tipo = "AC";
        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%sdata/cascade.xml", tipo));
//        Mat posImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/rpTeste3.png", tipo));

        Mat posImg = Imgcodecs.imread("/home/mertins/temp/ImgABCCC/SEimg3/FRONTVARBACKCOLOR/FRONT_59b990cc3dbc0e5149be94ac_waterfall.png");
        Mat negImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/docTeste.png", tipo));
        String filename1 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/detectRpTeste3.png", tipo);
        String filename2 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/detectDocTeste.png", tipo);
//        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/HorseTest/horse.xml"));
//        Mat posImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/HorseTest/rpTeste.png"));
//        Mat negImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/HorseTest/docTeste.png"));
//        String filename1 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/HorseTest/detectRpTeste.png");
//        String filename2 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/HorseTest/detectDocTeste.png");

        MatOfRect resenhaDetect = new MatOfRect();
        resenhaDetector.detectMultiScale(posImg, resenhaDetect);
        System.out.printf("Pos Detected %s resenha\n", resenhaDetect.toArray().length);

        for (Rect rect : resenhaDetect.toArray()) {
            Imgproc.rectangle(posImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 0));
        }

        System.out.printf("Writing %s\n", filename1);
        Imgcodecs.imwrite(filename1, posImg);

        resenhaDetect = new MatOfRect();
        resenhaDetector.detectMultiScale(negImg, resenhaDetect);
        System.out.printf("Neg Detected %s resenha\n", resenhaDetect.toArray().length);

        for (Rect rect : resenhaDetect.toArray()) {
            Imgproc.rectangle(negImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 0));
        }
        System.out.printf("Writing %s\n", filename2);
        Imgcodecs.imwrite(filename2, negImg);
        resenhaDetect.release();
        posImg.release();
    }

    public static void folder() {
        int count = 0;
        int numfiles = 0;
        String tipo = "AC";
        File folder = new File("/home/mertins/temp/ImgABCCC/SEimg4");
//        File folder = new File("/home/mertins/temp/ImgABCCC/imgs3/FRONT");
        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%sdata/cascade.xml", tipo));
        for (File file : folder.listFiles()) {
            Mat posImg = Imgcodecs.imread(file.getAbsolutePath());
            MatOfRect resenhaDetect = new MatOfRect();
            resenhaDetector.detectMultiScale(posImg, resenhaDetect);
            if (resenhaDetect.toArray().length == 0) {
                System.out.printf("## Não detectou no arquivo %s\n", file.getAbsolutePath());
            } else {
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
        AvalResenhas.folder();

//       AvalResenhas.individual();
    }
}
