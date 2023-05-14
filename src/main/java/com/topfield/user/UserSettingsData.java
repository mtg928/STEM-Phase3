/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.user;

import com.topfield.dao.UserpreferencesDao;
import com.topfield.daoImpl.UserpreferencesDaoImpl;
import com.topfield.modal.Userpreferences;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Murali
 */
public class UserSettingsData {
    
   private static UserpreferencesDao userpreDao= new UserpreferencesDaoImpl();
    
    
   public static Optional<Userpreferences> getUserSettingsData(){
       
       List<Userpreferences> list =userpreDao.getAllUserpreferencesByUser(UserInfo.getInstance().getuser().getUsername());
       
       return Optional.ofNullable(list.size() >0?list.get(0):null);
   }
    
    
    public static void setIntialSettingsData(){
       
         Optional<Userpreferences> preRef =getUserSettingsData();
         Userpreferences ref;
        
        if (!preRef.isPresent()) {
            ref = new Userpreferences();
            ref.setFramecolour("Gray");
            ref.setButtoncolour("Green");
            ref.setNotification(true);
            ref.setFont("Dialog");
            //ref.setSelectdpro(UserInfo.getInstance().getProjectNo());
            ref.setPreuser(UserInfo.getInstance().getuser());
            
            userpreDao.save(ref);
        } else{
             ref = preRef.get();
            //ref.setSelectdpro(UserInfo.getInstance().getProjectNo());
            //userpreDao.update(ref);
       }    
        
       UserInfo.getInstance().setUserpreferences(ref);
        
    }
    
    public static void setActiveProject(int proId){
         Userpreferences ref = UserInfo.getInstance().getUserpreferences();
         ref.setSelectdpro(proId);
         userpreDao.update(ref);
    }
    
    
}
