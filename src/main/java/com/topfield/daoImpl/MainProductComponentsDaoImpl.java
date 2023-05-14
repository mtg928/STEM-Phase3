/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.MainProductComponentsDao;
import com.topfield.modal.MainProductComponents;
import com.topfield.modal.MainProductGroup;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class MainProductComponentsDaoImpl implements MainProductComponentsDao{
    
     private Session sessionObj;

    @Override
    public MainProductComponents FindById(int mpcId) {
       Transaction tx = null;
  
        MainProductComponents mpcList = new MainProductComponents();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			mpcList = (MainProductComponents)sessionObj.get(MainProductComponents.class, mpcId);
                        
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
		return mpcList;
    }

    @Override
    public List<MainProductComponents> getAllMPC() {
        Transaction tx = null;
  
      List<MainProductComponents> mpcList = new ArrayList<MainProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			mpcList = sessionObj.createQuery("FROM MainProductComponents").list();
                        
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
		return mpcList;
    }

    @Override
    public List<MainProductComponents> getAllMPCByMPG(int mpgId) {
            Transaction tx = null;
  
      List<MainProductComponents> mpcList = new ArrayList<MainProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			Query query  = sessionObj.createQuery("FROM MainProductComponents m where m.mpgRef.mpgId = :mpgId");
                        query.setParameter("mpgId", mpgId);
                        mpcList = query.list();
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
		return mpcList;
    }
    
}
