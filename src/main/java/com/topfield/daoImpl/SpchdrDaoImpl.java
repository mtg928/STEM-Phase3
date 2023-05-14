/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.SpchdrDao;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Spchdr;
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
public class SpchdrDaoImpl implements SpchdrDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Spchdr findById(int spchdrId) {
        return (Spchdr)db_crud.findById(spchdrId, Spchdr.class);
    }

    @Override
    public List<Spchdr> getAllSpchdr() {
       Transaction tx = null;
        List<Spchdr> spchdrList = new ArrayList<Spchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			spchdrList = sessionObj.createQuery("FROM Spchdr").list();
                        
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
		return spchdrList;
    }

    @Override
    public List<Spchdr> getAllSpchdrByMPG(int spgId, String cal, int proId, String username) {
       Transaction tx = null;
        List<Spchdr> spchdrList = new ArrayList<Spchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query =sessionObj.createQuery("FROM Spchdr s where s.spcid.spgRef.spgId =:spgId  and s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname");
                        query.setParameter("spgId", spgId);
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
			spchdrList = query.list();
                        
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
		return spchdrList;
    }

    @Override
    public List<Spchdr> getAllSpchdrByPro(int proId, String cal, String username) {
       Transaction tx = null;
        List<Spchdr> spchdrList = new ArrayList<Spchdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query =sessionObj.createQuery("FROM Spchdr s where s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname");
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
			spchdrList = query.list();
                        
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
		return spchdrList;
    }

    @Override
    public void update(Spchdr spchdr) {
        db_crud.UpdateItems(spchdr);
    }

    @Override
    public int save(Spchdr spchdr) {
       return db_crud.SaveItems(spchdr);
    }

    @Override
    public void delete(int spchdrId) {
        db_crud.DeleteItems(findById(spchdrId));
    }
    
}
