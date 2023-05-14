/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.topfield.utilities.ListToArray;
import com.topfield.utilities.StringFuntions;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Murali
 */
public class EventTreeModel extends JPanel {
    
    private ArrayList<Integer> x_coordinates = new ArrayList<>();
    private int width= 0;
    private int height= 0;
    int [] x_coordinate = new int[20];
    int [][] y_coordinates = new int[10][3];
    int nodeLevelCount=0;
    private EventTreeData eventTreeData= null;
    private EventTreeCalculator parent= null;
    private double treeTotal;
 
    private static <T> Node<T> create(T contents)
    {
        return new Node<T>(contents);
    }
    private static <T> Node<T> create(Node<T> child0, Node<T> child1)
    {
        return new Node<T>(child0, child1);
    }


    private Node<String> root;
    private int leaves;
    private int levels;
    private int heightPerLeaf;
    private int widthPerLevel;
    private int currentY;
    private final int margin = 25;
   // private String[] toppings = new String[]{"FATALITY / SURVIVAL","PASSENGER KILLED","COLLISION / DERAILMENT","ACCIDENT","IEF"};
    //private String[] toppings = new String[]{"IEF","ACCIDENT","COLLISION / DERAILMENT","PASSENGER KILLED","FATALITY / SURVIVAL"}; 
    //private String[] toppings = new String[]{"COLLISION / DERAILMENT","ACCIDENT","IEF"}; 
  

    public EventTreeModel(EventTreeCalculator parent,int width,int height)           
    {
        this.parent = parent;
        this.eventTreeData = (EventTreeData)parent.getComponent(1);
        Double intPro = eventTreeData.getIntialProbaVal();
        this.width =width;
        this.height = height;
     
       List<Node> previous ;
       List<Node> next ;
       
        System.out.println("        Double intPro = eventTreeData.getIntialProbaVal()" +""+eventTreeData.getIntialProbaVal());
       
        //setLayout(new BorderLayout());
       // setLayout(new BoxLayout(this,  BoxLayout.PAGE_AXIS));
        //setPreferredSize(new Dimension(WIDTH, 1600));
        
        root = create(intPro.toString());
        root.setProbability(1.00);
        root.setFrequency(intPro);
        previous= new ArrayList<Node>();
        previous.add(root);
        
        for (Object[] objects : eventTreeData.getAllDatas()) {
           next = new ArrayList<Node>();
            
            nodeLevelIterator(previous,next, (objects[3]+"").split("\\#"), Integer.parseInt(objects[0]+""));
            
            previous = next;

        }
     
       /* for (int i = 1; i <= toppings.length; i++) {
            next = new ArrayList<Node>();
            
            for (Node pre : previous) {
                next.add(create("4.71E-5"));
                next.add(create("5.71E-5"));
                
                pre.setChildrens(next.get(next.size()-2), next.get(next.size()-1));
            }
            previous = next;
            
        }*/
        
      /*  root = create("1.7E-5"); 
        
        node = new ArrayList<Node>();
        node.add(root);
        node.get(0).setChildrens(create("4.71E-5"), create("5.71E-5"));*/
        
        
        
       /* 
        
       root =
            create(
                create(
                    create("4.7E-5"),
                    create(
                        create("1.7E-5"),
                        create(
                            create("6.71E-5"), 
                            create("4.71E-5")
                        )
                    )
                ),
                create(
                    create(
                        create("0.7E-5"),
                        create("8.72E-5")
                    ),
                    create(
                        create("2.7E-5"),
                        create(
                            create("7.7E-5"),
                            create(
                                create("8.7E-5"),
                                create("5.7E-5")
                            )
                        )
                    )
                )
            );*/
        
        
       

    }
    
    private void nodeLevelIterator(List<Node> parent,List<Node> nextParent, String [] prob, int level){
        
        
        //System.out.println("level - "+(Math.pow(2, level)/2));
    
        for (int i = 0; i < (Math.pow(2, level)/2); i++) {
            
            Double res = Double.parseDouble(prob[i]);
            
            if (res != 0.0) {
                
                for (Node node :  createSingleNode(parent.get(i),res )) {
                    nextParent.add(node);
                }                

            }
        }
        
    
    }
    
    
    private List<Node> createSingleNode(Node parent,Double prob ){
    
        List<Node> createdNode = new ArrayList<Node>();
        
        //System.out.println("pre.getProbability() - "+pre.getProbability());
                
            Node n1 = create("");
            n1.setParent(parent);
            n1.setProbability(prob);
            n1.setFrequency((parent.getFrequency()*prob));
            
            Node n2 = create("");
            n2.setParent(parent);
            n2.setProbability((1-prob));
            n2.setFrequency(parent.getFrequency()*(1-prob));
                
            createdNode.add(n1);  //intPro.toString()
            createdNode.add(n2);  //"5.71E-5"
                
            parent.setChildrens(createdNode.get(createdNode.size()-2), createdNode.get(createdNode.size()-1));
    
    
     return createdNode;
    }

