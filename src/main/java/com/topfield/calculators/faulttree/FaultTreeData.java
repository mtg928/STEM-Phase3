/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.utilities.AccessReader;
import com.topfield.utilities.ExcelExporter;
import com.topfield.utilities.FileSelector;
import com.topfield.utilities.GroupableTableHeader;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.TclSession;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.topfield.controller.FaulttreeEquations;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FaultdataDao;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Faulttree;
import com.topfield.modal.Fmea;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Spfhdr;
import com.topfield.modal.Spghdr;
import com.topfield.themes.PanelButton;
import com.topfield.themes.TopPanelButton;
import com.topfield.utilities.ScientificNotation;
import com.topfield.view.popup.FTABasicPopup;
import com.topfield.view.popup.FTAGatePopup;
import com.topfield.view.popup.FaultDataPopup;
import com.topfield.view.popup.FaultTreeChildSettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author Murali
 */
public class FaultTreeData extends JPanel {

    private ScientificNotation sciNotation = new ScientificNotation("0.00E0");
    
    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    private FaultDataCrud faultDataCrud = new FaultDataCrud();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private Spfhdr spfhdr;
    private FaultTreeChildSettings dialog;
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private AccessReader ar = new AccessReader();
    private FaultTreeCalculator parent;

    private DefaultTableModel model1 = new DefaultTableModel(new Object[1][],
            getHeadings());

    JTable jtFaultTree = new JTable(model1){  
       public boolean isCellEditable(int row,int column){  
         //Object o = getValueAt(row,column);  
         if(column ==0 || column ==8|| column ==10|| column ==11) return false;   //setBackground(new Color(244, 246, 246));
          return true;  
       }  
     }; 
    
    /*{
      protected JTableHeader createDefaultTableHeader() {
          return new GroupableTableHeader(columnModel);
      }
     };*/


