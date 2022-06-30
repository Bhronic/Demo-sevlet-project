package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.connection.DatabaseConnection;

/**
 * Servlet implementation class UserEditResponseServlet
 */
@WebServlet("/userdtls")
public class UserEditResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserEditResponseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Integer userId = null;
		String name = null;
		
		Cookie[] ck = request.getCookies();
		
		for (Cookie c : ck) {
			if(c.getName().equalsIgnoreCase("userName")){
				name = c.getValue();// (String)context.getAttribute("userName");
				System.out.println(c.getValue());
			}
			if(c.getName().equals("userId")) {
				userId  = Integer.parseInt(c.getValue());
				System.out.println(c.getValue());
			}
		}
		Connection con;
		try {
			con = new DatabaseConnection().getDatabadeConnection();

			PreparedStatement ps1 = con.prepareStatement("select * from user where id = ?");
			ps1.setInt(1, userId);
			
			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {
				pw.println("<html>" + "<head>"
						+ " <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n"
						+ " <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\r\n"
						+ "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n"
						+ "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>"
						+ "<style> tr,th, td {\r\n" + " border: 1px solid #f7a20c;" + "}</style>" + "</head>"
						+ "<body style=\"background-color:#face97;\">");
					
					pw.println("<div class=\"container-fluid\"><div class=\"row\">\r\n"
							+ "  <div class=\"col-sm-8\">Welcome " + name + "</div>\r\n"
							+ "  <div class=\"col-sm-4\"><a href=\"/Demo/login\"><button type=\"button\" class=\"btn btn-success\">Logout</button></a></div>\r\n"
							+ "</div></div>");
				
				// <a href=\"/Demo/login\">Logout</a>
				// Welcome " + rs.getString(3) + "
				PreparedStatement ps = con.prepareStatement("select * from user");
				ResultSet result = ps.executeQuery();
				pw.println("" + "<table  class=\"table");
				pw.println("<tr class=\"info\" ><th>Id</th><th>Email Id</th><th>Name</th><th colspan=\"2\">Action</th></tr>");

				while (result.next()) {
					pw.println("<tr class=\"success\"><td>" + result.getInt(1) + "</td><td>" + result.getString(2) + "</td><td>"
							+ result.getString(3) + "</td><td><a href=\"/Demo/edit?id=" + result.getInt(1)
							+ "\">edit</a></td>" + "<td><a href=\"/Demo/delete?id=" + result.getInt(1)
							+ "\">delete</a></td></tr>");
				}
				pw.println("</table>");
				pw.println("</body></html>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("invalid Login data.");
			RequestDispatcher rd = request.getRequestDispatcher("login");
			rd.forward(request, response);
		}
	}

}
