/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.topfield.reports.CCF_Report_Pro;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Murali
 */
public class ImageProcessor {
    
    
        /**
 * scale image
 * 
 * @param sbi image to scale
 * @param imageType type of image
 * @param dWidth width of destination image
 * @param dHeight height of destination image
 * @param fWidth x-factor for transformation / scaling
 * @param fHeight y-factor for transformation / scaling
 * @return scaled image
 */
public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
    BufferedImage dbi = null;
    if(sbi != null) {
        dbi = new BufferedImage(dWidth, dHeight, imageType);
        Graphics2D g = dbi.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
        g.drawRenderedImage(sbi, at);
    }
    return dbi;
}
    
 public static BufferedImage scaleImage(BufferedImage image, int imageType,
        int newWidth, int newHeight) {
        // Make sure the aspect ratio is maintained, so the image is not distorted
        double thumbRatio = (double) newWidth / (double) newHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double aspectRatio = (double) imageWidth / (double) imageHeight;

        if (thumbRatio < aspectRatio) {
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newWidth = (int) (newHeight * aspectRatio);
        }

        // Draw the scaled image
        BufferedImage newImage = new BufferedImage(newWidth, newHeight,
                imageType);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

        return newImage;
    }   
    
  public static InputStream ImageIconToInputStream(Image image){
   
     BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),    BufferedImage.TYPE_INT_RGB);
     //bufferedImage is the RenderedImage to be written
     Graphics2D g2 = bufferedImage.createGraphics();
     g2.drawImage(image, null, null);

     ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try { 
            ImageIO.write(bufferedImage, "jpg", outStream);
        } catch (IOException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        }
     InputStream is = new ByteArrayInputStream(outStream.toByteArray());
    return is;
   }
}
