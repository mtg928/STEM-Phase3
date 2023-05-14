/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Murali
 */
public class Test extends JFrame {

        public static void main(String[] args) {
                JFrame f = new JFrame();
                f.setSize(800, 800);
                f.setLocation(300, 200);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final mxGraph graph = new mxGraph();

                mxGraphComponent graphComponent = new mxGraphComponent(graph);
                f.getContentPane().add(graphComponent, BorderLayout.CENTER);

                f.setVisible(true);
                Object parent = graph.getDefaultParent();

                graph.getModel().beginUpdate();
                try {
                        Object start = graph.insertVertex(parent, "start", "start", 100,
                                        100, 80, 30);
                        for (int i = 0; i < 10; i++) {
                                Object a = graph.insertVertex(parent, "A" + i, "A" + i, 100,
                                                100, 80, 30);
                                graph.insertEdge(parent, null, "E" + i, start, a);

                                Object b = graph.insertVertex(parent, "B" + i, "B" + i, 100,
                                                100, 80, 30);
                                graph.insertEdge(parent, null, "E" + i, a, b);
                                start = a;
                        }
                } finally {
                        graph.getModel().endUpdate();
                }

                morphGraph(graph, graphComponent);
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

                        morph.addListener(mxEvent.DONE, new mxIEventListener() {

                                @Override
                                public void invoke(Object arg0, mxEventObject arg1) {
                                        graph.getModel().endUpdate();
                                        // fitViewport();
                                }

   

                        });

                        morph.startAnimation();
                }

        }
}
