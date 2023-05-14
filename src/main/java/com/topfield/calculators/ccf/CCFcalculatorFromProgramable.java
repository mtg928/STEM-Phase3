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
import com.topfield.view.calculators.ProgramableElectronics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
public class CCFcalculatorFromProgramable extends javax.swing.JPanel {
    
    private CCFDao ccfDao = new CCFDaoImpl();
    private SpfhdrDao spfhdrDao = new SpfhdrDaoImpl();
    private CCFCurd ccfCurd= new CCFCurd();
    private SubProductGroupDao subComDao = new SubProductGroupDaoImpl();
    private final String[] mainHeading = {"Separation / Segregation","Diversity","Complexity/design/application/maturity/experience","Assessment/Analysis and feedback of data","Procedures/human interface","Competence/training/safty culture","Environmental control","Environmental testing"};
    private final String[][] qestions = {{"Are SREC signal cable for the individual channels in all places separated from other channels or sufficiently protected",
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
    private int[][][] scoresAandB =new int[][][]{ {{15,52},{85,55},{90,60},{95,65}},
                                       {{100,25},{90,25},{70,25},{40,25},{20,0},{12,25}},
                                       {{30,0},{0,10},{0,20},{0,20},{0,20},{0,20},{30,0}},
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
    private List<Ccf> dataSingleCom ;
    /**
     * Creates new form CCFcalculatorFrom
     */
    public CCFcalculatorFromProgramable(Spfhdr ccfCalcHdr) {
        initComponents();
       // setBackground(Color.red);
       this.ccfCalcHdr = ccfCalcHdr;
       //dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() , "Programmable",spgId);
        dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
       jLabel50.setText("Betaplus - "+ccfCalcHdr.getSpfid().getMpgheader().getFmeaComponent().getMpgDescription());
       indiviScore = new JTextField[][][]{{{jTextField2,jTextField1},{jTextField4,jTextField5},{jTextField7,jTextField8},{jTextField10,jTextField11}},
	                                 {{jTextField28,jTextField29},{jTextField16,jTextField17},{jTextField88,jTextField89},{jTextField19,jTextField20},{jTextField13,jTextField14},{jTextField25,jTextField26}},
					 {{jTextField31,jTextField32},{jTextField34,jTextField35},{jTextField37,jTextField38},{jTextField40,jTextField41},{jTextField43,jTextField44},{jTextField46,jTextField47},{jTextField49,jTextField50}},
                                         {{jTextField22,jTextField23},{jTextField52,jTextField53}},
					 {{jTextField55,jTextField56},{jTextField58,jTextField59},{jTextField61,jTextField62},{jTextField64,jTextField65},{jTextField67,jTextField68}},
					 {{jTextField70,jTextField71},{jTextField73,jTextField74},{jTextField76,jTextField77}},
					 {{jTextField79,jTextField80},{jTextField85,jTextField86}},
					 {{jTextField82,jTextField83}}};
        
        indiviCombo = new JComboBox[][][]{{{jComboBox2,jComboBox24},{jComboBox4,jComboBox32},{jComboBox5,jComboBox27},{jComboBox1,jComboBox28}},
                                          {{jComboBox15,jComboBox38},{jComboBox14,jComboBox39},{jComboBox63,jComboBox64},{jComboBox13,jComboBox40},{jComboBox16,jComboBox41},{jComboBox17,jComboBox42}},
                                          {{jComboBox3,jComboBox47},{jComboBox18,jComboBox49},{jComboBox6,jComboBox50},{jComboBox7,jComboBox43},{jComboBox8,jComboBox44},{jComboBox19,jComboBox45},{jComboBox20,jComboBox46}},
                                          {{jComboBox29,jComboBox48},{jComboBox30,jComboBox51}},
                                          {{jComboBox33,jComboBox52},{jComboBox34,jComboBox53},{jComboBox35,jComboBox54},{jComboBox36,jComboBox55},{jComboBox31,jComboBox56}},
                                          {{jComboBox21,jComboBox57},{jComboBox22,jComboBox58},{jComboBox23,jComboBox59}},
                                          {{jComboBox25,jComboBox60},{jComboBox26,jComboBox61}},
                                          {{jComboBox37,jComboBox62}}};
        
        totalScore = new JLabel[][]{{jLabel109,jLabel110},{jLabel112,jLabel113},{jLabel115,jLabel116},{jLabel118,jLabel119},
                                    {jLabel121,jLabel122},{jLabel124,jLabel125},{jLabel127,jLabel128},{jLabel130,jLabel131}};        
        
        
        indiviNote = new JTextField[][]{{jTextField3,jTextField6,jTextField9,jTextField12},{jTextField30,jTextField18,jTextField90,jTextField21,jTextField15,jTextField27},
                                       {jTextField33,jTextField36,jTextField39,jTextField42,jTextField45,jTextField48,jTextField51},{jTextField24,jTextField54},
                                       {jTextField57,jTextField60,jTextField63,jTextField66,jTextField69},
                                       {jTextField72,jTextField75,jTextField78},{jTextField81,jTextField87},{jTextField84}}; 
        
        
    /* jTextField3.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
                     JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
             
          }
        });*/
        
       setCCFDataForComp(dataSingleCom);
       SetDiagnosticCoverage(ccfCurd.getJSONProperty(ccfCalcHdr.getProsettings(),"results","diagnostic"),ccfCalcHdr.getComments()); //diagnosticCoverage
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
      jLabel81.setText(totBScore+""); 
    
      CalculateFactors(totAScore,totBScore,(int)NumberConversion.NVLDouble(jButton1.getText(), 0));
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
      
        JLabel [] precentagelables = {jLabel17,jLabel18,jLabel19,jLabel20,jLabel21,jLabel22,jLabel23,jLabel24};
        
       for (JLabel precentagelable : precentagelables) {
          totalPercentages = totalPercentages + Integer.parseInt(precentagelable.getText().replace("%", ""));  
        }
 
        
        jLabel10.setText((totalPercentages/8)+"%");
    }
 
    public void CalculateScore(JComboBox[] combo, JLabel scoreLabel  ){
        
    }
    
    public void CalculateFactors(int aScore,int bScore,int c){
        int rawScore=0;
        double betaFactor=0;
        double temp;
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        
        rawScore = aScore*c+bScore;
        temp =  (rawScore * (3.4)  )/ 2624;
        betaFactor = 0.3*Math.exp(temp*-1);
    
    jLabel135.setText(rawScore+"");
    jLabel83.setText(StringFuntions.DoubleRound(betaFactor));
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
    
    public String getRawScore(){
    
    return jLabel135.getText();
    }
    
    public String getBetaFactor(){
    
    return jLabel83.getText();
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
    
    
    /* public void CalculateScore(JComboBox[] combo, JLabel scoreLabel  ){
     int score=0;
       
        for (JComboBox jComboBox : combo) {
            score = score+ jComboBox.getSelectedIndex();
            System.out.println("score"+score);
        }
        
        scoreLabel.setText(score+"");
        CalculateTotalScore();
    }*/   
    
 /* public void CalculateTotalScore(){
     int scores=0;
     int totalPercentages = 0;
       
     JLabel [] scorelables = {jLabel2,jLabel50,jLabel52,jLabel71,jLabel73,jLabel77,jLabel79,jLabel83};
     JLabel [] precentagelables = {jLabel17,jLabel18,jLabel19,jLabel20,jLabel21,jLabel22,jLabel23,jLabel24};
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
 
        
        jLabel10.setText((totalPercentages/8)+"%");
        jLabel81.setText(scores+"");
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox16 = new javax.swing.JComboBox<>();
        jComboBox15 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jComboBox13 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
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
        jComboBox41 = new javax.swing.JComboBox<>();
        jComboBox42 = new javax.swing.JComboBox<>();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jComboBox63 = new javax.swing.JComboBox<>();
        jComboBox64 = new javax.swing.JComboBox<>();
        jTextField88 = new javax.swing.JTextField();
        jTextField89 = new javax.swing.JTextField();
        jTextField90 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jTextField31 = new javax.swing.JTextField();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jTextField46 = new javax.swing.JTextField();
        jTextField47 = new javax.swing.JTextField();
        jTextField48 = new javax.swing.JTextField();
        jTextField49 = new javax.swing.JTextField();
        jTextField50 = new javax.swing.JTextField();
        jTextField51 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jComboBox43 = new javax.swing.JComboBox<>();
        jComboBox44 = new javax.swing.JComboBox<>();
        jComboBox45 = new javax.swing.JComboBox<>();
        jComboBox46 = new javax.swing.JComboBox<>();
        jComboBox47 = new javax.swing.JComboBox<>();
        jComboBox49 = new javax.swing.JComboBox<>();
        jComboBox50 = new javax.swing.JComboBox<>();
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
        jLabel90 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jTextField55 = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        jTextField57 = new javax.swing.JTextField();
        jTextField58 = new javax.swing.JTextField();
        jTextField59 = new javax.swing.JTextField();
        jTextField60 = new javax.swing.JTextField();
        jTextField61 = new javax.swing.JTextField();
        jTextField62 = new javax.swing.JTextField();
        jTextField63 = new javax.swing.JTextField();
        jTextField64 = new javax.swing.JTextField();
        jTextField65 = new javax.swing.JTextField();
        jTextField66 = new javax.swing.JTextField();
        jTextField67 = new javax.swing.JTextField();
        jTextField68 = new javax.swing.JTextField();
        jTextField69 = new javax.swing.JTextField();
        jComboBox52 = new javax.swing.JComboBox<>();
        jComboBox53 = new javax.swing.JComboBox<>();
        jComboBox54 = new javax.swing.JComboBox<>();
        jComboBox55 = new javax.swing.JComboBox<>();
        jComboBox56 = new javax.swing.JComboBox<>();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jComboBox25 = new javax.swing.JComboBox<>();
        jComboBox26 = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jTextField79 = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jTextField80 = new javax.swing.JTextField();
        jTextField81 = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jTextField85 = new javax.swing.JTextField();
        jTextField86 = new javax.swing.JTextField();
        jTextField87 = new javax.swing.JTextField();
        jComboBox60 = new javax.swing.JComboBox<>();
        jComboBox61 = new javax.swing.JComboBox<>();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox22 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        jTextField71 = new javax.swing.JTextField();
        jTextField72 = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jTextField73 = new javax.swing.JTextField();
        jTextField74 = new javax.swing.JTextField();
        jTextField75 = new javax.swing.JTextField();
        jTextField76 = new javax.swing.JTextField();
        jTextField77 = new javax.swing.JTextField();
        jTextField78 = new javax.swing.JTextField();
        jComboBox57 = new javax.swing.JComboBox<>();
        jComboBox58 = new javax.swing.JComboBox<>();
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
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
        jLabel54 = new javax.swing.JLabel();
        jComboBox24 = new javax.swing.JComboBox<>();
        jComboBox27 = new javax.swing.JComboBox<>();
        jComboBox28 = new javax.swing.JComboBox<>();
        jComboBox32 = new javax.swing.JComboBox<>();
        jLabel109 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jComboBox37 = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField82 = new javax.swing.JTextField();
        jTextField84 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jTextField83 = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jComboBox62 = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
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

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 28, 191, -1));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox16.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox16.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox16.setToolTipText("");
        jComboBox16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox16ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 130, 20));

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
        jPanel5.add(jComboBox14, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 130, 20));

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
        jPanel5.add(jComboBox13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 130, 20));

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("(i.e M out of N where N > M +1)");
        jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 330, -1));

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("<html> II. Were the diverse channels developed from separate requirements from separate people with no communication between them?</html>");
        jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 390, -1));

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("* Do they 1 electronic + 1 mechanical/pneumatic?");
        jPanel5.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 400, -1));

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("* Do they 1 electronic or CPU + 1 relay based?");
        jPanel5.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 400, -1));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("* Do they identical channels employ enhanced voting?");
        jPanel5.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 400, -1));

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("I. Channels employ diverse technologies");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 530, -1));

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("<html>III. Were the two design specifications separately audited against  known hazards by separate people and were separate test methods and maintenance applied by separate people?</html>");
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 380, -1));

        jComboBox17.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox17.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox17.setToolTipText("");
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 130, 20));

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("2.Diversity");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 51));
        jLabel18.setText("0%");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 220, 60, 30));

        jTextField15.setBackground(new java.awt.Color(255, 255, 255));
        jTextField15.setForeground(new java.awt.Color(0, 0, 0));
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField15MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 310, -1));

        jTextField13.setEditable(false);
        jTextField13.setBackground(new java.awt.Color(204, 204, 204));
        jTextField13.setForeground(new java.awt.Color(0, 0, 153));
        jTextField13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jTextField14.setEditable(false);
        jTextField14.setBackground(new java.awt.Color(204, 204, 204));
        jTextField14.setForeground(new java.awt.Color(102, 0, 102));
        jTextField14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(204, 204, 204));
        jTextField16.setForeground(new java.awt.Color(0, 0, 153));
        jTextField16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 60, -1));

        jTextField17.setEditable(false);
        jTextField17.setBackground(new java.awt.Color(204, 204, 204));
        jTextField17.setForeground(new java.awt.Color(102, 0, 102));
        jTextField17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 60, -1));

        jTextField18.setBackground(new java.awt.Color(255, 255, 255));
        jTextField18.setForeground(new java.awt.Color(0, 0, 0));
        jTextField18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField18MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 310, 20));

        jTextField19.setEditable(false);
        jTextField19.setBackground(new java.awt.Color(204, 204, 204));
        jTextField19.setForeground(new java.awt.Color(0, 0, 153));
        jTextField19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jTextField20.setEditable(false);
        jTextField20.setBackground(new java.awt.Color(204, 204, 204));
        jTextField20.setForeground(new java.awt.Color(102, 0, 102));
        jTextField20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField20, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jTextField21.setBackground(new java.awt.Color(255, 255, 255));
        jTextField21.setForeground(new java.awt.Color(0, 0, 0));
        jTextField21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField21MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField21, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 310, -1));

        jTextField25.setEditable(false);
        jTextField25.setBackground(new java.awt.Color(204, 204, 204));
        jTextField25.setForeground(new java.awt.Color(0, 0, 153));
        jTextField25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField25, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 60, -1));

        jTextField26.setEditable(false);
        jTextField26.setBackground(new java.awt.Color(204, 204, 204));
        jTextField26.setForeground(new java.awt.Color(102, 0, 102));
        jTextField26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField26, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 190, 60, -1));

        jTextField27.setBackground(new java.awt.Color(255, 255, 255));
        jTextField27.setForeground(new java.awt.Color(0, 0, 0));
        jTextField27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField27MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 310, -1));

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
        jPanel5.add(jComboBox39, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 50, 20));

        jComboBox40.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox40.setEditable(true);
        jComboBox40.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox40.setEnabled(false);
        jComboBox40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox40ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox40, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 50, 20));

        jComboBox41.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox41.setEditable(true);
        jComboBox41.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox41.setEnabled(false);
        jComboBox41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox41ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox41, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 50, 20));

        jComboBox42.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox42.setEditable(true);
        jComboBox42.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox42.setEnabled(false);
        jComboBox42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox42ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox42, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 50, 20));

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
        jPanel5.add(jComboBox63, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 130, 20));

        jComboBox64.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox64.setEditable(true);
        jComboBox64.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox64.setEnabled(false);
        jComboBox64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox64ActionPerformed(evt);
            }
        });
        jPanel5.add(jComboBox64, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jTextField88.setEditable(false);
        jTextField88.setBackground(new java.awt.Color(204, 204, 204));
        jTextField88.setForeground(new java.awt.Color(0, 0, 153));
        jTextField88.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField88ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField88, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField89.setEditable(false);
        jTextField89.setBackground(new java.awt.Color(204, 204, 204));
        jTextField89.setForeground(new java.awt.Color(102, 0, 102));
        jTextField89.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.add(jTextField89, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField90.setBackground(new java.awt.Color(255, 255, 255));
        jTextField90.setForeground(new java.awt.Color(0, 0, 0));
        jTextField90.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField90MouseClicked(evt);
            }
        });
        jPanel5.add(jTextField90, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("* 1 CPU + electronic hardwired ?");
        jPanel5.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 400, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 1070, 260));

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
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 130, 20));

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
        jPanel2.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 130, 20));

        jComboBox7.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox7.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox7.setToolTipText("");
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 130, 20));

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
        jPanel2.add(jComboBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 130, 20));

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("<html> I. Does cross-connection between CPUs preclude the exchange any information other  than the diagnostics? </html>");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 390, -1));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("<html>II. Is there > 5 years experience of the equipment in the particular environment? </html>");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 400, -1));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("* 5 PCBs per channels");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 370, -1));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("* 100 lines of code");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 370, -1));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("* 5 ladder logic rungs");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 370, -1));

        jComboBox18.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox18.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox18.setToolTipText("");
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 130, 20));

        jLabel36.setBackground(new java.awt.Color(0, 0, 0));
        jLabel36.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("III. The equipment simple ");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 370, -1));

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("* 50 I/O and < 5 safety functions?");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 370, -1));

        jComboBox19.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox19.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox19.setToolTipText("");
        jComboBox19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox19ItemStateChanged(evt);
            }
        });
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 180, 130, 20));

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("<html>IV. Are I/O protected from over-voltages and over-current and rated > 2:1 ? </html>");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 360, -1));

        jComboBox20.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox20.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox20.setToolTipText("");
        jComboBox20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox20ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox20, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 130, 20));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 51));
        jLabel19.setText("0%");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 250, 60, 30));

        jTextField32.setEditable(false);
        jTextField32.setBackground(new java.awt.Color(204, 204, 204));
        jTextField32.setForeground(new java.awt.Color(102, 0, 102));
        jTextField32.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField32, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

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
        jPanel2.add(jTextField31, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jTextField33.setBackground(new java.awt.Color(255, 255, 255));
        jTextField33.setForeground(new java.awt.Color(0, 0, 0));
        jTextField33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField33MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField33, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jTextField34.setEditable(false);
        jTextField34.setBackground(new java.awt.Color(204, 204, 204));
        jTextField34.setForeground(new java.awt.Color(0, 0, 153));
        jTextField34.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField34ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField34, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 60, -1));

        jTextField35.setEditable(false);
        jTextField35.setBackground(new java.awt.Color(204, 204, 204));
        jTextField35.setForeground(new java.awt.Color(102, 0, 102));
        jTextField35.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField35, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 60, 60, -1));

        jTextField36.setBackground(new java.awt.Color(255, 255, 255));
        jTextField36.setForeground(new java.awt.Color(0, 0, 0));
        jTextField36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField36MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField36, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 310, -1));

        jTextField37.setEditable(false);
        jTextField37.setBackground(new java.awt.Color(204, 204, 204));
        jTextField37.setForeground(new java.awt.Color(0, 0, 153));
        jTextField37.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField37ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField37, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 60, -1));

        jTextField38.setEditable(false);
        jTextField38.setBackground(new java.awt.Color(204, 204, 204));
        jTextField38.setForeground(new java.awt.Color(102, 0, 102));
        jTextField38.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField38, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 60, -1));

        jTextField39.setBackground(new java.awt.Color(255, 255, 255));
        jTextField39.setForeground(new java.awt.Color(0, 0, 0));
        jTextField39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField39MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField39, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, 310, -1));

        jTextField40.setEditable(false);
        jTextField40.setBackground(new java.awt.Color(204, 204, 204));
        jTextField40.setForeground(new java.awt.Color(0, 0, 153));
        jTextField40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField40ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField40, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 60, -1));

        jTextField41.setEditable(false);
        jTextField41.setBackground(new java.awt.Color(204, 204, 204));
        jTextField41.setForeground(new java.awt.Color(102, 0, 102));
        jTextField41.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField41, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 60, -1));

        jTextField42.setBackground(new java.awt.Color(255, 255, 255));
        jTextField42.setForeground(new java.awt.Color(0, 0, 0));
        jTextField42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField42MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 310, -1));

        jTextField43.setEditable(false);
        jTextField43.setBackground(new java.awt.Color(204, 204, 204));
        jTextField43.setForeground(new java.awt.Color(0, 0, 153));
        jTextField43.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField43ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField43, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 60, -1));

        jTextField44.setEditable(false);
        jTextField44.setBackground(new java.awt.Color(204, 204, 204));
        jTextField44.setForeground(new java.awt.Color(102, 0, 102));
        jTextField44.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField44, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 60, -1));

        jTextField45.setBackground(new java.awt.Color(255, 255, 255));
        jTextField45.setForeground(new java.awt.Color(0, 0, 0));
        jTextField45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField45MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField45, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, 310, -1));

        jTextField46.setEditable(false);
        jTextField46.setBackground(new java.awt.Color(204, 204, 204));
        jTextField46.setForeground(new java.awt.Color(0, 0, 153));
        jTextField46.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField46ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField46, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 60, -1));

        jTextField47.setEditable(false);
        jTextField47.setBackground(new java.awt.Color(204, 204, 204));
        jTextField47.setForeground(new java.awt.Color(102, 0, 102));
        jTextField47.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField47, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 60, -1));

        jTextField48.setBackground(new java.awt.Color(255, 255, 255));
        jTextField48.setForeground(new java.awt.Color(0, 0, 0));
        jTextField48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField48MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField48, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 180, 310, -1));

        jTextField49.setEditable(false);
        jTextField49.setBackground(new java.awt.Color(204, 204, 204));
        jTextField49.setForeground(new java.awt.Color(0, 0, 153));
        jTextField49.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField49, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 60, -1));

        jTextField50.setEditable(false);
        jTextField50.setBackground(new java.awt.Color(204, 204, 204));
        jTextField50.setForeground(new java.awt.Color(102, 0, 102));
        jTextField50.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(jTextField50, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 60, -1));

        jTextField51.setBackground(new java.awt.Color(255, 255, 255));
        jTextField51.setForeground(new java.awt.Color(0, 0, 0));
        jTextField51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField51MouseClicked(evt);
            }
        });
        jPanel2.add(jTextField51, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 210, 310, -1));

        jLabel89.setBackground(new java.awt.Color(0, 51, 51));
        jLabel89.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 102, 51));
        jLabel89.setText("Completed");
        jPanel2.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 250, 110, 30));

        jComboBox43.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox43.setEditable(true);
        jComboBox43.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox43.setEnabled(false);
        jComboBox43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox43ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox43, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 50, 20));

        jComboBox44.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox44.setEditable(true);
        jComboBox44.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox44.setEnabled(false);
        jComboBox44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox44ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox44, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 50, 20));

        jComboBox45.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox45.setEditable(true);
        jComboBox45.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox45.setEnabled(false);
        jComboBox45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox45ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox45, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 50, 20));

        jComboBox46.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox46.setEditable(true);
        jComboBox46.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox46.setEnabled(false);
        jComboBox46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox46ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox46, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 50, 20));

        jComboBox47.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox47.setEditable(true);
        jComboBox47.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox47.setEnabled(false);
        jComboBox47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox47ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox47, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jComboBox49.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox49.setEditable(true);
        jComboBox49.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox49.setEnabled(false);
        jComboBox49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox49ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox49, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 50, 20));

        jComboBox50.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox50.setEditable(true);
        jComboBox50.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox50.setEnabled(false);
        jComboBox50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox50ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox50, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 50, 20));

        jLabel114.setBackground(new java.awt.Color(0, 153, 204));
        jLabel114.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(102, 0, 0));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel114.setText("Score");
        jPanel2.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, 40, -1));

        jLabel115.setBackground(new java.awt.Color(0, 153, 204));
        jLabel115.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(102, 0, 0));
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel115.setText("--------------");
        jPanel2.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, 60, -1));

        jLabel116.setBackground(new java.awt.Color(0, 153, 204));
        jLabel116.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(102, 0, 0));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel116.setText("--------------");
        jPanel2.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, 60, -1));

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("3.Complexity/design/application/maturity/experience ");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 450, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 1070, 280));

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
        jPanel7.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 400, -1));

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("<html>II.Is there documentary evidence that field failures are fully analyzed with feedback to design?</html>");
        jPanel7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 30));

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("4. Assessment/Analysis and feedback of data");
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
        jPanel7.add(jTextField22, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jTextField23.setEditable(false);
        jTextField23.setBackground(new java.awt.Color(204, 204, 204));
        jTextField23.setForeground(new java.awt.Color(102, 0, 102));
        jTextField23.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.add(jTextField23, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jTextField24.setBackground(new java.awt.Color(255, 255, 255));
        jTextField24.setForeground(new java.awt.Color(0, 0, 0));
        jTextField24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField24MouseClicked(evt);
            }
        });
        jPanel7.add(jTextField24, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

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
        jPanel7.add(jTextField52, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField53.setEditable(false);
        jTextField53.setBackground(new java.awt.Color(204, 204, 204));
        jTextField53.setForeground(new java.awt.Color(102, 0, 102));
        jTextField53.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.add(jTextField53, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField54.setBackground(new java.awt.Color(255, 255, 255));
        jTextField54.setForeground(new java.awt.Color(0, 0, 0));
        jTextField54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField54MouseClicked(evt);
            }
        });
        jPanel7.add(jTextField54, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

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
        jPanel7.add(jComboBox48, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

        jComboBox51.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox51.setEditable(true);
        jComboBox51.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox51.setEnabled(false);
        jComboBox51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox51ActionPerformed(evt);
            }
        });
        jPanel7.add(jComboBox51, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

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

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 840, 1070, 170));

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
        jPanel8.add(jComboBox36, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 130, 20));

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("<html>I. Is there written system of work on site to ensure that failures are investigated and checked in other channels?  (including degraded items that have not yet failed)</html>");
        jPanel8.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 400, 50));

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText("<html>II. Is maintenance of diverse/redundant channels stagged at such an interval as to ensure that any proof-tests and cross-checks operate satisfactorily between the maintenance?</html>");
        jPanel8.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 410, 40));

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("<html>III. Do written maintenance procedures ensure that redundant separations,as,for example ,signal cables, are separated from each other and from power cables and must not be re-routed? </html>");
        jPanel8.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 400, -1));

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("IV. Are modifications forbidden without full design analysis of CCF?");
        jPanel8.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 450, -1));

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("V. Is diverse euipment maintained by different staff?");
        jPanel8.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 370, -1));

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
        jPanel8.add(jComboBox31, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 130, 20));

        jLabel90.setBackground(new java.awt.Color(0, 51, 51));
        jLabel90.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 102, 51));
        jLabel90.setText("Completed");
        jPanel8.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 240, 110, 30));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 51));
        jLabel21.setText("0%");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 240, 50, 30));

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
        jPanel8.add(jTextField55, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jLabel95.setBackground(new java.awt.Color(0, 0, 0));
        jLabel95.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel95.setText("B Score ");
        jPanel8.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 20, -1, -1));

        jTextField56.setEditable(false);
        jTextField56.setBackground(new java.awt.Color(204, 204, 204));
        jTextField56.setForeground(new java.awt.Color(102, 0, 102));
        jTextField56.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField56, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

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
        jPanel8.add(jTextField57, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jTextField58.setEditable(false);
        jTextField58.setBackground(new java.awt.Color(204, 204, 204));
        jTextField58.setForeground(new java.awt.Color(0, 0, 153));
        jTextField58.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField58ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField58, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField59.setEditable(false);
        jTextField59.setBackground(new java.awt.Color(204, 204, 204));
        jTextField59.setForeground(new java.awt.Color(102, 0, 102));
        jTextField59.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField59, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField60.setBackground(new java.awt.Color(255, 255, 255));
        jTextField60.setForeground(new java.awt.Color(0, 0, 0));
        jTextField60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField60MouseClicked(evt);
            }
        });
        jPanel8.add(jTextField60, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jTextField61.setEditable(false);
        jTextField61.setBackground(new java.awt.Color(204, 204, 204));
        jTextField61.setForeground(new java.awt.Color(0, 0, 153));
        jTextField61.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField61ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField61, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 60, -1));

        jTextField62.setEditable(false);
        jTextField62.setBackground(new java.awt.Color(204, 204, 204));
        jTextField62.setForeground(new java.awt.Color(102, 0, 102));
        jTextField62.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField62, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, 60, -1));

        jTextField63.setBackground(new java.awt.Color(255, 255, 255));
        jTextField63.setForeground(new java.awt.Color(0, 0, 0));
        jTextField63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField63MouseClicked(evt);
            }
        });
        jPanel8.add(jTextField63, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, 310, -1));

        jTextField64.setEditable(false);
        jTextField64.setBackground(new java.awt.Color(204, 204, 204));
        jTextField64.setForeground(new java.awt.Color(0, 0, 153));
        jTextField64.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField64ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField64, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 60, -1));

        jTextField65.setEditable(false);
        jTextField65.setBackground(new java.awt.Color(204, 204, 204));
        jTextField65.setForeground(new java.awt.Color(102, 0, 102));
        jTextField65.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField65, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 190, 60, -1));

        jTextField66.setBackground(new java.awt.Color(255, 255, 255));
        jTextField66.setForeground(new java.awt.Color(0, 0, 0));
        jTextField66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField66MouseClicked(evt);
            }
        });
        jPanel8.add(jTextField66, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 190, 310, -1));

        jTextField67.setEditable(false);
        jTextField67.setBackground(new java.awt.Color(204, 204, 204));
        jTextField67.setForeground(new java.awt.Color(0, 0, 153));
        jTextField67.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField67ActionPerformed(evt);
            }
        });
        jPanel8.add(jTextField67, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, 60, -1));

        jTextField68.setEditable(false);
        jTextField68.setBackground(new java.awt.Color(204, 204, 204));
        jTextField68.setForeground(new java.awt.Color(102, 0, 102));
        jTextField68.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.add(jTextField68, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 220, 60, -1));

        jTextField69.setBackground(new java.awt.Color(255, 255, 255));
        jTextField69.setForeground(new java.awt.Color(0, 0, 0));
        jTextField69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField69MouseClicked(evt);
            }
        });
        jPanel8.add(jTextField69, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 310, -1));

        jComboBox52.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox52.setEditable(true);
        jComboBox52.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox52.setEnabled(false);
        jComboBox52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox52ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox52, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jComboBox53.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox53.setEditable(true);
        jComboBox53.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox53.setEnabled(false);
        jComboBox53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox53ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox53, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jComboBox54.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox54.setEditable(true);
        jComboBox54.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox54.setEnabled(false);
        jComboBox54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox54ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox54, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 50, 20));

        jComboBox55.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox55.setEditable(true);
        jComboBox55.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox55.setEnabled(false);
        jComboBox55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox55ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox55, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 50, 20));

        jComboBox56.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox56.setEditable(true);
        jComboBox56.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox56.setEnabled(false);
        jComboBox56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox56ActionPerformed(evt);
            }
        });
        jPanel8.add(jComboBox56, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 50, 20));

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

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1020, 1070, 270));

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

        jLabel92.setBackground(new java.awt.Color(0, 51, 51));
        jLabel92.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 102, 51));
        jLabel92.setText("Completed");
        jPanel6.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 90, 110, 30));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 51));
        jLabel23.setText("0%");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 90, 50, 30));

        jLabel100.setBackground(new java.awt.Color(0, 0, 0));
        jLabel100.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel100.setText("A Score ");
        jPanel6.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 10, -1, -1));

        jTextField79.setEditable(false);
        jTextField79.setBackground(new java.awt.Color(204, 204, 204));
        jTextField79.setForeground(new java.awt.Color(0, 0, 153));
        jTextField79.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField79ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField79, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 60, -1));

        jLabel101.setBackground(new java.awt.Color(0, 0, 0));
        jLabel101.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel101.setText("B Score ");
        jPanel6.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 10, -1, -1));

        jTextField80.setEditable(false);
        jTextField80.setBackground(new java.awt.Color(204, 204, 204));
        jTextField80.setForeground(new java.awt.Color(102, 0, 102));
        jTextField80.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jTextField80, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 60, -1));

        jTextField81.setBackground(new java.awt.Color(255, 255, 255));
        jTextField81.setForeground(new java.awt.Color(0, 0, 0));
        jTextField81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField81MouseClicked(evt);
            }
        });
        jPanel6.add(jTextField81, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 310, -1));

        jLabel102.setBackground(new java.awt.Color(0, 0, 0));
        jLabel102.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel102.setText("Comments ");
        jPanel6.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, -1, -1));

        jTextField85.setEditable(false);
        jTextField85.setBackground(new java.awt.Color(204, 204, 204));
        jTextField85.setForeground(new java.awt.Color(0, 0, 153));
        jTextField85.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField85ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField85, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 55, 60, -1));

        jTextField86.setEditable(false);
        jTextField86.setBackground(new java.awt.Color(204, 204, 204));
        jTextField86.setForeground(new java.awt.Color(102, 0, 102));
        jTextField86.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.add(jTextField86, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 55, 60, -1));

        jTextField87.setBackground(new java.awt.Color(255, 255, 255));
        jTextField87.setForeground(new java.awt.Color(0, 0, 0));
        jTextField87.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField87MouseClicked(evt);
            }
        });
        jPanel6.add(jTextField87, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 55, 310, -1));

        jComboBox60.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox60.setEditable(true);
        jComboBox60.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox60.setEnabled(false);
        jComboBox60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox60ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox60, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

        jComboBox61.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox61.setEditable(true);
        jComboBox61.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox61.setEnabled(false);
        jComboBox61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox61ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox61, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 55, 50, 20));

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

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1440, 1070, 120));

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

        jComboBox22.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox22.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<  Please Select  >", "Yes", "No" }));
        jComboBox22.setToolTipText("");
        jComboBox22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox22ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 130, 20));

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
        jLabel40.setText("I.Have designers been trained to understand CCF?");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 300, -1));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("II.Have installers been trained to understand CCF?");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 370, -1));

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("III.Have maintainers been trained to understand CCF?");
        jPanel3.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 370, -1));

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("6.Competence/training/safty culture");
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

        jTextField73.setEditable(false);
        jTextField73.setBackground(new java.awt.Color(204, 204, 204));
        jTextField73.setForeground(new java.awt.Color(0, 0, 153));
        jTextField73.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField73ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField73, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 60, -1));

        jTextField74.setEditable(false);
        jTextField74.setBackground(new java.awt.Color(204, 204, 204));
        jTextField74.setForeground(new java.awt.Color(102, 0, 102));
        jTextField74.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(jTextField74, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 50, 60, -1));

        jTextField75.setBackground(new java.awt.Color(255, 255, 255));
        jTextField75.setForeground(new java.awt.Color(0, 0, 0));
        jTextField75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField75MouseClicked(evt);
            }
        });
        jPanel3.add(jTextField75, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, 310, -1));

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

        jComboBox58.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox58.setEditable(true);
        jComboBox58.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox58.setEnabled(false);
        jComboBox58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox58ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox58, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, 50, 20));

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

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1300, 1070, 130));

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
        jLabel80.setText("Total Programable Electronics Containers Score  =");
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

        add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1900, 920, 80));

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
        jPanel1.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 130, 20));

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
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 130, 20));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("I. Are all signal cables separated at all positions?");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 300, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("II. Are the programable channels on ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 370, -1));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("* Are they separate printed circuit  boards?");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 320, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("* Are they in separate racks?");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 320, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("* Are they in separate rooms or building?");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 320, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("1.Separation / Segregation");
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

        jLabel17.setBackground(new java.awt.Color(0, 51, 51));
        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 51));
        jLabel17.setText("0%");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 140, 60, 30));

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
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 90, 60, -1));

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(204, 204, 204));
        jTextField8.setForeground(new java.awt.Color(102, 0, 102));
        jTextField8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, 60, -1));

        jTextField9.setBackground(new java.awt.Color(255, 255, 255));
        jTextField9.setForeground(new java.awt.Color(0, 0, 0));
        jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField9MouseClicked(evt);
            }
        });
        jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 310, -1));

        jTextField10.setEditable(false);
        jTextField10.setBackground(new java.awt.Color(204, 204, 204));
        jTextField10.setForeground(new java.awt.Color(0, 0, 153));
        jTextField10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jTextField11.setEditable(false);
        jTextField11.setBackground(new java.awt.Color(204, 204, 204));
        jTextField11.setForeground(new java.awt.Color(102, 0, 102));
        jTextField11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jTextField12.setBackground(new java.awt.Color(255, 255, 255));
        jTextField12.setForeground(new java.awt.Color(0, 0, 0));
        jTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField12MouseClicked(evt);
            }
        });
        jPanel1.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 310, -1));

        jLabel54.setBackground(new java.awt.Color(0, 51, 51));
        jLabel54.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 102, 51));
        jLabel54.setText("Completed");
        jPanel1.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 140, 110, 30));

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
        jPanel1.add(jComboBox27, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 50, 20));

        jComboBox28.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox28.setEditable(true);
        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox28.setEnabled(false);
        jComboBox28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox28ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox28, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 50, 20));

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

        jLabel109.setBackground(new java.awt.Color(0, 153, 204));
        jLabel109.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(102, 0, 0));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel109.setText("--------------");
        jPanel1.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jLabel108.setBackground(new java.awt.Color(0, 153, 204));
        jLabel108.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(102, 0, 0));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel108.setText("Score");
        jPanel1.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 40, -1));

        jLabel110.setBackground(new java.awt.Color(0, 153, 204));
        jLabel110.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(102, 0, 0));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel110.setText("--------------");
        jPanel1.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1070, 170));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 190));

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
        jPanel10.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 380, -1));

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("8.Environmental testing");
        jPanel10.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, -1));

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Score");
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, -1140, 190, -1));

        jLabel93.setBackground(new java.awt.Color(0, 51, 51));
        jLabel93.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 102, 51));
        jLabel93.setText("Completed");
        jPanel10.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 100, 100, 30));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 51));
        jLabel24.setText("0%");
        jPanel10.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 50, 30));

        jTextField82.setEditable(false);
        jTextField82.setBackground(new java.awt.Color(204, 204, 204));
        jTextField82.setForeground(new java.awt.Color(0, 0, 153));
        jTextField82.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField82ActionPerformed(evt);
            }
        });
        jPanel10.add(jTextField82, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, 60, -1));

        jTextField84.setBackground(new java.awt.Color(255, 255, 255));
        jTextField84.setForeground(new java.awt.Color(0, 0, 0));
        jTextField84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField84MouseClicked(evt);
            }
        });
        jPanel10.add(jTextField84, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 310, -1));

        jLabel105.setBackground(new java.awt.Color(0, 0, 0));
        jLabel105.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel105.setText("Comments ");
        jPanel10.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, -1, -1));

        jTextField83.setEditable(false);
        jTextField83.setBackground(new java.awt.Color(204, 204, 204));
        jTextField83.setForeground(new java.awt.Color(102, 0, 102));
        jTextField83.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.add(jTextField83, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 60, -1));

        jLabel103.setBackground(new java.awt.Color(0, 0, 0));
        jLabel103.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel103.setText("A Score ");
        jPanel10.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(635, 20, -1, -1));

        jLabel104.setBackground(new java.awt.Color(0, 0, 0));
        jLabel104.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel104.setText("B Score ");
        jPanel10.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 20, -1, -1));

        jComboBox62.setBackground(new java.awt.Color(153, 153, 153));
        jComboBox62.setEditable(true);
        jComboBox62.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100" }));
        jComboBox62.setEnabled(false);
        jComboBox62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox62ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox62, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 50, 20));

        jLabel106.setBackground(new java.awt.Color(0, 153, 204));
        jLabel106.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(102, 0, 0));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel106.setText("Score");
        jPanel10.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 40, -1));

        jLabel107.setBackground(new java.awt.Color(0, 153, 204));
        jLabel107.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(102, 0, 0));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel107.setText("--------------");
        jPanel10.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 60, -1));

        jLabel2.setBackground(new java.awt.Color(0, 153, 204));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("--------------");
        jPanel10.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 60, -1));

        jLabel131.setBackground(new java.awt.Color(0, 153, 204));
        jLabel131.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(102, 0, 0));
        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel131.setText("--------------");
        jPanel10.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 110, 60, -1));

        jLabel130.setBackground(new java.awt.Color(0, 153, 204));
        jLabel130.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(102, 0, 0));
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel130.setText("--------------");
        jPanel10.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 60, -1));

        jLabel129.setBackground(new java.awt.Color(0, 153, 204));
        jLabel129.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(102, 0, 0));
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel129.setText("Score");
        jPanel10.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 40, -1));

        add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1570, 1070, 130));

        jLabel10.setBackground(new java.awt.Color(153, 153, 153));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("0%");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 1910, 140, 70));

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel25.setText("Total Completed");
        add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 1880, 150, -1));

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

        add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2000, 920, 80));

        jTextArea1.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Your Final Comments");
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1720, 1070, 120));

        jButton1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jButton1.setText("60");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 2020, 130, 60));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Diagnostic Coverage");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 2000, 130, -1));

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Betaplus");
        add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1060, -1));

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 2100, 1070, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox13ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
         JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13,jComboBox16,jComboBox17};
       
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(1,3);

    }//GEN-LAST:event_jComboBox13ActionPerformed

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox13,jComboBox16,jComboBox17};
        
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(1,1);

    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox15ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
         JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13,jComboBox16,jComboBox17};
        
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(1,0);
        

    }//GEN-LAST:event_jComboBox15ActionPerformed

    private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox16ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
         JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13,jComboBox16,jComboBox17};

        markedPercentage(combo,jLabel18);
        CalculateScoreMax(1,4);
 
    }//GEN-LAST:event_jComboBox16ActionPerformed

    private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox17ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
         JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13,jComboBox16,jComboBox17};
        
        markedPercentage(combo,jLabel18);
        CalculateScoreMax(1,5);
 
    }//GEN-LAST:event_jComboBox17ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,0);
 
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};

        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,2);
        
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,3);
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox8ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,4);

    }//GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox18ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,1);
    }//GEN-LAST:event_jComboBox18ActionPerformed

    private void jComboBox19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox19ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,5);

    }//GEN-LAST:event_jComboBox19ActionPerformed

    private void jComboBox20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox20ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox3,jComboBox6,jComboBox7,jComboBox8,jComboBox18,jComboBox19,jComboBox20};
        
        markedPercentage(combo,jLabel19);
        CalculateScoreMax(2,6);
    }//GEN-LAST:event_jComboBox20ActionPerformed

    private void jComboBox21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox21ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};

        markedPercentage(combo,jLabel22);
        CalculateScoreMax(5,0);

    }//GEN-LAST:event_jComboBox21ActionPerformed

    private void jComboBox22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox22ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};
        
         markedPercentage(combo,jLabel22);
         CalculateScoreMax(5,1);

    }//GEN-LAST:event_jComboBox22ActionPerformed

    private void jComboBox23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox23ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox21,jComboBox22,jComboBox23};
        
         markedPercentage(combo,jLabel22);
         CalculateScoreMax(5,2);

    }//GEN-LAST:event_jComboBox23ActionPerformed

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

    private void jComboBox31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox31ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        
        markedPercentage(combo,jLabel21);
        CalculateScoreMax(4,4);

    }//GEN-LAST:event_jComboBox31ActionPerformed

    private void jComboBox33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox33ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};

        markedPercentage(combo,jLabel21);
        CalculateScoreMax(4,0);
    }//GEN-LAST:event_jComboBox33ActionPerformed

    private void jComboBox34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox34ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        
        markedPercentage(combo,jLabel21);
        CalculateScoreMax(4,1);

    }//GEN-LAST:event_jComboBox34ActionPerformed

    private void jComboBox35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox35ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        
        markedPercentage(combo,jLabel21);
        CalculateScoreMax(4,2);

    }//GEN-LAST:event_jComboBox35ActionPerformed

    private void jComboBox36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox36ActionPerformed
         System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox31,jComboBox34,jComboBox35,jComboBox36,jComboBox33};
        
        markedPercentage(combo,jLabel21);
        CalculateScoreMax(4,3);

    }//GEN-LAST:event_jComboBox36ActionPerformed

    private void jComboBox26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox26ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox25,jComboBox26};
        
         markedPercentage(combo,jLabel23);
         CalculateScoreMax(6,1);

    }//GEN-LAST:event_jComboBox26ActionPerformed

    private void jComboBox25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox25ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox25,jComboBox26};
        
         markedPercentage(combo,jLabel23);
         CalculateScoreMax(6,0);

    }//GEN-LAST:event_jComboBox25ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};
        
        markedPercentage(combo,jLabel17);
        CalculateScoreMax(0,3);
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};
        
        markedPercentage(combo,jLabel17);
        CalculateScoreMax(0,2);
        
       
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};
        
        markedPercentage(combo,jLabel17);
        CalculateScoreMax(0,1);
        
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox2,jComboBox4,jComboBox5,jComboBox1};
        
        markedPercentage(combo,jLabel17);
        CalculateScoreMax(0,0);
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox37ActionPerformed
        System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
        JComboBox[] combo ={jComboBox37};
        
        markedPercentage(combo,jLabel24);
        CalculateScoreMax(7,0);

    }//GEN-LAST:event_jComboBox37ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
      /*  try {
            Integer.parseInt(jTextField2.getText());
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(null,"Invalid Number");  
        }*/
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField19ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    private void jTextField28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28ActionPerformed

    private void jTextField31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField31ActionPerformed

    private void jTextField34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField34ActionPerformed

    private void jTextField37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField37ActionPerformed

    private void jTextField40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField40ActionPerformed

    private void jTextField43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField43ActionPerformed

    private void jTextField46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField46ActionPerformed

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField49ActionPerformed

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField52ActionPerformed

    private void jTextField55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField55ActionPerformed

    private void jTextField58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField58ActionPerformed

    private void jTextField61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField61ActionPerformed

    private void jTextField64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField64ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField64ActionPerformed

    private void jTextField67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField67ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField67ActionPerformed

    private void jTextField70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField70ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField70ActionPerformed

    private void jTextField73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField73ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField73ActionPerformed

    private void jTextField76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField76ActionPerformed

    private void jTextField79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField79ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField79ActionPerformed

    private void jTextField82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField82ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField82ActionPerformed

    private void jTextField85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField85ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField85ActionPerformed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseExited
       /* try {
           int i= Integer.parseInt(jTextField2.getText()+"0");
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(null,"Invalid Number");  
           jTextField2.setText("0");
        }*/
    }//GEN-LAST:event_jTextField2MouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    /*   try {
            Integer.parseInt(jTextField1.getText());
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(null,"Invalid Number"); 
           //jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseExited
     /*/   try {
            Integer.parseInt(jTextField1.getText()+"");
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(null,"Invalid Number"); 
           jTextField1.setText("0");
        }*/
    }//GEN-LAST:event_jTextField1MouseExited

    private void jTextField3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseExited
        //JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
    }//GEN-LAST:event_jTextField3MouseExited

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

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

    private void jComboBox38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox38ActionPerformed
        CalculateScoreFraction(1,0);
    }//GEN-LAST:event_jComboBox38ActionPerformed

    private void jComboBox39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox39ActionPerformed
        CalculateScoreFraction(1,1);
    }//GEN-LAST:event_jComboBox39ActionPerformed

