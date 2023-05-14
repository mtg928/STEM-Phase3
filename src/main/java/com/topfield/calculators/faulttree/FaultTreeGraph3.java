/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.calculators.faulttree.FaultTreeCalculator;
import javax.swing.JPanel;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.topfield.dao.FaultdataDao;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.datamodel.FTALogan;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Faulttree;
import com.topfield.modal.Spfhdr;
import com.topfield.utilities.FaultTreeReader;
import com.topfield.utilities.FoldableTree;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.ScientificNotation;
import com.topfield.view.popup.FTABasicPopup;
import com.topfield.view.popup.FTAGatePopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class FaultTreeGraph3 extends JPanel {

    private JPanel faultTreeGraph = new JPanel();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    
    
    
    private FaultTreeCalculator parent;
    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    private FaulttreeCurd faulttreeCurd = new FaulttreeCurd();
    private FaultDataCrud faultDataCrud = new FaultDataCrud();
    private FoldableTree graph = new FoldableTree();
    private Spfhdr spfhdr;
    private List<Faultdata> fdList;
    
    private String imageURL ="/coachspecs/images/gray/";

    public FaultTreeGraph3(FaultTreeCalculator parentTab, Spfhdr spfhdr) {

        setLayout(new BorderLayout());
        
        
   
        faultTreeGraph.setLayout(new BorderLayout());
        add(faultTreeGraph, BorderLayout.CENTER);

        this.parent = parentTab;
        this.spfhdr = spfhdr;
        fdList = faultdataDao.getAllFaultdataBySpfHdr(spfhdr.getSpfhdrid());
        FaultTreeReader ftr = new FaultTreeReader();
        ArrayList<FTALogan> fTALogan = ftr.getLogonFTAFromDB(faultdataDao.getAllFaultdataBySpfHdr(spfhdr.getSpfhdrid()));

        if (fTALogan.size() != 0) {

            loadFaultTree(fTALogan, false);
        }else if(fdList.size()==1){
            loadFaultTreeSingle(fdList.get(0), false);
        
        }

    }
    
    
    public void refreshAll(){
    parent.refreshData(spfhdr);
    parent.refreshGraph(spfhdr);
    }
    
    public mxGraphComponent getGraphComponent(){
     return (mxGraphComponent)((BorderLayout)faultTreeGraph.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    }

    public mxGraph getGraph(){
        return graph;
    }
    
    public void loadFaultTree(ArrayList<FTALogan> fTA, boolean imports) {

        FaultTreeData data = (FaultTreeData) parent.getComponentAt(1);
        //mxGraph graph = new mxGraph();

        Object parent = graph.getDefaultParent();

        int NodeWeight = 100;
        int NodeHeight = 50;
        int nodeLevelDistanceX = 30;
        int nodeLevelDistanceY = 30;

        //setBackground(Color.red);
        //setLayout(new BorderLayout());
        //Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
        //edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);  //STYLE_ENDARROW
        graph.getModel().beginUpdate();

        try {

            ArrayList<FTALogan> fTALogan = faulttreeCurd.orderArrayList(fTA, imports, this.parent);

            Object root = null;
            Object vh = null;
            Object v1 = null;
            Object selectedNode = null;
            Faultdata faultdata;
            int i = 0;
            FTALogan ref;
            
            String[] nodeData;
            
            for (FTALogan fTALogan1 : fTALogan) {

                if (i == 0) {
                    //faultdata = faultdataDao.findByName(fTALogan1.getHeaders(), faultId);  //data.findByName(fTALogan1.getHeaders())
                    
                    System.out.println("parent"+ parent.toString());
                     System.out.println("parent"+ fTALogan1.getId());
                     System.out.println("parent"+ data.findById(fTALogan1.getId()+""));
                    
                    root = graph.insertVertex(parent, fTALogan1.getId() + "", data.findById2(fTALogan1.getId() + "")[1], (InternalFrameDemo.screenWidth / 20) * 0.8, 20, 100, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Node.png");
                    fTALogan1.setNode(root);
                    fTALogan1.setParentId(-1);
                    //fTALogan.add(i, fTALogan1);

                    if (fTALogan1.getLogicCode() == 1) {
                        //vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_TRIANGLE + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                         vh = graph.insertVertex(parent, "OR", "OR", 20, 20, 45,45,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_OR.png");
                    } else {
                         vh = graph.insertVertex(parent, "AND", "AND", 20, 20, 45,45,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_AND.png");
                    }
                    graph.insertEdge(parent, null, "", root, vh,"strokeColor=black;");

                    for (String child : fTALogan1.getChild()) {

                        ref = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId());
                        //faultdata = faultdataDao.findByName(child, faultId);
                        //v1 = graph.insertVertex(parent, child,  data.findByName(child), 0, 0, 60, 40/*, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR*/);
                       nodeData = data.findById2(child);
                        
                        if (nodeData[0].contentEquals("Basic")) {
                             v1 = graph.insertVertex(parent, child, nodeData[1], 0, 0, 100, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Event_Basic_03.png"+";verticalAlign=top;align=center");
                        } else {
                             v1 = graph.insertVertex(parent, child, nodeData[1], 0, 0, 100, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Node.png");
                        }
                        ((mxCell) v1).setId(child);
                        System.out.println(child + " child " + ((mxCell) v1).getId());
                        faulttreeCurd.setCreatedNodeById(fTALogan, Integer.parseInt(child), fTALogan1.getId(), v1);
                        graph.insertEdge(parent, null, "", vh, v1,"strokeColor=black;");

                    }
                } else {
                    selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();
                    if (fTALogan1.getLogicCode() == 1) {
                         vh = graph.insertVertex(parent, "OR", "OR", 20, 20, 45,45,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_OR.png");
                    } else {
                         vh = graph.insertVertex(parent, "AND", "AND", 20, 20, 45,45,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_AND.png");
                    }
                    graph.insertEdge(parent, null, "", selectedNode, vh);

                    for (String child : fTALogan1.getChild()) {
                        ref = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId());
                        //v1 = graph.insertVertex(parent, child, (ref != null ? ref.getId() : "" )+"-"+"-"+child+"-"+fTALogan1.getId(), 0, 0, 60, 40);
                        // faultdata = faultdataDao.findByName(child, faultId);
                        //v1 = graph.insertVertex(parent, child, data.findByName(child), 0, 0, 60, 40);
                        
                        nodeData = data.findById2(child);
                        
                        if (nodeData[0].contentEquals("Basic")) {
                             v1 = graph.insertVertex(parent, child, nodeData[1], 0, 0, 100, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Event_Basic_03.png"+";verticalAlign=top;align=center");
                        } else {
                             v1 = graph.insertVertex(parent, child, nodeData[1], 0, 0, 100, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL+"FTA_Node.png");
                        }
                       
                        ((mxCell) v1).setId(child);
                        System.out.println(child + " child " + ((mxCell) v1).getId());
                        //faulttreeCurd.setCreatedNode(fTALogan, child,fTALogan1.getId(), v1);
                        faulttreeCurd.setCreatedNodeById(fTALogan, Integer.parseInt(child), fTALogan1.getId(), v1);

                        try {

                            //selectedNode = getByHeaderName(fTALogan, fTALogan1.getHeaders()).getNode();
                            selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();

                            graph.insertEdge(parent, null, "", vh, v1,"strokeColor=black;");

                        } catch (NullPointerException e) {

                        }

                    }

                }

                i++;
            }

            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            layout.execute(parent);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        //graphComponent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.));
        //graphComponent.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        graphComponent.setBorder(null);
        //add(graphComponent, BorderLayout.CENTER);
        //add(graphComponent);
        
         graphComponent.getViewport().setOpaque(true);
         graphComponent.getViewport().setBackground(Color.WHITE);

        faultTreeGraph.removeAll();
        faultTreeGraph.add(new JLabel(""), BorderLayout.NORTH);
        faultTreeGraph.add(graphComponent, BorderLayout.CENTER);
        faultTreeGraph.revalidate();
        faultTreeGraph.repaint();

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {

                    Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                    if (cell != null) {
                        System.out.println("cell=" + graph.getLabel(cell));
                    }

                } else if (SwingUtilities.isRightMouseButton(e)) {
                    //JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint.");

                    mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                    String lablel = graph.getLabel(cell);
                    JPopupMenu menu = createPopupMenu(e.getPoint(), cell, Integer.parseInt(cell.getId()));  //lablel.substring(0, lablel.indexOf('-')
                    // Display PopupMenu
                    menu.show(graphComponent, e.getX(), e.getY());
                    //System.out.println("cell.getId() - "+cell.getId());
                }
            }
        });

    }
    
    public void loadFaultTreeSingle(Faultdata fTA, boolean imports) {

        FaultTreeData data = (FaultTreeData) parent.getComponentAt(1);
        //mxGraph graph = new mxGraph();

        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();


            Object root = null;
            Object vh = null;
            Object v1 = null;
            Object selectedNode = null;
            Faultdata faultdata;

            FTALogan ref;
  
                    //faultdata = faultdataDao.findByName(fTALogan1.getHeaders(), faultId);  //data.findByName(fTALogan1.getHeaders())
                    
                    System.out.println("parent"+ parent.toString());
                     System.out.println("parent"+ fTA.getFaultdataId());
                     System.out.println("parent"+ fTA.getDescription());
                    
                    root = graph.insertVertex(parent, fTA.getFaultdataId() + "", fTA.getDescription(), (InternalFrameDemo.screenWidth / 20) * 0.8, 20, 100, 60);
                    //fTALogan1.setNode(root);
                    //fTALogan1.setParentId(-1);
                    //fTALogan.add(i, fTALogan1);

                    /*if (fTALogan1.getLogicCode() == 1) {
                        vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_TRIANGLE + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    } else {
                        vh = graph.insertVertex(parent, "AND", "AND", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ACTOR + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    }
                    graph.insertEdge(parent, null, "", root, vh);
                     */
                   
                

                
            

            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            layout.execute(parent);
            graph.getModel().endUpdate();
      
        

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        //graphComponent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.));
        //graphComponent.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        graphComponent.setBorder(null);
        //add(graphComponent, BorderLayout.CENTER);
        //add(graphComponent);
        
         graphComponent.getViewport().setOpaque(true);
         graphComponent.getViewport().setBackground(Color.WHITE);

        faultTreeGraph.removeAll();
        faultTreeGraph.add(new JLabel(""), BorderLayout.NORTH);
        faultTreeGraph.add(graphComponent, BorderLayout.CENTER);
        faultTreeGraph.revalidate();
        faultTreeGraph.repaint();

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {

                    Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                    if (cell != null) {
                        System.out.println("cell=" + graph.getLabel(cell));
                    }

                } else if (SwingUtilities.isRightMouseButton(e)) {
                    //JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint.");

                    mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                    String lablel = graph.getLabel(cell);
                    JPopupMenu menu = createPopupMenu(e.getPoint(), cell, Integer.parseInt(cell.getId()));  //lablel.substring(0, lablel.indexOf('-')
                    // Display PopupMenu
                    menu.show(graphComponent, e.getX(), e.getY());
                    //System.out.println("cell.getId() - "+cell.getId());
                }
            }
        });

    }

    public static void main(String[] args) {

        SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
        JFrame frame = new JFrame();
        frame.add(new FaultTreeGraph3(null, spfhdrDao.findById(30)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

    public void loadFaultTreeImports(ArrayList<FTALogan> fTA) {

        FaultTreeData data = (FaultTreeData) parent.getComponent(1);
        // mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        int NodeWeight = 100;
        int NodeHeight = 50;
        int nodeLevelDistanceX = 30;
        int nodeLevelDistanceY = 30;

        //setBackground(Color.red);
        //setLayout(new BorderLayout());
        //Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
        //edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);  //STYLE_ENDARROW
        graph.getModel().beginUpdate();

        try {

            ArrayList<FTALogan> fTALogan = faulttreeCurd.orderArrayList(fTA, true, this.parent);

            Object root = null;
            Object vh = null;
            Object v1 = null;
            Object selectedNode = null;
            Faultdata faultdata;
            int i = 0;
            FTALogan ref;
            for (FTALogan fTALogan1 : fTALogan) {

                if (i == 0) {
                    //faultdata = faultdataDao.findByName(fTALogan1.getHeaders(), faultId);
                    root = graph.insertVertex(parent, fTALogan1.getHeaders(), data.findByName(fTALogan1.getHeaders()), (InternalFrameDemo.screenWidth / 20) * 0.8, 20, 60, 40);
                    fTALogan1.setNode(root);
                    fTALogan1.setParentId(-1);
                    //fTALogan.add(i, fTALogan1);

                    if (fTALogan1.getLogicCode() == 1) {
                        vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_TRIANGLE + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    } else {
                        vh = graph.insertVertex(parent, "AND", "AND", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ACTOR + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    }
                    graph.insertEdge(parent, null, "", root, vh);

                    for (String child : fTALogan1.getChild()) {

                        ref = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId());
                        //faultdata = faultdataDao.findByName(child, faultId);
                        v1 = graph.insertVertex(parent, child, data.findByName(child), 0, 0, 60, 40/*, mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ACTOR*/);
                        faulttreeCurd.setCreatedNode(fTALogan, child, fTALogan1.getId(), v1);
                        //faulttreeCurd.setCreatedNodeById(fTALogan, Integer.parseInt(child),fTALogan1.getId(), v1);
                        graph.insertEdge(parent, null, "", vh, v1);

                    }
                } else {
                    selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();
                    if (fTALogan1.getLogicCode() == 1) {
                        vh = graph.insertVertex(parent, "OR", "OR", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_TRIANGLE + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    } else {
                        vh = graph.insertVertex(parent, "AND", "AND", 0, 0, 30, 40, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_ACTOR + ";" + mxConstants.STYLE_ROTATION + "=" + "0;" + mxConstants.STYLE_FILLCOLOR + "=" + mxUtils.getHexColorString(Color.BLUE) + ";" + mxConstants.STYLE_FONTCOLOR + "=" + mxUtils.getHexColorString(Color.RED));
                    }
                    graph.insertEdge(parent, null, "", selectedNode, vh);

                    for (String child : fTALogan1.getChild()) {
                        ref = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId());
                        //v1 = graph.insertVertex(parent, child, (ref != null ? ref.getId() : "" )+"-"+"-"+child+"-"+fTALogan1.getId(), 0, 0, 60, 40);
                        // faultdata = faultdataDao.findByName(child, faultId);
                        v1 = graph.insertVertex(parent, child, data.findByName(child), 0, 0, 60, 40);
                        faulttreeCurd.setCreatedNode(fTALogan, child, fTALogan1.getId(), v1);
                        //faulttreeCurd.setCreatedNodeById(fTALogan, Integer.parseInt(child),fTALogan1.getId(), v1);

                        try {

                            //selectedNode = getByHeaderName(fTALogan, fTALogan1.getHeaders()).getNode();
                            selectedNode = faulttreeCurd.getByHeaderId(fTALogan, fTALogan1.getId()).getNode();

                            graph.insertEdge(parent, null, "", vh, v1);

                        } catch (NullPointerException e) {

                        }

                    }

                }

                i++;
            }

            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            layout.execute(parent);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        //graphComponent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.));
        //graphComponent.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        graphComponent.setBorder(null);
        //add(graphComponent, BorderLayout.CENTER);
        //add(graphComponent);

        faultTreeGraph.removeAll();
        faultTreeGraph.add(new JLabel(""), BorderLayout.NORTH);
        faultTreeGraph.add(graphComponent, BorderLayout.CENTER);
        faultTreeGraph.revalidate();
        faultTreeGraph.repaint();

    }

    //
    // PopupMenu
    //
    public JPopupMenu createPopupMenu(final Point pt, final Object cell, int nodeId) {
        JPopupMenu menu = new JPopupMenu();
        if (cell != null) {

            // Insert
            menu.add(new AbstractAction("Insert Event") {
                public void actionPerformed(ActionEvent ev) {
                    //insert(pt);

                    Faultdata fdata = faultdataDao.findById(nodeId);
                    Object[] options1 = {"Add", "Cancel"};
                    int childCreated;

                     FTAGatePopup fTAGatePopup = new FTAGatePopup();

                    int result = JOptionPane.showOptionDialog(null, fTAGatePopup, "Enter Details",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {

                        childCreated = faultDataCrud.insertAEvent(spfhdr, fdata, fTAGatePopup.getComponentName(), fTAGatePopup.getDescription(),fTAGatePopup.getGate(), fTAGatePopup.getReference(),fTAGatePopup.getPTIAdjestment());

                        if (fdata.getNode().contentEquals("Event")) {

                            faultDataCrud.insertANode(nodeId, childCreated);
                        } else {

                            faultDataCrud.addChildToBasicNoade(nodeId, childCreated, "OR");
                        }
                        clearTopNode();
                        parent.refreshData(spfhdr);
                        parent.refreshGraph(spfhdr);
                    }

                }
            }
            );
            menu.addSeparator();
            //Basic
            menu.add(new AbstractAction("Insert Basic") {
                public void actionPerformed(ActionEvent ev) {
                    //insert(pt);

                    Faultdata fdata = faultdataDao.findById(nodeId);
                    FaultTreeData dataf = (FaultTreeData) parent.getComponentAt(1);
                    Object[] options1 = {"Add", "Cancel"};
                    int childCreated;

                    FTABasicPopup fTABasicPopup = new FTABasicPopup(spfhdr,dataf.getAddedList(),false,false);

                    int result = JOptionPane.showOptionDialog(null, fTABasicPopup, "Enter Details",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {

                        childCreated = faultDataCrud.insertBasicNode(spfhdr, fdata,fTABasicPopup.getFunctionalComponents(), fTABasicPopup.getComponentName(), fTABasicPopup.getDescription(), fTABasicPopup.getReference(),
                                faultDataCrud.changeToShortTypeName(fTABasicPopup.getDataType()), fTABasicPopup.getFrequency(),
                                fTABasicPopup.getProbability(), fTABasicPopup.getRepairTime());

                        if (fdata.getNode().contentEquals("Event")) {

                            faultDataCrud.insertANode(nodeId, childCreated);
                        } else {

                            faultDataCrud.addChildToBasicNoade(nodeId, childCreated, "OR");
                        }
                        clearTopNode();
                        parent.refreshData(spfhdr);
                        parent.refreshGraph(spfhdr);
                    }

                }
            }
            );
            menu.addSeparator();
            // Edit
            menu.add(new AbstractAction("Edit") {
                public void actionPerformed(ActionEvent e) {

                    Faultdata fdata = faultdataDao.findById(nodeId);
                    Object[] options1 = {"Save", "Cancel"};

                    if (fdata.getNode().contentEquals("Event")) {

                        FTAGatePopup fTAGatePopup = new FTAGatePopup();
                        fTAGatePopup.setComponentName(fdata.getName());
                        fTAGatePopup.setDescription(fdata.getDescription());
                        fTAGatePopup.setGate(fdata.getChildGate());
                        fTAGatePopup.setPTIAdjestment(fdata.getComments());
                        fTAGatePopup.setReference(fdata.getFdreferences());

                        int result = JOptionPane.showOptionDialog(null, fTAGatePopup, "Enter Details",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options1, null);

                        if (result == JOptionPane.YES_OPTION) {

                            
                            fdata.setName(fTAGatePopup.getComponentName());
                            fdata.setDescription(fTAGatePopup.getDescription());
                            fdata.setChildGate(fTAGatePopup.getGate());
                            fdata.setComments(fTAGatePopup.getPTIAdjestment());
                            fdata.setFdreferences(fTAGatePopup.getReference());

                            faultdataDao.Update(fdata);
                            clearTopNode();
                            parent.refreshData(spfhdr);
                            parent.refreshGraph(spfhdr);

                        }

                    } else {

                        FTABasicPopup fTABasicPopup = new FTABasicPopup(spfhdr,new ArrayList<Integer>(),true,false);
                        fTABasicPopup.setFunctionalComponents(fdata.getCalcComp());
                        fTABasicPopup.setComponentName(fdata.getName());
                        fTABasicPopup.setDescription(fdata.getDescription());
                        fTABasicPopup.setReference(fdata.getFdreferences());
                        fTABasicPopup.setDataType(faultDataCrud.changeToFullTypeName(fdata.getCategory()));
                        fTABasicPopup.setFrequency(fdata.getFailurerate());
                        fTABasicPopup.setProbability(fdata.getProbability());
                        fTABasicPopup.setRepairTime(fdata.getTime());

                        int result = JOptionPane.showOptionDialog(null, fTABasicPopup, "Enter Details",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options1, null);

                        if (result == JOptionPane.YES_OPTION) {

                            fdata.setCalcComp(fTABasicPopup.getFunctionalComponents());
                            fdata.setName(fTABasicPopup.getComponentName());
                            fdata.setDescription(fTABasicPopup.getDescription());
                            fdata.setFdreferences(fTABasicPopup.getReference());
                            fdata.setCategory(faultDataCrud.changeToShortTypeName(fTABasicPopup.getDataType()));
                            fdata.setFailurerate(NumberConversion.NVLDouble(fTABasicPopup.getFrequency() + "", 0));
                            fdata.setProbability(NumberConversion.NVLDouble(fTABasicPopup.getProbability() + "", 0));
                            fdata.setTime(NumberConversion.NVLDouble(fTABasicPopup.getRepairTime() + "", 0));

                            faultdataDao.Update(fdata);
                            clearTopNode();
                            parent.refreshData(spfhdr);
                            parent.refreshGraph(spfhdr);
                        }

                    }

                    // graph.startEditingAtCell(cell);
                }
            }
            );
        }
        //menu.addSeparator();
        // Calculate
        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Calculate") {
                public void actionPerformed(ActionEvent e) {
                    //remove.actionPerformed(e);
                    
                    FaultTreeData faultTreeData = parent.getFaultTable();
                    // Calculate NodeProbablity
                    try {
                        InternalFrameDemo.mainFrame.setLoader();
                        FaultTreeEquations.calulateNodeProbability(faultTreeData.getFaultTable());

                        FaultTreeEquations.findCutSetNode(faultTreeData.getFaultdataByRowId(0), faultTreeData);

                        faultTreeData.saveAllTableData(false);
                        
                        spfhdr.setComments(faultTreeData.getFaultTable().getValueAt(0, 7)+"");
                        spfhdrDao.update(spfhdr);
                        //parent.setComponentAt(2, new FaultTreeGraph2(parent,faultId));
                        parent.refreshGraph(spfhdr);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        InternalFrameDemo.mainFrame.removeLoder();

                    }

                }
            });
        }
        //menu.addSeparator();
        // Remove
        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Remove") {
                public void actionPerformed(ActionEvent e) {
                    //remove.actionPerformed(e);

                    FaultTreeData faultTreeData = parent.getFaultTable();

                    System.out.println("nodeId " + nodeId);
                    faultDataCrud.deleteNode(faultTreeData.getFaultTable(), faultTreeData.findRowByFaultId(nodeId + ""));
                    clearTopNode();
                    parent.refreshData(spfhdr);
                    parent.refreshGraph(spfhdr);

                }
            });
        }
        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Merge Calcfile Data") {
                public void actionPerformed(ActionEvent e) {

                    margeData();
                    
                }
            });
        }
        

        return menu;
    }

    private double getXForCell(String id) {
        double res = -1;
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        for (Object object : cells) {
            mxCell cell = (mxCell) object;
            if (id.equals(cell.getId())) {
                res = cell.getGeometry().getX();
            }
        }
        graph.clearSelection();
        return res;
    }
    
    public void margeData(){
    
        try {
            
        InternalFrameDemo.mainFrame.setLoader();
            
        List<Faultdata> faultTreeData = faultdataDao.getAllFaultdataBySpfHdr(spfhdr.getSpfhdrid());
        Calcfile calcfileRef;
        double failrate;
        String dataType="";
        
        
        for (Faultdata faultdata : faultTreeData) {
            
            if (faultdata.getNode().contentEquals("Basic")) {
                dataType = faultDataCrud.changeToFullTypeName(faultdata.getCategory());
                calcfileRef = faultdata.getCalcComp();
                failrate = FaultTreeEquations.FailureRateConveter(dataType, calcfileRef.getFailureRate(), calcfileRef.getFMPercentage() ,calcfileRef.getURPercentage());
                
                
                faultdata.setName(calcfileRef.getCompId());
                faultdata.setDescription(calcfileRef.getComponents());
                if ((dataType.contentEquals("Fixed Probability"))) {
                    faultdata.setFailurerate(0.0);  
                    faultdata.setProbability(failrate);
                      
                } else {
                      faultdata.setFailurerate(failrate);
                      faultdata.setProbability(0.0);
                }
            
            
            faultdataDao.Update(faultdata);
            
            System.out.println(faultdata.getDescription()+ " - "+ failrate);
            
            }
        }
        clearTopNode();
        parent.refreshData(spfhdr);
        parent.refreshGraph(spfhdr);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
           InternalFrameDemo.mainFrame.removeLoder();
        
        }
        
    
    }

    
    public void clearTopNode(){
        
       Faultdata faultdata = fdList.get(0);
       faultdata.setFailurerate(0.0);
       faultdata.setProbability(0.0);
       faultdataDao.Update(faultdata);
    
    }
    
    
}
