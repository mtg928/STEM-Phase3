/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Faultdata;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FaultdataDao {
    
    public Faultdata findById(int id);
    public Faultdata findByName(String name);
    public Faultdata findByName(String name,int spfHdrId);
    public int Save (Faultdata fd);
    public void Update (Faultdata fd);
    public void remove (int faultId);
    public List<Faultdata> getAllCcf();
    public List<Faultdata> getAllFaultdataByFaulttree(int faultId);
    public List<Faultdata> getAllFaultdataBySpfHdr(int spfHdrId);
    public List<Faultdata> getAllFaultdataBySpfHdr(int spfHdrId,int parentId);
    public void removeByFaultTreeId (int faultTreeId);
    public void removeBySpfHdr (int spfHdrId);
    
    //public void clearCache(Object object);
   // public void refresh(List<Faultdata> ftDatas);
    
    
}
