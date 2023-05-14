/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.DatareferDao;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Datarefer;
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
public class DatareferDaoImpl implements DatareferDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Datarefer findById(int id) {
       return (Datarefer)db_crud.findById(id, Datarefer.class);
    }

    @Override
    public Datarefer findByName(String description) {
         Transaction tx = null;
        List<Datarefer> datareferList = new ArrayList<Datarefer>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Datarefer d where d.description = :description ");
                        query.setParameter("description", description);
			datareferList = query.list();
                        
                        
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
		return datareferList.get(0);
    }

    @Override
    public int Save(Datarefer cf) {
        return db_crud.SaveItems(cf);
    }

    @Override
    public int Update(Datarefer cf) {
       db_crud.UpdateItems(cf);
        return cf.getRefid();
    }

    @Override
    public List<Datarefer> getAllDatarefer() {
         Transaction tx = null;
        List<Datarefer> datareferList = new ArrayList<Datarefer>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Datarefer");
			datareferList = query.list();
                        
                        
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
		return datareferList;
    }

    @Override
    public List<Datarefer> getAllDatareferByCalType(String calType) {
         Transaction tx = null;
        List<Datarefer> datareferList = new ArrayList<Datarefer>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Datarefer d where d.caltype in (:calType,'OTHER') or d.caltype is null");
                        query.setParameter("calType", calType);
			datareferList = query.list();
                        
                        
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
		return datareferList;
    }

    @Override
    public void remove(int cfId) {
       db_crud.DeleteItems(findById(cfId));
    }
    
}
