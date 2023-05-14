/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FaultdataDao;
import com.topfield.dao.FaulttreeDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Spfhdr;
import com.topfield.utilities.NumberConversion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */
public class FaultDataCrud {

    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    
    public void addIntialFaultData(Spfhdr spfhdr){
        
        String child = "";
        Calcfile Calcfile = calcfileDao.findById(0);
        Faultdata rootNode = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        //rootNode.setSno(0);
        rootNode.setNode("Event");  //Gate
        rootNode.setName(spfhdr.getSpfid().getFunId());
        rootNode.setDescription(spfhdr.getSpfid().getCalcFunction());
        rootNode.setFailurerate(NumberConversion.NVLDouble("", 0));
        rootNode.setProbability(NumberConversion.NVLDouble("", 0));
        rootNode.setTime(NumberConversion.NVLDouble("", 0));
        rootNode.setCategory("A");
        rootNode.setChild("");
        rootNode.setChildGate("OR");
        rootNode.setFdreferences("");
        rootNode.setParent(null);
        rootNode.setFaultHeader(spfhdr);
        rootNode.setCalcComp(Calcfile);

 
        /*Faultdata f = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        f.setNode("Basic");
        f.setName("Basic 1");
        f.setDescription("");
        f.setFailurerate(NumberConversion.NVLDouble("", 0));
        f.setProbability(NumberConversion.NVLDouble("", 0));
        f.setTime(NumberConversion.NVLDouble("", 0));
        f.setCategory("U");
        f.setChild("");
        f.setChildGate("");
        f.setFaulttree(faulttreeDao.findById(faultId));
        
        Faultdata f2 = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        f2.setNode("Basic");
        f2.setName(" Basic 1");
        f2.setDescription("");
        f2.setFailurerate(NumberConversion.NVLDouble("", 0));
        f2.setProbability(NumberConversion.NVLDouble("", 0));
        f2.setTime(NumberConversion.NVLDouble("", 0));
        f.setCategory("Unrevealed");
        f2.setChild("");
        f2.setChildGate("");
        f2.setFaulttree(spfhdr);
        
        //child = child+faultdataDao.Save(f)+"#";
       // child = child+faultdataDao.Save(f2)+"#";*/
        
        //rootNode.setChild(child);
    
       faultdataDao.Save(rootNode);
    
    }
    
    
    
    public int insertBasicNode(Spfhdr spfhdr, Faultdata parent, Calcfile calc, String name, String des, String ref, String dataType, double fre, double pro, double repair) {

        Faultdata f = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        //f.setSno(0);
        f.setNode("Basic");
        f.setName(name);
        f.setDescription(des);
        f.setFailurerate(fre);  //NumberConversion.NVLDouble("", 0)
        f.setProbability(pro);
        f.setTime(repair);
        f.setCategory(dataType);
        f.setMethods("Fixed");
        f.setChild("");
        f.setChildGate("");
        f.setFdreferences(ref);
        f.setParent(parent);
        f.setFaultHeader(spfhdr);
        f.setCalcComp(calc);

        return faultdataDao.Save(f);

    }
    
    public int insertAEvent(Spfhdr spfhdr, Faultdata parent, String name, String des, String gate, String ref,String ptiAdj) {

        Faultdata f = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        //f.setSno(0);
        f.setNode("Event");
        f.setName(name);
        f.setDescription(des);
        f.setFailurerate(0.0);  //NumberConversion.NVLDouble("", 0)
        f.setProbability(0.0);
        f.setTime(0.0);
        f.setCategory("X");
        f.setMethods("Fixed");
        f.setChild("");
        f.setChildGate(gate);
        f.setComments(ptiAdj);
        f.setFdreferences(ref);
        f.setParent(parent);
        f.setFaultHeader(spfhdr);
        f.setCalcComp(calcfileDao.findById(0));

        return faultdataDao.Save(f);

    }

    public void insertANode(int nodeId, int child) { 

        Faultdata parent = faultdataDao.findById(nodeId);
        String childs = parent.getChild();

        childs = childs + child + "#";
        parent.setChild(childs);
        faultdataDao.Update(parent);

        System.out.println("parent - " + parent.getFaultdataId() + " child - " + parent.getChild());

    }

