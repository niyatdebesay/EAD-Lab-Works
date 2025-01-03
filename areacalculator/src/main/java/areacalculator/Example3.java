package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/servlet/personalInfo")
public class Example3 extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter pw = res.getWriter();
		String userName = req.getParameter("username");
		int age = Integer.parseInt(req.getParameter("age"));
		pw.printf("%s is %d",userName , age);
	}

}
