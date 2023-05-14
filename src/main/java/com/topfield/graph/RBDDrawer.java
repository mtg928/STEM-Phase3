package com.topfield.graph;

import com.jgraph.example.mycellview.GPCellViewFactory;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import com.mxgraph.view.mxGraph;
import com.topfield.calculators.calcfile.CalcCommonService;
import com.topfield.controller.BusinessException;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.GraphedgeDao;
import com.topfield.dao.GraphvertexDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.GraphedgeDaoImpl;
import com.topfield.daoImpl.GraphvertexDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.daoImpl.SpghdrDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Fmeca;
import com.topfield.modal.Graphedge;
import com.topfield.modal.Graphvertex;
import com.topfield.modal.Spfhdr;
import com.topfield.singleton.NumberMultiple;
import com.topfield.utilities.ImageConversion;
import com.topfield.view.popup.ListSelectionPopup;
import com.topfield.view.popup.RBDSettingsPopup;
import com.topfiled.interfaces.MyPrintable;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Edge;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.Port;

public class RBDDrawer extends JPanel implements GraphSelectionListener, KeyListener, MyPrintable {

    private SpfhdrDao spghdrDao = new SpfhdrDaoImpl();
    private GraphedgeDao graphedgeDao = new GraphedgeDaoImpl();
    private GraphvertexDao graphvertexDao = new GraphvertexDaoImpl();
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private boolean bottomUp = false;
    private Spfhdr vtxspfhdr = spghdrDao.findById(1);

    private RBDSettings rBDSettings = new RBDSettings();
    private List<Map> graphConstantsMap;
    private JLabel saveInfo;

    protected JGraph graph;  // JGraph instance
    protected GraphUndoManager undoManager;  // Undo Manager
    protected Action undo, redo, remove, group, ungroup, tofront, toback, cut, copy, paste, save; // Actions which Change State
    protected int cellCount = 0;  // cell count that gets put in cell label
    protected StatusBarGraphListener statusBar;  // Status Bar

    //
    // Editor Panel
    //
    // Construct an Editor Panel
    public RBDDrawer(Spfhdr vtxspfhdr) {

        this.vtxspfhdr = vtxspfhdr;
        this.graphConstantsMap = rBDSettings.RBDJsontoMap(vtxspfhdr.getProsettings());
        saveInfo = new JLabel();
        if (vtxspfhdr.getApproach() == 1) {
            bottomUp = true;
        }
        // Construct the Graph
        graph = createGraph();
      //performJGraphTreeLayout2();
        // Use a Custom Marquee Handler
        graph.setMarqueeHandler(createMarqueeHandler());
        // Construct Command History
        //
        // Create a GraphUndoManager which also Updates the ToolBar
        
        graph.setGridColor(Color.lightGray);
        graph.setGridMode(JGraph.LINE_GRID_MODE);
        graph.setGridSize(30);
        graph.setGridEnabled(true);
        graph.setGridVisible(false);
        graph.setHandleColor(Color.red);
        graph.setSelectionEnabled(true);
        
        undoManager = new GraphUndoManager() {
            // Override Superclass
            public void undoableEditHappened(UndoableEditEvent e) {
                // First Invoke Superclass
                super.undoableEditHappened(e);
                // Then Update Undo/Redo Buttons
                updateHistoryButtons();
            }
        };
        
        populateContentPane();

        setSavedHeading(false);
        installListeners(graph);
        LoadRBD();
    }

