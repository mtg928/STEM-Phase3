/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.help;


/**
 *
 * @author Murali
 */
public class StemsData {
    
    private static StemsData stemsData;
    private static String softwareName="Safety Transport Engineering Management"; //Railway Safety Engineering tool
    private static String softwareVersion="1.6.9";
    
    
     private  StemsData(){
     
     
     }
    
     public static StemsData getInstance() 
    { 
        if (stemsData == null) 
            stemsData = new StemsData(); 
  
        return stemsData; 
    } 

    /**
     * @return the softwareName
     */
    public static String getSoftwareName() {
        return softwareName;
    }

    /**
     * @return the softwareVersion
     */
    public static String getSoftwareVersion() {
        return softwareVersion;
    }
     
     
     
}
