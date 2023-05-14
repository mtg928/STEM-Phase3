/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;

import com.topfield.utilities.NumberConversion;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class CCFCurd {
    
    
    public String getJSONProperty(JSONObject json,String head,String key){
    
     return json.getJSONObject(head).get(key).toString();
    }
    
    
    public void setJSONProperty(JSONObject json,String head,String key,String value){
    
      json.getJSONObject(head).put(key, value);
    }
    
    public void setAscore(JSONObject json,String value){
    
      json.getJSONObject("results").put("ascore", value);
    }
    
    public String getAscore(JSONObject json){
    
     return json.getJSONObject("results").get("ascore").toString();
    }
    
    public void setBscore(JSONObject json,String value){
    
      json.getJSONObject("results").put("bscore", value);
    }
    
    public String getBscore(JSONObject json){
    
      return json.getJSONObject("results").get("bscore").toString();
    }
    
    public void setRowscore(JSONObject json,String value){
    
      json.getJSONObject("results").put("rowscore", value);
    }
    
    public String getRowscore(JSONObject json){
    
      return json.getJSONObject("results").get("rowscore").toString();
    }
    
    public void setBetaFactor(JSONObject json,String value){
    
      json.getJSONObject("results").put("betaFactor", value);
    }
    
    public String getBetaFactor(JSONObject json){
    
      return json.getJSONObject("results").get("betaFactor").toString();
    }
    
    public void setDiagnosticCoverage(JSONObject json,String value){
    
      json.getJSONObject("results").put("diagnostic", value);
    }
    
     public String getDiagnosticCoverage(JSONObject json){
    
      return json.getJSONObject("results").get("diagnostic").toString();
    }
    
}
