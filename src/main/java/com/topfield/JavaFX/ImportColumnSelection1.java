/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;


import com.topfield.datamodel.Item;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

public class ImportColumnSelection1 extends JPanel {
  private ArrayListTransferHandler arrayListHandler;

  public ImportColumnSelection1(String[] orgList,String[] matchList) {
    arrayListHandler = new ArrayListTransferHandler();
    JList listTemp,list1, list2;
    
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
    
    DefaultListModel modelTemp = new DefaultListModel();
    
      for (int i = 0; i < orgList.length; i++) {
          modelTemp.addElement(new Item(i, orgList[i]));
      }

    listTemp = new JList(modelTemp);
    //listTemp.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    //listTemp.setTransferHandler(arrayListHandler);
    //listTemp.setDragEnabled(false);
    JScrollPane tempView = new JScrollPane(listTemp);
    tempView.setPreferredSize(new Dimension(200, 400));
    JPanel panelTemp = new JPanel();
    panelTemp.setLayout(new BorderLayout());
    panelTemp.add(tempView, BorderLayout.CENTER);
    panelTemp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    DefaultListModel list1Model = new DefaultListModel();

    list1 = new JList(list1Model);
    list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list1.setTransferHandler(arrayListHandler);
    list1.setDragEnabled(true);
    JScrollPane list1View = new JScrollPane(list1);
    list1View.setPreferredSize(new Dimension(200, 100));
    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());
    panel1.add(list1View, BorderLayout.CENTER);
    panel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    DefaultListModel list2Model = new DefaultListModel();

      for (int i = 0; i < matchList.length; i++) {
          list2Model.addElement(new Item(i, matchList[i]));
      }
    
    list2 = new JList(list2Model);
    list2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list2.setTransferHandler(arrayListHandler);
    list2.setDragEnabled(true);
    JScrollPane list2View = new JScrollPane(list2);
    list2View.setPreferredSize(new Dimension(200, 100));
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.add(list2View, BorderLayout.CENTER);
    panel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    
    add(panelTemp, BorderLayout.LINE_START);
    add(panel1, BorderLayout.CENTER);
    add(panel2, BorderLayout.LINE_END);
    
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    //Create and set up the window.
    JFrame frame = new JFrame("DragListDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    String[] org = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"};
    String[] match = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode"};
    
    ImportColumnSelection1 demo = new ImportColumnSelection1(org,match);
    frame.setContentPane(demo);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
  
  
  
}






/*
 * ArrayListTransferHandler.java is used by the 1.4 DragListDemo.java example.
 */





class ArrayListTransferHandler extends TransferHandler {
  DataFlavor localArrayListFlavor, serialArrayListFlavor;

  String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
      + ";class=java.util.ArrayList";

  JList source = null;

  int[] indices = null;

  int addIndex = -1; //Location where items were added

  int addCount = 0; //Number of items added

  public ArrayListTransferHandler() {
    try {
      localArrayListFlavor = new DataFlavor(localArrayListType);
    } catch (ClassNotFoundException e) {
      System.out
          .println("ArrayListTransferHandler: unable to create data flavor");
    }
    serialArrayListFlavor = new DataFlavor(ArrayList.class, "ArrayList");
  }

  public boolean importData(JComponent c, Transferable t) {
    JList target = null;
    ArrayList alist = null;
    if (!canImport(c, t.getTransferDataFlavors())) {
      return false;
    }
    try {
      target = (JList) c;
      if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
        alist = (ArrayList) t.getTransferData(localArrayListFlavor);
      } else if (hasSerialArrayListFlavor(t.getTransferDataFlavors())) {
        alist = (ArrayList) t.getTransferData(serialArrayListFlavor);
      } else {
        return false;
      }
    } catch (UnsupportedFlavorException ufe) {
      System.out.println("importData: unsupported data flavor");
      return false;
    } catch (IOException ioe) {
      System.out.println("importData: I/O exception");
      return false;
    }

    //At this point we use the same code to retrieve the data
    //locally or serially.

    //We'll drop at the current selected index.
    int index = target.getSelectedIndex();

    //Prevent the user from dropping data back on itself.
    //For example, if the user is moving items #4,#5,#6 and #7 and
    //attempts to insert the items after item #5, this would
    //be problematic when removing the original items.
    //This is interpreted as dropping the same data on itself
    //and has no effect.
    if (source.equals(target)) {
      if (indices != null && index >= indices[0] - 1
          && index <= indices[indices.length - 1]) {
        indices = null;
        return true;
      }
    }

    DefaultListModel listModel = (DefaultListModel) target.getModel();
    int max = listModel.getSize();
    if (index < 0) {
      index = max;
    } else {
      index++;
      if (index > max) {
        index = max;
      }
    }
    addIndex = index;
    addCount = alist.size();
    for (int i = 0; i < alist.size(); i++) {
      listModel.add(index++, alist.get(i));
    }
    return true;
  }

  protected void exportDone(JComponent c, Transferable data, int action) {
    if ((action == MOVE) && (indices != null)) {
      DefaultListModel model = (DefaultListModel) source.getModel();

      //If we are moving items around in the same list, we
      //need to adjust the indices accordingly since those
      //after the insertion point have moved.
      if (addCount > 0) {
        for (int i = 0; i < indices.length; i++) {
          if (indices[i] > addIndex) {
            indices[i] += addCount;
          }
        }
      }
      for (int i = indices.length - 1; i >= 0; i--)
          
        model.remove(indices[i]);
    }
    indices = null;
    addIndex = -1;
    addCount = 0;
  }

  private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
    if (localArrayListFlavor == null) {
      return false;
    }

    for (int i = 0; i < flavors.length; i++) {
      if (flavors[i].equals(localArrayListFlavor)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasSerialArrayListFlavor(DataFlavor[] flavors) {
    if (serialArrayListFlavor == null) {
      return false;
    }

    for (int i = 0; i < flavors.length; i++) {
      if (flavors[i].equals(serialArrayListFlavor)) {
        return true;
      }
    }
    return false;
  }

  public boolean canImport(JComponent c, DataFlavor[] flavors) {
    if (hasLocalArrayListFlavor(flavors)) {
      return true;
    }
    if (hasSerialArrayListFlavor(flavors)) {
      return true;
    }
    return false;
  }

  protected Transferable createTransferable(JComponent c) {
    if (c instanceof JList) {
      source = (JList) c;
      indices = source.getSelectedIndices();
      Object[] values = source.getSelectedValues();
      if (values == null || values.length == 0) {
        return null;
      }
      ArrayList alist = new ArrayList(values.length);
      for (int i = 0; i < values.length; i++) {
        Object o = values[i];
        String str = o.toString();
        if (str == null)
          str = "";
        //alist.add(str);
        alist.add(new Item(i, str));
      }
      return new ArrayListTransferable(alist);
    }
    return null;
  }

  public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
  }

  
  
  
  public class ArrayListTransferable implements Transferable {
    ArrayList data;

    public ArrayListTransferable(ArrayList alist) {
      data = alist;
    }

    public Object getTransferData(DataFlavor flavor)
        throws UnsupportedFlavorException {
      if (!isDataFlavorSupported(flavor)) {
        throw new UnsupportedFlavorException(flavor);
      }
      return data;
    }

    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { localArrayListFlavor,
          serialArrayListFlavor };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
      if (localArrayListFlavor.equals(flavor)) {
        return true;
      }
      if (serialArrayListFlavor.equals(flavor)) {
        return true;
      }
      return false;
    }
  }
}    
