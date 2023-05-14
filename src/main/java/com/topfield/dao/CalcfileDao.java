/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Calcfile;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface CalcfileDao {
    
    public Calcfile findById(int id);
    public Calcfile findByName(String description);
    public int Save (Calcfile cf);
    public int Update (Calcfile cf);
    public List<Calcfile> getAllCalcfile();
    public List<Calcfile> getAllCalcfileByCalcFun(int calcFunId);
    public void remove (int cf);
    //public void refresh (List<Calcfile> list);
    
}
