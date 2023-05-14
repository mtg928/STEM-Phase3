/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.dao.GraphedgeDao;
import com.topfield.modal.Graphedge;
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
public class GraphedgeDaoImpl implements GraphedgeDao{
    
    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Graphedge findById(int graphedgeid) {
        return (Graphedge)db_crud.findById(graphedgeid, Graphedge.class);
    }

    @Override
    public Graphedge findByName(String name) {
         Transaction tx = null;
        List<Graphedge> graphedgeList = new ArrayList<Graphedge>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Graphedge g where g.edgetype = :edgetype");
                        query.setParameter("edgetype", name);
			graphedgeList = query.list();
                        
                        
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
		return graphedgeList.get(0);
    }

    @Override
    public int Save(Graphedge graphedge) {
      return db_crud.SaveItems(graphedge);
    }

    @Override
    public void Update(Graphedge graphedge) {
     db_crud.UpdateItems(graphedge);
    }

    @Override
    public void Delete(int graphedgeId) {
       db_crud.DeleteItems(findById(graphedgeId));
    }

    @Override
    public List<Graphedge> getAllGraphedgeBySpfHdr(int spfHdrId) {
     Transaction tx = null;
        List<Graphedge> graphedgeList = new ArrayList<Graphedge>();		
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Graphedge g where g.vtxspfhdr.spfhdrid = :spfHdrId");
                        query.setParameter("spfHdrId", spfHdrId);
			graphedgeList = query.list();
                        
                        
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
		return graphedgeList;
    }

    @Override
    public void deleteAllGraphedgeBySpfHdr(int spfHdrId) {
         Transaction tx = null;
        try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("delete FROM Graphedge g where g.vtxspfhdr.spfhdrid = :spfHdrId");
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
    
}
