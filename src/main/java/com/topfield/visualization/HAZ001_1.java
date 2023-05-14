/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import com.topfield.utilities.StringFuntions;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.topfield.main.InternalFrameDemo;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */



public class HAZ001_1  extends JPanel{

private static final long serialVersionUID = -2707712944901661771L;
private int totelLevel=5;
private int level1Count=5;
private int levelAlignX=50;
private int levelAlignY=110;
private StringFuntions sf = new StringFuntions();

public HAZ001_1(){

setLayout (new BorderLayout());

  mxGraph graph = new mxGraph();
  Object parent = graph.getDefaultParent();
  graph.getModel().beginUpdate();
  
  HazardsBuilder hb = new HazardsBuilder();
  
    System.out.println(" "+((InternalFrameDemo.screenWidth/2.5)+((InternalFrameDemo.screenWidth/2.5)-200))+" "+InternalFrameDemo.HEIGHT/2);
  
   try{
	Object root = graph.insertVertex(null, null, sf.MultipleLine("Failure to Control the Train", 20), InternalFrameDemo.screenWidth+250, InternalFrameDemo.screenHeight/6, 120,120,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.RED)+";"+mxConstants.STYLE_FONTSTYLE+"="+mxConstants.FONT_BOLD);

        String [] content = {"Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",
                             "Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",
                             "Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",
                             "Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",
                             "Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)"};
        
        //int[] omited = {3};
        
       /* Object levelObj[] = hb.CreateVertex(level1Count,content,new int[]{}, graph, levelAlignX, levelAlignY,5);
        Object levelObj2[] = hb.CreateVertex(level1Count,content,new int[]{}, graph, levelAlignX+250, levelAlignY,1);
        Object levelObj3[] = hb.CreateVertex(level1Count,content,new int[]{3}, graph, levelAlignX+500, levelAlignY,1);
        
        
        hb.CreateEdge(level1Count,"Causes", graph, levelObj, levelObj2, null,new int[]{});
        hb.EdgeParentSetter(levelObj2, levelObj3, new int[]{3});
        hb.CreateEdge(level1Count,"Barriers", graph, levelObj2, levelObj3, null,new int[]{3});
        hb.CreateEdge(level1Count,"Hazardous Event", graph, levelObj3, null, root,new int[]{});*/
       
       Object levelObj[] = hb.CreateVertexAndEdge(null,null, level1Count, content,"",  new int[]{}, graph, levelAlignX, levelAlignY, 5);
       Object levelObj2[] = hb.CreateVertexAndEdge(levelObj,null, level1Count, content,"Causes",  new int[]{}, graph, levelAlignX+250, levelAlignY, 1);
       Object levelObj3[] = hb.CreateVertexAndEdge(levelObj2,null, level1Count, content,"Barriers",  new int[]{3}, graph, levelAlignX+500, levelAlignY, 1);
       hb.EdgeParentSetter(levelObj2, levelObj3, new int[]{3}); 
       hb.CreateEdge(level1Count,"Hazardous Event", graph, levelObj3, null, root,new int[]{});
   }
   finally
   {
       graph.getModel().endUpdate();
   }

      mxGraphComponent graphComponent = new mxGraphComponent(graph);
      //getContentPane().add(graphComponent);
      add(graphComponent);
      
    //  morphGraph(graph, graphComponent);
}
	


 private static void morphGraph(mxGraph graph,mxGraphComponent graphComponent) {
                // define layout
                mxIGraphLayout layout = new mxFastOrganicLayout(graph);

                // layout using morphing
                graph.getModel().beginUpdate();
                try {
                        layout.execute(graph.getDefaultParent());
                } finally {
                        mxMorphing morph = new mxMorphing(graphComponent, 20, 1.5, 20);

                        morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {

                                @Override
                                public void invoke(Object arg0, mxEventObject arg1) {
                                        graph.getModel().endUpdate();
                                        // fitViewport();
                                }

   

                        });

                        morph.startAnimation();
                }

        }       
        
        

/*	public static void main(String[] args)
	{
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}*/

}


/*public HAZ001_1(){
    
  //super("Hello, World!");
  setLayout (new BorderLayout());

  mxGraph graph = new mxGraph();
  Object parent = graph.getDefaultParent();
  graph.getModel().beginUpdate();
  
    System.out.println(" "+((MainFrame.screenWidth/2.5)+((MainFrame.screenWidth/2.5)-200))+" "+MainFrame.HEIGHT/2);
  
   try{
	Object root = graph.insertVertex(null, null, sf.MultipleLine("Failure to Control the Train", 20), MainFrame.screenWidth-230, MainFrame.screenHeight/3, 120,120,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_FILLCOLOR+"="+mxUtils.getHexColorString(Color.RED)+";"+mxConstants.STYLE_FONTSTYLE+"="+mxConstants.FONT_BOLD);

        Object leftvlev11 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 550, 10,200, 100);
        Object leftvlev12 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 550, 120,200, 100);
        //Object leftvlev13 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 550, 230,200, 100);
        Object leftvlev14 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 550, 340,200, 100);
        Object leftvlev15 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 550, 450,200, 100);

        Object leftvlev21 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 300, 10,200, 100);
        Object leftvlev22 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 300, 120,200, 100);
        Object leftvlev23 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 300, 230,200, 100);
        Object leftvlev24 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 300, 340,200, 100);
        Object leftvlev25 = graph.insertVertex(parent, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 300, 450,200, 100);        

        Object leftvlev31 = graph.insertVertex(null, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 50, 10,200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+5);
        Object leftvlev32 = graph.insertVertex(null, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 50, 120,200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+5);
        Object leftvlev33 = graph.insertVertex(null, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 50, 230,200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+5);
        Object leftvlev34 = graph.insertVertex(null, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 50, 340,200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+5);
        Object leftvlev35 = graph.insertVertex(null, null,sf.MultipleLine("Failsafe design of the ATP/ATC system. (Failure of ATP/ATC (watchdog) system to result in an initiation of Emergency Brake bringing the train to a safe state)",35), 50, 450,200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+5);         
        
        graph.insertEdge(parent, null, "Hazardous Event", root, leftvlev11);
        graph.insertEdge(parent, null, "Hazardous Event", root, leftvlev12);
        graph.insertEdge(parent, null, "Hazardous Event", root, leftvlev23);
        graph.insertEdge(parent, null, "Hazardous Event", root, leftvlev14);
        graph.insertEdge(parent, null, "Hazardous Event", root, leftvlev15);
        
        graph.insertEdge(parent, null, "Barriers", leftvlev11, leftvlev21);
        graph.insertEdge(parent, null, "Barriers", leftvlev12, leftvlev22);
        //graph.insertEdge(parent, null, "Barriers", leftvlev13, leftvlev23);
        graph.insertEdge(parent, null, "Barriers", leftvlev14, leftvlev24);
        graph.insertEdge(parent, null, "Barriers", leftvlev15, leftvlev25);
		
        graph.insertEdge(parent, null, "Causes", leftvlev21, leftvlev31);
        graph.insertEdge(parent, null, "Causes", leftvlev22, leftvlev32);
        graph.insertEdge(parent, null, "Causes", leftvlev23, leftvlev33);
        graph.insertEdge(parent, null, "Causes", leftvlev24, leftvlev34);
        graph.insertEdge(parent, null, "Causes", leftvlev25, leftvlev35);
        
        
   }
   finally
   {
       graph.getModel().endUpdate();
   }

      mxGraphComponent graphComponent = new mxGraphComponent(graph);
      //getContentPane().add(graphComponent);
      add(graphComponent);
      
    //  morphGraph(graph, graphComponent);
   }
     */  