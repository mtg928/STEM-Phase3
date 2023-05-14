/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Sil;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SILDao {
    
    public Sil findById(int id);
    public Sil findByName(String name);
    public List<Sil> getAllSil();
    public int Save(Sil sil);
    public void Upadte(Sil sil);
    public void Delete(Sil sil);
    public List<Sil> getSILByTypeUserProj(String user,int proId);
    
}
