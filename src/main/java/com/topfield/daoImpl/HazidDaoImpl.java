/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.HazidDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Ccf;
import com.topfield.modal.Hazid;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class HazidDaoImpl implements HazidDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
       
       
       
       @Override
    public Hazid findById(int id) {
        Hazid hazid = (Hazid)db_crud.findById(id, Hazid.class);

        return hazid;
    }    

    @Override
    public List<Hazid> getAllHazid() {
       Transaction tx = null;
        List<Hazid> hazidList = new ArrayList<Hazid>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			hazidList = sessionObj.createQuery("FROM Hazid").list();
                        
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
		return hazidList;
    }


    
}
