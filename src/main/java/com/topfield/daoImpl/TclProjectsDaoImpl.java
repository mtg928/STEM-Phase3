/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.TclProjectsDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Components;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.TclProjects;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class TclProjectsDaoImpl implements TclProjectsDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
       
    @Override
    public TclProjects findById(int projectId) {
         Transaction tx = null;
  
         TclProjects pro = new TclProjects();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			pro = (TclProjects)sessionObj.get(TclProjects.class, projectId);
                        
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
		return pro;
    }

    @Override
    public int saveProjects(TclProjects pro) {

      return db_crud.SaveItems(pro);
      
       
    }

    @Override
    public void editProjects(TclProjects pro) {
        db_crud.UpdateItems(pro);
    }

    @Override
    public TclProjects findByUserLatest(String user) {
        Transaction tx = null;
  
         TclProjects pro = new TclProjects();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
			 Query query =sessionObj.createQuery("FROM TclProjects p where p.createdBy.username = :username order by p.proId DESC");
                         query.setParameter("username", user);
                         query.setMaxResults(1); 
			 pro = (TclProjects)query.uniqueResult();
                        
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
		return pro;
    }

    @Override
    public List<TclProjects> getProByUser(String user) {
        Transaction tx = null;
  
        List<TclProjects> pro = new ArrayList<TclProjects>();	
		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
			 Query query =sessionObj.createQuery("FROM TclProjects p where p.createdBy.username = :username");
                         query.setParameter("username", user);
			 pro = query.list();
                        
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
		return pro;
    }
    
}
