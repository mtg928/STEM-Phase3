/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmeca;

import com.topfield.utilities.ColumnGroup;
import com.topfield.utilities.CopyAction;
import com.topfield.utilities.ExcelExporter;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.TableColumnHider;
import com.topfield.utilities.TclSession;
import com.google.common.collect.ObjectArrays;
import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.utilities.ExcelAdapter;
import com.topfield.utilities.PasteAction;
import com.topfield.utilities.TableMouseListener;
import com.topfield.utilities.ToolTipHeader;
import com.topfield.user.UserInfo;
import com.topfield.controller.ComponentFunctions;
import com.topfield.controller.Equations;
import com.topfield.dao.FMECADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.SubProductGroup;
import com.topfield.project.ProjectDefinition;
import com.topfield.project.ShowSelectedItemsAsList;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.themes.DataEntryPopup;
import com.topfield.themes.TopPanelButton;
import com.topfield.utilities.FMEACellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field ;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;


/**
 *  
 * @author Murali
 */
public class FMECA extends JPanel implements ActionListener {
    
    private FMECADao fMECADao = new FMECADaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FMECACrud fMECACrud = new FMECACrud();
    
    
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private  SubProductGroupDao subDao = new SubProductGroupDaoImpl();
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private Equations equations = new Equations();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private Calchdr mpghdr;
    private ExcelAdapter myAd;
    private JTable footer;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemAddYourCom;
    private JMenuItem menuItemAddAllCom;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemCalculate;
    private JMenuItem menuItemfaliureSwith;
    private JMenuItem menuItemTemplate;
    private DefaultTableModel model = new DefaultTableModel();
    private JTable jtFmea = new JTable(model){
      protected JTableHeader createDefaultTableHeader() {
          return new GroupableTableHeader(columnModel);
      }};

    
    public FMECA(Calchdr fmeas) {
        
        setLayout(new BorderLayout());
        

        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE EFFECTS AND CRITICALITY ANALYSIS (FMECA) - "+fmeas.getMpghdr().getFmeaComponent().getMpgDescription()+" </p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);
        
        this.mpghdr = fmeas;
         myAd = new ExcelAdapter(jtFmea);
        fMEAServices.setCopyPasteFunction(jtFmea,myAd);
        fMECACrud.refreshTableData(jtFmea,mpghdr.getCalchdrid());
        setupRightClick();

        
        /*Color ivory = new Color(255, 255, 208);
        jtFmea.setBackground(ivory);*/
        
