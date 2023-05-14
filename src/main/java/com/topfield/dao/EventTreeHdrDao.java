/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Eventtreehdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface EventTreeHdrDao {
    
    public Eventtreehdr findById(int id);
    public Eventtreehdr findByName(String description);
    public int Save (Eventtreehdr eventTreeHdr);
    public int Update (Eventtreehdr eventTreeHdr);
    public void Remove (int eventTreeHdrId);
    public List<Eventtreehdr> getAllEventTreeHdr();
    public List<Eventtreehdr> getAllEventTreeHdrByUserAndPro(String user,int proId);
    
}
