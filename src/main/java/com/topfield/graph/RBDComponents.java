/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;


import com.topfield.calculators.rbd.RBDCalculator;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SpghdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SpghdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.datamodel.Item;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Spghdr;
import com.topfield.modal.Standards;
import com.topfield.modal.SubProductGroup;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.user.UserInfo;
import com.topfield.utilities.AlternateCellRenderer;
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
import javax.swing.plaf.basic.BasicScrollBarUI;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class RBDComponents extends JPanel {

    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();   
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private SubProductComponentsDao spcDao = new SubProductComponentsDaoImpl();

    private SpghdrDao spghdrDao = new SpghdrDaoImpl();


    private List<Spghdr> dataSingleCom;
    private JTable projects;
    private RBDCalculator RBDparent;


    public RBDComponents(Object parents, String calculator) {

        setLayout(new BorderLayout());

        JLabel label = new JLabel();
        label.setText("RBD of Components");
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        dataSingleCom = spghdrDao.getAllSpghdrByPro(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername());
        String[] splitData;
        String[] columnNames = {"No", "Id","Descriptions", "MPG Name", "SPG Name", "Standards", "Comments", "Created Date", ""};
        String[][] data = new String[dataSingleCom.size()][columnNames.length + 1];

        if (calculator.contentEquals("RBD")) {
            RBDparent = (RBDCalculator) parents;
        } 

        PanelButton addButton = new PanelButton("Add " + calculator);
        //addButton.setBackground(new Color(214, 219, 223));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                SpgPopup spgPopup = new SpgPopup("Add", calculator);
                Object[] options1 = {"Add", "Cancel"};

                int result = JOptionPane.showOptionDialog(null, spgPopup, "RBD Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {

                    if (spghdrDao.getAllSpghdrByProSpg(UserInfo.getInstance().getProjectNo(), calculator, UserInfo.getInstance().getuser().getUsername(),spgPopup.getSPG().getId()).size() == 0) {

                        spghdrDao.save(getSpghdr(-1,spgPopup.getDescriptions(), spgDao.getSPGById(spgPopup.getSPG().getId()), stdDao.findById(spgPopup.getStandards().getId()), spgPopup.getComments(), calculator));
                        RBDparent.refreshPage();

                    } else {

                        JOptionPane.showMessageDialog(null, calculator + " of \"" + spgPopup.getSPG().getDescription() + "\" already exist in the Project ");
                    }

                }

            
            }
        });

        PanelButton editButton = new PanelButton("Edit " + calculator);
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                int selRow = projects.getSelectedRow();
                MainProductGroup mpgref= null;
                SubProductGroup spgref= null;

                if (selRow >= 0) {

                    System.out.println("dataSingleCom.get(selRow).getDescriptions() - "+dataSingleCom.get(selRow).getStandard().getCode());
                    
                    SpgPopup spgPopup = new SpgPopup("Edit", calculator);  
                    spgPopup.setDescriptions(dataSingleCom.get(selRow).getDescriptions());
                    spgPopup.setComments(dataSingleCom.get(selRow).getComments());
                     spgref = dataSingleCom.get(selRow).getSpgid();
                    mpgref = spgref.getMpgRef();
                    spgPopup.setMPG(new Item(mpgref.getMpgId(), mpgref.getMpgDescription()));
                    spgPopup.setSPG(new Item(spgref.getSpgId(), spgref.getSpgDescription()));
                    spgPopup.setStandards(new Item(dataSingleCom.get(selRow).getStandard().getId(), dataSingleCom.get(selRow).getStandard().getCode()));
                    
                    
                    Object[] options1 = {"Edit", "Cancel"};

                    int result = JOptionPane.showOptionDialog(null, spgPopup, "Select to insert components",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {

                        spghdrDao.update(getSpghdr(dataSingleCom.get(selRow).getSpghdrid(),spgPopup.getDescriptions(), spgDao.getSPGById(spgPopup.getSPG().getId()), stdDao.findById(spgPopup.getStandards().getId()), spgPopup.getComments(), calculator));
                        RBDparent.refreshPage();

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
                       spghdrDao.delete(dataSingleCom.get(selRow).getSpghdrid());
                       RBDparent.refreshPage();

                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to delete");
                }

            }
        });

        PanelButton refreashButton = new PanelButton("Refresh");
        refreashButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                RBDparent.refreshPage();
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
            data[i][1] = dataSingleCom.get(i).getSpghdrid() + "";
            data[i][2] = dataSingleCom.get(i).getDescriptions();
            data[i][3] = dataSingleCom.get(i).getSpgid().getMpgRef().getMpgDescription();
            data[i][4] = dataSingleCom.get(i).getSpgid().getSpgDescription();
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

               // RBDparent.setComponentAt(1, new RBDDrawer(dataSingleCom.get(compId)));
                //RBDparent.setSelectedIndex(1); 
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

        projects.getTableHeader().setForeground(FrameSettings.getButtonColor());
        projects.getTableHeader().setFont(projects.getTableHeader().getFont().deriveFont(Font.BOLD));

        add(center, BorderLayout.CENTER);

    }



    public Spghdr getSpghdr(int spgId,String description,SubProductGroup spg, Standards std, String comments, String calculator) {
        Spghdr s = null;
        if (spgId>=0) {
            s = spghdrDao.findById(spgId);
        } else {
            s= new Spghdr();
            s.setProsettings(new JSONObject("{\"line\": {\"color\": \"-16777216\", \"style\": \"11\"}, \"text\": {\"size\": 20, \"color\": \"-16777114\"}, \"vertex\": {\"bgcolor\": \"-16763956\", \"fontcolor\": \"-16751053\", \"bordercolor\": \"-16737895\"}, \"connector\": {\"color\": \"-16777216\", \"border\": \"1\"}, \"vertexgroup\": {\"color\": \"-16737793\", \"textcolor\": \"-16737844\"}}"));
            s.setProjectId(proDao.findById(UserInfo.getInstance().getProjectNo()));
            s.setUser(userDao.findById(UserInfo.getInstance().getuser().getUsername()));
            s.setCreateddate(new Date());
        }

        s.setDescriptions(description);
        s.setComments(comments);
        s.setStandard(std);
        s.setSpgid(spg);
        s.setCalname(calculator);
         return s;
    }




    
    

}

