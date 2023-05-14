/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jgraph.example.mycellview;

import com.topfield.graph.RBDCell;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Murali
 */
public class JGraphRBDView extends VertexView {

	/**
	 */
	public static transient JGraphEllipseRenderer renderer = new JGraphEllipseRenderer();

	/**
	 */
	public JGraphRBDView() {
		super();
	}

	/**
	 */
	public JGraphRBDView(Object cell) {
		super(cell);
	}

	/**
	 * Returns the intersection of the bounding rectangle and the
	 * straight line between the source and the specified point p.
	 * The specified point is expected not to intersect the bounds.
	 */
	public Point2D getPerimeterPoint(EdgeView edge, Point2D source, Point2D p) {
		Rectangle2D r = getBounds();

		double x = r.getX();
		double y = r.getY();
		double a = (r.getWidth() + 1) / 2;
		double b = (r.getHeight() + 1) / 2;

		// x0,y0 - center of ellipse
		double x0 = x + a;
		double y0 = y + b;

		// x1, y1 - point
		double x1 = p.getX();
		double y1 = p.getY();

		// calculate straight line equation through point and ellipse center
		// y = d * x + h
		double dx = x1 - x0;
		double dy = y1 - y0;

		if (dx == 0)
			return new Point((int) x0, (int) (y0 + b * dy / Math.abs(dy)));

		double d = dy / dx;
		double h = y0 - d * x0;

		// calculate intersection
		double e = a * a * d * d + b * b;
		double f = -2 * x0 * e;
		double g = a * a * d * d * x0 * x0 + b * b * x0 * x0 - a * a * b * b;

		double det = Math.sqrt(f * f - 4 * e * g);

		// two solutions (perimeter points)
		double xout1 = (-f + det) / (2 * e);
		double xout2 = (-f - det) / (2 * e);
		double yout1 = d * xout1 + h;
		double yout2 = d * xout2 + h;

		double dist1Squared = Math.pow((xout1 - x1), 2)
				+ Math.pow((yout1 - y1), 2);
		double dist2Squared = Math.pow((xout2 - x1), 2)
				+ Math.pow((yout2 - y1), 2);

		// correct solution
		double xout, yout;

		if (dist1Squared < dist2Squared) {
			xout = xout1;
			yout = yout1;
		} else {
			xout = xout2;
			yout = yout2;
		}

		return getAttributes().createPoint(xout, yout);
	}

	/**
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
        


	/**
	 */
	public static class JGraphEllipseRenderer extends VertexRenderer {

		/**
		 * Return a slightly larger preferred size than for a rectangle.
		 */
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.width += d.width / 8;
			d.height += d.height / 2;
			return d;
		}

                /* Override VertexView for Dual Label*
             * Configure and return the renderer component based on the passed in cell.
             * The value is typically set from messaging the graph with
             * <code>convertValueToString</code>. We recommend you check the value's
             * class and throw an illegal argument exception if it's not correct.
             * 
             * @param graph
             *            the graph that that defines the rendering context.
             * @param view
             *            the cell view that should be rendered.
             * @param sel
             *            whether the object is selected.
             * @param focus
             *            whether the object has the focus.
             * @param preview
             *            whether we are drawing a preview.
             * @return the component used to render the value.
             */
            public Component getRendererComponent(JGraph graph, CellView view,
                            boolean sel, boolean focus, boolean preview) {
                    gridColor = graph.getGridColor();
                    highlightColor = graph.getHighlightColor();
                    lockedHandleColor = graph.getLockedHandleColor();
                    isDoubleBuffered = graph.isDoubleBuffered();  
                    

                    Color color = GraphConstants.getForeground(graph.getAttributes(view.getCell())); 
                    color = color == null ? Color.BLUE : color;
                    
                    String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());  

                    if (view instanceof VertexView) {
                            this.view = (VertexView) view;
                            setComponentOrientation(graph.getComponentOrientation());
                            if (graph.getEditingCell() != view.getCell()) {
                                    Object label = graph.convertValueToString(view);
                                    RBDCell rBDCell = (RBDCell)view.getCell();
                                    
                                    if (label != null)  
                                            setText("<html>"
                                                    + "<center><font color=\""+hex+"\" face=Arial size=-1>" +label.toString()+"</font></center> <br/>"
                                                    + "<p color=\""+hex+"\"> <small>"+ strFormatter(rBDCell.getDescription())+ "</small></p>"      
                                                    + "</html>");  //label.toString()+" GKAN "

                                    else
                                            setText(null);
                            } else
                                    setText(null);
                            this.hasFocus = focus;
                            this.childrenSelected = graph.getSelectionModel()
                                            .isChildrenSelected(view.getCell());
                            this.selected = sel;
                            this.preview = preview;
                            if (this.view.isLeaf()
                                            || GraphConstants.isGroupOpaque(view.getAllAttributes()))
                                    installAttributes(view);
                            else
                                    resetAttributes();
                            return this;
                    }
                    return null;
            }    
                
                
                
                
                
                
		/**
		 */
		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				if (gradientColor != null && !preview) {
					setOpaque(false);
					g2.setPaint(new GradientPaint(0, 0, getBackground(),
							getWidth(), getHeight(), gradientColor, true));
                                        

 
				}
				//g.fillOval(b - 1, b - 1, d.width - b, d.height - b);
                                 g.fillRect (b - 1, b - 1, d.width - b, d.height - b); 
                                 
                                 
                                
                                
			}
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
			if (bordercolor != null) {
				g.setColor(bordercolor);
				g2.setStroke(new BasicStroke(b));
				//g.drawOval(b - 1, b - 1, d.width - b, d.height - b);
                                g.drawRect(b - 1, b - 1, d.width - b, d.height - b);
                                
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(highlightColor);
				//g.drawOval(b - 1, b - 1, d.width - b, d.height - b);
                                g.drawRect(b - 1, b - 1, d.width - b, d.height - b);
                                
			}
                        
                        g.drawRect(b - 1, b - 1, d.width - b, 20);
                        g.setColor(Color.BLACK);
                        //drawString(g,"GEN KAN kk", b+1, 35);
		}
                
                
                public String strFormatter(String str){
                 return str.replaceAll("(.{18})", "$1<br/>");
                }
                
                
                
	}
        
        
        public static void drawString(Graphics g, String text, int x, int y) {
            
           /* StringBuilder sb = new StringBuilder(text);

            int i = 0;
            while ((i = sb.indexOf(" ", i + 16)) != -1) {
                sb.replace(i, i + 1, "\n");
            }*/
            
               for (String line : text.replaceAll("(.{16})", "$1\n").split("\n"))
                   g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
}