    private static <T> int countLeaves(Node<T> node)
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

    private static <T> int countLevels(Node<T> node)
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

    

   @Override
    protected void paintComponent(Graphics gr)
    {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D)gr;

        leaves = countLeaves(root);
        levels = countLevels(root);
        //heightPerLeaf = ((getHeight()*2) - margin - margin) / leaves;
       // widthPerLevel = (getWidth() - margin - margin)/ levels;
        heightPerLeaf = ((height) - margin - margin) / leaves;
        widthPerLevel = (width - margin - margin)/ levels;
        currentY = 0;
        nodeLevelCount=0;

        //System.out.println("getWidth() ="+getWidth()+" getHeight() "+getHeight());
         treeTotal=0;
        g.translate(margin+150, margin+40);
        draw(g, root, 0);
        g.drawLine(-150, currentY/4, 0, currentY/4); 
        g.setColor(Color.BLUE);
        g.drawString(eventTreeData.getIntialProbaVal()+"",-150, currentY/4);
             /*g.setColor(Color.GRAY);
             g.fillRect(-1*widthPerLevel+5, -60, widthPerLevel, 25);
             g.setColor(Color.BLACK);
             g.drawRect(-1*widthPerLevel+5, -60, widthPerLevel, 25);
             g.drawString("IEF",-140,-40);*/
       //this.setPreferredSize(new Dimension(getWidth()*2,getHeight()*2));
       
       
       /* for (Integer x_coordinate1 : ListToArray.removeDuplicates(x_coordinates)) {
            System.out.println("x_coordinate1 " + x_coordinate1);
        }*/
        
               
               
    }
    
 /*   @Override
   public Dimension getPreferredSize() {
    return new Dimension(SPA_WIDTH, SPA_HEIGHT);
  }*/


    private <T> Point draw(Graphics g, Node<T> node, int y)
    {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0)
        {
            int x = getWidth() - widthPerLevel - 2 * margin;
            g.drawString(String.valueOf(node.getContents()), x+8, currentY+8);
            int resultX = x;
            int resultY = currentY;
            currentY += heightPerLeaf;
            return new Point(resultX, resultY);
        }
        if (children.size() >= 2)
        {
            Node<T> child0 = children.get(0);
            Node<T> child1 = children.get(1);
            Point p0 = draw(g, child0, y);
            Point p1 = draw(g, child1, y+heightPerLeaf);

            g.fillRect(p0.x-2, p0.y-2, 4, 4);
            g.fillRect(p1.x-2, p1.y-2, 4, 4);
            int dx = widthPerLevel;
            int vx = Math.min(p0.x-dx, p1.x-dx);
            g.drawLine(vx, p0.y, p0.x, p0.y);
            g.drawLine(vx, p1.y, p1.x, p1.y);
            g.drawLine(vx, p0.y, vx, p1.y);
            Point p = new Point(vx, p0.y+(p1.y - p0.y)/2); 
            drawVerticalLine(g, vx,p0.y,p1.y,p0.x,p1.x,countLevels(node),node);

            //System.out.println("yyyyyyyyyyyyyyyyyyy "+countLevels(node));
             //g.setColor(Color.BLACK);
            return p;
        }
        // Should never happen
        return new Point();
    }
    
    public void drawVerticalLine(Graphics g,int vx,int vy1,int vy2,int vx1,int vx2,int levels,Node node){
        
        String title="";
        int levelcount = (eventTreeData.getAllDatas().length+1 )- (levels);
        //double probality = eventTreeData.getIntialProbaVal();
        //System.out.println(node.getParent());
        List<Node> child = node.getChildren();
        
        
        double probality  ;
        try {
            probality = (node.getParent() == null) ? eventTreeData.getIntialProbaVal() : node/*.getParent()*/.getProbability() ;
        } catch (Exception e) {
            probality = eventTreeData.getIntialProbaVal();
            System.out.println("Error - "+levels);
        }
       
        //int levelcount = 0;
        //int precount = 0;
        Object data[][] = eventTreeData.getAllDatas();
        
       
    
             Graphics2D g2d = (Graphics2D) g.create();
             Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{1}, 0);
             g2d.setStroke(dashed);
             g2d.setColor(Color.GRAY);
             g2d.drawLine(vx, -80, vx, getHeight()-50);
             x_coordinates.add(vx);
             
             //System.out.println("vxvxvxvxvxvxvx "+vx); 
             
             
              if(levels ==6){
                title = "IEF";
              }else if(levels ==5){
                 title = "ACCIDENT";
              }else if(levels ==4){
                 title = "COLLISION / DERAILMENT";
              }else if(levels ==3){
                 title = "PASSENGER KILLED";
              }else if(levels ==2){
                 title = "FATALITY / SURVIVAL";
              }else if(levels ==0){
                 title = "XXXX";
              }else {
                 title = "XXXX"+factorialFounders(nodeLevelCount);
                 
                  //System.out.println("88888888888888888888888888 - "+nodeLevelCount);
              }
             
              if (levels ==2) {
                  //treeTotal = treeTotal+probality*(Double.parseDouble(data[levelcount][6]+"")+ Double.parseDouble(data[levelcount][6]+""));
                  System.out.println("Probality ---- "+child.get(0).getFrequency()+"");
                  treeTotal = treeTotal+child.get(0).getFrequency();
            
              }
            
             ((EventTreeCal)parent.getComponent(2)).getEventTotal().setText(StringFuntions.DoubleRound(treeTotal));
              
             g2d.setColor(Color.GRAY);
             g2d.fillRect(vx, -60, widthPerLevel, 25);
             g2d.setColor(Color.BLACK);
             g2d.drawRect(vx, -60, widthPerLevel, 25);
             //g2d.drawString("0.9", vx+3, vy1);
             //g2d.drawString("0.1", vx+3, vy2);
             g2d.drawString(child.get(0).getProbability()+"", vx+3, vy1);
             g2d.drawString(child.get(1).getProbability()+"", vx+3, vy2);
             g2d.setColor(new Color(20, 143, 119  ));
             //g2d.drawString(StringFuntions.DoubleRound(probality*Double.parseDouble(data[levelcount][6]+""))+"", vx1-50,vy1);
             g2d.drawString(StringFuntions.DoubleRound(child.get(0).getFrequency()), vx1-50,vy1);
             g2d.setColor(new Color(176, 58, 46));
             //g2d.drawString(StringFuntions.DoubleRound(probality*Double.parseDouble(data[levelcount][6]+""))+"", vx2-50,vy2);
             g2d.drawString(StringFuntions.DoubleRound(child.get(1).getFrequency()), vx2-50,vy2);
             g2d.setColor(Color.BLACK);
             g2d.setFont(new Font("default", Font.BOLD, 12));
             g2d.drawString(data[levelcount][2]+"",vx+15,-40);
        //System.out.println("nodeLevelCount - "+nodeLevelCount+"levels -"+(nodeLevelCount/levels)+" x_coordinates - "+x_coordinates.get(nodeLevelCount)+" - "+factorialFounders(nodeLevelCount));
        nodeLevelCount++;
    }
    
     public void drawhorizontalLine(Graphics g){
    
    /* //Get the current size of this component
         Dimension d = this.getSize();

         //draw in black
         g.setColor(Color.BLACK);
         //draw a centered horizontal line
         g.drawLine(0,d.height/2,d.width,d.height/2);
    
       Line2D verticalLine;
       Dimension prefPanelSize = new Dimension(450, 450);
       verticalLine = new Line2D.Float(prefPanelSize.width / 2, 0,
                prefPanelSize.width / 2, prefPanelSize.height);
       g.draw(verticalLine);  */
    
                //vertical line
            g.setColor(Color.red);
            g.drawLine(10, 200, 10, 120);
    
    }
     
     public ArrayList<Integer> getTreeLevels(){
    // System.out.println("guk,,v,hjfhj,jjhhhhhhhhhhhhhhhhhhh");
      return ListToArray.removeDuplicates(x_coordinates);
         
     }
     
     
     public int factorialFounders(int input){
     
         int ref =0;
         int ref1 =0;
         int count =0;
         
         ref = 0;
         
         while(input >= ref){
          /*   ref = 2^count;
             ref1 = 2^(count+1);
             
             if(input >= ref && input <= ref1 ){
               break;
             }*/
             //System.out.println("yghjhh - "+ref);
              count++;
              ref = (int) Math.pow(2,count);
         }
         
     
      return (count-1);
     }
    
     public Double getTreeTotal(){
     
     return treeTotal;
     }
   
    
    
}