        jtFmea.addMouseListener(new java.awt.event.MouseAdapter(){

          public void mouseClicked(java.awt.event.MouseEvent e){  //mouseEntered
            if (e.getButton() == MouseEvent.BUTTON1){
                //jtFmea.setColumnSelectionAllowed(false);
              //jtFmea.setRowSelectionAllowed(true);
              jtFmea.setCellSelectionEnabled(true);
            
              try {
                  int row=jtFmea.rowAtPoint(e.getPoint());
                  int col= jtFmea.columnAtPoint(e.getPoint());
            //JOptionPane.showMessageDialog(null,"Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
            //System.out.println("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());

            //fMECACrud.addColumnData(jtFmea,col,row);
             //footertex.setText("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
             //footertex.setToolTipText("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
             //jtFmea.setToolTipText("<html> <textarea name=\"Text1\" cols=\"40\" rows=\"5\" disabled=\"true\" >"+jtFmea.getValueAt(row,col).toString()+"</textarea> </html>");
            jtFmea.setToolTipText(jtFmea.getValueAt(row,col).toString());
                  
              } catch (Exception rowError) {
                  
              }
              
            }
           }
          
          @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1){
                   // System.out.println("Left button clicked");
                } else if (arg0.getButton() == MouseEvent.BUTTON2){
                  //  System.out.println("Middle button clicked");
                } else if (arg0.getButton() == MouseEvent.BUTTON3) {
                  //  System.out.println("Right button clicked");
                } 
            }
            
            
          
          
          
         }
        );

        //jtFmea.setPreferredScrollableViewportSize(new Dimension(2000,63));
        //jtFmea.setFillsViewportHeight(true);
        
        
        JTableHeader header = jtFmea.getTableHeader();
        header.setBackground(FrameSettings.getButtonColor());
        header.setForeground(Color.WHITE);
        header.setFont(new Font ("Serif", Font.BOLD, 14));
        header.setOpaque(true);
        //header.setPreferredSize(new Dimension(6000,50));
        // jtFmea.setPreferredSize(new Dimension(6000,jtFmea.getPreferredSize().height ));
        
       
 
        
        //JPanel container = new JPanel(new BorderLayout());
        // Add header in NORTH slot
        //container.add(jtFmea.getTableHeader(), BorderLayout.NORTH);
        // Add table itself to CENTER slot
        JScroll center=new JScroll(jtFmea);
        jtFmea.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        //container.add(center, BorderLayout.CENTER);
        //resizeColumnWidth(jtFmea);
        
        JScrollPane foot =new JScrollPane(new footerInfo());
       
        
        
                footer = new JTable(1,12);
                footer.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
                JPanel holdingPanel = new JPanel(new BorderLayout());
               // JScrollPane sp = new JScrollPane(table);
               // sp.setPreferredSize(new Dimension(300,200));
                holdingPanel.add(center,BorderLayout.CENTER);
                holdingPanel.add(footer,BorderLayout.SOUTH);
        

        
        add(caption, BorderLayout.PAGE_START);
        //add(new JScrollPane(new footerInfo()), BorderLayout.PAGE_END);
        add(holdingPanel, BorderLayout.CENTER);
        
        
        setFoooterData();
        
    }
    
    
     public void setInfoFooter(String heading,double value,int col){
    
        footer.setValueAt(heading, 0, col);
        footer.setValueAt(value, 0, col+1);
        
        TableColumnModel columnModel = footer.getColumnModel();
        TableColumn column = columnModel.getColumn(col+1); 
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        column.setCellRenderer(renderer);

        // right align 2nd column when editing
        JTextField tf = new JTextField();
        renderer.setToolTipText(value+"");
        tf.setHorizontalAlignment(JTextField.RIGHT);
        column.setCellEditor(new DefaultCellEditor(tf));
    
    }
     
    public void setFoooterData(){
        setInfoFooter("Row Counts",jtFmea.getRowCount(), 0);
        setInfoFooter("Cm",getTotalCriticalityMode(), 10);
    
    }
    
    public double getTotalCriticalityMode(){
     double res=0.0;
     
        for (int i = 0; i < jtFmea.getRowCount(); i++) {
            if(jtFmea.getValueAt(i, 17)!= null && !(jtFmea.getValueAt(i, 17).equals("")) ){
                try {
                     res= res +Double.parseDouble(jtFmea.getValueAt(i,17)+"");
                } catch (Exception e) {
                }
  
             
               // System.out.println(" value "+ jtFmea.getValueAt(i,24));
            }
        }
        
     return res;
    }
    
    public double getTotalCriticalityItem(){
     double res=0.0;
     
        for (int i = 0; i < jtFmea.getRowCount(); i++) {
            if(jtFmea.getValueAt(i, 17)!= null && !(jtFmea.getValueAt(i, 17).equals("")) ){
                try {
                     res= res +Double.parseDouble(jtFmea.getValueAt(i,17)+"");
                } catch (Exception e) {
                }
  
             
               // System.out.println(" value "+ jtFmea.getValueAt(i,24));
            }
        }
        
     return res;
    }
      
    
 

 
 
 
 
 
 public void setupRightClick(){
        

    
         // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+P");
        menuItemAdd = new JMenuItem("Add Signle Row");
        menuItemAddYourCom = new JMenuItem("Edit selected row");  //"Add Your Components"
        menuItemAddAllCom = new JMenuItem("Generate FMECA");  //Add All Components
        menuItemSave = new JMenuItem("Save All");
        menuItemRemove = new JMenuItem("Delete Selected Row");
        menuItemCalculate = new JMenuItem("Calculate RPZ");
        menuItemfaliureSwith = new JMenuItem("Show Failure mode criticality");
        menuItemTemplate = new JMenuItem("Download file format template");
         
        menuItemCopy.addActionListener(this);
        menuItemPaste.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemAddYourCom.addActionListener(this);
        menuItemAddAllCom.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemCalculate.addActionListener(this);
        menuItemfaliureSwith.addActionListener(this);
        menuItemTemplate.addActionListener(this);
         
        popupMenu.add(menuItemCopy);
        popupMenu.add(menuItemPaste);
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemAddYourCom);
        popupMenu.add(menuItemAddAllCom);
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemCalculate);
        //popupMenu.add(menuItemfaliureSwith);
        popupMenu.add(menuItemTemplate);
         
        // sets the popup menu for the table
        jtFmea.setComponentPopupMenu(popupMenu);
         
        jtFmea.addMouseListener(new TableMouseListener(jtFmea));
       // jtFmea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
         for (int i = 0; i < jtFmea.getColumnCount(); i++) {
            
            jtFmea.setDefaultRenderer(jtFmea.getColumnClass(i), new FMEACellRenderer());

        }
    }

 @Override
