/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;

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
import com.topfield.view.calculators.ProgramableElectronics;
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
public class CCFMachineryStandard extends javax.swing.JPanel {
    
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    private CCFDao ccfDao = new CCFDaoImpl();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private CCFCurd ccfCurd= new CCFCurd();
    private List<Ccf> dataSingleCom ;
    
    
    private final JTextField [][][] indiviScore;
    private final JTextField [][] indiviNote;
    private final JComboBox [][][] indiviCombo;
    private final JLabel [][] totalScore;
    private int[][][] scoresAandB;
    //private Ccfhdr ccfHdr;
    private Spfhdr ccfCalcHdr;
    private final String[] mainHeading = {"1.Separation/Isolation","2.Diversity/Redundancy","3.Complexity/ Design / Application",
					  "4. Evaluation/Analysis","5.Competence / Training","6.Monitoring or Recalculated Conditions"};

    private final String[][] questions = {{"Are SREC signal cable for the individual channels in all places separated from other channels or sufficiently protected",
                                        "Is the detection of signal transmission errors sufficient when using information coding / decoding",                                                                                                                                  
                                        "Are SRECS signal cables and electrical power supply cables separated or sufficiently protected at all locations?",
                                        "Subsystem elements are considered to be physically separate entities in their own local enclosures if they can contribute to a CCF."},


                                        {"Are elements used in the subsystem various electronic technologies, for example,one electronic and on the other hand, an electromechanical relay",
                                        "Are elements used in the subsystem that use different physical principles (e.g.,sensing elements on a guard using mechanical and magnetic detection techniques)?",
                                        "Are elements with different time behavior in terms of functional operation and / or failure modes used in the subsystem?",
                                        "Do the subsystem elements have a diagnostic test interval of <1 min?"},

                                        {"Is the cross-connection between channels of the subsystem prevented with the exception of cross-connections, which are used for diagnostic purposes?"},

                                        {"Have the results of the failure mode and impact analysis been evaluated to identify sources of failures due to common cause, and have certain such sources previously been eliminated by the design?",
                                        "Are field failures analyzed and reported back to the design process?"},

                                        {"Do the developers of the subsystems understand the causes and effects of failures due to a common cause?"},

                                        {"Are the subsystems likely to have been tested in elements without external monitoring of environmental conditions within the temperature, humidity,corrosion, dust and vibration ranges etc.?",
                                        "Are the subsystems immune to the adverse effects of electromagnetic interference up to the limits of IEC 612326-3-1?"}};
    /**
     * Creates new form CCFMachineryStandard
     */
    public CCFMachineryStandard(Spfhdr ccfCalcHdr) {
        initComponents();
        this.ccfCalcHdr = ccfCalcHdr;
       dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
       jLabel50.setText("Machinery Standard - "+ccfCalcHdr.getSpfid().getMpgheader().getFmeaComponent().getMpgDescription());
        
          scoresAandB =new int[][][]{ {{1,5},{1,10},{2,5},{3,5}},
                                       {{4,8},{5,10},{6,10},{6,10},{7,10}},
                                       {{8,2}},
                                       {{9,9},{10,9}},
                                       {{11,4}},
                                       {{12,9},{13,9}}};
        
        indiviScore = new JTextField[][][]{{{jTextField2,jTextField1},{jTextField4,jTextField5},{jTextField7,jTextField8},{jTextField10,jTextField11}},
	                                 {{jTextField28,jTextField29},{jTextField16,jTextField17},{jTextField88,jTextField89},{jTextField19,jTextField20}},
					 {{jTextField31,jTextField32}},
                                         {{jTextField22,jTextField23},{jTextField52,jTextField53}},
					 {{jTextField55,jTextField56}},
					 {{jTextField70,jTextField71},{jTextField76,jTextField77}}};
               
        indiviNote = new JTextField[][]{{jTextField3,jTextField6,jTextField9,jTextField12},{jTextField30,jTextField18,jTextField90,jTextField21},
                                       {jTextField33},{jTextField24,jTextField54},
                                       {jTextField57},
                                       {jTextField72,jTextField78}}; 
               
        indiviCombo = new JComboBox[][][]{{{jComboBox2,jComboBox24},{jComboBox4,jComboBox32},{jComboBox5,jComboBox27},{jComboBox1,jComboBox28}},
                                          {{jComboBox15,jComboBox38},{jComboBox14,jComboBox39},{jComboBox63,jComboBox64},{jComboBox13,jComboBox40}},
                                          {{jComboBox3,jComboBox47}},
                                          {{jComboBox29,jComboBox48},{jComboBox30,jComboBox51}},
                                          {{jComboBox33,jComboBox52}},
                                          {{jComboBox21,jComboBox57},{jComboBox23,jComboBox59}}}; 
         
        totalScore = new JLabel[][]{{jLabel109,jLabel110},{jLabel112,jLabel113},{jLabel115,jLabel116},{jLabel118,jLabel119},
                                    {jLabel121,jLabel122},{jLabel124,jLabel125}};
        
        // dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() ,"Machinery Standard",spgId);
        setCCFDataForComp(dataSingleCom);
        
        SetDiagnosticCoverage(ccfCurd.getJSONProperty(ccfCalcHdr.getProsettings(),"results","diagnostic"),ccfCalcHdr.getComments()); //diagnosticCoverage
    }
    
    public void SetDiagnosticCoverage(String data,String comments){
        if (data.equals("") ||data.equals("null") || data == null  ) {
            
        }else{
            jButton1.setText(data);
        }    
        
        jTextArea1.setText(comments);
    }
    
    public String getRawScore(){
    
    return jLabel135.getText();
    }
    
