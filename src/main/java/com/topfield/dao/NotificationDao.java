/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Notification;
import java.util.List;

/**
 *
 * @author Murali
 */
public interface NotificationDao {
    
    public int findAllCount();
    public Notification findById(int id);
    public List<Notification> findAllCountPage(int firstRes, int maxRes);
    public List<Notification> findAll();
    
}
