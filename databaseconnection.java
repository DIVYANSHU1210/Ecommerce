
package com.example.ecomm;
import java.sql.*;
public class databaseConnection  {
    String dburl = "jdbc:mysql://localhost:3306/ecommm";
    String userName = "root";

    String password = "Summerking@24k";

    private Statement getStatement(){
        try{
            Connection conn = DriverManager.getConnection(dburl, userName, password);
            return conn.createStatement();   // Creates a Statement object for sending SQL statements to the database.
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getQueryTable (String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);   // executeQuery :-  Executes the given SQL statement, which returns a single ResultSet object.
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUpdate (String query){
        Statement statement = getStatement();
        try{
            statement.executeUpdate(query);  // Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String query = "select * from products";
        databaseConnection dbcon = new databaseConnection();
        ResultSet rs = dbcon.getQueryTable(query);
        if(rs != null){
            System.out.println("Connected To Database");
        }
    }
}
