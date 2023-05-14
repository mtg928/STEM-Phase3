/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Assumptions;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface AssumptionsDao {
    
    public Assumptions findById(int id);
    public Assumptions findByName(String description);
    public int Save (Assumptions ass);
    public int Update (Assumptions ass);
    public List<Assumptions> getAllAssumptions();
    public List<Assumptions> getAllAssumptionsByProId(int proId);
    public void remove (int ass);
}
