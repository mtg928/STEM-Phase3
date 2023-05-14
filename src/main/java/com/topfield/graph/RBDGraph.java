/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import com.topfield.drawings.EditorGraph;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;


    //
    // Custom Graph
    //
    // Defines a Graph that uses the Shift-Button (Instead of the Right
    // Mouse Button, which is Default) to add/remove point to/from an edge.
    public  class RBDGraph extends JGraph {

        // Construct the Graph using the Model as its Data Source
        public RBDGraph(GraphModel model) {
            this(model, null);
        }

        // Construct the Graph using the Model as its Data Source
        public RBDGraph(GraphModel model, GraphLayoutCache cache) {
            super(model, cache);
            // Make Ports Visible by Default
            //setPortsVisible(true); // Commented by Murali for disable link nodes at the begining
            // Use the Grid (but don't make it Visible)
            setGridEnabled(true);
            // Set the Grid Size to 10 Pixel
            setGridSize(6);
            // Set the Tolerance to 2 Pixel
            setTolerance(2);
            // Accept edits if click on background
            setInvokesStopCellEditing(true);
            // Allows control-drag
            setCloneable(true);
            // Jump to default port on connect
            setJumpToDefaultPort(true);
        }
        
        	/** 
	* Override parent method with custom GraphUI. 
	*/
	public void updateUI() {
		setUI(new RBDEditorGraphUI());
		invalidate();
	}

    }
