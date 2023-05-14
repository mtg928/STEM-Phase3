/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import com.topfield.utilities.NumberConversion;
import com.topfield.view.popup.RBDSettingsPopup;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class RBDSettings {

    public  List<RBDCell> RBDcellSelector(JGraph graph,String cellType) {
        
        Object obj= null;
        RBDCell refcell =null;
        List<RBDCell> rbdCellList = new ArrayList<>();
        
         for (int i = 0; i < graph.getModel().getRootCount(); i++) {
             
             obj = graph.getModel().getRootAt(i);
            
                if (obj instanceof RBDCell) {
                    refcell = (RBDCell)obj;
                    if (refcell.getVertexStyle().contentEquals(cellType)) {
                        rbdCellList.add(refcell);
                    }
                    
                }
         }
        
        return rbdCellList;
    }
    
    
    
    
    
    public  Map RBDcellSetter(Map attributeMap,double x,double y,double w,double h) {
        
       GraphConstants.setBounds(attributeMap, new Rectangle2D.Double(x, y, w, h)); 

       return attributeMap;
    }
    
    public  Map setRBDBackGroundColour(List<RBDCell> rbdCellList,Color bgColor,Color fontColor,Color borderColor) {
        
        Map nested = new Hashtable();
        Map attributeMap = new Hashtable();
        
        GraphConstants.setGradientColor(attributeMap , bgColor);
        GraphConstants.setForeground(attributeMap , fontColor);
        GraphConstants.setBorderColor(attributeMap, borderColor);
        
        for (RBDCell rBDCell : rbdCellList) {
            
            nested.put(rBDCell, attributeMap);
            System.out.println("rBDCell - "+rBDCell);
        }
        
        return nested;
        
    }
    
    public  void setRBDSettingsPro(JGraph graph,RBDSettingsPopup rBDSettingsPopup) {
        
        Object obj= null;
        RBDCell refcell =null;
        DefaultEdge refEdge = null;

        Map attributeMap = null;

        for (int i = 0; i < graph.getModel().getRootCount(); i++) {
             
             obj = graph.getModel().getRootAt(i);
            
                if (obj instanceof RBDCell) {
                    
                    refcell = (RBDCell)obj;
                    attributeMap = refcell.getAttributes();
                    
                    if (refcell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX)) {
                        
                        rBDSettingsPopup.setRBDVertixBgColor(GraphConstants.getGradientColor(attributeMap));
                        rBDSettingsPopup.setRBDVertixFontColor(GraphConstants.getForeground(attributeMap));
                        rBDSettingsPopup.setRBDVertixBorderColor(GraphConstants.getBorderColor(attributeMap));
                         
                    } else if (refcell.getVertexStyle().contentEquals(RBDCell.VERTEX_GROUP)) {
                        
                        rBDSettingsPopup.setRBDVertixGroupLineColor(GraphConstants.getBorderColor(attributeMap));
                        rBDSettingsPopup.setRBDVertixGroupTextColor(GraphConstants.getForeground(attributeMap));
                         
                    }else if (refcell.getVertexStyle().contentEquals(RBDCell.CAPTION_VERTEX)) {
                        
                        rBDSettingsPopup.setRBDCaptionTextColor(GraphConstants.getForeground(attributeMap));
                        rBDSettingsPopup.setRBDCaptionTextSize(GraphConstants.getFont(attributeMap).getSize());
                        
                    }else if (refcell.getVertexStyle().contentEquals(RBDCell.CON_VERTEX)) {
                        
                         rBDSettingsPopup.setInternalConnBorderStyle(GraphConstants.getBorder(attributeMap) instanceof LineBorder ? 0 :1);
                         rBDSettingsPopup.setInternalConnBorderColor(GraphConstants.getBorderColor(attributeMap));
                    }
                   

                    
                }else if (obj instanceof DefaultEdge) {
                     refEdge =(DefaultEdge)obj;
                     attributeMap = refEdge.getAttributes();
                     
                     rBDSettingsPopup.setRBDArrowLineColor(GraphConstants.getLineColor(attributeMap));
                     rBDSettingsPopup.setRBDArrowLineStyle(GraphConstants.getLineEnd(attributeMap));

                     
                }
         }
        

        
        
        
    }
    
    public  Map setRBDSettings(JGraph graph,RBDSettingsPopup rBDSettingsPopup) {
        
        Object obj= null;
        RBDCell refcell =null;
        DefaultEdge refEdge = null;
        
        Map nested = new Hashtable();
        Map attributeMap = null;

        for (int i = 0; i < graph.getModel().getRootCount(); i++) {
             
             obj = graph.getModel().getRootAt(i);
            
                if (obj instanceof RBDCell) {
                    
                    refcell = (RBDCell)obj;
                    attributeMap = new Hashtable();
                    
                    if (refcell.getVertexStyle().contentEquals(RBDCell.RBD_VERTEX)) {
                        GraphConstants.setGradientColor(attributeMap , rBDSettingsPopup.getRBDVertixBgColor());
                        GraphConstants.setForeground(attributeMap , rBDSettingsPopup.getRBDVertixFontColor());
                        GraphConstants.setBorderColor(attributeMap, rBDSettingsPopup.getRBDVertixBorderColor());
                         
                    } else if (refcell.getVertexStyle().contentEquals(RBDCell.VERTEX_GROUP)) {
                         GraphConstants.setBorder(attributeMap, BorderFactory.createDashedBorder(rBDSettingsPopup.getRBDVertixGroupLineColor()));
                         GraphConstants.setBorderColor(attributeMap, rBDSettingsPopup.getRBDVertixGroupLineColor());
                         GraphConstants.setForeground(attributeMap , rBDSettingsPopup.getRBDVertixGroupTextColor());
                         
                    }else if (refcell.getVertexStyle().contentEquals(RBDCell.CAPTION_VERTEX)) {
                         GraphConstants.setForeground(attributeMap , rBDSettingsPopup.getRBDCaptionTextColor());
                         GraphConstants.setFont(attributeMap , new Font("TimesRoman", Font.PLAIN, rBDSettingsPopup.getRBDCaptionTextSize()));
                    }else if (refcell.getVertexStyle().contentEquals(RBDCell.CON_VERTEX)) {
                         GraphConstants.setBorder(attributeMap, rBDSettingsPopup.getInternalConnBorderStyle()==1? BorderFactory.createDashedBorder(rBDSettingsPopup.getInternalConnBorderColor()):BorderFactory.createLineBorder(rBDSettingsPopup.getInternalConnBorderColor()));
                         GraphConstants.setBorderColor(attributeMap, rBDSettingsPopup.getInternalConnBorderColor());
                         GraphConstants.setForeground(attributeMap , rBDSettingsPopup.getRBDArrowLineColor());
                    }
                   
                    nested.put(refcell, attributeMap);
                    
                }else if (obj instanceof DefaultEdge) {
                     refEdge =(DefaultEdge)obj;
                     attributeMap = new Hashtable();
                     
                     GraphConstants.setLineColor(attributeMap, rBDSettingsPopup.getRBDArrowLineColor());    
                     GraphConstants.setLineEnd(attributeMap, rBDSettingsPopup.getRBDArrowLineStyle()); //ARROW_TECHNICAL
                     
                    nested.put(refEdge, attributeMap);
                }
         }
        

        
        return nested;
        
    }
    
    
    public  List<Map> RBDJsontoMap(JSONObject json) {
        
        List<Map> attributeMaps = new ArrayList<Map>();
        
        Map vertexMap = new Hashtable();
        JSONObject jsonVertex = json.getJSONObject("vertex");   //-16776961 //new Color(NumberConversion.NVL(jsonVertex.get("bgcolor")+"", 0))
        GraphConstants.setGradientColor(vertexMap ,new Color(NumberConversion.NVL(jsonVertex.get("bgcolor")+"", 0)));  //strToColor(jsonVertex.get("bgcolor")+"")
        GraphConstants.setForeground(vertexMap , new Color(NumberConversion.NVL(jsonVertex.get("fontcolor")+"", 0)));  //strToColor(jsonVertex.get("fontcolor")+"")
        GraphConstants.setBorderColor(vertexMap, new Color(NumberConversion.NVL(jsonVertex.get("bordercolor")+"", 0))); //strToColor(jsonVertex.get( "bordercolor")+"")
        attributeMaps.add(vertexMap);
        
        Map vertexGroupMap = new Hashtable();
        JSONObject jsonVertexGroup = json.getJSONObject("vertexgroup");   //-16776961 //new Color(NumberConversion.NVL(jsonVertex.get("bgcolor")+"", 0))
        GraphConstants.setBorderColor(vertexGroupMap, new Color(NumberConversion.NVL(jsonVertexGroup.get("color")+"", 0))); //strToColor(jsonVertex.get( "bordercolor")+"")
        GraphConstants.setForeground(vertexGroupMap , new Color(NumberConversion.NVL(jsonVertexGroup.get("textcolor")+"", 0)));  //strToColor(jsonVertex.get("fontcolor")+"")
        attributeMaps.add(vertexGroupMap);
        
        Map lineMap = new Hashtable();
        JSONObject jsonLine = json.getJSONObject("line");    
        GraphConstants.setLineColor(lineMap , new Color(NumberConversion.NVL(jsonLine.get("color")+"", 0)));  //strToColor(jsonLine.get("color")+"")
        GraphConstants.setLineEnd(lineMap , NumberConversion.NVL(jsonLine.get("style")+"", 0));
        attributeMaps.add(lineMap);
        
        
        Map textMap = new Hashtable();
        JSONObject jsonText = json.getJSONObject("text");    
        GraphConstants.setForeground(textMap , new Color(NumberConversion.NVL(jsonText.get("color")+"", 0)));  //strToColor(jsonText.get("color")+"")
        GraphConstants.setFont(textMap , new Font("TimesRoman", Font.PLAIN, NumberConversion.NVL(jsonText.get("size")+"", 0)));
        attributeMaps.add(textMap);
        
        
        Map connectorMap = new Hashtable();
        JSONObject jsonConnector = json.getJSONObject("connector"); 
        Color borderColor = new Color(NumberConversion.NVL(jsonConnector.get("color")+"", 0));  //strToColor(jsonConnector.get("color")+"")
        GraphConstants.setBorderColor(connectorMap , borderColor);
        GraphConstants.setBorder(connectorMap , NumberConversion.NVL(jsonConnector.get("border")+"", 0)==0? BorderFactory.createDashedBorder(borderColor):BorderFactory.createLineBorder(borderColor) );
        attributeMaps.add(connectorMap);
        
        
        
        //System.out.println(GraphConstants.getGradientColor(attributeMap));
        
        
        return attributeMaps;
    }
    
    public  JSONObject RBDMaptoJson(RBDCell vertex,RBDCell vertexGroup,DefaultEdge line,RBDCell text,RBDCell connector) {
       JSONObject json = new JSONObject();
       
       Map vertexMap =getVertixMap(vertex);
       Map vertexGroupMap =getVertixGroupMap(vertexGroup);
       Map lineMap =getLineMap(line);
       Map textMap = getTextMap(text);
       Map connectorMap = getConnectorMap(connector);
       
       JSONObject jsonVertex = new JSONObject();
       jsonVertex.put("bgcolor", String.valueOf(GraphConstants.getGradientColor(vertexMap).getRGB()));
       jsonVertex.put("fontcolor", String.valueOf(GraphConstants.getForeground(vertexMap).getRGB()));
       jsonVertex.put("bordercolor", String.valueOf(GraphConstants.getBorderColor(vertexMap).getRGB()));
       json.put("vertex", jsonVertex);
       
       JSONObject jsonVertexGroup = new JSONObject();
       jsonVertexGroup.put("color", String.valueOf(GraphConstants.getBorderColor(vertexGroupMap).getRGB()));
       jsonVertexGroup.put("textcolor", String.valueOf(GraphConstants.getForeground(vertexGroupMap).getRGB()));
       //jsonVertexGroup.put("size", GraphConstants.getBorder(vertexGroupMap) instanceof LineBorder ? 1 :0 );
       json.put("vertexgroup", jsonVertexGroup);
       
       JSONObject jsonLine = new JSONObject();
       jsonLine.put("color", String.valueOf(GraphConstants.getLineColor(lineMap).getRGB()));
       jsonLine.put("style", String.valueOf(GraphConstants.getLineStyle(lineMap)));
       json.put("line", jsonLine);
       
       JSONObject jsonText = new JSONObject();
       jsonText.put("color", String.valueOf(GraphConstants.getForeground(textMap).getRGB()));
       jsonText.put("size", GraphConstants.getFont(textMap).getSize());
       json.put("text", jsonText);
       
       JSONObject jsonConnector = new JSONObject();
       jsonConnector.put("color", String.valueOf(GraphConstants.getBorderColor(connectorMap).getRGB()));
       jsonConnector.put("border", GraphConstants.getBorder(connectorMap) instanceof LineBorder ? "1" :"0" );
       json.put("connector", jsonConnector);
       
       
       
       return json;
       
   }
    
    
    public  Map getVertixMap(RBDCell vertex){
        
        Map vertexMap = null;
      
        if (vertex == null) {
          vertexMap= new Hashtable(); 
          GraphConstants.setGradientColor(vertexMap , Color.BLUE);
          GraphConstants.setForeground(vertexMap , new Color(51, 119, 51));
          GraphConstants.setBorderColor(vertexMap, Color.BLACK);

            
        } else {
            
            vertexMap = vertex.getAttributes();
        }

      return vertexMap;
    } 
    
    public  Map getVertixGroupMap(RBDCell vertexGroup){
        
        Map vertexGroupMap = null;
      
        if (vertexGroup == null) {
          vertexGroupMap= new Hashtable(); 
            GraphConstants.setBorder(vertexGroupMap, BorderFactory.createDashedBorder(Color.RED));
            GraphConstants.setBorderColor(vertexGroupMap, Color.RED);
            GraphConstants.setForeground(vertexGroupMap , Color.BLACK);
            //GraphConstants.setVerticalAlignment (vertexGroupMap, SwingConstants.BOTTOM);
            //GraphConstants.setHorizontalAlignment(vertexGroupMap, SwingConstants.CENTER);

            
        } else {
            
            vertexGroupMap = vertexGroup.getAttributes();
        }

      return vertexGroupMap;
    } 
    
    
    public  Map getLineMap(DefaultEdge line){
        
        Map lineMap = null;
      
        if (line == null) {
            lineMap= new Hashtable(); 
            
         GraphConstants.setLineColor(lineMap, Color.BLACK);    
         GraphConstants.setLineEnd(lineMap, GraphConstants.ARROW_NONE); //ARROW_TECHNICAL
   
        } else {
            
            lineMap = line.getAttributes();
        }

      return lineMap;
    } 
    
    
    public  Map getTextMap(RBDCell text){
        
        Map textMap = null;
      
        if (text == null) {
          textMap= new Hashtable();
          GraphConstants.setForeground(textMap , Color.BLACK);
          GraphConstants.setFont(textMap , new Font("TimesRoman", Font.PLAIN, 20));

            
        } else {
            
            textMap = text.getAttributes();
        }

      return textMap;
    } 
    
    public  Map getConnectorMap(RBDCell connector){
        
        Map connectorMap = null;
      
        if (connector == null) {
          connectorMap= new Hashtable();
           GraphConstants.setBorder(connectorMap, BorderFactory.createDashedBorder(Color.RED));
           GraphConstants.setBorderColor(connectorMap, Color.BLACK);
            
        } else {
            
            connectorMap = connector.getAttributes();
        }

      return connectorMap;
    } 
    
    
    
    
     public  Color strToColor(String colour) {
        
       Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(colour);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
        return color;
    }
     
    /**
    * 
    * @param colorStr e.g. "#FFFFFF"
    * @return 
    */
   public  Color hex2Rgb(String colorStr) {
       return new Color(
               Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
               Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
               Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
   }
    
}
