/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Murali
 */
public class BrowserURLLink {
    
  public static void open(String uriStr) {
    URI uri;  
    if (Desktop.isDesktopSupported()) {
      try {
         uri = new URI(uriStr);  
        Desktop.getDesktop().browse(uri);
      } catch (URISyntaxException ex) {
          Logger.getLogger(BrowserURLLink.class.getName()).log(Level.SEVERE, null, ex);
      }catch (IOException e) { /* TODO: error handling */ }
    } else { /* TODO: error handling */ }
  }
    
    
}
