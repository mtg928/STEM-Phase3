/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.google.gson.Gson;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.dao.FMEADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Fmea;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Standards;
import com.topfield.project.ShowSelectedItemsAsList;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.utilities.AlternateCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.ERROR;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.topfield.calculators.calcfile.CalcCalculator;
import com.topfield.calculators.calcfile.CalcFile;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.calculators.fmea.FmeaMil1629A;
import com.topfield.calculators.fmea.FmeaRPN;
import com.topfield.calculators.fmeca.FMECA;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.fmeca.FmecaMil1629A;
import com.topfield.dao.FMECADao;
import com.topfield.view.popup.FmeaHeaderPopup;
import javax.swing.JDialog;
import com.topfield.dao.MpgHdrDao;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.modal.Fmeca;
import com.topfield.view.popup.PopupTable;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class MPGComponents extends JPanel {

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private FMEADao fmeaDao = new FMEADaoImpl();
    private MpgHdrDao fmeasDao = new MpgHdrDaoImpl();
    private FMECADao fMECADao = new FMECADaoImpl();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    //private List<Object[]> dataSingleCom = fmeaDao.getSummaryFMEAByMPG(UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());
    private List<Mpghdr> dataSingleCom;
    private JTable projects;
    private UserDao userDao = new UserDaoImpl();

    private FMEACalculator parFmea;
    private FMECACalculator parFmeca;
    private CalcCalculator  parCalc;

    public MPGComponents(Object parents, String calculator,String mpgTypes[]) {

        setLayout(new BorderLayout());

        JLabel label = new JLabel();
        label.setText("CCF of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        
        if (calculator.contentEquals("FMEA")) {
            parFmea = (FMEACalculator) parents;
        } else if (calculator.contentEquals("FMECA")) {
            parFmeca = (FMECACalculator) parents;
        } else if (calculator.contentEquals("CMM")) {
            parCalc = (CalcCalculator) parents;
        }

        dataSingleCom = fmeasDao.getSummaryFMEAHERByMPG(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername());
        String[] splitData;
        String[] columnNames = {"No", "Group/Function Id","Group/Function Name","Module", "MPG", /*"Standards", "Row Count", "Severity/RPZ",*/"Comments", ""};
        String[][] data = new String[dataSingleCom.size()][columnNames.length + 1];

        //columnNames[7] = getResultName(calculator);


        PanelButton addButton = new PanelButton("Add " + calculator);
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                FmeaHeaderPopup fmeaHeaderPopup = new FmeaHeaderPopup("Add", calculator,mpgTypes);
                Object[] options1 = {"Add", "Cancel"};

                int result = JOptionPane.showOptionDialog(null, fmeaHeaderPopup.getForm(), calculator+" Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    if(fmeaHeaderPopup.getMpg() == null){
                        JOptionPane.showMessageDialog(null, "Calc File doesn't found. Please create calcfile for the project and Try Again");
                    
                    }else /*if (fmeasDao.getAllFMEAHDRByMPG(fmeaHeaderPopup.getMpg().getMpgId(), calculator, UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername()).size() == 0) */{

                        //Mpghdr hdrtop = getFmeaHdr(fmeaHeaderPopup.getDescription(),fmeaHeaderPopup.getMpg(), fmeaHeaderPopup.getStandards(),fmeaHeaderPopup.getMPGType(), fmeaHeaderPopup.getComments(), calculator);
                        //fmeasDao.saveFMEAS(hdrtop);

                        /*if (calculator.contentEquals("FMEA")) {
                            fmeaDao.saveFMEA(getFmeaFinal(hdrtop, fmeaHeaderPopup.getMpg()));
                        } else if (calculator.contentEquals("FMECA")) {
                            fMECADao.saveFMECA(getFmecaFinal(hdrtop, fmeaHeaderPopup.getMpg()));
                        }*/
                        
                        CalcCommonService.createCalFileHdr(fmeaHeaderPopup.getDescription(), fmeaHeaderPopup.getMpgId(), fmeaHeaderPopup.getComments(), calculator);
                        
                        refreshPage(calculator);

                    } /*else {

                        JOptionPane.showMessageDialog(null, calculator + " of \"" + fmeaHeaderPopup.getMpg().getMpgDescription() + "\" already exist in the Project ");
                    }*/

                }

           
            }
        });

        PanelButton editButton = new PanelButton("Edit " + calculator);
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();

                if (selRow >= 0) {

                    FmeaHeaderPopup fmeaHeaderPopup = new FmeaHeaderPopup("Edit", calculator,mpgTypes);
                    fmeaHeaderPopup.setDescription(dataSingleCom.get(selRow).getDescriptions());
                    fmeaHeaderPopup.setMpgObj(dataSingleCom.get(selRow).getFmeaComponent());
                    //fmeaHeaderPopup.setStandardsObj(dataSingleCom.get(selRow).getStandard());
                    fmeaHeaderPopup.setMPGType(dataSingleCom.get(selRow).getMpgType());
                    fmeaHeaderPopup.setComments(dataSingleCom.get(selRow).getComments());
                    Object[] options1 = {"Save", "Cancel"};

                    int result = JOptionPane.showOptionDialog(null, fmeaHeaderPopup.getForm(), "Select to insert components",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {

                        Mpghdr hdrtop = dataSingleCom.get(selRow);
                        hdrtop.setDescriptions(fmeaHeaderPopup.getDescription());
                        hdrtop.setStandard(fmeaHeaderPopup.getStandards());
                        hdrtop.setMpgType(fmeaHeaderPopup.getMPGType());
                        hdrtop.setCalname(calculator);
                        hdrtop.setComments(fmeaHeaderPopup.getComments());
                        hdrtop.setFmeaComponent(fmeaHeaderPopup.getMpg());
                        fmeasDao.UpdateFMEAS(hdrtop);
                        refreshPage(calculator);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to edit");
                }

                //model.removeRow(table.getSelectedRow());
                //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
                //  refreshPage();
            }
        });

        PanelButton removeButton = new PanelButton("Remove " + calculator);
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();
                int mpgId;

                if (selRow >= 0) {
                    //fmeaDao.deleteFMEAComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), Integer.parseInt(dataSingleCom.get(selRow)[0]+""));
                    
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete? \n "
                            + "The operation will delete the record and the functions and components associated with it in the calculation (second) tab", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        
                           mpgId = Integer.parseInt(projects.getValueAt(selRow, 1) + "");
                          
                           if (CMMValidation.checkMPGHDRChildExists(mpgId)) {
                             JOptionPane.showMessageDialog(null, "The CMM used in the App. Deleted is not allowed");
                           } else {
                               fmeasDao.deleteFMEAS(mpgId);
                               refreshPage(calculator);
                           }
                           
                    } else {
                        // no option
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to delete");
                }

                //model.removeRow(table.getSelectedRow());
                //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
                //  refreshPage();
            }
        });

        PanelButton refreashButton = new PanelButton("Refresh");
        refreashButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                refreshPage(calculator);
            }
        });
        
        PanelButton usage = new PanelButton("Usage");
        usage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                 Object[] options1 = {"OK", "Cancel"};
                 int selRow = projects.getSelectedRow();
                  int mpgId = Integer.parseInt(projects.getValueAt(selRow, 1) + "");
                 String data[][] = CMMValidation.list2DtoArray(CMMValidation.getCalcHdrList(mpgId));
 
                 PopupTable poupUp =  new PopupTable();
                 poupUp.setData(data);
                 
                 int result = JOptionPane.showOptionDialog(InternalFrameDemo.contentPanel, poupUp, "Select to insert components",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                 if (result == JOptionPane.YES_OPTION) {
                        
                    
                 }
            }
        });

        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        buttonPanel.add(usage);
        int iteraterCount = 2;
        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = dataSingleCom.get(i).getMpghdrid() + "";
            data[i][2] = dataSingleCom.get(i).getDescriptions();
            data[i][3] = dataSingleCom.get(i).getMpgType();
            data[i][4] = dataSingleCom.get(i).getFmeaComponent().getMpgDescription();
            /*data[i][5] = dataSingleCom.get(i).getStandard().getCode();
            

            if (dataSingleCom.get(i).getSummary() != null) {
                iteraterCount = 2;
                Gson gson = new Gson();
                Type setType = new TypeToken<HashSet<String>>() {
                }.getType();
                Set<String> userSet = gson.fromJson(dataSingleCom.get(i).getSummary(), setType);

                for (String string : userSet) {
                    iteraterCount--;
                    data[i][6 + iteraterCount] = string;
                }

            } else {
                data[i][6] = "";
                data[i][7] = "";
            }*/


            /* splitData = dataSingleCom.get(i).getAchievement().split("\\#");
            
            for (int j = 0; j < (splitData.length); j++) {
               data[i][(j+3)] =splitData[j];
            }
            data[i][7] =dataSingleCom.get(i).getAchievementPercentage();*/
            data[i][5] = dataSingleCom.get(i).getComments();
            data[i][6] = "View Details";
           // System.out.println("ffffffffffffffffffffffffffff " + i);
        }

        // Column Names 
        projects = new JTable(data, columnNames);
        label.setBorder(new EmptyBorder(10, 10, 20, 0));

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int compId = Integer.parseInt(e.getActionCommand());

                //JOptionPane.showMessageDialog(null, "Massage - "+dataSingleCom.get(compId).getCcfSubComponent().getSpgId());
                //Mpghdr fmeas = dataSingleCom.get(compId);
                //fmeas.setStandard(stdDao.findByName(projects.getValueAt(compId, 3) + ""));
                //fmeasDao.UpdateFMEAS(fmeas);
               /* if (calculator.contentEquals("FMEA")) {
                    parFmea.setSelectedIndex(1);
                    if ((projects.getValueAt(compId, 5) + "").contentEquals("MIL-STD1629A")) {
                        //parFmea.setComponentAt(1, new FmeaMil1629A(dataSingleCom.get(compId)));
                    } else {
                        parFmea.setComponentAt(1, new FmeaRpz(dataSingleCom.get(compId)));
                    }
                } else if (calculator.contentEquals("FMECA")) {
                    parFmeca.setSelectedIndex(1);  
                    //parFmeca.setComponentAt(1, new FMECA(dataSingleCom.get(compId)));
                    parFmeca.setComponentAt(1, new FmecaMil1629A(dataSingleCom.get(compId)));        
                }else*/ if (calculator.contentEquals("CMM")) {
                    parCalc.setSelectedIndex(1);
                    parCalc.setComponentAt(1, new CalcFile(dataSingleCom.get(compId)));
                }

            }

        };

        for (int i = 0; i < projects.getColumnCount(); i++) {

            projects.setDefaultRenderer(projects.getColumnClass(i), new AlternateCellRenderer());

        }

        //Fiddle with the Sport column's cell editors/renderers.
        //setUpSportColumn(projects, projects.getColumnModel().getColumn(3));
        ButtonColumn buttonColumn = new ButtonColumn(projects, delete, 6);
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

        projects.setOpaque(true);
        projects.getTableHeader().setOpaque(true);
        projects.getTableHeader().setForeground(FrameSettings.getButtonColor());
        projects.getTableHeader().setFont(projects.getTableHeader().getFont().deriveFont(Font.BOLD));
        projects.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        projects.setGridColor(Color.LIGHT_GRAY);
        

        add(center, BorderLayout.CENTER);

    }

    public Mpghdr getFmeaFinal(String mpg) {

        MainProductGroup mpgRef = mainProDao.FindByName(mpg);

        Mpghdr f = new Mpghdr();
        //f.setFmeaId();
        f.setDescriptions(mpgRef.getMpgDescription());
        //f.setStandard(standard);
        f.setFmeaComponent(mpgRef);
        f.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
        f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
        f.setCreateddate(new Date());

        return f;
    }

  

   /* public Mpghdr getFmeaHdr(String des,MainProductGroup mpg, Standards std,String mpgType, String comments, String calculator) {
        //MainProductGroup mpgRef = mainProDao.FindByName(mpg);
        //Standards stdRef = stdDao.findByName(std);

        Mpghdr f = new Mpghdr();
        //f.setFmeaId();
        f.setDescriptions(des);
        f.setStandard(std);
        f.setMpgType(mpgType);
        f.setComments(comments);
        f.setFmeaComponent(mpg);
        f.setCalname(calculator);
        f.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
        f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
        f.setCreateddate(new Date());

        return f;
    }*/

    public String[] getMainComponents() {
        List<MainProductGroup> main = mainProDao.getAllMPG();
        String res[] = new String[main.size()];
        int count = 0;

        for (MainProductGroup mainProductGroup : main) {

            res[count] = mainProductGroup.getMpgDescription();

            count++;
        }

        return res;
    }

    public void refreshPage(String calculator) {
        if (calculator.contentEquals("FMEA")) {
            parFmea.refreshPage();
        } else if (calculator.contentEquals("FMECA")) {
            parFmeca.refreshPage();
        }else if (calculator.contentEquals("CMM")) {
            parCalc.refreshPage();
        }

    }
    
    public String getResultName(String calculator){
     String res="";
     
        if (calculator.contentEquals("FMEA")) {
            res="Severity/RPZ";
        } else if (calculator.contentEquals("FMECA")) {
            res="Criticality";
        }else if (calculator.contentEquals("CMM")) {
            res="SIL Compliance";
        }
        
     return res;
    }

    
    public int getSelectedId(){
        int selRow = projects.getSelectedRow();
        int mpgId=-1;
        
        if (selRow > 0) {
             mpgId = Integer.parseInt(projects.getValueAt(selRow, 1) + "");
        }
        
        return mpgId;
    }
  
}
