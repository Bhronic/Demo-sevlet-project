package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import com.db.connection.DatabaseConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserRegistration
 */
@WebServlet("/user")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		String userEmail = request.getParameter("email");
		String userName = request.getParameter("name");
		String password = request.getParameter("password");

		try {
			Connection con = new DatabaseConnection().getDatabadeConnection();

			PreparedStatement ps1 = con.prepareStatement("select * from user where email_id = ?");
			ps1.setString(1, userEmail);

			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {
				if (rs.getString(2).toString().equals(userEmail)) {
					pw.println(
							"<html><body><center><div style=font-color:\"green\">email id is allready register<div></center></body></html>");
					RequestDispatcher rd = request.getRequestDispatcher("signup"); //
					rd.include(request, response);
					ps1.close();
					

				}
			}
			 
			
			PreparedStatement ps = con.prepareStatement("insert into user values(?,?,?,?) ");
			ps.setInt(1, 0);
			ps.setString(2, userEmail);
			ps.setString(3, userName);
			ps.setString(4, password);

			int value = ps.executeUpdate();

			if (value == 1) {
				pw.println(
						"<html><body><center><p style=font-color:\"green\">user register succesfully<p></center></body></html>");
				RequestDispatcher rd = request.getRequestDispatcher("login");
				rd.include(request, response);
			}
			  
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("invalid data insert.");
			RequestDispatcher rd = request.getRequestDispatcher("signup");
			rd.include(request, response);
		}

	}

}
