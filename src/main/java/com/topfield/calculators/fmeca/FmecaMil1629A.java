/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmeca;

import com.topfield.calculators.faulttree.FaultTreeEquations;
import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.calculators.fmea.FmeaCurd;
import com.topfield.controller.BusinessException;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
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
import com.topfield.view.popup.FMECAPopup;
import com.topfield.view.popup.MgfPopup;
import com.topfield.view.popup.ProgressBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class FmecaMil1629A extends JPanel implements ActionListener {

    private Calchdr mpgCalcHdr;
    private JTable footer;
    private FMEADao fmeaDao = new FMEADaoImpl();
    private FMECADao fmecaDao = new FMECADaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FmeaCurd fmeaCurd = new FmeaCurd();
    private FMECACrud fmecaCurd = new FMECACrud();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
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
    
    
    private int[] tableHeadingGroup =new int[]{8,11,14,16};
    
    private WordWarpTable jtFmea = new WordWarpTable(8);

    public FmecaMil1629A(Calchdr mpgCalcHdr) {

        this.mpgCalcHdr = mpgCalcHdr;
        //this.mpgCalcHdr =mpgHdrDao.getAllFMEAHDRByMPGOne(mpgHdr.getFmeaComponent().getMpgId(), "CMM", UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());
        if (mpgCalcHdr.getApproach()==1) {
            bottomUp=true;
        }
        setLayout(new BorderLayout());

        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE EFFECTS AND CRITICALITY ANALYSIS  (MIL-STD1629A)  - " + mpgCalcHdr.getMpghdr().getFmeaComponent().getMpgDescription() +" - "+ mpgCalcHdr.getApproach()+ "</p> </center> </body> </html>");
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
        
         jtFmea.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {  //mouseEntered
                if (e.getButton() == MouseEvent.BUTTON1) {
                    jtFmea.setCellSelectionEnabled(true);
                }
            }

        }
        );

 
    }

    public String[] getTableHeadingMil1629A() {
        String[] columnNames = { "Line no","Id","Subsystem Breakdown Code","Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Severity Class",
                                 "Failure Rate Data Source","Failure Effect Probability (β)", "Failure Mode Ratio (α)", "Failure Rate (λp)", "Operating Time (t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "Remarks", "ComType", "ComId"};
        return columnNames;
    }
    
    
    
       public void setupRightClick(){
        
        myAd = new ExcelAdapter(jtFmea);
    
         // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+V");
        menuItemAdd = new JMenuItem("Add Sub Component");
        menuItemSave = new JMenuItem("Save All");
        menuItemLoad = new JMenuItem("Generate FMECA");
        menuItemEdit = new JMenuItem("Edit Selected Row");
        menuItemRemove = new JMenuItem("Delete Selected Row");
        menuItemCalculate = new JMenuItem("Calculate Criticality");
        menuItemRefresh = new JMenuItem("Refresh");
        //menuItemTemplate = new JMenuItem("Download file format template");
         
        menuItemCopy.addActionListener(this);
        menuItemPaste.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemLoad.addActionListener(this);
        menuItemEdit.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemCalculate.addActionListener(this);
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
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemLoad);
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemCalculate);
        
       if (!bottomUp){
            mergeCalcData = new JMenuItem("Merge Calcfile Data");
            mergeCalcData.addActionListener(this);
            popupMenu.add(mergeCalcData);
        }
        popupMenu.add(menuItemRefresh); 
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
        MgfPopup mgfPopup;

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
                    
                    for (Integer integer : mgfPopup.getSelectedItem()) {
                         fmecaCurd.insertFmeca(integer,mpgCalcHdr);
                    }
                    
                    refreshData();
                }
           
        } else if (menu == menuItemEdit) {
            if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==1) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed edit from here \n Go to Project Management -> Management System Architecture (Menu bar)");
            }else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==3) {
                if (bottomUp) {
                    fmecaCurd.editRowCalFuntion(NumberConversion.NVL(jtFmea.getValueAt(jtFmea.getSelectedRow(), 1)+"", 0),mpgCalcHdr.getMpghdr(),this,"MIL");
                } else {
                    JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed edit from here \n Go to Calc File Calculator");
                }
            } else {
                dataPopup(true,null);
            }
            
        }else if (menu == menuItemRemove) {
            deleteFmecaRow(jtFmea.getSelectedRow());
        }else if (menu == menuItemCopy) {
            myAd.copyFuntion();
        }else if (menu == menuItemPaste) {
            myAd.pasteFuntion();
        }else if (menu == menuItemCalculate) {
            modeCalculateCriticality();
        }else if (menu == mergeCalcData) {
            fmecaCurd.margeData(mpgCalcHdr.getCalchdrid());
            refreshData();
        }/*else if (menu == menuItemTemplate) {
            itemTemplate();
        }*/else if (menu == menuItemRefresh) {
           refreshData();
        }else if (menu == addfun) {
            fmecaCurd.AddFuntion(mpgCalcHdr.getMpghdr(), this, "MIL");
            
        }
    }
    
    public void dataPopup(boolean edit,FMECAPopup fmecaPop){

     FMECAPopup fmecaPopup=fmecaPop;
     
        if (fmecaPopup ==null) {
             List<Integer> addedList = fmeaCurd.getAddedList(jtFmea);
             fmecaPopup =  new FMECAPopup(mpgCalcHdr.getMpghdr().getMpghdrid(),addedList,edit,bottomUp);
        }
        dataPopup2(edit, fmecaPopup);
    }
    
    
    public void dataPopup2(boolean edit,FMECAPopup fmecaPopup){
    
     int selRow = jtFmea.getSelectedRow();   
     String message="";
     Calcfile calcfileRef= null;
     CalcFunctions CalcFunctions = null;
     Fmeca fmecaData = (edit== false)? new Fmeca():fmecaDao.getFMECAById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1)+"", 0) );
      
     
           if (edit) {
               
                calcfileRef =fmecaData.getCalcComp();
               
                fmecaPopup.setSubsystemBreakdownCode(calcfileRef.getCompId());
                fmecaPopup.setCalcFunction(fmecaData.getCalcComp().getCalcHeader());
                fmecaPopup.setFunctionalComponents(calcfileRef);
                fmecaPopup.setFunction(fmecaData.getFIFun());
                fmecaPopup.setPhase(fmecaData.getFIPhases());
                fmecaPopup.setFailureMode(calcfileRef.getFailureMode());
                fmecaPopup.setFailureCause(fmecaData.getFIFailCause());
                fmecaPopup.setServerityClass(fmecaData.getSeverityclass());
                        
                fmecaPopup.setFailureProbability(fmecaData.getFailureProbability());
                fmecaPopup.setFailureEffectProbability(fmecaData.getFCFailEffProb());
                fmecaPopup.setFailureModeRatio(calcfileRef.getFMPercentage());                       
                fmecaPopup.setFailureRate(calcfileRef.getFailureRate());
                fmecaPopup.setOperatingTime(fmecaData.getFCOperatingTime());
                
                fmecaPopup.setFailureItemCriticality(fmecaData.getFCFailItemCri());
                fmecaPopup.setFailureModeCriticality(fmecaData.getFCFailModeCri());
                fmecaPopup.setRemarks(fmecaData.getRemarks());
     
     
           }else{
               CalcFunctions = fmecaCurd.getSelCalcFun(jtFmea);
               if (CalcFunctions !=null) {
                  fmecaPopup.setCalcFunction(CalcFunctions);
               }
              
           }
     
            int result = JOptionPane.showOptionDialog(null, fmecaPopup, "Select to insert components",
                              JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                              null, new String[]{"Save", "Cancel"}, null);

            if (result == JOptionPane.YES_OPTION  && ( (!bottomUp && fmecaPopup.getFunctionalComponents()!= null)  ||(bottomUp && (fmecaPopup.getComponentText()!=null) && (!fmecaPopup.getComponentText().isEmpty())))) {

                 try {
                     
                      calcfileRef =fmecaPopup.getFunctionalComponents();
                      
                       if (fmecaPopup.getCalcEdit() || bottomUp) {
                          calcfileRef.setCompId(fmecaPopup.getSubsystemBreakdownCode());
                          calcfileRef.setComponents(fmecaPopup.getComponentText());
                          calcfileRef.setFailureMode(fmecaPopup.getFailureMode());
                          calcfileRef.setCalcHeader(fmecaPopup.getCalcFunction());

                          if (edit) {
                            calcfileDao.Update(calcfileRef);
                          }else if(bottomUp){
                           calcfileRef.setFailureRate(fmecaPopup.getFailureRate());
                           calcfileRef.setFMPercentage(fmecaPopup.getFailureModeRatio());
                           calcfileRef.setURPercentage(1.0);
                           calcfileRef.setRemarks("Generated");
                           calcfileRef.setReference(UserInfo.getInstance().getReference());
                           calcfileRef.setCalcId(calcfileDao.Save(calcfileRef));
                           //CalcfileRef = calcfileDao.findById(CalcfileRef.getCalcId());
                          }
                      }
                     
                      fmecaData.setFISubsysBreakCode(fmecaPopup.getSubsystemBreakdownCode());
                      fmecaData.setFISubsysComp(calcfileRef.getComponents());
                      fmecaData.setFIFun(fmecaPopup.getFunction());
                      fmecaData.setFIPhases(fmecaPopup.getPhase());
                      fmecaData.setFIFailmode(fmecaPopup.getFailureMode());
                      fmecaData.setFIFailCause(fmecaPopup.getFailureCause());
                      fmecaData.setSeverityclass(fmecaPopup.getServerityClass());
                      
                      fmecaData.setFailureProbability(fmecaPopup.getFailureProbability());
                      fmecaData.setFCFailEffProb(fmecaPopup.getFailureEffectProbability());
                      fmecaData.setFCFailModeRatio(fmecaPopup.getFailureModeRatio());
                      fmecaData.setFCFailureRate(fmecaPopup.getFailureRate());
                      fmecaData.setFCOperatingTime(fmecaPopup.getOperatingTime());
                      
                      fmecaData.setFCFailItemCri(fmecaPopup.getFailureItemCriticality());
                      fmecaData.setFCFailModeCri(fmecaPopup.getFailureModeCriticality());
                      fmecaData.setRemarks(fmecaPopup.getRemarks());
                      
                      fmecaData.setComType("SubComp");
                      fmecaData.setComId(calcfileRef.getCalcId());
                      fmecaData.setCalcComp(calcfileRef);
                      fmecaData.setFmecaHeader(mpgCalcHdr);

                      if (edit) {
                       fmecaDao.UpdateFMECA(fmecaData);
                      }else{
                       fmecaDao.saveFMECA(fmecaData);       
                      }
                      //fmeaCurd.refreshTableData2(jtFmea, mpgHdr,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
                      refreshData();
                      UserMessages.setNotificationUserPreference("FMECA001", "Saved", JOptionPane.INFORMATION_MESSAGE);
                      
                  } catch (Exception e) {
                       
                    JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, e.getMessage() ,"Error-", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                  }

            } else if(result == JOptionPane.YES_OPTION) {

                 if(bottomUp){
                   message="Please enter a vaild component";
                }else if (fmecaPopup.getFunctionalComponents()== null) {
                  message= "Functional components is not selected";
                }
                              //JOptionPane.showMessageDialog(null, calculator + " of \"" + fmeaHeaderPopup.getMpg().getMpgDescription() + "\" already exist in the Project ");
            }
    
            
          if (!message.equals("")) {
              dataPopup(edit, fmecaPopup);
          }
    
    
    
    }
    
    

    
    public Fmeca getFMECAByRowId(JTable jtFmea,Calchdr mpgCalcHdr,int rowId){
        int count=0; 
        Fmeca f =null;
        DefaultTableModel model =(DefaultTableModel)jtFmea.getModel();
        
        if (FMEAServices.getSystemLevelsNew(rowId, jtFmea)==4) {
            f = new Fmeca();
            f.setFmecaId(NumberConversion.NVL(model.getValueAt(rowId, ++count) + "", 0));
            f.setFISubsysBreakCode((String) model.getValueAt(rowId, ++count));
            f.setFISubsysComp((String) model.getValueAt(rowId, ++count));
            f.setFIFun((String) model.getValueAt(rowId, ++count));
            f.setFIPhases((String) model.getValueAt(rowId, ++count));
            f.setFIFailmode((String) model.getValueAt(rowId, ++count));
            f.setFIFailCause((String) model.getValueAt(rowId, ++count));
            f.setSeverityclass((String) model.getValueAt(rowId, ++count));
            
            f.setFailureProbability(model.getValueAt(rowId, ++count) + "");
            f.setFCFailEffProb(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setFCFailModeRatio(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setFCFailureRate(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setFCOperatingTime(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setFCFailModeCri(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setFCFailItemCri(NumberConversion.NVLDouble(model.getValueAt(rowId, ++count) + "", 0));
            f.setRemarks(model.getValueAt(rowId, ++count) + "");
            
            f.setComType("SubComp"); ++count;
            f.setComId(NumberConversion.NVL(model.getValueAt(rowId, ++count)+"", 0));
            f.setCalcComp(calcfileDao.findById(f.getComId()));
            f.setFmecaHeader(mpgCalcHdr);  
            
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
     Fmeca fmeca ;
       
        if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==4) {
            fmeca = getFMECAByRowId(jtFmea, mpgCalcHdr, rowId);
             if (fmeca != null  ) {
                 calcfileRef= fmeca.getCalcComp();
                 calcfileRef.setCompId(fmeca.getFISubsysBreakCode());
                 calcfileRef.setComponents(fmeca.getFISubsysComp());
                 calcfileRef.setFailureMode(fmeca.getFIFailmode());
                 calcfileRef.setFailureRate(fmeca.getFCFailureRate());
                 calcfileRef.setFMPercentage(fmeca.getFCFailModeRatio());
                 calcfileDao.Update(calcfileRef);
                 fmecaDao.UpdateFMECA(fmeca);
        }

     }
    }
    
    public void deleteFmecaRow(int rowId){
   
        if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==1) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed delete from here \n Go to Project Management -> Management System Architecture (Menu bar)");
        }else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea)==3) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed delete from here \n Go to Calc File Calculator");
        } else {
            
             if (BusinessException.showBusinessConformation("Do you want to delete this component? ",true)== JOptionPane.YES_OPTION){
                fmecaDao.deleteFMECA(NumberConversion.NVL(jtFmea.getValueAt(rowId, 1)+"", 0));
                //fmeaCurd.refreshTableData2(jtFmea, mpgHdr,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
                refreshData();
             }  
        }
   
   
    }
   
 
 public Object[][] getFMEATableData(){

 Object[] temp;    
 Object[] topcolumnNames = {"","Failure Identification","","","","","","","Failure Effects",
                                 "","","Detection and Recovery Measures","","",""};
  
  String [] columnNames = getTableHeadingMil1629A();
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
 
 
 public void modeCalculateCriticality() {
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        HashMap<String,Double> hm =new HashMap<String,Double>();
        String key;

        for (int i = 0; i < model.getRowCount(); i++) {

            if (FMEAServices.getSystemLevelsNew(i, model) == 4) {
                try {
                    double res = NumberConversion.NVLDouble(model.getValueAt(i, 10) + "", 0) * NumberConversion.NVLDouble(model.getValueAt(i, 12) + "", 0) * NumberConversion.NVLDouble(model.getValueAt(i, 13) + "", 0);
                    res = res*NumberConversion.NVLDouble(model.getValueAt(i, 11) + "", 0);
                    model.setValueAt(res, i, 14);
                    //model.setValueAt(res, i, 15);
                    
                    key = model.getValueAt(i, 3) + "";
                    
                    if(hm.containsKey(key)){
                       hm.replace(key, (hm.get(key)+res));
                    }else{
                       hm.put(key,res);
                    }

                } catch (NumberFormatException e2) {
                    model.setValueAt(0, i, 14);
                    model.setValueAt(0, i, 15);
                } catch (ArrayStoreException e3) {
                    model.setValueAt(0, i, 14);
                    model.setValueAt(0, i, 15);
                }
                
                

            }

        }



        jtFmea.changeSelection(0, jtFmea.getColumnCount() - 1, false, false);

        jtFmea.setColumnSelectionAllowed(true);
        jtFmea.setRowSelectionAllowed(false);

        // Select a column - column 0
        jtFmea.setColumnSelectionInterval(14, 15);
        
        
           itemCalculateCriticality(hm, model);
    }
 
 
 
 public void itemCalculateCriticality(HashMap<String,Double> hm, DefaultTableModel model){
     String key;
     
      for (int i = 0; i < model.getRowCount(); i++) {

            if (FMEAServices.getSystemLevelsNew(i, model) == 4) {
                
                 key = model.getValueAt(i, 3) + "";
                
                 model.setValueAt(hm.get(key), i, 15);
                 
                 System.out.println(key+" - "+hm.get(key) );
            }
      }
      
      
 }
 
public Object[][]  refreshData(){

  return fmecaCurd.refreshTableData2(jtFmea,mpgCalcHdr, getTableHeadingMil1629A(),tableHeadingGroup);
 } 
 
 public int [] getMargeCol(){
    
  return  tableHeadingGroup;
 }
 
 


  
}

