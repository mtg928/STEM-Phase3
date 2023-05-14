/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.SpfhdrDao;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.datamodel.Item;
import static com.topfield.main.InternalFrameDemo.contentPanel;
import com.topfield.modal.Spfhdr;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.checkerframework.common.value.qual.ArrayLen;

/**
 *
 * @author Murali
 */
public class FaultDataPopup extends JPanel {
    
    private JPanel addPopup = new JPanel(); 
    private FTABasicPopup fTABasicPopup;
    private FTAGatePopup fTAGatePopup;
    //private InsertCCF insertCCF;
    private JComboBox parentNode = new JComboBox();
    private JComboBox nodeType = new JComboBox(new String[]{"Basic","Event"/*,"CCF"*/});

    public FaultDataPopup(Spfhdr spfhdr,List<Item> parentNodes,List<Integer> addedList,boolean editable) {
        
        setLayout( new BorderLayout() );
        
        fTABasicPopup = new FTABasicPopup(spfhdr,addedList,editable,false);
        fTAGatePopup = new FTAGatePopup();
        //insertCCF = new InsertCCF(spfhdr);
        
        //parentNode.addItem(new Item(-1, "No Parent"));
        for (Item parentNode1 : parentNodes) {
            parentNode.addItem(parentNode1);
        }
        
       parentNode.setEnabled(editable);
       nodeType.setEnabled(editable);

        
        JPanel panel = new JPanel();  //new GridLayout(2, 2)
        //panel.setPreferredSize(new Dimension(600, 50));
        //panel.setLayout(null); 
        
        JLabel Parentl= new JLabel("Parent Node: ");
        Parentl.setSize(300, 30); 
        Parentl.setLocation(300, 30); 
        panel.add(Parentl);
        
        parentNode.setSize(300, 30); 
        parentNode.setLocation(600, 30); 
        panel.add(parentNode);
        
        
        JLabel nodeTypel= new JLabel("Event Type");
        nodeTypel.setSize(300, 30); 
        nodeTypel.setLocation(300, 80);
        panel.add(nodeTypel);
        panel.add(nodeType);
        
        addPopup.setLayout(new BorderLayout() );
        addPopup.add(fTABasicPopup);
        
        
         nodeType.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                System.out.println(e.getItem() + " " + e.getStateChange() );
                
                addPopup.removeAll();
                if (e.getItem().equals("Basic")) {
                    addPopup.add(fTABasicPopup);
                }else  if (e.getItem().equals("Event")){
                    addPopup.add(fTAGatePopup);
                }/*else  if (e.getItem().equals("CCF")){
                    addPopup.add(insertCCF);
                }*/
                
                addPopup.revalidate();
                addPopup.repaint();
                
            }
        });
        
        add(panel, BorderLayout.NORTH);
        add(addPopup, BorderLayout.CENTER);
    }
    
    
    public Item getParentNode(){
    
    return (Item)parentNode.getSelectedItem();
    }
    
    public void setParentNode(Item selParent){
    
    parentNode.setSelectedItem(selParent);
    }
    
    public String getNodeType(){
    
    return (String)nodeType.getSelectedItem();
    }
    
    public void setNodeType(String nodeTypes){
    
    nodeType.setSelectedItem(nodeTypes);
    }
    
    
    public JPanel getAddPopup(){
    
    return (JPanel)addPopup.getComponent(0);
    }
    
    
     public static void main(String[] args) {
        
         SpfhdrDao spfhdrDao = new SpfhdrDaoImpl(); 
         List<Integer> added = new ArrayList<>();
         added.add(26);
                
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        List<Item> parentNodes = new ArrayList<Item>();
        parentNodes.add(new Item(1, "GATE1"));
        parentNodes.add(new Item(2, "GATE2"));
        
        FaultDataPopup test = new FaultDataPopup(spfhdrDao.findById(10),parentNodes,added,true);
        test.setNodeType("Event");
        frame.setContentPane(test);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
}
