/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

import com.topfield.settings.JScroll;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author Murali
 */
public class SwingComponent {
    
    
    public static Object getContentFromJscroll(Object panel){
     Object res = null;
     
     res =((JViewport)((JScroll)panel).getComponent(0)).getComponent(0);
     
      return res;
    }
    
    public static Object getContentFromJscroll(Object panel,Class c){
     Object res = null;
     
     res =((JViewport)((JScroll.class).cast(panel)).getComponent(0)).getComponent(0);
     
      return res;
    }
    
    public static int PixelsToPoints(int value, int dpi) {
        return value / dpi * 72;
    }
    
}
