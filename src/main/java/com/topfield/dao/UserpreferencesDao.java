/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Userpreferences;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface UserpreferencesDao {
    
    public  Userpreferences findById(int preId);
    public  List<Userpreferences> getAllUserpreferences();
    public  List<Userpreferences> getAllUserpreferencesByUser(String username);
    public void update(Userpreferences spchdr);
    public int save(Userpreferences spchdr);
    public void delete(int preId);
    
}
