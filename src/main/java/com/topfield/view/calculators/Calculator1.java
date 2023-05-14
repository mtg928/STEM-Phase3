/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;


import com.topfield.dao.ComponentsDao;
import com.topfield.dao.SILDao;
import com.topfield.daoImpl.ComponentsDaoImpl;
import com.topfield.daoImpl.SILDaoImpl;
import com.topfield.modal.Components;
import com.topfield.modal.Sil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import com.topfield.modal.Sil;

/**
 *
 * @author Murali
 */
public class Calculator1 extends JPanel{
    
    private ComponentsDao componentsDao = new ComponentsDaoImpl();
    private Calculator1_form SensorComponentForm;
    private Calculator1_form LogicSystemComponentForm;
    private Calculator1_form FinalElementComponentForm;
    private JList<Components> SensorComponentsAddList = new JList<>(new DefaultListModel<>());
    private JList<Components> LSCsAddList = new JList<>(new DefaultListModel<>());
    private JList<Components> FECsAddList = new JList<>(new DefaultListModel<>());
    private Sil sil;
    private String voting;
    private SILDao silDao = new SILDaoImpl();
    

    public Calculator1(int silId,String voting) {
        
        
        setLayout(new BorderLayout());
        
        JPanel mainContent = new JPanel();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.sil = silDao.findById(silId);
        this.voting = voting;
                
        //mainContent.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        
       // setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        mainContent.setBorder( BorderFactory.createEmptyBorder() );
        SensorComponent sensor = new SensorComponent();
        LogicSystemComponent LSC= new LogicSystemComponent();
        FinalElementComponent FEL = new FinalElementComponent();
        //setBackground(Color.DARK_GRAY);
        
        //JPanel maincontent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent,BoxLayout.X_AXIS));    
        //add(Box.createRigidArea(new Dimension(5,0)));
        mainContent.add(sensor);
        mainContent.add(Box.createRigidArea(new Dimension(5,0)));
        mainContent.add(LSC);
        mainContent.add(Box.createRigidArea(new Dimension(5,0)));
        mainContent.add(FEL);
        //maincontent.add(Box.createRigidArea(new Dimension(5,0)));
        
        
        add(mainContent);
        add(new Calculator1FromTotal(SensorComponentsAddList,(JPanel)sensor.getComponent(2),
                                     LSCsAddList,(JPanel)LSC.getComponent(2),
                                     FECsAddList,(JPanel)FEL.getComponent(2),silId));
    }
    
    public void setcomponentsValues(Calculator1_form calculator1_form,JList<String> ComponentAddList,JList<Components> componentsList){
        
        MouseListener mouseListener = new MouseAdapter() {
      public void mouseClicked(MouseEvent mouseEvent) {
          //System.out.println("8888888888888888888888888888");
               

        JList theList = (JList) mouseEvent.getSource();
        //if (mouseEvent.getClickCount() == 2) {
          int index = theList.locationToIndex(mouseEvent.getPoint());
          if (index >= 0) {
            Object o = theList.getModel().getElementAt(index);
            //System.out.println("Double-clicked on: " + o.toString());
            //JOptionPane.showMessageDialog(null, "Double-clicked on: " + o.toString());
            

                
              try {
                        //Components component= componentsDao.findByName(o.toString());
               Components component = ((DefaultListModel<Components>) componentsList.getModel()).elementAt(index);
                calculator1_form.setInpLambda1(component.getLamda());
                //SensorComponentForm.setInpLambda2(2.00);
                calculator1_form.setSaftyFactor(component.getSafetyRelevantFactor());
                calculator1_form.setInpCCF1(component.getCcf());
                calculator1_form.setInpDC1(component.getDc());
                calculator1_form.setInpMTTR1(component.getMttr());
                calculator1_form.setInpPTP1(component.getPti());
 
              } catch (ArrayIndexOutOfBoundsException e) {
                  
                   System.out.println("setcomponentsValues Error - "+e);
                   
                calculator1_form.setInpLambda1(0.00);
                //SensorComponentForm.setInpLambda2(2.00);
                calculator1_form.setSaftyFactor(0.00);
                calculator1_form.setInpCCF1(0.00);
                calculator1_form.setInpDC1(0.00);
                calculator1_form.setInpMTTR1(0.00);
                calculator1_form.setInpPTP1(0.00);
                   
                   
               }catch (Exception e) {
                  
                  JOptionPane.showMessageDialog(null, "Error - "+ e);
              }
            
            
          }
       // }
      }
    };
    ComponentAddList.addMouseListener(mouseListener);
        
    
    } 
    