public void actionPerformed(ActionEvent e) {
    JMenuItem menu = (JMenuItem) e.getSource();
       // jtFmea.setColumnSelectionAllowed(false);
      //  jtFmea.setRowSelectionAllowed(true);
      
  
        
        if (menu == menuItemAdd) {
            //JOptionPane.showMessageDialog(null, "Your data save successfully "+menu.getName());
            fMECACrud.itemAdd(jtFmea,mpghdr);
        } else if (menu == menuItemAddYourCom) {
            //fMECACrud.itemAddYourComp(jtFmea,mpghdr.getMpghdrid());
            fMECACrud.itemEdit(jtFmea, mpghdr);
        }else if (menu == menuItemAddAllCom) {
            //fMECACrud.itemAddAllComp(jtFmea, mpghdr.getMpghdrid());
            
             new Thread() { 
                public void run() {
                     fMEAServices.itemLoad(jtFmea,mpghdr,fMECACrud.getTableHeadingString(),"FMECA",fMECACrud);
                }
            }.start();
            
            
        }else if (menu == menuItemSave) {
            fMECACrud.itemSave(jtFmea,mpghdr);
        } else if (menu == menuItemRemove) {
            fMECACrud.itemRemove(jtFmea);
        }else if (menu == menuItemCopy) {
            //new CopyAction(jtFmea,myAd);
            myAd.copyFuntion();
            //JOptionPane.showMessageDialog(null, "Copy "+menu.getName());
        }else if (menu == menuItemPaste) {
            //new PasteAction(jtFmea,myAd);
            myAd.pasteFuntion();
            //JOptionPane.showMessageDialog(null, "Paste "+menu.getName());
        }else if (menu == menuItemCalculate) {
            itemCalculateCriticality();
        }else if (menu == menuItemfaliureSwith) {
            itemfaliureSwith();
        }else if (menu == menuItemTemplate) {
             getFMEATableDataTemlate();
        }
        
        setFoooterData();
}

 
    
   



   





    private void itemCalculateCriticality() {
       InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR)); 
              for (int i = 0; i < model.getRowCount(); i++) {
                  
                  
              try {
              //int res = NumberConversion.NVL(model.getValueAt(i,13)+"", 0)*NumberConversion.NVL(model.getValueAt(i,14)+"", 0)*NumberConversion.NVL(model.getValueAt(i,16)+"", 0);
              ;
              Double res = equations.Criticality_Mode_Number(NumberConversion.NVLDouble( model.getValueAt(i,13)+"",0), NumberConversion.NVLDouble( model.getValueAt(i,14)+"",0), NumberConversion.NVLDouble( model.getValueAt(i,15)+"",0),NumberConversion.NVLDouble( model.getValueAt(i,16)+"",0));
              model.setValueAt(res, i, 17);
                  
              } catch (NumberFormatException e2) {
                  model.setValueAt(0, i, 17);
              } catch(ArrayStoreException e3){
                  model.setValueAt(0, i, 17);
              }
                  
              }
 
              
              
              
              for (int i = 0; i < model.getRowCount(); i++) {
                  
              /*    
              try {
                  double res =equations.Criticality_Number_item(NumberConversion.NVLDouble( model.getValueAt(i,17)+"",0),NumberConversion.StringToIntArray(mpghdr.getFmeaComponent().getCriModePro().split("\\#")));
              //int res = Integer.parseInt(model.getValueAt(i,21)+"")*Integer.parseInt(model.getValueAt(i,22)+"")*Integer.parseInt(model.getValueAt(i,23)+"");
              model.setValueAt(res, i, 18);
                  
              } catch (NumberFormatException e2) {
                  model.setValueAt(0, i, 18);
              } catch(ArrayStoreException e3){
                  model.setValueAt(0, i, 18);
              }*/
                  
              }
                InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
              
              /*
                               Frame frame = null;
            // display/center the jdialog when the button is pressed
            JDialog d = new JDialog(frame, "Hello "+jtFmea.getValueAt(1,13), true);
            d.setLocationRelativeTo(frame);
            d.setVisible(true);
              
              
              */
              
              jtFmea.changeSelection(0, jtFmea.getColumnCount()-1, false, false);
              
              jtFmea.setColumnSelectionAllowed(true);
              jtFmea.setRowSelectionAllowed(false);

                // Select a column - column 0
                jtFmea.setColumnSelectionInterval(17, 17);

    
              
           setFoooterData();
    }

    private void itemfaliureSwith() {
        //menuItemfaliureSwith.setTips("Failure mode criticality");
         final TableColumnHider hider = new TableColumnHider(jtFmea);
         hider.show("Failure Mode Criticality(Cm)");
         hider.show("Failure Item Criticality(Cr)");
               if(menuItemfaliureSwith.getText().equals("Show Failure item criticality")){
                   //menuItemfaliureSwith.setTips("Failure item criticality");
                   menuItemfaliureSwith.setText("Show Failure mode criticality");
                   hider.show("Failure Item Criticality(Cr)");
                   hider.hide("Failure Mode Criticality(Cm)");
                    JOptionPane.showMessageDialog(null, "Your data save successfully");
                  // setInfoFooter("Cr",getTotalCriticalityItem(), 10);
               }else{
               
                 menuItemfaliureSwith.setText("Show Failure item criticality");
                 //menuItemfaliureSwith.setTips("Failure mode criticality");
                   hider.show("Failure Mode Criticality(Cm)");
                   hider.hide("Failure Item Criticality(Cr)");
                  // setInfoFooter("Cm",getTotalCriticalityMode(), 10);

               }
    }



