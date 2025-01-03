package com.itsc.LabExam;
//Niyat Debesay Kibreab
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchBook")
public class SearchBook {
	private DBConnectionManagement dbManager = new DBConnectionManagement();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the search query from the request
        String searchQuery = request.getParameter("title");

        // Prepare response writer to send feedback to the client
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
           
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            String query = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, "%" + searchQuery + "%");

           
            ResultSet rs = stmt.executeQuery();

         
            out.println("<html>");
            out.println("<head><title>Search Results</title></head>");
            out.println("<body>");
            out.println("<h2>Search Results for: \"" + searchQuery + "\"</h2>");

           
            if (!rs.isBeforeFirst()) {
                out.println("<p>No tasks found matching the search query.</p>");
            } else {
               
                out.println("<table border='1' cellspacing='0' cellpadding='5'>");
                out.println("<tr><th>ID</th><th>Description</th><th>Status</th><th>Due Date</th></tr>");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String price= rs.getString("price");

                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + title + "</td>");
                    out.println("<td>" + author + "</td>");
                    out.println("<td>" + price + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            out.println("</body>");
            out.println("</html>");

           
            rs.close();
            stmt.close();
        } catch (Exception e) {
            
            e.printStackTrace();
            out.println("<h2>An error occurred while searching for tasks.</h2>");
        } finally {
            try {
               
                dbManager.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}