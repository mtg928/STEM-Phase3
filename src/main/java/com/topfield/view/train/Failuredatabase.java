/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;


import com.topfield.dao.FailuredataDao;
import com.topfield.daoImpl.FailuredataDaoImpl;
import com.topfield.modal.Failuredata;
import com.topfield.modal.Failuremodes;
import com.topfield.settings.FrameSettings;
import com.topfield.utilities.NumberConversion;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.util.Collection;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.*;

public class Failuredatabase {
    
  private FailuredataDao failuredataDao = new FailuredataDaoImpl();
  private List<Failuredata> faildatalist = failuredataDao.getAllFailuredata();
    
  private  String[] columnNames = {"Id", "Systems", "Components", "Failurerate Low (/h)", "Failurerate Medium (/h)","Failurerate High (/h)","Releasedate"};
  private  Object[][] data = listtoArray();
  private final DefaultTableModel model = new DefaultTableModel(data, columnNames) {
    @Override public Class<?> getColumnClass(int column) {
      return getValueAt(0, column).getClass();
    }
  };
  private final JTable table = new JTable(model){
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
        Component returnComp = super.prepareRenderer(renderer, row, column);
        Color alternateColor = new Color(252,242,206);
        Color whiteColor = Color.WHITE;
        if (!returnComp.getBackground().equals(getSelectionBackground())){
            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
            returnComp .setBackground(bg);
            bg = null;
        }
        return returnComp;
}};
  private DefaultTableModel subModel;
  private final JTable subData = new JTable(subModel);
  private final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);
  private final JTextField searchFilter = new JTextField();
  private final JButton open = new JButton("Load");
  
  

  public JComponent makeUI() {
      

      
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setTopComponent(new JScrollPane(table));
    splitPane.setBottomComponent(new JScrollPane(subData));
    splitPane.setResizeWeight(.5d);  
      
      
    table.setRowSorter(rowSorter);
    table.getTableHeader().setForeground(FrameSettings.getButtonColor());
    table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
    
    subData.getTableHeader().setOpaque(true);
    subData.setOpaque(true);
    subData.getTableHeader().setBackground(Color.gray);
    subData.setBackground(new Color(254, 249, 231)); 
    subData.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
    subData.setBorder(new EtchedBorder(EtchedBorder.RAISED));
    subData.setGridColor(Color.LIGHT_GRAY);
    
    
    open.addActionListener(new OpenData());//end OpenData class

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JLabel("Search:"), BorderLayout.WEST);
    panel.add(searchFilter, BorderLayout.CENTER);
    panel.add(open, BorderLayout.EAST);

    searchFilter.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        String text = searchFilter.getText();
        System.out.println(text);
        if (text.trim().length() == 0) {
          rowSorter.setRowFilter(null);
        } else {
          rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
        table.repaint();
      }
      @Override
      public void removeUpdate(DocumentEvent e) {
        String text = searchFilter.getText();
        if (text.trim().length() == 0) {
          rowSorter.setRowFilter(null);
        } else {
          rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
      }
      @Override
      public void changedUpdate(DocumentEvent e) {
        //not needed: throw new UnsupportedOperationException("Not supported yet.");
      }
    });
    
    
    table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e)
             {

             int row=table.rowAtPoint(e.getPoint());
             int col= table.columnAtPoint(e.getPoint());
             Failuredata failuredata;

            // JOptionPane.showMessageDialog(null," Value in the cell clicked :"+ " " +table.getValueAt(row,col).toString());

             System.out.println(" Value in the cell clicked :" + row +"-"+ table.getValueAt(row,0).toString());
             
             failuredata = failuredataDao.findById(Integer.parseInt(table.getValueAt(row,0)+""));
             
             Collection<Failuremodes> fmodeslist= failuredata.getFailuremodesCollection();
             String column[]={"ID","FAILURE MODE","FAILUREMODE PROBABILITY","DESCRIPTION"};
             String data[][] = new String[fmodeslist.size()][column.length];
             int count=0;

                 for (Failuremodes failuremodes : fmodeslist) {
                     data[count][0] = failuremodes.getModeid()+"";
                     data[count][1] = failuremodes.getFailuremode();
                     data[count][2] = failuremodes.getFailuremodeprobability()+"";
                     data[count][3] = failuremodes.getDescription();
                     count++;
                 }
      

                subModel = new DefaultTableModel(data, column);
                subData.setModel(subModel);
             

             }

             }

);




    
    

    JPanel p = new JPanel(new BorderLayout());
    p.add(splitPane);
    p.add(panel, BorderLayout.SOUTH);
    return p;
  }
  
  
  class OpenData implements ActionListener {
    @Override public void actionPerformed(ActionEvent e) {

      /*DefaultTableModel m = new DefaultTableModel(columnNames, 0);
      //Dummy data
      for (int i = 0; i < 5; i++) {
        String[] dataArray = {"aa" + i, "", "", "", ""};
        m.addRow(dataArray);
      }
      table.setModel(m);
      rowSorter.setModel(m);*/
    }//end action method
  }
  
  public Object[][] listtoArray(){
     
     Object[][] res = new Object[faildatalist.size()][columnNames.length];
     int count=0;
     
      System.out.println("res - "+res.length);
     
      for (Failuredata failuredata : faildatalist) {
          res[count][0] = failuredata.getId();
          res[count][1] = failuredata.getSystems();
          res[count][2] = failuredata.getComponents();
          res[count][3] = failuredata.getFailureratelow()+"";
          res[count][4] = failuredata.getFailureratemedium()+"";
          res[count][5] = failuredata.getFailureratehigh()+"";
          res[count][6] = failuredata.getReleasedate();

          count++;
      }
      

     return res;
  }
  
  
  
  
  
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGUI();
      }
    });
  }
  public static void createAndShowGUI() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.getContentPane().add(new Failuredatabase().makeUI());
    f.setSize(320, 240);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
  
  
}
