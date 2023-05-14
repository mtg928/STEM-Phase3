/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

/**
 *
 * @author Murali
 */
public class ComponentFunctions {
    
    public static String getSubSystemBreakDownCode(String subComCode,String mainComCode,int compId){
    
     return "TS_RST_"+mainComCode+subComCode+"_"+compId;
    }
    
}