    private void jComboBox40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox40ActionPerformed
        CalculateScoreFraction(1,3);
    }//GEN-LAST:event_jComboBox40ActionPerformed

    private void jComboBox41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox41ActionPerformed
        CalculateScoreFraction(1,4);
    }//GEN-LAST:event_jComboBox41ActionPerformed

    private void jComboBox42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox42ActionPerformed
        CalculateScoreFraction(1,5);
    }//GEN-LAST:event_jComboBox42ActionPerformed

    private void jComboBox43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox43ActionPerformed
        CalculateScoreFraction(2,3);
    }//GEN-LAST:event_jComboBox43ActionPerformed

    private void jComboBox44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox44ActionPerformed
        CalculateScoreFraction(2,4);
    }//GEN-LAST:event_jComboBox44ActionPerformed

    private void jComboBox45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox45ActionPerformed
        CalculateScoreFraction(2,5);
    }//GEN-LAST:event_jComboBox45ActionPerformed

    private void jComboBox46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox46ActionPerformed
        CalculateScoreFraction(2,6);
    }//GEN-LAST:event_jComboBox46ActionPerformed

    private void jComboBox47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox47ActionPerformed
        CalculateScoreFraction(2,0);
    }//GEN-LAST:event_jComboBox47ActionPerformed

    private void jComboBox49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox49ActionPerformed
        CalculateScoreFraction(2,1);
    }//GEN-LAST:event_jComboBox49ActionPerformed

    private void jComboBox50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox50ActionPerformed
        CalculateScoreFraction(2,2);
    }//GEN-LAST:event_jComboBox50ActionPerformed

    private void jComboBox48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox48ActionPerformed
        CalculateScoreFraction(3,0);
    }//GEN-LAST:event_jComboBox48ActionPerformed

    private void jComboBox51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox51ActionPerformed
        CalculateScoreFraction(3,1);
    }//GEN-LAST:event_jComboBox51ActionPerformed

    private void jComboBox52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox52ActionPerformed
        CalculateScoreFraction(4,0);
    }//GEN-LAST:event_jComboBox52ActionPerformed

    private void jComboBox53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox53ActionPerformed
        CalculateScoreFraction(4,1);
    }//GEN-LAST:event_jComboBox53ActionPerformed

    private void jComboBox54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox54ActionPerformed
        CalculateScoreFraction(4,2);
    }//GEN-LAST:event_jComboBox54ActionPerformed

    private void jComboBox55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox55ActionPerformed
        CalculateScoreFraction(4,3);
    }//GEN-LAST:event_jComboBox55ActionPerformed

    private void jComboBox56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox56ActionPerformed
        CalculateScoreFraction(4,4);
    }//GEN-LAST:event_jComboBox56ActionPerformed

    private void jComboBox57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox57ActionPerformed
        CalculateScoreFraction(5,0);
    }//GEN-LAST:event_jComboBox57ActionPerformed

    private void jComboBox58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox58ActionPerformed
        CalculateScoreFraction(5,1);
    }//GEN-LAST:event_jComboBox58ActionPerformed

    private void jComboBox59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox59ActionPerformed
        CalculateScoreFraction(5,2);
    }//GEN-LAST:event_jComboBox59ActionPerformed

    private void jComboBox60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox60ActionPerformed
        CalculateScoreFraction(6,0);
    }//GEN-LAST:event_jComboBox60ActionPerformed

    private void jComboBox61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox61ActionPerformed
        CalculateScoreFraction(6,1);
    }//GEN-LAST:event_jComboBox61ActionPerformed

    private void jComboBox62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox62ActionPerformed
        CalculateScoreFraction(7,0);
    }//GEN-LAST:event_jComboBox62ActionPerformed

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
        //JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
        setCommentsPopup(evt);

       
        
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
       setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jTextField9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField9MouseClicked
      setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField9MouseClicked

    private void jTextField12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField12MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField12MouseClicked

    private void jTextField30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField30MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField30MouseClicked

    private void jTextField18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField18MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField18MouseClicked

    private void jTextField21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField21MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField21MouseClicked

    private void jTextField15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField15MouseClicked

    private void jTextField27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField27MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField27MouseClicked

    private void jTextField33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField33MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField33MouseClicked

    private void jTextField36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField36MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField36MouseClicked

    private void jTextField39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField39MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField39MouseClicked

    private void jTextField42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField42MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField42MouseClicked

    private void jTextField45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField45MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField45MouseClicked

    private void jTextField48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField48MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField48MouseClicked

    private void jTextField51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField51MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField51MouseClicked

    private void jTextField24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField24MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField24MouseClicked

    private void jTextField54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField54MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField54MouseClicked

    private void jTextField57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField57MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField57MouseClicked

    private void jTextField60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField60MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField60MouseClicked

    private void jTextField63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField63MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField63MouseClicked

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

    private void jTextField78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField78MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField78MouseClicked

    private void jTextField81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField81MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField81MouseClicked

    private void jTextField87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField87MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField87MouseClicked

    private void jTextField84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField84MouseClicked
        setCommentsPopup(evt);
    }//GEN-LAST:event_jTextField84MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ProgramableElectronics proEle = new ProgramableElectronics();
        JOptionPane.showMessageDialog(null, new JScrollPane( proEle), "Select the Diagnostic Coverage",JOptionPane.QUESTION_MESSAGE);
        jButton1.setText(proEle.getDiagnosticCoverage());        
        //System.out.println("888888888888888888"+);
        CalculateFactors(Integer.parseInt(jLabel132.getText()),Integer.parseInt(jLabel81.getText()),Integer.parseInt(proEle.getDiagnosticCoverage()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox63ActionPerformed
       System.out.println("Value "+jComboBox2.getSelectedIndex() );
        
         JComboBox[] combo ={jComboBox15,jComboBox14,jComboBox63,jComboBox13,jComboBox16,jComboBox17};
        
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

    private void jTextField30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField30ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
       orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
       orOperator(new JComboBox[]{jComboBox4,jComboBox5,jComboBox1}, evt);
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox15ItemStateChanged
        orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox15ItemStateChanged

    private void jComboBox14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox14ItemStateChanged
       orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox14ItemStateChanged

    private void jComboBox63ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox63ItemStateChanged
       orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox63ItemStateChanged

    private void jComboBox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox13ItemStateChanged
        orOperator(new JComboBox[]{jComboBox15,jComboBox14,jComboBox63,jComboBox13}, evt);
    }//GEN-LAST:event_jComboBox13ItemStateChanged

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        orOperator(new JComboBox[]{jComboBox6,jComboBox7,jComboBox8,jComboBox19}, evt);
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        orOperator(new JComboBox[]{jComboBox6,jComboBox7,jComboBox8,jComboBox19}, evt);
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        orOperator(new JComboBox[]{jComboBox6,jComboBox7,jComboBox8,jComboBox19}, evt);
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jComboBox19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox19ItemStateChanged
        orOperator(new JComboBox[]{jComboBox6,jComboBox7,jComboBox8,jComboBox19}, evt);
    }//GEN-LAST:event_jComboBox19ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        JSONObject result= new JSONObject(ccfCalcHdr.getProsettings().toString());
        
        try {
           InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

            if(dataSingleCom.size()==0){
                System.out.println("dataSingleCom.size() "+dataSingleCom.size());
                dataSingleCom = ccfDao.getAllCcfByCalcHdrId(ccfCalcHdr.getSpfhdrid());
                //dataSingleCom = ccfDao.getAllCcfByTypeUserProj(UserInfo.getInstance().getuser().getUsername(),UserInfo.getInstance().getProjectNo() , "Programmable",spgId);
                setCCFDataForComp(dataSingleCom);
            }

            for (int i = 0; i < indiviScore.length; i++) {

                for (int j = 0; j < indiviScore[i].length; j++) {

                    //ccfDao.Save(getCCFLine(i,j));
                    saveCCFCom(getCCFLine(i,j));
                }

            }
            
                ccfCurd.setAscore(result, NumberConversion.NVLDouble(jLabel132.getText(), 0)+"");
                ccfCurd.setBscore(result,NumberConversion.NVLDouble(jLabel81.getText(), 0)+"");
                ccfCurd.setRowscore(result,NumberConversion.NVLDouble(jLabel135.getText(), 0)+"");
                ccfCurd.setBetaFactor(result,NumberConversion.NVLDouble(jLabel83.getText(), 0)+"");
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
         res  = Integer.parseInt(jLabel81.getText());
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
              // ccf.setMainCategory("Programmable");
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
               //ccf.setMainCategory("Programmable");
               ccf.setSubCategory("");
               ccf.setDescription("Final");
               ccf.setAchievement(jLabel132.getText()+"#"+jLabel81.getText()+"#"+jLabel135.getText()+"#"+jButton1.getText());
               ccf.setAchievementPercentage(jLabel83.getText());
               ccf.setComments(jTextArea1.getText());
               ccf.setCcfHdr(ccfHdr);
               /*ccf.setCcfSubComponent(subComDao.getSPGById(spgId));
               ccf.setUser(UserInfo.getInstance().getuser());
               ccf.setCreateddate(UserInfo.getInstance().getDate());
               ccf.setProjectId(UserInfo.getInstance().getTclProject());*/
    
   /* return ccf;
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
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JComboBox<String> jComboBox52;
    private javax.swing.JComboBox<String> jComboBox53;
    private javax.swing.JComboBox<String> jComboBox54;
    private javax.swing.JComboBox<String> jComboBox55;
    private javax.swing.JComboBox<String> jComboBox56;
    private javax.swing.JComboBox<String> jComboBox57;
    private javax.swing.JComboBox<String> jComboBox58;
    private javax.swing.JComboBox<String> jComboBox59;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox60;
    private javax.swing.JComboBox<String> jComboBox61;
    private javax.swing.JComboBox<String> jComboBox62;
    private javax.swing.JComboBox<String> jComboBox63;
    private javax.swing.JComboBox<String> jComboBox64;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
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
    private javax.swing.JLabel jLabel137;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
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
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
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
    private javax.swing.JTextField jTextField4;
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
    private javax.swing.JTextField jTextField5;
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
    private javax.swing.JTextField jTextField6;
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
    private javax.swing.JTextField jTextField7;
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
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField82;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField84;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField87;
    private javax.swing.JTextField jTextField88;
    private javax.swing.JTextField jTextField89;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField90;
    // End of variables declaration//GEN-END:variables
}
