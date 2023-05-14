/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyDialogPopup extends JDialog {

	public String sName="";
        public String prob="";
        public JTextField eventName;
        private int probCount1=0;
	
	public static void main(String[] args) {
		try {
			MyDialogPopup dialog = new MyDialogPopup(0,"#","Test");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MyDialogPopup(int probCount,String evtName,String prob) {
                
		setBounds(100, 100, 296, 175);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
                
                this.probCount1 = ((int)Math.pow(2, probCount));
		this.prob = prob;
                
                setTitle("Please type "+probCount1+" Probabilities(Line by Line)");
                
// TextArea
		
                final JLabel event = new JLabel("Event Name");
                event.setBounds(70, 0, 160, 20);
                add(event);
                
                eventName = new JTextField();
                eventName.setBounds(70, 20, 160, 20);
                eventName.setText(evtName);
                add(eventName);
                
                final JLabel proba = new JLabel("Probabilities");
                proba.setBounds(70, 40, 160, 20);
                add(proba);
                
		// ScrollPane
                final JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(70, 60, 160, 50);
		add(scroll);
		
		// Label
		final JLabel lbl = new JLabel("Result");
		lbl.setBounds(100, 80, 144, 14);
		add(lbl);
                
                 System.out.println("prob "+prob);
                   
                textArea.setText(prob.replace("#", "\n"));
                 
		
		// Button OK
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
			    try {
                                
                                for (String line : textArea.getText().split("\\n")) 
				{
					sName = sName+"#"+Double.parseDouble(line);
                                        
				}
                                
                                sName= sName.substring(1);
                                System.out.println(sName);
                                lbl.setText( sName);
                                
                                if(sName.split("\\#").length ==probCount1 ){
				    dispose();
                                }else{
                                    sName="";
                                   JOptionPane.showMessageDialog(null,"Please enter "+probCount1+" values for level "+(probCount+1));
                                }    
                                
                            } catch (NumberFormatException e5) {
                                
                                JOptionPane.showMessageDialog(null,"Please enter decimal value only");  
                            }
	// Read line
				
			}
		});
		btnOK.setBounds(70, 120, 78, 23);
		getContentPane().add(btnOK);
		
		// Button Cancel
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sName = "Invalid";
				dispose();
			}
		});
		btnCancel.setBounds(158, 120, 74, 23);
		getContentPane().add(btnCancel);
		
	}
        
        
        public String Data(){
        
         return sName;
        }
        
        public String GetEventName(){
        
         return eventName.getText();
        }
}
