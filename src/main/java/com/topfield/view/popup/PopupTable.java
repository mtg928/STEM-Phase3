/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.settings.JScroll;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */
public class PopupTable extends JPanel{
    
    private  JTable table =new JTable();
    private  String headers[] = { "APP Name", "Module Id", "Module Name", "CMM ID","CMM Description" };
    
    public PopupTable() {
        
       setIntials();
        

    }
    
    public PopupTable(String[][] data) {
        
        // Adding table to content pane
        setIntials();
        setData(data);
        
        
    }
    
   public void setIntials(){
     setLayout(new BorderLayout());
       //table.setLayout(new BorderLayout());
       setPreferredSize(new Dimension(800,200));
       JScroll scroll =new JScroll(table);
       add(scroll);
     //add(table);
    
    }
    
    public void setData(String[][] data){
            // Creating JTable with table data and headers
        table.setModel(new DefaultTableModel(data, headers));
    
    }

     public static void main(String[] args) {
        
                
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String data[][] = { { "Chandra", "Hyderabad", "4568569568", "true" , "true"},
                { "Srikanth", "Vijayawada", "8595652541", "true", "true" },
                { "Rajesh", "Banglore", "8585656545", "false", "true" },
                { "Charan", "Mumbai", "9858654852", "true", "true" },
                { "Kumar", "Pune", "4568569568", "2", "true" },
                { "Venu", "Chennai", "8451265923", "2" , "true"},
                { "Gopal", "Vizag", "7845956585", "2", "true" } };
 
        PopupTable poupUp =  new PopupTable();
        poupUp.setData(data);

        frame.setContentPane(poupUp);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
