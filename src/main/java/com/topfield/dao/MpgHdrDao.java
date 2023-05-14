/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Fmea;
import com.topfield.modal.Mpghdr;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface MpgHdrDao {
    
    public  List<Mpghdr> getAllFMEAS();
    public  Mpghdr getFMEASById(int fmeasId);
    public  List<Mpghdr> getAllFMEAHDRByMPG(int mpgId,String cal,int proId,String username);
    public  Mpghdr getAllFMEAHDRByMPGOne(int mpgId,String cal,int proId,String username);
    public  List<Mpghdr> getSummaryFMEAHERByMPG(int proId,String cal,String username);
    public void UpdateFMEAS(Mpghdr fmeas);
    public int saveFMEAS(Mpghdr fmeas);
    public void deleteFMEAS(int fmeasId);
    
}
