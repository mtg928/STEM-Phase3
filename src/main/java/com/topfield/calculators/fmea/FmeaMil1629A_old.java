/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.google.gson.Gson;
import com.topfield.dao.CalchdrDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.info.FMEAImportInfo_old;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Fmea;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.utilities.ExcelAdapter;
import com.topfield.utilities.ExcelExporter;
import com.topfield.utilities.FMEACellRenderer;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.TableMouseListener;
import com.topfield.view.popup.ProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.lang3.ArrayUtils;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.CalchdrDaoImpl;
import com.topfield.modal.Calchdr;

/**
 *
 * @author Murali
 */
public class FmeaMil1629A_old extends JPanel implements ActionListener {
    
    private FMEADao fMEADao = new FMEADaoImpl();
    private MpgHdrDao fmeasDao = new MpgHdrDaoImpl();
    private CalchdrDao calchdrDao = new CalchdrDaoImpl();
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao subDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
    private FunctionalfailuresDao funDao = new FunctionalfailuresDaoImpl();
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FmeaCurd fmeaCurd= new FmeaCurd();
    private Calchdr fmeas;
    private JTable footer;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemEdit;
    private JMenuItem menuItemLoad;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemCalculate;
    private JMenuItem menuItemTemplate;
    private ExcelAdapter myAd;
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private DefaultTableModel model = new DefaultTableModel();
    JTable jtFmea = new JTable(model){
      protected JTableHeader createDefaultTableHeader() {
          return new GroupableTableHeader(columnModel);
      }
        @Override
        public boolean isCellEditable(int row, int column) {  
            if(column <=3){return false;} else{return true;}               
        };
     };
    //private JTable jtFmea;       
    //private JPanel footertex = new JPanel();
    
    
    public FmeaMil1629A_old(Calchdr fmeas) {
        
        setLayout(new BorderLayout());
        
        String comName = "";
        try {
            //comName =mainDao.FindById(mpgId).getMpgDescription();
            comName = fmeas.getMpghdr().getFmeaComponent().getMpgDescription();
        } catch (Exception e) {
        }
        
        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE & EFFECTS ANALYSIS (MIL-STD1629A)  - "+comName+"</p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);
        
        myAd = new ExcelAdapter(jtFmea);
        this.fmeas = fmeas;
        fmeaCurd.refreshTableData(jtFmea,fmeas,getTableHeadingString(),new int[]{8,12,20});
        setStandreds(fmeas.getStandard().getCode());      
        jtFmea.setCellSelectionEnabled(true);
        
        
        
        jtFmea.addMouseListener(new java.awt.event.MouseAdapter(){

          public void mouseClicked(java.awt.event.MouseEvent e){  //mouseEntered
              
              
              jtFmea.setCellSelectionEnabled(true);
              //jtFmea.setColumnSelectionAllowed(false);
             // jtFmea.setRowSelectionAllowed(true);
          /*  jtFmea.setCellSelectionEnabled(true);
              try {
                  int row=jtFmea.rowAtPoint(e.getPoint());
            int col= jtFmea.columnAtPoint(e.getPoint());
            //JOptionPane.showMessageDialog(null,"Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
            System.out.println("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());

            if((jtFmea.getValueAt(row,2)+"").length() > (UserInfo.getInstance().getProAbbreviations().length()+4)){
             addColumnData(col,row);
            }
            
             //footertex.setText("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
             //footertex.setToolTipText("Value in the cell clicked :"+" "+jtFmea.getValueAt(row,col).toString());
             //jtFmea.setToolTipText("<html> <textarea name=\"Text1\" cols=\"40\" rows=\"5\" disabled=\"true\" >"+jtFmea.getValueAt(row,col).toString()+"</textarea> </html>");
            jtFmea.setToolTipText(jtFmea.getValueAt(row,col).toString());
                  
              } catch (Exception rowError) {
                  
              }*/
              
            
           }
          
          
         }
        );


        
        JTableHeader header = jtFmea.getTableHeader();
        header.setBackground(FrameSettings.getButtonColor());
        header.setForeground(Color.WHITE);
        header.setFont(new Font ("Serif", Font.BOLD, 14));
       // header.setPreferredSize(new Dimension(2000,50));
        // jtFmea.setPreferredSize(new Dimension(6000,jtFmea.getPreferredSize().height ));

