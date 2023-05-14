/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Ccf;
import com.topfield.modal.Components;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface CCFDao {
    
    public Ccf findById(int id);
    public Ccf findByName(String description);
    public int Save (Ccf ccf);
    public int Update (Ccf ccf);
    public List<Ccf> getAllCcf();
    public List<Ccf> getAllCcfByType(String CcfType);
    public List<Ccf> getAllCcfByHdrId(int hdrId);
    public List<Ccf> getAllCcfByCalcHdrId(int calcHdrId);
    public List<Ccf> getAllCcfByTypeUserProj(String user,int proId,String CcfType);
    public List<Ccf> getAllCcfByTypeUserProj(String user,int proId,String CcfType,int compId);
    public List<Ccf> getFinalCcfByTypeUserProj(String user,int proId,String CcfType,String desc);
    public List<Ccf> getFinalCcfByTypeUserProj(String user,int proId,String desc);
    public void deleteCCFComponents(String user,int proId,String CcfType,int compId);
}
