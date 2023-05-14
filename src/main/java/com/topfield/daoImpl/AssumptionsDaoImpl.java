/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.AssumptionsDao;
import com.topfield.modal.Assumptions;
import com.topfield.modal.Calcfile;
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
public class AssumptionsDaoImpl implements AssumptionsDao{

    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
    
    @Override
    public Assumptions findById(int id) {
        return (Assumptions)db_crud.findById(id, Assumptions.class);
    }

    @Override
    public Assumptions findByName(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Save(Assumptions ass) {
       return db_crud.SaveItems(ass);
    }

    @Override
    public int Update(Assumptions ass) {
       db_crud.UpdateItems(ass);
        return ass.getAssId();
    }

    @Override
    public List<Assumptions> getAllAssumptions() {
         Transaction tx = null;
        List<Assumptions> assList = new ArrayList<Assumptions>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Assumptions");
			assList = query.list();

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
		return assList;
    }

    @Override
    public List<Assumptions> getAllAssumptionsByProId(int proId) {
       Transaction tx = null;
        List<Assumptions> assList = new ArrayList<Assumptions>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Assumptions a where a.projectId.proId =:proId");
                        query.setParameter("proId", proId);
			assList = query.list();

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
		return assList;
    }

    @Override
    public void remove(int ass) {
        db_crud.DeleteItems(findById(ass));
    }
    
}