public class footerInfo extends JPanel{
    
    public footerInfo(){
        
        try{
        setLayout(new FlowLayout( FlowLayout.LEFT));
        
 
        JComboBox comboBox = new JComboBox();
        comboBox.setPreferredSize(new Dimension(250, 25));
           // comboBox.setMaximumRowCount(3);
            //comboBox.setPrototypeDisplayValue(" None of the above ");
       comboBox.addActionListener(e -> {
              JComboBox c = (JComboBox) e.getSource();
              Item item = (Item) c.getSelectedItem();
             // MpgId =item.getId();
            //  System.out.println(item.getId() + " : " + item.getDescription());
              
              fMECACrud.refreshTableData(jtFmea,mpghdr.getCalchdrid());
              setFoooterData();
              //JButton button3 = new JButton("Long-Named Button 4 (PAGE_END)");
  
              
              
         });
        /*    
        for (MainProductGroup mainCom : mainProDao.getAllMPG()) {
            comboBox.addItem(new Item(mainCom.getMpgId(), mainCom.getMpgDescription()));
            //System.out.println(""+mainCom.getMpgDescription());
        }
            
       
        
         TopPanelButton calculate = new TopPanelButton("Calculate Criticality");
         calculate.setTips("Calculate Criticality");
         calculate.setBackground(new Color(107, 106, 104));
         calculate.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              
          }
          
        });
         
         TopPanelButton save = new TopPanelButton("Save");
         save.setTips("Save the Changes");
         save.setBackground(new Color(107, 106, 104));
         save.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
              

                

          }
        });
         
        TopPanelButton add = new TopPanelButton("Add");
        add.setTips("Add Row");
        add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

              
            }
         
         
         });
         
         TopPanelButton delete = new TopPanelButton("Delete");
         delete.setTips("Delete Row");
         delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                

            }
             
         });
         
         TopPanelButton importTem = new TopPanelButton("Download Template");
         importTem.setTips("Download Import file template");
         importTem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               getFMEATableDataTemlate();
            }
         });
         
        TopPanelButton addComponents = new TopPanelButton("Add Your Components");
         addComponents.setTips("Add Your Components");
         addComponents.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    
            }
         });
         
         TopPanelButton addOtherComponents = new TopPanelButton("Add other Components");
         addOtherComponents.setTips("Add other Components");
         addOtherComponents.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
                   
            }
         });
         
         TopPanelButton hideShow = new TopPanelButton("FMC");
         
         hideShow.addActionListener(new ActionListener(){
            @Override  //"Failure Mode Criticality(Cm)","Failure Item Criticality (Cr)"
            public void actionPerformed(ActionEvent e) {

                   
                          //  JOptionPane.showMessageDialog(null, "Please add your details now ");
  
                
                
                
            }
         });
        
         add(comboBox);
         add(add);
         add(save);
         add(calculate);
         add(delete);
         add(importTem);
         add(addComponents);
         add(addOtherComponents);
         add(hideShow);
         */
        }catch(Exception e){
        
        }finally{
          InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    
    }
    
    
    
} 

class ItemRenderer extends BasicComboBoxRenderer {
  @Override
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);
    if (value != null) {
      Item item = (Item) value;
      setText(item.getDescription().toUpperCase());
    }
    if (index == -1) {
      Item item = (Item) value;
      setText("" + item.getId());
    }
    return this;
  }
}
class Item {

