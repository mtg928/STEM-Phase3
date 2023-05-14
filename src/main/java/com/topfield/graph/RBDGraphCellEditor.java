/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import org.jgraph.graph.DefaultGraphCellEditor;
import org.jgraph.graph.GraphCellEditor;

/**
 *
 * @author Murali
 */
public class RBDGraphCellEditor extends DefaultGraphCellEditor  {
    
    
    	/**
	 * Returns a new RealCellEditor.
	 */
	protected GraphCellEditor createGraphCellEditor() {
		return new RBDRealCellEditor();
	}
    
}
