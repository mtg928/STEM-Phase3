/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import java.awt.Color;
import static java.awt.Color.gray;

/**
 *
 * @author Murali
 */
public class FaulttreeEquations {
    
    public final static boolean AND =true;
    public final static boolean OR =false;
    
    public static double getProbability(double probA, double probB,boolean gate ){
     double res =0.0;
     
        if (gate) {
            res = probA*probB;
        } else {
            res = (probA+probB) -(probA*probB);
        }
     
       return res;
    }
    
    
    public static double getFrequency(double probA, double freB,boolean gate ){
     double res =0.0;
     
        System.out.println("probA - "+probA+" probB - "+freB+" gate - "+gate);
        if (gate) {
            res = probA*freB;
        } 
     
       return res;
    }
}
