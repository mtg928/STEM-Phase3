/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Fmea;
import com.topfield.modal.TclProjects;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface TclProjectsDao {
    public  TclProjects findById(int projectId);
    public  int saveProjects(TclProjects pro);
    public  void editProjects(TclProjects pro);
    public  TclProjects findByUserLatest(String user);
    public  List<TclProjects> getProByUser(String user);
}
