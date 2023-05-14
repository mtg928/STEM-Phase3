/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import com.topfield.datamodel.Item;
import com.topfield.utilities.ReportingListTransferHandler;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class ImportColumnSelection extends JPanel{
    ReportingListTransferHandler arrayListHandler =  new ReportingListTransferHandler();

    public ImportColumnSelection(String[] orgList,String[] matchList) {
        
        setLayout(new GridLayout(1,0));
        add(getListComponent("left",orgList));
        add(getListComponent("left",new String[]{}));
        add(getListComponent("right",matchList));
        
        /* for (int i = 0; i < orgList.length; i++) {
          modelTemp.addElement(new Item(i, orgList[i]));
         }*/
        
    }
    
    

 
    private JScrollPane getListComponent(String s,String[] listData) {
        DefaultListModel model = new DefaultListModel();
        
        for(int j = 0; j < listData.length; j++)
            model.addElement(new Item(j, listData[j]));
        
        JList list = new JList(model);
        list.setName(s);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setTransferHandler(arrayListHandler);
        list.setDragEnabled(true);
        return new JScrollPane(list);
    }
     

 
    public static void main(String[] args) {
        
       String[] org = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"};
       String[] match = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"};
        
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ImportColumnSelection(org,match));
        f.setSize(700,200);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