    // Hook for subclassers
    protected void populateContentPane() {
        /* // Use Border Layout
        getContentPane().setLayout(new BorderLayout());
        // Add a ToolBar
        getContentPane().add(createToolBar(), BorderLayout.NORTH);
        // Add the Graph as Center Component
        getContentPane().add(new JScrollPane(graph), BorderLayout.CENTER);
        statusBar = createStatusBar();
        getContentPane().add(statusBar, BorderLayout.SOUTH);*/

        // Use Border Layout
        setLayout(new BorderLayout());
        // Add a ToolBar
        add(createToolBar(), BorderLayout.NORTH);
        // Add the Graph as Center Component
        add(new JScrollPane(graph), BorderLayout.CENTER);
        statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    // Hook for subclassers
    protected JGraph createGraph() {
        JGraph graph = new RBDGraph(new MyModel());

        graph.getGraphLayoutCache().setFactory(new GPCellViewFactory());
         
        /*graph.getGraphLayoutCache().setFactory(new DefaultCellViewFactory() {

            // Override Superclass Method to Return Custom EdgeView
            protected EdgeView createEdgeView(Object cell) {

                // Return Custom EdgeView
                return new EdgeView(cell) {

                    /**
                     * Returns a cell handle for the view.
         */
 /*  public CellHandle getHandle(GraphContext context) {
                        return new MyEdgeHandle(this, context);
                    }

                };
            }
        });*/
        return graph;
    }

    // Hook for subclassers
    protected void installListeners(JGraph graph) {
        // Add Listeners to Graph
        //
        // Register UndoManager with the Model
        graph.getModel().addUndoableEditListener(undoManager);
        // Update ToolBar based on Selection Changes
        graph.getSelectionModel().addGraphSelectionListener(this);
        // Listen for Delete Keystroke when the Graph has Focus
        graph.addKeyListener(this);
        graph.getModel().addGraphModelListener(statusBar);
    }

    // Hook for subclassers
    protected void uninstallListeners(JGraph graph) {
        graph.getModel().removeUndoableEditListener(undoManager);
        graph.getSelectionModel().removeGraphSelectionListener(this);
        graph.removeKeyListener(this);
        graph.getModel().removeGraphModelListener(statusBar);
    }

    // Hook for subclassers
    protected BasicMarqueeHandler createMarqueeHandler() {
        return new MyMarqueeHandler(graph, this);
    }

    // Insert a new Vertex at point
    /*public void insert(Point2D point) {
        // Construct Vertex with no Label
        DefaultGraphCell vertex = createDefaultGraphCell();
        // Create a Map that holds the attributes for the Vertex
        //vertex.getAttributes().applyMap(createCellAttributes(point));
        // Insert the Vertex (including child port and attributes)
        graph.getGraphLayoutCache().insert(vertex);
    }*/
    // Insert a new Vertex at point
    public DefaultGraphCell insertVertex(double x, double y, int cellId, int calcId, String id, String description) {
        // Construct Vertex with no Label
        //DefaultGraphCell vertex = createDefaultGraphCell(NumberMultiple.findclosedNumber(x), NumberMultiple.findclosedNumber(y), cellId, calcId, id, description);
        DefaultGraphCell vertex = createDefaultGraphCell(x, y, cellId, calcId, id, description);
        // Create a Map that holds the attributes for the Vertex
        //vertex.getAttributes().applyMap(createCellAttributes(point));
        // Insert the Vertex (including child port and attributes)
        graph.getGraphLayoutCache().insert(vertex);
        return vertex;
    }

    // Insert a new VertexText at point
    public void insertText(double x, double y, double w, double h, int cellId, String description) {
        // Construct Vertex with no Label
        DefaultGraphCell vertex = createDefaultGraphText(x, y, w, h, cellId, description);
        // Create a Map that holds the attributes for the Vertex
        //vertex.getAttributes().applyMap(createCellAttributes(point));
        // Insert the Vertex (including child port and attributes)
        graph.getGraphLayoutCache().insert(vertex);
    }

    // Insert a new VertexText at point
    public void insertVtxGroup(double x, double y, double w, double h, int cellId, String description) {
        // Construct Vertex with no Label
        DefaultGraphCell vertex = createDefaultGraphVtxGroup(x, y, w, h, cellId, description);

        // Create a Map that holds the attributes for the Vertex
        //vertex.getAttributes().applyMap(createCellAttributes(point));
        // Insert the Vertex (including child port and attributes)
        graph.getGraphLayoutCache().insert(vertex);
        graph.getGraphLayoutCache().toBack(new Object[]{vertex});
    }

    // Insert a new VertexText at point
    public void insertInterConn(double x, double y, int cellId) {
        // Construct Vertex with no Label
        DefaultGraphCell vertex = createDefaultGraphInterConn(x, y, cellId);

        // Create a Map that holds the attributes for the Vertex
        //vertex.getAttributes().applyMap(createCellAttributes(point));
        // Insert the Vertex (including child port and attributes)
        graph.getGraphLayoutCache().insert(vertex);
    }

    // Hook for subclassers
    /*  public Map createCellAttributes(Point2D point) {
        Map map = new Hashtable();
        // Snap the Point to the Grid
        if (graph != null) {
            point = graph.snap((Point2D) point.clone());
        } else {
            point = (Point2D) point.clone();
        }
        
        //Rectangle2D newBounds = new Rectangle2D.Double(point.getX()+20,point.getY()+20, 500.0, 500.0) ;    //new Rectangle2D.Double((point.getX() - 9) / 10,point.getY() - 9) / 10, 18, 18)
         //new Rectangle2D.Double(32, 64, 32, 32); 
        Rectangle2D rectangle1 = new Rectangle2D.Double(32, 64, 50, 320); 
        // Add a Bounds Attribute to the Map
        GraphConstants.setBounds(map, rectangle1);  //new Rectangle2D.Double(point.getX(),point.getY(), 0, 0)
        // Make sure the cell is resized on insert
        GraphConstants.setResize(map, true);
        // Add a nice looking gradient background
        GraphConstants.setGradientColor(map, Color.BLUE);
        // Add a Border Color Attribute to the Map
        GraphConstants.setBorderColor(map, Color.black);
        // Add a White Background
        GraphConstants.setBackground(map, Color.white);
        // Make Vertex Opaque
        GraphConstants.setOpaque(map, true);

        return map;
    }*/
    public static DefaultGraphCell createVertex(double x,
            double y, double w, double h, Color bg, boolean raised, DefaultGraphCell cell, String viewClass) {

        // set the view class (indirection for the renderer and the editor)
        if (viewClass != null) {
            GPCellViewFactory.setViewClass(cell.getAttributes(), viewClass);
        }

        // Set bounds 
        GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
                x, y, w, h));

        // Set fill color
        if (bg != null) {
            GraphConstants.setForeground(cell.getAttributes(), new Color(51, 119, 51));
            GraphConstants.setGradientColor(cell.getAttributes(), bg);
            GraphConstants.setOpaque(cell.getAttributes(), true);
            GraphConstants.setVerticalAlignment(cell.getAttributes(), JLabel.TOP);
        }

        // Set raised border
        if (raised) {
            GraphConstants.setBorder(cell.getAttributes(), BorderFactory
                    .createRaisedBevelBorder());
        } else // Set black border
        {
            GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
        }

        // Add a Floating Port
        cell.addPort();

