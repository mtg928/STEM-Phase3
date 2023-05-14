/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;


import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.EventTreeDao;
import com.topfield.daoImpl.EventTreeDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Eventtreehdr;
import com.topfield.modal.Eventtree;
import com.topfield.settings.ComponentPrinter;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.view.popup.AddEvent;
import com.topfield.view.popup.MyDialogPopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */

public class EventTreeData extends JPanel {
  private DefaultTableModel model;
  private JTable eventTreeTable; 
  private JTextField probability = new JTextField();
  private EventTreeCalculator parent;
  private EventTreeDao eventDao  = new EventTreeDaoImpl();
  private List<Eventtree> dataEventTree;
  private Eventtreehdr head;

  public EventTreeData(EventTreeCalculator parent,Eventtreehdr head) {
    
     setLayout(new BorderLayout());
     
     this.head = head;
     this.parent = parent;
        
        JLabel label= new JLabel();
        label.setText("CCF of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial",Font.BOLD,20));
        String[] splitData;
       /* String[] columnNames = {"No","EventId","EventName","EventLevel","EventParent",
                                "True Probability","Child","Directions","" }; */
       
       String[] columnNames = {"No","EventId","EventLevel","EventName",
                                "True Probability"};
        
        //dataEventTree = eventDao.getAllEventtreeByTypeUserProj(head.getEventType(),head.getComments(),UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo());
         dataEventTree = eventDao.getAllEventsByEventHdr(head.getEventhdrId());
        
        String[][] data = new String[dataEventTree.size()][columnNames.length+1];
         for (int i = 0; i < dataEventTree.size(); i++) {         
            data[i][0] = (i+1)+"";
            data[i][1] = dataEventTree.get(i).getEventId()+"";
            data[i][2] = (dataEventTree.get(i).getEventLevel()+1)+"";
            data[i][3] = dataEventTree.get(i).getEventName();
            //data[i][4] = dataEventTree.get(i).getEventType()+"";
            //data[i][4] = dataEventTree.get(i).getEventParent()+"";
            data[i][4] = dataEventTree.get(i).getTrueProbs()+"";
            //data[i][7] = dataEventTree.get(i).getHazidRef()+"";
            //data[i][6] = dataEventTree.get(i).getChild()+"";
           // data[i][7] = dataEventTree.get(i).getDirections()+"";
            //data[i][10] = dataEventTree.get(i).getComments()+"";
            //data[i][5] ="Save";
        }
        
       
       // System.out.println("8888888888888888"+prob);
        
         PanelButton addButton = new PanelButton("Add Row");
       // addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
             int events = eventTreeTable.getRowCount();
             int eventNo=(events+1);
                   Object[] options = {"Programmable", "Non programmable"};
                   Object[] options1 = { "ADD","Cancel"};
                    
                    AddEvent addEvent = new AddEvent();
                  
