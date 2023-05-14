/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graph.draw2;

import com.graph.draw.Kind;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

   /**
     * A Node represents a node in a graph.
     */

   public class Node {

        private Point p0;
        private Point p1;
        private int r;
        private Color color;
        private Kind kind;
        private boolean selected = false;
        private Rectangle b = new Rectangle();
        private int widthPerLevel=50;

        /**
         * Construct a new node.
         */
        public Node(Point p, int r, Color color, Kind kind) {
            this.p0 = p;
            this.r = r;
            this.color = color;
            this.kind = kind;
              
        }

        /**
         * Calculate this node's rectangular boundary.
         */
        private void setBoundary(Rectangle b) {
            //b.setBounds(200 - r, 100 - r, 2 * r, 2 * r);
           // b.setBounds(getP1().x - r, getP1().y - r, 2 * r, 2 * r);
            b.setBounds(getP1().x - r, getP1().y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw1(Graphics g) {
            g.setColor(this.getColor());
            if (this.kind == Kind.Circular) {
                g.fillOval(b.x, b.y, b.width, b.height);
            } else if (this.kind == Kind.Rounded) {
                g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
            } else if (this.kind == Kind.Square) {
                g.fillRect(b.x, b.y, b.width, b.height);
            }
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }
        
        public void draw2(Graphics g) {
            g.setColor(this.getColor());

            //Point p0 =this.getP0();
            //Point p1 =new Point(p0.x+100, p0.y+200);
            int dx = widthPerLevel;
            int vx = Math.min(p0.x-dx, p1.x-dx);
           //g.drawLine(p.x, p.y, p.x+100, p.y);
           
            g.fillRect((p1.x)-2, p1.y-2, 4, 4);
            //g.fillRect(p1.x-2, p1.y-2, 4, 4);
           
            g.drawLine(p0.x, p0.y, p1.x, p1.y);
            //g.drawLine(vx, p1.y, p1.x, p1.y);
            //g.drawLine(vx, p0.y, vx, p1.y);
            
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
            
           setBoundary(b);
        }

        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return getP0();
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Collected all the selected nodes in list.
         */
        public static void getSelected(List<Node> list, List<Node> selected) {
            selected.clear();
            for (Node n : list) {
                if (n.isSelected()) {
                    selected.add(n);
                }
            }
        }

        /**
         * Select no nodes.
         */
        public static void selectNone(List<Node> list) {
            for (Node n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
         */
        public static boolean selectOne(List<Node> list, Point p) {
            for (Node n : list) {
                System.out.println("**************************"+list);
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        Node.selectNone(list);
                        n.setSelected(true);
                    }
                    return true;
                }
            }
            return false;
        }
        
         /**
         * Select a single node; return true if not already selected.
         */
        public static Node getSelectedOne(List<Node> list, Point p) {
            for (Node n : list) {
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        Node.selectNone(list);
                        n.setSelected(true);
                    }
                    return n;
                }
            }
            return null;
        }

        /**
         * Select each node in r.
         */
        public static void selectRect(List<Node> list, Rectangle r) {
            for (Node n : list) {
                n.setSelected(r.contains(n.getP0()));
            }
        }

        /**
         * Toggle selected state of each node containing p.
         */
        public static void selectToggle(List<Node> list, Point p) {
            for (Node n : list) {
                if (n.contains(p)) {
                    n.setSelected(!n.isSelected());
                }
            }
        }

        /**
         * Update each node's position by d (delta).
         */
        public static void updatePosition(List<Node> list, Point d) {
            for (Node n : list) {
                if (n.isSelected()) {
                    n.p0.x += d.x;
                    n.p0.y += d.y;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's radius r.
         */
        public static void updateRadius(List<Node> list, int r) {
            for (Node n : list) {
                if (n.isSelected()) {
                    n.r = r;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's color.
         */
        public static void updateColor(List<Node> list, Color color) {
            for (Node n : list) {
                if (n.isSelected()) {
                    n.color = color;
                }
            }
        }

        /**
         * Update each node's kind.
         */
        public static void updateKind(List<Node> list,Kind kind) {
            for (Node n : list) {
                if (n.isSelected()) {
                    n.kind = kind;
                }
            }
        }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the p1
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return the p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @param p0 the p0 to set
     */
    public void setP0(Point p0) {
        this.p0 = p0;
    }
    }
