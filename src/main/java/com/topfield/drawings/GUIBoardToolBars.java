/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.drawings;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.UIManager;


public class GUIBoardToolBars extends JToolBar {

    /**
     * 
     */
    private static final long serialVersionUID = 7241903583392004921L;


    GUIBoard _drawingBoard;
    //JToolBar _fileToolBar;

    public GUIBoardToolBars(GUIBoard ndb) {
        _drawingBoard=ndb;
        //_fileToolBar=new JToolBar();
    }

    public JToolBar addFileToolBar(){
        JToolBar fileToolBar=new JToolBar();
        fileToolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), getBorder()));
        fileToolBar.setFloatable(false);
        fileToolBar.setAlignmentX(0);
        UIManager.put("ToolTip.background", Color.TRANSLUCENT);


        fileToolBar.add(addNewComponent("New", new GUIBoardActions.NewNetwork(_drawingBoard), new ImageIcon("Icon/new.gif"))).setToolTipText("New");

        fileToolBar.add(addNewComponent("Open", new GUIBoardActions.OpenNetwork(_drawingBoard), new ImageIcon("Icon/open.gif"))).setToolTipText("Open");

        fileToolBar.add(addNewComponent("Save", new GUIBoardActions.SaveNetwork(_drawingBoard), new ImageIcon("Icon/save.gif"))).setToolTipText("Save");
        //JButton newFileToolBar=new JButton();
        //newFileToolBar.setActionCommand("newFileToolBar");
        //newFileToolBar.setIcon(new ImageIcon("Image/new.gif"));
        //newFileToolBar.setToolTipText("New");
        //fileToolBar.add(newFileToolBar);
        return fileToolBar;
    }

    public JToolBar addPowerElements(){
        JToolBar powerElements=new JToolBar(VERTICAL);
        powerElements.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3,3,3,3), getBorder()));
        powerElements.setFloatable(false);


        powerElements.add(addNewComponent("Valve", new GUIBoardActions.AddNewValve(_drawingBoard), new ImageIcon("Icon/valve1.png"))).setToolTipText("Valve");

        return powerElements;
    }

    @SuppressWarnings("serial")
    public Action addNewComponent(String name, Action componentAction, ImageIcon icon){

        AbstractAction newComponent=new AbstractAction(name,(icon!=null) ? icon : null){

            public void actionPerformed(ActionEvent e)
            {
                componentAction.actionPerformed(new ActionEvent(_drawingBoard.getGraphComponent(),e.getID(),e.getActionCommand()));
            }
        };
        icon.setDescription(name);      
        return newComponent;

    }

    public GUIBoardToolBars(int arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public GUIBoardToolBars(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public GUIBoardToolBars(String arg0, int arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

}
