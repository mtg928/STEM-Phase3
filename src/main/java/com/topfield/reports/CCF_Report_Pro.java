/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.reports;


import com.topfield.singleton.FileSelection;
import com.topfield.utilities.FileSelector;
import com.topfield.utilities.ImageProcessor;
import com.topfield.utilities.StringFuntions;
import com.topfield.user.UserInfo;
import com.topfield.utilities.WordExport;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.AttributedString;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;


/**
 *
 * @author Murali
 */
public class CCF_Report_Pro {
    
    public static String logo = "";
    public static String path="";
    public static String output = "C:\\Users\\Murali\\Desktop\\CCF_Report.docx";
    //URL u = getClass().getResource("/coachspecs/images/icons/TCL_Logo_32x32.png");
    private int [][] sectionTotals;
    private String notes;
    private String [][][] allSubdata;
    private String [] finaldata;

    public CCF_Report_Pro(int [][] sectionTotals,String [][][] allSubdata,String notes,String [] finaldata) {
        
        //path =FileSelector.getPathSelected();
        //output = path+"/"+"CCF_Report"+".docx";
        //output = path+"/"+"CCF_Report"+".docx";
        //path = FileSelector.getInstance().ChooseDirectory();

        this.sectionTotals = sectionTotals;
        this.allSubdata = allSubdata;
        this.notes = notes;
        this.finaldata = finaldata;
       
    }
    
    
   
   
   
   public void writeReport(){
       
      List<String> paths = FileSelection.getSelectionPath2(new int[]{2});
        
       if (paths.size()>1) {
        output = paths.get(0)+"."+paths.get(1);
        path = output;
      
   
       FileOutputStream out;
       XWPFDocument document = new XWPFDocument();
       
       writeHeaderLogo(document);
       writeProjectInfoTable(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);   
       writeHeadingTable(document, "Calculation"); 
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);   
       writeCalculationTable(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       writeHeadingTableWithContant(document,"Notes",StringFuntions.MultipleLine(notes, 90) ); 
       writeSubHeadings(document,"",ParagraphAlignment.LEFT); 
       String content = "1.Developments in the use of failure rate data and reliability prediction methods for Hardware, D.J. Smith, 2000."+"@"+
                        "2. Reliability, Maintainability and Risk, D.J. Smith, 9th Ed, 2011";
       writeHeadingTableWithContant(document,"References",content);
       
       //writeSubHeadings(document,"",ParagraphAlignment.LEFT); 
       //writeParagraph(document,"1.Developments in the use of failure rate data and reliability prediction methods for Hardware, D.J. Smith, 2000.\n");
       //writeParagraph(document,"2.Reliability, Maintainability and Risk, D.J. Smith, 9th Ed, 2011 ");
       
        try {
            out = new FileOutputStream(output);
                document.write(out);
                out.close();
                document.close();
                
             if(path.equals("")){
             }else{
                JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+output);
             }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        }
   }
   
   
    public void writeHeaderLogo(XWPFDocument doc)  {
        
        Path imagePath;
        
       // create header
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);

