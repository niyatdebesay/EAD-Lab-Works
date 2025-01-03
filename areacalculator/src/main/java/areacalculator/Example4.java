package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/servlet/displayHTML")
public class Example4 extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		String name = req.getParameter("first-name");
		
		if (name == null) {
			name = "Guest";
		}
		pw.println("<html><body>");
		pw.println("<h1>Hello, " + name +" <h1>");
		pw.println("<body><html>");
		
	}

}
