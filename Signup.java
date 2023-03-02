package com.example.ecomm;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.ResultSet;

public class signup {


    public static boolean validateEmail(String email){
        databaseConnection dbCon = new databaseConnection();
        String verifyLogin =" SELECT count(1) from customer WHERE email = '"+email+"'";
        ResultSet rs = dbCon.getQueryTable(verifyLogin);
        try{
            while(rs.next()){
                if(rs.getInt(1)==0){
                    return true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