        // header's first paragraph
        XWPFParagraph paragraph = header.getParagraphArray(0);
        if (paragraph == null) paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);

        XWPFRun run = paragraph.createRun();
        ImageIcon logo1= new ImageIcon(getClass().getResource("/coachspecs/images/icons/topfield_icon.png"));

        try {
            // imagePath = Paths.get(getClass().getResource(logo).toURI());
             run.addPicture(ImageProcessor.ImageIconToInputStream(logo1.getImage()), Document.PICTURE_TYPE_PNG, "topfield_icon.png", Units.toEMU(120), Units.toEMU(60));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        }
 

        //run.setText("HEADER"); 
 

        //run.setText("HEADER"); 


    }
   
    public void writeHeadings(XWPFDocument document){
    
      XWPFParagraph title = document.createParagraph();
      title.setSpacingAfter(0);
       title.setAlignment(ParagraphAlignment.CENTER);
       XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your REST API with Spring");
        //titleRun.setColor(""009933");
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);
        

    }
    
    public void writeSubHeadings(XWPFDocument document,String text,ParagraphAlignment align){
    
        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setSpacingAfter(0);
        subTitle.setAlignment(align);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText(text);
        //subTitleRun.setColor("00CC44");
        subTitleRun.setColor("000000");
        //subTitleRun.setFontFamily("Courier");
        subTitleRun.setBold(true);
        subTitleRun.setFontSize(13);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.SINGLE);
    
    }
  
   
    public void writeHeaderLogo(XWPFDocument document,XWPFHeaderFooterPolicy policy){
        //write header content
		CTP ctpHeader = CTP.Factory.newInstance();
	        CTR ctrHeader = ctpHeader.addNewR();
		CTText ctHeader = ctrHeader.addNewT();
		//String headerText = "This is header";
		//ctHeader.setStringValue(headerText);
                
                
                XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
                headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
                
                XWPFRun imageRun = headerParagraph.createRun();
                imageRun.setTextPosition(20);
                Path imagePath;

                try {
                    imagePath = Paths.get(getClass().getResource(logo).toURI());
                    imageRun.addPicture(Files.newInputStream(imagePath),
                    XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
                    Units.toEMU(100), Units.toEMU(50));


                } catch (IOException ex) {
                    Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
	        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
	        parsHeader[0] = headerParagraph;
                policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);
    }
   
   
    public void writeHeadingTable(XWPFDocument document,String text) {
      
     //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      
      XWPFTableRow tableRowOne = table.getRow(0);tableRowOne.setHeight(0);
      tableRowOne.addNewTableCell();
      tableRowOne.addNewTableCell();
      tableRowOne.addNewTableCell();
      WordExport.mergeCellHorizontally(table, 0, 0, 3,0,WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9",text,"000000",ParagraphAlignment.LEFT));
      
    }
    
     public void writeHeadingTableWithContant(XWPFDocument document,String text,String Content) {
      
     //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      
      XWPFTableRow tableRowOne = table.getRow(0);
      tableRowOne.addNewTableCell();
      tableRowOne.addNewTableCell();
      tableRowOne.addNewTableCell();
      WordExport.mergeCellHorizontally(table, 0, 0, 3,0,WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9",text,"000000",ParagraphAlignment.LEFT));
      
      

      XWPFTableRow tableRowTwo = table.createRow();
      tableRowTwo.getCell(0).setParagraph(writeProReference(tableRowTwo.getCell(0).getParagraphs().get(0),Content,"@"));
      //tableRowTwo.getCell(1).setParagraph(WordExport.formatCell(tableRowTwo.getCell(1),"FFFFFF","COMMON CAUSE FAILURE CALCULATION","000000"));
      //tableRowTwo.getCell(2).setText("");
      //tableRowTwo.getCell(3).setText("");
      WordExport.mergeCellHorizontally(table, 1, 0, 3,"",ParagraphAlignment.LEFT,0,true);
    }
          
      
    
    public void writeProjectInfoTable(XWPFDocument document) {
      
     //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      
		
      //create first row
      XWPFTableRow tableRowOne = table.getRow(0);tableRowOne.setHeight(0);
      tableRowOne.addNewTableCell().setText("");
      tableRowOne.addNewTableCell().setText("");
      tableRowOne.addNewTableCell().setText("");
      WordExport.mergeCellHorizontally(table, 0, 0, 3,0,WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9","STEMS TM","0000FF",ParagraphAlignment.CENTER));   
		     
      //create second row
      XWPFTableRow tableRowTwo = table.createRow(); 
      tableRowTwo.getCell(0).setParagraph(WordExport.formatCell(tableRowTwo.getCell(0),"ABB2B9","Calculation Title","000000"));
      tableRowTwo.getCell(1).setParagraph(WordExport.formatCell(tableRowTwo.getCell(1),"FFFFFF","COMMON CAUSE FAILURE CALCULATION","000000"));
      tableRowTwo.getCell(2).setText("");
      tableRowTwo.getCell(3).setText("");
      WordExport.mergeCellHorizontally(table, 1, 1, 3,"",ParagraphAlignment.LEFT,0,true);
		
      //create third row
      XWPFTableRow tableRowThree = table.createRow();
      tableRowThree.getCell(0).setParagraph(WordExport.formatCell(tableRowThree.getCell(0),"ABB2B9","TN Number","000000"));
      tableRowThree.getCell(1).setText("123");
      tableRowThree.getCell(2).setParagraph(WordExport.formatCell(tableRowThree.getCell(2),"ABB2B9","","000000"));
      tableRowThree.getCell(3).setText("            ");
      /*tableRowThree.getCell(0).getParagraphs().get(0).setSpacingAfter(0);
      tableRowThree.getCell(1).getParagraphs().get(0).setSpacingAfter(0);
      tableRowThree.getCell(2).getParagraphs().get(0).setSpacingAfter(0);
      tableRowThree.getCell(3).getParagraphs().get(0).setSpacingAfter(0);*/
      
      //create third row
      XWPFTableRow tableRow4 = table.createRow();
      tableRow4.getCell(0).setParagraph(WordExport.formatCell(tableRow4.getCell(0),"ABB2B9","Project Number","000000"));
      tableRow4.getCell(1).setText("PJ00"+UserInfo.getInstance().getProjectNo());
      tableRow4.getCell(2).setParagraph(WordExport.formatCell(tableRow4.getCell(2),"ABB2B9","Superseded by Calc.","000000"));
      tableRow4.getCell(3).setText("N/A");
      
      
      XWPFTableRow tableRow6 = table.createRow();
      tableRow6.getCell(0).setParagraph(WordExport.formatCell(tableRow6.getCell(0),"ABB2B9","Calculation Number","000000"));
      tableRow6.getCell(1).setText("028210319");
      tableRow6.getCell(2).setParagraph(WordExport.formatCell(tableRow6.getCell(2),"ABB2B9","Revision","000000"));
      tableRow6.getCell(3).setText("A");
	  
      XWPFTableRow tableRow7= table.createRow();
      tableRow7.getCell(0).setParagraph(WordExport.formatCell(tableRow7.getCell(0),"ABB2B9","Engineer","000000"));
      tableRow7.getCell(1).setText(UserInfo.getInstance().getuser().getUsername());
      tableRow7.getCell(2).setParagraph(WordExport.formatCell(tableRow7.getCell(2),"ABB2B9","Engineer","000000"));
      tableRow7.getCell(3).setText("21.03.19");
	
      XWPFTableRow tableRow8= table.createRow();
      tableRow8.getCell(0).setParagraph(WordExport.formatCell(tableRow8.getCell(0),"ABB2B9","Checker","000000"));
      tableRow8.getCell(1).setText("   -   ");
      tableRow8.getCell(2).setParagraph(WordExport.formatCell(tableRow8.getCell(2),"ABB2B9","Date","000000"));
      tableRow8.getCell(3).setText(UserInfo.getInstance().getStr_date());
	  
      XWPFTableRow tableRow9= table.createRow();
      WordExport.mergeCellHorizontally(table, 7, 0, 3,0,WordExport.formatCell(tableRow9.getCell(0),"ABB2B9","Data","000000",ParagraphAlignment.LEFT));   
	

    }
    
    public void writeCalculationTable(XWPFDocument document) {
    
    //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      String ans ="";
		
      //create first row
      XWPFTableRow tableRowOne = table.getRow(0);
      tableRowOne.addNewTableCell().setParagraph(WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9","","000000"));
      tableRowOne.addNewTableCell().setParagraph(WordExport.formatCell(tableRowOne.getCell(1),"ABB2B9","","000000"));
      tableRowOne.addNewTableCell().setText("");
      WordExport.mergeCellHorizontally(table, 0, 2, 3,2,WordExport.formatCell(tableRowOne.getCell(2),"ABB2B9","RESULT","000000",ParagraphAlignment.CENTER));   
		
      //create second row
      XWPFTableRow tableRowTwo = table.createRow();
      tableRowTwo.getCell(0).setParagraph(WordExport.formatCell(tableRowTwo.getCell(0),"ABB2B9","SECTION","000000"));
      tableRowTwo.getCell(1).setParagraph(WordExport.formatCell(tableRowTwo.getCell(1),"ABB2B9","Note","000000"));
      tableRowTwo.getCell(2).setParagraph(WordExport.formatCell(tableRowTwo.getCell(2),"ABB2B9","A","000000"));
      tableRowTwo.getCell(3).setParagraph(WordExport.formatCell(tableRowTwo.getCell(3),"ABB2B9","B","000000"));
      //WordExport.mergeCellHorizontally(table, 1, 1, 3,"",ParagraphAlignment.LEFT,0,true);
		
      //create third row
      XWPFTableRow Separation = table.createRow();
      Separation.getCell(0).setParagraph(WordExport.formatCell(Separation.getCell(0),"FFFFFF","1. SEPARATION/SEGREGATION","000000"));
      Separation.getCell(1).setText("          ");
      Separation.getCell(2).setParagraph(WordExport.formatCell(Separation.getCell(2),"FFFFFF",sectionTotals[0][0]+"","000000"));
      Separation.getCell(3).setParagraph(WordExport.formatCell(Separation.getCell(3),"FFFFFF",sectionTotals[0][1]+"","000000"));
      
            //create third row
      XWPFTableRow Separation1 = table.createRow(); 
      ans ="\n\n Justification: All signal cables are located in individual channels and separated from " +
        "other channels. Safety relevant signals are transmitted via hardwired trainlines " +
        "parallel to the Train Communication Network (TCN). MVB connections have for " +
        "redundancy reasons two independent lines called A and B. The MVB participants " +
        "send a telegram parallel on both pair of wires (line A and line B).";
      
      ans = allSubdata[0][0][3];
     
      Separation1.getCell(0).setParagraph(WordExport.formatCellQueAns(Separation1.getCell(0),"I. Are all signal cables separated at all positions? " , allSubdata[0][0][3]));
      Separation1.getCell(1).setText(allSubdata[0][0][2]);
      Separation1.getCell(2).setText(allSubdata[0][0][0]+"          ");
      Separation1.getCell(3).setText(allSubdata[0][0][1]+"            ");
      
                  //create third row
      XWPFTableRow Separation2 = table.createRow();
      //Separation2.getCell(0).setText("II. Are the programable channels on ");
      Separation2.getCell(1).setText("          ");
      Separation2.getCell(2).setText(sectionTotals[0][0]+"          ");
      Separation2.getCell(3).setText(sectionTotals[0][1]+"            ");
      WordExport.mergeCellHorizontally(table, 4, 0, 3,2,WordExport.formatCell(Separation2.getCell(0),"II. Are the programable channels on "));  
      
                        //create third row
                        
      XWPFTableRow Separation3 = table.createRow(); 
      Separation3.getCell(0).setParagraph(WordExport.formatCellQueAns(Separation3.getCell(0),"   * Are they separate printed circuit boards? " , allSubdata[0][1][3]));
      Separation3.getCell(1).setText(allSubdata[0][1][2]);
      Separation3.getCell(2).setText(allSubdata[0][1][0]+"          ");
      Separation3.getCell(3).setText(allSubdata[0][1][1]+"            ");
      
                              //create third row
      XWPFTableRow Separation4 = table.createRow(); 
      Separation4.getCell(0).setParagraph(WordExport.formatCellQueAns(Separation4.getCell(0),"   * Are they in separate racks? " , allSubdata[0][2][3]));
      Separation4.getCell(1).setText(allSubdata[0][2][2]);
      Separation4.getCell(2).setText(allSubdata[0][2][0]+"          ");
      Separation4.getCell(3).setText(allSubdata[0][2][1]+"            ");
      
                                    //create third row
      XWPFTableRow Separation5 = table.createRow(); 
      Separation5.getCell(0).setParagraph(WordExport.formatCellQueAns(Separation5.getCell(0),"   * Are they in separate rooms or building? " , allSubdata[0][3][3]));
      Separation5.getCell(1).setText(allSubdata[0][3][2]);
      Separation5.getCell(2).setText(allSubdata[0][3][0]+"          ");
      Separation5.getCell(3).setText(allSubdata[0][3][1]+"            ");
      
      
      //create third row
      XWPFTableRow diversity = table.createRow(); 
      diversity.getCell(0).setParagraph(WordExport.formatCell(diversity.getCell(0),"FFFFFF","2.DIVERSITY","000000"));
      diversity.getCell(1).setText(" ");
      diversity.getCell(2).setParagraph(WordExport.formatCell(diversity.getCell(2),"FFFFFF",sectionTotals[1][0]+"","000000"));
      diversity.getCell(3).setParagraph(WordExport.formatCell(diversity.getCell(3),"FFFFFF",sectionTotals[1][1]+"","000000"));
      
                        //create third row
      XWPFTableRow diversity1 = table.createRow(); 
      //Separation2.getCell(0).setText("II. Are the programable channels on ");
      diversity1.getCell(1).setText("          ");
      diversity1.getCell(2).setText(sectionTotals[0][0]+"          ");
      diversity1.getCell(3).setText(sectionTotals[0][1]+"            ");
      WordExport.mergeCellHorizontally(table, 9, 0, 3,2,WordExport.formatCell(diversity1.getCell(0),"I. Channels employ diverse technologies")); 
      
      XWPFTableRow diversity2 = table.createRow();
      diversity2.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity2.getCell(0),"   * Do they 1 electronic + 1 mechanical/pneumatic? " , allSubdata[1][0][3]));
      diversity2.getCell(1).setText(allSubdata[1][0][2]);
      diversity2.getCell(2).setText(allSubdata[1][0][0]+"          ");
      diversity2.getCell(3).setText(allSubdata[1][0][1]+"            ");
      
      XWPFTableRow diversity3 = table.createRow();
      diversity3.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity3.getCell(0),"   * Do they 1 electronic or CPU + 1 relay based? " , allSubdata[1][1][3])); 
      diversity3.getCell(1).setText(allSubdata[1][1][2]);
      diversity3.getCell(2).setText(allSubdata[1][1][0]+"          ");
      diversity3.getCell(3).setText(allSubdata[1][1][1]+"            ");
      
      XWPFTableRow diversity4 = table.createRow(); 
      diversity4.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity4.getCell(0),"   * Do they 1 CPU + electronic hardwired ? " , allSubdata[1][2][3]));
      diversity4.getCell(1).setText(allSubdata[1][2][2]);
      diversity4.getCell(2).setText(allSubdata[1][2][0]+"          ");
      diversity4.getCell(3).setText(allSubdata[1][2][1]+"            ");
      
      XWPFTableRow diversity5 = table.createRow(); 
      diversity5.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity5.getCell(0),"   * Do they identical channels employ enhanced voting? (i.e M out of N where N > M +1) " , allSubdata[1][3][3]));
      diversity5.getCell(1).setText(allSubdata[1][3][2]);
      diversity5.getCell(2).setText(allSubdata[1][3][0]+"          ");
      diversity5.getCell(3).setText(allSubdata[1][3][1]+"            ");
      
      XWPFTableRow diversity6 = table.createRow(); 
      diversity6.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity6.getCell(0),"II. Were the diverse channels developed from separate requirements from separate people with no communication between them?" , allSubdata[1][4][3]));
      diversity6.getCell(1).setText(allSubdata[1][4][2]);
      diversity6.getCell(2).setText(allSubdata[1][4][0]+"          ");
      diversity6.getCell(3).setText(allSubdata[1][4][1]+"            ");
      
      XWPFTableRow diversity7 = table.createRow(); 
      diversity7.getCell(0).setParagraph(WordExport.formatCellQueAns(diversity7.getCell(0),"III. Were the two design specifications separately audited against  known hazards by separate people and were separate test methods and maintenance applied by separate people?" , allSubdata[1][5][3]));
      diversity7.getCell(1).setText(allSubdata[1][5][2]);
      diversity7.getCell(2).setText(allSubdata[1][5][0]+"          ");
      diversity7.getCell(3).setText(allSubdata[1][5][1]+"            ");
      
      
      XWPFTableRow complexity = table.createRow();
      complexity.getCell(0).setParagraph(WordExport.formatCell(complexity.getCell(0),"FFFFFF","3. COMPLEXITY/DESIGN/APPLICATION/MATURITY/ EXPERIENCE","000000"));
      complexity.getCell(1).setText("");
      complexity.getCell(2).setParagraph(WordExport.formatCell(complexity.getCell(2),"FFFFFF",sectionTotals[2][0]+"","000000"));
      complexity.getCell(3).setParagraph(WordExport.formatCell(complexity.getCell(3),"FFFFFF",sectionTotals[2][1]+"","000000"));
      
      XWPFTableRow complexity1 = table.createRow(); 
      complexity1.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity1.getCell(0),"I. Does cross-connection between CPUs preclude the exchange any information other  than the diagnostics?" , allSubdata[2][0][3]));
      complexity1.getCell(1).setText(allSubdata[2][0][2]);
      complexity1.getCell(2).setText(allSubdata[2][0][0]+"          ");
      complexity1.getCell(3).setText(allSubdata[2][0][1]+"            ");
      
      XWPFTableRow complexity2 = table.createRow(); 
      complexity2.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity2.getCell(0),"II. Is there > 5 years experience of the equipment in the particular environment?" , allSubdata[2][1][3]));
      complexity2.getCell(1).setText(allSubdata[2][1][2]);
      complexity2.getCell(2).setText(allSubdata[2][1][0]+"          ");
      complexity2.getCell(3).setText(allSubdata[2][1][1]+"            ");
      
      XWPFTableRow complexity3 = table.createRow(); 
      //Separation2.getCell(0).setText("II. Are the programable channels on ");
      complexity3.getCell(1).setText(allSubdata[2][2][2]);
      complexity3.getCell(2).setText(allSubdata[2][2][0]+"          ");
      complexity3.getCell(3).setText(allSubdata[2][2][1]+"            ");
      WordExport.mergeCellHorizontally(table, 18, 0, 3,2,WordExport.formatCell(complexity3.getCell(0),"III. The equipment simple ")); 
      
      XWPFTableRow complexity4 = table.createRow(); 
      complexity4.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity4.getCell(0),"   * 5 PCBs per channels " , allSubdata[2][2][3]));
      complexity4.getCell(1).setText(allSubdata[2][2][2]);
      complexity4.getCell(2).setText(allSubdata[2][2][0]+"          ");
      complexity4.getCell(3).setText(allSubdata[2][2][1]+"            ");
      
      XWPFTableRow complexity5 = table.createRow(); 
      complexity5.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity5.getCell(0),"   * 100 lines of code " , allSubdata[2][3][3]));
      complexity5.getCell(1).setText(allSubdata[2][3][2]);
      complexity5.getCell(2).setText(allSubdata[2][3][0]+"          ");
      complexity5.getCell(3).setText(allSubdata[2][3][1]+"            ");
      
      XWPFTableRow complexity6 = table.createRow(); 
      complexity6.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity6.getCell(0),"   * 5 ladder logic rungs " , allSubdata[2][4][3]));
      complexity6.getCell(1).setText(allSubdata[2][4][2]);
      complexity6.getCell(2).setText(allSubdata[2][4][0]+"          ");
      complexity6.getCell(3).setText(allSubdata[2][4][1]+"            ");
      
      XWPFTableRow complexity7 = table.createRow(); 
      complexity7.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity7.getCell(0),"   * 50 I/O and < 5 safety functions? " , allSubdata[2][5][3]));
      complexity7.getCell(1).setText(allSubdata[2][5][2]);
      complexity7.getCell(2).setText(allSubdata[2][5][0]+"          ");
      complexity7.getCell(3).setText(allSubdata[2][5][1]+"            ");
      
      XWPFTableRow complexity8 = table.createRow(); 
      complexity8.getCell(0).setParagraph(WordExport.formatCellQueAns(complexity8.getCell(0),"IV. Are I/O protected from over-voltages and over-current and rated > 2:1 ? " , allSubdata[2][6][3]));
      complexity8.getCell(1).setText(allSubdata[2][6][2]);
      complexity8.getCell(2).setText(allSubdata[2][6][0]+"          ");
      complexity8.getCell(3).setText(allSubdata[2][6][1]+"            ");
      
      XWPFTableRow assessment = table.createRow();
      assessment.getCell(0).setParagraph(WordExport.formatCell(assessment.getCell(0),"FFFFFF","4. ASSESSMENT/ANALYSIS AND FEEDBACK OF DATA","000000"));
      assessment.getCell(1).setText(" ");
      assessment.getCell(2).setParagraph(WordExport.formatCell(assessment.getCell(2),"FFFFFF",sectionTotals[3][0]+"","000000"));
      assessment.getCell(3).setParagraph(WordExport.formatCell(assessment.getCell(3),"FFFFFF",sectionTotals[3][1]+"","000000"));
      
      XWPFTableRow assessment1 = table.createRow(); 
      assessment1.getCell(0).setParagraph(WordExport.formatCellQueAns(assessment1.getCell(0),"I. Has a combination of detailed FMEA, fault tree analysis and design review  established potential CCFs in the electronics?  " , allSubdata[3][0][3]));
      assessment1.getCell(1).setText(allSubdata[3][0][2]);
      assessment1.getCell(2).setText(allSubdata[3][0][0]+"          ");
      assessment1.getCell(3).setText(allSubdata[3][0][1]+"            ");
      
      XWPFTableRow assessment2 = table.createRow(); 
      assessment2.getCell(0).setParagraph(WordExport.formatCellQueAns(assessment2.getCell(0),"II.Is there documentary evidence that field failures are fully analyzed with feedback to design? " , allSubdata[3][1][3]));
      assessment2.getCell(1).setText(allSubdata[3][1][2]);
      assessment2.getCell(2).setText(allSubdata[3][1][0]+"          ");
      assessment2.getCell(3).setText(allSubdata[3][1][1]+"            ");
	  
      XWPFTableRow procedures= table.createRow();
      procedures.getCell(0).setParagraph(WordExport.formatCell(procedures.getCell(0),"FFFFFF","5. PROCEDURES/HUMAN INTERFACE","000000"));
      procedures.getCell(1).setText(" ");
      procedures.getCell(2).setParagraph(WordExport.formatCell(procedures.getCell(2),"FFFFFF",sectionTotals[4][0]+"","000000"));
      procedures.getCell(3).setParagraph(WordExport.formatCell(procedures.getCell(3),"FFFFFF",sectionTotals[4][1]+"","000000"));
      
      XWPFTableRow procedures1 = table.createRow(); 
      procedures1.getCell(0).setParagraph(WordExport.formatCellQueAns(procedures1.getCell(0),"I. Is there written system of work on site to ensure that failures are investigated and checked in other channels?  (including degraded items that have not yet failed) " , allSubdata[4][0][3]));
      procedures1.getCell(1).setText(allSubdata[4][0][2]);
      procedures1.getCell(2).setText(allSubdata[4][0][0]+"          ");
      procedures1.getCell(3).setText(allSubdata[4][0][1]+"            ");
      
      XWPFTableRow procedures2 = table.createRow(); 
      procedures2.getCell(0).setParagraph(WordExport.formatCellQueAns(procedures2.getCell(0),"II. Is maintenance of diverse/redundant channels stagged at such an interval as to ensure that any proof-tests and cross-checks operate satisfactorily between the maintenance? " , allSubdata[4][1][3]));
      procedures2.getCell(1).setText(allSubdata[4][1][2]);
      procedures2.getCell(2).setText(allSubdata[4][1][0]+"          ");
      procedures2.getCell(3).setText(allSubdata[4][1][1]+"            ");
      
      XWPFTableRow procedures3 = table.createRow(); 
      procedures3.getCell(0).setParagraph(WordExport.formatCellQueAns(procedures3.getCell(0),"III. Do written maintenance procedures ensure that redundant separations,as,for example ,signal cables, are separated from each other and from power cables and must not be re-routed? " , allSubdata[4][2][3]));
      procedures3.getCell(1).setText(allSubdata[4][2][2]);
      procedures3.getCell(2).setText(allSubdata[4][2][0]+"          ");
      procedures3.getCell(3).setText(allSubdata[4][2][1]+"            ");
      
      XWPFTableRow procedures4 = table.createRow(); 
      procedures4.getCell(0).setParagraph(WordExport.formatCellQueAns(procedures4.getCell(0),"IV. Are modifications forbidden without full design analysis of CCF? " , allSubdata[4][3][3]));
      procedures4.getCell(1).setText(allSubdata[4][3][2]);
      procedures4.getCell(2).setText(allSubdata[4][3][0]+"          ");
      procedures4.getCell(3).setText(allSubdata[4][3][1]+"            ");
      
      XWPFTableRow procedures5 = table.createRow(); 
      procedures5.getCell(0).setParagraph(WordExport.formatCellQueAns(procedures5.getCell(0),"V. Is diverse euipment maintained by different staff? " , allSubdata[4][4][3]));
      procedures5.getCell(1).setText(allSubdata[4][4][2]);
      procedures5.getCell(2).setText(allSubdata[4][4][0]+"          ");
      procedures5.getCell(3).setText(allSubdata[4][4][1]+"            ");
	  
      XWPFTableRow competence= table.createRow(); 
      competence.getCell(0).setParagraph(WordExport.formatCell(competence.getCell(0),"FFFFFF","6. COMPETENCE/TRAINING/SAFETY CULTURE","000000"));
      competence.getCell(1).setText("   -   ");
      competence.getCell(2).setParagraph(WordExport.formatCell(competence.getCell(2),"FFFFFF",sectionTotals[5][0]+"","000000"));
      competence.getCell(3).setParagraph(WordExport.formatCell(competence.getCell(3),"FFFFFF",sectionTotals[5][1]+"","000000"));
      
      XWPFTableRow competence1 = table.createRow(); 
      competence1.getCell(0).setParagraph(WordExport.formatCellQueAns(competence1.getCell(0),"I.Have designers been trained to understand CCF? " , allSubdata[5][0][3]));
      competence1.getCell(1).setText(allSubdata[5][0][2]);
      competence1.getCell(2).setText(allSubdata[5][0][0]+"          ");
      competence1.getCell(3).setText(allSubdata[5][0][1]+"            ");
      
      XWPFTableRow competence2 = table.createRow(); 
      competence2.getCell(0).setParagraph(WordExport.formatCellQueAns(competence2.getCell(0),"II.Have installers been trained to understand CCF? " , allSubdata[5][1][3]));
      competence2.getCell(1).setText(allSubdata[5][1][2]);
      competence2.getCell(2).setText(allSubdata[5][1][0]+"          ");
      competence2.getCell(3).setText(allSubdata[5][1][1]+"            ");
      
      XWPFTableRow competence3 = table.createRow(); 
      competence3.getCell(0).setParagraph(WordExport.formatCellQueAns(competence3.getCell(0),"III.Have maintainers been trained to understand CCF? " , allSubdata[5][2][3]));
      competence3.getCell(1).setText(allSubdata[5][2][2]);
      competence3.getCell(2).setText(allSubdata[5][2][0]+"          ");
      competence3.getCell(3).setText(allSubdata[5][2][1]+"            ");
	  
      XWPFTableRow environmental= table.createRow();
      environmental.getCell(0).setParagraph(WordExport.formatCell(environmental.getCell(0),"FFFFFF","7. ENVIRONMENTAL CONTROL","000000"));
      environmental.getCell(1).setText("   -   ");
      environmental.getCell(2).setParagraph(WordExport.formatCell(environmental.getCell(2),"FFFFFF",sectionTotals[6][0]+"","000000"));
      environmental.getCell(3).setParagraph(WordExport.formatCell(environmental.getCell(3),"FFFFFF",sectionTotals[6][1]+"","000000"));
      
      XWPFTableRow environmental1 = table.createRow(); 
      environmental1.getCell(0).setParagraph(WordExport.formatCellQueAns(environmental1.getCell(0),"I. Is there limited personnel access? " , allSubdata[6][0][3]));
      environmental1.getCell(1).setText(allSubdata[6][0][2]);
      environmental1.getCell(2).setText(allSubdata[6][0][0]+"          ");
      environmental1.getCell(3).setText(allSubdata[6][0][1]+"            ");
      
      XWPFTableRow environmental2 = table.createRow(); 
      environmental2.getCell(0).setParagraph(WordExport.formatCellQueAns(environmental2.getCell(0),"II. Is there appropriate environmental control? <br> (e.g temperture,humidity) " , allSubdata[6][1][3]));
      environmental2.getCell(1).setText(allSubdata[6][1][2]);
      environmental2.getCell(2).setText(allSubdata[6][1][0]+"          ");
      environmental2.getCell(3).setText(allSubdata[6][0][1]+"            ");

      XWPFTableRow testing= table.createRow();
      testing.getCell(0).setParagraph(WordExport.formatCell(testing.getCell(0),"FFFFFF","8. ENVIRONMENTAL TESTING","000000"));
      testing.getCell(1).setText("   -   ");
      testing.getCell(2).setParagraph(WordExport.formatCell(testing.getCell(2),"FFFFFF",sectionTotals[7][0]+"","000000"));
      testing.getCell(3).setParagraph(WordExport.formatCell(testing.getCell(3),"FFFFFF",sectionTotals[7][1]+"","000000"));
      
      XWPFTableRow testing1 = table.createRow(); 
      testing1.getCell(0).setParagraph(WordExport.formatCellQueAns(testing1.getCell(0),"I. Has full EMC immunity or equivalent mechanical testing been conducted on prototypes and production units (using recognized standards) " , allSubdata[7][0][3]));
      testing1.getCell(1).setText(allSubdata[7][0][2]);
      testing1.getCell(2).setText(allSubdata[7][0][0]+"          ");
      testing1.getCell(3).setText(allSubdata[7][0][1]+"          ");
      
      XWPFTableRow total= table.createRow();
      total.getCell(0).setParagraph(WordExport.formatCell(total.getCell(0),"ABB2B9","TOTAL","000000"));
      total.getCell(1).setParagraph(WordExport.formatCell(total.getCell(1),"ABB2B9","","000000"));
      total.getCell(2).setParagraph(WordExport.formatCell(total.getCell(2),"ABB2B9",sectionTotals[8][0]+"","000000"));
      total.getCell(3).setParagraph(WordExport.formatCell(total.getCell(3),"ABB2B9",sectionTotals[8][1]+"","000000"));
    
      XWPFTableRow betaFactor= table.createRow();
      betaFactor.getCell(0).setParagraph(WordExport.formatCell(betaFactor.getCell(0),"ABB2B9","Result: Beta factor = "+finaldata[1]+"     Raw Score = "+finaldata[0],"000000"));
      betaFactor.getCell(1).setParagraph(WordExport.formatCell(betaFactor.getCell(1),"ABB2B9","","000000"));
      betaFactor.getCell(2).setParagraph(WordExport.formatCell(betaFactor.getCell(2),"ABB2B9",sectionTotals[8][0]+"","000000"));
      betaFactor.getCell(3).setParagraph(WordExport.formatCell(betaFactor.getCell(3),"ABB2B9",sectionTotals[8][1]+"","000000"));
      WordExport.mergeCellHorizontally(table, 43, 0, 3,2,WordExport.formatCell(betaFactor.getCell(0),"")); 
    }
    
    public XWPFParagraph writeProReference(XWPFParagraph para,String content,String splitStr){
    
       String[] conArray  = content.split(splitStr); 
       
       XWPFRun xr = para.createRun();
       
        for (String SingleLine : conArray) {
             xr.setText(SingleLine);  
             xr.addBreak(); 
        }

       return para;
    }
    
    
    public void writeParagraph(XWPFDocument document,String paragraph){
    
    XWPFRun run = document.createParagraph().createRun();
    run.setText(paragraph);
    //run.addBreak();
    }
    
   public XWPFParagraph paragraphWritter(XWPFParagraph para,String paragraph){
    
    XWPFRun run =para.createRun();
    run.setText(paragraph);
    //run.addBreak();
    return para;
    }
    

    
   public static void main(String[] args)throws Exception {
      // CCF_Report ccfRep = new CCF_Report(new int [8][],"");
      // ccfRep.writeReport();
               
    
   }
    
    
   
   
   /*
    public void writeHeaderLogo(XWPFDocument doc) throws IOException, InvalidFormatException{



   XWPFParagraph paragraph = paragraph = doc.createParagraph();
   XWPFRun run=paragraph.createRun();  
   run.setText("Lorem ipsum....");

  // create header start
  CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
  XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);

  XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

  paragraph = header.getParagraphArray(0);
  paragraph.setAlignment(ParagraphAlignment.LEFT);

  CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
  tabStop.setVal(STTabJc.RIGHT);
  int twipsPerInch =  1440;
  tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

  run = paragraph.createRun();  
  run.setText("The Header:");
  run.addTab();

  run = paragraph.createRun();  
  run.addPicture(new FileInputStream(logo), XWPFDocument.PICTURE_TYPE_PNG, logo, Units.toEMU(50), Units.toEMU(50));

/*
  // create footer start
  XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

  paragraph = footer.getParagraphArray(0);
  paragraph.setAlignment(ParagraphAlignment.CENTER);

  run = paragraph.createRun();  
  run.setText("The Footer:");


  doc.write(new FileOutputStream("test.docx"));*/
       
   /*}

   */
  
   /*
   
    public void writeLogo(XWPFDocument document){
   
   XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        Path imagePath;
        
        try {
            imagePath = Paths.get(getClass().getResource(logo).toURI());
            imageRun.addPicture(Files.newInputStream(imagePath),
            XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(),
            Units.toEMU(100), Units.toEMU(50));

            
        } catch (IOException ex) {
            Logger.getLogger(CCF_Report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(CCF_Report.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(CCF_Report.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   
   
   }
   
   
   
   
   
   */
   

    
    
}



       
   /* XWPFTable tableOne = document.createTable(2,2);
    tableOne.setWidth(8000);
    
    XWPFTableRow tablerow = tableOne.getRow(0);
    
    tablerow.getCell(0).setText("Test");
    

    tablerow = tableOne.getRow(1);
    tablerow.getCell(0).setText("Test");

    XWPFParagraph paragraph = tablerow.getCell(1).getParagraphArray(0);
    XWPFTable tableTwo = tablerow.getCell(1).insertNewTbl(paragraph.getCTP().newCursor());

    tableTwo.getCTTbl().addNewTblPr().addNewTblBorders().addNewLeft().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);
    tableTwo.getCTTbl().getTblPr().getTblBorders().addNewRight().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);
    tableTwo.getCTTbl().getTblPr().getTblBorders().addNewTop().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);
    tableTwo.getCTTbl().getTblPr().getTblBorders().addNewBottom().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);
    tableTwo.getCTTbl().getTblPr().getTblBorders().addNewInsideH().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);
    tableTwo.getCTTbl().getTblPr().getTblBorders().addNewInsideV().setVal(
     org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder.SINGLE);

    tablerow = tableTwo.createRow();
    tablerow.createCell().setText("aaaaaaaaaa");
    tablerow.createCell().setText("jjjjjjjj"); 
    tablerow = tableTwo.createRow(); 
    tablerow.getCell(0).setText("bbbbbbbbbb"); 
    tablerow.getCell(1).setText("gggggggggg");    
    */
        