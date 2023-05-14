/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductFunctionsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductFunctionsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductFunctions;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.utilities.ImageConversion;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeCellRenderer;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.UIManager;



/**
 *
 * @author Murali
 */
public class SubComponentsFunctions extends JPanel {

private JTree tree;
private SubProductGroup spg;
private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
private SubProductFunctionsDao spfDao = new SubProductFunctionsDaoImpl();
private JPanel infoPanel = new JPanel() ;


  public  SubComponentsFunctions(SubProductGroup spg){
       
       setLayout(new BorderLayout());
       infoPanel.setLayout(new BorderLayout());
       this.spg = spg;          
       add(new SubComponentsFunction(spg),BorderLayout.WEST);
       add(infoPanel, BorderLayout.CENTER);
       
       //infoPanel.add(getFunctionalFailuresInfo("A"));
       
       repaint();
       revalidate();
   } 
  
    public FunctionalFailuresInfo getFunctionalFailuresInfo(String selectedNode){
     
       FunctionalFailuresInfo info = new FunctionalFailuresInfo(selectedNode);
                 
        infoPanel.removeAll();
        infoPanel.add(info);
        infoPanel.revalidate();
        infoPanel.repaint();
     
      return info;
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
    
    
    public class SubComponentsFunction extends JPanel {
    
    
       public  SubComponentsFunction (SubProductGroup spg){
       
       
       SubProductComponentsDao mpc = new SubProductComponentsDaoImpl(); 
       List<SubProductComponents> spcList = mpc.getAllSPCBySPG(spg.getSpgId());
       List<SubProductFunctions> spf = spfDao.getAllSPGFunction();
       
       //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       setLayout(new BorderLayout());
       setPreferredSize(new Dimension(400,getHeight()));
       
       
       
       /*add(new JButton());
       add(new JButton());
       add(new JButton());
       add(new JButton());*/
       
        ImageIcon mpgIcon= ImageConversion.SetImageSize(getClass().getResource(FrameSettings.getImageSelPath()+"STEM_Icons_01_Settings_00.png"),50,50);
        ImageIcon spgIcon = ImageConversion.SetImageSize(getClass().getResource(FrameSettings.getImageSelPath()+"STEM_Icons_01_MainProdGroup.png"),40,40);
        ImageIcon spcIcon = ImageConversion.SetImageSize(getClass().getResource(FrameSettings.getImageSelPath()+"STEM_Icons_01_SubProdGroup.png"),30,30);
        ImageIcon spfIcon = ImageConversion.SetImageSize(getClass().getResource(FrameSettings.getImageSelPath()+"STEM_Icons_01_SubProdComponent.png"),20,20); 
       //create the root node
        DefaultMutableTreeNode rootMpg = new DefaultMutableTreeNode(spg.getMpgRef().getMpgDescription()+" ["+spg.getMpgRef().getUsername().getUsername()+"]");
        DefaultMutableTreeNode rootSpg = new DefaultMutableTreeNode(spg.getSpgDescription()+" ["+spg.getUsername().getUsername()+"]");
       
        
       
        if(spcList != null & spcList.size() > 0) {
			for(SubProductComponents spcObj : spcList) {
                            System.out.println("Hi"+spcObj.toString());
                            
                            DefaultMutableTreeNode root2 = new DefaultMutableTreeNode(spcObj.getSpcDescription()+" ["+spcObj.getUsername().getUsername()+"]");
                            rootSpg.add(root2);
		            //middlePanel.add(new TrainSystems.TrainMainComponents(mpgObj,""));
                            
                            
                            for (Functionalfailures functionalfailures : spcObj.getFunctionalfailuresCollection()) {
                                root2.add(new DefaultMutableTreeNode(functionalfailures.getDescription()+" ["+spcObj.getUsername().getUsername()+"]"));
                            }
                            
			}
        }
        
        rootMpg.add(rootSpg);
        
        
        
        
      /*  //create the child nodes
        DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Current collector up/down");
        DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Short circuiter control ");
        DefaultMutableTreeNode fruitNode1 = new DefaultMutableTreeNode("Main switch control (HSCB)");
        DefaultMutableTreeNode fruitNode2 = new DefaultMutableTreeNode("Activate main power supply (Bat. on) ");
        DefaultMutableTreeNode fruitNode3 = new DefaultMutableTreeNode("Deactivate main power supply (Bat. off)");
        DefaultMutableTreeNode fruitNode4 = new DefaultMutableTreeNode("Shut down to sleeping mode / standby mode ");
        DefaultMutableTreeNode fruitNode5 = new DefaultMutableTreeNode("Switch on auxiliary inverter ");
        //add the child nodes to the root node
        root.add(vegetableNode);
        root.add(fruitNode);
        root.add(fruitNode1);
        root.add(fruitNode2);
        root.add(fruitNode3);
        root.add(fruitNode4);
        root.add(fruitNode5);*/
         
        //create the tree by passing in the root node
        tree = new JTree(rootMpg);
        
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                int i= (tree.getMinSelectionRow() > 1) ? tree.getMinSelectionRow() -2  :1 ;
                //selectedLabel.setText(selectedNode.getUserObject().toString());
                
                 //JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame,"You click at "+selectedNode.getUserObject().toString());
                //spcList.get(i).getSpgRef().getSubProductComponentsCollection().
                
                 //ImagePanel info = new ImagePanel(spg.getMpgRef().getMpgDescription(),spg.getSpgDescription(),spcList.get(i).getSpcDescription(),spf.get(i).getSilRms(),spf.get(i).getSilRmsGoa4(),spf.get(i).getSilRmsManual(),spf.get(i).getDesignImplementation());
                 
                 //JOptionPane.showMessageDialog(null,selectedNode.getUserObject().toString());  
                 
                 //System.out.println("hhhhhhhhhhhhhh"+selectedNode.getUserObject().toString().replaceAll("\\p{P}",""));
                 //System.out.println("hhhhhhhhhhhhhh"+removeBrackets(selectedNode.getUserObject().toString()));
                 

                  getFunctionalFailuresInfo(removeBrackets(selectedNode.getUserObject().toString()));

            }
        });
        
        
        
        
        

