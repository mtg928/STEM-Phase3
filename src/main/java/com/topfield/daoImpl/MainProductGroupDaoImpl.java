/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Student;
import com.topfield.modal.SubProductGroup;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Murali
 */
public class MainProductGroupDaoImpl implements MainProductGroupDao {
    
   private Session sessionObj;
   private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

@SuppressWarnings("unchecked")
public List<MainProductGroup> getAllMPG() {
  Transaction tx = null;
  
  List<MainProductGroup> mpgList = new ArrayList<MainProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			mpgList = sessionObj.createQuery("FROM MainProductGroup").list();
                        
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
		}
		return mpgList;
}

    @Override
    public MainProductGroup FindById(int mpgId) {
    
         Transaction tx = null;
  
         MainProductGroup mpg = new MainProductGroup();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			mpg = (MainProductGroup)sessionObj.get(MainProductGroup.class, mpgId);
                        
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
		}
		return mpg;
    
    
    }

    @Override
    public MainProductGroup FindByName(String mpgName) {
        Transaction tx = null;
  
      List<MainProductGroup> mpgList = new ArrayList<MainProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM MainProductGroup s where s.mpgDescription = :mpgDescription");
                         query.setParameter("mpgDescription", mpgName);
                         mpgList = query.list();
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
                        
                   
		}
                
	        if (mpgList.size()>0) {
                     return mpgList.get(0);
                } else {
                    // System.out.println("Null Retutn");
                      return null;
                }	
    }

    @Override
    public List<MainProductGroup> getAllMPGBYUser(String username) {
         Transaction tx = null;
  
      List<MainProductGroup> mpgList = new ArrayList<MainProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM MainProductGroup s where s.username.username = :username");
                         query.setParameter("username", username);
                         mpgList = query.list();
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
		}
		return mpgList;
    }

    @Override
    public int Save(MainProductGroup mpg) {
       return db_crud.SaveItems(mpg);
    }

    @Override
    public void Update(MainProductGroup mpg) {
        db_crud.UpdateItems(mpg);
    }

    @Override
    public void Delete(int mpgId) {
        db_crud.DeleteItems(FindById(mpgId));
    }

    @Override
    public List<MainProductGroup> getAllMPGByAdminUser(String username) {
         Transaction tx = null;
  
      List<MainProductGroup> mpgList = new ArrayList<MainProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM MainProductGroup s where ( s.username.username = :username or s.username.username = 'Admin')");
                         query.setParameter("username", username);
                         mpgList = query.list();
                        tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				//sessionObj.close();
                               
			}
		}
		return mpgList;
    }
        
        

    
}
