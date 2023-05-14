/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.SubProductFunctionsDao;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductFunctions;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class SubProductFunctionsDaoImpl implements SubProductFunctionsDao{
    
    private Session sessionObj;

    @Override
    public SubProductFunctions getSPGFunctionById(int SPGId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SubProductFunctions> getAllSPGFunction() {
      Transaction tx = null;
      List<SubProductFunctions> spfList = new ArrayList<SubProductFunctions>();
      
       
      
      
		try {
			sessionObj = DBconnectionPool.getDBSession();
		        tx = sessionObj.beginTransaction();
			spfList = sessionObj.createQuery("FROM SubProductFunctions").list();
                        
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
		return spfList;
    }
    
}
