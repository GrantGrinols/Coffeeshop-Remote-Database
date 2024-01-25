package com.example.coffeeshop;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BirthdayChecker {
    public List<String[]> GetBirthdays(){
        Connection connection = new UserDataController().MakeConnection();
        LocalDate today = LocalDate.now();
        String query = "SELECT FirstName, LastName FROM CustomerTable WHERE DAYOFMONTH(Birthday) = ? AND MONTH(Birthday) = ?";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, today.getDayOfMonth());
        preparedStatement.setInt(2, today.getMonthValue());

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String[]> rows = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while(resultSet.next()){
            String[] row = new String[columnCount];
            for(int i = 1; i <= columnCount; i++){
                row[i - 1] = resultSet.getString(i);
            }
            rows.add(row);
        }

        return rows;
        
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
