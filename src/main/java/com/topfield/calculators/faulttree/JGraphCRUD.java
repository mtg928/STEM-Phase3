/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.mxgraph.model.mxCell;
import com.topfield.datamodel.FaulttreeNode;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Spfhdr;
import com.topfield.utilities.NumberConversion;
import com.topfield.view.popup.FTABasicPopup;
import com.topfield.view.popup.FTAGatePopup;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Murali
 */
public class JGraphCRUD {
    
    private FaultDataCrud faultDataCrud = new FaultDataCrud();
    private JGraphServices graphServices;
    private FTAGatePopup fTAGatePopup;
    private FTABasicPopup fTABasicPopup;

    public JGraphCRUD(JGraphServices graphServices) {
        this.graphServices = graphServices;
    }
        
    public FTAGatePopup getFTAGatePopup(){
        
        if (fTAGatePopup == null) {
            fTAGatePopup = new FTAGatePopup();
        }else{
            fTAGatePopup.resetAll();
        }
        
      return fTAGatePopup;
    } 
    
    public FTABasicPopup getFTABasicPopup(Spfhdr spfhdr,List<Integer> addedList,boolean editable,boolean bottomUp){
        
        if (fTABasicPopup == null) {
            fTABasicPopup = new FTABasicPopup(spfhdr,addedList,editable,bottomUp);
        }else{
            fTABasicPopup.setSpfhdr(spfhdr);
            fTABasicPopup.setAddedList(addedList);
            fTABasicPopup.setEditable(editable);
            fTABasicPopup.clearAll();
        }
        
      return fTABasicPopup;  
    } 
            
    
    public FaulttreeNode insertORUpdateEvent(mxCell cell,boolean edit,JgraphDrawer graphDrawer){
    
      FTAGatePopup fTAGatePopup = getFTAGatePopup();
      FaulttreeNode node = graphServices.getCellValue(cell);
      FaulttreeNode res = edit ? node: new FaulttreeNode();
      Object[] options1 = {"Save", "Cancel"};
      String category ="X";
      int parentId=-200;
  
      //fTAGatePopup.resetAll();
                   
    if (edit) {

        fTAGatePopup.setComponentName(node.getCompId());
        fTAGatePopup.setDescription(node.getDescription());
        fTAGatePopup.setGate(node.getChildGate());
        fTAGatePopup.setPTIAdjestment(node.getMethods());
        fTAGatePopup.setReference(node.getReference());
        
        if (node.getCategory().equals("A")) {
            fTAGatePopup.setjLabel1("Top Event Id :");
            fTAGatePopup.setjLabel2("Top Event :");
        }
    }

           int result = JOptionPane.showOptionDialog(null, fTAGatePopup, "Enter Details",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options1, null);

            if (result == JOptionPane.YES_OPTION) {
                
                if (edit && (!node.getChildGate().equals(fTAGatePopup.getGate()))) {
                    graphServices.gateGraphChange(graphDrawer.getGraphModel(), cell, fTAGatePopup.getGate(), graphDrawer.getImageURL());
                }   

            if (!edit) {
              res.setId(-100);
              parentId = node.getId();
            }else{
               category = res.getCategory();
               parentId = node.getParentId();
            }
    
            res.setCompId(fTAGatePopup.getComponentName());
            res.setDescription(fTAGatePopup.getDescription());
            res.setType("Event");
            res.setCategory(category);
            res.setChildGate(fTAGatePopup.getGate());
            res.setMethods(fTAGatePopup.getPTIAdjestment());
            res.setFrequency(0.0);
            res.setProbability(0.0);
            res.setTime(0.0);
            res.setParentId(parentId);
            res.setClacId(0);
            res.setReference(fTAGatePopup.getReference());
            
             //clearTopNode();
              return res;
            }else{
            
              return null;
            }
    
    
    }
    
    
    public FaulttreeNode insertORUpdateNode(mxCell cell,boolean edit,JgraphDrawer graphDrawer,boolean bottomUp){
     
      List<Integer> addedList = graphDrawer.getAddedList();
      FaulttreeNode node = graphServices.getCellValue(cell);
      FaulttreeNode res = edit ? node: new FaulttreeNode();
      Object[] options1 = {"Save", "Cancel"};
      int parentId=-200;
      
        if (edit && !bottomUp) {
            addedList.remove(node.getClacId());
        }
        
        FTABasicPopup ftaBasicPopup = getFTABasicPopup(graphDrawer.getHeader(), addedList, edit,bottomUp);
        //ftaBasicPopup.clearAll(); 
        if (edit) {
         
         
        ftaBasicPopup.setFunctionalComponents(node.getClacId());   
        ftaBasicPopup.setComponentName(node.getCompId());
        ftaBasicPopup.setDescription(node.getDescription());
        ftaBasicPopup.setReference(node.getReference());
        ftaBasicPopup.setDataType(faultDataCrud.changeToFullTypeName(node.getCategory()));
        ftaBasicPopup.setFrequency(node.getFrequency());
        ftaBasicPopup.setProbability(node.getProbability());
        ftaBasicPopup.setRepairTime(node.getTime());
            
        }


     int result = JOptionPane.showOptionDialog(null, ftaBasicPopup, "Enter Details",
             JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
             null, options1, null);

     if (result == JOptionPane.YES_OPTION) {
         
            if (!edit) {
              res.setId(-100);
              parentId = node.getId();
            }else{
              parentId = node.getParentId();
            }
    
            res.setCompId(ftaBasicPopup.getComponentName());
            res.setDescription(ftaBasicPopup.getDescription());
            res.setType("Basic");
            res.setCategory(faultDataCrud.changeToShortTypeName(ftaBasicPopup.getDataType()));
            res.setChildGate("");
            res.setMethods("Fixed");
            res.setFrequency(ftaBasicPopup.getFrequency());
            res.setProbability(ftaBasicPopup.getProbability());
            res.setTime(ftaBasicPopup.getRepairTime());
            res.setParentId(parentId);
            res.setClacId((bottomUp&&!edit)? -333:ftaBasicPopup.getFunctionalComponents().getCalcId());
            res.setReference(ftaBasicPopup.getReference());

         //clearTopNode();
          return res;
     }else{
     
          return null;
     }

      
    }
}
