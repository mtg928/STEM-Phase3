/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Murali
 */
public class RiskMatrixContainer extends JPanel{
    
   JPanel riskMatrixLatest = new JPanel();
   JPanel riskMatrixPrevious = new JPanel();
   

    public RiskMatrixContainer() {
        
      String[] celltextLatest={"RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText"};  
      
      Color[] bgRiskLatest= {new Color(247, 220, 111),new Color(231, 76, 60  ),new Color(231, 76, 60  ),new Color(231, 76, 60  ),new Color(231, 76, 60  ),
                        new Color(29, 131, 72),new Color(247, 220, 111),new Color(231, 76, 60  ),new Color(231, 76, 60  ),new Color(231, 76, 60  ),
                        new Color(29, 131, 72),new Color(29, 131, 72),new Color(247, 220, 111),new Color(231, 76, 60  ),new Color(231, 76, 60  ),
                        new Color(29, 131, 72),new Color(29, 131, 72),new Color(29, 131, 72),new Color(247, 220, 111),new Color(231, 76, 60  ),
                        new Color(29, 131, 72),new Color(29, 131, 72),new Color(29, 131, 72),new Color(29, 131, 72),new Color(247, 220, 111)};  
      
     String[] tabletextLatest={"RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText",
                          "RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText","RiskMatrixText"};  
      
      Color[] tableLatest= {Color.white,Color.white,Color.white,Color.white,Color.white,
                            Color.white,Color.white,Color.white,Color.white,Color.white,
                            Color.white,Color.white,Color.white,Color.white,Color.white,
                            Color.white,Color.white,Color.white,Color.white,Color.white,
                            Color.white,Color.white,Color.white,Color.white,Color.white};
        
        
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      riskMatrixLatest.setLayout(new GridLayout(1, 2));
      riskMatrixLatest.add(new RiskMatrix(celltextLatest,bgRiskLatest,5,5));
      riskMatrixLatest.add(new RiskMatrix(celltextLatest,tableLatest,5,5));
      
      
      riskMatrixPrevious.setLayout(new GridLayout(1, 2));
      riskMatrixPrevious.add(new RiskMatrix(celltextLatest,bgRiskLatest,5,5));
      riskMatrixPrevious.add(new RiskMatrix(celltextLatest,tableLatest,5,5));
      
      
      
     JLabel latestLabel = new JLabel("<html>  Latest Risk Matrix  </html>",SwingConstants.CENTER);
     latestLabel.setOpaque(true);
     latestLabel.setBackground(Color.darkGray);
     latestLabel.setForeground(Color.WHITE);
     
     JLabel latestLabel2 = new JLabel("<html>  Pervious Risk Matrix  </html>",SwingConstants.CENTER);
     latestLabel2.setOpaque(true);
     latestLabel2.setBackground(Color.darkGray);
     latestLabel2.setForeground(Color.WHITE);
      
      add(latestLabel);
      add(riskMatrixLatest); 
      add(latestLabel2);
      add(riskMatrixPrevious);
    }
    
    
    
}
