/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.topfield.info.SingleNote;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Murali
 */
public class ImageConversion {

    
    public static ImageIcon SetImageSize(URL url) {
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);

        return newImc;
    }
    
    public static ImageIcon SetImageSize(URL url,int width,int height) {
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);

        return newImc;
    }
    
    public static ImageIcon setImageSizeExternal(String url,int width,int height) {
        
        ImageIcon newImc = null ;
         try {
              URL urlLink = new URL(url);   
              Image image = ImageIO.read(urlLink);
              Image newImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
               newImc = new ImageIcon(newImg);
              
            } catch (MalformedURLException ex) {
                Logger.getLogger(SingleNote.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SingleNote.class.getName()).log(Level.SEVERE, null, ex);
            }


        return newImc;
    }
    
    

    
    
}
