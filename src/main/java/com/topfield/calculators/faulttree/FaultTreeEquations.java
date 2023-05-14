/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.modal.Faultdata;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Murali
 */
public class FaultTreeEquations {

    public final static int OR = 0;
    public final static int XOR = 1;
    public final static int AND = 2;
    public final static int XAND = 3;

    public static void calulateNodeProbability( JTable jtFaultTree) {
        // Calculate NodeProbablity
        for (int i = 0; i < jtFaultTree.getRowCount(); i++) {

            if (jtFaultTree.getValueAt(i, 2).equals("Basic")) {

                if ((jtFaultTree.getValueAt(i, 8)+"").charAt(0) =='R') { //.equals("Revealed Failure")
                    jtFaultTree.setValueAt(FaultTreeEQ.calRevealedProbality(Double.parseDouble(jtFaultTree.getValueAt(i, 5) + ""), Double.parseDouble(jtFaultTree.getValueAt(i, 7) + "")), i, 6);
                } else if ((jtFaultTree.getValueAt(i, 8)+"").charAt(0)=='U') { //.equals("Unrevealed Failure"
                    jtFaultTree.setValueAt(FaultTreeEQ.calUnRevealedProbality(Double.parseDouble(jtFaultTree.getValueAt(i, 5) + ""), Double.parseDouble(jtFaultTree.getValueAt(i, 7) + "")), i, 6);
                }

            }
        }

    }  
    
    public static double calulateNodeProbability(String type,String category,double failureRate, double repairTime) {
        // Calculate NodeProbablity
        double res=0.0;   

            if (type.equals("Basic")) {

                if ((category).charAt(0) =='R') { //.equals("Revealed Failure")
                    res= FaultTreeEQ.calRevealedProbality(failureRate, repairTime);
                } else if ((category).charAt(0)=='U') { //.equals("Unrevealed Failure"
                    res= FaultTreeEQ.calUnRevealedProbality(failureRate, repairTime);
                }

            }
        return res;

    }
    
    public static double calulateNodeProbability2(String type,String category,double probability,double failureRate, double repairTime) {
        // Calculate NodeProbablity
        double res=0.0;   

            if (type.equals("Basic")) {

                if ((category).charAt(0) =='R') { //.equals("Revealed Failure")
                    res= FaultTreeEQ.calRevealedProbality(failureRate, repairTime);
                } else if ((category).charAt(0)=='U') { //.equals("Unrevealed Failure"
                    res= FaultTreeEQ.calUnRevealedProbality(failureRate, repairTime);
                } else if ((category).charAt(0)=='P') { //.equals("Unrevealed Failure"
                    res= probability;
                } 

            }
        return res;

    }
    
    
    

    public static double calPerentProCutSet(Faultdata faultdata, FaultTreeData fTDPanel) {
        double res = 0.0;
        String[] childs;

        List<Faultdata> childdata = new ArrayList<>();
        int gate = faultdata.getChildGate().contentEquals("AND") ? FaultTreeEquations.AND : FaultTreeEquations.OR;

        for (String faultId : faultdata.getChild().split("\\#")) {

            childdata.add(fTDPanel.getFaultdataByRowId(fTDPanel.findRowByFaultId(faultId)));
            // System.out.println(getFaultdataByRowId(findRowByFaultId(faultId)).getName());
        }

        return res;
    }

    public static double[] findCutSetNode(Faultdata faultdata, FaultTreeData fTDPanel) {
        double res[] = {0.0, 0.0};
        int unRevealedCount=0;
        Faultdata ftdata = null;

        String[] childs;

        System.out.println("------------------------" + faultdata.getName() + "----------------------------------------");

        List<Faultdata> childdata = new ArrayList<>();
        int gate = faultdata.getChildGate().contentEquals("AND") ? FaultTreeEquations.AND : FaultTreeEquations.OR;

        for (String faultId : faultdata.getChild().split("\\#")) {
            ftdata = fTDPanel.getFaultdataByRowId(fTDPanel.findRowByFaultId(faultId));
            childdata.add(ftdata);
             if (ftdata.getCategory().contentEquals("U")) {
                unRevealedCount++;
             }
            // System.out.println(getFaultdataByRowId(findRowByFaultId(faultId)).getName());
        }

        res = findCutSetBasic(childdata, gate, fTDPanel);

        if (unRevealedCount > 0 && gate ==FaultTreeEquations.AND ) {
            res[1]= res[1]*FaultTreeEQ.PTIAdjustment(faultdata.getComments(), unRevealedCount);
            System.out.println("####### faultdata - "+faultdata.getName());
        }

        fTDPanel.updateTableCalclatedProbablityById(res[0], res[1], faultdata.getFaultdataId());

        System.out.println("------------------------ Prob of" + faultdata.getName() + " - " + res[0] + " - " + res[0] + "------------------------");

        return res;
    }

