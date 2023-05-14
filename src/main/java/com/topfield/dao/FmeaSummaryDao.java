/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Standards;

/**
 *
 * @author Murali
 */
public interface FmeaSummaryDao {
    public Standards findById(int id);
    public Standards findByCalculator(String description);
}
