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
public class TclSession {
    
    private static String username = "bala";
    private static String projectName ="Train Project";
    private static Integer projectId = 1;

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
     * @return the projectName
     */
    public static String getProjectName() {
        return projectName;
    }

    /**
     * @param aProjectName the projectName to set
     */
    public static void setProjectName(String aProjectName) {
        projectName = aProjectName;
    }

    /**
     * @return the projectId
     */
    public static Integer getProjectId() {
        return projectId;
    }

    /**
     * @param aProjectId the projectId to set
     */
    public static void setProjectId(Integer aProjectId) {
        projectId = aProjectId;
    }
    
}
