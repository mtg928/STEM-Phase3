/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductComponents;
import com.topfield.modal.SubProductGroup;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class SubProductGroupDaoImpl implements SubProductGroupDao{

    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();
    
    @Override
    public List<SubProductGroup> getAllSPG() {
         Transaction tx = null;
        List<SubProductGroup> spgList = new ArrayList<SubProductGroup>();
        
        try {
			sessionObj = DBconnectionPool.getDBSession();
		    tx =sessionObj.beginTransaction();

		    spgList = sessionObj.createQuery("FROM SubProductGroup").list();
                        
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
        
        
        return spgList;
    }

    @Override
    public SubProductGroup getSPGById(int SPGId) {
        SubProductGroup spg = null;
         Transaction tx = null;
                   try {
			sessionObj = DBconnectionPool.getDBSession();
		tx =	sessionObj.beginTransaction();

                spg = (SubProductGroup)sessionObj.get(SubProductGroup.class, SPGId);
                tx.commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} 
                   
        return spg;           
       
    }

    @Override
    public SubProductGroup getSPGByName(String SPGName) {
              Transaction tx = null;
  
      List<SubProductGroup> spcList = new ArrayList<SubProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductGroup s where s.spgDescription = :spgDescription");
                         query.setParameter("spgDescription", SPGName);
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
    public String[] getAllSPGHeading() {
        List<SubProductGroup> list = getAllSPG();
        String[] compHead = new String[list.size()];
        int count =0;
        
        for (SubProductGroup comp : list) {
            
            compHead[count] = comp.getSpgDescription();
            count++;
        }
       
        

        return compHead;
    }

    @Override
    public int Save(SubProductGroup spg) {
       return db_crud.SaveItems(spg);
    }

    @Override
    public void Update(SubProductGroup spg) {
        db_crud.UpdateItems(spg);
    }

    @Override
    public void Delete(int spgId) {
        db_crud.DeleteItems(getSPGById(spgId));
    }

    @Override
    public List<SubProductGroup> getAllSPGByUser(String username) {
         Transaction tx = null;
  
      List<SubProductGroup> spcList = new ArrayList<SubProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductGroup s where s.username.username = :username");
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
    public  List<SubProductGroup> getSPGByMpg(int mpgId) {
     Transaction tx = null;
  
      List<SubProductGroup> spcList = new ArrayList<SubProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductGroup s where mpgRef.mpgId = :mpgId");
                         query.setParameter("mpgId", mpgId);
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
    public SubProductGroup getSPGByNameAndMpg(String SPGName, int mpgId) {
                   Transaction tx = null;
  
      List<SubProductGroup> spcList = new ArrayList<SubProductGroup>();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			 Query query =sessionObj.createQuery("FROM SubProductGroup s where s.spgDescription = :spgDescription and mpgRef.mpgId = :mpgId");
                         query.setParameter("spgDescription", SPGName);
                         query.setParameter("mpgId", mpgId);
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
