/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import com.topfield.utilities.StringFuntions;

/**
 *
 * @author Murali
 */
public class ErrorController {
    
    
    public static String formatError(String errorMassage){
     String res="";
        try {
            res = errorMassage.split("\\:")[0];
            
            if(res.contentEquals("Cannot delete or update a parent row")){
              res ="The row prevent from deleted due to the child rows found";
            }
            
        } catch (Exception e) {
            res = errorMassage;
        }
     
       // res = StringFuntions.MultipleLine(res, 2);
     
     return res;
    }
    
    
}
