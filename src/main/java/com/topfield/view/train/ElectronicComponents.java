/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.ComponentsDao;
import com.topfield.daoImpl.ComponentsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Components;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
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


    
public class ElectronicComponents extends JPanel{
    
    private ComponentsDao comDao = new ComponentsDaoImpl();
    private JTable silTable; 
    private String[] funtions;
    

    public ElectronicComponents() {
     setLayout(new BorderLayout());
        
        JLabel label= new JLabel();
        label.setText("Electronic Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        List<Components> dataMpg = comDao.getAllComponentsByUser(UserInfo.getInstance().getuser().getUsername());
        String[] columnNames = {"No","ComponentId","Component Type","Component Name","Manufacturer", "Failure Rate","" }; 
        String[][] data = new String[dataMpg.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataMpg.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataMpg.get(i).getComId()+"";
            data[i][2] =dataMpg.get(i).getComType();
            data[i][3] =dataMpg.get(i).getComDescription();
            data[i][4] =dataMpg.get(i).getManufacturer();
            data[i][5] =dataMpg.get(i).getFailureRate();
            data[i][6] =UpdateSetter(dataMpg.get(i).getUsername().getUsername());
        }

  
        // Column Names 
        
        
        silTable = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               
               int compId = Integer.parseInt(e.getActionCommand());
               Components compref = comDao.findById(Integer.parseInt(silTable.getValueAt(compId, 1)+""));
               String[] res = getElectronicComponentsAsArray(compref);
               res = display(res,compref.getUsername().getUsername().equals("Admin")?false:true);
            
                    if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") ||res[3].equals("") )) {
                       // res = display(res);
                      /*for (MouseListeners listener : silTable.getMouseListeners()) {
                            listener.actionPerformed(event);
                        }*/
                      JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }else{

                        if((res != null)){
                          comDao.Update(getComFinal(Integer.parseInt(silTable.getValueAt(compId, 1)+""), res));
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
  
        add(setbuttonSettings(),BorderLayout.NORTH); 
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
    
    
   public Components getComFinal(int mpgId,String[] mpgData){
       Components comb = new Components();
       if (mpgId != -1) {
           comb.setComId(mpgId);
       }
       
       comb.setComType(mpgData[0]);
       comb.setComDescription(mpgData[1]);
       comb.setLamda(Double.parseDouble(mpgData[2]));
       comb.setCcf(Double.parseDouble(mpgData[3]));
       comb.setSafetyRelevantFactor(Double.parseDouble(mpgData[4]));
       comb.setDc(Double.parseDouble(mpgData[5]));
       comb.setMttr(Double.parseDouble(mpgData[6]));
       comb.setPti(Double.parseDouble(mpgData[7]));
       comb.setFailureRate(mpgData[8]);
       comb.setManufacturer(mpgData[9]);
       comb.setUsername(UserInfo.getInstance().getuser());
    return comb;
    }   

    private JPanel setbuttonSettings() {
        
        
        PanelButton addButton = new PanelButton("Add Main Products");
        addButton.addActionListener(new ActionListener() {
        String[] res = new String[10];
        public void actionPerformed(ActionEvent event) {
            
            System.out.println("event - "+event.getModifiers());
             res = display(res,true);
            
            if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") ||res[3].equals("") )) {
               // res = display(res);
              for (ActionListener listener : addButton.getActionListeners()) {
                    listener.actionPerformed(event);
                }
            }else{

                if((res != null)){
                  comDao.Save(getComFinal(-1, res));
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
                   
                 
                       
                            if (comDao.findById(Integer.parseInt(silTable.getValueAt(selRow, 1)+"")).getUsername().getUsername().equals("Admin")) {

                                 JOptionPane.showMessageDialog(null, "Admin level data can not be deleted");
                            } else {
                                  if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                                     comDao.Delete(Integer.parseInt(silTable.getValueAt(selRow, 1)+""));
                                     JOptionPane.showMessageDialog(null, "Deleted");
                                     refreshPage();
                                     
                                     } else {
                       
                                     }
                            
                            }


                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }

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
     
     
     private String[] display(String[] data,boolean view) {
        String[] res = null;
        String[] items = {"Sensors", "Controller","Final Element"};
        int result =2;
        
        JComboBox<String> combo = new JComboBox<>(items); combo.setSelectedItem( (data[0] ==null || data[0].equals("") )? "Mechanical":data[0]);
        JTextField field1 = new JTextField(); field1.setText(data[1]);
        JTextField field2 = new JTextField(); field2.setText(data[2]);
        JTextField field3 = new JTextField(); field3.setText(data[3]); 
        JTextField field4 = new JTextField(); field4.setText(data[4]);
        JTextField field5 = new JTextField(); field5.setText(data[5]);
        JTextField field6 = new JTextField(); field6.setText(data[6]);
        JTextField field7 = new JTextField(); field7.setText(data[7]);
        JTextField field8 = new JTextField(); field8.setText(data[8]);
        JTextField field9 = new JTextField(); field9.setText(data[9]);
        
        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.add(new JLabel("Component Type"));
        panel.add(combo);
        panel.add(new JLabel("Component Name"));
        panel.add(field1);
        panel.add(new JLabel("Lamda"));
        panel.add(field2);
        panel.add(new JLabel("CCF"));
        panel.add(field3);
        panel.add(new JLabel("Safety Relevant Factor"));
        panel.add(field4);
        panel.add(new JLabel("DC"));
        panel.add(field5);
        panel.add(new JLabel("MTTR"));
        panel.add(field6);
        panel.add(new JLabel("PTI"));
        panel.add(field7);
        panel.add(new JLabel("Failure Rate"));
        panel.add(field8);
        panel.add(new JLabel("Manufacturer"));
        panel.add(field9);
        
        JOptionPane optionPane = new JOptionPane();
       
        
         if (view) {
             result = optionPane.showConfirmDialog(null, new JScrollPane(panel), "Please fill all fields",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
         }else{
         
             optionPane.showMessageDialog(null, new JScrollPane(panel), "Please fill all fields",JOptionPane.WARNING_MESSAGE);
         }
        
        if (result == JOptionPane.OK_OPTION) {
            res = new String[10];
            System.out.println(combo.getSelectedItem()+ " " + field1.getText()+ " " + field2.getText());
            res[0]=combo.getSelectedItem()+"";
            res[1]=field1.getText();
            res[2]=field2.getText();
            res[3]=field3.getText();
            res[4]=field4.getText();
            res[5]=field5.getText();
            res[6]=field6.getText();
            res[7]=field7.getText();
            res[8]=field8.getText();
            res[9]=field9.getText();
        } else {
            System.out.println("Cancelled");
        }

       return res;
    }

     
   private void refreshPage() {
     InternalFrameDemo.mainFrame.setCalculators(new ElectronicComponents(), false,true );
   }
     
   
   public String[] getElectronicComponentsAsArray(Components comb){
       
       String res [] = new String[10];
       
            res[0]= comb.getComType();
            res[1]= comb.getComDescription();
            res[2]= comb.getLamda().toString();
            res[3]= comb.getCcf().toString();
            res[4]= comb.getSafetyRelevantFactor().toString();
            res[5]= comb.getDc().toString();
            res[6]= comb.getMttr().toString();
            res[7]= comb.getPti().toString();  				
            res[8]= comb.getFailureRate();  			
            res[9]= comb.getManufacturer();  				

       return res;
   }
     
   public String UpdateSetter(String username){
    String res = "";
    
       if (username.equals("Admin")) {
           res = "View Only(Admin)";
       }else{
           res = "Update("+username+")";
       }

    return res;
    }
     
    
}



    



