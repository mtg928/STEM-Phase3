/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

// This will change the source of the actionevent to graph.

import com.topfield.utilities.ImageConversion;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import org.jgraph.JGraph;

  public class EventRedirector extends AbstractAction {

        protected JGraph graph;  // JGraph instance
        protected Action action;

        // Construct the "Wrapper" Action
        public EventRedirector(Action a,JGraph graph) {
            //super("", (ImageIcon) a.getValue(Action.SMALL_ICON));
            super("", new ImageIcon());
            this.action = a;
            this.graph = graph;
        }
        
          // Construct the "Wrapper" Action
        public EventRedirector(Action a,JGraph graph,URL url) {
            //super("", (ImageIcon) a.getValue(Action.SMALL_ICON));
            //super("", new ImageIcon(url));
            super("",  ImageConversion.SetImageSize(url));
            this.action = a;
            this.graph = graph;
        }

        // Redirect the Actionevent
        public void actionPerformed(ActionEvent e) {
            e = new ActionEvent(graph, e.getID(), e.getActionCommand(), e
                    .getModifiers());
            action.actionPerformed(e);
        }
    }
