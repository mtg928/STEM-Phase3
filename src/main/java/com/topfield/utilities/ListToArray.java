/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Murali
 */
public class ListToArray {
    
    
   public Object[][]  twoDListToTwoDArray(List<List<String>> dataList){
     Object[][] data2d = new Object[dataList.size()][];
     //Object[] data1d = new Object[dataList.size()][];
     
     int rowCount=0;
     //int colCount =0;
     
            for (List<String> list : dataList) {
                //data1d = dataList.toArray();
                data2d[rowCount] = list.toArray();
                System.out.println(rowCount);
                rowCount++;
            }
            
     return data2d;
    } 
     public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) 
    { 
  
        // Create a new ArrayList 
        ArrayList<T> newList = new ArrayList<T>(); 
  
        // Traverse through the first list 
        for (T element : list) { 
  
            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) { 
  
                newList.add(element); 
            } 
        } 
  
        // return the new list 
        return newList; 
    } 
}
