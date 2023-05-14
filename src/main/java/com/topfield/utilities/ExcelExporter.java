/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;


import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.main.InternalFrameDemo;
import com.topfield.singleton.FileSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Murali
 */
public class ExcelExporter {
    
    private  Object[][] datatypes;
    private  String FILE_NAME = "G:\\";
    

    public ExcelExporter() {
        //this.datatypes = datatypes;
    }

    
    public void WriteExcel(String fileName,Object[][] data){
        
 
       List<String> path = FileSelection.getSelectionPath2(new int[]{1});
        
        
        if (path.size()>1) {
       
            FILE_NAME =path.get(0)+"."+path.get(1);
        
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(fileName);



             int rowNum = 0;
             System.out.println("Creating excel");

             for (Object[] datatype : data) {
                 Row row = sheet.createRow(rowNum++);
                 int colNum = 0;
                 for (Object field : datatype) {
                     Cell cell = row.createCell(colNum++);
                     if (field instanceof String) {
                         cell.setCellValue((String) field);
                     } else if (field instanceof Integer) {
                         cell.setCellValue((Integer) field);
                     }
                     
                 }
             }

             try {
                 FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
                 workbook.write(outputStream);
                 workbook.cloneSheet(0);
                 if(path.equals("")){
                 }else{
                 JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+FILE_NAME);
                 workbook.close();
                 outputStream.close();
                 }
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
             } catch (IOException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
             }


             System.out.println("Done");
             
        }  
    }
    
     
    public void WriteExcel2(String fileName,Object[][] data){
        
 
       List<String> path = FileSelection.getSelectionPath2(new int[]{1});
        
        
        if (path.size()>1) {
       
            FILE_NAME =path.get(0)+"."+path.get(1);
        
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(fileName);



             int rowNum = 0;
             System.out.println("Creating excel");

             for (Object[] datatype : data) {
                 Row row = sheet.createRow(rowNum++);
                 int colNum = 0;
                 for (Object field : datatype) {
                     Cell cell = row.createCell(colNum++);
                     if (field instanceof String) {
                         cell.setCellValue((String) field);
                     } else if (field instanceof Integer) {
                         cell.setCellValue((Integer) field);
                     }
                     
                     cell.setCellStyle(getCellStyle(workbook,(String)datatype[datatype.length-1],rowNum,data.length));
                 }
             }

             try {
                 FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
                 workbook.write(outputStream);
                 workbook.cloneSheet(0);
                 if(path.equals("")){
                 }else{
                 JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+FILE_NAME);
                 workbook.close();
                 outputStream.close();
                 }
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
             } catch (IOException e) {
                 e.printStackTrace();
                 JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
             }


             System.out.println("Done");
             
        }  
    }
    
    
    
    
    
    public void WriteExcel(String fileName,Object[][] data,int[] marge){
        
       List<String> path = FileSelection.getSelectionPath2(new int[]{1});
        
        
       if (path.size()>1) {
       
            FILE_NAME =path.get(0)+"."+path.get(1);
        

        
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet(fileName);



                     int rowNum = 0;
                     System.out.println("Creating excel");

                     for (Object[] datatype : data) {
                         Row row = sheet.createRow(rowNum++);
                         int colNum = 0;
                         for (Object field : datatype) {
                             Cell cell= null;
                             if (colNum < datatype.length-2 ) {
                                  cell = row.createCell(colNum++);
                                 if (field instanceof String) {
                                     cell.setCellValue((String) field);
                                 } else if (field instanceof Integer) {
                                     cell.setCellValue((Integer) field);
                                 }
                                 cell.setCellStyle(getCellStyle(workbook,(String)datatype[datatype.length-2],rowNum,data.length));
                             }

                         }
                     }

                     try {



                             sheet.addMergedRegion(new CellRangeAddress(0,0,1,(marge[0]-1))); 
                             sheet.addMergedRegion(new CellRangeAddress(0,0,marge[0],(marge[1]-1))); 
                             sheet.addMergedRegion(new CellRangeAddress(0,0,(marge[1]),marge[3])); 
                             //sheet.addMergedRegion(new CellRangeAddress(0,0,(marge[2]),data[0].length)); 
                             //sheet.addMergedRegion(new CellRangeAddress(0,0,marge[2],data[0].length-2)); 

                         /*sheet.addMergedRegion(new CellRangeAddress(0,0,1,6)); 
                         sheet.addMergedRegion(new CellRangeAddress(0,0,7,9));  
                         sheet.addMergedRegion(new CellRangeAddress(0,0,10,14));  
                         sheet.addMergedRegion(new CellRangeAddress(0,0,15,16));  */

                         /*sheet.addMergedRegion(new CellRangeAddress(0,0,1,7)); 
                         sheet.addMergedRegion(new CellRangeAddress(0,0,8,10));  
                         sheet.addMergedRegion(new CellRangeAddress(0,0,11,19));  
                         sheet.addMergedRegion(new CellRangeAddress(0,0,20,26));*/

                         FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
                         workbook.write(outputStream);
                         workbook.cloneSheet(0);
                         if(path.equals("")){
                         }else{
                         JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+FILE_NAME);
                         workbook.close();
                         outputStream.close();
                         }
                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                         JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
                     } catch (IOException e) {
                         e.printStackTrace();
                         JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
                     }


                     System.out.println("Done");
                     
       }         
    }
    
    
    public CellStyle getCellStyle(XSSFWorkbook wb,String colType,int row,int rowCount){
       CellStyle style = wb.createCellStyle();  
       Font font = wb.createFont();

        System.out.println("colType - "+colType);
        int type =FMEAServices.getSystemLevelsNew(colType);

        if (row ==1) {
            style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            style.setFont(font);
            
            setBorder(style, BorderStyle.THIN, IndexedColors.WHITE.getIndex(),0);
            
            
        } else {

                // Setting Background color
                switch (type) {
                    case 0:
                        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
                        font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                        style.setFont(font);
                        setBorder(style, BorderStyle.THIN, IndexedColors.WHITE.getIndex(),0);
                        break;
                    case 1:
                        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                        break;
                    case 2:
                        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        break;
                    case 3:
                        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        break;
                    case 4:
                        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                        break;
                    default:
                        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                        break;
                }
                
                if (type >=1) {
                   setBorder(style, BorderStyle.THIN, IndexedColors.BLACK.getIndex(),rowCount >=row ? 3: 2);
                } 
         }    
           
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  

    return style;
    }
    
    public void setBorder(CellStyle style,BorderStyle borderStyle,short indexColor,int place){
    
        if (place==0 || place==1) {
            style.setBorderTop(borderStyle);  
            style.setTopBorderColor(indexColor); 
        }
 
        if (place==0 || place==2 || place==3) {
            style.setBorderLeft(borderStyle);  
            style.setLeftBorderColor(indexColor); 
        }
        
        if (place==0 || place==2 || place==3) {
            style.setBorderRight(borderStyle);  
            style.setRightBorderColor(indexColor);
        }
        
        if (place==0 || place==3) {
            style.setBorderBottom(borderStyle);  
            style.setBottomBorderColor(indexColor); 
        }

 

    
    
    }
    
    
    
    
    public void WriteExcel(String fileName,Object[] head,Object[][] data){
        
      List<String> path = FileSelection.getSelectionPath2(new int[]{1});
        
        
       if (path.size()>1) {
       
            FILE_NAME =path.get(0)+"."+path.get(1);
        
       XSSFWorkbook workbook = new XSSFWorkbook();
       XSSFSheet sheet = workbook.createSheet(fileName);
        


        int rowNum = 0;
        System.out.println("Creating excel");

       
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : head) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        
        
        for (Object[] datatype : data) {
             row = sheet.createRow(rowNum++);
             colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.cloneSheet(0);
            if(path.equals("")){
            }else{
            JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+FILE_NAME);
            workbook.close();
            outputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed :-"+e.getMessage());
        }

        
        System.out.println("Done");
       }
        
    }
    
    
    
    public static void main(String[] args) {
                Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
        };
        
               // FILE_NAME = FileSelector.getPathSelected();
            //   System.out.println("bhjghjg "+FileSelector.getPathSelected());
                
       ExcelExporter e = new ExcelExporter();
        e.WriteExcel("MyFirstExcel",datatypes);
    }
    
}