  private int id;
  private String description;

  public Item(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return description;
  }
}


public void refreshTable(DefaultTableModel dm){
 int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
for (int i = rowCount - 1; i >= 0; i--) {
    dm.removeRow(i);
}

}




public Object[][] getFMEATableData(){
 /*Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
                    };*/
Object[] topcolumnNames = {"Failure Identification","","","","","","Failure Effects",
                                 "","","","Detection and Recovery Measures","",
                                 "Failure Effect ","","","","","",""};
  
  Object[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Failure detection","Preventive and compensating measures","Failure Effect Probability(?)","Failure Mode Ratio(?)","Failure Rate(?p)","Operating Time(t)","Failure Mode Criticality(Cm)","Failure Item Criticality(Cr)","Created Time"};
  Object[][] tableData = fMECACrud.refreshTableData(jtFmea,mpghdr.getCalchdrid());
          
   Object[][] datatypes = new Object[tableData.length+2][columnNames.length];
       //System.out.println("tableData.length "+tableData.length);
    
    datatypes[0] =topcolumnNames;    
    datatypes[1] =columnNames; 
    Object[] temp;
    for (int i = 0; i < tableData.length; i++) {
       temp = ArrayUtils.removeElement(tableData[i], tableData[i][0]);
        datatypes[i+2]= temp;
    }
   
       
 return datatypes;
}

public void getFMEATableDataTemlate(){
 /*Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", em},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
                    };*/
 Object[] topcolumnNames = {"Failure Identification","","","","","","Failure Effects",
                                 "","","","Detection and Recovery Measures","",
                                 "Failure Effect ","","","","","",""};
  
  Object[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Failure detection","Preventive and compensating measures","Failure Effect Probability(?)","Failure Mode Ratio(?)","Failure Rate(?p)","Operating Time(t)","Failure Mode Criticality(Cm)","Failure Item Criticality(Cr)","Created Time"};
  Object[][] tableData = fMECACrud.refreshTableData(jtFmea,mpghdr.getCalchdrid());
          
   Object[][] datatypes = new Object[tableData.length+2][columnNames.length];
       //System.out.println("tableData.length "+tableData.length);
    
    datatypes[0] =topcolumnNames;    
    datatypes[1] =columnNames; 
 /*   Object[] temp;
    for (int i = 0; i < tableData.length; i++) {
       temp = ArrayUtils.removeElement(tableData[i], tableData[i][0]);
        datatypes[i+2]= temp;
    }*/
   
       
 ExcelExporter e = new ExcelExporter();
  e.WriteExcel("FMECA_Template",datatypes);
}



public void progressImportSetup(Object[][] tableData){
     
 
     
      new Thread() { 
            public void run() {
                
                if (fMEAServices.checkExistFmeaData(jtFmea)==1) {
                   if (fMEAServices.infoFileFormat()==1) {
                       if (fMEAServices.validateImport(tableData)==1) {
                           fMEAServices.setFMEATableDataInstanly(jtFmea,tableData,fMEAServices.productSepration(tableData),(fMECACrud.getTableHeadingString().length-2));
                             
                            //fMECACrud.itemSave(jtFmea,mpghdr);

                             //setFMEATableDataInstanly(tableData,fMEAServices.productSepration(tableData));
                             //c.setFMEATableData(tableData);
                              //importSave();
                             //c.refreshTableData();
                        }
                }
                }
                
                
            }
        }.start();
     
 
 }

public void setFMEATableData(Object[][] tableData){

Object[] first = new Object[1]; 
//first[0] =1;
    
Object[][] result = new Object[tableData.length][];


    for (int i = 0; i < tableData.length; i++) {
        result[i] =ArrayUtils.addAll(first, tableData[i]);
    }



    model = new DefaultTableModel(result,fMECACrud.getTableHeadingString());
    jtFmea.setModel(model);
    fMECACrud.setGroupheading(jtFmea);
}


public void importSave(){

           JFrame parent = InternalFrameDemo.mainFrame;    
           
           if (jtFmea.isEditing()) {
            // Get the current editor
            TableCellEditor editor = jtFmea.getCellEditor();
            if (editor != null) {
                // Try and stop the cell editing process
                if (!editor.stopCellEditing()) {
                    // Cancel the editing if can't be stopped...
                    // You could handle an error state here instead...
                    editor.cancelCellEditing();
                }
            }
        }
              
              
             if(model.getRowCount()>0){ 
               parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
              for (int i = 0; i < model.getRowCount(); i++) {
                  Fmeca fmeca = fMECACrud.getFMECAByRowId(model,i,mpghdr);
                  fmeca.setFmecaId(null);
                  if(fmeca.getFISubsysComp() != null && !(fmeca.getFISubsysComp().equals(""))){
                   fMECADao.saveFMECA(fmeca);
                  }
              }
              
               parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              
              
              JOptionPane.showMessageDialog(null, "Your data save successfully");
             }else {
              JOptionPane.showMessageDialog(null, "No data to save");
              
             }


}



    
    
    

    
     




    
}

