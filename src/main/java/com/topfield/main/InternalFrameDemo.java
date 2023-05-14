/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.main;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import com.qoppa.pdfWriter.PDFPrinterJob;
import com.topfield.print.PrintUtilities;
import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.utilities.DBconnectionPool;
import com.topfield.utilities.ExcelExporter;
import com.topfield.utilities.ExcelImporter;
import com.topfield.utilities.FaultTreeReader;
import com.topfield.utilities.FileSelector;
import com.topfield.user.UserInfo;
import com.topfield.project.CreateNewProject;
import com.topfield.project.ProjectProfile;
import com.topfield.project.YourProject;
import com.topfield.reports.CCF_Report_Machine;
import com.topfield.reports.CCF_Report_NonPro;
import com.topfield.reports.CCF_Report_Pro;
import com.topfield.reports.HeatMapReport;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.GraphicsProperties;
import com.topfield.settings.JScroll;
import com.topfield.settings.UISettings;
import com.topfield.themes.MenuBarTheme;
import com.topfield.user.UserProfile;
import com.topfield.calculators.ccf.CCFMachineryStandard;
import com.topfield.calculators.ccf.CCFcalculator;
import com.topfield.calculators.ccf.CCFcalculatorFromNonProgramable;
import com.topfield.calculators.ccf.CCFcalculatorFromProgramable;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.calculators.fmea.FmeaRPN;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.controller.ExportController;
import com.topfield.controller.ImportController;
import com.topfield.calculators.fmeca.FMECA;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.print.PrintPanelToPDF;
import com.topfield.calculators.faulttree.FaultTreeData;
import com.topfield.view.panel.ContentPanel;
import com.topfield.view.panel.LeftPanel;
import com.topfield.view.panel.ToolBar;
import com.topfield.view.train.ElectronicComponents;
import com.topfield.view.train.ManageArchitecture;
import com.topfield.calculators.faulttree.FaultTreeGraph;
import com.topfield.controller.BusinessException;
import com.topfield.help.AboutUs;
import com.topfield.help.StemsData;
import com.topfield.print.PrintContantToPDF;
import com.topfield.print.PrintImageToPDF;
import com.topfield.singleton.FileSelection;
import com.topfield.user.UserPackage;
import com.topfield.utilities.BrowserURLLink;
import com.topfield.visualization.LinkMatrix;
import com.topfiled.interfaces.MyPrintable;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.lang3.ArrayUtils;

