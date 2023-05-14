/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Murali
 */
public class EvacuationFrom extends JPanel{
    private JTextField[] fields;

  // Create a form with the specified labels, tooltips, and sizes.
  public EvacuationFrom() {
    super(new BorderLayout());
    
    String[] labels = { "First Name", "Middle Initial", "Last Name", "Age" };
    char[] mnemonics = { 'F', 'M', 'L', 'A' };
    int[] widths = { 15, 15, 15, 15 };
    String[] descs = { "First Name", "Middle Initial", "Last Name", "Age" };
    
    
    JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
    JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
    add(labelPanel, BorderLayout.WEST);
    add(fieldPanel, BorderLayout.CENTER);
    fields = new JTextField[labels.length];

    for (int i = 0; i < labels.length; i += 1) {
      fields[i] = new JTextField();
      if (i < descs.length)
        fields[i].setToolTipText(descs[i]);
      if (i < widths.length)
        fields[i].setColumns(widths[i]);

      JLabel lab = new JLabel(labels[i], JLabel.RIGHT);
      lab.setLabelFor(fields[i]);
      if (i < mnemonics.length)
        lab.setDisplayedMnemonic(mnemonics[i]);

      labelPanel.add(lab);
      JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.add(fields[i]);
      fieldPanel.add(p);
    }
  }

  public String getText(int i) {
    return (fields[i].getText());
  }

  public static void main(String[] args) {


    final EvacuationFrom form = new EvacuationFrom();

    JButton submit = new JButton("Submit Form");

    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(form.getText(0) + " " + form.getText(1) + ". " + form.getText(2)
            + ", age " + form.getText(3));
      }
    });

    JFrame f = new JFrame("Text Form Example");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(form, BorderLayout.NORTH);
    JPanel p = new JPanel();
    p.add(submit);
    f.getContentPane().add(p, BorderLayout.SOUTH);
    f.pack();
    f.setVisible(true);
  }
}
