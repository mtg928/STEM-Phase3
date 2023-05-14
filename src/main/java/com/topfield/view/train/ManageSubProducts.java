/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.datamodel.Item;
import com.topfield.valid.JTextFieldLimit;
import com.topfield.utilities.UppercaseDocumentFilter;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Murali
 */
public class ManageSubProducts extends JPanel {
 private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();   
 private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private JTable silTable; 
    private String[] funtions;
    private ManageArchitecture parent;
    

    public ManageSubProducts(ManageArchitecture parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        JLabel label= new JLabel();
        label.setText("Manage Main Products");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        List<SubProductGroup> dataSpg = spgDao.getAllSPGByUser(UserInfo.getInstance().getuser().getUsername());
        String[] columnNames = {"No","Product Id","Product Ref","Main Product","Product Type", "Product Name","" }; 
        String[][] data = new String[dataSpg.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataSpg.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSpg.get(i).getSpgId()+"";
            data[i][2] =dataSpg.get(i).getSpgRef();
            data[i][3] =dataSpg.get(i).getMpgRef().getMpgDescription();
            data[i][4] =dataSpg.get(i).getSil();
            data[i][5] =dataSpg.get(i).getSpgDescription();
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
               int hbhjb = Integer.parseInt(silTable.getValueAt(compId, 1)+"");
                System.out.println(""+silTable.getValueAt(compId, 1)+"   -   "+compId);
               
               MainProductGroup main = spgDao.getSPGById(Integer.parseInt(silTable.getValueAt(compId, 1)+"")).getMpgRef();
               
               System.out.println(""+main.getMpgId());
               String[] res = new String []{silTable.getValueAt(compId, 2)+"",main.getMpgId()+"",silTable.getValueAt(compId, 4)+"",silTable.getValueAt(compId, 5)+""};
               res = display(res);
            
                    if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") ||res[3].equals("") )) {
                       // res = display(res);
                      /*for (MouseListeners listener : silTable.getMouseListeners()) {
                            listener.actionPerformed(event);
                        }*/
                      JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }else{

                        if((res != null)){
                          spgDao.Update(getSpgFinal(Integer.parseInt(silTable.getValueAt(compId, 1)+""), res));
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
    
    
   public SubProductGroup getSpgFinal(int spgId,String[] spgData){
       SubProductGroup spg = new SubProductGroup();
       if (spgId != -1) {
           spg.setSpgId(spgId);
       }
       
       spg.setSpgRef(spgData[0]);
       spg.setMpgRef(mpgDao.FindById(Integer.parseInt(spgData[1])));
       spg.setSil(spgData[2]);
       spg.setSpgDescription(spgData[3]);
       spg.setUsername(UserInfo.getInstance().getuser());
    return spg;
    }   

    private JPanel setbuttonSettings(ManageArchitecture parent) {
        
        
        PanelButton addButton = new PanelButton("Add Sub Products");
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
                  spgDao.Save(getSpgFinal(-1, res));
                  refreshPage();
                }
                
                            
                System.out.println("Data Saved");
                res = new String[4];
                
            } 
               
          }
        });

        PanelButton removeButton = new PanelButton("Remove Sub Products");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = silTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                    if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                              spgDao.Delete(Integer.parseInt(silTable.getValueAt(selRow, 1)+""));
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
        Item itemSel;
        MainProductGroup main ;
        
        //JTextFieldLimit wordLimit = new JTextFieldLimit(1);
        //wordLimit.setDocumentFilter(new UppercaseDocumentFilter());
        
        JTextField field1 = new JTextField(); field1.setDocument(new JTextFieldLimit(1)); field1.setText(data[0]);
        if(data[1] == null || data[1].equals("")){
           main = mpgDao.FindById(1);  
        }else{
          main = mpgDao.FindById(Integer.parseInt(data[1]));
        }
        JComboBox combo = new JComboBox();
        JComboBox<String> combo2 = new JComboBox<>(items); combo2.setSelectedItem( (data[2] ==null || data[2].equals("") )? "Mechanical":data[2]);
        JTextField field2 = new JTextField(); field2.setText(data[3]); 
        
        for (MainProductGroup mainProductGroup : mpgDao.getAllMPG()) {
            itemSel = new Item(mainProductGroup.getMpgId(), mainProductGroup.getMpgDescription());
            combo.addItem(itemSel); 
            if (mainProductGroup.getMpgId() == main.getMpgId()) {
                combo.setSelectedItem(itemSel);
            }
            
        }
        
        
      
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select MPG Group"));
        panel.add(combo);
        panel.add(new JLabel("SPG Designation"));
        panel.add(field1);
        panel.add(new JLabel("SPG Name"));
        panel.add(field2);
        panel.add(new JLabel("SPG Type"));
        panel.add(combo2);

        
        JOptionPane optionPane = new JOptionPane();
        
        int result = optionPane.showConfirmDialog(null, panel, "Please fill all fields",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            res = new String[4];
            Item item = (Item) combo.getSelectedItem();
            System.out.println(item.getId()+ " " + field1.getText()+ " " + field2.getText());
            res[0]=field1.getText();
            res[1]=item.getId()+"";
            res[2]=combo2.getSelectedItem()+"";
            res[3]=field2.getText();
        } else {
            System.out.println("Cancelled");
        }

       return res;
    }

     
   private void refreshPage() {
     parent.setComponentAt(1, new ManageSubProducts(parent)); 
   }
     
     
     
    
}



    



