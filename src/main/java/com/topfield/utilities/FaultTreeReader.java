/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.topfield.datamodel.FTALogan;
import com.topfield.modal.Faultdata;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Murali
 */
public class FaultTreeReader {
    

    public ArrayList<FTALogan> getLogonFTA(){
    
         ArrayList<FTALogan> fTALogan = new ArrayList<FTALogan>();
         FTALogan node;
         String childs[];
         int count=0;

        //String fileName = "G:/Work Documents/Rev A/logan files/FTA for PFD of EB CCU SIL0 27.01.17.gte"; // The name of the file to open.  
       //String fileName = "G:/Work Documents/Logan Test/Safety loop (EB) Rev A.gte"; // The name of the file to open.  
       //String fileName = "G:/Work Documents/Logan Test/DoorLoop.gte"; // The name of the file to open.
       String  fileName =FileSelector.getPathSelectedFile(new String[]{"gte"});
       String line = null; // This will reference one line at a time

        try {
           
            FileReader fileReader = new FileReader(fileName);  // FileReader reads text files in the default encoding.
            BufferedReader bufferedReader = new BufferedReader(fileReader); // Always wrap FileReader in BufferedReader.
            count=0;
            
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                
                String[] splited = line.split("\\s+");
                
               // System.out.println(" splited "+ splited.length);
                
                if(splited.length >0 && !splited[0].equals("***") ){ 
                    
                   // System.out.println(" splited ffffffffffffffff "+ splited.length);
                
                    node = new FTALogan();
                    node.setId(count);
                    node.setHeaders(splited[0]);
                    node.setChildCount(Integer.parseInt(splited[1]));
                    node.setLogicCode(Integer.parseInt(splited[2]));
                    
                    childs = new String[node.getChildCount()];

                    for (int i = 3; i < splited.length; i++) {
                        childs[i-3] =splited[i];
                    }

                    node.setChild(childs);
                    fTALogan.add(node);
                    
                    //System.out.println("id -"+count);
                count++;
                
                }
                
            }   

            // Always close files.
            bufferedReader.close();     
            
            
           
            
            
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    
      return fTALogan;
    
    }
    
    public ArrayList<FTALogan> getLogonFTAFromDB(List<Faultdata> data){
    
         ArrayList<FTALogan> fTALogan = new ArrayList<FTALogan>();
         FTALogan node;
         String childs[];
         int count=0;


            count=0;
            
            
            for (Faultdata faultdata : data) {
                
                    try {

                     node = new FTALogan();
                     node.setId(faultdata.getFaultdataId());
                     node.setHeaders(faultdata.getName());
                     node.setLogicCode(faultdata.getChildGate().equals("OR") ? 1 : 2);
                        
                        // System.out.println("childs - **************"+faultdata.getChild());
                     if(faultdata.getChild() != null && (!faultdata.getChild().equals(""))){
                          childs =faultdata.getChild().split("\\#");
                          System.out.println("childs - "+childs.length);

                              //if(!(faultdata.getChild().equals("") || faultdata.getChild()== null)){
                            if(childs.length != 0){

                               //node.setId(count); commented and Replace below for Id change from Node Name
                               node.setChildCount(childs.length);
                               /*for (int i = 3; i < childs.length; i++) {
                                   childs[i-3] =childs[i];
                               }*/
                               node.setChild(childs);
                               //System.out.println("id -"+count);
                               fTALogan.add(node);
                           }
                      }else{
                               node.setChildCount(0);
                               //node.setChild(new String[]{});
                      }
                     
                       /* if (data.size()==1) {
                            fTALogan.add(node);
                        }*/
                     
                     count++;

                    } catch (Exception e) {
                        //System.out.println("Error - "+e); 
                        e.printStackTrace();
                    }
               
                }


        
    
      return fTALogan;
    
    }
    
    
   
    
     public static void main(String [] args) {
        
         FaultTreeReader ftr = new FaultTreeReader();
    
          for (FTALogan fTALogan1 : ftr.getLogonFTA()) {
                for (String child :  fTALogan1.getChild()) {
                  //  System.out.println(" child - "+child);
                  
                   
                }
                //System.out.println(" ---------------------------------------- "+fTALogan1.getId());
                 System.out.println("Id - "+fTALogan1.getId()+" Header - "+fTALogan1.getHeaders());
            }
         
         
    }
    
    
    
    
}
