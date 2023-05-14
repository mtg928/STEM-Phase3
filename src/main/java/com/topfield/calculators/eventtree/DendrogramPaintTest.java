/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.graph.draw2.GraphDrawPanel;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import com.topfield.controller.BusinessException;
import com.topfield.dao.EventTreeDao;
import com.topfield.dao.EventTreeHdrDao;
import com.topfield.daoImpl.EventTreeDaoImpl;
import com.topfield.daoImpl.EventTreeHdrDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Eventtree;
import com.topfield.modal.Eventtreehdr;
import com.topfield.singleton.DaoInstances;
import com.topfield.utilities.ScientificNotation;
import com.topfield.utilities.StringFuntions;
import com.topfield.utilities.TableMouseListener;
import com.topfield.view.popup.AddEventNodePopup;
import com.topfield.view.popup.EventHeadingPopup;
import com.topfield.view.popup.EventNodePopup;
import com.topfiled.interfaces.MyPrintable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class DendrogramPaintTest extends JPanel /*JComponent*/ implements /*ActionListener,*/ MyPrintable {

    private EventTreeDao eventDao = DaoInstances.getEventTreeDao();
    private ScientificNotation sciNotation = new ScientificNotation("0.00E0");
    private List<Eventtree> dataEventTree;
    private JPopupMenu popup;
    private DendrogramGraph dendrogramGraph = new DendrogramGraph();
    private Node<String> root;
    private int leaves;
    private int levels;
    private int heightPerLeaf;
    private int widthPerLevel;
    private final int margin = 5;
    private List<String> levelHeadings;
    private Eventtreehdr eventHdr;
    private TreeMap<String, Double>  summary = new TreeMap<>();

    private int graphWidth = 0;
    private int graphHeight = 0;
    private int smmaryMargin = 120;

    private static final int WIDE = 640;
    private static final int HIGH = 480;
    public static final int RADIUS = 15;
    //private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();
    private Point mousePt = new Point(WIDE / 2, HIGH / 2);
    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;
    private ControlPanel control;

    public DendrogramPaintTest(EventTreeCalculator parent, Eventtreehdr eventHdr) {

        //setLayout(new BorderLayout());
        //setLayout(null);
        //setupPopup();
        this.eventHdr = eventHdr;
        levelHeadings = new ArrayList<String>();

        root = dendrogramGraph.create("");
        root.setFrequency(eventHdr.getIntialProbs());
        root.setLevel(0);
        levelHeadings.add("");

        dataEventTree = eventDao.getAllEventsByEventHdr(eventHdr.getEventhdrId());
        setBackground(Color.WHITE);
        loadGraphDBNew();
        //loadGraphDB();

        //this.setOpaque(false);
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
        control = new ControlPanel(this, RADIUS, getAvailableNode(), edges, mousePt);


        /* Node a   = dendrogramGraph.create("10");
      Node b   = dendrogramGraph.create("30");
      
      Node c   = dendrogramGraph.create("10");
      Node d   = dendrogramGraph.create("30");
      //root = dendrogramGraph.create(a, b);
        
      root = dendrogramGraph.create("10");
      root.setChildrens(a, b);
      a.setChildrens(c, d);  */
        // getGraphSample();
    }

    private void getGraphSample() {
        root
                = dendrogramGraph.create(
                        dendrogramGraph.create(
                                dendrogramGraph.create(
                                        dendrogramGraph.create("10"),
                                        dendrogramGraph.create("9")),
                                dendrogramGraph.create(
                                        dendrogramGraph.create("8"),
                                        dendrogramGraph.create("7"))
                        ),
                        dendrogramGraph.create(
                                dendrogramGraph.create(
                                        dendrogramGraph.create("10"),
                                        dendrogramGraph.create("9")),
                                dendrogramGraph.create(
                                        dendrogramGraph.create("8"),
                                        dendrogramGraph.create("7"))
                        )
                );

    }

    private void getGraphSample2() {
        root
                = dendrogramGraph.create(
                        dendrogramGraph.create(
                                dendrogramGraph.create("10"),
                                dendrogramGraph.create(
                                        dendrogramGraph.create("9"),
                                        dendrogramGraph.create(
                                                dendrogramGraph.create("8"),
                                                dendrogramGraph.create("7")
                                        )
                                )
                        ),
                        dendrogramGraph.create(
                                dendrogramGraph.create(
                                        dendrogramGraph.create("6"),
                                        dendrogramGraph.create("5")
                                ),
                                dendrogramGraph.create(
                                        dendrogramGraph.create("4"),
                                        dendrogramGraph.create(
                                                dendrogramGraph.create("3"),
                                                dendrogramGraph.create(
                                                        dendrogramGraph.create("2"),
                                                        dendrogramGraph.create("1")
                                                )
                                        )
                                )
                        )
                );

    }

    public void setupPopup() {

        popup = new JPopupMenu();

        // add menu items to popup
        JMenuItem add = new JMenuItem("Add / Edit Node");
        JMenuItem addCap = new JMenuItem("Add / Edit Level Captions");
        JMenuItem delete = new JMenuItem("Delete Node");
        JMenuItem deleteCap = new JMenuItem("Delete Level Captions");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem zoomIn = new JMenuItem("Zoom In");
        JMenuItem zoomOut = new JMenuItem("Zoom Out");
        JMenuItem calPro = new JMenuItem("Calculate Results");
        JMenuItem refresh = new JMenuItem("Refresh");
        JMenuItem reset = new JMenuItem("Reset");

        popup.add(add);
        popup.add(addCap);
        popup.add(delete);
        popup.add(deleteCap);
        popup.add(zoomIn);
        popup.add(zoomOut);
        popup.add(calPro);
        popup.add(save);
        popup.add(refresh);
        popup.add(reset);
        popup.addSeparator();
        popup.add(new JMenuItem("SelectAll"));

        /*add.addActionListener(this);
        addCap.addActionListener(this);
        save.addActionListener(this);
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        calPro.addActionListener(this);
        delete.addActionListener(this);
        deleteCap.addActionListener(this);
        refresh.addActionListener(this);
        reset.addActionListener(this);*/
        // sets the popup menu for the table
        setComponentPopupMenu(popup);

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent me) {
                //JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame,"Hello, Welcome to Javatpoint. ");  
                showPopup(me); // showPopup() is our own user-defined method
            }
        });

    }

    public void showPopup(MouseEvent me) {
        if (me.isPopupTrigger()) {
            popup.show(me.getComponent(), me.getX(), me.getY());
        }
    }

    /* @Override
    public void actionPerformed(ActionEvent event) {

        JMenuItem menu = (JMenuItem) event.getSource();
        int selectedOption=0;

        //JOptionPane.showMessageDialog(this,"Hello, Welcome to Javatpoint. "+menu.getText());  
        if (menu.getText().contentEquals("Add / Edit Node")) {
            performAddOrEdit();
        } else if (menu.getText().contentEquals("Add / Edit Level Captions")) {
             performAddOrEditLevelCaptions();
 
        }else if (menu.getText().contentEquals("Save")) {

           // System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");

           // SaveEventTreeData();
           
            SaveEventTreeDataNew();
           
            /*ArrayList<String> savedata = getSaveData(root);

            for (int i = 0; i < savedata.size() - 1; i++) {
                System.out.println(i + ") " + levelHeadings.get(i) + " " + savedata.get(i));
            }*/
 /*
        }else if (menu.getText().contentEquals("Delete Node")) {
             performDelete();
        }else if (menu.getText().contentEquals("Delete Level Captions")) {
            performDeleteLevelCaptions();
            
        }else if (menu.getText().contentEquals("Zoom In")) {
            performZoomIn();
            

        }else if (menu.getText().contentEquals("Refresh")) {
            /*selectedOption = JOptionPane.showConfirmDialog(this, 
                                  "Do you wanna to Refresh? \n It will bring to the diagram to initial save state", 
                                  "Choose", 
                                  JOptionPane.YES_NO_OPTION); 
            if (selectedOption == JOptionPane.YES_OPTION) {
                dataEventTree = eventDao.getAllEventsByEventHdr(eventHdr.getEventhdrId());
                loadGraphDB();
                repaint();
            }*/
 /*  performRefersh();
            
                
        }else if (menu.getText().contentEquals("Zoom Out")) {
             performRefersh();
         

        }else if (menu.getText().contentEquals("Calculate Results")) {
              performCalculate();
             
            
        }else if (menu.getText().contentEquals("Reset")) {
            performReset();    
        }
    }*/
    public void performAddOrEdit() {
        //JOptionPane.showMessageDialog(this,"Hello, Welcome to Javatpoint. "+menu.getText());  
        //System.out.println("cvfrbgbn");
        AddEventNodePopup addpopup = new AddEventNodePopup(10, levelHeadings, this, true);

        AddEventNode(addpopup);

    }

    public void performAddEdit(boolean edit) {
        //JOptionPane.showMessageDialog(this,"Hello, Welcome to Javatpoint. "+menu.getText());  
        //System.out.println("cvfrbgbn");
        try {

            EventNodePopup addpopup = new EventNodePopup(10, levelHeadings, this, true);
            addNewNode(addpopup, edit);

        } catch (Exception e) {
            e.printStackTrace();
            BusinessException.showBusinessException("ET0407004", "Error in performing the 'Add Edit' action." + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

    }

    public void performAddOrEditLevelCaptions() {
        try {
            EventHeadingPopup eventHeadingPopup = new EventHeadingPopup(getSaveData(root).size() - 1);
            Object[] options1 = {"OK", "Cancel"};
            eventHeadingPopup.setLevelCaptions(levelHeadings);
            int result = JOptionPane.showOptionDialog(null, eventHeadingPopup, "Edit Level Captions",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {
                levelHeadings = eventHeadingPopup.getLevelCaptions();
                repaint();
                EventTreeGraph.setSavedHeading(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            BusinessException.showBusinessException("ET0407005", "Error in 'Edit Level Captions' action. Please check and re-enter" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

    }

    public void performDelete() {
        Node a;
        Node b;
        AddEventNodePopup addpopup = new AddEventNodePopup(6, levelHeadings, this, false);

        Object[] options1 = {"OK", "Cancel"};
        int result = JOptionPane.showOptionDialog(null, addpopup, "Select Node to delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        if (result == JOptionPane.YES_OPTION) {

            if (BusinessException.showBusinessConformation("Do you want to delete this Node? \n "
                    + "The operation will delete the node and the sub nodes associated with it" ,true)== JOptionPane.YES_OPTION){
                            Node perent = findNode(addpopup.getLevelIndex(), addpopup.getItem());

                            perent.removeChildrens();
                            //levelHeadings.remove(addpopup.getLevel()-1);
                            //recalculateFrequency(perent);
                            repaint();
                            EventTreeGraph.setSavedHeading(true);
            }

        }

    }

    public void performDeleteLevelCaptions() {
        JComboBox<Integer> comboBox = new JComboBox();
        for (int i = 1; i <= levelHeadings.size(); i++) {
            comboBox.addItem(i);
        }

        int selectedOption = JOptionPane.showConfirmDialog(this,
                comboBox,
                "Choose",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            
               if (BusinessException.showBusinessConformation("Do you want to delete this level caption? ",true)== JOptionPane.YES_OPTION){
                               levelHeadings.remove(comboBox.getSelectedIndex());
                                repaint();
                                EventTreeGraph.setSavedHeading(true);
               }

        }

    }

    public void performZoomIn() {
        System.out.println("nsfvgkldjblfkh;f;");

        graphWidth += 200;
        graphHeight += 200;

        setPreferredSize(new Dimension(graphWidth, graphHeight));
        //setPreferredSize(new Dimension(1600,1200));
        //pack();
        revalidate();
        repaint();

    }

    public void performRefersh() {
        ItemConverter(0, 0);
    }

    public void performZoomOut() {
        if (graphWidth >= (getWidth() - 100)) {
            graphWidth -= 200;
            graphHeight -= 200;

            setPreferredSize(new Dimension(graphWidth, graphHeight));
            revalidate();
            repaint();
        }
    }

    public void performCalculate() {
        //summary = getSummaryData(root);
        getSummaryData(root);
        //summary = summaryAsString();
        repaint();
        //System.out.println(getSummaryData(root)); 
    }

    public void performReset() {
        int selectedOption = JOptionPane.showConfirmDialog(this,
                "Do you wanna to Reset? \n It will delete all the nodes",
                "Choose",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            levelHeadings = new ArrayList<String>();

            root = dendrogramGraph.create("10");
            root.setFrequency(eventHdr.getIntialProbs());
            root.setLevel(0);
            levelHeadings.add("");

            repaint();
        }
    }

    public void AddEventNode(AddEventNodePopup addpopup) {

        Object[] options1 = {"Save", "Cancel"};
        addpopup.setLevelHeadings(getHeading(0));
        Node a;
        Node b;

        int result = JOptionPane.showOptionDialog(null, addpopup, "Select the node and enter node details",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);

        if (result == JOptionPane.YES_OPTION) {

            setHeading(addpopup.getLevelIndex(), addpopup.getLevelHeadings());

            if (addpopup.getTrueProbability() <= 0.0) {
                JOptionPane.showMessageDialog(this, "Event can not created with zero or negative probability");
                AddEventNode(addpopup);
            } else {

                try {
                    Node perent = findNode(addpopup.getLevelIndex(), addpopup.getItem());

                    if (perent.getChildren().size() != 0) {
                        a = (Node) perent.getChildren().get(0);
                        b = (Node) perent.getChildren().get(1);
                    } else {
                        a = dendrogramGraph.create(addpopup.getTrueTags());
                        b = dendrogramGraph.create(addpopup.getFalseTags());
                    }

                    a.setLevel(addpopup.getLevelIndex());
                    a.setProbability(addpopup.getTrueProbability());
                    a.setFrequency(a.getProbability() * perent.getFrequency());
                    a.setContents(addpopup.getTrueTags());
                    a.setParent(perent);

                    b.setLevel(addpopup.getLevelIndex());
                    b.setProbability((1 - addpopup.getTrueProbability()));
                    b.setFrequency(b.getProbability() * perent.getFrequency());
                    b.setContents(addpopup.getFalseTags());
                    b.setParent(perent);

                    //getEdgeNode().setChildrens(a, b);
                    perent.setChildrens(a, b);

                    recalculateFrequency(perent);

                    repaint();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "The event in " + (addpopup.getLevelItem() - 1) + " th level is not defined yet to create " + addpopup.getLevelItem() + " level");
                    AddEventNode(addpopup);
                }
            }
            EventTreeGraph.setSavedHeading(true);
        }

    }

    public void addNewNode(EventNodePopup addpopup, boolean edit) {

        Object[] options1 = {"Save", "Cancel"};
        Node perent = findNode2();
        Node a;
        Node b;

        if (perent != null) {
            addpopup.setLevelHeadings(getLevelString());
            addpopup.setProbablityData(perent);

            int result = JOptionPane.showOptionDialog(null, addpopup, "Select the node and enter node details",
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);

            if (result == JOptionPane.YES_OPTION) {

                setHeading(getlevel(), addpopup.getLevelHeadings());

                if (addpopup.getTrueProbability() <= 0.0 || addpopup.getTrueProbability() >= 1.0) {
                    BusinessException.showBusinessException("ET0207001", "Probability must be between zero and one", JOptionPane.WARNING_MESSAGE);
                    addNewNode(addpopup, edit);
                } else if (addpopup.getTrueTags().contains("*") || addpopup.getTrueTags().contains("#") || addpopup.getFalseTags().contains("*") || addpopup.getFalseTags().contains("#")) {
                    BusinessException.showBusinessException("ET0207002", "TrueTags or FalseTags can not caontains * or #", JOptionPane.WARNING_MESSAGE);
                    addNewNode(addpopup, edit);
                } else {

                    try {

                        if (perent.getChildren().size() != 0) {
                            a = (Node) perent.getChildren().get(0);
                            b = (Node) perent.getChildren().get(1);
                        } else {
                            a = dendrogramGraph.create(addpopup.getTrueTags());
                            b = dendrogramGraph.create(addpopup.getFalseTags());
                        }

                        //a.setLevel(addpopup.getLevelIndex());
                        a.setProbability(addpopup.getTrueProbability());
                        a.setFrequency(a.getProbability() * perent.getFrequency());
                        a.setContents(addpopup.getTrueTags());
                        a.setParent(perent);

                        //b.setLevel(addpopup.getLevelIndex());
                        b.setProbability((1 - addpopup.getTrueProbability()));
                        b.setFrequency(b.getProbability() * perent.getFrequency());
                        b.setContents(addpopup.getFalseTags());
                        b.setParent(perent);

                        //getEdgeNode().setChildrens(a, b);
                        perent.setChildrens(a, b);

                        recalculateFrequency(perent);
                        perent.setSelected(false);
                        repaint();

                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(this, "The event in " + (addpopup.getLevelItem()-1) + " th level is not defined yet to create "+addpopup.getLevelItem()+" level");
                        addNewNode(addpopup, edit);
                    }
                    EventTreeGraph.setSavedHeading(true);
                }

            }

        }

    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        graphWidth = getWidth() - 100;
        graphHeight = getHeight() - smmaryMargin;

        leaves = dendrogramGraph.countLeaves(root);
        levels = dendrogramGraph.countLevels(root);
        heightPerLeaf = (graphHeight - margin) / leaves;
        widthPerLevel = (graphWidth - margin) / levels;
        //currentY = 0;
        dendrogramGraph.setCurrentX(150);
        dendrogramGraph.setCurrentY(50);
        dendrogramGraph.setNodeLevelCount(0);
        //dendrogramGraph.setNodeLevelCount(0);

        g.translate(margin, margin);
        dendrogramGraph.draw(g, root, 0, graphWidth, graphHeight, margin, widthPerLevel, heightPerLeaf, levelHeadings);

        int io = 0;
        /*for (Eventtree eventtree : dataEventTree) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("default", Font.BOLD, 12));
            g.drawString(io+"."+levelHeadings.get(io),(io*widthPerLevel)+15,15);
            io++;
        }*/

        for (String eventtree : levelHeadings) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("default", Font.BOLD, 12));
            g.drawString(/*(io+1)+"."+*/StringFuntions.stringCutter(levelHeadings.get(io), widthPerLevel / 7), (io * widthPerLevel) + dendrogramGraph.getCurrentX() + 15, 15);
            io++;
            //System.out.println("widthPerLevel "+widthPerLevel);
        }

        g.drawLine(0, dendrogramGraph.getCurrentY() / 2, dendrogramGraph.getCurrentX() - margin, dendrogramGraph.getCurrentY() / 2);
        if (getMousePoint().x <= dendrogramGraph.getCurrentX()) {
            g.drawRect(dendrogramGraph.getCurrentX() - 14, (dendrogramGraph.getCurrentY() / 2) - RADIUS, RADIUS * 2, RADIUS * 2);
        }

        g.setColor(Color.BLUE);
        g.drawString(eventHdr.getIntialProbs() + eventHdr.getUnits(), 5, (dendrogramGraph.getCurrentY() / 2) - 10);
        //g.drawString("hkjjjjjjjj\n bbbbbbbbbbbbb",15, (dendrogramGraph.getCurrentY()/2)+15);
        drawString(gr, StringFuntions.MultipleLine(eventHdr.getEventName(), 15), 5, (dendrogramGraph.getCurrentY() / 2) + 5);
        g.setColor(Color.DARK_GRAY);
        //g.drawString(summary,15, dendrogramGraph.getCurrentY()-50);
        //drawCenteredString("Unit - "+eventHdr.getUnits(), getWidth(),dendrogramGraph.getCurrentY()-50, g);
        //drawCenteredString(summary, getWidth(),dendrogramGraph.getCurrentY()-30, g);
        //g.drawLine(getWidth()-270,dendrogramGraph.getCurrentY()-100, getWidth()-50,dendrogramGraph.getCurrentY()-100);
        // drawString(gr, summary, getWidth()-230,dendrogramGraph.getCurrentY()-100);

        //createSummaryTable(g,(graphWidth-230),(graphHeight-200),5,3);
        if (summary.size() > 0) {
            createSummaryTable(g, graphWidth - 250, graphHeight, summary.size(), 2);
        }

    }

    public void loadGraphDBNew() {

        List<Node> created = new ArrayList<>();
        if (dataEventTree.size() > 0) {
            root.setId(dataEventTree.get(0).getEventId());
        }
        
        created.add(root);

        for (Eventtree eventtree : dataEventTree) {

            for (Node node : createChildNode(getChild(eventtree, dataEventTree), getNodeById(created, eventtree.getEventId()))) {
                created.add(node);
            }
            
            if (eventtree.getEventLevel()!=0) {
                setHeading(eventtree.getEventLevel()-1, eventtree.getEventName()); 
            }
            
        }

        //   Node n1 = createNode(dataEventTree.get(1),root);
        //n1.setContents(tag[0]);
        // Node n2 = createNode(dataEventTree.get(2),root);
        //root.setChildrens(n1, n2);
        /* for (Eventtree eventtree : dataEventTree) {
             
         }*/
    }

    public List<Eventtree> getChild(Eventtree parent, List<Eventtree> eventTreeList) {
        return eventTreeList.stream().filter(n -> (n.getEventParent() == parent.getEventId())).collect(Collectors.toList());
    }

    public Node getNodeById(List<Node> created, int id) {
        System.out.println("id*** - "+id);
        return created.stream().filter(n -> (n.getId() == id)).collect(Collectors.toList()).get(0);
    }

    public List<Node> createChildNode(List<Eventtree> child, Node parent) {

        List<Node> createdNode = new ArrayList<>();

        if (child.size()==2) {
            Node n1 = dendrogramGraph.create(child.get(0).getDirections());
            n1.setId(child.get(0).getEventId());
            n1.setLevel(child.get(0).getEventLevel());
            n1.setParent(parent);
            n1.setProbability(child.get(0).getProbs());
            n1.setFrequency((parent.getFrequency() * child.get(0).getProbs()));

            Node n2 = dendrogramGraph.create(child.get(1).getDirections());
            n2.setId(child.get(1).getEventId());
            n2.setLevel(child.get(1).getEventLevel());
            n2.setParent(parent);
            n2.setProbability(child.get(1).getProbs());
            n2.setFrequency((parent.getFrequency() * child.get(1).getProbs()));

            createdNode.add(n1);  //intPro.toString()
            createdNode.add(n2);  //"5.71E-5"

            parent.setChildrens(createdNode.get(createdNode.size() - 2), createdNode.get(createdNode.size() - 1));
        }

        return createdNode;
    }

    public void loadGraphDB() {

        List<Node> previous = new ArrayList<Node>();
        previous.add(root);
        List<Node> next;

        for (Eventtree eventtree : dataEventTree) {
            next = new ArrayList<Node>();
            setHeading(eventtree.getEventLevel(), eventtree.getEventName());
            levelIterator(previous, next, eventtree.getTrueProbs().split("\\#"), eventtree.getComments().split("\\#"), eventtree.getEventLevel());

            previous = next;
        }

    }

    private List<Node> createSingleNode(Node parent, int level, Double prob, String tags) {

        //System.out.println("tags - "+tags);
        List<Node> createdNode = new ArrayList<Node>();
        String[] tag = tags.split("\\*").length > 1 ? tags.split("\\*") : new String[]{"", ""};

        //System.out.println("pre.getProbability() - "+pre.getProbability());
        System.out.println("tag[0] - " + tag[0] + " tag[1] -" + tag[1]);
        Node n1 = dendrogramGraph.create(tag[0]);
        n1.setLevel(level);
        n1.setParent(parent);
        n1.setProbability(prob);
        n1.setFrequency((parent.getFrequency() * prob));
        //n1.setContents(tag[0]);

        Node n2 = dendrogramGraph.create(tag[1]);
        n2.setLevel(level);
        n2.setParent(parent);
        n2.setProbability((1 - prob));
        n2.setFrequency(parent.getFrequency() * (1 - prob));
        //n2.setContents(tag[1]);

        createdNode.add(n1);  //intPro.toString()
        createdNode.add(n2);  //"5.71E-5"

        parent.setChildrens(createdNode.get(createdNode.size() - 2), createdNode.get(createdNode.size() - 1));

        return createdNode;
    }

    private void levelIterator(List<Node> parent, List<Node> nextParent, String[] prob, String[] tags, int level) {

        //System.out.println("level - "+(Math.pow(2, level)/2));
        for (int i = 0; i < (Math.pow(2, (level + 1)) / 2); i++) {

            Double res = prob.length > i ? Double.parseDouble(prob[i]) : 0.0;
            String sinTags = tags.length > i ? tags[i] : "*";

            if (res != 0.0) {

                for (Node node : createSingleNode(parent.get(i), level, res, sinTags)) {
                    nextParent.add(node);
                }

            }
        }

    }

    public Node<String> getEdgeNode() {
        Node<String> ref = root;

        while (ref.getChildren().size() != 0) {
            ref = ref.getChildren().get(0);
        }

        return ref;
    }

    public Node<String> findNode(int level, int item) {
        Node<String> ref = root;
        int levelTot = (int) Math.pow(2, level);
        int intRef = item;

        for (int i = 1; i <= level; i++) {
            System.out.println("levelTot - " + intRef);
            if (intRef <= (levelTot / 2)) {
                ref = ref.getChildren().get(0);
            } else if (intRef <= levelTot) {
                ref = ref.getChildren().get(1);
                intRef = intRef / 2;
            }
            levelTot = levelTot / 2;

        }

        return ref;
    }

    public Node findNode2() {
        Node node = null;

        if (getMousePoint().x <= dendrogramGraph.getCurrentX()) {
            node = root;
        } else {
            node = Node.getSelectedOne(getAvailableNode(), getMousePoint());
        }

        if (node == null) {
            JOptionPane.showMessageDialog(this, "No node selected yet", "Selected", JOptionPane.INFORMATION_MESSAGE);
        }

        return node;
    }

    public void setHeading(int level, String heading) {

        System.out.println("level " + level + " levelHeadings.size() " + levelHeadings.size());

        if (levelHeadings.size() <= level) {

            for (int i = levelHeadings.size(); i <= level; i++) {
                levelHeadings.add("");
            }

        }
        levelHeadings.set(level, heading);

        /*if (level < levelHeadings.size() && levelHeadings.get(level) != null) {
            levelHeadings.set(level, heading);
        } else {
            levelHeadings.add(level, heading);
        }*/
    }

    public String getHeading(int level) {

        System.out.println(level + " " + levelHeadings.size());

        if (levelHeadings.size() >= level && levelHeadings.get(level) != null) {
            return levelHeadings.get(level);
        } else {
            return "";
        }

    }

    public void recalculateFrequency(Node root) {
        Node a;
        Node b;

        if (root.getChildren().size() != 0) {
            a = (Node) root.getChildren().get(0);
            a.setFrequency(root.getFrequency() * a.getProbability());
            b = (Node) root.getChildren().get(1);
            b.setFrequency(root.getFrequency() * b.getProbability());

            if (a.getChildren().size() != 0) {
                recalculateFrequency(a);
            }

            if (b.getChildren().size() != 0) {
                recalculateFrequency(b);
            }
        }

    }

    public void dataSave(Node root) {

        Node a;
        Node b;
        int count = 0;

        HashMap<String, String> capitalCities = new HashMap<String, String>();
        capitalCities.put(levelHeadings.get(count), root.getProbability().toString());

        if (root.getChildren().size() != 0) {
            a = (Node) root.getChildren().get(0);
            a.setFrequency(root.getFrequency() * a.getProbability());
            b = (Node) root.getChildren().get(1);
            b.setFrequency(root.getFrequency() * b.getProbability());

            if (a.getChildren().size() != 0) {
                recalculateFrequency(a);
            }

            if (b.getChildren().size() != 0) {
                recalculateFrequency(b);
            }
        }

    }

    public ArrayList<Node> getChildByLevel(Node root, int level) {
        // MutableTreeNode 
        int count = 0;
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;

        for (int i = 1; i <= level; i++) {
            res = new ArrayList<Node>();

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    res.add((Node) node.getChildren().get(0));
                    res.add((Node) node.getChildren().get(1));
                }

            }

            queue = res;
        }

        return res;
    }

    public ArrayList<String> getSaveData(Node root) {
        // MutableTreeNode 
        int count = 0;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        String childPro = "";
        Node a;
        Node b;

        while (queue.size() > 0) {
            res = new ArrayList<Node>();
            childPro = "";

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                    childPro = childPro + a.getProbability() + "#";
                } else {

                    childPro = childPro + "0.0#";
                }

            }
            result.add(childPro);
            queue = res;
            count++;
        }

        return result;
    }

    public ArrayList<String> getSaveDataTag(Node root) {
        // MutableTreeNode 
        int count = 0;
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        String childPro = "";
        Node a;
        Node b;

        while (queue.size() > 0) {
            res = new ArrayList<Node>();
            childPro = "";

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                    if (checkNull(a.getContents()) && checkNull(b.getContents())) {
                        childPro = childPro + "-*-#";
                    } else if (checkNull(a.getContents())) {
                        childPro = childPro + "-*" + b.getContents() + "#";
                    } else if (checkNull(b.getContents())) {
                        childPro = childPro + a.getContents() + "*-#";
                    } else {
                        childPro = childPro + a.getContents() + "*" + b.getContents() + "#";
                    }

                } else {

                    childPro = childPro + "*#";
                }

            }
            result.add(childPro);
            queue = res;
            count++;
        }

        return result;
    }

    public boolean checkNull(Object object) {
        boolean res = false;

        if (object == null || object.equals("")) {
            res = true;
        }

        return res;
    }

    public TreeMap<String, Double> getSummaryData(Node root) {
        // MutableTreeNode 
        int count = 0;
        summary = new TreeMap<>();
        TreeMap<String, Double> result = summary;
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        String childPro = "";
        Node a;
        Node b;

        while (queue.size() > 0) {
            res = new ArrayList<Node>();
            childPro = "";

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                } else {

                    if (result.containsKey(node.getContents())) {
                        result.put(node.getContents() + "", result.get(node.getContents().toString()) + node.getFrequency());
                    } else {
                        result.put(node.getContents() + "", node.getFrequency());
                    }

                }

            }

            queue = res;
            count++;
        }

        return result;
    }

    public Eventtree getEventtree(int level, String levelName, String probs, Eventtreehdr eventHdr) {

        Eventtree eventtree = new Eventtree();

        eventtree.setEventLevel(level);
        eventtree.setEventName(levelName);
        eventtree.setEventParent(3);
        eventtree.setTrueProbs(probs);
        eventtree.setChild("Yes");
        eventtree.setDirections("Up");
        eventtree.setEventHdr(eventHdr);

        return eventtree;
    }

    public void SaveAllNodes() {

        try {
            
        
            eventDao.removeAllEventsByEventHdr(eventHdr.getEventhdrId());
            for (Node node : getAvailableNode()) {
                // System.out.println(  ", level=" + node.getLevel()+" contents=" + StringFuntions.NVL(node.getContents().toString(), "")  + ", children=" + node.getChildren().size() + ", parent=" + StringFuntions.NVL(node.getParent().getContents().toString(),"null") + ", probability=" + node.getProbability().toString() + ", frequency=" + node.getFrequency()  );

                EventTreeCURD.covertNodeEventTree(node, getHeading(node.getLevel()==0?0:node.getLevel()-1), eventHdr);

            }
            
            BusinessException.showBusinessException("Saved", "Event Tree is saved successfully", JOptionPane.INFORMATION_MESSAGE);
        
        } catch (Exception e) {
             e.printStackTrace();
             BusinessException.showBusinessException("ET0407007", "Error while saving the Event Tree - "+e, JOptionPane.ERROR_MESSAGE);
        }

    }

    public void SaveEventTreeDataNew() {

        ArrayList<String> savedata = getSaveData(root);
        ArrayList<String> savedataTag = getSaveDataTag(root);
        Eventtree eventtree;
        int esize = savedata.size() - 1;

        eventDao.removeAllEventsByEventHdr(eventHdr.getEventhdrId());

        for (int i = 0; i < esize; i++) {

            eventtree = new Eventtree();

            eventtree.setEventLevel(i);
            eventtree.setEventName(levelHeadings.get(i));
            //eventtree.setComments(head.getComments());
            //eventtree.setEventType(head.getEventType());
            //eventtree.setEventParent((events));
            eventtree.setTrueProbs(savedata.get(i));
            eventtree.setComments(savedataTag.get(i));
            //eventtree.setHazidRef(head.getHazidRef());
            eventtree.setChild("Yes");
            eventtree.setDirections("Up");
            eventtree.setEventHdr(eventHdr);

            eventDao.Save(eventtree);
            //System.out.println(i + ") " + levelHeadings.get(i) + " " + savedata.get(i));
        }
        JOptionPane.showMessageDialog(this, "Diagram saved successfully");
        EventTreeGraph.setSavedHeading(false);
    }

    public void SaveEventTreeData() {

        ArrayList<String> savedata = getSaveData(root);
        Eventtree eventtree;
        boolean update = false;
        int esize = savedata.size() - 1;

        eventDao.removeAllEventsByEventHdr(eventHdr.getEventhdrId(), savedata.size());
        dataEventTree = eventDao.getAllEventsByEventHdr(eventHdr.getEventhdrId());

        for (int i = 0; i < esize; i++) {

            update = findEventtreeByLevel(i).isPresent();
            eventtree = update ? findEventtreeByLevel(i).get() : new Eventtree();

            eventtree.setEventLevel(i);
            eventtree.setEventName(levelHeadings.get(i));
            //eventtree.setComments(head.getComments());
            //eventtree.setEventType(head.getEventType());
            //eventtree.setEventParent((events));
            eventtree.setTrueProbs(savedata.get(i));
            //eventtree.setHazidRef(head.getHazidRef());
            eventtree.setChild("Yes");
            eventtree.setDirections("Up");
            eventtree.setEventHdr(eventHdr);

            if (update) {
                eventDao.Update(eventtree);
            } else {
                eventDao.Save(eventtree);
            }

            System.out.println(i + ") " + levelHeadings.get(i) + " " + savedata.get(i));
        }

    }

    public Optional<Eventtree> findEventtreeByLevel(final int Level) {
        return dataEventTree.stream().filter(e -> e.getEventLevel() == Level).findFirst();
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, h);
    }

    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private int ItemConverter(int level, int Item) {
        int levelCount = dataEventTree.size();
        int res = 0;

        for (Node node : getAllLastNode()) {
            System.out.println("Level " + node.getLevel() + " " + node.getProbability() + " " + node.getContents());
        }

        return res;
    }

    private Node findNodeByConvertion() {
        Node res = null;

        for (Node node : getAllLastNode()) {
            System.out.println("Level " + node.getLevel() + " " + node.getProbability() + " " + node.getContents());
        }

        return res;
    }

    private List<Node> getAllLastNode() {

        ArrayList<Node> results = new ArrayList<Node>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        Node a;
        Node b;

        while (queue.size() > 0) {
            res = new ArrayList<Node>();

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                } else {
                    results.add(node);
                }

            }

            queue = res;
        }
        return results;
    }

    /* public int[] getAvailableNodeCount(int level){
        
        int count =0;
        ArrayList<Node> results = new ArrayList<Node>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        Node a;
        Node b;
        int resList[][] = new int[level][];
        
        if (level == 0) {
            return new int []{1};
        }else if (level == 1) {
            return new int []{1,2};
        }else {
            
            while (queue.size() > 0 && (count <= level)) {
            res = new ArrayList<Node>();

              for (Node node : queue) {
                        if (node.getChildren().size() != 0) {
                            a = (Node) node.getChildren().get(0);
                            b = (Node) node.getChildren().get(1);
                            res.add(a);
                            res.add(b);

                        } else {
                              results.add(node);
                        }

                    }

                    queue = res;
                }
            
        }

        
        
    }*/
    public int getAvailableNodeCount(int level) {

        int count = 0;
        ArrayList<Node> results = new ArrayList<Node>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(root);
        ArrayList<Node> res = queue;
        Node a;
        Node b;
        int resList = 0;

        while (queue.size() > 0 && (count <= level)) {
            res = new ArrayList<Node>();
            results = new ArrayList<Node>();

            for (Node node : queue) {
                if (node.getChildren().size() != 0) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                } else {
                    //results.add(node);
                }
                results.add(node);
            }

            queue = res;
            count++;
        }

        return results.size();

    }

    public ArrayList<Node> getAvailableNode() {
        int count = 0;
        ArrayList<Node> result = new ArrayList<>();
        ArrayList<Node> queue = new ArrayList<>();
        queue.add(root);
        ArrayList<Node> res;
        Node a;
        Node b;

        while (queue.size() > 0) {
            res = new ArrayList<>();

            for (Node node : queue) {
                result.add(node);
                if (!node.getChildren().isEmpty()) {
                    a = (Node) node.getChildren().get(0);
                    b = (Node) node.getChildren().get(1);
                    res.add(a);
                    res.add(b);

                } else {

                }

            }
            queue = res;
            count++;
        }

        return result;

    }

    private static void createAndShowGUI() {

        EventTreeHdrDao eventHdrDao = new EventTreeHdrDaoImpl();

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        DendrogramPaintTest panel = new DendrogramPaintTest(new EventTreeCalculator(), eventHdrDao.findById(8));
        f.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);

        f.setSize(1000, 800);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /*public JPanel getpJpanel() {
        return new DendrogramPaintTest();
    }*/
    @Override
    public BufferedImage getPrintContantGraph() {

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        paint(g2);

        return image;
    }

    public void createSummaryTable(Graphics2D g, int x, int y, int row, int col) {
        int totalWidth = 200;
        int totalHeight = 80;
        int rowHt = (totalHeight) / row;
        int rowWid = (totalWidth) / col;
        int endpointX = x + totalWidth;
        int endpointY = y + totalHeight;
        Map.Entry me = null;
        // Get a set of the entries
        Set set = summary.entrySet();

        // Get an iterator
        Iterator it = set.iterator();

        for (int i = 0; i <= row; i++) {
            g.drawLine(x, y + (i * rowHt), endpointX, y + (i * rowHt));
            if (i != 0) {
                me = (Map.Entry) it.next();
                g.drawString(((me.getKey() + "").equals("") || (me.getKey() + "").equals("-")) ? "Not Defined" : me.getKey() + "", (x + 2), (y - 2) + (i * rowHt));
                g.drawString(sciNotation.sciFormat((double) me.getValue()), (x + 2) + rowWid, (y - 2) + (i * rowHt));
            }

        }
        // draw the columns

        for (int i = 0; i <= col; i++) {
            g.drawLine(x + (i * rowWid), y, x + (i * rowWid), endpointY);
            /*if (i!=col) {
                   g.drawString("Test", x+(i * rowWid), y+rowHt);
                }*/

        }

    }

    public String summaryAsString() {

        String summary1 = "";

        for (Map.Entry<String, Double> entry : getSummaryData(root).entrySet()) {

            String key = entry.getKey();
            Double value = entry.getValue();

            summary1 = summary1 + key/*== null ? "Unclassified" :key */ + " => " + sciNotation.sciFormat(value) + "\n";

            System.out.println(key + " => " + value);
        }
        return summary1;
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            List<Node> nodes = getAvailableNode();
            System.out.println("X1 " + mousePt.x + " Y1 " + mousePt.y);
            if (e.getClickCount() == 2) {
                performAddEdit(false);
            } else if (e.isShiftDown()) {
                Node.selectToggle(nodes, mousePt);
            } else if (e.isPopupTrigger()) {
                Node.selectOne(nodes, mousePt);
                showPopup(e);
            } else if (Node.selectOne(nodes, mousePt)) {
                selecting = false;
            } else {
                Node.selectNone(nodes);
                selecting = true;
            }
            e.getComponent().repaint();
        }

        private void showPopup(MouseEvent e) {
            //control.popup.show(e.getComponent(), e.getX(), e.getY());
            control.popup.show(e.getComponent(), mousePt.x, mousePt.y);
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();
        List<Node> nodes = getAvailableNode();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                        Math.min(mousePt.x, e.getX()),
                        Math.min(mousePt.y, e.getY()),
                        Math.abs(mousePt.x - e.getX()),
                        Math.abs(mousePt.y - e.getY()));
                Node.selectRect(nodes, mouseRect);
            } else {
                delta.setLocation(
                        e.getX() - mousePt.x,
                        e.getY() - mousePt.y);
                Node.updatePosition(nodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

    public Point getMousePoint() {

        return mousePt;
    }

    public int getlevel() {
        int res = getMousePoint().x / widthPerLevel;

        return res;
    }

    public String getLevelString() {
        String res = "";

        try {
            res = levelHeadings.get(getlevel());
        } catch (IndexOutOfBoundsException e) {
        }

        return res;
    }
}