    public void deleteNode(JTable jtFaultTree, int modelRow) {

        Faultdata parentNode;
        int parentRowId;
        String nodeId = jtFaultTree.getValueAt(modelRow, 0) + "";
        String childs = "";

        
        if ((jtFaultTree.getValueAt(modelRow, 8)+"").contentEquals("Head Event")) {
            
            JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame,"Topevent can't be deleted");  
            
        } else {
            
            if (jtFaultTree.getValueAt(modelRow, 2).equals("Basic") || jtFaultTree.getValueAt(modelRow, 2).equals("Event")) {
            parentRowId = findParentByChild(jtFaultTree, modelRow);
            
                if (parentRowId != -1) {

                    parentNode = faultdataDao.findById(Integer.parseInt(jtFaultTree.getValueAt(parentRowId, 0) + ""));

                    childs = deleteChild(nodeId, parentNode.getChild());

                    if (childs.contentEquals("") && !(parentNode.getCategory().contentEquals("A"))) {
                        changeGateToBasic(parentNode.getFaultdataId());
                        System.out.println("childs final" + " * " + childs);
                    } else {
                        parentNode.setChild(childs);
                    }

                    jtFaultTree.setValueAt(parentNode.getChild(), parentRowId, 10);
                    faultdataDao.Update(parentNode);

                    }
                    
                }

             faultdataDao.remove(Integer.parseInt(nodeId));
            ((DefaultTableModel) jtFaultTree.getModel()).removeRow(modelRow);
            // System.out.println("getValueAt(modelRow,0)"+jtFmea.getValueAt(modelRow-1,0));
            
            
        }
        
        

    }

    public int findParentByChild(JTable jtFaultTree, int modelRow) {
        int res = -1;
        String childId = jtFaultTree.getValueAt(modelRow, 0) + "";

        System.out.println("childId - " + childId);

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            //System.out.println("(jtFaultTree.getValueAt(i, 13) "+(jtFaultTree.getValueAt(i, 13)+""));
            if ((jtFaultTree.getValueAt(i, 10) + "").contains(childId)) {
                res = i;
                System.out.println("res - " + res);
                break;
            }

        }

        return res;
    }

    public String deleteChild(String childId, String allChilds) {

        return allChilds.replace(childId + "#", "");
    }

    public void addChildToBasicNoade(int nodeId, int childId, String childGate) {

        Faultdata parentNode = faultdataDao.findById(nodeId);
        parentNode.setNode("Event");
        parentNode.setFailurerate(0.0);
        parentNode.setProbability(0.0);
        parentNode.setTime(0.0);
        parentNode.setCategory("X");
        parentNode.setChild(childId + "#");
        parentNode.setChildGate(childGate);
        faultdataDao.Update(parentNode);

    }

    public void changeGateToBasic(int nodeId) {

        Faultdata basicNode = faultdataDao.findById(nodeId);
        basicNode.setNode("Basic");
        basicNode.setFailurerate(0.0);
        basicNode.setProbability(0.0);
        basicNode.setTime(0.0);
        basicNode.setCategory("P");
        basicNode.setChild("");
        basicNode.setChildGate("");
        faultdataDao.Update(basicNode);

    }

    public List<Item> getAllNodes(JTable jtFaultTree) {
        List<Item> parentNodes = new ArrayList<Item>();

        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            parentNodes.add(new Item(NumberConversion.NVL(jtFaultTree.getValueAt(i, 0) + "", 0), jtFaultTree.getValueAt(i, 3) + ""));
        }

        return parentNodes;
    }

    public String changeToFullTypeName(String shortTypeName) {
        String res = "*";

        if (shortTypeName.contentEquals("P")) {
            res = "Fixed Probability";
        } else if (shortTypeName.contentEquals("F")) {
            res = "Non Repairable Failure";
        } else if (shortTypeName.contentEquals("R")) {
            res = "Revealed Failure";
        } else if (shortTypeName.contentEquals("U")) {
            res = "Unrevealed Failure";
        }else if (shortTypeName.contentEquals("A")) {
            res = "Head Event";
        }else if (shortTypeName.contentEquals("X")) {
            res = "Event";
        }

        return res;
    }

    public String changeToShortTypeName(String fullTypeName) {
        String res = "*";

        if (fullTypeName.contentEquals("Fixed Probability")) {
            res = "P";
        } else if (fullTypeName.contentEquals("Non Repairable Failure")) {
            res = "F";
        } else if (fullTypeName.contentEquals("Revealed Failure")) {
            res = "R";
        } else if (fullTypeName.contentEquals("Unrevealed Failure")) {
            res = "U";
        }else if (fullTypeName.contentEquals("Head Event")) {
            res = "A";
        }else if (fullTypeName.contentEquals("Event")) {
            res = "X";
        }

        return res;
    }

}
