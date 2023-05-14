/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.EventTreeDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Eventtree;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class EventTreeDaoImpl implements EventTreeDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Eventtree findById(int id) {
        Eventtree eventtree = (Eventtree)db_crud.findById(id, Eventtree.class);
        return eventtree;
    }

    @Override
    public Eventtree findByName(String name) {
        Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Eventtree e where e.eventName = :eventName ");
                        query.setParameter("eventName", name);
			EventtreeList = query.list();
                        
                        
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
		return EventtreeList.get(0);
    }

    @Override
    public int Save(Eventtree eventtree) {
       return db_crud.SaveItems(eventtree);
    }

    @Override
    public int Update(Eventtree eventtree) {
         db_crud.UpdateItems(eventtree);
        return eventtree.getEventId();
    }

    @Override
    public List<Eventtree> getAllEventtree() {
         Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			EventtreeList = sessionObj.createQuery("FROM Eventtree").list();
                        
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
		return EventtreeList;
    }

    @Override
    public List<Eventtree> getAllEvents(String user,int proId) {
        Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("FROM Eventtree e where e.directions = 'Head' and e.user.username =:user and e.projectId.proId =:proId");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        EventtreeList =query.list();
                        
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
		return EventtreeList;
    }


    @Override
    public List<Eventtree> getAllEventtreeByUserProj(String user, int proId) {
         Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("FROM Eventtree e where e.directions <> 'Head' and e.user.username =:user and e.projectId.proId =:proId");
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        EventtreeList =query.list();
                        
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
		return EventtreeList;
    }

    @Override
    public List<Eventtree> getAllEventtreeByTypeUserProj(String type,String EventName, String user, int proId) {
         Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("FROM Eventtree e where (e.directions <> 'Head' or e.directions is null) and e.eventType =:eventType and e.comments=:EventName  and e.user.username =:user and e.projectId.proId =:proId");
                        query.setParameter("eventType", type);
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("EventName", EventName);
                        EventtreeList =query.list();
                        
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
		return EventtreeList;
    }

    @Override
    public void RemoveAllEventtreeByTypeUserProj(String type, String EventName, String user, int proId) {
        Transaction tx = null;
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("delete FROM Eventtree e where e.eventType =:eventType and e.comments=:EventName  and e.user.username =:user and e.projectId.proId =:proId");
                        query.setParameter("eventType", type);
                        query.setParameter("user", user);
                        query.setParameter("proId", proId);
                        query.setParameter("EventName", EventName);
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
    public void remove(int eventId) {
       db_crud.DeleteItems(findById(eventId));
    }

    @Override
    public List<Eventtree> getAllEventsByEventHdr(int hdrId) {
         Transaction tx = null;
        List<Eventtree> EventtreeList = new ArrayList<Eventtree>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Eventtree e where e.eventHdr.eventhdrId = :eventhdrId order by eventLevel");
                        query.setParameter("eventhdrId", hdrId);
			EventtreeList = query.list();
                        
                        
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
		return EventtreeList;
    }

    @Override
    public void removeAllEventsByEventHdr(int hdrId, int eventLevel) {
         Transaction tx = null;
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("delete FROM Eventtree e where e.eventHdr.eventhdrId = :eventhdrId and eventLevel > :eventLevel");
                        query.setParameter("eventhdrId", hdrId);
                        query.setParameter("eventLevel", eventLevel);
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
    public void removeAllEventsByEventHdr(int hdrId) {
        Transaction tx = null;
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("delete FROM Eventtree e where e.eventHdr.eventhdrId = :eventhdrId");
                        query.setParameter("eventhdrId", hdrId);
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
