package com.example.TodoApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DisplayTask extends HttpServlet {
	
	private final DBConnectionManager dbConnectionManager ;
	
	private static final String query = "select * from tasks";
	
	@Autowired
	public DisplayTask(DBConnectionManager DbconnectionManager) {
		this.dbConnectionManager = DbconnectionManager;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			Connection  conn = dbConnectionManager.getConnection();
			PreparedStatement pdstmt = conn.prepareStatement(query);
			
			ResultSet rs = pdstmt.executeQuery();
			PrintWriter pw = res.getWriter();
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<table>");
			pw.println("<td>");
			pw.println("<th> ID</th>");
			pw.println("<th> Task Description</th>");
			pw.println("<th> Task Status</th>");
			pw.println("<th> Date</th>");
			pw.println("<th> Delete</th>");
			pw.println("</td>");
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>" +  rs.getInt("id") + "</td>");
				pw.println("<td>" +  rs.getString("description") + "</td>");
				pw.println("<td>" +  rs.getString("status") + "</td>");
				pw.println("<td>" +  rs.getDate("due_date") + "</td>");
				pw.println("<td><a href= \"delete?id=" + rs.getInt("id") + "\">Delete Task </a></td>");
				pw.println("</tr>");
			}
			pw.println("</table>");
			pw.println("<a href =\"index.html\"> Add a task </a>"); 
			pw.println("</body>");
			pw.println("</html>");
			
		}
		catch(SQLException  | IOException e){
			e.printStackTrace();
		}
		
	}

}
