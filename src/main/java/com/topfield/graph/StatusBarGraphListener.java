/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import javax.swing.JPanel;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;

    public class StatusBarGraphListener extends JPanel implements GraphModelListener {

        /**
         * Graph Model change event
         */
        public void graphChanged(GraphModelEvent e) {
            updateStatusBar();
        }

        protected void updateStatusBar() {

        }
    }
