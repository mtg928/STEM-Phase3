/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.modal.MainProductComponents;
import com.topfield.modal.SubProductComponents;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class SubProductComponentsDaoImpl implements SubProductComponentsDao {

     private Session sessionObj;
     private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
     
    @Override
    public SubProductComponents FindById(int spcId) {
        Transaction tx = null;
  
        SubProductComponents spcList = new SubProductComponents();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			spcList = (SubProductComponents)sessionObj.get(SubProductComponents.class, spcId);
                        
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
		return spcList;
    }

    @Override
    public List<SubProductComponents> getAllSPC() {
           Transaction tx = null;
  
      List<SubProductComponents> spcList = new ArrayList<SubProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			spcList = sessionObj.createQuery("FROM SubProductComponents").list();
                        
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
		return spcList;
    }

    @Override
    public List<SubProductComponents> getAllSPCBySPG(int spgId) {
              Transaction tx = null;
  
      List<SubProductComponents> spcList = new ArrayList<SubProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductComponents s where s.spgRef.spgId = :spgId");
                         query.setParameter("spgId", spgId);
                         spcList = query.list();
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
		return spcList;
    }

    @Override
    public SubProductComponents FindByName(String spcName) {
               Transaction tx = null;
  
      List<SubProductComponents> spcList = new ArrayList<SubProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductComponents s where s.spcDescription = :spcDescription");
                         query.setParameter("spcDescription", spcName);
                         spcList = query.list();
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
                 if (spcList.size()>0) {
                     return spcList.get(0);
                } else {
                      return null;
                }
    }

    @Override
    public int Save(SubProductComponents spc) {
       return db_crud.SaveItems(spc);
    }

    @Override
    public void Update(SubProductComponents spc) {
        db_crud.UpdateItems(spc);
    }

    @Override
    public void Delete(int spcId) {
       db_crud.DeleteItems(FindById(spcId));
    }

    @Override
    public List<SubProductComponents> getAllSPCByUser(String username) {
             Transaction tx = null;
  
      List<SubProductComponents> spcList = new ArrayList<SubProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductComponents s where s.username.username = :username");
                         query.setParameter("username", username);
                         spcList = query.list();
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
		return spcList;
    }

    @Override
    public SubProductComponents FindByNameBySpg(String spcName, int spgId) {
             Transaction tx = null;
  
      List<SubProductComponents> spcList = new ArrayList<SubProductComponents>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductComponents s where s.spcDescription = :spcDescription and spgRef.spgId =:spgId");
                         query.setParameter("spcDescription", spcName);
                         query.setParameter("spgId", spgId);
                         spcList = query.list();
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
                 if (spcList.size()>0) {
                     return spcList.get(0);
                } else {
                      return null;
                }
    }
    
}
