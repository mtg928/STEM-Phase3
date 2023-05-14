/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

 
public class EdStatusBar extends StatusBarGraphListener {

        /**
         *
         */
        protected JLabel leftSideStatus;

        /**
         * contains the scale for the current graph
         */
        protected JLabel rightSideStatus;

        /**
         * Constructor for GPStatusBar.
         *
         */
        public EdStatusBar() {
            super();
            // Add this as graph model change listener
            setLayout(new BorderLayout());
            leftSideStatus = new JLabel();
            rightSideStatus = new JLabel("0/0Mb");
            leftSideStatus.setBorder(BorderFactory.createLoweredBevelBorder());
            rightSideStatus.setBorder(BorderFactory.createLoweredBevelBorder());
            add(leftSideStatus, BorderLayout.CENTER);
            add(rightSideStatus, BorderLayout.EAST);
        }

        protected void updateStatusBar() {
            Runtime runtime = Runtime.getRuntime();
            int freeMemory = (int) (runtime.freeMemory() / 1024);
            int totalMemory = (int) (runtime.totalMemory() / 1024);
            int usedMemory = (totalMemory - freeMemory);
            String str = (usedMemory / 1024) + "/" + (totalMemory / 1024)
                    + "Mb";
            rightSideStatus.setText(str);
        }

        /**
         * @return Returns the leftSideStatus.
         */
        public JLabel getLeftSideStatus() {
            return leftSideStatus;
        }

        /**
         * @param leftSideStatus The leftSideStatus to set.
         */
        public void setLeftSideStatus(JLabel leftSideStatus) {
            this.leftSideStatus = leftSideStatus;
        }

        /**
         * @return Returns the rightSideStatus.
         */
        public JLabel getRightSideStatus() {
            return rightSideStatus;
        }

        /**
         * @param rightSideStatus The rightSideStatus to set.
         */
        public void setRightSideStatus(JLabel rightSideStatus) {
            this.rightSideStatus = rightSideStatus;
        }
    }
