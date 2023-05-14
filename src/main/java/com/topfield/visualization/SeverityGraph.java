/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;



import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

/**
 * @author imssbora
 */
public class SeverityGraph extends JPanel implements ChartMouseListener {
  private static final long serialVersionUID = 6294689542092367723L;
    private ChartPanel chartPanel;

    private Crosshair xCrosshair;

    private Crosshair yCrosshair;

  public SeverityGraph() {
    
      setLayout(new BorderLayout());
    // Create dataset
    XYDataset dataset = createDataset();
    
    

    // Create chart
    JFreeChart chart = ChartFactory.createScatterPlot(
        "Severity Graph", 
        "Severity Classification", "Criticality Number", dataset);

    
    //Changes background color
    XYPlot plot = (XYPlot)chart.getPlot();
    plot.setBackgroundPaint(new Color(255,228,196));
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainGridlineStroke(new BasicStroke(1.0f));
        plot.setRangeGridlineStroke(new BasicStroke(1.0f));
        plot.setDomainMinorGridlinesVisible(true);
        plot.setRangeMinorGridlinesVisible(true);
        plot.setDomainMinorGridlineStroke(new BasicStroke(0.1f));
        plot.setRangeMinorGridlineStroke(new BasicStroke(0.1f));
        LogAxis yAxis = new LogAxis("Criticality Number");
        //yAxis.setTickUnit(new NumberTickUnit(10));
        plot.setRangeAxis(yAxis);
    
        /*NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();  
        xAxis.setTickUnit(new NumberTickUnit(1));*/
   
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    chartPanel = panel;
    panel.getChart().getXYPlot().setDomainMinorGridlinesVisible(true);
    
   // panel.getChart().getXYPlot().getRangeAxis().setInverted(true);
     panel.getChart().getXYPlot().getDomainAxis().setInverted(true);
     //panel.getChart().getXYPlot().getRangeAxis().setRange(0.001, 10);
     //panel.getChart().getXYPlot().getRangeAxis().setTickUnit(new NumberTickUnit(0.1));
     panel.getChart().getXYPlot().getRangeAxis().setMinorTickMarksVisible(true);
    //setContentPane(panel);
    

        
        panel.addChartMouseListener(this);
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
    
        

    
      add(panel);
  }

  private XYDataset createDataset() {
    XYSeriesCollection dataset = new XYSeriesCollection();

    //Boys (Age,weight) series
    XYSeries series1 = new XYSeries("R1(Open)");
    
     /* for (int i = 1; i <= 40; i++) {
          
           series1.add(i/10, 1);
          
      }*/
    
      
      
    series1.add(0, 10);
    series1.add(0.2,6.217);
    series1.add(0.4,4.099);
    series1.add(0.6,2.511);
    series1.add(0.8,1.594);
    series1.add(1, 1);
    series1.add(1.2,0.644);
    series1.add(1.4,0.404);
    series1.add(1.6,0.25);
    series1.add(1.8,0.158);
    series1.add(2, 0.1);
    series1.add(2.2,6.217/100);
    series1.add(2.4,4.099/100);
    series1.add(2.6,2.511/100);
    series1.add(2.8,1.594/100);
    series1.add(3, 0.01);
    series1.add(3.2,6.217/1000);
    series1.add(3.4,4.099/1000);
    series1.add(3.6,2.511/1000);
    series1.add(3.8,1.594/1000);
    series1.add(4, 0.001);
    /*series1.add(5, 102.1);
    series1.add(6, 108.5);
    series1.add(7, 113.9);
    series1.add(8, 119.3);
    series1.add(9, 123.8);
    series1.add(10, 124.4);*/

    
    
  //Girls (Age,weight) series
    XYSeries series2 = new XYSeries("R1(Short/Open)");
    series2.add(4, 0.1);
    series2.add(1, 0.15);
    /*series2.add(3, 87.2);
    series2.add(4, 94.5);
    series2.add(5, 101.4);
    series2.add(6, 107.4);
    series2.add(7, 112.8);
    series2.add(8, 118.2);
    series2.add(9, 122.9);
    series2.add(10, 123.4);*/

   dataset.addSeries(series2);
   dataset.addSeries(series1);
    return dataset;
  }

    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        // ignore
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
    }


  
}