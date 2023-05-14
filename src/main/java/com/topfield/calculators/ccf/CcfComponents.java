/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;

import com.topfield.calculators.ccf.CCFcalculator;
import com.topfield.calculators.ccf.CCFcalculatorFromProgramable;
import com.topfield.calculators.ccf.CCFMachineryStandard;
import com.topfield.calculators.ccf.CCFcalculatorFromNonProgramable;
import com.topfield.controller.BusinessException;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.controller.ComponentFunctions;
import com.topfield.dao.CCFDao;
import com.topfield.dao.CcfHdrDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.CCFDaoImpl;
import com.topfield.daoImpl.CcfHdrDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Ccf;
import com.topfield.modal.Ccfhdr;
import com.topfield.modal.Fmeca;
import com.topfield.modal.SubProductGroup;
import com.topfield.modal.TclProjects;
import com.topfield.project.ProjectDefinition;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */
public class CcfComponents extends JPanel {

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private CCFDao ccfDao = new CCFDaoImpl();
    private CcfHdrDao ccfHdrDao = new CcfHdrDaoImpl();
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    //private List<Ccf> dataSingleCom = ccfDao.getFinalCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() , "Final");
    private List<Ccfhdr> dataSingleCom = ccfHdrDao.getAllCcfhdrByUserAndPro(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getProjectNo());
    private JTable projects;
    private ProjectDefinition projectDefinition = new ProjectDefinition(false);

    public CcfComponents(CCFcalculator parent) {

        setLayout(new BorderLayout());

        JLabel label = new JLabel();
        label.setText("CCF of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        String[] splitData;
        String[] columnNames = {"No", "Component Name", "Component Type ", "A Score", "B Score", "Raw Score", "Beta Factor", "Diagnostic Coverage", ""};
        String[][] data = new String[dataSingleCom.size()][columnNames.length + 1];

        PanelButton addButton = new PanelButton("Add CCF");
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int n = 0;
                Object[] options = {"Programmable", "Non programmable", "Machinery Standard"};
                Object[] options1 = {"ADD", "Cancel"};

                int result = JOptionPane.showOptionDialog(null, projectDefinition, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {
                    
                    try {
                        
                        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    //String select[] = projectDefinition.getSelections().split("\\#");
                    String select[] = projectDefinition.getSelectionsAsArray();
                    // System.out.println("****************************************8 "+projectDefinition.getSelections());

                    n = JOptionPane.showOptionDialog(null, "Please select component type", "Component type",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, //do not use a custom Icon
                            options, options[0]);

                    for (String string : select) {

                        System.out.println("**** String - " + string);
                        //ccfDao.Save(getCCFFinal(string, options[n]+""));
                        if (string != null && !string.contentEquals("")) {
                            ccfHdrDao.Save(getCcfhdr(string, options[n] + ""));
                        } else {
                        
                            JOptionPane.showMessageDialog(null, "Your selections does not have valid subproductgroup "); 
                        }
                    }

                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally{
                        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        parent.refreshPage();
                    
                    }

                    

                } else if (result == JOptionPane.NO_OPTION) {

                } else {
                    //  System.out.println("Cancelled");
                }

                //  JOptionPane.showMessageDialog(null, "Please add your details now ");      
            }
        });

        PanelButton editButton = new PanelButton("Edit CCF");
        //addButton.setBackground(new Color(214, 219, 223));
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();
                Object[] options1 = {"Update", "Cancel"};
                Ccfhdr ccfhdr = dataSingleCom.get(selRow);

                int result = JOptionPane.showOptionDialog(null, projectDefinition, "Select to insert components",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    //String select[] = projectDefinition.getSelections().split("\\#");

                    try {

                        String select[] = projectDefinition.getSelectionsAsArray();
                        // System.out.println("****************************************8 "+projectDefinition.getSelections());

                        if (select.length == 1) {

                            if (select[0] != null && !select[0].contentEquals("")) {
                                ccfhdr.setCcfSubComponent(subComDao.getSPGByName(select[0]));
                                ccfHdrDao.Update(ccfhdr);

                                //InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                                parent.refreshPage();
                            } else {

                                JOptionPane.showMessageDialog(null, "Your selections does not have valid subproductgroup ");
                            }

                        } else {

                            JOptionPane.showMessageDialog(null, "Please select one component");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                } else if (result == JOptionPane.NO_OPTION) {

                } else {
                    //  System.out.println("Cancelled");
                }

                //  JOptionPane.showMessageDialog(null, "Please add your details now ");      
            }
        });

        PanelButton removeButton = new PanelButton("Remove CCF");
        //removeButton.setBackground(new Color(214, 219, 223));
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();

