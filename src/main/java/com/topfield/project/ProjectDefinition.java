/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.utilities.JCheckBoxTree;
import com.topfield.utilities.PaintRenderer;
import com.topfield.utilities.UniqueArray;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductGroup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Murali
 */
public class ProjectDefinition extends JPanel{
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private String selections="";
    private JCheckBoxTree cbt;
    private JLabel label;

    public ProjectDefinition(boolean sil) {
        
     int mainCompCount=0; 
     int subCompCount=0;
     MutableTreeNode bNode ;
     
      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(672, 304));
       
       List<MainProductGroup> mpg = mpgDao.getAllMPG();
       MutableTreeNode root = new DefaultMutableTreeNode("Train Systems");
       
        for (MainProductGroup mainProductGroup : mpg) {
            
          bNode = new DefaultMutableTreeNode(mainProductGroup.getMpgDescription());

            
          root.insert(bNode, mainCompCount);
               subCompCount=0;
               for (SubProductGroup subProductGroup : mainProductGroup.getSubProductGroupCollection()) {
                
                   if (sil && subProductGroup.getSil().equals("Y")) {
                       bNode.insert(new DefaultMutableTreeNode(subProductGroup.getSpgDescription()), subCompCount);
                       subCompCount++;
                   }else if(!sil) {
                       bNode.insert(new DefaultMutableTreeNode(subProductGroup.getSpgDescription()), subCompCount);
                       subCompCount++;
                   }
                  // System.out.println(""+subProductGroup.getSpgDescription());
                   
               }
          
          mainCompCount++;
        }
        

  /*  MutableTreeNode bNode = new DefaultMutableTreeNode("B");
    //MutableTreeNode cNode = new DefaultMutableTreeNode("C");
    //root.insert(bNode, 0);
    //root.insert(cNode, 1);
    bNode.insert(new DefaultMutableTreeNode("1"), 0);
    bNode.insert(new DefaultMutableTreeNode("2"), 1);
    cNode.insert(new DefaultMutableTreeNode("1"), 0);
    cNode.insert(new DefaultMutableTreeNode("2"), 1);*/

    final DefaultTreeModel model = new DefaultTreeModel(root);
         

          cbt = new JCheckBoxTree();
         //cbt.setPreferredSize(new Dimension(640, 290));
         cbt.setModel(model);

         ((DefaultTreeModel)cbt.getModel()).reload();
  
          label = new JLabel("<html> <h3> Select your required tain components </h3> </html>");

        add(label,BorderLayout.NORTH);
        add(new JScrollPane(cbt),BorderLayout.CENTER);
        
        
        cbt.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
            public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
                System.out.println("event");
                TreePath[] paths = cbt.getCheckedPaths();
                String array[];
                String node="";
                selections="";
                
                for (TreePath tp : paths) {
                    for (Object pathPart : tp.getPath()) {
                       // array1= .split(",");
                        //System.out.print("*---"+pathPart + ",");
                        node=node+pathPart + "@";
                    }      
                    array= node.split("@");
                    //System.out.println("Array Length -"+array.length);
                    if(array.length > 2){
                     System.out.println(array[2]);
                      selections=selections+array[2]+"#";
                    }
                    node="";
                }
                
                
                
            }           
        });         
    }
    
    public String getSelections(){
    
    return selections;
    }
    
    public String[] getSelectionsAsArray(){
    
        String[] res = UniqueArray.toUniqueArray(selections.split("\\#"));
        
    return res;
    }
    
    public JLabel getlabel(){
    
    return label;
    }
    
    public void setselectionNode(TreePath paths){
    
    SwingUtilities.invokeLater(new Runnable() {  
    public void run() {  
        cbt.startEditingAtPath(paths);  
    }  
    });
    
        System.out.println("setselectionNode ****************");
    }
    
    
  /*  public void setselectionNode(TreePath paths){
     cbt.setSelectionPath(paths);
     //cbt.expandPath(paths);     
     //cbt.setSelectionPath(paths);
     //cbt.scrollPathToVisible(paths);
     //cbt.repaint();
     //cbt.setSelectionRow(2);
     //cbt.setSelectionInterval(0, cbt.getRowCount());
     
     //cbt.getModel().getRoot();
     
     cbt.getCellRenderer().getTreeCellRendererComponent(cbt, cbt.getModel().getRoot(), true, true, true, 0, true);
     //cbt.setRowSelectionInterval(0, 0);
        System.out.println("semfhgj-------"+cbt.getSelectionPath());
        System.out.println(cbt.getModel().getRoot().toString());
    }*/
    
    
}
