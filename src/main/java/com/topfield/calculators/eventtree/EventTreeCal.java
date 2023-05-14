/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Murali
 */
public class EventTreeCal extends JPanel{
    
    private int width= 1200;
    private int height= 600;
    
    private JPanel top = new JPanel();
    private JPanel middle = new JPanel();
    //private EventTreeModel middle = new EventTreeModel(width,height);
    private JPanel bottom = new JPanel();
    private EventTreeCalculator parent= null;
    private EventTreeData eventTreeData= null;
    private JLabel total;

    public EventTreeCal(EventTreeCalculator parent)  {
        
        this.parent = parent;
        this.eventTreeData = ((EventTreeData)parent.getComponent(1));
        setLayout(new BorderLayout());
        JButton zoomInTree = new JButton("ZoomIn Tree");
        JButton zoomOutTree = new JButton("ZoomOut Tree");
        //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        JLabel toplabel;
        //top.setPreferredSize(new Dimension(WIDTH, 50));
        //top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.setLayout(new BorderLayout());
        top.setBackground(Color.gray);
        
        JLabel heeding = new JLabel("Event Tree Analysis");
        //heeding.setBackground(Color.red);
        //heeding.setOpaque(true);
        heeding.setFont(new Font ("ARIAL", Font.PLAIN, 20));
        heeding.setHorizontalAlignment(JLabel.CENTER);
        top.add(heeding);
        
        middle.setLayout(new BorderLayout());
        middle.add(new EventTreeModel(parent,width,height));
        middle.setPreferredSize(new Dimension(width,height));
        middle.setBackground(new Color(214, 219, 223));
        //bottom.setPreferredSize(new Dimension(WIDTH, 50));
        
        
        total = new JLabel("Total : - 4.71E-5    ");
        total.setFont(new Font ("ARIAL", Font.PLAIN, 20));
        total.setHorizontalAlignment(JLabel.RIGHT);
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.gray);
        bottom.add(zoomInTree);
        bottom.add(zoomOutTree);
        bottom.add(total);
        
        zoomInTree.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        
            width = width+200;
            height = height+100;
            
                       middle.removeAll();
                       middle.setPreferredSize(new Dimension(width,height));
                        //content.add(new DendrogramPaintTest().getpJpanel());EventTreeCal
                        //content.add(new JScrollPane(new EventTreeModel()));
                        //content.add(new EventTreeModel());
                        middle.add(new EventTreeModel(parent,width,height));
                        middle.revalidate();
                        middle.repaint();
            
            
          }
        });
        
        zoomOutTree.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        
            if(width >1200){
            width = width-200;
            height = height-100;
            
                       middle.removeAll();
                       middle.setPreferredSize(new Dimension(width,height));
                        //content.add(new DendrogramPaintTest().getpJpanel());EventTreeCal
                        //content.add(new JScrollPane(new EventTreeModel()));
                        //content.add(new EventTreeModel());
                        middle.add(new EventTreeModel(parent,width,height));
                        middle.revalidate();
                        middle.repaint();
          }
            
            
        /*  for (Object[] objects : eventTreeData.getAllDatas()) {
          
           for (Object object : objects) {
               
               System.out.print(" - "+object);
           }
           System.out.println("");
           }*/
            
          }
        });
        
        
        
        
        
        add(top, BorderLayout.PAGE_START);
        add(middle, BorderLayout.CENTER);
        add(bottom, BorderLayout.PAGE_END);
        
      /*  ArrayList<Integer> Levellist =middle.getTreeLevels();
        
                for (int i = 0; i < 5; i++) {
            toplabel = new JLabel("Test",SwingConstants.CENTER);
            toplabel.setPreferredSize(new Dimension(100, 30));
            toplabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            //toplabel.setAlignmentX();
            top.add(toplabel);
        }*/
    }
    
    public JLabel getEventTotal(){
    return total;
    }
    
    @Override
    protected void paintComponent(Graphics gr){
        System.out.println("ssddddddddddjfhfhhvjvh");
    }
    
    
    
}
