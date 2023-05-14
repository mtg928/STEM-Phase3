/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.datamodel.Item;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

/**
 *
 * @author Murali
 */

 public class ManageSubComponents extends JPanel {
 private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();   
 private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
 private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
 
    private JTable silTable; 
    private String[] funtions;
    private ManageArchitecture parent;
    

    public ManageSubComponents(ManageArchitecture parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        JLabel label= new JLabel();
        label.setText("Manage Main Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        List<SubProductComponents> dataSpc = spcDao.getAllSPCByUser(UserInfo.getInstance().getuser().getUsername());
        String[] columnNames = {"No","Component Id","Main Product","Sub Product","Product Name","" }; 
        String[][] data = new String[dataSpc.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataSpc.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSpc.get(i).getSpcId()+"";
            data[i][2] =dataSpc.get(i).getSpgRef().getMpgRef().getMpgDescription();
            data[i][3] =dataSpc.get(i).getSpgRef().getSpgDescription();
            data[i][4] =dataSpc.get(i).getSpcDescription();
            data[i][5] ="Update";
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
               
               SubProductGroup sub = spcDao.FindById(Integer.parseInt(silTable.getValueAt(compId, 1)+"")).getSpgRef();
               MainProductGroup main = sub.getMpgRef();
               
               System.out.println(""+main.getMpgId());
               String[] res = new String []{main.getMpgId()+"",sub.getSpgId()+"",silTable.getValueAt(compId, 4)+""};
               res = display(res);
            
                    if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") )) {
                       // res = display(res);
                      /*for (MouseListeners listener : silTable.getMouseListeners()) {
                            listener.actionPerformed(event);
                        }*/
                      JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }else{

                        if((res != null)){
                          spcDao.Update(getSpcFinal(Integer.parseInt(silTable.getValueAt(compId, 1)+""), res));
                          refreshPage();
                        }


                        System.out.println("Data Saved");
                        res = new String[3];

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

        
        ButtonColumn buttonColumn = new ButtonColumn(silTable, delete, 5);
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
    
    
   public SubProductComponents getSpcFinal(int spcId,String[] spcData){
       SubProductComponents spc = new SubProductComponents();
       if (spcId != -1) {
           spc.setSpcId(spcId);
       }
       
       spc.setSpgRef(spgDao.getSPGById(Integer.parseInt(spcData[1])));
       spc.setSpcDescription(spcData[2]);
       spc.setUsername(UserInfo.getInstance().getuser());
    return spc;
    }   

    private JPanel setbuttonSettings(ManageArchitecture parent) {
        
        
        PanelButton addButton = new PanelButton("Add Sub Component");
        addButton.addActionListener(new ActionListener() {
        String[] res = new String[3];
        public void actionPerformed(ActionEvent event) {
            
            System.out.println("event - "+event.getModifiers());
             res = display(res);
            
            if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") )) {
               // res = display(res);
              for (ActionListener listener : addButton.getActionListeners()) {
                    listener.actionPerformed(event);
                }
            }else{

                if((res != null)){
                  spcDao.Save(getSpcFinal(-1, res));
                  refreshPage();
                }
                
                            
                System.out.println("Data Saved");
                res = new String[3];
                
            } 
               
          }
        });

        PanelButton removeButton = new PanelButton("Remove Sub Component");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = silTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                    if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                              spcDao.Delete(Integer.parseInt(silTable.getValueAt(selRow, 1)+""));
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
        SubProductGroup sub;
        
        
        if(data[0] == null || data[0].equals("")){
           main = mpgDao.FindById(1);  
        }else{
          main = mpgDao.FindById(Integer.parseInt(data[0]));
        }
        JComboBox combo = new JComboBox();
        
        for (MainProductGroup mainProductGroup : mpgDao.getAllMPG()) {
            itemSel = new Item(mainProductGroup.getMpgId(), mainProductGroup.getMpgDescription());
            combo.addItem(itemSel); 
            if (mainProductGroup.getMpgId() == main.getMpgId()) {
                combo.setSelectedItem(itemSel);
            }
            
        }
        
        if(data[1] == null || data[1].equals("")){
           sub = null;  
        }else{
           sub = spgDao.getSPGById(Integer.parseInt(data[1]));
        }
        JComboBox combo2 = new JComboBox();
        
        for (SubProductGroup sainProductGroup : main.getSubProductGroupCollection()) {
                 Item  itemSel2 = new Item(sainProductGroup.getSpgId(), sainProductGroup.getSpgDescription());
                        combo2.addItem(itemSel2);

                        if (sub != null && sainProductGroup.getSpgId() == sub.getSpgId()) {
                            combo2.setSelectedItem(itemSel2);
                        }
        }
        
        combo.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
              Item  itemSel1;
              Item  itemSel2 = (Item) combo.getSelectedItem();
              MainProductGroup main2 = mpgDao.FindById(itemSel2.getId());
              
               combo2.removeAllItems();
                        for (SubProductGroup sainProductGroup : main2.getSubProductGroupCollection()) {
                        itemSel1 = new Item(sainProductGroup.getSpgId(), sainProductGroup.getSpgDescription());
                        combo2.addItem(itemSel1); 
                        if (sub != null && sainProductGroup.getSpgId() == sub.getSpgId()) {
                            combo2.setSelectedItem(itemSel1);
                        }
            
        }
            }
        });
        


        JTextField field1 = new JTextField(); field1.setText(data[2]); 

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select MPG"));
        panel.add(combo);
        panel.add(new JLabel("Select SPG"));
        panel.add(combo2);
        panel.add(new JLabel("Sub Product Component Name"));
        panel.add(field1);
        
        JOptionPane optionPane = new JOptionPane();
        
        int result = optionPane.showConfirmDialog(null, panel, "Please fill all fields",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            res = new String[3];
            Item item = (Item) combo.getSelectedItem();
            Item item2 = (Item) combo2.getSelectedItem();
            System.out.println(item.getId()+ " " + field1.getText()+ " " + item2.getId());
            res[0]=item.getId()+"";
            res[1]=item2.getId()+"";
            res[2]=field1.getText();
        } else {
            System.out.println("Cancelled");
        }

       return res;
    }

     
   private void refreshPage() {
     parent.setComponentAt(2, new ManageSubComponents(parent)); 
    // parent.refreshPage();
   }
     
     
     
    
}
