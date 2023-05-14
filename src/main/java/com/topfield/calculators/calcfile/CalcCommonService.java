/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.DatareferDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Standards;
import com.topfield.user.UserInfo;
import java.util.Date;

/**
 *
 * @author Murali
 */
public class CalcCommonService {
    
    private static TclProjectsDao proDao = new TclProjectsDaoImpl();
    private static StandardsDao stdDao = new StandardsDaoImpl();
    private static MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
    private static MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private static CalcFunctionsDao calcFunctionsDao = new CalcFunctionsDaoImpl();
    private static CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private static DatareferDao datareferDao = new DatareferDaoImpl();
    
    
    public static Mpghdr createCalFileHdr(String des,int mpgId,String comments, String calculator){
   
            Mpghdr mpghdrRef = new Mpghdr();
            mpghdrRef.setDescriptions(des);
            mpghdrRef.setStandard(stdDao.findById(6));
            mpghdrRef.setMpgType(calculator);
            mpghdrRef.setCalname("CMM");
            mpghdrRef.setComments(comments);
            mpghdrRef.setFmeaComponent(mainProDao.FindById(mpgId));
            mpghdrRef.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
            mpghdrRef.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
            mpghdrRef.setCreateddate(new Date());
            
            mpghdrRef.setMpghdrid(mpgHdrDao.saveFMEAS(mpghdrRef)); 
            
            return mpghdrRef;
   }
    
   public static CalcFunctions createCalcFunction(int funtionId,String funtiontype,String funId,String funtionName,String sil,
                                    double silTargetFrom,double silTargetTo, String remarks, Mpghdr mpghdr,boolean edit){
   
       CalcFunctions calcFun= new CalcFunctions();
       calcFun.setCalcFunType(funtiontype);
       calcFun.setFunId(funId);
       calcFun.setCalcFunction(funtionName);
       calcFun.setSil(sil);
       calcFun.setSilTargetFrom(silTargetFrom);
       calcFun.setSilTargetTo(silTargetTo);
       calcFun.setRemarks(remarks);
       calcFun.setMpgheader(mpghdr);
       
       if (edit) {
           calcFun.setCalcFunId(funtionId);
           calcFunctionsDao.Update(calcFun); 
       } else {
           calcFunctionsDao.Save(calcFun);
       }
     
            
       return calcFun;
   } 
   
   public static Calcfile createCalcFile(int calcId,String ComId,String comName,double failurerate,double probability,CalcFunctions calcFunctions,boolean edit){
   
      Calcfile calcfileRef = edit? calcfileDao.findById(calcId): new Calcfile();
      calcfileRef.setCompId(ComId);
      calcfileRef.setComponents(comName);
      calcfileRef.setFailureRate(failurerate);
      calcfileRef.setProbability(probability);
      calcfileRef.setCalcHeader(calcFunctions);
      
       if (edit) {
          calcfileDao.Update(calcfileRef);
       }else{
          calcfileRef.setFailureMode(""); 
          calcfileRef.setFMPercentage(0.0);
          calcfileRef.setURPercentage(0.0);
          calcfileRef.setRemarks("Generated"); 
          calcfileRef.setReference(UserInfo.getInstance().getReference());
          calcfileRef.setCalcId(calcfileDao.Save(calcfileRef));
       }
     
   
      return calcfileRef;
   }
   
   
    public static void removeCalcFile(int calcId){
       //calcfileDao.remove(calcId);
    }
   
}
