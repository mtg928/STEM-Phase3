/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;


import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.StringFuntions;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Murali
 */
public class DendrogramGraph {

    private static DecimalFormat df = new DecimalFormat("0.0000");
    private int currentX=0;
    private int currentY=0;
    private int nodeLevelCount=0;
    
    public static <T> Node<T> create(T contents){
        Node<T> node = new Node<T>(contents);
        return node;
    }
    public static <T> Node<T> create(Node<T> child0, Node<T> child1){
        Node<T> node = new Node<T>(child0, child1);
        return node;
    }
    

     public static <T> int countLeaves(Node<T> node)
    {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0)
        {
            return 1;
        }
        Node<T> child0 = children.get(0);
        Node<T> child1 = children.get(1);
        return countLeaves(child0) + countLeaves(child1);
    }

    public static <T> int countLevels(Node<T> node)
    {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0)
        {
            return 1;
        }
        Node<T> child0 = children.get(0);
        Node<T> child1 = children.get(1);
        return 1+Math.max(countLevels(child0), countLevels(child1));
    }


    public <T> Point draw(Graphics g, Node<T> node, int y,int frameWidth,int frameHeight,int margin,int widthPerLevel,int heightPerLeaf,List<String> Heading )
    {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0) 
        {
            int x = frameWidth - widthPerLevel - 2 * margin+getCurrentX();
            g.drawString(String.valueOf(node.getContents()), x+8, getCurrentY()+8);
            int resultX = x;
            int resultY = getCurrentY();
            setCurrentY(getCurrentY() + heightPerLeaf);
            return new Point(resultX, resultY);
        }
        if (children.size() >= 2)
        {   
            Node<T> child0 = children.get(0);
            child0.setWidthPerLevel(widthPerLevel);
            Node<T> child1 = children.get(1);
            child1.setWidthPerLevel(widthPerLevel);
            
            Point p0 = draw(g, child0, y,frameWidth,frameHeight,margin,widthPerLevel,heightPerLeaf,Heading);
            Point p1 = draw(g, child1, y+heightPerLeaf,frameWidth,frameHeight,margin,widthPerLevel,heightPerLeaf,Heading);

            g.fillRect(p0.x-2, p0.y-2, 4, 4);
            g.fillRect(p1.x-2, p1.y-2, 4, 4);
            int dx = widthPerLevel;
            int vx = Math.min(p0.x-dx, p1.x-dx);
           // g.drawString("dgggggggg", dx+8, vx+8);
           
           
            //g.drawLine(vx, p0.y, p0.x, p0.y); 
            child0.setP0(new Point(vx, p0.y));
            child0.setP1(new Point(p0.x, p0.y));
            child0.draw2(g); 
            
            //g.drawLine(vx, p1.y, p1.x, p1.y);
            child1.setP0(new Point(vx, p1.y));
            child1.setP1(new Point(p1.x, p1.y));
            child1.draw2(g);
            
            g.drawLine(vx, p0.y, vx, p1.y);

            
            Point p = new Point(vx, p0.y+(p1.y - p0.y)/2);
            nodeLevelCount++;
            drawVerticalLine(g, vx,p0.y,p1.y,p0.x,p1.x,countLeaves(node),node,frameHeight,widthPerLevel,Heading);
            return p;
        }
        // Should never happen
        return new Point();
    }
    
    /**
     * @return the currentY
     */
    public int getCurrentY() {
        return currentY;
    }

    /**
     * @param currentY the currentY to set
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
    
    /**
     * @return the nodeLevelCount
     */
    public int getNodeLevelCount() {
        return nodeLevelCount;
    }

    /**
     * @param nodeLevelCount the nodeLevelCount to set
     */
    public void setNodeLevelCount(int nodeLevelCount) {
        this.nodeLevelCount = nodeLevelCount;
    }
    
    
    
    public void drawVerticalLine(Graphics g,int vx,int vy1,int vy2,int vx1,int vx2,int levels,Node node,int height,int widthPerLevel,List<String> heading){
   
               
        
             Graphics2D g2d = (Graphics2D) g.create();
             Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{1}, 0);
             g2d.setStroke(dashed);
             g2d.setColor(Color.GRAY);
             g2d.drawLine(vx, 0, vx, height);
             
             g2d.setColor(Color.LIGHT_GRAY);
             g2d.fillRect(vx, 0, widthPerLevel, 25);
             g2d.setColor(Color.BLACK);
             g2d.drawRect(vx, 0, widthPerLevel, 25);
             
             List<Node> child = node.getChildren();
             
             g2d.drawString(df.format(child.get(0).getProbability()), vx+3, vy1);
             g2d.drawString(df.format(child.get(1).getProbability()), vx+3, vy2);
             g2d.setColor(new Color(20, 143, 119  ));

             //g2d.drawString(StringFuntions.DoubleRound(child.get(0).getFrequency()), vx1-70,vy1);
             g2d.drawString(NumberConversion.scientificNotation(child.get(0).getFrequency(),4), vx1-70,vy1);
             g2d.setColor(new Color(176, 58, 46));

             //g2d.drawString(StringFuntions.DoubleRound(child.get(1).getFrequency()), vx2-70,vy2);
             g2d.drawString(NumberConversion.scientificNotation(child.get(1).getFrequency(),4), vx2-70,vy2);
            
             
             //g2d.setColor(Color.BLACK);
             //g2d.setFont(new Font("default", Font.BOLD, 12));
             //int maxCount = Math.max(child.get(0).getLevel(), child.get(1).getLevel());
             //setNodeLevelCount(Math.max(maxCount,getNodeLevelCount())) ;
             //g2d.drawString(heading.get(maxCount),vx+15,15);
             //g2d.drawString(heading.get(nodeLevelCount),vx+15,15);
            
             //System.out.println("GGGGGGGGGGGGGGGGGGGGGG "+maxCount+" "+(heading.size()-nodeLevelCount));
             
              

    }
    
    
    public String[] getEventHeading(){
    
     return new String[]{"ACCIDENT","COLLISION / DERAILMENT","PASSENGER KILLED","FATALITY / SURVIVAL"};
    }

    /**
     * @return the currentX
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * @param currentX the currentX to set
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    
    
      
}
