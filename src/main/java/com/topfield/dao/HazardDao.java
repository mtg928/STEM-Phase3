/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Hazards;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface HazardDao {
    
    public Hazards findById(int id);
    public List<Hazards> getAllHazards();
    public void Delete(int hazardId);
    public void Update(Hazards hazards);
    public int Save(Hazards hazards);
    
}
