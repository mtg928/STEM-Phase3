/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.dao.FaultdataDao;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.Faultdata;
import com.topfield.utilities.DualListBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.*;

 
public class FaultTreeChildSettings extends JDialog {
  private JPanel dropCombo;
  private DualListBox dual;
  private JPanel buttonPanel;
  private JTable faultTable ;
  private FaultdataDao faultdataDao = new FaultdataDaoImpl();
     
    public FaultTreeChildSettings(JTable faultTable ) {
    
    setTitle("Please select the immediate child nodes and gate");    
    setBounds(100, 100, 400, 400);
    setResizable(false);
    setLocationRelativeTo(null);
    getContentPane().setLayout(new BorderLayout());

    dual = new DualListBox();
    this.faultTable = faultTable;
    
    dropCombo = new JPanel();
    dropCombo.setLayout(new FlowLayout(FlowLayout.LEFT));
    JComboBox combo = new JComboBox(new String[] {"Please select", "AND", "OR"});
    dropCombo.add(new JLabel("Select the childs gate :-"));
    dropCombo.add(combo);
    
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    JButton ok = new JButton("OK");
    JButton cancel = new JButton("Cancel");
    
    buttonPanel.add(ok);
    ok.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	
            String child="";
            int slectedId = faultTable.getSelectedRow();
            if(combo.getSelectedIndex() ==0 ){
		 JOptionPane.showMessageDialog(null,"Please select the gate");	
            }else{
                
                       Iterator iterator = getDualListBox().destinationIterator();
                        
                        while(iterator.hasNext()) {
                                 //System.out.println(iterator.next());
                                 child = child+((Item)iterator.next()).getId()+"#";
                        }
                         
                        //System.out.println("child - "+child);
                        faultTable.setValueAt(child,slectedId , 13);
                        faultTable.setValueAt(selectedGate(), slectedId, 14);
                        
                        Faultdata faultdata = faultdataDao.findById(Integer.parseInt(faultTable.getValueAt(slectedId, 0)+""));
                        faultdata.setChild(child);
                        faultdata.setChildGate(selectedGate());
                        faultdataDao.Update(faultdata);
                
               dispose();
            }    
	}
    });
    
    cancel.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	   dispose();
	}
    });
    buttonPanel.add(cancel);
    
    getContentPane().add(dropCombo, BorderLayout.NORTH);
    getContentPane().add(dual, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);

  
        
        
        

    }
     
  public String selectedGate(){
  
  return ((JComboBox)dropCombo.getComponent(1)).getSelectedItem()+"";
  }
  
  public void setSelectedGate(int index){
  
     ((JComboBox)dropCombo.getComponent(1)).setSelectedIndex(index);
  }
  
  public DualListBox getDualListBox(){
    return dual;
  }
  
 
   /* private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new setChildFault();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }*/
 
    public static void main(String[] args) {
        
        
        
        
        
        	        /*FaultTreeChildSettings dialog = new FaultTreeChildSettings();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);*/
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
     /*   javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });*/
    }
}
