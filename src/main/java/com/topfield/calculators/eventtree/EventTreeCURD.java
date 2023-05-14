/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.topfield.dao.EventTreeDao;
import com.topfield.modal.Eventtree;
import com.topfield.modal.Eventtreehdr;
import com.topfield.singleton.DaoInstances;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.StringFuntions;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Murali
 */
public class EventTreeCURD {
    
     private static EventTreeDao eventDao = DaoInstances.getEventTreeDao();
    
    public static Eventtree covertNodeEventTree(Node node,String levelHeading,Eventtreehdr eventHdr){
        
         Optional<Node> parent = Optional.ofNullable(node.getParent());
        
         System.out.println(  ", level=" + node.getLevel() + " - "+getLevel(node)+
                 " levelHeading"+levelHeading+ ", frequency=" +
                 node.getFrequency()+", contents=" 
                 + StringFuntions.NVL(node.getContents(),"Null")+
                 " Parent "+  (parent.isPresent() ?  node.getParent().getId() :"null") );
        
        Eventtree eventtree = new Eventtree();

        eventtree.setEventLevel(getLevel(node));
        eventtree.setEventName(levelHeading);
        eventtree.setProbs(NumberConversion.NVLDouble(node.getProbability()+"", 0));
        eventtree.setDirections(StringFuntions.NVL(node.getContents(),""));
        eventtree.setEventParent((parent.isPresent() ?  node.getParent().getId() :-1));
        eventtree.setEventHdr(eventHdr);
        
        if (node.getId() < 0) {
            int id = eventDao.Save(eventtree);
            node.setId(id);
        } else {
            eventtree.setEventId(node.getId());
            eventDao.Update(eventtree);
        }
        
        return eventtree;
    }
    
    
    public static int getLevel(Node node){
        int level =0;
       Optional<Node> parent = Optional.ofNullable(node.getParent());
        
         while(parent.isPresent()){
             
             parent = Optional.ofNullable(parent.get().getParent());
             level++;
         }
        
        return level;
    }
    
    
    
}
