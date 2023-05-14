/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.MainProductGroup;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface MainProductGroupDao {
    public  MainProductGroup FindById(int mpgId);
    public  MainProductGroup FindByName(String mpgName);
    public int Save(MainProductGroup mpg);
    public void Update(MainProductGroup mpg);
    public void Delete(int mpgId);
    public  List<MainProductGroup> getAllMPG();
    public  List<MainProductGroup> getAllMPGBYUser(String username);
    public  List<MainProductGroup> getAllMPGByAdminUser(String username);
    
}
