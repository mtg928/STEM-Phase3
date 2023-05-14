/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Fmeca;
import com.topfield.utilities.DBconnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.topfield.dao.MpgHdrDao;

/**
 *
 * @author Murali
 */
public class MpgHdrDaoImpl implements MpgHdrDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public List<Mpghdr> getAllFMEAS() {
        Transaction tx = null;
        List<Mpghdr> FmeasList = new ArrayList<Mpghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			FmeasList = sessionObj.createQuery("FROM Mpghdr").list();
                        
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
		return FmeasList;
    }

    @Override
    public Mpghdr getFMEASById(int fmeasId) {
        Transaction tx = null;
  
         Mpghdr fmeas = new Mpghdr();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			fmeas = (Mpghdr)sessionObj.get(Mpghdr.class, fmeasId);
                        
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
		return fmeas;
    }

    @Override
    public void UpdateFMEAS(Mpghdr fmeas) {
         db_crud.UpdateItems(fmeas);
    }

    @Override
    public int saveFMEAS(Mpghdr fmeas) {
        return  db_crud.SaveItems(fmeas);
    }

    @Override
    public void deleteFMEAS(int fmeasId) {
        db_crud.DeleteItems(getFMEASById(fmeasId));
    }

    @Override
    public List<Mpghdr> getAllFMEAHDRByMPG(int mpgId,String cal, int proId, String username) {
        Transaction tx = null;
        List<Mpghdr> fmeaList = new ArrayList<Mpghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Mpghdr f where projectId.proId =:fmecaproid and user.username =:fmecauser and f.fmeaComponent.mpgId =:mpgId and f.calname =:calname");
                        query.setParameter("mpgId", mpgId);
                        query.setParameter("fmecaproid", proId);
                        query.setParameter("fmecauser", username);
                        query.setParameter("calname", cal);
			fmeaList = query.list();
                        
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
		return fmeaList;
    }
    
     @Override
    public Mpghdr getAllFMEAHDRByMPGOne(int mpgId,String cal, int proId, String username) {
        Transaction tx = null;
        List<Mpghdr> fmeaList = new ArrayList<Mpghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Mpghdr f where projectId.proId =:fmecaproid and user.username =:fmecauser and f.fmeaComponent.mpgId =:mpgId and f.calname =:calname");
                        query.setParameter("mpgId", mpgId);
                        query.setParameter("fmecaproid", proId);
                        query.setParameter("fmecauser", username);
                        query.setParameter("calname", cal);
			fmeaList = query.list();
                        
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
		  if (fmeaList.size()>0) {
                     return fmeaList.get(0);
                } else {
                    // System.out.println("Null Retutn");
                      return null;
                }	
    }

    @Override
    public List<Mpghdr> getSummaryFMEAHERByMPG(int proId,String cal, String username) {
        Transaction tx = null;
        List<Mpghdr> fmeaList = new ArrayList<Mpghdr>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Mpghdr f where projectId.proId =:fmecaproid and f.calname =:calname"); /* and user.username =:fmecauser group by f.fmeaComponent.mpgId*/
                        query.setParameter("fmecaproid", proId);
                       // query.setParameter("fmecauser", username);
                        query.setParameter("calname", cal);
			fmeaList = query.list();
                        
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
		return fmeaList;
    }
    
}
