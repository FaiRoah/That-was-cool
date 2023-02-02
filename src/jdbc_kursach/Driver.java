package jdbc_kursach;
import java.sql.*;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lab5_schema", "", "");
			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from жанр");
			
			while (myRs.next()) {
				System.out.println(myRs.getString("Номер жанра") + ", " + myRs.getString("название"));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
