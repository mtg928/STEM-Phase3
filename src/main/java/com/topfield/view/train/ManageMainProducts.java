/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SILDao;
import com.topfield.dao.SubProductFunctionsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SILDaoImpl;
import com.topfield.daoImpl.SubProductFunctionsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Sil;
import com.topfield.modal.SubProductFunctions;
import com.topfield.project.ProjectDefinition;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.DataEntryPopup;
import com.topfield.themes.PanelButton;
import com.topfield.valid.JTextFieldLimit;
import com.topfield.utilities.UppercaseDocumentFilter;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.view.calculators.Calculator1;
import com.topfield.view.calculators.Calculator1FromTotal;
import com.topfield.view.calculators.SILcalculator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;


public class ManageMainProducts extends JPanel{
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private JTable silTable; 
    private String[] funtions;
    private ManageArchitecture parent;
    

    public ManageMainProducts(ManageArchitecture parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        JLabel label= new JLabel();
        label.setText("Manage Main Products");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        List<MainProductGroup> dataMpg = mpgDao.getAllMPGBYUser(UserInfo.getInstance().getuser().getUsername());
        String[] columnNames = {"No","Product Id","Product Ref","Product Name","Product Type", "Fss","" }; 
        String[][] data = new String[dataMpg.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataMpg.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataMpg.get(i).getMpgId()+"";
            data[i][2] =dataMpg.get(i).getMpgRef();
            data[i][3] =dataMpg.get(i).getMpgDescription();
            data[i][4] =dataMpg.get(i).getType();
            data[i][5] =dataMpg.get(i).getFss();
            data[i][6] ="Update";
        }

  
        // Column Names 
        
        
        silTable = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               
               int compId = Integer.parseInt(e.getActionCommand());
               String[] res = new String []{silTable.getValueAt(compId, 2)+"",silTable.getValueAt(compId, 3)+"",silTable.getValueAt(compId, 4)+"",silTable.getValueAt(compId, 5)+""};
               res = display(res);
            
                    if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") ||res[3].equals("") )) {
                       // res = display(res);
                      /*for (MouseListeners listener : silTable.getMouseListeners()) {
                            listener.actionPerformed(event);
                        }*/
                      JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }else{

                        if((res != null)){
                          mpgDao.Update(getMpgFinal(Integer.parseInt(silTable.getValueAt(compId, 1)+""), res));
                          refreshPage();
                        }


                        System.out.println("Data Saved");
                        res = new String[4];

                    } 
                
               
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
                
                if (col ==6 ) {
                     

                }
            }
        });

        
        ButtonColumn buttonColumn = new ButtonColumn(silTable, delete, 6);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
  
        add(setbuttonSettings( parent),BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(silTable),BorderLayout.CENTER); 
        
         //silTable.getTableHeader().setBackground(FrameSettings.getButtonSelColor());
         //silTable.getTableHeader().setBackground(Color.DARK_GRAY);
         silTable.setOpaque(true);
         silTable.getTableHeader().setOpaque(true);
         silTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         silTable.getTableHeader().setFont(silTable.getTableHeader().getFont().deriveFont(Font.BOLD));
         silTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         silTable.setGridColor(Color.LIGHT_GRAY);
    }
    
    
   public MainProductGroup getMpgFinal(int mpgId,String[] mpgData){
       MainProductGroup mpg = new MainProductGroup();
       if (mpgId != -1) {
           mpg.setMpgId(mpgId);
       }
       
       mpg.setMpgRef(mpgData[0]);
       mpg.setMpgDescription(mpgData[1]);
       mpg.setType(mpgData[2]);
       mpg.setFss(mpgData[3]);
       mpg.setUsername(UserInfo.getInstance().getuser());
    return mpg;
    }   

    private JPanel setbuttonSettings(ManageArchitecture parent) {
        
        
        PanelButton addButton = new PanelButton("Add Main Products");
        addButton.addActionListener(new ActionListener() {
        String[] res = new String[4];
        public void actionPerformed(ActionEvent event) {
            
            System.out.println("event - "+event.getModifiers());
             res = display(res);
            
            if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") ||res[3].equals("") )) {
               // res = display(res);
              for (ActionListener listener : addButton.getActionListeners()) {
                    listener.actionPerformed(event);
                }
            }else{

                if((res != null)){
                  mpgDao.Save(getMpgFinal(-1, res));
                  refreshPage();
                }
                
                            
                System.out.println("Data Saved");
                res = new String[4];
                
            } 
               
          }
        });

        PanelButton removeButton = new PanelButton("Remove Main Products");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = silTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                   if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            mpgDao.Delete(Integer.parseInt(silTable.getValueAt(selRow, 1)+""));
                            refreshPage();
                    } else {
                        // no option
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
            refreshPage();
          }
        });
        
        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        return buttonPanel;
    }
     
     
     private String[] display(String[] data) {
        String[] res = null;
        String[] items = {"Mechanical", "Electrical"};
        
        
        
        JTextField field1 = new JTextField(); field1.setDocument(new JTextFieldLimit(1)); field1.setText(data[0]);
        //((AbstractDocument) field1.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        JTextField field2 = new JTextField(); field2.setText(data[1]);
        JComboBox<String> combo = new JComboBox<>(items); combo.setSelectedItem( (data[2] ==null || data[2].equals("") )? "Mechanical":data[2]);
        JTextField field3 = new JTextField(); field3.setText(data[3]); 
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("MPG Designation"));
        panel.add(field1);
        panel.add(new JLabel("MPG Name"));
        panel.add(field2);
        panel.add(new JLabel("MPG Type"));
        panel.add(combo);
        panel.add(new JLabel("Fss"));
        panel.add(field3);
        
        JOptionPane optionPane = new JOptionPane();
        
        int result = optionPane.showConfirmDialog(null, panel, "Please fill all fields",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            res = new String[4];
            System.out.println(combo.getSelectedItem()+ " " + field1.getText()+ " " + field2.getText());
            res[0]=field1.getText();
            res[1]=field2.getText();
            res[2]=combo.getSelectedItem()+"";
            res[3]=field3.getText();
        } else {
            System.out.println("Cancelled");
        }

       return res;
    }

     
   private void refreshPage() {
     parent.setComponentAt(0, new ManageMainProducts(parent)); 
   }
     
     
     
    
}



    


