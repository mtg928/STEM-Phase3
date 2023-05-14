/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;



    
import coachspecs.utilities.VectorTableCellRenderer;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.modal.Functionalfailures;
import com.topfield.utilities.RowHeaderRenderer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Murali
 */
public class FunctionalFailuresInfo extends JPanel {
    
    private FunctionalfailuresDao dao = new FunctionalfailuresDaoImpl();
    private String description;
    //private 
  
  public FunctionalFailuresInfo(String description) {

    setLayout(new BorderLayout());
    this.description = description;
         
    ListModel lm = new AbstractListModel() {
      //String headers[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        String headers[] = getHeading();
      public int getSize() { return headers.length; }
      public Object getElementAt(int index) {
        return headers[index];
      }
    };
  
    DefaultTableModel dm = new DefaultTableModel(14,10);
    JTable table = new JTable( dm );
        //or, set TableCellRenderer into a specified JTable column
   // table.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
    
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setRowHeight(40);
   
    JList rowHeader = new JList(lm);   
    rowHeader.setFixedCellWidth(150);
    rowHeader.setAlignmentX(LEFT_ALIGNMENT);
     
    rowHeader.setFixedCellHeight(table.getRowHeight()
                               /*+ table.getRowMargin()*/);
//                             + table.getIntercellSpacing().height);
    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
    table.setDefaultRenderer(Vector.class, new VectorTableCellRenderer());
    
    dm.setDataVector(getData(),getHeadingTop());
    
    //table.setModel(createTableModel());
    
      SetColumn(table);
    
  
    JScrollPane scroll = new JScrollPane( table );
    scroll.setRowHeaderView(rowHeader);
    add(scroll, BorderLayout.CENTER);
  }
  
  public static void main(String[] args) {
    //FunctionalFailuresInfo frame = new FunctionalFailuresInfo();
    
    
    JFrame frame = createFrame();
    frame.add(new FunctionalFailuresInfo("precharging resistor"));
    frame.addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        System.exit(0);
      }
    });
    frame.setVisible(true);
    
    
  }
  
    private static JFrame createFrame() {
      JFrame frame = new JFrame("JComboBox Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(600, 500));
      return frame;
  }
    
    private  TableModel createTableModel() {
      Vector<String> columns = new Vector<>(Arrays.asList("Projecttype","Failuretype","Description","Function","Phase","FailureMode","FailureCause","LocalFailureEffect","SubsystemFailureEffect","TrainFailureEffect","FailureDetection","Importance","FailureRate","PreventiveMeasures"));
      Vector<Vector<Object>> rows = new Vector<>();

      
       for (Functionalfailures functionalfailures : dao.findByName(description)) {
            System.out.println("functionalfailures - "+ functionalfailures.getDescription());
            
             Vector<Object> v = new Vector<>();
             v.add(functionalfailures.getDescription());
             v.add(functionalfailures.getFunction());
             v.add(functionalfailures.getFailureMode());
             rows.add(v);
        }

      DefaultTableModel dtm = new DefaultTableModel(rows, columns) {
          @Override
          public Class<?> getColumnClass(int columnIndex) {
              return columnIndex == 20 ? Integer.class : String.class;
          }
      };
      return dtm;
  }
    
    
    public Object [][] getData(){
    
     List<Functionalfailures> funs =dao.findByName(description);
     
     Object [][] res = new Object[14][funs.size()];
        
     
        for (int i = 0; i < funs.size(); i++) {
            
            res[0] [i]= funs.get(i).getProjecttype();
            res[1] [i]= funs.get(i).getFailuretype();
            res[2] [i]= funs.get(i).getDescription();
            res[3] [i]= funs.get(i).getFunction();
            res[4] [i]= funs.get(i).getPhase();
            res[5] [i]= funs.get(i).getFailureMode();
            res[6] [i]= funs.get(i).getFailureCause();
            res[7] [i]= funs.get(i).getLocalFailureEffect();
            res[8] [i]= funs.get(i).getSubsystemFailureEffect();
            res[9] [i]= funs.get(i).getTrainFailureEffect();
            res[10][i] = funs.get(i).getFailureDetection();
            res[11][i] = funs.get(i).getImportance();
            res[12][i] = funs.get(i).getFailureRate();
            res[13][i] = funs.get(i).getPreventiveMeasures();
            
        }
 
     
     
    
     return res;
    }
    
    
    public String[] getHeading(){
    
    
     return new String[]{"Projecttype","Failuretype","Description","Function","Phase","FailureMode","FailureCause","LocalFailureEffect","SubsystemFailureEffect","TrainFailureEffect","FailureDetection","Importance","FailureRate","PreventiveMeasures"};
    }
    
    
   public String[] getHeadingTop(){
    List<Functionalfailures> funs =dao.findByName(description);
     
     String [] res = new String[funs.size()];
        
     
        for (int i = 0; i < funs.size(); i++) {
            
            res [i]= funs.get(i).getDescription();

        }    
        
        return res;
    }
    
   public void SetColumn(JTable table){
   
    for (int i = 0; i < table.getColumnCount(); i++) {
      TableColumn   column = table.getColumnModel().getColumn(i);
      
      column.setPreferredWidth(500); 
       /* if (i == 2) {
            column.setPreferredWidth(100); //sport column is bigger
        } else {
            column.setPreferredWidth(50);
        }*/
    }    
   
   
   }
    
}


