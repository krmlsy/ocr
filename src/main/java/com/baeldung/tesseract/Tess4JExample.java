package com.baeldung.tesseract;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.baeldung.html.HtmlParser;
import com.baeldung.image.ImageProcessing;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class Tess4JExample {

    public static void main(String[] args) throws IOException {
        String result = null;
        try {
            File image = new File("src/main/resources/images/findeksLoginPage.png");
            Tesseract tesseract = new Tesseract();
            tesseract.setLanguage("tur");
            //tesseract.setPageSegMode(1);
            //tesseract.setOcrEngineMode(1);
            tesseract.setHocr(true);
            tesseract.setDatapath("src/main/resources/tessdata");
            //result = tesseract.doOCR(image, new Rectangle(1200, 200));
            result = tesseract.doOCR(image);

        } catch (TesseractException e) {
            e.printStackTrace();
        }


        System.out.println(result);
/*
        // save results
        File f = new File("source.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(result);
        bw.close();
*/
        HashMap<String, ArrayList<Integer>> resultLines = new HtmlParser().getTextAndCoordinates(result);

        String clickWithText = "Geri";

        new ImageProcessing().drawRects(resultLines, clickWithText);

    }

}
