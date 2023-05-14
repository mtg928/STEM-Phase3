/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.main;

import com.topfield.calculators.calcfile.CalcFile;
import com.topfield.dao.CalcfileDao;
import com.topfield.dao.FMEADao;
import com.topfield.dao.FMECADao;
import com.topfield.dao.FaultdataDao;
import com.topfield.dao.FaulttreeDao;
import com.topfield.dao.FunctionalfailuresDao;
import com.topfield.dao.GraphvertexDao;
import com.topfield.dao.StudentDao;
import com.topfield.dao.SubProductComponentsDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.dao.TclProjectsDao;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.DbOperations;
import com.topfield.daoImpl.FMEADaoImpl;
import com.topfield.daoImpl.FMECADaoImpl;
import com.topfield.daoImpl.MpgHdrDaoImpl;
import com.topfield.daoImpl.FunctionalfailuresDaoImpl;
import com.topfield.daoImpl.MainProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductComponentsDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.modal.Fmea;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.Fmeca;
import com.topfield.modal.Functionalfailures;
import com.topfield.modal.MainProductComponents;
import com.topfield.modal.Student;
import com.topfield.modal.SubProductComponents;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.topfield.dao.MpgHdrDao;
import com.topfield.dao.SpghdrDao;
import com.topfield.dao.StandardsDao;
import com.topfield.daoImpl.CalcfileDaoImpl;
import com.topfield.daoImpl.FaultdataDaoImpl;
import com.topfield.daoImpl.FaulttreeDaoImpl;
import com.topfield.daoImpl.GraphvertexDaoImpl;
import com.topfield.daoImpl.SpghdrDaoImpl;
import com.topfield.daoImpl.StandardsDaoImpl;
import com.topfield.graph.RBDCell;
import com.topfield.graph.RBDSettings;
import com.topfield.modal.CalcFunctions;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Calchdr;
import com.topfield.modal.Faultdata;
import com.topfield.modal.Spfhdr;
import com.topfield.modal.Spghdr;
import com.topfield.user.UserInfo;
import com.topfield.utilities.NumberConversion;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphConstants;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class TestClass {

    private FunctionalfailuresDao dao = new FunctionalfailuresDaoImpl();
    private MpgHdrDao fmeasDao = new MpgHdrDaoImpl();
    private FaulttreeDao faulttreeDao = new FaulttreeDaoImpl();
    private FaultdataDao faultdataDao = new FaultdataDaoImpl();
    
    private TclProjectsDao proDao = new TclProjectsDaoImpl();
    private StandardsDao stdDao = new StandardsDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    private SpghdrDao spghdrDao = new SpghdrDaoImpl();
    private GraphvertexDao graphDao= new GraphvertexDaoImpl();
    
    private CalcfileDao calcDao = new CalcfileDaoImpl();
    private MpgHdrDao mpgDao = new MpgHdrDaoImpl();

    public void testCalll() {

        //System.out.println("Calc File"+graphDao.isCMMUsedRef(0).toString());
        
       
       
      /* Mpghdr mpgHdr = mpgDao.getFMEASById(19);
       
        System.out.println("mpgHdr "+mpgHdr.getDescriptions());
     
        for (Calchdr calc : mpgHdr.getCalchdrCollection()) {
            System.out.println(calc.getCalchdrid()+" - "+calc.getCalname()+"-"+calc.getDescriptions());
        }
   
         for (CalcFunctions calc : mpgHdr.getCalcFunctionsCollection()) {
            System.out.println(calc.getCalcFunId()+" - "+calc.getCalcFunType()+"-"+calc.getCalcFunction());
            
             for (Spfhdr spfhdr : calc.getSpfhdrCollection()) {
                  System.out.println(spfhdr.getSpfhdrid()+" - "+spfhdr.getCalname()+"-"+spfhdr.getDescriptions());
             }
        }*/
      
      
        Calcfile calc = calcDao.findById(19);
      
        System.out.println(calc.getCalcHeader().getMpgheader().getMpghdrid()+" "+calc.getCompId()+" "+calc.getComponents());
        
        for (String res : graphDao.isCMMUsedRef(calc.getCalcId())) {
            System.out.println("CMM - "+res);
        }
        
        for (Fmea fmea : calc.getFmeaCollection()) {
             System.out.println(fmea.getFmeaHeader().getCalchdrid()+" "+fmea.getFmeaId()+" "+fmea.getFISubsysComponent());
        }
      
           
    }
    


    public static void main(String[] args) {

        TestClass t = new TestClass();
        t.testCalll();

    }
    
    
    public void testJson(){
    
      /*   Spghdr spghdr = new Spghdr();
        
        spghdr.setCalname("vjhbhj");
        spghdr.setProjectId(proDao.findById(3));
        spghdr.setStandard(stdDao.findById(1));
        spghdr.setUser(userDao.findById("bala"));
        spghdr.setCalname("RBD");
        spghdr.setSpgid(spgDao.getSPGById(5));
         spghdr.setCreateddate(new Date());
          
          spghdr.setDescriptions("GEN KAN Test");*/
          
           //String jsonDataString = "{userInfo : [{username:abc123}, {username:xyz123},{username:pqr123},   {username:mno123},{username:jkl123}]}";
          
           String jsonDataString ="{\n" +
                    "\n" +
                    "  \"vertex\": {\n" +
                    "    \"bgcolor\": \"BLUE\",\n" +
                    "	\"fontcolor\": \"GREEN\",\n" +
                    "	\"bordercolor\": \"CYAN\",\n" +
                    "  },\n" +
                    "  \n" +
                    "  \"line\": {\n" +
                    "    \"color\": \"BLUE\",\n" +
                    "    \"style\": \"Arrow\"                   \n" +
                    "  },\n" +
                    "\n" +
                    "  \"text\": {\n" +
                    "    \"color\": \"BLUE\",   \n" +
                    "	\"size\": 1,\n" +
                    "  },\n" +
                    "  \n" +
                    "  \"connector\": {\n" +
                    "    \"color\": \"BLUE\",   \n" +
                    "	\"border\": 1,\n" +
                    "  }\n" +
                    "}";
           //spghdr.setProsettings(new JSONObject(jsonDataString));
          
        //spghdrDao.save(spghdr);
        
           //RBDSettings.RBDcellSelector(new JSONObject(jsonDataString));

           //System.out.println("ccv - "+String.valueOf(Color.CYAN.getRGB()));
           //System.out.println("ccv - "+String.valueOf(new Color(Color.CYAN.getRGB()).getRGB()));
           
           
       JSONObject jo=    new JSONObject(jsonDataString);
       
        System.out.println(jo.getJSONObject("vertex").get("bgcolor"));
    
    
    
    }

}
