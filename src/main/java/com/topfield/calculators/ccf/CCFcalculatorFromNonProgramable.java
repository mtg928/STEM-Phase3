/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;

import com.topfield.utilities.StringFuntions;
import com.topfield.user.UserInfo;
import com.topfield.dao.CCFDao;
import com.topfield.dao.CcfHdrDao;
import com.topfield.dao.SpfhdrDao;

import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.CCFDaoImpl;
import com.topfield.daoImpl.CcfHdrDaoImpl;
import com.topfield.daoImpl.SpfhdrDaoImpl;

import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Ccf;
import com.topfield.modal.Ccfhdr;
import com.topfield.modal.Spfhdr;
import com.topfield.utilities.NumberConversion;
import com.topfield.view.calculators.SensorsAndActuators;

import java.awt.Color;
import java.awt.Cursor;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class CCFcalculatorFromNonProgramable extends javax.swing.JPanel {
    
    private CCFDao ccfDao = new CCFDaoImpl();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private CCFCurd ccfCurd= new CCFCurd();
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    private List<Ccf> dataSingleCom ;
    
   
    private final int[][][] scoresAandB =new int[][][]{ {{15,52},{65,35},{95,65}},
                                       {{100,25},{90,25},{70,25},{40,25},{32,52}},
                                       {{30,0},{0,10},{0,20},{30,0}},
                                       {{0,140},{0,70}},
                                       {{30,20},{60,0},{15,25},{0,20},{15,20}},
                                       {{0,100},{0,50},{0,60}},
                                       {{40,50},{40,50}},
                                       {{0,316}}};
    
    
    private final JTextField [][][] indiviScore;
    private final JComboBox [][][] indiviCombo;
    private final JLabel [][] totalScore;
    private final JTextField [][] indiviNote;
        //private Ccfhdr ccfHdr;
    private Spfhdr ccfCalcHdr;
    private final String[] mainHeading = {"Separation / Segregation","Diversity","Complexity/design/application/maturity/experience","Assessment/Analysis and feedback of data","Procedures/human interface","Competence/training/safty culture","Environmental control","Environmental testing"};

    public CCFcalculatorFromNonProgramable(Spfhdr ccfCalcHdr) {
        initComponents();
       this.ccfCalcHdr = ccfCalcHdr;
       //dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() ,"Non programmable",spgId);
        dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
        jLabel71.setText("Betaplus - "+ccfCalcHdr.getSpfid().getMpgheader().getFmeaComponent().getMpgDescription());
       
       
        indiviScore = new JTextField[][][]{{{jTextField2,jTextField1},{jTextField10,jTextField11},{jTextField13,jTextField14}},
	                                 {{jTextField16,jTextField17},{jTextField22,jTextField23},{jTextField25,jTextField26},{jTextField28,jTextField29},{jTextField31,jTextField32}},
					 {{jTextField34,jTextField35},{jTextField52,jTextField53},{jTextField55,jTextField56},{jTextField58,jTextField59}},
                                         {{jTextField37,jTextField38},{jTextField61,jTextField62}},
					 {{jTextField40,jTextField41},{jTextField64,jTextField65},{jTextField67,jTextField68},{jTextField70,jTextField71},{jTextField73,jTextField74}},
					 {{jTextField43,jTextField44},{jTextField76,jTextField77},{jTextField79,jTextField80}},
					 {{jTextField46,jTextField47},{jTextField82,jTextField83}},
					 {{jTextField49,jTextField50}}};
        
        indiviCombo = new JComboBox[][][]{{{jComboBox2,jComboBox14},{jComboBox4,jComboBox15},{jComboBox5,jComboBox13}},
	                                {{jComboBox3,jComboBox16},{jComboBox6,jComboBox17},{jComboBox8,jComboBox19},{jComboBox9,jComboBox20},{jComboBox7,jComboBox24}},
					{{jComboBox10,jComboBox27},{jComboBox18,jComboBox28},{jComboBox11,jComboBox32},{jComboBox12,jComboBox38}},
					{{jComboBox29,jComboBox40},{jComboBox30,jComboBox39}},
					{{jComboBox33,jComboBox45},{jComboBox34,jComboBox44},{jComboBox35,jComboBox43},{jComboBox36,jComboBox42},{jComboBox31,jComboBox41}},
					{{jComboBox21,jComboBox48},{jComboBox22,jComboBox47},{jComboBox23,jComboBox46}},
					{{jComboBox25,jComboBox50},{jComboBox26,jComboBox49}},
					{{jComboBox37,jComboBox51}}};
         totalScore = new JLabel[][]{{jLabel3,jLabel2},{jLabel112,jLabel113},{jLabel115,jLabel116},{jLabel118,jLabel119},
                                     {jLabel121,jLabel122},{jLabel124,jLabel125},{jLabel127,jLabel128},{jLabel130,jLabel131}};
         
          indiviNote = new JTextField[][]{{jTextField3,jTextField12,jTextField15},{jTextField18,jTextField24,jTextField27,jTextField30,jTextField33},
                                       {jTextField36,jTextField54,jTextField57,jTextField60},{jTextField39,jTextField63},
                                       {jTextField42,jTextField66,jTextField69,jTextField72,jTextField75},
                                       {jTextField45,jTextField78,jTextField81},{jTextField48,jTextField84},{jTextField51}}; 
       
        setCCFDataForComp(dataSingleCom);
        SetDiagnosticCoverage(ccfCurd.getJSONProperty(ccfCalcHdr.getProsettings(),"results","diagnostic"),ccfCalcHdr.getComments());
    }
    
    public void SetDiagnosticCoverage(String data,String comments){
      if(data.equals("null")){ 
        jButton1.setText("60");
      }else{
        jButton1.setText(data);
      }  
      
      jTextArea1.setText(comments);
    }
    

    public void CalculateScoreMax(int sectionNo,int questionNo){

        if (indiviCombo[sectionNo][questionNo][0].getSelectedIndex()==1){
          indiviCombo[sectionNo][questionNo][1].setEnabled(false);
          indiviCombo[sectionNo][questionNo][1].setSelectedIndex(0);
          indiviScore[sectionNo][questionNo][0].setText(scoresAandB[sectionNo][questionNo][0]+"");
          indiviScore[sectionNo][questionNo][1].setText(scoresAandB[sectionNo][questionNo][1]+"");
        }else if (indiviCombo[sectionNo][questionNo][0].getSelectedIndex()==2){
          indiviCombo[sectionNo][questionNo][1].setEnabled(true);
          indiviScore[sectionNo][questionNo][0].setText("0");
          indiviScore[sectionNo][questionNo][1].setText("0");
        }else{
          indiviCombo[sectionNo][questionNo][1].setEnabled(false);
          indiviCombo[sectionNo][questionNo][1].setSelectedIndex(0);
          indiviScore[sectionNo][questionNo][0].setText("0");
          indiviScore[sectionNo][questionNo][1].setText("0");
        }
        
        CalculateSectionTotal(sectionNo);
        CalculateFinalTotal();

    }
    public void CalculateScoreFraction(int sectionNo,int questionNo){
        
        double fra =0;
        
        //System.out.println("*** combo.getSelectedIndex()"+combo.getSelectedIndex()+" *** comboFraction.getSelectedIndex() "+comboFraction.getSelectedIndex());
        
        if (indiviCombo[sectionNo][questionNo][0].getSelectedIndex()==2 && indiviCombo[sectionNo][questionNo][1].getSelectedIndex()!=0){
            fra =indiviCombo[sectionNo][questionNo][1].getSelectedIndex()/100.00;
            //System.out.println("fra - "+fra+ " *** " +comboFraction.getSelectedIndex());
          indiviScore[sectionNo][questionNo][0].setText(Math.round(scoresAandB[sectionNo][questionNo][0]*(fra))+"");
          indiviScore[sectionNo][questionNo][1].setText(Math.round(scoresAandB[sectionNo][questionNo][1]*(fra))+"");
        }else if(indiviCombo[sectionNo][questionNo][0].getSelectedIndex()==2 && indiviCombo[sectionNo][questionNo][1].getSelectedIndex()==0){
          indiviScore[sectionNo][questionNo][0].setText("0");
          indiviScore[sectionNo][questionNo][1].setText("0");
        }
        CalculateSectionTotal(sectionNo);
        CalculateFinalTotal();
    }
  
    public void CalculateSectionTotal(int sectionNo){
     int totAScore=0;
     int totBScore=0;
        for (JTextField[] ises : indiviScore[sectionNo]) {
            try {
                totAScore =totAScore+Integer.parseInt(ises[0].getText());
            } catch (NumberFormatException e) {
                totAScore =totAScore+0;
            }
            
            try {
                totBScore =totBScore+Integer.parseInt(ises[1].getText());
            } catch (NumberFormatException e) {
                totBScore =totBScore+0;
            }
        }
        
        totalScore[sectionNo][0].setText(totAScore+"");
        totalScore[sectionNo][1].setText(totBScore+"");
    
    }
    
    public void CalculateFinalTotal(){
     int totAScore=0;
     int totBScore=0;
     
      for (JLabel[] ises : totalScore) {
            try {
                totAScore =totAScore+Integer.parseInt(ises[0].getText());
            } catch (NumberFormatException e) {
                totAScore =totAScore+0;
            }
            
            try {
                totBScore =totBScore+Integer.parseInt(ises[1].getText());
            } catch (NumberFormatException e) {
                totBScore =totBScore+0;
            }
        }
      
      jLabel132.setText(totAScore+"");
      jLabel83.setText(totBScore+"");
    
      CalculateFactors(totAScore,totBScore,(int)NumberConversion.NVLDouble(jButton1.getText(),0));
    }
  
    public void markedPercentage(JComboBox[] combo, JLabel percentageLabel){

          int count=0;
          int res =0;
          for (JComboBox jComboBox : combo) {
              if(jComboBox.getSelectedIndex()!=0){
                count++;
                jComboBox.setBackground(new Color(163, 228, 215));
                //jComboBox.getSelectedItem().setBackground(new Color(163, 228, 215));
              }else{
                  jComboBox.setBackground(new Color(153, 153, 153));
              }
          }

          res = count*100/combo.length;

          if(count ==0){
           percentageLabel.setText("0%");
          }else{
           percentageLabel.setText(""+res+"%");
          }
           CalculateTotalMarkedPercentage();
      }
    
      public void CalculateTotalMarkedPercentage(){
        int totalPercentages = 0;
        JLabel [] precentagelables = {jLabel17,jLabel18,jLabel25,jLabel29,jLabel30,jLabel31,jLabel34,jLabel35};
        
        for (JLabel precentagelable : precentagelables) {
          totalPercentages = totalPercentages + Integer.parseInt(precentagelable.getText().replace("%", ""));  
        }
        
        jLabel37.setText((totalPercentages/8)+"%");    
    }
  
     public void CalculateFactors(int aScore,int bScore,int c){
        int rawScore=0;
        double betaFactor=0;
        double temp;
        
        rawScore = aScore*c+bScore;
        temp =  (rawScore * (3.4)  )/ 2624;
        betaFactor = 0.3*Math.exp(temp*-1);
    
    jLabel108.setText(rawScore+"");
    jLabel81.setText(StringFuntions.DoubleRound(betaFactor)+"");
    }
     
    public String [][][] getAllSubData(){
    
      String [][][] data = new String[indiviNote.length][][];
      String [][] rowData = new String[1][4];
      
        for (int i = 0; i < indiviNote.length; i++) {
            
            rowData = new String[indiviNote[i].length][4];
            
            for (int j = 0; j < indiviNote[i].length; j++) {
              
                try {
                    
                    
                    
                   // System.out.println(" indiviScore[i][j][0].getText() "+indiviScore[i][j][0].getText());
                   // System.out.println(" indiviScore[i][j][1].getText() "+indiviScore[i][j][1].getText());
                    //System.out.println(" indiviCombo[i][j][1].getSelectedItem().toString() "+indiviCombo[i][j][0].getSelectedItem().toString());
                   // System.out.println(" indiviNote[i][j].getText() "+indiviNote[i][j].getText());
                    
                    
                    rowData[j][0]= indiviScore[i][j][0].getText();
                    rowData[j][1]= indiviScore[i][j][1].getText();
                    rowData[j][2]= indiviCombo[i][j][0].getSelectedItem().toString(); 
                    rowData[j][3]= indiviNote[i][j].getText();
                    
                    
                    
                } catch (Exception e) {
                    
                    rowData[j][0]= "0";
                    rowData[j][1]= "0";
                    rowData[j][2]= "0";
                    rowData[j][3]= "0";

                        System.out.println("5656");
                }
   
                
            }
            
            
               data[i] = rowData;
               
               //System.out.println("Hi "+i+")"+rowData[0][0]);
        } 
         return data; 
    }
    
    public String getRawScore(){
    
    return jLabel108.getText();
    }
    
    public String getBetaFactor(){
    
    return jLabel81.getText();
    }
    
   public void orOperator(JComboBox [] combos,java.awt.event.ItemEvent evt){
         JComboBox combo = (JComboBox) evt.getSource();
    
         for (JComboBox combo1 : combos) {
             if (!(combo1.equals(combo))) {
                 System.out.println(" Value"+combo1.getName()); 
                 combo1.setSelectedIndex(0);
             }
             
         }
    }
       
    /*  public void CalculateMaxScore(JTextField[] scores, JLabel scoreLable  ){
     int score=0;
       
        for (JTextField text : scores) {
            try{
                score = score+ Integer.parseInt(text.getText());
            }catch(NumberFormatException e){
                score = score+ 0;
            }
            
            System.out.println("score"+score);
        }
        
        scoreLable.setText(score+"");
        CalculateTotalScore();
    }*/
    

    
  
 /* public void CalculateTotalScore(){
     int scores=0;
     int totalPercentages = 0;
       
     JLabel [] scorelables = {jLabel2,jLabel26,jLabel28,jLabel71,jLabel73,jLabel77,jLabel79,jLabel83};
     JLabel [] precentagelables = {jLabel17,jLabel18,jLabel25,jLabel29,jLabel30,jLabel31,jLabel34,jLabel35};
     
     
        for (JLabel score : scorelables) {
            
            try {
                scores = scores+ Integer.parseInt(score.getText());
            } catch (Exception e) {
                scores = scores+0 ;
            }   
            System.out.println("score"+scores);
        }
        
        for (JLabel precentagelable : precentagelables) {
          totalPercentages = totalPercentages + Integer.parseInt(precentagelable.getText().replace("%", ""));  
        }
 
        
        jLabel37.setText((totalPercentages/8)+"%");    
        jLabel81.setText(scores+"");
    }*/
      
     /* public void CalculateScore(JComboBox[] combo, JLabel scoreLable  ){
     int score=0;
       
        for (JComboBox jComboBox : combo) {
            score = score+ jComboBox.getSelectedIndex();
            System.out.println("score"+score);
        }
        
        scoreLable.setText(score+"");
        CalculateTotalScore();
    }*/
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel96 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jComboBox17 = new javax.swing.JComboBox<>();
        jComboBox19 = new javax.swing.JComboBox<>();
        jComboBox20 = new javax.swing.JComboBox<>();
        jComboBox24 = new javax.swing.JComboBox<>();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jComboBox11 = new javax.swing.JComboBox<>();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jTextField34 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jTextField56 = new javax.swing.JTextField();
        jTextField57 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jComboBox27 = new javax.swing.JComboBox<>();
        jComboBox28 = new javax.swing.JComboBox<>();
        jComboBox32 = new javax.swing.JComboBox<>();
        jComboBox38 = new javax.swing.JComboBox<>();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jComboBox29 = new javax.swing.JComboBox<>();
        jComboBox30 = new javax.swing.JComboBox<>();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jComboBox39 = new javax.swing.JComboBox<>();
        jComboBox40 = new javax.swing.JComboBox<>();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jComboBox33 = new javax.swing.JComboBox<>();
        jComboBox34 = new javax.swing.JComboBox<>();
        jComboBox35 = new javax.swing.JComboBox<>();
        jComboBox36 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jComboBox31 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jTextField41 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jTextField64 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jTextField70 = new javax.swing.JTextField();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jTextField73 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jComboBox41 = new javax.swing.JComboBox<>();
        jComboBox42 = new javax.swing.JComboBox<>();
        jComboBox43 = new javax.swing.JComboBox<>();
        jComboBox44 = new javax.swing.JComboBox<>();
        jComboBox45 = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBox25 = new javax.swing.JComboBox<>();
        jComboBox26 = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        jTextField82 = new javax.swing.JTextField();
        jTextField83 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jComboBox49 = new javax.swing.JComboBox<>();
        jComboBox50 = new javax.swing.JComboBox<>();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox22 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jTextField79 = new javax.swing.JTextField();
        jTextField80 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jComboBox46 = new javax.swing.JComboBox<>();
        jComboBox47 = new javax.swing.JComboBox<>();
        jComboBox48 = new javax.swing.JComboBox<>();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jComboBox37 = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jLabel106 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jComboBox51 = new javax.swing.JComboBox<>();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox2.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox2.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox2.setToolTipText("");
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 130, 20));

        jComboBox4.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox4.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox4.setToolTipText("");
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 130, 20));

        jComboBox5.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox5.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox5.setToolTipText("");
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 130, 20));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("<html>I. Are the sensors or actuators physically separated and atleast 1 metre apart? </html>");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 340, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("<html>II. If the sensor/actuator has some intermediate electronics or pneumatics </html>");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 400, 30));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("1.Separation / Segregation");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Score");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 190, -1));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Score");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 190, -1));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Score");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 190, -1));

        jLabel13.setBackground(new java.awt.Color(0, 153, 204));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Score");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 40, -1));

        jLabel2.setBackground(new java.awt.Color(0, 153, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("--------------");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("?");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 20, -1));

        jLabel46.setBackground(new java.awt.Color(0, 0, 0));
        jLabel46.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel46.setText("A Score");
        jPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 50, -1));

        jLabel47.setBackground(new java.awt.Color(0, 0, 0));
        jLabel47.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel47.setText("B Score ");
        jPanel1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        jLabel44.setBackground(new java.awt.Color(0, 0, 0));
        jLabel44.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel44.setText("Comments ");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setForeground(new java.awt.Color(0, 0, 0));
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField3MouseExited(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setForeground(new java.awt.Color(102, 0, 102));
        jTextField1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField1MouseExited(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(204, 204, 204));
        jTextField2.setForeground(new java.awt.Color(0, 0, 153));
        jTextField2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField2MouseExited(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(204, 204, 204));
        jTextField10.setForeground(new java.awt.Color(0, 0, 153));
        jTextField10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField10MouseExited(evt);
            }
        });
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField10KeyPressed(evt);
            }
        });
        jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 60, -1));

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(204, 204, 204));
        jTextField11.setForeground(new java.awt.Color(102, 0, 102));
        jTextField11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField11MouseExited(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 60, -1));

        jTextField12.setBackground(new java.awt.Color(255, 255, 255));
        jTextField12.setForeground(new java.awt.Color(0, 0, 0));
        jTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField12MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField12MouseExited(evt);
            }
        });
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 310, -1));

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(204, 204, 204));
        jTextField13.setForeground(new java.awt.Color(0, 0, 153));
        jTextField13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField13MouseExited(evt);
            }
        });
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField13KeyPressed(evt);
            }
        });
        jPanel1.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 60, -1));

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(204, 204, 204));
        jTextField14.setForeground(new java.awt.Color(102, 0, 102));
        jTextField14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField14MouseExited(evt);
            }
        });
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 60, -1));

        jTextField15.setBackground(new java.awt.Color(255, 255, 255));
        jTextField15.setForeground(new java.awt.Color(0, 0, 0));
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField15MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField15MouseExited(evt);
            }
        });
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, 310, -1));

        jLabel43.setBackground(new java.awt.Color(0, 0, 0));
        jLabel43.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 102, 51));
        jLabel43.setText("Completed");
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, 110, 30));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 51));
        jLabel18.setText("0%");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 140, 140, 30));

        jLabel3.setBackground(new java.awt.Color(0, 153, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("--------------");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jComboBox13.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox13.setEditable(true);
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox13.setEnabled(false);
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 50, 20));

        jComboBox14.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox14.setEditable(true);
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox14.setEnabled(false);
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jComboBox15.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox15.setEditable(true);
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox15.setEnabled(false);
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 50, 20));

        jLabel135.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(0, 0, 0));
        jLabel135.setText("* are the channels indoors in separate racks or rooms?");
        jPanel1.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 300, -1));

        jLabel136.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(0, 0, 0));
        jLabel136.setText("* are the channels on separate PCBs and screens?");
        jPanel1.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 300, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1070, 170));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Score");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 190, -1));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox3.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox3.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox3.setToolTipText("");
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

        jComboBox6.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox6.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox6.setToolTipText("");
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 130, 20));

        jComboBox7.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox7.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox7.setToolTipText("");
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 130, 20));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("<html>I. Do the redundant units employ different technologies?</html>");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 550, -1));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("<html>II. Were separate test methods and maintenance applied by separate people? </html>");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 390, -1));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("2.Diversity");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, -1));

        jLabel93.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 0));
        jLabel93.setText("* 1 electronic or programmable + 1 mechanical/pneumatic");
        jPanel2.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 300, -1));

        jLabel94.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 0));
        jLabel94.setText("* 1 electronic , 1 relay based?");
        jPanel2.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 300, -1));

        jLabel95.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setText("* 1 PE, 1 electronic hardwired?");
        jPanel2.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 300, -1));

        jComboBox8.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox8.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox8.setToolTipText("");
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 130, 20));

        jLabel96.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 0));
        jLabel96.setText("* Do the devices employ 'M out of N' voting where; N> M+?");
        jPanel2.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 300, -1));

        jComboBox9.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox9.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox9.setToolTipText("");
        jComboBox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox9ItemStateChanged(evt);
            }
        });
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 130, 20));

        jTextField16.setBackground(new java.awt.Color(204, 204, 204));
        jTextField16.setForeground(new java.awt.Color(0, 0, 153));
        jTextField16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField16MouseExited(evt);
            }
        });
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField16KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jTextField17.setBackground(new java.awt.Color(204, 204, 204));
        jTextField17.setForeground(new java.awt.Color(102, 0, 102));
        jTextField17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField17MouseExited(evt);
            }
        });
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jLabel50.setBackground(new java.awt.Color(0, 0, 0));
        jLabel50.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel50.setText("B Score ");
        jPanel2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, -1));

        jLabel54.setBackground(new java.awt.Color(0, 0, 0));
        jLabel54.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel54.setText("A Score ");
        jPanel2.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 50, -1));

        jTextField18.setBackground(new java.awt.Color(255, 255, 255));
        jTextField18.setForeground(new java.awt.Color(0, 0, 0));
        jTextField18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField18MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField18MouseExited(evt);
            }
        });
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jLabel56.setBackground(new java.awt.Color(0, 0, 0));
        jLabel56.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel56.setText("Comments ");
        jPanel2.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        jTextField21.setBackground(new java.awt.Color(204, 204, 204));
        jTextField21.setForeground(new java.awt.Color(0, 0, 0));
        jTextField21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField21MouseExited(evt);
            }
        });
        jTextField21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField21ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jTextField22.setEditable(false);
        jTextField22.setBackground(new java.awt.Color(204, 204, 204));
        jTextField22.setForeground(new java.awt.Color(0, 0, 153));
        jTextField22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField22MouseExited(evt);
            }
        });
        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });
        jTextField22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField22KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 60, -1));

        jTextField23.setEditable(false);
        jTextField23.setBackground(new java.awt.Color(204, 204, 204));
        jTextField23.setForeground(new java.awt.Color(102, 0, 102));
        jTextField23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField23MouseExited(evt);
            }
        });
        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 60, -1));

        jTextField24.setBackground(new java.awt.Color(255, 255, 255));
        jTextField24.setForeground(new java.awt.Color(0, 0, 0));
        jTextField24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField24MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField24MouseExited(evt);
            }
        });
        jTextField24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField24ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 310, -1));

        jTextField25.setEditable(false);
        jTextField25.setBackground(new java.awt.Color(204, 204, 204));
        jTextField25.setForeground(new java.awt.Color(0, 0, 153));
        jTextField25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField25MouseExited(evt);
            }
        });
        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        jTextField25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField25KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField26.setEditable(false);
        jTextField26.setBackground(new java.awt.Color(204, 204, 204));
        jTextField26.setForeground(new java.awt.Color(102, 0, 102));
        jTextField26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField26MouseExited(evt);
            }
        });
        jTextField26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField27.setBackground(new java.awt.Color(255, 255, 255));
        jTextField27.setForeground(new java.awt.Color(0, 0, 0));
        jTextField27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField27MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField27MouseExited(evt);
            }
        });
        jTextField27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField27ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jTextField28.setEditable(false);
        jTextField28.setBackground(new java.awt.Color(204, 204, 204));
        jTextField28.setForeground(new java.awt.Color(0, 0, 153));
        jTextField28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField28MouseExited(evt);
            }
        });
        jTextField28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28ActionPerformed(evt);
            }
        });
        jTextField28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField28KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jTextField29.setEditable(false);
        jTextField29.setBackground(new java.awt.Color(204, 204, 204));
        jTextField29.setForeground(new java.awt.Color(102, 0, 102));
        jTextField29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField29MouseExited(evt);
            }
        });
        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jTextField30.setBackground(new java.awt.Color(255, 255, 255));
        jTextField30.setForeground(new java.awt.Color(0, 0, 0));
        jTextField30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField30MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField30MouseExited(evt);
            }
        });
        jTextField30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField30ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 310, -1));

        jTextField31.setEditable(false);
        jTextField31.setBackground(new java.awt.Color(204, 204, 204));
        jTextField31.setForeground(new java.awt.Color(0, 0, 153));
        jTextField31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField31MouseExited(evt);
            }
        });
        jTextField31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField31ActionPerformed(evt);
            }
        });
        jTextField31.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField31KeyPressed(evt);
            }
        });
        jPanel2.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jTextField32.setEditable(false);
        jTextField32.setBackground(new java.awt.Color(204, 204, 204));
        jTextField32.setForeground(new java.awt.Color(102, 0, 102));
        jTextField32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField32MouseExited(evt);
            }
        });
        jTextField32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField32ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jTextField33.setBackground(new java.awt.Color(255, 255, 255));
        jTextField33.setForeground(new java.awt.Color(0, 0, 0));
        jTextField33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField33MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField33MouseExited(evt);
            }
        });
        jTextField33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField33ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 310, -1));

        jLabel57.setBackground(new java.awt.Color(0, 0, 0));
        jLabel57.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 102, 51));
        jLabel57.setText("Completed");
        jPanel2.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 210, 110, 30));

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 51));
        jLabel25.setText("0%");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 210, 140, 30));

        jComboBox16.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox16.setEditable(true);
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox16.setEnabled(false);
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jComboBox17.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox17.setEditable(true);
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox17.setEnabled(false);
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 50, 20));

        jComboBox19.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox19.setEditable(true);
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox19.setEnabled(false);
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jComboBox20.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox20.setEditable(true);
        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox20.setEnabled(false);
        jComboBox20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox20ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox20, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 50, 20));

        jComboBox24.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox24.setEditable(true);
        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox24.setEnabled(false);
        jComboBox24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox24ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox24, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 50, 20));

        jLabel111.setBackground(new java.awt.Color(0, 153, 204));
        jLabel111.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(102, 0, 0));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Score");
        jPanel2.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, 40, -1));

        jLabel112.setBackground(new java.awt.Color(0, 153, 204));
        jLabel112.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(102, 0, 0));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel112.setText("--------------");
        jPanel2.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 60, -1));

        jLabel113.setBackground(new java.awt.Color(0, 153, 204));
        jLabel113.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(102, 0, 0));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel113.setText("--------------");
        jPanel2.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 60, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 1070, 240));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("1.Separation / Segregation");
        add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 190, -1));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Score");
        add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 190, -1));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox10.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox10.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox10.setToolTipText("");
        jComboBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox10ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 130, 20));

        jComboBox11.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox11.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox11.setToolTipText("");
        jComboBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox11ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 130, 20));

        jComboBox12.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox12.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox12.setToolTipText("");
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox12, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 130, 20));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("<html>I. Does cross-connection preclude the exchange of any information other than the diagnostics? </html>");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 380, -1));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("<html>II. Is there > 5 years experience of the equipment in the particular environment?</html>");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 360, -1));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("<html>IV. Are devices protected from over-voltage and over-current and rated > 2:1 or machanical equivalent? </html>");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 410, -1));

        jComboBox18.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox18.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox18.setToolTipText("");
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 130, 20));

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("<html>III. Is the equipment simple e.g. non-programable type sensor or single actuator field device? </html>");
        jPanel3.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 400, -1));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 102, 51));
        jLabel29.setText("0%");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 210, 70, 30));

        jLabel61.setBackground(new java.awt.Color(0, 0, 0));
        jLabel61.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 102, 51));
        jLabel61.setText("Completed");
        jPanel3.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 210, 110, 30));

        jLabel86.setBackground(new java.awt.Color(0, 0, 0));
        jLabel86.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel86.setText("A Score ");
        jPanel3.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 50, -1));

        jTextField34.setEditable(false);
        jTextField34.setBackground(new java.awt.Color(204, 204, 204));
        jTextField34.setForeground(new java.awt.Color(0, 0, 153));
        jTextField34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField34MouseExited(evt);
            }
        });
        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });
        jTextField34.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField34KeyPressed(evt);
            }
        });
        jPanel3.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jLabel87.setBackground(new java.awt.Color(0, 0, 0));
        jLabel87.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel87.setText("B Score ");
        jPanel3.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        jTextField35.setEditable(false);
        jTextField35.setBackground(new java.awt.Color(204, 204, 204));
        jTextField35.setForeground(new java.awt.Color(102, 0, 102));
        jTextField35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField35MouseExited(evt);
            }
        });
        jTextField35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField35ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

        jTextField36.setBackground(new java.awt.Color(255, 255, 255));
        jTextField36.setForeground(new java.awt.Color(0, 0, 0));
        jTextField36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField36MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField36MouseExited(evt);
            }
        });
        jTextField36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField36ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jLabel88.setBackground(new java.awt.Color(0, 0, 0));
        jLabel88.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel88.setText("Comments ");
        jPanel3.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jTextField52.setEditable(false);
        jTextField52.setBackground(new java.awt.Color(204, 204, 204));
        jTextField52.setForeground(new java.awt.Color(0, 0, 153));
        jTextField52.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField52MouseExited(evt);
            }
        });
        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });
        jTextField52.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField52KeyPressed(evt);
            }
        });
        jPanel3.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, 60, -1));

        jTextField53.setEditable(false);
        jTextField53.setBackground(new java.awt.Color(204, 204, 204));
        jTextField53.setForeground(new java.awt.Color(102, 0, 102));
        jTextField53.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField53MouseExited(evt);
            }
        });
        jTextField53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField53ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField53, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 60, -1));

        jTextField54.setBackground(new java.awt.Color(255, 255, 255));
        jTextField54.setForeground(new java.awt.Color(0, 0, 0));
        jTextField54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField54MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField54MouseExited(evt);
            }
        });
        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 310, -1));

        jTextField55.setEditable(false);
        jTextField55.setBackground(new java.awt.Color(204, 204, 204));
        jTextField55.setForeground(new java.awt.Color(0, 0, 153));
        jTextField55.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField55MouseExited(evt);
            }
        });
        jTextField55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField55ActionPerformed(evt);
            }
        });
        jTextField55.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField55KeyPressed(evt);
            }
        });
        jPanel3.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 60, -1));

        jTextField56.setEditable(false);
        jTextField56.setBackground(new java.awt.Color(204, 204, 204));
        jTextField56.setForeground(new java.awt.Color(102, 0, 102));
        jTextField56.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField56MouseExited(evt);
            }
        });
        jTextField56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField56ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 60, -1));

        jTextField57.setBackground(new java.awt.Color(255, 255, 255));
        jTextField57.setForeground(new java.awt.Color(0, 0, 0));
        jTextField57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField57MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField57MouseExited(evt);
            }
        });
        jTextField57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField57ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField57, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, 310, -1));

        jTextField58.setEditable(false);
        jTextField58.setBackground(new java.awt.Color(204, 204, 204));
        jTextField58.setForeground(new java.awt.Color(0, 0, 153));
        jTextField58.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField58.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField58MouseExited(evt);
            }
        });
        jTextField58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField58ActionPerformed(evt);
            }
        });
        jTextField58.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField58KeyPressed(evt);
            }
        });
        jPanel3.add(jTextField58, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 60, -1));

        jTextField59.setEditable(false);
        jTextField59.setBackground(new java.awt.Color(204, 204, 204));
        jTextField59.setForeground(new java.awt.Color(102, 0, 102));
        jTextField59.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField59MouseExited(evt);
            }
        });
        jTextField59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField59ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField59, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 60, -1));

        jTextField60.setBackground(new java.awt.Color(255, 255, 255));
        jTextField60.setForeground(new java.awt.Color(0, 0, 0));
        jTextField60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField60MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField60MouseExited(evt);
            }
        });
        jTextField60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField60ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField60, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, 310, -1));

        jComboBox27.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox27.setEditable(true);
        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox27.setEnabled(false);
        jComboBox27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox27ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox27, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jComboBox28.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox28.setEditable(true);
        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox28.setEnabled(false);
        jComboBox28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox28ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 50, 20));

        jComboBox32.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox32.setEditable(true);
        jComboBox32.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox32.setEnabled(false);
        jComboBox32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox32ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox32, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 50, 20));

        jComboBox38.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox38.setEditable(true);
        jComboBox38.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox38.setEnabled(false);
        jComboBox38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox38ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox38, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 50, 20));

        jLabel114.setBackground(new java.awt.Color(0, 153, 204));
        jLabel114.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(102, 0, 0));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Score");
        jPanel3.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, 40, -1));

        jLabel115.setBackground(new java.awt.Color(0, 153, 204));
        jLabel115.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(102, 0, 0));
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel115.setText("--------------");
        jPanel3.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 60, -1));

        jLabel116.setBackground(new java.awt.Color(0, 153, 204));
        jLabel116.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(102, 0, 0));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel116.setText("--------------");
        jPanel3.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 60, -1));

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("3.Complexity/design/application/maturity/experience ");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 450, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 1070, 240));

        jLabel51.setBackground(new java.awt.Color(0, 153, 204));
        jLabel51.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 0, 0));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Score");
        add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 40, -1));

        jLabel52.setBackground(new java.awt.Color(0, 153, 204));
        jLabel52.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 0, 0));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel52.setText("--------------------------------");
        add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 130, -1));

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox29.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox29.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox29.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox29.setToolTipText("");
        jComboBox29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox29ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox29, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

        jComboBox30.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox30.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox30.setToolTipText("");
        jComboBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox30ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox30, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 130, 20));

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("<html> I. Has a combination of detailed FMEA, fault tree analysis and design review  established potential CCFs in the electronics? </html>");
        jPanel7.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 410, -1));

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("<html>II.Is there documentary evidence that field failures are fully analyzed with feedback to design?</html>");
        jPanel7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 410, -1));

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("4. Assessment/Analysis and feedback of data");
        jPanel7.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, -1));

        jLabel60.setBackground(new java.awt.Color(0, 0, 0));
        jLabel60.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 102, 51));
        jLabel60.setText("Completed");
        jPanel7.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 140, 110, 30));

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 51));
        jLabel30.setText("0%");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 140, 140, 30));

        jLabel89.setBackground(new java.awt.Color(0, 0, 0));
        jLabel89.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel89.setText("A Score ");
        jPanel7.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 50, -1));

        jTextField37.setEditable(false);
        jTextField37.setBackground(new java.awt.Color(204, 204, 204));
        jTextField37.setForeground(new java.awt.Color(0, 0, 153));
        jTextField37.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField37MouseExited(evt);
            }
        });
        jTextField37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField37ActionPerformed(evt);
            }
        });
        jTextField37.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField37KeyPressed(evt);
            }
        });
        jPanel7.add(jTextField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jLabel90.setBackground(new java.awt.Color(0, 0, 0));
        jLabel90.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel90.setText("B Score ");
        jPanel7.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, -1));

        jTextField38.setEditable(false);
        jTextField38.setBackground(new java.awt.Color(204, 204, 204));
        jTextField38.setForeground(new java.awt.Color(102, 0, 102));
        jTextField38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField38MouseExited(evt);
            }
        });
        jTextField38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField38ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jTextField39.setBackground(new java.awt.Color(255, 255, 255));
        jTextField39.setForeground(new java.awt.Color(0, 0, 0));
        jTextField39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField39MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField39MouseExited(evt);
            }
        });
        jTextField39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField39ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jLabel91.setBackground(new java.awt.Color(0, 0, 0));
        jLabel91.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel91.setText("Comments ");
        jPanel7.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        jTextField61.setEditable(false);
        jTextField61.setBackground(new java.awt.Color(204, 204, 204));
        jTextField61.setForeground(new java.awt.Color(0, 0, 153));
        jTextField61.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField61MouseExited(evt);
            }
        });
        jTextField61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField61ActionPerformed(evt);
            }
        });
        jTextField61.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField61KeyPressed(evt);
            }
        });
        jPanel7.add(jTextField61, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField62.setEditable(false);
        jTextField62.setBackground(new java.awt.Color(204, 204, 204));
        jTextField62.setForeground(new java.awt.Color(102, 0, 102));
        jTextField62.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField62MouseExited(evt);
            }
        });
        jTextField62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField62ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField62, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField63.setBackground(new java.awt.Color(255, 255, 255));
        jTextField63.setForeground(new java.awt.Color(0, 0, 0));
        jTextField63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField63MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField63MouseExited(evt);
            }
        });
        jTextField63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField63ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField63, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jComboBox39.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox39.setEditable(true);
        jComboBox39.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox39.setEnabled(false);
        jComboBox39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox39ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox39, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jComboBox40.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox40.setEditable(true);
        jComboBox40.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox40.setEnabled(false);
        jComboBox40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox40ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox40, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jLabel117.setBackground(new java.awt.Color(0, 153, 204));
        jLabel117.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(102, 0, 0));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Score");
        jPanel7.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 140, 40, -1));

        jLabel118.setBackground(new java.awt.Color(0, 153, 204));
        jLabel118.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(102, 0, 0));
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel118.setText("--------------");
        jPanel7.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 60, -1));

        jLabel119.setBackground(new java.awt.Color(0, 153, 204));
        jLabel119.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(102, 0, 0));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel119.setText("--------------");
        jPanel7.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 60, -1));

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 760, 1070, 170));

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox33.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox33.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox33.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox33.setToolTipText("");
        jComboBox33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox33ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox33, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 130, 20));

        jComboBox34.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox34.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox34.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox34.setToolTipText("");
        jComboBox34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox34ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox34, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 130, 20));

        jComboBox35.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox35.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox35.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox35.setToolTipText("");
        jComboBox35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox35ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox35, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 130, 20));

        jComboBox36.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox36.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox36.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox36.setToolTipText("");
        jComboBox36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox36ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox36, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 130, 20));

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("<html>I. Is there written system of work on site to ensure that failures are investigated and checked in other channels?  (including degraded items that have not yet failed)</html>");
        jPanel8.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 410, 50));

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("<html>II. Is maintenance of diverse/redundant channels stagged at such an interval as to ensure that any proof-tests and cross-checks operate satisfactorily between the maintenance?</html>");
        jPanel8.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 410, 40));

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("<html>III. Do written maintenance procedures ensure that redundant separations,as,for example ,signal cables, are separated from each other and from power cables and must not be re-routed? </html>");
        jPanel8.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 410, -1));

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("IV. Are modifications forbidden without full design analysis of CCF?");
        jPanel8.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 450, -1));

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("V. Is diverse euipment maintained by different staff?");
        jPanel8.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 370, -1));

        jLabel55.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("5.Procedures/human interface");
        jPanel8.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, -1));

        jComboBox31.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox31.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox31.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox31.setToolTipText("");
        jComboBox31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox31ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox31, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 130, 20));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 102, 51));
        jLabel31.setText("0%");
        jPanel8.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 240, 60, 30));

        jLabel74.setBackground(new java.awt.Color(0, 0, 0));
        jLabel74.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 102, 51));
        jLabel74.setText("Completed");
        jPanel8.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 240, 110, 30));

        jLabel92.setBackground(new java.awt.Color(0, 0, 0));
        jLabel92.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel92.setText("A Score ");
        jPanel8.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 50, -1));

        jTextField40.setEditable(false);
        jTextField40.setBackground(new java.awt.Color(204, 204, 204));
        jTextField40.setForeground(new java.awt.Color(0, 0, 153));
        jTextField40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField40MouseExited(evt);
            }
        });
        jTextField40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField40ActionPerformed(evt);
            }
        });
        jTextField40.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField40KeyPressed(evt);
            }
        });
        jPanel8.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jLabel97.setBackground(new java.awt.Color(0, 0, 0));
        jLabel97.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel97.setText("B Score ");
        jPanel8.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        jTextField41.setEditable(false);
        jTextField41.setBackground(new java.awt.Color(204, 204, 204));
        jTextField41.setForeground(new java.awt.Color(102, 0, 102));
        jTextField41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField41MouseExited(evt);
            }
        });
        jTextField41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField41ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField41, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

        jTextField42.setBackground(new java.awt.Color(255, 255, 255));
        jTextField42.setForeground(new java.awt.Color(0, 0, 0));
        jTextField42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField42MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField42MouseExited(evt);
            }
        });
        jTextField42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField42ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jLabel98.setBackground(new java.awt.Color(0, 0, 0));
        jLabel98.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel98.setText("Comments ");
        jPanel8.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jTextField64.setEditable(false);
        jTextField64.setBackground(new java.awt.Color(204, 204, 204));
        jTextField64.setForeground(new java.awt.Color(0, 0, 153));
        jTextField64.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField64MouseExited(evt);
            }
        });
        jTextField64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField64ActionPerformed(evt);
            }
        });
        jTextField64.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField64KeyPressed(evt);
            }
        });
        jPanel8.add(jTextField64, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField65.setEditable(false);
        jTextField65.setBackground(new java.awt.Color(204, 204, 204));
        jTextField65.setForeground(new java.awt.Color(102, 0, 102));
        jTextField65.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField65MouseExited(evt);
            }
        });
        jTextField65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField65ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField65, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField66.setBackground(new java.awt.Color(255, 255, 255));
        jTextField66.setForeground(new java.awt.Color(0, 0, 0));
        jTextField66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField66MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField66MouseExited(evt);
            }
        });
        jTextField66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField66ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField66, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jTextField67.setEditable(false);
        jTextField67.setBackground(new java.awt.Color(204, 204, 204));
        jTextField67.setForeground(new java.awt.Color(0, 0, 153));
        jTextField67.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField67MouseExited(evt);
            }
        });
        jTextField67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField67ActionPerformed(evt);
            }
        });
        jTextField67.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField67KeyPressed(evt);
            }
        });
        jPanel8.add(jTextField67, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 60, -1));

        jTextField68.setEditable(false);
        jTextField68.setBackground(new java.awt.Color(204, 204, 204));
        jTextField68.setForeground(new java.awt.Color(102, 0, 102));
        jTextField68.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField68MouseExited(evt);
            }
        });
        jTextField68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField68ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField68, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 60, -1));

        jTextField69.setBackground(new java.awt.Color(255, 255, 255));
        jTextField69.setForeground(new java.awt.Color(0, 0, 0));
        jTextField69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField69MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField69MouseExited(evt);
            }
        });
        jTextField69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField69ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField69, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 310, -1));

        jTextField70.setEditable(false);
        jTextField70.setBackground(new java.awt.Color(204, 204, 204));
        jTextField70.setForeground(new java.awt.Color(0, 0, 153));
        jTextField70.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField70MouseExited(evt);
            }
        });
        jTextField70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField70ActionPerformed(evt);
            }
        });
        jTextField70.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField70KeyPressed(evt);
            }
        });
        jPanel8.add(jTextField70, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 60, -1));

        jTextField71.setEditable(false);
        jTextField71.setBackground(new java.awt.Color(204, 204, 204));
        jTextField71.setForeground(new java.awt.Color(102, 0, 102));
        jTextField71.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField71MouseExited(evt);
            }
        });
        jTextField71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField71ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField71, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 60, -1));

        jTextField72.setBackground(new java.awt.Color(255, 255, 255));
        jTextField72.setForeground(new java.awt.Color(0, 0, 0));
        jTextField72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField72MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField72MouseExited(evt);
            }
        });
        jTextField72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField72ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField72, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 180, 310, -1));

        jTextField73.setEditable(false);
        jTextField73.setBackground(new java.awt.Color(204, 204, 204));
        jTextField73.setForeground(new java.awt.Color(0, 0, 153));
        jTextField73.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField73MouseExited(evt);
            }
        });
        jTextField73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField73ActionPerformed(evt);
            }
        });
        jTextField73.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField73KeyPressed(evt);
            }
        });
        jPanel8.add(jTextField73, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 60, -1));

        jTextField74.setEditable(false);
        jTextField74.setBackground(new java.awt.Color(204, 204, 204));
        jTextField74.setForeground(new java.awt.Color(102, 0, 102));
        jTextField74.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField74MouseExited(evt);
            }
        });
        jTextField74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField74ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField74, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 60, -1));

        jTextField75.setBackground(new java.awt.Color(255, 255, 255));
        jTextField75.setForeground(new java.awt.Color(0, 0, 0));
        jTextField75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField75MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField75MouseExited(evt);
            }
        });
        jTextField75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField75ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField75, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 210, 310, -1));

        jComboBox41.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox41.setEditable(true);
        jComboBox41.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox41.setEnabled(false);
        jComboBox41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox41ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox41, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 50, 20));

        jComboBox42.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox42.setEditable(true);
        jComboBox42.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox42.setEnabled(false);
        jComboBox42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox42ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox42, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 50, 20));

        jComboBox43.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox43.setEditable(true);
        jComboBox43.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox43.setEnabled(false);
        jComboBox43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox43ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox43, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 50, 20));

        jComboBox44.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox44.setEditable(true);
        jComboBox44.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox44.setEnabled(false);
        jComboBox44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox44ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox44, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jComboBox45.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox45.setEditable(true);
        jComboBox45.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox45.setEnabled(false);
        jComboBox45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox45ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox45, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jLabel120.setBackground(new java.awt.Color(0, 153, 204));
        jLabel120.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(102, 0, 0));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("Score");
        jPanel8.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 250, 40, -1));

        jLabel121.setBackground(new java.awt.Color(0, 153, 204));
        jLabel121.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(102, 0, 0));
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel121.setText("--------------");
        jPanel8.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 60, -1));

        jLabel122.setBackground(new java.awt.Color(0, 153, 204));
        jLabel122.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(102, 0, 0));
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel122.setText("--------------");
        jPanel8.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 60, -1));

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 940, 1070, 270));

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox25.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox25.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox25.setToolTipText("");
        jComboBox25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox25ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox25, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 130, 20));

        jComboBox26.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox26.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox26.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox26.setToolTipText("");
        jComboBox26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox26ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 55, 130, 20));

        jLabel48.setBackground(new java.awt.Color(0, 51, 102));
        jLabel48.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("I. Is there limited personnel access?");
        jPanel6.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 300, -1));

        jLabel49.setBackground(new java.awt.Color(0, 51, 102));
        jLabel49.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("<html>II. Is there appropriate environmental control? <br> (e.g temperture,humidity)</html>");
        jPanel6.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 430, -1));

        jLabel53.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("7. Environmental control ");
        jPanel6.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel84.setBackground(new java.awt.Color(0, 0, 0));
        jLabel84.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 102, 51));
        jLabel84.setText("Completed");
        jPanel6.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 90, 110, 30));

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 102, 51));
        jLabel35.setText("0%");
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 90, 60, 30));

        jLabel102.setBackground(new java.awt.Color(0, 0, 0));
        jLabel102.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel102.setText("A Score ");
        jPanel6.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 50, -1));

        jTextField46.setEditable(false);
        jTextField46.setBackground(new java.awt.Color(204, 204, 204));
        jTextField46.setForeground(new java.awt.Color(0, 0, 153));
        jTextField46.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField46MouseExited(evt);
            }
        });
        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46ActionPerformed(evt);
            }
        });
        jTextField46.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField46KeyPressed(evt);
            }
        });
        jPanel6.add(jTextField46, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 60, -1));

        jLabel103.setBackground(new java.awt.Color(0, 0, 0));
        jLabel103.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel103.setText("B Score ");
        jPanel6.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        jTextField47.setEditable(false);
        jTextField47.setBackground(new java.awt.Color(204, 204, 204));
        jTextField47.setForeground(new java.awt.Color(102, 0, 102));
        jTextField47.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField47MouseExited(evt);
            }
        });
        jTextField47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField47ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 60, -1));

        jTextField48.setBackground(new java.awt.Color(255, 255, 255));
        jTextField48.setForeground(new java.awt.Color(0, 0, 0));
        jTextField48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField48MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField48MouseExited(evt);
            }
        });
        jTextField48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField48ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField48, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 310, -1));

        jLabel104.setBackground(new java.awt.Color(0, 0, 0));
        jLabel104.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel104.setText("Comments ");
        jPanel6.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jTextField82.setEditable(false);
        jTextField82.setBackground(new java.awt.Color(204, 204, 204));
        jTextField82.setForeground(new java.awt.Color(0, 0, 153));
        jTextField82.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField82MouseExited(evt);
            }
        });
        jTextField82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField82ActionPerformed(evt);
            }
        });
        jTextField82.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField82KeyPressed(evt);
            }
        });
        jPanel6.add(jTextField82, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 54, 60, -1));

        jTextField83.setEditable(false);
        jTextField83.setBackground(new java.awt.Color(204, 204, 204));
        jTextField83.setForeground(new java.awt.Color(102, 0, 102));
        jTextField83.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField83MouseExited(evt);
            }
        });
        jTextField83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField83ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField83, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 54, 60, -1));

        jTextField84.setBackground(new java.awt.Color(255, 255, 255));
        jTextField84.setForeground(new java.awt.Color(0, 0, 0));
        jTextField84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField84MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField84MouseExited(evt);
            }
        });
        jTextField84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField84ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField84, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 54, 310, -1));

        jComboBox49.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox49.setEditable(true);
        jComboBox49.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox49.setEnabled(false);
        jComboBox49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox49ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox49, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 55, 50, 20));

        jComboBox50.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox50.setEditable(true);
        jComboBox50.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox50.setEnabled(false);
        jComboBox50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox50ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox50, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

        jLabel126.setBackground(new java.awt.Color(0, 153, 204));
        jLabel126.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(102, 0, 0));
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel126.setText("Score");
        jPanel6.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 40, -1));

        jLabel127.setBackground(new java.awt.Color(0, 153, 204));
        jLabel127.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(102, 0, 0));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel127.setText("--------------");
        jPanel6.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 60, -1));

        jLabel128.setBackground(new java.awt.Color(0, 153, 204));
        jLabel128.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(102, 0, 0));
        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel128.setText("--------------");
        jPanel6.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 60, -1));

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1360, 1070, 120));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox21.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox21.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox21.setToolTipText("");
        jComboBox21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox21ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 130, 20));

        jComboBox22.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox22.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox22.setToolTipText("");
        jComboBox22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox22ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

        jComboBox23.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox23.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox23.setToolTipText("");
        jComboBox23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox23ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox23, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 130, 20));

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("I.Have designers been trained to understand CCF?");
        jPanel5.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 300, -1));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("II.Have installers been trained to understand CCF?");
        jPanel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 370, -1));

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("III.Have maintainers been trained to understand CCF?");
        jPanel5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 370, -1));

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("6.Competence/training/safty culture");
        jPanel5.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 430, -1));

        jLabel69.setBackground(new java.awt.Color(0, 0, 0));
        jLabel69.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 102, 51));
        jLabel69.setText("Completed");
        jPanel5.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 110, 30));

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 102, 51));
        jLabel34.setText("0%");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 100, 60, 30));

        jLabel99.setBackground(new java.awt.Color(0, 0, 0));
        jLabel99.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel99.setText("A Score ");
        jPanel5.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 50, -1));

        jTextField43.setEditable(false);
        jTextField43.setBackground(new java.awt.Color(204, 204, 204));
        jTextField43.setForeground(new java.awt.Color(0, 0, 153));
        jTextField43.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField43MouseExited(evt);
            }
        });
        jTextField43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField43ActionPerformed(evt);
            }
        });
        jTextField43.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField43KeyPressed(evt);
            }
        });
        jPanel5.add(jTextField43, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 60, -1));

        jLabel100.setBackground(new java.awt.Color(0, 0, 0));
        jLabel100.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel100.setText("B Score ");
        jPanel5.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        jTextField44.setEditable(false);
        jTextField44.setBackground(new java.awt.Color(204, 204, 204));
        jTextField44.setForeground(new java.awt.Color(102, 0, 102));
        jTextField44.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField44MouseExited(evt);
            }
        });
        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField44, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 60, -1));

        jTextField45.setBackground(new java.awt.Color(255, 255, 255));
        jTextField45.setForeground(new java.awt.Color(0, 0, 0));
        jTextField45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField45MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField45MouseExited(evt);
            }
        });
        jTextField45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField45ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField45, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 310, -1));

        jLabel101.setBackground(new java.awt.Color(0, 0, 0));
        jLabel101.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel101.setText("Comments ");
        jPanel5.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jTextField76.setEditable(false);
        jTextField76.setBackground(new java.awt.Color(204, 204, 204));
        jTextField76.setForeground(new java.awt.Color(0, 0, 153));
        jTextField76.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField76MouseExited(evt);
            }
        });
        jTextField76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField76ActionPerformed(evt);
            }
        });
        jTextField76.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField76KeyPressed(evt);
            }
        });
        jPanel5.add(jTextField76, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jTextField77.setEditable(false);
        jTextField77.setBackground(new java.awt.Color(204, 204, 204));
        jTextField77.setForeground(new java.awt.Color(102, 0, 102));
        jTextField77.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField77.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField77MouseExited(evt);
            }
        });
        jTextField77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField77ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField77, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jTextField78.setBackground(new java.awt.Color(255, 255, 255));
        jTextField78.setForeground(new java.awt.Color(0, 0, 0));
        jTextField78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField78MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField78MouseExited(evt);
            }
        });
        jTextField78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField78ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField78, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jTextField79.setEditable(false);
        jTextField79.setBackground(new java.awt.Color(204, 204, 204));
        jTextField79.setForeground(new java.awt.Color(0, 0, 153));
        jTextField79.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField79MouseExited(evt);
            }
        });
        jTextField79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField79ActionPerformed(evt);
            }
        });
        jTextField79.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField79KeyPressed(evt);
            }
        });
        jPanel5.add(jTextField79, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 60, -1));

        jTextField80.setEditable(false);
        jTextField80.setBackground(new java.awt.Color(204, 204, 204));
        jTextField80.setForeground(new java.awt.Color(102, 0, 102));
        jTextField80.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField80MouseExited(evt);
            }
        });
        jTextField80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField80ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField80, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 60, -1));

        jTextField81.setBackground(new java.awt.Color(255, 255, 255));
        jTextField81.setForeground(new java.awt.Color(0, 0, 0));
        jTextField81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField81MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField81MouseExited(evt);
            }
        });
        jTextField81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField81ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField81, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 310, -1));

        jComboBox46.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox46.setEditable(true);
        jComboBox46.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox46.setEnabled(false);
        jComboBox46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox46ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox46, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 50, 20));

        jComboBox47.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox47.setEditable(true);
        jComboBox47.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox47.setEnabled(false);
        jComboBox47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox47ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox47, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jComboBox48.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox48.setEditable(true);
        jComboBox48.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox48.setEnabled(false);
        jComboBox48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox48ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox48, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

        jLabel123.setBackground(new java.awt.Color(0, 153, 204));
        jLabel123.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(102, 0, 0));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("Score");
        jPanel5.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 40, -1));

        jLabel124.setBackground(new java.awt.Color(0, 153, 204));
        jLabel124.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(102, 0, 0));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel124.setText("--------------");
        jPanel5.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jLabel125.setBackground(new java.awt.Color(0, 153, 204));
        jLabel125.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(102, 0, 0));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel125.setText("--------------");
        jPanel5.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1220, 1070, 130));

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Score");
        jPanel9.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel81.setBackground(new java.awt.Color(102, 51, 0));
        jLabel81.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(102, 0, 0));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("------------");
        jPanel9.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 240, -1));

        jLabel108.setBackground(new java.awt.Color(102, 51, 0));
        jLabel108.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(102, 0, 0));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("---------------");
        jPanel9.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 250, -1));

        jLabel109.setBackground(new java.awt.Color(153, 0, 51));
        jLabel109.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(102, 0, 0));
        jLabel109.setText("Beta Factor ");
        jPanel9.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 110, -1));

        jLabel110.setBackground(new java.awt.Color(153, 0, 51));
        jLabel110.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(102, 0, 0));
        jLabel110.setText("Raw Score ");
        jPanel9.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, -1));

        add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1870, 920, 80));

        jPanel10.setBackground(new java.awt.Color(102, 102, 102));
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox37.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox37.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox37.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox37.setToolTipText("");
        jComboBox37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox37ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox37, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 130, 20));

        jLabel63.setBackground(new java.awt.Color(0, 51, 102));
        jLabel63.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("<html>I. Has full EMC immunity or equivalent mechanical testing been conducted on prototypes and production units (using recognized standards)</html>");
        jPanel10.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 410, -1));

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("8.Environmental testing");
        jPanel10.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Score");
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel85.setBackground(new java.awt.Color(0, 0, 0));
        jLabel85.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 102, 51));
        jLabel85.setText("Completed");
        jPanel10.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 110, 30));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 51));
        jLabel17.setText("0%");
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 100, 60, 30));

        jLabel105.setBackground(new java.awt.Color(0, 0, 0));
        jLabel105.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel105.setText("A Score ");
        jPanel10.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        jTextField49.setEditable(false);
        jTextField49.setBackground(new java.awt.Color(204, 204, 204));
        jTextField49.setForeground(new java.awt.Color(0, 0, 153));
        jTextField49.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField49MouseExited(evt);
            }
        });
        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });
        jTextField49.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField49KeyPressed(evt);
            }
        });
        jPanel10.add(jTextField49, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jLabel106.setBackground(new java.awt.Color(0, 0, 0));
        jLabel106.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel106.setText("B Score ");
        jPanel10.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        jTextField50.setEditable(false);
        jTextField50.setBackground(new java.awt.Color(204, 204, 204));
        jTextField50.setForeground(new java.awt.Color(102, 0, 102));
        jTextField50.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField50MouseExited(evt);
            }
        });
        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField50, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

        jTextField51.setBackground(new java.awt.Color(255, 255, 255));
        jTextField51.setForeground(new java.awt.Color(0, 0, 0));
        jTextField51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField51MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField51MouseExited(evt);
            }
        });
        jTextField51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField51ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField51, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jLabel107.setBackground(new java.awt.Color(0, 0, 0));
        jLabel107.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel107.setText("Comments ");
        jPanel10.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jComboBox51.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox51.setEditable(true);
        jComboBox51.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox51.setEnabled(false);
        jComboBox51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox51ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox51, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jLabel129.setBackground(new java.awt.Color(0, 153, 204));
        jLabel129.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(102, 0, 0));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("Score");
        jPanel10.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 40, -1));

        jLabel130.setBackground(new java.awt.Color(0, 153, 204));
        jLabel130.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(102, 0, 0));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel130.setText("--------------");
        jPanel10.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 60, -1));

        jLabel131.setBackground(new java.awt.Color(0, 153, 204));
        jLabel131.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(102, 0, 0));
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel131.setText("--------------");
        jPanel10.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 60, -1));

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1490, 1070, 130));

        jLabel37.setBackground(new java.awt.Color(153, 153, 153));
        jLabel37.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(102, 0, 0));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("0%");
        jLabel37.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel37.setOpaque(true);
        add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 1790, 140, 60));

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel38.setText("Total Completed");
        add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 1768, 150, -1));

        jPanel11.setBackground(new java.awt.Color(153, 153, 153));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Score");
        jPanel11.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel82.setBackground(new java.awt.Color(102, 51, 0));
        jLabel82.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 24)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(102, 0, 0));
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Total Non Programable Electronics Containers Score  =");
        jPanel11.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 630, -1));

        jLabel83.setBackground(new java.awt.Color(102, 51, 0));
        jLabel83.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(102, 0, 0));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setText("------");
        jPanel11.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 100, -1));

        jLabel132.setBackground(new java.awt.Color(102, 51, 0));
        jLabel132.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(102, 0, 0));
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("------");
        jPanel11.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 100, -1));

        jLabel133.setBackground(new java.awt.Color(153, 0, 51));
        jLabel133.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(102, 0, 0));
        jLabel133.setText("B Score");
        jPanel11.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 70, -1));

        jLabel134.setBackground(new java.awt.Color(153, 0, 51));
        jLabel134.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(102, 0, 0));
        jLabel134.setText("A Score");
        jPanel11.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 70, -1));

        add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1770, 920, 80));

        jTextArea1.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Your Final Comments");
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1640, 1070, 120));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Diagnostic Coverage");
        add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 1870, 130, -1));

        jButton1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jButton1.setText("60");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 1890, 130, 60));

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 1970, 1070, -1));

        jLabel71.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Betaplus");
        add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1070, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
      JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5};
        markedPercentage(combo,jLabel18);
       CalculateScoreMax(0,0);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5};
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(0,1);
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5};
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(0,2);
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox8,jComboBox9,jComboBox7};
        markedPercentage(combo,jLabel25);
        CalculateScoreMax(1,0);
        
        
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox8,jComboBox9,jComboBox7};
        markedPercentage(combo,jLabel25);
        CalculateScoreMax(1,1);
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox8,jComboBox9,jComboBox7};
        markedPercentage(combo,jLabel25);
        CalculateScoreMax(1,4);
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox8,jComboBox9,jComboBox7};
        markedPercentage(combo,jLabel25);
        CalculateScoreMax(1,2);
    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox9ActionPerformed
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox8,jComboBox9,jComboBox7};
        markedPercentage(combo,jLabel25);
        CalculateScoreMax(1,3);
    }//GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox10ActionPerformed
        JComboBox[] combo ={jComboBox10,jComboBox18,jComboBox11,jComboBox12};
        markedPercentage(combo,jLabel29);
        CalculateScoreMax(2,0);
    }//GEN-LAST:event_jComboBox10ActionPerformed

    private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox11ActionPerformed
        JComboBox[] combo ={jComboBox10,jComboBox18,jComboBox11,jComboBox12};
        markedPercentage(combo,jLabel29);
        CalculateScoreMax(2,2);
    }//GEN-LAST:event_jComboBox11ActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        JComboBox[] combo ={jComboBox10,jComboBox18,jComboBox11,jComboBox12};
        markedPercentage(combo,jLabel29);
        CalculateScoreMax(2,3);
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox18ActionPerformed
        JComboBox[] combo ={jComboBox10,jComboBox18,jComboBox11,jComboBox12};
        markedPercentage(combo,jLabel29);
         CalculateScoreMax(2,1);
    }//GEN-LAST:event_jComboBox18ActionPerformed

    private void jComboBox29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox29ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox29,jComboBox30};
        markedPercentage(combo,jLabel30);
        CalculateScoreMax(3,0);
    }//GEN-LAST:event_jComboBox29ActionPerformed

    private void jComboBox30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox30ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox29,jComboBox30};
        markedPercentage(combo,jLabel30);
        CalculateScoreMax(3,1);
        
    }//GEN-LAST:event_jComboBox30ActionPerformed

    private void jComboBox33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox33ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        markedPercentage(combo,jLabel31);
        CalculateScoreMax(4,0);
    }//GEN-LAST:event_jComboBox33ActionPerformed

    private void jComboBox34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox34ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        markedPercentage(combo,jLabel31);
        CalculateScoreMax(4,1);
    }//GEN-LAST:event_jComboBox34ActionPerformed

    private void jComboBox35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox35ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        markedPercentage(combo,jLabel31);
        CalculateScoreMax(4,2);
    }//GEN-LAST:event_jComboBox35ActionPerformed

    private void jComboBox36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox36ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        markedPercentage(combo,jLabel31);
        CalculateScoreMax(4,3);
    }//GEN-LAST:event_jComboBox36ActionPerformed

    private void jComboBox31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox31ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        markedPercentage(combo,jLabel31);
        CalculateScoreMax(4,4);
    }//GEN-LAST:event_jComboBox31ActionPerformed

    private void jComboBox25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox25ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox25,jComboBox26};
        markedPercentage(combo,jLabel35);
        CalculateScoreMax(6,0);
    }//GEN-LAST:event_jComboBox25ActionPerformed

    private void jComboBox26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox26ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox25,jComboBox26};
        markedPercentage(combo,jLabel35);
        CalculateScoreMax(6,1);
    }//GEN-LAST:event_jComboBox26ActionPerformed

    private void jComboBox21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox21ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};
        markedPercentage(combo,jLabel34);
        CalculateScoreMax(5,0);
    }//GEN-LAST:event_jComboBox21ActionPerformed

    private void jComboBox22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox22ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};
        markedPercentage(combo,jLabel34);
        CalculateScoreMax(5,1);
    }//GEN-LAST:event_jComboBox22ActionPerformed

    private void jComboBox23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox23ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};
        markedPercentage(combo,jLabel34);
        CalculateScoreMax(5,2);
    }//GEN-LAST:event_jComboBox23ActionPerformed

    private void jComboBox37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox37ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox37};
        markedPercentage(combo,jLabel17);
        CalculateScoreMax(7,0);
        
    }//GEN-LAST:event_jComboBox37ActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
       
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
     JOptionPane.showMessageDialog(null,"Eggs are not supposed to be green.","Help",JOptionPane.QUESTION_MESSAGE);      
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jTextField3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseExited

    }//GEN-LAST:event_jTextField3MouseExited

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseExited
       /*JTextField[] scores = {jTextField1,jTextField11,jTextField14,};
                
        CalculateMaxScore(scores,jLabel2);
        
        System.out.println("gnsfhmjg");
        */
        
        /* try {
            Integer.parseInt(jTextField1.getText()+"");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1MouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
     
     /*  JTextField[] scores = {jTextField1,jTextField11,jTextField14,};
                
        CalculateMaxScore(scores,jLabel2);
        
        System.out.println("gnsfhmjg");*/
        
        /*  try {
            Integer.parseInt(jTextField1.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            //jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseExited
       /*JTextField[] scores = {jTextField2,jTextField10,jTextField13,};
                
        CalculateMaxScore(scores,jLabel3);*/
       
        
        /* try {
            int i= Integer.parseInt(jTextField2.getText()+"0");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            jTextField2.setText("0");
        }*/
    }//GEN-LAST:event_jTextField2MouseExited

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        
        /*JTextField[] scores = {jTextField2,jTextField10,jTextField13,};
                
        CalculateMaxScore(scores,jLabel3);
        
        System.out.println("gnsfhmjg");*/
        /*try {
            Integer.parseInt(jTextField2.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
        }*/
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField10MouseExited
       /* JTextField[] scores = {jTextField2,jTextField10,jTextField13,};
                
        CalculateMaxScore(scores,jLabel3);
        */
    }//GEN-LAST:event_jTextField10MouseExited

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
       /*/ JTextField[] scores = {jTextField2,jTextField10,jTextField13,};
                
        CalculateMaxScore(scores,jLabel3);
        */
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10KeyPressed

    private void jTextField11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField11MouseExited
       /* JTextField[] scores = {jTextField1,jTextField11,jTextField14,};
                
        CalculateMaxScore(scores,jLabel2);
         */
    }//GEN-LAST:event_jTextField11MouseExited

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField12MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12MouseExited

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField13MouseExited
       /*JTextField[] scores = {jTextField2,jTextField10,jTextField13,};
                
        CalculateMaxScore(scores,jLabel3);
        */
    }//GEN-LAST:event_jTextField13MouseExited

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
       /*/ JTextField[] scores = {jTextField2,jTextField10,jTextField13,};     
        CalculateMaxScore(scores,jLabel3);
      */
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13KeyPressed

    private void jTextField14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField14MouseExited
       /* JTextField[] scores = {jTextField1,jTextField11,jTextField14,};
                
        CalculateMaxScore(scores,jLabel2);
       */
    }//GEN-LAST:event_jTextField14MouseExited

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15MouseExited

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16MouseExited

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16KeyPressed

    private void jTextField17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField17MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17MouseExited

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jTextField18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField18MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18MouseExited

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField18ActionPerformed

    private void jTextField21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField21MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21MouseExited

    private void jTextField21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField21ActionPerformed

    private void jTextField22MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField22MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22MouseExited

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jTextField22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField22KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22KeyPressed

    private void jTextField23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField23MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23MouseExited

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField24MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24MouseExited

    private void jTextField24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField24ActionPerformed

    private void jTextField25MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField25MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25MouseExited

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void jTextField25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField25KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25KeyPressed

    private void jTextField26MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField26MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26MouseExited

    private void jTextField26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26ActionPerformed

    private void jTextField27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField27MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27MouseExited

    private void jTextField27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField27ActionPerformed

    private void jTextField28MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField28MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28MouseExited

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jTextField28KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField28KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28KeyPressed

    private void jTextField29MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField29MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29MouseExited

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jTextField30MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField30MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30MouseExited

    private void jTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30ActionPerformed

    private void jTextField31MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField31MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31MouseExited

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField31KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField31KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31KeyPressed

    private void jTextField32MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField32MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32MouseExited

    private void jTextField32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField32ActionPerformed

    private void jTextField33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField33MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33MouseExited

    private void jTextField33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField33ActionPerformed

    private void jTextField34MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField34MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34MouseExited

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jTextField34KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField34KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34KeyPressed

    private void jTextField35MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField35MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField35MouseExited

    private void jTextField35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField35ActionPerformed

    private void jTextField36MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField36MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36MouseExited

    private void jTextField36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField36ActionPerformed

    private void jTextField37MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField37MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37MouseExited

    private void jTextField37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37ActionPerformed

    private void jTextField37KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField37KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37KeyPressed

    private void jTextField38MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField38MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38MouseExited

    private void jTextField38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField38ActionPerformed

    private void jTextField39MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField39MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39MouseExited

    private void jTextField39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField39ActionPerformed

    private void jTextField40MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField40MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40MouseExited

    private void jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40ActionPerformed

    private void jTextField40KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField40KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40KeyPressed

    private void jTextField41MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField41MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41MouseExited

    private void jTextField41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField41ActionPerformed

    private void jTextField42MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField42MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42MouseExited

    private void jTextField42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField42ActionPerformed

    private void jTextField43MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField43MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43MouseExited

    private void jTextField43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43ActionPerformed

    private void jTextField43KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField43KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43KeyPressed

    private void jTextField44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField44MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField44MouseExited

    private void jTextField44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField44ActionPerformed

    private void jTextField45MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField45MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField45MouseExited

    private void jTextField45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField45ActionPerformed

    private void jTextField46MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField46MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46MouseExited

    private void jTextField46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46ActionPerformed

    private void jTextField46KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField46KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46KeyPressed

    private void jTextField47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField47MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47MouseExited

    private void jTextField47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField47ActionPerformed

    private void jTextField48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField48MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48MouseExited

    private void jTextField48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField48ActionPerformed

    private void jTextField49MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField49MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49MouseExited

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49ActionPerformed

    private void jTextField49KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField49KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49KeyPressed

    private void jTextField50MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField50MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50MouseExited

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField50ActionPerformed

    private void jTextField51MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField51MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField51MouseExited

    private void jTextField51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField51ActionPerformed

    private void jTextField52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField52MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52MouseExited

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jTextField52KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField52KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52KeyPressed

    private void jTextField53MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField53MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53MouseExited

    private void jTextField53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField53ActionPerformed

    private void jTextField54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField54MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54MouseExited

    private void jTextField54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField54ActionPerformed

    private void jTextField55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField55MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55MouseExited

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField55KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField55KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55KeyPressed

    private void jTextField56MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField56MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56MouseExited

    private void jTextField56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField56ActionPerformed

    private void jTextField57MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField57MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField57MouseExited

    private void jTextField57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField57ActionPerformed

    private void jTextField58MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField58MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58MouseExited

    private void jTextField58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58ActionPerformed

    private void jTextField58KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField58KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58KeyPressed

    private void jTextField59MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField59MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField59MouseExited

    private void jTextField59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField59ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField59ActionPerformed

    private void jTextField60MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField60MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField60MouseExited

    private void jTextField60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField60ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField60ActionPerformed

    private void jTextField61MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField61MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61MouseExited

    private void jTextField61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61ActionPerformed

    private void jTextField61KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField61KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61KeyPressed

    private void jTextField62MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField62MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField62MouseExited

    private void jTextField62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField62ActionPerformed

    private void jTextField63MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField63MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField63MouseExited

    private void jTextField63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField63ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField63ActionPerformed

    private void jTextField64MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField64MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField64MouseExited

    private void jTextField64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField64ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField64ActionPerformed

    private void jTextField64KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField64KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField64KeyPressed

    private void jTextField65MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField65MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField65MouseExited

    private void jTextField65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField65ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField65ActionPerformed

    private void jTextField66MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField66MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField66MouseExited

    private void jTextField66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField66ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField66ActionPerformed

    private void jTextField67MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField67MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField67MouseExited

    private void jTextField67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField67ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField67ActionPerformed

    private void jTextField67KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField67KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField67KeyPressed

    private void jTextField68MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField68MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField68MouseExited

    private void jTextField68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField68ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField68ActionPerformed

    private void jTextField69MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField69MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField69MouseExited

    private void jTextField69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField69ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField69ActionPerformed

    private void jTextField70MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField70MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField70MouseExited

    private void jTextField70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField70ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField70ActionPerformed

    private void jTextField70KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField70KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField70KeyPressed

    private void jTextField71MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField71MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField71MouseExited

    private void jTextField71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField71ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField71ActionPerformed

    private void jTextField72MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField72MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField72MouseExited

    private void jTextField72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField72ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField72ActionPerformed

    private void jTextField73MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField73MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField73MouseExited

    private void jTextField73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField73ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField73ActionPerformed

    private void jTextField73KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField73KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField73KeyPressed

    private void jTextField74MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField74MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField74MouseExited

    private void jTextField74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField74ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField74ActionPerformed

    private void jTextField75MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField75MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField75MouseExited

    private void jTextField75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField75ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField75ActionPerformed

    private void jTextField76MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField76MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField76MouseExited

    private void jTextField76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField76ActionPerformed

    private void jTextField76KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField76KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField76KeyPressed

    private void jTextField77MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField77MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField77MouseExited

    private void jTextField77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField77ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField77ActionPerformed

    private void jTextField78MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField78MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField78MouseExited

    private void jTextField78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField78ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField78ActionPerformed

    private void jTextField79MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField79MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField79MouseExited

    private void jTextField79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField79ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField79ActionPerformed

    private void jTextField79KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField79KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField79KeyPressed

    private void jTextField80MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField80MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField80MouseExited

    private void jTextField80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField80ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField80ActionPerformed

    private void jTextField81MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField81MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField81MouseExited

    private void jTextField81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField81ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField81ActionPerformed

    private void jTextField82MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField82MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField82MouseExited

    private void jTextField82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField82ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField82ActionPerformed

    private void jTextField82KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField82KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField82KeyPressed

    private void jTextField83MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField83MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField83MouseExited

    private void jTextField83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField83ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField83ActionPerformed

    private void jTextField84MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField84MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField84MouseExited

    private void jTextField84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField84ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField84ActionPerformed

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
       CalculateScoreFraction(0,2);
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        CalculateScoreFraction(0,0);
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
       CalculateScoreFraction(0,1);
    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
       CalculateScoreFraction(1,0);
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox17ActionPerformed
       CalculateScoreFraction(1,1);
    }//GEN-LAST:event_jComboBox17ActionPerformed

    private void jComboBox19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox19ActionPerformed
        CalculateScoreFraction(1,2);
    }//GEN-LAST:event_jComboBox19ActionPerformed

    private void jComboBox20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox20ActionPerformed
       CalculateScoreFraction(1,3);
    }//GEN-LAST:event_jComboBox20ActionPerformed

    private void jComboBox24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox24ActionPerformed
        CalculateScoreFraction(1,4);
    }//GEN-LAST:event_jComboBox24ActionPerformed

    private void jComboBox27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox27ActionPerformed
        CalculateScoreFraction(2,0);
    }//GEN-LAST:event_jComboBox27ActionPerformed

    private void jComboBox28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox28ActionPerformed
        CalculateScoreFraction(2,1);
    }//GEN-LAST:event_jComboBox28ActionPerformed

    private void jComboBox32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox32ActionPerformed
        CalculateScoreFraction(2,2);
    }//GEN-LAST:event_jComboBox32ActionPerformed

    private void jComboBox38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox38ActionPerformed
        CalculateScoreFraction(2,3);
    }//GEN-LAST:event_jComboBox38ActionPerformed

    private void jComboBox39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox39ActionPerformed
        CalculateScoreFraction(3,1);
    }//GEN-LAST:event_jComboBox39ActionPerformed

    private void jComboBox40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox40ActionPerformed
        CalculateScoreFraction(3,0);
    }//GEN-LAST:event_jComboBox40ActionPerformed

    private void jComboBox41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox41ActionPerformed
       CalculateScoreFraction(4,4);
    }//GEN-LAST:event_jComboBox41ActionPerformed

    private void jComboBox42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox42ActionPerformed
       CalculateScoreFraction(4,3);
    }//GEN-LAST:event_jComboBox42ActionPerformed

    private void jComboBox43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox43ActionPerformed
       CalculateScoreFraction(4,2);
    }//GEN-LAST:event_jComboBox43ActionPerformed

    private void jComboBox44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox44ActionPerformed
       CalculateScoreFraction(4,1);
    }//GEN-LAST:event_jComboBox44ActionPerformed

    private void jComboBox45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox45ActionPerformed
        CalculateScoreFraction(4,0);
    }//GEN-LAST:event_jComboBox45ActionPerformed

    private void jComboBox46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox46ActionPerformed
       CalculateScoreFraction(5,2);
    }//GEN-LAST:event_jComboBox46ActionPerformed

    private void jComboBox47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox47ActionPerformed
        CalculateScoreFraction(5,1);
    }//GEN-LAST:event_jComboBox47ActionPerformed

    private void jComboBox48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox48ActionPerformed
       CalculateScoreFraction(5,0);
    }//GEN-LAST:event_jComboBox48ActionPerformed

    private void jComboBox49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox49ActionPerformed
        CalculateScoreFraction(6,1);
    }//GEN-LAST:event_jComboBox49ActionPerformed

    private void jComboBox50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox50ActionPerformed
        CalculateScoreFraction(6,0);
    }//GEN-LAST:event_jComboBox50ActionPerformed

    private void jComboBox51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox51ActionPerformed
        CalculateScoreFraction(7,0);
    }//GEN-LAST:event_jComboBox51ActionPerformed

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField12MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField12MouseClicked

    private void jTextField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField15MouseClicked

    private void jTextField18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField18MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField18MouseClicked

    private void jTextField24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField24MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField24MouseClicked

    private void jTextField27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField27MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField27MouseClicked

    private void jTextField30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField30MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField30MouseClicked

    private void jTextField33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField33MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField33MouseClicked

    private void jTextField36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField36MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField36MouseClicked

    private void jTextField54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField54MouseClicked
       setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField54MouseClicked

    private void jTextField57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField57MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField57MouseClicked

    private void jTextField60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField60MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField60MouseClicked

    private void jTextField39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField39MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField39MouseClicked

    private void jTextField63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField63MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField63MouseClicked

    private void jTextField42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField42MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField42MouseClicked

    private void jTextField66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField66MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField66MouseClicked

    private void jTextField69MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField69MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField69MouseClicked

    private void jTextField72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField72MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField72MouseClicked

    private void jTextField75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField75MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField75MouseClicked

    private void jTextField45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField45MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField45MouseClicked

    private void jTextField78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField78MouseClicked
       setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField78MouseClicked

    private void jTextField81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField81MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField81MouseClicked

    private void jTextField48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField48MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField48MouseClicked

    private void jTextField84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField84MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField84MouseClicked

    private void jTextField51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField51MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField51MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        SensorsAndActuators senAct = new SensorsAndActuators();
        int res =JOptionPane.showConfirmDialog(null, new JScrollPane( senAct), "Select the Diagnostic Coverage",JOptionPane.OK_CANCEL_OPTION);
        jButton1.setText(senAct.getDiagnosticCoverage());
        System.out.println("888888888888888888"+res);
        if(res ==0){
            CalculateFactors(Integer.parseInt(jLabel132.getText()),Integer.parseInt(jLabel83.getText()),Integer.parseInt(senAct.getDiagnosticCoverage()));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        JSONObject result= new JSONObject(ccfCalcHdr.getProsettings().toString());
        
        try {
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
        if(dataSingleCom.size()==0){ 
         System.out.println("dataSingleCom.size() "+dataSingleCom.size());
         dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
         //dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() , "Non programmable",spgId);
         setCCFDataForComp(dataSingleCom);    
        }
            
            for (int i = 0; i < indiviScore.length; i++) {

                for (int j = 0; j < indiviScore[i].length; j++) {

                           //ccfDao.Save(getCCFLine(i,j));
                           saveCCFCom(getCCFLine(i,j));
                }

            }

                ccfCurd.setAscore(result, NumberConversion.NVLDouble(jLabel132.getText(), 0)+"");
                ccfCurd.setBscore(result,NumberConversion.NVLDouble(jLabel83.getText(), 0)+"");
                ccfCurd.setRowscore(result,NumberConversion.NVLDouble(jLabel108.getText(), 0)+"");
                ccfCurd.setBetaFactor(result,NumberConversion.NVLDouble(jLabel81.getText(), 0)+"");
                ccfCurd.setDiagnosticCoverage(result,NumberConversion.NVLDouble(jButton1.getText(), 0)+"");
                ccfCalcHdr.setProsettings(result);
                ccfCalcHdr.setComments(jTextArea1.getText());
       
                spfhdrDao.update(ccfCalcHdr);
                
        }catch(Exception e){
        
            JOptionPane.showMessageDialog(null,"Error - "+e.getMessage(), "Information",JOptionPane.ERROR_MESSAGE);     
        
        } finally{
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        
        
        
        JOptionPane.showMessageDialog(null,"Save Successfully", "Information",JOptionPane.QUESTION_MESSAGE);     
       
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        orOperator(new JComboBox[]{jComboBox4,jComboBox5}, evt);
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        orOperator(new JComboBox[]{jComboBox4,jComboBox5}, evt);
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        orOperator(new JComboBox[]{jComboBox3,jComboBox6,jComboBox8,jComboBox9}, evt);
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        orOperator(new JComboBox[]{jComboBox3,jComboBox6,jComboBox8,jComboBox9}, evt);
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
       orOperator(new JComboBox[]{jComboBox3,jComboBox6,jComboBox8,jComboBox9}, evt);
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jComboBox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox9ItemStateChanged
        orOperator(new JComboBox[]{jComboBox3,jComboBox6,jComboBox8,jComboBox9}, evt);
    }//GEN-LAST:event_jComboBox9ItemStateChanged

    public void saveCCFCom(Ccf ccf){
    


      Ccf ref =findCCFByDescription(ccf.getDescription());
      Ccf upCcf =ccf;
    
        if(ref== null){
            System.out.println(" ***********Save*************** ");
             ccfDao.Save(ccf);
        }else{
            upCcf.setCcfId(ref.getCcfId());
            ccfDao.Update(upCcf);
             System.out.println(" ***********Update*************** "+upCcf.getCcfId());
        }
    
    }
    
    public Ccf findCCFByDescription(String description){
    Ccf ref = null; 
    

        for (Ccf ccf : dataSingleCom) {
            
            System.out.println("************* description "+description+" "+ccf.getDescription());
            
            if(ccf.getDescription().equals(description)){
            
                ref =ccf;
                
                // System.out.println("************* description "+description+" "+ccf.getDescription());
            } 
            
        }
        
      return ref;
    }
    
    public int getSeparationTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel3.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getSeparationTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel2.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
   public int getDiversityTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel112.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getDiversityTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel113.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getComplexityTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel115.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getComplexityTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel116.getText());
        } catch (Exception e) {
        }  
    return res;
    }
     public int getAssessmentTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel118.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getAssessmentTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel119.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
     public int getProceduresTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel121.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getProceduresTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel122.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getCompetenceTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel124.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getCompetenceTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel125.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getEnvironmentalTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel127.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getEnvironmentalTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel128.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
     public int getEnvironmentalTestingTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel130.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getEnvironmentalTestingTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel131.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    
    public int getFinalTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel132.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getFinalTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel83.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public String getFinalComments(){
     return jTextArea1.getText();
    }
    
    public void setCommentsPopup(MouseEvent evt){
        
        JTextField jTextField = (JTextField) evt.getSource();
        
        JTextArea textArea = new JTextArea();
        textArea.setRows(7);
        textArea.setColumns(40);
        textArea.setLineWrap(true);
        textArea.setText(jTextField.getText());
        JOptionPane.showMessageDialog(null, new JScrollPane( textArea), "Please enter your comments",JOptionPane.QUESTION_MESSAGE);
        jTextField.setText(textArea.getText());
    
    }
    
    public Ccf getCCFLine(int i, int j){
     
        String subData[][][] = getAllSubData();
        
       // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        
        Ccf ccf = new Ccf();
              // ccf.setMainCategory("Non programmable");
               ccf.setSubCategory(mainHeading[i]);
               ccf.setDescription(i+"."+j);
               ccf.setAchievement(subData[i][j][2]);
               ccf.setAchievementPercentage(indiviCombo[i][j][1].getSelectedItem()+"");
               ccf.setComments(subData[i][j][3]);
               ccf.setCcfHdr(ccfCalcHdr);
               /*ccf.setCcfSubComponent(subComDao.getSPGById(spgId));
               ccf.setUser(UserInfo.getInstance().getuser());
               ccf.setCreateddate(UserInfo.getInstance().getDate());
               ccf.setProjectId(UserInfo.getInstance().getTclProject());*/
    
    return ccf;
    }
    
     
    /*public Ccf getCCFFinal(){
     
        String subData[][][] = getAllSubData();
        
       // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        
        Ccf ccf = new Ccf();
               ccf.setMainCategory("Non programmable");
               ccf.setSubCategory("");
               ccf.setDescription("Final");
               ccf.setAchievement(jLabel132.getText()+"#"+jLabel83.getText()+"#"+jLabel108.getText()+"#"+jButton1.getText());
               ccf.setAchievementPercentage(jLabel81.getText());
               ccf.setComments(jTextArea1.getText());
               ccf.setCcfSubComponent(subComDao.getSPGById(spgId));
               ccf.setUser(UserInfo.getInstance().getuser());
               ccf.setCreateddate(UserInfo.getInstance().getDate());
               ccf.setProjectId(UserInfo.getInstance().getTclProject());
    
    return ccf;
    }   */
    
    public void setCCFDataForComp(List<Ccf> data){
    
        Ccf ccf;
        int count=0;
        
        for (int i = 0; i < indiviScore.length; i++) {
            
            for (int j = 0; j < indiviScore[i].length; j++) {
                       
               
                
                    try {
                       // ccf = data.get(count); 
                       ccf =getByDescription(data, i+"."+j);
                       indiviCombo[i][j][0].setSelectedItem(ccf.getAchievement());
                       indiviCombo[i][j][1].setSelectedItem(ccf.getAchievementPercentage());
                       indiviNote[i][j].setText(ccf.getComments());
                      // System.out.println(i+"."+j);
                       count++;

                    } catch (Exception e) {
                    }

            }
            
        }
    
    }
    
    public Ccf getByDescription(List<Ccf> data,String desc){
    Ccf res = null;
            
        for (Ccf ccf : data) {
            if (ccf.getDescription().equals(desc)) {
                res = ccf;
            }
            
        }
        
      return res;
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox22;
    private javax.swing.JComboBox<String> jComboBox23;
    private javax.swing.JComboBox<String> jComboBox24;
    private javax.swing.JComboBox<String> jComboBox25;
    private javax.swing.JComboBox<String> jComboBox26;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox28;
    private javax.swing.JComboBox<String> jComboBox29;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox30;
    private javax.swing.JComboBox<String> jComboBox31;
    private javax.swing.JComboBox<String> jComboBox32;
    private javax.swing.JComboBox<String> jComboBox33;
    private javax.swing.JComboBox<String> jComboBox34;
    private javax.swing.JComboBox<String> jComboBox35;
    private javax.swing.JComboBox<String> jComboBox36;
    private javax.swing.JComboBox<String> jComboBox37;
    private javax.swing.JComboBox<String> jComboBox38;
    private javax.swing.JComboBox<String> jComboBox39;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox40;
    private javax.swing.JComboBox<String> jComboBox41;
    private javax.swing.JComboBox<String> jComboBox42;
    private javax.swing.JComboBox<String> jComboBox43;
    private javax.swing.JComboBox<String> jComboBox44;
    private javax.swing.JComboBox<String> jComboBox45;
    private javax.swing.JComboBox<String> jComboBox46;
    private javax.swing.JComboBox<String> jComboBox47;
    private javax.swing.JComboBox<String> jComboBox48;
    private javax.swing.JComboBox<String> jComboBox49;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox50;
    private javax.swing.JComboBox<String> jComboBox51;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    private javax.swing.JTextField jTextField61;
    private javax.swing.JTextField jTextField62;
    private javax.swing.JTextField jTextField63;
    private javax.swing.JTextField jTextField64;
    private javax.swing.JTextField jTextField65;
    private javax.swing.JTextField jTextField66;
    private javax.swing.JTextField jTextField67;
    private javax.swing.JTextField jTextField68;
    private javax.swing.JTextField jTextField69;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField73;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    // End of variables declaration//GEN-END:variables
}
