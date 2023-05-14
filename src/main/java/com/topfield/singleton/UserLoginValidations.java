/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

import com.topfield.controller.RestController;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.UserDaoImpl;
import com.topfield.modal.TclProjects;
import com.topfield.user.HybridData;
import com.topfield.user.UserInfo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
public class UserLoginValidations {

    public static final String customerAPIUrl = "https://www.topfieldconsultancy.co.uk/db-service/authenticate";
    private static UserDao userDao = new UserDaoImpl();

    public static String doValidation(String username, String password) {
        String res = checkInternetConnectvity();

        if (!res.equals("")) {
            return res;
        }

        res = getUserAuthentication(username, password);

        if (!res.equals("")) {
            return res;
        }

        setUserLevelData(username);

        if (UserInfo.getInstance().getuser().getEnabled() == 0) {
            res = "<html> LG0401005 Your Account is blocked by Admin. <br> Please contact Topfield IT at stemenquiries@topfieldconsultancy.co.uk </html>";
        } else if (!(userDao.checkedValidLicense(UserInfo.getInstance().getuser().getLicensestartdate(), UserInfo.getInstance().getuser().getLicenseEnddate()))) {
            res = "<html> LG0401003 Your license expired on " + UserInfo.getInstance().getuser().getLicenseEnddate() + "</html>";
        }

        return res;
    }

    public static String checkInternetConnectvity() {
        String res = " LG0401004 Internet is not connected. Please check your Internet connection.";

        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            res = "";
            System.out.println("Internet is connected");
        } catch (MalformedURLException e) {
            System.out.println("Internet is not connected");
        } catch (IOException e) {
            System.out.println("Internet is not connected");
        }

        return res;
    }

    public static String getUserAuthentication(String username, String password) {
        String massage = "";

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);

        JSONObject jwt = RestController.getSingleJsonRes(customerAPIUrl, json, null);

        if (jwt == null) {
            massage = "<html>LG0401001 Login Error. Contact IT Helpdesk at stemenquiries@topfieldconsultancy.co.uk</html>";
        } else if (jwt.getString("jwt").equals("403")) {
            massage = "<html>LG0401002 Invalid username or password</html>";
        } else {
            dbSetter(jwt);
        }

        return massage;
    }

    public static void dbSetter(JSONObject jwt) {
        String customerAPIUrl2 = "https://www.topfieldconsultancy.co.uk/db-service/welcome";
        JSONObject data = RestController.getSingleJsonRes(customerAPIUrl2, new JSONObject(), jwt.getString("jwt"));

        HybridData.setUsername(data.getString("username"));
        HybridData.setPassword(data.getString("password"));
        HybridData.setUrl(data.getString("url"));
    }

    public static void setUserLevelData(String username) {

        //Users user = userDao.findById(username);

        /*if(user.getUsername() == null ||user.getUsername().equals("")){
                  massage ="<html>Invalid username ...!!</html>";
                }else if(!(password.equals(user.getPassword()))){
                   massage ="<html>Invalid password...!!</html>";
                }else if(user.getEnabled()==0){
                  massage ="<html>Your Account is blocked by Admin. Please contact Topfield IT...!!</html>";
                }else if(!(userDao.checkedValidLicense(user.getLicensestartdate(),user.getLicenseEnddate()))){
                 massage ="<html>Your licenese is expired on "+user.getLicenseEnddate()+"</html>";
                }*/
        try {

            UserInfo.getInstance().setAllUserInfoData(username);

        } catch (NullPointerException eee) {

            TclProjects noPro = new TclProjects();
            noPro.setProId(-1);
            noPro.setProName(" No Pjoject Yet");
            UserInfo.getInstance().setProject(noPro);

            eee.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Internet is connected - " + checkInternetConnectvity());
    }

}
