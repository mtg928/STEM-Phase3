/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Ccfhdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface CcfHdrDao {
    
    public Ccfhdr findById(int id);
    public Ccfhdr findByName(String description);
    public int Save (Ccfhdr ccfhdr);
    public int Update (Ccfhdr ccfhdr);
    public void Remove (int ccfhdrId);
    public List<Ccfhdr> getAllCcfhdr();
    public List<Ccfhdr> getAllCcfhdrByUserAndPro(String user,int proId);
    
}
