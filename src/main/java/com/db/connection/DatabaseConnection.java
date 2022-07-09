package com.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	Connection con;
	public Connection getDatabadeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		 con = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/user_info", "root", "admin@123");
		return con;
	}
	
	public void connectinClose() throws SQLException {
		con.close();
	}
}
