/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.utilities.FaultTreeReader;
import com.topfield.utilities.FoldableTree;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.topfield.dao.FaultdataDao;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.datamodel.FTALogan;
import com.topfield.modal.Faultdata;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.calculators.faulttree.FaultTreeData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Murali
 */
public class FaultTreeGraph extends JPanel  {
    
     private static final long serialVersionUID = -2707712944901661771L;
     private JPanel faultTreeGraph = new JPanel();
     private FaultTreeCalculator parent;
     private FaultdataDao faultdataDao = new FaultdataDaoImpl();
     private FaulttreeCurd faulttreeCurd = new FaulttreeCurd();
     private int faultId;

    public FaultTreeGraph(FaultTreeCalculator parent, int faultId)
    {
        
        setLayout(new BorderLayout());
        faultTreeGraph.setLayout(new BorderLayout());
        
        add(faultTreeGraph,BorderLayout.CENTER);
        
        this.parent = parent;
        this.faultId = faultId;
        FaultTreeReader ftr = new FaultTreeReader();
        ArrayList<FTALogan> fTALogan = ftr.getLogonFTAFromDB(faultdataDao.getAllFaultdataByFaulttree(faultId));
        
        if(fTALogan.size() !=0){
        
           loadFaultTree(fTALogan,false);
        }
        

    }
    
    
    
    public void loadFaultTree(ArrayList<FTALogan> fTA,boolean imports){
    
    FaultTreeData data = (FaultTreeData)parent.getComponent(1);
    FoldableTree graph = new FoldableTree();
    //mxGraph graph = new mxGraph();

        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);         
        layout.setUseBoundingBox(false);
        layout.setEdgeRouting(false);
        layout.setLevelDistance(30);
        layout.setNodeDistance(10);

        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {      
            
            ArrayList<FTALogan> fTALogan = faulttreeCurd.orderArrayList(fTA,imports,this.parent);
            

            Object root =null;
            Object vh = null;
            Object v1 = null;
            Object selectedNode = null;
            Faultdata faultdata;
            int i=0;
            FTALogan ref;
            for (FTALogan fTALogan1 : fTALogan) {
                
                if(i==0){  
                          //faultdata = faultdataDao.findByName(fTALogan1.getHeaders(), faultId);
                          root = graph.insertVertex(parent,fTALogan1.getHeaders(),data.findByName(fTALogan1.getHeaders()), (InternalFrameDemo.screenWidth/20)*0.8, 20, 60, 40);
                          fTALogan1.setNode(root);
                          fTALogan1.setParentId(-1);
                          //fTALogan.add(i, fTALogan1);
                          
                         if (fTALogan1.getLogicCode()==1) {
                          vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_TRIANGLE+";"+mxConstants.STYLE_ROTATION+"="+"0;"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.BLUE)+";"+mxConstants.STYLE_FONTCOLOR+"="+mxUtils.getHexColorString(Color.RED));
                         } else {
                          vh = graph.insertVertex(parent, "AND", "AND", 0, 0, 30, 40, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR+";"+mxConstants.STYLE_ROTATION+"="+"0;"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.BLUE)+";"+mxConstants.STYLE_FONTCOLOR+"="+mxUtils.getHexColorString(Color.RED));    
                         }
                          graph.insertEdge(parent, null, "", root, vh);
                          
                          
                          for (String child : fTALogan1.getChild()) {
                          
                           ref = faulttreeCurd.getByHeaderId(fTALogan,fTALogan1.getId());   
                           //faultdata = faultdataDao.findByName(child, faultId);
                          v1 = graph.insertVertex(parent, child,  data.findByName(child), 0, 0, 60, 40/*, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR*/);
                          faulttreeCurd.setCreatedNode(fTALogan, child,fTALogan1.getId(), v1);
                          graph.insertEdge(parent, null, "", vh, v1);
                        
                          }
                }else{
                         selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();
                         if (fTALogan1.getLogicCode()==1) {
                          vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_TRIANGLE+";"+mxConstants.STYLE_ROTATION+"="+"0;"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.BLUE)+";"+mxConstants.STYLE_FONTCOLOR+"="+mxUtils.getHexColorString(Color.RED));
                         } else {
                          vh = graph.insertVertex(parent, "AND", "AND", 0, 0, 30, 40, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR+";"+mxConstants.STYLE_ROTATION+"="+"0;"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.BLUE)+";"+mxConstants.STYLE_FONTCOLOR+"="+mxUtils.getHexColorString(Color.RED));    
                         }
                         graph.insertEdge(parent, null, "", selectedNode, vh);
                          
                          for (String child : fTALogan1.getChild()) {
                          ref = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId());     
                          //v1 = graph.insertVertex(parent, child, (ref != null ? ref.getId() : "" )+"-"+"-"+child+"-"+fTALogan1.getId(), 0, 0, 60, 40);
                           // faultdata = faultdataDao.findByName(child, faultId);
                          v1 = graph.insertVertex(parent, child, data.findByName(child), 0, 0, 60, 40);
                          faulttreeCurd.setCreatedNode(fTALogan, child,fTALogan1.getId(), v1);
                          


                          
                              try {
                                  
                                   //selectedNode = getByHeaderName(fTALogan, fTALogan1.getHeaders()).getNode();
                                   selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();
                                   
                                   graph.insertEdge(parent, null, "",vh , v1);
                                  
                              } catch (NullPointerException e) {
                                  
                              }
                           
                               
                               
                            
                        
                          }
                        
                }
                
                i++;
            }
            
            

            layout.execute(parent);         
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        graph.addListener(mxEvent.FOLD_CELLS,  new mxEventSource.mxIEventListener() {

            @Override
            public void invoke(Object sender, mxEventObject evt) {
                layout.execute(graph.getDefaultParent());
            }
        });
        
        
 
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setBorder(null);
    
        faultTreeGraph.removeAll();
        faultTreeGraph.add(new JLabel(""), BorderLayout.NORTH);
        faultTreeGraph.add(graphComponent, BorderLayout.CENTER);
        faultTreeGraph.revalidate();
        faultTreeGraph.repaint();
    
    }
    


    public static void main(String[] args)
    {
       /* JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
         
        frame.add(new FaultTreeGraph());*/
        
     /*   
        FaultTreeGraph ftg = new FaultTreeGraph();
        //ArrayList<FTALogan> aaa = ftg.removeDuplicate(ftg.orderArrayList(new FaultTreeReader().getLogonFTA()));
        ArrayList<FTALogan> aaa = ftg.orderArrayList(new FaultTreeReader().getLogonFTA());
        
       for (FTALogan fTALogan : aaa) {
            //System.out.println(""+fTALogan.getHeaders());
             System.out.println("Id - "+fTALogan.getId()+" Header - "+fTALogan.getHeaders()+"------ Parent Id - "+fTALogan.getParentId());
            //System.out.println("Id - "+fTALogan.getId()+" Header - "+fTALogan.getHeaders()+"------ Parent Id - "+fTALogan.getNode());
        }*/
        
    }
    
    
    
}