    public static double[] findCutSetBasic(List<Faultdata> childdata, int gate, FaultTreeData fTDPanel) {
        double res[] = {0.0, 0.0};
        double temp[] = {0.0, 0.0};
        int count = 0;
        String perviousType = "";

        if (gate == FaultTreeEquations.OR) {
            for (Faultdata faultdata : childdata) {
                temp = getProbabilityCutset(faultdata, fTDPanel);
                System.out.println("Cut Set - " + faultdata.getName() + " (" + faultdata.getChildGate() + ") - Prob - " + temp[0] + " - Fre - " + temp[1]);

                if (count == 0 || faultdata.getCategory().contentEquals("A") || faultdata.getCategory().contentEquals("X")) {
                    res[0] += temp[0];
                    res[1] += temp[1];
                } else {
                    res[0] = FaultTreeFreCal.getFreSelector(res[0], res[1], perviousType, temp[0], temp[1], faultdata.getCategory(), gate);
                    res[1] = FaultTreeEQ.getProSelector(res[1], perviousType, temp[1], faultdata.getCategory(), gate);
                }

                perviousType = faultdata.getCategory();
                count++;
            }

        } else if (gate == FaultTreeEquations.AND) {
            res[1] = 1.0;
            for (Faultdata faultdata : childdata) {
                temp = getProbabilityCutset(faultdata, fTDPanel);
                //res[0] = res[0]*temp[0];

                if (count == 0 || faultdata.getCategory().contentEquals("A") || faultdata.getCategory().contentEquals("X")) {
                    res[0] = (res[0] * temp[1]) + (res[1] * temp[0]);
                    res[1] = res[1] * temp[1];
                } else {
                    res[0] = FaultTreeFreCal.getFreSelector(res[0], res[1], perviousType, temp[0], temp[1], faultdata.getCategory(), gate);
                    res[1] = FaultTreeEQ.getProSelector(res[1], perviousType, temp[1], faultdata.getCategory(), gate);
                }

                perviousType = faultdata.getCategory();

                System.out.println("Cut Set - " + faultdata.getName() + " (" + faultdata.getChildGate() + ") - Prob - " + temp[0] + " - Fre - " + temp[1]);
                count++;
            }
        }

        return res;
    }
    
    
     public static double[] findCutSetBasic(int gate,String categoryA,String categoryB,double fRateA,double fRateB,double proA,double proB) {
        double res[] = {0.0, 0.0};

        if (gate == FaultTreeEquations.OR) {
            

                if ( categoryB.contentEquals("A") || categoryB.contentEquals("X")) {
                    res[0] = fRateA+fRateB;
                    res[1] = proA+proB;
                } else {
                    res[0] = FaultTreeFreCal.getFreSelector(fRateA, proA, categoryA, fRateB, proB, categoryB, gate);
                    res[1] = FaultTreeEQ.getProSelector(proA, categoryA, proB, categoryB, gate);
                }


        } else if (gate == FaultTreeEquations.AND) {


                if ( categoryB.contentEquals("A") || categoryB.contentEquals("X")) {
                    res[0] = (fRateA * proB) + (proA * fRateA);
                    res[1] = proB * proB;
                } else {
                    res[0] = FaultTreeFreCal.getFreSelector(fRateA, proA, categoryA, fRateB, proB, categoryB, gate);
                    res[1] = FaultTreeEQ.getProSelector(proA, categoryA, proB, categoryB, gate);
                }

        }

        return res;
    }
    
    

    public static double calPerentProData(Faultdata Faultdata, FaultTreeData fTDPanel) {

        List<Faultdata> childdata = new ArrayList<>();
        int gate = Faultdata.getChildGate().contentEquals("AND") ? FaultTreeEquations.AND : FaultTreeEquations.OR;
        int unRevealedCount=0;
        Faultdata ftdata = null;

        for (String faultId : Faultdata.getChild().split("\\#")) {
            ftdata =fTDPanel.getFaultdataByRowId(fTDPanel.findRowByFaultId(faultId));
            childdata.add(ftdata);
            if (ftdata.getCategory().contentEquals("U")) {
                unRevealedCount++;
            }
            // System.out.println(getFaultdataByRowId(findRowByFaultId(faultId)).getName());
        }

        double res = FaultTreeEQ.getProFixedPro(getProbability(childdata.get(0), fTDPanel), getProbability(childdata.get(1), fTDPanel), gate);

        for (int i = 2; i < childdata.size(); i++) {
            //res += getProFixedPro(res, Faultdata.get(i).getProbability(), gate);
            res = FaultTreeEQ.getProFixedPro(res, getProbability(childdata.get(i), fTDPanel), gate);
        }
        
        System.out.println("unRevealedCount -------------"+unRevealedCount);
        
        if (unRevealedCount > 0) {
            res= res*FaultTreeEQ.PTIAdjustment("S", unRevealedCount);
        }

        fTDPanel.updateTableCalclatedProbablityById(0.0, res, Faultdata.getFaultdataId());
        return res;
    }

    public static double getProbability(Faultdata ftA, FaultTreeData fTDPanel) {

        double res = 0.0;

        if (ftA.getNode().contentEquals("Event")) {

            res = calPerentProData(ftA, fTDPanel);
        } else if (ftA.getNode().contentEquals("Basic")) {
            res = ftA.getProbability();
        }

        return res;
    }

    public static double[] getProbabilityCutset(Faultdata ftA, FaultTreeData fTDPanel) {

        double res[] = {0.0, 0.0};

        if (ftA.getNode().contentEquals("Event")) {

            res = findCutSetNode(ftA, fTDPanel);
        } else if (ftA.getNode().contentEquals("Basic")) {
            res[0] = ftA.getFailurerate();
            res[1] = ftA.getProbability();
        }

        return res;
    }
    
    
   public static double FailureRateConveter(String dataType,Double failurerate,Double fmodePre,Double unRev){
     double res=failurerate;
    
        System.out.println("dataType - "+dataType+" failurerate - "+failurerate);
        System.out.println("fmodePre - "+fmodePre+" unRev - "+unRev);
    
        if (dataType.contentEquals("Revealed Failure")) { 
             res = res*fmodePre*(1-unRev);
              
        } else if (dataType.contentEquals("Unrevealed Failure")) {
             res = res*fmodePre*unRev; 
        } 
        
    return res;
    }

}