    public void AddComponents(int index,String comName){
    
          Components component= componentsDao.findByName(comName);
          DefaultListModel <Components> Components = null;
          
          System.out.println("@@@@@@@@@@@@@@@@@@@2 "+comName);
          
          
           
           if(component.getComType().equals("Sensors")) {
               // System.out.println("inside "+(SensorComponents).elementAt(index));
               Components = (DefaultListModel<Components>) SensorComponentsAddList.getModel();
               
           }else if(component.getComType().equals("Controller")) {
               Components = (DefaultListModel<Components>) LSCsAddList.getModel();
               
           }else if(component.getComType().equals("Final Element")) {
               Components = (DefaultListModel<Components>) FECsAddList.getModel();
               
           }    
           
             try {
                   System.out.println("index - " + index+" size - "+Components.size());
                   if((Components).elementAt(index)== null){  
                   System.out.println("inside");
                   Components.add(index, component);   
                    }else{
                       System.out.println("outside");
                    }
                   
               } catch (ArrayIndexOutOfBoundsException e) {
                   Components.add(index, component);
                   System.out.println(""+e);
               }

          
               for (int i = 0; i < Components.size(); i++) {
			System.out.println(i+") "+Components.elementAt(i));
		}
          
    }
    
    public void removeComponents(int index,String comName){
        Components component= componentsDao.findByName(comName);
        DefaultListModel <Components> Components = null;
        Calculator1_form calculator1_forms = null;
        
          //System.out.println("component.getComType() " + component.getComType());
           
           if(component.getComType().equals("Sensors")) {
               // System.out.println("inside "+(SensorComponents).elementAt(index));
               Components = (DefaultListModel<Components>) SensorComponentsAddList.getModel();
               calculator1_forms = SensorComponentForm;
           }else if(component.getComType().equals("Controller")) {
               Components = (DefaultListModel<Components>) LSCsAddList.getModel();
               calculator1_forms = LogicSystemComponentForm;
           }else if(component.getComType().equals("Final Element")) {
               Components = (DefaultListModel<Components>) FECsAddList.getModel();
               calculator1_forms = FinalElementComponentForm;
           }    
        
        try {
            Components.removeElementAt(index);
            
                calculator1_forms.setInpLambda1(0.00);
                //SensorComponentForm.setInpLambda2(2.00);
                calculator1_forms.setSaftyFactor(0.00);
                calculator1_forms.setInpCCF1(0.00);
                calculator1_forms.setInpDC1(0.00);
                calculator1_forms.setInpMTTR1(0.00);
                calculator1_forms.setInpPTP1(0.00);
            
        } catch (Exception e) {
            System.out.println("removeComponents - "+e);
        }
    
    
    }
    
    public void LoadComponents(JList<String> ComponentsAddList, String com,int position){
    
        ((DefaultListModel<String>) ComponentsAddList.getModel()).add(position, com);
         AddComponents(position, com);
    
    }
    
    public void editComponents(){
    
    
    
    }
    
    
   public class SensorComponent extends JPanel{
       
        private JList<String> SensorComponentList = new JList<>(new DefaultListModel<>());
        private JList<String> SensorComponentAddList = new JList<>(new DefaultListModel<>());
        

