/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.drawings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class GUIBoard extends JPanel {

    private mxGraph _graph;
    private mxGraphComponent _graphComponent;
    private JPanel _panel;
    private JFrame _frame;
    private ArrayList<Valve> _valves;

    public GUIBoard(JFrame frame) {
        // TODO Auto-generated constructor stub
        _frame = frame;
        initialize();
    }

    private void initialize() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.Y_AXIS));

        addToRegistry("Images/valve.shape");
        /**
         * Define a new object to be saved in the Model
         */
        mxCodecRegistry.addPackage(Valve.class.getPackage().toString());
        mxCodecRegistry.register(new mxObjectCodec(new Valve()));

        _graph = new mxGraph() {

            public boolean isCellFoldable(Object cell, boolean collapse) {
                return false;
            }

            public String convertValueToString(Object cell) {
                Object value = null;
                if (cell instanceof mxCell) {
                    value = ((mxCell) cell).getValue();

                    if (value instanceof Valve) {
                        Valve v = (Valve) value;

                        return ((Valve) value).getValveName();

                    }
                }

                return super.convertValueToString(value);
            }

            public boolean isCellResizable(Object cell) {
                return !getModel().isVertex(cell);
            }
        };

        _graph.addListener(mxEvent.MOVE_CELLS, new mxIEventListener() {
            @Override
            public void invoke(Object sender, mxEventObject mxevt) {

                Object[] cells = (Object[]) mxevt.getProperty("cells");
                Point x = (Point) mxevt.getProperty("location");
                Double dx = (Double) mxevt.getProperty("dx");
                Double dy = (Double) mxevt.getProperty("dy");
            }
        });

        setDefaultConnector();

        _graphComponent = new mxGraphComponent(_graph);
        _graphComponent.setPreferredSize(new Dimension(600, 400));
        this.add(_graphComponent);

        _graph.setVertexLabelsMovable(true);
        _graph.setDisconnectOnMove(false);
        _graph.setConnectableEdges(false);
        _graph.setCellsEditable(false);
        _graph.setResetEdgesOnMove(true);

        _valves = new ArrayList<Valve>();

        _panel.add(new GUIBoardToolBars(this).addFileToolBar());
        _frame.add(new GUIBoardToolBars(this).addPowerElements(), BorderLayout.EAST);
        _frame.add(_panel, BorderLayout.NORTH);

    }

    public mxGraph getGraph() {

        return _graph;
    }

    public mxGraphComponent getGraphComponent() {

        return _graphComponent;

    }

    private void setDefaultConnector() {
        Map<String, Object> connector = new HashMap<String, Object>();
        connector.put(mxConstants.STYLE_ROUNDED, false);
        connector.put(mxConstants.STYLE_ORTHOGONAL, true);
        connector.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");
        connector.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        connector.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);

        mxStylesheet connectorStyle = new mxStylesheet();
        connectorStyle.setDefaultEdgeStyle(connector);
        _graph.setStylesheet(connectorStyle);
    }

    public void addValves(Valve valve) {

        _valves.add(valve);
    }

    public ArrayList<Valve> getValves() {

        return _valves;
    }

    public void addToRegistry(String element) {
        String nodeXml, objectName;
        mxStencilShape newShape;
        try {
            nodeXml = mxUtils.readFile(element);
            newShape = new mxStencilShape(nodeXml);
            objectName = newShape.getName();
            mxGraphics2DCanvas.putShape(objectName, newShape);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public GUIBoard(LayoutManager arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public GUIBoard(boolean arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public GUIBoard(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame("Swing");
        frame.add(new GUIBoard(frame));
        // Display the frame
        frame.setVisible(true);
    }
}
