/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.topfield.calculators.calcfile.CalcCalculator;
import com.topfield.calculators.calcfile.CalcCommonService;
import com.topfield.calculators.calcfile.CalcFile;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.fmeca.FmecaMil1629A;
import com.topfield.dao.CalchdrDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.CalchdrDaoImpl;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Standards;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.user.UserInfo;
import com.topfield.utilities.AlternateCellRenderer;
import com.topfield.view.popup.CalcHdrPopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

/**
 *
 * @author Murali
 */
public class CalcHdrComponents extends JPanel {

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private FMEADao fmeaDao = new FMEADaoImpl();
    private CalchdrDao calchdrDao = new CalchdrDaoImpl();
    private FMECADao fMECADao = new FMECADaoImpl();
    private MainProductGroupDao mainProDao = new MainProductGroupDaoImpl();
    private MpgHdrDao mpgHdrDao = new MpgHdrDaoImpl();
    //private List<Object[]> dataSingleCom = fmeaDao.getSummaryFMEAByMPG(UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername());
    private List<Calchdr> dataSingleCom;
    private JTable projects;
    private UserDao userDao = new UserDaoImpl();

    private FMEACalculator parFmea;
    private FMECACalculator parFmeca;

    public CalcHdrComponents(Object parents, String calculator,String mpgTypes[]) {

        setLayout(new BorderLayout());

        
        if (calculator.contentEquals("FMEA")) {
            parFmea = (FMEACalculator) parents;
        } else if (calculator.contentEquals("FMECA")) {
            parFmeca = (FMECACalculator) parents;
        } 

        dataSingleCom = calchdrDao.getSummaryCalchdrByPro(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername());
        String[] splitData;
        String[] columnNames = {"No", "Id",calculator+" Name","MPG Type", "CMM Id","CMM Name", "Standards", /*"Row Count", "Severity/RPZ",*/"Comments", ""};
        String[][] data = new String[dataSingleCom.size()][columnNames.length + 1];

       // columnNames[7] = getResultName(calculator);


        PanelButton addButton = new PanelButton("Add " + calculator);
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                CalcHdrPopup calcHdrPopup = new CalcHdrPopup("Add", calculator,mpgTypes);
                Object[] options1 = {"Add", "Cancel"};

                int result = JOptionPane.showOptionDialog(null, calcHdrPopup, calculator+" Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    if(calcHdrPopup.getCalchdr() == null && calcHdrPopup.getCalchdrId() != -1){
                        JOptionPane.showMessageDialog(null, "Calc File doesn't found. Please create calcfile for the project and Try Again");
                    
                    }else /*if (calchdrDao.getAllCalchdrByMPGOne(calcHdrPopup.getCalchdr().getMpghdrid(), calculator, UserInfo.getInstance().getProjectNo(), UserInfo.getInstance().getuser().getUsername()).size() == 0) */{

                        Calchdr hdrtop = getCalcHdr(calcHdrPopup.getDescription(),calcHdrPopup.getCalchdrId(), calcHdrPopup.getStandards(),calcHdrPopup.getMPGType(),calcHdrPopup.getMpgId(), calcHdrPopup.getComments(), calculator);
                        calchdrDao.save(hdrtop);

                        refreshPage(calculator);

                    } /*else {

                        JOptionPane.showMessageDialog(null, calculator + " of \"" + calcHdrPopup.getCalchdr().getDescriptions() + "\" already exist in the Project ");
                    }*/

                }

           
            }
        });

        PanelButton editButton = new PanelButton("Edit " + calculator);
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();

                if (selRow >= 0) {

                    CalcHdrPopup calcHdrPopup = new CalcHdrPopup("Edit", calculator,mpgTypes);
                    calcHdrPopup.setDescription(dataSingleCom.get(selRow).getDescriptions());
                    calcHdrPopup.setMpgHdrObj(dataSingleCom.get(selRow).getMpghdr());
                    calcHdrPopup.setStandardsObj(dataSingleCom.get(selRow).getStandard());
                    calcHdrPopup.setMPGType(dataSingleCom.get(selRow).getCalcType());
                    calcHdrPopup.setComments(dataSingleCom.get(selRow).getComments());
                    Object[] options1 = {"Save", "Cancel"};

                    int result = JOptionPane.showOptionDialog(null, calcHdrPopup, calculator+" Settings",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {

                        Calchdr hdrtop = dataSingleCom.get(selRow);
                        hdrtop.setDescriptions(calcHdrPopup.getDescription());
                        hdrtop.setStandard(calcHdrPopup.getStandards());
                        hdrtop.setCalcType(calcHdrPopup.getMPGType());
                        hdrtop.setCalname(calculator);
                        //hdrtop.setApproach(selRow);
                        hdrtop.setComments(calcHdrPopup.getComments());
                        hdrtop.setMpghdr(calcHdrPopup.getCalchdr());
                        calchdrDao.Update(hdrtop);
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

                if (selRow >= 0) {

                    
                     if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete? \n "
                            + "The operation will delete the record and the functions and components associated with it in the calculation (second) tab", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                         
                            //fmeaDao.deleteFMEAComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), Integer.parseInt(dataSingleCom.get(selRow)[0]+""));
                            calchdrDao.delete(Integer.parseInt(projects.getValueAt(selRow, 1) + ""));
                            refreshPage(calculator);
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

        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        int iteraterCount = 2;
        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = dataSingleCom.get(i).getCalchdrid()+ "";
            data[i][2] = dataSingleCom.get(i).getDescriptions();
            data[i][3] = dataSingleCom.get(i).getCalcType();
            data[i][4] = dataSingleCom.get(i).getMpghdr().getMpghdrid()+"";
            data[i][5] = dataSingleCom.get(i).getMpghdr().getDescriptions();
            data[i][6] = dataSingleCom.get(i).getStandard().getCode();
            

           /* if (dataSingleCom.get(i).getSummary() != null && !dataSingleCom.get(i).getSummary().contentEquals("") ) {
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
            data[i][7] = dataSingleCom.get(i).getComments();
            data[i][8] = "View Details";
           // System.out.println("ffffffffffffffffffffffffffff " + i);
        }

        // Column Names 
        projects = new JTable(data, columnNames);
      

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int compId = Integer.parseInt(e.getActionCommand());

                //JOptionPane.showMessageDialog(null, "Massage - "+dataSingleCom.get(compId).getCcfSubComponent().getSpgId());
                //Mpghdr fmeas = dataSingleCom.get(compId);
                //fmeas.setStandard(stdDao.findByName(projects.getValueAt(compId, 3) + ""));
                //fmeasDao.UpdateFMEAS(fmeas);
                if (calculator.contentEquals("FMEA")) {
                    parFmea.setSelectedIndex(1);
                    if ((projects.getValueAt(compId, 6) + "").contentEquals("MIL-STD1629A")) {
                        parFmea.setComponentAt(1, new FmeaMil1629A(dataSingleCom.get(compId)));
                    } else {
                        parFmea.setComponentAt(1, new FmeaRPN(dataSingleCom.get(compId)));
                    }
                } else if (calculator.contentEquals("FMECA")) {
                    parFmeca.setSelectedIndex(1);  
                    //parFmeca.setComponentAt(1, new FMECA(dataSingleCom.get(compId)));
                    parFmeca.setComponentAt(1, new FmecaMil1629A(dataSingleCom.get(compId)));        
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

    public Fmea getFmeaFinal(Mpghdr fmeaHdr, MainProductGroup mpg) {

        // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        //MainProductGroup mpgRef = mainProDao.FindByName(mpg);
        Fmea f = new Fmea();
        //f.setFmeaId();
        f.setFISubsysComponent(mpg.getMpgDescription());
        f.setFISubsysBreakdownCode(UserInfo.getInstance().getProAbbreviations() + "_" + mpg.getMpgRef());
        //f.setFmeaHeader(fmeaHdr);
        f.setComType("MainProduct");
        f.setComId(mpg.getMpgId());

        /*
                f.setFmeaComponent(mpgRef);
               f.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
               f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
               f.setCreateddate(new Date());
         */
        return f;
    }

    public Fmeca getFmecaFinal(Calchdr fmeaHdr, MainProductGroup mpg) {

        Fmeca f = new Fmeca();
        //f.setFmeaId();
        f.setFISubsysComp(mpg.getMpgDescription());
        f.setFISubsysBreakCode(UserInfo.getInstance().getProAbbreviations() + "_" + mpg.getMpgRef());
        f.setFmecaHeader(fmeaHdr);
        f.setComType("MainProduct");
        f.setComId(mpg.getMpgId());

        //f.setFmecaComponent(mainProDao.FindByName(mpg));
        //f.setFmecaproid(proDao.findById(UserInfo.getInstance().getProjectNo()));
        //f.setFmecaproid(proDao.findById(TclSession.getProjectId()));
        //f.setFmecauser(userDao.findById(TclSession.getUsername()));
        //f.setFmecaCreateddate(new Date());
        return f;
    }

    public Calchdr getCalcHdr(String des,int mpghdr, Standards std,String mpgType,int mpgId, String comments, String calculator) {
        //MainProductGroup mpgRef = mainProDao.FindByName(mpg);
        //Standards stdRef = stdDao.findByName(std);
        
        Mpghdr mpghdrRef= null;
        int appoarch =0;
        
        if (mpghdr == -1) {
            
           mpghdrRef = CalcCommonService.createCalFileHdr(des, mpgId, "Generated", calculator);
  
           appoarch =1;
        } else {

            mpghdrRef = mpgHdrDao.getFMEASById(mpghdr);
        }
        

        Calchdr f = new Calchdr();
        //f.setFmeaId();
        f.setDescriptions(des);
        f.setStandard(std);
        f.setCalcType(mpgType);
        f.setComments(comments);
        f.setMpghdr(mpghdrRef);
        f.setCalname(calculator);
        f.setApproach(appoarch);
        f.setProjectId(UserInfo.getInstance().getProject());
        f.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
        f.setCreateddate(new Date());

        return f;
    }

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

  
}
