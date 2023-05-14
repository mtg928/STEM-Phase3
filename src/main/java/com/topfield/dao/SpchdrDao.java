/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Spchdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SpchdrDao {
    
    public  Spchdr findById(int spchdrId);
    public  List<Spchdr> getAllSpchdr();
    public  List<Spchdr> getAllSpchdrByMPG(int spgId,String cal,int proId,String username);
    public  List<Spchdr> getAllSpchdrByPro(int proId,String cal,String username);
    public void update(Spchdr spchdr);
    public int save(Spchdr spchdr);
    public void delete(int spchdrId);
    
}
