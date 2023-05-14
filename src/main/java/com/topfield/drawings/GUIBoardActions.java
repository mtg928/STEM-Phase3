/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.drawings;

/**
 *
 * @author Murali
 */
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;


public class GUIBoardActions {

    public GUIBoardActions() {
        // TODO Auto-generated constructor stub
    }

    public static class NewNetwork extends AbstractAction {

        private GUIBoard _newNDB;
        public NewNetwork(GUIBoard ndb) {
            _newNDB=ndb;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }

    }

    public static class OpenNetwork extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = 8166859573134106478L;
        private GUIBoard _openNDB;
        private Document document;
        mxCodec codec ;
        public OpenNetwork(GUIBoard ndb) {
            _openNDB=ndb;
            mxCodecRegistry.addPackage(Valve.class.getPackage().toString());
            mxCodecRegistry.register(new mxObjectCodec(new Valve()));

        }

        @Override
        public void actionPerformed(ActionEvent aevt) {
            mxCodecRegistry.addPackage(Valve.class.getPackage().toString());
            mxCodecRegistry.register(new mxObjectCodec(new Valve()));

            try {
                document = mxXmlUtils.parseXml(mxUtils.readFile("C:\\test graph/file.xml"));
                codec = new mxCodec(document);
                codec.decode(document.getDocumentElement(), _openNDB.getGraph().getModel());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            _openNDB.getGraph().getModel().beginUpdate();
            Object[] cells;
            try
            {
                cells= _openNDB.getGraph().getChildCells(_openNDB.getGraph().getDefaultParent(), true, true);

            }
            finally
            {
                _openNDB.getGraph().getModel().endUpdate();
            }

            _openNDB.getGraph().addCells(cells);
            for (Object c : cells) {
                mxCell cell = (mxCell) c;
                if (cell.isVertex()) {
                    if (cell.getValue() instanceof Valve) {
                        Valve valve=(Valve) cell.getValue();
                        _openNDB.addValves(valve);

                    }
                }
            }

        }

    }


    public static class SaveNetwork extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = 4757526430199667311L;
        private GUIBoard _saveNDB;

        public SaveNetwork(GUIBoard ndb) {
            _saveNDB=ndb;
        }

        @Override
        public void actionPerformed(ActionEvent aevt) {
            mxCodec codec = new mxCodec();
            try {

                String xml2 = mxUtils.getPrettyXml(codec.encode(_saveNDB.getGraph().getModel()));

                mxUtils.writeFile(xml2, "C:\\test graph/file.xml");


            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   
        }

    }

    public static class AddNewValve extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = 4187443683391653845L;
        private GUIBoard _valveNDB;
        private MouseMotionListener _boardMotionListener;
        private MouseListener _boardListener;

        public AddNewValve(GUIBoard ndb){
            _valveNDB=ndb;

        }

        @Override
        public void actionPerformed(ActionEvent aevt) {

            Valve v = new Valve();
            v.setValveNumber(_valveNDB.getValves().size()+1);
            v.setValveName("V"+v.getValveName());
            v.setValveStyleName("VALVE"+v.getValveNumber());
            v.setValveStyle(_valveNDB.getGraph());            

            _valveNDB.addValves(v);

            mxCell vCell;
            _valveNDB.getGraph().getModel().beginUpdate();
            try
            {
                vCell=(mxCell) _valveNDB.getGraph().insertVertex(_valveNDB.getGraph().getDefaultParent(), null, v, MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y, 80, 30,v.getValveStyleString());

            }
            finally
            {
                _valveNDB.getGraph().getModel().endUpdate();
            }

            vCell.setAttribute("Name", v.getValveName());
            vCell.setConnectable(false);


            _valveNDB.getGraphComponent().getGraphControl().addMouseMotionListener(_boardMotionListener=new MouseMotionAdapter() {


                @Override
                public void mouseDragged(MouseEvent mevt) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseMoved(MouseEvent mevt) {
                    vCell.setGeometry(new mxGeometry(mevt.getX(),mevt.getY(),80,30));
                    _valveNDB.getGraph().refresh();

                }

            });

            _valveNDB.getGraphComponent().getGraphControl().addMouseListener(_boardListener=new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent mevt) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent mevt) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(MouseEvent mevt) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent mevt) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent mevt) {
                    _valveNDB.getGraphComponent().getGraphControl().removeMouseListener(_boardListener);
                    _valveNDB.getGraphComponent().getGraphControl().removeMouseMotionListener(_boardMotionListener);

                }
            });

        }       
    }
    
}
