/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.graph.draw.ColorIcon;
import com.graph.draw.Kind;
import com.topfield.controller.BusinessException;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Murali
 */
public class ControlPanel extends JToolBar {

    private static final Random rnd = new Random();
    private Kind kind = Kind.Circular;
    private Action newNode = new NewNodeAction("Add / Edit");
    private Action selNode = new SelectedNodeAction("Selected");
    private Action clearAll = new ClearAction("Clear");
    private Action kindAction = new KindComboAction("Kind");
    private Action color = new ColorAction("Color");
    private Action connect = new ConnectAction("Connect");
    private Action delete = new DeleteAction("Delete");
    private Action random = new RandomAction("Random");
    public JButton defaultButton = new JButton(newNode);
    private JComboBox kindCombo = new JComboBox();
    private ColorIcon hueIcon = new ColorIcon(Color.blue);
    public JPopupMenu popup = new JPopupMenu();
    private List<Node> selected = new ArrayList<>();
     
    private static int radius;
    private DendrogramPaintTest graph;
    private List<Node> nodes;
    private List<Edge> edges;

    public ControlPanel(DendrogramPaintTest graph,int radius, List<Node> nodes, List<Edge> edges,Point mousePt) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.lightGray);

        this.add(defaultButton);
        this.add(new JButton(clearAll));
        this.add(kindCombo);
        this.add(new JButton(color));
        this.add(new JLabel(hueIcon));

        this.graph = graph;
        this.radius = radius;
        this.nodes = nodes;
        this.edges = edges;
        //this.mousePt =mousePt;
        JSpinner js = new JSpinner();
        js.setModel(new SpinnerNumberModel(radius, 5, 100, 5));
        js.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                ControlPanel.radius = (Integer) s.getValue();
                Node.updateRadius(nodes, radius);
                graph.repaint();
            }
        });
        this.add(new JLabel("Size:"));
        this.add(js);
        this.add(new JButton(random));

        popup.add(new JMenuItem(newNode));
        popup.add(new JMenuItem(selNode));
        //popup.add(new JMenuItem(color));
        //popup.add(new JMenuItem(connect));
        popup.add(new JMenuItem(delete));
        /*JMenu subMenu = new JMenu("Kind");
        for (Kind k : Kind.values()) {
            kindCombo.addItem(k);
            subMenu.add(new JMenuItem(new KindItemAction(k)));
        }
        popup.add(subMenu);
        kindCombo.addActionListener(kindAction);*/
    }

    class KindItemAction extends AbstractAction {

        private Kind k;

        public KindItemAction(Kind k) {
            super(k.toString());
            this.k = k;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            kindCombo.setSelectedItem(k);
        }
    }

    private class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            nodes.clear();
            edges.clear();
            graph.repaint();
            
        }
    }

    private class ColorAction extends AbstractAction {

        public ColorAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            Color color = hueIcon.getColor();
            color = JColorChooser.showDialog(
                     graph, "Choose a color", color);
            if (color != null) {
                Node.updateColor(nodes, color);
               hueIcon.setColor(color);
               repaint();
                 graph.repaint();
            }
        }
    }

    private class ConnectAction extends AbstractAction {

        public ConnectAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node.getSelected(nodes, selected);
            if (selected.size() > 1) {
                for (int i = 0; i < selected.size() - 1; ++i) {
                    Node n1 = selected.get(i);
                    Node n2 = selected.get(i + 1);
                    edges.add(new Edge(n1, n2));
                }
            }
             graph.repaint();
        }
    }

    private class DeleteAction extends AbstractAction {

        public DeleteAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            
        if (BusinessException.showBusinessConformation("Do you want to delete this Node? \n "
                    + "The operation will delete the node and the sub nodes associated with it" ,true)== JOptionPane.YES_OPTION){
            Node node =  graph.findNode2();
            node.removeChildrens();
                    //levelHeadings.remove(addpopup.getLevel()-1);
                    //recalculateFrequency(perent);
             node.setSelected(false);
             repaint();
             EventTreeGraph.setSavedHeading(true);
             graph.repaint();
             
        }
        }

        private void deleteEdges(Node n) {
            ListIterator<Edge> iter = edges.listIterator();
            while (iter.hasNext()) {
                Edge e = iter.next();
                if (e.n1 == n || e.n2 == n) {
                    iter.remove();
                }
            }
        }
    }

    private class KindComboAction extends AbstractAction {

        public KindComboAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            kind = (Kind) combo.getSelectedItem();
            //Node.updateKind(nodes, kind);
             graph.repaint();
        }
    }

    private class NewNodeAction extends AbstractAction {

        public NewNodeAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            
       
             graph.performAddEdit(false);
             
             graph.repaint();
            /*Node.selectNone(nodes);
             //Point p = GraphPanel.graph.getMousePoint().getLocation(); 
             Point p = DendrogramPaintTest.graph.getMousePoint().getLocation(); 
             System.out.println("X2 "+p.x+" Y2 "+p.y);
            //Point p = new Point(50, 50);
            Color color = hueIcon.getColor();
            Node n = new Node(p, radius, color, kind);
            n.setSelected(true);
            nodes.add(n);
            DendrogramPaintTest.graph.repaint();*/
        }
    }
    
    private class SelectedNodeAction extends AbstractAction {

        public SelectedNodeAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
             Node node = graph.findNode2();
             String res="No node selected yet";
             
             if (node != null) {
                res="Selected node frequency is "+node.getFrequency();
                JOptionPane.showConfirmDialog(graph, res, "Selected", JOptionPane.INFORMATION_MESSAGE);
            } 
             
        }
    }

    private class RandomAction extends AbstractAction {

        public RandomAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            /*for (int i = 0; i < 16; i++) {
                Point p = new Point(rnd.nextInt(DendrogramPaintTest.graph.getWidth()), rnd.nextInt(DendrogramPaintTest.graph.getHeight()));
                nodes.add(new Node(p, radius, new Color(rnd.nextInt()), kind));
            }*/
             graph.repaint();
        }
    }

}
