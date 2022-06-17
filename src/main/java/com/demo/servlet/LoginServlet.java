package com.demo.servlet;

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

import com.db.connection.DatabaseConnection;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		pw.println("<html>" + "<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "<style>" + "body {" + "  font-family: Arial, Helvetica, sans-serif;" + "  background-color: black;"
				+ "}" + "" + "* {" + "  box-sizing: border-box;" + "}" + "" + "/* Add padding to containers */"
				+ ".container {" + "width :40%;" + "  padding: 16px;" + "  background-color: white;" + "}" + ""
				+ "/* Full-width input fields */" + "input[type=text], input[type=password] {" + "  width: 100%;"
				+ "  padding: 15px;" + "  margin: 5px 0 22px 0;" + "  display: inline-block;" + "  border: none;"
				+ "  background: #f1f1f1;" + "}" + "" + "input[type=text]:focus, input[type=password]:focus {"
				+ "  background-color: #ddd;" + "  outline: none;" + "}" + "" + "/* Overwrite default styles of hr */"
				+ "hr {" + "  border: 1px solid #f1f1f1;" + "  margin-bottom: 25px;" + "}" + ""
				+ "/* Set a style for the submit button */" + ".registerbtn {" + "  background-color: #04AA6D;"
				+ "  color: white;" + "  padding: 16px 20px;" + "  margin: 8px 0;" + "  border: none;"
				+ "  cursor: pointer;" + "  width: 100%;" + "  opacity: 0.9;" + "}" + "" + ".registerbtn:hover {"
				+ "  opacity: 1;" + "}" + "" + "/* Add a blue text color to links */" + "a {" + "  color: dodgerblue;"
				+ "}" + "" + "/* Set a grey background color and center the text of the \"sign in\" section */"
				+ ".signin {" + "  background-color: #f1f1f1;" + "  text-align: center;" + "}" + "</style>" + "</head>"
				+ "<title>demo page</title>" + "<body style=\"background-color:#face97;\">");

		pw.println("<form action=\"/Demo/login\" method=\"post\">" + " <div style=\"padding-left: 450px;\">"
				+ " <div class=\"container\">" + "    <h1>Login</h1>"

				+ "    <hr>" + "" + "    <label for=\"email\"><b>Email</b></label>"
				+ "    <input type=\"text\" placeholder=\"Enter Email\" name=\"email\" id=\"email\" required>"
				+ "    <label for=\"psw-repeat\"><b>Password</b></label>"
				+ "    <input type=\"password\" placeholder=\"Password\" name=\"password\" id=\"password\" required>"
				+ "    <hr>" + "    <button type=\"submit\" class=\"btnLogin\">Sign in</button>" + "  </div>"
				+ "  <div class=\"container signin\">"
				+ "    <p>create new account ? <a href=\"signup\">Sign up</a>.</p>" + "  </div>" + "</div>"
				+ "</form> ");
		pw.println("</body>" + "</html>");
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
		String password = request.getParameter("password");

		Connection con;
		try {
			con = new DatabaseConnection().getDatabadeConnection();

			PreparedStatement ps1 = con.prepareStatement("select * from user where email_id = ? and password = ?");
			ps1.setString(1, userEmail);
			ps1.setString(2, password);

			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {
				pw.println("<html>" + "<head>" + "<style> tr,th, td {\r\n" + " border: 1px solid #f7a20c;" + "}</style>"
						+ "</head>" + "<body style=\"background-color:#face97;\">");
				if (rs.getString(2).toString().equals(userEmail)) {

					pw.println("" + "<table  style=\"padding-left: 250px; \" width=\"80%\">" + "<tr>"
							+ "<td width=50%><label>Welcome " + rs.getString(3) + "</label></td>"
							+ "<td width=50% style=\"text-align:right\"><a href=\"/Demo/login\">Logout</a></td>"
							+ "</tr>" + "</table>");
				}
				
				PreparedStatement ps = con.prepareStatement("select * from user");
				ResultSet result = ps.executeQuery();
				pw.println("" + "<table  style=\"padding-left: 250px; \" width=\"80%\">" );
				pw.println("<tr><th>Id</th><th>Email Id</th><th>Name</th><th colspan=\"2\">Action</th></tr>");
				
				while (result.next()) {
					pw.println("<tr><td>"+result.getInt(1)+"</td><td>"+result.getString(2)+
							"</td><td>"+result.getString(3)
							+"</td><td><a href=\"/Demo/edit?id="+result.getInt(1)+"\">edit</a></td>"
							+ "<td><a href=\"/Demo/delete?id="+result.getInt(1)+"\">delete</a></td></tr>");
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
