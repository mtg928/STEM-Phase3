/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author Murali
 */
public class EvacuationCalculator extends JPanel implements ChartMouseListener{
    
    private JPanel cp = new JPanel();
    private Crosshair xCrosshair;
    private Evacuation_From panelOne1;

    private Crosshair yCrosshair;
    private Crosshair yCrosshair2;
    private Crosshair yCrosshair3;

    public EvacuationCalculator() {
        //setLayout(new BorderLayout());
        
      /*  setBounds(40,80,200,200);    
        setBackground(Color.gray);  
        JButton b1=new JButton("Button 1");     
        b1.setBounds(50,100,80,30);    
        b1.setBackground(Color.yellow);   
        JButton b2=new JButton("Button 2");   
        b2.setBounds(100,100,80,30);    
        b2.setBackground(Color.green);   
        add(b1); 
        add(b2);  */
      
        setLayout(new BorderLayout());
        cp.setLayout(new BorderLayout());
        JPanel container = new JPanel();
        panelOne1 = new Evacuation_From(this);
        
        
        
        JPanel panelTwo2 = new JPanel();
        panelTwo2.setBackground(Color.GRAY);
        
        JLabel label = new JLabel("<html>  <b> Evacuation Calculation</b> </html>");
        label.setFont(new Font(label.getName(), Font.PLAIN, 20));
        panelTwo2.add(label);
        
        JPanel panelTwo = new JPanel();
        panelTwo.setBackground(Color.GRAY);
        panelTwo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
       generateChart(createDatasetIntial());
       //cp = new ChartPanel(null);
                
        container.setLayout(new BorderLayout());
        container.add(panelTwo2,BorderLayout.NORTH);
        container.add(panelOne1,BorderLayout.WEST);
        container.add(cp,BorderLayout.CENTER);

        this.add(container);
      
      
      
    }
    
    
    public void generateChart(XYDataset ds){
    
        
        JFreeChart chart = ChartFactory.createXYLineChart("Evacuation Time",
                        "Time (s)", "Persons", ds, PlotOrientation.VERTICAL, true, true,
                        false);

        
        
        ChartPanel   cp1 = new ChartPanel(chart);
                cp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(214, 234, 248)));
                cp1.setBackground(Color.red);
                
        
                
        cp1.addChartMouseListener(this);
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        cp1.addOverlay(crosshairOverlay);        
        
        CrosshairOverlay crosshairOverlay2 = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair2 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair2.setLabelVisible(true);
        crosshairOverlay2.addDomainCrosshair(xCrosshair);
        crosshairOverlay2.addRangeCrosshair(yCrosshair2);
        cp1.addOverlay(crosshairOverlay2);   
        
        CrosshairOverlay crosshairOverlay3 = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair3 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair3.setLabelVisible(true);
        crosshairOverlay3.addDomainCrosshair(xCrosshair);
        crosshairOverlay3.addRangeCrosshair(yCrosshair3);
        cp1.addOverlay(crosshairOverlay3); 
                
                
       cp.removeAll();
       cp.add(cp1, BorderLayout.CENTER);
    }
    
    
    private  XYDataset createDatasetIntial() {

        DefaultXYDataset ds = new DefaultXYDataset();
        
        double[][] best = { {0.0,0.075, 0.2}, {1,15, 20} };
        double[][] least = { {0.0,0.125, 0.2}, {1,7, 20} };

        ds.addSeries("Best Evacuation Time", best);
        ds.addSeries("Least Evacuation Time", least);
        
        return ds;
    }
    
    public  XYDataset createDataset() {

        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] first = new double[2][panelOne1.getNoOfPassengers("first")];
        double[][] family = new double[2][panelOne1.getNoOfPassengers("family")];
        double[][] single = new double[2][panelOne1.getNoOfPassengers("single")];
        
        for (int i = 0; i < panelOne1.getNoOfPassengers("first"); i++) {
            
            first[1][i]= i;
            first[0][i]= panelOne1.iterationPerson(i, "first");
        }
        
       for (int i = 0; i < panelOne1.getNoOfPassengers("family"); i++) {
            
            family[1][i]= i;
            family[0][i]= panelOne1.iterationPerson(i, "family");
        }
       
        for (int i = 0; i < panelOne1.getNoOfPassengers("single"); i++) {
            
            single[1][i]= i;
            single[0][i]= panelOne1.iterationPerson(i, "single");
        }
        
        

        double[][] best = { {30.0,32.125, 35.0}, {10,150, 200} };
        double[][] least = { {30.0,32.125, 35.0}, {10,70, 200} };

        //ds.addSeries("Best Evacuation Time", first);
        //ds.addSeries("Least Evacuation Time", family);
        
        ds.addSeries("First class", first);
        ds.addSeries("Family class", family);
        ds.addSeries("Single class", single);

        return ds;
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent cme) {

        Rectangle2D dataArea = ((ChartPanel)cp.getComponent(0)).getScreenDataArea();
        JFreeChart chart = cme.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(cme.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        //double y = DatasetUtilities.findDomainBounds(plot.getDataset()).getUpperBound();
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
        this.yCrosshair2.setValue(DatasetUtilities.findYValue(plot.getDataset(), 1, x));
        this.yCrosshair3.setValue(DatasetUtilities.findYValue(plot.getDataset(), 2, x));
    }
    
    
    private  double interpolate(XYSeries s, double x)
    {
        if (x <= s.getMinX())
        {
            return s.getY(0).doubleValue();
        }
        if (x >= s.getMaxX())
        {
            return s.getY(s.getItemCount()-1).doubleValue();
        }
        List<?> items = s.getItems();
        for (int i=0; i<items.size()-1; i++)
        {
            XYDataItem i0 = (XYDataItem) items.get(i);
            XYDataItem i1 = (XYDataItem) items.get(i+1);
            double x0 = i0.getXValue();
            double y0 = i0.getYValue();
            double x1 = i1.getXValue();
            double y1 = i1.getYValue();

            if (x >= x0 && x <= x1)
            {
                double d = x - x0;
                double a = d / (x1-x0);
                double y = y0 + a * (y1 - y0);
                return y;
            }
        }
        // Should never happen
        return 0;
    }
    
    
}


