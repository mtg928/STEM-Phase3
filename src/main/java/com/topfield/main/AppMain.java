package com.topfield.main;

import com.topfield.settings.GraphicsProperties;
import static com.topfield.settings.GraphicsProperties.path;
import java.io.File;
import java.io.IOException;


 
public class AppMain {


	public static void main(String[] args) {
            
           // logger.info("\n=======CREATE RECORDS=======\n");
	  //	DbOperations.createRecord();
                
          
         // MainProductGroupDaoImpl db = new MainProductGroupDaoImpl();
      /*      List<MainProductGroup>viewStudents = db.displayRecords();
		if(viewStudents != null & viewStudents.size() > 0) {
			for(MainProductGroup studentObj : viewStudents) {
                            System.out.println("Hi"+studentObj.toString());
				
			}
		}
		System.exit(0);*/
      
            //System.out.println("System.getProperty(\"user.home\")"+System.getProperty("user.home")+"\\AppData\\STEMS");
      
           //new UserLogin().setVisible(true);
           /* javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        InternalFrameDemo.createAndShowGUI();
                    }
                });*/
          
         File file = new File(System.getProperty("user.home")+"\\AppData\\STEMS\\");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            new UserLogin().setVisible(true);
            
            
            
            
            try {
                File myObj = new File(GraphicsProperties.path+"config.properties");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
            
        }
        
        
        
        
}


/*
logger.info(".......Hibernate Crud Operations Example.......\n");

		logger.info("\n=======CREATE RECORDS=======\n");
		DbOperations.createRecord();

		logger.info("\n=======READ RECORDS=======\n");
		List<Student>viewStudents = DbOperations.displayRecords();
		if(viewStudents != null & viewStudents.size() > 0) {
			for(Student studentObj : viewStudents) {
				logger.info(studentObj.toString());
			}
		}

		logger.info("\n=======UPDATE RECORDS=======\n");
		int updateId = 1;
		DbOperations.updateRecord(updateId);
		logger.info("\n=======READ RECORDS AFTER UPDATION=======\n");
		List<Student> updateStudent = DbOperations.displayRecords();
		if(updateStudent != null & updateStudent.size() > 0) {
			for(Student studentObj : updateStudent) {
				logger.info(studentObj.toString());
			}
		}

		logger.info("\n=======DELETE RECORD=======\n");
		int deleteId = 5;
		DbOperations.deleteRecord(deleteId);
		logger.info("\n=======READ RECORDS AFTER DELETION=======\n");
		List<Student> deleteStudentRecord = DbOperations.displayRecords();
		for(Student studentObj : deleteStudentRecord) {
			logger.info(studentObj.toString());
		}

		logger.info("\n=======DELETE ALL RECORDS=======\n");
		DbOperations.deleteAllRecords();
		logger.info("\n=======READ RECORDS AFTER ALL RECORDS DELETION=======");
		List<Student> deleteAll = DbOperations.displayRecords();
		if(deleteAll.size() == 0) {
			logger.info("\nNo Records Are Present In The Database Table!\n");
		}		
		System.exit(0);

*/