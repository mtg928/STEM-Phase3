/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.topfield.calculators.calcfile.CalcCurd;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Fmea;
import com.topfield.themes.WordWarpTable;
import com.topfield.modal.Mpghdr;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.singleton.UserMessages;
import com.topfield.user.UserInfo;
import com.topfield.utilities.ExcelAdapter;
import com.topfield.utilities.FMEACellRenderer;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.TableMouseListener;
import com.topfield.utilities.WordWrapCellRenderer;
import com.topfield.view.popup.FmeaMil1629APopup;
import com.topfield.view.popup.MgfPopup;
import com.topfield.view.popup.ProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import oracle.jrockit.jfr.JFR;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Murali
 */
public class FmeaMil1629A extends JPanel implements ActionListener {

    private Calchdr mpgCalcHdr;
    private JTable footer;
    private FMEADao fmeaDao = new FMEADaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FmeaCurd fmeaCurd = new FmeaCurd();
    private CalcCurd calcCurd = new CalcCurd();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private CalcFunctionsDao calcFunDao = new CalcFunctionsDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    private boolean bottomUp = false;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemEdit;
    private JMenuItem menuItemLoad;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemCalculate;
    private JMenuItem menuItemRefresh;
    private JMenuItem mergeCalcData;
    private JMenuItem addfun;
    //private JMenuItem menuItemTemplate;
    private ExcelAdapter myAd;
    
    private int[] tableHeadingGroup =new int[]{8,11,15,14};
    
    private WordWarpTable jtFmea = new WordWarpTable(13);
    
    //private FmeaMil1629APopup fmeaPopup;

    public FmeaMil1629A(Calchdr calchdr) {

        this.mpgCalcHdr = calchdr;
        if (mpgCalcHdr.getApproach()==1) {
            bottomUp=true;
        }
        //this.mpgCalcHdr =mpgHdrDao.getAllFMEAHDRByMPGOne(mpgHdr.getFmeaComponent().getMpgId(), "CMM", UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());

        setLayout(new BorderLayout());

        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE & EFFECTS ANALYSIS (MIL-STD1629A)  - " + mpgCalcHdr.getMpghdr().getFmeaComponent().getMpgDescription() +" - "+ mpgCalcHdr.getApproach() +"</p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);

        JPanel holdingPanel = new JPanel(new BorderLayout());
        
        
        //jtFmea.setRowHeight(50);
        //fmeaCurd.refreshTableData2(jtFmea, mpgHdr,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
        refreshData();

        JScroll center = new JScroll(jtFmea);
        jtFmea.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        footer = new JTable(1, 12);
        footer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        holdingPanel.add(center, BorderLayout.CENTER);
        holdingPanel.add(footer, BorderLayout.SOUTH);

        add(caption, BorderLayout.PAGE_START);
        add(holdingPanel, BorderLayout.CENTER);
        
