package com.demo.controller;

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
import com.demo.sessiontracking.Page;
import com.demo.sessiontracking.SessionTracking;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println(request.getParameter("size"));
		Integer userId = null;
		String name = null;
		String emailId = null;
//		Cookie[] ck = request.getCookies();
		try {
			HttpSession session = request.getSession(false);
			userId = Integer.parseInt((session.getAttribute("userId").toString()));
			name = session.getAttribute("userName").toString();
			emailId = session.getAttribute("userEmailId").toString();
			
			new SessionTracking().sessionTracking(emailId, session.getId(),
					new Date(session.getCreationTime()).toString(),
					new Date(session.getLastAccessedTime()).toString(), Page.USER_DETAIL.toString());
//		for (Cookie c : ck) {
//			if(c.getName().equalsIgnoreCase("userName")){
//				name = c.getValue();// (String)context.getAttribute("userName");
//				System.out.println(c.getValue());
//			}
//			if(c.getName().equals("userId")) {
//				userId  = Integer.parseInt(c.getValue());
//				System.out.println(c.getValue());
//			}
//		}
		} catch (Exception e) {
			PrintWriter pw1 = response.getWriter();
			pw1.println("<center>Session is expired.</center>");
			RequestDispatcher rd = request.getRequestDispatcher("login");
			rd.include(request, response);
		}

		Connection con;
		try {
			int totalRecords = 3;
			int pageNo = Integer.parseInt(request.getParameter("page"));
			con = new DatabaseConnection().getDatabadeConnection();
			
			int offsetPageNo = 0;
			if(pageNo == 0) {}
			else {
				offsetPageNo = (pageNo-1)*totalRecords;
			}

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
				pw.println("<center><table><tr>");
				
				pw.println("<label for=\"cars\">Choose a page size:</label>\r\n"
						+ "\r\n"
						+ "<select name=\"size\" id=\"size\">\r\n"
						+ "  <option value=\"2\">2</option>\r\n"
						+ "  <option value=\"3\">3</option>\r\n"
						+ "  <option value=\"5\">5</option>\r\n"
						+ "  <option value=\"all\">all</option>\r\n"
						+ "</select>");;
				
				pw.println("</tr></table></center>");
				
				PreparedStatement ps = con.prepareStatement("select * from user limit "+offsetPageNo+","+totalRecords);
				ResultSet result = ps.executeQuery();
				pw.println("" + "<table  class=\"table");
				pw.println(
						"<tr class=\"info\" ><th>Id</th><th>Email Id</th><th>Name</th><th colspan=\"2\">Action</th></tr>");

				while (result.next()) {
					pw.println("<tr class=\"success\"><td>" + result.getInt(1) + "</td><td>" + result.getString(2)
							+ "</td><td>" + result.getString(3) + "</td><td><a href=\"/Demo/edit?id=" + result.getInt(1)
							+ "\">edit</a></td>" + "<td><a href=\"/Demo/delete?id=" + result.getInt(1)
							+ "\">delete</a></td></tr>");
				}
				pw.println("</table>");
				
				PreparedStatement ps2 = con.prepareStatement("select count(*) from user limit 0,"+totalRecords);
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
