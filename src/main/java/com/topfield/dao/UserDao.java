/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.dao;

import com.topfield.modal.Users;
import java.util.Date;

/**
 *
 * @author Murali
 */
public interface UserDao {
    public  Users findById(String username);
    public  boolean checkedValidLicense(Date start, Date end);
}