        JScroll center=new JScroll(jtFmea);
        jtFmea.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

        
                footer = new JTable(1,12);
                footer.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
                JPanel holdingPanel = new JPanel(new BorderLayout());
                holdingPanel.add(center,BorderLayout.CENTER);
                holdingPanel.add(footer,BorderLayout.SOUTH);

        add(caption, BorderLayout.PAGE_START);
        add(holdingPanel, BorderLayout.CENTER);
        
        setFoooterData();
        setupRightClick();
    }
      
    
    public void setupRightClick(){
        

    
         // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+P");
        menuItemAdd = new JMenuItem("Add Signle Row");
        menuItemSave = new JMenuItem("Save All");
        menuItemLoad = new JMenuItem("Generate FMEA");
        menuItemEdit = new JMenuItem("Edit Selected Row");
        menuItemRemove = new JMenuItem("Delete Selected Row");
        menuItemCalculate = new JMenuItem("Calculate Severity");
        menuItemTemplate = new JMenuItem("Download file format template");
         
        menuItemCopy.addActionListener(this);
        menuItemPaste.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemLoad.addActionListener(this);
        menuItemEdit.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemCalculate.addActionListener(this);
        menuItemTemplate.addActionListener(this);
         
        popupMenu.add(menuItemCopy);
        popupMenu.add(menuItemPaste);
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemLoad);
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemCalculate);
        popupMenu.add(menuItemTemplate);
         
        // sets the popup menu for the table
        jtFmea.setComponentPopupMenu(popupMenu);
         
        jtFmea.addMouseListener(new TableMouseListener(jtFmea));
       // jtFmea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       for (int i = 0; i < jtFmea.getColumnCount(); i++) {
            
            jtFmea.setDefaultRenderer(jtFmea.getColumnClass(i), new FMEACellRenderer());

        }
    }
    
    public void setInfoFooter(String heading,String value,int col){
    
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
        
        Gson gson = new Gson();
        String[] lang = {footer.getValueAt(0, 1)+"", footer.getValueAt(0, 11)+""};
        fmeas.setSummary(gson.toJson(lang));
        calchdrDao.Update(fmeas);
    
    }
    
    
    public void setFoooterData(){
        setInfoFooter("Row Counts",jtFmea.getRowCount()+"", 0);
        setInfoFooter("Severity class",getSeverity(), 10);
    
    }
    
    public String getSeverity(){
     String res="High";
     
        
     return res;
    }
    
    


 


  @Override
    public void actionPerformed(ActionEvent event) {
       JMenuItem menu = (JMenuItem) event.getSource();
       // jtFmea.setColumnSelectionAllowed(false);
      //  jtFmea.setRowSelectionAllowed(true);
      
        
        if (menu == menuItemAdd) {
            //JOptionPane.showMessageDialog(null, "Your data save successfully "+menu.getName());
            fmeaCurd.itemAdd(jtFmea,fmeas,getTableHeadingString(),new int[]{8,12,20});
        } else if (menu == menuItemSave) {
            fmeaCurd.itemSave(jtFmea,fmeas);
        }else if (menu == menuItemLoad) {
            new Thread() { 
                public void run() {
                    // fMEAServices.itemLoad(jtFmea,fmeas,getTableHeadingString(),"FMEA",fmeaCurd);
                    JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint.");  
                }
            }.start();
           
        } else if (menu == menuItemEdit) {
            fmeaCurd.itemEdit(jtFmea,fmeas);
        }else if (menu == menuItemRemove) {
            fmeaCurd.itemRemove(jtFmea);
        }else if (menu == menuItemCopy) {
            //new CopyAction(jtFmea,myAd);
            myAd.copyFuntion();
            //JOptionPane.showMessageDialog(null, "Copy "+menu.getName());
        }else if (menu == menuItemPaste) {
            //new PasteAction(jtFmea,myAd);
            myAd.pasteFuntion();
            //JOptionPane.showMessageDialog(null, "Paste "+menu.getName());
        }else if (menu == menuItemCalculate) {
            itemCalculateRPZ();
        }else if (menu == menuItemTemplate) {
            itemTemplate();
        }
    }
 
    public void itemCopy(){
    
    
    }
    
    public void itemPaste(){
    
    
    }
    
   
    
    

    
    public void itemCalculateRPZ(){
        
    for (int i = 0; i < model.getRowCount(); i++) {
                  
                  
              try {
              int res = NumberConversion.NVL(model.getValueAt(i,14)+"", 0)*NumberConversion.NVL(model.getValueAt(i,15)+"", 0)*NumberConversion.NVL(model.getValueAt(i,16)+"", 0);
              model.setValueAt(res, i, 17);
                  
              } catch (NumberFormatException e2) {
                  model.setValueAt(0, i, 17);
              } catch(ArrayStoreException e3){
                  model.setValueAt(0, i, 17);
              }
                  
              }
 
              
              
              
              for (int i = 0; i < model.getRowCount(); i++) {
                  
                  
              try {
              int res = Integer.parseInt(model.getValueAt(i,21)+"")*Integer.parseInt(model.getValueAt(i,22)+"")*Integer.parseInt(model.getValueAt(i,23)+"");
              model.setValueAt(res, i, 24);
                  
              } catch (NumberFormatException e2) {
                  model.setValueAt(23, i, 24);
              } catch(ArrayStoreException e3){
                  model.setValueAt((Object)(10), i, 24);
              }
                  
              }

       
              
              jtFmea.changeSelection(0, jtFmea.getColumnCount()-1, false, false);
              
              jtFmea.setColumnSelectionAllowed(true);
              jtFmea.setRowSelectionAllowed(false);

                // Select a column - column 0
                jtFmea.setColumnSelectionInterval(24, 24);

    
    
    }
     
    public void itemTemplate(){
    
    getFMEATableDataTemlate();
    }

    
    
    public int getTotalRowCount(MainProductGroup  mainPro){
    int res=0;
    for (SubProductGroup spgNew : mainPro.getSubProductGroupCollection()) {
          res++;
            for (SubProductComponents subProductComponents : spgNew.getSubProductComponentsCollection()) {
             res++;
                for (Functionalfailures functionalfailures : subProductComponents.getFunctionalfailuresCollection()) {
                 
                 res++;
                }
            }

        }
    
    return res;
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
 Object[] topcolumnNames = {"Failure Identification","","","","","","","Failure Effects",
                                 "","","Detection and Recovery Measures","","",""};
  
  Object[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Failure detection","Preventive and compensating measures","Detection measures","Improvement measures","Created Time"};
  Object[][] tableData = setAlignExport(fmeaCurd.refreshTableData(jtFmea,fmeas,getTableHeadingString(),new int[]{8,12,20}));
          
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
 Object[] topcolumnNames = {"Failure Identification","","","","","","","Failure Effects",
                                 "","","Detection and Recovery Measures","","",""};
  
  Object[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Failure detection","Preventive and compensating measures","Detection measures","Improvement measures","Created Time"};
  
  Object[][] tableData = fmeaCurd.refreshTableData(jtFmea,fmeas,getTableHeadingString(),new int[]{8,12,20});
          
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
  e.WriteExcel("FMEA_Template",datatypes);
}




public void setFMEATableData(Object[][] tableData){

Object[] first = new Object[1]; 
//first[0] =1;
    
Object[][] result = new Object[tableData.length][];


    for (int i = 0; i < tableData.length; i++) {
        result[i] =ArrayUtils.addAll(first, tableData[i]); 
    }



    model = new DefaultTableModel(result,getTableHeading());
    jtFmea.setModel(model);
    fmeaCurd.setGroupheading(jtFmea,getTableHeadingString(),new int[]{8,12,20});
}




public Object[] getTableHeading(){
     Object[] columnNames = {"Id","Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","ComType","ComId"};
 return columnNames;
}

public String[] getTableHeadingString(){
     String[] columnNames = {"Id","Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","ComType","ComId"};
 return columnNames;
}


public void progressImportSetup(Object[][] tableData){
     
 
     
      new Thread() { 
            public void run() {
                
                if (fMEAServices.checkExistFmeaData(jtFmea)==1) {
                   if (fMEAServices.infoFileFormat()==1) {
                       if (fMEAServices.validateImport(tableData)==1) {
                             fMEAServices.setFMEATableDataInstanly(jtFmea,tableData,fMEAServices.productSepration(tableData),(getTableHeadingString().length-2));
                             //c.setFMEATableData(tableData);
                              //importSave();
                             //c.refreshTableData();
                        }
                }
                }
                
                
            }
        }.start();
     
 
 }


public void importSave(){

  JFrame parent = InternalFrameDemo.mainFrame; 
  ProgressBar progress =  new ProgressBar();
  progress.setBountry(0, model.getRowCount());
  progress.setProgressTitle("Saving...");
           
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
              
        try {      
             if(model.getRowCount()>0){ 
               parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
              for (int i = 0; i < model.getRowCount(); i++) {
                  Fmea fmea = fmeaCurd.getFMEAByRowId(model,fmeas,i);
                  //fmea.setFmeaId(null);
                  // fMEADao.saveFMEA(fmea);
                  if (fmea.getFmeaId()==0) {
                    fMEADao.saveFMEA(fmea);
                  }else{
                    fMEADao.UpdateFMEA(fmea);
                  }
                  progress.setPercentage(i+1);
              }
              
              
              JOptionPane.showMessageDialog(null, model.getRowCount()+" Rows save successfully");
              fmeaCurd.refreshTableData(jtFmea,fmeas,getTableHeadingString(),new int[]{8,12,20});
             }else {
              JOptionPane.showMessageDialog(null, "No data to save");
              
             }
             } catch (Exception e) {
         e.printStackTrace();
         
     }finally{
       parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
     }


}






public void setStandreds(String standred){

    System.out.println("standred -------------"+standred);
    if(standred.contentEquals("MIL-STD1629A")){
      int[] hide = getDataHideMil1629A(); //12,13,15,18
    
        for (int i : hide) {
            jtFmea.getColumnModel().getColumn(i).setMinWidth(0);
            jtFmea.getColumnModel().getColumn(i).setMaxWidth(0); 
        }
    }
    

}

public int[] getDataHideMil1629A(){
 return new int []{11,14,16,17,19,20,21,22,23,24,25,26};//12,13
}

public int[] getDataHideMil1629ABar(){ //10 -11,13-14,15-17
 return new int []{0,1,2,3,4,5,6,7,8,9,11,12,14,17,18,27};
}

  public Object[][] setAlignImport(Object[][] importedData){
   Object[][] res = new Object [importedData.length][getTableHeading().length-1];

      for (int i = 0; i < importedData.length; i++) {
          for (int j = 0; j < importedData[i].length; j++) {
               res[i][getDataHideMil1629ABar()[j]] = importedData[i][j];
          }
      }
   
     /* for (int i : getDataHideMil1629A()) {
          res[i]= new Object[(getTableHeadingString().length-1)];
      }*/
   
   return res;
  }
  
  public Object[][] setAlignExport(Object[][] exportedData){
   Object[][] res = new Object [exportedData.length][getDataHideMil1629ABar().length];

      
      for (int i = 0; i < res.length; i++) {
          
           for (int j = 0; j < res[i].length; j++) {
          
             res[i][j] = exportedData[i][getDataHideMil1629ABar()[j]];
          }
      }

   return res;
  }
  
  
  /*public Object[][] setAlignColExport(int [] colAlign,Object[][] importedData,int colCount){
   Object[][] res = new Object [importedData.length][colAlign.length];
     
              /*  for (int matchColumn : colAlign) {
                    System.out.println("matchColumn - " + matchColumn);
                }*/

              
            /*  for (int i = 0; i < importedData[0].length; i++) {
                  System.out.println(i+" importedData - "+ ( (importedData[0][i].toString()== null) ? "" :importedData[0][i]) );
              }*/
   
     /* for (int i = 0; i < res.length; i++) {
          
           for (int j = 0; j < res[i].length; j++) {
          
               if ( colAlign[j]< importedData[i].length) {
                   res[i][j] = importedData[i][colAlign[j]];
               } else {
                    res[i][j]="";
               }
 
               //
             //System.out.println("importedData "+i+" "+importedData[i].length);
             // System.out.println("colAlign["+j+"] - "+colAlign[j]);
          }
           //System.out.println("importedData "+i+" "+importedData[i].length);
           //System.out.println("i"+i);
      }

   return res;
  }*/
    
}

