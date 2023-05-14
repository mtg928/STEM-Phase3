/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.GraphvertexDao;
import com.topfield.modal.Graphvertex;
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
public class GraphvertexDaoImpl implements GraphvertexDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Graphvertex findById(int vertexid) {
        return (Graphvertex)db_crud.findById(vertexid, Graphvertex.class);
    }

    @Override
    public Graphvertex findByName(String name) {
        Transaction tx = null;
        List<Graphvertex> graphvertexList = new ArrayList<Graphvertex>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Graphvertex v where v.vertexHeading = :vertexHeading");
                        query.setParameter("vertexHeading", name);
			graphvertexList = query.list();
                        
                        
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
		return graphvertexList.get(0);
    }

    @Override
    public int Save(Graphvertex vertex) {
        return db_crud.SaveItems(vertex);
    }

    @Override
    public void Update(Graphvertex vertex) {
       db_crud.UpdateItems(vertex);
    }

    @Override
    public void Delete(int vertexId) {
        db_crud.DeleteItems(findById(vertexId));
    }

    @Override
    public List<Graphvertex> getAllVertexBySpfHdr(int spfHdrId) {
        Transaction tx = null;
        List<Graphvertex> graphvertexList = new ArrayList<Graphvertex>();		
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Graphvertex v where v.vtxspfhdr.spfhdrid = :spfHdrId");
                        query.setParameter("spfHdrId", spfHdrId);
			graphvertexList = query.list();
                        
                        
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
		return graphvertexList;
    }

    @Override
    public void deleteAllVertexBySpfHdr(int spfHdrId) {
       Transaction tx = null;
        try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("delete FROM Graphvertex v where v.vtxspfhdr.spfhdrid = :spfHdrId");
                        query.setParameter("spfHdrId", spfHdrId);       
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
    public boolean isCMMUsed(int cmmId) {
        Transaction tx = null;
	long res=0; 	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("Select count(*) FROM Graphvertex v where v.calccomp= :cmmId");
                        query.setParameter("cmmId", cmmId);
			 res = (long)query.uniqueResult();
                        
                        
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
             return res>0;
    }

    @Override
    public List<String> isCMMUsedRef(int cmmId) {
        Transaction tx = null;
	List<String> res = new ArrayList<>(); 	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("Select concat(vtxspfhdr.spfhdrid,'-',vtxspfhdr.descriptions) FROM Graphvertex v where v.calccomp= :cmmId");
                        query.setParameter("cmmId", cmmId);
			 res = query.list();
                        
                        
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
             return res;
    }
    
}
