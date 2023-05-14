/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.user;

import com.topfield.dao.DatareferDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.modal.Datarefer;
import com.topfield.modal.TclProjects;
import com.topfield.modal.Userpreferences;
import com.topfield.modal.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Murali
 */
public class UserInfo {
    
    private static UserInfo userInfo = null; 
    private  UserDao userDao = new UserDaoImpl();
    private  TclProjectsDao projectDao = new TclProjectsDaoImpl();
    private  DatareferDao datareferDao = new DatareferDaoImpl();
    private  Users  user ;
    private  Date date ;
    private  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private  String str_date ;
    private TclProjects tclProjects;
    private Datarefer reference; 
    private Userpreferences userpreferences;
            
            
    private  UserInfo() {
    user = userDao.findById("bala");
     str_date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
     tclProjects = projectDao.findById(34);
     //projectNo = 34;
     
        try {
            date = formatter.parse(str_date);
        } catch (ParseException ex) {
            Logger.getLogger(UserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static UserInfo getInstance() 
    { 
        if (userInfo == null) 
            userInfo = new UserInfo(); 
  
        return userInfo; 
    } 


    /**
     * @return the username
     */
    public Users getuser() {
        return user;
    }

    /**
     * @param username the username to set
     */
    public void setuser(Users user) {
        this.user = user;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the str_date
     */
    public String getStr_date() {
        return str_date;
    }

    /**
     * @param str_date the str_date to set
     */
    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }

    /**
     * @return the projectNo
     */
    public int getProjectNo() {
        return tclProjects.getProId();
    }

    /**
     * @param projectNo the projectNo to set
     */
    
    public void setProjectNo(int projectNo) {
        this.tclProjects = projectDao.findById(projectNo);
    }
    
    
    public void setProject(TclProjects project) {
        this.tclProjects = project;
    }
    
     public TclProjects getProject() {
        return tclProjects ;
    }
    

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return tclProjects.getProName();
    }


    
    public void setAllUserInfoData(String username){
        
        UserInfo.getInstance().setuser(userDao.findById(username));
        UserSettingsData.setIntialSettingsData();
        TclProjects pro ;
        if (UserInfo.getInstance().getUserpreferences().getSelectdpro() == null) {
             pro =  projectDao.findByUserLatest(username);
        } else {
             pro = projectDao.findById(UserInfo.getInstance().getUserpreferences().getSelectdpro());
        }
        
        
        UserInfo.getInstance().setProject(pro);
        
    }

    /**
     * @return the tclProjects
     */
    public TclProjects getTclProject() {
        
        if(tclProjects == null){
          tclProjects = projectDao.findByUserLatest(userInfo.user.getUsername());
        }
        
        return tclProjects;
    }

    /**
     * @param tclProjects the tclProjects to set
     */
    public void setTclProject(TclProjects tclProjects) {
        this.tclProjects = tclProjects;
    }

    /**
     * @return the proAbbreviations
     */
    public String getProAbbreviations() {
        return tclProjects.getAbbreviation();
    }

  
    public Datarefer getReference(){
     
        if (reference == null) {
            reference = datareferDao.findById(3);
        }
        return reference;
    }

    /**
     * @return the userpreferences
     */
    public Userpreferences getUserpreferences() {
        return userpreferences;
    }

    /**
     * @param userpreferences the userpreferences to set
     */
    public void setUserpreferences(Userpreferences userpreferences) {
        this.userpreferences = userpreferences;
    }
    
    
}
