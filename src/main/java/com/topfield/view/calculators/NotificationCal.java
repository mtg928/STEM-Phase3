/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import com.topfield.info.NotificationPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class NotificationCal extends JTabbedPane {

    public NotificationCal() {
        
         add("Notification", new NotificationPanel());
    }

}
