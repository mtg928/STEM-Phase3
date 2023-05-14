/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.topfield.user.HybridData;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class DBconnectionPool {

    private static Session sessionObj;
    private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {

		if (sessionFactory == null) {
			try {
				Properties settings = new Properties();

				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, HybridData.getUrl());
				settings.put(Environment.USER, HybridData.getUsername() );
				settings.put(Environment.PASS, HybridData.getPassword());
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.FORMAT_SQL, "true");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                                
                                settings.put("hibernate.c3p0.acquire_increment", 1);
                                settings.put("hibernate.c3p0.idle_test_period", 300);
                                settings.put("hibernate.c3p0.timeout", 600);
                                settings.put("hibernate.c3p0.max_size", 5);
                                settings.put("hibernate.c3p0.min_size", 1);
                                settings.put("hibernate.c3p0.max_statement", 0);
                                settings.put("hibernate.c3p0.acquireRetryAttempts", 1);
                                settings.put("hibernate.c3p0.acquireRetryDelay", 250);

				Configuration configuration = new Configuration();
				configuration.setProperties(settings);

				configuration.addAnnotatedClass(com.topfield.modal.Student.class);
                                configuration.addAnnotatedClass(com.topfield.modal.MainProductGroup.class);
                                configuration.addAnnotatedClass(com.topfield.modal.SubProductGroup.class);
                                configuration.addAnnotatedClass(com.topfield.modal.SubProductFunctions.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Components.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Fmea.class);
                                configuration.addAnnotatedClass(com.topfield.modal.MainProductComponents.class);
                                configuration.addAnnotatedClass(com.topfield.modal.SubProductComponents.class);
                                configuration.addAnnotatedClass(com.topfield.modal.TclProjects.class);
                                configuration.addAnnotatedClass(com.topfield.modal.UserRoles.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Users.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Fmeca.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Test1.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Ccf.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Sil.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Hazid.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Hazards.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Eventtree.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Faultdata.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Faulttree.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Functionalfailures.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Mpghdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Standards.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Spchdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Spfhdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Spghdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Ccfhdr.class); 
                                configuration.addAnnotatedClass(com.topfield.modal.Eventtreehdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Failuredata.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Failuremodes.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Datarefer.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Graphvertex.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Graphedge.class);
                                configuration.addAnnotatedClass(com.topfield.modal.MainProductFunctions.class);
                                configuration.addAnnotatedClass(com.topfield.modal.CalcFunctions.class);        
                                configuration.addAnnotatedClass(com.topfield.modal.Calcfile.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Packages.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Notification.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Assumptions.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Calchdr.class);
                                configuration.addAnnotatedClass(com.topfield.modal.Userpreferences.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sessionFactory;
	}
        
        public static Session getDBSession(){
        
            if( sessionFactory== null || sessionObj == null || sessionObj.isOpen() == false){
             sessionObj = DBconnectionPool.getSessionFactory().openSession(); //getCurrentSession()
            }
            
            return sessionObj;
        }

	public static void closeSessionFactory() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
        
        
       public static void referesh(Object obj){
          Transaction tx = null;
          
	       sessionObj = DBconnectionPool.getDBSession();       
	       tx = sessionObj.beginTransaction();
               
               sessionObj.refresh(obj);
               tx.commit();
               

     
     }
     
     public static void marge(Object obj){
      Transaction tx = null;
          
	       sessionObj = DBconnectionPool.getDBSession();       
	       tx = sessionObj.beginTransaction();
               
               sessionObj.merge(obj);
               tx.commit();

     
     }
     
     public static void evict(Object obj){
      Transaction tx = null;

	       sessionObj = DBconnectionPool.getDBSession();       
	       tx = sessionObj.beginTransaction();
               
               sessionObj.evict(obj);
               tx.commit();
              
     
     }
}
