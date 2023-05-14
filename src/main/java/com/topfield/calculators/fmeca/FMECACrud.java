/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmeca;

import com.topfield.calculators.calcfile.CalcCurd;
import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.calculators.fmea.FmeaCurd;
import com.topfield.calculators.fmea.FmeaMil1629A;
import com.topfield.calculators.fmea.FmeaRPN;
import com.topfield.controller.ComponentFunctions;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import com.topfield.project.ProjectDefinition;
import com.topfield.project.ShowSelectedItemsAsList;
import com.topfield.themes.DataEntryPopup;
import com.topfield.utilities.ColumnGroup;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.user.UserInfo;
import com.topfield.utilities.ScientificNotation;
import com.topfield.view.popup.CalcFunctionsPopup;
import com.topfield.view.popup.FMEAPopup;
import com.topfield.view.popup.FMEAPopupAdd;
import com.topfield.view.popup.FMECAPopupAdd;
import com.topfield.view.popup.FMECAPopupEdit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Murali
 */
public class FMECACrud {

    private FMECADao fmecaDao = new FMECADaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private CalcFunctionsDao calcFunctionsDao = new CalcFunctionsDaoImpl();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private CalcCurd calcCurd = new CalcCurd();
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();
    private FunctionalfailuresDao funDao = new FunctionalfailuresDaoImpl();

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private ScientificNotation sciNotation = new ScientificNotation("0.00E0");

    public Fmeca getFMECAByRowId(DefaultTableModel model, int rowId, Calchdr mpghdr) {

        Fmeca f = new Fmeca();

        f.setFmecaId(NumberConversion.NVL(model.getValueAt(rowId, 0) + "", 0));
        f.setFISubsysBreakCode((String) model.getValueAt(rowId, 2));
        f.setFISubsysComp((String) model.getValueAt(rowId, 3));
        f.setFIFun((String) model.getValueAt(rowId, 4));
        f.setFIPhases((String) model.getValueAt(rowId, 5));
        f.setFIFailmode((String) model.getValueAt(rowId, 6));
        f.setFIFailCause((String) model.getValueAt(rowId, 7));
        f.setFELocSysLevel((String) model.getValueAt(rowId, 8));
        f.setFETrainSubsysLevel((String) model.getValueAt(rowId, 9));
        f.setFETrainLvl((String) model.getValueAt(rowId, 10));
        //f.setFEImportanceB             (NumberConversion.NVL(model.getValueAt(rowId, 11)+"",0));
        f.setDRMFaildetection((String) model.getValueAt(rowId, 11));
        f.setDRMPrevAndcompenmeas((String) model.getValueAt(rowId, 12));
        f.setFCFailEffProb(NumberConversion.NVLDouble(model.getValueAt(rowId, 13) + "", 0));
        f.setFCFailModeRatio(NumberConversion.NVLDouble(model.getValueAt(rowId, 14) + "", 0));
        f.setFCFailureRate(NumberConversion.NVLDouble(model.getValueAt(rowId, 15) + "", 0));
        f.setFCOperatingTime(NumberConversion.NVLDouble(model.getValueAt(rowId, 16) + "", 0));
        f.setFCFailModeCri(NumberConversion.NVLDouble(model.getValueAt(rowId, 17) + "", 0));
        f.setFCFailItemCri(NumberConversion.NVLDouble(model.getValueAt(rowId, 18) + "", 0));

        f.setComType(model.getValueAt(rowId, 19) + "");
        f.setComId(NumberConversion.NVL(model.getValueAt(rowId, 20) + "", 0));
        f.setCalcComp(calcfileDao.findById(f.getComId()));
        f.setFmecaHeader(mpghdr);

        return f;
    }

