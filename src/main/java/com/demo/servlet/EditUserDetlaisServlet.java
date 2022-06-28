package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.connection.DatabaseConnection;

/**
 * Servlet implementation class EditUserDetlaisServlet
 */
@WebServlet("/editUser")
public class EditUserDetlaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserDetlaisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		
		
		int userId = Integer.parseInt(request.getParameter("id"));
		String  emailId = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Connection con;
		ServletContext context = getServletContext();
		String userName = (String)context.getAttribute("userName");
		System.out.println(context.getAttribute("userName"));
		try {
			con =new  DatabaseConnection().getDatabadeConnection();
			
			PreparedStatement ps = con.prepareStatement("update user set email_id = ?,name = ?,password = ?  where id = ?");
			ps.setString(1, emailId);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setInt(4, userId);
			
			int value = ps.executeUpdate();
			
			pw.println("<center>"+value+" user record updated.</center>");
			//RequestDispatcher rd = request.getRequestDispatcher("userdtls?id="+userId+"&name="+userName);
			//rd.include(request, response);
			response.sendRedirect("http://localhost:8080/Demo/userdtls?id="+userId+"&name="+userName);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
