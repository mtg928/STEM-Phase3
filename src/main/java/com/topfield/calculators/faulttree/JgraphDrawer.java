/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import com.topfield.calculators.calcfile.CalcFile;
import static com.topfield.calculators.eventtree.EventTreeGraph.setSavedHeading;
import com.topfield.controller.BusinessException;
import com.topfield.dao.FaultdataDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.datamodel.FaulttreeNode;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Spfhdr;
import com.topfield.settings.FrameSettings;
import com.topfield.utilities.FoldableTree;
import com.topfield.utilities.ImageConversion;
import com.topfield.utilities.NumberConversion;
import com.topfield.view.popup.FTABasicPopup;
import com.topfield.view.popup.FTAGatePopup;
import com.topfield.view.popup.InsertCCFBottomUp;
import com.topfield.view.popup.ProgressBar;
import com.topfiled.interfaces.MyPrintable;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import org.jgraph.graph.DefaultGraphCell;

public class JgraphDrawer extends JPanel implements MyPrintable {

    protected Action event, basic, undo, redo, edit, remove, group, ungroup, tofront, toback, cut, copy, paste, save; // Actions which Change State
    private static final long serialVersionUID = -2707712944901661771L;
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    private String imageURL = "/coachspecs/images/gray/";
    private FoldableTree graph = new FoldableTree();
    //private mxGraph graph = new mxGraph();
    private Object parent = graph.getDefaultParent();
    private mxCompactTreeLayout mxCompactLayout;
    private JGraphServices jgraphServices = new JGraphServices();
    private JGraphCRUD graphCRUD = new JGraphCRUD(jgraphServices);
    private List<Faultdata> fdataList;
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private boolean bottomUp = false;
    private JLabel saveInfo;

    private Spfhdr spfHdr;

    public JgraphDrawer(Spfhdr spfhdr) {

        setLayout(new BorderLayout());
        //mxGraph graph = new mxGraph();
        this.spfHdr = spfhdr;
        if (spfHdr.getApproach() == 1) {
            bottomUp = true;
        }
        saveInfo = new JLabel();
        mxCompactLayout = new mxCompactTreeLayout(graph, false);
        mxCompactLayout.setMoveTree(true);
        setSavedHeading(false);
        // Make all vertices and edges uneditable
        graph.setCellsEditable(false);

        // Make all edges unbendable
        graph.setCellsBendable(false);
        //graph.setCellsMovable(false);
        //graph.setCellsResizable(false);
        //graph.setDropEnabled(false);

        loadData(false);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
                
         graphComponent.getViewport().setOpaque(true);
         graphComponent.getViewport().setBackground(Color.WHITE);
        //graphComponent.setConnectable(false);
        // graphComponent.setDragEnabled(false);
        add(createToolBar(), BorderLayout.NORTH);
        add(graphComponent, BorderLayout.CENTER);
        mouseClickListeners(graphComponent);

    }