    public Fmeca getFMECAByRowIdImport(DefaultTableModel model, int rowId) {
        // System.out.println("model length 8888888888888888888 "+model.getColumnCount());
        Fmeca f = new Fmeca();

        f.setFmecaId(NumberConversion.NVL(model.getValueAt(rowId, 0) + "", 0));
        f.setFISubsysBreakCode((String) model.getValueAt(rowId, 1));
        f.setFISubsysComp((String) model.getValueAt(rowId, 2));
        f.setFIFun((String) model.getValueAt(rowId, 3));
        f.setFIPhases((String) model.getValueAt(rowId, 4));
        f.setFIFailmode((String) model.getValueAt(rowId, 5));
        f.setFIFailCause((String) model.getValueAt(rowId, 6));
        f.setFELocSysLevel((String) model.getValueAt(rowId, 7));
        f.setFETrainSubsysLevel((String) model.getValueAt(rowId, 8));
        f.setFETrainLvl((String) model.getValueAt(rowId, 9));
        //f.setFEImportanceB             (NumberConversion.NVL(model.getValueAt(rowId, 11)+"",0));
        f.setDRMFaildetection((String) model.getValueAt(rowId, 10));
        f.setDRMPrevAndcompenmeas((String) model.getValueAt(rowId, 11));
        f.setFCFailEffProb(NumberConversion.NVLDouble(model.getValueAt(rowId, 12) + "", 0));
        f.setFCFailModeRatio(NumberConversion.NVLDouble(model.getValueAt(rowId, 13) + "", 0));
        f.setFCFailureRate(NumberConversion.NVLDouble(model.getValueAt(rowId, 14) + "", 0));
        f.setFCOperatingTime(NumberConversion.NVLDouble(model.getValueAt(rowId, 15) + "", 0));
        f.setFCFailModeCri(NumberConversion.NVLDouble(model.getValueAt(rowId, 16) + "", 0));
        f.setFCFailItemCri(NumberConversion.NVLDouble(model.getValueAt(rowId, 17) + "", 0));
        //f.setIrRpz                     (Integer.parseInt(model.getValueAt(rowId, 23)+""));   NumberConversion
        //f.setIrRpz                     (NumberConversion.NVL(model.getValueAt(rowId, 24)+"", 0));
        //System.out.println("************************"+NumberConversion.NVL(model.getValueAt(rowId, 24)+"", 0));
        //f.setFmecaCreateddate(new Date());
        //f.setFmecaComponent(mainProDao.FindById(MpgId));
        //f.setFmecaproid(proDao.findById(UserInfo.getInstance().getProjectNo()));
        //f.setFmecauser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));

        return f;
    }

