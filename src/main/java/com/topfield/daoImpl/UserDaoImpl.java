/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.UserDao;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Users;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class UserDaoImpl implements UserDao{

   private Session sessionObj;
   
    @Override
    public Users findById(String username) {
         Transaction tx = null;
  
         Users user = new Users();		
		try {
			sessionObj = DBconnectionPool.getDBSession();
                        
                        
		tx = sessionObj.beginTransaction();

			user = (Users)sessionObj.get(Users.class, username);
                        
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
		return user;
     
    }

    @Override
    public boolean checkedValidLicense(Date start, Date end) {
       Calendar cal = Calendar.getInstance();
       Date testDate = cal.getTime();
       
        System.out.println("testDate "+ testDate);
        
        return testDate.after(start) && testDate.before(end);
    }
    
}
