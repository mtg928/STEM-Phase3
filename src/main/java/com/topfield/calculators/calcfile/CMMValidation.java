/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Fmea;
import com.topfield.modal.Fmeca;
import com.topfield.modal.Graphvertex;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Spfhdr;
import com.topfield.singleton.DaoInstances;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.StringFuntions;
import com.topfield.view.popup.CalcFilePopup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Murali
 */
public class CMMValidation {
    
     public static List<String> inputValidation(CalcFilePopup calcFilePopup) {
         List<String> res = new ArrayList<>();
         
         if (!StringFuntions.isNotEmptyString(calcFilePopup.getComponentId())) {
             res.add("CM0207002");
             res.add("Component Id cannot be empty. Please enter a valid component id. ");
         }else if (!StringFuntions.isNotEmptyString(calcFilePopup.getComponentName())) {
             res.add("CM0207003");
             res.add("Component Name cannot be empty. Please enter a valid component name. ");
         }else if (!StringFuntions.isNotEmptyString(calcFilePopup.getFailureMode())) {
             res.add("CM0207011");
             res.add("Failure Mode cannot be empty. Please enter a valid failure mode ");
         }else if (calcFilePopup.getFailureRate()<=0) {
             res.add("CM0207004");
             res.add("Failure Rate is not valid. Please enter a valid failure rate (i.e., 1.00E02).");
         }else if (calcFilePopup.getProbability()==0) {
             res.add("CM0207005");
             res.add("Probability is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (calcFilePopup.getProbability()<0 || calcFilePopup.getProbability()>=1) {
             res.add("CM0207008");
             res.add("Probability must be between zero and one (i.e.,0.1). ");
         }else if (calcFilePopup.getFailureModePer()==0) {
             res.add("CM0207006");
             res.add("Failure Mode Ratio not valid. Please enter a valid number between zero and one (i.e.,0.1). ");
         }else if (calcFilePopup.getFailureModePer()<0 || calcFilePopup.getFailureModePer()>=1) {
             res.add("CM0207009");
             res.add("Failure Mode Ratio must be between zero and one (i.e.,0.1). ");
         }else if (calcFilePopup.getUNRFailurePer()==0) {
             res.add("CM0207007");
             res.add("Unrevealed Failure Rate Ratio is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (calcFilePopup.getUNRFailurePer()<0 || calcFilePopup.getUNRFailurePer()>=1) {
             res.add("CM0207010");
             res.add("Unrevealed Failure Rate must be between zero and one (i.e.,0.1). ");
         }
         
         
         return res;
     }
     
     public static List<String> inputValidation(JTable table,int i,int c) {
         List<String> res = new ArrayList<>();
         
         if (c==2 && !StringFuntions.isNotEmptyString(table.getValueAt(i,2)+"")) {
             res.add("CM0207002");
             res.add("Component Id cannot be empty. Please enter a valid component id. ");
         }else if (c==3 && !StringFuntions.isNotEmptyString(table.getValueAt(i,3)+"")) {
             res.add("CM0207003");
             res.add("Component Name cannot be empty. Please enter a valid component name. ");
         }else if (c==4 && !StringFuntions.isNotEmptyString(table.getValueAt(i,4)+"")) {
             res.add("CM0207011");
             res.add("Failure Mode cannot be empty. Please enter a valid failure mode ");
         }else if (c==6 && NumberConversion.NVLDouble(table.getValueAt(i,6)+"", i)<=0) {
             res.add("CM0207004");
             res.add("Failure Rate is not valid. Please enter a valid failure rate (i.e., 1.00E02).");
         }else if (c==7 && NumberConversion.NVLDouble(table.getValueAt(i,7)+"", i)==0) {
             res.add("CM0207005");
             res.add("Probability is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (c==7 && NumberConversion.NVLDouble(table.getValueAt(i,7)+"", i)<0 || NumberConversion.NVLDouble(table.getValueAt(i,7)+"", i)>=1) {
             res.add("CM0207008");
             res.add("Probability must be between zero and one (i.e.,0.1). ");
         }else if (c==8 && NumberConversion.NVLDouble(table.getValueAt(i,8)+"", i)==0) {
             res.add("CM0207006");
             res.add("Failure Mode Ratio not valid. Please enter a valid number between zero and one (i.e.,0.1). ");
         }else if (c==8 && NumberConversion.NVLDouble(table.getValueAt(i,8)+"", i)<0 || NumberConversion.NVLDouble(table.getValueAt(i,8)+"", i)>=1) {
             res.add("CM0207009");
             res.add("Failure Mode Ratio must be between zero and one (i.e.,0.1). ");
         }else if (c==9 && NumberConversion.NVLDouble(table.getValueAt(i,9)+"", i)==0) {
             res.add("CM0207007");
             res.add("Unrevealed Failure Rate Ratio is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (c==9 && NumberConversion.NVLDouble(table.getValueAt(i,9)+"", i)<0 || NumberConversion.NVLDouble(table.getValueAt(i,9)+"", i)>=1) {
             res.add("CM0207010");
             res.add("Unrevealed Failure Rate must be between zero and one (i.e.,0.1). ");
         }
         
         
         return res;
     }
     
     
     public static List<String> inputValidationValue(Object aValue,int c) {
         List<String> res = new ArrayList<>();
         
         System.out.println("aValue - "+aValue+" c ="+c);
         
         if (c==2 && !StringFuntions.isNotEmptyString(aValue+"")) {
             res.add("CM0207002");
             res.add("Component Id cannot be empty. Please enter a valid component id. ");
         }else if (c==3 && !StringFuntions.isNotEmptyString(aValue+"")) {
             res.add("CM0207003");
             res.add("Component Name cannot be empty. Please enter a valid component name. ");
         }else if (c==4 && !StringFuntions.isNotEmptyString(aValue+"")) {
             res.add("CM0207011");
             res.add("Failure Mode cannot be empty. Please enter a valid failure mode ");
         }else if (c==6 && NumberConversion.NVLDouble(aValue+"", 0)<=0) {
             res.add("CM0207004");
             res.add("Failure Rate is not valid. Please enter a valid failure rate (i.e., 1.00E02).");
         }else if (c==7 && NumberConversion.NVLDouble(aValue+"", 0)==0) {
             res.add("CM0207005");
             res.add("Probability is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (c==7 && NumberConversion.NVLDouble(aValue+"", 0)<0 || NumberConversion.NVLDouble(aValue+"", 0)>=1) {
             res.add("CM0207008");
             res.add("Probability must be between zero and one (i.e.,0.1). ");
         }else if (c==8 && NumberConversion.NVLDouble(aValue+"", 0)==0) {
             res.add("CM0207006");
             res.add("Failure Mode Ratio not valid. Please enter a valid number between zero and one (i.e.,0.1). ");
         }else if (c==8 && NumberConversion.NVLDouble(aValue+"", 0)<0 || NumberConversion.NVLDouble(aValue+"", 0)>=1) {
             res.add("CM0207009");
             res.add("Failure Mode Ratio must be between zero and one (i.e.,0.1). ");
         }else if (c==9 && NumberConversion.NVLDouble(aValue+"", 0)==0) {
             res.add("CM0207007");
             res.add("Unrevealed Failure Rate Ratio is not valid. Please enter a valid number between zero and one. (i.e.,0.1). ");
         }else if (c==9 && NumberConversion.NVLDouble(aValue+"", 0)<0 || NumberConversion.NVLDouble(aValue+"", 0)>=1) {
             res.add("CM0207010");
             res.add("Unrevealed Failure Rate must be between zero and one (i.e.,0.1). ");
         }
         
         
         return res;
     }

    public static boolean checkCMMChildExists(int calcId) {
        boolean res = false;

        try {
            Calcfile calcFile = DaoInstances.getCalcDao().findById(calcId);

            if (DaoInstances.getGraphDao().isCMMUsed(calcId)
                    || (calcFile.getFmeaCollection() != null && calcFile.getFmeaCollection().size() > 0)
                    || (calcFile.getFmecaCollection() != null && calcFile.getFmecaCollection().size() > 0)
                    || (calcFile.getFaultdataCollection() != null && calcFile.getFaultdataCollection().size() > 0)) {

                res = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "CMM Delete validation error", "Delete validation error ....!!!", JOptionPane.ERROR_MESSAGE);
            res = true;
        }

        return res;
    }

    public static boolean checkFUNChildExists(int funId) {
        boolean res = false;

        try {
            CalcFunctions calcFunctions = DaoInstances.getCalcFunDao().findById(funId);

            if ((calcFunctions.getSpfhdrCollection() != null && calcFunctions.getSpfhdrCollection().size() > 0)) {

                res = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "CMM FunctionsDelete validation error", "Delete validation error ....!!!", JOptionPane.ERROR_MESSAGE);
            res = true;
        }

        return res;
    }

    public static boolean checkFUNChildExists(CalcFunctions calcFunctions) {
        boolean res = false;

        try {

            if ((calcFunctions.getSpfhdrCollection() != null && calcFunctions.getSpfhdrCollection().size() > 0)) {

                res = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "CMM FunctionsDelete validation error", "Delete validation error ....!!!", JOptionPane.ERROR_MESSAGE);
            res = true;
        }

        return res;
    }

    public static boolean checkMPGHDRChildExists(int mpgId) {
        boolean res = false;

        try {
            Mpghdr Mpghdr = DaoInstances.getMpgDao().getFMEASById(mpgId);

            if ((Mpghdr.getCalchdrCollection() != null && Mpghdr.getCalchdrCollection().size() > 0)) {

                res = true;
            } else if ((Mpghdr.getCalcFunctionsCollection() != null && Mpghdr.getCalcFunctionsCollection().size() > 0)) {

                for (CalcFunctions calcFunctions : Mpghdr.getCalcFunctionsCollection()) {

                    if (checkFUNChildExists(calcFunctions)) {
                        res = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "CMM  Delete validation error", "Delete validation error ....!!!", JOptionPane.ERROR_MESSAGE);
            res = true;
        }

        return res;
    }

    public static List<ArrayList<String>> getCalcHdrList(int mpgId) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            Mpghdr mpghdr = DaoInstances.getMpgDao().getFMEASById(mpgId);

            if ((mpghdr.getCalchdrCollection() != null && mpghdr.getCalchdrCollection().size() > 0)) {
                for (Calchdr calchdr : mpghdr.getCalchdrCollection()) {
                    list1 = new ArrayList<>();
                    list1.add(calchdr.getCalname());
                    list1.add(calchdr.getCalchdrid()+ "");
                    list1.add(calchdr.getDescriptions());
                    list1.add(mpghdr.getMpghdrid() + "");
                    list1.add(mpghdr.getDescriptions());
                    listOfLists.add(list1);
                }
             }
            
             if ((mpghdr.getCalcFunctionsCollection()!= null && mpghdr.getCalcFunctionsCollection().size() > 0)) {
                  for (CalcFunctions calcFunctions : mpghdr.getCalcFunctionsCollection()) {
                        listOfLists.addAll(getSpfHdrList(calcFunctions));
                  }
             }

           
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    public static List<ArrayList<String>> getSpfHdrList(CalcFunctions calcFunctions) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcFunctions.getSpfhdrCollection() != null && calcFunctions.getSpfhdrCollection().size() > 0)) {
                for (Spfhdr spfhdr : calcFunctions.getSpfhdrCollection()) {
                    list1 = new ArrayList<>();
                    list1.add(spfhdr.getCalname());
                    list1.add(spfhdr.getSpfhdrid()+ "");
                    list1.add(spfhdr.getDescriptions());
                    list1.add(calcFunctions.getCalcFunId()+ "");
                    list1.add(calcFunctions.getCalcFunction());
                    listOfLists.add(list1);
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
     public static List<ArrayList<String>> getFMEAORCAList(CalcFunctions calcFunctions) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcFunctions.getCalcfileCollection() != null && calcFunctions.getCalcfileCollection().size() > 0)) {
               
                 for (Calcfile calcfile : calcFunctions.getCalcfileCollection()) {
                    
                    if (getFMEAList(calcfile).size()>0|| getFMECAList(calcfile).size()>0) {
                         
                        list1 = new ArrayList<>();    
                        list1.add("FMEA / FMECA");
                        list1.add("");
                        list1.add("");
                        list1.add(calcfile.getCalcId()+ "");
                        list1.add(calcfile.getComponents());
                        listOfLists.add(list1);
                        break;
                    }
                    
                  
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    
    public static List<ArrayList<String>> getCalcElementList(Calcfile calcfile) {
         List<ArrayList<String>> listOfLists = new ArrayList<>();
         
         listOfLists.addAll(getRBDList(calcfile));
         listOfLists.addAll(getFMEAList(calcfile));
         listOfLists.addAll(getFMECAList(calcfile));
         listOfLists.addAll(getFTAList(calcfile));

         return listOfLists;
    }
     
    
    public static List<ArrayList<String>> getFunElementList(CalcFunctions calcFunctions) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();

        listOfLists.addAll(getFMEAFunList(calcFunctions));
        listOfLists.addAll(getFMECAFunList(calcFunctions));
        listOfLists.addAll(getSpfHdrList(calcFunctions));


        return listOfLists;
    }
    

    public static String[][] list2DtoArray(List<ArrayList<String>> lists) {

        String[][] array = new String[lists.size()][];
        String[] blankArray = new String[0];
        for (int i = 0; i < lists.size(); i++) {
            array[i] = lists.get(i).toArray(blankArray);
        }

        return array;
    }

    
    public static List<ArrayList<String>> getRBDList(Calcfile calcfile) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcfile.getGraphvertexCollection() != null && calcfile.getGraphvertexCollection().size() > 0)) {
                for (Graphvertex graphvertex : calcfile.getGraphvertexCollection()) {
                    list1 = new ArrayList<>();
                    
                    list1.add("RBD");
                    list1.add(graphvertex.getVertexid()+ "");
                    list1.add(graphvertex.getDescription());
                    list1.add(calcfile.getCalcId()+ "");
                    list1.add(calcfile.getComponents());
                    listOfLists.add(list1);
                }
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    public static List<ArrayList<String>> getFMEAList(Calcfile calcfile) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcfile.getFmeaCollection() != null && calcfile.getFmeaCollection().size() > 0)) {
                for (Fmea fmea : calcfile.getFmeaCollection()) {
                    list1 = new ArrayList<>();
                    
                    list1.add("FMEA");
                    list1.add(fmea.getFmeaId()+ "");
                    list1.add(fmea.getFISubsysComponent());
                    list1.add(calcfile.getCalcId()+ "");
                    list1.add(calcfile.getComponents());
                    listOfLists.add(list1);
                }
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    public static List<ArrayList<String>> getFMECAList(Calcfile calcfile) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcfile.getFmecaCollection() != null && calcfile.getFmecaCollection().size() > 0)) {
                for (Fmeca fmeca : calcfile.getFmecaCollection()) {
                    list1 = new ArrayList<>();
                    
                    list1.add("FMEA");
                    list1.add(fmeca.getFmecaId()+ "");
                    list1.add(fmeca.getFISubsysComp());
                    list1.add(calcfile.getCalcId()+ "");
                    list1.add(calcfile.getComponents());
                    listOfLists.add(list1);
                }
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    
    public static List<ArrayList<String>> getFTAList(Calcfile calcfile) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        try {
            

            if ((calcfile.getFaultdataCollection() != null && calcfile.getFaultdataCollection().size() > 0)) {
                for (Faultdata faultdata : calcfile.getFaultdataCollection()) {
                    list1 = new ArrayList<>();
                    
                    list1.add("FMEA");
                    list1.add(faultdata.getFaultdataId()+ "");
                    list1.add(faultdata.getName());
                    list1.add(calcfile.getCalcId()+ "");
                    list1.add(calcfile.getComponents());
                    listOfLists.add(list1);
                }
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfLists;
    }
    
    
     public static List<ArrayList<String>> getFMEAFunList(CalcFunctions calcFunctions) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        
         try {
            
            for (Calchdr  calchdr: calcFunctions.getMpgheader().getCalchdrCollection()) {
                
                for (Fmea fmea : calchdr.getFmeaCollection()) {

                    if (Objects.equals(calcFunctions.getCalcFunId(), fmea.getCalcComp().getCalcHeader().getCalcFunId())) {
                           list1 = new ArrayList<>();
                           list1.add("FMEA");
                           list1.add(calchdr.getCalchdrid()+ "");
                           list1.add(calchdr.getDescriptions());
                           list1.add(calcFunctions.getCalcFunId()+ "");
                           list1.add(calcFunctions.getCalcFunction());
                           listOfLists.add(list1);
                           break;
                    }
                
                }
            }
         
          } catch (Exception e) {
            e.printStackTrace();

          }
          return listOfLists;
        
     }
     
      public static List<ArrayList<String>> getFMECAFunList(CalcFunctions calcFunctions) {
        List<ArrayList<String>> listOfLists = new ArrayList<>();
        ArrayList<String> list1;
        
          try {
            
            for (Calchdr  calchdr: calcFunctions.getMpgheader().getCalchdrCollection()) {
                
                for (Fmeca fmeca : calchdr.getFmecaCollection()) {

                    if (Objects.equals(calcFunctions.getCalcFunId(), fmeca.getCalcComp().getCalcHeader().getCalcFunId())) {
                           list1 = new ArrayList<>();
                           list1.add("FMECA");
                           list1.add(calchdr.getCalchdrid()+ "");
                           list1.add(calchdr.getDescriptions());
                           list1.add(calcFunctions.getCalcFunId()+ "");
                           list1.add(calcFunctions.getCalcFunction());
                           listOfLists.add(list1);
                           break;
                    }
                
                }
            }
         
          } catch (Exception e) {
            e.printStackTrace();

          }
          return listOfLists;
        
     }
    
}
