/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmeca;

import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.fmeca.FMECA;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.FMECADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.modal.MainProductGroup;
import com.topfield.project.ShowSelectedItemsAsList;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Murali
 */
public class FMECAComponents extends JPanel{

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private FMECADao fMECADao = new FMECADaoImpl();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private List<Object[]> dataSingleCom = fMECADao.getSummaryFMECAByMPG(UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());
    private JTable projects; 
    private UserDao userDao = new UserDaoImpl();

    public FMECAComponents(FMECACalculator parent) {
        
        setLayout(new BorderLayout());
        
        JLabel label= new JLabel();
        label.setText("FMECA of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        String[] splitData;
        String[] columnNames = {"No","Main Component Id","Main Component Name","Row Count","Failure Mode Criticality(Cm)","Failure Item Criticality (Cr)"/*, "B Score","Raw Score","Beta Factor","Diagnostic Coverage"*/,"" }; 
        String[][] data = new String[dataSingleCom.size()][columnNames.length+1];
        
        PanelButton addButton = new PanelButton("Add FMECA Components");
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
             int n=0;
             
              ShowSelectedItemsAsList selcomp=    new ShowSelectedItemsAsList(getMainComponents());
                   Object[] options1 = { "ADD","Cancel"};

                   int result = JOptionPane.showOptionDialog(null, selcomp, "Select to insert components",
                           JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                           null, options1, null);

                     
                         if (result == JOptionPane.YES_OPTION) {
                             
                           InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));     
                             //String select[] = projectDefinition.getSelections().split("\\#");
                             String select[] = selcomp.getSelectedComponents();
                            // System.out.println("****************************************8 "+projectDefinition.getSelections());
                            
                             for (String string : select) {
                                 
                                 System.out.println("**** String - "+string);
                                  fMECADao.saveFMECA(getFmeaFinal(string));
                             
                             }
                             
                          InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                          parent.refreshPage();

                         } else if (result == JOptionPane.NO_OPTION) { 

                         } else {
                           //  System.out.println("Cancelled");
                         }
                         
                  //  JOptionPane.showMessageDialog(null, "Please add your details now ");      
          }
        });

        PanelButton removeButton = new PanelButton("Remove FMECA Components");
        //removeButton.setBackground(new Color(214, 219, 223));
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = projects.getSelectedRow();
            
               if(selRow >= 0){
                  fMECADao.deleteFMECAComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), Integer.parseInt(dataSingleCom.get(selRow)[0]+""));
                  parent.refreshPage();

                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }
            
              
            //model.removeRow(table.getSelectedRow());
          //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
          //  refreshPage();
          }
        });
        
        PanelButton refreashButton = new PanelButton("Refresh");
        //refreashButton.setBackground(new Color(214, 219, 223));
        refreashButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            parent.refreshPage();
          }
        });
        
        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSingleCom.get(i)[0]+"";
            data[i][2] =dataSingleCom.get(i)[1]+"";
            data[i][3] =dataSingleCom.get(i)[2]+"";
            data[i][4] =dataSingleCom.get(i)[3]+"";
            data[i][5] =dataSingleCom.get(i)[4]+"";
           /* splitData = dataSingleCom.get(i).getAchievement().split("\\#");
            
            for (int j = 0; j < (splitData.length); j++) {
               data[i][(j+3)] =splitData[j];
            }
            data[i][7] =dataSingleCom.get(i).getAchievementPercentage();*/
            data[i][6] ="View Details";
            System.out.println("ffffffffffffffffffffffffffff "+i);
        }


  
        // Column Names 
        
        
        projects = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               int compId = Integer.parseInt(e.getActionCommand());
                
                //JOptionPane.showMessageDialog(null, "Massage - "+dataSingleCom.get(compId)[0]+"");
                
                   // parent.setComponentAt(1, new FMECA(Integer.parseInt(dataSingleCom.get(compId)[0]+"")));
                    parent.setSelectedIndex(1); 


            }


        };
        
        for (int i = 0; i < projects.getColumnCount(); i++) {
            
            projects.setDefaultRenderer(projects.getColumnClass(i), new WineCellRenderer());
            
            
        }
        

        
        
        ButtonColumn buttonColumn = new ButtonColumn(projects, delete, 6);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
  
        add(buttonPanel,BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        
        JScrollPane center = new JScrollPane(projects);
        
        center.getVerticalScrollBar().setUnitIncrement(16);
        center.getHorizontalScrollBar().setUnitIncrement(35);
        center.getHorizontalScrollBar().setPreferredSize(new Dimension( Integer.MAX_VALUE,15));
        center.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override 
            protected void configureScrollBarColors(){
                this.thumbColor = new Color(33, 97, 140);
            }
        });
        center.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override 
            protected void configureScrollBarColors(){
                this.thumbColor = new Color(33, 97, 140);
            }
        });
        
         projects.getTableHeader().setForeground(FrameSettings.getButtonColor());
         projects.getTableHeader().setFont(projects.getTableHeader().getFont().deriveFont(Font.BOLD));
        
        add(center,BorderLayout.CENTER); 
        
    }
    
    public Fmeca getFmeaFinal(String mpg){
     
               Fmeca f  = new Fmeca();
               //f.setFmeaId();
               //f.setFmecaComponent(mainProDao.FindByName(mpg));
               //f.setFmecaproid(proDao.findById(UserInfo.getInstance().getProjectNo()));
               //f.setFmecauser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
               //f.setFmecaproid(proDao.findById(TclSession.getProjectId()));
               //f.setFmecauser(userDao.findById(TclSession.getUsername()));
               //f.setFmecaCreateddate(new Date());
    
    return f;
    }   
     
     
     
     public String[] getMainComponents(){
        List<MainProductGroup> main = mainProDao.getAllMPG(); 
        String res[] = new String[main.size()];
        int count =0;
        
         for (MainProductGroup mainProductGroup : main) {
             
             res[count] = mainProductGroup.getMpgDescription();
             
             count++;
         }
        
     
     
     return res;
     }
     
     
    
}
