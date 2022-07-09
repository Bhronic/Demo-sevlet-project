package com.demo.contoller1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.connection.DatabaseConnection;
import com.demo.model.User;
import com.demo.sessiontracking.Page;
import com.demo.sessiontracking.SessionTracking;

/**
 * Servlet implementation class MVCLogin
 */
@WebServlet("/mvclogin")
public class MVCLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MVCLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String userEmail = request.getParameter("email");
		String password = request.getParameter("password");
		int totalRecords = 3;
		Connection con;
		try {
			con = new DatabaseConnection().getDatabadeConnection();

			PreparedStatement ps1 = con.prepareStatement("select * from user where email_id = ? and password = ?");
			ps1.setString(1, userEmail);
			ps1.setString(2, password);

			ResultSet rs = ps1.executeQuery();
			
			while (rs.next()) {
				HttpSession session = request.getSession();
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
				session.setAttribute("user",user);
				
//				SessionTracking sessionTracking = new SessionTracking();
//				sessionTracking.sessionTracking(rs.getString(2), session.getId(),
//						new Date(session.getCreationTime()).toString(),
//						new Date(session.getLastAccessedTime()).toString(), Page.WELCOME.toString());
//				
				RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");  
		            rd.forward(request, response);  
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("invalid Login data.");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

}