    public FaultTreeData(FaultTreeCalculator parent, Spfhdr spfhdr) {

        setLayout(new BorderLayout());
        this.spfhdr = spfhdr;
        this.parent = parent;

        JPanel caption = new JPanel();
        JLabel captiontxt = new JLabel("<html> <body> <center> <p>FAULT TREE ANALYSIS</p> </center> </body> </html>");
        caption.setBackground(Color.lightGray);
        caption.setAlignmentX(Component.CENTER_ALIGNMENT);
        caption.add(captiontxt);
        dialog = new FaultTreeChildSettings(jtFaultTree);

        // JPanel tableFmea = new JPanel();
        // model = new DefaultTableModel(data,column.toArray());
        /// jtFmea=new JTable(model); 
        //resizeColumnWidth(jtFmea);
        //tableFmea.add(jtFmea);
        Color ivory = new Color(255, 255, 208);
        jtFaultTree.setBackground(ivory);

        jtFaultTree.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {  //mouseEntered

                jtFaultTree.setColumnSelectionAllowed(false);
                jtFaultTree.setRowSelectionAllowed(true);

                try {
                    int row = jtFaultTree.rowAtPoint(e.getPoint());
                    int col = jtFaultTree.columnAtPoint(e.getPoint());

                    if (col == 13 || col == 14) {
                        // JOptionPane.showMessageDialog(null,"Value in the cell clicked :"+" "+jtFaultTree.getValueAt(row,col).toString());

                        dialog.getDualListBox().clearSourceListModel();
                        dialog.getDualListBox().clearDestinationListModel();
                        dialog.setSelectedGate(jtFaultTree.getValueAt(row, 14).equals("AND") ? 1 : 2);
                        dialog.getDualListBox().addSourceElements(faultData());
                        dialog.getDualListBox().addDestinationElements(faultDataCurrentSel(jtFaultTree.getValueAt(row, 13).toString().split("\\#")));
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);

                        //System.out.println("jtFaultTree.getValueAt(row, 13) "+jtFaultTree.getValueAt(row, 13));
                    }

                } catch (Exception rowError) {

                    rowError.printStackTrace();

                }

            }
        }
        );

        jtFaultTree.setOpaque(true);
        jtFaultTree.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        jtFaultTree.setGridColor(Color.LIGHT_GRAY);
        
        JTableHeader header = jtFaultTree.getTableHeader();
        header.setOpaque(true);
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.white);

        //header.setPreferredSize(new Dimension(6000,50));
        // jtFmea.setPreferredSize(new Dimension(6000,jtFmea.getPreferredSize().height ));

        JPanel container = new JPanel(new BorderLayout());
        // Add header in NORTH slot
        container.add(jtFaultTree.getTableHeader(), BorderLayout.NORTH);
        // Add table itself to CENTER slot
        container.add(jtFaultTree, BorderLayout.CENTER);

        loadData();

        //setPreferredSize(new Dimension(6000, HEIGHT));
        add(caption, BorderLayout.PAGE_START);
        add(new footerInfo(), BorderLayout.PAGE_END);
        add(new JScrollPane(container, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);

    }
    
    public JTable getFaultTable(){
    
    return jtFaultTree;
    }

    public String findById(String node) {
        String res = "";

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            //System.out.println("cscd"+jtFaultTree.getValueAt(i, 0));
            if ((jtFaultTree.getValueAt(i, 0) + "").equals(node)) {

                //res = /*jtFaultTree.getValueAt(i, 3) + "- " +*/ jtFaultTree.getValueAt(i, 4)  + "\n F = " + NumberConversion.scientificNotation(jtFaultTree.getValueAt(i, 5) + "", 2) + "\n P = " + NumberConversion.scientificNotation(jtFaultTree.getValueAt(i, 6) + "", 2);
                  res = jtFaultTree.getValueAt(i, 3)  + "\n\n" +jtFaultTree.getValueAt(i, 4)  + "\n F = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 5) + "")+ " /h \n P = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 6)+ "") ;
                
                if ((jtFaultTree.getValueAt(i, 2) + "").equals("Basic")) { 
                    res += "    \n T = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 7)+"")+"  h";
                }
                
                break;
            }

        }

        return res;
    }
    
    public String[] findById2(String node) {
        String [] res = new String []{"Node",""};

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            //System.out.println("cscd"+jtFaultTree.getValueAt(i, 0));
            if ((jtFaultTree.getValueAt(i, 0) + "").equals(node)) {

                //res = /*jtFaultTree.getValueAt(i, 3) + "- " +*/ jtFaultTree.getValueAt(i, 4)  + "\n F = " + NumberConversion.scientificNotation(jtFaultTree.getValueAt(i, 5) + "", 2) + "\n P = " + NumberConversion.scientificNotation(jtFaultTree.getValueAt(i, 6) + "", 2);
                
                if ((jtFaultTree.getValueAt(i, 2) + "").equals("Basic")) { 
                    //res[1] = jtFaultTree.getValueAt(i, 3)  + "\n\n\n\n\n" +jtFaultTree.getValueAt(i, 4)  + "\n F = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 5) + "")+ " /h \n P = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 6)+ "")+"    \n T = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 7)+"")+"  h";
                    res[1] = InnnerContentAlignBasic(jtFaultTree.getValueAt(i, 3)+"", jtFaultTree.getValueAt(i, 4)+"",jtFaultTree.getValueAt(i, 8)+"", jtFaultTree.getValueAt(i, 5) + "", jtFaultTree.getValueAt(i, 6) + "", jtFaultTree.getValueAt(i, 7) + "");
                    res[0]="Basic";
                }else{
                   //res[1] = jtFaultTree.getValueAt(i, 3)  + "\n\n" +jtFaultTree.getValueAt(i, 4)  + "\n F = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 5) + "")+ " /h \n P = " + sciNotation.sciFormatString(jtFaultTree.getValueAt(i, 6)+ "") ;
                    res[1] =  InnnerContentAlignEvent(jtFaultTree.getValueAt(i, 3)+"", jtFaultTree.getValueAt(i, 4)+"", jtFaultTree.getValueAt(i, 5) + "", jtFaultTree.getValueAt(i, 6) + "");
                }
                
                break;
            }

        }

        return res;
    }

    public String findNameById(String node) {
        String res = "";

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            //System.out.println("cscd"+jtFaultTree.getValueAt(i, 0));
            if ((jtFaultTree.getValueAt(i, 0) + "").equals(node)) {

                res = jtFaultTree.getValueAt(i, 3) + "";
                break;
            }

        }

        return res;
    }

    public String findByName(String node) {
        String res = "";

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            if (jtFaultTree.getValueAt(i, 3).equals(node)) {

                res = node + "\n T:-" + jtFaultTree.getValueAt(i, 7) + "\n F:-" + jtFaultTree.getValueAt(i, 6) + "\n P:-" + jtFaultTree.getValueAt(i, 7);
                break;
            }

        }

        return res;
    }

    public Object[] faultData() {
        ArrayList<Item> list = new ArrayList<>();

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {
            if (jtFaultTree.getValueAt(i, 2).equals("Basic")) {
                list.add(new Item(Integer.parseInt(jtFaultTree.getValueAt(i, 0) + ""), jtFaultTree.getValueAt(i, 3).toString()));
                //list.add(jtFaultTree.getValueAt(i, 3).toString());
            }

        }

        return list.toArray();
    }

    public Object[] faultDataCurrentSel(String[] selected) {
        ArrayList<Item> list = new ArrayList<>();

        for (String string : selected) {
            list.add(new Item(Integer.parseInt(string), findNameById(string)));
        }

        return list.toArray();
    }

    public Object[][] loadData() {
      //  List<Faultdata> faultTreeData = faultdataDao.getAllFaultdataByFaulttree(faultId);
        List<Faultdata> faultTreeData = faultdataDao.getAllFaultdataBySpfHdr(spfhdr.getSpfhdrid());
        Object[][] tableData = new Object[faultTreeData.size()][];
        Object singleRow[];
        int setCount = 0;
        
        for (Faultdata faultdata : faultTreeData) {
            singleRow = new Object[15];
            singleRow[0] = faultdata.getFaultdataId();
            singleRow[1] = setCount+1;
            singleRow[2] = faultdata.getNode();
            singleRow[3] = faultdata.getName();
            singleRow[4] = faultdata.getDescription();
            singleRow[5] = faultdata.getFailurerate();
            singleRow[6] = faultdata.getProbability();
            singleRow[7] = faultdata.getTime();
            singleRow[8] = faultDataCrud.changeToFullTypeName(faultdata.getCategory()); //faultdata.getCategory();
            //singleRow[9] = faultdata.getMethods();  
            //singleRow[10] = faultdata.getChild();
            singleRow[9] = faultdata.getChildGate();
            singleRow[10] = faultdata.getFdreferences();
            //singleRow[13] = faultdata.getParent() == null ? " - " : faultdata.getParent().getFaultdataId();
            //singleRow[11] = faultdata.getCalcComp().getCalcId();
            tableData[setCount] = singleRow;
            setCount++;
        }

        setTableData(tableData);
        //parent.refreshGraph(faultId);
        
        return tableData;
    }

    public void tableSettings() {

        jtFaultTree.getColumnModel().getColumn(4).setMinWidth(500);
        jtFaultTree.getColumnModel().getColumn(4).setMaxWidth(500);
        jtFaultTree.getColumnModel().getColumn(4).setPreferredWidth(500);

        jtFaultTree.getColumnModel().getColumn(5).setMinWidth(100);
        jtFaultTree.getColumnModel().getColumn(5).setMaxWidth(100);
        jtFaultTree.getColumnModel().getColumn(5).setPreferredWidth(100);

        jtFaultTree.getColumnModel().getColumn(6).setMinWidth(100);
        jtFaultTree.getColumnModel().getColumn(6).setMaxWidth(100);
        jtFaultTree.getColumnModel().getColumn(6).setPreferredWidth(100);

    }

    public Object[][] importAccess() {
        Object[][] data = null;
        String path = FileSelector.getPathSelectedFile(new String[]{"mdb"});
        String tableName = JOptionPane.showInputDialog(InternalFrameDemo.mainFrame, "What's your database table name?", "Table1");

        if (!(tableName == null || tableName.equals(""))) {

            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            
            // System.out.println("path "+path);
            data = ar.readsData(path, tableName);
            tableSettings();
            saveImport(data);

            loadData();
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }

        return data;
    }

    public void setTableData(Object[][] data) {

        model1 = new DefaultTableModel(data, getHeadings()
        );

        jtFaultTree.setModel(model1);

    }

    public String [] getHeadings() {

        return new String []{"Id", "No", "Node", "Name", "Description", "FailureRate (/h)", "Probability",
            "PTI-RepairTime (h)", "Category", /*"Methods", "Child",*/ "Gate", "References",/*"ParentId","ComId"*/}; //"RepairTime / PTI"
    }

    public void saveImport(Object[][] data) {
        
        //faultdataDao.removeByFaultTreeId(faultId);
        faultdataDao.removeBySpfHdr(spfhdr.getSpfhdrid());
        parent.refreshGraph(spfhdr);
        Faultdata fd;
        for (Object[] objects : data) {
            
            
           fd = new Faultdata();
            //fd.setFaultdataId(1);
            fd.setNode(objects[0] + "");
            fd.setName(objects[1] + "");
            fd.setDescription(objects[2] + "");
            fd.setFailurerate(NumberConversion.NVLDouble(objects[3] + "", 0));
            fd.setProbability(NumberConversion.NVLDouble(objects[4] + "", 0));
            fd.setTime(NumberConversion.NVLDouble(objects[5] + "", 0));
            fd.setCategory(objects[6] + "");
            fd.setMethods(objects[7] + "");
            fd.setFdreferences(objects[8] + "");
            fd.setFaultHeader(spfhdr);
            fd.setCalcComp(calcfileDao.findById(0));
            faultdataDao.Save(fd);
          
            
        }

    }

    public void saveImportExcel(Object[][] data) {
        Faultdata fd;
        //faultdataDao.removeByFaultTreeId(faultId);
        faultdataDao.removeBySpfHdr(spfhdr.getSpfhdrid());
        for (Object[] objects : data) {
            fd = new Faultdata();

            /*try {
                fd.setSno(Integer.parseInt(objects[1] + ""));
            } catch (NumberFormatException e) {
                fd.setSno((int) Double.parseDouble(objects[1] + ""));
            }*/
            
            if (objects != null) {
                fd.setNode(objects[1] + "");
                fd.setName(objects[2] + "");
                fd.setDescription(objects[3] + "");
                fd.setFailurerate(NumberConversion.NVLDouble(objects[4] + "", 0));
                fd.setProbability(NumberConversion.NVLDouble(objects[5] + "", 0));
                fd.setTime(NumberConversion.NVLDouble(objects[6] + "", 0));
                fd.setCategory(objects[7] + "");
                fd.setMethods(objects[8] + "");
                fd.setChild(objects[9] + "");
                fd.setChildGate(objects[10] + "");
                fd.setFdreferences(objects[11] + "");

                fd.setFaultHeader(spfhdr);
                fd.setCalcComp(calcfileDao.findById(0));
                faultdataDao.Save(fd);
            }

            
           //System.out.println("objects - "+objects.length);
        }

    }

    public void exportAccess() {

        String path = FileSelector.getPathSelected();

        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File(path + "/Fault_Tree.mdb"));
            Table newTable = new TableBuilder("Table1")       
                    .addColumn(new ColumnBuilder("Type").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Name").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("DataRef").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Description").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Frequency").setSQLType(Types.DOUBLE))
                    .addColumn(new ColumnBuilder("Probability").setSQLType(Types.DOUBLE))
                    .addColumn(new ColumnBuilder("Repair Time / PTI").setSQLType(Types.DOUBLE))
                    .addColumn(new ColumnBuilder("Data Type").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Distribution").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Spread").setSQLType(Types.NUMERIC))
                    .addColumn(new ColumnBuilder("Page").setSQLType(Types.VARCHAR))
                    .toTable(db);
            
            


            for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

                newTable.addRow(jtFaultTree.getValueAt(i, 2).equals("Event")? "Gate":jtFaultTree.getValueAt(i, 2), jtFaultTree.getValueAt(i, 3),
                        jtFaultTree.getValueAt(i, 10), jtFaultTree.getValueAt(i, 4), jtFaultTree.getValueAt(i, 5),
                        jtFaultTree.getValueAt(i, 6), jtFaultTree.getValueAt(i, 7), jtFaultTree.getValueAt(i, 2).equals("Event")? "-":faultDataCrud.changeToShortTypeName(jtFaultTree.getValueAt(i, 8)+"") ,
                        jtFaultTree.getValueAt(i, 9), 0.0, jtFaultTree.getValueAt(i, 8).equals("Head Event")? "T":"");

                //System.out.println("jtFaultTree.getValueAt(i, 0) - "+jtFaultTree.getValueAt( i,0));
            }

            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (IOException ex) {
            Logger.getLogger(FaultTreeData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FaultTreeData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        JOptionPane.showMessageDialog(null, "Export successfully " + path + "/Fault_Tree.mdb");

    }

    public Faultdata getFaultdataByRowId(int rowId) {

        //Faultdata f = new Faultdata();
        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        
        Faultdata f  = faultdataDao.findById(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        //f.setSno(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 1) + "", 0));
        f.setNode(jtFaultTree.getValueAt(rowId, 2) + "");
        f.setName(jtFaultTree.getValueAt(rowId, 3) + "");
        //f.setDataref(jtFaultTree.getValueAt(rowId, 4) + "");
        f.setDescription(jtFaultTree.getValueAt(rowId, 4) + "");
        f.setFailurerate(NumberConversion.NVLDouble(jtFaultTree.getValueAt(rowId, 5) + "", 0));
        f.setProbability(NumberConversion.NVLDouble(jtFaultTree.getValueAt(rowId, 6) + "", 0));
        f.setTime(NumberConversion.NVLDouble(jtFaultTree.getValueAt(rowId, 7) + "", 0));
        f.setCategory(faultDataCrud.changeToShortTypeName(jtFaultTree.getValueAt(rowId, 8) + ""));
        //f.setMethods(jtFaultTree.getValueAt(rowId, 9) + "");
        //f.setChild(jtFaultTree.getValueAt(rowId, 10) == null || jtFaultTree.getValueAt(rowId, 10).equals("null") ? "" : jtFaultTree.getValueAt(rowId, 10) + "");
        f.setChildGate(jtFaultTree.getValueAt(rowId, 9) == null || jtFaultTree.getValueAt(rowId, 9).equals("null") ? "" : jtFaultTree.getValueAt(rowId, 9) + "");
        f.setFdreferences(jtFaultTree.getValueAt(rowId, 10)+"");
        //f.setParent(faultdataDao.findById(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 13)+"", 0)));
        //f.setCalcComp(calcfileDao.findById(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 11)+"", 0)));
        f.setFaultHeader(spfhdr);
        

        return f;
    }

    public int findRowByFaultId(String faultId) {
        int res = -1;

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            if (faultId.contentEquals(jtFaultTree.getValueAt(i, 0) + "")) {
                res = i;
                break;
            }
        }

        return res;
    }

    public void UpdateFaultTreeGraph(String nodeName, int logicGate, String[] child, boolean head) {

        Faultdata f = faultdataDao.findByName(nodeName, spfhdr.getSpfhdrid());
        String res = "";

        for (String string : child) {
            //res = res+string+"#";
            res = res + faultdataDao.findByName(string, spfhdr.getSpfhdrid()).getFaultdataId() + "#";
        }

        f.setChild(res);
        f.setChildGate(logicGate == 1 ? "OR" : "AND");

        /*if (head) {
            f.setPage(f.getPage() + "H");
        }*/

        faultdataDao.Update(f);

    }

    public void saveAllTableData(boolean massage) {

        /*-----------------------------------------------------*/
        JFrame parent = InternalFrameDemo.mainFrame;

        if (jtFaultTree.isEditing()) {
            // Get the current editor
            TableCellEditor editor = jtFaultTree.getCellEditor();
            if (editor != null) {
                // Try and stop the cell editing process
                if (!editor.stopCellEditing()) {
                    // Cancel the editing if can't be stopped...
                    // You could handle an error state here instead...
                    editor.cancelCellEditing();
                }
            }
        }

        if (jtFaultTree.getRowCount() > 0) {
            parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            if (jtFaultTree.getRowCount() > 0) {

                for (int i = 0; i < jtFaultTree.getRowCount(); i++) {
                    faultdataDao.Update(getFaultdataByRowId(i));
                }
            }

            parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            if (massage) {
                JOptionPane.showMessageDialog(null, "Your data save successfully");
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "No data to save");

        }

    }

    public void updateTableCalclatedProbablityById(double fre, double pro, int faultId) {

        int i = findRowByFaultId(faultId + "");

        jtFaultTree.setValueAt(NumberConversion.NVLDouble(fre+"", 0), i,5 );
        jtFaultTree.setValueAt(NumberConversion.NVLDouble(pro+"", 0), i,6);
    }

    public  List<Integer> getAddedList(){
     List<Integer> addedList = new ArrayList<Integer>();
     int rowct =jtFaultTree.getRowCount();
     int colct =jtFaultTree.getColumnCount();
     
        for (int i = 0; i < rowct ; i++) {
            if((jtFaultTree.getValueAt(i, 2)+"").contentEquals("Basic")){
                addedList.add(NumberConversion.NVL(jtFaultTree.getValueAt(i, colct-1)+"", 0));
                //System.out.println("AddedList - "+jtFmea.getValueAt(i, colct-1));
            }
        }
    
      return addedList;
    }
    
    
    public class footerInfo extends JPanel {

        public footerInfo() {

            setLayout(new FlowLayout(FlowLayout.LEFT));

            /*PanelButton calculate = new PanelButton("Calculate Probability");
            calculate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    double prob = 0;

                    // Calculate NodeProbablity
                    try {
                        InternalFrameDemo.mainFrame.setLoader();
                        FaultTreeEquations.calulateNodeProbability(jtFaultTree);

                        FaultTreeEquations.findCutSetNode(getFaultdataByRowId(0), FaultTreeData.this);

                        saveAllTableData(false);
                        //parent.setComponentAt(2, new FaultTreeGraph2(parent,faultId));
                        parent.refreshGraph(spfhdr);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        InternalFrameDemo.mainFrame.removeLoder();

                    }

                    /* for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

                        if (jtFaultTree.getValueAt(i, 2).equals("Gate") && i == 0) {
                            //System.out.println(jtFaultTree.getValueAt(i, 3)+" - "+jtFaultTree.getValueAt(i, 7));

                            //prob = caclulateProbability(i);
                            prob = FaultTreeEquations.calPerentProData(getFaultdataByRowId(i), FaultTreeData.this);
                            //jtFaultTree.setValueAt(prob, i, 7);

                            //jtFaultTree.setValueAt(caclulateFrequency(i), i, 6);
                            /*if (i == 0) {
                                Faulttree ft = faulttreeDao.findById(faultId);
                                ft.setFaultResult(Double.parseDouble(jtFaultTree.getValueAt(i, 7) + ""));
                                faulttreeDao.Update(ft);

                            }*/
 /*      }
                    }*/
              /*  }
            });*/

            PanelButton save = new PanelButton("Save the Data");
            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    saveAllTableData(true);

                }

            });

           /* PanelButton add = new PanelButton("Add Data");

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   Object[] options1 = {"Add", "Cancel"}; 
                   Faultdata parentdata ;
                   int childCreated =-10;
                   FTABasicPopup fTABasicPopup = null;
                   FTAGatePopup fTAGatePopup;
                    
                    FaultDataPopup faultDataPopup = new FaultDataPopup(spfhdr,faultDataCrud.getAllNodes(jtFaultTree),getAddedList(),true);
                   
                   
                     int result = JOptionPane.showOptionDialog(null, faultDataPopup, "Enter Details",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options1, null);

                        if (result == JOptionPane.YES_OPTION ) {
                            
                           parentdata = faultdataDao.findById(((Item)faultDataPopup.getParentNode()).getId());
                           
                           
                             if (parentdata != null) {

                                        if (faultDataPopup.getNodeType().contentEquals("Basic")) {

                                        fTABasicPopup = (FTABasicPopup)faultDataPopup.getAddPopup();

                                        if ( fTABasicPopup.getFunctionalComponents() != null) {
                                             childCreated = faultDataCrud.insertBasicNode(spfhdr, parentdata,fTABasicPopup.getFunctionalComponents(), fTABasicPopup.getComponentName(), fTABasicPopup.getDescription(), fTABasicPopup.getReference(),
                                             faultDataCrud.changeToShortTypeName(fTABasicPopup.getDataType()), fTABasicPopup.getFrequency(),
                                             fTABasicPopup.getProbability(), fTABasicPopup.getRepairTime());
                                        } else {

                                            JOptionPane.showMessageDialog(null, "Please select a component");
                                        }



                                    } else if (faultDataPopup.getNodeType().contentEquals("Event")) {
                                        fTAGatePopup = (FTAGatePopup)faultDataPopup.getAddPopup();
                                        childCreated = faultDataCrud.insertAEvent(spfhdr, parentdata, fTAGatePopup.getComponentName(), fTAGatePopup.getDescription(),fTAGatePopup.getGate(), fTAGatePopup.getReference(),fTAGatePopup.getPTIAdjestment());

                                    }


                                        if (parentdata.getNode().contentEquals("Event")) {

                                            faultDataCrud.insertANode(parentdata.getFaultdataId(), childCreated);
                                        } else if (fTABasicPopup.getFunctionalComponents() != null) {

                                            faultDataCrud.addChildToBasicNoade(parentdata.getFaultdataId(), childCreated, "OR");
                                        }


                                     parent.refreshData(spfhdr);
                                     parent.refreshGraph(spfhdr);
                            
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Please select Parent Node");
                            }

                        }
                    
                    
                    
                    /*InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    Faultdata f = new Faultdata();
                    f.setFaulttree(faulttreeDao.findById(faultId));

                    faultdataDao.Save(f);
                    loadData();
                    InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    JOptionPane.showMessageDialog(null, "Please add your details now " + faultId);*/

                /*}

            });*/
            
            
           /* PanelButton editdata = new PanelButton("Edit");
            editdata.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    int modelRow = jtFaultTree.getSelectedRow();
                    FTABasicPopup fTABasicPopup =null;
                    FaultDataPopup faultDataPopup= null;
                    FTAGatePopup fTAGatePopup =null;
                    Faultdata fdata;
                    int parentrow;
                    Object[] options1 = {"Save", "Cancel"};
                    
                    if (modelRow >= 0) {
                        
                      parentrow = faultDataCrud.findParentByChild(jtFaultTree, modelRow);
                      
                      faultDataPopup = new FaultDataPopup(spfhdr,faultDataCrud.getAllNodes(jtFaultTree),getAddedList(),false);
                      fdata = faultdataDao.findById(Integer.parseInt(jtFaultTree.getValueAt(modelRow, 0)+""));
                   
                      if(parentrow >=0){
                      faultDataPopup.setParentNode(new Item(Integer.parseInt(jtFaultTree.getValueAt(parentrow, 0)+""),jtFaultTree.getValueAt(parentrow, 3)+""));
                      }
                      faultDataPopup.setNodeType(fdata.getNode());
                      
                        if (fdata.getNode().contentEquals("Event")) {
                           fTAGatePopup =  (FTAGatePopup)faultDataPopup.getAddPopup();
                           fTAGatePopup.setComponentName(fdata.getName());
                           fTAGatePopup.setDescription(fdata.getDescription());
                           fTAGatePopup.setGate(fdata.getChildGate());
                           fTAGatePopup.setPTIAdjestment(fdata.getComments());
                           fTAGatePopup.setReference(fdata.getFdreferences());

                        } else {
                            
                           fTABasicPopup = (FTABasicPopup)faultDataPopup.getAddPopup();
                           fTABasicPopup.setFunctionalComponents(fdata.getCalcComp());
                           //fTABasicPopup.setComponentName(fdata.getName());
                           //fTABasicPopup.setDescription(fdata.getDescription());
                           fTABasicPopup.setReference(fdata.getFdreferences());
                           fTABasicPopup.setDataType(faultDataCrud.changeToFullTypeName(fdata.getCategory()));
                           //fTABasicPopup.setFrequency(fdata.getFailurerate());
                           //fTABasicPopup.setProbability(fdata.getProbability());
                           fTABasicPopup.setRepairTime(fdata.getTime());

                        }       
                      
                      
                      int result = JOptionPane.showOptionDialog(null, faultDataPopup, "Enter Details",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options1, null);
                      
     
                      

                        if (result == JOptionPane.YES_OPTION) {
                          
                          
                          if (fdata.getNode().contentEquals("Event")) {
                            fdata.setName(fTAGatePopup.getComponentName());
                            fdata.setDescription(fTAGatePopup.getDescription());
                            fdata.setChildGate(fTAGatePopup.getGate());
                            fdata.setComments(fTAGatePopup.getPTIAdjestment());
                            fdata.setFdreferences(fTAGatePopup.getReference());
                            faultdataDao.Update(fdata);
                              
                          }else if (fdata.getNode().contentEquals("Basic")) {
                              
                            fdata.setCalcComp(fTABasicPopup.getFunctionalComponents());
                            fdata.setName(fTABasicPopup.getComponentName());
                            fdata.setDescription(fTABasicPopup.getDescription());
                            fdata.setFdreferences(fTABasicPopup.getReference());
                            fdata.setCategory(faultDataCrud.changeToShortTypeName(fTABasicPopup.getDataType()));
                            fdata.setFailurerate(NumberConversion.NVLDouble(fTABasicPopup.getFrequency() + "", 0));
                            fdata.setProbability(NumberConversion.NVLDouble(fTABasicPopup.getProbability() + "", 0));
                            fdata.setTime(NumberConversion.NVLDouble(fTABasicPopup.getRepairTime() + "", 0));
 
                            
                              
                          }
        
                           faultdataDao.Update(fdata);
                           parent.refreshData(spfhdr);
                           parent.refreshGraph(spfhdr);
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select row to delete");
                    }

                }

            });*/

           /* PanelButton delete = new PanelButton("Delete Row");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    int modelRow = jtFaultTree.getSelectedRow();
 
                    
                    if (modelRow >= 0) {
                        
                     faultDataCrud.deleteNode(jtFaultTree, modelRow);   
                     parent.refreshData(spfhdr);
                     parent.refreshGraph(spfhdr);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select row to delete");
                    }

                }

            });*/
            
            PanelButton refresh  = new PanelButton("Refresh ");
            refresh .addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                      parent.refreshData(spfhdr);
                     // parent.refreshGraph(spfhdr);

                }
            });

            PanelButton importTem = new PanelButton("Download Report");
            importTem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    InternalFrameDemo.mainFrame.getExportController().setExport( InternalFrameDemo.contentPanel);
                     //downloadTemplate();
                }
            });

            //add(comboBox);
            //add(add);
            //add(editdata);
            //add(save);
            //add(calculate);
            //add(delete);
            add(refresh);
            add(importTem);

        }

        
        public void downloadTemplate(){
            
                    Object[][] datatypes = new Object[1][];
                    datatypes[0] =  getHeadings();
                    datatypes[0] = ArrayUtils.remove(datatypes[0], 0);
                    //datatypes[0] = ArrayUtils.remove(datatypes[0], 0);
                            
                            /*new Object[]{"Id", "SNo.", "Type", "Name", "DataRef", "Description", "Frequency", "Probability",
                        "Repair Time / PTI", "Data Type", "Distribution", "Spread", "Page"};*/

                    ExcelExporter export = new ExcelExporter();
                    export.WriteExcel("FTA_Data_Template", datatypes);
        }


        public double caclulateProbability(int row) {

            String[] res = jtFaultTree.getValueAt(row, 13).toString().split("\\#");
            int childId = 0;
            double pro = 0.0;

            for (String child : res) {
                childId = findRowByName(child);
                //pro = pro + findProById(childId);
                //System.out.println(childId+" - "+findProById(childId));
                pro = FaulttreeEquations.getProbability(pro, findProById(childId), jtFaultTree.getValueAt(row, 14).equals("AND") ? FaulttreeEquations.AND : FaulttreeEquations.OR);
            }

            return pro;
        }

        public double caclulateFrequency(int row) {

            String[] res = jtFaultTree.getValueAt(row, 13).toString().split("\\#");
            int childId = 0;
            int prechild = 0;
            double fre = 1.0;

            for (String child : res) {
                childId = findRowByName(child);
                //pro = pro + findProById(childId);
                //System.out.println(childId+" - "+findProById(childId));
                fre = FaulttreeEquations.getFrequency(fre, prechild == 0 ? 1.00 : findProById(prechild), jtFaultTree.getValueAt(row, 14).equals("AND") ? FaulttreeEquations.AND : FaulttreeEquations.OR);
                prechild = childId;
            }

            return fre;
        }

        public int findRowByName(String name) {
            int res = -1;

            for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

                if (jtFaultTree.getValueAt(i, 3).equals(name)) {
                    res = i;
                    break;
                }
            }

            return res;
        }

        public double findProById(int row) {
            double res = 0.0;

            // System.out.println(jtFaultTree.getValueAt(row, 3)+" - "+jtFaultTree.getValueAt(row, 2));
            if (jtFaultTree.getValueAt(row, 2).equals("Basic")) {
                res = Double.parseDouble(jtFaultTree.getValueAt(row, 7) + "");

            } else {

                for (String child : getChilds(row)) {
                    res = res + findProById(findRowByName(child));
                }

            }

            return res;
        }

        public double findFreById(int row) {
            double res = 0.0;

            System.out.println(jtFaultTree.getValueAt(row, 3) + " - " + jtFaultTree.getValueAt(row, 2));

            if (jtFaultTree.getValueAt(row, 2).equals("Basic")) {
                res = Double.parseDouble(jtFaultTree.getValueAt(row, 6) + "");

            } else {

                for (String child : getChilds(row)) {
                    res = res + findProById(findRowByName(child));
                }

            }

            return res;
        }

        public String[] getChilds(int row) {

            return jtFaultTree.getValueAt(row, 13).toString().split("\\#");
        }

        
        
       

    }
    
    
    public String InnnerContentAlignBasic(String id,String des,String type,String fre,String pro, String time){
        String res="\n"+id+"\n";
        String [] desSplit = DesSplitter(des, 18); //sciNotation.sciFormatString
        
        for (int i = 0; i < 4; i++) {
            if (i < desSplit.length ) {
                res = res+desSplit[i]+"\n";
            } else {
                res = res+"\n";
            }
            
        }

         res = res+"\n"+type+"\nF = "+sciNotation.sciFormatString(fre)+" /h \n P = "+sciNotation.sciFormatString(pro)+"    \n T = " +sciNotation.sciFormatString(time)+"  h";

        return res;
    }
    
    public String InnnerContentAlignEvent(String id,String des,String fre,String pro){
        String res=id+"\n\n";
        String [] desSplit = DesSplitter(des, 19); //sciNotation.sciFormatString
        
        for (String string : desSplit) {
            res = res+string+"\n";
        }
        
        res = res+"F = "+sciNotation.sciFormatString(fre)+" /h \n P = "+sciNotation.sciFormatString(pro);

        return res;
    }
        
    public String[] DesSplitter(String des,int splitLength){
        
            String[] res;
        
            if (des.length() >= splitLength) {
                res = des.split("(?<=\\G.{" + splitLength + "})");
            } else {
                res = new String[]{des};
            }
        
            return res;
    }

}
