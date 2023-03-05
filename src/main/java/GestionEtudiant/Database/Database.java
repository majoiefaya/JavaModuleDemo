package GestionEtudiantDatabase.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "");
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}