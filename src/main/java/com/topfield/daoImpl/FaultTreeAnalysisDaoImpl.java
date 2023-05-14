/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.FaultTreeAnalysisDao;
import com.topfield.modal.FaultTreeAnalysis;
import com.topfield.modal.Fmea;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FaultTreeAnalysisDaoImpl implements FaultTreeAnalysisDao {
    
    private Session sessionObj;

    @Override
    public List<FaultTreeAnalysis> getAllFaultTreeAnalysis() {
        
       Transaction tx = null;
        List<FaultTreeAnalysis> fTList = new ArrayList<FaultTreeAnalysis>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			fTList = sessionObj.createQuery("FROM FaultTreeAnalysis").list();
                        
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
		return fTList;
    }

    @Override
    public FaultTreeAnalysis getFaultTreeAnalysisById(int faultTreeId) {
       
        Transaction tx = null;
        List<FaultTreeAnalysis> fTList = new ArrayList<FaultTreeAnalysis>();
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM FaultTreeAnalysis f where f.fTid = :faultTreeId ");
                        query.setParameter("faultTreeId", faultTreeId);
			fTList = query.list();
                        
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
		return fTList.get(0);
        
    }

    @Override
    public void UpdateFaultTreeAnalysis(FaultTreeAnalysis faultTree) {
        
        Transaction tx = null;
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        sessionObj.merge(faultTree);
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
    }

    @Override
    public void saveFaultTreeAnalysis(FaultTreeAnalysis faultTree) {
       Transaction tx = null;
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        sessionObj.save(faultTree);
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
    }

    @Override
    public void deleteFaultTreeAnalysis(int faultTreeId) {
              Transaction tx = null;
  
        FaultTreeAnalysis ft = getFaultTreeAnalysisById(faultTreeId);		
		try {
	        sessionObj = DBconnectionPool.getDBSession(); 
		tx = sessionObj.beginTransaction();

		sessionObj.delete(ft);
                        
                        tx.commit();
                        System.out.println("Deleted Id - "+faultTreeId);
                        
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
    }
    
}
