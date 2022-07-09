package com.demo.controller;

import java.awt.print.Pageable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
						pw.println("<html>" + "<head>"
								+ "<script>"
								+ "function getValue(val){\r\n"
								+ "   document.getElementById('size').innerHTML = val;\r\n"
								+ "}"
								+ "</script>"
								+ " <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n"
								+ " <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\r\n"
								+ "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n"
								+ "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>"
								+ "<style> tr,th, td {\r\n" + " border: 1px solid #f7a20c;" + "}</style>" + "</head>"
								+ "<body style=\"background-color:#face97;\">");
						if (rs.getString(2).toString().equals(userEmail)) {
							
							// using http session
							HttpSession session = request.getSession();
							session.setAttribute("userName",rs.getString(3));
							session.setAttribute("userId",rs.getString(1));
							session.setAttribute("userEmailId",rs.getString(2));
							
							SessionTracking sessionTracking = new SessionTracking();
							sessionTracking.sessionTracking(rs.getString(2), session.getId(),
									new Date(session.getCreationTime()).toString(),
									new Date(session.getLastAccessedTime()).toString(), Page.WELCOME.toString());
//							Cookie ck = new Cookie("userName",rs.getString(3));
//							response.addCookie(ck);
//							Cookie ck1 = new Cookie("userId",rs.getString(1));
//							response.addCookie(ck1);
							pw.println("<div class=\"container-fluid\"><div class=\"row\">\r\n"
									+ "  <div class=\"col-sm-8\">Welcome " + rs.getString(3) + "</div>\r\n"
									+ "  <div class=\"col-sm-4\"><a href=\"/Demo/login\"><button type=\"button\" class=\"btn btn-success\">Logout</button></a></div>\r\n"
									+ "</div></div>");
						}
						// <a href=\"/Demo/login\">Logout</a>
						// Welcome " + rs.getString(3) + "
						
						pw.println("<center><table><tr>");
						
						pw.println("<label for=\"cars\">Choose a page size:</label>\r\n"
								+ "\r\n"
								+ "<select onchange=\"getValue(this.value)\">\r\n"
								+ "  <option value=\"2\">2</option>\r\n"
								+ "  <option value=\"3\">3</option>\r\n"
								+ "  <option value=\"5\">5</option>\r\n"
								+ "  <option value=\"all\">all</option>\r\n"
								+ "</select>");
								
												
						pw.println("</tr></table></center>");
						PreparedStatement ps = con.prepareStatement("select * from user limit 0,"+totalRecords);
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
						
						/*
						 * pagination login need to improve.
						 * */
						PreparedStatement ps2 = con.prepareStatement("select count(*) from user");
						ResultSet resultSet = ps2.executeQuery();
						pw.println("<center><table><tr>");
						
						while (resultSet.next()) {
							int count = 1;
							int value = (resultSet.getInt(1)/totalRecords);
							int reminder = resultSet.getInt(1)%totalRecords;
							while(value >= count) {
								
							pw.println("<td style=\"padding: 15px;\"><a href=\"userdtls?page="+count+"\">"+count+"</a> </td>");
							count++;
							}
							if(reminder !=0) {
								pw.println("<td style=\"padding: 15px;\"><a href=\"userdtls?page="+count+"\">"+count+"</a> </td>");
								count++;	
							}
						}
						
						pw.println("</tr></table></center>");
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
