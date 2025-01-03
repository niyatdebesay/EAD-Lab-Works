package com.itsc.LabExam;
//Niyat Debesay
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/bookRegister")
public class BookRegistrationServlet extends HttpServlet {
	

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve task details from the request
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("price"));

        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContextxml.xml");
        DBConnectionManagement dbManager = context.getBean("dbConnectionManager", DBConnectionManagement.class);


        try {
           
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Prepare the SQL query to insert task details
            String query = "INSERT INTO tasks (description, status, due_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set query parameters
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);

            
            int rowsAffected = stmt.executeUpdate();

          
            if (rowsAffected > 0) {
                out.println("<h2>Book registered successfully!</h2>");
            } else {
                out.println("<h2>Book registration failed!</h2>");
            }

          
            stmt.close();
        } catch (Exception e) {
           
            e.printStackTrace();
            out.println("<h2>An error occurred while registering the task.</h2>");
        } finally {
            try {
               
                dbManager.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