    public SensorComponent() {
        String load[];
        setLayout(new BorderLayout());
       // setBackground(Color.LIGHT_GRAY);
       // setAlignmentX(CENTER_ALIGNMENT);
        setBackground(new java.awt.Color(214, 219, 223));
        
        try {
            load = sil.getSensors().split("\\#");
            for (int i = 0; i < load.length; i++) {
                //System.out.println("load[i] - "+load[i]+" size"+load.length);
                if (!load[i].equals("")) {
                    LoadComponents(SensorComponentAddList, load[i], i);
                }
                
            }
            
            
            
        } catch (Exception e) {
        }
        
        
        //LoadComponents(SensorComponentAddList, "Pressure", 0);
        //LoadComponents(SensorComponentAddList, "Accelerometer", 1);
        
        // ((DefaultListModel<String>) SensorComponentAddList.getModel()).add(0, "Pressure");
        // AddComponents(0, "Pressure");
        
        //SensorComponentsAddList.
            
     /* for (int i = 0; i < 15; i++) {
      ((DefaultListModel<String>) SensorComponentList.getModel()).add(i, "Sensor " + i);
      //((DefaultListModel<String>) SensorComponentAddList.getModel()).add(i, "B " + i);
      }*/
     
       // System.out.println(" ----------"+componentsDao.getAllComponents().get(0).getComDescription());
        
        List<Components> SensorcomponentsList = componentsDao.getAllComponentsByType("Sensors");
 
        
        for (int i = 0; i < SensorcomponentsList.size(); i++) {
            ((DefaultListModel<String>) SensorComponentList.getModel()).add(i, SensorcomponentsList.get(i).getComDescription());
        }

      
      JLabel myLabel = new JLabel("<html> <body> <h1> <center> Sensor Component </center> </h1>  </body> </html>");
      JLabel myLabel1 = new JLabel("<html> <body> <h1> <center> Sensor Component </center> </h1>  </body> </html>");
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);
      
             
   /*Box nameBox = Box.createHorizontalBox();
    nameBox.add(new JLabel("New:"));
    nameBox.add(newTextField);*/

    Box sourceBox = Box.createVerticalBox();
    sourceBox.add(new JLabel("Sensors"));
    sourceBox.add(new JScrollPane(getSensorComponentList()));

    Box destBox = Box.createVerticalBox();
    destBox.add(new JLabel("Selected Sensors"));
    destBox.add(new JScrollPane(getSensorComponentAddList()));

    Box listBox = Box.createHorizontalBox();
    listBox.add(sourceBox);
    listBox.add(destBox);

    Box allBox = Box.createVerticalBox();
    //allBox.add(nameBox);
   // allBox.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    //allBox.add(myLabel);
    allBox.add(listBox);

    

     //add(allBox);
     
    SensorComponentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    SensorComponentAddList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

   // newTextField.setDragEnabled(true);
    SensorComponentList.setDragEnabled(true);
    SensorComponentAddList.setDragEnabled(true);

    SensorComponentList.setDropMode(DropMode.INSERT);
    SensorComponentAddList.setDropMode(DropMode.INSERT);

    SensorComponentList.setTransferHandler(new ListTransferHandler());
    SensorComponentAddList.setTransferHandler(new ListTransferHandler1());
    
    SensorComponentForm = new Calculator1_form("Sensors",voting,SensorComponentsAddList,SensorComponentAddList);
     
 /*   SensorComponentForm.setInpLambda1(0.0000002);
    //SensorComponentForm.setInpLambda2(2.00);
    SensorComponentForm.setSaftyFactor(50.00);
    SensorComponentForm.setInpCCF1(0.02);
    SensorComponentForm.setInpDC1(0.99);
    SensorComponentForm.setInpMTTR1(8.00);
    SensorComponentForm.setInpPTP1(1.00);*/
    
    
       
    
    
    
       // System.out.println("|||||||||||||||||||||||| "+SensorComponentForm.);
   /* SensorComponentForm.setLayout(new GridLayout(3, 2));
    
        JLabel lblPhone = new JLabel("Phone #");
	lblPhone.setBounds(65, 68, 46, 14);
	SensorComponentForm.add(lblPhone);
	
	JTextField textField_1 = new JTextField();
	textField_1.setBounds(128, 65, 86, 20);
        textField_1.setColumns(10);
	SensorComponentForm.add(textField_1);
	
	
	JLabel lblEmailId = new JLabel("Email Id");
	lblEmailId.setBounds(65, 115, 46, 14);
	SensorComponentForm.add(lblEmailId);
	
	JTextField textField_2 = new JTextField();
        textField_2.setColumns(10);
	textField_2.setBounds(128, 112, 247, 17);
	SensorComponentForm.add(textField_2);
        
        JLabel lblEmailId1 = new JLabel("Email Id");
	lblEmailId1.setBounds(65, 162, 46, 14);
	SensorComponentForm.add(lblEmailId1);
	
	JTextField textField_21 = new JTextField();
        textField_21.setColumns(10);
	textField_21.setBounds(126, 157, 212, 40);
	SensorComponentForm.add(textField_21);*/
	
