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
public class Equations {
    
    
    public static double EvacuationTime(int noOfPassengers,double egress,double doorWidth,double availableDoors,double doorOpeningTime){
        Double res= 0.0; 
    
        try {
            res = (noOfPassengers/((egress/60)*doorWidth*availableDoors))+doorOpeningTime;
        } catch (Exception e) {
        }
        
    
       return res;
    }
    
    
    public Double PFD_AVG(Double lamda,Double saftyFactor,Double proofTestInterval,int voting ){
        
        System.out.println("PFD_AVG -"+lamda*saftyFactor+" "+proofTestInterval+"voting-"+voting);
      Double pfd_val=0.0;
        
      switch (voting) {
            case 0:
                pfd_val = ((lamda*saftyFactor) * proofTestInterval*730)/2;
                break;
            case 1:
                pfd_val = Math.pow(((lamda*saftyFactor) * proofTestInterval*730)/2, 2);
                break;
            case 2:
                pfd_val = ((lamda*saftyFactor) * proofTestInterval*730)/2;
                break;
            default:
                break;
        }
       
    
     return pfd_val;
    }
    
    
    
   public Double Criticality_Mode_Number(Double fail_Eff_Prob,Double fail_Mode_Ratio,Double failure_Rate,Double operating_Time){
    
     Double res =0.0;
    
      res = fail_Eff_Prob*fail_Mode_Ratio*failure_Rate*operating_Time;
    
    return res;
    } 
    
    public Double Criticality_Number_item(Double criticality_Mode_Number , int []cri_Mode_Pro ){
    
     Double res =0.0;
    
        for (int i = 0; i <cri_Mode_Pro.length; i++) {
            res =res+(criticality_Mode_Number*cri_Mode_Pro[i])/100;
            System.out.println("res - "+res);
        }
    
     return res;
    } 
    
    
    
}
