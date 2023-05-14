/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

/**
 *
 * @author Murali
 */
import com.healthmarketscience.jackcess.DatabaseBuilder;
import java.util.Date;
import java.util.ArrayDeque;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import com.healthmarketscience.jackcess.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class AccessReader {   
    
    public void CreateDB(){
    
      Database db = null;
        TableBuilder mytabbuild = null;
        Table mytable = null;
       
        try
        {
            db = DatabaseBuilder.create(Database.FileFormat.V2000, new File("mytest.mdb"));
       
            mytabbuild = new TableBuilder("myfuntable");
            mytabbuild.addColumn(new ColumnBuilder("ID", DataType.LONG).setAutoNumber(true));
            mytabbuild.addColumn(new ColumnBuilder("Name", DataType.TEXT));
            mytabbuild.addColumn(new ColumnBuilder("Salary", DataType.MONEY));
            mytabbuild.addColumn(new ColumnBuilder("StartDate", DataType.SHORT_DATE_TIME));
       
            mytabbuild.addIndex(new IndexBuilder(IndexBuilder.PRIMARY_KEY_NAME).addColumns("ID").setPrimaryKey());
            mytabbuild.addIndex(new IndexBuilder("NameIndex").addColumns("Name"));
       
            mytable = mytabbuild.toTable(db);
           
            Date mydate = new Date();
            mytable.addRow(Column.AUTO_NUMBER, "myname", "2.34", mydate);
        }
        catch (IOException e)
        {
            System.out.println("ERROR: problem building access db and table!");
            e.printStackTrace();
        }
        System.out.println("successfully created access db");
       
    
    
    
    }
    
    
    
    public Object[][] readsData(String FilePath,String tableName){
    
        Object [][] data = null;
        
        
        Table table;
        try {
            
            table = DatabaseBuilder.open(new File(FilePath)).getTable(tableName);
            data = new Object[table.getRowCount()][table.getColumnCount()];
            
            int count=0;
            for(Row row : table) {
                //System.out.println("Column 'a' has value: " + row.get("Type"));
                
                /*data[count] =new Object[]{count+1,row.get("Type"), row.get("Name"), row.get("DataRef"), row.get("Description"), row.get("Frequency"), row.get("Probability"), 
                                          row.get("Repair Time / PTI"), row.get("Data Type"), row.get("Distribution"), row.get("Spread"), row.get("Page")};*/
                
                
                data[count] =new Object[]{row.get("Type").equals("Gate") ? "Event" :row.get("Type") , row.get("Name"), row.get("Description"), row.get("Frequency"), row.get("Probability"), 
                                          row.get("Repair Time / PTI"), row.get("Page").equals("T")? "A" : (row.get("Data Type").equals("-") ? "X" :row.get("Data Type")), row.get("Distribution"), row.get("DataRef")};
                
                count++;
             }
            
        } catch (IOException ex) {
            Logger.getLogger(AccessReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException e){
        
            JOptionPane.showMessageDialog(null, "Please give the correct table name");
        
        }
        
        
       return data;
       
    }
    
    
    public static void main(String[] args)
    {
      
        AccessReader ar = new AccessReader();
        ar.readsData("G:/Work Documents/Rev A/logan files/FTA for PFD of EB CCU SIL0 27.01.17.mdb","Table1");
    }
    
    
    
    
}//class ClassCodeShim
