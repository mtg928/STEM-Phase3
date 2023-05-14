/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.CalcfileDao;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
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
public class CalcfileDaoImpl implements CalcfileDao {
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Calcfile findById(int id) {
        return (Calcfile)db_crud.findById(id, Calcfile.class);
    }

    @Override
    public Calcfile findByName(String description) {
        Transaction tx = null;
        List<Calcfile> calcfileList = new ArrayList<Calcfile>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calcfile f where f.components = :description ");
                        query.setParameter("description", description);
			calcfileList = query.list();
                        
                        
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
		return calcfileList.get(0);
    }

    @Override
    public int Save(Calcfile cf) {
         return db_crud.SaveItems(cf);
    }

    @Override
    public int Update(Calcfile cf) {
       db_crud.UpdateItems(cf);
        return cf.getCalcId();
    }

    @Override
    public List<Calcfile> getAllCalcfile() {
        Transaction tx = null;
        List<Calcfile> calcfileList = new ArrayList<Calcfile>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calcfile");
			calcfileList = query.list();

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
		return calcfileList;
    }

    @Override
    public List<Calcfile> getAllCalcfileByCalcFun(int calcFunId) {
        Transaction tx = null;
        List<Calcfile> calcfileList = new ArrayList<Calcfile>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calcfile f where f.calcHeader.calcFunId = :calcFunId");
                        query.setParameter("calcFunId", calcFunId);
			calcfileList = query.list();
                        
                        
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
		return calcfileList;
    }

    @Override
    public void remove(int cf) {
       db_crud.DeleteItems(findById(cf));
    }

   /* @Override
    public void refresh(List<Calcfile> list) {
        for (Calcfile calcfile : list) {
            sessionObj.refresh(calcfile);
        }
    }*/
    
}
