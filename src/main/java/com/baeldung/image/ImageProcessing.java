package com.baeldung.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageProcessing {

    public void drawRects(HashMap<String, ArrayList<Integer>> resultLines , String clickWithText) throws IOException {
        // draw bbox of the text
        BufferedImage img = ImageIO.read(new File("src/main/resources/images/findeksLoginPage.png"));

        // Obtain the Graphics2D context associated with the BufferedImage.
        Graphics2D g = img.createGraphics();

        for(Map.Entry<String, ArrayList<Integer>> entry : resultLines.entrySet()){

            ArrayList<Integer> bbox = entry.getValue();

            if(entry.getKey().contains(clickWithText)){
                // Draw on the BufferedImage via the graphics context.
                g.setColor(Color.GREEN);
                g.setStroke(new BasicStroke(3));


                g.drawRect(bbox.get(0), bbox.get(1), bbox.get(2)-bbox.get(0), bbox.get(3)-bbox.get(1));

                // evaluate where we should click
                int clickX = (bbox.get(2)+bbox.get(0)) /2 ;
                int clickY = (bbox.get(3)+bbox.get(1)) /2 ;
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(10));

                g.drawOval(clickX,clickY,20,20);
            }
            else {
                // Draw on the BufferedImage via the graphics context.
                g.setColor(Color.MAGENTA);
                g.setStroke(new BasicStroke(3));


                g.drawRect(bbox.get(0), bbox.get(1), bbox.get(2)-bbox.get(0), bbox.get(3)-bbox.get(1));
            }
        }



        g.dispose();

        ImageIO.write(img, "png", new File("src/main/resources/images/findeksResult.png"));
    }
}
