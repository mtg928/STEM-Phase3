/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import static com.topfield.calculators.faulttree.FaultTreeEquations.AND;
import static com.topfield.calculators.faulttree.FaultTreeEquations.OR;
import static com.topfield.calculators.faulttree.FaultTreeEquations.getProbabilityCutset;
import com.topfield.modal.Faultdata;
import com.topfield.utilities.NumberConversion;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Murali
 */
public class FaultTreeEQ {

    public final static int FIXED_PRO = 0;
    public final static int FIXED_PRO_FRE = 1;
    public final static int FIXED_PRO_RE = 2;
    public final static int FIXED_FRE = 3;
    public final static int FIXED_FRE_RE = 4;
    public final static int ALL_REV = 5;

    public final static int FIXED_PRO_FRE_BAR = 11;
    public final static int FIXED_PRO_RE_BAR = 12;
    public final static int FIXED_FRE_RE_BAR = 14;

    public static int selectCase(String typeA, String typeB) {
        int res = -1;

        if ((typeB + typeB).contentEquals("PP")) {
            res = FIXED_PRO;
        } else if ((typeB + typeB).contentEquals("PF")) {
            res = FIXED_PRO_FRE;
        } else if ((typeB + typeB).contentEquals("FP")) {
            res = FIXED_PRO_FRE_BAR;
        } else if ((typeB + typeB).contentEquals("PR") || (typeB + typeB).contentEquals("PU")) {
            res = FIXED_PRO_RE;
        } else if ((typeB + typeB).contentEquals("RP") || (typeB + typeB).contentEquals("UP")) {
            res = FIXED_PRO_RE_BAR;
        } else if ((typeB + typeB).contentEquals("FF")) {
            res = FIXED_FRE;
        } else if ((typeB + typeB).contentEquals("FR") || (typeB + typeB).contentEquals("FU")) {
            res = FIXED_FRE_RE;
        } else if ((typeB + typeB).contentEquals("RF") || (typeB + typeB).contentEquals("UF")) {
            res = FIXED_FRE_RE_BAR;
        } else if ((typeB + typeB).contentEquals("RR") || (typeB + typeB).contentEquals("RU") || (typeB + typeB).contentEquals("UR") || (typeB + typeB).contentEquals("UU")) {
            res = ALL_REV;
        }

        return res;
    }

    public static double getProSelector(double proA, String typeA, double proB, String typeB, int gate) {
        double res = 0.0;

        List<Integer> fieldValues = Arrays.asList(FIXED_PRO, FIXED_PRO_RE, FIXED_PRO_RE_BAR, ALL_REV);

        if (fieldValues.contains(selectCase(typeA, typeB))) {

            res = getProFixedPro(proA, proB, gate);

        } else {

            if (gate == FaultTreeEquations.OR) {
                res = 0.0;
            } else if (gate == FaultTreeEquations.AND) {
                res = 1.0;
            }
        }

        return res;
    }

    public static double getProFixedPro(double proA, double proB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = proA * proB;

        } else if (gate == OR) {

            res = (proA + proB) - (proA * proB);
        }
        System.out.println("proA - " + proA + " proB - " + proB + " gate - " + gate + " res = " + res);
        return res;
    }

    /*Revealed (R) - normally used for the failure rate of items whose failure is revealed to operators
     who will initiate a repair. If an item failure is revealed, an associated time for restoration (repair time)
     must also be entered. Logan calculates the event unavailability (= probability of being in the failed state) 
     from the failure rate and the repair time */
    // repair time assume as mean down time
    public static double calRevealedProbality(double failureRate, double repairTime) {
        double res = 0.0;

        res = (failureRate * repairTime) / (1 + (failureRate * repairTime));

        return NumberConversion.NVLDouble(res + "", 0);  //NumberConversion.roundNew
    }

    /*Unrevealed (U) - normally used for the failure rate of items whose failure is not revealed to
       operators until a routine proof test is carried out. This is often applicable to protective systems
       which do not operate until called upon to do so (e.g. a fire alarm). If an item failure is unrevealed,
       an associated proof test interval (PTI) must also be entered. Logan calculates the event probability of
       failure on demand (PFD) from the failure rate and the PTI. */
    // repair time assume as mean down time
    public static double calUnRevealedProbality(double failureRate, double proofTestIntervel) {
        double res = 0.0;
        double lamdaT= failureRate * proofTestIntervel;
        
        if (lamdaT < 0.05) {
             // Approximation
             res = (lamdaT) / 2;
        }else{
          // Exact
          res = 1 - ((1 / (lamdaT)) * (1 - Math.exp(-1 * lamdaT)));
        }
      
       
        return NumberConversion.NVLDouble(res + "", 0);
    }

    public static double PTIAdjustment(String adjType, int n) {
        double res = 1.0;

        if ( n > 0 && adjType != null ) { 


            if (adjType.contentEquals("Simultaneous")) {

                res = Math.pow(2, n) / (n + 1);

            } else if (adjType.contentEquals("Perfectly staggered")) {
                res = (Math.pow(2, n) / (n + 1)) * (((n + 3) * NumberConversion.factorial(n)) / (4 * Math.pow(n, n)));
            }
        }

        return res;
    }

}
