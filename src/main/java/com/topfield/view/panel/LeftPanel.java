/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.panel;

import com.topfield.calculators.calcfile.CalcCalculator;
import com.topfield.main.InternalFrameDemo;
import com.topfield.settings.FrameSettings;
import com.topfield.settings.JScroll;
import com.topfield.themes.CalculatorButton;
import com.topfield.themes.TopPanelButton;
import com.topfield.calculators.ccf.CCFcalculator;
import com.topfield.view.calculators.EvacuationCalculation;
import com.topfield.calculators.eventtree.EventTreeCalculator;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.calculators.rbd.RBDCalculator;
import com.topfield.info.NotificationPanel;
import com.topfield.modal.Packages;
import com.topfield.user.UserInfo;
import com.topfield.view.calculators.NotificationCal;
import com.topfield.view.calculators.SILcalculator;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */
public class LeftPanel extends JPanel {

    private CalculatorButton selected;

    private CalcCalculator calcFile;
    private RBDCalculator rbdCal;
    private SILcalculator silCal;
    private FMEACalculator fmeaCal;
    private FMECACalculator fmecaCal;
    private EvacuationCalculation evcaCal;
    private EventTreeCalculator eventreeCal;
    private CCFcalculator ccfCal;
    private FaultTreeCalculator faulttreeCal;
    private NotificationCal notificationCal;

    public LeftPanel() {

        CalculatorButton calc;
        CalculatorButton rbd;
        CalculatorButton sil0;
        CalculatorButton fmea;
        CalculatorButton fmeca;
        //CalculatorButton evacuation;
        CalculatorButton eventTree;
        CalculatorButton ccf;
        CalculatorButton fault_tree;
        CalculatorButton reminder;

        setBackground(FrameSettings.getFrameColor());
        setForeground(FrameSettings.getFontColor());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        calc = new CalculatorButton("STEM_Icons_01_CalcFile", "Calculator Management Module");
        rbd = new CalculatorButton("STEM_Icons_01_RBD", "Reliability Block Diagram");
        sil0 = new CalculatorButton("STEM_Icons_01_SIL_Determine", "Safety integrity level");
        fmea = new CalculatorButton("STEM_Icons_01_FMEA", "Failure Mode and Effects Analysis");
        fmeca = new CalculatorButton("STEM_Icons_01_FMECA", "Failure Mode, Effects & Criticality Analysis");
        //evacuation = new CalculatorButton("STEM_Icons_01_EVAC", "Evacuation calculator");
        eventTree = new CalculatorButton("STEM_Icons_01_Event_Tree", "Event Tree calculation");
        ccf = new CalculatorButton("STEM_Icons_01_CCF", "Common Cause Failure Calculator");
        fault_tree = new CalculatorButton("STEM_Icons_01_Fault_Tree", "Fault Tree Calculator");
        reminder = new CalculatorButton("STEM_Icons_01_Reminder", "Reminder");

        Packages packages = UserInfo.getInstance().getuser().getPackage1();
        String[] cal = {"Calc File", "RBD", "SIL", "FMEA", "FMECA", "CCF", "ETA", "Event Tree", "EVAC"};

        if (packages.getAllowedCal().contentEquals("All")) {
            add(calc);
            //sil0.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 6));
            add(rbd);
            //sil0.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 6));
            add(sil0);
            //fmea.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
            add(fmea);
            //fmeca.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
            add(fmeca);
            //evacuation.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
             //add(evacuation);
            //eventTree.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
            add(eventTree);
            //ccf.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
            add(ccf);
            //fault_tree.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 6));
            add(fault_tree);

            add(reminder);
        } else {

            String[] allowedCal = packages.getAllowedCal().split("\\#");

            for (String name : allowedCal) {
                if (name.contentEquals("Calc File")) {
                    add(calc);

                } else if (name.contentEquals("RBD")) {
                    add(rbd);
                } else if (name.contentEquals("SIL")) {
                    add(sil0);
                } else if (name.contentEquals("FMEA")) {
                    add(fmea);
                } else if (name.contentEquals("FMECA")) {
                    add(fmeca);
                } else if (name.contentEquals("CCF")) {
                    add(ccf);
                } else if (name.contentEquals("FTA")) {
                    add(fault_tree);
                } else if (name.contentEquals("Event Tree")) {
                    add(eventTree);
                } else if (name.contentEquals("EVAC")) {
                    //add(evacuation);
                }

            }
            
             add(reminder);

        }

    }

    public void setOnclickColour(CalculatorButton button) {
        CalculatorButton test;
        for (int i = 0; i < getComponentCount(); i++) {
            test = (CalculatorButton) getComponent(i);
            if (test.equals(button)) {
                test.OnClickAction();
            } else {
                test.OffClickAction();
            }
        }

    }

    public void setUnSelectAll() {
        CalculatorButton test;
        for (int i = 0; i < getComponentCount(); i++) {
            test = (CalculatorButton) getComponent(i);

            test.OffALLClickAction();

        }

        setSelected(null);
    }

    /**
     * @return the selected
     */
    public CalculatorButton getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(CalculatorButton selected) {
        this.selected = selected;
    }

    public void setCalcFile(CalcCalculator calcFile) {
        this.calcFile = calcFile;
        setCalculators(this.calcFile == null ? this.calcFile = new CalcCalculator() : this.calcFile, false, CalcCalculator.class);
    }

    public void setRBDCal(RBDCalculator rbdCal) {
        this.rbdCal = rbdCal;
        setCalculators(this.rbdCal == null ? this.rbdCal = new RBDCalculator() : this.rbdCal, false, RBDCalculator.class);
    }

