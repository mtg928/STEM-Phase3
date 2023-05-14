/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.CalchdrDao;
import com.topfield.modal.Assumptions;
import com.topfield.modal.Calchdr;
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
public class CalchdrDaoImpl implements CalchdrDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public List<Calchdr> getAllCalchdr() {
          Transaction tx = null;
        List<Calchdr> calcHdrList = new ArrayList<Calchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calchdr");
			calcHdrList = query.list();

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
		return calcHdrList;
    }

    @Override
    public Calchdr findById(int calchdrId) {
        return (Calchdr)db_crud.findById(calchdrId, Calchdr.class);
    }

    @Override
    public List<Calchdr> getSummaryCalchdrByPro(int proId, String cal, String username) {
          Transaction tx = null;
        List<Calchdr> calcHdrList = new ArrayList<Calchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calchdr where projectId.proId =:fmecaproid and calname =:calname"); //and user.username =:fmecauser  
                        query.setParameter("fmecaproid", proId);
                        //query.setParameter("fmecauser", username);
                        query.setParameter("calname", cal);
                        
                        calcHdrList = query.list();

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
		return calcHdrList;
    }

    @Override
    public void Update(Calchdr calchdr) {
       db_crud.UpdateItems(calchdr);
    }

    @Override
    public int save(Calchdr calchdr) {
        return db_crud.SaveItems(calchdr);
    }

    @Override
    public void delete(int calchdr) {
        db_crud.DeleteItems(findById(calchdr));
    }

    @Override
    public List<Calchdr>  getAllCalchdrByMPGOne(int mpghdrid, String cal, int proId, String username) {
          Transaction tx = null;
        List<Calchdr> calcHdrList = new ArrayList<Calchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Calchdr where projectId.proId =:fmecaproid and user.username =:fmecauser and f.calname =:calname and mpghdr.mpghdrid =:mpghdrid");
                        query.setParameter("fmecaproid", proId);
                        query.setParameter("fmecauser", username);
                        query.setParameter("calname", cal);
                        query.setParameter("mpghdrid", mpghdrid);
                        
                        calcHdrList = query.list();

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
		return calcHdrList;
    }
    
}