/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class InternalFrameDemo extends JFrame implements ActionListener {

    private JDesktopPane desktop;
    public static int screenWidth = 0;//1345;
    public static int screenHeight = 0; // 1000;
    public static JPanel contentPanel;
    public static InternalFrameDemo mainFrame;
    private JPanel toolBar;
    private JPanel leftBar;
    private JMenu userInfo;
    private ImportController importController = new ImportController();
    private ExportController exportController = new ExportController();

    public InternalFrameDemo() {
        super("STEMS - "+StemsData.getSoftwareName()+" "+StemsData.getSoftwareVersion());
        ImageIcon img = new ImageIcon(getClass().getResource("/coachspecs/images/icons/TCL_Logo_32x32.png"));
        setIconImage(img.getImage());

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        toolBar = new JPanel();
        toolBar.setLayout(new BorderLayout());
        toolBar.setBorder(null);
        leftBar = new JPanel();
        leftBar.setLayout(new BorderLayout());
        leftBar.setBorder(null);
        screenHeight = screenSize.width - inset * 2;
        screenWidth = screenSize.height - inset * 2;
        mainFrame = this;

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        desktop.setBackground(FrameSettings.getFrameColor());
        desktop.setLayout(new BorderLayout());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        //createFrame(); //create first "window"
        setContentPane(desktop);

        //setLookAndFeel();
        ThemeSetter();
        refresh();
        //setUserMenuTag();
        
        setCalculators(new WelcomePage(), false, false);
    }

    protected JMenuBar createMenuBar() {
        MenuBarTheme menuBar = new MenuBarTheme();

        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menuBar.add(file);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("Create new");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        file.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Open Project");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("open");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
                //Set up the second menu item.
        menuItem = new JMenuItem("Open Sample Project");
        menuItem.setMnemonic(KeyEvent.VK_J);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("sample");
        menuItem.addActionListener(this);
        file.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Import");
        menuItem.setMnemonic(KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Import");
        menuItem.addActionListener(this);
       // file.add(menuItem);

        menuItem = new JMenuItem("Export Report");
        menuItem.setMnemonic(KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Export");
        menuItem.addActionListener(this);
        file.add(menuItem);

        menuItem = new JMenuItem("Print");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Print");
        menuItem.addActionListener(this);
        file.add(menuItem);

        menuItem = new JMenuItem("Snapshot");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Snapshot");
        menuItem.addActionListener(this);
        file.add(menuItem);

        file.add(new JSeparator()); 
        
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Exit");
        menuItem.addActionListener(this);
        file.add(menuItem);

        /**
         * *****************************************************************************************
         */
        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        JMenu user = new JMenu("User Management");
        user.setMnemonic(KeyEvent.VK_U);
        menuBar.add(user);

        //Set up the first menu item.
        menuItem = new JMenuItem("User Profile");
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Profile");
        menuItem.addActionListener(this);
        user.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("User Package Details");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("UPDetails");
        menuItem.addActionListener(this);
        user.add(menuItem);

        menuItem = new JMenuItem("Software Upgrades");
        menuItem.setMnemonic(KeyEvent.VK_Y);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Ppgrade");
        menuItem.addActionListener(this);
        //user.add(menuItem);
        
        menuItem = new JMenuItem("Your Comments");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("YComments");
        menuItem.addActionListener(this);
        user.add(menuItem);
        
        user.add(new JSeparator()); 

        //Set up the second menu item.
        menuItem = new JMenuItem("Layout Settings");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("USettings");
        menuItem.addActionListener(this);
        user.add(menuItem);



        /**
         * *****************************************************************************************
         */
        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        JMenu project = new JMenu("Project Management");
        project.setMnemonic(KeyEvent.VK_M);
        menuBar.add(project);

        //Set up the first menu item.
        menuItem = new JMenuItem("Project Profile");
        menuItem.setMnemonic(KeyEvent.VK_G);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("psettings");
        menuItem.addActionListener(this);
        project.add(menuItem);
        
        //Set up the second menu item.
        menuItem = new JMenuItem("Your Projects");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("yProjects");
        menuItem.addActionListener(this);
        project.add(menuItem);
        
                //Set up the second menu item.
        menuItem = new JMenuItem("Sample Projects");
        menuItem.setMnemonic(KeyEvent.VK_J);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("sample");
        menuItem.addActionListener(this);
        project.add(menuItem);

        
        project.add(new JSeparator()); 

        //Set up the second menu item.
        menuItem = new JMenuItem("Manage Systems Architecture");
        menuItem.setMnemonic(KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("ysettings");
        menuItem.addActionListener(this);
        project.add(menuItem);

        menuItem = new JMenuItem("Manage Electronic Components(SIL)");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("PDetails");
        menuItem.addActionListener(this);
        project.add(menuItem);
        
        project.add(new JSeparator()); 

        menuItem = new JMenuItem("Calculator Linkage Matrix");
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("PLinkage");
        menuItem.addActionListener(this);
        project.add(menuItem);

        /**
         * *****************************************************************************************
         */
        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        /*JMenu layout = new JMenu("Layout Management");
        layout.setMnemonic(KeyEvent.VK_L);
        menuBar.add(layout);
 
        //Set up the first menu item.
        menuItem = new JMenuItem("Layout Profile");
        menuItem.setMnemonic(KeyEvent.VK_K);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("lsettings");
        menuItem.addActionListener(this);
        layout.add(menuItem);
 
        //Set up the second menu item.
        menuItem = new JMenuItem("Layout settings");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("lysettings");
        menuItem.addActionListener(this);
        layout.add(menuItem);
        
        //Set up the second menu item.
        menuItem = new JMenuItem("Layout Projects");
        menuItem.setMnemonic(KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("lyProjects");
        menuItem.addActionListener(this);
        layout.add(menuItem);*/
        /**
         * *****************************************************************************************
         */
        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        menuBar.add(help);

        //Set up the first menu item.
        menuItem = new JMenuItem("Help Contents");
        menuItem.setMnemonic(KeyEvent.VK_K);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("HContents");
        menuItem.addActionListener(this);
        help.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Online Help");
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("OHelp");
        menuItem.addActionListener(this);
        help.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Report Issue");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("RIssue");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        help.add(new JSeparator());

        //Set up the second menu item.
        menuItem = new JMenuItem("About");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("About");
        menuItem.addActionListener(this);
        help.add(menuItem);

        /**
         * *****************************************************************************************
         */
        menuBar.revalidate();

        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            menuBar.getMenu(i).setForeground(FrameSettings.getFontColor());
        }

        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {

        if ("new".equals(e.getActionCommand())) { //new
            mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            new CreateNewProject().displayProjectSettings();
        } else if ("open".equals(e.getActionCommand())) { //new
             setCalculators(new YourProject(false), false, true);
        } else if ("sample".equals(e.getActionCommand())) { //new
             setCalculators(new YourProject(true), false, true);
        } else if ("Import".equals(e.getActionCommand())) { //new
            importController.setImport(contentPanel);
        } else if ("Export".equals(e.getActionCommand())) { //new
            exportController.setExport(contentPanel);
        } else if ("Print".equals(e.getActionCommand())) { //new
            //PrintUtilities.printComponent(contentPanel);

          JFileChooser fc = new JFileChooser();
          fc.setAcceptAllFileFilterUsed(false);
          FileNameExtensionFilter extFilter = new FileNameExtensionFilter("PDF", "pdf");
          fc.addChoosableFileFilter(extFilter);
          
          int returnVal = fc.showSaveDialog(contentPanel);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                
            //JPanel c = (JPanel)((FaultTreeCalculator)contentPanel.getComponent(0)).getComponent(1);
            //JPanel c = (JPanel)getPrintContent(true);
            /*JFrame f = new JFrame();
            f.getContentPane().add(new JScrollPane(c));
            f.pack();*/
            
              Object print =getPrintContent(true);
                
                if (print instanceof JPanel) {
                   PrintContantToPDF.printJpanel(file, (JPanel) print);
                }else if (print instanceof BufferedImage) {
                   PrintContantToPDF.printMxGraph(file.getAbsolutePath(), (BufferedImage) print);
                } else {
                   JOptionPane.showMessageDialog(null, "Print is not supported for the App ");
                }
            }

            /*  desktop.add(contentPanel, BorderLayout.CENTER);
                desktop.revalidate();
                desktop.repaint();*/

        } else if ("Snapshot".equals(e.getActionCommand())) { //new
            snapshot();
        } else if ("Exit".equals(e.getActionCommand())) { //new
            if (JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to close this Engineering tool?", "Close Window?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                DBconnectionPool.closeSessionFactory();
                System.exit(0);
            }
        } else if ("Profile".equals(e.getActionCommand())) { //new
            setCalculators(new UserProfile(), false, true);
        } else if ("UPDetails".equals(e.getActionCommand())) { //new
            setCalculators( new UserPackage(), false,true);
        } else if ("USettings".equals(e.getActionCommand())) { //new
            setCalculators(new UISettings(), false, true);
            //JOptionPane.showMessageDialog(this,"Hello, "+e.getActionCommand());  
            // createFrame();
        } else if ("Ppgrade".equals(e.getActionCommand())) { //new
            JOptionPane.showMessageDialog(this, "Hello, " + e.getActionCommand());
            // createFrame();
        } else if ("YComments".equals(e.getActionCommand())) { //new
            BrowserURLLink.open("https://topfieldconsultancy.co.uk/clientArea/support");
            JOptionPane.showMessageDialog(this , " Your comments will be open in to the web browser "
                    + "\n Welcome to Our Client Area"
                    + "\n  Login and Proceed", "Please Wait", JOptionPane.INFORMATION_MESSAGE);
            // createFrame();
        } else if ("psettings".equals(e.getActionCommand())) { //new
            setCalculators(new ProjectProfile(), false, true);
        } else if ("ysettings".equals(e.getActionCommand())) { //new
            setCalculators(new ManageArchitecture(), true, true);
            //JOptionPane.showMessageDialog(this,"Hello, "+e.getActionCommand());  
            // createFrame();
        } else if ("yProjects".equals(e.getActionCommand())) { //new
            setCalculators(new YourProject(false), false, true);
        } else if ("PDetails".equals(e.getActionCommand())) { //new
            setCalculators(new ElectronicComponents(), false, false);
        } else if ("PLinkage".equals(e.getActionCommand())) { //new
            setCalculators(new LinkMatrix(), false, false);
        } else if ("lsettings".equals(e.getActionCommand())) { //new
            setCalculators(new UISettings(), false, true);
        } else if ("lysettings".equals(e.getActionCommand())) { //new
            JOptionPane.showMessageDialog(this, "Hello, " + e.getActionCommand());
            // createFrame();
        } else if ("lyProjects".equals(e.getActionCommand())) { //new
            JOptionPane.showMessageDialog(this, "Hello, " + e.getActionCommand());
            // createFrame();
        } else if ("HContents".equals(e.getActionCommand())) { //new
            JOptionPane.showMessageDialog(this, "Hello, " + e.getActionCommand());
            // createFrame();
        } else if ("OHelp".equals(e.getActionCommand())) { //new
            BrowserURLLink.open("https://topfieldconsultancy.co.uk/clientArea/training");
            JOptionPane.showMessageDialog(this , " Online Help will be open in to the web browser "
                    + "\n Welcome to Our Client Area"
                    + "\n  Login and Proceed", "Please Wait", JOptionPane.INFORMATION_MESSAGE);
        } else if ("RIssue".equals(e.getActionCommand())) { //new
             BrowserURLLink.open("https://topfieldconsultancy.co.uk/clientArea/support");
            JOptionPane.showMessageDialog(this , " Report Issue will be open in to the web browser "
                    + "\n Welcome to Our Client Area"
                    + "\n  Login and Proceed", "Please Wait", JOptionPane.INFORMATION_MESSAGE);
        } else if ("About".equals(e.getActionCommand())) { //new
           // JOptionPane.showMessageDialog(this, "Hello, " + e.getActionCommand());
           JOptionPane.showMessageDialog(this,new AboutUs(), "Welcome to STEMS",JOptionPane.DEFAULT_OPTION);  
        } else { //quit
            //quit();
        }
    }

    public void refresh() {

        JMenuBar menuBar = createMenuBar();

        /**
         * *****************************************************************************************
         */
        //Set up the lone menu.
        userInfo = new JMenu("User");
        userInfo.setForeground(FrameSettings.getButtonColor());
        //userInfo.setMnemonic(KeyEvent.VK_H);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(userInfo);
        /**
         * *****************************************************************************************
         */

        setJMenuBar(menuBar);

        toolBar.add(new ToolBar());
        //leftBar.add(new LeftPanel());
        contentPanel = new ContentPanel();
        contentPanel.setBorder(null);
        desktop.removeAll();
        desktop.add(toolBar, BorderLayout.NORTH);
        desktop.add(new JScroll(leftBar), BorderLayout.WEST);
        desktop.add(contentPanel, BorderLayout.CENTER);
        desktop.revalidate();
        desktop.repaint();

    }
    
    public void setLeftPanel(){
     leftBar.add(new LeftPanel());
     desktop.revalidate();
     desktop.repaint();
    }

    public static void setCalculators(Object cal, boolean scroll, int type, int scrollType) {

        if (scroll == true && type == 0) {
            contentPanel.removeAll();
            contentPanel.add(new JScroll((JPanel) cal));
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == false && type == 0) {
            contentPanel.removeAll();
            contentPanel.add((JPanel) cal);
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == true && type == 1) {
            contentPanel.removeAll();
            contentPanel.add(new JScroll((JTabbedPane) cal));
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == false && type == 1) {
            contentPanel.removeAll();
            contentPanel.add((JTabbedPane) cal);
            contentPanel.revalidate();
            contentPanel.repaint();

        }

    }

    public void ThemeSetter() {

        //System.out.println("z vd" + GraphicsProperties.getThemeProperties().equals("Gray"));

        if (GraphicsProperties.getThemeProperties() != null && GraphicsProperties.getThemeProperties().equals("Black")) {
            FrameSettings.setBlackTheme();
        } else {
            FrameSettings.setDefultTheme();
        }

        if (GraphicsProperties.getButtonProperties() != null && GraphicsProperties.getButtonProperties().equals("Green")) {
            FrameSettings.setGreenSelection();
        } else {
            FrameSettings.setBlueSelection();
        }

    }

    public void refreshTopPannel() {

        toolBar.removeAll();
        toolBar.add(new ToolBar());
        toolBar.revalidate();
        toolBar.repaint();

    }

    public void refreshLeftPannel() {

        leftBar.removeAll();
        leftBar.add(new LeftPanel());
        leftBar.revalidate();
        leftBar.repaint();

    }
    
    public void refreshContantPanel() {

        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    

    public void setUserMenuTag() {

        userInfo.setText(UserInfo.getInstance().getuser().getUsername() + " (" + UserInfo.getInstance().getProjectName() + ")");

    }
    
    

    //Create a new internal frame.
    protected void createFrame() {
        MyInternalFrame frame = new MyInternalFrame();
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {
        
        try {

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        InternalFrameDemo frame = new InternalFrameDemo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
        
        } catch (Exception e) {
              e.printStackTrace();
              BusinessException.showBusinessException("GL0404001", "Error" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public ToolBar getToolBar() {
        return (ToolBar) toolBar.getComponent(0);
    }

    public LeftPanel getLeftBar() {
        return (LeftPanel) leftBar.getComponent(0);
    }

    public void setCalculators(Object cal, boolean tabpanel, boolean scroll) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (scroll == true && tabpanel == true) {
            contentPanel.removeAll();
            contentPanel.add(new JScroll((JTabbedPane) cal));
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == false && tabpanel == true) {
            contentPanel.removeAll();
            contentPanel.add((JTabbedPane) cal);
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == true && tabpanel == false) {
            contentPanel.removeAll();
            contentPanel.add(new JScroll((JPanel) cal));
            contentPanel.revalidate();
            contentPanel.repaint();
        } else if (scroll == false && tabpanel == false) {
            contentPanel.removeAll();
            contentPanel.add((JPanel) cal);
            contentPanel.revalidate();
            contentPanel.repaint();

        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void snapshot() {

        // JMenuItem menuBar = ((JMenuItem)evt.getSource());
        //   menuBar.doClick();
           // Thread.sleep(5000);

          java.util.List<String> path = FileSelection.getSelectionPath2(new int[]{3});
             
          if (path.size()>1) {
              
              try {
                  
                  setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    
                  Robot robot = new Robot();

                 // String desktopPath = System.getProperty("user.home") + "/Desktop";
                  String fileName = path.get(0)+"."+path.get(1);

                  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                  screenHeight = (int) (screenSize.getHeight() * 95) / 100;
                  screenWidth = (int) (screenSize.getWidth() * 95) / 100;

                  /*Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
                                     .getScreenSize());*/
                  // Define an area of size 500*400 starting at coordinates (10,50)
                  Rectangle rectArea = new Rectangle(mainFrame.getX() + 8, mainFrame.getY() + 8, mainFrame.getWidth() - 17, mainFrame.getHeight() - 17);
                  BufferedImage screenFullImage = robot.createScreenCapture(rectArea);
                  ImageIO.write(screenFullImage, "jpg", new File(fileName));

                  JOptionPane.showMessageDialog(null, "Snapshot created successfully \nSnapshot file path:-" + fileName);

              } catch (AWTException | IOException ex) {
                  ex.printStackTrace();

                  BusinessException.showBusinessException("SC0407001", "Error while create screenshot " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
              }finally{
                  setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              }
        }
    }

    public void setLookAndFeel() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InternalFrameDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InternalFrameDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InternalFrameDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InternalFrameDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    public void setLoader() {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

    }

    public void removeLoder() {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }
    
    public Object getPrintContent(boolean isGraph){
     Object res=null;       
     JTabbedPane seletedTab;
     
        if (contentPanel.getComponent(0) instanceof JTabbedPane) {
            seletedTab =((JTabbedPane) contentPanel.getComponent(0));
            
            if (seletedTab.getComponent(1) instanceof MyPrintable) {
                res = ((MyPrintable)seletedTab.getComponentAt(1)).getPrintContantGraph();
            }
        }
     
     return res;
    }
    
    public void printJpanel(File file,Object print){
        
        
        JPanel panel = null;
        BufferedImage image = null;
        boolean isGraph= false;
        
        if (print instanceof JPanel) {
            panel = (JPanel)print;
        }else if (print instanceof mxGraph) {
             image = mxCellRenderer.createBufferedImage((mxGraph)print, null,
				1, Color.WHITE, true, null); 
             isGraph = true;
        }
        
        try {
                        
               
                // create a PDF Printer Job  https://www.qoppa.com/pdfwriter/
                PDFPrinterJob printer = (PDFPrinterJob) PDFPrinterJob.getPrinterJob();

                PageFormat pf = printer.defaultPage();
                Paper paper = new Paper();
                pf.setOrientation(PageFormat.LANDSCAPE);
                double margin = 0; 
                
                if (isGraph) {
                    paper.setSize(image.getHeight()+15,image.getWidth()+30);
                } else {
                    paper.setSize(panel.getHeight()+15, panel.getWidth()+30);
                }
               
                //paper.setSize(1000, 2000);
                
                paper.setImageableArea(margin, margin, paper.getWidth(), paper.getHeight());
                pf.setPaper(paper);
                

                // set the printable object 
                if (isGraph) {
                    printer.setPrintable(new PrintImageToPDF(image), pf);
                } else {
                    
                }
                
                // set number of copies to 1 
                printer.setCopies(1);
                // print and save the document
                printer.print(file.getAbsolutePath()+".pdf");
                // output done message 
                System.out.println("Done!");
                
                JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+file.getAbsolutePath()+".pdf");

                
            } catch (Throwable t) {
                t.printStackTrace();
            }
            
    }
    
    
    public ExportController getExportController(){
        return exportController;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

    }
}
