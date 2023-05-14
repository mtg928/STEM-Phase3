/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.daoImpl;

import com.topfield.utilities.DBconnectionPool;
import com.topfield.dao.FMECADao;
import com.topfield.controller.DB_CRUD_Interface;
import com.topfield.modal.Ccf;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.user.UserInfo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Murali
 */
public class FMECADaoImpl implements FMECADao {

    private Session sessionObj;
    private DB_CRUD_Interface db_crud = new DB_CRUD_Interface();

    @Override
    public List<Fmeca> getAllFMECA() {
        Transaction tx = null;
        List<Fmeca> fmecaList = new ArrayList<Fmeca>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            fmecaList = sessionObj.createQuery("FROM Fmeca").list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmecaList;
    }

    @Override
    public Fmeca getFMECAById(int fmecaId) {
        Transaction tx = null;

        Fmeca fmeca  = (Fmeca)db_crud.findById(fmecaId, Fmeca.class);
        
        return fmeca;
    }

    @Override
    public List<Fmeca> getAllFMECAByMPG(int mpgId) {
        Transaction tx = null;
        List<Fmeca> fmecaList = new ArrayList<Fmeca>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("FROM Fmeca f where f.fmecaComponent.mpgId = :mpgId ");
            query.setParameter("mpgId", mpgId);
            fmecaList = query.list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmecaList;
    }

    @Override
    public void UpdateFMECA(Fmeca fmeca) {
        db_crud.UpdateItems(fmeca);
    }

    @Override
    public void saveFMECA(Fmeca fmeca) {
        db_crud.SaveItems(fmeca);
    }

    @Override
    public void deleteFMECA(int fmecaId) {
        db_crud.DeleteItems(getFMECAById(fmecaId));
    }

    @Override
    public List<Fmeca> getAllFMECAByMPG(int mpgId, int proId, String username) {
        Transaction tx = null;
        List<Fmeca> fmecaList = new ArrayList<Fmeca>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("FROM Fmeca f where f.fmecaComponent.mpgId = :mpgId and f.fmecaproid.proId = :fmecaproid and f.fmecauser.username = :fmecauser");
            query.setParameter("mpgId", mpgId);
            query.setParameter("fmecaproid", proId);
            query.setParameter("fmecauser", username);
            fmecaList = query.list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmecaList;
    }

    @Override
    public List<Object[]> getSummaryFMECAByMPG(int proId, String username) {
        Transaction tx = null;
        List<Object[]> fmeaList = new ArrayList<Object[]>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("SELECT f.fmecaComponent.mpgId,f.fmecaComponent.mpgDescription, count(*),SUM(f.fCFailItemCri),SUM(f.fCFailModeCri) FROM Fmeca f where fmecaproid.proId =:fmecaproid and fmecauser.username =:fmecauser group by f.fmecaComponent.mpgId");
            query.setParameter("fmecaproid", proId);
            query.setParameter("fmecauser", username);
            fmeaList = query.list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmeaList;
    }

    @Override
    public void deleteFMECAComponents(String user, int proId, int compId) {
        Transaction tx = null;
        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("delete FROM Fmeca f where f.fmecaComponent.mpgId = :mpgId and f.fmecaproid.proId = :fmecaproid and f.fmecauser.username = :fmecauser ");
            query.setParameter("fmecauser", user);
            query.setParameter("fmecaproid", proId);
            query.setParameter("mpgId", compId);
            query.executeUpdate();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
    }

    @Override
    public List<Fmeca> getAllFMECAByHdr(int hdrId) {
        Transaction tx = null;
        List<Fmeca> fmecaList = new ArrayList<Fmeca>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

           /* String sql = "SELECT f.* FROM fmeca f  JOIN calchdr h ON f.FmecaHeader=h.calchdrid where h.calchdrid =:hdrId "
                    + "order by SUBSTRING(FI_Subsys_Break_Code, 1,:subStrCount),"
                    + "CAST(SUBSTRING(FI_Subsys_Break_Code, (:subStrCount+1),2)AS unsigned), "
                    + "CAST(SUBSTRING(FI_Subsys_Break_Code, (LOCATE( \"-\",FI_Subsys_Break_Code)+1)) AS unsigned)";
            SQLQuery query = sessionObj.createSQLQuery(sql);
            query.setParameter("hdrId", hdrId);
            query.setParameter("subStrCount", (UserInfo.getInstance().getProAbbreviations().length() + 3));
            query.addEntity(Fmeca.class);
            fmecaList = query.list();*/
           
           String sql = "SELECT f.* FROM fmeca f  JOIN calchdr h ON f.FmecaHeader=h.calchdrid where h.calchdrid =:hdrId ";
            SQLQuery query = sessionObj.createSQLQuery(sql);
            query.setParameter("hdrId", hdrId);
            query.addEntity(Fmeca.class);
            fmecaList = query.list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmecaList;
    }

    @Override
    public List<Fmeca> getAllFMECAByMPGHdrFun(int mpgHdrId, int funId) {
        Transaction tx = null;
        List<Fmeca> fmecaList = new ArrayList<Fmeca>();

        try {
            sessionObj = DBconnectionPool.getDBSession();
            tx = sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("FROM Fmeca f where f.fmecaHeader.calchdrid = :mpgHdrId and calcComp.calcHeader.calcFunId = :funId");
            query.setParameter("mpgHdrId", mpgHdrId);
            query.setParameter("funId", funId);
            fmecaList = query.list();

            tx.commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                //sessionObj.close();

            }
        }
        return fmecaList;
    }

}
