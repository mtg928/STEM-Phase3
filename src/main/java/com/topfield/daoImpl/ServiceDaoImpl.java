package com.topfield.daoImpl;


import com.topfield.utilities.HibernateUtil;
import com.topfield.dao.ServiceDao;
import com.topfield.modal.Test1;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author Maikel Chandika <mkdika@gmail.com>
 */
public class ServiceDaoImpl implements ServiceDao {

    Transaction tx = null;
    
    public Session getSession() {
        Session session = HibernateUtil.getInstance().getCurrentSession();
        tx = session.beginTransaction();                
        return session;        
    }

    public Session session() {
        return HibernateUtil.getInstance().getCurrentSession();
    }

    public void sessionClear() {
        HibernateUtil.getInstance().getCurrentSession().clear();
    }
      
    @Override
    public boolean save(Object obj) {                
        getSession().saveOrUpdate(obj);
        tx.commit();
        return true;
    }

    @Override
    public boolean delete(Object obj) { 
        getSession().delete(obj);
        tx.commit();
        return true;
    }

    @Override
    public List<Test1> getTest1All() {        
        List<Test1> list = getSession().createQuery("from Test1 a order by a.column1").list();
        tx.commit();
        return list;
    }        

    @Override
    public boolean deleteTest1All() {
        getSession().createQuery("delete from Test1").executeUpdate();
        tx.commit();
        return true;
    }

    @Override
    public java.sql.Connection getConn() {
        SessionImpl sessionImpl = (SessionImpl) HibernateUtil.getInstance().getCurrentSession();
        return sessionImpl.connection();
    }
}
