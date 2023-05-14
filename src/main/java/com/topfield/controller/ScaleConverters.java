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
public class ScaleConverters {
    
    
    public double mttrPeriodConversion(String periods,double value){
      double hours= 0;
     
        switch (periods) {
            case "hr":
                hours = value;
                break;
            case "days":
                hours = value*24;
                break;
            case "weeks":
                hours = value*168;
                break;
            default:
                break;
        }
        
        
      return hours;
    }
    
    
    public double ptpPeriodConversion(String periods,double value){
      double months= 0;
        
        switch (periods) {
            case "month(s)":
                months = value;
                break;
            case "year(s)":
                months = value*12;
                break;
            default:
                break;
        }
        
        
      return months;
    }
    
}
