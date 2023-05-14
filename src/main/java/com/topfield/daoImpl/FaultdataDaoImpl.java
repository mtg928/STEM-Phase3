/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.FaultdataDao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Faulttree;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FaultdataDaoImpl implements FaultdataDao{
    
       private Session sessionObj;
       private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public Faultdata findById(int id) {
       Faultdata fd = (Faultdata)db_crud.findById(id, Faultdata.class);

        return fd;
    }

    @Override
    public Faultdata findByName(String name) {
        Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Faultdata f where f.name = :name ");
                        query.setParameter("name", name);
			fdList = query.list();
                        
                        
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
		return fdList.get(0);
    }

    @Override
    public int Save(Faultdata fd) {
        Faultdata parent = fd.getParent();
        

        int res = db_crud.SaveItems(fd);
        
        if (parent != null) {
           // parent.getFaultdataCollection().add(fd);
        }
        return res;
    }

    @Override
    public void Update(Faultdata fd) {
         db_crud.UpdateItems(fd);
    }

    @Override
    public List<Faultdata> getAllCcf() {
         Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			fdList = sessionObj.createQuery("FROM Faultdata").list();
                        
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
		return fdList;
    }

    @Override
    public List<Faultdata> getAllFaultdataByFaulttree(int faultId) {
         Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query =sessionObj.createQuery("FROM Faultdata f where f.faulttree.faultId = :faultId order by f.category ");
                        query.setParameter("faultId", faultId);
			fdList = query.list();
                        
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
		return fdList;
    }

    @Override
    public void remove(int faultId) {
       Faultdata fd = findById(faultId);   
       Faultdata parent = fd.getParent(); 
       
       db_crud.DeleteItems(fd); 
       
        if (parent != null) {
            parent.getFaultdataCollection().remove(fd);
        }
        
       
    }

    @Override
    public Faultdata findByName(String name, int spfHdrId) {
         Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
                        Query query =sessionObj.createQuery("FROM Faultdata f where f.faultHeader.spfhdrid = :spfHdrId and f.name = :name ");
                        query.setParameter("spfHdrId", spfHdrId);
                        query.setParameter("name", name);
			fdList = query.list();
                        
                        
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
		return fdList.get(0);
    }

    @Override
    public void removeByFaultTreeId(int faultTreeId) {
       Transaction tx = null;
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("delete Faultdata f where f.faulttree.faultId =:faultTreeId");
                        query.setParameter("faultTreeId", faultTreeId);
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
    public List<Faultdata> getAllFaultdataBySpfHdr(int spfHdrId) {
        Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query =sessionObj.createQuery("FROM Faultdata f where f.faultHeader.spfhdrid = :spfHdrId "); //order by f.category
                        query.setParameter("spfHdrId", spfHdrId);
			fdList = query.list();
                        //sessionObj.evict(fdList);
                        //sessionObj.flush();
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
		return fdList;
    }

    @Override
    public void removeBySpfHdr(int spfHdrId) {
        Transaction tx = null;
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query = sessionObj.createQuery("delete Faultdata f where f.faultHeader.spfhdrid = :spfHdrId");
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
    public List<Faultdata> getAllFaultdataBySpfHdr(int spfHdrId, int parentId) {
         Transaction tx = null;
        List<Faultdata> fdList = new ArrayList<Faultdata>();	
        
		try {
			sessionObj = DBconnectionPool.getDBSession();       
		        tx = sessionObj.beginTransaction();
                        
			Query query =sessionObj.createQuery("FROM Faultdata f "
                                                          + "where f.faultHeader.spfhdrid = :spfHdrId "
                                                          + "and f.parent.faultdataId=:parentId  "
                                                          + "and f.faultdataId <> :parentId  " //+ "order by f.category "
                                                          );
                        query.setParameter("spfHdrId", spfHdrId);
                        query.setParameter("parentId", parentId);
			fdList = query.list();
                        
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
		return fdList;
    }

  /*  @Override
    public void clearCache(Object object) {
        //sessionObj.clear();
        if(sessionObj != null && object!= null) {
	sessionObj.evict(object);                   
	}
        
    }

    @Override
    public void refresh(List<Faultdata> ftDatas) {
        for (Faultdata ftData : ftDatas) {
            sessionObj.refresh( ftData);
        }
       
    }*/
    
}
