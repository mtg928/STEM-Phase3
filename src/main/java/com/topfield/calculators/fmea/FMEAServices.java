/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.topfield.calculators.fmeca.FMECACrud;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.info.FMEAImportInfo_old;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Fmea;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import com.topfield.themes.DataEntryPopup;
import com.topfield.utilities.CopyAction;
import com.topfield.utilities.ExcelAdapter;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.PasteAction;
import com.topfield.user.UserInfo;
import com.topfield.view.popup.ProgressBar;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.persistence.Column;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ProgressMonitor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Murali
 */
public class FMEAServices {

    private FMEADao fMEADao = new FMEADaoImpl();
    private FMECADao fMECADao = new FMECADaoImpl();
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
    private FunctionalfailuresDao funfailDao = new FunctionalfailuresDaoImpl();

    /*------------------------------------Add-------------------------------------*/
    public String generateSubSystemCode(String mainRef, int compId, DefaultTableModel model) {
        String res = "";
        try {
            res = mainRef.substring(0, mainRef.indexOf('-'));
        } catch (StringIndexOutOfBoundsException e) {
            res = mainRef;
        }

        //res = res+"-"+funDao.getAllFunctionCountByCompId(compId);
        res = res + "-" + findFuntionCount(mainRef, model);
        return res;
    }

    public int findFuntionCount(String subSystemCode, DefaultTableModel model) {
        int res = 0;
        String subSystemCodeRef = subSystemCode.substring(0, UserInfo.getInstance().getProAbbreviations().length() + 4);
        String subSystemCodeRef2 = "";

        for (int i = 0; i < model.getRowCount(); i++) {

            if (model.getValueAt(i, 2).toString().length() >= UserInfo.getInstance().getProAbbreviations().length() + 4) {

                subSystemCodeRef2 = model.getValueAt(i, 2).toString().substring(0, UserInfo.getInstance().getProAbbreviations().length() + 4);

                if (subSystemCodeRef.equals(subSystemCodeRef2)) {
                    res++;
                }

            }

        }

        return res;
    }

