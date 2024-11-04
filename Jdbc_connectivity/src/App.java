
import java.sql.*;


public class App {
public static void main(String[] args) {
	
try {
String driver = "com.mysql.cj.jdbc.Driver";
String url = "jdbc:mysql://localhost:3306/student";
String username = "root";
String password = "Thefamilyperson1!";
Class.forName(driver); 
Connection conn = DriverManager.getConnection(url,username, password
);
System.out.println("Established Connection");


}catch (ClassNotFoundException e) {
    System.out.println("JDBC Driver not found: " + e.getMessage());
} catch (SQLException e) {
    System.out.println("SQL Exception: " + e.getMessage());
} catch (Exception e) {
    e.printStackTrace();
}
}
}