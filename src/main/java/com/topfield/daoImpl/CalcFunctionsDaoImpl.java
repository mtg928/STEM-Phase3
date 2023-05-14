/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Faulttree;
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
public class CalcFunctionsDaoImpl implements CalcFunctionsDao  {
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public CalcFunctions findById(int id) {
        return (CalcFunctions)db_crud.findById(id, CalcFunctions.class);
    }

    @Override
    public CalcFunctions findByName(String description) {
         Transaction tx = null;
        List<CalcFunctions> calcFunctionsList = new ArrayList<CalcFunctions>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM CalcFunctions f where f.function = :description ");
                        query.setParameter("description", description);
			calcFunctionsList = query.list();
                        
                        
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
		return calcFunctionsList.get(0);
    }

    @Override
    public int Save(CalcFunctions cf) {
        return db_crud.SaveItems(cf);
    }

    @Override
    public int Update(CalcFunctions cf) {
        db_crud.UpdateItems(cf);
        return cf.getCalcFunId();
    }

    @Override
    public List<CalcFunctions> getAllCalcFunctions() {
        Transaction tx = null;
        List<CalcFunctions> calcFunctionsList = new ArrayList<CalcFunctions>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM CalcFunctions");
			calcFunctionsList = query.list();
                        
                        
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
		return calcFunctionsList;
    }

    @Override
    public List<CalcFunctions> getAllCalcFunctionsByMpgheader(int hdrId) {
        Transaction tx = null;
        List<CalcFunctions> calcFunctionsList = new ArrayList<CalcFunctions>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        
                        Query query =sessionObj.createQuery("FROM CalcFunctions f where f.mpgheader.mpghdrid=:hdrId ");
                        query.setParameter("hdrId", hdrId); 
			calcFunctionsList = query.list();
                        
                        
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
		return calcFunctionsList;
    }

    @Override
    public void remove(int cfId) {
        db_crud.DeleteItems(findById(cfId));
    }

    @Override
    public void flash() {
       sessionObj.flush();
    }
    
}
