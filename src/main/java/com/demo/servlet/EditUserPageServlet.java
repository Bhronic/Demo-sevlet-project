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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.connection.DatabaseConnection;

/**
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/edit")
public class EditUserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserPageServlet() {
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
			
			PreparedStatement ps = con.prepareStatement("select * from user  where id = ?");
			ps.setInt(1, userId);
			
			ResultSet value = ps.executeQuery();
			
			while(value.next()) {
				
				pw.println("<html>"
						+ "<head>"
						+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
						+ "<style>"
						+ "body {"
						+ "  font-family: Arial, Helvetica, sans-serif;"
						+ "  background-color: black;"
						+ "}"
						+ ""
						+ "* {"
						+ "  box-sizing: border-box;"
						+ "}"
						+ ""
						+ "/* Add padding to containers */"
						+ ".container {"
						+ "width :40%;"
						+ "  padding: 16px;"
						+ "  background-color: white;"
						+ "}"
						+ ""
						+ "/* Full-width input fields */"
						+ "input[type=text], input[type=password] {"
						+ "  width: 100%;"
						+ "  padding: 15px;"
						+ "  margin: 5px 0 22px 0;"
						+ "  display: inline-block;"
						+ "  border: none;"
						+ "  background: #f1f1f1;"
						+ "}"
						+ ""
						+ "input[type=text]:focus, input[type=password]:focus {"
						+ "  background-color: #ddd;"
						+ "  outline: none;"
						+ "}"
						+ ""
						+ "/* Overwrite default styles of hr */"
						+ "hr {"
						+ "  border: 1px solid #f1f1f1;"
						+ "  margin-bottom: 25px;"
						+ "}"
						+ ""
						+ "/* Set a style for the submit button */"
						+ ".registerbtn {"
						+ "  background-color: #04AA6D;"
						+ "  color: white;"
						+ "  padding: 16px 20px;"
						+ "  margin: 8px 0;"
						+ "  border: none;"
						+ "  cursor: pointer;"
						+ "  width: 100%;"
						+ "  opacity: 0.9;"
						+ "}"
						+ ""
						+ ".registerbtn:hover {"
						+ "  opacity: 1;"
						+ "}"
						+ ""
						+ "/* Add a blue text color to links */"
						+ "a {"
						+ "  color: dodgerblue;"
						+ "}"
						+ ""
						+ "/* Set a grey background color and center the text of the \"sign in\" section */"
						+ ".signin {"
						+ "  background-color: #f1f1f1;"
						+ "  text-align: center;"
						+ "}"
						+ "</style>"
						+ "</head>"
						+ "<title>demo page</title>"
						+ "<body style=\"background-color:#face97;\">");
				
				
				pw.println("<form action=\"/Demo/editUser\" method=\"post\">"
						+ " <div style=\"padding-left: 450px;\">"
						+ " <div class=\"container\">"
						+ "    <h1>Edit User</h1>"
						+ "    <hr>"
						+ ""
						+ "    <input type=\"hidden\" placeholder=\"Enter Email\" value="+value.getString(1)+" name=\"id\" id=\"id\" required>"
						+ "    <label for=\"email\"><b>Email</b></label>"
						+ "    <input type=\"text\" placeholder=\"Enter Email\" value="+value.getString(2)+" name=\"email\" id=\"email\" required>"
						+ ""
						+ "    <label for=\"psw\"><b>User Name</b></label>"
						+ "    <input type=\"text\" placeholder=\"Enter User Name\" value="+value.getString(3)+" name=\"name\" id=\"name\" required>"
						+ ""
						+ "    <label for=\"psw-repeat\"><b>Password</b></label>"
						+ "    <input type=\"password\" placeholder=\"Password\" value="+value.getString(4)+" name=\"password\" id=\"password\" required>"
						+ "    <hr>"
						+ "    <button type=\"submit\" class=\"registerbtn\">Register</button>"
						+ "  </div>"
						+ "</div>"
						+ "</form> ");
				pw.println("</body>"
						+ "</html>");
			}
			
			//pw.println("<center>"+value+" user deleted.</center>");
			//RequestDispatcher rd = request.getRequestDispatcher("login");
			//rd.include(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
