/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.calculators.fmeca.FMECACrud;
import com.topfield.controller.BusinessException;
import com.topfield.controller.Equations;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.DatareferDao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Fmeca;
import com.topfield.modal.Mpghdr;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.utilities.ExcelAdapter;
import com.topfield.utilities.ExcelExporter;
import com.topfield.utilities.FMEACellRenderer;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.TableColumnHider;
import com.topfield.utilities.TableMouseListener;
import com.topfield.view.popup.CalcFilePopup;
import com.topfield.view.popup.CalcFunctionsPopup;
import com.topfield.view.popup.InsertCCF;
import com.topfield.view.popup.PopupTable;
import com.topfiled.interfaces.AppsCommon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultCellEditor;
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
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
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
public class CalcFile extends JPanel implements ActionListener, AppsCommon {

    private CalcCurd calcCurd = new CalcCurd();
    private FMECADao fMECADao = new FMECADaoImpl();
    private FMEAServices fMEAServices = new FMEAServices();
    private FMECACrud fMECACrud = new FMECACrud();

    private CalcFunctionsDao calcFunDao = new CalcFunctionsDaoImpl();
    private CalcfileDao CalcfileDao = new CalcfileDaoImpl();
    private DatareferDao datareferDao = new DatareferDaoImpl();

    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao subDao = new SubProductGroupDaoImpl();
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private Equations equations = new Equations();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();

    private Mpghdr mpghdr;
    private ExcelAdapter myAd;
    private JTable footer;

    private JPopupMenu popupMenu;
    private JMenuItem menuItemCopy;
    private JMenuItem menuItemPaste;
    private JMenuItem menuItemSave;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemAddYourCom;
    private JMenuItem menuItemAddAllCom;
    private JMenuItem menuItemAddCCFCom;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemCalculate;
    private JMenuItem menuItemfaliureSwith;
    private JMenuItem menuItemRefresh;
    private JMenuItem viewUsage;
    private JMenuItem duplicateRow;

    public int col = 0;
    public int row = 0;
    public String data = "";

    private DefaultTableModel model = new DefaultTableModel();
    private JTable jtCalc = new JTable(model) {
        protected JTableHeader createDefaultTableHeader() {
            return new GroupableTableHeader(columnModel);
        }

        public void setValueAt(Object aValue, int row, int column) {

            List<String> val = CMMValidation.inputValidationValue(aValue, column);

            if (val.size() > 0) {
                BusinessException.showBusinessException(val.get(0), val.get(1), JOptionPane.WARNING_MESSAGE);
            } else {
                getModel().setValueAt(aValue, convertRowIndexToModel(row),
                        convertColumnIndexToModel(column));
            }

        }
    };

