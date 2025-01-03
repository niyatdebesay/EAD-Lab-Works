package com.example.TodoApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class TaskRegistrationServlet  extends HttpServlet{
	private static final  String query = "Insert into tasks(description, status, due_date) values(?, ?, ?)";
	
	private Connection Connection;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res){
			try {
				Connection = DBConnectionManager.getConnection();
				PreparedStatement pd = Connection.prepareStatement(query);
				
				pd.setString(1,  req.getParameter("description"));
				pd.setString(2,  req.getParameter("status"));
				String dateStr = req.getParameter("date");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date utilDate =sdf.parse(dateStr); 
		        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 

		        
		        pd.setDate(3, sqlDate);
				int i = pd.executeUpdate();
				
				PrintWriter pw = res.getWriter();
				if (i ==0) {
					pw.println("Task was not saved Successfully");
				}
				pw.println("Task saved succesfully");
				
				
			}
			catch(SQLException| IOException e){
				
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}
	

}
