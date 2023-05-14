/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Functionalfailures;
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Murali
 */


 public class FunctionalComponents extends JPanel {
 private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();   
 private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
 private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
 private FunctionalfailuresDao funfailDao = new FunctionalfailuresDaoImpl();
 private JComboBox comb = new JComboBox();
 
    private JTable silTable = new JTable(); 
    private String[] funtions;
    private FunctionalArchitecture parent;
    private Action delete;
    

    public FunctionalComponents(FunctionalArchitecture parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        JLabel label= new JLabel();
        label.setText("Manage Main Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        List<Functionalfailures> dataSpc = funfailDao.getAllFuncfailBySubCompUser(1,UserInfo.getInstance().getuser().getUsername());
       
        
        
        
        label.setBorder(new EmptyBorder(10,10,20,0));
        
         delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               
               int compId = Integer.parseInt(e.getActionCommand());
               int hbhjb = Integer.parseInt(silTable.getValueAt(compId, 1)+"");
                System.out.println(""+silTable.getValueAt(compId, 1)+"   -   "+compId);
               
               SubProductGroup sub = spcDao.FindById(Integer.parseInt(silTable.getValueAt(compId, 1)+"")).getSpgRef();
               MainProductGroup main = sub.getMpgRef();
               Functionalfailures fun = funfailDao.findById(hbhjb);
               
               System.out.println(""+main.getMpgId());
               String[] res = grtFuntionalFailuresAsArray(fun);
               
               res = display(res,fun.getUsername().getUsername().equals("Admin")?false:true);
            
                    if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") )) {
                       // res = display(res);
                      /*for (MouseListeners listener : silTable.getMouseListeners()) {
                            listener.actionPerformed(event);
                        }*/
                      JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }else{

                        if((res != null)){
                          funfailDao.Update(getFunFinal(Integer.parseInt(silTable.getValueAt(compId, 1)+""),((Item)comb.getSelectedItem()).getId(), res));
                          refreshPage();
                        }


                        System.out.println("Data Saved");
                        res = new String[13];

                    } 
                
               
            }


        };
        
        loadData(dataSpc);
        
        silTable.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = silTable.rowAtPoint(evt.getPoint());
                int col = silTable.columnAtPoint(evt.getPoint());
                
                if (col ==6 ) {
                     

                }
            }
        });

        

  
        add(setbuttonSettings( parent),BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(silTable),BorderLayout.CENTER); 
        
        
         silTable.getTableHeader().setOpaque(true);
         silTable.setOpaque(true);
         //silTable.getTableHeader().setBackground(FrameSettings.getButtonSelColor());
         //silTable.getTableHeader().setBackground(Color.DARK_GRAY);
         silTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         silTable.getTableHeader().setFont(silTable.getTableHeader().getFont().deriveFont(Font.BOLD));
         silTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         silTable.setGridColor(Color.LIGHT_GRAY);
    }
    
    
   public Functionalfailures getFunFinal(int funId,int compId,String[] funData){
       Functionalfailures fun = new Functionalfailures();
       if (funId != -1) {
           fun.setFailureId(funId);
       }
       
         fun.setProjecttype(funData[0]);
         fun.setFailuretype(funData[1])	;
         fun.setDescription(funData[2])	;
         fun.setFunction(funData[3])	;
         fun.setPhase(funData[4])	;
         fun.setFailureMode(funData[5])	;
         fun.setFailureCause(funData[6])	;
         fun.setLocalFailureEffect(funData[7])	; 				
         fun.setSubsystemFailureEffect(funData[8])	; 			
         fun.setTrainFailureEffect(funData[9])	; 				
         fun.setFailureDetection(funData[10]);				
         fun.setFailureRate(Double.parseDouble(funData[11].equals("")? "0.0" :funData[11]));			
         fun.setPreventiveMeasures(funData[12]);
       fun.setSubComponent(spcDao.FindById(compId));
       fun.setUsername(UserInfo.getInstance().getuser());
    return fun;
    }   

    private JPanel setbuttonSettings(FunctionalArchitecture parent) {
        
        
        PanelButton addButton = new PanelButton("Add Sub Component");
        addButton.addActionListener(new ActionListener() {
        String[] res = new String[13];
        public void actionPerformed(ActionEvent event) {
            
            System.out.println("event - "+event.getModifiers());
             res = display(res,true);
            
            if ( (res != null) && (res[0].equals("") ||res[1].equals("") ||res[2].equals("") )) {
               // res = display(res);
              for (ActionListener listener : addButton.getActionListeners()) {
                    listener.actionPerformed(event);
                }
            }else{

                if((res != null)){
                  funfailDao.Save(getFunFinal(-1,((Item)comb.getSelectedItem()).getId(), res));
                  refreshPage();
                }
                
                            
                System.out.println("Data Saved");
                res = new String[13];
                
            } 
               
          }
        });

        PanelButton removeButton = new PanelButton("Remove Sub Component");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = silTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                  if (funfailDao.findById(Integer.parseInt(silTable.getValueAt(selRow, 1)+"")).getUsername().getUsername().equals("Admin")) {

                                 JOptionPane.showMessageDialog(null, "Admin level data can not be deleted");
                            } else {
                                  if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                                     funfailDao.remove(Integer.parseInt(silTable.getValueAt(selRow, 1)+""));
                                     JOptionPane.showMessageDialog(null, "Deleted");
                                     refreshPage();
                                     
                                     } else {
                       
                                     }
                            
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
        
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout());
        
        JComboBox main = new JComboBox();
        JComboBox sub = new JComboBox();
        
        
        dataLoadIntial(main, sub, comb,0,null);
        
        main.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
              dataLoadIntial(main, sub, comb,1,null);
                if (sub.getItemCount()==0) {
                    loadData(new ArrayList<Functionalfailures>() );
                }
                
            }
        });
        
        sub.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
              dataLoadIntial(main, sub, comb,2,e);
                if (comb.getItemCount()==0) {
                    loadData(new ArrayList<Functionalfailures>() );
                }
            }
        });
        
        comb.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                loadData( funfailDao.getAllFuncfailBySubCompUser(((Item)e.getItem()).getId(),UserInfo.getInstance().getuser().getUsername()));
            }
        });
        

        
        selectionPanel.add(main);
        selectionPanel.add(sub);
        selectionPanel.add(comb);
        
        
        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        northPanel.add(selectionPanel,BorderLayout.NORTH);
        northPanel.add(buttonPanel,BorderLayout.SOUTH);
        
        return northPanel;
    }
     
     
     private String[] display(String[] data,boolean view) {
        String[] res = null;
        String[] projectTypeData = {"RAM", "Engineering safety","Functional Safety"};
        String[] items = {"Mechanical", "Electrical"};
        String[] phaseItems= {"UTO Mode", "RM Mode","DM Mode","ATPM Mode","UTO Mode, RM Mode,DM Mode,ATPM Mode"};
        int result =2;

        
        JPanel panel = new JPanel(); 
        panel.setBounds(300, 20, 700, 650); 
        panel.setLayout(new GridBagLayout() ); 
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);

		JLabel name = new JLabel("Project Type");
		name.setFont(new Font("Arial", Font.BOLD, 12)); 
		name.setSize(200, 20); 
		name.setLocation(40, 50); 
                gc.gridx = 0;
                gc.gridy = 0;
		panel.add(name,gc); 

		JComboBox proType = new JComboBox(projectTypeData); proType.setSelectedItem(data[0]); 
		proType.setFont(new Font("Arial", Font.PLAIN, 12)); 
		proType.setSize(190, 20); 
		proType.setLocation(170, 50); 
                gc.gridx = 1;
                gc.gridy = 0;
		panel.add(proType,gc); 

		JLabel mno = new JLabel("Failure Type"); 
		mno.setFont(new Font("Arial", Font.BOLD, 12)); 
		mno.setSize(200, 20); 
		mno.setLocation(40, 80); 
                gc.gridx = 0;
                gc.gridy = 1;
		panel.add(mno,gc); 

		JComboBox combo = new JComboBox(items);combo.setSelectedItem(data[1]); 
		combo.setFont(new Font("Arial", Font.PLAIN, 12)); 
		combo.setSize(190, 20); 
		combo.setLocation(170, 80); 
                gc.gridx = 1;
                gc.gridy = 1;
		panel.add(combo,gc); 

		JLabel Component1 = new JLabel("Component"); 
		Component1.setFont(new Font("Arial", Font.BOLD, 12)); 
		Component1.setSize(200, 20); 
		Component1.setLocation(40, 110); 
                gc.gridx = 0;
                gc.gridy = 2;
		panel.add(Component1,gc); 

		JTextField Component = new JTextField(); Component.setText(data[2]); 
		Component.setFont(new Font("Arial", Font.PLAIN, 12)); 
                Component.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Component.setSize(500, 20); 
		Component.setLocation(170, 110); 
                gc.gridx = 1;
                gc.gridy = 2;
		panel.add(Component,gc); 
                
                JLabel function1 = new JLabel("Function"); 
		function1.setFont(new Font("Arial", Font.BOLD, 12)); 
		function1.setSize(200, 20); 
		function1.setLocation(40, 140); 
                gc.gridx = 0;
                gc.gridy = 3;
		panel.add(function1,gc); 

		JTextField function = new JTextField(); function.setText(data[3]); 
		function.setFont(new Font("Arial", Font.PLAIN, 12)); 
                function.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		function.setSize(500, 20); 
		function.setLocation(170, 140);
                gc.gridx = 1;
                gc.gridy = 3;
		panel.add(function,gc); 
                
                JLabel phase1 = new JLabel("Phase"); 
		phase1.setFont(new Font("Arial", Font.BOLD, 12)); 
		phase1.setSize(200, 20); 
		phase1.setLocation(40, 170); 
                gc.gridx = 0;
                gc.gridy = 4;
		panel.add(phase1,gc); 

		JComboBox phase = new JComboBox(phaseItems);phase.setSelectedItem(data[4]);
		phase.setFont(new Font("Arial", Font.PLAIN, 12)); 
		phase.setSize(500, 20); 
		phase.setLocation(170, 170);
                gc.gridx = 1;
                gc.gridy = 4;
		panel.add(phase,gc);

                JLabel failureMode1 = new JLabel("Failure Mode"); 
		failureMode1.setFont(new Font("Arial", Font.BOLD, 12)); 
		failureMode1.setSize(200, 20); 
		failureMode1.setLocation(40, 200); 
                gc.gridx = 0;
                gc.gridy = 5;
		panel.add(failureMode1,gc); 

		JTextField failureMode = new JTextField(); failureMode.setText(data[5]); 
		failureMode.setFont(new Font("Arial", Font.PLAIN, 12)); 
                failureMode.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		failureMode.setSize(500, 20); 
		failureMode.setLocation(170, 200); 
                gc.gridx = 1;
                gc.gridy = 5;
		panel.add(failureMode,gc);

                JLabel failureCause1 = new JLabel("Failure Cause"); 
		failureCause1.setFont(new Font("Arial", Font.BOLD, 12)); 
		failureCause1.setSize(200, 20); 
		failureCause1.setLocation(40, 230); 
                gc.gridx = 0;
                gc.gridy = 6;
		panel.add(failureCause1,gc); 

		JTextField failureCause = new JTextField(); failureCause.setText(data[6]); 
		failureCause.setFont(new Font("Arial", Font.PLAIN, 12)); 
                failureCause.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		failureCause.setSize(500, 20); 
		failureCause.setLocation(170, 230); 
                gc.gridx = 1;
                gc.gridy = 6;
		panel.add(failureCause,gc);
                
                JLabel localFailureEffect1 = new JLabel("Local Failure Effect"); 
		localFailureEffect1.setFont(new Font("Arial", Font.BOLD, 12)); 
		localFailureEffect1.setSize(200, 20); 
		localFailureEffect1.setLocation(40, 260);
                gc.gridx = 0;
                gc.gridy = 7;
		panel.add(localFailureEffect1,gc); 

		JTextArea  localFailureEffect = new JTextArea (); localFailureEffect.setText(data[7]); localFailureEffect.setLineWrap(true);
		localFailureEffect.setFont(new Font("Arial", Font.PLAIN, 12)); 
		localFailureEffect.setSize(500, 50); 
		localFailureEffect.setLocation(170, 260); 
                gc.gridx = 1;
                gc.gridy = 7;
		panel.add(localFailureEffect,gc);
                
                JLabel subsystemFailureEffect1 = new JLabel("<html>Subsystem Failure <br/> Effect<html>"); 
		subsystemFailureEffect1.setFont(new Font("Arial", Font.BOLD, 12)); 
		subsystemFailureEffect1.setSize(200, 50); 
		subsystemFailureEffect1.setLocation(40, 310);
                gc.gridx = 0;
                gc.gridy = 8;
		panel.add(subsystemFailureEffect1,gc); 

		JTextArea subsystemFailureEffect = new JTextArea(); subsystemFailureEffect.setText(data[8]); subsystemFailureEffect.setLineWrap(true);
		subsystemFailureEffect.setFont(new Font("Arial", Font.PLAIN, 12)); 
		subsystemFailureEffect.setSize(500, 50); 
		subsystemFailureEffect.setLocation(170, 320); 
                gc.gridx = 1;
                gc.gridy = 8;
		panel.add(subsystemFailureEffect,gc);
                
                JLabel trainFailureEffect1 = new JLabel("Train Failure Effect"); 
		trainFailureEffect1.setFont(new Font("Arial", Font.BOLD, 12)); 
		trainFailureEffect1.setSize(200, 50); 
		trainFailureEffect1.setLocation(40, 380); 
                gc.gridx = 0;
                gc.gridy = 9;
		panel.add(trainFailureEffect1,gc); 

		JTextArea trainFailureEffect = new JTextArea(); trainFailureEffect.setText(data[9]); trainFailureEffect.setLineWrap(true);
		trainFailureEffect.setFont(new Font("Arial", Font.PLAIN, 12)); 
		trainFailureEffect.setSize(500, 50); 
		trainFailureEffect.setLocation(170, 380); 
                gc.gridx = 1;
                gc.gridy = 9;
		panel.add(trainFailureEffect,gc);
                
                JLabel failureDetection1 = new JLabel("Failure Detection"); 
		failureDetection1.setFont(new Font("Arial", Font.BOLD, 12)); 
		failureDetection1.setSize(200, 50); 
		failureDetection1.setLocation(40, 440); 
                gc.gridx = 0;
                gc.gridy = 10;
		panel.add(failureDetection1,gc); 

		JTextArea failureDetection = new JTextArea(); failureDetection.setText(data[10]); failureDetection.setLineWrap(true);
		failureDetection.setFont(new Font("Arial", Font.PLAIN, 12)); 
		failureDetection.setSize(500, 50); 
		failureDetection.setLocation(170, 440); 
                gc.gridx = 1;
                gc.gridy = 10;
		panel.add(failureDetection,gc);
                
                JLabel failureRate1 = new JLabel("Failure Rate"); 
		failureRate1.setFont(new Font("Arial", Font.BOLD, 12)); 
		failureRate1.setSize(200, 20); 
		failureRate1.setLocation(40, 500); 
                gc.gridx = 0;
                gc.gridy = 11;
		panel.add(failureRate1,gc); 

		JTextField failureRate = new JTextField(); failureRate.setText(data[11]); 
		failureRate.setFont(new Font("Arial", Font.PLAIN, 12)); 
                failureRate.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		failureRate.setSize(500, 20); 
		failureRate.setLocation(170, 500); 
                gc.gridx = 1;
                gc.gridy = 11;
		panel.add(failureRate,gc);
                
                
                JLabel preventiveMeasures1 = new JLabel("Preventive Measures"); 
		preventiveMeasures1.setFont(new Font("Arial", Font.BOLD, 12)); 
		preventiveMeasures1.setSize(200, 50); 
		preventiveMeasures1.setLocation(40, 530); 
                gc.gridx = 0;
                gc.gridy = 12;
		panel.add(preventiveMeasures1,gc); 

		JTextArea  preventiveMeasures = new JTextArea (); preventiveMeasures.setText(data[12]); preventiveMeasures.setLineWrap(true);
		preventiveMeasures.setFont(new Font("Arial", Font.PLAIN, 12)); 
		preventiveMeasures.setSize(500, 50); 
		preventiveMeasures.setLocation(170, 530); 
                gc.gridx = 1;
                gc.gridy = 12;
		panel.add(preventiveMeasures,gc);

        
        
        
        JOptionPane optionPane = new JOptionPane();
        
         if (view) {
             result = optionPane.showConfirmDialog(null, new JScrollPane(panel), "Please fill all fields",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
         }else{
         
             optionPane.showMessageDialog(null, new JScrollPane(panel), "Please fill all fields",JOptionPane.WARNING_MESSAGE);
         }
        

        
        if (result == JOptionPane.OK_OPTION) {
            res = new String[13];
            
            res[0]= proType.getSelectedItem()+"";
            res[1]= combo.getSelectedItem()+"";
            res[2]= Component.getText();
            res[3]= function.getText();
            res[4]= phase.getSelectedItem()+"";
            res[5]= failureMode.getText();
            res[6]= failureCause.getText();
            res[7]= localFailureEffect.getText();  				
            res[8]= subsystemFailureEffect.getText();  			
            res[9]= trainFailureEffect.getText();  				
            res[10]= failureDetection.getText();  				
            res[11]= failureRate.getText();  					
            res[12]= preventiveMeasures.getText();
                    
        } else {
            System.out.println("Cancelled");
        }

       return res;
    }

     
   private void refreshPage() {
    // parent.setComponentAt(3, new ManageSubComponents(parent)); 
         loadData( funfailDao.getAllFuncfailBySubCompUser(((Item)comb.getSelectedItem()).getId(),UserInfo.getInstance().getuser().getUsername()));
   }
     
   
   public void dataLoadIntial(JComboBox main, JComboBox sub, JComboBox comb,int selControl,ItemEvent event){
   
       try {
           
           InternalFrameDemo.mainFrame.setLoader();
       MainProductGroup selSub;
       SubProductGroup selComb;
       
       if (selControl ==0) {
           for (MainProductGroup mainProductGroup : mpgDao.getAllMPG()) {
                main.addItem(new Item(mainProductGroup.getMpgId(), mainProductGroup.getMpgDescription()));
                
           }
           System.out.println("Inside1");
       }


       if (selControl ==0 || selControl ==1) {
        selSub = mpgDao.FindById(((Item)main.getSelectedItem()).getId());
        sub.removeAllItems();
        for (SubProductGroup sainProductGroup : selSub.getSubProductGroupCollection()) {
                sub.addItem(new Item(sainProductGroup.getSpgId(), sainProductGroup.getSpgDescription()));
        }
        System.out.println("Inside1234");
       }
        
       comb.removeAllItems();
       
           
           if (event != null) {
               System.out.println(event.paramString());
               selComb = spgDao.getSPGById(((Item)event.getItem()).getId());
           }else{
           
              selComb = spgDao.getSPGById(((Item)sub.getSelectedItem()).getId());
           }
        
        for (SubProductComponents subProductComponents : selComb.getSubProductComponentsCollection()) {
                comb.addItem(new Item(subProductComponents.getSpcId(), subProductComponents.getSpcDescription()));
        }
        System.out.println("Inside13456");
       }catch (Exception e) {
           e.printStackTrace();
       }finally{
         InternalFrameDemo.mainFrame.removeLoder();
       
       }
       
   }
   
   
   public String[] grtFuntionalFailuresAsArray(Functionalfailures fun){
       
       String res [] = new String[13];
       
            res[0]= fun.getProjecttype()+"";
            res[1]= fun.getFailuretype();
            res[2]= fun.getDescription();
            res[3]= fun.getFunction();
            res[4]= fun.getPhase();
            res[5]= fun.getFailureMode();
            res[6]= fun.getFailureCause();
            res[7]= fun.getLocalFailureEffect();  				
            res[8]= fun.getSubsystemFailureEffect();  			
            res[9]= fun.getTrainFailureEffect();  				
            res[10]= fun.getFailureDetection();  				
            res[11]= fun.getFailureRate()+"";  					
            res[12]= fun.getPreventiveMeasures();

       return res;
   }
     
   
   public void loadData(List <Functionalfailures> dataSpc){
   
       String[] columnNames = {"No","Function Id","Function Type","Component Name","Function Name","" }; 
        String[][] data = new String[dataSpc.size()][columnNames.length+1];
        
   
        for (int i = 0; i < dataSpc.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataSpc.get(i).getFailureId()+"";
            data[i][2] =dataSpc.get(i).getFailuretype();
            data[i][3] =dataSpc.get(i).getDescription();
            data[i][4] =dataSpc.get(i).getFunction();
            //data[i][5] ="Update ("+dataSpc.get(i).getUsername().getUsername()+")";
            data[i][5] = UpdateSetter(dataSpc.get(i).getUsername().getUsername());
                    
                    System.out.println("Called ");
        }

  
        // Column Names 
         System.out.println("Called vxnfhjtukuyl");
        
        
        silTable.setModel(new DefaultTableModel(data, columnNames));
        ButtonColumn buttonColumn = new ButtonColumn(silTable, delete, 5);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
   
   
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

