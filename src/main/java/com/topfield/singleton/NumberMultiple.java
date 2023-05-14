/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

/**
 *
 * @author Murali
 */
public class NumberMultiple {
   public static double findclosedNumber(double num){
     double res=0.0;
     int mult;
     
        if (num>0) {
                 mult = (int)(num/30);
     
                 res = 30*(mult+1);
        }

     
     return res;   
    }
   
   
   public static void main(String[] args) {
        System.out.println("findclosedNumber -"+findclosedNumber(0));
        System.out.println("findclosedNumber -"+findclosedNumber(20));
        System.out.println("findclosedNumber -"+findclosedNumber(50));
        System.out.println("findclosedNumber -"+findclosedNumber(70));
    }
}
