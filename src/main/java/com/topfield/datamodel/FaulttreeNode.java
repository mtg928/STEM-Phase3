/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.datamodel;

import com.topfield.modal.Faultdata;
import com.topfield.utilities.ScientificNotation;

/**
 *
 * @author Murali
 */
public class FaulttreeNode {

    
    private ScientificNotation sciNotation = new ScientificNotation("0.00E0");
    
    private int id;
    private String compId;
    private String description;
    private String childGate;
    private String type;
    private String category;
    private String methods;
    private Double frequency;
    private Double probability;
    private Double time;
    private Integer parentId;
    private Integer clacId;
    private String reference;

    public FaulttreeNode(){
     
    }


    
    public FaulttreeNode(Faultdata faultdata) {
        this.id = faultdata.getFaultdataId();
        this.compId = faultdata.getName();
        this.description = faultdata.getDescription();
        this.type = faultdata.getNode();
        this.category = faultdata.getCategory();
        this.methods = faultdata.getMethods();
        //this.methods = type.contentEquals("Event")? faultdata.getComments():faultdata.getMethods();
        this.childGate = faultdata.getChildGate();
        this.frequency = faultdata.getFailurerate();
        this.probability = faultdata.getProbability();
        this.time = faultdata.getTime();
        this.parentId = faultdata.getParent()==null? -1 :faultdata.getParent().getFaultdataId();
        this.clacId = faultdata.getCalcComp().getCalcId();
    }
    
    public FaulttreeNode(String compId, String description,String type,String category,String methods, String childGate, Double frequency, Double probability, Double time,int parentId,Integer clacId) {
        this.compId = compId;
        this.description = description;
        this.type = type;
        this.category = category;
        this.methods = methods;
        this.childGate = childGate;
        this.frequency = frequency;
        this.probability = probability;
        this.time = time;
        this.parentId = parentId;
        this.clacId = clacId;
    }
    
    

    public FaulttreeNode(int id, String compId, String description,String type,String category,String methods, String childGate, Double frequency, Double probability, Double time,int parentId,Integer clacId) {
        this.id = id;
        this.compId = compId;
        this.description = description;
        this.type = type;
        this.category = category;
        this.methods = methods;
        this.childGate = childGate;
        this.frequency = frequency;
        this.probability = probability;
        this.time = time;
        this.parentId = parentId;
        this.clacId = clacId;
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

    /**
     * @return the compId
     */
    public String getCompId() {
        return compId;
    }

    /**
     * @param compId the compId to set
     */
    public void setCompId(String compId) {
        this.compId = compId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the childGate
     */
    public String getChildGate() {
        return childGate;
    }

    /**
     * @param childGate the childGate to set
     */
    public void setChildGate(String childGate) {
        this.childGate = childGate;
    }

    /**
     * @return the frequency
     */
    public Double getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    /**
     * @return the probability
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(Double probability) {
        this.probability = probability;
    }

    /**
     * @return the time
     */
    public Double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Double time) {
        this.time = time;
    }
    
     /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    
    

    @Override
    public String toString() {
       // return "FaulttreeNode{" + "id=" + id + ", compId=" + compId + ", description=" + description + ", childGate=" + childGate + ", frequency=" + frequency + ", probability=" + probability + ", time=" + time + '}';
       
        if (getType().contentEquals("Event")) {
            return InnnerContentAlignEvent(compId, description, frequency.toString(), probability.toString());
        } else {
            return InnnerContentAlignBasic(compId, description, getCategory(),frequency.toString(), probability.toString(),time.toString());
        }
 
    }
    
    
     public String InnnerContentAlignBasic(String id,String des,String type,String fre,String pro, String time){
         id = stringCutter(id, 10);
         des = stringCutter(des, 42);
         //String res=this.id+"-"+clacId+" "+"\n"+id+"\n";
         String res="\n"+id+"\n";
        String [] desSplit = DesSplitter(des, 14); //sciNotation.sciFormatString
        
        for (int i = 0; i < 4; i++) {
            if (i < desSplit.length ) {
                res = res+desSplit[i]+"\n";
            } else {
                res = res+"\n";
            }
            
        }

         res = res+"\n"+type+"\nF = "+sciNotation.sciFormatString(fre)+" /h \n P = "+sciNotation.sciFormatString(pro)+"    \n T = " +sciNotation.sciFormatString(time)+"  h";

        return res;
    }
    
    public String InnnerContentAlignEvent(String id,String des,String fre,String pro){
         id = stringCutter(id, 10);
         des = stringCutter(des, 28);
        //String res=this.id+" "+id+"\n\n";
        String res=id+"\n\n";
        
        String [] desSplit = DesSplitter(des, 14); //sciNotation.sciFormatString
        
        for (String string : desSplit) {
            res = res+string+"\n";
        }
        
        res = res+"F = "+sciNotation.sciFormatString(fre)+" /h \n P = "+sciNotation.sciFormatString(pro);

        return res;
    }
        
    public String[] DesSplitter(String des,int splitLength){
        
            String[] res;
        
            if (des.length() >= splitLength) {
                res = des.split("(?<=\\G.{" + splitLength + "})");
            } else {
                res = new String[]{des};
            }
        
            return res;
    }
    
    public String stringCutter(String des, int limit){
    
        if (des.length()> limit) {
            des = des.substring(0,(limit-3))+"...";
        } 
    
    return des;
    }

    /**
     * @return the parentId
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the clacId
     */
    public Integer getClacId() {
        return clacId;
    }

    /**
     * @param clacId the clacId to set
     */
    public void setClacId(Integer clacId) {
        this.clacId = clacId;
    }

   
    /**
     * @return the methods
     */
    public String getMethods() {
        return methods;
    }

    /**
     * @param methods the methods to set
     */
    public void setMethods(String methods) {
        this.methods = methods;
    }
    
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
}
