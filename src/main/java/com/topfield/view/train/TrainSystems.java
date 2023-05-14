/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductGroup;
import com.topfield.user.UserInfo;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class TrainSystems extends JPanel {
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    
    private JTextArea jTextArea;
    private JButton button1;
    private JButton button2;
    
    public TrainSystems(){
      intialSettings(mpgDao.getAllMPG());
    }
    
    
    public TrainSystems(String user){
      intialSettings(mpgDao.getAllMPGBYUser(user));
 
    }
    
    
    
    public void intialSettings(List<MainProductGroup> mpg){
    
      setLayout (new BorderLayout());

       JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //topPanel.setBackground(Color.lightGray);
        topPanel.add(new JLabel("<html><h1>Main Product Group</h1></html>"));
        
        int mpgcount=1; 
       mpgcount = (mpg.size()<=5) ?  1 : mpg.size(); 

        
       JPanel middlePanel = new JPanel();
       middlePanel.setLayout(new GridLayout(5,mpgcount/5));
       middlePanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
       
       if(mpg != null & mpg.size() > 0) {
			for(MainProductGroup mpgObj : mpg) {
                            //System.out.println("Hi"+mpgObj.toString());
		            middlePanel.add(new JScrollPane( new TrainMainComponents(mpgObj,"")));
			}
       }
    
        add(topPanel,BorderLayout.PAGE_START);
        add(middlePanel,BorderLayout.CENTER);
        
        
              /*
        
       
       setLayout(new GridLayout(5,mpgcount/5));
       setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
       //setBackground(Color.GRAY);

   //     add(new TrainMainComponents("Hai",""));
        
       
		if(mpg != null & mpg.size() > 0) {
			for(MainProductGroup mpgObj : mpg) {
                            //System.out.println("Hi"+mpgObj.toString());
		            add(new TrainMainComponents(mpgObj,""));
			}
		}
 
        
       */ 
    
    }
    
    public class TrainMainComponents extends javax.swing.JButton implements MouseListener {
        String subComList=""; 

    public TrainMainComponents(MainProductGroup mpg,String icon) {
        
   
      
        setText(mpg.getMpgDescription()+" ("+UserInfo.getInstance().getProAbbreviations()+mpg.getMpgRef()+")");
        setBackground(Color.white);
        addMouseListener(this);
        
        addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              JPopupMenu popup = new JPopupMenu("Popup");
              subComList="";
              
            // display/center the jdialog when the button is pressed
            /*JDialog d = new JDialog(MainFrame.mainFrame, "You click at "+title, true);
            d.setSize(300, 150);

            d.setLocationRelativeTo(MainFrame.mainFrame);
            d.setVisible(true);*/
            
            
            //spgDao.getAllSPG()
            
            popup.setAlignmentY(BOTTOM);
            popup.setBackground(Color.white);
            popup.add(new JLabel("<html><b>Sub Components</b></html>", CENTER));
              
              for (SubProductGroup spg :mpg.getSubProductGroupCollection() ) {
                  
                  
                  System.out.println("Hi "+spg.getSpgDescription() );
                  
                  subComList = subComList+"\n* "+spg.getSpgDescription();
                  
                  JMenuItem subGroub = new JMenuItem(new AbstractAction(spg.getSpgDescription()) {
                        public void actionPerformed(ActionEvent e) {
                            // Button pressed logic goes here
                           // JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame,"You click at "+spg.getSpgId() +" "+spg.getSpgDescription());
                            
                            
                                    InternalFrameDemo.contentPanel.removeAll();
                                    //MainFrame.contentPanel.add(new JScrollPane(new SubComponentsFunctions(spg)));
                                    InternalFrameDemo.contentPanel.add(new SubComponentsFunctions(spg));
                                    InternalFrameDemo.contentPanel.revalidate();
                                    InternalFrameDemo.contentPanel.repaint();

                        }
                    });
                  
                  popup.add(subGroub);
                  
                 /* subGroub.addMouseListener(new MouseAdapter() {
                      @Override
                      public void mouseClicked(MouseEvent e) {
                        //menu.show(e.getComponent(), e.getX(), e.getY());
                        JOptionPane.showMessageDialog(MainFrame.mainFrame,"You click at "+mpg.getMpgDescription()+"\n \n"+subComList); 
                      }
                    });     */   
                  
                  
                  
              }
              
              if( subComList.equals("")){
                 subComList="No Sub Componets found";
                 popup.add(new JMenuItem(subComList));
              }
            
             // JOptionPane.showMessageDialog(MainFrame.mainFrame,"You click at "+mpg.getMpgDescription()+"\n \n"+subComList); 
                   
                    
                    // show on the button?
                    popup.show((Component)e.getSource(), 0, 0);
                    
                    
                    
                                        
                    
                    
          }
          
                  public void mouseExited(MouseEvent e) 
                    {
                        if (! getVisibleRect().contains(e.getPoint()) )
                        {
                           setBackground(Color.yellow);
                        }
                    }
        });
        

        
       
        
       
       
       
    }

    // Expose the getToolTipText event of our JList
    public String getToolTipText(MouseEvent e) {
        return super.getToolTipText();
    }

        @Override
        public void mouseClicked(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.1"); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.2"); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.3"); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBackground(Color.cyan);
        }

        @Override
        public void mouseExited(MouseEvent e) {
             setBackground(Color.white);
        }
        
        
        
        
        
        
        
    }

    
 
   
    
}


/*

 addMouseMotionListener(new MouseAdapter() {
            
            
            /*
            @Override
            public void mouseClicked(MouseEvent e) {
                setBackground(Color.yellow);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.yellow);
            } 
            
            @Override
            public void mouseMoved(MouseEvent e) {

                setBackground(Color.yellow);

            }
            
           /*  public void mouseClicked(MouseEvent e) {
             
                setBackground(Color.green);
                
                 System.out.println("mouseClicked");
             }
            
            
          /*  
            public void mouseExited(MouseEvent e) 
            {
                if (! getVisibleRect().contains(e.getPoint()) )
                {
                    setBackground(Color.yellow);
                }
            }
            
            
            
            
            
            

            
        });*/
        
        
        
        
       /* addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                
                
                setBackground(Color.cyan);
            }
        });*/
       
       
       





