package com.itsc.LabExam;
//Niyat Debesay Kibreab
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteBook")
public class DeleteBook extends HttpServlet {
  

   
    private DBConnectionManagement dbManager = new DBConnectionManagement();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String Id = request.getParameter("id");

       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

           
            stmt.setInt(1, Integer.parseInt(Id));

            
            int i = stmt.executeUpdate();

           
            if (i> 0) {
                out.println("<h2>Book deleted successfully!</h2>");
            } else {
                out.println("<h2>No books found with the given ID!</h2>");
            }

          
            stmt.close();
        } catch (Exception e) {
            
            e.printStackTrace();
            out.println("<h2>An error occurred while deleting the task.</h2>");
        } finally {
            try {
              
                dbManager.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}