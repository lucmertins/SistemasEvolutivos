package br.com.mertins.se.avalopencv;

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

    public static void main(String[] args) {

        String tipo = "AC";
        CascadeClassifier resenhaDetector = new CascadeClassifier(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%sdata/cascade.xml", tipo));
        Mat posImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/rpTeste.png", tipo));
        Mat negImg = Imgcodecs.imread(String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/docTeste.png", tipo));
        String filename1 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/detectRpTeste.png", tipo);
        String filename2 = String.format("/home/mertins/Documentos/UFPel/Dr/SistemasEvolutivos/OpenCV/TrainingResenha/%stestes/detectDocTeste.png", tipo);

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

    }
}
