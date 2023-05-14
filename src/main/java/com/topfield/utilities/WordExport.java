/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

/**
 *
 * @author Murali
 */
public class WordExport {
    
 public static void mergeCellVertically(XWPFTable table, int col, int fromRow, int toRow) {
  for(int rowIndex = fromRow; rowIndex <= toRow; rowIndex++){
   XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
   CTVMerge vmerge = CTVMerge.Factory.newInstance();
   if(rowIndex == fromRow){
    // The first merged cell is set with RESTART merge value
    vmerge.setVal(STMerge.RESTART);
   } else {
    // Cells which join (merge) the first one, are set with CONTINUE
    vmerge.setVal(STMerge.CONTINUE);
    // and the content should be removed
    for (int i = cell.getParagraphs().size(); i > 0; i--) {
     cell.removeParagraph(0);
    }
    cell.addParagraph();
   }
   // Try getting the TcPr. Not simply setting an new one every time.
   CTTcPr tcPr = cell.getCTTc().getTcPr();
   if (tcPr != null) {
    tcPr.setVMerge(vmerge);
   } else {
    // only set an new TcPr if there is not one already
    tcPr = CTTcPr.Factory.newInstance();
    tcPr.setVMerge(vmerge);
    cell.getCTTc().setTcPr(tcPr);
   }
  }
 }

 public static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol,String margeCellText,ParagraphAlignment align,int textCell,boolean bold) {
   
     XWPFTableCell merge;
     
  for(int colIndex = fromCol; colIndex <= toCol; colIndex++){
   XWPFTableCell cell = table.getRow(row).getCell(colIndex);
   CTHMerge hmerge = CTHMerge.Factory.newInstance();
   if(colIndex == fromCol){
    // The first merged cell is set with RESTART merge value
    hmerge.setVal(STMerge.RESTART);
   } else {
    // Cells which join (merge) the first one, are set with CONTINUE
    hmerge.setVal(STMerge.CONTINUE);
    // and the content should be removed
    for (int i = cell.getParagraphs().size(); i > 0; i--) {
     cell.removeParagraph(0);
    }
    cell.addParagraph();
   }
   // Try getting the TcPr. Not simply setting an new one every time.
   CTTcPr tcPr = cell.getCTTc().getTcPr();
   if (tcPr != null) {
    tcPr.setHMerge(hmerge);
   } else {
    // only set an new TcPr if there is not one already
    tcPr = CTTcPr.Factory.newInstance();
    tcPr.setHMerge(hmerge);
    cell.getCTTc().setTcPr(tcPr);
   }
  }
  
  merge = table.getRow(row).getCell(textCell);
  XWPFParagraph p1 = merge.getParagraphs().get(0);
  p1.setSpacingAfter(0);
  p1.setAlignment(align);
  
  XWPFRun xr = p1.createRun(); 
  xr.setText(margeCellText);
  xr.setBold(bold);
  xr.setColor("154360");
  merge.setParagraph(p1);
  
 }
 
 public static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol,int textCell,XWPFParagraph p1) {
   
     XWPFTableCell merge;
     
  for(int colIndex = fromCol; colIndex <= toCol; colIndex++){
   XWPFTableCell cell = table.getRow(row).getCell(colIndex);
   CTHMerge hmerge = CTHMerge.Factory.newInstance();
   if(colIndex == fromCol){
    // The first merged cell is set with RESTART merge value
    hmerge.setVal(STMerge.RESTART);
   } else {
    // Cells which join (merge) the first one, are set with CONTINUE
    hmerge.setVal(STMerge.CONTINUE);
    // and the content should be removed
    for (int i = cell.getParagraphs().size(); i > 0; i--) {
     cell.removeParagraph(0);
    }
    cell.addParagraph();
   }
   // Try getting the TcPr. Not simply setting an new one every time.
   CTTcPr tcPr = cell.getCTTc().getTcPr();
   if (tcPr != null) {
    tcPr.setHMerge(hmerge);
   } else {
    // only set an new TcPr if there is not one already
    tcPr = CTTcPr.Factory.newInstance();
    tcPr.setHMerge(hmerge);
    cell.getCTTc().setTcPr(tcPr);
   }
  }
  
  merge = table.getRow(row).getCell(textCell);
  merge.setParagraph(p1);
  
 }

 
 public static XWPFParagraph boldText(XWPFTableCell cell,String text){
 
  
  XWPFParagraph p1 = cell.getParagraphs().get(0);
  p1.setSpacingAfter(0);
  XWPFRun xr = p1.createRun(); 
  xr.setBold(true);
  xr.setText(text);
  cell.setParagraph(p1);
  return p1;
 
 } 

   public static XWPFParagraph formatCell(XWPFTableCell cell,String cellColor,String text,String textColor){
 
  
  XWPFParagraph p1 = cell.getParagraphs().get(0);
  p1.setSpacingAfter(0);
  XWPFRun xr = p1.createRun(); 
  xr.setBold(true);
  xr.setColor(textColor);
  xr.setText(text);
  cell.setParagraph(p1);
  cell.setColor(cellColor);
  return p1;
 
 }
 
 
  public static XWPFParagraph formatCell(XWPFTableCell cell,String cellColor,String text,String textColor,ParagraphAlignment align){
 
  
  XWPFParagraph p1 = cell.getParagraphs().get(0);
  p1.setSpacingAfter(0);
  p1.setAlignment(align);
  XWPFRun xr = p1.createRun(); 
  xr.setBold(true);
  xr.setColor(textColor);
  xr.setText(text);
  cell.setParagraph(p1);
  cell.setColor(cellColor);
  return p1;
 
 }

  public static XWPFParagraph formatCell(XWPFTableCell cell,String text){
 
  
  XWPFParagraph p1 = cell.getParagraphs().get(0);
  p1.setSpacingAfter(0);

  XWPFRun xr = p1.createRun(); 
  
            if (text.contains("\n")) {
                String[] lines = text.split("\n");
                xr.setText(lines[0], 0); // set first line into XWPFRun
                //xr.addBreak();
                for(int i=1;i<lines.length;i++){
                    // add break and insert new text
                    //xr.setColor("0000FF");
                    xr.addBreak();
                    xr.setText(lines[i]);
                }
            } else {
                xr.setText(text, 0);
            }

  
  cell.setParagraph(p1);

  return p1;
 
 }
  
 public static XWPFParagraph formatCellQueAns(XWPFTableCell cell,String que,String ans){
 
  
  XWPFParagraph p1 = cell.getParagraphs().get(0);
  p1.setSpacingAfter(0);

  XWPFRun xr = p1.createRun(); 
  xr.setText(que, 0);
  xr.addBreak();
  
  XWPFRun xr2 = p1.createRun(); 
  xr2.setText(ans, 0);
  xr2.setColor("2980B9");
  

  
  cell.setParagraph(p1);

  return p1;
 
 }
  
  
  
}