    public String[] getTableHeadingGlobal() {
        String[] columnNames = {"Id", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause",
            "Severity Class", "Failure Effect:(Local System Level)", "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures",
            "Failure Rate Data Source", "Failure Effect Probability (β)", "Failure Mode Ratio (α)", "Failure Rate (λp)", "Unrevealed Probability", "Operating Time (t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "Unrevealed Failure Rate", "Remarks", "ComType", "ComId"};
        return columnNames;
    }

    public List<HashMap<String, String>> getFMEAData(Mpghdr mpghdr) {

        String[] columnNames = getTableHeadingGlobal();

        List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hmap;

        List<Fmeca> fmeaList = fmecaDao.getAllFMECAByHdr(mpghdr.getMpghdrid());

        for (Fmeca fmeca : fmeaList) {
            hmap = new HashMap<String, String>();

            hmap.put(columnNames[0], fmeca.getFmecaId() + "");
            hmap.put(columnNames[1], fmeca.getFISubsysBreakCode());
            hmap.put(columnNames[2], fmeca.getFISubsysComp());
            hmap.put(columnNames[3], fmeca.getFIFun());
            hmap.put(columnNames[4], fmeca.getFIPhases());
            hmap.put(columnNames[5], fmeca.getFIFailmode());
            hmap.put(columnNames[6], fmeca.getFIFailCause());
            hmap.put(columnNames[7], fmeca.getSeverityclass());
            hmap.put(columnNames[8], fmeca.getFELocSysLevel());
            hmap.put(columnNames[9], fmeca.getFETrainSubsysLevel());
            hmap.put(columnNames[10], fmeca.getFETrainLvl());
            hmap.put(columnNames[11], fmeca.getDRMFaildetection());
            hmap.put(columnNames[12], fmeca.getDRMPrevAndcompenmeas());
            hmap.put(columnNames[13], fmeca.getFailureProbability());
            hmap.put(columnNames[14], fmeca.getFCFailEffProb() + "");
            hmap.put(columnNames[15], fmeca.getFCFailModeRatio() + "");
            hmap.put(columnNames[16], fmeca.getFCFailureRate() + "");
            hmap.put(columnNames[17], fmeca.getFCUnrevealedfailurePro() + "");
            hmap.put(columnNames[18], fmeca.getFCOperatingTime() + "");
            hmap.put(columnNames[19], fmeca.getFCFailModeCri() + "");
            hmap.put(columnNames[20], fmeca.getFCFailItemCri() + "");
            hmap.put(columnNames[21], fmeca.getUnrevealedfailure() + "");
            hmap.put(columnNames[22], fmeca.getRemarks());
            hmap.put(columnNames[23], fmeca.getComType() + "");
            hmap.put(columnNames[24], fmeca.getComId() + "");

            mapList.add(hmap);
        }

        return mapList;
    }

    public List<HashMap<String, String>> getFMEAData2(Calchdr calcHdr) {

        String[] columnNames = getTableHeadingGlobal();

        List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hmap;
        List<CalcFunctions> calcFunList = calcFunctionsDao.getAllCalcFunctionsByMpgheader(calcHdr.getMpghdr().getMpghdrid());
        MainProductGroup mpg = calcHdr.getMpghdr().getFmeaComponent();
        Mpghdr mpgHdr = calcHdr.getMpghdr();
        List<Fmeca> fmecaList;
        //List<Fmea> fmeaList = fMEADao.getAllFMEAByHdr(mpgHdr.getMpghdrid());

        hmap = new HashMap<String, String>();
        hmap.put(columnNames[0], mpgHdr.getMpghdrid() + "");
        hmap.put(columnNames[1], "R" + mpg.getMpgRef());
        hmap.put(columnNames[2], mpgHdr.getDescriptions());
        hmap.put(columnNames[23], "MainProduct");
        hmap.put(columnNames[24], mpgHdr.getMpghdrid() + "");
        mapList.add(hmap);

        for (CalcFunctions calcFun : calcFunList) {
            fmecaList = fmecaDao.getAllFMECAByMPGHdrFun(calcHdr.getCalchdrid(), calcFun.getCalcFunId());

            if (fmecaList.size() > 0 || calcHdr.getApproach() == 1) {

                hmap = new HashMap<String, String>();
                hmap.put(columnNames[0], calcFun.getCalcFunId() + "");
                hmap.put(columnNames[1], calcFun.getFunId());
                hmap.put(columnNames[2], calcFun.getCalcFunction());
                hmap.put(columnNames[23], "Functions");
                hmap.put(columnNames[24], calcFun.getCalcFunId() + "");
                mapList.add(hmap);

                for (Fmeca fmeca : fmecaList) {
                    hmap = new HashMap<String, String>();

                    hmap.put(columnNames[0], fmeca.getFmecaId() + "");
                    hmap.put(columnNames[1], fmeca.getFISubsysBreakCode());
                    hmap.put(columnNames[2], fmeca.getFISubsysComp());
                    hmap.put(columnNames[3], fmeca.getFIFun());
                    hmap.put(columnNames[4], fmeca.getFIPhases());
                    hmap.put(columnNames[5], fmeca.getFIFailmode());
                    hmap.put(columnNames[6], fmeca.getFIFailCause());
                    hmap.put(columnNames[7], fmeca.getSeverityclass());
                    hmap.put(columnNames[8], fmeca.getFELocSysLevel());
                    hmap.put(columnNames[9], fmeca.getFETrainSubsysLevel());
                    hmap.put(columnNames[10], fmeca.getFETrainLvl());
                    hmap.put(columnNames[11], fmeca.getDRMFaildetection());
                    hmap.put(columnNames[12], fmeca.getDRMPrevAndcompenmeas());
                    hmap.put(columnNames[13], fmeca.getFailureProbability());
                    hmap.put(columnNames[14], fmeca.getFCFailEffProb() + "");
                    hmap.put(columnNames[15], fmeca.getFCFailModeRatio() + "");
                    hmap.put(columnNames[16], fmeca.getFCFailureRate() + "");
                    hmap.put(columnNames[17], fmeca.getFCUnrevealedfailurePro() + "");
                    hmap.put(columnNames[18], fmeca.getFCOperatingTime() + "");
                    hmap.put(columnNames[19], sciNotation.sciFormat(fmeca.getFCFailModeCri()));
                    hmap.put(columnNames[20], sciNotation.sciFormat(fmeca.getFCFailItemCri()));
                    hmap.put(columnNames[21], fmeca.getUnrevealedfailure() + "");
                    hmap.put(columnNames[22], fmeca.getRemarks());
                    hmap.put(columnNames[23], fmeca.getComType() + "");
                    hmap.put(columnNames[24], fmeca.getComId() + "");

                    mapList.add(hmap);
                }
            }
        }

        return mapList;
    }

    public Object[][] refreshTableData2(JTable jtFmea, Calchdr mpgCalcHdr, String[] subHeadings, int[] group) {
        int columCount = 0;
        int totalColCount = jtFmea.getColumnCount();
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        List<HashMap<String, String>> dbData = getFMEAData2(mpgCalcHdr);
        String data[][] = new String[dbData.size()][subHeadings.length];

        for (HashMap<String, String> hashMap : dbData) {

            data[columCount][0] = (columCount + 1) + "";

            for (int i = 1; i < subHeadings.length; i++) {

                data[columCount][i] = hashMap.get(subHeadings[i]);

            }

            columCount++;
        }

        model = new DefaultTableModel(data, subHeadings) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        jtFmea.setModel(model);
        setGroupheading(jtFmea, subHeadings, group);
        // setUpSportColumn(jtFmea, jtFmea.getColumnModel().getColumn(13));

        TableColumn column1 = null;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column1 = jtFmea.getColumnModel().getColumn(i);
            // column1.setCellRenderer(new WordWrapCellRenderer());
            if (i == 0) {
                column1.setPreferredWidth(30);
            } else if (i == 1) { //|| i==16 || 1==17
                column1.setPreferredWidth(40);
            } else if (i == (subHeadings.length - 2)) { //|| i==16 || 1==17
                column1.setMinWidth(0);
                column1.setMaxWidth(0);
            } else if (i == (subHeadings.length - 1)) { //|| i==16 || 1==17
                column1.setMinWidth(0);
                column1.setMaxWidth(0);
            } else {
                column1.setPreferredWidth(250);
            }
        }

        return data;
    }

