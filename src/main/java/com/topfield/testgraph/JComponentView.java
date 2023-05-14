/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.testgraph;

import java.awt.Component;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Murali
 */
public class JComponentView extends VertexView
{
private static CompViewRenderer renderer = new CompViewRenderer();

private static class CompViewRenderer extends VertexRenderer
{
public CompViewRenderer()
{
super();
}

@Override
public Component getRendererComponent(JGraph jGraph, CellView cellView, boolean selected, boolean focused,
boolean preview)
{
Component comp = null;
DefaultGraphCell cell = (DefaultGraphCell) cellView.getCell();
JComponentCell jcell = (JComponentCell)cell.getUserObject();
switch (jcell.getType())
{
case LABEL:
JLabel lbl = (JLabel) super.getRendererComponent(jGraph, cellView, selected, focused, preview);
lbl.setText(jcell.getLabel());
comp = lbl;
break;
case TEXT:
comp = new JTextField(jcell.getLabel());
break;
case COMBO:
comp = new JComboBox();
break;
case CHECK:
comp = new JCheckBox();
break;
case AREA:
JTextArea txtArea = new JTextArea();
comp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
comp.setBounds(((Rectangle2D) cell.getAttributes().get(GraphConstants.BOUNDS)).getBounds());
break;
case BUTTON:
comp = new JButton(jcell.getLabel());
break;
}
return comp;
}
}

public JComponentView(Object cell)
{
super(cell);
}

@Override
public CellViewRenderer getRenderer()
{
return renderer;
}
}
