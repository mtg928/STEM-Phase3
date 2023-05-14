/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.SubProductFunctions;
import com.topfield.modal.SubProductGroup;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface SubProductFunctionsDao {
    
    public  SubProductFunctions getSPGFunctionById(int SPGId);
    public  List<SubProductFunctions> getAllSPGFunction();
    
}
