/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.FMEADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.Fmea;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Standards;
import com.topfield.user.UserInfo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JDialog;

/**
 *
 * @author Murali
 */
public class FmeaHeaderPopup extends JDialog {

    private MainProductGroupDao mainDao = new MainProductGroupDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();

    private JTextField description = new JTextField(20);
    private JComboBox<Item> mpgCombo = new JComboBox<Item>();
    private JComboBox<Item> stdCombo = new JComboBox<Item>();
    private JComboBox<String> mpgType = new JComboBox<String>();
    private JTextField comments = new JTextField(20);

    private JButton buttonOk = new JButton("OK");
    private JButton buttonCancel = new JButton("Cancel");
    private String calculator;
    private String jLabel;

    //private boolean submit= false;
    public FmeaHeaderPopup(String modify, String calculator, String mpgTypes[]) {

        setTitle("Please enter details");
        setBounds(100, 100, 500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        this.calculator = calculator;

        for (String string : mpgTypes) {
            mpgType.addItem(string);
        }

        mpgType.setEnabled(false);
        
        if (modify.contentEquals("Edit")) {
           // mpgCombo.setEnabled(false);
        } else {
            mpgCombo.setEnabled(true);
        }

        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //submit= true;
                dispose();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //submit= false;
                dispose();
            }
        });

        getContentPane().add(getForm());
        /*for (MainProductGroup mpgItem : mainDao.getAllMPG()) {
            mpgCombo.addItem(new Item(mpgItem.getMpgId(), mpgItem.getMpgDescription()));
        }*/

        if (calculator.contentEquals("CMM")) {
            for (MainProductGroup mpg : mainDao.getAllMPGByAdminUser(UserInfo.getInstance().getuser().getUsername())) {
                mpgCombo.addItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()+" ("+mpg.getMpgRef()+")"));
            }
            
            jLabel = "Main Product Group";
        } else {

            for (Mpghdr mpgType1 : mpgHdrDao.getSummaryFMEAHERByMPG(UserInfo.getInstance().getProjectNo(), "CMM", UserInfo.getInstance().getuser().getUsername())) {
                //mpgCombo.addItem(new Item(mpgType1.getFmeaComponent().getMpgId(), mpgType1.getFmeaComponent().getMpgDescription()+" ("+mpgType1.getFmeaComponent().getMpgRef()+")"));
                mpgCombo.addItem(new Item(mpgType1.getFmeaComponent().getMpgId(), mpgType1.getDescriptions()));
            }
            
            jLabel = "Select Calcfile";
        }

        for (Standards standards : stdDao.findByCalculator(calculator)) {
            stdCombo.addItem(new Item(standards.getId(), standards.getCode()));
        }
    }

    public JPanel getForm() {
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(new JLabel("Group/Function Name"), constraints);

        constraints.gridx = 1;
        newPanel.add(description, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(new JLabel(jLabel), constraints);

        constraints.gridx = 1;
        newPanel.add(mpgCombo, constraints);

       /* constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(new JLabel("Standards"), constraints);

        constraints.gridx = 1;
        newPanel.add(stdCombo, constraints);*/

        constraints.gridx = 0;
        constraints.gridy = 2;
        //newPanel.add(new JLabel(calculator + " Type"), constraints);
        newPanel.add(new JLabel("Module"), constraints);

        constraints.gridx = 1;
        newPanel.add(mpgType, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        newPanel.add(new JLabel("Comments"), constraints);

        constraints.gridx = 1;
        newPanel.add(comments, constraints);

        /*constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,1));
        buttonPanel.add(buttonOk);
        buttonPanel.add(buttonCancel);
        newPanel.add(buttonPanel, constraints);*/
        // set border for the panel
        /*newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));*/
        return newPanel;
    }

    /*public boolean getSubmission(){
        
  return submit;
 }*/
    public String getDescription() {

        return description.getText();
    }
    
    public int getMpgId() {
      int res=-2;
      
       if (mpgCombo.getSelectedItem() != null) {
           res = ((Item) mpgCombo.getSelectedItem()).getId();
       }
      
      return res;
    }

    public MainProductGroup getMpg() {

        if (mpgCombo.getSelectedItem() == null) {
            return null;
        } else {
            return mainDao.FindById(((Item) mpgCombo.getSelectedItem()).getId());
        }
        
    }
    
    public void setMpgObj(MainProductGroup mpg) {
        mpgCombo.setSelectedItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()));
    }


    public void setMpg(int mpgId) {
        MainProductGroup mpg = mainDao.FindById(mpgId);
        mpgCombo.setSelectedItem(new Item(mpg.getMpgId(), mpg.getMpgDescription()));
    }

    public Standards getStandards() {

        return stdDao.findById(6);
    }

    /*public void setStandardsObj(Standards stds) {
        stdCombo.setSelectedItem(new Item(stds.getId(), stds.getCode()));
    }*/
    
    public String getMPGType() {

        return mpgType.getSelectedItem() + "";
    }

    public String getComments() {

        return comments.getText();
    }

    public void setDescription(String des) {

        description.setText(des);
    }



    public void setStandards(String std) {
        Standards stds = stdDao.findByName(std);
        stdCombo.setSelectedItem(new Item(stds.getId(), stds.getCode()));
    }

    public void setComments(String comment) {

        comments.setText(comment);
    }

    public void setMPGType(String mpgTypes) {

        mpgType.setSelectedItem(mpgTypes);
    }

    public static void main(String[] args) {
        try {
            FmeaHeaderPopup dialog = new FmeaHeaderPopup("add", "FMEA", new String[]{"Qualitative"});
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
