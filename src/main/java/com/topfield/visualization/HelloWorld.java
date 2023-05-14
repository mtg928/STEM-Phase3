/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

/**
 *
 * @author Murali
 */
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import javax.swing.JFrame;

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

public class HelloWorld extends JPanel
{

private static final long serialVersionUID = -2707712944901661771L;


	
public HelloWorld(){
    
  //super("Hello, World!");
  setLayout (new BorderLayout());

  mxGraph graph = new mxGraph();
  Object parent = graph.getDefaultParent();
  graph.getModel().beginUpdate();
  
    System.out.println(" "+((InternalFrameDemo.screenWidth/2.5)+((InternalFrameDemo.screenWidth/2.5)-200))+" "+InternalFrameDemo.HEIGHT/2);
  
   try{
	Object root = graph.insertVertex(null, null, "Hello", 500, 225, 40,80,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ELLIPSE+";"+mxConstants.STYLE_GRADIENTCOLOR+"="+mxUtils.getHexColorString(Color.CYAN));
         
	Object leftv1 = graph.insertVertex(parent, null, "World!", 200, 50,80, 30);
        Object leftv2 = graph.insertVertex(parent, null, "World!", 200, 100,80, 30);
        Object leftv3 = graph.insertVertex(parent, null, "World!", 200, 150,80, 30);
        Object leftv4 = graph.insertVertex(parent, null, "World!", 200, 200,80, 30);
        Object leftv5 = graph.insertVertex(parent, null, "World!", 200, 250,80, 30);
        Object leftv6 = graph.insertVertex(parent, null, "World!", 200, 300,80, 30);
        Object leftv7 = graph.insertVertex(parent, null, "World!", 200, 350,80, 30);
        Object leftv8 = graph.insertVertex(parent, null, "World!", 200, 400,80, 30);
        Object leftv9 = graph.insertVertex(parent, null, "World!", 200, 450,80, 30);
        
        graph.insertEdge(parent, null, "Edge1", root, leftv1);
        graph.insertEdge(parent, null, "Edge2", root, leftv2);
        graph.insertEdge(parent, null, "Edge3", root, leftv3);
        graph.insertEdge(parent, null, "Edge4", root, leftv4);
        graph.insertEdge(parent, null, "Edge5", root, leftv5);
        graph.insertEdge(parent, null, "Edge6", root, leftv6);
        graph.insertEdge(parent, null, "Edge7", root, leftv7);
        graph.insertEdge(parent, null, "Edge8", root, leftv8);
        graph.insertEdge(parent, null, "Edge9", root, leftv9);
		
		
        Object reightv1 = graph.insertVertex(parent, null, "World!", 838, 50,80, 30);
        Object reightv2 = graph.insertVertex(parent, null, "World!", 838, 100,80, 30);
        Object reightv3 = graph.insertVertex(parent, null, "World!", 838, 150,80, 30);
        Object reightv4 = graph.insertVertex(parent, null, "World!", 838, 200,80, 30);
        Object reightv5 = graph.insertVertex(parent, null, "World!", 838, 250,80, 30);
        Object reightv6 = graph.insertVertex(parent, null, "World!", 838, 300,80, 30);
        Object reightv7 = graph.insertVertex(parent, null, "World!", 838, 350,80, 30);
        Object reightv8 = graph.insertVertex(parent, null, "World!", 838, 400,80, 30);
        Object reightv9 = graph.insertVertex(parent, null, "World!", 838, 450,80, 30);
        
        graph.insertEdge(parent, null, "Edge1", root, reightv1);
        graph.insertEdge(parent, null, "Edge2", root, reightv2);
        graph.insertEdge(parent, null, "Edge3", root, reightv3);
        graph.insertEdge(parent, null, "Edge4", root, reightv4);
        graph.insertEdge(parent, null, "Edge5", root, reightv5);
        graph.insertEdge(parent, null, "Edge6", root, reightv6);
        graph.insertEdge(parent, null, "Edge7", root, reightv7);
        graph.insertEdge(parent, null, "Edge8", root, reightv8);
        graph.insertEdge(parent, null, "Edge9", root, reightv9);
        
        
        
        
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
        
        private static void morphGraph(mxGraph graph,
                        mxGraphComponent graphComponent) {
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

