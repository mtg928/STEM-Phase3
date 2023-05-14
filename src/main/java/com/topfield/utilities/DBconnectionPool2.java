/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Murali
 */

    
public class DBconnectionPool2 {
    
    
    
    
        private static Session sessionObj;
	private static SessionFactory sessionFactoryObj;
        //public final  Logger logger = Logger.getLogger(DbOperations.class);
        
        
	// This Method Is Used To Create The Hibernate's SessionFactory Object
	public static  SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
                
                try{
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
                }catch (Throwable ex) {
			System.err.println("Session Factory could not be created." + ex);
			throw new ExceptionInInitializerError(ex);
		}	
		return sessionFactoryObj;
	}
        
     /*   public static Session getDBSession(){
        
            if( sessionObj== null || sessionObj.isOpen() == false){
             sessionObj = DBconnectionPool.buildSessionFactory().openSession();
            }
            
            return sessionObj;
        }*/
        
        public static void CloseSession(){
                    if(sessionObj != null) {
			sessionObj.close();
		    }
        }
        
}
