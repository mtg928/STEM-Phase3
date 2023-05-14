/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.settings;

import com.topfield.main.InternalFrameDemo;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Murali
 */
public class FrameSettings {
    
    private static Color frameColor = new Color(245,245,245);
    private static Color fontColor = Color.BLACK;
    private static Color buttonColor = new Color(63, 130, 190);
    private static Color buttonSelColor = new Color(63, 130, 190);
    private static String imagePath = "/coachspecs/images/gray/";
    private static String imageSelPath = "/coachspecs/images/blue/";
    private static String arrowColor = "strokeColor=black;";


    
    /**
     * @return the frameColor
     */
    public static Color getFrameColor() {
        return frameColor;
    }

    /**
     * @param aFrameColor the frameColor to set
     */
    public static void setFrameColor(Color aFrameColor) {
        frameColor = aFrameColor;
    }

    /**
     * @return the fontColor
     */
    public static Color getFontColor() {
        return fontColor;
    }

    /**
     * @param aFontColor the fontColor to set
     */
    public static void setFontColor(Color aFontColor) {
        fontColor = aFontColor;
    }

    /**
     * @return the buttonColor
     */
    public static Color getButtonColor() {
        return buttonColor;
    }

    /**
     * @param aButtonColor the buttonColor to set
     */
    public static void setButtonColor(Color aButtonColor) {
        buttonColor = aButtonColor;
    }
    
    public static Color getButtonSelectionColor() {
        
        
        return new Color(buttonColor.getRed()-70, buttonColor.getGreen()-70, buttonColor.getBlue()-70);
    }

    /**
     * @return the imagePath
     */
    public static String getImagePath() {
        return imagePath;
    }

    /**
     * @param aImagePath the imagePath to set
     */
    public static void setImagePath(String aImagePath) {
        imagePath = aImagePath;
    }
    

    /**
     * @return the imageSelPath
     */
    public static String getImageSelPath() {
        return imageSelPath;
    }

    /**
     * @param aImageSelPath the imageSelPath to set
     */
    public static void setImageSelPath(String aImageSelPath) {
        imageSelPath = aImageSelPath;
    }
    
    public static void setDefultTheme() {
        setFrameColor(new Color(247, 247, 247));
        setFontColor(Color.BLACK);
        setImagePath("/coachspecs/images/gray/");
        setArrowColor("strokeColor=black;");
        //InternalFrameDemo.mainFrame.refresh();

    }
    
    public static void setBlackTheme() {
        setFrameColor(Color.BLACK);
        setFontColor(Color.WHITE);
        setImagePath("/coachspecs/images/black/");
        setArrowColor("strokeColor=white;");
        //InternalFrameDemo.mainFrame.refresh();

    }
    
    public static void setBlueSelection() {
        setButtonColor(new Color(63, 130, 190));
        setButtonSelColor(new Color(133, 193, 233));
        setImageSelPath("/coachspecs/images/blue/");
        //InternalFrameDemo.mainFrame.refresh();

    }
    
    public static void setGreenSelection() {
        setButtonColor(new Color(79, 139, 104));
        setButtonSelColor(new Color(171, 235, 198));
        setImageSelPath("/coachspecs/images/green/");
        //InternalFrameDemo.mainFrame.refresh();

    }

    /**
     * @return the buttonSelColor
     */
    public static Color getButtonSelColor() {
        return buttonSelColor;
    }

    /**
     * @param aButtonSelColor the buttonSelColor to set
     */
    public static void setButtonSelColor(Color aButtonSelColor) {
        buttonSelColor = aButtonSelColor;
    }
    
    public static String getArrowColor() {
        return arrowColor;
    }

    public static void setArrowColor(String arrowColor) {
        FrameSettings.arrowColor = arrowColor;
    }
    
}
