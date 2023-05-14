/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.modal.Fmea;
import com.topfield.modal.MainProductGroup;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class DB_CRUD_Interface {
    
     private Session sessionObj;
     
     public Object findById(int id,Class type){
      Object res = null;
      Transaction tx = null;
  	
       try {
	sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			res = sessionObj.get(type, id);
                        
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
                        sessionObj.close();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                              
			}
		}
		return res;
     
     }
     
     
     public List<Object> getAllItems(String modalName){
          Transaction tx = null;
           List<Object> objList = new ArrayList<Object>();
           
		try {
		     sessionObj = DBconnectionPool.getDBSession();
		     tx = sessionObj.beginTransaction();
                     objList = sessionObj.createQuery("FROM "+modalName).list();
                     tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
                        sessionObj.close();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
		}
		return objList;
     
     
     }
     
     public int SaveItems(Object obj){
         int res=0;
              Transaction tx = null;
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                      res = (int) sessionObj.save(obj);
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
                        sessionObj.close();
		} finally {
			if(sessionObj != null) {
				//
                               
			}
		}
     
     
      return res;
     }
     
     public void UpdateItems(Object obj){
              Transaction tx = null;
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        sessionObj.merge(obj);
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
                        sessionObj.close();
		} finally {
			if(sessionObj != null) {
				
                               
			}
		}
     
     
     
     }
     
     public void DeleteItems(Object obj){
              Transaction tx = null;
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        sessionObj.delete(obj);
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				//sessionObj.getTransaction().rollback();
                                tx.rollback();
			}
                        
                        if(sessionObj != null) {
			 sessionObj.close();
                               
			}
                        JOptionPane.showMessageDialog(null,sqlException.getClass().getSimpleName()+"\n"+ErrorController.formatError(sqlException.getCause().getMessage()),"Error - ",JOptionPane.ERROR_MESSAGE);  
			sqlException.printStackTrace();
		} finally {
			/*if(sessionObj != null) {
			 sessionObj.close();
                               
			}*/
		}
     
     
     
     }
     
 
    
}
