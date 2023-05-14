/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.user.UserInfo;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.TclProjects;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;

/**
 *
 * @author Murali
 */
public class ProjectProfile extends JPanel {
    
    private TclProjectsDao tclProDao = new TclProjectsDaoImpl();
    

    public ProjectProfile() {
        JFrame parent = InternalFrameDemo.mainFrame;   
         parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        setLayout(new BorderLayout());
        
        TclProjects tcl = tclProDao.findById(UserInfo.getInstance().getProjectNo());
        
        CreateProjectPanel createProjectPanel = new CreateProjectPanel();
        createProjectPanel.setData(tcl);
        CalculaterSettings calculaterSettings = new CalculaterSettings();
        calculaterSettings.setSelectedString(tcl.getCalculationScope());
        SelectedSystemComponents selectedSystemComponents = new SelectedSystemComponents();
        selectedSystemComponents.SetData(tcl.getSystemScope());
        
         JLabel leftPanelCaption = new JLabel("Project Profile",SwingConstants.CENTER);
        //leftPanelCaption.setFont(Font.getFont(attributes));
                // if true the component paints every pixel within its bounds
        leftPanelCaption.setText("<html>"+ "<strong> <h2> Project Profile </h2> </strong>" +"</html>");
        leftPanelCaption.setOpaque(true);
        leftPanelCaption.setBackground(Color.darkGray);
        leftPanelCaption.setForeground(Color.WHITE);
        add(leftPanelCaption);
        
        JPanel heading = new JPanel();
        heading.setLayout(new BorderLayout());
        //heading.add(new JLabel("<html> <h1>This is heading 1</h1> </html>"),BorderLayout.NORTH);
        heading.add(leftPanelCaption,BorderLayout.NORTH);
        heading.add(createProjectPanel, BorderLayout.CENTER);
        
        JPanel Bottom = new JPanel();
        JButton updateProfile = new JButton("Update Profile");
        
        Bottom.setLayout(new BorderLayout());
        //heading.add(new JLabel("<html> <h1>This is heading 1</h1> </html>"),BorderLayout.NORTH);
        Bottom.add(calculaterSettings, BorderLayout.CENTER);
        Bottom.add(updateProfile,BorderLayout.SOUTH);
        updateProfile.setBackground(new Color(107, 106, 104));
        updateProfile.setPreferredSize(new Dimension(300, 30));
        updateProfile.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
        updateProfile.setForeground(Color.red);
        
        add(heading, BorderLayout.NORTH);
        add(selectedSystemComponents, BorderLayout.CENTER);
        add(Bottom, BorderLayout.SOUTH);
        
         parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        updateProfile.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
               TclProjects pro = new TclProjects();
                pro.setProId(UserInfo.getInstance().getProjectNo());
                pro.setProName(createProjectPanel.getProjetName());
                pro.setProType(createProjectPanel.getProjetType());
                pro.setAbbreviation(createProjectPanel.getAbbreviation());
                pro.setProDescription(createProjectPanel.getProjetDisc());
                pro.setFilePath(createProjectPanel.getFilePath());
                pro.setCreatedBy(UserInfo.getInstance().getuser());
                pro.setCreatedDate(UserInfo.getInstance().getDate());
                pro.setCalculationScope(calculaterSettings.getSelectedString());
                pro.setSystemScope(selectedSystemComponents.getSelections());
                tclProDao.editProjects(pro);
                UserInfo.getInstance().setProject(pro);
                InternalFrameDemo.mainFrame.setUserMenuTag();
              JOptionPane.showMessageDialog(null, "Project Profile Updated");
          }
        });
        
    }
    
    
    
    
   
    
    
    
}
