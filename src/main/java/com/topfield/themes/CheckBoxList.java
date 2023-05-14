/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.datamodel.Item;
import com.topfield.utilities.NumberConversion;
import java.awt.*;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;  
  
public class CheckBoxList extends JPanel{ 
    
    
//private ArrayList<Item> checkBoxList;
 
public CheckBoxList(ArrayList<Item> checkBoxList,List<String>  sel){ 
    
     setLayout(new FlowLayout(FlowLayout.LEFT));  
    //setting flow layout of right alignment  
  
    JCheckBox checkBox;
    JButton showDialogButton=new JButton("Click Here");  
    
    
    for (Item item : checkBoxList) {
       checkBox =   new JCheckBox(item.getDescription(), sel.contains(item.getDescription()));  
       checkBox.setName(item.getId()+"");
       add(checkBox) ;
    }

  //  add(showDialogButton);
    
    showDialogButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
               
          for (int object : getSelectedItemsTest()) {
              System.out.println("Ine - "+object);
          }
 
              
      } 
    });
      


}  



public ArrayList<Integer> getSelectedItems(){

 ArrayList<Integer> res = new ArrayList();
 JCheckBox check;
    
  System.out.println("GKN kjdkvfkdf");

    for (int i = 0; i < getComponentCount(); i++) {
        check = (JCheckBox)getComponent(i);
        //System.out.println(getComponent(i).getName());
        if (check.isSelected()) {
            res.add(NumberConversion.NVL(check.getName(), 0));
        }
        
    }


return res;
}

public String getSelectedItemNames(){

 String res="";
 JCheckBox check;
    
  System.out.println("GKN kjdkvfkdf");

    for (int i = 0; i < getComponentCount(); i++) {
        check = (JCheckBox)getComponent(i);
        //System.out.println(getComponent(i).getName());
        if (check.isSelected()) {
            res = res+ check.getText()+"#";
        }
        
    }


return res;
}




public ArrayList<Integer> getSelectedItemsTest(){

 ArrayList<Integer> res = new ArrayList();
 JCheckBox check;
    
  System.out.println("GKN kjdkvfkdf");

    for (int i = 0; i < getComponentCount()-1; i++) {
        check = (JCheckBox)getComponent(i);
        //System.out.println(getComponent(i).getName());
        if (check.isSelected()) {
            res.add(NumberConversion.NVL(check.getName(), 0));
        }
        
    }


return res;
}


public static void main(String[] args) {  
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        
        ArrayList<Item> checkBoxList = new ArrayList<>();
        checkBoxList.add(new Item(1,"Test 1"));
         checkBoxList.add(new Item(2,"Test 2"));
          checkBoxList.add(new Item(3,"Test 3"));
           checkBoxList.add(new Item(4,"Test 4"));
            checkBoxList.add(new Item(5,"Test 5"));
             checkBoxList.add(new Item(6,"Test 6"));
             
              ArrayList<String> contanis = new ArrayList<>();
              contanis.add("Test 4");

        frame.setContentPane(new CheckBoxList(checkBoxList,contanis));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
}  


}  
