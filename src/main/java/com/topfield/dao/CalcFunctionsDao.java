/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.CalcFunctions;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface CalcFunctionsDao {
    
    public CalcFunctions findById(int id);
    public CalcFunctions findByName(String description);
    public int Save (CalcFunctions cf);
    public int Update (CalcFunctions cf);
    public List<CalcFunctions> getAllCalcFunctions();
    public List<CalcFunctions> getAllCalcFunctionsByMpgheader(int hdrId);
    public void remove (int cfId);
    public void flash();
    
}
