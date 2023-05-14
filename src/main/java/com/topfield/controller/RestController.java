/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;


/**
 *
 * @author Murali
 */
public class RestController {
    
    public static JSONObject getSingleJsonRes(String url,JSONObject requstJson,String jwt){
     JSONObject json=null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (jwt != null) {
            headers.set("Authorization", "Bearer "+jwt);
        }
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(requstJson.toString(), headers);

        //Try exchange
          try {
            ResponseEntity<String> response = SingletonController.getRestTemplate().exchange(url, HttpMethod.POST, entity, String.class);
            json = new JSONObject(response.getBody().toString());
             
          }  catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getMessage());
            json = new JSONObject("{\"jwt\":\""+e.getStatusCode()+"\"}");
          }

          // System.out.println("response - "+json);
     return json;
    }
    
    
}
