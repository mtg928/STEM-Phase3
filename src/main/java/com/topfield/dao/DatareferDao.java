/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Datarefer;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface DatareferDao {
    public Datarefer findById(int id);
    public Datarefer findByName(String description);
    public int Save (Datarefer cf);
    public int Update (Datarefer cf);
    public List<Datarefer> getAllDatarefer();
    public List<Datarefer> getAllDatareferByCalType(String calType);
    public void remove (int cfId);
}
