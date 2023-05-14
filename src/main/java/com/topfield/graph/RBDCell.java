/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import org.jgraph.graph.DefaultGraphCell;

/**
 * CustomCell that allows user to define an icon and a description for the
 * graph vertex.
 * 
 * @author Murali
 * @created Jun 28, 2020
 */
public class RBDCell extends DefaultGraphCell {
    
    private int cellId;
    private int calcId;
    
    private String id;

    private String description;
    
    private String vertexStyle;
            
    public static final String  RBD_VERTEX ="RBD"; 
    public static final String  CAPTION_VERTEX ="CAPTION";
    public static final String  VERTEX_GROUP ="GROUP";
    public static final String  CON_VERTEX ="CON";

    public RBDCell(String id, String description,String vertexStyle) {
            this.id = id;
            this.description = description;
            this.vertexStyle = vertexStyle;
    }
    
   public RBDCell(int cellId,String id, String description,String vertexStyle) {
            this.id = id;
            this.description = description;
            this.vertexStyle = vertexStyle;
            this.cellId= cellId;
            
    }

    public String getDescription() {
            return description;
    }

    public String getId() {
            return id;
    }

    /**
     * Sets the description on a cell. This is called from the multi-lined
     * editor.
     */
    public void setUserObject(Object obj) {
            if (obj != null && obj instanceof String) {
                this.setId(obj.toString());
            }
    }

    /**
     * Return the description of the cell so that it will be the initial
     * value of the in-graph editor.
     */
    public String toString() {
            return id;  //+"-"+cellId
    }    

    /**
     * @return the cellId
     */
    public int getCellId() {
        return cellId;
    }

    /**
     * @param cellId the cellId to set
     */
    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        
         this.description = description;
        
        if (!(this.vertexStyle.contentEquals(RBDCell.RBD_VERTEX))) {
          this.id =description;   
        } 
        
        
    }

    /**
     * @return the vertexStyle
     */
    public String getVertexStyle() {
        return vertexStyle;
    }

    /**
     * @param vertexStyle the vertexStyle to set
     */
    public void setVertexStyle(String vertexStyle) {
        this.vertexStyle = vertexStyle;
    }

    /**
     * @return the calcId
     */
    public int getCalcId() {
        return calcId;
    }

    /**
     * @param calcId the calcId to set
     */
    public void setCalcId(int calcId) {
        this.calcId = calcId;
    }
    
}
