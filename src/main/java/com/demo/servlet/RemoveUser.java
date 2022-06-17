package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.connection.DatabaseConnection;

/**
 * Servlet implementation class RemoveUser
 */
@WebServlet("/delete")
public class RemoveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		int userId = Integer.parseInt(request.getParameter("id"));
		Connection con;
		
		try {
			con =new  DatabaseConnection().getDatabadeConnection();
			
			PreparedStatement ps = con.prepareStatement("Delete from user where id = ?");
			ps.setInt(1, userId);
			
			int value = ps.executeUpdate();
			
			pw.println("<center>"+value+" user deleted.</center>");
			RequestDispatcher rd = request.getRequestDispatcher("login");
			rd.include(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
