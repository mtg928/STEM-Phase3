/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.FMEADao;
import com.topfield.modal.Components;
import com.topfield.modal.Fmea;
import com.topfield.modal.MainProductComponents;
import com.topfield.modal.MainProductGroup;
import com.topfield.user.UserInfo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FMEADaoImpl implements FMEADao{
    
     private Session sessionObj;
     private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    
    @Override
    public List<Fmea> getAllFMEA() {
        Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

			fmeaList = sessionObj.createQuery("FROM Fmea").list();
                        
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
    public List<Fmea> getAllFMEAByMPG(int mpgId) {
         Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Fmea f where f.fmeaComponent.mpgId = :mpgId ");
                        query.setParameter("mpgId", mpgId);
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
    public void UpdateFMEA(Fmea fmea) {
       db_crud.UpdateItems(fmea);
		
    }

    @Override
    public void saveFMEA(Fmea fmea) {
      db_crud.SaveItems(fmea);
    }

    @Override
    public void deleteFMEA(int fmeaId) {
       Transaction tx = null;
  
        Fmea fmea = getFMEAById(fmeaId);		
		try {
	        sessionObj = DBconnectionPool.getDBSession(); 
		tx = sessionObj.beginTransaction();

		sessionObj.delete(fmea);
                        
                        tx.commit();
                        System.out.println("Deleted Id - "+fmeaId);
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
    public Fmea getFMEAById(int fmeaId) {
        Transaction tx = null;
  
        Fmea fmea  =  (Fmea)db_crud.findById(fmeaId, Fmea.class);	
	
	return fmea;
    }

    @Override
    public List<Fmea> getAllFMEAByMPG(int mpgId, int proId, String username) {
         Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
        
        try {
            
         sessionObj = DBconnectionPool.getDBSession();       
         tx = sessionObj.beginTransaction();   
         
         String sql = "SELECT * FROM fmea where project_id =:fmecaproid and Fmea_Component = :mpgId and user =:fmecauser "
                     + "order by SUBSTRING(FI_Subsys_Breakdown_Code, 1,:subStrCount),"
                     + "CAST(SUBSTRING(FI_Subsys_Breakdown_Code, (:subStrCount+1),2)AS unsigned), "
                     + "CAST(SUBSTRING(FI_Subsys_Breakdown_Code, (LOCATE( \"-\",FI_Subsys_Breakdown_Code)+1)) AS unsigned)";
         SQLQuery query = sessionObj.createSQLQuery(sql);
         query.setParameter("mpgId", mpgId);
         query.setParameter("fmecaproid", proId);
         query.setParameter("fmecauser", username);
         query.setParameter("subStrCount", (UserInfo.getInstance().getProAbbreviations().length()+3));
         query.addEntity(Fmea.class);
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
        
        
        
		/*try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Fmea f where f.fmeaComponent.mpgId = :mpgId and projectId.proId =:fmecaproid and user.username =:fmecauser "
                                                            + "order by "
                                //+ "                         cast(SUBSTRING(f.fISubsysBreakdownCode,(:subStrCount+1),2)as integer),"
                                //+ "                         cast(SUBSTRING(f.fISubsysBreakdownCode,:subStrCount+1)as integer),"
                                //+"LENGTH(f.fISubsysBreakdownCode),"
                                + "                         cast(SUBSTRING(f.fISubsysBreakdownCode,(LOCATE(f.fISubsysBreakdownCode, '-')+1))as integer) asc");  //order by f.fISubsysBreakdownCode  REPLACE(SUBSTRING(f.fISubsysBreakdownCode,(:subStrCount+1),2),'-','')
                        query.setParameter("mpgId", mpgId);
                        query.setParameter("fmecaproid", proId);
                        query.setParameter("fmecauser", username);
                        //query.setParameter("subStrCount", (UserInfo.getInstance().getProAbbreviations().length()+3));
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
		}*/
		return fmeaList;
    }

    @Override
    public List<Object[]> getSummaryFMEAByMPG(int proId, String username) {
         Transaction tx = null;
        List<Object[]> fmeaList = new ArrayList<Object[]>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("SELECT f.fmeaComponent.mpgId,f.fmeaComponent.mpgDescription, count(*),SUM(f.irRpz) FROM Fmea f where projectId.proId =:fmecaproid and user.username =:fmecauser group by f.fmeaComponent.mpgId");
                        query.setParameter("fmecaproid", proId);
                        query.setParameter("fmecauser", username);
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
    public void deleteFMEAComponents(String user, int proId, int compId) {
        Transaction tx = null;
        try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("delete FROM Fmea f where f.fmeaComponent.mpgId = :mpgId and projectId.proId =:fmeaproid and user.username =:fmeauser ");
                        query.setParameter("fmeauser", user);
                        query.setParameter("fmeaproid", proId);
                        query.setParameter("mpgId",compId);        
			query.executeUpdate();
                        
                        
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

  /*  @Override
    public List<Fmea> getAllFMEAByHdr(int hdrId) {
        Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
        
        try {
            
         sessionObj = DBconnectionPool.getDBSession();       
         tx = sessionObj.beginTransaction();   
         
         String sql = "SELECT f.* FROM fmea f  JOIN mpghdr h ON f.FmeaHeader=h.mpghdrid where h.mpghdrid =:hdrId "
                     + "order by SUBSTRING(FI_Subsys_Breakdown_Code, 1,:subStrCount),"
                     + "CAST(SUBSTRING(FI_Subsys_Breakdown_Code, (:subStrCount+1),2)AS unsigned), "
                     + "CAST(SUBSTRING(FI_Subsys_Breakdown_Code, (LOCATE( \"-\",FI_Subsys_Breakdown_Code)+1)) AS unsigned)";
         SQLQuery query = sessionObj.createSQLQuery(sql);
         query.setParameter("hdrId", hdrId);
         query.setParameter("subStrCount", (UserInfo.getInstance().getProAbbreviations().length()+3));
         query.addEntity(Fmea.class);
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
    }*/

    
    @Override
    public List<Fmea> getAllFMEAByHdr(int hdrId) {
         Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Fmea f where f.fmeaHeader.calchdrid = :mpgHdrId");
                        query.setParameter("mpgHdrId", hdrId);
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
    public List<Fmea> getAllFMEAByMPGHdrFun(int mpgHdrId, int funId) {
         Transaction tx = null;
        List<Fmea> fmeaList = new ArrayList<Fmea>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();

                        Query query =sessionObj.createQuery("FROM Fmea f where f.fmeaHeader.calchdrid = :mpgHdrId and calcComp.calcHeader.calcFunId = :funId");
                        query.setParameter("mpgHdrId", mpgHdrId);
                        query.setParameter("funId", funId);
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