    public Object[][] refreshTableData(JTable jtFmea, int mpgHdrId) {

        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        // System.out.println("MpgId "+MpgId+"  -- "+UserInfo.getInstance().getProjectNo()+" -- "+UserInfo.getInstance().getuser().getUsername());
        //List<Fmeca> fmeaList = fMECADao.getAllFMECAByMPG(MpgId,UserInfo.getInstance().getProjectNo(),UserInfo.getInstance().getuser().getUsername());
        //JTable table = new JTable(fMEADao.getAllMPG(),columnNames);
        List<Fmeca> fmeaList = fmecaDao.getAllFMECAByHdr(mpgHdrId);

        /* ArrayList<String> column = new ArrayList<>();

        for (Field field : getFields(Fmea.class)) {
          //  System.out.println("field - "+WordUtils.capitalize(field.getName()) );
            column.add(WordUtils.capitalize(field.getName()));
        }*/
        int columCount = 0;
        String data[][] = null;
        data = new String[fmeaList.size()][getTableHeadingString().length + 1];
        for (Fmeca fmea : fmeaList) {

            data[columCount][0] = fmea.getFmecaId() + "";
            data[columCount][1] = (columCount + 1) + "";
            data[columCount][2] = fmea.getFISubsysBreakCode();
            data[columCount][3] = fmea.getFISubsysComp();
            data[columCount][4] = fmea.getFIFun();
            data[columCount][5] = fmea.getFIPhases();
            data[columCount][6] = fmea.getFIFailmode();
            data[columCount][7] = fmea.getFIFailCause();
            data[columCount][8] = fmea.getFELocSysLevel();
            data[columCount][9] = fmea.getFETrainSubsysLevel();
            data[columCount][10] = fmea.getFETrainLvl();
            data[columCount][11] = fmea.getDRMFaildetection();
            data[columCount][12] = fmea.getDRMPrevAndcompenmeas();
            data[columCount][13] = fmea.getFCFailEffProb() + "";
            data[columCount][14] = fmea.getFCFailModeRatio() + "";
            data[columCount][15] = fmea.getFCFailureRate() + "";
            data[columCount][16] = fmea.getFCOperatingTime() + "";
            data[columCount][17] = fmea.getFCFailModeCri() + "";
            data[columCount][18] = fmea.getFCFailItemCri() + "";
            data[columCount][19] = fmea.getComType() + "";
            data[columCount][20] = fmea.getComId() + "";
            //data[columCount][19]= fmea.getFmecaCreateddate()+"";

            columCount++;
        }

        //JOptionPane.showMessageDialog(null, "Your data save successfully "+model.getRowCount()+" - "+data.length+" "+fmeaList.size());
        //columnNames = column.toArray();
        //model = new DefaultTableModel(data,column.toArray());columnNames
        model = new DefaultTableModel(data, getTableHeadingString());
        jtFmea.setModel(model);
        setGroupheading(jtFmea);

        // TableModel model = new PlayerTableModel(FileHandler.getCompletedPlayers());
        /*  refreshTable(model);*/
        //jtFmea.setPreferredSize(new Dimension(6000,jtFmea.getPreferredSize().height));
        TableColumn column1 = null;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column1 = jtFmea.getColumnModel().getColumn(i);
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

    public void itemAdd(JTable jtFmea, Calchdr mpgHdr) {

        int selRow = jtFmea.getSelectedRow();
        SubProductGroup spgPara = null;
        SubProductComponents spcPara = null;
        int funNewRef = 0;
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();

        if (FMEAServices.getSystemLevels(jtFmea.getValueAt(selRow, 2).toString()) > 2) {
            //try{
            //setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Object[] options1 = {"Save", "Cancel"};
            FMECAPopupAdd fmeaPopup = new FMECAPopupAdd(mpgHdr.getMpghdr().getFmeaComponent().getMpgId());

            if (FMEAServices.getSystemLevels(model.getValueAt(selRow, 2).toString()) == 4) {
                Functionalfailures funRef = funDao.findByName(model.getValueAt(selRow, 3).toString()).get(0);
                spcPara = funRef.getSubComponent();
                spgPara = spcPara.getSpgRef();
            } else {
                System.out.println("model.getValueAt(selRow,3).toString() - " + model.getValueAt(selRow, 3).toString());
                spcPara = spcDao.FindByName(model.getValueAt(selRow, 3).toString());
                spgPara = spcPara.getSpgRef();
            }

            fmeaPopup.setSubsystemBreakdownCode(model.getValueAt(selRow, 2).toString());
            fmeaPopup.eventSelected(spgPara, spcPara);

            int result = JOptionPane.showOptionDialog(null, fmeaPopup, "Please enter the details",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                Functionalfailures funNew = new Functionalfailures();
                funNew.setProjecttype(UserInfo.getInstance().getTclProject().getProType());
                //funNew.setFailuretype(funRef.getFailuretype());
                funNew.setDescription(fmeaPopup.getFunctionalComponents());
                funNew.setSubComponent(spcDao.FindById(fmeaPopup.getSubProductComponents().getId()));
                funNew.setFunction(fmeaPopup.getFunction());
                funNew.setPhase(fmeaPopup.getPhase());
                funNew.setFailureMode(fmeaPopup.getFailureMode());
                funNew.setFailureCause(fmeaPopup.getFailureCause());
                funNew.setLocalFailureEffect(fmeaPopup.getLocalSystemLevel());
                funNew.setSubsystemFailureEffect(fmeaPopup.getTrainSubsystemLevel());
                funNew.setTrainFailureEffect(fmeaPopup.getTrainLevel());
                funNew.setFailureDetection(fmeaPopup.getFailureDetection());
                funNew.setImportance(0.00);
                funNew.setFailureRate(NumberConversion.NVLDouble(fmeaPopup.getFailureRate(), 0));
                funNew.setPreventiveMeasures(fmeaPopup.getPreventiveMeasures());
                funNew.setUsername(UserInfo.getInstance().getuser());
                funNewRef = funDao.Save(funNew);

                Fmeca fmeca = new Fmeca();
                //fmea.setFmeaId(null);
                fmeaPopup.setSubsystemBreakdownCode(fMEAServices.generateSubSystemCode(fmeaPopup.getSubsystemBreakdownCode(), fmeaPopup.getSubProductComponents().getId(), model));
                fmeca.setFISubsysBreakCode(fmeaPopup.getSubsystemBreakdownCode());
                fmeca.setFISubsysComp(fmeaPopup.getFunctionalComponents());
                fmeca.setFIFun(fmeaPopup.getFunction());
                fmeca.setFIPhases(fmeaPopup.getPhase());
                fmeca.setFIFailmode(fmeaPopup.getFailureMode());
                fmeca.setFIFailCause(fmeaPopup.getFailureCause());
                fmeca.setFELocSysLevel(fmeaPopup.getLocalSystemLevel());
                fmeca.setFETrainSubsysLevel(fmeaPopup.getTrainSubsystemLevel());
                fmeca.setFETrainLvl(fmeaPopup.getTrainLevel());
                fmeca.setDRMFaildetection(fmeaPopup.getFailureDetection());
                fmeca.setDRMPrevAndcompenmeas(fmeaPopup.getPreventiveMeasures());

                fmeca.setFCFailEffProb(NumberConversion.NVLDouble(fmeaPopup.getFailureEffectProbability(), 0));
                fmeca.setFCFailModeRatio(NumberConversion.NVLDouble(fmeaPopup.getFailureModeRatio(), 0));
                fmeca.setFCFailureRate(NumberConversion.NVLDouble(fmeaPopup.getFailureRate(), 0));
                fmeca.setFCOperatingTime(NumberConversion.NVLDouble(fmeaPopup.getOperatingTime(), 0));
                fmeca.setFCFailModeCri(NumberConversion.NVLDouble(fmeaPopup.getFailureItemCriticality(), 0));
                fmeca.setFCFailItemCri(NumberConversion.NVLDouble(fmeaPopup.getOperatingTime(), 0));

                fmeca.setComType("Functions");
                fmeca.setComId(funNewRef);
                fmeca.setFmecaHeader(mpgHdr);
                //fmea.setCreateddate(new Date());
                //fmea.setFmeaComponent(mainDao.FindById(MpgId));
                //fmea.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
                //fmea.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
                fmecaDao.saveFMECA(fmeca);

                refreshTableData(jtFmea, mpgHdr.getCalchdrid());

            }

        } else {
            JOptionPane.showMessageDialog(null, "You can't add funtion to this level(Sub prodct group)");

        }

    }

    public void itemEdit(JTable jtFmea, Calchdr fmeas) {

        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        int selRow = jtFmea.getSelectedRow();
        Functionalfailures funRef = null;

        if (FMEAServices.getSystemLevels(jtFmea.getValueAt(selRow, 2).toString()) == 4) {  //jtFmea.getValueAt(selRow,2).toString().contains("-")==true
            try {
                //setCursor(new Cursor(Cursor.WAIT_CURSOR));
                InternalFrameDemo.mainFrame.setLoader();
                Object[] options1 = {"Save", "Cancel"};
                FMECAPopupEdit fmecaPopup = new FMECAPopupEdit(fmeas.getCalchdrid());

                try {
                    funRef = funDao.findById(Integer.parseInt(model.getValueAt(selRow, 20).toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    //funRef = funDao.findByName(model.getValueAt(selRow,3).toString()).get(0);
                }

                SubProductComponents subComRef = funRef.getSubComponent();
                fmecaPopup.setSubsystemBreakdownCode(model.getValueAt(selRow, 2) + "");
                fmecaPopup.eventSelected(subComRef.getSpgRef(), subComRef, funRef);
                System.out.println("subComRef.getSpgRef() - " + subComRef.getSpgRef().getSpgDescription());
                fmecaPopup.setFunction(model.getValueAt(selRow, 4) + "");
                fmecaPopup.setPhase(model.getValueAt(selRow, 5) + "");
                fmecaPopup.setFailureMode(model.getValueAt(selRow, 6) + "");
                fmecaPopup.setFailureCause(model.getValueAt(selRow, 7) + "");
                fmecaPopup.setLocalSystemLevel(model.getValueAt(selRow, 8) + "");
                fmecaPopup.setTrainSubsystemLevel(model.getValueAt(selRow, 9) + "");
                fmecaPopup.setTrainLevel(model.getValueAt(selRow, 10) + "");

                fmecaPopup.setFailureDetection(model.getValueAt(selRow, 11) + "");
                fmecaPopup.setPreventiveMeasures(model.getValueAt(selRow, 12) + "");

                fmecaPopup.setFailureEffectProbability(model.getValueAt(selRow, 13) + "");
                fmecaPopup.setFailureModeRatio(model.getValueAt(selRow, 14) + "");
                fmecaPopup.setFailureRate(model.getValueAt(selRow, 15) + "");
                fmecaPopup.setOperatingTime(model.getValueAt(selRow, 16) + "");
                fmecaPopup.setFailureModeCriticality(model.getValueAt(selRow, 17) + "");
                fmecaPopup.setFailureItemCriticality(model.getValueAt(selRow, 18) + "");

                /*fmeaPopup.setSubProductGroup(new com.topfield.utilities.Item(subComRef.getSpgRef().getSpgId(),subComRef.getSpgRef().getSpgDescription()));
        fmeaPopup.setSubProductComponents(new com.topfield.utilities.Item(subComRef.getSpcId(),subComRef.getSpcDescription()));
        fmeaPopup.setFunctionalComponents(new com.topfield.utilities.Item(funRef.getFailureId(),funRef.getDescription()));
                 */
                int result = JOptionPane.showOptionDialog(null, fmecaPopup, "Please enter the details",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    model.setValueAt(fmecaPopup.getFunctionalComponents().getDescription(), selRow, 3);
                    model.setValueAt(fmecaPopup.getFunction(), selRow, 4);
                    model.setValueAt(fmecaPopup.getPhase(), selRow, 5);
                    model.setValueAt(fmecaPopup.getFailureMode(), selRow, 6);
                    model.setValueAt(fmecaPopup.getFailureCause(), selRow, 7);
                    model.setValueAt(fmecaPopup.getLocalSystemLevel(), selRow, 8);
                    model.setValueAt(fmecaPopup.getTrainSubsystemLevel(), selRow, 9);
                    model.setValueAt(fmecaPopup.getTrainLevel(), selRow, 10);
                    model.setValueAt(fmecaPopup.getFailureDetection(), selRow, 11);
                    model.setValueAt(fmecaPopup.getPreventiveMeasures(), selRow, 12);

                    model.setValueAt(fmecaPopup.getFailureEffectProbability(), selRow, 13);
                    model.setValueAt(fmecaPopup.getFailureModeRatio(), selRow, 14);
                    model.setValueAt(fmecaPopup.getFailureRate(), selRow, 15);
                    model.setValueAt(fmecaPopup.getOperatingTime(), selRow, 16);
                    model.setValueAt(fmecaPopup.getFailureModeCriticality(), selRow, 17);
                    model.setValueAt(fmecaPopup.getFailureItemCriticality(), selRow, 18);
                    model.setValueAt("Functions", selRow, 19);
                    model.setValueAt(fmecaPopup.getFunctionalComponents().getId(), selRow, 20);

                    fmecaDao.UpdateFMECA(getFMECAByRowId(model, selRow, fmeas));

                }

            } catch (Exception ex) {
                ex.printStackTrace();

            } finally {
                InternalFrameDemo.mainFrame.removeLoder();
                //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }

        } else {
            JOptionPane.showMessageDialog(null, "No funtion available to edit the current row");

        }
    }

    public void itemAddYourComp(JTable jtFmea, int mpgHdrId) {

        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        int count = jtFmea.getRowCount() + 1;
        Object[] options1 = {"ADD", "Cancel"};
        ShowSelectedItemsAsList selcomp = new ShowSelectedItemsAsList(proDao.findById(UserInfo.getInstance().getProjectNo()).getSystemScope().split("\\#"));

        int result = JOptionPane.showOptionDialog(null, selcomp, "Select to insert components",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        if (result == JOptionPane.YES_OPTION) {

            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            for (String string : selcomp.getSelectedComponents()) {
                // System.out.println("****************************************8 "+string);

                SubProductGroup subCom = spgDao.getSPGByName(string);
                Fmeca f = new Fmeca();
                //f.setFmeaId();
                //f.setFmecaComponent(mainProDao.FindById(MpgId));
                //f.setFISubsysComp(subCom.getSpgDescription());
                //f.setFISubsysBreakCode(ComponentFunctions.getSubSystemBreakDownCode(subCom.getSpgRef(), subCom.getMpgRef().getMpgRef(),(count)));
                //f.setFETrainSubsysLevel("Underframe");
                //f.setFmecaproid(proDao.findById(UserInfo.getInstance().getProjectNo()));
                //f.setFmecauser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
                //f.setFmecaCreateddate(new Date());
                fmecaDao.saveFMECA(f);
                count++;
            }

            refreshTableData(jtFmea, mpgHdrId);
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //  JOptionPane.showMessageDialog(null, "Please add your details now ");*/
        } else if (result == JOptionPane.NO_OPTION) {

        } else {
            // System.out.println("Cancelled");
        }

    }

    public void itemAddAllComp(JTable jtFmea, int mpgHdrId) {

        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        ProjectDefinition projectDefinition = new ProjectDefinition(false);
        Object[] options1 = {"ADD", "Cancel"};

        int result = JOptionPane.showOptionDialog(null, projectDefinition, "Select to insert components",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        if (result == JOptionPane.YES_OPTION) {

            /*for (String object : projectDefinition.getSelections()) {
                                 
                             }*/
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            String select[] = projectDefinition.getSelections().split("\\#");
            // System.out.println("****************************************8 "+projectDefinition.getSelections());
            for (String string : select) {
                //  System.out.println("****************************************8 "+string);

                SubProductGroup subCom = spgDao.getSPGByName(string);
                Fmeca f = new Fmeca();
                //f.setFmeaId();
                //f.setFmecaComponent(mainProDao.FindById(MpgId));
                f.setFISubsysComp(subCom.getSpgDescription());
                f.setFISubsysBreakCode(ComponentFunctions.getSubSystemBreakDownCode(subCom.getSpgRef(), subCom.getMpgRef().getMpgRef(), (jtFmea.getRowCount() + 1)));
                //f.setFETrainSubsysLevel("Underframe");
                //f.setFmecaproid(proDao.findById(UserInfo.getInstance().getProjectNo()));
                //f.setFmecauser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
                //f.setFmecaCreateddate(new Date());
                fmecaDao.saveFMECA(f);
            }

            refreshTableData(jtFmea, mpgHdrId);
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //  JOptionPane.showMessageDialog(null, "Please add your details now ");
        } else if (result == JOptionPane.NO_OPTION) {

        } else {
            //  System.out.println("Cancelled");
        }

    }

    public void itemSave(JTable jtFmea, Calchdr mpghdr) {

        JFrame parent = InternalFrameDemo.mainFrame;
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();

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

        if (model.getRowCount() > 0) {
            parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            for (int i = 0; i < model.getRowCount(); i++) {
                fmecaDao.UpdateFMECA(getFMECAByRowId(model, i, mpghdr));
            }

            parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            JOptionPane.showMessageDialog(null, "Your data save successfully");
        } else {
            JOptionPane.showMessageDialog(null, "No data to save");

        }
    }

    public void itemRemove(JTable jtFmea) {
        int modelRow = jtFmea.getSelectedRow();
        if (modelRow >= 0) {
            fmecaDao.deleteFMECA(Integer.parseInt(jtFmea.getValueAt(modelRow, 0) + ""));
            ((DefaultTableModel) jtFmea.getModel()).removeRow(modelRow);
            // System.out.println("getValueAt(modelRow,0)"+jtFmea.getValueAt(modelRow-1,0));
        } else {
            JOptionPane.showMessageDialog(null, "Please select row to delete");
        }

    }

    public void setGroupheading(JTable jtFmea, String[] subHeadings, int[] group) {
        TableColumnModel cm = jtFmea.getColumnModel();

        ColumnGroup fi = new ColumnGroup("<html> <h3> Failure Identification </h3></html>", Color.BLACK);
        for (int i = 1; i < group[0]; i++) {
            fi.add(cm.getColumn(i));
            System.out.println(cm.getColumn(i));
        }
        ColumnGroup fe = new ColumnGroup("<html> <h3>Failure Effects </h3></html>", Color.DARK_GRAY);
        for (int i = group[0]; i < group[1]; i++) {
            fe.add(cm.getColumn(i));

            System.out.println(cm.getColumn(i));
        }
        ColumnGroup drm = new ColumnGroup("<html> <h3>Detection and Recovery Measures </h3></html>", Color.BLACK);
        for (int i = group[1]; i < group[2]; i++) {
            drm.add(cm.getColumn(i));
            System.out.println(cm.getColumn(i));
        }
        ColumnGroup ir = new ColumnGroup("<html> <h3>Improvement Results </h3></html>", Color.DARK_GRAY);
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
        GroupableTableHeader header = (GroupableTableHeader) jtFmea.getTableHeader();
        header.addColumnGroup(fi);
        header.addColumnGroup(fe);
        header.addColumnGroup(drm);
        header.addColumnGroup(ir);
        header.setToolTipStrings(subHeadings);

    }

    public void setGroupheading(JTable jtFmea) {
        TableColumnModel cm = jtFmea.getColumnModel();

        ColumnGroup fi = new ColumnGroup("<html> <h3> Failure Identification </h3></html>", Color.BLACK);
        for (int i = 1; i < 7; i++) {
            fi.add(cm.getColumn(i));
            // System.out.println(cm.getColumn(i));
        }
        ColumnGroup fe = new ColumnGroup("<html> <h3>Failure Effects </h3></html>", Color.DARK_GRAY);
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
        }

        /*g_name.add(cm.getColumn(1));
            g_name.add(cm.getColumn(2));
            ColumnGroup g_lang = new ColumnGroup("Language");
            g_lang.add(cm.getColumn(3));
            ColumnGroup g_other = new ColumnGroup("Others");
            g_other.add(cm.getColumn(4));
            g_other.add(cm.getColumn(5));
            g_lang.add(g_other);*/
        GroupableTableHeader header = (GroupableTableHeader) jtFmea.getTableHeader();
        header.addColumnGroup(fi);
        header.addColumnGroup(fe);
        header.addColumnGroup(drm);
        header.addColumnGroup(ir);
        header.setToolTipStrings(getTableHeadingString());
        //table.setTableHeader(tooltipHeader);

    }

    public String[] getTableHeadingString() {
        String[] columnNames = {"Id", "Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Failure Effect Probability(B)", "Failure Mode Ratio(?)", "Failure Rate(?p)", "Operating Time(t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "ComType", "ComId"};
        return columnNames;
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

    public void insertFmeca(int calcId, Calchdr mpgHdr) {
        Calcfile calcfile = calcfileDao.findById(calcId);
        Fmeca fmecaData = new Fmeca();

        fmecaData.setFISubsysBreakCode(calcfile.getCompId());
        fmecaData.setFISubsysComp(calcfile.getComponents());
        //fmecaData.setFCFailProb(0.00);
        fmecaData.setFCFailEffProb(0.00);
        fmecaData.setFCFailureRate(calcfile.getFailureRate());
        fmecaData.setFIFailmode(calcfile.getFailureMode());
        fmecaData.setFCFailModeRatio(calcfile.getFMPercentage());
        fmecaData.setFCOperatingTime(0.00);

        fmecaData.setFCFailItemCri(0.00);
        fmecaData.setFCFailModeCri(0.00);

        fmecaData.setComType("SubComp");
        fmecaData.setComId(calcfile.getCalcId());
        fmecaData.setCalcComp(calcfile);
        fmecaData.setFmecaHeader(mpgHdr);

        fmecaDao.saveFMECA(fmecaData);

    }

    public void margeData(int mpgHdrId) {
        Calcfile calc;

        try {

            InternalFrameDemo.mainFrame.setLoader();

            for (Fmeca fmeca : fmecaDao.getAllFMECAByHdr(mpgHdrId)) {
                calc = fmeca.getCalcComp();
                fmeca.setFISubsysBreakCode(calc.getCompId());
                fmeca.setFISubsysComp(calc.getComponents());
                fmeca.setFIFailmode(calc.getFailureMode());
                fmeca.setFCFailureRate(calc.getFailureRate());
                fmeca.setFCFailModeRatio(calc.getFMPercentage());
                fmecaDao.UpdateFMECA(fmeca);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();

        }

    }

    public void AddFuntion(Mpghdr mpghdr, Object cal, String std) {
        int resultRef = -2;

        try {
            InternalFrameDemo.mainFrame.setLoader();
            resultRef = calcCurd.addRowFuntion(mpghdr,new CalcFunctionsPopup());
        } finally {
            if (resultRef == JOptionPane.YES_NO_OPTION) {
                if (std.equals("MIL")) {
                    ((FmecaMil1629A) cal).refreshData();
                }

            }

            InternalFrameDemo.mainFrame.removeLoder();
        }
    }

    public void editRowCalFuntion(int calId, Mpghdr mpghdr, Object cal, String std) {
        int result;

        try {
            InternalFrameDemo.mainFrame.setLoader();
            result = calcCurd.editRowFuntion(mpghdr, calId, new CalcFunctionsPopup());
            if (result == JOptionPane.YES_OPTION) {

                if (std.equals("MIL")) {
                    ((FmecaMil1629A) cal).refreshData();
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
            res = fmecaDao.getFMECAById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1)+"", 0)).getCalcComp().getCalcHeader();
        }
    return res;
    }

}
