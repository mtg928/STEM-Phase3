/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.reports;

import com.topfield.utilities.FileSelector;
import com.topfield.utilities.ImageProcessor;
import com.topfield.utilities.WordExport;
import com.microsoft.schemas.vml.CTFormulas;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTShape;
import com.microsoft.schemas.vml.CTTextbox;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.sl.usermodel.GroupShape;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;
import org.w3c.dom.Node;


/**
 *
 * @author Murali
 */
public class HeatMapReport {
    
    public static String path="";
    public static String output = "C:\\Users\\Murali\\Desktop\\CCF_Report.docx";

    public HeatMapReport() {
        
         path =FileSelector.getPathSelected();
        output = path+"/"+"HeatMap_Report"+".docx";
        
    }
    
    public void writeReport(){
   
       FileOutputStream out;
       XWPFDocument document = new XWPFDocument();
       
       writeHeaderLogo(document);
       writeHeadings(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);   
       writeHeadingTable(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       writeProjectInfoTable(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       drawDialIndicator(document);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       writeSubHeadings(document,"",ParagraphAlignment.LEFT);
       barrierDetails(document);
       //appendCalloutShape(document, "1in", "-150px", "100px", "150px", "#0000FF", "yellow", "The callout\ntext...", true);
       
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


    }
       
    public void writeHeadings(XWPFDocument document){
    
      XWPFParagraph title = document.createParagraph();
      title.setSpacingAfter(0);
       title.setAlignment(ParagraphAlignment.CENTER);
       XWPFRun titleRun = title.createRun();
        titleRun.setText("HEAT MAP REPORT");
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
    
    public void writeHeadingTable(XWPFDocument document) {
      
     //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      
      XWPFTableRow tableRowOne = table.getRow(0);tableRowOne.setHeight(0);
      tableRowOne.getCell(0).setText("Final Position on 30.09.2018: All 3 barriers included in design");
      tableRowOne.addNewTableCell().setText("Current Position on 30.09.2018: 2 of the 3 barriers included in the design");
      tableRowOne.addNewTableCell().setText("Position on 30.04.2018: with no barriers included in design");
      //WordExport.mergeCellHorizontally(table, 0, 0, 3,0,WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9",text,"000000",ParagraphAlignment.LEFT));
      
      tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("A7BFDE");
      tableRowOne.getCell(1).getCTTc().addNewTcPr().addNewShd().setFill("A7BFDE");
      tableRowOne.getCell(2).getCTTc().addNewTcPr().addNewShd().setFill("A7BFDE");
    }
    
     public void writeProjectInfoTable(XWPFDocument document) {
      
     //create table
      XWPFTable table = document.createTable();
      table.setWidth(10000);
      
		
      //create first row
      XWPFTableRow tableRowOne = table.getRow(0);tableRowOne.setHeight(0);
      
      
        tableRowOne.addNewTableCell();
        tableRowOne.addNewTableCell();
      
      
        tableRowOne.getCell(0).setParagraph(WordExport.formatCell(tableRowOne.getCell(0),"5DADE2","O","1B4F72",ParagraphAlignment.CENTER));
        tableRowOne.getCell(1).setParagraph(WordExport.formatCell(tableRowOne.getCell(1),"5DADE2","O <--------------------","1B4F72",ParagraphAlignment.RIGHT));
        tableRowOne.getCell(2).setParagraph(WordExport.formatCell(tableRowOne.getCell(2),"5DADE2","-----------------------O","1B4F72",ParagraphAlignment.LEFT));
 
      
      tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("1ABC9C");
      tableRowOne.getCell(1).getCTTc().addNewTcPr().addNewShd().setFill("F1C40F");
      tableRowOne.getCell(2).getCTTc().addNewTcPr().addNewShd().setFill("CB4335");
      
      tableRowOne.getCell(0).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
      tableRowOne.getCell(1).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
      tableRowOne.getCell(2).getCTTc().addNewTcPr().addNewVAlign().setVal(STVerticalJc.CENTER);
      
      //WordExport.mergeCellHorizontally(table, 0, 0, 3,0,WordExport.formatCell(tableRowOne.getCell(0),"ABB2B9","STEMS TM","0000FF",ParagraphAlignment.CENTER));   
		     
      //create second row
      XWPFTableRow tableRowTwo = table.createRow(); 
      tableRowTwo.getCell(0).setParagraph(WordExport.formatCell(tableRowTwo.getCell(0),"ABB2B9","Following 3 Barriers installed and confirmed:","000000"));
      tableRowTwo.getCell(1).setParagraph(WordExport.formatCell(tableRowTwo.getCell(1),"ABB2B9","Following 2 barriers installed and confirmed","000000"));
      tableRowTwo.getCell(2).setParagraph(WordExport.formatCell(tableRowTwo.getCell(2),"ABB2B9","Following 1 barriers installed and confirmed","000000"));

		
      //create third row
      XWPFTableRow tableRowThree = table.createRow();
      tableRowThree.getCell(0).setText("1) Controller fitted inside metal enclosure");
      tableRowThree.getCell(1).setText("1) Controller fitted inside metal enclosure");
      tableRowThree.getCell(2).setText("1) Initial Position with no barriers included in design");

      
      //create third row
      XWPFTableRow tableRow4 = table.createRow();
      tableRow4.getCell(0).setText("2). Door enclosure is fitted with an alarm");
      tableRow4.getCell(1).setText("2). Door enclosure is fitted with an alarm");
      tableRow4.getCell(2).setText("");
      
      
      XWPFTableRow tableRow6 = table.createRow();
      tableRow6.getCell(0).setText("3). Door failure message sent to the Operations centre (OCC)");
      tableRow6.getCell(1).setText("3). Outstanding");
      tableRow6.getCell(2).setText("");

	  
     
	  
	

    }
     
    public void drawDialIndicator(XWPFDocument document){
       Path imagePath;


        // header's first paragraph
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        ImageIcon logo1= new ImageIcon(getClass().getResource("/coachspecs/images/BarriersPosition.png"));

        try {
            // imagePath = Paths.get(getClass().getResource(logo).toURI());
             run.addPicture(ImageProcessor.ImageIconToInputStream(logo1.getImage()), Document.PICTURE_TYPE_PNG, "topfield_icon.png", Units.toEMU(280), Units.toEMU(150));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CCF_Report_Pro.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    } 
    
    
    
      public void barrierDetails(XWPFDocument document){
      
         CTGroup ctGroup = CTGroup.Factory.newInstance();
         CTShape ctShape = ctGroup.addNewShape();
         //ctShape.setPath2("m,l,216r216,l216,xe");
         //ctShape.setStyle("-fx-control-inner-background:#2874A6");
         ctShape.setFillcolor("#2874A6");
         //ctShape.addNewFill().setColor("2874A6"); 
         ctShape.setStrokecolor("yellow");
         ctShape.setStyle("width:300pt;height:100pt");
         
        
         

         XWPFHeader header = document.createHeaderFooterPolicy().createHeader(XWPFHeaderFooterPolicy.DEFAULT);
         
         XWPFParagraph paragraph = document.createParagraph();
         XWPFRun run = paragraph.createRun();
         /*paragraph = header.getParagraphArray(0);
         if (paragraph == null) paragraph = header.createParagraph();
          paragraph.setAlignment(ParagraphAlignment.LEFT);*/
         
         
         CTTxbxContent ctTxbxContent = ctShape.addNewTextbox().addNewTxbxContent();
         XWPFParagraph textboxparagraph = new XWPFParagraph(ctTxbxContent.addNewP(),run.getDocument());
         paragraph.setAlignment(ParagraphAlignment.CENTER);
         XWPFRun textboxrun = textboxparagraph.createRun();
         textboxrun = textboxparagraph.createRun();
         textboxrun.setText("Barrier A: Controller in metal enclosure");
         textboxrun.addBreak();
         textboxrun.setText("Barrier B: Door enclosure is Alarmed");
         textboxrun.addBreak();
         textboxrun.setText("Barrier C: Door failure signal sent to OCC ");
         textboxrun.setFontSize(15);
         textboxrun.setColor("2E86C1");
         
         
         Node ctGroupNode = ctGroup.getDomNode(); 
         CTPicture ctPicture;
        try {
            ctPicture = CTPicture.Factory.parse(ctGroupNode);
                CTR cTR = run.getCTR();
             cTR.addNewPict();
             cTR.setPictArray(0, ctPicture);
        } catch (XmlException ex) {
            Logger.getLogger(HeatMapReport.class.getName()).log(Level.SEVERE, null, ex);
        }

      
      }
    
    public static void appendCalloutShape(XWPFDocument document, String left, String top, String width, String height, 
                                       String strokecolor, String fillcolor, String calloutext, boolean hashandles) {
        
        XWPFRun run = document.createParagraph().createRun();
        
        CTGroup ctGroup = CTGroup.Factory.newInstance();

        CTShape ctShape = ctGroup.addNewShape();
        ctShape.setCoordsize("21600,21600");
        if (hashandles) { //is not Libreoffice Writer compatible
         ctShape.setAdj("" + 21600*1/3 + ",21600");
         CTFormulas cTFormulas = ctShape.addNewFormulas();
         cTFormulas.addNewF().setEqn("val #0");
         cTFormulas.addNewF().setEqn("val #1");
         //ctShape.setPath2("m 1,1 l 1," + 21600*2/3 + ", " + 21600*1/3 + "," + 21600*2/3 + ", @0,@1, " + 21600*2/3 + "," + 21600*2/3 + ", 21600," + 21600*2/3 + ", 21600,1 x e");
         //ctShape.setPath2("m,l,21600r21600,l21600,xe");
         ctShape.addNewHandles().addNewH().setPosition("#0,#1");
        } else { // is Libreoffice Writer compatible
         //ctShape.setPath2("m 1,1 l 1," + 21600*2/3 + ", " + 21600*1/3 + "," + 21600*2/3 + ", " + 21600*1/3 + ",21600, " + 21600*2/3 + "," + 21600*2/3 + ", 21600," + 21600*2/3 + ", 21600,1 x e");
        }

        ctShape.setStyle("width:270pt;height:75pt");
        //ctShape.addNewPath().setTextboxrect("1,1,21600," + 21600*2/3);

        ctShape.setStyle("position:absolute;margin-left:" + left + ";margin-top:" + top + ";width:" + width + ";height:" + height + ";z-index:251659264;visibility:visible;rotation:0;");
        ctShape.setStrokecolor(strokecolor);
        ctShape.setFillcolor(fillcolor);

        CTTextbox cTTextbox = ctShape.addNewTextbox();

        CTTxbxContent ctTxbxContent = cTTextbox.addNewTxbxContent();
        XWPFParagraph textboxparagraph = new XWPFParagraph(ctTxbxContent.addNewP(), run.getDocument());
        XWPFRun textboxrun = null;
        String[] lines = calloutext.split("\n");
        for (int i = 0; i < lines.length; i++) {
         textboxrun = textboxparagraph.createRun();
         textboxrun.setText(lines[i]);
         textboxrun.addBreak();
        }

        Node ctGroupNode = ctGroup.getDomNode(); 
        CTPicture ctPicture;
        try {
            ctPicture = CTPicture.Factory.parse(ctGroupNode);
            CTR cTR = run.getCTR();
            cTR.addNewPict();
            cTR.setPictArray(0, ctPicture);
        } catch (XmlException ex) {
            Logger.getLogger(HeatMapReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 }

          
    
    
     public static void main(String[] args)throws Exception {
     HeatMapReport hm = new HeatMapReport();
     hm.writeReport();
     
    
   }
    
    
}
