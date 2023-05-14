/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.MainProductComponents;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface MainProductComponentsDao {
    public  MainProductComponents FindById(int mpcId);
    public  List<MainProductComponents> getAllMPC();
    public  List<MainProductComponents> getAllMPCByMPG(int mpgId);
}
