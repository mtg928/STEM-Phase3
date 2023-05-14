/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.FaultTreeAnalysis;
import com.topfield.modal.Fmea;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface FaultTreeAnalysisDao {
    
    public  List<FaultTreeAnalysis> getAllFaultTreeAnalysis();
    public  FaultTreeAnalysis getFaultTreeAnalysisById(int faultTreeId);
    public void UpdateFaultTreeAnalysis(FaultTreeAnalysis faultTree);
    public void saveFaultTreeAnalysis(FaultTreeAnalysis faultTree);
    public void deleteFaultTreeAnalysis(int faultTreeId);
    
    
}
