/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Faulttree;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FaulttreeDao {
    
    public Faulttree findById(int id);
    public Faulttree findByName(String description);
    public int Save (Faulttree ft);
    public int Update (Faulttree ft);
    public List<Faulttree> getAllCcf();
    public List<Faulttree> getAllFaulttreeByTypeUserProj(String user,int proId);
    public void remove (Faulttree ft);
    
    
}
