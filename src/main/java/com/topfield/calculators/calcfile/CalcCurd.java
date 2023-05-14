/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.controller.BusinessException;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.DatareferDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Mpghdr;
import com.topfield.settings.FrameSettings;
import com.topfield.user.UserInfo;
import com.topfield.utilities.ColumnGroup;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.StringFuntions;
import com.topfield.view.popup.CalcFunctionsPopup;
import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Murali
 */
public class CalcCurd {
    
    private CalcFunctionsDao calcFunctionsDao = new CalcFunctionsDaoImpl();
    private CalcfileDao CalcfileDao = new CalcfileDaoImpl();
    private DatareferDao datareferDao = new DatareferDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    
    public void refreshTableData(JTable jtCalc, Mpghdr mpgHdr) {

        //int columCount = 0;
        int mainColumCount = 0;
        int subColumCount = 0;
        String data[] = null;
        Collection<Calcfile> calcfileRef  = null;
        //DefaultTableModel model = (DefaultTableModel) jtCalc.getModel();
        DefaultTableModel model = new DefaultTableModel(getTableHeadingString(), 0){
          public boolean isCellEditable(int row, int column)
            {
                if (column ==0 ||column ==1 ||(column ==4 && isCCF(jtCalc, row) )||column ==5 || column == 10 || column == 11) {
                   return false;//This causes all cells to be not editable
                } else {
                     return true;//This causes all cells to be not editable
                }
             
            }
          };

        List<CalcFunctions> calcFunList = calcFunctionsDao.getAllCalcFunctionsByMpgheader(mpgHdr.getMpghdrid());
        data = new String[getTableHeadingString().length + 1];
        
        
        if (calcFunList.size()==0) {
            
            for (int i = 0; i < data.length; i++) {
                 data[i] = "";
            }
            model.addRow(data);
        }else{
        
            for (CalcFunctions calcFun : calcFunList) {

                //calcfileRef = calcFun.getCalcfileCollection();
                calcfileRef = CalcfileDao.getAllCalcfileByCalcFun(calcFun.getCalcFunId());
                subColumCount =0;

                data[0] = calcFun.getCalcFunId() + "";
                data[1] = (mainColumCount + 1) + "";
                data[2] = calcFun.getFunId();
                data[3] = calcFun.getCalcFunction();
                data[4] = calcFun.getCalcFunType();
                data[5] = "";
                data[6] = "Targets";
                data[7] = "From - ";
                data[8] = NumberConversion.scientificNotation(calcFun.getSilTargetFrom(),2);
                data[9] = "To -";
                data[10] = NumberConversion.scientificNotation(calcFun.getSilTargetTo(),2);
                data[11] = calcFun.getSil();
                data[12] = calcFun.getRemarks();

                model.addRow(data);
               // columCount++;

                if (calcfileRef != null) {


                for (Calcfile calcfile : calcfileRef) {

                    data[0] = calcfile.getCalcId() + "";
                    data[1] = (mainColumCount + 1) + "."+(subColumCount + 1) + "";
                    data[2] = calcfile.getCompId();
                    data[3] = calcfile.getComponents();
                    data[4] = isCCFByFM(calcfile.getCompId())? "" : calcfile.getFailureMode();
                    data[5] = (calcfile.getFailureRate()*Math.pow(10, 9))+"";
                    data[6] = NumberConversion.scientificNotation(calcfile.getFailureRate(),2);
                    data[7] = NumberConversion.scientificNotation((calcfile.getProbability()== null)? 0:calcfile.getProbability(),2);
                    data[8] = calcfile.getFMPercentage()+"";
                    data[9] = calcfile.getURPercentage()+"";
                    data[10] = "";
                    data[11] = calcfile.getReference().getDescription();
                    data[12] = calcfile.getRemarks();
                    model.addRow(data);
                    subColumCount++;
                   // columCount++;
                }
               }
                mainColumCount++;
            }
        }
        //model = new DefaultTableModel(data, getTableHeadingString());
        jtCalc.setModel(model);
        setGroupheading(jtCalc, mpgHdr.getDescriptions(),mpgHdr.getMpghdrid());


        TableColumn column1 = null;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column1 = jtCalc.getColumnModel().getColumn(i);
            if (i == 0) {
                column1.setPreferredWidth(40); //sport column is bigger
            } else if (i == 1) {
                column1.setPreferredWidth(60);
            }else if (i == 2) {
                column1.setPreferredWidth(100);
            }else if (i == 3) {
                column1.setPreferredWidth(350);
            }else if (i == 4) {
                column1.setPreferredWidth(100);
            }else if (i == 10||i == 11) {
                column1.setPreferredWidth(120);
            } else {
                column1.setPreferredWidth(70);
            }
        }

