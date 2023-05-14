/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.SubProductComponents;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SubProductComponentsDao {
    public  SubProductComponents FindById(int spcId);
    public  SubProductComponents FindByName(String spcName);
    public  SubProductComponents FindByNameBySpg(String spcName,int spgId);
    public  List<SubProductComponents> getAllSPC(); 
    public  List<SubProductComponents> getAllSPCBySPG(int spgId);
    public  List<SubProductComponents> getAllSPCByUser(String username);        
    public int Save(SubProductComponents spc);
    public void Update(SubProductComponents spc);
    public void Delete(int spcId);
}
