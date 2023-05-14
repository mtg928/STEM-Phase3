/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

/**
 *
 * @author Murali
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
 
public class JGraphLearning5 extends JFrame {
  private static final long serialVersionUID = -2398843485837905351L;
  private static final int X_SOURCE = 50, Y_SOURCE = 50 ,PAGE_WIDTH = 1300, 
    HORIZONTAL_STEP = 150, VERTICAL_STEP = 100, SHAPE_WIDTH = 50,
    SHAPE_HEIGHT = 60;
 
  public JGraphLearning5() {
    mxGraph graph = new mxGraph();
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.getViewport().setOpaque(true);
    graphComponent.getViewport().setBackground(Color.WHITE);
     
    int x = X_SOURCE, y = Y_SOURCE;
    for (JGraphShape shape : JGraphShape.values()) {
      graph.insertVertex(null, null, shape.mxShapeConstantValue, x, y, SHAPE_WIDTH, SHAPE_HEIGHT, mxConstants.STYLE_SHAPE+"="+shape.mxShapeConstantValue);
      x += HORIZONTAL_STEP;
      if(x > PAGE_WIDTH) { x = X_SOURCE; y += VERTICAL_STEP; }
    }
    // Some options for the shapes:
 /*   x = X_SOURCE; y += VERTICAL_STEP;
    String styleShape = mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_RECTANGLE+";"; 
    for(JGraphStyle style: JGraphStyle.values()) {
      graph.insertVertex(null, null, style.name(), x, y, SHAPE_WIDTH, SHAPE_HEIGHT, styleShape+style.mxStyle);
      x += HORIZONTAL_STEP;
      if(x > PAGE_WIDTH) { x = X_SOURCE; y += VERTICAL_STEP; }
    }
     
    styleShape = mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_ELLIPSE+";"; 
    for(JGraphStyle style: JGraphStyle.values()) {
      graph.insertVertex(null, null, style.name(), x, y, SHAPE_WIDTH, SHAPE_HEIGHT, styleShape+style.mxStyle);
      x += HORIZONTAL_STEP;
      if(x > PAGE_WIDTH) { x = X_SOURCE; y += VERTICAL_STEP; }
    }*/
 
    getContentPane().setLayout(new BorderLayout(0,0));
    getContentPane().add(graphComponent, BorderLayout.CENTER);
  }
 
  public enum JGraphShape {
    RECTANGLE(mxConstants.SHAPE_RECTANGLE),
    ELLIPSE(mxConstants.SHAPE_ELLIPSE),
    DOUBLE_ELLIPSE(mxConstants.SHAPE_DOUBLE_ELLIPSE),
    RHOMBUS(mxConstants.SHAPE_RHOMBUS),
    LINE(mxConstants.SHAPE_LINE),
    IMAGE(mxConstants.SHAPE_IMAGE),
// doesn't work   ARROW(mxConstants.SHAPE_ARROW),
    CURVE(mxConstants.SHAPE_CURVE),
    LABEL(mxConstants.SHAPE_LABEL),
    CILINDER(mxConstants.SHAPE_CYLINDER),
    SWIMLANE(mxConstants.SHAPE_SWIMLANE),
    CONNECTOR(mxConstants.SHAPE_CONNECTOR),
    ACTOR(mxConstants.SHAPE_ACTOR),
    CLOUD(mxConstants.SHAPE_CLOUD),
    TRIANGLE(mxConstants.SHAPE_TRIANGLE),
    HEXAGON(mxConstants.SHAPE_HEXAGON);
     
    public String mxShapeConstantValue;
 
    JGraphShape(String mxShapeConstantValue) {
      this.mxShapeConstantValue = mxShapeConstantValue;
    }
  }
   
  public enum JGraphStyle {
    OPACITY(mxConstants.STYLE_OPACITY, 50.0),
    TEXT_OPACITY(mxConstants.STYLE_TEXT_OPACITY, 50.0),
    OVERFLOW_1(mxConstants.STYLE_OVERFLOW, "visible"),
    OVERFLOW_2(mxConstants.STYLE_OVERFLOW, "hidden"),
    OVERFLOW_3(mxConstants.STYLE_OVERFLOW, "fill"),
    ROTATION(mxConstants.STYLE_ROTATION, 45),
    FILLCOLOR(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.RED)),
    GRADIENTCOLOR(mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.BLUE)),
    GRADIENT_DIRECTION(mxConstants.STYLE_GRADIENT_DIRECTION, mxConstants.DIRECTION_EAST, mxConstants.STYLE_GRADIENTCOLOR, mxUtils.getHexColorString(Color.YELLOW)),
    STROKECOLOR(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.GREEN)),
    STROKEWIDTH(mxConstants.STYLE_STROKEWIDTH, 5),
    ALIGN(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT),
    VERTICAL_ALIGN(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM),
    LABEL_POSITION(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_LEFT),
    VERTICAL_LABEL_POSITION(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_BOTTOM),
    GLASS(mxConstants.STYLE_GLASS, 1),
    NOLABEL(mxConstants.STYLE_NOLABEL, 1),
    LABEL_BACKGROUNDCOLOR(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, mxUtils.getHexColorString(Color.CYAN)),
    LABEL_BORDERCOLOR(mxConstants.STYLE_LABEL_BORDERCOLOR, mxUtils.getHexColorString(Color.PINK)),
    SHADOW(mxConstants.STYLE_SHADOW, true),
    DASHED(mxConstants.STYLE_DASHED, true),
    ROUNDED(mxConstants.STYLE_ROUNDED, true),
    HORIZONTAL(mxConstants.STYLE_HORIZONTAL, false),
    FONTCOLOR(mxConstants.STYLE_FONTCOLOR, mxUtils.getHexColorString(Color.ORANGE)),
    FONTFAMILY(mxConstants.STYLE_FONTFAMILY, "Times New Roman"),
    FONTSIZE(mxConstants.STYLE_FONTSIZE, 15),
    FONTSTYLE(mxConstants.STYLE_FONTSTYLE, mxConstants.FONT_BOLD),        
    ;
 
    public String mxStyle;
     
    JGraphStyle(Object... values) {
      mxStyle = ""; 
      for (int i = 0; i < values.length; i++) {
        if(i%2==0) {
          mxStyle += values[i] + "=";
        } else {
          mxStyle += values[i] + ";";
        }
      }
    }
  }
   
  public static void main(String args[]) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        JGraphLearning5 jgl = new JGraphLearning5();
        jgl.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jgl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jgl.setVisible(true);
      }
    });
  }
}
