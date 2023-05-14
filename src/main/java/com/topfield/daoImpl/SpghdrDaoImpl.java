/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.SpghdrDao;
import com.topfield.modal.Spchdr;
import com.topfield.modal.Spghdr;
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
public class SpghdrDaoImpl implements SpghdrDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Spghdr findById(int spghdrId) {
        return (Spghdr)db_crud.findById(spghdrId, Spghdr.class);
    }

    @Override
    public List<Spghdr> getAllSpghdr() {
         Transaction tx = null;
        List<Spghdr> spghdrList = new ArrayList<Spghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			spghdrList = sessionObj.createQuery("FROM Spghdr").list();
                        
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
		return spghdrList;
    }

    @Override
    public List<Spghdr> getAllSpghdrByMPG(int mpgId, String cal, int proId, String username) {
       Transaction tx = null;
        List<Spghdr> spghdrList = new ArrayList<Spghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query =sessionObj.createQuery("FROM Spghdr s where s.spgid.mpgRef.mpgId =:mpgId  and s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname");
                        query.setParameter("mpgId", mpgId);
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
			spghdrList = query.list();
                        
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
		return spghdrList;
    }

    @Override
    public List<Spghdr> getAllSpghdrByPro(int proId, String cal, String username) {
         Transaction tx = null;
        List<Spghdr> spghdrList = new ArrayList<Spghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query =sessionObj.createQuery("FROM Spghdr s where s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname");
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
			spghdrList = query.list();
                        
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
		return spghdrList;
    }

    @Override
    public void update(Spghdr spghdr) {
      db_crud.UpdateItems(spghdr);
    }

    @Override
    public int save(Spghdr spghdr) {
        return db_crud.SaveItems(spghdr);
    }

    @Override
    public void delete(int spghdrId) {
        db_crud.DeleteItems(findById(spghdrId));
    }

    @Override
    public List<Spghdr> getAllSpghdrByProSpg(int proId, String cal, String username, int spgId) {
        Transaction tx = null;
        List<Spghdr> spghdrList = new ArrayList<Spghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query =sessionObj.createQuery("FROM Spghdr s where s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname and s.spgid.spgId = :spgId");
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
                        query.setParameter("spgId", spgId);
			spghdrList = query.list();
                        
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
		return spghdrList;
    }
    
}
