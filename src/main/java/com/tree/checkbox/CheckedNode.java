/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tree.checkbox;



    // Defining data structure that will enable to fast check-indicate the state of each node
    // It totally replaces the "selection" mechanism of the JTree
   public class CheckedNode {
        private int id;
	private String text;
        private boolean enabled =true;
        boolean isSelected;
        boolean hasChildren;
        boolean allChildrenSelected;

        public CheckedNode(int id, String text,boolean isSelected_,boolean enabled, boolean hasChildren_, boolean allChildrenSelected_) {
            this.id = id;
            this.text = text;
            isSelected = isSelected_;
            this.enabled = enabled;
            hasChildren = hasChildren_;
            allChildrenSelected = allChildrenSelected_;
        }
    }