                     try {
                    
                            MyDialogPopup dialog = new MyDialogPopup(events,"","");
                            dialog.setModal(true);
                            dialog.setVisible(true);
                             
                          InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));  
                          
                          if (!dialog.Data().equals("Invalid")) {
                             
                         
                          Eventtree eventtree = new Eventtree();
                          eventtree.setEventLevel(events); //eventNo
                          eventtree.setEventName(dialog.GetEventName());
                          eventtree.setComments(head.getComments());
                          eventtree.setEventType(head.getEventType());
                          eventtree.setEventParent((events));
                          eventtree.setTrueProbs(dialog.Data());
                          //eventtree.setHazidRef(head.getHazidRef());
                          eventtree.setChild("Yes");
                          eventtree.setDirections("Up");
                          eventtree.setEventHdr(head);
                          /*eventtree.setUser(UserInfo.getInstance().getuser());
                          eventtree.setCreateddate(UserInfo.getInstance().getDate());
                          eventtree.setProjectId(UserInfo.getInstance().getTclProject());*/
 
                          eventDao.Save(eventtree);
                          refreshPage();
                          
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

         
        PanelButton removeButton = new PanelButton("Remove Event");
        //removeButton.setBackground(new Color(214, 219, 223));
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            eventDao.remove(Integer.parseInt(eventTreeTable.getValueAt(eventTreeTable.getSelectedRow(), 1)+""));
            
             refreshPage();
          }
        });
        
        PanelButton refreashButton = new PanelButton("Refresh");
        //refreashButton.setBackground(new Color(214, 219, 223));
        refreashButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            refreshPage();
          }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        
        
        
    


  
        // Column Names 
        
        
        eventTreeTable = new JTable(data, columnNames); 
        label.setBorder(new EmptyBorder(10,10,20,0));
        
        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               //int compId = Integer.parseInt(eventTreeTable.getValueAt(Integer.parseInt(e.getActionCommand()), 1)+"");
                int compId = Integer.parseInt(e.getActionCommand()+"");
                try {
                    //eventDao.Update(getEventtreeFinal(compId));
                    // eventDao.remove(compId);
                    ComponentPrinter.printComponent(parent);
                } catch (PrinterException ex) {
                    Logger.getLogger(EventTreeData.class.getName()).log(Level.SEVERE, null, ex);
                }

            }


        };
        
        for (int i = 0; i < eventTreeTable.getColumnCount(); i++) {
            
            eventTreeTable.setDefaultRenderer(eventTreeTable.getColumnClass(i), new WineCellRenderer());
            
            
        }
        
        eventTreeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = eventTreeTable.rowAtPoint(evt.getPoint());
                int col = eventTreeTable.columnAtPoint(evt.getPoint());
                int result = 0;
                if (col ==2 || col ==3  ) {

                    dataEntryPopup(row);
                     
                }
                }
            
        });

        
        
        //ButtonColumn buttonColumn = new ButtonColumn(eventTreeTable, delete, 5);
        //buttonColumn.setMnemonic(KeyEvent.VK_D);
        
         eventTreeTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         eventTreeTable.getTableHeader().setFont(eventTreeTable.getTableHeader().getFont().deriveFont(Font.BOLD));
  
        //eventTreeTable.getColumnModel().getColumn(1).set
        add(buttonPanel,BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(eventTreeTable),BorderLayout.CENTER); 
        
        
    }
    
  
    public void dataEntryPopup(int row){
    
                    
     MyDialogPopup dialog = new MyDialogPopup(row,eventTreeTable.getValueAt(row, 3)+"",eventTreeTable.getValueAt(row, 4)+"");
     dialog.setModal(true);
     dialog.setVisible(true);
                    
                    if (!(dialog.Data().equals("Invalid") || dialog.Data().equals("") || dialog.Data()== null )) {
                        eventTreeTable.setValueAt(dialog.GetEventName(), row, 3);
                        eventTreeTable.setValueAt(dialog.Data(), row, 4);
                        eventDao.Update(getEventtreeFinal(row));
                    }
    }
  
   
    
     public Eventtree getEventtreeFinal(int rowId){
         
      //  JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint."+rowId);
     
               Eventtree eventtree = eventDao.findById(Integer.parseInt(eventTreeTable.getValueAt(rowId, 1)+""));
               eventtree.setEventName(eventTreeTable.getValueAt(rowId, 3)+"");
               //eventtree.setEventLevel(Integer.parseInt(eventTreeTable.getValueAt(rowId, 3)+""));
               //eventtree.setEventParent(Integer.parseInt(eventTreeTable.getValueAt(rowId, 4)+""));
               eventtree.setTrueProbs(eventTreeTable.getValueAt(rowId, 4)+"");
               //eventtree.setChild(eventTreeTable.getValueAt(rowId, 6)+"");
               //eventtree.setDirections(eventTreeTable.getValueAt(rowId, 7)+"");

    
    return eventtree;
    }   
     
     public Object [][] getAllDatas(){
  
      Object [][] data = new Object[eventTreeTable.getRowCount()][];
      Object [] dataCol ;
     // Vector data = model.getDataVector();

      for (int count = 0; count < eventTreeTable.getRowCount(); count++){
          dataCol = new Object[eventTreeTable.getColumnCount()];
          
          for (int col = 0; col < eventTreeTable.getColumnCount(); col++){
            //System.out.print(model.getValueAt(count, col).toString()+" - ");
            dataCol[col] = eventTreeTable.getValueAt(count, col);
          }
          
         // System.out.println("------------");
          data[count] = dataCol;
      }
      
    /*   for (Object[] objects : data) {
          
           for (Object object : objects) {
               
               System.out.print(" 1122232 "+object);
           }
           System.out.println("");
      }*/
      
  
    return data;
  }
     
 public double getIntialProbaVal(){
  /*Double val =0.00;
          
      try {
         val= Double.parseDouble(head.getTrueProbs());
        // val = head.
      } catch (Exception e) {
      }
      
  return val;*/
  
  return head.getIntialProbs();
  }
     
     
     public void refreshPage(){
         
        parent.setComponentAt(2,new EventTreeData(parent,head));
        parent.setSelectedIndex(2);
         
        /* MainFrame.contentPanel.removeAll();
         MainFrame.contentPanel.add(new EventTreeCalculator());
         MainFrame.contentPanel.revalidate();
         MainFrame.contentPanel.repaint();*/
     
     
     }
     
  
  
}
 
    
    
    
    
    
    

