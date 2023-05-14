/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.rbd;

import com.topfield.calculators.calcfile.CalcCommonService;
import com.topfield.calculators.ccf.CCFMachineryStandard;
import com.topfield.calculators.ccf.CCFcalculator;
import com.topfield.calculators.ccf.CCFcalculatorFromNonProgramable;
import com.topfield.calculators.ccf.CCFcalculatorFromProgramable;
import com.topfield.calculators.faulttree.FaultDataCrud;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.calculators.faulttree.FaultTreeData;
import com.topfield.calculators.faulttree.FaultTreeGraph3;
import com.topfield.calculators.faulttree.JgraphDrawer;
import com.topfield.dao.CalcFunctionsDao;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SpfhdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalcFunctionsDaoImpl;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.graph.RBDDrawer;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Spfhdr;
import com.topfield.modal.Spghdr;
import com.topfield.modal.Standards;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.user.UserInfo;
import com.topfield.utilities.AlternateCellRenderer;
import com.topfield.view.popup.SpfPopup;
import com.topfield.view.popup.SpgPopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class SPFComponents extends JPanel {

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();   
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();

    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private CalcfileDao CalcfileDao = new CalcfileDaoImpl();
    private CalcFunctionsDao calcFunDao = new CalcFunctionsDaoImpl();
    private FaultDataCrud faultDataCrud = new FaultDataCrud();

    private List<Spfhdr> dataSingleCom;
    private JTable projects;
    private RBDCalculator RBDparent;
    private FaultTreeCalculator FTparent;
    private CCFcalculator CCFparent;


    public SPFComponents(Object parents, String calculator) {

        setLayout(new BorderLayout());

        JLabel label = new JLabel();
        label.setText("RBD of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        dataSingleCom = spfhdrDao.getAllSpfhdrByPro(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername());
        String[] splitData;
        String[] columnNames = {"No", "Id",calculator+" Name", "CMM", "Function Name", "Standards", "Comments", "Created Date", ""};
        String[][] data = new String[dataSingleCom.size()][columnNames.length + 1];
       

        if (getCalculatorId(calculator)==1) {
            RBDparent = (RBDCalculator) parents;
        } else if (getCalculatorId(calculator)==2) {
            FTparent = (FaultTreeCalculator) parents;
        } else if (getCalculatorId(calculator)==3) {
            CCFparent = (CCFcalculator) parents;
        } 

        PanelButton addButton = new PanelButton("Add " + calculator);
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                SpfPopup spfPopup = new SpfPopup(false, calculator);
                Object[] options1 = {"Add", "Cancel"};
                CalcFunctions calcFunctions =null;
                int spfhdrId;
                int approach =0;

                int result = JOptionPane.showOptionDialog(null, spfPopup, calculator+" Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    if (spfPopup.getSPF()== null && spfPopup.getCalcFile().getId() != -1){
                        
                        JOptionPane.showMessageDialog(null, "Calc File doesn't found. Please create calcfile for the project and Try Again");
                            
                    }else /*if (spfhdrDao.getAllSpfhdrByProFun(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername(),spfPopup.getSPF().getCalcFunId()).size() == 0)*/ {

                            if (spfPopup.getCalcFile().getId() == -1) {
                              calcFunctions  = creatrCalcFunctions(spfPopup.getDescriptions(), spfPopup.getMpgId(), calculator, spfPopup.getFuntionName());
                              approach=1;
                            } else {
                               calcFunctions = spfPopup.getSPF();
                               approach=0;
                            }
                            
                            spfhdrId =spfhdrDao.save(getSpfhdr(-1,spfPopup.getDescriptions(),calcFunctions, stdDao.findById(spfPopup.getStandards().getId()), spfPopup.getComments(), calculator,approach));
                        
                            if (getCalculatorId(calculator)==2) {
                                faultDataCrud.addIntialFaultData(spfhdrDao.findById(spfhdrId));
                            } 

                            refreshPage(calculator);
                            
                       
                        

                    } /*else {

                        JOptionPane.showMessageDialog(null, calculator + " of \"" + spfPopup.getSPF().getCalcFunction() + "\" already exist in the Project ");
                    }*/

                }

            
            }
        });

        PanelButton editButton = new PanelButton("Edit " + calculator);
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();
                Mpghdr mpgref= null;
                CalcFunctions spfref= null;
                //List<Spfhdr> check = null;

                if (selRow >= 0) {

                    //System.out.println("dataSingleCom.get(selRow).getDescriptions() - "+dataSingleCom.get(selRow).getStandard().getCode());
                    
                    SpfPopup spfPopup = new SpfPopup(true, calculator);  
                    spfPopup.setDescriptions(dataSingleCom.get(selRow).getDescriptions());
                    spfPopup.setComments(dataSingleCom.get(selRow).getComments());
                    spfref = dataSingleCom.get(selRow).getSpfid();
                    mpgref = spfref.getMpgheader();
                    spfPopup.setCalcFile(new Item(mpgref.getMpghdrid(), mpgref.getDescriptions()));
                    spfPopup.setSPF(spfref);
                    spfPopup.setStandards(new Item(dataSingleCom.get(selRow).getStandard().getId(), dataSingleCom.get(selRow).getStandard().getCode()));
                    
                    
                    Object[] options1 = {"Save", "Cancel"};

                    int result = JOptionPane.showOptionDialog(null, spfPopup, calculator+" Settings",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                         // check =spfhdrDao.getAllSpfhdrByProFun(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername(),spfPopup.getSPF().getCalcFunId());

                          //if (check.size() == 1 && check.get(0).getSpfhdrid()==dataSingleCom.get(selRow).getSpfhdrid()) {

                                spfhdrDao.update(getSpfhdr(dataSingleCom.get(selRow).getSpfhdrid(),spfPopup.getDescriptions(), spfPopup.getSPF(), stdDao.findById(spfPopup.getStandards().getId()), spfPopup.getComments(), calculator,0));
                                refreshPage(calculator);

                            /*} else {

                                JOptionPane.showMessageDialog(null, calculator + " of \"" + spfPopup.getSPF().getCalcFunction() + "\" already exist in the Project ");
                            }*/


                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to edit");
                }

            }
        });

        PanelButton removeButton = new PanelButton("Remove " + calculator);
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();

                if (selRow >= 0) {
                    
                     if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete? \n "
                            + "The operation will delete the record and the functions and components associated with it in the calculation (second) tab", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                         
                       spfhdrDao.delete(dataSingleCom.get(selRow).getSpfhdrid());
                       refreshPage(calculator);
                         
                     }


                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to delete");
                }

            }
        });

        PanelButton refreashButton = new PanelButton("Refresh");
        refreashButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                refreshPage(calculator);
            }
        });

        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        int iteraterCount = 2;
        
        
        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = dataSingleCom.get(i).getSpfhdrid() + "";
            data[i][2] = dataSingleCom.get(i).getDescriptions();
            data[i][3] = dataSingleCom.get(i).getSpfid().getMpgheader().getDescriptions();
            data[i][4] = dataSingleCom.get(i).getSpfid().getCalcFunction();
            data[i][5] = dataSingleCom.get(i).getStandard().getCode();
            data[i][6] = dataSingleCom.get(i).getComments();
            data[i][7] = dataSingleCom.get(i).getCreateddate().toString();
            data[i][8] = "View Details";
        }

        // Column Names 
        projects = new JTable(data, columnNames);
        label.setBorder(new EmptyBorder(10, 10, 20, 0));

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int compId = Integer.parseInt(e.getActionCommand());

                if (getCalculatorId(calculator)==1) {
                    RBDparent.setComponentAt(1, new RBDDrawer(dataSingleCom.get(compId)));
                    RBDparent.setSelectedIndex(1);
                } else if (getCalculatorId(calculator)==2) {
                    
                    /* Below code Effececy check */
                    //FTparent.setComponentAt(1, new FaultTreeData(FTparent,dataSingleCom.get(compId)));
                   // FTparent.setComponentAt(2, new FaultTreeGraph3(FTparent,dataSingleCom.get(compId)));
                    //FTparent.setComponentAt(1, new FaultTreeData(FTparent,dataSingleCom.get(compId)));
                    
                    FTparent.setComponentAt(1, new JgraphDrawer(dataSingleCom.get(compId)));
                    FTparent.setComponentAt(2, new FaultTreeData(FTparent,dataSingleCom.get(compId)));
                    FTparent.setSelectedIndex(1);
                } else if (getCalculatorId(calculator)==3) {
                    
                    setCCFCalculator(CCFparent, dataSingleCom, compId);
                }
 
            }

        };

        for (int i = 0; i < projects.getColumnCount(); i++) {

            projects.setDefaultRenderer(projects.getColumnClass(i), new AlternateCellRenderer());

        }

        //Fiddle with the Sport column's cell editors/renderers.
        //setUpSportColumn(projects, projects.getColumnModel().getColumn(3));
        ButtonColumn buttonColumn = new ButtonColumn(projects, delete, 8);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        add(buttonPanel, BorderLayout.NORTH);
        //add(new JScrollPane(projects),BorderLayout.CENTER); 

        JScrollPane center = new JScrollPane(projects);

        center.getVerticalScrollBar().setUnitIncrement(16);
        center.getHorizontalScrollBar().setUnitIncrement(35);
        center.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 15));
        center.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(33, 97, 140);
            }
        });
        center.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(33, 97, 140);
            }
        });
        
        
        projects.getTableHeader().setOpaque(true);
        projects.setOpaque(true);
        projects.getTableHeader().setForeground(FrameSettings.getButtonColor());
        projects.getTableHeader().setFont(projects.getTableHeader().getFont().deriveFont(Font.BOLD));
        projects.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        projects.setGridColor(Color.LIGHT_GRAY);

        add(center, BorderLayout.CENTER);

    }



    public Spfhdr getSpfhdr(int spfId,String description,CalcFunctions spg, Standards std, String comments, String calculator,int approach) {
        Spfhdr s = null;
        
        if (spfId>=0) {
            s = spfhdrDao.findById(spfId);
        } else {
            s= new Spfhdr();
            
                if (getCalculatorId(calculator)==1) {
                    s.setProsettings(new JSONObject("{\"line\": {\"color\": \"-16777216\", \"style\": \"11\"}, \"text\": {\"size\": 20, \"color\": \"-16777114\"}, \"vertex\": {\"bgcolor\": \"-16763956\", \"fontcolor\": \"-16751053\", \"bordercolor\": \"-16737895\"}, \"connector\": {\"color\": \"-16777216\", \"border\": \"1\"}, \"vertexgroup\": {\"color\": \"-16737793\", \"textcolor\": \"-16737844\"}}"));
                } else if (getCalculatorId(calculator)==2) {
                    s.setProsettings(new JSONObject("{\"results\": {\"probability\": \"6E-9\", \"frequency\": \"6E-9\"}}"));
                } else if (getCalculatorId(calculator)==3) {
                    s.setProsettings(new JSONObject("{\"results\": {\"ascore\": \"15.0\", \"bscore\": \"24.0\", \"rowscore\": \"924.0\", \"betaFactor\": \"0.1\", \"diagnostic\": \"60.0\"}}"));
                } 
            

            s.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
            s.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
            s.setCreateddate(new Date());
            s.setApproach(approach);
        }

        s.setDescriptions(description);
        s.setComments(comments);
        s.setStandard(std);
        s.setSpfid(spg);
        s.setCalname(calculator);
        
        return s;
    }
    
    public void refreshPage(String calculator){
    
        if (getCalculatorId(calculator)==1) {
            RBDparent.refreshPage();
        } else if (getCalculatorId(calculator)==2) {
            FTparent.refreshPage();
        } else if (getCalculatorId(calculator)==3) {
            CCFparent.refreshPage();
        } 
    
    }
    
    public int getCalculatorId(String calculator){
    int res=0;
    
        if (calculator.contentEquals("RBD")) {
            res=1;
        } else if (calculator.contentEquals("FTA")) {
            res=2;
        } else if (calculator.contentEquals("CCF")) {
            res=3;
        } 
    
    return res;
    }
    
    public void setCCFCalculator(CCFcalculator parent,List<Spfhdr> dataSingleCom, int compId){
    
            if (dataSingleCom.get(compId).getStandard().getId()==9) {
               parent.setSelectedIndex(1);
               parent.setTitleAt(1, "Programable (Betaplus)");
               //parent.setComponentAt(1, new CCFcalculatorFromProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId(),projects.getValueAt(compId, 7)+"" ));

               parent.setComponentAt(1, new JScroll(new CCFcalculatorFromProgramable(dataSingleCom.get(compId))));
           } else if (dataSingleCom.get(compId).getStandard().getId()==10) {
               //parent.setSelectedIndex(2);
               //parent.setComponentAt(2, new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId()));
               parent.setSelectedIndex(1);
               parent.setTitleAt(1, "NonProgramable (Betaplus)");
               //parent.setComponentAt(1, new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId(),projects.getValueAt(compId, 7)+"" ));
               parent.setComponentAt(1, new JScroll(new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId))));
           } else if (dataSingleCom.get(compId).getStandard().getId()==8) {
               //parent.setSelectedIndex(3);
               //parent.setComponentAt(3, new CCFMachineryStandard(dataSingleCom.get(compId).getCcfSubComponent().getSpgId()));
               parent.setSelectedIndex(1);
               parent.setTitleAt(1, "Machinery Standard");
               parent.setComponentAt(1, new JScroll(new CCFMachineryStandard(dataSingleCom.get(compId))));
           }
    
    
    }
    
    public  CalcFunctions creatrCalcFunctions(String des,int mpgId,String calculator,String FuntionName){
     CalcFunctions calcFunctions =null;
    
     Mpghdr mpghdr =CalcCommonService.createCalFileHdr(des,
                                                       mpgId, 
                                                       "Generated", calculator);
     
     calcFunctions = CalcCommonService.createCalcFunction(-1, "", "FUN 1", FuntionName, "SIL 0", 0.0, 0.0, "Generated", mpghdr, false);
      
    
      return calcFunctions;
    } 
    
    
}

