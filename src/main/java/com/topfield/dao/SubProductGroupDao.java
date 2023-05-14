/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.SubProductGroup;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SubProductGroupDao {
    
    public  SubProductGroup getSPGById(int SPGId);
    public  SubProductGroup getSPGByName(String SPGName);
    public   List<SubProductGroup> getSPGByMpg(int mpgId);
    public  SubProductGroup getSPGByNameAndMpg(String SPGName,int mpgId);
    public  List<SubProductGroup> getAllSPG();
    public  List<SubProductGroup> getAllSPGByUser(String username);
    public int Save(SubProductGroup spg);
    public void Update(SubProductGroup spg);
    public void Delete(int spgId);
    public  String[] getAllSPGHeading();
}
