/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.EventTreeHdrDao;
import com.topfield.modal.Ccf;
import com.topfield.modal.Eventtreehdr;
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
public class EventTreeHdrDaoImpl implements EventTreeHdrDao {

    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Eventtreehdr findById(int id) {
          Eventtreehdr eventTreeHdr = (Eventtreehdr)db_crud.findById(id, Eventtreehdr.class);
        return eventTreeHdr;
    }

    @Override
    public Eventtreehdr findByName(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Save(Eventtreehdr eventTreeHdr) {
          return db_crud.SaveItems(eventTreeHdr);
    }

    @Override
    public int Update(Eventtreehdr eventTreeHdr) {
       db_crud.UpdateItems(eventTreeHdr);
       return eventTreeHdr.getEventhdrId();
    }

    @Override
    public void Remove(int eventTreeHdrId) {
        db_crud.DeleteItems(findById(eventTreeHdrId));
    }

    @Override
    public List<Eventtreehdr> getAllEventTreeHdr() {
          Transaction tx = null;
        List<Eventtreehdr> eventTreeHdrList = new ArrayList<Eventtreehdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			eventTreeHdrList = sessionObj.createQuery("FROM Eventtreehdr").list();
                        
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
		return eventTreeHdrList;
    }

    @Override
    public List<Eventtreehdr> getAllEventTreeHdrByUserAndPro(String user, int proId) {
         Transaction tx = null;
        List<Eventtreehdr> eventTreeHdrList = new ArrayList<Eventtreehdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query  = sessionObj.createQuery("FROM Eventtreehdr f where f.projectId.proId =:proId "); //f.username.username =:user and
                        //query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        
			eventTreeHdrList = query.list();
                        
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
		return eventTreeHdrList;
    }

}
