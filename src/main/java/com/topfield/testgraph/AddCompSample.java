/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.testgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;
import java.awt.event.ActionListener;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexView;

/**
*
* @author Kalaimaan
*
*         Adding different component in Jgraph using vertex
*
*/
public class AddCompSample extends JFrame implements ActionListener
{
protected JGraph jGraph = null;
protected DefaultGraphModel jGraphModel = null;

private JButton btnInsert = null;
private JButton btnDelete = null; 
private JComboBox cbxElements = null;
private JComboBox cbxObject = null;

private static int cNum = 0;

private static int gNum = 0;

private HashMap<String, DefaultGraphCell> allCellList = new HashMap<String, DefaultGraphCell>();

public AddCompSample()
{
super("JGraph Demo-kalaimaan");
initGraph();
}

/**
*
*/
private void initGraph() 
{
jGraphModel = new DefaultGraphModel();
jGraph = new JGraph(jGraphModel);

jGraph.setGridColor(Color.lightGray);
jGraph.setGridMode(JGraph.LINE_GRID_MODE);
jGraph.setGridSize(20);
jGraph.setGridEnabled(true);
jGraph.setGridVisible(true);
jGraph.setHandleColor(Color.red);
jGraph.setSelectionEnabled(true);

setLayout(new BorderLayout());

addGraphLayout();
setSize(600, 600);
setVisible(true);
}

/**
*
*/
private void addGraphLayout()
{
jGraphModel.addGraphModelListener(new GraphMode());
jGraph.getGraphLayoutCache().setFactory(new JComponentCellViewFactory());
add(toolButton(), BorderLayout.NORTH);
add(jGraph, BorderLayout.CENTER);
}

/**
*
* @return
*/
private JPanel toolButton()
{
JPanel temp = new JPanel();

// cbxElements = new JComboBox();
// cbxElements.setPreferredSize(new Dimension(100, 25));
// cbxElements.addActionListener(this);
// temp.add(cbxElements);

cbxObject = new JComboBox();
cbxObject.addItem("Text");
cbxObject.addItem("TextArea");
cbxObject.addItem("ComboBox");
cbxObject.addItem("CheckBox");
cbxObject.addItem("Button");
cbxObject.addItem("Lable");
cbxObject.setPreferredSize(new Dimension(100, 25));
cbxObject.addActionListener(this);
temp.add(cbxObject);

btnInsert = new JButton("Insert");
btnInsert.setPreferredSize(new Dimension(100, 25));
btnInsert.addActionListener(this);
temp.add(btnInsert);

btnDelete = new JButton("Delete");
btnDelete.setPreferredSize(new Dimension(100, 25));
btnDelete.addActionListener(this);
temp.add(btnDelete);

return temp;
}

public static void main(String[] args)
{
try
{
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
}
catch (Exception e)
{
e.printStackTrace();
}
AddCompSample gug = new AddCompSample();
gug.setDefaultCloseOperation(EXIT_ON_CLOSE);
}

@Override
public void actionPerformed(ActionEvent e)
{
String action = e.getActionCommand();

if (action.equalsIgnoreCase("Insert"))
insertElement();
else if (action.equalsIgnoreCase("Delete"))
deleteElement();
}

/**
*
*/
private void insertElement()
{
String name = cbxObject.getSelectedItem().toString();
String cellId = cellNumber();
JComponentCell jc = null;
if (name.equalsIgnoreCase("Text"))
jc = new JComponentCell(JcomponentType.TEXT, "Text -" + cellId);
else if (name.equalsIgnoreCase("TextArea"))
jc = new JComponentCell(JcomponentType.AREA, "TextArea -" + cellId);
else if (name.equalsIgnoreCase("ComboBox"))
jc = new JComponentCell(JcomponentType.COMBO, "ComboBox -" + cellId);
else if (name.equalsIgnoreCase("CheckBox"))
jc = new JComponentCell(JcomponentType.CHECK, "CheckBox -" + cellId);
else if (name.equalsIgnoreCase("Button"))
jc = new JComponentCell(JcomponentType.BUTTON, "Button -" + cellId);
else if (name.equalsIgnoreCase("Lable"))
jc = new JComponentCell(JcomponentType.LABEL, "Label -" + cellId);

DefaultGraphCell cell = getCell(jc);
jGraph.getGraphLayoutCache().insert(cell);
allCellList.put(cellId, cell);
}

/**
*
* @param jCell
* @return
*/
private DefaultGraphCell getCell(JComponentCell jCell)
{
DefaultGraphCell cell = new DefaultGraphCell(jCell);

AttributeMap map = new AttributeMap();
Rectangle rec = null;
switch (jCell.getType())
{
case AREA:
rec = new Rectangle(20, 20, 150, 50);
break;
case BUTTON:
rec = new Rectangle(20, 20, 150, 30);
break;

case TEXT:
case CHECK:
case COMBO:
case LABEL:
default:
rec = new Rectangle(20, 20, 150, 25);
}

GraphConstants.setBounds(map, rec);
GraphConstants.setGradientColor(map, Color.white.brighter());
GraphConstants.setBorderColor(map, Color.blue);
GraphConstants.setBackground(map, Color.gray);
GraphConstants.setSizeableAxis(map, 3);
GraphConstants.setOpaque(map, true);
GraphConstants.setEditable(map, false);
cell.setAttributes(map);

return cell;
}

/**
*
*/
private void deleteElement()
{
Object[] object = jGraph.getSelectionCells();
for (Object obj : object)
{
if (obj == null)
continue;

String key = ((DefaultGraphCell) obj).getUserObject().toString();
allCellList.remove(key);
jGraph.getGraphLayoutCache().remove(new Object[] { obj });
}
}

private String cellNumber()
{
return "" + cNum++;
}

private String groupNumber()
{
return "Group-" + gNum++;
}

private class GraphMode implements GraphModelListener
{
@Override
public void graphChanged(GraphModelEvent e)
{
jGraph.clearOffscreen();
}
}

private class JComponentCellViewFactory extends DefaultCellViewFactory
{
protected VertexView createVertexView(Object objCell)
{
DefaultGraphCell cell = (DefaultGraphCell) objCell;
VertexView vertex = null;
vertex = new JComponentView(cell);

return vertex;
}
}

}
