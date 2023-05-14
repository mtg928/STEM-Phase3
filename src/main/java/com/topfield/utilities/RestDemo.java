/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topfield.controller.RestController;
import com.topfield.controller.SingletonController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Murali
 */
public class RestDemo {
    
    public static void main(String args[]) {
        
          String customerAPIUrl = "https://www.topfieldconsultancy.co.uk/db-service/authenticate";
          String customerAPIUrl2 = "https://www.topfieldconsultancy.co.uk/db-service/welcome";
          
          JSONObject json = new JSONObject();
          json.put("username", "admin");
          json.put("password", "bala");
          
     
          RestController.getSingleJsonRes(customerAPIUrl, json,null);
          
          RestController.getSingleJsonRes(customerAPIUrl2, new JSONObject(),"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjU3Njk5MCwiaWF0IjoxNjIyNTQwOTkwfQ.5B4vJQFL-YXi6yzn-m2BgRdqsEWSF-9PSAUoTxjyrbE");

     
         //Test(customerAPIUrl);
      
      
         }
    
    
  
}
