/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.google.gson.Gson;
import com.topfield.controller.BusinessException;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.CalchdrDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.CalchdrDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.main.InternalFrameDemo;
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
import com.topfield.view.popup.FMEAPopupAdd;
import com.topfield.view.popup.FmeaMil1629APopup;
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
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Murali
 */
public class FmeaRpz extends JPanel implements ActionListener {
    private Calchdr mpgCalcHdr;
    //private Mpghdr mpgHdr, mpgCalcHdr;
    private JTable footer;
    private FMEADao fmeaDao = new FMEADaoImpl();
    private CalchdrDao calchdrDao = new CalchdrDaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FmeaCurd fmeaCurd = new FmeaCurd();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();

    private JPopupMenu popupMenu;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemEdit;
    private JMenuItem menuItemLoad;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemCalculate;
    private JMenuItem mergeCalcData;
    private JMenuItem menuItemRefresh;
    //private JMenuItem menuItemTemplate;
    private ExcelAdapter myAd;

    private int[] tableHeadingGroup = new int[]{8, 11, 20};

    private WordWarpTable jtFmea = new WordWarpTable(-1);

    public FmeaRpz(Calchdr mpgHdr) {

        this.mpgCalcHdr = mpgHdr;
        //this.mpgCalcHdr = mpgHdrDao.getAllFMEAHDRByMPGOne(mpgHdr.getFmeaComponent().getMpgId(), "CMM", UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());

        setLayout(new BorderLayout());

        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE & EFFECTS ANALYSIS (RPZ)  - " + mpgHdr.getMpghdr().getFmeaComponent().getMpgDescription() + "</p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);

        JPanel holdingPanel = new JPanel(new BorderLayout());

        //jtFmea.setRowHeight(50);
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

    public String[] getTableHeadingString() {
        String[] columnNames = {"Line no", "Id", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Importance (B)", "Failure detection", "Preventive and compensating measures", "Occurrence (A)", "Detection measures", "Detection (E)", "RPZ", "Improvement measures", "Responsible / Date", "Undertaken measures", "Importance_I (B)", "Occurrence_I (A)", "Detection_I (E)", "RPZ_I"/*,"Remarks"*/, "ComType", "ComId"};
        return columnNames;
    }

    public void setupRightClick() {

        myAd = new ExcelAdapter(jtFmea);

        // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+V");
        menuItemAdd = new JMenuItem("Add Signle Row");
        menuItemSave = new JMenuItem("Save All");
        menuItemLoad = new JMenuItem("Generate FMEA");
        menuItemEdit = new JMenuItem("Edit Selected Row");
        menuItemRemove = new JMenuItem("Delete Selected Row");
        menuItemCalculate = new JMenuItem("Calculate RPZ");
        mergeCalcData = new JMenuItem("Merge Calcfile Data");
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
        mergeCalcData.addActionListener(this);
        menuItemRefresh.addActionListener(this);
        //menuItemTemplate.addActionListener(this);

        popupMenu.add(menuItemCopy);
        popupMenu.add(menuItemPaste);
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemLoad);
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemCalculate);
        popupMenu.add(mergeCalcData);
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
        List<Integer> addedList = fmeaCurd.getAddedList(jtFmea);
        MgfPopup mgfPopup = new MgfPopup(mpgCalcHdr.getMpghdr(), addedList);

