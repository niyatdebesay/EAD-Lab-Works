package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/servlet/Login")
public class Example6 extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		if("admin".equals(username) && "123".equals(password)) {
			pw.println("<h1> Welcome, " + username + "!</h1>");
			
		}else {
			pw.println("<h1> Invalid login, please try again. </h1>");
		}
		}

}
