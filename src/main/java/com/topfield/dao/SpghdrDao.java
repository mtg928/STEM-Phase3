/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Spghdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SpghdrDao {
    
    public  Spghdr findById(int spghdrId);
    public  List<Spghdr> getAllSpghdr();
    public  List<Spghdr> getAllSpghdrByMPG(int mpgId,String cal,int proId,String username);
    public  List<Spghdr> getAllSpghdrByPro(int proId,String cal,String username);
    public  List<Spghdr> getAllSpghdrByProSpg(int proId,String cal,String username,int spgId);
    public void update(Spghdr spghdr);
    public int save(Spghdr spghdr);
    public void delete(int spghdrId);
    
}
