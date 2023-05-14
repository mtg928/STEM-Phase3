/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import com.topfield.datamodel.Item;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class ListExample_UltraExtended implements ActionListener {

    JList itemList, shoppingList, itemRef;
    JButton buttonin, buttonout;
    ArrayList<Item> fileRef = new ArrayList<Item>();

    // The ListModels we will be using in the example.
    DefaultListModel shopping, items, ref;

    public JPanel createContentPane(String[] dataTemp, String[] DataFile) {

        // Create the final Panel.
        JPanel totalGUI = new JPanel();
        totalGUI.setLayout(new BorderLayout());

        // Instantiate the List Models.
        shopping = new DefaultListModel();
        items = new DefaultListModel();
        ref = new DefaultListModel();

        // Using a for loop, we add every item in the String array
        // into the ListModel.
        for (int i = 0; i < DataFile.length; i++) {
            Item setItem = new Item(i, DataFile[i]);
            shopping.addElement(setItem);
            fileRef.add(setItem);
        }

        for (int i = 0; i < dataTemp.length; i++) {
            ref.addElement(new Item(i, dataTemp[i]));
        }

        // We create the buttons to be placed between the lists.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        buttonin = new JButton(">>");
        buttonin.addActionListener(this);
        //buttonPanel.add(buttonin);

        buttonout = new JButton("<<");
        buttonout.addActionListener(this);
        //buttonPanel.add(buttonout);
        
        itemList = new JList(shopping);
        itemList.setVisibleRowCount(25);
        itemList.setFixedCellHeight(20);
        itemList.setFixedCellWidth(200);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane list1 = new JScrollPane(itemList);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Available Elements:"), BorderLayout.NORTH);
        leftPanel.add(list1, BorderLayout.CENTER);
        leftPanel.add(buttonin, BorderLayout.SOUTH);
        
        shoppingList = new JList(items);
        shoppingList.setVisibleRowCount(25);
        shoppingList.setFixedCellHeight(20);
        shoppingList.setFixedCellWidth(200);
        shoppingList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane list2 = new JScrollPane(shoppingList);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Selected Elements:"), BorderLayout.NORTH);
        rightPanel.add(list2, BorderLayout.CENTER);
        rightPanel.add(buttonout, BorderLayout.SOUTH);
        
        itemRef = new JList(ref);
        itemRef.setVisibleRowCount(25);
        itemRef.setFixedCellHeight(20);
        itemRef.setFixedCellWidth(200);
        itemRef.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane list3 = new JScrollPane(itemRef);
        
        JPanel refPanel = new JPanel(new BorderLayout());
        refPanel.add(new JLabel("Template Elements:"), BorderLayout.NORTH);
        refPanel.add(list3, BorderLayout.CENTER);
        //refPanel.add(new JButton("Template Elements"), BorderLayout.SOUTH);

        buttonPanel.add(leftPanel,BorderLayout.LINE_START);
        buttonPanel.add(rightPanel,BorderLayout.CENTER);
        buttonPanel.add(refPanel, BorderLayout.LINE_END);

        totalGUI.add(buttonPanel);
        totalGUI.setOpaque(true);
        return totalGUI;
    }

    // In this method, we create a square JPanel of a colour and set size
    // specified by the arguments.
    private JPanel createSquareJPanel(Color color, int size) {
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(color);
        tempPanel.setMinimumSize(new Dimension(size, size));
        tempPanel.setMaximumSize(new Dimension(size, size));
        tempPanel.setPreferredSize(new Dimension(size, size));
        return tempPanel;
    }

    // valueChanged is the method that deals with a ListSelectionEvent.
    // This simply changes the boxes that are selected to true.
    public void actionPerformed(ActionEvent e) {
        int i = 0;

        // When the 'in' button is pressed,
        // we take the indices and values of the selected items
        // and output them to an array.
        if (e.getSource() == buttonin) {
            int[] fromindex = itemList.getSelectedIndices();
            Object[] from = itemList.getSelectedValues();

            // Then, for each item in the array, we add them to
            // the other list.
            for (i = 0; i < from.length; i++) {
                items.addElement(from[i]);
            }

            // Finally, we remove the items from the first list.
            // We must remove from the bottom, otherwise we try to 
            // remove the wrong objects.
            for (i = (fromindex.length - 1); i >= 0; i--) {
                shopping.remove(fromindex[i]);
            }

        } // If the out button is pressed, we take the indices and values of
        // the selected items and output them to an array.
        else if (e.getSource() == buttonout) {
            Object[] to = shoppingList.getSelectedValues();
            int[] toindex = shoppingList.getSelectedIndices();

            // Then, for each item in the array, we add them to
            // the other list.
            for (i = 0; i < to.length; i++) {
                shopping.addElement(to[i]);
            }

            // Finally, we remove the items from the first list.
            // We must remove from the bottom, otherwise we try to
            // remove the wrong objects.
            for (i = (toindex.length - 1); i >= 0; i--) {
                items.remove(toindex[i]);
            }
        }
       // getSelection();
    }

    public void getSelection() {

        for (int i = 0; i < shoppingList.getModel().getSize(); i++) {
            Item item = (Item) shoppingList.getModel().getElementAt(i);
            System.out.println(i + " - " + item.getId() + " - " + item.getDescription());
        }

    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("[=] JListExample - Adding and Removing [=]");

        String[] org = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode"};
        String[] match = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode"};

        ListExample_UltraExtended demo = new ListExample_UltraExtended();
        frame.setContentPane(demo.createContentPane(org, match));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    public int[] getMatchColumns(){
     int [] res = new int[items.size()];
    
        for (int i = 0; i < items.size(); i++) {
            res[i]=fileRef.indexOf(items.elementAt(i));
        }
    
    return res;
    }
    
    public int[] getMatchColumnsNew(){
     int [] res = new int[items.size()];
    
        for (int i = 0; i < items.size(); i++) {
            res[i]= ((Item)items.get(i)).getId();
        }
    
    return res;
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
