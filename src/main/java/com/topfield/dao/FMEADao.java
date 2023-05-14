/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Fmea;
import com.topfield.modal.MainProductGroup;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FMEADao {
    public  List<Fmea> getAllFMEA();
    public  Fmea getFMEAById(int fmeaId);
    public  List<Fmea> getAllFMEAByMPG(int mpgId);
    public  List<Fmea> getAllFMEAByMPGHdrFun(int mpgHdrId,int funId);
    public  List<Fmea> getAllFMEAByHdr(int hdrId);
    public  List<Fmea> getAllFMEAByMPG(int mpgId,int proId,String username);
    public  List<Object[]> getSummaryFMEAByMPG(int proId,String username);
    public void UpdateFMEA(Fmea fmea);
    public void saveFMEA(Fmea fmea);
    public void deleteFMEA(int fmeaId);
    public void deleteFMEAComponents(String user,int proId,int compId);
    
}
