/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Failuredata;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FailuredataDao {
    
    public Failuredata findById(int id);
    public Failuredata findByName(String description);
    public int Save (Failuredata failuredata);
    public int Update (Failuredata failuredata);
    public void Remove (int Id);
    public List<Failuredata> getAllFailuredata();
    
}
