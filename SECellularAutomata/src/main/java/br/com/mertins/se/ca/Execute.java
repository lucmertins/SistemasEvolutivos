package br.com.mertins.se.ca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mertins
 */
public class Execute {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file;
        if (args.length == 1) {
            file = new File(args[0]);
        } else {
            file = new File("fileLoad.config");
        }
        if (file.exists()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            String action = ((String) properties.get("action")).trim().toLowerCase();
            switch (action) {
                case "backgroundelimination":
                    BackgroundElimination back = new BackgroundElimination();
                    back.init(properties);
                    back.process();
                    break;
                case "edgedetection":
                    EdgeDetection edgeDetec = new EdgeDetection();
                    edgeDetec.init(properties);
                    edgeDetec.process();
                    break;
                case "executewaterfall":
                    Waterfall.process(properties);
                    break;
                default:
                    msgOut(action, file);
                    break;
            }
        } else {
            msgOut("", file);
        }
    }
    
    private static void msgOut(String action, File file) {
        System.out.printf("Ação [%s] não foi realizada. Ação ou Arquivo [%s] não encontrado\n", action, file == null ? "" : file.getAbsoluteFile());
        System.out.println("Ações possíveis: BackgroundElimination EdgeDetection ExecuteWaterfall");
        
    }
}