        if (menu == menuItemAdd) {

            dataPopup(false);
        } else if (menu == menuItemSave) {

            new Thread() {
                public void run() {

                    saveAll();
                    refreshData();
                }
            }.start();

        } else if (menu == menuItemLoad) {

            int result = JOptionPane.showOptionDialog(null, mgfPopup, "Select to insert components",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                for (Integer integer : mgfPopup.getSelectedItem()) {
                    fmeaCurd.insertFmea(integer, mpgCalcHdr);
                }
                refreshData();
            }

        } else if (menu == menuItemEdit) {
            if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(), jtFmea) == 1) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed edit from here \n Go to Project Management -> Management System Architecture (Menu bar)");
            } else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(), jtFmea) == 3) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed edit from here \n Go to Calc File Calculator");
            } else {
                dataPopup(true);
            }

        } else if (menu == menuItemRemove) {
            deleteFmeaRow(jtFmea.getSelectedRow());
        } else if (menu == menuItemCopy) {
            myAd.copyFuntion();
        } else if (menu == menuItemPaste) {
            myAd.pasteFuntion();
        } else if (menu == menuItemCalculate) {
            itemCalculateRPZ();
        }else if (menu == mergeCalcData) {
            fmeaCurd.margeData(mpgCalcHdr.getCalchdrid());
            refreshData();
        }/*else if (menu == menuItemTemplate) {
            itemTemplate();
        }*/ else if (menu == menuItemRefresh) {
            refreshData();
        }
    }

    public void dataPopup(boolean edit) {

        int selRow = jtFmea.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();
        //List<Integer> addedList = Arrays.asList(new Integer[]{0,1,2,3,4,5});  
        List<Integer> addedList = fmeaCurd.getAddedList(jtFmea);
        FMEAPopupAdd fmeaPopup = new FMEAPopupAdd(mpgCalcHdr.getMpghdr().getMpghdrid(), addedList, edit);
        Fmea fmeaData = (edit == false) ? new Fmea() : fmeaDao.getFMEAById(NumberConversion.NVL(jtFmea.getValueAt(selRow, 1) + "", 0));

        if (edit) {

            fmeaPopup.setSubsystemBreakdownCode(model.getValueAt(selRow, 2) + "");
            fmeaPopup.setCalcFunction(fmeaData.getCalcComp().getCalcHeader());
            fmeaPopup.setFunctionalComponents(fmeaData.getCalcComp());
            fmeaPopup.setFunction(model.getValueAt(selRow, 4) + "");
            fmeaPopup.setPhase(model.getValueAt(selRow, 5) + "");
            //fmeaPopup.setFailureMode(model.getValueAt(selRow, 6) + "");
            //fmeaPopup.loadSubsystemBreakdownCode(fmeaData.getCalcComp().getCalcId());
            fmeaPopup.setFailureCause(model.getValueAt(selRow, 7) + "");
            fmeaPopup.setLocalSystemLevel(model.getValueAt(selRow, 8) + "");
            fmeaPopup.setTrainSubsystemLevel(model.getValueAt(selRow, 9) + "");
            fmeaPopup.setTrainLevel(model.getValueAt(selRow, 10) + "");

            fmeaPopup.setImportance(NumberConversion.NVL(model.getValueAt(selRow, 11) + "", 0));
            fmeaPopup.setFailureDetection(model.getValueAt(selRow, 12) + "");
            fmeaPopup.setPreventiveMeasures(model.getValueAt(selRow, 13) + "");
            fmeaPopup.setOccurrence(NumberConversion.NVL(model.getValueAt(selRow, 14) + "", 0));
            fmeaPopup.setDetectionMeasures(model.getValueAt(selRow, 15) + "");
            fmeaPopup.setDetection(NumberConversion.NVL(model.getValueAt(selRow, 16) + "", 0));
            fmeaPopup.setRPZ(model.getValueAt(selRow, 17) + "");
            fmeaPopup.setImprovementMeasures(model.getValueAt(selRow, 18) + "");

            fmeaPopup.setResponsibleDateImpr(model.getValueAt(selRow, 19) + "");
            fmeaPopup.setUndertakenMeasures(model.getValueAt(selRow, 20) + "");
            fmeaPopup.setImportanceImpr(NumberConversion.NVL(model.getValueAt(selRow, 21) + "", 0));
            fmeaPopup.setOccurrenceImpr(NumberConversion.NVL(model.getValueAt(selRow, 22) + "", 0));
            fmeaPopup.setDetectionImpr(NumberConversion.NVL(model.getValueAt(selRow, 23) + "", 0));
            fmeaPopup.setRPZImpr(model.getValueAt(selRow, 24) + "");

        }

        int result = JOptionPane.showOptionDialog(null, fmeaPopup, "Select to insert components",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, new String[]{"Save", "Cancel"}, null);

        if (result == JOptionPane.YES_OPTION && fmeaPopup.getFunctionalComponents() != null) {

            fmeaData.setFISubsysBreakdownCode(fmeaPopup.getSubsystemBreakdownCode());
            fmeaData.setFISubsysComponent(fmeaPopup.getFunctionalComponents().getComponents());
            fmeaData.setFIFunctions(fmeaPopup.getFunction());
            fmeaData.setFIPhase(fmeaPopup.getPhase());
            fmeaData.setFIFailuremode(fmeaPopup.getFailureMode());
            fmeaData.setFIFailureCause(fmeaPopup.getFailureCause());
            fmeaData.setFELocalSystemLevel(fmeaPopup.getLocalSystemLevel());
            fmeaData.setFETrainSubsystemLevel(fmeaPopup.getTrainSubsystemLevel());
            fmeaData.setFETrainLevel(fmeaPopup.getTrainLevel());
            fmeaData.setFEImportanceB(fmeaPopup.getImportance());
            fmeaData.setDRMFailuredetection(fmeaPopup.getFailureDetection());
            fmeaData.setDRMPrevAndcompenmeasures(fmeaPopup.getPreventiveMeasures());
            fmeaData.setDRMOccurrenceA(fmeaPopup.getOccurrence());
            fmeaData.setDRMDetectionmeasures(fmeaPopup.getDetectionMeasures());
            fmeaData.setDRMDetectionE(fmeaPopup.getDetection());
            fmeaData.setDrmRpz(NumberConversion.NVL(fmeaPopup.getRPZ(), 0));
            fmeaData.setDRMImprovementmeasures(fmeaPopup.getImprovementMeasures());
            fmeaData.setDRMResponsibleDate(fmeaPopup.getResponsibleDateImpr());
            fmeaData.setIRUndertakenmeasures(fmeaPopup.getUndertakenMeasures());
            fmeaData.setIRImportanceB(fmeaPopup.getImportanceImpr());
            fmeaData.setIROccurrenceA(fmeaPopup.getOccurrenceImpr());
            fmeaData.setIRDetectionE(fmeaPopup.getDetectionImpr());
            fmeaData.setIrRpz(NumberConversion.NVL(fmeaPopup.getRPZImpr(), 0));

            fmeaData.setComType("SubComp");
            fmeaData.setComId(fmeaPopup.getFunctionalComponents().getCalcId());
            fmeaData.setCalcComp(fmeaPopup.getFunctionalComponents());
            fmeaData.setFmeaHeader(mpgCalcHdr);

            if (edit) {
                fmeaDao.UpdateFMEA(fmeaData);
            } else {
                fmeaDao.saveFMEA(fmeaData);
            }
            refreshData();
            UserMessages.setNotificationUserPreference("FMEA001", "Saved", JOptionPane.INFORMATION_MESSAGE);

        } else {

            if (fmeaPopup.getFunctionalComponents() == null) {
                JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functional components is not selected");
            }
            //JOptionPane.showMessageDialog(null, calculator + " of \"" + fmeaHeaderPopup.getMpg().getMpgDescription() + "\" already exist in the Project ");
        }

    }

    public void saveAll() {

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

    public void saveById(int rowId) {

        Fmea fmea = fmeaCurd.getFMEAByRowId((DefaultTableModel) jtFmea.getModel(), mpgCalcHdr, rowId);

        if (fmea != null) {
            fmeaDao.UpdateFMEA(fmea);
        }

    }

    public void deleteFmeaRow(int rowId) {

        if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(), jtFmea) == 1) {
            JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "MainProduct names are not allowed delete from here \n Go to Project Management -> Management System Architecture (Menu bar)");
        } else if (FMEAServices.getSystemLevelsNew(jtFmea.getSelectedRow(), jtFmea) == 3) {
            JOptionPane.showMessageDialog(InternalFrameDemo.contentPanel, "Functions names are not allowed delete from here \n Go to Calc File Calculator");
        } else {
            
            if (BusinessException.showBusinessConformation("Do you want to delete this component? ",true)== JOptionPane.YES_OPTION){
                fmeaDao.deleteFMEA(NumberConversion.NVL(jtFmea.getValueAt(rowId, 1) + "", 0));
                refreshData();
            }   
        }

    }

    public Object[][] getFMEATableData() {

        Object[] temp;
        Object[] topcolumnNames = {"", "Failure Identification", "", "", "", "", "", "", "Failure Effects",
            "", "", "Detection and Recovery Measures", "", "", "", "", "", "", "", "",
            "Improvement Results ", "", "", "", "", ""};

        String[] columnNames = getTableHeadingString();
        //String [] columnNames = ArrayUtils.removeElement(getTableHeadingMil1629A(), "Line no");
        //Object[][] tableData = setAlignExport(fmeaCurd.refreshTableData(jtFmea,mpgHdr,getTableHeadingMil1629A(),new int[]{8,12,20}));

        Object[][] tableData = refreshData();

        Object[][] datatypes = new Object[tableData.length + 2][];
        //System.out.println("tableData.length "+tableData.length);

        datatypes[0] = topcolumnNames;
        datatypes[1] = columnNames;

        for (int i = 0; i < tableData.length; i++) {
            //temp = ArrayUtils.removeElement(tableData[i], tableData[i][0]);
            datatypes[i + 2] = tableData[i];
            System.out.println("Length - " + tableData[i].length);
        }

        return datatypes;
    }

    public Object[][] refreshData() {

        return fmeaCurd.refreshTableData2(jtFmea, mpgCalcHdr, getTableHeadingString(), tableHeadingGroup);
    }

    public void setInfoFooter(String heading, double value, int col) {

        footer.setValueAt(heading, 0, col);
        footer.setValueAt(value, 0, col + 1);

        TableColumnModel columnModel = footer.getColumnModel();
        TableColumn column = columnModel.getColumn(col + 1);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        column.setCellRenderer(renderer);

        // right align 2nd column when editing
        JTextField tf = new JTextField();
        renderer.setToolTipText(value + "");
        tf.setHorizontalAlignment(JTextField.RIGHT);
        column.setCellEditor(new DefaultCellEditor(tf));

        Gson gson = new Gson();
        String[] lang = {footer.getValueAt(0, 1) + "", footer.getValueAt(0, 11) + ""};
        mpgCalcHdr.setSummary(gson.toJson(lang));
        calchdrDao.Update(mpgCalcHdr);
    }

    public void setFoooterData() {
        setInfoFooter("Row Counts", jtFmea.getRowCount(), 0);
        setInfoFooter("RPZ", getTotalRPZ(), 10);

    }

    public double getTotalRPZ() {
        double res = 0.0;

        for (int i = 0; i < jtFmea.getRowCount(); i++) {
            if (jtFmea.getValueAt(i, 24) != null && !(jtFmea.getValueAt(i, 24).equals(""))) {
                res = res + Double.parseDouble(jtFmea.getValueAt(i, 24) + "");
                // System.out.println(" value "+ jtFmea.getValueAt(i,24));
            }
        }

        return res;
    }

    public void itemCalculateRPZ() {
        DefaultTableModel model = (DefaultTableModel) jtFmea.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {

            if (FMEAServices.getSystemLevelsNew(i, model) == 4) {
                try {
                    int res = NumberConversion.NVL(model.getValueAt(i, 11) + "", 0) * NumberConversion.NVL(model.getValueAt(i, 14) + "", 0) * NumberConversion.NVL(model.getValueAt(i, 16) + "", 0);
                    model.setValueAt(res, i, 17);

                } catch (NumberFormatException e2) {
                    model.setValueAt(0, i, 17);
                } catch (ArrayStoreException e3) {
                    model.setValueAt(0, i, 17);
                }

            }

        }

        for (int i = 0; i < model.getRowCount(); i++) {

            if (FMEAServices.getSystemLevelsNew(i, model) == 4) {
                try {
                    int res = Integer.parseInt(model.getValueAt(i, 21) + "") * Integer.parseInt(model.getValueAt(i, 22) + "") * Integer.parseInt(model.getValueAt(i, 23) + "");
                    model.setValueAt(res, i, 24);

                } catch (NumberFormatException e2) {
                    model.setValueAt(23, i, 24);
                } catch (ArrayStoreException e3) {
                    model.setValueAt((Object) (10), i, 24);
                }

            }

        }

        jtFmea.changeSelection(0, jtFmea.getColumnCount() - 1, false, false);

        jtFmea.setColumnSelectionAllowed(true);
        jtFmea.setRowSelectionAllowed(false);

        // Select a column - column 0
        jtFmea.setColumnSelectionInterval(24, 24);

    }

    public int[] getMargeCol() {

        return tableHeadingGroup;
    }

}