    public CalcFile(Mpghdr mpghdrs) {

        setLayout(new BorderLayout());


        /*JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAILURE MODE EFFECTS AND CRITICALITY ANALYSIS (FMECA) - "+fmeas.getFmeaComponent().getMpgDescription()+" </p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);*/
        this.mpghdr = mpghdrs;
        myAd = new ExcelAdapter(jtCalc);
        fMEAServices.setCopyPasteFunction(jtCalc, myAd);
        calcCurd.refreshTableData(jtCalc, mpghdr);
        setupRightClick();

        jtCalc.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        jtCalc.setGridColor(Color.LIGHT_GRAY);

        jtCalc.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {  //mouseEntered
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //jtFmea.setColumnSelectionAllowed(false);
                    //jtFmea.setRowSelectionAllowed(true);
                    jtCalc.setCellSelectionEnabled(true);

                    try {
                        int row = jtCalc.rowAtPoint(e.getPoint());
                        int col = jtCalc.columnAtPoint(e.getPoint());
                        jtCalc.setToolTipText(jtCalc.getValueAt(row, col).toString());

                        if (e.getClickCount() == 2) {
                            System.out.println("double clicked");
                            editCMM();
                        }

                    } catch (Exception rowError) {

                    }

                }
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    // System.out.println("Left button clicked");
                } else if (arg0.getButton() == MouseEvent.BUTTON2) {
                    //  System.out.println("Middle button clicked");
                } else if (arg0.getButton() == MouseEvent.BUTTON3) {
                    //  System.out.println("Right button clicked");
                }
            }

        }
        );

        /* jtCalc.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {

                data = "";
                col = e.getColumn();
                row = e.getFirstRow();
                System.out.println("Table change " + e.getColumn() + " " + e.getFirstRow());
                // String res =jtCalc.getValueAt(e.getColumn(), jtCalc.getSelectedRow()).toString();
                System.out.println("get Val " + jtCalc.getValueAt(e.getColumn(), e.getFirstRow()));
                //jtCalc.setValueAt(jtCalc.getValueAt(jtCalc.getSelectedRow(),jtCalc.getSelectedColumn()), jtCalc.getSelectedRow(), jtCalc.getSelectedColumn());
                List<String> val = CMMValidation.inputValidation(jtCalc, jtCalc.getSelectedRow(), jtCalc.getSelectedColumn());

                if (val.size() > 0) {
                    BusinessException.showBusinessException(val.get(0), val.get(1), JOptionPane.WARNING_MESSAGE);
                    data = jtCalc.getValueAt(e.getColumn(), e.getFirstRow()) + " - " + e.getSource();
                    if (!data.isEmpty()) {
                        jtCalc.getModel().setValueAt(data, row, col);
                    }
                }

            }
        });*/
        jtCalc.setOpaque(true);

        JScroll center = new JScroll(jtCalc);
        //jtFmea.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        //JScrollPane foot =new JScrollPane(new footerInfo());

        footer = new JTable(1, 12);
        footer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JPanel holdingPanel = new JPanel(new BorderLayout());
        holdingPanel.add(center, BorderLayout.CENTER);
        holdingPanel.add(footer, BorderLayout.SOUTH);

        add(holdingPanel, BorderLayout.CENTER);
        //setFoooterData();

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

    }

    public void setFoooterData() {
        setInfoFooter("Row Counts", jtCalc.getRowCount(), 0);
        setInfoFooter("Cm", getTotalCriticalityMode(), 10);

    }

    public double getTotalCriticalityMode() {
        double res = 0.0;

        for (int i = 0; i < jtCalc.getRowCount(); i++) {
            if (jtCalc.getValueAt(i, 17) != null && !(jtCalc.getValueAt(i, 17).equals(""))) {
                try {
                    res = res + Double.parseDouble(jtCalc.getValueAt(i, 17) + "");
                } catch (Exception e) {
                }

                // System.out.println(" value "+ jtFmea.getValueAt(i,24));
            }
        }

        return res;
    }

    public double getTotalCriticalityItem() {
        double res = 0.0;

        for (int i = 0; i < jtCalc.getRowCount(); i++) {
            if (jtCalc.getValueAt(i, 17) != null && !(jtCalc.getValueAt(i, 17).equals(""))) {
                try {
                    res = res + Double.parseDouble(jtCalc.getValueAt(i, 17) + "");
                } catch (Exception e) {
                }

                // System.out.println(" value "+ jtFmea.getValueAt(i,24));
            }
        }

        return res;
    }

    public void setupRightClick() {

        // constructs the popup menu
        popupMenu = new JPopupMenu();
        menuItemCopy = new JMenuItem("Copy Ctrl+C");
        menuItemPaste = new JMenuItem("Paste Ctrl+P");
        menuItemAdd = new JMenuItem("Add Function");
        menuItemAddAllCom = new JMenuItem("Add Sub Component");  //Add All Components
        menuItemAddCCFCom = new JMenuItem("Add CCF Component");  //Add All Components
        menuItemAddYourCom = new JMenuItem("Edit selected row");  //"Add Your Components"
        duplicateRow = new JMenuItem("Duplicate Row");
        menuItemSave = new JMenuItem("Save All");
        menuItemRemove = new JMenuItem("Delete Selected Row");
        menuItemCalculate = new JMenuItem("Calculate Unrevealed Failure rate");
        menuItemRefresh = new JMenuItem("Refresh");
        //menuItemfaliureSwith = new JMenuItem("Show Failure mode criticality");
        viewUsage = new JMenuItem("View Usage");

        menuItemCopy.addActionListener(this);
        menuItemPaste.addActionListener(this);
        menuItemAdd.addActionListener(this);
        menuItemAddAllCom.addActionListener(this);
        menuItemAddYourCom.addActionListener(this);
        duplicateRow.addActionListener(this);
        menuItemAddCCFCom.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemCalculate.addActionListener(this);
        menuItemRefresh.addActionListener(this);
        //menuItemfaliureSwith.addActionListener(this);
        viewUsage.addActionListener(this);

        popupMenu.add(menuItemCopy);
        popupMenu.add(menuItemPaste);
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemAddAllCom);
        popupMenu.add(duplicateRow);
        popupMenu.add(menuItemAddCCFCom);
        popupMenu.add(menuItemAddYourCom);
        popupMenu.add(menuItemSave);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemCalculate);
        popupMenu.add(menuItemRefresh);
        //popupMenu.add(menuItemfaliureSwith);
        popupMenu.add(viewUsage);

        // sets the popup menu for the table
        jtCalc.setComponentPopupMenu(popupMenu);

        jtCalc.addMouseListener(new TableMouseListener(jtCalc));
        // jtFmea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        for (int i = 0; i < jtCalc.getColumnCount(); i++) {

            jtCalc.setDefaultRenderer(jtCalc.getColumnClass(i), new CalcFileCellRenderer());

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menu = (JMenuItem) e.getSource();
        // jtFmea.setColumnSelectionAllowed(false);
        //  jtFmea.setRowSelectionAllowed(true);

        if (menu == menuItemAdd) {
            try {
                InternalFrameDemo.mainFrame.setLoader();
                CalcFunctionsPopup calcFunctionsPopup = new CalcFunctionsPopup();
                calcCurd.addRowFuntion(mpghdr, calcFunctionsPopup);
            } finally {
                calcCurd.refreshTableData(jtCalc, mpghdr);
                InternalFrameDemo.mainFrame.removeLoder();
            }

        } else if (menu == menuItemAddAllCom) {
            CalcFilePopup calcFilePopup = new CalcFilePopup(mpghdr.getCalname());
            addRowSubComp(calcFilePopup);
        } else if (menu == menuItemAddCCFCom) {
            addRowCCFComp(false);
        } else if (menu == menuItemAddYourCom) {
            editCMM();
        } else if (menu == duplicateRow) {
            duplicateRow();
        } else if (menu == menuItemSave) {
            saveAll();
        } else if (menu == menuItemRemove) {
            delete();
        } else if (menu == menuItemCopy) {
            myAd.copyFuntion();
        } else if (menu == menuItemPaste) {
            myAd.pasteFuntion();
        } else if (menu == menuItemCalculate) {
            CalculateURFailurerate();
        } else if (menu == menuItemRefresh) {
            try {
                InternalFrameDemo.mainFrame.setLoader();
                calcCurd.refreshTableData(jtCalc, mpghdr);
            } finally {
                InternalFrameDemo.mainFrame.removeLoder();
            }

        } else if (menu == viewUsage) {
            ViewUsageCalcFile(calcCurd.isFunction(jtCalc, jtCalc.getSelectedRow()));
        }/*else if (menu == menuItemTemplate) {
             getFMEATableDataTemlate();
        }*/

        // setFoooterData();
    }

    public void editCMM() {

        if (calcCurd.isFunction(jtCalc, jtCalc.getSelectedRow())) {
            editRowCalFuntion();
        } else if (calcCurd.isCCF(jtCalc, jtCalc.getSelectedRow())) {
            addRowCCFComp(true);
        } else {
            CalcFilePopup calcFilePopup = new CalcFilePopup(mpghdr.getCalname());
            editRowSubComp(calcFilePopup);
        }

    }

    public void addRowSubComp(CalcFilePopup calcFilePopup) {

        int selectedRow = jtCalc.getSelectedRow();
        Object[] options1 = {"Add", "Cancel"};
        Calcfile calcFile = new Calcfile();
        List<String> val;

        if (selectedRow >= 0) {

            int result = JOptionPane.showOptionDialog(null, calcFilePopup, "Select to insert components",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {
                val = CMMValidation.inputValidation(calcFilePopup);

                if (val.isEmpty()) {
                    InternalFrameDemo.mainFrame.setLoader();

                    calcFile.setCompId(calcFilePopup.getComponentId());
                    calcFile.setComponents(calcFilePopup.getComponentName());
                    calcFile.setFailureRate(calcFilePopup.getFailureRate());
                    calcFile.setFailureMode(calcFilePopup.getFailureMode());
                    calcFile.setProbability(calcFilePopup.getProbability());
                    calcFile.setFMPercentage(calcFilePopup.getFailureModePer());
                    calcFile.setURPercentage(calcFilePopup.getUNRFailurePer());
                    calcFile.setRemarks(calcFilePopup.getRemarks());
                    calcFile.setReference(calcFilePopup.getReference());
                    calcFile.setCalcHeader(calcCurd.getCalcFunctionsByRow(jtCalc, selectedRow));

                    CalcfileDao.Save(calcFile);

                    calcCurd.refreshTableData(jtCalc, mpghdr);
                    InternalFrameDemo.mainFrame.removeLoder();

                } else {
                    BusinessException.showBusinessException(val.get(0), val.get(1), JOptionPane.WARNING_MESSAGE);
                    addRowSubComp(calcFilePopup);

                }

            }

        }

    }

    public void addRowCCFComp(boolean edit) {

        int selectedRow = jtCalc.getSelectedRow();
        Object[] options1 = {"Save", "Cancel"};
        Calcfile calcFile = edit ? CalcfileDao.findById(NumberConversion.NVL(jtCalc.getValueAt(selectedRow, 0) + "", 0)) : new Calcfile();
        List<String> contains = edit ? Arrays.asList(calcFile.getRemarks().split("\\#")) : new ArrayList<>();
        InsertCCF insertCCF = new InsertCCF(calcCurd.getCalcFunctionsByRow(jtCalc, selectedRow), contains);
        String[] CCFhdr;

        if (edit) {
            CCFhdr = calcFile.getFailureMode().split("\\#");
            insertCCF.setDescription(jtCalc.getValueAt(selectedRow, 3) + "");
            if (CCFhdr.length > 1) {
                insertCCF.setCCFHdr(CCFhdr[0], CCFhdr[1]);
            }

        }

        if (selectedRow >= 0) {

            int result = JOptionPane.showOptionDialog(null, insertCCF, "Select to insert components",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                InternalFrameDemo.mainFrame.setLoader();

                calcFile.setCompId("CCF");
                calcFile.setComponents(insertCCF.getDescription());
                calcFile.setFailureRate(insertCCF.getCCFFailureRate());
                // CalcFile.setFailureMode(insertCCF.getCCFHdr().getSpfhdrid()+"#"+insertCCF.getCCFHdr().getDescriptions());
                calcFile.setFailureMode(insertCCF.getCCFHdrString());
                calcFile.setProbability(0.0);
                calcFile.setFMPercentage(insertCCF.getFailmodePre());
                calcFile.setURPercentage(insertCCF.getUnreveledPre());
                calcFile.setRemarks(insertCCF.getCheckBoxList().getSelectedItemNames());
                calcFile.setReference(datareferDao.findById(3));
                calcFile.setCalcHeader(calcCurd.getCalcFunctionsByRow(jtCalc, selectedRow));

                if (edit) {
                    CalcfileDao.Update(calcFile);
                } else {
                    CalcfileDao.Save(calcFile);
                }

                calcCurd.refreshTableData(jtCalc, mpghdr);
                InternalFrameDemo.mainFrame.removeLoder();
            }

        }

    }

    public void editRowCalFuntion() {
        int selectedRow = jtCalc.getSelectedRow();

        if (selectedRow >= 0) {

            try {
                InternalFrameDemo.mainFrame.setLoader();
                calcCurd.editRowFuntion(mpghdr, NumberConversion.NVL(jtCalc.getValueAt(selectedRow, 0) + "", 0), new CalcFunctionsPopup());
                calcCurd.refreshTableData(jtCalc, mpghdr);

            } finally {
                InternalFrameDemo.mainFrame.removeLoder();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select row to edit");
        }

    }

    public void editRowSubComp(CalcFilePopup calcFilePopup) {

        int selectedRow = jtCalc.getSelectedRow();
        Object[] options1 = {"Save", "Cancel"};
        Calcfile calcFile = null;
        List<String> val;

        if (selectedRow >= 0) {

            calcFile = CalcfileDao.findById(NumberConversion.NVL(jtCalc.getValueAt(selectedRow, 0) + "", 0));
            calcFilePopup.setComponentId(calcFile.getCompId());
            calcFilePopup.setComponentName(calcFile.getComponents());
            calcFilePopup.setFailureRate(calcFile.getFailureRate());
            calcFilePopup.setProbability(calcFile.getProbability());
            calcFilePopup.setFailureMode(calcFile.getFailureMode());
            calcFilePopup.setFailureModePer(calcFile.getFMPercentage());
            calcFilePopup.setUNRFailurePer(calcFile.getURPercentage());
            calcFilePopup.setRemarks(calcFile.getRemarks());
            calcFilePopup.setReference(calcFile.getReference());

            int result = JOptionPane.showOptionDialog(null, calcFilePopup, "Select to insert components",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                val = CMMValidation.inputValidation(calcFilePopup);

                if (val.isEmpty()) {
                    InternalFrameDemo.mainFrame.setLoader();

                    calcFile.setCompId(calcFilePopup.getComponentId());
                    calcFile.setComponents(calcFilePopup.getComponentName());
                    calcFile.setFailureRate(calcFilePopup.getFailureRate());
                    calcFile.setFailureMode(calcFilePopup.getFailureMode());
                    calcFile.setProbability(calcFilePopup.getProbability());
                    calcFile.setFMPercentage(calcFilePopup.getFailureModePer());
                    calcFile.setURPercentage(calcFilePopup.getUNRFailurePer());
                    calcFile.setRemarks(calcFilePopup.getRemarks());
                    calcFile.setReference(calcFilePopup.getReference());
                    calcFile.setCalcHeader(calcCurd.getCalcFunctionsByRow(jtCalc, selectedRow));

                    CalcfileDao.Update(calcFile);

                    calcCurd.refreshTableData(jtCalc, mpghdr);
                    InternalFrameDemo.mainFrame.removeLoder();

                } else {
                    BusinessException.showBusinessException(val.get(0), val.get(1), JOptionPane.WARNING_MESSAGE);
                    editRowSubComp(calcFilePopup);

                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select row to edit");
        }

    }

    public void saveAll() {

        model = (DefaultTableModel) jtCalc.getModel();

        if (jtCalc.isEditing()) {
            // Get the current editor
            TableCellEditor editor = jtCalc.getCellEditor();
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
            InternalFrameDemo.mainFrame.setLoader();

            for (int i = 0; i < jtCalc.getRowCount(); i++) {

                if (calcCurd.isFunction(jtCalc, i)) {
                    calcFunDao.Update(calcCurd.getCALCFunByRowId(model, mpghdr, i));
                } else {
                    CalcfileDao.Update(calcCurd.getCALCByRowId(model, mpghdr, i));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();
        }

    }

    private void delete() {

        int selRow = jtCalc.getSelectedRow();
        int id;
        if (selRow >= 0) {
            try {

                InternalFrameDemo.mainFrame.setLoader();
                id = NumberConversion.NVL(jtCalc.getValueAt(selRow, 0) + "", -1);
                if (calcCurd.isFunction(jtCalc, selRow)) {

                    if (JOptionPane.showConfirmDialog(null, "Are you sure? This will delete all components belongs to it", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        if (CMMValidation.checkFUNChildExists(id)) {
                            JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame, "The Function Data is used in the APPs \n It can't be deleted ....!!! \n "
                                    + " \n Please view Usage Table", "Delete Not Allowed", JOptionPane.ERROR_MESSAGE);
                        } else {
                            calcFunDao.remove(id);
                            calcCurd.refreshTableData(jtCalc, mpghdr);
                        }

                    }

                } else {

                    if (CMMValidation.checkCMMChildExists(id)) {
                        JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame, "The CMM Data is used in the APPs \n It can't be deleted ....!!! \n "
                                + " \n Please view Usage Table", "Delete Not Allowed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        
                        if (BusinessException.showBusinessConformation("Do you want to delete this component? ",true)== JOptionPane.YES_OPTION){
                        CalcfileDao.remove(NumberConversion.NVL(jtCalc.getValueAt(selRow, 0) + "", -1));
                        calcCurd.refreshTableData(jtCalc, mpghdr);
                        
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                InternalFrameDemo.mainFrame.removeLoder();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please select row to delete");
        }

    }

    private void CalculateURFailurerate() {

        model = (DefaultTableModel) jtCalc.getModel();

        try {
            InternalFrameDemo.mainFrame.setLoader();
            for (int i = 0; i < model.getRowCount(); i++) {

                if (!calcCurd.isFunction(jtCalc, i)) {
                    Double res = NumberConversion.NVLDouble(model.getValueAt(i, 6) + "", 0) * NumberConversion.NVLDouble(model.getValueAt(i, 8) + "", 0) * NumberConversion.NVLDouble(model.getValueAt(i, 9) + "", 0);
                    model.setValueAt(NumberConversion.scientificNotation(res, 2), i, 10);
                }
            }

        } catch (Exception e2) {
            e2.printStackTrace();

        } finally {
            InternalFrameDemo.mainFrame.removeLoder();
        }

        /*
              jtCalc.changeSelection(0, jtCalc.getColumnCount()-1, false, false);
              jtCalc.setColumnSelectionAllowed(true);
              jtCalc.setRowSelectionAllowed(false);
                // Select a column - column 0
              jtCalc.setColumnSelectionInterval(9, 9);
         */
        //setFoooterData();
    }

    private void itemfaliureSwith() {
        //menuItemfaliureSwith.setTips("Failure mode criticality");
        final TableColumnHider hider = new TableColumnHider(jtCalc);
        hider.show("Failure Mode Criticality(Cm)");
        hider.show("Failure Item Criticality(Cr)");
        if (menuItemfaliureSwith.getText().equals("Show Failure item criticality")) {
            //menuItemfaliureSwith.setTips("Failure item criticality");
            menuItemfaliureSwith.setText("Show Failure mode criticality");
            hider.show("Failure Item Criticality(Cr)");
            hider.hide("Failure Mode Criticality(Cm)");
            JOptionPane.showMessageDialog(null, "Your data save successfully");
            // setInfoFooter("Cr",getTotalCriticalityItem(), 10);
        } else {

            menuItemfaliureSwith.setText("Show Failure item criticality");
            //menuItemfaliureSwith.setTips("Failure mode criticality");
            hider.show("Failure Mode Criticality(Cm)");
            hider.hide("Failure Item Criticality(Cr)");
            // setInfoFooter("Cm",getTotalCriticalityMode(), 10);

        }
    }

    private void ViewUsageCalcFile(boolean fun) {

        Object[] options1 = {"OK", "Cancel"};
        int selRow = jtCalc.getSelectedRow();
        int mpgId = Integer.parseInt(jtCalc.getValueAt(selRow, 0) + "");
        String data[][];
        if (fun) {
            data = CMMValidation.list2DtoArray(CMMValidation.getFunElementList(calcFunDao.findById(mpgId)));
        } else {
            data = CMMValidation.list2DtoArray(CMMValidation.getCalcElementList(CalcfileDao.findById(mpgId)));
        }

        PopupTable poupUp = new PopupTable();
        poupUp.setData(data);

        int result = JOptionPane.showOptionDialog(InternalFrameDemo.contentPanel, poupUp, "Select to insert components",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        if (result == JOptionPane.YES_OPTION) {

        }

    }

    private void duplicateRow() {
        int selectedRow = jtCalc.getSelectedRow();
        Calcfile calcfile;
        Calcfile calcfileRef;
        if (calcCurd.isFunction(jtCalc, selectedRow)) {
            BusinessException.showBusinessException("Can't Duplicate", "Funtion can't be duplicated", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                model = (DefaultTableModel)jtCalc.getModel();
                calcfile = calcCurd.getCALCByRowId(model, mpghdr, selectedRow);
                calcfileRef = new Calcfile();
 
               calcfileRef.setCompId(calcfile.getCompId());
               calcfileRef.setComponents(calcfile.getComponents());
               calcfileRef.setFailureRate(calcfile.getFailureRate());
               calcfileRef.setProbability(calcfile.getProbability());
               calcfileRef.setFailureMode(calcfile.getFailureMode());
               calcfileRef.setFMPercentage(calcfile.getFMPercentage());
               calcfileRef.setURPercentage(calcfile.getURPercentage());
               calcfileRef.setRemarks(calcfile.getRemarks());
               calcfileRef.setCalcHeader(calcfile.getCalcHeader());
               calcfileRef.setReference(calcfile.getReference());
                CalcfileDao.Save(calcfileRef);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                calcCurd.refreshTableData(jtCalc, mpghdr);
                InternalFrameDemo.mainFrame.removeLoder();
            }
        }
    }

    public class footerInfo extends JPanel {

        public footerInfo() {

            try {
                setLayout(new FlowLayout(FlowLayout.LEFT));

                JComboBox comboBox = new JComboBox();
                comboBox.setPreferredSize(new Dimension(250, 25));
                // comboBox.setMaximumRowCount(3);
                //comboBox.setPrototypeDisplayValue(" None of the above ");
                comboBox.addActionListener(e -> {
                    JComboBox c = (JComboBox) e.getSource();
                    Item item = (Item) c.getSelectedItem();
                    // MpgId =item.getId();
                    //  System.out.println(item.getId() + " : " + item.getDescription());

                    //fMECACrud.refreshTableData(jtCalc, mpghdr.getMpghdrid());
                    calcCurd.refreshTableData(jtCalc, mpghdr);
                    setFoooterData();
                    //JButton button3 = new JButton("Long-Named Button 4 (PAGE_END)");

                });

            } catch (Exception e) {

            } finally {
                InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

        }

    }

    public void refreshTable(DefaultTableModel dm) {
        int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }

    }

    public Object[][] getFMEATableData() {

        /*Object[] topcolumnNames = {"Failure Identification", "", "", "", "", "", "Failure Effects",
            "", "", "", "Detection and Recovery Measures", "",
            "Failure Effect ", "", "", "", "", "", ""};

        Object[] columnNames = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Failure Effect Probability(?)", "Failure Mode Ratio(?)", "Failure Rate(?p)", "Operating Time(t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "Created Time"};*/
        //Object[][] tableData = calcCurd.refreshTableData(jtCalc, mpghdr);
        Object[][] datatypes = new Object[jtCalc.getRowCount() + 1][calcCurd.getTableHeadingString().length + 1];
        //System.out.println("tableData.length "+tableData.length);

        for (int i = 0; i < calcCurd.getTableHeadingString().length; i++) {
            datatypes[0][i] = calcCurd.getTableHeadingString()[i];
        }

        datatypes[0][calcCurd.getTableHeadingString().length] = " ";
        //datatypes[1] = columnNames;
        Object[] temp;
        for (int i = 0; i < jtCalc.getRowCount(); i++) {
            //temp = ArrayUtils.removeElement(tableData[i], tableData[i][0]);
            for (int j = 0; j < jtCalc.getColumnCount(); j++) {
                datatypes[i + 1][j] = jtCalc.getValueAt(i, j);
            }
            datatypes[i + 1][calcCurd.getTableHeadingString().length] = calcCurd.isFunction(jtCalc, i) ? "Functions" : "SubComp";
        }

        return datatypes;
    }

    public void getFMEATableDataTemlate() {

        /*Object[] topcolumnNames = {"Failure Identification", "", "", "", "", "", "Failure Effects",
            "", "", "", "Detection and Recovery Measures", "",
            "Failure Effect ", "", "", "", "", "", ""};

        Object[] columnNames = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Failure Effect Probability(?)", "Failure Mode Ratio(?)", "Failure Rate(?p)", "Operating Time(t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "Created Time"};*/
        Object[][] tableData = fMECACrud.refreshTableData(jtCalc, mpghdr.getMpghdrid());

        Object[][] datatypes = new Object[tableData.length + 1][calcCurd.getTableHeadingString().length];
        //System.out.println("tableData.length "+tableData.length);

        datatypes[0] = calcCurd.getTableHeadingString();
        //datatypes[1] = columnNames;

        ExcelExporter e = new ExcelExporter();
        e.WriteExcel("FMECA_Template", datatypes);
    }

    public void progressImportSetup(Object[][] tableData) {

        new Thread() {
            public void run() {

                if (fMEAServices.checkExistFmeaData(jtCalc) == 1) {
                    if (fMEAServices.infoFileFormat() == 1) {
                        if (fMEAServices.validateImport(tableData) == 1) {
                            fMEAServices.setFMEATableDataInstanly(jtCalc, tableData, fMEAServices.productSepration(tableData), (fMECACrud.getTableHeadingString().length - 2));

                        }
                    }
                }

            }
        }.start();

    }

    public void setFMEATableData(Object[][] tableData) {

        Object[] first = new Object[1];
//first[0] =1;

        Object[][] result = new Object[tableData.length][];

        for (int i = 0; i < tableData.length; i++) {
            result[i] = ArrayUtils.addAll(first, tableData[i]);
        }

        model = new DefaultTableModel(result, fMECACrud.getTableHeadingString());
        jtCalc.setModel(model);
        fMECACrud.setGroupheading(jtCalc);
    }

    @Override
    public int saveApp() {
        int flag = 0;

        try {
            saveAll();
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException.showBusinessException("CM0207012", "Error while saving the app  - " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            flag = 1;
        }

        return flag;
    }

}