    /*------------------------------------Add-------------------------------------*/
 /*------------------------------------load-------------------------------------*/
    public void itemLoad(JTable jtFmea, Calchdr fmeas, String[] subHeadings, String cal, Object crud) {

        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        if (cal.contentEquals("FMEA")) {
            ((FmeaCurd) crud).refreshTableData(jtFmea, fmeas, subHeadings, new int[]{8,12,20});
        } else if (cal.contentEquals("FMECA")) {
            ((FMECACrud) crud).refreshTableData(jtFmea, fmeas.getCalchdrid());
        }

        MainProductGroup mainPro = fmeas.getMpghdr().getFmeaComponent();

        int intialNumber = model.getRowCount();
        String ref = UserInfo.getInstance().getProAbbreviations() + "_" + mainPro.getMpgRef();
        String ref2 = ref;
        ProgressBar progress = new ProgressBar();
        int pogressCount = mainPro.getSubProductGroupCollection().size();
        progress.setBountry(intialNumber, 200);
        int itemCount = 0;
        int funCount = 0;

        //JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint."+getTotalRowCount(mainPro));  
        Object[] columnNames /*= {"",++intialNumber,ref,mainPro.getMpgDescription(),"","","","","",
                                 "","","","","","","","","","","","","","","",""}*/;
        //model.addRow(columnNames);

        /*   Object[] columnNames = {"","Line no",UserInfo.getInstance().getProAbbreviations()+"_"+mainPro.getMpgRef(),mainPro.getMpgDescription(),"Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ"};*/
        for (SubProductGroup spgNew : mainPro.getSubProductGroupCollection()) {
            ref2 = ref2 + spgNew.getSpgRef();

            columnNames = setRowLevelData(cal, subHeadings.length, ++intialNumber, ref2, spgNew.getSpgDescription(), "SubProduct", spgNew.getSpgId());

            /*columnNames = new Object[]{"", ++intialNumber, ref2, spgNew.getSpgDescription(), "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "SubProduct", spgNew.getSpgId()};
            model.addRow(columnNames);*/
            addRow(model, fmeas, "", columnNames, intialNumber, cal, crud);
            itemCount = 1;
            for (SubProductComponents subProductComponents : spgNew.getSubProductComponentsCollection()) {

                /*columnNames = new Object[]{"", ++intialNumber, ref2 + itemCount, subProductComponents.getSpcDescription(), "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "SubComp", subProductComponents.getSpcId()};
                model.addRow(columnNames);*/
                columnNames = setRowLevelData(cal, subHeadings.length, ++intialNumber, ref2 + itemCount, subProductComponents.getSpcDescription(), "SubComp", subProductComponents.getSpcId());

                addRow(model, fmeas, "", columnNames, intialNumber, cal, crud);
                funCount = 1;

                for (Functionalfailures functionalfailures : subProductComponents.getFunctionalfailuresCollection()) {

                    /*columnNames = new Object[]{"", ++intialNumber, ref2 + itemCount + "-" + funCount, functionalfailures.getDescription(), functionalfailures.getFunction(), functionalfailures.getPhase(), functionalfailures.getFailureMode(), functionalfailures.getFailureCause(), functionalfailures.getLocalFailureEffect(),
                        functionalfailures.getSubsystemFailureEffect(), functionalfailures.getTrainFailureEffect(), functionalfailures.getImportance(), functionalfailures.getFailureDetection(), functionalfailures.getPreventiveMeasures(), "", "", "", "", "", "", "", "", "", "", "", "Functions", functionalfailures.getFailureId()};
                    model.addRow(columnNames);*/
                    columnNames = setRowLevelFun(cal, subHeadings.length, ++intialNumber, ref2 + itemCount + "-" + funCount, functionalfailures);

                    addRow(model, fmeas, "", columnNames, intialNumber, cal, crud);
                    funCount++;
                    progress.setPercentage(intialNumber);
                }
                itemCount++;
            }
            ref2 = ref;
            progress.setPercentage(intialNumber);
        }
        progress.setPercentage(190);
        progress.setPercentage(200);
        if (cal.contentEquals("FMEA")) {
            ((FmeaCurd) crud).refreshTableData(jtFmea, fmeas, subHeadings, new int[]{8,12,20});
        } else if (cal.contentEquals("FMECA")) {
            ((FMECACrud) crud).refreshTableData(jtFmea, fmeas.getCalchdrid());
        }
        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void addRow(DefaultTableModel model, Calchdr fmeas, String comType, Object[] row, int rowCount, String cal, Object crud) {

        if (findFMEARowByCompName(model, row[2].toString(), row[3].toString()) == -1) {

            model.addRow(row);

            if (cal.contentEquals("FMEA")) {
                fMEADao.saveFMEA(((FmeaCurd) crud).getFMEAByRowId(model, fmeas, (model.getRowCount() - 1)));
            } else if (cal.contentEquals("FMECA")) {
                //fMECADao.saveFMECA(((FMECACrud) crud).getFMECAByRowId(model, (model.getRowCount() - 1), fmeas));
            }
        }

    }

    public int findFMEARowByCompName(DefaultTableModel model, String breakdowncode, String compName) {
        int res = -1;

        for (int i = 0; i < model.getRowCount(); i++) {
            if (checkFmeaEqual(breakdowncode, compName, model.getValueAt(i, 2).toString(), model.getValueAt(i, 3).toString())) {
                res = Integer.parseInt(model.getValueAt(i, 0).toString());
                break;
            }

        }

        return res;
    }

    public boolean checkFmeaEqual(String breakdowncodeNew, String compNameNew, String breakdowncode, String compName) {
        boolean res = false;

        if (breakdowncodeNew.equals(breakdowncode) && compNameNew.equals(compName)) {
            res = true;
            // System.out.println("breakdowncode - "+breakdowncode+" breakdowncodeNew - "+breakdowncodeNew);
            // System.out.println("compName - "+compName+" compNameNew - "+compNameNew);
        }

        return res;
    }

    public Object[] setRowLevelData(String cal, int rowCount, int lineNum, String subSysCode, String ComName, String comType, int compId) {
        Object[] res = new Object[rowCount];

        for (int i = 0; i < rowCount; i++) {
            if (i == 1) {
                res[i] = lineNum;
            } else if (i == 2) {
                res[i] = subSysCode;
            } else if (i == 3) {
                res[i] = ComName;
            } else if (i == (rowCount - 2)) {
                res[i] = comType;
            } else if (i == (rowCount - 1)) {
                res[i] = compId;
            } else {
                res[i] = "";
            }
        }

        return res;
    }

    public Object[] setRowLevelFun(String cal, int rowCount, int lineNum, String subSysCode, Functionalfailures functionalfailures) {
        Object res[] = null;

        if (cal.contentEquals("FMEA")) {
            res = new Object[]{"", lineNum, subSysCode, functionalfailures.getDescription(), functionalfailures.getFunction(), functionalfailures.getPhase(), functionalfailures.getFailureMode(), functionalfailures.getFailureCause(), functionalfailures.getLocalFailureEffect(),
                functionalfailures.getSubsystemFailureEffect(), functionalfailures.getTrainFailureEffect(), functionalfailures.getImportance(), functionalfailures.getFailureDetection(), functionalfailures.getPreventiveMeasures(), "", "", "", "", "", "", "", "", "", "", "", "Functions", functionalfailures.getFailureId()};
        } else if (cal.contentEquals("FMECA")) {
            res = new Object[]{"", lineNum, subSysCode, functionalfailures.getDescription(), functionalfailures.getFunction(), functionalfailures.getPhase(), functionalfailures.getFailureMode(), functionalfailures.getFailureCause(), functionalfailures.getLocalFailureEffect(),
                functionalfailures.getSubsystemFailureEffect(), functionalfailures.getTrainFailureEffect(), functionalfailures.getFailureDetection(), functionalfailures.getPreventiveMeasures(), "", "", functionalfailures.getFailureRate(), "", "", "", "Functions", functionalfailures.getFailureId()};
        }

        return res;
    }

    /*------------------------------------load-------------------------------------*/
 /*------------------------------------Import-------------------------------------*/
    public void deleteFromToFmeaData(DefaultTableModel model, int start, int end) {

        for (int i = (end - 1); i >= start; i--) {
            fMEADao.deleteFMEA(NumberConversion.NVL(model.getValueAt(i, 0) + "", -1));
            model.removeRow(i);
//         System.out.println("Fmea Deleted Id - "+NumberConversion.NVL(model.getValueAt(i, 0)+"",-1));
        }

    }

    public void deleteAllFmeaData(DefaultTableModel model) {

        for (int i = 1; i < model.getRowCount(); i++) {
            fMEADao.deleteFMEA(NumberConversion.NVL(model.getValueAt(i, 0) + "", -1));
            System.out.println("Fmea Deleted Id - " + NumberConversion.NVL(model.getValueAt(i, 0) + "", -1));
        }

    }

    public static int getSystemLevels(String sunSysCode) {

        int res = 0;
        int length = UserInfo.getInstance().getProAbbreviations().length();

        if (sunSysCode.length() == (length + 2)) {
            res = 1;
        } else if (sunSysCode.length() == (length + 3)) {
            res = 2;
        } else if ((sunSysCode.length() >= (length + 4)) && (sunSysCode.contains("-") == false)) {
            res = 3;
        } else {
            res = 4;
        }

        return res;
    }
    
    
     public static int getSystemLevelsNew(int rowId,JTable jtFmea) {

        String sunSysCode = (String)jtFmea.getValueAt (rowId, jtFmea.getColumnCount()-2);

        return getSystemLevelsNew(sunSysCode);
    }
   
    public static int getSystemLevelsNew(int rowId,DefaultTableModel model) {

        String sunSysCode = (String)model.getValueAt (rowId, model.getColumnCount()-2);

        return getSystemLevelsNew(sunSysCode);
    } 
     
    public static int getSystemLevelsNew(String sunSysCode) {

        int res = -1;

        if (sunSysCode.contains("MainProduct")) {
            res = 1;
        }else if (sunSysCode.contains("SubProduct")) {
            res = 2;
        } else if (sunSysCode.contains("Functions")) {
            res = 3;
        } else if (sunSysCode.contains("SubComp")) {
            res = 4;
        }else if (sunSysCode.contains("ComType")) {
            res = 0;
        } else {
            res = 5;
        }

        return res;
    }  

    public int validateImport(Object[][] tableData) {

        JFrame parent = InternalFrameDemo.mainFrame;
        String projectCode = "";
        String baseCode = "";
        String checkSubSys = "";
        int vaild = -1;

        parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            if (tableData.length != 0) {

                baseCode = tableData[0][1].toString();
                projectCode = baseCode.substring(0, tableData[0][1].toString().length() - 2);

                if (baseCode.contains(UserInfo.getInstance().getProAbbreviations())) {

                    if (baseCode.contains("-") == false) {

                        //JOptionPane.showMessageDialog(null,"Hello, projectCode - "+baseCode);
                        checkSubSys = checkSubSysCode(tableData, baseCode);

                        if (checkSubSys.length() > 0) {
                            JOptionPane.showMessageDialog(null, "File Validation Failed due to SubSysCode \n" + checkSubSys);
                        } else {
                            //progressSetup(tableData, baseCode);
                            //productSepration(tableData, baseCode);
                            vaild = 1;
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, " SubsystemBreakdownCode (" + baseCode + ") format error \n MainProdctCode must not contains \" - \" ");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, " Format error \n -------------------\n"
                            + "1) Please ensure the Line numbers cloumn is not empty \n"
                            + "2) SubsystemBreakdownCode (" + baseCode + ") \n BreakdownCode must starts with " + UserInfo.getInstance().getProAbbreviations() + " (Projectcode)");
                }

            } else {
                JOptionPane.showMessageDialog(null, "No Data found in the file");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        return vaild;
    }

    public String checkSubSysCode(Object[][] tableData, String baseCode) {
        String res = "";

        for (int i = 0; i < tableData.length; i++) {

            if (tableData[i][1].toString().contains(baseCode)) {

            } else {

                res = res + tableData[i][1].toString() + ", \n";

            }
        }

        return res;
    }

    public String[][] productSepration(Object[][] tableData) {

        String[][] productSepRes = new String[tableData.length][2];
        String subProdctCode = "";
        String subProdctComp = "";
        String subProdctfun = "";
        String subProdctCodePre = "";
        int check = 0;
        ProgressBar progress = new ProgressBar();
        progress.setBountry(0, tableData.length);

        MainProductGroup mainProdct = null;
        SubProductGroup subProdct = null;
        SubProductComponents subProComp = null;
        String baseCode = tableData[0][1].toString();

        for (int i = 0; i < tableData.length; i++) {

            // while(productIdentification(tableData[i][1].toString(), baseCode.length())==check){
            if (productIdentification(tableData[i][1].toString(), baseCode.length()) == 1) {
                //JOptionPane.showMessageDialog(null,"Hello, projectCode - "+baseCode);
                //System.out.println("-------------------------------------------------------");
                //System.out.println("                  "+tableData[i][1].toString()+"");
                //System.out.println("-------------------------------------------------------");
                mainProdct = isMainProductExist(tableData[i]);
                //mainProdct = mainDao.FindByName(tableData[i][2].toString());
                System.out.println("tableData[i] ------------------" + tableData[i].length);
                productSepRes[i][0] = "MainProduct";
                productSepRes[i][1] = mainProdct.getMpgId() + "";
            } else if (productIdentification(tableData[i][1].toString(), baseCode.length()) == 2) {
                subProdctCode = tableData[i][1].toString();
                if (subProdctCodePre.equals("")) {
                    subProdctCodePre = subProdctCode;
                }
                if (subProdctCodePre.contentEquals(subProdctCode)) {

                } else {
                    //System.out.println("");
                    subProdctCodePre = subProdctCode;
                }
                subProdct = isSubProductExist(tableData[i], mainProdct);
                //subProdct = spgDao.getSPGByName(tableData[i][2].toString());
                productSepRes[i][0] = "SubProduct";
                productSepRes[i][1] = subProdct.getSpgId() + "";
                //System.out.println(subProdctCode);
            } else if (productIdentification(tableData[i][1].toString(), baseCode.length()) == 3) {
                subProdctComp = tableData[i][1].toString();
                //System.out.println("      "+subProdctComp);
                subProComp = isSubProductCombExist(tableData[i], subProdct);
                //subProComp = spcDao.FindByName(tableData[i][2].toString()) ;
                productSepRes[i][0] = "SubComp";
                productSepRes[i][1] = subProComp.getSpcId() + "";
            } else if (productIdentification(tableData[i][1].toString(), baseCode.length()) == 4) {
                subProdctfun = tableData[i][1].toString();
                //System.out.println("      "+"      "+subProdctfun);
                productSepRes[i][0] = "Functions";
                productSepRes[i][1] = isSubCombFunExist(tableData[i], subProComp).getFailureId() + "";
            }
            progress.setPercentage(i + 1);
            // }

        }
        return productSepRes;
    }

    public MainProductGroup isMainProductExist(Object[] data) {
        MainProductGroup res = mainDao.FindByName(data[2].toString());

        if (res == null) {
            System.out.println("MainProduct - " + data[2].toString() + " does n't exist ");

            MainProductGroup mpg = new MainProductGroup();
            mpg.setMpgRef(data[2].toString().charAt(0) + "".toUpperCase());
            mpg.setMpgDescription(data[2].toString());
            mpg.setUsername(UserInfo.getInstance().getuser());
            res = mpg;
            mainDao.Save(mpg);

        } else {
            //res = true;
            //System.out.println(" isMainProductExist - "+mainDao.FindByName(data[2].toString()).getMpgDescription());
        }

        return res;
    }

    public SubProductGroup isSubProductExist(Object[] data, MainProductGroup mainProdct) {
        SubProductGroup res = spgDao.getSPGByNameAndMpg(data[2].toString(), mainProdct.getMpgId());

        if (res == null) {
            System.out.println("SubProduct - " + data[2].toString() + " does n't exist ");

            SubProductGroup spg = new SubProductGroup();
            spg.setSpgRef(data[2].toString().charAt(0) + "".toUpperCase());
            spg.setSpgDescription(data[2].toString());
            spg.setMpgRef(mainProdct);
            spg.setSil("Y");
            spg.setUsername(UserInfo.getInstance().getuser());
            res = spg;
            spgDao.Save(spg);
        } else {
            //res = true;
            //System.out.println(" isSubProductExist - "+spgDao.getSPGByName(data[2].toString()).getSpgDescription());
        }

        return res;
    }

    public SubProductComponents isSubProductCombExist(Object[] data, SubProductGroup subProdct) {
        SubProductComponents res = spcDao.FindByNameBySpg(data[2].toString(), subProdct.getSpgId());

        if (res == null) {
            System.out.println("SubProduct - " + data[2].toString() + " does n't exist ");

            SubProductComponents spc = new SubProductComponents();
            spc.setSpcDescription(data[2].toString());
            spc.setSpgRef(subProdct);
            spc.setUsername(UserInfo.getInstance().getuser());
            res = spc;
            spcDao.Save(spc);

        } else {
            // res = true;
            //System.out.println(" isSubProductExist - "+spgDao.getSPGByName(data[2].toString()).getSpgDescription());
        }

        return res;
    }

    public Functionalfailures isSubCombFunExist(Object[] data, SubProductComponents subProComp) {
        Functionalfailures res = null;

        if (funfailDao.findByNameBySpcId(data[2].toString(), subProComp.getSpcId()).size() == 0) {
            System.out.println("SubProduct - " + data[2].toString() + " does n't exist ");

            Functionalfailures funNew = new Functionalfailures();
            funNew.setProjecttype(UserInfo.getInstance().getTclProject().getProType());
            //funNew.setFailuretype(funRef.getFailuretype());
            funNew.setDescription(data[2].toString());
            funNew.setSubComponent(subProComp);
            funNew.setFunction(data[3].toString());
            funNew.setPhase(data[4].toString());
            funNew.setFailureMode(data[5].toString());
            funNew.setFailureCause(data[6].toString());
            funNew.setLocalFailureEffect(data[7].toString());
            funNew.setSubsystemFailureEffect(data[8].toString());
            funNew.setTrainFailureEffect(data[9].toString());
            funNew.setImportance(NumberConversion.NVLDouble(data[10].toString(), 0));
            funNew.setFailureDetection(data[11].toString());
            funNew.setFailureRate(0.00);
            funNew.setPreventiveMeasures(data[12].toString());
            funNew.setUsername(UserInfo.getInstance().getuser());
            funfailDao.Save(funNew);

            res = funNew;

        } else {
            res = funfailDao.findByNameBySpcId(data[2].toString(), subProComp.getSpcId()).get(0);
            //System.out.println(" isSubProductExist - "+spgDao.getSPGByName(data[2].toString()).getSpgDescription());
        }

        return res;
    }

    public int checkExistFmeaData(JTable jtFmea) {

        int result = -1;
        int res = -1;
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();

        if (model.getRowCount() > 1) {

            result = JOptionPane.showConfirmDialog(InternalFrameDemo.mainFrame, "Already " + model.getRowCount() + " rows in FMEA. \nImport FMEA file remove all current rows in the system and \nupload the new file. Are you sure want to do it?", "Upload FMEA",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                res = 1;
            } else {

                res = 0;
            }

        } else {

            res = 1;

        }

        return res;
    }

    public int infoFileFormat() {

        int res = 1;

        JScrollPane scroll = new JScrollPane(new FMEAImportInfo_old());
        scroll.setPreferredSize(new Dimension(900, 500));

        JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame, scroll, "FMEA File Format",
                JOptionPane.INFORMATION_MESSAGE);

        return res;
    }

    public void setFMEATableDataInstanly(JTable jtFmea, Object[][] tableData, String[][] proData, int colCount) {
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        for (int i = 0; i < tableData.length; i++) {

            for (int j = 0; j < tableData[i].length; j++) {

                if ((i + 1) > model.getRowCount()) {
                    model.addRow(new Object[colCount]);
                }
                model.setValueAt(j == 0 ? (i + 1) : tableData[i][j], i, j + 1);
            }

            model.setValueAt(proData[i][0], i, colCount);
            model.setValueAt(proData[i][1], i, colCount + 1);

        }

        /*  if(tableData.length < model.getRowCount()){
       fMEAServices.deleteFromToFmeaData(model, tableData.length, model.getRowCount());
    }*/
    }
    
  public Object[][] setAlignColExport(int [] colAlign,Object[][] importedData){
   Object[][] res = new Object [importedData.length][colAlign.length];
     
              /*  for (int matchColumn : colAlign) {
                    System.out.println("matchColumn - " + matchColumn);
                }*/

              
            /*  for (int i = 0; i < importedData[0].length; i++) {
                  System.out.println(i+" importedData - "+ ( (importedData[0][i].toString()== null) ? "" :importedData[0][i]) );
              }*/
   
      for (int i = 0; i < res.length; i++) {
          
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
  }

    public Object[][] setAlignColExportOld(int[] colAlign, Object[][] importedData,int colCount) {
        Object[][] res = new Object[importedData.length][colCount]; 

        /*       for (int matchColumn : colAlign) {
                    System.out.println("matchColumn - " + matchColumn);
                }*/
        for (int i = 0; i < res.length; i++) {

            for (int j = 0; j < importedData[i].length; j++) {

                res[i][colAlign[j]] = importedData[i][j];
                //System.out.println("importedData "+i+" "+importedData[i].length);

            }
        }

        return res;
    }

    /*------------------------------------Import-------------------------------------*/
 /*------------------------------------Common-------------------------------------*/
    public int productIdentification(String productCode, int baseLength) {
        int res = 0;

        if (productCode.length() == baseLength) {
            res = 1;
        } else if (productCode.length() == (baseLength + 1)) {
            res = 2;
        } else if (productCode.length() >= (baseLength + 2) && (productCode.contains("-") == false)) {
            res = 3;
        } else if ((productCode.contains("-"))) {
            res = 4;
        }

        return res;
    }

    public void progressSetup(Object[][] tableData, String baseCode) {

        new Thread() {
            public void run() {
                // productSepration(tableData, baseCode);
            }
        }.start();

    }

    public void refreshTable(DefaultTableModel dm) {
        int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }

    }

    public List<Map> createTable(List queryResults) throws Throwable {
        List<Map> r = new LinkedList<Map>();
        for (Object o : queryResults) {
            r.add(entityMap(o));
        }
        return r;
    }

    public Map entityMap(Object obj) throws Throwable {
        Map m = new HashMap();
        for (Field field : getFields(obj.getClass())) {
            Method method = getMethod(field);
            Object value = method.invoke(obj);
            m.put(field, value);
        }
        return m;
    }

    public List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new LinkedList<Field>();

        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    public Method getMethod(Field field) throws NoSuchMethodException {
        Class<?> clazz = field.getDeclaringClass();
        String name = "get" + uppercase(field.getName());
        Method method = clazz.getMethod(name);
        return method;
    }

    public String uppercase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }

    }

    public void addColumnData(JTable jtFmea, int col, int row) {

        switch (col) {
            case 1:
                // JOptionPane.showMessageDialog(null, "Your column- "+col);

                // jtFmea.setValueAt("Your column- "+col, row, col);
                break;

            case 2:
                // code block
                break;

            case 3:
                String[] components = spgDao.getAllSPGHeading();
                DataEntryPopup.showMultipleInputMessageDialog(components, col, row, jtFmea);
                break;

            case 4:
                String[] function = {};
                DataEntryPopup.showMultipleInputMessageDialog(function, col, row, jtFmea);
                break;

            case 5:
                String[] phases = {"UTO Mode", "RM Mode", "DM Mode", "ATPM Mode", "UTO Mode,  RM Mode, DM Mode, ATPM Mode"};
                DataEntryPopup.showMultipleInputMessageDialog(phases, col, row, jtFmea);
                /*String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
        "Choose", JOptionPane.QUESTION_MESSAGE, null, // Use
                                                                        // default
                                                                        // icon
        choices, // Array of choices
        choices[1]); // Initial choice
     // System.out.println(input);
        if(input.equals("")){
         
            Object result = JOptionPane.showInputDialog(null, "Enter Cell ");
             if(!result.equals("")){
              jtFmea.setValueAt(result, row, col);
             }
            
        }else{
         jtFmea.setValueAt(input, row, col);
        }  */
                break;

            case 6:
                String[] falureModes = {"Arching and damage", "Bind", "Binding", "Braid damage", "Break", "Catastrophic open", "Catastrophic short",
                    "Clogged", "Clogged, contaminants", "Damaged, pumping too fast", "Drift", "Drift or Intermittent", "Dry",
                    "Fail to close", "Fail to open", "Failed", "Fails to open", "High resistance", "High reverse", "Intermittent",
                    "Leak", "Leakage", "Loss of function", "Loss of output", "Loss of pressure function", "Low output", "No control",
                    "No reading", "No Solder", "No transmission", "o/p high or low", "o/p max", "o/p min (zero)", "Open", "Open circuit",
                    "Opens", "Output frozen", "Parameter change", "Short", "Short circuit", "Slip", "Slow to open", "Sort", "Spurious open",
                    "Sticking", "Worn"};

                DataEntryPopup.showMultipleInputMessageDialog(falureModes, col, row, jtFmea);
                break;

            case 7:
                String[] failCause = {};
                DataEntryPopup.showMultipleInputMessageDialog(failCause, col, row, jtFmea);
                break;

            case 8:
                String[] failEffects1 = {};
                DataEntryPopup.showMultipleInputMessageDialog(failEffects1, col, row, jtFmea);
                break;

            case 9:
                String[] failEffects2 = {};
                DataEntryPopup.showMultipleInputMessageDialog(failEffects2, col, row, jtFmea);
                break;

            case 10:
                String[] failEffects3 = {};
                DataEntryPopup.showMultipleInputMessageDialog(failEffects3, col, row, jtFmea);
                break;

            case 11:
                String[] failerDetection = {};
                DataEntryPopup.showMultipleInputMessageDialog(failerDetection, col, row, jtFmea);
                break;

            case 12:
                String[] PreventiveAndComPensatingMeasure = {};
                DataEntryPopup.showMultipleInputMessageDialog(PreventiveAndComPensatingMeasure, col, row, jtFmea);
                break;

            default:
            // code block
        }

    }

    public void setCopyPasteFunction(JTable table, ExcelAdapter excelAdapter) {

        final JPopupMenu pm = new JPopupMenu();
        pm.add(new CopyAction(table, excelAdapter));
        pm.add(new PasteAction(table, excelAdapter));
        pm.add(new PasteAction(table, excelAdapter));
        pm.add(new PasteAction(table, excelAdapter));
        pm.add(new PasteAction(table, excelAdapter));
        pm.add(new PasteAction(table, excelAdapter));

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON3) {
                    doPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() && e.getButton() == MouseEvent.BUTTON3) {
                    doPopup(e);

                }
            }

            protected void doPopup(MouseEvent e) {
                pm.show(e.getComponent(), e.getX(), e.getY());
            }

            protected void highlightRow(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);

                table.setRowSelectionInterval(row, row);
                table.setColumnSelectionInterval(col, col);
            }

        });

    }

    public boolean checkLevel(String subSysCode, String level) {
        boolean res = false;

        if (level.contentEquals("mpg") && subSysCode.length() == (UserInfo.getInstance().getProAbbreviations().length() + 2)) {

        } else if (level.contentEquals("spg") && subSysCode.length() == (UserInfo.getInstance().getProAbbreviations().length() + 3)) {

        } else if (level.contentEquals("spc") && subSysCode.length() == (UserInfo.getInstance().getProAbbreviations().length() + 4)) {

        } else if (level.contentEquals("fun") && subSysCode.length() > (UserInfo.getInstance().getProAbbreviations().length() + 3)) {

        }

        return res;
    }

    /*------------------------------------Common-------------------------------------*/
    
    
    
}
