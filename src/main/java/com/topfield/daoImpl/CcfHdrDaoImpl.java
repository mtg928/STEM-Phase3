/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.CcfHdrDao;
import com.topfield.modal.Ccf;
import com.topfield.modal.Ccfhdr;
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
public class CcfHdrDaoImpl implements CcfHdrDao {
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Ccfhdr findById(int id) {
         Transaction tx = null;
  
         Ccfhdr ccfhdr = (Ccfhdr)db_crud.findById(id, Ccfhdr.class);

        return ccfhdr;
    }

    @Override
    public Ccfhdr findByName(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Save(Ccfhdr ccfhdr) {
        return db_crud.SaveItems(ccfhdr);
    }

    @Override
    public int Update(Ccfhdr ccfhdr) {
       db_crud.UpdateItems(ccfhdr);
       return ccfhdr.getCcfhdrId();
    }

    @Override
    public List<Ccfhdr> getAllCcfhdr() {
       Transaction tx = null;
        List<Ccfhdr> CcfhdrList = new ArrayList<Ccfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			CcfhdrList = sessionObj.createQuery("FROM Ccfhdr").list();
                        
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
		return CcfhdrList;
    }

    @Override
    public List<Ccfhdr> getAllCcfhdrByUserAndPro(String user, int proId) {
        Transaction tx = null;
        List<Ccfhdr> CcfhdrList = new ArrayList<Ccfhdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query = sessionObj.createQuery("FROM Ccfhdr f where f.user.username =:user and f.projectId.proId =:proId");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        CcfhdrList = query.list();
                        
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
		return CcfhdrList;
    }

    @Override
    public void Remove(int ccfhdrId) {
       db_crud.DeleteItems(findById(ccfhdrId));
    }
    
}
