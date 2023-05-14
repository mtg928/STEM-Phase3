/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxConstants;
import com.topfield.calculators.calcfile.CalcCommonService;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FaultdataDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.datamodel.FaulttreeNode;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Spfhdr;
import com.topfield.utilities.FoldableTree;
import com.topfield.view.popup.ProgressBar;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Murali
 */
public class JGraphServices {
    
    public final static int OR = 0;
    public final static int XOR = 1;
    public final static int AND = 2;
    public final static int XAND = 3;
    
    public final static int NODE_EVENT = 4;
    public final static int NODE_BASIC = 5;
    public final static int ALL = 6;
    
    private CalcfileDao calcfileDao = new CalcfileDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    
    public List<Faultdata> getOrderedList(List<Faultdata> ftDatas,ProgressBar progressBar){
      List<Faultdata> refData = new ArrayList<>();
      List<Faultdata> childs = new ArrayList<>();
      List<Faultdata> childsRef;
      
      childs.add(ftDatas.get(0));
      
        for (int i = 0; i < ftDatas.size();) {
            childsRef = new ArrayList<>();
            
            for (Faultdata faultdata : childs) {
               refData.add(faultdata);
               i++;
               
               /* for (Faultdata childsIn : faultdata.getFaultdataCollection()) {
                    childsRef.add(childsIn);
                }*/
               
                for( Faultdata childsIn :faultdataDao.getAllFaultdataBySpfHdr(faultdata.getFaultHeader().getSpfhdrid(),faultdata.getFaultdataId())){
                        childsRef.add(childsIn);
                }
                
                progressBar.setPercentage(i);
            }
            
            childs = childsRef;
            
        }
       
       /* for (Faultdata faultdata : ftDatas) {
            System.out.println("faultdata "+faultdata);
        }
       
        for (Faultdata faultdata : refData) {
            System.out.println("faultdata "+faultdata);
        }*/
    
    
      return refData;
    }
    
    public List<Faultdata> getOrderedList2(List<Faultdata> ftDatas,ProgressBar progressBar){
      List<Faultdata> refData = new ArrayList<>();
      List<Faultdata> childs = new ArrayList<>();
       Faultdata childsRef;
      
      refData.add(ftDatas.get(0));
      for (int i = 0; i < ftDatas.size(); i++) {
          
          childsRef = refData.get(i);
          final int parentId = childsRef.getFaultdataId();
          
          /*for( Faultdata childsIn :faultdataDao.getAllFaultdataBySpfHdr(childsRef.getFaultHeader().getSpfhdrid(),childsRef.getFaultdataId())){
          refData.add(childsIn);
          }*/
          
          //ftDatas.stream().forEach(System.out::println);
          
          ftDatas.stream().filter(f-> ((f.getParent()!=null)&&(f.getParent().getFaultdataId()==parentId) && (f.getParent().getFaultdataId() != f.getFaultdataId())) )/*.collect(Collectors.toList())*/.forEach((childsIn) -> {
              refData.add(childsIn);
              //System.out.println("childsIn - "+childsIn);
          });
           
      }

      /* for (Faultdata faultdata : refData) {
            System.out.println("faultdata "+faultdata);
        }*/
    
      return refData;
    }
    
    
    
    public Faultdata covertGRtoDB(FaulttreeNode ftData,Spfhdr Spfhdr,boolean edit){
    
        Faultdata ft = new Faultdata();
        Calcfile calcFile = null;
       // try {
        
        if (Spfhdr.getApproach()==1 && ftData.getType().equals("Basic")) {
          calcFile = CalcCommonService.createCalcFile(ftData.getClacId(),ftData.getCompId(),ftData.getDescription(),
                     ftData.getFrequency(),ftData.getProbability(),Spfhdr.getSpfid(),edit);
        }else{
           calcFile = calcfileDao.findById(ftData.getClacId());
        }
            
        ft.setFaultdataId(ftData.getId());
        ft.setName(ftData.getCompId());
        ft.setDescription(ftData.getDescription());
        ft.setNode(ftData.getType());
        ft.setCategory(ftData.getCategory());
        ft.setChildGate(ftData.getChildGate());
        ft.setMethods(ftData.getMethods());
        ft.setFailurerate(ftData.getFrequency());
        ft.setProbability(ftData.getProbability());
        ft.setTime(ftData.getTime());
        ft.setParent(ftData.getParentId()==-1 ? null: faultdataDao.findById(ftData.getParentId()));
        ft.setCalcComp(calcFile);
        ft.setFaultHeader(Spfhdr);
        ft.setFdreferences(ftData.getReference());
        
        System.out.println("Node "+ftData.getDescription()+" getParentId - "+ftData.getParentId());
       /* } catch (Exception e) {
            e.printStackTrace();
        }*/
        
  
        return ft;
    }
    
