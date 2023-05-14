/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Standards;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface StandardsDao {
    public Standards findById(int id);
    public Standards findByName(String stdName);
    public List<Standards> findByCalculator(String calculator);
}
