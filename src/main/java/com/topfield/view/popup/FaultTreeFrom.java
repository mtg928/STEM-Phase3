/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.dao.FaultdataDao;
import com.topfield.user.UserInfo;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Faulttree;
import com.topfield.utilities.NumberConversion;
import com.topfield.calculators.eventtree.EventTreeCalculator;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Murali
 */
public class FaultTreeFrom extends JDialog {

    private JComboBox<Item> combo;
    private JTextField field1;
    private JComboBox<String> combo2;
    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    
    private Faulttree faultTree;

    public FaultTreeFrom(Item[] comp, Faulttree faultTree,FaultTreeCalculator parent) {

        combo = new JComboBox<>(comp);
        field1 = new JTextField();
        combo2 = new JComboBox<>(new String[]{"RAM","Safety"});

        if (faultTree != null) {

            setFaultName(faultTree.getFaultName());
            setFaultType(faultTree.getFaultType());
            setFaultComponent(new Item(faultTree.getFaultComponent().getMpgId(), faultTree.getFaultComponent().getMpgDescription()));
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Fault Name"));
        panel.add(field1);
        panel.add(new JLabel("Fault Type"));
        panel.add(combo2);
        panel.add(new JLabel("Fault Component"));
        panel.add(combo);

        this.faultTree = faultTree;

        int result = JOptionPane.showConfirmDialog(null, panel, "Fault Tree Details",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            Faulttree ft =getFaulttreeFinal();
            
            if (faultTree != null) {
                faulttreeDao.Update(ft);
            } else {
                faulttreeDao.Save(ft);
                addIntialFaultData(ft.getFaultId());
            }
            
            
            

            System.out.println(combo.getSelectedItem()
                    + " " + field1.getText()
                    + " " + combo2.getSelectedItem());
            
            parent.refreshPage();

        } else {
            System.out.println("Cancelled");
        }

        add(panel);
    }

    public String getFaultName() {

        return field1.getText();
    }

    public void setFaultName(String faultName) {

        field1.setText(faultName);
    }

    public String getFaultType() {

        return combo2.getSelectedItem()+"";
    }

    public void setFaultType(String faultType) {

        combo2.setSelectedItem(faultType);
    }

    public Item getFaultComponent() {

        return (Item) combo.getSelectedItem();
    }

    public void setFaultComponent(Item faultComponent) {

        combo.setSelectedItem(faultComponent);
    }

    public Faulttree getFaulttreeFinal() {
        Faulttree f = null;

        if (faultTree != null) {
            f = faultTree;
            f.setFaultName(getFaultName());
            f.setFaultType(getFaultType());
            f.setFaultComponent(mainProDao.FindById(getFaultComponent().getId()));
        } else {
            f = new Faulttree();
            f.setFaultName(getFaultName());
            f.setFaultType(getFaultType());
            f.setFaultComponent(mainProDao.FindById(getFaultComponent().getId()));
            f.setProjectId(UserInfo.getInstance().getTclProject());
            f.setUser(UserInfo.getInstance().getuser());
            f.setCreateddate(new Date());
        }

        return f;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            Item[] items = {new Item(1, "One"), new Item(1, "Two"), new Item(1, "Three"), new Item(1, "Four"), new Item(1, "Five")};

            @Override
            public void run() {
                new FaultTreeFrom(items, null, new FaultTreeCalculator());
            }
        });
    }
    
    public void addIntialFaultData(int faultId){
        
        String child = "";
        
        Faultdata rootNode = new Faultdata();

        //f.setFaultdataId(NumberConversion.NVL(jtFaultTree.getValueAt(rowId, 0) + "", 0));
        //rootNode.setSno(0);
        rootNode.setNode("Event");  //Gate
        rootNode.setName("Root");
        rootNode.setDescription("");
        rootNode.setFailurerate(NumberConversion.NVLDouble("", 0));
        rootNode.setProbability(NumberConversion.NVLDouble("", 0));
        rootNode.setTime(NumberConversion.NVLDouble("", 0));
        rootNode.setCategory("A");
        //rootNode.setReferences("");
        rootNode.setChildGate("OR");
        //rootNode.setFaulttree(faulttreeDao.findById(faultId));
        
        
        
        Faultdata f = new Faultdata();

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
        //f.setFaulttree(faulttreeDao.findById(faultId));
        
        /*Faultdata f2 = new Faultdata();

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
        f2.setFaulttree(faulttreeDao.findById(faultId));*/
        
        child = child+faultdataDao.Save(f)+"#";
       // child = child+faultdataDao.Save(f2)+"#";
        
        rootNode.setChild(child);
    
       faultdataDao.Save(rootNode);
    
    }

}