    public Faultdata getFaultdataWithId(List<Faultdata> searchList,int id){
          return searchList.stream()
              .filter(p -> p.getFaultdataId()== id)
              .collect(Collectors.toList()).get(0);
    }
    
    public int getCellType(mxCell cell){
    int res=-1;
    FaulttreeNode fNode;
    
    //System.out.println("cell.getId() "+ cell.getId()+" cell.getValue() "+cell.getValue());
        if (cell == null) {
            
        }else if (cell.getId().contentEquals("OR")) {
            res = OR;
        } else if (cell.getId().contentEquals("AND")) {
             res = AND;
        } else {
             fNode = (FaulttreeNode) cell.getValue();
                 
            if (fNode.getType().contentEquals("Event")) {
               res = NODE_EVENT ;
            } else if (fNode.getType().contentEquals("Basic")) {
               res = NODE_BASIC ;  
            }
    
        }
    
    return res;
    }
    
    
    public List<mxCell> getChildByParent(mxCell parent){
        List<mxCell> res= new ArrayList();
        mxCell gateVer;
        
        if (parent.getEdgeCount()> 0) {
            parent = (mxCell)parent.getEdgeAt(parent.getEdgeCount()-1).getTerminal(false);

            for (int i = 0; i < parent.getEdgeCount(); i++) {
                 gateVer = (mxCell)parent.getEdgeAt(i).getTerminal(false);

                //System.out.println("gateVer - " + gateVer.getId());
                
                if (!(gateVer.equals(parent))) {
                    res.add(gateVer);
                }
                
            }
            
        }
        

        
        
        return res;
    }
    
    
    public double[] calculate(mxCell root,mxIGraphModel model){
    
       List<mxCell> childs = getChildByParent(root);
       FaulttreeNode nodeRoot = getCellValue(root);
       double freProPre[]={0.0,0.0};
       double freProCur[]={0.0,0.0};
       String preCategory;
       String curCategory;
       int gate = nodeRoot.getChildGate().contentEquals("AND") ? FaultTreeEquations.AND : FaultTreeEquations.OR;
       int unRevealedCount=0;
       
        if (childs.size()>0) {
            
            freProPre = getFrequency(childs.get(0),model);
            preCategory =  getCellValue(childs.get(0)).getCategory();
            
             if (preCategory.contentEquals("U")) {
                unRevealedCount++;
             }
       
            for (int i = 1; i < childs.size(); i++) {
                freProCur = getFrequency(childs.get(i),model);
                curCategory = getCellValue(childs.get(i)).getCategory();
                freProPre = FaultTreeEquations.findCutSetBasic(gate, preCategory,curCategory, freProPre[0], freProCur[0],freProPre[1], freProCur[1]);
                
                if (preCategory.contentEquals("U")) {
                  unRevealedCount++;
                }
                
                preCategory = curCategory;
            }
            
               // PTI Adjustment
                if (unRevealedCount > 0 && gate ==FaultTreeEquations.AND ) {
                    freProPre[1]= freProPre[1]*FaultTreeEQ.PTIAdjustment(nodeRoot.getMethods(), unRevealedCount);
                    System.out.println("####### faultdata - "+nodeRoot.getDescription());
                }
            
        } 
        nodeRoot.setFrequency(freProPre[0]);
        nodeRoot.setProbability(freProPre[1]);
        model.setValue(root, nodeRoot);

        return freProPre;
    
    }
    
