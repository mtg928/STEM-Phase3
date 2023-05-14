/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.datamodel;

import javax.persistence.Id;

/**
 *
 * @author Murali
 */
public class FTALogan {
    
    @Id
    private int id;
    private String headers;
    private int childCount;
    private int logicCode;
    private String[] child;
    private Object node;
    private int parentId;
    
    
    
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

    /**
     * @return the headers
     */
    public String getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * @return the childCount
     */
    public int getChildCount() {
        return childCount;
    }

    /**
     * @param childCount the childCount to set
     */
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    /**
     * @return the logicCode
     */
    public int getLogicCode() {
        return logicCode;
    }

    /**
     * @param logicCode the logicCode to set
     */
    public void setLogicCode(int logicCode) {
        this.logicCode = logicCode;
    }

    /**
     * @return the child
     */
    public String[] getChild() {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(String[] child) {
        this.child = child;
    }

    /**
     * @return the node
     */
    public Object getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(Object node) {
        this.node = node;
    }

    /**
     * @return the parentId
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

   
    
    
    
    
}
