package com.example.coffeeshop;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;

import java.sql.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@RestController
public class UserDataController {
    //this function gets called when the html button is clicked
    //input: A UserData object
    //output: nothing
    @PostMapping("/saveUserData")
    public String updateDatabase(@RequestBody UserData userData) {
        System.out.println("First Name: " + userData.GetFirstName());
        System.out.println("Last Name: " + userData.GetLastName());
        System.out.println("Date: " + userData.getBirthday());
        userData.setEmail(userData.getEmail().toLowerCase());
        Connection connection = MakeConnection();
        if(connection==null){
            System.err.println("Something went wrong");
            return "null";
        }
        CheckSimilarity(connection, userData);
        

        return "Data recieved! Hurray!";
    }
    public boolean CheckSimilarity(Connection connection, UserData userData){
        try{
            
            String query = "SELECT * FROM CustomerTable WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, userData.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            Boolean flag = !resultSet.next();
            if(flag){
                System.out.println("This is not in the database");
                InsertQuery(connection, userData);
            }else{
                System.out.println("This is in the database");
                UpdateQuery(connection,userData);
            }
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }


    }
    public void UpdateQuery(Connection connection, UserData userData){
        String query = "Update CustomerTable SET FavoriteDrink = ? WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userData.getDrink());
            preparedStatement.setString(2, userData.getEmail());
            int rowsUpdate = preparedStatement.executeUpdate();
            if(rowsUpdate>0){
                System.out.println("Update successful");
            }else{
                System.out.println("No rows updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void InsertQuery(Connection connection,UserData userData){
        try{
        PreparedStatement preparedStatement = null;
        String insertQuery = "INSERT INTO CustomerTable (FirstName, LastName, Birthday, Email, FavoriteDrink)" +
                            "Values (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(insertQuery);

        preparedStatement.setString(1, userData.GetFirstName());
        preparedStatement.setString(2, userData.GetLastName());
        preparedStatement.setString(3, userData.getBirthday());
        preparedStatement.setString(4, userData.getEmail());
        preparedStatement.setString(5, userData.getDrink());

        int rowsinserted = preparedStatement.executeUpdate();
        if(rowsinserted > 0){
            System.out.println("A new customer has entered the database");
        }
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private List<String> GetConfigs(){
        try{
            System.out.println("printing");
        String filepath = "[path to configs.json]";

        FileReader reader = new FileReader(filepath);

        JSONTokener tokener = new JSONTokener(reader);

        List<String> configs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(tokener);
        configs.add(jsonObject.getString("host"));
        configs.add(jsonObject.getString("database"));
        configs.add(jsonObject.getString("user"));
        configs.add(jsonObject.getString("password"));

        return configs;
        
        
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }
    }
    public Connection MakeConnection(){
        List<String> configs = GetConfigs();
        String dbURL="jdbc:mariadb://"+configs.get(0)+":3306/"+configs.get(1);
        String username = configs.get(2);
        String password = configs.get(3);

        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected with database...");
            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("MariaDB JDBC driver not found.");
            e.printStackTrace();
        } catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return null;
    }
    
}
