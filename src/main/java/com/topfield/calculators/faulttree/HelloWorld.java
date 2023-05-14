/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.net.URL;
import javax.swing.ImageIcon;

public class HelloWorld extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;
        
        URL res = getClass().getResource("/coachspecs/images/train.png");
        
        private String imageURL ="/coachspecs/images/gray/";
        

	public HelloWorld()
	{
		super("Hello, World!");
                
                System.out.println("image path"+res.getPath());

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate(); //mxConstants.STYLE_IMAGE+";"+ ;strokeColor=red;fillColor=green
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 100,
					100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"
                                          +mxConstants.STYLE_FONTCOLOR+"="+"black;"
                                          +mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Basic.png");
                
			Object v2 = graph.insertVertex(parent, null, "Hello", 20, 300, 100,
					100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"
                                          +mxConstants.STYLE_FONTCOLOR+"="+"black;"
                                          +mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Node.png");
			graph.insertEdge(parent, null, "Edge", v1, v2,"strokeColor=black;");
                        
			Object v3 = graph.insertVertex(parent, null, "Hello", 20, 300, 100,
					100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"
                                          +mxConstants.STYLE_FONTCOLOR+"="+"black;"
                                          +mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Node.png");
			graph.insertEdge(parent, null, "Edge", v2, v3,"strokeColor=black;");                        
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String[] args)
	{
		HelloWorld frame = new HelloWorld();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}
