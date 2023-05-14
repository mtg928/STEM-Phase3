/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

/**
 *
 * @author Murali
 */
public class UniqueArray {
    /**
     * Return true if number num is appeared only once in the
     * array num is unique.
     */
    public static boolean isUnique(int[] array, int num) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) {
                return false;
            }
        }
        return true;
    }
    
    
    public static boolean isUnique(String [] array, String val) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(val)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert the given array to an array with unique values
     * without duplicates and returns it.
     */
    public static int[] toUniqueArray(int[] array) {
        int[] temp = new int[array.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = -1; // in case u have value of 0 in the array
        }

        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i]))
                temp[counter++] = array[i];
        }

        int[] uniqueArray = new int[counter];
        System.arraycopy(temp, 0, uniqueArray, 0, uniqueArray.length);
        return uniqueArray;
    }
    
    public static String [] toUniqueArray(String[] array) {
        String[] temp = new String[array.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = "-1"; // in case u have value of 0 in the array
        }

        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (isUnique(temp, array[i]))
                temp[counter++] = array[i];
        }

        String[] uniqueArray = new String[counter];
        System.arraycopy(temp, 0, uniqueArray, 0, uniqueArray.length);
        return uniqueArray;
    }

    /**
     * Print given array
     */
    public static void printArray(Object [] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }

        System.out.println("");
    }
    
    
    
    

    public static void main(String[] args) {
       // int[] array = {1, 1, 2, 3, 4, 1, 4, 7, 9, 7};
       // printArray(array);
       // printArray(toUniqueArray(array));
       
         String [] array = {"1", "1", "2", "3", "4", "1", "4", "7", "9", "7"};
         printArray(array);
         printArray(toUniqueArray(array));
       
       
    }
}
