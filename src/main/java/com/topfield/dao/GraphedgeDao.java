/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Graphedge;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface GraphedgeDao {  
    
    public Graphedge findById(int graphedgeid);
    public Graphedge findByName(String name);
    public int Save(Graphedge graphedge);
    public void Update(Graphedge graphedge);
    public void Delete(int graphedgeId);
    public List<Graphedge> getAllGraphedgeBySpfHdr(int spfHdrId);
    public void deleteAllGraphedgeBySpfHdr(int spfHdrId);
    
}
