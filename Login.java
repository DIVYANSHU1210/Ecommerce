package com.example.ecomm;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public  class Login {

    private static byte[] getSha(String input){  //encryption
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8)); //UTF-8 is a character encoding system. It lets you represent characters as ASCII text
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getEncryptedPassword(String password){
        try{
            BigInteger num = new BigInteger(1, getSha(password));
            StringBuilder hexString = new StringBuilder(num.toString(16));
            return hexString.toString();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean signUp(String email, String name, String add, String pass, String mnum) {
        String hashPassword = getEncryptedPassword(pass);
        String s = "Insert into customer(name,email,address,password, mobile) values('"+name+"','"+email+"','"+add+"','"+hashPassword+ "','"+ mnum +"')";
        try{
            databaseConnection dbConn = new databaseConnection();
            return dbConn.insertUpdate(s);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static Customer customerLogin(String userEmail, String password) {
        //SELECT * FROM Customer WHERE email = 'angad@gmail.com' and password = 'afvbc';
        String encryptedPass = getEncryptedPassword(password);
        String query = "SELECT * FROM Customer WHERE email = '" + userEmail + "' and password = '" + encryptedPass + "'";
        databaseConnection dbconn = new databaseConnection();

        try {
            ResultSet rs = dbconn.getQueryTable(query);

            if (rs != null && rs.next()) {
                return new Customer(
                        rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(customerLogin("ramesh123@gmail.com", "12345"));
        System.out.println(getEncryptedPassword("123"));
    }

}
