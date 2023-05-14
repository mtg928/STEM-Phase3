/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
/*from   w  ww  .  j  ava 2s.  c  o m*/
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SortOrder;
public class ColumnSelector{
  public static void main(String args[]) {
    JFrame frame = new JFrame("Dual List Box Tester");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    DualListBox dual = new DualListBox();
    dual.addSourceElements(new String[] {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"});
    dual.addTemplateElements(new String[] {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"});
    
    frame.add(dual, BorderLayout.CENTER);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}


class SortedListModel extends AbstractListModel {
  SortedSet<Object> model;

  public SortedListModel() {
    model = new TreeSet<Object>();
  }

  public int getSize() {
    return model.size();
  }

  public Object getElementAt(int index) {
    return model.toArray()[index];
  }

  public void add(Object element) {
    if (model.add(element)) {
      fireContentsChanged(this, 0, getSize());
    }
  }

  public void addAll(Object elements[]) {
    Collection<Object> c = Arrays.asList(elements);
    model.addAll(c);
    fireContentsChanged(this, 0, getSize());
  }

  public void clear() {
    model.clear();
    fireContentsChanged(this, 0, getSize());
  }

  public boolean contains(Object element) {
    return model.contains(element);
  }

  public Object firstElement() {
    return model.first();
  }

  public Iterator iterator() {
    return model.iterator();
  }

  public Object lastElement() {
    return model.last();
  }

  public boolean removeElement(Object element) {
    boolean removed = model.remove(element);
    if (removed) {
      fireContentsChanged(this, 0, getSize());
    }
    return removed;
  }
}

class DualListBox extends JPanel {
    
  private JList orginList;
  private SortedListModel orginListModel;   
  private JList sourceList;
  private SortedListModel sourceListModel;
  private JList destList;
  private SortedListModel destListModel;
  private JButton addButton;
  private JButton removeButton;

  public DualListBox() {
    initScreen();
  }

  public void clearSourceListModel() {
    sourceListModel.clear();
  }

  public void clearDestinationListModel() {
    destListModel.clear();
  }

  public void addSourceElements(ListModel newValue) {
    fillListModel(sourceListModel, newValue);
  }

  public void setSourceElements(ListModel newValue) {
    clearSourceListModel();
    addSourceElements(newValue);
  }

  public void addDestinationElements(ListModel newValue) {
    fillListModel(destListModel, newValue);
  }

  private void fillListModel(SortedListModel model, ListModel newValues) {
    int size = newValues.getSize();
    for (int i = 0; i < size; i++) {
      model.add(newValues.getElementAt(i));
    }
  }

  public void addSourceElements(Object newValue[]) {
    fillListModel(sourceListModel, newValue);
  }

  public void setSourceElements(Object newValue[]) {
    clearSourceListModel();
    addSourceElements(newValue);
  }

  public void addDestinationElements(Object newValue[]) {
    fillListModel(destListModel, newValue);
  }
  
  public void addTemplateElements(Object newValue[]) {
    fillListModel(orginListModel, newValue);
  }

  private void fillListModel(SortedListModel model, Object newValues[]) {
    model.addAll(newValues);
  }

  private void clearSourceSelected() {
    Object selected[] = sourceList.getSelectedValues();
    for (int i = selected.length - 1; i >= 0; --i) {
      sourceListModel.removeElement(selected[i]);
    }
    sourceList.getSelectionModel().clearSelection();
  }

  private void clearDestinationSelected() {
    Object selected[] = destList.getSelectedValues();
    for (int i = selected.length - 1; i >= 0; --i) {
      destListModel.removeElement(selected[i]);
    }
    destList.getSelectionModel().clearSelection();
  }

  private void initScreen() {
    setLayout(new GridLayout(0, 3));
    
    orginListModel = new SortedListModel();
    orginList = new JList(orginListModel);    
    
    sourceListModel = new SortedListModel();
    sourceList = new JList(sourceListModel);

    addButton = new JButton("<<");
    addButton.addActionListener(new AddListener());
    removeButton = new JButton(">>");
    removeButton.addActionListener(new RemoveListener());

    destListModel = new SortedListModel();
    destList = new JList(destListModel);
  
    JPanel orginPanel = new JPanel(new BorderLayout());
    orginPanel.add(new JLabel("Template columns"), BorderLayout.NORTH);
    orginPanel.add(new JScrollPane(orginList), BorderLayout.CENTER);
    
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(new JLabel("Available Elements:"), BorderLayout.NORTH);
    leftPanel.add(new JScrollPane(sourceList), BorderLayout.CENTER);
    leftPanel.add(addButton, BorderLayout.SOUTH);

    JPanel rightPanel = new JPanel(new BorderLayout());

    rightPanel.add(new JLabel("Selected Elements:"), BorderLayout.NORTH);
    rightPanel.add(new JScrollPane(destList), BorderLayout.CENTER);
    rightPanel.add(removeButton, BorderLayout.SOUTH);

    add(orginPanel);
    add(rightPanel);
    add(leftPanel);
  }
  
  

  private class AddListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object selected[] = sourceList.getSelectedValues();
      addDestinationElements(selected);
      clearSourceSelected();
    }
  }

  private class RemoveListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object selected[] = destList.getSelectedValues();
      addSourceElements(selected);
      clearDestinationSelected();
    }
  }


}
