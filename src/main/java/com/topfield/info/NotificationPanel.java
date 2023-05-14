/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.info;

import com.topfield.dao.NotificationDao;
import com.topfield.daoImpl.NotificationDaoImpl;
import com.topfield.modal.Notification;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Murali
 */
public class NotificationPanel extends JPanel {
    
    private NotificationDao noteDao = new NotificationDaoImpl();

    SingleNote[] notes;

    public NotificationPanel() {
        
         List<Notification> noteList = noteDao.findAll();
        

        notes = new SingleNote[noteList.size()];

        add(Box.createRigidArea(new Dimension(0, noteList.size())));
        
        
        for (int i = 0; i < noteList.size(); i++) {
            notes[i] = new SingleNote(noteList.get(i));
            add(notes[i]);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //setLayout(new VerticalFlowLayout());

    }

    public static void main(String[] args) {

        
        
        JFrame frame = new JFrame("Swing Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(new NotificationPanel()));
        //frame.setLayout(new BorderLayout());
        frame.setSize(560, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
