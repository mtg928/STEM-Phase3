/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.CCFDao;
import com.topfield.dao.SILDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.SubProductFunctionsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.CCFDaoImpl;
import com.topfield.daoImpl.SILDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.SubProductFunctionsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Ccf;
import com.topfield.modal.Sil;
import com.topfield.modal.Standards;
import com.topfield.modal.SubProductFunctions;
import com.topfield.project.ProjectDefinition;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.DataEntryPopup;
import com.topfield.themes.PanelButton;
import com.topfield.themes.SelectionTixBoxPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Murali
 */
public class SILComponents extends JPanel{
    
    private SILDao silDao = new SILDaoImpl();
    private List<Sil> dataSil = silDao.getSILByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo());
    private ProjectDefinition projectDefinition = new ProjectDefinition(true);
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    private JTable silTable; 
    private SubProductFunctionsDao funtionsDao = new SubProductFunctionsDaoImpl();
    private String[] funtions;
    

    public SILComponents(SILcalculator parent) {
     setLayout(new BorderLayout());
        
        JLabel label= new JLabel();
        label.setText("CCF of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        String[] splitData;
        String[] columnNames = {"No","Component Id","Component Name","Component Functions","SIL Architecture", "Sensors Score", "Controller Score","Final Element Score","Total Score","" }; 
        String[][] data = new String[dataSil.size()][columnNames.length+1];
        List<SubProductFunctions> funs =funtionsDao.getAllSPGFunction();
        
        funtions = new String[funs.size()];
        
        for (int i = 0; i < funs.size(); i++) {
            funtions[i]= funs.get(i).getFunctions();
        }
        
        
   
        for (int i = 0; i < dataSil.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSil.get(i).getSilId()+"";
            data[i][2] =dataSil.get(i).getSILSubComponent().getSpgDescription();
            data[i][3] =dataSil.get(i).getSilFunction();
            data[i][4] =dataSil.get(i).getArchitecture();
            data[i][5] =dataSil.get(i).getSensorsScore();
            data[i][6] =dataSil.get(i).getControllerScore();
            data[i][7] =dataSil.get(i).getFinalElementScore();
            data[i][8] =dataSil.get(i).getTotalScore();
           /* splitData = dataSil.get(i).getAchievement().split("\\#");
            
            for (int j = 0; j < (splitData.length); j++) {
               data[i][(j+3)] =splitData[j];
            }
            data[i][7] =dataSil.get(i).getAchievementPercentage();*/
            data[i][9] ="View Details";
        }


  
        // Column Names 
        
        
        silTable = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               int compId = Integer.parseInt(e.getActionCommand());
                
                //JOptionPane.showMessageDialog(null, "Massage - "+dataSingleCom.get(compId).getCcfSubComponent().getSpgId());
                
                
               /* if (dataSingleCom.get(compId).getMainCategory().equals("Programmable")) {

                } else if (dataSingleCom.get(Integer.parseInt(e.getActionCommand())).getMainCategory().equals("Non programmable")){
                  // parent.setSelectedIndex(2);

                }*/

                    Calculator1 cal = new Calculator1(Integer.parseInt(silTable.getValueAt(compId, 1)+""),silTable.getValueAt(compId, 4)+"");
                    Calculator1FromTotal calTot = (Calculator1FromTotal)cal.getComponent(1);
                    parent.setSelectedIndex(1);
                    parent.setComponentAt(1, cal);
                    calTot.CalculateFinalTotal();
                    
                    //System.out.println("|||||||||||||||"+);
            }


        };
        
        for (int i = 0; i < silTable.getColumnCount(); i++) {
            
            silTable.setDefaultRenderer(silTable.getColumnClass(i), new WineCellRenderer());
            
            
        }
        
        silTable.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = silTable.rowAtPoint(evt.getPoint());
                int col = silTable.columnAtPoint(evt.getPoint());
                if (col ==3 ) {
                    
                  DataEntryPopup.showMultipleInputMessageDialog(funtions,col,row,silTable);
                  Sil sil = silDao.findById(Integer.parseInt(silTable.getValueAt(row, 1)+""));
                  sil.setSilFunction(silTable.getValueAt(row, 3)+"");
                  silDao.Upadte(sil);
                }
            }
        });

        //InternalFrameDemo.mainFrame.setLookAndFeel();
        
                //Fiddle with the Sport column's cell editors/renderers.
        setUpSportColumn(silTable, silTable.getColumnModel().getColumn(4));
        
        ButtonColumn buttonColumn = new ButtonColumn(silTable, delete, 9);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
  
        add(setbuttonSettings( parent),BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(silTable),BorderLayout.CENTER); 
        
         //silTable.getTableHeader().setBackground(FrameSettings.getButtonSelColor());
         //silTable.getTableHeader().setBackground(Color.DARK_GRAY);
         silTable.getTableHeader().setOpaque(true);
         silTable.setOpaque(true);
         silTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         silTable.getTableHeader().setFont(silTable.getTableHeader().getFont().deriveFont(Font.BOLD));
         silTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         silTable.setGridColor(Color.LIGHT_GRAY);
    }
    
    
     public Sil getSILFinal(String spg,String fun){
     
       // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
               Sil sil = new Sil();
               sil.setSilFunction(fun);
               sil.setSILSubComponent(subComDao.getSPGByName(spg));
               sil.setSiluser(UserInfo.getInstance().getuser());
               sil.setCreateddate(UserInfo.getInstance().getDate());
               sil.setProjectId(UserInfo.getInstance().getTclProject());
    
    return sil;
    }   

    private JPanel setbuttonSettings(SILcalculator parent) {
        
        
        PanelButton addButton = new PanelButton("Add SIL Components");
        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
             int n=0;
             Sil saveSil;
                   Object[] options = {"Programmable", "Non programmable"};
                   Object[] options1 = { "ADD","Cancel"};

                   int result = JOptionPane.showOptionDialog(null, projectDefinition, "Select to insert components",
                           JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                           null, options1, null);

                     
                         if (result == JOptionPane.YES_OPTION) {
                             
                           InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));     
                             //String select[] = projectDefinition.getSelections().split("\\#");
                             String select[] = projectDefinition.getSelectionsAsArray();
                             System.out.println("****************************************8 "+projectDefinition.getSelections());
                            
                            
                             for (String string : select) {
                                 
                                 
                                // System.out.println("**** String - "+string);
                                 if (string == null || string.contentEquals("")) {
                                     
                                     JOptionPane.showMessageDialog(null, "Subcomponents do not exists for this main component"); 
                                 } else {
                                      saveSil = getSILFinal(string, "");
                                      
                                      if (saveSil == null) {
                                         JOptionPane.showMessageDialog(null, "The components do not found - "+string);
                                     } else {
                                          // JOptionPane.showMessageDialog(null, "The components do not found cxvgdnfhn - "+saveSil.getSilFunction()+saveSil.getSILSubComponent().getSpgDescription());
                                          silDao.Save(saveSil);
                                          parent.refreshPage();
                                     }
                                      
                                         
                                 }
                                  
                                  
                             
                             }
                             
                          InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 


                         } else if (result == JOptionPane.NO_OPTION) { 

                         } else {
                           //  System.out.println("Cancelled");
                         }
                         
                  //  JOptionPane.showMessageDialog(null, "Please add your details now ");      
          }
        });

        PanelButton removeButton = new PanelButton("Remove SIL Components");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = silTable.getSelectedRow();
            
               if(selRow >= 0){
                  //ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), dataSingleCom.get(selRow).getMainCategory(),  dataSingleCom.get(selRow).getCcfSubComponent().getSpgId());
                       if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete? \n "
                            + "The operation will delete the record and the functions and components associated with it in the calculation (second) tab", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                           
                            silDao.Delete(silDao.findById(Integer.parseInt(silTable.getValueAt(selRow, 1)+"")));
                            parent.refreshPage();
                       }


                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }
            
              
            //model.removeRow(table.getSelectedRow());
          //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
          //  refreshPage();
          }
        });
        
        PanelButton refreashButton = new PanelButton("Refresh");
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
        
        return buttonPanel;
    }
     
     
     
     
     public void setUpSportColumn(JTable table,
                                 TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("1oo1");
        comboBox.addItem("1oo2");
        comboBox.addItem("2oo2");
        comboBox.addItem("1oo2D");
        comboBox.addItem("2oo3");
        //comboBox.addItem("None of the above");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
 
        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }
     
     
     
    
}



    

