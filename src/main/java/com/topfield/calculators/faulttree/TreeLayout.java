/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.JFrame;

/**
 *
 * @author Murali
 */
public class TreeLayout extends JFrame
{

        

	public TreeLayout()
	{
		super("Hello, World!");
                

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
                graph.getModel().beginUpdate(); 
                
                
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 0, 0, 100,100);
                
			Object v2 = graph.insertVertex(parent, null, "Hello", 0, 0, 100,100);
                        
			Object v3 = graph.insertVertex(parent, null, "Hello", 0, 0, 100,100);          
                        
                        
                        graph.insertEdge(parent, null, "Edge", v1, v2); 
                        graph.insertEdge(parent, null, "Edge", v1, v3); 
                        
                        new mxCompactTreeLayout(graph, true).execute(parent);
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
		TreeLayout frame = new TreeLayout();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}