/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Failuremodes;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FailureModesDao {
    
    public Failuremodes findById(int id);
    public Failuremodes findByName(String description);
    public int Save (Failuremodes failuremodes);
    public int Update (Failuremodes failuremodes);
    public void Remove (int Id);
    public List<Failuremodes> getAllFailuremodes();
    
}
