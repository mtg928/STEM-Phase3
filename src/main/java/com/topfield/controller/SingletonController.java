/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Murali
 */
public class SingletonController {
    
    private static RestTemplate restTemplate;
    
    public static RestTemplate getRestTemplate(){
    
        if (restTemplate== null) {
            restTemplate = new RestTemplate();
        }   
    return restTemplate;
    }
    
}
