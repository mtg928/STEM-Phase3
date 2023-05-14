/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.SILDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Ccf;
import com.topfield.modal.Sil;
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
public class SILDaoImpl implements SILDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Sil findById(int id) {
      return (Sil)db_crud.findById(id, Sil.class);
    }

    @Override
    public Sil findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sil> getAllSil() {
        Transaction tx = null;
  
        List<Sil> sil = new ArrayList<Sil>();	
		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
			 Query query =sessionObj.createQuery("FROM Sil");
			 sil = query.list();
                        
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
		return sil;
    }

    @Override
    public int Save(Sil sil) {
       return db_crud.SaveItems(sil);
    }

    @Override
    public void Upadte(Sil sil) {
        db_crud.UpdateItems(sil);
    }

    @Override
    public void Delete(Sil sil) {
       db_crud.DeleteItems(sil);
    }

    @Override
    public List<Sil> getSILByTypeUserProj(String user, int proId) {
        Transaction tx = null;
        List<Sil> SilList = new ArrayList<>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Sil s where s.projectId.proId =:proId "); //s.siluser.username =:user and
                        //query.setParameter("user", user);
                        query.setParameter("proId", proId);     
			SilList = query.list();
                        
                        
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
		return SilList;
    }
    
}
