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
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Fmea;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import com.topfield.utilities.ColumnGroup;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WordWrapCellRenderer;
import com.topfield.view.popup.CalcFunctionsPopup;
import com.topfield.view.popup.FMEAPopup;
import com.topfield.view.popup.FMEAPopupAdd;
import com.topfield.view.popup.ProgressBar;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Murali
 */
public class FmeaCurd {

    private FMEADao fmeaDao = new FMEADaoImpl();
    private CalcFunctionsDao calcFunctionsDao = new CalcFunctionsDaoImpl();
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao subDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
    private FunctionalfailuresDao funDao = new FunctionalfailuresDaoImpl();
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private CalcCurd calcCurd = new CalcCurd();
    
    public Fmea getFMEAByRowId(DefaultTableModel model,Calchdr mpgHdr,int rowId){
     Fmea f =null;
    
     
      if (FMEAServices.getSystemLevelsNew(rowId, model)==4) {
       f = new Fmea();
   
            f.setFmeaId                    (NumberConversion.NVL(model.getValueAt(rowId, 1)+"",0));
            f.setFISubsysBreakdownCode     ((String)model.getValueAt (rowId, 2 ));
            f.setFISubsysComponent         ((String)model.getValueAt (rowId, 3 ));
            f.setFIFunctions               ((String)model.getValueAt (rowId, 4	));
            f.setFIPhase                   ((String)model.getValueAt (rowId, 5	));
            f.setFIFailuremode             ((String)model.getValueAt (rowId, 6	));
            f.setFIFailureCause            ((String)model.getValueAt (rowId, 7	));
            f.setFELocalSystemLevel        ((String)model.getValueAt (rowId, 8	));
            f.setFETrainSubsystemLevel     ((String)model.getValueAt (rowId, 9	));
            f.setFETrainLevel              ((String)model.getValueAt (rowId, 10	));
            f.setFEImportanceB             (NumberConversion.NVL(model.getValueAt(rowId, 11)+"",0));
            f.setDRMFailuredetection       ((String)model.getValueAt (rowId, 12));
            f.setDRMPrevAndcompenmeasures  ((String)model.getValueAt (rowId, 13));
            f.setDRMOccurrenceA            (NumberConversion.NVL(model.getValueAt(rowId, 14)+"",0));
            f.setDRMDetectionmeasures      ((String)model.getValueAt (rowId, 15));
            f.setDRMDetectionE             (NumberConversion.NVL(model.getValueAt(rowId, 16)+"",0));
            f.setDrmRpz                    (NumberConversion.NVL(model.getValueAt(rowId, 17)+"",0));
           /* f.setDRMImprovementmeasures    ((String)model.getValueAt (rowId, 18));
            f.setDRMResponsibleDate        ((String)model.getValueAt (rowId, 19));
            f.setIRUndertakenmeasures      ((String)model.getValueAt (rowId, 20));
            f.setIRImportanceB             (NumberConversion.NVL(model.getValueAt(rowId, 21)+"",0));
            f.setIROccurrenceA             (NumberConversion.NVL(model.getValueAt(rowId, 22)+"",0));
            f.setIRDetectionE              (NumberConversion.NVL(model.getValueAt(rowId, 23)+"",0));
            f.setIrRpz                     (NumberConversion.NVL(model.getValueAt(rowId, 24)+"", 0));*/

            f.setComType("SubComp"); 
            f.setComId(NumberConversion.NVL(model.getValueAt(rowId, 19)+"", 0));
            f.setCalcComp(calcfileDao.findById(f.getComId()));
            f.setFmeaHeader(mpgHdr);  
            
          }
           //System.out.println("************************"+NumberConversion.NVL(model.getValueAt(rowId, 24)+"", 0));
            //f.setCreateddate(new Date());
            //f.setFmeaComponent(mainDao.FindById(MpgId));
            //f.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
            //f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
      
   return f;     
 }