                if (selRow >= 0) {
                    // ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), dataSingleCom.get(selRow).getMainCategory(),  dataSingleCom.get(selRow).getCcfSubComponent().getSpgId());
                    
                     if (BusinessException.showBusinessConformation("Do you want to delete this CCF? ",true)== JOptionPane.YES_OPTION){
                            ccfHdrDao.Remove(dataSingleCom.get(selRow).getCcfhdrId());
                            parent.refreshPage();
                    
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
        //refreashButton.setBackground(new Color(214, 219, 223));
        refreashButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                parent.refreshPage();
            }
        });

        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);

        for (int i = 0; i < dataSingleCom.size(); i++) {
            data[i][0] = (i + 1) + "";
            data[i][1] = dataSingleCom.get(i).getCcfSubComponent().getSpgDescription();
            data[i][2] = dataSingleCom.get(i).getMainCategory();
            data[i][3] = dataSingleCom.get(i).getAscore() + "";
            data[i][4] = dataSingleCom.get(i).getBscore() + "";
            data[i][5] = dataSingleCom.get(i).getRowscore() + "";
            data[i][6] = dataSingleCom.get(i).getBetaFactor() + "";
            data[i][7] = dataSingleCom.get(i).getDiagnosticCoverage() + "";

            /*splitData = dataSingleCom.get(i).getAchievement().split("\\#");
            
            for (int j = 0; j < (splitData.length); j++) {
               data[i][(j+3)] =splitData[j];
            }
            data[i][7] =data[i][6];
            data[i][6] =dataSingleCom.get(i).getAchievementPercentage();*/
            data[i][8] = "View Details";
        }

        // Column Names 
        projects = new JTable(data, columnNames);
        label.setBorder(new EmptyBorder(10, 10, 20, 0));

        Action delete = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int compId = Integer.parseInt(e.getActionCommand());

                //JOptionPane.showMessageDialog(null, "Massage - "+dataSingleCom.get(compId).getCcfSubComponent().getSpgId());
                if (dataSingleCom.get(compId).getMainCategory().equals("Programmable")) {
                    parent.setSelectedIndex(1);
                    parent.setTitleAt(1, "Programable (Betaplus)");
                    //parent.setComponentAt(1, new CCFcalculatorFromProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId(),projects.getValueAt(compId, 7)+"" ));

                    //parent.setComponentAt(1, new CCFcalculatorFromProgramable(dataSingleCom.get(compId)));
                } else if (dataSingleCom.get(Integer.parseInt(e.getActionCommand())).getMainCategory().equals("Non programmable")) {
                    //parent.setSelectedIndex(2);
                    //parent.setComponentAt(2, new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId()));
                    parent.setSelectedIndex(1);
                    parent.setTitleAt(1, "NonProgramable (Betaplus)");
                    //parent.setComponentAt(1, new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId).getCcfSubComponent().getSpgId(),projects.getValueAt(compId, 7)+"" ));
                    //parent.setComponentAt(1, new CCFcalculatorFromNonProgramable(dataSingleCom.get(compId)));
                } else if (dataSingleCom.get(Integer.parseInt(e.getActionCommand())).getMainCategory().equals("Machinery Standard")) {
                    //parent.setSelectedIndex(3);
                    //parent.setComponentAt(3, new CCFMachineryStandard(dataSingleCom.get(compId).getCcfSubComponent().getSpgId()));
                    parent.setSelectedIndex(1);
                    parent.setTitleAt(1, "Machinery Standard");
                    //parent.setComponentAt(1, new CCFMachineryStandard(dataSingleCom.get(compId)));
                }

            }

        };

        for (int i = 0; i < projects.getColumnCount(); i++) {

            projects.setDefaultRenderer(projects.getColumnClass(i), new WineCellRenderer());

        }

        ButtonColumn buttonColumn = new ButtonColumn(projects, delete, 8);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        add(buttonPanel, BorderLayout.NORTH);
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(projects), BorderLayout.CENTER);

        projects.getTableHeader().setForeground(FrameSettings.getButtonColor());
        projects.getTableHeader().setFont(projects.getTableHeader().getFont().deriveFont(Font.BOLD));
    }

    public Ccfhdr getCcfhdr(String spg, String ComType) {

        // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        Ccfhdr ccfhdr = new Ccfhdr();
        ccfhdr.setMainCategory(ComType);
        ccfhdr.setDescription("Final");
        ccfhdr.setComments("");
        ccfhdr.setCcfSubComponent(subComDao.getSPGByName(spg));
        ccfhdr.setUser(UserInfo.getInstance().getuser());
        ccfhdr.setCreateddate(UserInfo.getInstance().getDate());
        ccfhdr.setProjectId(UserInfo.getInstance().getTclProject());

        return ccfhdr;
    }

}
