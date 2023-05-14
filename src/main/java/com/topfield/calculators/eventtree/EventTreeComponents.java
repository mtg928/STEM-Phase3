/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;


import com.topfield.dao.DatareferDao;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.EventTreeDao;
import com.topfield.dao.EventTreeHdrDao;
import com.topfield.dao.HazidDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SILDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.dao.SubProductFunctionsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.EventTreeDaoImpl;
import com.topfield.daoImpl.EventTreeHdrDaoImpl;
import com.topfield.daoImpl.HazidDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SILDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.daoImpl.SubProductFunctionsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Datarefer;
import com.topfield.modal.Eventtreehdr;
import com.topfield.modal.Eventtree;
import com.topfield.modal.Hazid;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Sil;
import com.topfield.modal.Spfhdr;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.utilities.NumberConversion;
import com.topfield.view.popup.AddEvent;
import com.topfield.view.popup.AddEventPopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali  FaulttreeComponents
 */
public class EventTreeComponents extends JPanel {
    private EventTreeDao eventDao  = new EventTreeDaoImpl();
    private EventTreeHdrDao eventHdrDao  = new EventTreeHdrDaoImpl();
    private List<Eventtreehdr> dataEventTreeHdr = eventHdrDao.getAllEventTreeHdrByUserAndPro(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo());
    private EventTreeCalculator parent;
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    private HazidDao hazidDao = new HazidDaoImpl();
    private JTable eventTable; 
    private DatareferDao refDao = new DatareferDaoImpl();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private String[] funtions;
    //private AddEventPopup addEvent4556;
    

