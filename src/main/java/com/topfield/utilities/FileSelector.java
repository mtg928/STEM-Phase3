/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.filechooser.FileFilter;


public class FileSelector extends JPanel implements ActionListener {
    
  private static FileSelector instance = null;  
   JButton go;
   JFileChooser chooser;
   String choosertitle;

  private FileSelector() {
    go = new JButton("Do it");
    go.addActionListener(this);
    add(go);
   }
  
  public static FileSelector getInstance() {
		if (instance == null) {
			instance = new FileSelector();
		}
		return instance;
  }

  public void actionPerformed(ActionEvent e) {            
   //ChooseFile(new String[]{"xlsx"});
   ChooseDirectory();
  }
  
  
  public  String ChooseDirectory(){
  
     String directory ="";
     String desktopPath = System.getProperty("user.home") + "/Desktop";         
    chooser = new JFileChooser(); 
    //chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setCurrentDirectory(new java.io.File(desktopPath));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setDialogTitle("Select the save path");
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setApproveButtonText("Save");
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      
      directory =chooser.getSelectedFile().toString();
      //JOptionPane.showMessageDialog(null, "Export successfully "+directory);
      
      
      }
    else {
      System.out.println("No Selection ");
      directory="";
      }
  
    return directory;
  }
  
  public  String ChooseFile(String [] filetypes){
    String filepath ="";  
    String desktopPath = System.getProperty("user.home") + "/Desktop";  
    JFileChooser fileChooser = new JFileChooser(".");
    //FileFilter filter1 = new ExtensionFileFilter("JPG and JPEG", new String[] { "JPG", "JPEG" });
    //FileFilter filter1 = new ExtensionFileFilter("xlsx or mdb or gte", new String[] { "xlsx","mdb","gte" });
    FileFilter filter1 = new ExtensionFileFilter(filetypes[0], filetypes);
    fileChooser.setCurrentDirectory(new java.io.File(desktopPath));
    fileChooser.setFileFilter(filter1);
    int status = fileChooser.showOpenDialog(null);
    if (status == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      System.out.println(selectedFile.getParent());
      System.out.println(selectedFile.getName());
      filepath = selectedFile.toString();
    } else if (status == JFileChooser.CANCEL_OPTION) {
      System.out.println(JFileChooser.CANCEL_OPTION);
    }
    return filepath;
  }  

  
  
    public static String getPathSelected(){
        
        return getInstance().ChooseDirectory();
    }
  
    public static String getPathSelectedFile(String [] filetypes){
        
        return getInstance().ChooseFile(filetypes);
    }
  
  

  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }

  public static void main(String s[]) {
    JFrame frame = new JFrame("");
    FileSelector panel = new FileSelector();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
}
