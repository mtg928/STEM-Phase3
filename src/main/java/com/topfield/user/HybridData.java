/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.user;

/**
 *
 * @author Murali
 */
public class HybridData {
    
    private static String username = "SqlAdmin";
    private static String password = "sC0opYdoo928";
    private static String url = "jdbc:mysql://35.208.149.36:3306/tcl_systems?serverTimezone=UTC";
    
    /*private static String username ;
    private static String password;
    private static String url ;*/

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param aPassword the password to set
     */
    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * @return the url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * @param aUrl the url to set
     */
    public static void setUrl(String aUrl) {
        url = aUrl;
    }
    
}
