/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Eventtree;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface EventTreeDao {
    
    public Eventtree findById(int id);
    public Eventtree findByName(String name);
    public int Save (Eventtree eventtree);
    public int Update (Eventtree eventtree);
    public void remove (int eventId);
    public List<Eventtree> getAllEventtree();
    public List<Eventtree> getAllEventsByEventHdr(int hdrId);
    public List<Eventtree> getAllEvents(String user,int proId);
    public List<Eventtree> getAllEventtreeByUserProj(String user,int proId);
    public List<Eventtree> getAllEventtreeByTypeUserProj(String type,String EventName,String user,int proId);
    public void RemoveAllEventtreeByTypeUserProj(String type,String EventName,String user,int proId);
     public void removeAllEventsByEventHdr(int hdrId);
    public void removeAllEventsByEventHdr(int hdrId,int level);
    
}
