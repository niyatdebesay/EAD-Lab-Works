package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/servlet/FormSubmission")
public class Example5 extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		pw.println("<html><body>");
		pw.println("<h1>Form Submitter</h1>");
		pw.println("<p>Username:" + username + "</p>");
		pw.println("<p> Email:" + email + "</p>");
		pw.println("<p> Email <a href = 'mailto:" + email +"'>" + "</a></p>");
		pw.println("</body></html>");
		
		}

}
