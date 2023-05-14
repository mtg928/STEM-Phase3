/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.ComponentsDao;
import com.topfield.modal.Components;
import com.topfield.modal.MainProductGroup;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;

/**
 *
 * @author Murali
 */
public class ComponentsDaoImpl implements ComponentsDao{
    
     private Session sessionObj;
     private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Components findById(int id) {
       return (Components)db_crud.findById(id, Components.class);
    }

    @Override
    public Components findByName(String name) {
        Transaction tx = null;
        List<Components> componentsList = new ArrayList<Components>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query  = sessionObj.createQuery("FROM Components c where c.comDescription = :comDescription");
                        query.setParameter("comDescription", name);

			componentsList = query.list();
                        
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
		return componentsList.get(0);
    }

    @Override
    public List<Components> getAllComponents() {
        Transaction tx = null;
        List<Components> componentsList = new ArrayList<Components>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			componentsList = sessionObj.createQuery("FROM Components").list();
                        
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
		return componentsList;
    }

    @Override
    public List<Components> getAllComponentsByType(String componentsType) {
        Transaction tx = null;
        List<Components> componentsList = new ArrayList<Components>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query  = sessionObj.createQuery("FROM Components c where c.comType = :comType");
                        query.setParameter("comType", componentsType);

			componentsList = query.list();
                        
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
		return componentsList;
    }

    @Override
    public List<Components> getAllComponentsByUser(String username) {
        Transaction tx = null;
        List<Components> componentsList = new ArrayList<Components>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query  = sessionObj.createQuery("FROM Components c where c.username.username in (:username1,:username2)");
                        query.setParameter("username1", "Admin");
                        query.setParameter("username2", username);

			componentsList = query.list();
                        
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
		return componentsList;
    }

    @Override
    public int Save(Components comp) {
        return db_crud.SaveItems(comp);
    }

    @Override
    public void Update(Components comp) {
        db_crud.UpdateItems(comp);
    }

    @Override
    public void Delete(int compId) {
        db_crud.DeleteItems(findById(compId));
    }
    
}
