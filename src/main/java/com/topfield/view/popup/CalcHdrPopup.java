/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.CalchdrDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.daoImpl.CalchdrDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.Calchdr;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Standards;
import com.topfield.user.UserInfo;
import javax.swing.JFrame;

/**
 *
 * @author Murali
 */
public class CalcHdrPopup extends javax.swing.JPanel {

    private StandardsDao stdDao = new StandardsDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    private CalchdrDao calchdrDao = new CalchdrDaoImpl();
    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    
    public CalcHdrPopup(String modify, String calculator, String mpgTypes[]) {
        initComponents();
        
        for (MainProductGroup mpg : mainDao.getAllMPGByAdminUser(UserInfo.getInstance().getuser().getUsername())) {
                jComboBox4.addItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()+" ("+mpg.getMpgRef()+")"));
        }
        
        jComboBox1.addItem(new Item(-1, "<- New File ->"));
        for (Mpghdr mpgType1 : mpgHdrDao.getSummaryFMEAHERByMPG(UserInfo.getInstance().getProjectNo(), "CMM", UserInfo.getInstance().getuser().getUsername())) {
                //mpgCombo.addItem(new Item(mpgType1.getFmeaComponent().getMpgId(), mpgType1.getFmeaComponent().getMpgDescription()+" ("+mpgType1.getFmeaComponent().getMpgRef()+")"));
                jComboBox1.addItem(new Item(mpgType1.getMpghdrid(), mpgType1.getDescriptions()));
        }
        
        for (Standards standards : stdDao.findByCalculator(calculator)) {
            jComboBox2.addItem(new Item(standards.getId(), standards.getCode()));
        }
        
        for (String string : mpgTypes) {
            jComboBox3.addItem(string);
        }

        if (modify.contentEquals("Edit")) {
            jComboBox1.setEnabled(false);
        } else {
            jComboBox1.setEnabled(true);
        }
        
        jLabel1.setText(calculator+" Name");
        //setCalMpgSel();
    }
    
    
    public String getDescription() {

        return jTextField1.getText();
    }

    public void setDescription(String des) {

        jTextField1.setText(des);
    }
    
    public Mpghdr getCalchdr() {

        if (jComboBox1.getSelectedItem() == null) {
            return null;
        } else {
            return mpgHdrDao.getFMEASById(((Item) jComboBox1.getSelectedItem()).getId());
        }
        
    }
    
    public int getCalchdrId() {

        if (jComboBox1.getSelectedItem() == null) {
            return -2;
        } else {
            return ((Item) jComboBox1.getSelectedItem()).getId();
        }
        
    }
    
    public void setMpgHdrObj(Mpghdr mpghdr) {
        jComboBox1.setSelectedItem(new Item(mpghdr.getMpghdrid(), mpghdr.getDescriptions()));
    }
    
    public void setMpgHdr(int calchdrId) {
        Mpghdr mpghdr = mpgHdrDao.getFMEASById(calchdrId);
        jComboBox1.setSelectedItem(new Item(mpghdr.getMpghdrid(), mpghdr.getDescriptions()));
    }
    
    public int getMpgId() {
      int res=-2;
      
       if (jComboBox4.getSelectedItem() != null) {
           res = ((Item) jComboBox4.getSelectedItem()).getId();
       }
      
      return res;
    }

    public MainProductGroup getMpg() {

        if (jComboBox4.getSelectedItem() == null) {
            return null;
        } else {
            return mainDao.FindById(((Item) jComboBox4.getSelectedItem()).getId());
        }
        
    }
    
    
    public void setMpgObj(MainProductGroup mpg) {
        jComboBox4.setSelectedItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()));
    }

    /*public void setStandardsObj(Standards stds) {
        stdCombo.setSelectedItem(new Item(stds.getId(), stds.getCode()));
    }*/

    public void setMpg(int mpgId) {
        MainProductGroup mpg = mainDao.FindById(mpgId);
        jComboBox4.setSelectedItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()));
    }
    
    
    public Standards getStandards() {

        return stdDao.findById(((Item) jComboBox2.getSelectedItem()).getId());
    }
    
    public void setStandardsObj(Standards stds) {
        jComboBox2.setSelectedItem(new Item(stds.getId(), stds.getCode()));
    }
   
    public void setStandards(String std) {
        Standards stds = stdDao.findByName(std);
        jComboBox2.setSelectedItem(new Item(stds.getId(), stds.getCode()));
    }

    public String getMPGType() {

        return jComboBox3.getSelectedItem() + "";
    }
    
    public void setMPGType(String mpgTypes) {

        jComboBox3.setSelectedItem(mpgTypes);
    }

    public String getComments() {

        return jTextField2.getText();
    }
    public void setComments(String comment) {

        jTextField2.setText(comment);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();

        jLabel1.setText("Function Name :");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Select CMM :");

        jLabel3.setText("Standards :");

        jLabel4.setText("MPG Type:");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel5.setText("Comments : ");

        jLabel6.setText("Select MPG :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
     
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
         setCalMpgSel();
    }//GEN-LAST:event_jComboBox1ActionPerformed


    public void setCalMpgSel(){
    
      if (jComboBox1.getSelectedIndex()==0) {
             //jComboBox1.setEnabled(true);
             jComboBox4.setEnabled(true);
        } else {
            // jComboBox1.setEnabled(false);
             jComboBox4.setEnabled(false);
        }
    
    }
    
     public static void main(String[] args) {

        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new CalcHdrPopup("add", "FMEA", new String[]{"RAM","Safety"}));
        
        

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Item> jComboBox1;
    private javax.swing.JComboBox<Item> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<Item> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
