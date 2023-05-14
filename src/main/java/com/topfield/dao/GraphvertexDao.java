/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Graphvertex;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface GraphvertexDao {
    
    public Graphvertex findById(int vertexid);
    public Graphvertex findByName(String name);
    public int Save(Graphvertex vertex);
    public void Update(Graphvertex vertex);
    public void Delete(int vertexId);
    public List<Graphvertex> getAllVertexBySpfHdr(int spfHdrId);
    public void deleteAllVertexBySpfHdr(int spfHdrId);
    public boolean isCMMUsed(int cmmId);
    public List<String> isCMMUsedRef(int cmmId);
    
}
