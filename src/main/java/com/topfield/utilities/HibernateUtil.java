
package com.topfield.utilities;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author hennebrueder This class garanties that only one single SessionFactory
 *         is instanciated and that the configuration is done thread safe as
 *         singleton. Actually it only wraps the Hibernate SessionFactory.
 *         You are free to use any kind of JTA or Thread transactionFactories.
 */
public class HibernateUtil {

   /** The single instance of hibernate SessionFactory */
   private static org.hibernate.SessionFactory sessionFactory;

   
   @SuppressWarnings("deprecation")
   public static SessionFactory getInstance() {
      /*if (sessionFactory == null) {
         // Annotation and XML
         sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
      }*/
      
      		try{
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();

		}catch (Throwable ex) {
			System.err.println("Session Factory could not be created." + ex);
			throw new ExceptionInInitializerError(ex);
		}
      
      return sessionFactory;
   }

   /**
    * Opens a session and will not bind it to a session context
    * @return the session
    */
   public Session openSession() {
      return sessionFactory.openSession();
   }

   /**
    * Returns a session from the session context. If there is no session in the context it opens a session,
    * stores it in the context and returns it.
    * This factory is intended to be used with a hibernate.cfg.xml
    * including the following property <property
    * name="current_session_context_class">thread</property> This would return
    * the current open session or if this does not exist, will create a new
    * session
    *
    * @return the session
    */
   public Session getCurrentSession() {
      return sessionFactory.getCurrentSession();
   }

   /**
    * closes the session factory
    */
   public static void close() {
      if (sessionFactory != null) {
         sessionFactory.close();
      }
      sessionFactory = null;
   }
}