    public void itemAdd(JTable jtFmea,Calchdr fmeas,String [] subHeadings,int[] group) {

        int selRow = jtFmea.getSelectedRow();
        SubProductGroup spgPara = null;
        SubProductComponents spcPara = null;
        int funNewRef = 0;
        DefaultTableModel model = (DefaultTableModel)jtFmea.getModel();
        
        // if(jtFmea.getValueAt(selRow,2).toString().contains("-")==true){
        //if (jtFmea.getValueAt(selRow, 2).toString().length() > (UserInfo.getInstance().getProAbbreviations().length() + 3)) {
           if(fMEAServices.getSystemLevels(jtFmea.getValueAt(selRow, 2).toString()) > 2){
            //try{
            //setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Object[] options1 = {"Save", "Cancel"};
            FMEAPopupAdd fmeaPopup = new FMEAPopupAdd(fmeas.getMpghdr().getMpghdrid(),new ArrayList<Integer>(),false);

            if (FMEAServices.getSystemLevels(model.getValueAt(selRow, 2).toString()) == 4) {
                Functionalfailures funRef = funDao.findByName(model.getValueAt(selRow, 3).toString()).get(0);
                spcPara = funRef.getSubComponent();
                spgPara = spcPara.getSpgRef();
            } else {
                System.out.println("model.getValueAt(selRow,3).toString() - " + model.getValueAt(selRow, 3).toString());
                spcPara = spcDao.FindByName(model.getValueAt(selRow, 3).toString());
                spgPara = spcPara.getSpgRef();
            }
            //Functionalfailures funRef = funDao.findByName(model.getValueAt(selRow,3).toString()).get(0);
            //SubProductComponents subComRef = spcDao.FindById(fmeaPopup.getSubProductComponents().getId());

            fmeaPopup.setSubsystemBreakdownCode(model.getValueAt(selRow, 2).toString());
            //fmeaPopup.eventSelected(spgPara, spcPara);
            /*fmeaPopup.setFunction(model.getValueAt(selRow,4).toString());
        fmeaPopup.setPhase(model.getValueAt(selRow,5).toString());
        fmeaPopup.setFailureMode(model.getValueAt(selRow,6).toString());
        fmeaPopup.setFailureCause(model.getValueAt(selRow,7).toString());
        fmeaPopup.setLocalSystemLevel(model.getValueAt(selRow,8).toString());
        fmeaPopup.setTrainSubsystemLevel(model.getValueAt(selRow,9).toString());
        fmeaPopup.setTrainLevel(model.getValueAt(selRow,10).toString());

        fmeaPopup.setImportance(model.getValueAt(selRow,11).toString());
        fmeaPopup.setFailureDetection(model.getValueAt(selRow,12).toString());
        fmeaPopup.setPreventiveMeasures(model.getValueAt(selRow,13).toString());
        fmeaPopup.setOccurrence(model.getValueAt(selRow,14).toString());
        fmeaPopup.setDetectionMeasures(model.getValueAt(selRow,15).toString());
        fmeaPopup.setDetection(model.getValueAt(selRow,16).toString());
        fmeaPopup.setRPZ(model.getValueAt(selRow,17).toString());
        fmeaPopup.setImprovementMeasures(model.getValueAt(selRow,18).toString());

        fmeaPopup.setResponsibleDateImpr(model.getValueAt(selRow,19).toString());
        fmeaPopup.setUndertakenMeasures(model.getValueAt(selRow,20).toString());
        fmeaPopup.setImportanceImpr(model.getValueAt(selRow,21).toString());
        fmeaPopup.setOccurrenceImpr(model.getValueAt(selRow,22).toString());
        fmeaPopup.setDetectionImpr(model.getValueAt(selRow,23).toString());
        fmeaPopup.setRPZImpr(model.getValueAt(selRow,24).toString());*/

 /*fmeaPopup.setSubProductGroup(new com.topfield.utilities.Item(subComRef.getSpgRef().getSpgId(),subComRef.getSpgRef().getSpgDescription()));
        fmeaPopup.setSubProductComponents(new com.topfield.utilities.Item(subComRef.getSpcId(),subComRef.getSpcDescription()));
        fmeaPopup.setFunctionalComponents(new com.topfield.utilities.Item(funRef.getFailureId(),funRef.getDescription()));
             */
            int result = JOptionPane.showOptionDialog(null, fmeaPopup, "Please enter the details",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                /*model.setValueAt(fmeaPopup.getFunctionalComponents(), selRow, 3);
              model.setValueAt(fmeaPopup.getFunction(), selRow, 4);
              model.setValueAt(fmeaPopup.getPhase(), selRow, 5);
              model.setValueAt(fmeaPopup.getFailureMode(), selRow, 6);
              model.setValueAt(fmeaPopup.getFailureCause(), selRow, 7);
              model.setValueAt(fmeaPopup.getLocalSystemLevel(), selRow, 8);
              model.setValueAt(fmeaPopup.getTrainSubsystemLevel(), selRow, 9);
              model.setValueAt(fmeaPopup.getTrainLevel(), selRow, 10);
              model.setValueAt(fmeaPopup.getImportance(), selRow, 11);
              model.setValueAt(fmeaPopup.getFailureDetection(), selRow, 12);
              model.setValueAt(fmeaPopup.getPreventiveMeasures(), selRow, 13);
              model.setValueAt(fmeaPopup.getOccurrence(), selRow, 14);
              model.setValueAt(fmeaPopup.getDetectionMeasures(), selRow, 15);
              model.setValueAt(fmeaPopup.getDetection(), selRow, 16);
              model.setValueAt(fmeaPopup.getRPZ(), selRow, 17);
              model.setValueAt(fmeaPopup.getImprovementMeasures(), selRow, 18);
              model.setValueAt(fmeaPopup.getResponsibleDateImpr(), selRow, 19);
              model.setValueAt(fmeaPopup.getUndertakenMeasures(), selRow, 20);
              model.setValueAt(fmeaPopup.getImportanceImpr(), selRow, 21);
              model.setValueAt(fmeaPopup.getOccurrenceImpr(), selRow, 22);
              model.setValueAt(fmeaPopup.getDetectionImpr(), selRow, 23);
              model.setValueAt(fmeaPopup.getRPZImpr(), selRow, 24);*/
                Functionalfailures funNew = new Functionalfailures();
                funNew.setProjecttype(UserInfo.getInstance().getTclProject().getProType());
                //funNew.setFailuretype(funRef.getFailuretype());
                funNew.setDescription(fmeaPopup.getFunctionalComponents().getComponents());
               // funNew.setSubComponent(fmeaPopup.getFunctionalComponents().)); Need to set After linkage
                funNew.setFunction(fmeaPopup.getFunction());
                funNew.setPhase(fmeaPopup.getPhase());
                funNew.setFailureMode(fmeaPopup.getFailureMode());
                funNew.setFailureCause(fmeaPopup.getFailureCause());
                funNew.setLocalFailureEffect(fmeaPopup.getLocalSystemLevel());
                funNew.setSubsystemFailureEffect(fmeaPopup.getTrainSubsystemLevel());
                funNew.setTrainFailureEffect(fmeaPopup.getTrainLevel());
                funNew.setFailureDetection(fmeaPopup.getFailureDetection());
                funNew.setImportance(NumberConversion.NVLDouble(fmeaPopup.getImportance()+"", 0));
                funNew.setFailureRate(0.00);
                funNew.setPreventiveMeasures(fmeaPopup.getImprovementMeasures());
                funNew.setUsername(UserInfo.getInstance().getuser());
                funNewRef = funDao.Save(funNew);

                Fmea fmea = new Fmea();
                //fmea.setFmeaId(null);
                //fmeaPopup.setSubsystemBreakdownCode(fMEAServices.generateSubSystemCode(fmeaPopup.getSubsystemBreakdownCode(), fmeaPopup.getSubProductComponents().getId(),model));
                fmea.setFISubsysBreakdownCode(fmeaPopup.getSubsystemBreakdownCode());
                fmea.setFISubsysComponent(fmeaPopup.getFunctionalComponents().getComponents());
                fmea.setFIFunctions(fmeaPopup.getFunction());
                fmea.setFIPhase(fmeaPopup.getPhase());
                fmea.setFIFailuremode(fmeaPopup.getFailureMode());
                fmea.setFIFailureCause(fmeaPopup.getFailureCause());
                fmea.setFELocalSystemLevel(fmeaPopup.getLocalSystemLevel());
                fmea.setFETrainSubsystemLevel(fmeaPopup.getTrainSubsystemLevel());
                fmea.setFETrainLevel(fmeaPopup.getTrainLevel());
                fmea.setFEImportanceB(fmeaPopup.getImportance());
                fmea.setDRMFailuredetection(fmeaPopup.getFailureDetection());
                fmea.setDRMPrevAndcompenmeasures(fmeaPopup.getPreventiveMeasures());
                fmea.setDRMOccurrenceA(fmeaPopup.getOccurrence());
                fmea.setDRMDetectionmeasures(fmeaPopup.getDetectionMeasures());
                fmea.setDRMDetectionE(fmeaPopup.getDetection());
                fmea.setDrmRpz(NumberConversion.NVL(fmeaPopup.getRPZ(), 0));
                fmea.setDRMImprovementmeasures(fmeaPopup.getImprovementMeasures());
                fmea.setDRMResponsibleDate(fmeaPopup.getResponsibleDateImpr());
                fmea.setIRUndertakenmeasures(fmeaPopup.getUndertakenMeasures());
                fmea.setIRImportanceB(fmeaPopup.getImportanceImpr());
                fmea.setIROccurrenceA(fmeaPopup.getOccurrenceImpr());
                fmea.setIRDetectionE(fmeaPopup.getDetectionImpr());
                fmea.setIrRpz(NumberConversion.NVL(fmeaPopup.getRPZImpr(), 0));
                fmea.setComType("Functions");
                fmea.setComId(funNewRef);
                fmea.setFmeaHeader(fmeas);
                //fmea.setCreateddate(new Date());
                //fmea.setFmeaComponent(mainDao.FindById(MpgId));
                //fmea.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
                //fmea.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
                fmeaDao.saveFMEA(fmea);

                refreshTableData(jtFmea,fmeas,subHeadings,group);

            }

            /* }catch(Exception ex ){


        }finally{
          setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }*/
        } else {
            JOptionPane.showMessageDialog(null, "You can't add funtion to this level(Sub prodct group)");

        }

        /*  JOptionPane.showMessageDialog(null, "Please add your details now ");
               Fmea f  = new Fmea();
               //f.setFmeaId();
               f.setFmeaComponent(mainProDao.FindById(MpgId));
               f.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
               f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
               f.setCreateddate(new Date());
               fMEADao.saveFMEA(f);
               refreshTableData();
              jtFmea.changeSelection(jtFmea.getRowCount()-1, 0, false, false);*/
    }
    
