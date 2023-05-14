/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.CalchdrDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.CalchdrDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.datamodel.JTreeCheckItem;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Mpghdr;
import com.topfield.utilities.NumberConversion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.swing.checkboxtree.CheckBoxNodeData;
import com.swing.checkboxtree.CheckBoxNodeEditor;
import com.swing.checkboxtree.CheckBoxNodePanel;
import com.swing.checkboxtree.CheckBoxNodeRenderer;

public class MgfPopup extends JPanel {

    private CalcFunctionsDao calcFunctionsDao = new CalcFunctionsDaoImpl();
    private static MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    private CalcfileDao CalcfileDao = new CalcfileDaoImpl();
    private static CalchdrDao calchdrDao = new CalchdrDaoImpl();
    private final DefaultMutableTreeNode root;


   
    
    
    public MgfPopup(Mpghdr mpgHdr,List<Integer> addedList){
        
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(500, 450));
    
      Collection<CalcFunctions> calcFunList = mpgHdr.getCalcFunctionsCollection();
      root = new DefaultMutableTreeNode(mpgHdr.getFmeaComponent().getMpgDescription());
      Collection<Calcfile> calcfileRef;
      DefaultMutableTreeNode treeFun;
      DefaultMutableTreeNode treeComp;
      
       for (CalcFunctions calcFun : calcFunList) {
   
            //calcfileRef = calcFun.getCalcfileCollection();
            calcfileRef = CalcfileDao.getAllCalcfileByCalcFun(calcFun.getCalcFunId());
            treeFun = add(root, calcFun.getCalcFunId(),calcFun.getCalcFunction(), false,false);
            //root.add(treeFun);
            
            if (calcfileRef != null) {
                
            
            for (Calcfile calcfile : calcfileRef) {
                //System.out.println("!addedList.contains(calcFun.getCalcFunId()) - "+!addedList.contains(calcfile.getCalcId()));
                treeComp = add(treeFun,calcfile.getCalcId(), calcfile.getComponents(),!addedList.contains(calcfile.getCalcId()),!addedList.contains(calcfile.getCalcId()));
                   
            }
           }
        }
      
         DefaultTreeModel treeModel = new DefaultTreeModel(root);
         JTree tree = new JTree(treeModel);

         CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        tree.setCellRenderer(renderer);

        CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
        tree.setCellEditor(editor);
        tree.setEditable(true);
        
        //selectChild(tree);
        