/*

JLabel lblPhone = new JLabel("Phone #");
        lblPhone.setBounds(65, 68, 46, 14);
        panelOne.add(lblPhone);

        JTextField textField_1 = new JTextField();
        textField_1.setBounds(128, 65, 86, 20);
        panelOne.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblEmailId = new JLabel("Email Id");
        lblEmailId.setBounds(65, 115, 46, 14);
        panelOne.add(lblEmailId);

        JTextField textField_2 = new JTextField();
        textField_2.setBounds(128, 112, 247, 17);
        panelOne.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(65, 162, 46, 14);
        panelOne.add(lblAddress);

        JTextArea textArea_1 = new JTextArea();
        textArea_1.setBounds(126, 157, 212, 40);
        panelOne.add(textArea_1);



        JButton btnClear = new JButton("Clear");

        btnClear.setBounds(312, 387, 89, 23);
        panelOne.add(btnClear);

        JLabel lblSex = new JLabel("Sex");
        lblSex.setBounds(65, 228, 46, 14);
        panelOne.add(lblSex);

        JLabel lblMale = new JLabel("Male");
        lblMale.setBounds(128, 228, 46, 14);
        panelOne.add(lblMale);

        JLabel lblFemale = new JLabel("Female");
        lblFemale.setBounds(292, 228, 46, 14);
        panelOne.add(lblFemale);

        JRadioButton radioButton = new JRadioButton("");
        radioButton.setBounds(337, 224, 109, 23);
        panelOne.add(radioButton);

        JRadioButton radioButton_1 = new JRadioButton("");
        radioButton_1.setBounds(162, 224, 109, 23);
        panelOne.add(radioButton_1);

        JLabel lblOccupation = new JLabel("Occupation");
        lblOccupation.setBounds(65, 288, 67, 14);
        panelOne.add(lblOccupation);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Select");
        comboBox.addItem("Business");
        comboBox.addItem("Engineer");
        comboBox.addItem("Doctor");
        comboBox.addItem("Student");
        comboBox.addItem("Others");
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }

        });
        comboBox.setBounds(180, 285, 91, 20);
        panelOne.add(comboBox);


        JButton btnSubmit = new JButton("submit");

        btnSubmit.setBackground(Color.BLUE);
        btnSubmit.setForeground(Color.MAGENTA);
        btnSubmit.setBounds(65, 387, 89, 23);
        panelOne.add(btnSubmit);
        
        
        panelOne.setBackground(Color.white);
        JPanel panelTwo = new JPanel();
        panelTwo.setBackground(Color.GRAY);
        panelOne.add(new JLabel("1"));
        panelTwo.add(new JLabel("2"));





*/