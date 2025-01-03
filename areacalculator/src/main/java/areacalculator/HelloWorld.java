package areacalculator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servlet")
public class HelloWorld extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter pw = res.getWriter();
		pw.println("Hello World");
		
	}

}


