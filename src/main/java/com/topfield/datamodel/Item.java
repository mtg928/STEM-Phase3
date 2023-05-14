/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.datamodel;

import java.io.Serializable;

/**
 *
 * @author Murali
 */
public class  Item implements Serializable, Comparable<Item>  {

  private int id;
  private String description;

  public Item(int id, String description) {
    this.id = id;
    this.description = description;
  }
  


  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return description;
  }
  
  @Override
    public boolean equals(Object o) { 
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Item)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Item c = (Item) o; 
          
        // Compare the data members and return accordingly  
        return Integer.compare(id, c.id) == 0
                && description.compareTo(c.description) == 0; 
    }

    @Override
    public int compareTo(Item o) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     Item c = (Item) o; 
     
        return Integer.compare(id, c.id);
               // && description.compareTo(c.description) == 0; 
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }



}