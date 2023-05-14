/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Calchdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface CalchdrDao {
    
    public  List<Calchdr> getAllCalchdr();
    public  Calchdr findById(int calchdrId);
    //public  List<Calchdr> getAllCalchdrRByMPG(int mpgId,String cal,int proId,String username);
    public  List<Calchdr> getAllCalchdrByMPGOne(int calchdrId,String cal,int proId,String username);
    public  List<Calchdr> getSummaryCalchdrByPro(int proId,String cal,String username);
    public void Update(Calchdr calchdr);
    public int save(Calchdr calchdr);
    public void delete(int calchdr);
    
}
