/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.Sil;
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
public class FunctionalfailuresDaoImpl implements FunctionalfailuresDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Functionalfailures findById(int id) {
         return (Functionalfailures)db_crud.findById(id, Functionalfailures.class);
    }

    @Override
    public  List<Functionalfailures> findByName(String description) {
         Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("FROM Functionalfailures f where f.description = :description");
                         query.setParameter("description", description);
			 functionalfailures = query.list();
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
              return functionalfailures;
    }

    @Override
    public int Save(Functionalfailures fun) {
        return db_crud.SaveItems(fun);
    }

    @Override
    public int Update(Functionalfailures fun) {
        db_crud.UpdateItems(fun);
        return fun.getFailureId();
    }

    @Override
    public List<Functionalfailures> getAllFunctionalfailures() {
        Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
			 Query query =sessionObj.createQuery("FROM Functionalfailures");
			 functionalfailures = query.list();
                        
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
		return functionalfailures;
    }

    @Override
    public List<Functionalfailures> getAllFunctionalfailuresBySubComp(int subCompId) {
        Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("FROM Functionalfailures f where f.subComponent.spcId = :subCompId");
                         query.setParameter("subCompId", subCompId);
			 functionalfailures = query.list();
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
		return functionalfailures;
    }

    @Override
    public void remove(int funId) {
         db_crud.DeleteItems(findById(funId));
    }

    @Override
    public List<Functionalfailures> getAllFunctionalfailuresByFunction(int funId) {
         Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("FROM Functionalfailures f where f.failureId = :funId");
                         query.setParameter("funId", funId);
			 functionalfailures = query.list();
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
		return functionalfailures;
    }

    @Override
    public List<Functionalfailures> getAllFuncfailBySubCompUser(int subCompId, String username) {
         Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("FROM Functionalfailures f where f.subComponent.spcId = :subCompId and f.username.username in (:username1,:username2)");
                         query.setParameter("subCompId", subCompId);
                         query.setParameter("username1", "Admin");
                         query.setParameter("username2", username);
			 functionalfailures = query.list();
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
		return functionalfailures;
    }

    @Override
    public long getAllFunctionCountByCompId(int compId) {
        Transaction tx = null;
        long funCount=0;
        try {
	        sessionObj = DBconnectionPool.getDBSession(); 
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("Select count(*) FROM Functionalfailures f where f.subComponent.spcId = :compId");
			 query.setParameter("compId", compId);
                         System.out.println("query.list().get(0) "+query.list().get(0));
                         funCount = (Long)query.list().get(0);
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
        return funCount;
    }

    @Override
    public List<Functionalfailures> findByNameBySpcId(String description, int spcId) {
          Transaction tx = null;
        List<Functionalfailures> functionalfailures = new ArrayList<Functionalfailures>();	
		
	      try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();
                
			 Query query =sessionObj.createQuery("FROM Functionalfailures f where f.description = :description and f.subComponent.spcId =:spcId");
                         query.setParameter("description", description);
                         query.setParameter("spcId", spcId);
			 functionalfailures = query.list();
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
              return functionalfailures;
    }
    
}