        // listen for changes in the selection
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(final TreeSelectionEvent e) {
                Calcfile calcfile ;

               // System.out.println(System.currentTimeMillis() + ": selection changed");
                  /*for (int object : getSelectedItem()) {
                      calcfile = CalcfileDao.findById(object);
                      System.out.println(calcfile.getCalcId()+" - "+calcfile.getComponents());
                  }*/
                //print(root);
            }
        });

        
        add(new JScrollPane(tree), BorderLayout.CENTER);
    
        expandTree(tree, true);
    
    }

    private DefaultMutableTreeNode add( DefaultMutableTreeNode parent, int id,  String text,
                                        boolean checked) {
        //final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
        //final Item data = new Item(1, text, checked);
        //final JTreeCheckItem data = new JTreeCheckItem(id, text, checked);
        final CheckBoxNodeData data = new CheckBoxNodeData(id, text, checked,true);

        final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
        parent.add(node);
        return node;
    }
    
    private DefaultMutableTreeNode add( DefaultMutableTreeNode parent, int id,  String text,
                                        boolean checked,boolean enabled) {
        //final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
        //final Item data = new Item(1, text, checked);
        //final JTreeCheckItem data = new JTreeCheckItem(id, text, checked);
        final CheckBoxNodeData data = new CheckBoxNodeData(id, text, checked,enabled);

        final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
        parent.add(node);
        return node;
    }
    
    private void expandTree(JTree tree, boolean expand) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), expand);
    }
    
    private void expandAll(JTree tree, TreePath path, boolean expand) {
        TreeNode node = (TreeNode) path.getLastPathComponent();

        if (node.getChildCount() >= 0) {
            Enumeration enumeration = node.children();
            while (enumeration.hasMoreElements()) {
                TreeNode n = (TreeNode) enumeration.nextElement();
                TreePath p = path.pathByAddingChild(n);

                expandAll(tree, p, expand);
            }
        }

        if (expand) {
            tree.expandPath(path);
        } else {
            tree.collapsePath(path);
        }
    }
    
    public List<Integer> getSelectedItem(){
     DefaultMutableTreeNode firstNode;
     DefaultMutableTreeNode secNode;
     CheckBoxNodeData dataFun;
     CheckBoxNodeData dataComp;
     List<Integer> res = new ArrayList<>();

      System.out.println("-----");
        for (int i = 0; i < root.getChildCount(); i++) {
            firstNode = (DefaultMutableTreeNode)root.getChildAt(i);
            dataFun = (CheckBoxNodeData)firstNode.getUserObject();
            //System.out.println("Node -"+aNode);
            
            //selectChild(firstNode);
            
            // System.out.println("Node -"+dataFun.getId()+" - "+dataFun.getText());
             //System.out.println("Node -"+dataFun.getText());
             
             for (int j = 0; j < firstNode.getChildCount(); j++) {
                secNode = (DefaultMutableTreeNode)firstNode.getChildAt(j);
                dataComp = (CheckBoxNodeData)secNode.getUserObject();
                //System.out.println("       SecondNode -"+dataComp.getId()+" - "+dataComp.getText()+" - "+dataComp.isChecked());
                //System.out.println("       SecondNode -"+dataComp.getText());
                 if (dataComp.isChecked()) {
                     res.add(dataComp.getId());
                    
                    // System.out.println(dataComp.getText()+" - "+dataComp.isChecked()+dataComp.getId());
                     //res.add(NumberConversion.NVL(dataComp.getText().substring(dataComp.getText().length() - 3,dataComp.getText().length() - 1), 0));
                 }
            }
             
        }

        return res;
    
    }
    
    public void selectChild(JTree tree){
           /*CheckBoxNodeRenderer node =  (CheckBoxNodeRenderer)tree.getCellRenderer();
           System.out.println("NNNNNNNNNNNNNNN - "+node.getPanel().label.getText());*/
           
            //TreeNode root = (TreeNode) tree.getModel().getRoot();
            CheckBoxNodePanel root = (CheckBoxNodePanel) tree.getModel().getRoot();
            System.out.println("NNNNNNNNNNNNNNN - "+root);
        
    }

    public static void print(DefaultMutableTreeNode aNode) {
        String name = aNode.toString();
        int level = aNode.getLevel();
        String placement = "";
        while (level > 0) {
            placement += ">";
            level--;
        }
        if (aNode.isLeaf()) {
            System.out.println(placement + name);
            return;
        }

        System.out.println(placement + "--- " + name + " ---");
        for (int i = 0; i < aNode.getChildCount(); i++) {
            print((DefaultMutableTreeNode) aNode.getChildAt(i));
        }
        System.out.println(placement + "+++ " + name + " +++");
    }

    public static void main(final String args[]) {
        
        List<Integer> addedList = Arrays.asList(new Integer[]{1,2,3,4,5});
        
        // show the tree onscreen
        final JFrame frame = new JFrame("CheckBox Tree");
        //final JScrollPane scrollPane = new JScrollPane(new MgfPopup(mpgHdrDao.getFMEASById(19),addedList));
        //final JScrollPane scrollPane = new JScrollPane(new MgfPopup());
        frame.add(new MgfPopup(mpgHdrDao.getFMEASById(41),addedList));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setVisible(true);
    }
    
     public MgfPopup() {

        setLayout(new BorderLayout());

        root = new DefaultMutableTreeNode("Root");

        final DefaultMutableTreeNode accessibility
                = add(root,1, "Accessibility", true);
        add(accessibility,1, "Move system caret with focus/selection changes", false);
        add(accessibility,1, "Always expand alt text for images", true);
        root.add(accessibility);

        /*final DefaultMutableTreeNode browsing
                = new DefaultMutableTreeNode("Browsing");*/
         final DefaultMutableTreeNode browsing
                = add(root,1, "Browsing", true);
        add(browsing,1, "Notify when downloads complete", true);
        add(browsing,1, "Disable script debugging", true);
        add(browsing,1, "Use AutoComplete", true);
        add(browsing,1, "Browse in a new process", false);
        root.add(browsing);

        final DefaultTreeModel treeModel = new DefaultTreeModel(root);
        final JTree tree = new JTree(treeModel);

        final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        tree.setCellRenderer(renderer);

        final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
        tree.setCellEditor(editor);
        tree.setEditable(true);
        
        

      // listen for changes in the selection
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(final TreeSelectionEvent e) {
               // System.out.println(System.currentTimeMillis() + ": selection changed");
                  
                  
                  for (int object : getSelectedItem()) {
                      //System.out.println(CalcfileDao.findById(object).getComponents());
                  }
                  
                //print(root);
            }
        });

        // listen for changes in the model (including check box toggles)
        treeModel.addTreeModelListener(new TreeModelListener() {

            @Override
            public void treeNodesChanged(final TreeModelEvent e) {
                System.out.println(System.currentTimeMillis() + ": nodes changed");
            }

            @Override
            public void treeNodesInserted(final TreeModelEvent e) {
                System.out.println(System.currentTimeMillis() + ": nodes inserted");
            }

            @Override
            public void treeNodesRemoved(final TreeModelEvent e) {
                System.out.println(System.currentTimeMillis() + ": nodes removed");
            }

            @Override
            public void treeStructureChanged(final TreeModelEvent e) {
                System.out.println(System.currentTimeMillis() + ": structure changed");
            }
        });

        add(tree, BorderLayout.CENTER);

    }

}
