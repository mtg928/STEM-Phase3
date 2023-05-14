/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.plaf.basic.BasicGraphUI;

/**
 *
 * @author Murali
 */

/** 
* Definition of the custom GraphUI. 
*/
public class RBDEditorGraphUI extends BasicGraphUI {

	protected CellEditorListener cellEditorListener;

	protected JFrame editDialog = null;
        
        private boolean desFlag=false;

	/** 
	* Create the dialog using the cell's editing component. 
	*/
	protected void createEditDialog(Object cell) {
		Dimension editorSize = editingComponent.getPreferredSize();
		editDialog = new JFrame("Edit " + graph.convertValueToString(cell));
		editDialog.setSize(editorSize.width, editorSize.height);
		editDialog.getContentPane().add(editingComponent);
		editingComponent.validate();
		editDialog.pack();
		editDialog.setVisible(true);
	}

	/**
	 * Stops the editing session. If messageStop is true the editor is messaged
	 * with stopEditing, if messageCancel is true the editor is messaged with
	 * cancelEditing. If messageGraph is true the graphModel is messaged with
	 * valueForCellChanged.
	 */
	protected void completeEditing(boolean messageStop, boolean messageCancel,
			boolean messageGraph) {
		if (stopEditingInCompleteEditing && editingComponent != null) {
			Component oldComponent = editingComponent;
                        Object oldCell = editingCell;
                        
			GraphCellEditor oldEditor = cellEditor;
			boolean requestFocus = (graph != null && (graph.hasFocus() || SwingUtilities
					.findFocusOwner(editingComponent) != null));
			editingCell = null;
			editingComponent = null;
			if (messageStop)
				oldEditor.stopCellEditing();
			else if (messageCancel)
				oldEditor.cancelCellEditing();
			graph.remove(oldComponent);
			if (requestFocus)
				graph.requestFocus();
			if (messageGraph) {
		            Object newValue = oldEditor.getCellEditorValue();
                                
                            if (desFlag) {
                                ((RBDCell)oldCell).setDescription(newValue+"");
                            } else {
                                graphLayoutCache.valueForCellChanged(oldCell, newValue);
                            }
  
			}
			updateSize();
			// Remove Editor Listener
			if (oldEditor != null && cellEditorListener != null)
				oldEditor.removeCellEditorListener(cellEditorListener);
			cellEditor = null;
		}
	}

	/**
	 * Will start editing for cell if there is a cellEditor and shouldSelectCell
	 * returns true.
	 * <p>
	 * This assumes that cell is valid and visible.
	 */
	protected boolean startEditing(Object cell, MouseEvent event) {
		completeEditing();
                
                   //Point pMouse = MouseInfo.getPointerInfo().getLocation();
                Point componentLocation = event.getComponent().getMousePosition();
                int i=0;
                RBDCell rBDCell = (RBDCell)cell;
                desFlag=false;
                
		if (graph.isCellEditable(cell)) {
                    
                       Rectangle2D cellBounds = graph.getCellBounds(cell);
                       CellView tmp = graphLayoutCache.getMapping(cell, false);
		       AbstractCellView.cellEditor = new RBDGraphCellEditor();
                       cellEditor = tmp.getEditor();
                        
                    if ((componentLocation.getY()-cellBounds.getY())<=20) {
                         
                          editingComponent = cellEditor.getGraphCellEditorComponent(graph,rBDCell, graph.isCellSelected(cell));
                    
                    }else{
                      i=30;
                          editingComponent = cellEditor.getGraphCellEditorComponent(graph,rBDCell.getDescription(), graph.isCellSelected(cell));                             
                                       //editorSize.setSize(rBDCell.getDescription().length()*10, editorSize.getHeight());
                                       //rBDCell.setDescription(cellEditor.getCellEditorValue()+"vfvfvdb11");
                                       
                          desFlag=true;
                     }                       
                          
                        
			if (cellEditor.isCellEditable(event)) {
			  editingCell = cell;
                          Dimension2D editorSize = editingComponent.getPreferredSize();

				
				Point2D p = getEditorLocation(cell, editorSize, graph
						.toScreen(new Point2D.Double(cellBounds.getX(),
								cellBounds.getY())));
                                
                                //System.out.println("***************"+p.getY()+"--------------"+componentLocation.getY()+"--------*"+cellBounds.getY());

                                graph.add(editingComponent);
   
				editingComponent.setBounds((int) p.getX(), (int) p.getY()+i,
						(int) editorSize.getWidth(), (int) editorSize
								.getHeight());
				editingCell = cell;   
				editingComponent.validate();

				// Add Editor Listener
				if (cellEditorListener == null)
					cellEditorListener = createCellEditorListener();
				if (cellEditor != null && cellEditorListener != null)
					cellEditor.addCellEditorListener(cellEditorListener);
				Rectangle2D visRect = graph.getVisibleRect();
				graph.paintImmediately((int) p.getX(), (int) p.getY(),
						(int) (visRect.getWidth() + visRect.getX() - cellBounds
								.getX()), (int) editorSize.getHeight());
				if (cellEditor.shouldSelectCell(event)
						&& graph.isSelectionEnabled()) {
					stopEditingInCompleteEditing = false;
					try {
						graph.setSelectionCell(cell);  
					} catch (Exception e) {
						System.err.println("Editing exception: " + e);
					}
					stopEditingInCompleteEditing = true;
				}

				if (event instanceof MouseEvent) {
					/*
					 * Find the component that will get forwarded all the mouse
					 * events until mouseReleased.
					 */
					Point componentPoint = SwingUtilities.convertPoint(graph,
							new Point(event.getX(), event.getY()),
							editingComponent);

					/*
					 * Create an instance of BasicTreeMouseListener to handle
					 * passing the mouse/motion events to the necessary
					 * component.
					 */
					// We really want similiar behavior to getMouseEventTarget,
					// but it is package private.
					Component activeComponent = SwingUtilities
							.getDeepestComponentAt(editingComponent,
									componentPoint.x, componentPoint.y);
					if (activeComponent != null) {
						new MouseInputHandler(graph, activeComponent, event);
					}
				}
				return true;
			} else
				editingComponent = null;
		}
		return false;
	}

}