    public void setSilCal(SILcalculator silCal) {
        this.silCal = silCal;
        setCalculators(this.silCal == null ? this.silCal = new SILcalculator() : this.silCal, true, SILcalculator.class);
    }

    public void setFmeaCal(FMEACalculator fmeaCal) {
        this.fmeaCal = fmeaCal;
        setCalculators(this.fmeaCal == null ? this.fmeaCal = new FMEACalculator() : this.fmeaCal, false, FMEACalculator.class);
    }

    public void setFmecaCal(FMECACalculator fmecaCal) {
        this.fmecaCal = fmecaCal;
        setCalculators(this.fmecaCal == null ? this.fmecaCal = new FMECACalculator() : this.fmecaCal, false, FMECACalculator.class);
    }

    public void setEvcaCal(EvacuationCalculation evcaCal) {
        this.evcaCal = evcaCal;
        setCalculators(this.evcaCal == null ? this.evcaCal = new EvacuationCalculation() : this.evcaCal, true, EvacuationCalculation.class);
    }

    public void setEventreeCal(EventTreeCalculator eventreeCal) {
        this.eventreeCal = eventreeCal;
        setCalculators(this.eventreeCal == null ? this.eventreeCal = new EventTreeCalculator() : this.eventreeCal, false, EventTreeCalculator.class);
    }

    public void setCcfCal(CCFcalculator ccfCal) {
        this.ccfCal = ccfCal;
        setCalculators(this.ccfCal == null ? this.ccfCal = new CCFcalculator() : this.ccfCal, false, CCFcalculator.class);
    }

    public void setFaulttreeCal(FaultTreeCalculator faulttreeCal) {
        this.faulttreeCal = faulttreeCal;
        setCalculators(this.faulttreeCal == null ? this.faulttreeCal = new FaultTreeCalculator() : this.faulttreeCal, false, FaultTreeCalculator.class);
    }

    public SILcalculator getSilCal() {
        return silCal;
    }

    public FMEACalculator getFmeaCal() {
        return fmeaCal;
    }

    public FMECACalculator getFmecaCal() {
        return fmecaCal;
    }

    public EvacuationCalculation getEvcaCal() {
        return evcaCal;
    }

    public EventTreeCalculator getEventreeCal() {
        return eventreeCal;
    }

    public CCFcalculator getCcfCal() {
        return ccfCal;
    }

    public FaultTreeCalculator getFaulttreeCal() {
        return faulttreeCal;
    }

    public NotificationCal getNotificationCal() {
        return notificationCal;
    }

    public void setOnclickAction(String button) {
        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        switch (button) {
            case "STEM_Icons_01_CalcFile":
                setCalculators(this.calcFile == null ? this.calcFile = new CalcCalculator() : this.calcFile, false, CalcCalculator.class);
                break;
            case "STEM_Icons_01_RBD":
                setCalculators(this.rbdCal == null ? this.rbdCal = new RBDCalculator() : this.rbdCal, false, RBDCalculator.class);
                break;
            case "STEM_Icons_01_SIL_Determine":
                setCalculators(silCal == null ? silCal = new SILcalculator() : silCal, true, SILcalculator.class);
                break;
            case "STEM_Icons_01_FMEA":
                setCalculators(fmeaCal == null ? fmeaCal = new FMEACalculator() : fmeaCal, false, FMEACalculator.class);
                break;
            case "STEM_Icons_01_FMECA":
                setCalculators(fmecaCal == null ? fmecaCal = new FMECACalculator() : fmecaCal, false, FMECACalculator.class);
                break;
            case "STEM_Icons_01_EVAC":
                setCalculators(evcaCal == null ? evcaCal = new EvacuationCalculation() : evcaCal, true, EvacuationCalculation.class);
                break;
            case "STEM_Icons_01_Event_Tree":
                setCalculators(eventreeCal == null ? eventreeCal = new EventTreeCalculator() : eventreeCal, false, EventTreeCalculator.class);
                break;
            case "STEM_Icons_01_CCF":
                setCalculators(ccfCal == null ? ccfCal = new CCFcalculator() : ccfCal, false, CCFcalculator.class);
                break;
            case "STEM_Icons_01_Fault_Tree":
                setCalculators(faulttreeCal == null ? faulttreeCal = new FaultTreeCalculator() : faulttreeCal, false, FaultTreeCalculator.class);
                break;
            case "STEM_Icons_01_Reminder":
                InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setCalculators(notificationCal == null ? notificationCal = new NotificationCal() : notificationCal, true, NotificationCal.class);
                break;
            default:
                InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;
        }

    }

    public void setCalculators(Object cal, boolean scroll, Class type) {

        if (scroll == true) {
            InternalFrameDemo.contentPanel.removeAll();
            InternalFrameDemo.contentPanel.add(new JScroll((JTabbedPane) cal));
            InternalFrameDemo.contentPanel.revalidate();
            InternalFrameDemo.contentPanel.repaint();
        } else {
            InternalFrameDemo.contentPanel.removeAll();
            InternalFrameDemo.contentPanel.add((JTabbedPane) cal);
            InternalFrameDemo.contentPanel.revalidate();
            InternalFrameDemo.contentPanel.repaint();

        }
        InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void resetCalculators() {

      calcFile = null;
      rbdCal = null;
      silCal = null;
      fmeaCal = null;
      fmecaCal = null;
      evcaCal = null;
      eventreeCal = null;
      ccfCal = null;
      faulttreeCal = null;
      notificationCal = null;

    }

}