       /* DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();  
        renderer.setOpenIcon(mpgIcon);
        renderer.setClosedIcon(spgIcon);
        renderer.setLeafIcon(imageIcon);
        tree.setCellRenderer(renderer);*/
       
       
         tree.setCellRenderer(new DefaultTreeCellRenderer() {
   

           @Override
           public Component getTreeCellRendererComponent(JTree tree, Object value,
               boolean selected, boolean expanded, boolean isLeaf, int row,
               boolean focused) {
             Component c = super.getTreeCellRendererComponent(tree, value, selected,
                 expanded, isLeaf, row, focused);
               int pathcount =tree.getPathForRow(row)==null? 1:tree.getPathForRow(row).getPathCount();
               System.out.println("svfdbgdn"+row+" "+value);
             
               if (pathcount==1) {
                   setIcon(mpgIcon);
               }  else if (pathcount==2) {
                   setIcon(spgIcon);
               }else if (pathcount==3) {
                   setIcon(spcIcon);
               }else {
                   setIcon(spfIcon);
               }
               
               
             return c;
           }
         });
         
        
        
        //tree.setShowsRootHandles(true);
        //tree.setRootVisible(false);
        
        
       
        
        //expandTree(tree, true);
         add(new JScrollPane(tree));
        //add(tree);

        

    
    }
       
       
     
       
       
       
    public String removeBrackets(String string){
        
       String[] parts = string.split("\\s\\[");
       
       return parts[0];
       }
       
    }
    
    public class ImagePanel extends JPanel{

    private BufferedImage image;
     

    public ImagePanel(String mainHeading , String subHeading , String functions ,
     String sil_rms, String sil_rms_goa4, String sil_rms_manual, String design_implementation ) {
        
        DisplayImage(this,"/coachspecs/images/faceView.png", mainHeading ,  subHeading ,  functions ,
                     sil_rms,  sil_rms_goa4,  sil_rms_manual,  design_implementation);
        
     
    } 

  private  void DisplayImage(JPanel jp, String url,String mainHeading , String subHeading , String functions ,
     String sil_rms, String sil_rms_goa4, String sil_rms_manual, String design_implementation) {
    JLabel jl=new JLabel();
     jl.setIcon(new javax.swing.ImageIcon(getClass().getResource(url)));
    jp.add(jl);
    
    
   
    
   add(new JLabel("<html>\n" +
"<head>\n" +
"<style>\n" +
"table {\n" +
"    font-family: arial, sans-serif;\n" +
"    border-collapse: collapse;\n" +
"    width: 100%;\n" +
"}\n" +
"\n" +
"td, th {\n" +
"    border: 1px solid #dddddd;\n" +
"    text-align: left;\n" +
"    padding: 8px;\n" +
"}\n" +
"\n" +
"tr:nth-child(even) {\n" +
"    background-color: #dddddd;\n" +
"}\n" +
"</style>\n" +
"</head>\n" +
"<body>\n" +
"\n" +
"<h2>"+mainHeading+" - "+subHeading+"</h2>\n" +
"\n" +
"<table width=\"400\">\n" +
"  <tr>\n" +
"    <th> Functions </th>\n" +
"    <th> Properties  </th>\n" +

"  </tr>\n" +
"  <tr>\n" +
"    <td>functions</td>\n" +
"    <td>"+functions+"</td>\n" +

"  </tr>\n" +
"  <tr>\n" +
"    <td>sil_rms</td>\n" +
"    <td>"+sil_rms+"</td>\n" +

"  </tr>\n" +
"  <tr>\n" +
"    <td>sil_rms_goa4</td>\n" +
"    <td>"+sil_rms_goa4+"</td>\n" +

"  </tr>\n" +
"  <tr>\n" +
"    <td>sil_rms_manual</td>\n" +
"    <td>"+sil_rms_manual+"</td>\n" +

"  </tr>\n" +
"  <tr>\n" +
"    <td>design_implementation</td>\n" +
"    <td>"+design_implementation+"</td>\n" +

"  </tr>\n" +

"</table>\n" +
"\n" +
"</body>\n" +
"</html>"));
    
      /* add(new JLabel(
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    ".cities {\n" +
                    "    background-color: black;\n" +
                    "    color: white;\n" +
                    "    margin: 20px;\n" +
                    "    padding: 20px;\n" +
                    "} \n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<div class=\"cities\">\n" +
                    "  <h2>London</h2>\n" +
                    "  <p>London is the capital of England.</p>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"cities\">\n" +
                    "  <h2>Paris</h2>\n" +
                    "  <p>Paris is the capital of France.</p>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div class=\"cities\">\n" +
                    "  <h2>Tokyo</h2>\n" +
                    "  <p>Tokyo is the capital of Japan.</p>\n" +
                    "</div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>"));*/
      
    
    
  }  
    
       
  /*  @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }
  
    try {     

            ImageIcon frontView = new ImageIcon(getClass().getResource("/coachspecs/images/faceView.png"));
          //image = ImageIO.read(new File("/coachspecs/images/faceView.png"));
          image = (BufferedImage) frontView.getImage();
       } catch (Exception ex) {
            // handle exception...
       }
  
  
  
*/

  
    }
}
    

