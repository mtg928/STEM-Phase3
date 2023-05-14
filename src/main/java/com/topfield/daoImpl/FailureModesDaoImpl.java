/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.FailureModesDao;
import com.topfield.modal.Failuredata;
import com.topfield.modal.Failuremodes;
import com.topfield.utilities.DBconnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FailureModesDaoImpl implements FailureModesDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Failuremodes findById(int id) {
        return (Failuremodes)db_crud.findById(id, Failuremodes.class);
    }

    @Override
    public Failuremodes findByName(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Save(Failuremodes failuremodes) {
        return db_crud.SaveItems(failuremodes);
    }

    @Override
    public int Update(Failuremodes failuremodes) {
        db_crud.UpdateItems(failuremodes);
         return failuremodes.getModeid();
    }

    @Override
    public void Remove(int Id) {
         db_crud.DeleteItems(findById(Id));
    }

    @Override
    public List<Failuremodes> getAllFailuremodes() {
       Transaction tx = null;
        List<Failuremodes> failuremodesList = new ArrayList<Failuremodes>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			failuremodesList = sessionObj.createQuery("FROM Failuremodes").list();
                        
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
		return failuremodesList;
    }
    
}
