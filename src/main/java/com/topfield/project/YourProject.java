/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.controller.BusinessException;
import com.topfield.user.UserInfo;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.TclProjects;
import com.topfield.themes.ButtonColumn;
import com.topfield.user.UserSettingsData;
import com.topfield.view.panel.LeftPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Murali
 */
public class YourProject extends JPanel{
    
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private JTable projects;
    private List<TclProjects> tclProjects;
    
    public YourProject(boolean sample) {
        
        setLayout(new BorderLayout());
        
        JLabel label= new JLabel();
        if (sample) {
             label.setText("Sample Projects");
             tclProjects = proDao.getProByUser("sample");
        } else {
             label.setText("Your Projects");
             tclProjects = proDao.getProByUser(UserInfo.getInstance().getuser().getUsername());
        }
       
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        
        String[] columnNames = {"Project Id","Project Name", "Project Type", "Project Description","Button" }; 
        
        String[][] data = new String[tclProjects.size()][5];
        
        for (int i = 0; i < tclProjects.size(); i++) {
            data[i][0] =tclProjects.get(i).getProId()+"";            
            data[i][1] =tclProjects.get(i).getProName();
            data[i][2] =tclProjects.get(i).getProType();
            data[i][3] =tclProjects.get(i).getProDescription();
            data[i][4] ="Switch  to this Project";
        }


  
        // Column Names 
        
        
        projects = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    
                
                int selPro = Integer.parseInt(data[Integer.parseInt(e.getActionCommand())][0]);
                 UserInfo.getInstance().setProjectNo(selPro);
                 JOptionPane.showMessageDialog(null, "Switch  to your project "+data[Integer.parseInt(e.getActionCommand())][0]);
                    InternalFrameDemo.contentPanel.removeAll();
                    //contentPanel.add(new JScrollPane(  new ProjectProfile_1()));
                    //contentPanel.add(new JScrollPane(  new /*ProjectProfile*/SelectedSystemComponents()));
                    InternalFrameDemo.contentPanel.add(new JScrollPane(new YourProject(sample)));
                    InternalFrameDemo.contentPanel.revalidate();
                    InternalFrameDemo.contentPanel.repaint();
                    
                   InternalFrameDemo m =   (InternalFrameDemo) InternalFrameDemo.mainFrame;
                   m.setUserMenuTag();
                   
                   LeftPanel left = m.getLeftBar();
                   left.resetCalculators();
                   
                   UserSettingsData.setActiveProject(selPro);
                   
                   } catch (Exception e1) {
                       
                       BusinessException.showBusinessException("LG0401001",e1.getMessage(),JOptionPane.ERROR_MESSAGE);
                       e1.printStackTrace();
                   }
                    
                    //JMenu j =(JMenu)MainFrame.mainFrame.getMenuBar().getMenu(5);
                   // MainFrame.mainFrame.getMenuBar().getMenu(5).setLabel("sklbdfblg");

            }


        };
        
        for (int i = 0; i < projects.getColumnCount(); i++) {
            
            projects.setDefaultRenderer(projects.getColumnClass(i), new WineCellRenderer());
            
            
        }
        

        
        
        ButtonColumn buttonColumn = new ButtonColumn(projects, delete, 4);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
  
        add(label,BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(projects),BorderLayout.CENTER); 
        
         projects.getTableHeader().setOpaque(true);
         projects.setOpaque(true);
         projects.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         projects.setGridColor(Color.LIGHT_GRAY);
    }
    
}

class WineCellRenderer extends DefaultTableCellRenderer {
   public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
   {
   
  
      if (table.getValueAt(row, 0).equals(UserInfo.getInstance().getProjectNo()+"")) {
         setBackground(Color.cyan);
      }
      else {
         setBackground(Color.white);
         //setBackground(Color.red);
      }
  
      return super.getTableCellRendererComponent(table, value, isSelected,
                                                 hasFocus, row, column);
   }
}