    public double[] getFrequency(mxCell a,mxIGraphModel model){
      double res[]={0.0,0.0};
      FaulttreeNode nodeA = getCellValue(a);
      
        if (nodeA.getType().contentEquals("Event")) {
           res = calculate(a,model);
           
        } else {
           res[0] = nodeA.getFrequency(); 
           res[1] = FaultTreeEquations.calulateNodeProbability2(nodeA.getType(), nodeA.getCategory(),nodeA.getProbability(), nodeA.getFrequency(), nodeA.getTime());
           nodeA.setProbability(res[1]);   
           model.setValue(a, nodeA);
        }

      return res;
    }
    
    
    public double getFrequency(mxCell a, mxCell b){
      double res=0.0;
      FaulttreeNode nodeA = (FaulttreeNode)a.getValue();
      FaulttreeNode nodeB = (FaulttreeNode)b.getValue();

      res = nodeA.getFrequency()+ nodeB.getFrequency();
      return res;
    }
    
    
    public FaulttreeNode getCellValue(mxCell cell){
    
    return (FaulttreeNode)cell.getValue();
    }
    
    public mxCell getGateCellByParent(mxCell cell){
        
      mxCell res =(mxCell)(cell.getEdgeAt(cell.getEdgeCount()-1).getTerminal(false));
      int cellType = getCellType(res);
      
        if (cellType== JGraphServices.OR || cellType== JGraphServices.AND) {
            return res;
        }else {
            return null;          
        }

    }
    
    public void gateGraphChange(mxIGraphModel model,mxCell cell, String gate,String imageURL){
    
      String style = mxConstants.STYLE_SHAPE+"="+mxConstants.SHAPE_IMAGE+";"+mxConstants.STYLE_IMAGE+"="+imageURL;
      mxCell gateCell = getGateCellByParent(cell);   
      
        System.out.println("88888888888888888888888888888888888888");
       
        if (gateCell != null) {
            
            if (gate.equals("OR")) {
                   style+="FTA_OR.png";

            }else{
                  style+="FTA_AND.png";
            }

            gateCell.setId(gate);        
            model.setStyle(gateCell, style);
            model.setValue(gateCell, gateCell.getId());
            
        }

    }
    
    public void setInsertParentId(mxIGraphModel model,mxCell parent,int id){
        FaulttreeNode node;
        for (mxCell cell : getChildByParent(parent)) {
            node = getCellValue(cell);
            node.setParentId(id);
            model.setValue(cell, node);
        }
    
    }
    
    public static void main(String[] args) {
        FaultdataDao faultdataDao = new FaultdataDaoImpl();
        List<Faultdata> list = faultdataDao.getAllFaultdataBySpfHdr(51);
        JGraphServices jgraphServices = new JGraphServices();
        
       /* for (Faultdata faultdata : list) {
            System.out.println("list"+ faultdata);
        }*/
       
       jgraphServices.getOrderedList2(list,null);
       
       //list.stream().filter(f-> ((f.getParent()!=null)&&(f.getParent().getFaultdataId()==726) && (f.getParent().getFaultdataId() == f.getFaultdataId()) )).collect(Collectors.toList()).forEach(System.out::println);
       
                final int parentId = 636;
          
          /*for( Faultdata childsIn :faultdataDao.getAllFaultdataBySpfHdr(childsRef.getFaultHeader().getSpfhdrid(),childsRef.getFaultdataId())){
          refData.add(childsIn);
          }*/
          
          //ftDatas.stream().forEach(System.out::println);
          
          /*list.stream().filter(f-> ((f.getParent()!=null)&&(f.getParent().getFaultdataId()==parentId) && (f.getParent().getFaultdataId() != f.getFaultdataId())) ).collect(Collectors.toList()).forEach((childsIn) -> {
              //list.add(childsIn);
              System.out.println("childsIn - "+childsIn);
          });*/
    }
    
}