/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import static com.topfield.calculators.faulttree.FaultTreeEQ.ALL_REV;
import static com.topfield.calculators.faulttree.FaultTreeEQ.FIXED_PRO;
import static com.topfield.calculators.faulttree.FaultTreeEQ.FIXED_PRO_RE;
import static com.topfield.calculators.faulttree.FaultTreeEQ.FIXED_PRO_RE_BAR;
import static com.topfield.calculators.faulttree.FaultTreeEQ.getProFixedPro;
import static com.topfield.calculators.faulttree.FaultTreeEQ.selectCase;
import static com.topfield.calculators.faulttree.FaultTreeEquations.AND;
import static com.topfield.calculators.faulttree.FaultTreeEquations.OR;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Murali
 */
public class FaultTreeFreCal {

    public static double getFreSelector(double freA, double proA, String typeA, double freB, double proB, String typeB, int gate) {
        double res = 0.0;

        List<Integer> fixedPro_Fre_Rev = Arrays.asList(FaultTreeEQ.FIXED_PRO_FRE, FaultTreeEQ.FIXED_PRO_RE);
        List<Integer> fixedPro_Fre_Rev_bar = Arrays.asList(FaultTreeEQ.FIXED_PRO_FRE_BAR, FaultTreeEQ.FIXED_PRO_RE_BAR);

        if (fixedPro_Fre_Rev.contains(selectCase(typeA, typeB))) {

            res = getFreFixedProAndFre_Rev(proA, freB, gate);

        } else if (fixedPro_Fre_Rev_bar.contains(selectCase(typeA, typeB))) {

            res = getFreFixedProAndFre_Rev_Bar(freA, proB, gate);

        }else if (selectCase(typeA, typeB) == FaultTreeEQ.FIXED_FRE) {

            res = getFreFixed(freA, freB, gate);

        }else if (selectCase(typeA, typeB) == FaultTreeEQ.FIXED_FRE_RE) {

            res = getFreFixedFre_AndRev(freA, freB, proB, gate);

        }else if (selectCase(typeA, typeB) == FaultTreeEQ.FIXED_FRE_RE_BAR) {

            res = getFreFixedFre_AndRev_Bar(freA, proA, freB, gate);
        }else if (selectCase(typeA, typeB) == FaultTreeEQ.ALL_REV) {

            res = getFreAndRev(freA, proA, freB, proB, gate);
        }else if (selectCase(typeA, typeB) == FaultTreeEQ.FIXED_PRO) {
              // Add this if closed for New JgraphDrawer 13.09.2021
            res = 0.0;
        } else {

            if (gate == FaultTreeEquations.OR) {
                res = 0.0;
            } else if (gate == FaultTreeEquations.AND) {
                res = 1.0;
            }
        }

        return res;
    }

    public static double getFreFixedProAndFre_Rev(double proA, double freB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = proA * freB;

        } else if (gate == OR) {

            res = 0.0;
        }
        System.out.println("proA - " + proA + " freB - " + freB + " gate - " + gate + " res = " + res);
        return res;
    }

    public static double getFreFixedProAndFre_Rev_Bar(double freA, double proB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = freA * proB;

        } else if (gate == OR) {

            res = 0.0;
        }
        System.out.println("proA - " + freA + " freB - " + proB + " gate - " + gate + " res = " + res);
        return res;
    }

    public static double getFreFixed(double freA, double freB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = 1.0;

        } else if (gate == OR) {

            res = freA + freB;
        }
        System.out.println("proA - " + freA + " freB - " + freB + " gate - " + gate + " res = " + res);
        return res;
    }

    public static double getFreFixedFre_AndRev(double freA, double freB, double proB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = freA * proB;

        } else if (gate == OR) {

            res = freA + freB;
        }
        System.out.println("proA - " + freA + " freB - " + freB + " gate - " + gate + " res = " + res);
        return res;
    }

    public static double getFreFixedFre_AndRev_Bar(double freA, double proA, double freB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = proA * freB;

        } else if (gate == OR) {

            res = freA + freB;
        }
        System.out.println("proA - " + freA + " freB - " + freB + " gate - " + gate + " res = " + res);
        return res;
    }

    public static double getFreAndRev(double freA, double proA, double freB, double proB, int gate) {
        double res = 0.0;

        if (gate == AND) {

            res = (freA * proB) + (freB * proA);

        } else if (gate == OR) {

            res = freA + freB;
        }
        System.out.println("proA - " + freA + " freB - " + freB + " gate - " + gate + " res = " + res);
        return res;
    }

}
