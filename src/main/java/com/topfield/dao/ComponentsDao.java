/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Components;
import com.topfield.modal.SubProductGroup;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface ComponentsDao {
    
    public Components findById(int id);
    public Components findByName(String name);
    public int Save(Components comp);
    public void Update(Components comp);
    public void Delete(int compId);
    public List<Components> getAllComponents();
    public List<Components> getAllComponentsByUser(String username);
    public List<Components> getAllComponentsByType(String componentsType);
    
    
}
