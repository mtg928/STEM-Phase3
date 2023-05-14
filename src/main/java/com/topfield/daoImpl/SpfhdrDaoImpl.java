/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.SpfhdrDao;
import com.topfield.modal.Spfhdr;
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
public class SpfhdrDaoImpl implements SpfhdrDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Spfhdr findById(int spfhdrId) {
        return (Spfhdr)db_crud.findById(spfhdrId, Spfhdr.class);
    }

    @Override
    public List<Spfhdr> getAllSpfhdr() {
         Transaction tx = null;
        List<Spfhdr> spfhdrList = new ArrayList<Spfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			spfhdrList = sessionObj.createQuery("FROM Spfhdr").list();
                        
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
		return spfhdrList;
    }

    @Override
    public List<Spfhdr> getAllSpfhdrByMPG(int mpgId, String cal, int proId, String username) {
            Transaction tx = null;
        List<Spfhdr> spfhdrList = new ArrayList<Spfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query = sessionObj.createQuery("FROM Spfhdr s where s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname and s.spfid.mpgheader.mpghdrid =:mpgId");
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
                        query.setParameter("mpgId", mpgId);
                        spfhdrList = query.list();
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
		return spfhdrList;
    }

    @Override
    public List<Spfhdr> getAllSpfhdrByPro(int proId, String cal, String username) {
            Transaction tx = null;
        List<Spfhdr> spfhdrList = new ArrayList<Spfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query = sessionObj.createQuery("FROM Spfhdr s where s.projectId.proId =:proId and s.calname =:calname"); //and s.user.username =:usernames
                        query.setParameter("proId", proId);
                        //query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
                        spfhdrList = query.list();
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
		return spfhdrList;
    }

    @Override
    public List<Spfhdr> getAllSpfhdrByProFun(int proId, String cal, String username, int calcFunId) {
        Transaction tx = null;
        List<Spfhdr> spfhdrList = new ArrayList<Spfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			Query query = sessionObj.createQuery("FROM Spfhdr s where s.projectId.proId =:proId and s.user.username =:usernames and s.calname =:calname and s.spfid.calcFunId =:calcFunId");
                        query.setParameter("proId", proId);
                        query.setParameter("usernames", username);
                        query.setParameter("calname", cal);
                        query.setParameter("calcFunId", calcFunId);
                        spfhdrList = query.list();
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
		return spfhdrList;
    }

    @Override
    public void update(Spfhdr spfhdr) {
         db_crud.UpdateItems(spfhdr);
    }

    @Override
    public int save(Spfhdr spfhdr) {
        return db_crud.SaveItems(spfhdr);
    }

    @Override
    public void delete(int spfhdrId) {
        db_crud.DeleteItems(findById(spfhdrId));
    }
    
}