    public String getBetaFactor(){
    
    return jLabel83.getText();
    }
    
    public String getFinalComments(){
     return jTextArea1.getText();
    }
    
    public String[] getHeadings(){
     return mainHeading;
    }
    
    public String[][] getQuestions(){
     return questions;
    }
    
    public String [][][] getAllSubData(){
    
      String [][][] data = new String[indiviNote.length][][];
      String [][] rowData = new String[1][4];
      
        for (int i = 0; i < indiviNote.length; i++) {
            
            rowData = new String[indiviNote[i].length][4];
            
            for (int j = 0; j < indiviNote[i].length; j++) {
              
                try {
                    
                    
                    
                    //System.out.println(" indiviScore[i][j][0].getText() "+indiviScore[i][j][0].getText());
                    //System.out.println(" indiviScore[i][j][1].getText() "+indiviScore[i][j][1].getText());
                    //System.out.println(" indiviCombo[i][j][1].getSelectedItem().toString() "+indiviCombo[i][j][0].getSelectedItem().toString());
                    //System.out.println(" indiviNote[i][j].getText() "+indiviNote[i][j].getText());
                    
                    
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
               
              // System.out.println("Hi "+i+")"+rowData[0][0]);
        }
      
    
     return data; 
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
      
        JLabel [] precentagelables = {jLabel17,jLabel18,jLabel19,jLabel20,jLabel21,jLabel22};
        
       for (JLabel precentagelable : precentagelables) {
          totalPercentages = totalPercentages + Integer.parseInt(precentagelable.getText().replace("%", ""));  
        }
 
        
        jLabel10.setText((totalPercentages/8)+"%");
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
      jLabel81.setText(totBScore+"");
    
      CalculateFactors(totAScore,totBScore,(int)NumberConversion.NVLDouble(jButton1.getText(),0));
    }
    
    public void CalculateFactors(int aScore,int bScore,int c){
        int rawScore=0;
        double betaFactor=0;
        double temp;
        
        try {
            rawScore = aScore*c+bScore;
            temp =  (rawScore * (3.4)  )/ 2624;
            //betaFactor = 0.3*Math.exp(temp*-1);
            betaFactor = caculateBataFactor( bScore);
        } catch (NumberFormatException e) {
        }

    
    jLabel135.setText(rawScore+"");
    jLabel83.setText(betaFactor+"");
    }
    
    public double caculateBataFactor(int score){
     double res=0.0;
    
       if(score <=35){
            res=0.1;
       }else if( (score >35) && (score <=65)){
            res=0.05;
       }else if( (score >65) && (score <=85)){
            res=0.02;
       }else if( (score >85) && (score <=100)){
            res=0.01;
       }

     return res;
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
    
    public int getSeparationTotalA(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel109.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    public int getSeparationTotalB(){
     int res=0;
        
        try {
         res  = Integer.parseInt(jLabel110.getText());
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
         res  = Integer.parseInt(jLabel81.getText());
        } catch (Exception e) {
        }  
    return res;
    }
    
    
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jComboBox15 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextField29 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jTextField30 = new javax.swing.JTextField();
        jComboBox38 = new javax.swing.JComboBox<>();
        jComboBox39 = new javax.swing.JComboBox<>();
        jComboBox40 = new javax.swing.JComboBox<>();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jComboBox63 = new javax.swing.JComboBox<>();
        jComboBox64 = new javax.swing.JComboBox<>();
        jTextField88 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jComboBox47 = new javax.swing.JComboBox<>();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jComboBox29 = new javax.swing.JComboBox<>();
        jComboBox30 = new javax.swing.JComboBox<>();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jTextField53 = new javax.swing.JTextField();
        jTextField54 = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jComboBox48 = new javax.swing.JComboBox<>();
        jComboBox51 = new javax.swing.JComboBox<>();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jComboBox33 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jComboBox52 = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jComboBox57 = new javax.swing.JComboBox<>();
        jComboBox59 = new javax.swing.JComboBox<>();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jComboBox24 = new javax.swing.JComboBox<>();
        jComboBox27 = new javax.swing.JComboBox<>();
        jComboBox28 = new javax.swing.JComboBox<>();
        jComboBox32 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox15.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox15.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox15.setToolTipText("");
        jComboBox15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox15ItemStateChanged(evt);
            }
        });
        jComboBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox15ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

        jComboBox14.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox14.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox14.setToolTipText("");
        jComboBox14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox14ItemStateChanged(evt);
            }
        });
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 130, 20));

        jComboBox13.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox13.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox13.setToolTipText("");
        jComboBox13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox13ItemStateChanged(evt);
            }
        });
        jComboBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox13ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 130, 20));

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("<html> II.Are elements used in the subsystem that use different physical principles (e.g., sensing elements on a guard using mechanical and magnetic detection techniques)?</html>");
        jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, -1));

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("<html>I.  Are elements used in the subsystem various electronic technologies, for example, one electronic and on the other hand, an electromechanical relay? </html>");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 410, -1));

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("<html>IV. Do the subsystem elements have a diagnostic test interval of <1 min?</html>");
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 380, -1));

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("2.Diversity/Redundancy");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 51));
        jLabel18.setText("0%");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 220, 60, 30));

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(204, 204, 204));
        jTextField16.setForeground(new java.awt.Color(0, 0, 153));
        jTextField16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 60, -1));

        jTextField17.setEditable(false);
        jTextField17.setBackground(new java.awt.Color(204, 204, 204));
        jTextField17.setForeground(new java.awt.Color(102, 0, 102));
        jTextField17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 60, -1));

        jTextField18.setBackground(new java.awt.Color(255, 255, 255));
        jTextField18.setForeground(new java.awt.Color(0, 0, 0));
        jTextField18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField18MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 310, 20));

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(204, 204, 204));
        jTextField19.setForeground(new java.awt.Color(0, 0, 153));
        jTextField19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 60, -1));

        jTextField20.setEditable(false);
        jTextField20.setBackground(new java.awt.Color(204, 204, 204));
        jTextField20.setForeground(new java.awt.Color(102, 0, 102));
        jTextField20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 60, -1));

        jTextField21.setBackground(new java.awt.Color(255, 255, 255));
        jTextField21.setForeground(new java.awt.Color(0, 0, 0));
        jTextField21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField21MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 180, 310, -1));

        jLabel61.setBackground(new java.awt.Color(0, 51, 51));
        jLabel61.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 102, 51));
        jLabel61.setText("Completed");
        jPanel5.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 220, 110, 30));

        jLabel69.setBackground(new java.awt.Color(0, 0, 0));
        jLabel69.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel69.setText("A Score ");
        jPanel5.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 30, -1, -1));

        jTextField28.setEditable(false);
        jTextField28.setBackground(new java.awt.Color(204, 204, 204));
        jTextField28.setForeground(new java.awt.Color(0, 0, 153));
        jTextField28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField28, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jLabel74.setBackground(new java.awt.Color(0, 0, 0));
        jLabel74.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel74.setText("B Score ");
        jPanel5.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 30, -1, -1));

        jTextField29.setEditable(false);
        jTextField29.setBackground(new java.awt.Color(204, 204, 204));
        jTextField29.setForeground(new java.awt.Color(102, 0, 102));
        jTextField29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField29, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jLabel84.setBackground(new java.awt.Color(0, 0, 0));
        jLabel84.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel84.setText("Comments ");
        jPanel5.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        jTextField30.setBackground(new java.awt.Color(255, 255, 255));
        jTextField30.setForeground(new java.awt.Color(0, 0, 0));
        jTextField30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField30MouseClicked(evt);
            }
        });
        jTextField30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField30ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField30, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jComboBox38.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox38.setEditable(true);
        jComboBox38.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox38.setEnabled(false);
        jComboBox38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox38ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox38, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jComboBox39.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox39.setEditable(true);
        jComboBox39.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox39.setEnabled(false);
        jComboBox39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox39ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox39, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 50, 20));

        jComboBox40.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox40.setEditable(true);
        jComboBox40.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox40.setEnabled(false);
        jComboBox40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox40ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox40, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 50, 20));

        jLabel111.setBackground(new java.awt.Color(0, 153, 204));
        jLabel111.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(102, 0, 0));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel111.setText("Score");
        jPanel5.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 40, -1));

        jLabel112.setBackground(new java.awt.Color(0, 153, 204));
        jLabel112.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(102, 0, 0));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel112.setText("--------------");
        jPanel5.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 60, -1));

        jLabel113.setBackground(new java.awt.Color(0, 153, 204));
        jLabel113.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(102, 0, 0));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel113.setText("--------------");
        jPanel5.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 60, -1));

        jComboBox63.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox63.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox63.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox63.setToolTipText("");
        jComboBox63.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox63ItemStateChanged(evt);
            }
        });
        jComboBox63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox63ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox63, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 130, 20));

        jComboBox64.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox64.setEditable(true);
        jComboBox64.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox64.setEnabled(false);
        jComboBox64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox64ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox64, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 50, 20));

        jTextField88.setEditable(false);
        jTextField88.setBackground(new java.awt.Color(204, 204, 204));
        jTextField88.setForeground(new java.awt.Color(0, 0, 153));
        jTextField88.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField88ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField88, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 60, -1));

        jTextField89.setEditable(false);
        jTextField89.setBackground(new java.awt.Color(204, 204, 204));
        jTextField89.setForeground(new java.awt.Color(102, 0, 102));
        jTextField89.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField89, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 60, -1));

        jTextField90.setBackground(new java.awt.Color(255, 255, 255));
        jTextField90.setForeground(new java.awt.Color(0, 0, 0));
        jTextField90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField90MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField90, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 310, -1));

        jLabel51.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("<html>III. Are elements with different time behavior in terms of functional operation and / or failure modes used in the subsystem?</html>");
        jPanel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 380, -1));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox3.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox3.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox3.setToolTipText("");
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("<html> I. Is the cross-connection between channels of the subsystem prevented with the exception of cross-connections, which are used for diagnostic purposes? </html>");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 390, -1));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 51));
        jLabel19.setText("0%");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, 60, 30));

        jTextField32.setEditable(false);
        jTextField32.setBackground(new java.awt.Color(204, 204, 204));
        jTextField32.setForeground(new java.awt.Color(102, 0, 102));
        jTextField32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jLabel87.setBackground(new java.awt.Color(0, 0, 0));
        jLabel87.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel87.setText("Comments ");
        jPanel2.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jLabel85.setBackground(new java.awt.Color(0, 0, 0));
        jLabel85.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel85.setText("A Score ");
        jPanel2.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 20, -1, -1));

        jLabel86.setBackground(new java.awt.Color(0, 0, 0));
        jLabel86.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel86.setText("B Score ");
        jPanel2.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 20, -1, -1));

        jTextField31.setEditable(false);
        jTextField31.setBackground(new java.awt.Color(204, 204, 204));
        jTextField31.setForeground(new java.awt.Color(0, 0, 153));
        jTextField31.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField31ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jTextField33.setBackground(new java.awt.Color(255, 255, 255));
        jTextField33.setForeground(new java.awt.Color(0, 0, 0));
        jTextField33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField33MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

        jLabel89.setBackground(new java.awt.Color(0, 51, 51));
        jLabel89.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 102, 51));
        jLabel89.setText("Completed");
        jPanel2.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 100, 110, 30));

        jComboBox47.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox47.setEditable(true);
        jComboBox47.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox47.setEnabled(false);
        jComboBox47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox47ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox47, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jLabel114.setBackground(new java.awt.Color(0, 153, 204));
        jLabel114.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(102, 0, 0));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Score");
        jPanel2.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 40, -1));

        jLabel115.setBackground(new java.awt.Color(0, 153, 204));
        jLabel115.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(102, 0, 0));
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel115.setText("--------------");
        jPanel2.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 60, -1));

        jLabel116.setBackground(new java.awt.Color(0, 153, 204));
        jLabel116.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(102, 0, 0));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel116.setText("--------------");
        jPanel2.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, 60, -1));

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("3.Complexity/ Design / Application");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 450, -1));

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
        jPanel7.add(jComboBox29, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 130, 20));

        jComboBox30.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox30.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox30.setToolTipText("");
        jComboBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox30ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox30, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 130, 20));

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("<html> I.Have the results of the failure mode and impact analysis been evaluated to identify sources of failures due to common cause, and have certain such sources previously been eliminated by the design? </html>");
        jPanel7.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 400, -1));

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("<html>II.Are field failures analyzed and reported back to the design process?</html>");
        jPanel7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 30));

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("4. Evaluation/Analysis");
        jPanel7.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, -1));

        jTextField22.setEditable(false);
        jTextField22.setBackground(new java.awt.Color(204, 204, 204));
        jTextField22.setForeground(new java.awt.Color(0, 0, 153));
        jTextField22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 60, -1));

        jTextField23.setEditable(false);
        jTextField23.setBackground(new java.awt.Color(204, 204, 204));
        jTextField23.setForeground(new java.awt.Color(102, 0, 102));
        jTextField23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 60, -1));

        jTextField24.setBackground(new java.awt.Color(255, 255, 255));
        jTextField24.setForeground(new java.awt.Color(0, 0, 0));
        jTextField24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField24MouseClicked(evt);
            }
        });
        jPanel7.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 310, -1));

        jLabel60.setBackground(new java.awt.Color(0, 0, 0));
        jLabel60.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel60.setText("Comments ");
        jPanel7.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        jLabel57.setBackground(new java.awt.Color(0, 0, 0));
        jLabel57.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel57.setText("B Score ");
        jPanel7.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 30, -1, -1));

        jLabel56.setBackground(new java.awt.Color(0, 0, 0));
        jLabel56.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel56.setText("A Score ");
        jPanel7.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 30, -1, -1));

        jTextField52.setEditable(false);
        jTextField52.setBackground(new java.awt.Color(204, 204, 204));
        jTextField52.setForeground(new java.awt.Color(0, 0, 153));
        jTextField52.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 60, -1));

        jTextField53.setEditable(false);
        jTextField53.setBackground(new java.awt.Color(204, 204, 204));
        jTextField53.setForeground(new java.awt.Color(102, 0, 102));
        jTextField53.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.add(jTextField53, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 60, -1));

        jTextField54.setBackground(new java.awt.Color(255, 255, 255));
        jTextField54.setForeground(new java.awt.Color(0, 0, 0));
        jTextField54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField54MouseClicked(evt);
            }
        });
        jPanel7.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 310, -1));

        jLabel88.setBackground(new java.awt.Color(0, 51, 51));
        jLabel88.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 102, 51));
        jLabel88.setText("Completed");
        jPanel7.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 250, 110, 30));

        jLabel39.setBackground(new java.awt.Color(0, 51, 51));
        jLabel39.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 102, 51));
        jLabel39.setText("Completed");
        jPanel7.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 140, 110, 30));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 51));
        jLabel20.setText("0%");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 140, 60, 30));

        jComboBox48.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox48.setEditable(true);
        jComboBox48.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox48.setEnabled(false);
        jComboBox48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox48ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox48, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 50, 20));

        jComboBox51.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox51.setEditable(true);
        jComboBox51.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox51.setEnabled(false);
        jComboBox51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox51ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox51, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 50, 20));

        jLabel117.setBackground(new java.awt.Color(0, 153, 204));
        jLabel117.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(102, 0, 0));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel117.setText("Score");
        jPanel7.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 40, -1));

        jLabel118.setBackground(new java.awt.Color(0, 153, 204));
        jLabel118.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(102, 0, 0));
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel118.setText("--------------");
        jPanel7.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jLabel119.setBackground(new java.awt.Color(0, 153, 204));
        jLabel119.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(102, 0, 0));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel119.setText("--------------");
        jPanel7.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

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
        jPanel8.add(jComboBox33, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 130, 20));

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("<html>I.Do the developers of the subsystems understand the causes and effects of failures due to a common cause?</html>");
        jPanel8.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 400, 50));

        jLabel55.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("5.Competence / Training");
        jPanel8.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 340, -1));

        jLabel90.setBackground(new java.awt.Color(0, 51, 51));
        jLabel90.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 102, 51));
        jLabel90.setText("Completed");
        jPanel8.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, 110, 30));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 51));
        jLabel21.setText("0%");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 110, 50, 30));

        jLabel94.setBackground(new java.awt.Color(0, 0, 0));
        jLabel94.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel94.setText("A Score ");
        jPanel8.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 20, -1, -1));

        jTextField55.setEditable(false);
        jTextField55.setBackground(new java.awt.Color(204, 204, 204));
        jTextField55.setForeground(new java.awt.Color(0, 0, 153));
        jTextField55.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField55ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 60, -1));

        jLabel95.setBackground(new java.awt.Color(0, 0, 0));
        jLabel95.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel95.setText("B Score ");
        jPanel8.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 20, -1, -1));

        jTextField56.setEditable(false);
        jTextField56.setBackground(new java.awt.Color(204, 204, 204));
        jTextField56.setForeground(new java.awt.Color(102, 0, 102));
        jTextField56.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 60, -1));

        jLabel96.setBackground(new java.awt.Color(0, 0, 0));
        jLabel96.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel96.setText("Comments ");
        jPanel8.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jTextField57.setBackground(new java.awt.Color(255, 255, 255));
        jTextField57.setForeground(new java.awt.Color(0, 0, 0));
        jTextField57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField57MouseClicked(evt);
            }
        });
        jPanel8.add(jTextField57, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 310, -1));

        jComboBox52.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox52.setEditable(true);
        jComboBox52.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox52.setEnabled(false);
        jComboBox52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox52ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox52, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 50, 20));

        jLabel120.setBackground(new java.awt.Color(0, 153, 204));
        jLabel120.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(102, 0, 0));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel120.setText("Score");
        jPanel8.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 120, 40, -1));

        jLabel121.setBackground(new java.awt.Color(0, 153, 204));
        jLabel121.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(102, 0, 0));
        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel121.setText("--------------");
        jPanel8.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, 60, -1));

        jLabel122.setBackground(new java.awt.Color(0, 153, 204));
        jLabel122.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(102, 0, 0));
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel122.setText("--------------");
        jPanel8.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, 60, -1));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox21.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox21.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox21.setToolTipText("");
        jComboBox21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox21ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 130, 20));

        jComboBox23.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox23.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox23.setToolTipText("");
        jComboBox23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox23ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox23, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 130, 20));

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("<html> I.Are the subsystems likely to have been tested in elements without external monitoring of environmental conditions within the temperature, humidity, corrosion, dust and vibration ranges etc.? </html>");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 420, 50));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("<html> II. Are the subsystems immune to the adverse effects of electromagnetic interference up to the limits of IEC 612326-3-1? </html>");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 370, -1));

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("6.Monitoring or Recalculated Conditions");
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 430, -1));

        jLabel91.setBackground(new java.awt.Color(0, 51, 51));
        jLabel91.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 102, 51));
        jLabel91.setText("Completed");
        jPanel3.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 100, 110, 30));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 51));
        jLabel22.setText("0%");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 50, 30));

        jLabel97.setBackground(new java.awt.Color(0, 0, 0));
        jLabel97.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel97.setText("A Score ");
        jPanel3.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 10, -1, -1));

        jTextField70.setEditable(false);
        jTextField70.setBackground(new java.awt.Color(204, 204, 204));
        jTextField70.setForeground(new java.awt.Color(0, 0, 153));
        jTextField70.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField70ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField70, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 60, -1));

        jLabel98.setBackground(new java.awt.Color(0, 0, 0));
        jLabel98.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel98.setText("B Score ");
        jPanel3.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 10, -1, -1));

        jTextField71.setEditable(false);
        jTextField71.setBackground(new java.awt.Color(204, 204, 204));
        jTextField71.setForeground(new java.awt.Color(102, 0, 102));
        jTextField71.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(jTextField71, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 60, -1));

        jTextField72.setBackground(new java.awt.Color(255, 255, 255));
        jTextField72.setForeground(new java.awt.Color(0, 0, 0));
        jTextField72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField72MouseClicked(evt);
            }
        });
        jPanel3.add(jTextField72, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 310, -1));

        jLabel99.setBackground(new java.awt.Color(0, 0, 0));
        jLabel99.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel99.setText("Comments ");
        jPanel3.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jTextField76.setEditable(false);
        jTextField76.setBackground(new java.awt.Color(204, 204, 204));
        jTextField76.setForeground(new java.awt.Color(0, 0, 153));
        jTextField76.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField76ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField76, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 60, -1));

        jTextField77.setEditable(false);
        jTextField77.setBackground(new java.awt.Color(204, 204, 204));
        jTextField77.setForeground(new java.awt.Color(102, 0, 102));
        jTextField77.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(jTextField77, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 60, -1));

        jTextField78.setBackground(new java.awt.Color(255, 255, 255));
        jTextField78.setForeground(new java.awt.Color(0, 0, 0));
        jTextField78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField78MouseClicked(evt);
            }
        });
        jPanel3.add(jTextField78, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 310, -1));

        jComboBox57.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox57.setEditable(true);
        jComboBox57.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox57.setEnabled(false);
        jComboBox57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox57ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox57, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

        jComboBox59.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox59.setEditable(true);
        jComboBox59.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox59.setEnabled(false);
        jComboBox59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox59ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox59, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 50, 20));

        jLabel123.setBackground(new java.awt.Color(0, 153, 204));
        jLabel123.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(102, 0, 0));
        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel123.setText("Score");
        jPanel3.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 40, -1));

        jLabel124.setBackground(new java.awt.Color(0, 153, 204));
        jLabel124.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(102, 0, 0));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel124.setText("--------------");
        jPanel3.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jLabel125.setBackground(new java.awt.Color(0, 153, 204));
        jLabel125.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(102, 0, 0));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel125.setText("--------------");
        jPanel3.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Score");
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel80.setBackground(new java.awt.Color(102, 51, 0));
        jLabel80.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 24)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(102, 0, 0));
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("Total Machinery Standard Containers Score  =");
        jPanel9.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 32, 600, 30));

        jLabel132.setBackground(new java.awt.Color(102, 51, 0));
        jLabel132.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(102, 0, 0));
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setText("------");
        jPanel9.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 100, -1));

        jLabel133.setBackground(new java.awt.Color(153, 0, 51));
        jLabel133.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(102, 0, 0));
        jLabel133.setText("A Score");
        jPanel9.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 70, -1));

        jLabel134.setBackground(new java.awt.Color(153, 0, 51));
        jLabel134.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(102, 0, 0));
        jLabel134.setText("B Score");
        jPanel9.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 70, -1));

        jLabel81.setBackground(new java.awt.Color(102, 51, 0));
        jLabel81.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(102, 0, 0));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setText("------");
        jPanel9.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 100, -1));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);
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
        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 130, 20));

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
        jPanel1.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 130, 20));

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
        jPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 130, 20));

        jComboBox1.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox1.setToolTipText("");
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 130, 20));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("<html> I. Are SREC signal cable for the individual channels in all places separated from other channels or sufficiently protected ? </html>");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 390, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("<html> IV. Subsystem elements are considered to be physically separate entities in their own local enclosures if they can contribute to a CCF.? </html>");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 370, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("1.Separation/Isolation");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, -1));

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
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 60, -1));

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
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 60, -1));

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
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 310, -1));

        jLabel43.setBackground(new java.awt.Color(0, 0, 0));
        jLabel43.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel43.setText("Comments ");
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jLabel46.setBackground(new java.awt.Color(0, 0, 0));
        jLabel46.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel46.setText("A Score ");
        jPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 10, -1, -1));

        jLabel47.setBackground(new java.awt.Color(0, 0, 0));
        jLabel47.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel47.setText("B Score ");
        jPanel1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 10, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(204, 204, 204));
        jTextField4.setForeground(new java.awt.Color(0, 0, 153));
        jTextField4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 60, -1));

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(204, 204, 204));
        jTextField5.setForeground(new java.awt.Color(102, 0, 102));
        jTextField5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 60, -1));

        jTextField6.setBackground(new java.awt.Color(255, 255, 255));
        jTextField6.setForeground(new java.awt.Color(0, 0, 0));
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 310, -1));

        jTextField7.setEditable(false);
        jTextField7.setBackground(new java.awt.Color(204, 204, 204));
        jTextField7.setForeground(new java.awt.Color(0, 0, 153));
        jTextField7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(204, 204, 204));
        jTextField8.setForeground(new java.awt.Color(102, 0, 102));
        jTextField8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jTextField9.setBackground(new java.awt.Color(255, 255, 255));
        jTextField9.setForeground(new java.awt.Color(0, 0, 0));
        jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField9MouseClicked(evt);
            }
        });
        jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 310, -1));

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(204, 204, 204));
        jTextField10.setForeground(new java.awt.Color(0, 0, 153));
        jTextField10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(204, 204, 204));
        jTextField11.setForeground(new java.awt.Color(102, 0, 102));
        jTextField11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jTextField12.setBackground(new java.awt.Color(255, 255, 255));
        jTextField12.setForeground(new java.awt.Color(0, 0, 0));
        jTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField12MouseClicked(evt);
            }
        });
        jPanel1.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 310, -1));

        jComboBox24.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox24.setEditable(true);
        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox24.setEnabled(false);
        jComboBox24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox24ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox24, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

        jComboBox27.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox27.setEditable(true);
        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox27.setEnabled(false);
        jComboBox27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox27ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox27, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 50, 20));

        jComboBox28.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox28.setEditable(true);
        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox28.setEnabled(false);
        jComboBox28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox28ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 50, 20));

        jComboBox32.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox32.setEditable(true);
        jComboBox32.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox32.setEnabled(false);
        jComboBox32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox32ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox32, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 50, 20));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("<html> II. Is the detection of signal transmission errors sufficient when using information coding / decoding ? </html>");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 370, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("<html> III. Are SRECS signal cables and electrical power supply cables separated or sufficiently protected at all locations? </html>");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 370, -1));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1070, 200));

        jLabel108.setBackground(new java.awt.Color(0, 153, 204));
        jLabel108.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(102, 0, 0));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Score");
        jPanel4.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 40, -1));

        jLabel109.setBackground(new java.awt.Color(0, 153, 204));
        jLabel109.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(102, 0, 0));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel109.setText("--------------");
        jPanel4.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 60, -1));

        jLabel110.setBackground(new java.awt.Color(0, 153, 204));
        jLabel110.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(102, 0, 0));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel110.setText("--------------");
        jPanel4.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 60, -1));

        jLabel54.setBackground(new java.awt.Color(0, 51, 51));
        jLabel54.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 102, 51));
        jLabel54.setText("Completed");
        jPanel4.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 210, 110, 30));

        jLabel17.setBackground(new java.awt.Color(0, 51, 51));
        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 51));
        jLabel17.setText("0%");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 210, 60, 30));

        jLabel10.setBackground(new java.awt.Color(153, 153, 153));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("0%");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setText("Total Completed");

        jPanel11.setBackground(new java.awt.Color(153, 153, 153));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Score");
        jPanel11.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel83.setBackground(new java.awt.Color(102, 51, 0));
        jLabel83.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(102, 0, 0));
        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("--------------");
        jPanel11.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 440, -1));

        jLabel135.setBackground(new java.awt.Color(102, 51, 0));
        jLabel135.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 36)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(102, 0, 0));
        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel135.setText("------");
        jPanel11.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 120, -1));

        jLabel136.setBackground(new java.awt.Color(153, 0, 51));
        jLabel136.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(102, 0, 0));
        jLabel136.setText("Beta Factor ");
        jPanel11.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 110, -1));

        jLabel137.setBackground(new java.awt.Color(153, 0, 51));
        jLabel137.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(102, 0, 0));
        jLabel137.setText("Raw Score ");
        jPanel11.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 110, -1));

        jTextArea1.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Your Final Comments");
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jButton1.setText("60");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Diagnostic Coverage");

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Machinery Standard");

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(5, 5, 5)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox15ItemStateChanged
       //  // orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox15ItemStateChanged

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13};

         markedPercentage(combo,jLabel18);
         CalculateScoreMax(1,0);

    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox14ItemStateChanged
      //   // orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox14ItemStateChanged

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

       JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox13};

       markedPercentage(combo,jLabel18);
       CalculateScoreMax(1,1);
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox13ItemStateChanged
      //  // orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox13ItemStateChanged

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

       JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13};

       markedPercentage(combo,jLabel18);
       CalculateScoreMax(1,3);
    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField18MouseClicked
       setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField18MouseClicked

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField21MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField21MouseClicked

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jTextField30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField30MouseClicked
       setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField30MouseClicked

    private void jTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30ActionPerformed

    private void jComboBox38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox38ActionPerformed
       CalculateScoreFraction(1,0);
    }//GEN-LAST:event_jComboBox38ActionPerformed

    private void jComboBox39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox39ActionPerformed
        CalculateScoreFraction(1,1);
    }//GEN-LAST:event_jComboBox39ActionPerformed

    private void jComboBox40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox40ActionPerformed
       CalculateScoreFraction(1,3);
    }//GEN-LAST:event_jComboBox40ActionPerformed

    private void jComboBox63ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox63ItemStateChanged
       //  // orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox63ItemStateChanged

    private void jComboBox63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox63ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

       JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13};

       markedPercentage(combo,jLabel18);
       CalculateScoreMax(1,2);
    }//GEN-LAST:event_jComboBox63ActionPerformed

    private void jComboBox64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox64ActionPerformed
        CalculateScoreFraction(1,2);
    }//GEN-LAST:event_jComboBox64ActionPerformed

    private void jTextField88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField88ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField88ActionPerformed

    private void jTextField90MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField90MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField90MouseClicked

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

       JComboBox[] combo ={jComboBox3};

       markedPercentage(combo,jLabel19);
       CalculateScoreMax(2,0);

    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField33MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField33MouseClicked

    private void jComboBox47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox47ActionPerformed
         CalculateScoreFraction(2,0);
    }//GEN-LAST:event_jComboBox47ActionPerformed

    private void jComboBox29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox29ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox29,jComboBox30};

          markedPercentage(combo,jLabel20);
         CalculateScoreMax(3,0);
    }//GEN-LAST:event_jComboBox29ActionPerformed

    private void jComboBox30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox30ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox29,jComboBox30};

         markedPercentage(combo,jLabel20);
         CalculateScoreMax(3,1);
    }//GEN-LAST:event_jComboBox30ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jTextField24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField24MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField24MouseClicked

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jTextField54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField54MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField54MouseClicked

    private void jComboBox48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox48ActionPerformed
         CalculateScoreFraction(3,0);
    }//GEN-LAST:event_jComboBox48ActionPerformed

    private void jComboBox51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox51ActionPerformed
         CalculateScoreFraction(3,1);
    }//GEN-LAST:event_jComboBox51ActionPerformed

    private void jComboBox33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox33ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox33};

         markedPercentage(combo,jLabel21);
         CalculateScoreMax(4,0);
    }//GEN-LAST:event_jComboBox33ActionPerformed

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField57MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField57MouseClicked

    private void jComboBox52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox52ActionPerformed
         CalculateScoreFraction(4,0);
    }//GEN-LAST:event_jComboBox52ActionPerformed

    private void jComboBox21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox21ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox21,jComboBox23};

         markedPercentage(combo,jLabel22);
         CalculateScoreMax(5,0);
    }//GEN-LAST:event_jComboBox21ActionPerformed

    private void jComboBox23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox23ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox21,jComboBox23};

         markedPercentage(combo,jLabel22);
         CalculateScoreMax(5,1);
    }//GEN-LAST:event_jComboBox23ActionPerformed

    private void jTextField70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField70ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField70ActionPerformed

    private void jTextField72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField72MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField72MouseClicked

    private void jTextField76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField76ActionPerformed

    private void jTextField78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField78MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField78MouseClicked

    private void jComboBox57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox57ActionPerformed
         CalculateScoreFraction(5,0);
    }//GEN-LAST:event_jComboBox57ActionPerformed

    private void jComboBox59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox59ActionPerformed
         CalculateScoreFraction(5,1);
    }//GEN-LAST:event_jComboBox59ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};

         markedPercentage(combo,jLabel17);
         CalculateScoreMax(0,0);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
         // orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};

         markedPercentage(combo,jLabel17);
         CalculateScoreMax(0,1);

    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
         // orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};

         markedPercentage(combo,jLabel17);
         CalculateScoreMax(0,2);

    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
         // orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );

        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};

         markedPercentage(combo,jLabel17);
         CalculateScoreMax(0,3);

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseExited
        /*/   try {
            Integer.parseInt(jTextField1.getText()+"");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1MouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        /*   try {
            Integer.parseInt(jTextField1.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            //jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseExited
        /* try {
            int i= Integer.parseInt(jTextField2.getText()+"0");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
            jTextField2.setText("0");
        }*/
    }//GEN-LAST:event_jTextField2MouseExited

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        /*  try {
            Integer.parseInt(jTextField2.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Invalid Number");
        }*/
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
        //JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
         setCommentsPopup(evt);

    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseExited
        //JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
    }//GEN-LAST:event_jTextField3MouseExited

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed

    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField9MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField9MouseClicked

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField12MouseClicked
         setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField12MouseClicked

    private void jComboBox24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox24ActionPerformed
         CalculateScoreFraction(0,0);
    }//GEN-LAST:event_jComboBox24ActionPerformed

    private void jComboBox27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox27ActionPerformed
         CalculateScoreFraction(0,2);
    }//GEN-LAST:event_jComboBox27ActionPerformed

    private void jComboBox28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox28ActionPerformed
         CalculateScoreFraction(0,3);
    }//GEN-LAST:event_jComboBox28ActionPerformed

    private void jComboBox32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox32ActionPerformed
         CalculateScoreFraction(0,1);
    }//GEN-LAST:event_jComboBox32ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ProgramableElectronics proEle = new ProgramableElectronics();
        JOptionPane.showMessageDialog(null, new JScrollPane( proEle), "Select the Diagnostic Coverage",JOptionPane.QUESTION_MESSAGE);
        jButton1.setText(proEle.getDiagnosticCoverage());
        //System.out.println("888888888888888888"+);
        CalculateFactors(Integer.parseInt(jLabel132.getText()),Integer.parseInt(jLabel81.getText()),Integer.parseInt(proEle.getDiagnosticCoverage()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
       JSONObject result= new JSONObject(ccfCalcHdr.getProsettings().toString());
        
       try {
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            
        if(dataSingleCom.size()==0){ 
         System.out.println("dataSingleCom.size() "+dataSingleCom.size());
         dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
         //dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() , "Machinery Standard",spgId);
         setCCFDataForComp(dataSingleCom);    
        }
            
            for (int i = 0; i < indiviScore.length; i++) {

                for (int j = 0; j < indiviScore[i].length; j++) {

                           //ccfDao.Save(getCCFLine(i,j));
                           saveCCFCom(getCCFLine(i,j));
                }

            }
             //saveCCFCom(getCCFFinal());

               
                ccfCurd.setAscore(result, NumberConversion.NVLDouble(jLabel132.getText(), 0)+"");
                ccfCurd.setBscore(result,NumberConversion.NVLDouble(jLabel81.getText(), 0)+"");
                ccfCurd.setRowscore(result,NumberConversion.NVLDouble(jLabel135.getText(), 0)+"");
                ccfCurd.setBetaFactor(result,NumberConversion.NVLDouble(jLabel83.getText(), 0)+"");
                ccfCurd.setDiagnosticCoverage(result,NumberConversion.NVLDouble(jButton1.getText(), 0)+"");
                ccfCalcHdr.setProsettings(result);
                ccfCalcHdr.setComments(jTextArea1.getText());
       
                spfhdrDao.update(ccfCalcHdr);
            
            
            JOptionPane.showMessageDialog(null,"Save Successfully", "Information",JOptionPane.QUESTION_MESSAGE);    
        }catch(Exception e){
        
            JOptionPane.showMessageDialog(null,"Error - "+e.getMessage(), "Information",JOptionPane.ERROR_MESSAGE);     
        
        } finally{
            InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

     public Ccf getCCFLine(int i, int j){
     
        String subData[][][] = getAllSubData();
        
       // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        
        Ccf ccf = new Ccf();
               //ccf.setMainCategory("Machinery Standard");
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
     
   /* public Ccf getCCFFinal(){
     
        String subData[][][] = getAllSubData();
        
       // System.out.println("Achievement - "+subData[i][j][2]+"Ach Per"+indiviCombo[i][j][1].getSelectedItem()+" A- "+subData[i][j][0]+" B-"+subData[i][j][1]+" comments -"+subData[i][j][3]);
        
        Ccf ccf = new Ccf();
               ccf.setMainCategory("Machinery Standard");
               ccf.setSubCategory("");
               ccf.setDescription("Final");
               ccf.setAchievement(jLabel132.getText()+"#"+jLabel81.getText()+"#"+jLabel135.getText()+"#"+jButton1.getText());
               ccf.setAchievementPercentage(jLabel83.getText());
               ccf.setComments(jTextArea1.getText());
               ccf.setCcfSubComponent(subComDao.getSPGById(spgId));
               ccf.setUser(UserInfo.getInstance().getuser());
               ccf.setCreateddate(UserInfo.getInstance().getDate());
               ccf.setProjectId(UserInfo.getInstance().getTclProject());
    
    return ccf;
    }    */
     
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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox23;
    private javax.swing.JComboBox<String> jComboBox24;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox28;
    private javax.swing.JComboBox<String> jComboBox29;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox30;
    private javax.swing.JComboBox<String> jComboBox32;
    private javax.swing.JComboBox<String> jComboBox33;
    private javax.swing.JComboBox<String> jComboBox38;
    private javax.swing.JComboBox<String> jComboBox39;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox40;
    private javax.swing.JComboBox<String> jComboBox47;
    private javax.swing.JComboBox<String> jComboBox48;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox51;
    private javax.swing.JComboBox<String> jComboBox52;
    private javax.swing.JComboBox<String> jComboBox57;
    private javax.swing.JComboBox<String> jComboBox59;
    private javax.swing.JComboBox<String> jComboBox63;
    private javax.swing.JComboBox<String> jComboBox64;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
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
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField57;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField71;
    private javax.swing.JTextField jTextField72;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField78;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    // End of variables declaration//GEN-END:variables
}
