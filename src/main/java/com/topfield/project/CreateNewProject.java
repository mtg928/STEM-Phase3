/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.project.CalculaterSettings;
import com.topfield.utilities.FileSelector;
import com.topfield.user.UserInfo;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.TclProjects;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class CreateNewProject {
    
    private CreateProjectPanel createProjectPanel = new CreateProjectPanel();
    private ProjectDefinition projectDefinition = new ProjectDefinition(false);
    private CalculaterSettings calculaterSettings = new CalculaterSettings();
    private TclProjectsDao tclProDao = new TclProjectsDaoImpl();
    
    
    
    public  void displayProjectSettings() {
         
         Object[] options1 = { "Next", "Cancel"};

        
        int result = JOptionPane.showOptionDialog(null, createProjectPanel, "Create New Project",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        
          if (result == JOptionPane.OK_OPTION) {

            List<String> lines = Arrays.asList(createProjectPanel.getProjetName(),"", createProjectPanel.getProjetDisc());
            Path file = Paths.get(createProjectPanel.getFilePath()+"/"+createProjectPanel.getProjetName()+".stems");
            
             try {
                 Files.write(file, lines, StandardCharsets.UTF_8);
                 displayProjectDefinition();
             } catch (IOException ex) {
                 
                  createProjectPanel.setErrorMass("File created failed \nPlase check your Project Name and Destination \n"+ex.getMessage());
                 displayProjectSettings();
                 
                
                // JOptionPane.showMessageDialog(null, "File created failed \nPlase check your Project Name and Destination \n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                 Logger.getLogger(CreateNewProject.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        } else {
            System.out.println("Cancelled");
        }
          
           InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    public  void displayProjectDefinition() {
         Object[] options1 = { "Pervious", "Next","Cancel"};

         
        int result = JOptionPane.showOptionDialog(null, projectDefinition, "Project Scope",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        
            if (result == JOptionPane.YES_OPTION) {
                 createProjectPanel.setErrorMass("");
                 displayProjectSettings();
            } else if (result == JOptionPane.NO_OPTION) { 
                
              displayCalculaterSettings();
            } else {
                System.out.println("Cancelled");
            }
    }
    
    public  void displayCalculaterSettings() {
         Object[] options1 = { "Pervious", "Finish","Cancel"};

         
        calculaterSettings.setTitle(createProjectPanel.getProjetType());
        calculaterSettings.setToolSelection(createProjectPanel.getProjetType());
         
        int result = JOptionPane.showOptionDialog(null, calculaterSettings, "Calculater Settings",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        
            if (result == JOptionPane.YES_OPTION) {
                displayProjectDefinition();
            } else if (result == JOptionPane.NO_OPTION) { 
                TclProjects pro = new TclProjects();
                //pro.setProId(2);
                pro.setProName(createProjectPanel.getProjetName());
                pro.setProType(createProjectPanel.getProjetType());
                pro.setAbbreviation(createProjectPanel.getAbbreviation());
                pro.setProDescription(createProjectPanel.getProjetDisc());
                pro.setFilePath(createProjectPanel.getFilePath());
                pro.setCreatedBy(UserInfo.getInstance().getuser());
                pro.setCreatedDate(UserInfo.getInstance().getDate());
                pro.setCalculationScope(calculaterSettings.getSelectedString());
                pro.setSystemScope(projectDefinition.getSelections());
                
               // UserInfo.getInstance().setAllUserInfoData(UserInfo.getInstance().getuser().getUsername());
                
                UserInfo.getInstance().setProjectNo(tclProDao.saveProjects(pro));
                
                InternalFrameDemo m =   (InternalFrameDemo) InternalFrameDemo.mainFrame;
                m.setUserMenuTag();
                m.refreshContantPanel();

            } else {
                System.out.println("Cancelled");
            }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                CreateNewProject c = new CreateNewProject();
                c.displayProjectSettings();
            }
        });
    }
    
    
 


}

