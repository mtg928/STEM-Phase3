/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.HazardDao;
import com.topfield.modal.Hazards;
import com.topfield.modal.Hazid;
import com.topfield.user.UserInfo;
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
public class HazardDaoImpl implements HazardDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
    
   
    @Override
    public Hazards findById(int id) {
       Hazards hazards = (Hazards)db_crud.findById(id, Hazards.class);

        return hazards;
    }

    @Override
    public List<Hazards> getAllHazards() {
        Transaction tx = null;
        List<Hazards> hazardsList = new ArrayList<Hazards>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			 Query query =sessionObj.createQuery("FROM Hazards h where h.createdBy.username in ('Admin',:usernames)");
                          query.setParameter("usernames",UserInfo.getInstance().getuser().getUsername() );
                          hazardsList = query.list();
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
		return hazardsList;
    }

    @Override
    public void Delete(int hazardId) {
        db_crud.DeleteItems(findById(hazardId));
    }

    @Override
    public void Update(Hazards hazards) {
        db_crud.UpdateItems(hazards);
    }

    @Override
    public int Save(Hazards hazards) {
      return db_crud.SaveItems(hazards);
    }
    
}
