/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Fmeca;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FMECADao {
    
    public  List<Fmeca> getAllFMECA();
    public  Fmeca getFMECAById(int fmecaId);
    public  List<Fmeca> getAllFMECAByMPG(int mpgId);
    public  List<Fmeca> getAllFMECAByHdr(int hdrId);
    public  List<Fmeca> getAllFMECAByMPGHdrFun(int mpgHdrId,int funId);
    public  List<Fmeca> getAllFMECAByMPG(int mpgId,int proId,String username);
    public  List<Object[]> getSummaryFMECAByMPG(int proId,String username);
    public void UpdateFMECA(Fmeca fmeca);
    public void saveFMECA(Fmeca fmeca);
    public void deleteFMECA(int fmecaId);
    public void deleteFMECAComponents(String user,int proId,int compId);
    
    
}
