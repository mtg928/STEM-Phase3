/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.FailuredataDao;
import com.topfield.modal.Ccf;
import com.topfield.modal.Failuredata;
import com.topfield.utilities.DBconnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FailuredataDaoImpl implements FailuredataDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Failuredata findById(int id) {
          
         Failuredata failuredata = (Failuredata)db_crud.findById(id, Failuredata.class);
         
         return failuredata;
    }

    @Override
    public Failuredata findByName(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Save(Failuredata failuredata) {
        return db_crud.SaveItems(failuredata);
    }

    @Override
    public int Update(Failuredata failuredata) {
         db_crud.UpdateItems(failuredata);
         return failuredata.getId();
    }

    @Override
    public void Remove(int Id) {
        db_crud.DeleteItems(findById(Id));
    }

    @Override
    public List<Failuredata> getAllFailuredata() {
         Transaction tx = null;
        List<Failuredata> failuredataList = new ArrayList<Failuredata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			failuredataList = sessionObj.createQuery("FROM Failuredata").list();
                        
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
		return failuredataList;
    }
    
}