        return cell;
    }

    // Hook for subclassers
    /* protected DefaultGraphCell createDefaultGraphCell() {  //"<html> <h4> GEN </h4> <p>This <br/> <br/> heading</p> </html>"
        /*DefaultGraphCell cell = new DefaultGraphCell("Cell  "
                + new Integer(cellCount++));
        // Add one Floating Port
        cell.addPort();
        return cell;*/
 /* return createVertex(20, 20, 120, 140, Color.BLUE, false, new RBDCell("Cell  "+ new Integer(cellCount++),"Description",RBDCell.RBD_VERTEX), "com.jgraph.example.mycellview.JGraphRBDView");
    }*/
    protected DefaultGraphCell createDefaultGraphCell(double x, double y, int cellId, int calcId, String id, String description) {  //"<html> <h4> GEN </h4> <p>This <br/> <br/> heading</p> </html>"

        Map attributesMap = graphConstantsMap.get(0);
        RBDCell cell = new RBDCell(cellId, id, description, RBDCell.RBD_VERTEX);
        cell.setCalcId(calcId);

        // set the view class (indirection for the renderer and the editor)
        GPCellViewFactory.setViewClass(attributesMap, "com.jgraph.example.mycellview.JGraphRBDView");

        // Set bounds 
        GraphConstants.setBounds(attributesMap, new Rectangle2D.Double(x, y, 120, 120));
        GraphConstants.setOpaque(attributesMap, true);
        GraphConstants.setVerticalAlignment(attributesMap, JLabel.TOP);
        GraphConstants.setSizeable(attributesMap, false);
        GraphConstants.setEditable(attributesMap,bottomUp);
        
        // print new mapping using forEcah() 
        /* attributesMap.forEach( 
            (k, v) -> System.out.println("Key : " + k + ", Value : " + v)); */

        cell.getAttributes().applyMap(attributesMap);

        // Add a Floating Port
        cell.addPort();

        return cell;
    }

    // Hook for subclassers
    protected DefaultGraphCell createDefaultGraphText(double x, double y, double w, double h, int cellId, String description) {  //"<html> <h4> GEN </h4> <p>This <br/> <br/> heading</p> </html>"
        Map attributesMap = graphConstantsMap.get(3);
        DefaultGraphCell cell = new RBDCell(cellId, description, description, RBDCell.CAPTION_VERTEX);
        GraphConstants.setBounds(attributesMap, new Rectangle2D.Double(x, y, w, h));

        cell.getAttributes().applyMap(attributesMap);
        // Add one Floating Port
        //cell.addPort();
        return cell;
    }

    protected DefaultGraphCell createDefaultGraphVtxGroup(double x, double y, double w, double h, int cellId, String description) {
        Map attributesMap = graphConstantsMap.get(1);
        DefaultGraphCell cell = new RBDCell(cellId, description, description, RBDCell.VERTEX_GROUP);

        GraphConstants.setBounds(attributesMap, new Rectangle2D.Double(x, y, w, h));
        GraphConstants.setBorder(attributesMap, BorderFactory.createDashedBorder(GraphConstants.getBorderColor(attributesMap)));
        GraphConstants.setVerticalAlignment(attributesMap, SwingConstants.BOTTOM);
        GraphConstants.setHorizontalAlignment(attributesMap, SwingConstants.CENTER);

        cell.getAttributes().applyMap(attributesMap);
        // Add one Floating Port
        //cell.addPort();
        return cell;
    }

    protected DefaultGraphCell createDefaultGraphInterConn(double x, double y, int cellId) {
        Map attributesMap = graphConstantsMap.get(4);
        DefaultGraphCell cell = new RBDCell(cellId, "----", "", RBDCell.CON_VERTEX);

        GraphConstants.setBounds(attributesMap, new Rectangle2D.Double(x, y, 10, 10));
        GraphConstants.setVerticalAlignment(attributesMap, SwingConstants.BOTTOM);
        GraphConstants.setHorizontalAlignment(attributesMap, SwingConstants.CENTER);
        GraphConstants.setEditable(attributesMap, false);
        //GraphConstants.setResize(attributesMap, true);
        //GraphConstants.setAutoSize(attributesMap, false);
        GraphConstants.setSizeable(attributesMap, false);

        cell.getAttributes().applyMap(attributesMap);

        // Add one Floating Port
        cell.addPort();
        return cell;
    }

    // Insert a new Edge between source and target
    public void connect(Port source, Port target) {
        // Construct Edge with no label
        DefaultEdge edge = createDefaultEdge();
        if (graph.getModel().acceptsSource(edge, source)
                && graph.getModel().acceptsTarget(edge, target)) {
            // Create a Map thath holds the attributes for the edge
            edge.getAttributes().applyMap(createEdgeAttributes());
            // Insert the Edge and its Attributes
            graph.getGraphLayoutCache().insertEdge(edge, source, target);
        }
    }

    // Hook for subclassers
    protected DefaultEdge createDefaultEdge() {
        return new DefaultEdge();
    }

    // Hook for subclassers
    public Map createEdgeAttributes() {
        //Map map = new Hashtable();
        Map map = graphConstantsMap.get(2);

        //GraphConstants.setLineStyle(map, GraphConstants.STYLE_SPLINE);
        GraphConstants.setLineStyle(map, GraphConstants.STYLE_ORTHOGONAL);
        GraphConstants.setRouting(map, GraphConstants.ROUTING_SIMPLE);
        // GraphConstants.
        GraphConstants.setEndFill(map, true);
        // Add a label along edge attribute
        GraphConstants.setLabelAlongEdge(map, true);

        return map;
    }

    // Create a Group that Contains the Cells
    public void group(Object[] cells) {
        // Order Cells by Model Layering
        cells = graph.order(cells);
        // If Any Cells in View
        if (cells != null && cells.length > 0) {
            DefaultGraphCell group = createGroupCell();
            // Insert into model
            graph.getGraphLayoutCache().insertGroup(group, cells);
        }
    }

    // Hook for subclassers
    protected DefaultGraphCell createGroupCell() {
        return new DefaultGraphCell();
    }

    // Returns the total number of cells in a graph
    protected int getCellCount(JGraph graph) {
        Object[] cells = graph.getDescendants(graph.getRoots());
        return cells.length;
    }

    // Ungroup the Groups in Cells and Select the Children
    public void ungroup(Object[] cells) {
        graph.getGraphLayoutCache().ungroup(cells);
    }

    // Determines if a Cell is a Group
    public boolean isGroup(Object cell) {
        // Map the Cell to its View
        CellView view = graph.getGraphLayoutCache().getMapping(cell, false);
        if (view != null) {
            return !view.isLeaf();
        }
        return false;
    }

    // Brings the Specified Cells to Front
    public void toFront(Object[] c) {
        graph.getGraphLayoutCache().toFront(c);
    }

    // Sends the Specified Cells to Back
    public void toBack(Object[] c) {
        graph.getGraphLayoutCache().toBack(c);
    }

    // Undo the last Change to the Model or the View
    public void undo() {
        try {
            undoManager.undo(graph.getGraphLayoutCache());
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            updateHistoryButtons();
        }
    }

    // Redo the last Change to the Model or the View
    public void redo() {
        try {
            undoManager.redo(graph.getGraphLayoutCache());
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            updateHistoryButtons();
        }
    }

    // Update Undo/Redo Button State based on Undo Manager
    protected void updateHistoryButtons() {
        // The View Argument Defines the Context
        undo.setEnabled(undoManager.canUndo(graph.getGraphLayoutCache()));
        redo.setEnabled(undoManager.canRedo(graph.getGraphLayoutCache()));
    }

    //
    // Listeners
    //
    // From GraphSelectionListener Interface
    public void valueChanged(GraphSelectionEvent e) {
        // Group Button only Enabled if more than One Cell Selected
        group.setEnabled(graph.getSelectionCount() > 1);
        // Update Button States based on Current Selection
        boolean enabled = !graph.isSelectionEmpty();
        remove.setEnabled(enabled);
        ungroup.setEnabled(enabled);
        tofront.setEnabled(enabled);
        toback.setEnabled(enabled);
        copy.setEnabled(enabled);
        cut.setEnabled(enabled);
    }

    //
    // KeyListener for Delete KeyStroke
    //
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        // Listen for Delete Key Press
        if (e.getKeyCode() == KeyEvent.VK_DELETE) // Execute Remove Action on Delete Key Press
        {
            remove.actionPerformed(null);
        }
    }

    @Override
    public BufferedImage getPrintContantGraph() {
        BufferedImage imagebuf = null;

        try {
            imagebuf = new Robot().createScreenCapture(getGraph().bounds());
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Graphics2D graphics2D = imagebuf.createGraphics();
        getGraph().paint(graphics2D);
        return imagebuf;
    }

    //
    // Custom Edge Handle
    //
    // Defines a EdgeHandle that uses the Shift-Button (Instead of the Right
    // Mouse Button, which is Default) to add/remove point to/from an edge.
    public static class MyEdgeHandle extends EdgeView.EdgeHandle {

        /**
         * @param edge
         * @param ctx
         */
        public MyEdgeHandle(EdgeView edge, GraphContext ctx) {
            super(edge, ctx);
        }

        // Override Superclass Method
        public boolean isAddPointEvent(MouseEvent event) {
            // Points are Added using Shift-Click
            return event.isShiftDown();
        }

        // Override Superclass Method
        public boolean isRemovePointEvent(MouseEvent event) {
            // Points are Removed using Shift-Click
            return event.isShiftDown();
        }

    }

    //
    // Custom Model
    //
    // A Custom Model that does not allow Self-References
    public static class MyModel extends DefaultGraphModel {
        // Override Superclass Method

        public boolean acceptsSource(Object edge, Object port) {
            // Source only Valid if not Equal Target
            return (((Edge) edge).getTarget() != port);
        }

        // Override Superclass Method
        public boolean acceptsTarget(Object edge, Object port) {
            // Target only Valid if not Equal Source
            return (((Edge) edge).getSource() != port);
        }
    }

    //
    //
    //
    //
    // PopupMenu and ToolBar
    //
    //
    //
    //
    //
    // PopupMenu
    //
    public JPopupMenu createPopupMenu(final Point pt, final Object cell) {
        JPopupMenu menu = new JPopupMenu();
        /*if (cell != null && bottomUp) {
            // Edit
            menu.add(new AbstractAction("Edit") {
                public void actionPerformed(ActionEvent e) {
                    graph.startEditingAtCell(cell);
                }
            });
        }*/
        // Remove
        if (!graph.isSelectionEmpty()) {
           // menu.addSeparator();
            menu.add(new AbstractAction("Remove") {
                public void actionPerformed(ActionEvent e) {
                    
                    
                    
                      remove.actionPerformed(e);
                       /* RBDCell deletedCell = (RBDCell)cell;
                        if (deletedCell.getCalcId() >0) {
                            //CalcCommonService.removeCalcFile(deletedCell.getCalcId());
                        }
                        
                       setSavedHeading(true);*/
                       
                      
                }
            });
        }
        menu.addSeparator();
        // Insert
        menu.add(new AbstractAction("Insert") {
            public void actionPerformed(ActionEvent ev) {
                //insert(pt);
                insertNode(pt.x, pt.y);
                
            }
        });

       // menu.addSeparator();
        // Insert
        /* menu.add(new AbstractAction("Merge Calcfile Data") {
            public void actionPerformed(ActionEvent ev) {

                 margeData(vtxspfhdr.getSpfhdrid());
                 LoadRBD();
            }
        });*/
        return menu;
    }

    public void LoadRBD() {

        Graphvertex source = null;
        Graphvertex target = null;
        Calcfile calCom = null;

        for (Graphvertex graphvtx : graphvertexDao.getAllVertexBySpfHdr(vtxspfhdr.getSpfhdrid())) {
            if (graphvtx.getVertextype().contentEquals(RBDCell.RBD_VERTEX)) {
                //calCom = calcfileDao.findById(graphvtx.getCalccomp());
                calCom = graphvtx.getCalccomp();
                //insertVertex(graphvtx.getPointx(), graphvtx.getPointy(),graphvtx.getVertexid(),graphvtx.getCalccomp(),graphvtx.getVertexHeading(), graphvtx.getDescription());
                insertVertex(graphvtx.getPointx(), graphvtx.getPointy(), graphvtx.getVertexid(), calCom.getCalcId(), calCom.getCompId(), calCom.getComponents());
            } else if (graphvtx.getVertextype().contentEquals(RBDCell.CAPTION_VERTEX)) {
                insertText(graphvtx.getPointx(), graphvtx.getPointy(), graphvtx.getWeight(), graphvtx.getHeight(), graphvtx.getVertexid(), graphvtx.getVertexHeading());
            } else if (graphvtx.getVertextype().contentEquals(RBDCell.VERTEX_GROUP)) {
                insertVtxGroup(graphvtx.getPointx(), graphvtx.getPointy(), graphvtx.getWeight(), graphvtx.getHeight(), graphvtx.getVertexid(), graphvtx.getVertexHeading());
            } else if (graphvtx.getVertextype().contentEquals(RBDCell.CON_VERTEX)) {
                insertInterConn(graphvtx.getPointx(), graphvtx.getPointy(), graphvtx.getVertexid());
            }

        }

        for (Graphedge graphedge : graphedgeDao.getAllGraphedgeBySpfHdr(vtxspfhdr.getSpfhdrid())) {
            source = graphedge.getSourcevtx();
            target = graphedge.getTargetvtx();
            connect((Port) graph.getPortViewAt(source.getPointx(), source.getPointy()).getCell(), (Port) graph.getPortViewAt(target.getPointx(), target.getPointy()).getCell());
        }

    }

    public void SaveRBD() {
        Object obj = null;
        RBDCell refcell = null;
        Rectangle2D refcellBounds = null;
        DefaultEdge refEdge = null;

        RBDCell refVertix = null;
        RBDCell refVertixGroup = null;
        RBDCell refText = null;
        RBDCell refConnector = null;

        System.out.println("GEN KAN " + graph.getModel().getRootCount());

        graphedgeDao.deleteAllGraphedgeBySpfHdr(vtxspfhdr.getSpfhdrid());
        graphvertexDao.deleteAllVertexBySpfHdr(vtxspfhdr.getSpfhdrid());

        for (int i = 0; i < graph.getModel().getRootCount(); i++) {
            //System.out.println("");
            //System.out.println(i+""+graph.getModel().getRootAt(i).getClass());

            obj = graph.getModel().getRootAt(i);

            if (obj instanceof RBDCell) {
                refcell = (RBDCell) obj;

                System.out.println("refcell id - " + refcell.getId() + " Desc - " + refcell.getDescription());

                refcellBounds = GraphConstants.getBounds(refcell.getAttributes());

                System.out.println("X -" + refcellBounds.getX() + " Y -" + refcellBounds.getY() + " Width - " + refcellBounds.getWidth() + " Height - " + refcellBounds.getHeight());

                refcell.setCellId(graphvertexDao.Save(getGraphvertex(refcell, refcellBounds)));

                //graphvertexDao.Save(getGraphvertex(refcell, refcellBounds));
                if (refcell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX)) {
                    refVertix = refcell;
                } else if (refcell.getVertexStyle().contentEquals(RBDCell.VERTEX_GROUP)) {
                    refVertixGroup = refcell;
                } else if (refcell.getVertexStyle().contentEquals(RBDCell.CAPTION_VERTEX)) {
                    refText = refcell;
                } else if (refcell.getVertexStyle().contentEquals(RBDCell.CON_VERTEX)) {
                    refConnector = refcell;
                }

                /*  switch (refcell.getVertexStyle()) {
                    case RBDCell.RBD_VERTEX:
                        refVertix =refcell;
                    case RBDCell.VERTEX_GROUP:
                        refVertixGroup =refcell;
                    case RBDCell.CAPTION_VERTEX:
                        refText =refcell;
                    case RBDCell.CON_VERTEX:
                       refConnector =refcell;
                }*/
            } else if (obj instanceof DefaultEdge) {
                refEdge = (DefaultEdge) obj;

                System.out.println("refEdge Source - " + ((RBDCell) ((DefaultPort) refEdge.getSource()).getParent()).getId() + " Target - " + ((RBDCell) ((DefaultPort) refEdge.getTarget()).getParent()).getId());
                //((RBDCell)((DefaultPort)refEdge.getSource()).getC).getId()

                graphedgeDao.Save(getGraphedge(graphvertexDao.findById(((RBDCell) ((DefaultPort) refEdge.getSource()).getParent()).getCellId()), graphvertexDao.findById(((RBDCell) ((DefaultPort) refEdge.getTarget()).getParent()).getCellId())));

                //  graphedgeDao.Save(getGraphedgeCalc(calcfileDao.findById(((RBDCell)((DefaultPort)refEdge.getSource()).getParent()).getCellId()), calcfileDao.findById(((RBDCell)((DefaultPort)refEdge.getTarget()).getParent()).getCellId())));
            }

        }

        vtxspfhdr.setProsettings(rBDSettings.RBDMaptoJson(refVertix, refVertixGroup, refEdge, refText, refConnector));
        spghdrDao.update(vtxspfhdr);
        
        setSavedHeading(false);

    }

    public boolean checkExist(int cellID) {
        boolean res = false;
        Object obj = null;
        RBDCell refcell = null;

        for (int i = 0; i < graph.getModel().getRootCount(); i++) {
            obj = graph.getModel().getRootAt(i);

            if (obj instanceof RBDCell) {
                refcell = (RBDCell) obj;

                if (refcell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX) && refcell.getCellId() == cellID) {
                    res = true;
                }

            }

        }

        return res;
    }

    public List<Integer> checkExistList() {
        List<Integer> res = new ArrayList<Integer>();
        Object obj = null;
        RBDCell refcell = null;

        for (int i = 0; i < graph.getModel().getRootCount(); i++) {
            obj = graph.getModel().getRootAt(i);

            if (obj instanceof RBDCell) {
                refcell = (RBDCell) obj;

                if (refcell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX)) {
                    res.add(refcell.getCalcId());

                    System.out.println(refcell.getDescription() + " - " + refcell.getCalcId());
                }

            }

        }

        return res;
    }

    public Graphvertex getGraphvertex(RBDCell rBDCell, Rectangle2D bounds) {
       int calcId;
       
        if (vtxspfhdr.getApproach()==1 && rBDCell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX)) {
            
          calcId = CalcCommonService.createCalcFile(rBDCell.getCalcId(),rBDCell.getId(),rBDCell.getDescription(),
                     0.0,0.0,vtxspfhdr.getSpfid(),rBDCell.getCalcId()>0).getCalcId();
        }else{
           calcId = rBDCell.getCalcId();
        }

        Graphvertex graphvertex = new Graphvertex();
        graphvertex.setVertextype(rBDCell.getVertexStyle());
        graphvertex.setVertexHeading(rBDCell.getId());
        graphvertex.setDescription(rBDCell.getDescription());
        graphvertex.setPointx(bounds.getX());
        graphvertex.setPointy(bounds.getY());
        graphvertex.setWeight(bounds.getWidth());
        graphvertex.setHeight(bounds.getHeight());
        graphvertex.setVtxspfhdr(vtxspfhdr);
        graphvertex.setCalccomp(calcfileDao.findById(rBDCell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX) ? calcId : 0));

        return graphvertex;
    }

    public Graphedge getGraphedge(Graphvertex sourcevtx, Graphvertex targetvtx) {
        Graphedge graphedge = new Graphedge();
        graphedge.setEdgetype("RBD");
        graphedge.setSourcelabel("Non");
        graphedge.setSourcevtx(sourcevtx);
        graphedge.setTargetvtx(targetvtx);
        graphedge.setVtxspfhdr(vtxspfhdr);
        return graphedge;
    }

    /* public Graphedge getGraphedgeCalc(Calcfile sourcevtx, Calcfile targetvtx){
     Graphedge graphedge = new Graphedge();
     graphedge.setEdgetype("RBD");
     graphedge.setSourcelabel("Non");
     graphedge.setSourcevtx(sourcevtx.getGraphvertexCollection().iterator().next());
     graphedge.setTargetvtx(targetvtx.getGraphvertexCollection().iterator().next());
     graphedge.setVtxspfhdr(vtxspfhdr);
     return graphedge;
    }*/
    //
    // ToolBar
    //
    public JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Insert
        URL insertUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/insert.png");
        ImageIcon insertIcon = ImageConversion.SetImageSize(insertUrl);
        toolbar.add(new AbstractAction("", insertIcon) {
            public void actionPerformed(ActionEvent e) {
                //insert(new Point(10, 10));
                //insertVertex(20, 20,-1,"Cell  "+ new Integer(cellCount++),"Description");

                insertNode(graph.getGridSize(),graph.getGridSize());

            }

        }).setToolTipText("RBD Node");

        // Toggle Connect Mode
        URL connectUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/connecton.png");
        ImageIcon connectIcon = ImageConversion.SetImageSize(connectUrl);
        toolbar.add(new AbstractAction("", connectIcon) {
            public void actionPerformed(ActionEvent e) {
                graph.setPortsVisible(!graph.isPortsVisible());
                URL connectUrl;
                if (graph.isPortsVisible()) {
                    connectUrl = getClass().getClassLoader().getResource(
                            "small/icon/gray/connecton.png"); //connectoff
                } else {
                    connectUrl = getClass().getClassLoader().getResource(
                            "small/icon/gray/connecton.png");
                }
                ImageIcon connectIcon = ImageConversion.SetImageSize(connectUrl);
                putValue(SMALL_ICON, connectIcon);
                setSavedHeading(true);
            }
        }).setToolTipText("Line Connector");

        // Undo
        toolbar.addSeparator();
        URL undoUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/undo.png");
        ImageIcon undoIcon = ImageConversion.SetImageSize(undoUrl);
        undo = new AbstractAction("", undoIcon) {
            public void actionPerformed(ActionEvent e) {
                undo();
                setSavedHeading(true);
            }
        };
        undo.setEnabled(false);
        toolbar.add(undo).setToolTipText("Undo");

        // Redo
        URL redoUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/redo.png");
        ImageIcon redoIcon = ImageConversion.SetImageSize(redoUrl);
        redo = new AbstractAction("", redoIcon) {
            public void actionPerformed(ActionEvent e) {
                redo();
                setSavedHeading(true);
            }
        };
        redo.setEnabled(false);
        toolbar.add(redo).setToolTipText("Redo");

        //
        // Edit Block
        //
        toolbar.addSeparator();
        Action action;
        URL url;
        

        // Copy
        action = javax.swing.TransferHandler // JAVA13:
                // org.jgraph.plaf.basic.TransferHandler
                .getCopyAction();
        url = getClass().getClassLoader().getResource(
                "small/icon/gray/copy.png");
        //action.putValue(Action.SMALL_ICON, ImageConversion.SetImageSize(url));
        copy = new EventRedirector(action, graph, url);
        toolbar.add(copy).setToolTipText("Copy");

        // Paste
        action = javax.swing.TransferHandler // JAVA13:
                // org.jgraph.plaf.basic.TransferHandler
                .getPasteAction();
        url = getClass().getClassLoader().getResource(
                "small/icon/gray/paste.png");
        //action.putValue(Action.SMALL_ICON, ImageConversion.SetImageSize(url));
        toolbar.add(paste = new EventRedirector(action, graph, url)).setToolTipText("Paste");

        // Cut
        action = javax.swing.TransferHandler // JAVA13:
                // org.jgraph.plaf.basic.TransferHandler
                .getCutAction();
        url = getClass().getClassLoader().getResource(
                "small/icon/gray/cut.png");
        //action.putValue(Action.SMALL_ICON, ImageConversion.SetImageSize(url));
        toolbar.add(cut = new EventRedirector(action, graph, url)).setToolTipText("Cut");

        // Remove
        URL removeUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/delete.png");
        ImageIcon removeIcon = ImageConversion.SetImageSize(removeUrl);
        remove = new AbstractAction("", removeIcon) {
            public void actionPerformed(ActionEvent e) {
                if (!graph.isSelectionEmpty()) {

                   if (BusinessException.showBusinessConformation("Do you want to delete this component? ",true)== JOptionPane.YES_OPTION){
                    
                    RBDCell deletedCell; 
                    Object[] cells = graph.getSelectionCells();
                    cells = graph.getDescendants(cells);
                    graph.getModel().remove(cells);
                        
                    for (Object cell : cells) {
                        
                        if (cell instanceof RBDCell) {
                            
                            deletedCell = (RBDCell)cell;
                            if (deletedCell.getCalcId() >0) {
                                //CalcCommonService.removeCalcFile(deletedCell.getCalcId());
                            }
                            
                        }
                    }
                    
                     setSavedHeading(true);
                    
                    }
                }
            }
        };
        remove.setEnabled(false);
        toolbar.add(remove).setToolTipText("Delete");

        // To Front
        toolbar.addSeparator();
        URL toFrontUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/tofront.png");
        ImageIcon toFrontIcon = ImageConversion.SetImageSize(toFrontUrl);
        tofront = new AbstractAction("", toFrontIcon) {
            public void actionPerformed(ActionEvent e) {
                if (!graph.isSelectionEmpty()) {
                    toFront(graph.getSelectionCells());
                }
            }
        };
        tofront.setEnabled(false);
        toolbar.add(tofront).setToolTipText("Bring Front");

        // To Back
        URL toBackUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/toback.png");
        ImageIcon toBackIcon = ImageConversion.SetImageSize(toBackUrl);
        toback = new AbstractAction("", toBackIcon) {
            public void actionPerformed(ActionEvent e) {
                if (!graph.isSelectionEmpty()) {
                    toBack(graph.getSelectionCells());
                }
            }
        };
        toback.setEnabled(false);
        toolbar.add(toback).setToolTipText("Send Back");

        // Zoom Std
        toolbar.addSeparator();
        URL zoomUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoom.png");
        ImageIcon zoomIcon = ImageConversion.SetImageSize(zoomUrl);
        toolbar.add(new AbstractAction("", zoomIcon) {
            public void actionPerformed(ActionEvent e) {
                graph.setScale(1.0);
            }
        }).setToolTipText("Zoom");
        // Zoom In
        URL zoomInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomin.png");
        ImageIcon zoomInIcon = ImageConversion.SetImageSize(zoomInUrl);
        toolbar.add(new AbstractAction("", zoomInIcon) {
            public void actionPerformed(ActionEvent e) {
                graph.setScale(2 * graph.getScale());
            }
        }).setToolTipText("Zoom In");
        // Zoom Out
        URL zoomOutUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomout.png");
        ImageIcon zoomOutIcon = ImageConversion.SetImageSize(zoomOutUrl);
        toolbar.add(new AbstractAction("", zoomOutIcon) {
            public void actionPerformed(ActionEvent e) {
                graph.setScale(graph.getScale() / 2);
            }
        }).setToolTipText("Zoom Out");

        // Group
        toolbar.addSeparator();
        URL groupUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/group.png");
        ImageIcon groupIcon = ImageConversion.SetImageSize(groupUrl);
        group = new AbstractAction("", groupIcon) {
            public void actionPerformed(ActionEvent e) {
                group(graph.getSelectionCells());
            }
        };
        group.setEnabled(false);
        toolbar.add(group).setToolTipText("Group");

        // Ungroup
        URL ungroupUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/ungroup.png");
        ImageIcon ungroupIcon = ImageConversion.SetImageSize(ungroupUrl);
        ungroup = new AbstractAction("", ungroupIcon) {
            public void actionPerformed(ActionEvent e) {
                ungroup(graph.getSelectionCells());
            }
        };
        ungroup.setEnabled(false);
        toolbar.add(ungroup).setToolTipText("Ungroup");

        // Save
        URL saveInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/save.png");
        ImageIcon saveIcon = ImageConversion.SetImageSize(saveInUrl);
        toolbar.add(new AbstractAction("", saveIcon) {
            public void actionPerformed(ActionEvent e) {
                //InternalFrameDemo.mainFrame.setLoader();
                SaveRBD();
                // InternalFrameDemo.mainFrame.removeLoder();
                setSavedHeading(false);
                JOptionPane.showMessageDialog(null, "Diagram saved successfully");
            }
        }).setToolTipText("Save All");

        // Text
        URL textUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/text.png");
        ImageIcon textIcon = ImageConversion.SetImageSize(textUrl);
        toolbar.add(new AbstractAction("", textIcon) {
            public void actionPerformed(ActionEvent e) {
                insertText(600, 500, 120, 30, -1, "RBD Caption");
                setSavedHeading(true);
            }
        }).setToolTipText("Insert Caption Text");

        // system group
        URL systemGroupUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/vertixgroup.png");
        ImageIcon systemGroupIcon = ImageConversion.SetImageSize(systemGroupUrl);
        toolbar.add(new AbstractAction("", systemGroupIcon) {
            public void actionPerformed(ActionEvent e) {

                insertVtxGroup(20, 20, 200, 200, -1, "RBD Caption");
                setSavedHeading(true);
                //JOptionPane.showMessageDialog(null,"Diagram saved successfully");  
            }
        }).setToolTipText("RBD Subsystem Outline");

        // internal connector
        URL internalConUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/conector.png");
        ImageIcon internalConIcon = ImageConversion.SetImageSize(internalConUrl);
        toolbar.add(new AbstractAction("", internalConIcon) {
            public void actionPerformed(ActionEvent e) {

                insertInterConn(20, 20, -1);
                setSavedHeading(true);
                //insertVtxGroup(20, 20,200,200, -1,"RBD Caption");
                // JOptionPane.showMessageDialog(null,"Diagram saved successfully");  
            }
        }).setToolTipText("RBD Internal connector");
        
        // grid 
        URL gridUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/grid.png");
        ImageIcon gridIcon = ImageConversion.SetImageSize(gridUrl);
        toolbar.add(new AbstractAction("", gridIcon) {
            public void actionPerformed(ActionEvent e) {
               //boolean gridFlag = graph.isGridVisible();
                  graph.setGridVisible(!graph.isGridVisible());
            }
        }).setToolTipText("Set Grid");

        // settings
        URL settingsUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/settings.png");
        ImageIcon settingsIcon = ImageConversion.SetImageSize(settingsUrl);
        toolbar.add(new AbstractAction("", settingsIcon) {
            public void actionPerformed(ActionEvent e) {

                Object[] options1 = {"Apply", "Cancel"};
                RBDSettingsPopup rbdSettingsPopup = new RBDSettingsPopup();

                rBDSettings.setRBDSettingsPro(graph, rbdSettingsPopup);

                int result = JOptionPane.showOptionDialog(null, rbdSettingsPopup, "RBD Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    graph.getGraphLayoutCache().edit(rBDSettings.setRBDSettings(graph, rbdSettingsPopup), null, null, null);
                    setSavedHeading(true);
                    //graph.getGraphLayoutCache().edit(rBDSettings.setRBDBackGroundColour(rBDSettings.RBDcellSelector(graph, RBDCell.RBD_VERTEX), rbdSettingsPopup.getRBDVertixBgColor(),rbdSettingsPopup.getRBDVertixFontColor(),rbdSettingsPopup.getRBDVertixBorderColor()), null, null, null);
                    //JOptionPane.showMessageDialog(null,"Diagram saved successfully");   

                }

            }
        }).setToolTipText("Settings");

        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(saveInfo);

        return toolbar;
    }

    public void insertTopBottom() {

        List<Calcfile> Calcfilelst = calcfileDao.getAllCalcfileByCalcFun(vtxspfhdr.getSpfid().getCalcFunId());
        Item[] inputItems = new Item[Calcfilelst.size()];
        Calcfile cfr;
        Calcfile cfr2;
        int count = 0;

        for (int i = 0; i < inputItems.length; i++) {
            cfr = Calcfilelst.get(i);
            inputItems[i] = new Item(cfr.getCalcId(), cfr.getComponents());
        }

        Object[] options1 = {"Add", "Cancel"};
        ListSelectionPopup listSelectionPopup = new ListSelectionPopup(checkExistList(), inputItems);

        int result = JOptionPane.showOptionDialog(null, listSelectionPopup, "Select to insert components",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        //listSelectionPopup.setDisableInserted(checkExistList());
        if (result == JOptionPane.YES_OPTION) {

            for (Item itemRef : listSelectionPopup.getselectedItems()) {
                //System.out.println(itemRef.getId()+" - "+ itemRef.getDescription());
                cfr2 = calcfileDao.findById(itemRef.getId());

                /* if (checkExist(cfr2.getCalcId())) {
                            JOptionPane.showMessageDialog(null,"Item Already Inserted");  
                        } else {
                            insertVertex(20, 20,cfr2.getCalcId(),cfr2.getCompId(),cfr2.getComponents());
                        }*/
                insertVertex(NumberMultiple.findclosedNumber(((count % 4) * 150) + 20), NumberMultiple.findclosedNumber((count / 4) * 150 + 20), -1, cfr2.getCalcId(), cfr2.getCompId(), cfr2.getComponents());

                count++;
                setSavedHeading(true);
            }

        }

    }

    private void insertNode(double x, double y) {
        if (bottomUp) {
            insertVertex(NumberMultiple.findclosedNumber(x), NumberMultiple.findclosedNumber(y), -1, -1, "Cell  " + cellCount++, "Description");
            setSavedHeading(true);
        } else {
            insertTopBottom();
        }
    }

    /**
     * @return Returns the graph.
     */
    public JGraph getGraph() {
        return graph;
    }

    /**
     * @param graph The graph to set.
     */
    public void setGraph(JGraph graph) {
        this.graph = graph;
    }

    /**
     * Create a status bar
     */
    protected StatusBarGraphListener createStatusBar() {
        return new EdStatusBar();
    }

    /**
     *
     * @return a String representing the version of this application
     */
    protected String getVersion() {
        return JGraph.VERSION;
    }

    /**
     * @return Returns the redo.
     */
    public Action getRedo() {
        return redo;
    }

    /**
     * @param redo The redo to set.
     */
    public void setRedo(Action redo) {
        this.redo = redo;
    }

    /**
     * @return Returns the undo.
     */
    public Action getUndo() {
        return undo;
    }

    /**
     * @param undo The undo to set.
     */
    public void setUndo(Action undo) {
        this.undo = undo;
    }

    /**
     * @return Returns the copy.
     */
    public Action getCopy() {
        return copy;
    }

    /**
     * @param copy The copy to set.
     */
    public void setCopy(Action copy) {
        this.copy = copy;
    }

    /**
     * @return Returns the cut.
     */
    public Action getCut() {
        return cut;
    }

    /**
     * @param cut The cut to set.
     */
    public void setCut(Action cut) {
        this.cut = cut;
    }

    /**
     * @return Returns the paste.
     */
    public Action getPaste() {
        return paste;
    }

    /**
     * @param paste The paste to set.
     */
    public void setPaste(Action paste) {
        this.paste = paste;
    }

    /**
     * @return Returns the toback.
     */
    public Action getToback() {
        return toback;
    }

    /**
     * @param toback The toback to set.
     */
    public void setToback(Action toback) {
        this.toback = toback;
    }

    /**
     * @return Returns the tofront.
     */
    public Action getTofront() {
        return tofront;
    }

    /**
     * @param tofront The tofront to set.
     */
    public void setTofront(Action tofront) {
        this.tofront = tofront;
    }

    /**
     * @return Returns the remove.
     */
    public Action getRemove() {
        return remove;
    }

    /**
     * @param remove The remove to set.
     */
    public void setRemove(Action remove) {
        this.remove = remove;
    }

    /* public JPanel CovertToJpanel(){
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(this);
     return jpanel;
    }*/
    //
    // Main
    //
    // Main Method
    public static void main(String[] args) {

        // Construct Frame
        JFrame frame = new JFrame("GraphEd");
        // Set Close Operation to Exit
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add an Editor Panel

        frame.getContentPane().add(new RBDDrawer(new SpfhdrDaoImpl().findById(1)));
        // Fetch URL to Icon Resource
        URL jgraphUrl = RBDDrawer.class.getClassLoader().getResource(
                "graph/images/jgraph.gif");
        // If Valid URL
        if (jgraphUrl != null) {
            // Load Icon
            ImageIcon jgraphIcon = new ImageIcon(jgraphUrl);
            // Use in Window
            frame.setIconImage(jgraphIcon.getImage());
        }
        // Set Default Size
        frame.setSize(520, 390);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Show Frame
        frame.setVisible(true);
    }

    public void margeData(int mpgHdrId) {
        Calcfile calc;

        try {

            InternalFrameDemo.mainFrame.setLoader();

            for (Graphvertex graphvertex : graphvertexDao.getAllVertexBySpfHdr(mpgHdrId)) {
                //calc = calcfileDao.findById(graphvertex.getCalccomp());
                calc = graphvertex.getCalccomp();
                graphvertex.setVertexHeading(calc.getCompId());
                graphvertex.setDescription(calc.getComponents());
                graphvertexDao.Update(graphvertex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();

        }

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
    
       private void performJGraphLayout(JGraphLayout aLayout) {
        JGraphFacade facade = new JGraphFacade(graph);
        //facade.run(aLayout, true);
        aLayout.run(facade); // Run the layout on the facade

        Map nested = facade.createNestedMap(false, false); // Obtain a mapof the resulting attribute changes from the facade
        graph.getGraphLayoutCache().edit(nested); // Apply the results tothe actual graph
    }
       
    private void performJGraphTreeLayout2() {
        JGraphTreeLayout theLayout = new JGraphTreeLayout();
        theLayout.setAlignment(SwingConstants.TOP);
        theLayout.setAlignment(SwingConstants.CENTER);
        theLayout.setAlignment(SwingConstants.BOTTOM);
       // theLayout.setOrientation(SwingConstants.SOUTH);
        //theLayout.setPositionMultipleTrees(true);
        performJGraphLayout(theLayout);
    }   
}
