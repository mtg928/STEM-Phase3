/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import com.topfield.utilities.StringFuntions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 *
 * @author Murali
 */
public class HazardsBuilder {
    private StringFuntions sf = new StringFuntions();
    
    public HazardsBuilder() {
     //super("Hello, World!");
  
   }
    
    
    public Object[] CreateVertexAndEdge(Object [] parent,Object root,int level1Count,String [] VertexContent,String EdgeContent ,int[] omitted,mxGraph graph,int levelAlignX,int levelAlignY,int borderWidth){
        
        Object levelObj[] = null;
   
         levelObj = CreateVertex(level1Count,VertexContent,omitted, graph, levelAlignX, levelAlignY,borderWidth);
         
         if (parent != null){
             CreateEdge(level1Count,EdgeContent, graph, parent, levelObj, root,omitted);
         }
       
        
        return levelObj;
    }
    
    
    public Object[] CreateVertex(int levelCount,String[] content,int[] omitted,mxGraph graph,int levelAlignX,int levelAlignY,int borderWidth){
    
        Object levelObj[] = new Object[levelCount];
        
        for (int i = 0; i < levelCount; i++) {
           if(!OmittedCheck(omitted, i)){ 
           levelObj[i] = graph.insertVertex(null, null,sf.MultipleLine(content[i],35), levelAlignX, (levelAlignY*i+10),200, 100,mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"+mxConstants.STYLE_STROKEWIDTH+"="+borderWidth);
           }
        }
     return levelObj;
    }
    
    public void CreateEdge(int levelCount,String linkText,mxGraph graph,Object[] child,Object[] parent,Object root,int[] ommitedRows){
    
      if(root != null){
          for (int i = 0; i < child.length; i++) {
              if(!OmittedCheck(ommitedRows, i)){ 
              graph.insertEdge(null, null,linkText , root, child[i]);
              }
          }  
      }else if (child.length == parent.length ){
         for (int i = 0; i < child.length; i++) {
              if(!OmittedCheck(ommitedRows, i)){
              graph.insertEdge(null, null, linkText, parent[i], child[i]);
              }
          }
      }else{
          System.out.println("Child Parent Miss Match Error");
      }
    
    }
    
    public boolean  OmittedCheck(int[] ommitedRows, int checkRows ){
        boolean res = false;
        
        for (int i = 0; i < ommitedRows.length; i++) {
            
            if(ommitedRows [i] == (checkRows+1) ){
               res = true;
               break;
            }
        }
        
       return  res; 
    }
    
    public void EdgeParentSetter(Object [] Parent, Object [] child, int[] ommited){
    
        for (int i = 0; i < child.length; i++) {
            
            if(OmittedCheck(ommited, i)){
                child[i] = Parent[i];
            }
            
        }
    
    }
    
}
