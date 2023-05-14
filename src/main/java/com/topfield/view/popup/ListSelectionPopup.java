/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.datamodel.Item;
import com.topfield.utilities.NumberConversion;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Murali
 */
public class ListSelectionPopup  extends JPanel {

        private JCheckBox all;
        private List<JCheckBox> checkBoxes;
        

        public ListSelectionPopup(List InsertedList,Item... options) {
            
            checkBoxes = new ArrayList<>();
            setLayout(new BorderLayout());
            
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
            all = new JCheckBox("Select All");
            
            all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    for (JCheckBox cb : checkBoxes) {
                        if (cb.isEnabled())
                        cb.setSelected(all.isSelected());
                    }
                    
                    if (all.getText().contentEquals("Select All")) {
                        all.setText("Deselect All");
                    } else {
                        all.setText("Select All");
                    }
                    
                   /* for (Item itemRef : getselectedItems()) {
                        System.out.println(itemRef.getId()+" - "+ itemRef.getDescription());
                    }*/

                }
            });
            header.add(all);
            add(header, BorderLayout.NORTH);

            JPanel content = new ScrollablePane(new GridBagLayout());
            content.setBackground(UIManager.getColor("List.background"));
            
            if (options.length > 0) {

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.weightx = 1;
                
                for (int index = 0; index < options.length - 1; index++) {
                    JCheckBox cb = new JCheckBox((options[index]).getDescription());
                    cb.setOpaque(false);
                    if (InsertedList.contains(options[index].getId())) {
                        cb.setEnabled(false);
                    }
                    //cb.setToolTipText(options[index].getId()+"");
                    //cb.setIconTextGap(options[index].getId());
                    cb.setName(options[index].getId()+"");
                    checkBoxes.add(cb);
                    content.add(cb, gbc);
                }

                JCheckBox cb = new JCheckBox((options[options.length - 1]).getDescription());
                cb.setOpaque(false);
                 if (InsertedList.contains(options[options.length - 1].getId())) {
                        cb.setEnabled(false);
                  }
                cb.setName((options[options.length - 1]).getId()+"");
                checkBoxes.add(cb);
                gbc.weighty = 1;
                content.add(cb, gbc);

            }

            add(new JScrollPane(content));
        }

        public class ScrollablePane extends JPanel implements Scrollable {

            public ScrollablePane(LayoutManager layout) {
                super(layout);
            }

            public ScrollablePane() {
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(300, 300);
            }

            @Override
            public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
                return 32;
            }

            @Override
            public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
                return 32;
            }

            @Override
            public boolean getScrollableTracksViewportWidth() {
                boolean track = false;
                Container parent = getParent();
                if (parent instanceof JViewport) {
                    JViewport vp = (JViewport) parent;
                    track = vp.getWidth() > getPreferredSize().width;
                }
                return track;
            }

            @Override
            public boolean getScrollableTracksViewportHeight() {
                boolean track = false;
                Container parent = getParent();
                if (parent instanceof JViewport) {
                    JViewport vp = (JViewport) parent;
                    track = vp.getHeight() > getPreferredSize().height;
                }
                return track;
            }

        }
        
    public List<Item> getselectedItems(){
      List<Item> res = new ArrayList();
      
 
        for (JCheckBox checkBoxe : checkBoxes) {
            if (checkBoxe.isSelected()) {
                //System.out.println(checkBoxe.getText()+" - "+checkBoxe.getName());  
                res.add(new Item(NumberConversion.NVL(checkBoxe.getName(), 0), checkBoxe.getText()));
            }
        }
      
    
      return res;
    }
    
    public void setDisableInserted(List InsertedList){
        
        for (Object object : InsertedList) {
            System.out.println("object - "+object);
        }
        
        for (JCheckBox checkBoxe : checkBoxes) {
             System.out.println("checkBoxe - "+checkBoxe.getName());
        }
    
        for (JCheckBox checkBoxe : checkBoxes) {
            if (InsertedList.contains(checkBoxe.getName())) {
                  checkBoxe.setEnabled(false);
                  System.out.println(" checkBoxe false");
            }else{
                  checkBoxe.setEnabled(true);
            }
        }

    }
        
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

               // List<Item> inputItems = new ArrayList<Item>();
               Item [] inputItems = new Item[10];
                
                for (int i = 0; i < inputItems.length; i++) {
                    inputItems[i] = new Item(i, "Item "+i);
                }
                
                
                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setLayout(new GridLayout(0, 2));
                frame.add(new ListSelectionPopup(new ArrayList<Integer>(),inputItems));
                //frame.add(new ListSelectionPopup(new String[]{"Learn Archery", "Float in the dead sea", "Swing with a whale shark", "Sail the greek islands", "Go skydiving", "Dance in the rain", "Cycle through the Netherlands"}));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    }