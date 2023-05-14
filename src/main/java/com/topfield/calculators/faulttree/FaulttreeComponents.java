/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.calculators.faulttree.FaultTreeData;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Faulttree;
import com.topfield.modal.Fmeca;
import com.topfield.modal.MainProductGroup;
import com.topfield.project.ShowSelectedItemsAsList;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.view.popup.FaultTreeFrom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
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
public class FaulttreeComponents extends JPanel{

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private List<Faulttree> dataSingleCom = faulttreeDao.getAllFaulttreeByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo());
    private JTable faulttreeTable; 
    private UserDao userDao = new UserDaoImpl();

    public FaulttreeComponents(FaultTreeCalculator parent) {
        
        setLayout(new BorderLayout());
        
        JLabel label= new JLabel();
        label.setText("FMECA of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        String[] splitData;
        String[] columnNames = {"No","Fault Id","Fault Name","Fault Type","Result","Components Name","Created date","","" }; 
        String[][] data = new String[dataSingleCom.size()][columnNames.length+1];
        
        PanelButton addButton = new PanelButton("Add Faulttree");
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
             int n=0;
             
             
            FaultTreeFrom faultForm =  new FaultTreeFrom(getMainComponents(),null,parent);
             
             
            
          }
        });
        
       PanelButton editButton = new PanelButton("Edit Faulttree");
        //removeButton.setBackground(new Color(214, 219, 223));
        editButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = faulttreeTable.getSelectedRow();
            
               if(selRow >= 0){
                   //System.out.println("faultTree.getFaultName() ----"+faultTree.getFaultName());
                  FaultTreeFrom faultForm =  new FaultTreeFrom(getMainComponents(),dataSingleCom.get(selRow),parent);
                  
                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to Edit");
                }
            

          }
        });

        PanelButton removeButton = new PanelButton("Remove Faulttree");
        //removeButton.setBackground(new Color(214, 219, 223));
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = faulttreeTable.getSelectedRow();
            
               if(selRow >= 0){
                  //fMECADao.deleteFMECAComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), Integer.parseInt(dataSingleCom.get(selRow)[0]+""));
                  faulttreeDao.remove(faulttreeDao.findById(Integer.parseInt(faulttreeTable.getValueAt(selRow, 1)+"")));
                  parent.refreshPage();

                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }
            
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
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        
        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSingleCom.get(i).getFaultId()+"";
            data[i][2] =dataSingleCom.get(i).getFaultName();
            data[i][3] =dataSingleCom.get(i).getFaultType();
            data[i][4] =dataSingleCom.get(i).getFaultResult()+"";
            data[i][5] =dataSingleCom.get(i).getFaultComponent().getMpgDescription();
            data[i][6] =formatter.format(dataSingleCom.get(i).getCreateddate());
           /* splitData = dataSingleCom.get(i).getAchievement().split("\\#");
            
            for (int j = 0; j < (splitData.length); j++) {
               data[i][(j+3)] =splitData[j];
            }
            data[i][7] =dataSingleCom.get(i).getAchievementPercentage();*/
            data[i][7] ="View Details";
            data[i][8] ="View Fault Tree";
            System.out.println("ffffffffffffffffffffffffffff "+i);
        }


  
        // Column Names 
        
        
        faulttreeTable = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               int compId = Integer.parseInt(e.getActionCommand());
               
               
               InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR)); 
               
                     parent.remove(1);
                     parent.remove(1);
                             //add("Fault Tree Graph", new Zoom());
                        //parent.add("Fault Tree Data", new FaultTreeData(parent,Integer.parseInt(faulttreeTable.getValueAt(compId,1)+"")));

                        //parent.add("Fault Tree Graph", new FaultTreeGraph(parent,Integer.parseInt(faulttreeTable.getValueAt(compId,1)+"")));
                        
                       // parent.add("Fault Tree Graph", new FaultTreeGraph2(parent,Integer.parseInt(faulttreeTable.getValueAt(compId,1)+"")));
                        
                 InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                   if (faulttreeTable.getSelectedColumn() == 7) {
                       
                    parent.setSelectedIndex(1); 
                       
                   } else if(faulttreeTable.getSelectedColumn() == 8) {
                    parent.setSelectedIndex(2); 

                   }
            }


        };
        
        for (int i = 0; i < faulttreeTable.getColumnCount(); i++) {
            
            faulttreeTable.setDefaultRenderer(faulttreeTable.getColumnClass(i), new WineCellRenderer());
            
            
        }
        

        
        
        ButtonColumn buttonColumn = new ButtonColumn(faulttreeTable, delete, 7);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
        
        ButtonColumn buttonColumn1 = new ButtonColumn(faulttreeTable, delete, 8);
        buttonColumn1.setMnemonic(KeyEvent.VK_D);
  
        add(buttonPanel,BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        
        JScrollPane center = new JScrollPane(faulttreeTable);
        
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
        
        
        
        add(center,BorderLayout.CENTER); 
        
         faulttreeTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         faulttreeTable.getTableHeader().setFont(faulttreeTable.getTableHeader().getFont().deriveFont(Font.BOLD));
        
    }
    


     
     
     
     public Item[] getMainComponents(){
        List<MainProductGroup> main = mainProDao.getAllMPG(); 
        Item res[] = new Item[main.size()];
        int count =0;
        
         for (MainProductGroup mainProductGroup : main) {
             
             res[count] = new Item(mainProductGroup.getMpgId(), mainProductGroup.getMpgDescription());
             
             count++;
         }
        
     
     
     return res;
     }
     
     
    
}

