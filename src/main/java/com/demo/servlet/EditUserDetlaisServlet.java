package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.db.connection.DatabaseConnection;
import com.demo.sessiontracking.Page;
import com.demo.sessiontracking.SessionTracking;

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
		
		String userEmailId = null;
		
		HttpSession session = request.getSession(false);
		userEmailId = session.getAttribute("userEmailId").toString();
		
		try {
			new SessionTracking().sessionTracking(userEmailId, session.getId(),
					new Date(session.getCreationTime()).toString(),
					new Date(session.getLastAccessedTime()).toString(), Page.USER_EDIT.toString());
			session.setMaxInactiveInterval(3000);;
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		
		int userId = Integer.parseInt(request.getParameter("id"));
		String  emailId = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		Connection con;
//		Cookie[] ck = request.getCookies();
//		String userName = null;
//		for (Cookie c : ck) {
//			if(c.getName().equalsIgnoreCase("userName")){
//				userName = c.getValue();// (String)context.getAttribute("userName");
//				System.out.println(c.getValue());
//			}
//		}
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
			response.sendRedirect("http://localhost:8080/Demo/userdtls");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
