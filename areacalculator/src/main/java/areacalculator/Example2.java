package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servlet/name")
public class Example2 extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		 PrintWriter pw = res.getWriter();
		 String userName = req.getParameter("username");
		 pw.println("Helllo, " + userName);
		 }
	
}