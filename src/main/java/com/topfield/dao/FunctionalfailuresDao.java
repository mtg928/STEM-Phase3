/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Functionalfailures;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FunctionalfailuresDao {
    
    public Functionalfailures findById(int id);
    public  List<Functionalfailures> findByName(String description);
    public  List<Functionalfailures> findByNameBySpcId(String description,int spcId);
    public int Save (Functionalfailures fun);
    public int Update (Functionalfailures fun);
    public List<Functionalfailures> getAllFunctionalfailures();
    public List<Functionalfailures> getAllFunctionalfailuresBySubComp(int subCompId);
    public List<Functionalfailures> getAllFuncfailBySubCompUser(int subCompId,String username);
    public List<Functionalfailures> getAllFunctionalfailuresByFunction(int funId);
    public long getAllFunctionCountByCompId(int compId);
    public void remove (int funId);
    
}
