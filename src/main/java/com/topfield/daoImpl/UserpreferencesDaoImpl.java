/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.UserpreferencesDao;
import com.topfield.modal.Assumptions;
import com.topfield.modal.Userpreferences;
import com.topfield.utilities.DBconnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class UserpreferencesDaoImpl implements UserpreferencesDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Userpreferences findById(int preId) {
          return (Userpreferences)db_crud.findById(preId, Userpreferences.class);
    }

    @Override
    public List<Userpreferences> getAllUserpreferences() {
         Transaction tx = null;
        List<Userpreferences> assList = new ArrayList<Userpreferences>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Userpreferences");
			assList = query.list();

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
		return assList;
    }

    @Override
    public List<Userpreferences> getAllUserpreferencesByUser(String username) {
          Transaction tx = null;
        List<Userpreferences> assList = new ArrayList<Userpreferences>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Userpreferences where preuser.username =:username");
                          query.setParameter("username", username);
			assList = query.list();

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
		return assList;
    }

    @Override
    public void update(Userpreferences spchdr) {
       db_crud.UpdateItems(spchdr);
    }

    @Override
    public int save(Userpreferences spchdr) {
       return db_crud.SaveItems(spchdr);
    }

    @Override
    public void delete(int preId) {
        db_crud.DeleteItems(findById(preId));
    }
    
}
