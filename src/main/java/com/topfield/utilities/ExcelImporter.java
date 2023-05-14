/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Murali
 */
public class ExcelImporter {
    private  Object[][] datatypes;  //RN9a-FMECA
    private static final String FILE_NAME = "C:\\Users\\Murali\\Desktop\\Doors1.xlsx";
    
    public ExcelImporter() {
        //this.datatypes = datatypes;
    }

        
    public Object[][] ReadExcelNew(String fileName){
        
        //Object[][] data = new Object[20][20];
        ListToArray l = new ListToArray();
        List<List<String>> listOfLists = new ArrayList<List<String>>();
        List<String> list = new ArrayList<String>();
        FileInputStream excelFile;
        
        //int rowCount=0;
        //int colCount =0;
        
       try {

            excelFile = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
            
            
            
            DataFormatter fmt = new DataFormatter();

            //for(int sn=0; sn<workbook.getNumberOfSheets(); sn++) {
              // Sheet sheet = workbook.getSheetAt(sn);
              Sheet datatypeSheet = workbook.getSheetAt(0);
              
               for (int rn=datatypeSheet.getFirstRowNum(); rn<=datatypeSheet.getLastRowNum(); rn++) {
                  Row row = datatypeSheet.getRow(rn);
                  list = new ArrayList<String>();
                  if (row == null) {
                     // There is no data in this row, handle as needed
                  } else {
                     // Row "rn" has data
                     for (int cn=0; cn<row.getLastCellNum(); cn++) {
                        Cell cell = row.getCell(cn);
                        if (cell == null) {
                          list.add("");
                        } else {
                           if (cell.getCellType()== CellType.STRING) {
                                list.add(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                list.add(cell.getNumericCellValue()+"");
                            }
                        }
                        
                     }
                  }
                  listOfLists.add(list);
               }
               
            //}
            
            excelFile.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
          
       }
     
        
        System.out.println("Done");
        
        return l.twoDListToTwoDArray(listOfLists);
    }
    
    
    public Object[][] ReadExcel(String fileName){
        
        //Object[][] data = new Object[20][20];
        ListToArray l = new ListToArray();
        List<List<String>> listOfLists = new ArrayList<List<String>>();
        List<String> list = new ArrayList<String>();
        FileInputStream excelFile;
        
        //int rowCount=0;
        //int colCount =0;
        
       try {

            excelFile = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {
            
            
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                list = new ArrayList<String>();
                while (cellIterator.hasNext()) {
                    
                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                   // if (currentCell.getCellType()== HSSFCell.CELL_TYPE_STRING) {
                   if (currentCell.getCellType()== CellType.STRING) {
                        //data[rowCount][colCount]=currentCell.getStringCellValue();
                        //System.out.print(rowCount+"."+colCount+") "+currentCell.getStringCellValue() + "--");
                        list.add(currentCell.getStringCellValue());
                        //colCount++;
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        //data[rowCount][colCount]=currentCell.getNumericCellValue()+"";
                        //System.out.print(rowCount+"."+colCount+") "+currentCell.getNumericCellValue() + "--");
                        list.add(currentCell.getNumericCellValue()+"");
                        //colCount++;
                    }

                }
                
                 //colCount=0;
                 //rowCount++;
                 listOfLists.add(list);
                //System.out.println();
                  excelFile.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
          
       }
     
        
        System.out.println("Done");
        
        return l.twoDListToTwoDArray(listOfLists);
    }
    
    
    
    
    
    
    
    public static void main(String[] args) {
    
       ExcelImporter e = new ExcelImporter();
       ListToArray l = new ListToArray();
       // e.ReadExcel(FILE_NAME);
      for (Object[] arg : e.ReadExcelNew(FILE_NAME)) {
            for (Object object : arg) {
                
                try {
                    if(!(object.equals(""))){
                      System.out.print(""+object+" ---- ");
                    }
                } catch (NullPointerException e1) {
                }
                
                
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------");
        }
    }
    
    


    
    
}
