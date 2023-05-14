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
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxGraphLayout;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorld1 extends JFrame {

  private static final long serialVersionUID = -2707712944901661771L;

  
  
  public HelloWorld1() {
    super("Hello, World!");

    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    mxGraphLayout layout = new mxHierarchicalLayout(graph);

    graph.getModel().beginUpdate();
   // Set<String> inserted = new HashSet<>();
    try {


      Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
      Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
      Object v3 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
      
      graph.insertEdge(parent, null, "Edge", v1, v2);
      graph.insertEdge(parent, null, "Edge 2", v1, v3);
      graph.insertEdge(parent, null, "Edge 3", v2, v3);
      
    } catch (Exception ex) {
      Logger.getLogger(HelloWorld1.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      layout.execute(parent);
      graph.getModel().endUpdate();
    }

    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    getContentPane().add(graphComponent);
    
  }


  public static void main(String[] args) {
    HelloWorld1 frame = new HelloWorld1();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 320);
    frame.setVisible(true);
  }

}
