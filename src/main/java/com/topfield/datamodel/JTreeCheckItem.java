/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.datamodel;

import com.swing.checkboxtree.CheckBoxNodeData;

/**
 *
 * @author Murali
 */
public class JTreeCheckItem extends CheckBoxNodeData{
    
    private int id;
    
    public JTreeCheckItem(int id,String text, boolean checked) {
        super(text, checked);
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
