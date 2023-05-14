/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Spfhdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SpfhdrDao {
    
    public  Spfhdr findById(int spfhdrId);
    public  List<Spfhdr> getAllSpfhdr();
    public  List<Spfhdr> getAllSpfhdrByMPG(int mpgId,String cal,int proId,String username);
    public  List<Spfhdr> getAllSpfhdrByPro(int proId,String cal,String username);
    public  List<Spfhdr> getAllSpfhdrByProFun(int proId,String cal,String username,int spfId);
    
    public void update(Spfhdr spfhdr);
    public int save(Spfhdr spfhdr);
    public void delete(int spfhdrId);
    
}