        setupRightClick();
        


    }

    public String[] getTableHeadingMil1629A() {
        String[] columnNames = {"Line no","Id", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Improvement measures","Remarks", "ComType", "ComId"};
        return columnNames;
    }
    
    
    
    
       public void setupRightClick(){
        
        myAd = new ExcelAdapter(jtFmea);
    
         // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+V");
        menuItemAdd = new JMenuItem("Add Sub Component");
        menuItemLoad = new JMenuItem("Generate FMEA");
        menuItemSave = new JMenuItem("Save All");   
        menuItemEdit = new JMenuItem("Edit Selected Row");
        menuItemRemove = new JMenuItem("Delete Selected Row");
       // menuItemCalculate = new JMenuItem("Calculate Severity");
        menuItemRefresh = new JMenuItem("Refresh");
        //menuItemTemplate = new JMenuItem("Download file format template");
         
        menuItemCopy.addActionListener(this);
        menuItemPaste.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemLoad.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemEdit.addActionListener(this);
        menuItemRemove.addActionListener(this);
       // menuItemCalculate.addActionListener(this);
        menuItemRefresh.addActionListener(this);
        //menuItemTemplate.addActionListener(this);
         
        popupMenu.add(menuItemCopy);
        popupMenu.add(menuItemPaste);        
        if (bottomUp) {
               addfun = new JMenuItem("Add Function");
               addfun.addActionListener(this);
               popupMenu.add(addfun);
        }
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemLoad);
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
       // popupMenu.add(menuItemCalculate);
       if (!bottomUp){
            mergeCalcData = new JMenuItem("Merge Calcfile Data");
            mergeCalcData.addActionListener(this);
            popupMenu.add(mergeCalcData);
        }
        popupMenu.add(menuItemRefresh);
        
        //popupMenu.add(menuItemTemplate);
         
        // sets the popup menu for the table
        jtFmea.setComponentPopupMenu(popupMenu);
         
        jtFmea.addMouseListener(new TableMouseListener(jtFmea));
       // jtFmea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       

    }
    

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        Object[] options1 = {"Save", "Cancel"};
        //List<Integer> addedList = Arrays.asList(new Integer[]{19,20});
        List<Integer> addedList;
        MgfPopup mgfPopup ;
        int resultRef=-2;

        if (menu == menuItemAdd) {

            dataPopup(false,null);
        } else if (menu == menuItemSave) {
            
            new Thread() { 
                public void run() {
                    
                    saveAll();
                    refreshData(); 
                }
            }.start();
            
           
            
        }else if (menu == menuItemLoad) {
            addedList = fmeaCurd.getAddedList(jtFmea);
            mgfPopup = new MgfPopup(mpgCalcHdr.getMpghdr(), addedList);
               int result = JOptionPane.showOptionDialog(null,mgfPopup, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {
                    try {
                        InternalFrameDemo.mainFrame.setLoader();
                        for (Integer integer : mgfPopup.getSelectedItem()) {
                             fmeaCurd.insertFmea(integer,mpgCalcHdr);
                        }
                    
                         refreshData();
                        
                    } finally {
                        InternalFrameDemo.mainFrame.removeLoder();
                    }
                    
                }
           
        } else if (menu == menuItemEdit) {
            if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==1) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed edit from here \n Go to Project Management -> Management System Architecture (Menu bar)");
            }else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==3) {
                if (bottomUp) {
                    fmeaCurd.editRowCalFuntion(NumberConversion.NVL(jtFmea.getValueAt(jtFmea.getSelectedRow(), 1)+"", 0),mpgCalcHdr.getMpghdr(),this,"MIL");
                } else {
                    JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed edit from here \n Go to Calc File Calculator");
                }
                
            } else {
                dataPopup(true,null);
            }
            
        }else if (menu == menuItemRemove) {
            deleteFmeaRow(jtFmea.getSelectedRow());
        }else if (menu == menuItemCopy) {
            myAd.copyFuntion();
        }else if (menu == menuItemPaste) {
            myAd.pasteFuntion();
        }else if (menu == menuItemCalculate) {
           // itemCalculateRPZ();
        }else if (menu == mergeCalcData) {
            fmeaCurd.margeData(mpgCalcHdr.getCalchdrid());
            refreshData(); 
        }else if (menu == addfun) {
            fmeaCurd.AddFuntion(mpgCalcHdr.getMpghdr(), this, "MIL");
            
        }else if (menu == menuItemRefresh) {
            refreshData(); 
        }/*else if (menu == menuItemTemplate) {
            itemTemplate();
        }*/
    }
    
    public void dataPopup(boolean edit,FmeaMil1629APopup fmeaPop){
        
   
     FmeaMil1629APopup fmeaPopup=fmeaPop;
     
        if (fmeaPopup ==null) {
             List<Integer> addedList = fmeaCurd.getAddedList(jtFmea);   
             fmeaPopup = new FmeaMil1629APopup(mpgCalcHdr.getMpghdr().getMpghdrid(),addedList,edit,bottomUp);
        }
        dataPopup2(edit, fmeaPopup);
    }
    
    
    public String dataPopup2(boolean edit,FmeaMil1629APopup fmeaPopup){
    
     int selRow = jtFmea.getSelectedRow();   
     //List<Integer> addedList = Arrays.asList(new Integer[]{0,1,2,3,4,5});  
     
     Fmea fmeaData = (edit== false)? new Fmea():fmeaDao.getFMEAById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1)+"", 0) );
     Calcfile calcfileRef; 
     String message="";
     CalcFunctions CalcFunctions = null;
     
           if (edit) {
               
                calcfileRef =fmeaData.getCalcComp();
               
                fmeaPopup.setSubsystemBreakdownCode(fmeaData.getFISubsysBreakdownCode());
                fmeaPopup.setCalcFunction(fmeaData.getCalcComp().getCalcHeader());
                fmeaPopup.setFunctionalComponents(calcfileRef);
                fmeaPopup.setFunction(fmeaData.getFIFunctions());
                fmeaPopup.setPhase(fmeaData.getFIPhase());
                //fmeaPopup.setFailureMode(fmeaData.getFIFailuremode());
                fmeaPopup.setFailureMode(calcfileRef.getFailureMode());
                fmeaPopup.setFailureCause(fmeaData.getFIFailureCause());
                        
                fmeaPopup.setLocalSystemLevel(fmeaData.getFELocalSystemLevel());
                fmeaPopup.setTrainSubsystemLevel(fmeaData.getFETrainSubsystemLevel());
                fmeaPopup.setTrainLevel(fmeaData.getFETrainLevel());
                        
                fmeaPopup.setFailureDetection(fmeaData.getDRMFailuredetection());
                fmeaPopup.setPreventiveMeasures(fmeaData.getDRMPrevAndcompenmeasures());
                fmeaPopup.setServerityClass(fmeaData.getDRMImprovementmeasures()); //fmeaData.getDrmRpz()
                fmeaPopup.setRemarks(fmeaData.getRemarks());
     
     
           }else{
               CalcFunctions = fmeaCurd.getSelCalcFun(jtFmea);
               if (CalcFunctions !=null) {
                  fmeaPopup.setCalcFunction(CalcFunctions);
               }
              
           }
     
            int result = JOptionPane.showOptionDialog(null, fmeaPopup, "Select to insert components ***",
                              JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                              null, new String[]{"Save", "Cancel"}, null);

            if ((result == JOptionPane.YES_OPTION) && ( (!bottomUp && fmeaPopup.getFunctionalComponents()!= null)  ||(bottomUp && (fmeaPopup.getComponentText()!=null) && (!fmeaPopup.getComponentText().isEmpty())))) {
               //
               // System.out.println("bottomUp "+bottomUp+" "+(fmeaPopup.getComponentText()!=null)+" "+(!fmeaPopup.getComponentText().isEmpty()));
                try {
                    
                        calcfileRef =fmeaPopup.getFunctionalComponents();
                    
                      if (fmeaPopup.getCalcEdit() || bottomUp) {
                          calcfileRef.setCompId(fmeaPopup.getSubsystemBreakdownCode());
                          calcfileRef.setComponents(fmeaPopup.getComponentText());
                          calcfileRef.setFailureMode(fmeaPopup.getFailureMode());
                          calcfileRef.setCalcHeader(fmeaPopup.getCalcFunction());

                          if (edit) {
                            calcfileDao.Update(calcfileRef);
                          }else if(bottomUp){
                           calcfileRef.setFailureRate(0.0);
                           calcfileRef.setFMPercentage(0.0);
                           calcfileRef.setURPercentage(0.0);
                           calcfileRef.setRemarks("Generated");
                           calcfileRef.setReference(UserInfo.getInstance().getReference());
                           calcfileRef.setCalcId(calcfileDao.Save(calcfileRef));
                           //CalcfileRef = calcfileDao.findById(CalcfileRef.getCalcId());
                           
                          
                          }
                      }
              
                      
                      fmeaData.setFISubsysBreakdownCode(fmeaPopup.getSubsystemBreakdownCode());
                      fmeaData.setFISubsysComponent(calcfileRef.getComponents());
                      fmeaData.setFIFunctions(fmeaPopup.getFunction());
                      fmeaData.setFIPhase(fmeaPopup.getPhase());
                      fmeaData.setFIFailuremode(fmeaPopup.getFailureMode());
                      fmeaData.setFIFailureCause(fmeaPopup.getFailureCause());
                      fmeaData.setFELocalSystemLevel(fmeaPopup.getLocalSystemLevel());
                      fmeaData.setFETrainSubsystemLevel(fmeaPopup.getTrainSubsystemLevel());
                      fmeaData.setFETrainLevel(fmeaPopup.getTrainLevel());

                      fmeaData.setDRMFailuredetection(fmeaPopup.getFailureDetection());
                      fmeaData.setDRMPrevAndcompenmeasures(fmeaPopup.getPreventiveMeasures());

                      fmeaData.setDRMImprovementmeasures(fmeaPopup.getServerityClass());
                      fmeaData.setRemarks(fmeaPopup.getRemarks());
                      fmeaData.setComType("SubComp");
                      fmeaData.setComId(calcfileRef.getCalcId());
                      fmeaData.setCalcComp(calcfileRef);
                      fmeaData.setFmeaHeader(mpgCalcHdr);
                      


                      if (edit) {
                       fmeaDao.UpdateFMEA(fmeaData);
                      }else{
                       fmeaDao.saveFMEA(fmeaData);       
                      }
                      //fmeaCurd.refreshTableData2(jtFmea, mpgHdr,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
                      refreshData();
                       UserMessages.setNotificationUserPreference("FMEA001", "Saved", JOptionPane.INFORMATION_MESSAGE);
                   } catch (Exception e) {
                       
                    JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, e.getMessage() ,"Error-", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                  }

            } else if(result == JOptionPane.YES_OPTION) {

                if(bottomUp){
                   message="Please enter a vaild component";
                }else if (fmeaPopup.getFunctionalComponents()== null) {
                    // JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functional components is not selected");
                   message="Functional components is not selected";
                }
                              //JOptionPane.showMessageDialog(null, calculator + " of \"" + fmeaHeaderPopup.getMpg().getMpgDescription() + "\" already exist in the Project ");
            }
    
          fmeaPopup.setValidationMessage(message);
          
          if (!message.equals("")) {
              dataPopup(edit, fmeaPopup);
          }
    
       return message;
    }
    
    

    
    public Fmea getFMEAByRowId(JTable jtFmea,Calchdr mpgCalcHdr,int rowId){
        int count=0; 
        Fmea f =null;
        DefaultTableModel model =(DefaultTableModel)jtFmea.getModel();
        
        if (FMEAServices.getSystemLevelsNew(rowId, jtFmea)==4) {
            f = new Fmea();
        
            f.setFmeaId                    (NumberConversion.NVL(model.getValueAt(rowId, ++count)+"",0));
            f.setFISubsysBreakdownCode     ((String)model.getValueAt (rowId, ++count));
            f.setFISubsysComponent         ((String)model.getValueAt (rowId, ++count ));
            f.setFIFunctions               ((String)model.getValueAt (rowId, ++count));
            f.setFIPhase                   ((String)model.getValueAt (rowId, ++count));
            f.setFIFailuremode             ((String)model.getValueAt (rowId, ++count));
            f.setFIFailureCause            ((String)model.getValueAt (rowId, ++count));
            f.setFELocalSystemLevel        ((String)model.getValueAt (rowId, ++count));
            f.setFETrainSubsystemLevel     ((String)model.getValueAt (rowId, ++count));
            f.setFETrainLevel              ((String)model.getValueAt (rowId, ++count));
            f.setDRMFailuredetection       ((String)model.getValueAt (rowId, ++count));
            f.setDRMPrevAndcompenmeasures  ((String)model.getValueAt (rowId, ++count));
            f.setDRMImprovementmeasures    ((String)model.getValueAt(rowId, ++count));
            f.setRemarks((String)model.getValueAt (rowId, ++count));
            
            f.setComType("SubComp"); ++count;
            f.setComId(NumberConversion.NVL(model.getValueAt(rowId, ++count)+"", 0));
            f.setCalcComp(calcfileDao.findById(f.getComId()));
            f.setFmeaHeader(mpgCalcHdr);  
            
         }
   return f;     
  }
    
    
   public void saveAll(){
   
       ProgressBar progress = new ProgressBar();
       progress.setBountry(0, jtFmea.getRowCount());
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
            
            
            for (int i = 0; i < jtFmea.getRowCount(); i++) {
                
                 saveById(i);
                progress.setPercentage(i);
            }
            progress.setPercentage(jtFmea.getRowCount());
            //JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Data saved successfully");
   
   
   
   }

    public void saveById(int rowId){
     Calcfile calcfileRef;
     Fmea fmea;
     
        if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==4) {
            fmea = getFMEAByRowId(jtFmea, mpgCalcHdr, rowId);
            if (fmea != null  ) {
                calcfileRef= fmea.getCalcComp();
                calcfileRef.setCompId(fmea.getFISubsysBreakdownCode());
                calcfileRef.setComponents(fmea.getFISubsysComponent());
                calcfileRef.setFailureMode(fmea.getFIFailuremode());
                calcfileDao.Update(calcfileRef);
                fmeaDao.UpdateFMEA(fmea);
           }
        }
       
    }
   
    
    public void deleteFmeaRow(int rowId){
   
        if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==1) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed delete from here \n Go to Project Management -> Management System Architecture (Menu bar)");
        }else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==3) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed delete from here \n Go to Calc File Calculator");
        } else {
            
              if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        
                fmeaDao.deleteFMEA(NumberConversion.NVL(jtFmea.getValueAt(rowId, 1)+"", 0));
                //fmeaCurd.refreshTableData2(jtFmea, mpgHdr,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
                refreshData();
               } else {
                        // no option
               }

        }
   
   
    }
    
 
 public Object[][] getFMEATableData(){

 Object[] temp;    
 Object[] topcolumnNames = {"","Failure Identification","","","","","","","Failure Effects",
                                 "","","Detection and Recovery Measures","","","",""};
  
  String [] columnNames = getTableHeadingMil1629A();
  columnNames[13]="Severity Class";
  //String [] columnNames = ArrayUtils.removeElement(getTableHeadingMil1629A(), "Line no");
  //Object[][] tableData = setAlignExport(fmeaCurd.refreshTableData(jtFmea,mpgHdr,getTableHeadingMil1629A(),new int[]{8,12,20}));
  
  Object[][] tableData = refreshData();
          
   Object[][] datatypes = new Object[tableData.length+2][];
       //System.out.println("tableData.length "+tableData.length);
    
    datatypes[0] =topcolumnNames;    
    datatypes[1] =columnNames; 
   
    for (int i = 0; i < tableData.length; i++) {
       //temp = ArrayUtils.removeElement(tableData[i], tableData[i][0]);
        datatypes[i+2]= tableData[i];
        System.out.println("Length - "+tableData[i].length);
    }
   
       
 return datatypes;
}
 
 
  public Object[][]  refreshData(){

  return fmeaCurd.refreshTableData2(jtFmea,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
 } 
 
 public int [] getMargeCol(){
    
  return  tableHeadingGroup;
 }
 

 

  
}
