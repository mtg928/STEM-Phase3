/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Hazid;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface HazidDao {
    
    public Hazid findById(int id);
    public List<Hazid> getAllHazid();
}
