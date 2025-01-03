package com.example.TodoApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteTaskServlet extends HttpServlet {
	private  static String query = "Delete from tasks where id = ?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		try {
			Connection con = DBConnectionManager.getConnection();
			PreparedStatement pdstmt = con.prepareStatement(query);
			pdstmt.setInt(1,  Integer.parseInt(req.getParameter("id")));
			
			int i = pdstmt.executeUpdate();
			PrintWriter pw = res.getWriter();
			pw.println("<html>");
			pw.println("<body>");
			
			if (i ==0) {
				pw.println("<h2>Task was not deleted</h2>");
				
			}
			else {
				pw.println("<h2>Task  deleted succesfully</h2>");
			}
			pw.println("<a href=\'display\'> go back to display</a>");
			pw.println("</body>");
			pw.println("</html>");
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
