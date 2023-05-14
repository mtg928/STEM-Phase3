/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.FaulttreeDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Ccf;
import com.topfield.modal.Faulttree;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FaulttreeDaoImpl implements FaulttreeDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Faulttree findById(int id) {
   
  
         Faulttree ft = (Faulttree)db_crud.findById(id, Faulttree.class);

        return ft;
        
    }

    @Override
    public Faulttree findByName(String description) {
        Transaction tx = null;
        List<Faulttree> ftList = new ArrayList<Faulttree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Faulttree f where f.faultName = :description ");
                        query.setParameter("description", description);
			ftList = query.list();
                        
                        
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
		return ftList.get(0);
    }

    @Override
    public int Save(Faulttree ft) {
        return db_crud.SaveItems(ft);
    }

    @Override
    public int Update(Faulttree ft) {
       
        db_crud.UpdateItems(ft);
        return ft.getFaultId();
    }

    @Override
    public List<Faulttree> getAllCcf() {
         Transaction tx = null;
        List<Faulttree> ftList = new ArrayList<Faulttree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			ftList = sessionObj.createQuery("FROM Faulttree").list();
                        
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
		return ftList;
    }

    @Override
    public List<Faulttree> getAllFaulttreeByTypeUserProj(String user, int proId) {
         Transaction tx = null;
        List<Faulttree> ftList = new ArrayList<Faulttree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Faulttree f where f.user.username =:user and f.projectId.proId =:proId");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);     
			ftList = query.list();
                        
                        
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
		return ftList;
    }

    @Override
    public void remove(Faulttree ft) {
         db_crud.DeleteItems(ft);
    }
    
}
