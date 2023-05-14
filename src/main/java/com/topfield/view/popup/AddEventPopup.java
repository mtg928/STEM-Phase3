/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.DatareferDao;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.HazidDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.HazidDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.Datarefer;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Faulttree;
import com.topfield.modal.Hazid;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Spfhdr;
import com.topfield.modal.SubProductGroup;
import com.topfield.user.UserInfo;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Murali
 */
public class AddEventPopup extends javax.swing.JPanel {

    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private DatareferDao refDao = new DatareferDaoImpl();
    private HazidDao hazidDao = new HazidDaoImpl();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private List<Spfhdr> dataSingleCom = dataSingleCom = spfhdrDao.getAllSpfhdrByPro(UserInfo.getInstance().getProjectNo(), "FTA", UserInfo.getInstance().getuser().getUsername());

    private List<Datarefer> datarefer;
    
    /**
     * Creates new form AddEventPopup
     */
    public AddEventPopup() {
        initComponents();

       
         /*
        jComboBox2.addItem(new Item(1, "Internal FTA"));  Internal FTA,Reference FTA,Historical Value,Internal ETA
        jComboBox2.addItem(new Item(2, "Reference FTA"));
        jComboBox2.addItem(new Item(3, "Historical Value"));
        jComboBox2.addItem(new Item(4, "Internal ETA"));
        */
        

        for (Hazid hazid : hazidDao.getAllHazid()) {
            jComboBox4.addItem(new Item(hazid.getHazidId(), hazid.getDescription()));
        }
        
        setReferenceIET();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jLabel3.setText("Event Name");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Event Type :");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Event Name :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Safety", "Functional safety", "Reliability" }));

        jLabel7.setText("Hazard :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Internal FTA", "External FTA" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jComboBox2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox2PropertyChange(evt);
            }
        });

        jLabel5.setText("Reference :");

        jLabel6.setText("Initiating Event :");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jComboBox3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox3PropertyChange(evt);
            }
        });

        jLabel4.setText("Initial Value :");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jComboBox5.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "/ hr", "/ year", "/ million hour", "/ billion hours (FIT)" }));
        jComboBox5.setPreferredSize(new java.awt.Dimension(56, 20));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox5KeyReleased(evt);
            }
        });

        jLabel8.setText("Comments :");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(116, 116, 116))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1)
                                .addComponent(jComboBox1, 0, 399, Short.MAX_VALUE)
                                .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
      
            setReferenceIET();
       /* int selectedMpg = 1;
        if (jComboBox2.getSelectedIndex() >= 0) {
            selectedMpg = ((Item) jComboBox2.getSelectedItem()).getId();
        }

        MainProductGroup main2 = mpgDao.FindById(selectedMpg);

        jComboBox3.addItem(new Item(-1, "<Please Select>"));

        for (SubProductGroup sainProductGroup : main2.getSubProductGroupCollection()) {
            jComboBox3.addItem(new Item(sainProductGroup.getSpgId(), sainProductGroup.getSpgDescription()));
        }*/
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox2PropertyChange
        /* jComboBox3.removeAllItems();

        if (jComboBox2.getSelectedIndex() > 0) {
            
            if (datarefer == null) {
                datarefer = refDao.getAllDatareferByCalType("ETA");
            }
            
            for (Datarefer refData : datarefer) {
                jComboBox3.addItem(new Item(refData.getRefid(), refData.getDescription()));
            }
            
        }else{
            
            
        
            for (Spfhdr spfhdr : dataSingleCom) {
                jComboBox3.addItem(new Item(spfhdr.getSpfhdrid(), spfhdr.getDescriptions()));
            }
        
        }*/
    }//GEN-LAST:event_jComboBox2PropertyChange

    private void jComboBox3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox3PropertyChange
        
       /* Spfhdr spfhdr = spfhdrDao.findById(((Item)jComboBox3.getSelectedItem()).getId());

        System.out.println("spfhdr - "+spfhdr.getDescriptions());*/
    }//GEN-LAST:event_jComboBox3PropertyChange

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
             
        
        if (getReferenceType().contentEquals("Internal FTA")) {
            
            Spfhdr spfhdr = spfhdrDao.findById(((Item)evt.getItem()).getId());
            
            if (spfhdr.getFaultdataCollection().size()>0) {
                
                 Faultdata  faultdata= spfhdr.getFaultdataCollection().iterator().next();

                System.out.println("spfhdr - "+spfhdr.getDescriptions()+ " "+ faultdata.getName());

                setEventName(faultdata.getName());
                setInitialFrequency(faultdata.getFailurerate()+"");
            }
           
            
        }else if(getReferenceType().contentEquals("External FTA") || getReferenceType().contentEquals("Historical Value")){
            
            Datarefer dref = refDao.findById(((Item)evt.getItem()).getId());
            
            if (dref != null) {
                            //setEventName(dref.getName());
              setInitialFrequency(dref.getValue());
            }

            
            
        }

    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
       // Item selItem = (Item)evt.getItem();

    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox5KeyReleased

    }//GEN-LAST:event_jComboBox5KeyReleased

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    public static void main(String[] args) {

        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         AddEventPopup addpopup = new AddEventPopup();
         
         //addpopup.setReferenceType(new Item(2, "Vehicle fitting out"));
        frame.setContentPane(addpopup);
        
        

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<Item> jComboBox3;
    private javax.swing.JComboBox<Item> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    public String getEventName() {
        return jTextField1.getText();
    }

    public void setEventName(String eventName) {
        jTextField1.setText(eventName);
    }
    
  
    public String getEventType() {
        return jComboBox1.getSelectedItem()+"";
    }


    public void setEventType(String eventType) {
        this.jComboBox1.setSelectedItem(eventType);
    }
    
    
    public String getInitialFrequency() {
        return jTextField4.getText();
    }


    public void setInitialFrequency(String initialFrequency) {
        this.jTextField4.setText(initialFrequency);
    }
    

    public String getReferenceType() {
        return jComboBox2.getSelectedItem()+"";
    }


    public void setReferenceType(String ref) {
        this.jComboBox2.setSelectedItem(ref);
    }


    public Item getSelectIET() {
        return (Item)jComboBox3.getSelectedItem();
    }


    public void setSelectIET(Item spg) {
        this.jComboBox3.setSelectedItem(spg);
    }

    public String getUnits() {
        return jComboBox5.getSelectedItem()+"";
    }


    public void setUnits(String unit) {
        this.jComboBox5.setSelectedItem(unit);
    }
    

    public Item getHazid() {
        return (Item)jComboBox4.getSelectedItem();
    }


    public void setHazid(Item hazid) {
        this.jComboBox4.setSelectedItem(hazid);
    }


    public String getComments() {
        return jTextArea1.getText();
    }


    public void setComments(String comments) {
        this.jTextArea1.setText(comments);
    }

    
    public void setReferenceIET(){
    
             jComboBox3.removeAllItems();

        if (jComboBox2.getSelectedIndex() > 0) {
            
            if (datarefer == null) {
                datarefer = refDao.getAllDatareferByCalType("ETA");
            }
            
            for (Datarefer refData : datarefer) {
                jComboBox3.addItem(new Item(refData.getRefid(), refData.getDescription()));
            }
            
        }else{
            
            
        
            for (Spfhdr spfhdr : dataSingleCom) {
                jComboBox3.addItem(new Item(spfhdr.getSpfhdrid(), spfhdr.getDescriptions()));
            }
        
        }
    
    }

}
