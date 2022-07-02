package com.demo.sessiontracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.db.connection.DatabaseConnection;

public class SessionTracking {

	public void sessionTracking(String emailId, String sessionId, String logIn, String logOut, String page)
			throws ClassNotFoundException, SQLException {
		Connection con = new DatabaseConnection().getDatabadeConnection();

		PreparedStatement ps;
		try {
			ps = con.prepareStatement("insert into session_tracking values(?,?,?,?,?,?) ");
			ps.setInt(1, 0);
			ps.setString(2, emailId);
			ps.setString(3, sessionId);
			ps.setString(4, logIn);
			ps.setString(5, logOut);
			ps.setString(6, page);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