    public void mouseClickListeners(mxGraphComponent graphComponent) {

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {

                mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());

                if (jgraphServices.getCellType(cell) == JGraphServices.NODE_EVENT || jgraphServices.getCellType(cell) == JGraphServices.NODE_BASIC) {

                    event.setEnabled(true);
                    basic.setEnabled(true);
                    edit.setEnabled(true);
                    remove.setEnabled(true);
                } else {
                    event.setEnabled(false);
                    basic.setEnabled(false);
                    edit.setEnabled(false);
                    remove.setEnabled(false);
                }

                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {

                    if (cell != null) {
                        //JOptionPane.showMessageDialog(null,"cell=" + graph.getLabel(cell));
                        editCell(cell);
                    }

                } else if (SwingUtilities.isRightMouseButton(e)) {
                    //JOptionPane.showMessageDialog(null,"Hello, Welcome to Javatpoint.");

                    String lablel = graph.getLabel(cell);

                    if (jgraphServices.getCellType(cell) == JGraphServices.NODE_EVENT || jgraphServices.getCellType(cell) == JGraphServices.NODE_BASIC) {
                        JPopupMenu menu = createPopupMenu(e.getPoint(), cell, Integer.parseInt(cell.getId()));  //lablel.substring(0, lablel.indexOf('-')
                        // Display PopupMenu
                        menu.show(graphComponent, e.getX(), e.getY());
                        //System.out.println("cell.getId() - "+cell.getId());
                    }

                }
            }
        });

    }

    public void loadData(boolean refresh) {

        ProgressBar progress = new ProgressBar();
        int count = 0;
        // faultdataDao.clearCache();
        //Stack<Faultdata> nodes = new Stack<Faultdata>();
        Queue<Faultdata> nodes = new LinkedList<>();
        //faultdataDao.clearCache(fdataList);
        progress.setTitle("Progressing...");
        fdataList = faultdataDao.getAllFaultdataBySpfHdr(spfHdr.getSpfhdrid());
        if (refresh) {
            //faultdataDao.refresh(fdataList);
        }
        progress.setTitle("Ordering...");
        progress.setBountry(0, fdataList.size() * 2);

        //fdataList= jgraphServices.getOrderedList2(fdataList,progress);
        Faultdata headNode = fdataList.get(0);

        count = fdataList.size();
        progress.setPercentage(count);

        try {

            getGraphModel().beginUpdate();
            graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
            graph.removeCells(graph.getChildEdges(graph.getDefaultParent()));

            mxCell root = (mxCell) graph.insertVertex(parent, headNode.getFaultdataId() + "", new FaulttreeNode(headNode), 20, 20, 100, 100, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + imageURL + "FTA_Node.png;" + "strokeColor=red;");

            /*for (Faultdata faultdata : headNode.getFaultdataCollection()) {
              nodes.add(faultdata);    
          }*/
 /* for( Faultdata ftdata :faultdataDao.getAllFaultdataBySpfHdr(spfHdr.getSpfhdrid(),headNode.getFaultdataId())){
                        nodes.add(ftdata);
            }*/
            final int parentId = headNode.getFaultdataId();

            fdataList.stream().filter(f -> ((f.getParent() != null) && (f.getParent().getFaultdataId() == parentId) && (f.getParent().getFaultdataId() != f.getFaultdataId()))).forEach((ftdata) -> {
                nodes.add(ftdata);
            });

            while (!nodes.isEmpty()) {
                Faultdata node = nodes.poll();
                if (node == null) {
                    continue;
                }
                System.out.println(node.getDescription());

                if (node.getNode().contentEquals("Event")) {
                    insertNodeVertex(new FaulttreeNode(node), getmxCellWithId(node.getParent().getFaultdataId()));
                } else {
                    insertBasicVertex(new FaulttreeNode(node), getmxCellWithId(node.getParent().getFaultdataId()));
                }

                if (node.getNode().contentEquals("Event") /*&& node.getFaultdataCollection() != null*/) {

                    /* for (Faultdata ftdata : node.getFaultdataCollection()) {
                        nodes.add(ftdata);
                    }*/
 /*for( Faultdata ftdata :faultdataDao.getAllFaultdataBySpfHdr(spfHdr.getSpfhdrid(),node.getFaultdataId())){
                        nodes.add(ftdata);
                    }*/
                    final int parentId2 = node.getFaultdataId();

                    fdataList.stream().filter(f -> ((f.getParent() != null) && (f.getParent().getFaultdataId() == parentId2) && (f.getParent().getFaultdataId() != f.getFaultdataId()))).forEach((ftdata) -> {
                        nodes.add(ftdata);
                    });

                }
                count++;
                progress.setPercentage(count);
            }
            //new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
            mxCompactLayout.execute(parent);

            //new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
            //new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());
        } finally {
            getGraphModel().endUpdate();
            progress.setPercentage(fdataList.size() * 2);
        }

        //insertNodeVertex(root.getFaultdataId(), root.getChildGate(), root.getName(), root.getDescription(), root.getFailurerate(), root.getProbability());
    }

    public void saveData() {
        ProgressBar progress = new ProgressBar();
        mxCell mxCellRef = null;
        FaulttreeNode nodeRes; 
        int cellType;
        int cellInsertId;
        int count = 0;
        Object[] childVertices = graph.getChildVertices(parent);
        Faultdata faultdata = null;

        progress.setTitle("Saving...");
        progress.setBountry(0, childVertices.length);

        try {
            for (Object node : childVertices) {
                mxCellRef = (mxCell) node;
                cellType = jgraphServices.getCellType(mxCellRef);

                // jgraphServices;   
                if (cellType == JGraphServices.OR || cellType == JGraphServices.AND) {

                } else {
                    nodeRes = jgraphServices.getCellValue(mxCellRef);
                    // System.out.println("xzcvfdbgbg "+nodeRes.getDescription()+" getParentId - "+nodeRes.getParentId());

                    if (nodeRes.getId() == -100) {
                        faultdata = jgraphServices.covertGRtoDB((FaulttreeNode) mxCellRef.getValue(), spfHdr, false);
                        cellInsertId = faultdataDao.Save(faultdata);
                        mxCellRef.setId(cellInsertId + "");
                        nodeRes.setId(cellInsertId);
                        nodeRes.setClacId(faultdata.getCalcComp().getCalcId());
                        getGraphModel().setValue(mxCellRef, nodeRes);
                        jgraphServices.setInsertParentId(getGraphModel(), mxCellRef, cellInsertId);

                        System.out.println("Node " + nodeRes.getDescription() + " getParentId - " + nodeRes.getParentId());
                    } else {

                        //System.out.println("mxCellRef "+mxCellRef+" "+mxCellRef.getId());
                        //System.out.println("mxCellRef.getValue() "+((FaulttreeNode)mxCellRef.getValue()).getDescription());
                        faultdataDao.Update(jgraphServices.covertGRtoDB((FaulttreeNode) mxCellRef.getValue(), spfHdr, true));
                    }

                }

                //System.out.println("mxCellRef "+mxCellRef );
                //System.out.println("mxCellRef.getValue() "+mxCellRef.getValue());
                count++;
                progress.setPercentage(count);

            }
        } finally {
            progress.setPercentage(childVertices.length);
        }

    }

    public void mergeData() {

        ProgressBar progress = new ProgressBar();
        progress.setTitle("Merging...");
        try {

            progress.setBountry(0, fdataList.size());
            //jgraphServices.getOrderedList(fdataList);
            //cell.setStyle(NAME);
            Calcfile ref;
            int count = 0;

            for (Faultdata faultdata : fdataList) {
                ref = faultdata.getCalcComp();

                if (faultdata.getNode().equals("Basic")) {

                    faultdata.setName(ref.getCompId());
                    faultdata.setDescription(ref.getComponents());
                    faultdata.setFailurerate(faultdata.getCategory().equals("P") ? 0 : ref.getFailureRate());
                    faultdata.setProbability(faultdata.getCategory().equals("P") ? ref.getProbability() : 0);
                    faultdataDao.Update(faultdata);

                }

                count++;
                progress.setPercentage(count);
            }

            loadData(false);

        } finally {
            progress.setPercentage(fdataList.size());
        }

    }

    public mxCell getmxCellWithId(int id) {
        mxCell mxCell = null;
        mxCell mxCellRef = null;

        for (Object node : graph.getChildVertices(parent)) {
            mxCellRef = (mxCell) node;

            if (mxCellRef.getId().contentEquals(id + "")) {
                mxCell = mxCellRef;
            }
            //System.out.println("ccccccccccccccccc "+mxCellRef.getId());
        }

        return mxCell;
    }

    public mxCell insertNodeVertex(FaulttreeNode fNode, mxCell parentCell) {
        mxCell gateVer;
        mxCell selVer = parentCell;
        FaulttreeNode fNodeParent = (FaulttreeNode) selVer.getValue();

        //System.out.println("aaaaaaaaaaaaaaa "+selVer.getEdgeCount());
        if (selVer.getEdgeCount() == 0) {
            gateVer = insertGateVertex(1, fNodeParent.getChildGate());
            //graph.insertEdge(parent, null, null, selVer , gateVer);
            insertEdge(selVer, gateVer);
        } else if ((selVer.getEdgeCount() == 1 && selVer.getEdgeAt(0).getTerminal(false) == selVer)) {
            gateVer = insertGateVertex(1, fNodeParent.getChildGate());
            //graph.insertEdge(parent, null, null, selVer , gateVer);
            insertEdge(selVer, gateVer);
        } else {
            gateVer = jgraphServices.getGateCellByParent(selVer);
        }

        // Construct Vertex with no Label
        mxCell vertex = (mxCell) graph.insertVertex(parent, fNode.getId() + "", fNode, 20, 20, 100, 100, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + imageURL + "FTA_Node.png");
        vertex.setId(fNode.getId() + "");
        //vertex.setAttribute("label", "MyLabel");
        //graph.updateCellSize(vertex, false);
        graph.isCellEditable(false);

        //graph.insertEdge(parent, null, null, gateVer , vertex);
        insertEdge(gateVer, vertex);
        //graph.isCellFoldable(vertex, true);
        return vertex;
    }

    public mxCell insertBasicVertex(FaulttreeNode fNode, mxCell parentCell) {

        mxCell gateVer;
        mxCell selVer = parentCell;
        FaulttreeNode fNodeParent = (FaulttreeNode) selVer.getValue();

        //System.out.println("aaaaaaaaaaaaaaa "+selVer.getEdgeCount());
        if (selVer.getEdgeCount() == 0) {
            gateVer = insertGateVertex(1, fNodeParent.getChildGate());
            //graph.insertEdge(parent, null, null, selVer , gateVer);
            insertEdge(selVer, gateVer);
        } else if ((selVer.getEdgeCount() == 1 && selVer.getEdgeAt(0).getTerminal(false) == selVer)) {
            gateVer = insertGateVertex(1, fNodeParent.getChildGate());
            //graph.insertEdge(parent, null, null, selVer , gateVer);
            insertEdge(selVer, gateVer);
        } else {
            gateVer = jgraphServices.getGateCellByParent(selVer);
        }

        // Construct Vertex with no Label
        mxCell vertex = (mxCell) graph.insertVertex(parent, fNode.getId() + "", fNode, 20, 20, 100, 100, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + imageURL + "FTA_Event_Basic_03.png" + ";verticalAlign=top;align=center");
        vertex.setId(fNode.getId() + "");
        //vertex.setValue(fNode);
        //graph.insertEdge(parent, null, null, gateVer , vertex);
        insertEdge(gateVer, vertex);
        //graph.isCellFoldable(vertex, true);
        return vertex;
    }

    public mxCell insertGateVertex(int cellId, String gate) {
        // Construct Vertex with no Label
        mxCell vertex;

        if (gate.contentEquals("OR")) {
            vertex = (mxCell) graph.insertVertex(parent, "OR", "OR", 20, 20, 45, 45, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + imageURL + "FTA_OR.png");
            vertex.setId("OR");
        } else {
            vertex = (mxCell) graph.insertVertex(parent, "AND", "AND", 20, 20, 45, 45, mxConstants.STYLE_SHAPE + "=" + mxConstants.SHAPE_IMAGE + ";" + mxConstants.STYLE_IMAGE + "=" + imageURL + "FTA_AND.png");
            vertex.setId("AND");
        }

        return vertex;
    }

    public void insertEdge(Object source, Object traget) {

        graph.insertEdge(parent, null, null, source, traget, FrameSettings.getArrowColor());
    }
    //
    // PopupMenu
    //

    public JPopupMenu createPopupMenu(final Point pt, final mxCell cell, int nodeId) {
        JPopupMenu menu = new JPopupMenu();
        if (cell != null) {

            if (((FaulttreeNode) cell.getValue()).getType().contentEquals("Event")) {
                // Insert
                menu.add(new AbstractAction("Insert Event") {
                    public void actionPerformed(ActionEvent ev) {
                        InsertEvent(cell);
                    }

                }
                );
                menu.addSeparator();
                //Basic
                menu.add(new AbstractAction("Insert Basic") {
                    public void actionPerformed(ActionEvent ev) {

                        InsertBasic(cell);

                    }
                }
                );
                menu.addSeparator();
                
                //CCF
                /*menu.add(new AbstractAction("Insert CCF") {
                    public void actionPerformed(ActionEvent ev) {

                        InsertCCF(cell);

                    }
                }
                );
                menu.addSeparator();*/
            }
            // Edit
            menu.add(new AbstractAction("Edit") {
                public void actionPerformed(ActionEvent e) {

                    editCell(cell);

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
                    calculateData(cell);
                }

            });
        }

        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Remove") {
                public void actionPerformed(ActionEvent e) {

                    deleteNode(cell);

                }

            });
        }
        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Save") {
                public void actionPerformed(ActionEvent e) {

                    SaveGraph();

                    // loadData(true);
                }

            });
        }
        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Merge Calcfile Data") {
                public void actionPerformed(ActionEvent e) {

                    MergeGraphwithCMM();

                }

            });
        }

        if (!graph.isSelectionEmpty()) {
            menu.addSeparator();
            menu.add(new AbstractAction("Refresh") {
                public void actionPerformed(ActionEvent e) {

                    refresh();
                   
                    /*for (FaulttreeNode faultdata : getAllVertixNodes(JGraphServices.NODE_BASIC)) {
                        System.out.println("FaulttreeNode - "+faultdata.getDescription());
                    }*/

                }

            });
        }

        return menu;
    }

    public void editCell(mxCell cell) {
        FaulttreeNode nodeRes;
        if (jgraphServices.getCellType(cell) == JGraphServices.NODE_EVENT) {

            nodeRes = graphCRUD.insertORUpdateEvent(cell, true, getJgraphDrawer());
            if (nodeRes != null) {
                getGraphModel().setValue(cell, nodeRes);
                setSavedHeading(true);
            }

        } else if (jgraphServices.getCellType(cell) == JGraphServices.NODE_BASIC) {
            nodeRes = graphCRUD.insertORUpdateNode(cell, true, getJgraphDrawer(), bottomUp);
            if (nodeRes != null) {
                getGraphModel().setValue(cell, nodeRes);
                setSavedHeading(true);
            }
        }

    }

    public mxIGraphModel getGraphModel() {
        return graph.getModel();
    }

    public String getImageURL() {

        return imageURL;
    }

    public JgraphDrawer getJgraphDrawer() {
        return this;
    }

    public List<mxCell> getAllCell(int id, int type) {
        List<mxCell> mxCells = new ArrayList<>();
        mxCell mxCellRef = null;
        int typeNode;

        for (Object node : graph.getChildVertices(parent)) {
            mxCellRef = (mxCell) node;
            typeNode = jgraphServices.getCellType(mxCellRef);

            if (typeNode == JGraphServices.NODE_EVENT && (type == typeNode || type == JGraphServices.ALL)) {
                mxCells.add(mxCellRef);
            } else if (typeNode == JGraphServices.NODE_BASIC && (type == typeNode || type == JGraphServices.ALL)) {
                mxCells.add(mxCellRef);
            }

        }

        return mxCells;
    }

    public List<Integer> getAddedList() {
        List<Integer> addedList = new ArrayList<Integer>();
        mxCell mxCellRef = null;
        FaulttreeNode value;
        int typeNode;

        for (Object node : graph.getChildVertices(parent)) {
            mxCellRef = (mxCell) node;

            typeNode = jgraphServices.getCellType(mxCellRef);

            if (typeNode == JGraphServices.NODE_BASIC) {
                value = jgraphServices.getCellValue(mxCellRef);
                addedList.add(value.getClacId());
            }

        }

        return addedList;
    }

    public Spfhdr getHeader() {
        return spfHdr;
    }

    /* public ProgressBar getProgressBar(){
    return progress;
    }*/
    public static void main(String[] args) {
        SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();

        JFrame frame = new JFrame();
        frame.add(new JgraphDrawer(spfhdrDao.findById(51)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

    public void InsertEvent(mxCell cell) {

        //mxCell parNode = (mxCell)graph.getSelectionCell();
        FaulttreeNode nodeRes = graphCRUD.insertORUpdateEvent(cell, false, getJgraphDrawer());

        if (nodeRes != null) {
            insertNodeVertex(nodeRes, cell);
            //mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            mxCompactLayout.execute(parent);
            setSavedHeading(true);
        }

    }

    public void InsertBasic(mxCell cell) {

        //mxCell parNode = (mxCell)graph.getSelectionCell();
        FaulttreeNode nodeRes = graphCRUD.insertORUpdateNode(cell, false, getJgraphDrawer(), bottomUp);

        if (nodeRes != null) {
            insertBasicVertex(nodeRes, cell);
            //mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            mxCompactLayout.execute(parent);
            setSavedHeading(true);
        }

        //mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
        //mxCompactLayout.execute(parent);
    }
    
    public void InsertCCF(mxCell cell) {

      Object[] options1 = {"Save", "Cancel"};
      FaulttreeNode nodeRes = null;
        
         InsertCCFBottomUp insertCCF = new InsertCCFBottomUp(getAllVertixNodes(JGraphServices.NODE_BASIC), new ArrayList<>());

          int result = JOptionPane.showOptionDialog(null, insertCCF, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

           if (result == JOptionPane.YES_OPTION) {
               nodeRes = new FaulttreeNode();
               nodeRes.setId(-111);
               nodeRes.setCompId("CCF");
               nodeRes.setDescription(insertCCF.getDescription());
               nodeRes.setType("Basic");
               nodeRes.setCategory("U");
               nodeRes.setFrequency(insertCCF.getCCFFailureRate());
               nodeRes.setProbability(0.0);
               nodeRes.setTime(24.0);
               nodeRes.setClacId(-1);
           }
           
           
        if (nodeRes != null) {
            insertBasicVertex(nodeRes, cell);
            //mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
            mxCompactLayout.execute(parent);
            setSavedHeading(true);
        }

        //mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
        //mxCompactLayout.execute(parent);
    }

    private void refresh() {
        new Thread() {
            public void run() {

                loadData(true);
            }
        }.start();

    }

    @Override
    public BufferedImage getPrintContantGraph() {
        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null,
                1, Color.WHITE, true, null);

        return image;
    }

    public JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Insert
        URL insertUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/InsertNode.png");
        ImageIcon insertIcon = ImageConversion.SetImageSize(insertUrl);
        event = new AbstractAction("", insertIcon) {
            public void actionPerformed(ActionEvent e) {

                Optional<mxCell> cell = getSelectedNode();

                if (cell.isPresent()) {
                    InsertEvent(cell.get());
                    setSavedHeading(true);
                    //System.out.println("Value found - " + cell.get());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to insert", "Insert Event", JOptionPane.INFORMATION_MESSAGE);
                }

                //dendrogramPaintTest.performAddOrEdit();
            }
        };
        event.setEnabled(false);
        toolbar.add(event).setToolTipText("Insert Event");

        // Toggle Connect Mode
        URL basicUrl = getClass().getClassLoader().getResource(
                "coachspecs/images/gray/FTA_Basic.png");
        ImageIcon basicIcon = ImageConversion.SetImageSize(basicUrl);
        basic = new AbstractAction("", basicIcon) {
            public void actionPerformed(ActionEvent e) {

                Optional<mxCell> cell = getSelectedNode();

                if (cell.isPresent()) {
                    InsertBasic(cell.get());
                    setSavedHeading(true);
                    //System.out.println("Value found - " + cell.get());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to insert", "Insert Basic", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        };
        basic.setEnabled(false);
        toolbar.add(basic).setToolTipText("Insert Basic");

        // Toggle Connect Mode
        URL connectUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/edit.png");
        ImageIcon connectIcon = ImageConversion.SetImageSize(connectUrl);
        edit = new AbstractAction("", connectIcon) {
            public void actionPerformed(ActionEvent e) {

                Optional<mxCell> cell = getSelectedNode();

                if (cell.isPresent()) {
                    editCell(cell.get());
                    setSavedHeading(true);
                    //System.out.println("Value found - " + cell.get());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to edit", "Edit Node", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
         edit.setEnabled(false);
         toolbar.add(edit).setToolTipText("Edit Selected Cell");
        // Remove
        URL removeUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/delete.png");
        ImageIcon removeIcon = ImageConversion.SetImageSize(removeUrl);
        remove = new AbstractAction("", removeIcon) {
            public void actionPerformed(ActionEvent e) {
    
                      Optional<mxCell> cell = getSelectedNode();
                       if (cell.isPresent()) {
                            deleteNode(cell.get());
                            setSavedHeading(true);
                            //System.out.println("Value found - " + cell.get());
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a node to delete", "Delete Node", JOptionPane.INFORMATION_MESSAGE);
                        }
                       
                 }
            
        };
        remove.setEnabled(false);
        toolbar.add(remove).setToolTipText("Delete");

        // To Front
        toolbar.addSeparator();
        URL toFrontUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/refresh.png");
        ImageIcon toFrontIcon = ImageConversion.SetImageSize(toFrontUrl);
        tofront = new AbstractAction("", toFrontIcon) {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        };
        toolbar.add(tofront).setToolTipText("Refresh");

        // To Back
        URL toBackUrl = getClass().getClassLoader().getResource(
                "coachspecs/images/gray/STEM_Icons_01_Systems.png");
        ImageIcon toBackIcon = ImageConversion.SetImageSize(toBackUrl);
        toback = new AbstractAction("", toBackIcon) {
            public void actionPerformed(ActionEvent e) {
                 MergeGraphwithCMM();
                 
            }
        };
        toolbar.add(toback).setToolTipText("Merge with CMM");

        // Zoom Std
        toolbar.addSeparator();
        URL zoomUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/calculate.png");
        ImageIcon zoomIcon = ImageConversion.SetImageSize(zoomUrl);
        toolbar.add(new AbstractAction("", zoomIcon) {
            public void actionPerformed(ActionEvent e) {
                     
                calculateData((mxCell)graph.getChildVertices(parent)[0]);
            }
        }).setToolTipText("Calculate");
        // Zoom In
        URL zoomInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomin.png");
        ImageIcon zoomInIcon = ImageConversion.SetImageSize(zoomInUrl);
        toolbar.add(new AbstractAction("", zoomInIcon) {
            public void actionPerformed(ActionEvent e) {
                 mxGraphView view = graph.getView();
                 view.setScale(2 * view.getScale());
            }
        }).setToolTipText("Zoom In");
        // Zoom Out
        URL zoomOutUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomout.png");
        ImageIcon zoomOutIcon = ImageConversion.SetImageSize(zoomOutUrl);
        toolbar.add(new AbstractAction("", zoomOutIcon) {
            public void actionPerformed(ActionEvent e) {
                mxGraphView view = graph.getView();
                 view.setScale(view.getScale() / 2);
            }
        }).setToolTipText("Zoom Out");

        URL saveInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/save.png");
        ImageIcon saveIcon = ImageConversion.SetImageSize(saveInUrl);
        toolbar.add(new AbstractAction("", saveIcon) {
            public void actionPerformed(ActionEvent e) {
                SaveGraph();

            }
        }).setToolTipText("Save All");

        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(saveInfo);

        return toolbar;
    }

    public void setSavedHeading(boolean edited) {

        if (edited) {
            saveInfo.setText("Please save the Document     ");
            saveInfo.setForeground(Color.red);
        } else {
            saveInfo.setText("Document Saved     ");
            saveInfo.setForeground(new Color(14, 102, 85));
        }
    }

    private void SaveGraph() {
        new Thread() {
            public void run() {

                saveData();
                setSavedHeading(false);
            }
        }.start();

    }

    public void deleteNode(mxCell cell) {
        FaulttreeNode node = (FaulttreeNode) cell.getValue();

        //int res =NumberConversion.NVL(, -1);
        int res = node.getId();

        if (node.getCategory().equals("A")) {
            JOptionPane.showMessageDialog(null, "Top Node can't be deleted");
        } else if (jgraphServices.getChildByParent(cell).size() > 0) {
            JOptionPane.showMessageDialog(null, "Child node found. Please deleted child node first \n"
                    + "Try Again !!");
        } else {

               if (BusinessException.showBusinessConformation("Do you want to delete this Node? ",true)== JOptionPane.YES_OPTION){
                    try {

                        if (res != -100) {
                            System.out.println("cell.getId() - " + cell.getId() + "res " + res);
                            faultdataDao.remove(res);
                            graph.removeCells(new Object[]{cell}, true);
                            mxCompactLayout.execute(parent);
                        } else {
                            graph.removeCells(new Object[]{cell}, true);
                            mxCompactLayout.execute(parent);
                        }
                        setSavedHeading(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
            
               }

        }
    }

    private void calculateData(mxCell cell) {
        /* for (mxCell cell : jgraphServices.getChildByParent(cell)) {
                        System.out.println("cell "+cell.getId());
                    }*/
       /* FaulttreeNode node = jgraphServices.getCellValue(cell);
                     node.setFrequency(1.067565);
                     
                     graph.getModel().setValue(cell, node);*/
        System.out.println("Value - " + jgraphServices.calculate(cell, getGraphModel()));
        //graph.repaint();

        System.out.println("Cell Value - " + jgraphServices.getCellValue(cell).getFrequency());
    }

    public void MergeGraphwithCMM() {
        new Thread() {
            public void run() {

                mergeData();
                setSavedHeading(true);
            }
        }.start();
    }

    public Optional<mxCell> getSelectedNode() {
        mxCell cell = (mxCell) graph.getSelectionCell();
        Optional<mxCell> userOptional = Optional.ofNullable(cell);

        return userOptional;
    }
    
    public List<FaulttreeNode> getAllVertixNodes(int type){
    
    
     return Arrays.asList(graph.getChildVertices(parent)).stream()
             .filter(c -> c instanceof mxCell).map(cell -> (mxCell) cell).filter(c -> (jgraphServices.getCellType(c)== type))
             .map(cell -> jgraphServices.getCellValue(cell)).collect(Collectors.toList());
       
       
    }
}
