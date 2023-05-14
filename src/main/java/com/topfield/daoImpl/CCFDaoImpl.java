/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.CCFDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Ccf;
import com.topfield.modal.Fmeca;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class CCFDaoImpl implements CCFDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Ccf findById(int id) {
        Transaction tx = null;
  
         Ccf ccf = (Ccf)db_crud.findById(id, Ccf.class);

        return ccf;
    }

    @Override
    public Ccf findByName(String description) {
        Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.description = :description ");
                        query.setParameter("description", description);
			CcfList = query.list();
                        
                        
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
		return CcfList.get(0);
    }

    @Override
    public int Save(Ccf ccf) {
        return db_crud.SaveItems(ccf);
    }

    @Override
    public List<Ccf> getAllCcf() {
        Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			CcfList = sessionObj.createQuery("FROM Ccf").list();
                        
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
		return CcfList;
    }

    @Override
    public List<Ccf> getAllCcfByType(String CcfType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ccf> getAllCcfByTypeUserProj(String user, int proId, String CcfType,int compId) {
        Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.user.username =:user and f.projectId.proId =:proId and f.mainCategory = :CcfType and f.ccfSubComponent.spgId =:compId ");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("CcfType",CcfType);
                        query.setParameter("compId",compId);        
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }

    @Override
    public int Update(Ccf ccf) {
       db_crud.UpdateItems(ccf);
       return ccf.getCcfId();
    }

    @Override
    public List<Ccf> getAllCcfByTypeUserProj(String user, int proId, String CcfType) {
       Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.user.username =:user and f.projectId.proId =:proId and f.mainCategory = :CcfType ");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("CcfType",CcfType);       
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }

    @Override
    public List<Ccf> getFinalCcfByTypeUserProj(String user, int proId, String CcfType, String desc) {
        Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.user.username =:user and f.projectId.proId =:proId and f.mainCategory = :CcfType and f.description =:description ");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("CcfType",CcfType);
                        query.setParameter("description",desc);        
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }
      @Override
    public List<Ccf> getFinalCcfByTypeUserProj(String user, int proId, String desc) {
        Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.user.username =:user and f.projectId.proId =:proId  and f.description =:description ");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("description",desc);        
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }

    @Override
    public void deleteCCFComponents(String user, int proId, String CcfType, int compId) {
        Transaction tx = null;
        try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("delete FROM Ccf f where f.user.username =:user and f.projectId.proId =:proId and f.mainCategory = :CcfType and f.ccfSubComponent.spgId =:compId ");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("CcfType",CcfType);
                        query.setParameter("compId",compId);        
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

    @Override
    public List<Ccf> getAllCcfByHdrId(int hdrId) {
         Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.ccfHdr.ccfhdrId = :hdrId ");
                        query.setParameter("hdrId", hdrId);
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }

    @Override
    public List<Ccf> getAllCcfByCalcHdrId(int calcHdrId) {
         Transaction tx = null;
        List<Ccf> CcfList = new ArrayList<Ccf>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Ccf f where f.ccfHdr.spfhdrid = :calcHdrId ");
                        query.setParameter("calcHdrId", calcHdrId);
			CcfList = query.list();
                        
                        
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
		return CcfList;
    }
}
