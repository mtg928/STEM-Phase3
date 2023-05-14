/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

import com.topfield.controller.BusinessException;
import com.topfield.main.InternalFrameDemo;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Murali
 */
public class FileSelection {
    
    public static final int PDF = 0;
    public static final int EXCEL = 1;
    public static final int WORD = 2;
    public static final int IMAGE = 3; 
    
    
    public static List<String> getSelectionPath(){
     List<String> res = new ArrayList<>();
     
      JFileChooser jFile = new JFileChooser(); 
          jFile.setApproveButtonText("Save");
          //jFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          jFile.setCurrentDirectory(new File(System.getProperty("user.home")));
          jFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
          jFile.setAcceptAllFileFilterUsed(false);
          FileNameExtensionFilter extFilter = new FileNameExtensionFilter("JPEG", "jpg", "jpeg");
          jFile.addChoosableFileFilter(extFilter);
          Path pth = null;
          String extension= null;    
          
          int returnVal = jFile.showSaveDialog(InternalFrameDemo.contentPanel);
          
          if (returnVal == JFileChooser.APPROVE_OPTION) {

            extension = jFile.getFileFilter().getDescription().toLowerCase();
            pth = jFile.getSelectedFile().toPath();

            res.add(pth+"");
            res.add(extension);
        
          } 
    
     return res;
    }
    
    public static List<String> getSelectionPath2(int [] ext){
     List<String> res = new ArrayList<>();
     
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")+"/Desktop"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setApproveButtonText("Save");
        
        for (int i : ext) {
            fileChooser.addChoosableFileFilter(getFilterSlections(i));
        }

        fileChooser.setAcceptAllFileFilterUsed(false);
        
        Path pth = null;
        String extension= null;  
 
        int result = fileChooser.showSaveDialog(InternalFrameDemo.contentPanel);
 
        if (result == JFileChooser.APPROVE_OPTION) {
            
            extension = ((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0].toLowerCase();
            pth = fileChooser.getSelectedFile().toPath();
            
            if (checkExists(pth+"."+extension)) {
                BusinessException.showBusinessException("EX0201006", "File already exists. Please re-enter a new filename",JOptionPane.WARNING_MESSAGE);
                 res = getSelectionPath3(fileChooser);
            }else{
            
            res.add(pth+"");
            res.add(extension);
            
            }
        }
    
     return res;
    }
    
    public static List<String> getSelectionPath3( JFileChooser fileChooser){
     List<String> res = new ArrayList<>();

        
        Path pth = null;
        String extension= null;  
 
        int result = fileChooser.showSaveDialog(InternalFrameDemo.contentPanel);
 
        if (result == JFileChooser.APPROVE_OPTION) {
            
            extension = ((FileNameExtensionFilter)fileChooser.getFileFilter()).getExtensions()[0].toLowerCase();
            pth = fileChooser.getSelectedFile().toPath();
            
            if (checkExists(pth+"."+extension)) {
              
                BusinessException.showBusinessException("EX0201006", "File already exist. Please change the name",JOptionPane.WARNING_MESSAGE);
                res = getSelectionPath3(fileChooser);
            }else{
            
            res.add(pth+"");
            res.add(extension);
            
            }
        }
    
     return res;
    }
    
    
    public static void main(String[] args) {
        for (String arg : getSelectionPath2(new int[]{3,2})) {
            System.out.println("1 "+arg);
        }

    }
    
    public static FileNameExtensionFilter  getFilterSlections(int sel){
     FileNameExtensionFilter res=null;
     
        switch (sel) {
            case PDF:
                res = new FileNameExtensionFilter("Portable Document Format (PDF)", "pdf");
                break;
            case EXCEL:
                res = new FileNameExtensionFilter("MS Office Excel","xlsx","docx", "pptx");
                break;
            case WORD:
                res = new FileNameExtensionFilter("MS Office Word", "docx","xlsx", "pptx");
                break;
            case IMAGE:
                res = new FileNameExtensionFilter("Images (PNG)","png","jpg", "gif", "bmp");
                break;
            default:
                break;
        }
     
     return res;
    }
    
    
    public static boolean checkExists(String path){
        File f = new File(path);
        /*if(f.exists() && !f.isDirectory()) { 

        }*/
     return f.exists() ;
    }
    
}
