/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.datamodel.FTALogan;
import java.util.ArrayList;

/**
 *
 * @author Murali
 */
public class FaulttreeCurd {
    
    
        public FTALogan getByHeaderId(ArrayList<FTALogan> fTALogan , int id){
    
        FTALogan res = null;
        
        for (FTALogan fTALogan1 : fTALogan) {
            
            if( fTALogan1.getId() == id){
            
              res = fTALogan1;
            }
            
        }
    
      /*  if(res != null){
        System.out.println(" getByHeaderName "+ res.getHeaders());
        }*/
        
      return res;        
    }
    
        
        
    
    
    
     public FTALogan getByHeaderName(ArrayList<FTALogan> fTALogan , String headerName){
    
        FTALogan res = null;
        FTALogan resRef = null;
        
        for (FTALogan fTALogan1 : fTALogan) {
            
            if(headerName.equals(fTALogan1.getHeaders())  ){
            
              res = fTALogan1;
            }/*else if(headerName.equals(fTALogan1.getHeaders())&& fTALogan1.getParentId() == 0){
            
             resRef = fTALogan1;
             resRef.setHeaders(headerName+"_");
             
             res = resRef;
            
            }*/
            
        }
    
      /*  if(res != null){
        System.out.println(" getByHeaderName "+ res.getHeaders());
        }*/
        
      return res;        
    }
    
    public FTALogan getByHeaderName(ArrayList<FTALogan> fTALogan , String headerName,int parentId ){
    
        FTALogan res = null;
        
        for (FTALogan fTALogan1 : fTALogan) {
            
            if(headerName.equals(fTALogan1.getHeaders()) && fTALogan1.getParentId() ==parentId ){
            
              res = fTALogan1;
            }
            
        }
    
      /*  if(res != null){
        System.out.println(" getByHeaderName "+ res.getHeaders());
        }*/
        
      return res;        
    }
    
    
   public void setCreatedNodeById(ArrayList<FTALogan> fTALogan ,int fTALoganId,int parentId,Object node){
    
     //FTALogan logan= getByHeaderName(fTALogan, headerName,parentId);
     FTALogan logan= getByHeaderId(fTALogan, fTALoganId); // Name to Id
        
        if(logan != null && logan.getParentId() ==parentId && logan.getNode() == null ){
        
          logan.setNode(node);
        }
    
    }
    
    
    public void setCreatedNode(ArrayList<FTALogan> fTALogan , String headerName,int parentId,Object node){
    
     FTALogan logan= getByHeaderName(fTALogan, headerName,parentId);
        
        if(logan != null && logan.getParentId() ==parentId && logan.getNode() == null ){
        
          logan.setNode(node);
        }
    
    }
    
    
    public ArrayList<FTALogan> orderArrayList(ArrayList<FTALogan> fTALogan, boolean imports,FaultTreeCalculator parent){
    
      ArrayList<FTALogan> fTAL  = new ArrayList<FTALogan>();
      FTALogan frist;
      FTALogan second;
      int twistIndex3=0;
      int countIndex3=0;
      
      second = fTALogan.get(0);
      
                    frist= new FTALogan();
                    //frist.setId(countIndex3);
                    frist.setId(second.getId());
                    frist.setHeaders(second.getHeaders());
                    frist.setChild(second.getChild());
                    frist.setChildCount(second.getChildCount());
                    frist.setLogicCode(second.getLogicCode());
                    frist.setNode(second.getNode());
                    frist.setParentId(second.getParentId());
   
        countIndex3++;
      
         fTAL.add(frist);
         System.out.println(" -----------------------------------------------");
         
         for (int i = 0; i < fTALogan.size(); i++) {
             //System.out.println("twistIndex - "+i+" "+fTAL.get(i).getHeaders());
            
             
             try {
                countIndex3= childInsert(fTALogan, fTAL.get(i), fTAL,countIndex3,imports);
                if(imports){
                FaultTreeData ft=   (FaultTreeData)parent.getComponent(1);
                ft.UpdateFaultTreeGraph(fTALogan.get(i).getHeaders(),fTALogan.get(i).getLogicCode(), fTALogan.get(i).getChild(),i == 0);
                }
             } catch (IndexOutOfBoundsException e) {
                 
              /*   for (FTALogan fTALogan1 : fTAL) {
                     System.out.println("fTALogan1 - "+fTALogan1.getHeaders());
                 }*/
             }
            
         }
      
              /*   for (FTALogan fTALogan1 : fTAL) {
                     System.out.println("fTALogan1 - "+fTALogan1.getHeaders()+"-----"+fTALogan1.getParentId());
                 }*/
      
      
      
      return fTAL;
    
    }
    
    public int childInsert(ArrayList<FTALogan> input,FTALogan log,ArrayList<FTALogan> output, int count,boolean imports){
    
        FTALogan logan =null;
        FTALogan loganRef ;
        int mycount = count;
        
        for (String string : log.getChild()) {
            
            try {
                if (imports) {
                     logan = getByHeaderName(input, string); // Name to Id
                }else{
                     logan = getByHeaderId(input, Integer.parseInt(string));
                }
                
                if (logan != null) {
                    
                    loganRef= new FTALogan();
                    //loganRef.setId(mycount); // Name to Id
                    loganRef.setId(logan.getId());
                    loganRef.setHeaders(logan.getHeaders());
                    loganRef.setChild(logan.getChild());
                    loganRef.setChildCount(logan.getChildCount());
                    loganRef.setLogicCode(logan.getLogicCode());
                    loganRef.setNode(logan.getNode());
                    loganRef.setParentId(log.getId());
                    
                    output.add(loganRef);
                    mycount++;
                    //System.out.println(output.size()+")"+logan.getHeaders()+" ------"+log.getId());
                }
                
            } catch (NullPointerException e) {
                System.out.println("Child Not Found "+string);
            }
            
            
            
        }
    
        /*
        for (FTALogan fTALogan : output) {
            System.out.println(" fTALogan - "+fTALogan.getHeaders());
        }*/
    
        return mycount;
    }
    
    
    
    public ArrayList<FTALogan> removeDuplicate(ArrayList<FTALogan> fTALogan ){ 
    
        ArrayList<FTALogan> fTAL  = new ArrayList<FTALogan>();
        FTALogan temp = new FTALogan();
        int dupCount =1;
        int couunt =0;
        
        for (FTALogan fTALogan1 : fTALogan) {
            
            if(fTAL.contains(fTALogan1)){
                
                temp = fTALogan1;
                temp.setHeaders(fTALogan1.getHeaders()+"_"+dupCount);
                fTAL.add(temp);
            
             dupCount++;
             
                System.out.println("dupCount " + dupCount);
            }else{
            
            
              fTAL.add(fTALogan1);
            }
            
        }
        
    
    
        
        return fTAL;
    
    }
    
}
