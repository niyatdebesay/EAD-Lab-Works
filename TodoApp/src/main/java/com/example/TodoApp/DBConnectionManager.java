package com.example.TodoApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class DBConnectionManager {
	private static  Connection connection;
	
	

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub

	}
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			createConnection();
		}
		return connection;
	}
	
	public static  void createConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/tododb";
		String username = "root";
		String password = "Thefamilyperson1!";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");	
			connection = DriverManager.getConnection(url, username, password);
			if (connection == null) {
				System.out.println("Connection Failed");
				
			}
		
			
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		
	}
	
	public static void closeConnection() throws SQLException {
		if (connection != null) {
			
				connection.close();
			
		}
	}

}
