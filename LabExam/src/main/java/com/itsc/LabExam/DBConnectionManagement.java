package com.itsc.LabExam;
//Niyat Debesay Kibrab
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class DBConnectionManagement {
		
		
			private String url = "jdbc:mysql://localhost:3306/booksdb";
			private String username = "root";
			private String password = "Thefamilyperson1!";
			private  static Connection connection;
			
			
			
	
			public void openConnection() throws SQLException, ClassNotFoundException {
		        
		        Class.forName("com.mysql.cj.jdbc.Driver");

		      
		        connection = DriverManager.getConnection(url, username, password);
		        System.out.println("Database connection established successfully.");
		    }

		   
		    public void closeConnection() throws SQLException {
		        if (connection != null && !connection.isClosed()) {
		            connection.close();
		            System.out.println("Database connection closed successfully.");
		        }
		    }
		    
		    public Connection getConnection() {
		    	return connection;
		    }
		    
		    
}
	
