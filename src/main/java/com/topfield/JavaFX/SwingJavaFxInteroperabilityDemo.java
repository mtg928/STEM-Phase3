/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Simple class demonstrating interoperability between Swing and JavaFX. This
 * class is adapted from the example provided in the Javadoc documentation for
 * {@code javafx.embed.swing.JFXPanel}.
 */
public class SwingJavaFxInteroperabilityDemo
{
   private static void initAndShowGUI()
   {
      // This method is invoked on Swing thread
       JFrame frame = new JFrame("JavaFX / Swing Integrated");
       
       JFXPanel fxPanel = new JFXPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 600);
            }
        };
      frame.add(fxPanel);
      frame.setVisible(true);

      Platform.runLater(new Runnable()
      {
         @Override
         public void run()
         {
            initFX(fxPanel);
         }
      });
   }

   private static void initFX(JFXPanel fxPanel)
   {
      // This method is invoked on JavaFX thread
      final Scene scene = TextIntegrationSceneCreator.createTextScene();
      fxPanel.setScene(scene);
   }

   public static void main(String[] arguments)
   {
      SwingUtilities.invokeLater(new Runnable()
      {
         @Override
         public void run()
         {
            initAndShowGUI();
         }
      });
   }   
}
