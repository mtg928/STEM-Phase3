/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.EventTreeDao;
import com.topfield.dao.GraphvertexDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.SpghdrDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.EventTreeDaoImpl;
import com.topfield.daoImpl.GraphvertexDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.SpghdrDaoImpl;

/**
 *
 * @author Murali
 */
public class DaoInstances {
    private static  SpghdrDao spghdrDao;
    private static  GraphvertexDao graphDao;
    private static  CalcfileDao calcDao ;
    private static  MpgHdrDao mpgDao ;
    private static  CalcFunctionsDao calcFunDao;
    private static EventTreeDao eventDao;

    public static SpghdrDao getSpghdrDao() {  
        if (spghdrDao == null) {
             spghdrDao = new SpghdrDaoImpl();
        }
        return spghdrDao;
    }

    public static GraphvertexDao getGraphDao() {
         if (graphDao == null) {
             graphDao = new GraphvertexDaoImpl();
        }
        return graphDao;
    }

    public static CalcfileDao getCalcDao() {
         if (calcDao == null) {
             calcDao = new CalcfileDaoImpl();
        }
        return calcDao;
    }

    public static MpgHdrDao getMpgDao() {
         if (mpgDao == null) {
             mpgDao= new MpgHdrDaoImpl();
        }
        return mpgDao;
    }

    public static CalcFunctionsDao getCalcFunDao() {
        if (calcFunDao == null) {
             calcFunDao = new CalcFunctionsDaoImpl();
        }
        return calcFunDao;
    }
    
    public static EventTreeDao getEventTreeDao() {
        if (eventDao == null) {
             eventDao = new EventTreeDaoImpl();
        }
        return eventDao;
    }
    
    
    
    
    
    
}