    public EventTreeComponents(EventTreeCalculator parent) {
     setLayout(new BorderLayout());
      this.parent = parent;  

        String[] columnNames = {"No","Id","Event Name","Event type","Reference Type","Initiating Event","Intial Value","Unit","Hazard",""}; 
        String[][] data = new String[dataEventTreeHdr.size()][columnNames.length+1];

        for (int i = 0; i < dataEventTreeHdr.size(); i++) {
            
            data[i][0] = (i+1)+"";
            data[i][1] = dataEventTreeHdr.get(i).getEventhdrId()+"";
            data[i][2] = dataEventTreeHdr.get(i).getEventName();
            data[i][3] = dataEventTreeHdr.get(i).getEventType();
            data[i][4] = dataEventTreeHdr.get(i).getComType();
            data[i][5] =  getReferenceIET(dataEventTreeHdr.get(i).getComType(),dataEventTreeHdr.get(i).getComRef());
            data[i][6] = dataEventTreeHdr.get(i).getIntialProbs()+"";
            data[i][7] = dataEventTreeHdr.get(i).getUnits();
            data[i][8] = hazidDao.findById(dataEventTreeHdr.get(i).getHazidRef()).getDescription();
            //data[i][9] ="View Data";
            data[i][9] ="View Event Tree";
            
        }
        
        
        
        
        
        PanelButton addButton = new PanelButton("Add Event");
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            Object[] options1 = { "ADD","Cancel"};
            AddEventPopup addEvent = new AddEventPopup();
                  
                try {
                    
                    int result = JOptionPane.showOptionDialog(null, addEvent, "Fill event details",
                           JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                           null, options1, null);

                     
                         if (result == JOptionPane.YES_OPTION) {
                             
                          InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));  
                          
                             if (addEvent.getSelectIET() != null) {
                                 
                             
                          
                                Eventtreehdr eventtreehdr = new Eventtreehdr();
                                eventtreehdr.setEventName(addEvent.getEventName());
                                eventtreehdr.setEventType(addEvent.getEventType());
                                eventtreehdr.setComType(addEvent.getReferenceType());
                                eventtreehdr.setComRef(((Item)addEvent.getSelectIET()).getId());
                                eventtreehdr.setIntialProbs(NumberConversion.NVLDouble(addEvent.getInitialFrequency(), 0));
                                eventtreehdr.setUnits(addEvent.getUnits());
                                eventtreehdr.setHazidRef(((Item)addEvent.getHazid()).getId());
                                eventtreehdr.setComments(addEvent.getComments());
                                eventtreehdr.setUsername(UserInfo.getInstance().getuser());
                                eventtreehdr.setCreateddate(UserInfo.getInstance().getDate());
                                eventtreehdr.setProjectId(UserInfo.getInstance().getTclProject());

                                eventHdrDao.Save(eventtreehdr);

                                parent.refreshPage();
                                
                             } else {
                                  JOptionPane.showMessageDialog(null,"Please select refernce and Try Again..");  
                             }

                         } else if (result == JOptionPane.NO_OPTION) { 

                         } else {
                           //  System.out.println("Cancelled");
                         }
                         
                
                } catch (NumberFormatException e) {
                    
                      JOptionPane.showMessageDialog(null,"Invalid Initial Frequency ");  
                      
                    
                }catch (Exception e1) {
                    
                }finally{
                
                  InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                }
  
                   
                  //  JOptionPane.showMessageDialog(null, "Please add your details now ");      
          }
        });
        
        PanelButton editButton = new PanelButton("Edit Event");
        //removeButton.setBackground(new Color(214, 219, 223));
        editButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = eventTable.getSelectedRow();
            Object[] options1 = { "Update","Cancel"};
            Datarefer refData;
            Spfhdr spfhdr;
            Hazid hazid;
            
               if(selRow >= 0){
                   
                   Eventtreehdr eventtreehdr = dataEventTreeHdr.get(selRow);
                   AddEventPopup addEvent = new AddEventPopup()/* getPopup()*/;
                   addEvent.setEventName(eventtreehdr.getEventName());
                   addEvent.setEventType(eventtreehdr.getEventType());
                   addEvent.setReferenceType(eventtreehdr.getComType());
                   addEvent.setUnits(eventtreehdr.getUnits());
                   
                   
                   if (eventtreehdr.getComType().contentEquals("Internal FTA")) {
                        spfhdr = spfhdrDao.findById(eventtreehdr.getComRef());
                         addEvent.setSelectIET(new Item(spfhdr.getSpfhdrid(), spfhdr.getDescriptions()));
                   } else {
                        refData = refDao.findById(eventtreehdr.getComRef());
                        addEvent.setSelectIET(new Item(refData.getRefid(), refData.getDescription()));  
                   }
                   
                   addEvent.setInitialFrequency(eventtreehdr.getIntialProbs()+"");
                   hazid = hazidDao.findById(eventtreehdr.getHazidRef());
                   addEvent.setHazid(new Item(hazid.getHazidId(), hazid.getDescription()));
                   addEvent.setComments(eventtreehdr.getComments());

                    try {

                        int result = JOptionPane.showOptionDialog(null, addEvent, "Fill event details",
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options1, null);


                             if (result == JOptionPane.YES_OPTION) {

                              InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));  

                              
                              eventtreehdr.setEventName(addEvent.getEventName());
                              eventtreehdr.setEventType(addEvent.getEventType());
                              eventtreehdr.setComType(addEvent.getReferenceType());
                              eventtreehdr.setComRef(((Item)addEvent.getSelectIET()).getId());
                              eventtreehdr.setIntialProbs(NumberConversion.NVLDouble(addEvent.getInitialFrequency(), 0));
                              eventtreehdr.setUnits(addEvent.getUnits());
                              eventtreehdr.setHazidRef(((Item)addEvent.getHazid()).getId());
                              eventtreehdr.setComments(addEvent.getComments());
                              eventtreehdr.setUsername(UserInfo.getInstance().getuser());
                              eventtreehdr.setCreateddate(UserInfo.getInstance().getDate());
                              eventtreehdr.setProjectId(UserInfo.getInstance().getTclProject());

                              eventHdrDao.Update(eventtreehdr);

                              parent.refreshPage();

                             } else if (result == JOptionPane.NO_OPTION) { 

                             } else {
                               //  System.out.println("Cancelled");
                             }


                    } catch (NumberFormatException e) {

                          JOptionPane.showMessageDialog(null,"Invalid Initial Frequency ");  


                    }catch (Exception e1) {

                    }finally{

                      InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                    }
                   
                   
                   
                   
                   

                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }
            
              
            //model.removeRow(table.getSelectedRow());
          //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
          //  refreshPage();
          }
        });

        PanelButton removeButton = new PanelButton("Remove Event");
        //removeButton.setBackground(new Color(214, 219, 223));
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = eventTable.getSelectedRow();
            
               if(selRow >= 0){

                  if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete? \n "
                            + "The operation will delete the record and the functions and components associated with it in the calculation (second) tab", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                  
                   System.out.println(eventTable.getValueAt(selRow, 3)+" "+eventTable.getValueAt(selRow, 2));
                   
                   eventHdrDao.Remove(dataEventTreeHdr.get(selRow).getEventhdrId());
                  //eventDao.RemoveAllEventtreeByTypeUserProj(eventTable.getValueAt(selRow, 3)+"", eventTable.getValueAt(selRow, 2)+"", UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId());
                  parent.refreshPage();
                  
                    }

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
        



  
        // Column Names 
        
        
        eventTable = new JTable(data, columnNames); 
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               int compId = Integer.parseInt(e.getActionCommand());
               
                System.out.println(eventTable.getSelectedColumn());
                
                   /*if (eventTable.getSelectedColumn() == 9) {
                    
                       System.out.println("kdjgk - "+eventTable.getValueAt(compId, 1));
                     parent.setSelectedIndex(1);  //eventTable.getValueAt(compId, 4)+"")
                     //parent.setComponentAt(1, new EventTreeData(parent,eventDao.findById(Integer.parseInt(eventTable.getValueAt(compId, 1)+""))));
                       parent.setComponentAt(1, new EventTreeData(parent,dataEventTreeHdr.get(compId)));
                   } else*/ if(eventTable.getSelectedColumn() == 9) {
                       
                      // System.out.println("fj,chgtjjhjhjfhjjvj"+eventTable.getValueAt(compId, 3)+"");
                     
                      
                     //parent.remove(1);
                     //parent.remove(1);
                     //parent.setComponentAt(1, new EventTreeData(parent,eventTable.getValueAt(compId, 2)+"",eventTable.getValueAt(compId, 3)+""));
                     //parent.add("Event Tree Data",new EventTreeData(parent,eventDao.findById(Integer.parseInt(eventTable.getValueAt(compId, 1)+""))));
                    //parent.add("Event Tree Data",new EventTreeData(parent,dataEventTreeHdr.get(compId)));
                     //parent.add("EventTree",new EventTreeCal(parent));
                     //parent.setComponentAt(2, new EventTreeCal(parent));
                     parent.setComponentAt(1,new EventTreeGraph(parent, dataEventTreeHdr.get(compId)));
                     parent.setSelectedIndex(1);  //eventTable.getValueAt(compId, 4)+"")
                       
                   }

                    
            }


        };
        
        for (int i = 0; i < eventTable.getColumnCount(); i++) {
            
            eventTable.setDefaultRenderer(eventTable.getColumnClass(i), new WineCellRenderer());
            
            
        }
        

     
       /* ButtonColumn buttonColumn = new ButtonColumn(eventTable, delete, 9);
        buttonColumn.setMnemonic(KeyEvent.VK_D);*/
        
        ButtonColumn buttonColumn1 = new ButtonColumn(eventTable, delete, 9);
        buttonColumn1.setMnemonic(KeyEvent.VK_D);
  
        add(buttonPanel,BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(eventTable),BorderLayout.CENTER); 
        
         eventTable.getTableHeader().setOpaque(true);
         eventTable.setOpaque(true);
         eventTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         eventTable.getTableHeader().setFont(eventTable.getTableHeader().getFont().deriveFont(Font.BOLD));
         eventTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         eventTable.setGridColor(Color.LIGHT_GRAY);
        
    }
    
    
   
     
     
     public String getReferenceIET(String refType,int id){
      String res="";
      Datarefer refData;
      Spfhdr spfhdr;
     
         try {
             
          if (refType.contentEquals("Internal FTA")) {
              spfhdr = spfhdrDao.findById(id);
              res =spfhdr.getDescriptions();
           } else {
               refData = refDao.findById(id);
               res = refData.getDescription();  
           }
          
         } finally  {
             
         }

      return res;
     }
     
     
  /*   public AddEventPopup getPopup(){
     
         if (addEvent4556 == null) {
             addEvent4556 = new AddEventPopup();
         }
     
         return addEvent4556;
     }*/

}
