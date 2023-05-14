/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class FormatEdges extends JFrame
{

    public FormatEdges() {
            mxGraph graph = new mxGraph();
            Object parent = graph.getDefaultParent();

            //Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
            //edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);  //STYLE_ENDARROW

            graph.getModel().beginUpdate();
            try
            {
                // Create vertexes
                Object vertex1 = graph.insertVertex(parent, null, "01",  10, 20, 80, 30);
                Object vertex2 = graph.insertVertex(parent, null, "010", 10, 20, 80, 30);
                Object vertex3 = graph.insertVertex(parent, null, "011", 10, 20, 80, 30);
                Object vertex4 = graph.insertVertex(parent, null, "B", 115, 200, 50, 50, "shape=ellipse");

                graph.insertEdge(parent, "01", "", vertex1, vertex2);
                graph.insertEdge(parent, "02", "", vertex1, vertex3);
                graph.insertEdge(parent, "02", "", vertex3, vertex4);
                
                // Connect
                //mxICell edge1 = ((mxICell)(graph.insertEdge(parent, "01", "", vertex1, vertex2)));
                //mxICell edge2 = ((mxICell)(graph.insertEdge(parent, "02", "", vertex1, vertex3)));

                // Layout
                mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);//new ExtendedCompactTreeLayout(graph);
                layout.execute(parent);
            } finally
            {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            getContentPane().add(graphComponent);
    }

    public static void main(String[] args)
    {
        FormatEdges frame = new FormatEdges();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

   
}