       // return data;
    }
    
    
    public Object[][] refreshTableDataOld(JTable jtCalc, Mpghdr mpgHdr) {

        int columCount = 0;
        int mainColumCount = 0;
        int subColumCount = 0;
        String data[][] = null;
        Collection<Calcfile> calcfileRef  = null;
        //DefaultTableModel model = (DefaultTableModel) jtCalc.getModel();
        DefaultTableModel model = new DefaultTableModel(getTableHeadingString(), 0);

        List<CalcFunctions> calcFunList = calcFunctionsDao.getAllCalcFunctionsByMpgheader(mpgHdr.getMpghdrid());
        data = new String[20][getTableHeadingString().length + 1];
        
        
        for (CalcFunctions calcFun : calcFunList) {
   
            calcfileRef = calcFun.getCalcfileCollection();
            
            data[columCount][0] = calcFun.getCalcFunId() + "";
            data[columCount][1] = (mainColumCount + 1) + "";
            data[columCount][2] = calcFun.getFunId();
            data[columCount][3] = calcFun.getCalcFunction();
            data[columCount][4] = calcFun.getCalcFunType();
            data[columCount][5] = "Targets";
            data[columCount][6] = calcFun.getSilTargetFrom()+"";
            data[columCount][7] = calcFun.getSilTargetTo()+"";
            data[columCount][8] = calcFun.getSilTargetFrom()+"";
            data[columCount][9] = calcFun.getSilTargetFrom()+"";
            data[columCount][10] = calcFun.getSil();
            data[columCount][11] = calcFun.getRemarks();
            
            columCount++;
            
            for (Calcfile calcfile : calcfileRef) {
                subColumCount =0;
                data[columCount][0] = calcfile.getCalcId() + "";
                data[columCount][1] = (subColumCount + 1) + "";
                data[columCount][2] = calcfile.getCompId();
                data[columCount][3] = calcfile.getComponents();
                data[columCount][4] = calcfile.getFailureMode();
                data[columCount][5] = calcfile.getFailureRate()+"";
                data[columCount][6] = calcfile.getFailureRate()+"";
                data[columCount][7] = calcfile.getFMPercentage()+"";
                data[columCount][8] = calcfile.getURPercentage()+"";
                data[columCount][9] = "";
                data[columCount][10] = calcfile.getReference().getDescription();
                data[columCount][11] = calcfile.getRemarks();
                
                subColumCount++;
                columCount++;
            }

            mainColumCount++;
        }

        //model = new DefaultTableModel(data, getTableHeadingString());
        jtCalc.setModel(model);
        setGroupheading(jtCalc, mpgHdr.getFmeaComponent().getMpgDescription(),mpgHdr.getFmeaComponent().getMpgId());


        TableColumn column1 = null;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column1 = jtCalc.getColumnModel().getColumn(i);
            if (i == 0) {
                column1.setPreferredWidth(50); //sport column is bigger
            } else if (i == 1) {
                column1.setPreferredWidth(150);
            } else {
                column1.setPreferredWidth(250);
            }
        }

        return data;
    }

    public String[] getTableHeadingString() {
        String[] columnNames = {"Id", "No", "Subsystem Code", "Subsystem/ Component", "(Mode) Failure mode",
                                "FITS Failure Rate(FITS)","FR/h Failure Rate (/h)","Probability","FM % Failure Mode Percentage",
                                "URF % Unrevealed Failure Percentage","URF/h Unrevealed Failure rate (/h)","Reference","Remarks"};
        return columnNames;
    }
    
    public void setGroupheading(JTable jtFmea, String mpgName,int calcId) {
        TableColumnModel cm = jtFmea.getColumnModel();

        ColumnGroup fi = new ColumnGroup("<html> <h3> Calculation File - "+mpgName+" ("+calcId+") </h3></html>", Color.BLACK);
        for (int i = 1; i <= 12; i++) {
            fi.add(cm.getColumn(i));
            // System.out.println(cm.getColumn(i));
        }
        /*ColumnGroup fe = new ColumnGroup("<html> <h3>Failure Effects </h3></html>", Color.DARK_GRAY);
        for (int i = 7; i < 11; i++) {
            fe.add(cm.getColumn(i));

            // System.out.println(cm.getColumn(i));
        }
        ColumnGroup drm = new ColumnGroup("<html> <h3>Detection and Recovery Measures </h3></html>", Color.BLACK);
        for (int i = 11; i < 13; i++) {
            drm.add(cm.getColumn(i));
            // System.out.println(cm.getColumn(i));
        }
        ColumnGroup ir = new ColumnGroup("<html> <h3>Failure Criticality </h3></html>", Color.DARK_GRAY);
        for (int i = 13; i < getTableHeadingString().length; i++) {
            ir.add(cm.getColumn(i));
            // System.out.println(cm.getColumn(i));
        }*/

        GroupableTableHeader header = (GroupableTableHeader) jtFmea.getTableHeader();
        header.addColumnGroup(fi);
        /*header.addColumnGroup(fe);
        header.addColumnGroup(drm);
        header.addColumnGroup(ir);*/
        header.setToolTipStrings(getTableHeadingString());

    }
    
    public boolean isFunction(JTable jCalc,int row){
     boolean res=false;
     String sunSysCode = jCalc.getValueAt(row, 11)+"";
     
       if (sunSysCode.contains("SIL")) {
             res = true;
       }
    
     return res;
    }
    
    public boolean isCCF(JTable jCalc,int row){
     boolean res=false;
     String sunSysCode = jCalc.getValueAt(row, 2)+"";
     //String mode = jCalc.getValueAt(row, 4)+"";
     
       if (sunSysCode.contains("CCF") /*&& mode.contains("CCF")*/) {
             res = true;
       }
    
     return res;
    }
    
    public boolean isCCFByFM(String failmode){
     boolean res=false;
     
       if (failmode.contains("CCF") /*&& mode.contains("CCF")*/) {
             res = true;
       }
    
     return res;
    }
    
    public CalcFunctions getCalcFunctionsByRow(JTable jCalc,int row){
    CalcFunctions res = null;
    int calcId  = NumberConversion.NVL(jCalc.getValueAt(row, 0)+"", 0);
    
        if (isFunction(jCalc,row)) {
            res = calcFunctionsDao.findById(calcId);
        } else {
            res = CalcfileDao.findById(calcId).getCalcHeader();
        }
    
    return res;
    }
    
    
    public CalcFunctions getCALCFunByRowId(DefaultTableModel model,Mpghdr mpghdr,int rowId){

          //CalcFunctions CalcFun = new CalcFunctions();
          CalcFunctions CalcFun = calcFunctionsDao.findById(NumberConversion.NVL(model.getValueAt(rowId, 0)+"", 0));
   
               //CalcFun.setCalcFunId(NumberConversion.NVL(model.getValueAt(rowId, 0)+"", rowId));
               //CalcFun.setCalcFunType(model.getValueAt(rowId, 4)+"");
               CalcFun.setFunId(model.getValueAt(rowId, 2)+"");
               CalcFun.setCalcFunction(model.getValueAt(rowId, 3)+"");
               //CalcFun.setSil(model.getValueAt(rowId, 10)+"");
               CalcFun.setSilTargetFrom(NumberConversion.NVLDouble(model.getValueAt(rowId, 8)+"", rowId));
               CalcFun.setSilTargetTo(NumberConversion.NVLDouble(model.getValueAt(rowId, 10)+"", rowId));
               CalcFun.setRemarks(model.getValueAt(rowId, 12)+"");
               //CalcFun.setMpgheader(mpghdr);

      
   return CalcFun;     
  }
    
  public Calcfile getCALCByRowId(DefaultTableModel model,Mpghdr mpghdr,int rowId){

              Calcfile CalcFile = CalcfileDao.findById(NumberConversion.NVL(model.getValueAt(rowId, 0)+"", rowId));
   
               //CalcFile.setCalcId(NumberConversion.NVL(model.getValueAt(rowId, 0)+"", rowId));
               CalcFile.setCompId(model.getValueAt(rowId, 2)+"");
               CalcFile.setComponents(model.getValueAt(rowId, 3)+"");
               CalcFile.setFailureRate(NumberConversion.NVLDouble(model.getValueAt(rowId, 6)+"", rowId));
               CalcFile.setProbability(NumberConversion.NVLDouble(model.getValueAt(rowId, 7)+"", rowId));
               CalcFile.setFailureMode(model.getValueAt(rowId, 4)+"");
               CalcFile.setFMPercentage(NumberConversion.NVLDouble(model.getValueAt(rowId, 8)+"", rowId));
               CalcFile.setURPercentage(NumberConversion.NVLDouble(model.getValueAt(rowId, 9)+"", rowId));
               CalcFile.setRemarks(model.getValueAt(rowId, 12)+"");
               //CalcFile.setReference(datareferDao.findByName(model.getValueAt(rowId, 10)+""));
               //CalcFile.setCalcHeader(calcCurd.getCalcFunctionsByRow(jtCalc, selectedRow));

   return CalcFile;     
  }  
  
  
  
   public int addRowFuntion(Mpghdr mpghdr, CalcFunctionsPopup calcFunctionsPopup){
 
     Object[] options1 = {"Add", "Cancel"};
     CalcFunctions CalcFun= new CalcFunctions();
    
     
         
          int result = JOptionPane.showOptionDialog(null, calcFunctionsPopup, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

           if (result == JOptionPane.YES_OPTION) {
               
               
               if (StringFuntions.isNotEmptyString(calcFunctionsPopup.getFunctionId())
                       && StringFuntions.isNotEmptyString(calcFunctionsPopup.getFunctionName())) {
               
                    CalcCommonService.createCalcFunction(-1,calcFunctionsPopup.getFunctionType(),
                                                         calcFunctionsPopup.getFunctionId(),
                                                         calcFunctionsPopup.getFunctionName(), 
                                                         calcFunctionsPopup.getSILType(),
                                                         calcFunctionsPopup.getSILTargetFrom(),
                                                         calcFunctionsPopup.getSILTargetTo(),
                                                         calcFunctionsPopup.getRemarks(), mpghdr,false);
                                  
               }else{
                   BusinessException.showBusinessException("CM0207001", "Function Id and Function Name cannot be empty. Please enter a valid name.", JOptionPane.WARNING_MESSAGE);
                 result =  addRowFuntion(mpghdr,calcFunctionsPopup);
                   
               }
              
               
           }

  return result;
  
 }   
    
   
   public int editRowFuntion(Mpghdr mpghdr,int calcFunId,CalcFunctionsPopup calcFunctionsPopup){
    
     
     Object[] options1 = {"Save", "Cancel"};
     CalcFunctions CalcFun= null;

         CalcFun = calcFunctionsDao.findById(calcFunId);
            calcFunctionsPopup.setFunctionType(CalcFun.getCalcFunType());
            calcFunctionsPopup.setFunctionId(CalcFun.getFunId());
            calcFunctionsPopup.setFunctionName(CalcFun.getCalcFunction());
            calcFunctionsPopup.setSILType(CalcFun.getSil());
            calcFunctionsPopup.setSILTargetFrom(NumberConversion.NVLDouble(CalcFun.getSilTargetFrom().toString(), 0));
            calcFunctionsPopup.setSILTargetTo(NumberConversion.NVLDouble(CalcFun.getSilTargetTo().toString(), 0));
            calcFunctionsPopup.setRemarks(CalcFun.getRemarks());  
     
          int result = JOptionPane.showOptionDialog(null, calcFunctionsPopup, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);
           
           if (result == JOptionPane.YES_OPTION) {
               
              //InternalFrameDemo.mainFrame.setLoader();  
               if (StringFuntions.isNotEmptyString(calcFunctionsPopup.getFunctionId())
                       && StringFuntions.isNotEmptyString(calcFunctionsPopup.getFunctionName())) {
                   
               CalcCommonService.createCalcFunction(calcFunId,calcFunctionsPopup.getFunctionType(),
                                                    calcFunctionsPopup.getFunctionId(),
                                                    calcFunctionsPopup.getFunctionName(), 
                                                    calcFunctionsPopup.getSILType(),
                                                    calcFunctionsPopup.getSILTargetFrom(),
                                                    calcFunctionsPopup.getSILTargetTo(),
                                                    calcFunctionsPopup.getRemarks(), mpghdr,true);
                }else{
                   BusinessException.showBusinessException("CM0207001", "Function Id and Function Name cannot be empty. Please enter a valid name.", JOptionPane.WARNING_MESSAGE);
                   result =  editRowFuntion(mpghdr, calcFunId, calcFunctionsPopup);
                   
               }
           }
           
           
     return result;
 }  
   

}