     setcomponentsValues(SensorComponentForm,SensorComponentAddList,SensorComponentsAddList);
    
     add(myLabel,BorderLayout.PAGE_START);
     add(allBox, BorderLayout.CENTER);
     add(SensorComponentForm,BorderLayout.PAGE_END);
     
     }

        /**
         * @return the SensorComponentList
         */
        public JList<String> getSensorComponentList() {
            return SensorComponentList;
        }

        /**
         * @return the SensorComponentAddList
         */
        public JList<String> getSensorComponentAddList() {
            return SensorComponentAddList;
        }
    
   
   } 
    
   public class LogicSystemComponent extends JPanel{

       
       
        private JList<String> LSCList = new JList<>(new DefaultListModel<>());
        private JList<String> LSCAddList = new JList<>(new DefaultListModel<>());

        public LogicSystemComponent() {
          String[] load;  
          setLayout(new BorderLayout());
          // setBackground(Color.LIGHT_GRAY);
          // setAlignmentX(CENTER_ALIGNMENT);
          setBackground(new java.awt.Color(214, 219, 223));
          LogicSystemComponentForm = new Calculator1_form("Controller",voting,LSCsAddList,this.getLSCAddList());
          
            try {
                load = sil.getController().split("\\#");
                for (int i = 0; i < load.length; i++) {
                    if (!load[i].equals("")) {
                    LoadComponents(LSCAddList, load[i], i);
                    }
                }
            } catch (Exception e) {
            }

            
    /*for (int i = 0; i < 15; i++) {
      ((DefaultListModel<String>) LSCList.getModel()).add(i, "LogicSystem " + i);
     // ((DefaultListModel<String>) SensorComponentAddList.getModel()).add(i, "B " + i);
    }*/
    
      List<Components> LSCcomponentsList = componentsDao.getAllComponentsByType("Controller");
     
        for (int i = 0; i < LSCcomponentsList.size(); i++) {
            ((DefaultListModel<String>) LSCList.getModel()).add(i, LSCcomponentsList.get(i).getComDescription());
        }
    
    JLabel myLabel = new JLabel("<html> <body> <h1> <center> Logic System Component </center> </h1>  </body> </html>");
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);
        
   /*Box nameBox = Box.createHorizontalBox();
    nameBox.add(new JLabel("New:"));
    nameBox.add(newTextField);*/

    Box sourceBox = Box.createVerticalBox();
    sourceBox.add(new JLabel("Logic Systems"));
    sourceBox.add(new JScrollPane(LSCList));

    Box destBox = Box.createVerticalBox();
    destBox.add(new JLabel("Selected Logic Systems"));
    destBox.add(new JScrollPane(getLSCAddList()));

    Box listBox = Box.createHorizontalBox();
    listBox.add(sourceBox);
    listBox.add(destBox);

    Box allBox = Box.createVerticalBox();
    //allBox.add(nameBox);
   // allBox.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    allBox.add(listBox);
    
    LSCList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    LSCAddList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    setcomponentsValues(LogicSystemComponentForm,LSCAddList,LSCsAddList);


   // newTextField.setDragEnabled(true);
    LSCList.setDragEnabled(true);
    LSCAddList.setDragEnabled(true);

    LSCList.setDropMode(DropMode.INSERT);
    LSCAddList.setDropMode(DropMode.INSERT);

    LSCList.setTransferHandler(new ListTransferHandler());
    LSCAddList.setTransferHandler(new ListTransferHandler1());
    
    
   

     add(myLabel,BorderLayout.PAGE_START);
     add(allBox, BorderLayout.CENTER);
     add(LogicSystemComponentForm,BorderLayout.PAGE_END);
     //add(allBox);
     
   
        }
    /**
         * @return the LSCAddList
         */
        public JList<String> getLSCAddList() {
            return LSCAddList;
        }

        /**
         * @param LSCAddList the LSCAddList to set
         */
        public void setLSCAddList(JList<String> LSCAddList) {
            this.LSCAddList = LSCAddList;
        }
   
   } 
   
   public class FinalElementComponent extends JPanel{

       
       
       private JList<String> FECList = new JList<>(new DefaultListModel<>());
        private JList<String> FECAddList = new JList<>(new DefaultListModel<>());

        public FinalElementComponent() {
            String [] load;
            setLayout(new BorderLayout());
            // setBackground(Color.LIGHT_GRAY);
            // setAlignmentX(CENTER_ALIGNMENT);
             setBackground(new java.awt.Color(214, 219, 223));
             FinalElementComponentForm = new Calculator1_form("Final Element",voting,FECsAddList,this.getFECAddList());
             
    JLabel myLabel = new JLabel("<html> <body> <h1> <center> Final Element Component </center> </h1>  </body> </html>");
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setVerticalAlignment(JLabel.CENTER);    
        
            try {
                 load = sil.getFinalElement().split("\\#");
                for (int i = 0; i < load.length; i++) {
                    if (!load[i].equals("")) {
                    LoadComponents(FECAddList, load[i], i);
                    }
                }
            } catch (Exception e) {
            }
        


            
   /* for (int i = 0; i < 15; i++) {
      ((DefaultListModel<String>) FECList.getModel()).add(i, "FinalElement " + i);
     // ((DefaultListModel<String>) SensorComponentAddList.getModel()).add(i, "B " + i);
    }*/
   
        List<Components> FECcomponentsList = componentsDao.getAllComponentsByType("Final Element");
     
         for (int i = 0; i < FECcomponentsList.size(); i++) {
            ((DefaultListModel<String>) FECList.getModel()).add(i, FECcomponentsList.get(i).getComDescription());
         }
             
   /*Box nameBox = Box.createHorizontalBox();
    nameBox.add(new JLabel("New:"));
    nameBox.add(newTextField);*/

    Box sourceBox = Box.createVerticalBox();
    sourceBox.add(new JLabel("Final Elements "));
    sourceBox.add(new JScrollPane(FECList));

    Box destBox = Box.createVerticalBox();
    destBox.add(new JLabel("Selected Final Elements"));
    destBox.add(new JScrollPane(getFECAddList()));

    Box listBox = Box.createHorizontalBox();
    listBox.add(sourceBox);
    listBox.add(destBox);

    Box allBox = Box.createVerticalBox();
    //allBox.add(nameBox);
   // allBox.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    allBox.add(listBox);
     
    FECList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    FECAddList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

   // newTextField.setDragEnabled(true);
    FECList.setDragEnabled(true);
    FECAddList.setDragEnabled(true);

    FECList.setDropMode(DropMode.INSERT);
    FECAddList.setDropMode(DropMode.INSERT);

    FECList.setTransferHandler(new ListTransferHandler());
    FECAddList.setTransferHandler(new ListTransferHandler1());
    
    setcomponentsValues(FinalElementComponentForm,FECAddList,FECsAddList);
    
  

     add(myLabel,BorderLayout.PAGE_START);
     add(allBox, BorderLayout.CENTER);
     add(FinalElementComponentForm,BorderLayout.PAGE_END);
        }
    /**
         * @return the FECAddList
         */
        public JList<String> getFECAddList() {
            return FECAddList;
        }

        /**
         * @param FECAddList the FECAddList to set
         */
        public void setFECAddList(JList<String> FECAddList) {
            this.FECAddList = FECAddList;
        }
   
   } 
   
   class ListTransferHandler extends TransferHandler {
  @Override
  public int getSourceActions(JComponent c) {
    return TransferHandler.COPY_OR_MOVE;
  }
  @Override
  protected Transferable createTransferable(JComponent source) {
    JList<String> sourceList = (JList<String>) source;
    String data = sourceList.getSelectedValue();
    Transferable t = new StringSelection(data);
    return t;
  }

  @Override
  protected void exportDone(JComponent source, Transferable data, int action) {
    @SuppressWarnings("unchecked")
    JList<String> sourceList = (JList<String>) source;
    String movedItem = sourceList.getSelectedValue();
    if (action == TransferHandler.MOVE) {
      DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList
          .getModel();
     // listModel.removeElement(movedItem); commented by bala to stop remove after drag
    }
  }
  @Override
  public boolean canImport(TransferHandler.TransferSupport support) {
    if (!support.isDrop()) {
      return false;
    }
    return support.isDataFlavorSupported(DataFlavor.stringFlavor);
  }
  @Override
  public boolean importData(TransferHandler.TransferSupport support) {
    if (!this.canImport(support)) {
      return false;
    }
    Transferable t = support.getTransferable();
    String data = null;
    try {
      data = (String) t.getTransferData(DataFlavor.stringFlavor);
      if (data == null) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    JList.DropLocation dropLocation = (JList.DropLocation) support
        .getDropLocation();
    int dropIndex = dropLocation.getIndex();
    JList<String> targetList = (JList<String>) support.getComponent();
    DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
        .getModel();
  /*  if (dropLocation.isInsert()) {
      listModel.add(dropIndex, data);
    } else {   commented by bala to stop re add to the main list
      listModel.set(dropIndex, data);
    }*/
    return true;
  }
}

  class ListTransferHandler1 extends TransferHandler {
  @Override
  public int getSourceActions(JComponent c) {
    return TransferHandler.COPY_OR_MOVE;
  }
  @Override
  protected Transferable createTransferable(JComponent source) {
    JList<String> sourceList = (JList<String>) source;
    String data = sourceList.getSelectedValue();
    Transferable t = new StringSelection(data);
    return t;
  }

  @Override
  protected void exportDone(JComponent source, Transferable data, int action) {
    @SuppressWarnings("unchecked")
    JList<String> sourceList = (JList<String>) source;
    String movedItem = sourceList.getSelectedValue();
    if (action == TransferHandler.MOVE) {
         System.out.println("movedItem "+movedItem+ " index "+sourceList.getSelectedIndex());
        removeComponents(sourceList.getSelectedIndex(), movedItem);
        
        
      DefaultListModel<String> listModel = (DefaultListModel<String>) sourceList.getModel();
      listModel.removeElement(movedItem); 
      
       
    }
  }
  @Override
  public boolean canImport(TransferHandler.TransferSupport support) {
    if (!support.isDrop()) {
      return false;
    }
    return support.isDataFlavorSupported(DataFlavor.stringFlavor);
  }
  @Override
  public boolean importData(TransferHandler.TransferSupport support) {
    if (!this.canImport(support)) {
      return false;
    }
    Transferable t = support.getTransferable();
    String data = null;
    try {
      data = (String) t.getTransferData(DataFlavor.stringFlavor);
      if (data == null) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    JList.DropLocation dropLocation = (JList.DropLocation) support
        .getDropLocation();
    int dropIndex = dropLocation.getIndex();
    JList<String> targetList = (JList<String>) support.getComponent();
    DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
        .getModel();
    if (dropLocation.isInsert()) {
      listModel.add(dropIndex, data);
    } else {
      listModel.set(dropIndex, data);
    }
    
      System.out.println("dropIndex "+dropIndex+"data "+data);
    //((DefaultListModel<Components>) SensorComponentsAddList.getModel()).add(dropIndex, component);
      AddComponents(dropIndex, data);
    
    return true;
  }
}

        
    
}