    public void itemSave(JTable jtFmea,Calchdr fmeas){
        
        DefaultTableModel model = (DefaultTableModel)jtFmea.getModel();
        
         /*-----------------------------------------------------*/
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
                  fmeaDao.UpdateFMEA(getFMEAByRowId(model,fmeas,i));
              }
              
               parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              

              
              JOptionPane.showMessageDialog(null, "Your data save successfully");
             }else {
              JOptionPane.showMessageDialog(null, "No data to save");
              
             }

    
    
    }
    
    
    
    
    
    public void itemEdit(JTable jtFmea,Calchdr fmeas) {
    
    DefaultTableModel model = (DefaultTableModel)jtFmea.getModel();
    int selRow = jtFmea.getSelectedRow();
    Functionalfailures funRef = null;    
    
    
    if(FMEAServices.getSystemLevels(jtFmea.getValueAt(selRow,2).toString())==4){  //jtFmea.getValueAt(selRow,2).toString().contains("-")==true
        try{
        //setCursor(new Cursor(Cursor.WAIT_CURSOR));
        InternalFrameDemo.mainFrame.setLoader();
        Object[] options1 = { "Save","Cancel"};
        FMEAPopup fmeaPopup = new FMEAPopup(fmeas.getCalchdrid());
        
        
            try {
                funRef =funDao.findById(Integer.parseInt(model.getValueAt(selRow,26).toString()));
            } catch (Exception e) {
                e.printStackTrace();
                //funRef = funDao.findByName(model.getValueAt(selRow,3).toString()).get(0);
            }

         

        SubProductComponents subComRef = funRef.getSubComponent();
        fmeaPopup.setSubsystemBreakdownCode(model.getValueAt(selRow,2)+"");
        fmeaPopup.eventSelected(subComRef.getSpgRef(), subComRef, funRef);
        fmeaPopup.setFunction(model.getValueAt(selRow,4)+"");
        fmeaPopup.setPhase(model.getValueAt(selRow,5)+"");
        fmeaPopup.setFailureMode(model.getValueAt(selRow,6)+"");
        fmeaPopup.setFailureCause(model.getValueAt(selRow,7)+"");
        fmeaPopup.setLocalSystemLevel(model.getValueAt(selRow,8)+"");
        fmeaPopup.setTrainSubsystemLevel(model.getValueAt(selRow,9)+"");
        fmeaPopup.setTrainLevel(model.getValueAt(selRow,10)+"");

        fmeaPopup.setImportance(NumberConversion.NVL(model.getValueAt(selRow,11)+"", 0));
        fmeaPopup.setFailureDetection(model.getValueAt(selRow,12)+"");
        fmeaPopup.setPreventiveMeasures(model.getValueAt(selRow,13)+"");
        fmeaPopup.setOccurrence(model.getValueAt(selRow,14)+"");
        fmeaPopup.setDetectionMeasures(model.getValueAt(selRow,15)+"");
        fmeaPopup.setDetection(model.getValueAt(selRow,16)+"");
        fmeaPopup.setRPZ(model.getValueAt(selRow,17)+"");
        fmeaPopup.setImprovementMeasures(model.getValueAt(selRow,18)+"");

        fmeaPopup.setResponsibleDateImpr(model.getValueAt(selRow,19)+"");
        fmeaPopup.setUndertakenMeasures(model.getValueAt(selRow,20)+"");
        fmeaPopup.setImportanceImpr(NumberConversion.NVL(model.getValueAt(selRow,21)+"",0));
        fmeaPopup.setOccurrenceImpr(model.getValueAt(selRow,22)+"");
        fmeaPopup.setDetectionImpr(model.getValueAt(selRow,23)+"");
        fmeaPopup.setRPZImpr(model.getValueAt(selRow,24)+"");

        /*fmeaPopup.setSubProductGroup(new com.topfield.utilities.Item(subComRef.getSpgRef().getSpgId(),subComRef.getSpgRef().getSpgDescription()));
        fmeaPopup.setSubProductComponents(new com.topfield.utilities.Item(subComRef.getSpcId(),subComRef.getSpcDescription()));
        fmeaPopup.setFunctionalComponents(new com.topfield.utilities.Item(funRef.getFailureId(),funRef.getDescription()));
    */




        int result = JOptionPane.showOptionDialog(null, fmeaPopup, "Please enter the details",
                               JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                               null, options1, null);


         if (result == JOptionPane.YES_OPTION) {

              model.setValueAt(fmeaPopup.getFunctionalComponents().getDescription(), selRow, 3);
              model.setValueAt(fmeaPopup.getFunction(), selRow, 4);
              model.setValueAt(fmeaPopup.getPhase(), selRow, 5);
              model.setValueAt(fmeaPopup.getFailureMode(), selRow, 6);
              model.setValueAt(fmeaPopup.getFailureCause(), selRow, 7);
              model.setValueAt(fmeaPopup.getLocalSystemLevel(), selRow, 8);
              model.setValueAt(fmeaPopup.getTrainSubsystemLevel(), selRow, 9);
              model.setValueAt(fmeaPopup.getTrainLevel(), selRow, 10);
              model.setValueAt(fmeaPopup.getImportance(), selRow, 11);
              model.setValueAt(fmeaPopup.getFailureDetection(), selRow, 12);
              model.setValueAt(fmeaPopup.getPreventiveMeasures(), selRow, 13);
              model.setValueAt(fmeaPopup.getOccurrence(), selRow, 14);
              model.setValueAt(fmeaPopup.getDetectionMeasures(), selRow, 15);
              model.setValueAt(fmeaPopup.getDetection(), selRow, 16);
              model.setValueAt(fmeaPopup.getRPZ(), selRow, 17);
              model.setValueAt(fmeaPopup.getImprovementMeasures(), selRow, 18);
              model.setValueAt(fmeaPopup.getResponsibleDateImpr(), selRow, 19);
              model.setValueAt(fmeaPopup.getUndertakenMeasures(), selRow, 20);
              model.setValueAt(fmeaPopup.getImportanceImpr(), selRow, 21);
              model.setValueAt(fmeaPopup.getOccurrenceImpr(), selRow, 22);
              model.setValueAt(fmeaPopup.getDetectionImpr(), selRow, 23);
              model.setValueAt(fmeaPopup.getRPZImpr(), selRow, 24);
              model.setValueAt("Functions", selRow, 25);
              model.setValueAt(fmeaPopup.getFunctionalComponents().getId(), selRow, 26);

              fmeaDao.UpdateFMEA(getFMEAByRowId(model,fmeas,selRow));

          }

        }catch(Exception ex ){
           ex.printStackTrace();

        }finally{
          InternalFrameDemo.mainFrame.removeLoder();
                  //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }
    
    }else{
      JOptionPane.showMessageDialog(null,"No funtion available to edit the current row");  
    
    }
    }
    
    public void itemRemove(JTable jtFmea){
        
               int modelRow = jtFmea.getSelectedRow();
                if(modelRow >= 0){
                    fmeaDao.deleteFMEA(Integer.parseInt(jtFmea.getValueAt(modelRow,0)+""));
                  ((DefaultTableModel)jtFmea.getModel()).removeRow(modelRow);
                   // System.out.println("getValueAt(modelRow,0)"+jtFmea.getValueAt(modelRow-1,0));
                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }
    
    
    }
    
    public String[] getTableHeadingGlobal() {
        String[] columnNames = {"Id", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Severity (S)", "Failure detection", "Preventive and compensating measures", "Occurrence (O)", "Detection measures", "Detection (D)", "RPN", "Improvement measures", "Responsible / Date", "Undertaken measures", "Severity_I (S)", "Occurrence_I (O)", "Detection_I (D)", "RPN_I","Remarks", "ComType", "ComId"};
        return columnNames;
    }
    
    
    public List<HashMap<String,String>> getFMEAData(Calchdr fmeas){
    
     String[] columnNames = getTableHeadingGlobal();
   
     List<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
     HashMap<String, String> hmap;
        
      List<Fmea> fmeaList = fmeaDao.getAllFMEAByHdr(fmeas.getCalchdrid()); 
      
      for (Fmea fmea : fmeaList) {
            hmap = new HashMap<String, String>();
          
            hmap.put(columnNames[0], 	fmea.getFmeaId                    ()+"");
            hmap.put(columnNames[1],	fmea.getFISubsysBreakdownCode     ());
            hmap.put(columnNames[2], 	fmea.getFISubsysComponent         ());
            hmap.put(columnNames[3], 	fmea.getFIFunctions               ());
            hmap.put(columnNames[4], 	fmea.getFIPhase                   ());
            hmap.put(columnNames[5], 	fmea.getFIFailuremode             ());
            hmap.put(columnNames[6], 	fmea.getFIFailureCause            ());
            hmap.put(columnNames[7], 	fmea.getFELocalSystemLevel        ());
            hmap.put(columnNames[8], 	fmea.getFETrainSubsystemLevel     ());
            hmap.put(columnNames[9],  	fmea.getFETrainLevel              ());
            hmap.put(columnNames[10], 	fmea.getFEImportanceB             ()+"");
            hmap.put(columnNames[11], 	fmea.getDRMFailuredetection       ());
            hmap.put(columnNames[12], 	fmea.getDRMPrevAndcompenmeasures  ());
            hmap.put(columnNames[13], 	fmea.getDRMOccurrenceA            ()+"");
            hmap.put(columnNames[14], 	fmea.getDRMDetectionmeasures      ());
            hmap.put(columnNames[15], 	fmea.getDRMDetectionE             ()+"");
            hmap.put(columnNames[16], 	fmea.getDrmRpz                    ()+"");
            hmap.put(columnNames[17], 	fmea.getDRMImprovementmeasures    ());
            hmap.put(columnNames[18], 	fmea.getDRMResponsibleDate        ());
            hmap.put(columnNames[19], 	fmea.getIRUndertakenmeasures      ());
            hmap.put(columnNames[20], 	fmea.getIRImportanceB             ()+"");
            hmap.put(columnNames[21], 	fmea.getIROccurrenceA             ()+"");
            hmap.put(columnNames[22], 	fmea.getIRDetectionE              ()+"");
            hmap.put(columnNames[23], 	NumberConversion.NVL(fmea.getIrRpz()+"",0)+"");
            hmap.put(columnNames[24], 	fmea.getRemarks());
            hmap.put(columnNames[25], 	fmea.getComType());
            hmap.put(columnNames[26], 	fmea.getComId()+"");
          
            mapList.add(hmap);
      }
        
       return mapList;
    }
    
   public List<HashMap<String,String>> getFMEAData2(Calchdr calcHdr){
    
     String[] columnNames = getTableHeadingGlobal();
   
     List<HashMap<String,String>> mapList = new ArrayList<HashMap<String,String>>();
     HashMap<String, String> hmap;
     Mpghdr mpgHdr = calcHdr.getMpghdr();
     Collection<CalcFunctions> calcFunList = calcFunctionsDao.getAllCalcFunctionsByMpgheader(calcHdr.getMpghdr().getMpghdrid()); 
     MainProductGroup mpg = mpgHdr.getFmeaComponent();
     Calcfile masterData;
     List<Fmea> fmeacol;
      //List<Fmea> fmeaList = fMEADao.getAllFMEAByHdr(mpgHdr.getMpghdrid());
      
      
            hmap = new HashMap<String, String>();
            hmap.put(columnNames[0], 	mpgHdr.getMpghdrid()+"");
            hmap.put(columnNames[1],	"R"+mpg.getMpgRef());
            hmap.put(columnNames[2], 	mpgHdr.getDescriptions());
            hmap.put(columnNames[25], 	"MainProduct");
            hmap.put(columnNames[26], 	mpgHdr.getMpghdrid()+"");
            mapList.add(hmap);
      
      
       for (CalcFunctions calcFun : calcFunList) {
            hmap = new HashMap<String, String>();
            fmeacol = fmeaDao.getAllFMEAByMPGHdrFun(calcHdr.getCalchdrid(), calcFun.getCalcFunId());
            
            if((fmeacol.size()>0 || calcHdr.getApproach()==1)){
            
            hmap.put(columnNames[0], 	calcFun.getCalcFunId()+"");
            hmap.put(columnNames[1],	calcFun.getFunId());
            hmap.put(columnNames[2], 	calcFun.getCalcFunction());
            hmap.put(columnNames[25], 	"Functions");
            hmap.put(columnNames[26], 	calcFun.getCalcFunId()+"");
            mapList.add(hmap);
            
            for (Fmea fmea : fmeacol) {
            hmap = new HashMap<String, String>();
             masterData =  fmea.getCalcComp();
          
            hmap.put(columnNames[0], 	fmea.getFmeaId                    ()+"");
            /*hmap.put(columnNames[1],	fmea.getFISubsysBreakdownCode     ());*/
            hmap.put(columnNames[1],	masterData.getCompId()              );
            /*hmap.put(columnNames[2], 	fmea.getFISubsysComponent         ());*/
            hmap.put(columnNames[2], 	masterData.getComponents()          );
            hmap.put(columnNames[3], 	fmea.getFIFunctions               ());
            hmap.put(columnNames[4], 	fmea.getFIPhase                   ());
            /*hmap.put(columnNames[5], 	fmea.getFIFailuremode             ());*/
            hmap.put(columnNames[5], 	masterData.getFailureMode()         );
            hmap.put(columnNames[6], 	fmea.getFIFailureCause            ());
            hmap.put(columnNames[7], 	fmea.getFELocalSystemLevel        ());
            hmap.put(columnNames[8], 	fmea.getFETrainSubsystemLevel     ());
            hmap.put(columnNames[9],  	fmea.getFETrainLevel              ());
            hmap.put(columnNames[10], 	fmea.getFEImportanceB             ()+"");
            hmap.put(columnNames[11], 	fmea.getDRMFailuredetection       ());
            hmap.put(columnNames[12], 	fmea.getDRMPrevAndcompenmeasures  ());
            hmap.put(columnNames[13], 	fmea.getDRMOccurrenceA            ()+"");
            hmap.put(columnNames[14], 	fmea.getDRMDetectionmeasures      ());
            hmap.put(columnNames[15], 	fmea.getDRMDetectionE             ()+"");
            hmap.put(columnNames[16], 	fmea.getDrmRpz                    ()+"");
            hmap.put(columnNames[17], 	fmea.getDRMImprovementmeasures    ());
            hmap.put(columnNames[18], 	fmea.getDRMResponsibleDate        ());
            hmap.put(columnNames[19], 	fmea.getIRUndertakenmeasures      ());
            hmap.put(columnNames[20], 	fmea.getIRImportanceB             ()+"");
            hmap.put(columnNames[21], 	fmea.getIROccurrenceA             ()+"");
            hmap.put(columnNames[22], 	fmea.getIRDetectionE              ()+"");
            hmap.put(columnNames[23], 	NumberConversion.NVL(fmea.getIrRpz()+"",0)+"");
            hmap.put(columnNames[24], 	fmea.getRemarks());
            hmap.put(columnNames[25], 	fmea.getComType());
            hmap.put(columnNames[26], 	fmea.getComId()+"");  
          
            mapList.add(hmap);
         }
         }
       }
      
      
      
        
       return mapList;
    }
   
   
   public Object[][] refreshTableData2(JTable jtFmea,  Calchdr mpgHdr,String [] subHeadings,int[] group){
        int columCount =0;
        int totalColCount =jtFmea.getColumnCount();
        DefaultTableModel model = (DefaultTableModel)jtFmea.getModel();    
        List<HashMap<String,String>> dbData = getFMEAData2(mpgHdr);
        String data[][] = new String[dbData.size()][subHeadings.length];
        
         for (HashMap<String, String> hashMap : dbData) {
            
           data[columCount][0] = (columCount+1)+"";
            
            for (int i = 1; i < subHeadings.length; i++) {
                 
             data[columCount][i] =  hashMap.get(subHeadings[i]);
             
            }

            columCount++;
         }
        

    
      // model = new DefaultTableModel(data,subHeadings); //getTableHeading()
       if (mpgHdr.getStandard().getId()==1) {
           subHeadings[13]="Severity Class";
       }
      
       model = new DefaultTableModel(data,subHeadings) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
       jtFmea.setModel(model);
       setGroupheading(jtFmea,subHeadings,group);
      // setUpSportColumn(jtFmea, jtFmea.getColumnModel().getColumn(13));
        
      TableColumn column1 = null;  
      for (int i = 0; i < model.getColumnCount(); i++) {
        column1 = jtFmea.getColumnModel().getColumn(i);
       // column1.setCellRenderer(new WordWrapCellRenderer());
        if (i == 0) {
            column1.setPreferredWidth(30);
        } else if (i == 1  ) { //|| i==16 || 1==17
            column1.setPreferredWidth(40);
        }else if (i == (subHeadings.length-2)  ) { //|| i==16 || 1==17
            column1.setMinWidth(0);
            column1.setMaxWidth(0);
        }else if (i == (subHeadings.length-1)  ) { //|| i==16 || 1==17
            column1.setMinWidth(0);
            column1.setMaxWidth(0);
        }else {
            column1.setPreferredWidth(250);
        }
      } 

      
      
 return data;
}
   

    
    public Object[][] refreshTableData(JTable jtFmea,  Calchdr mpgHdr,String [] subHeadings,int[] group){
        int columCount =0;
        DefaultTableModel model = (DefaultTableModel)jtFmea.getModel();    
        List<HashMap<String,String>> dbData = getFMEAData(mpgHdr);
        String data[][] = new String[dbData.size()][subHeadings.length+1];
        
         for (HashMap<String, String> hashMap : dbData) {
            
           data[columCount][0] = (columCount+1)+"";
            
            for (int i = 1; i < subHeadings.length; i++) {
                 
             data[columCount][i] =  hashMap.get(subHeadings[i]);
             
            }

            columCount++;
         }
        

    
      // model = new DefaultTableModel(data,subHeadings); //getTableHeading()
       model = new DefaultTableModel(data,subHeadings) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
       jtFmea.setModel(model);
       setGroupheading(jtFmea,subHeadings,group);

        
      TableColumn column1 = null;  
      for (int i = 0; i < model.getColumnCount(); i++) {
        column1 = jtFmea.getColumnModel().getColumn(i);
       // column1.setCellRenderer(new WordWrapCellRenderer());
        if (i == 0) {
            column1.setPreferredWidth(30);
        } else if (i == 1) {
            column1.setPreferredWidth(30);
        }else {
            column1.setPreferredWidth(250);
        }
      } 

      
      
 return data;
}
    
    
  public void setGroupheading(JTable jtFmea,String [] subHeadings,int[] group){
  TableColumnModel cm = jtFmea.getColumnModel();
  
        
         
            ColumnGroup fi = new ColumnGroup("<html> <h3> Failure Identification </h3></html>",Color.BLACK);
             for (int i = 1; i < group[0]; i++) {
                fi.add(cm.getColumn(i));
                 System.out.println(cm.getColumn(i));
             }
            ColumnGroup fe = new ColumnGroup("<html> <h3>Failure Effects </h3></html>",Color.DARK_GRAY);
             for (int i = group[0]; i < group[1]; i++) {
                fe.add(cm.getColumn(i));
                
                 System.out.println(cm.getColumn(i));
             }
            ColumnGroup drm = new ColumnGroup("<html> <h3>Detection and Recovery Measures </h3></html>",Color.BLACK);
             for (int i = group[1]; i < group[2]; i++) {
                drm.add(cm.getColumn(i));
                 System.out.println(cm.getColumn(i));
             }
             ColumnGroup ir = new ColumnGroup(" ",Color.DARK_GRAY); //<html> <h3>Improvement Results </h3></html>
             for (int i = group[2]; i < subHeadings.length; i++) {
                 ir.add(cm.getColumn(i));
                 System.out.println(cm.getColumn(i));
             }
            
            /*g_name.add(cm.getColumn(1));
            g_name.add(cm.getColumn(2));
            ColumnGroup g_lang = new ColumnGroup("Language");
            g_lang.add(cm.getColumn(3));
            ColumnGroup g_other = new ColumnGroup("Others");
            g_other.add(cm.getColumn(4));
            g_other.add(cm.getColumn(5));
            g_lang.add(g_other);*/
    
            GroupableTableHeader header = (GroupableTableHeader)jtFmea.getTableHeader();
            header.addColumnGroup(fi);
            header.addColumnGroup(fe); 
            header.addColumnGroup(drm); 
            header.addColumnGroup(ir); 
            header.setToolTipStrings(subHeadings);  
        

}
  
  
 public  List<Integer> getAddedList(JTable jtFmea){
     List<Integer> addedList = new ArrayList<Integer>();
     int rowct =jtFmea.getRowCount();
     int colct =jtFmea.getColumnCount();
     
        for (int i = 0; i < rowct ; i++) {
            if((jtFmea.getValueAt(i, colct-2)+"").contentEquals("SubComp")){
                addedList.add(NumberConversion.NVL(jtFmea.getValueAt(i, colct-1)+"", 0));
                //System.out.println("AddedList - "+jtFmea.getValueAt(i, colct-1));
            }
        }
    
      return addedList;
    }

    
    public void insertFmea(int calcId,Calchdr mpgHdr){
       Calcfile calcfile = calcfileDao.findById(calcId);
       Fmea fmeaData = new Fmea();
       
       fmeaData.setFISubsysBreakdownCode(calcfile.getCompId());
       fmeaData.setFISubsysComponent(calcfile.getComponents());
       fmeaData.setDrmRpz(1);
       fmeaData.setComType("SubComp");
       fmeaData.setComId(calcfile.getCalcId());
       fmeaData.setCalcComp(calcfile);
       fmeaData.setFmeaHeader(mpgHdr);    
       
       fmeaDao.saveFMEA(fmeaData);       

    }  
    
    
    public void margeData(int mpgHdrId){
    Calcfile calc;
    
        try {
            
        InternalFrameDemo.mainFrame.setLoader();
        
            //System.out.println("cbgnjtm,u ---"+fmeaDao.getAllFMEAByHdr(mpgHdrId).size());
            
        for (Fmea fmea : fmeaDao.getAllFMEAByHdr(mpgHdrId)) {
            calc = fmea.getCalcComp();
            fmea.setFISubsysBreakdownCode(calc.getCompId());
            fmea.setFISubsysComponent(calc.getComponents());
            fmea.setFIFailuremode(calc.getFailureMode());
            fmeaDao.UpdateFMEA(fmea);
            
            System.out.println("dscvfbrgntu------------------------------");
        }

            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
           InternalFrameDemo.mainFrame.removeLoder();
        
        }
        
    
    }

    public void AddFuntion(Mpghdr mpghdr,Object cal,String std){
     int resultRef=-2;
     
         try {
               InternalFrameDemo.mainFrame.setLoader();
                resultRef = calcCurd.addRowFuntion(mpghdr,new CalcFunctionsPopup());
            } finally{
                 if (resultRef==JOptionPane.YES_NO_OPTION) {
                     if (std.equals("MIL")) {
                         ((FmeaMil1629A)cal). refreshData();
                     }else if (std.equals("RPN")) {
                         ((FmeaRPN)cal). refreshData();
                     }
                     
                 }
               
                InternalFrameDemo.mainFrame.removeLoder();
            }
    }
    
  public void editRowCalFuntion(int calId,Mpghdr mpghdr,Object cal,String std){
  int result;
      
      try {
          InternalFrameDemo.mainFrame.setLoader();
          result = calcCurd.editRowFuntion(mpghdr, calId, new CalcFunctionsPopup());
          if (result == JOptionPane.YES_OPTION) {
              
                      if (std.equals("MIL")) {
                         ((FmeaMil1629A)cal). refreshData();
                     }else if (std.equals("RPN")) {
                         ((FmeaRPN)cal). refreshData();
                     }
          }
          

      } finally {
          InternalFrameDemo.mainFrame.removeLoder();
      }


}
  
    public CalcFunctions getSelCalcFun(JTable jtFmea) {
     CalcFunctions res= null;
     int selRow = jtFmea.getSelectedRow();
     int level = FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(),jtFmea);

        System.out.println("level 88888888888888 "+level);
        if (level==3) {
            res = calcFunctionsDao.findById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1)+"", -5));
        }else if(level ==4){
            res = fmeaDao.getFMEAById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1)+"", 0)).getCalcComp().getCalcHeader();
        }
    return res;
    }


}
