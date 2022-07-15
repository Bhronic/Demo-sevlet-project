package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Demo
 */
@WebServlet("/signup")
public class SignupPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SignupPageServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.getWriter().append("time : "+new Date()).append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
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
		
		
		pw.println("<form action=\"/Demo/user\" method=\"post\">"
				+ " <div style=\"padding-left: 450px;\">"
				+ " <div class=\"container\">"
				+ "    <h1>Register</h1>"
				+ "    <p>Please fill in this form to create an account.</p>"
				+ "    <hr>"
				+ ""
				+ "    <label for=\"email\"><b>Email</b></label>"
				+ "    <input type=\"text\" placeholder=\"Enter Email\" name=\"email\" id=\"email\" required>"
				+ ""
				+ "    <label for=\"psw\"><b>User Name</b></label>"
				+ "    <input type=\"text\" placeholder=\"Enter User Name\" name=\"name\" id=\"name\" required>"
				+ ""
				+ "    <label for=\"psw-repeat\"><b>Password</b></label>"
				+ "    <input type=\"password\" placeholder=\"Password\" name=\"password\" id=\"password\" required>"
				+ "    <hr>"
				+ "    <button type=\"submit\" class=\"registerbtn\">Register</button>"
				+ "  </div>"
				+ "  <div class=\"container signin\">"
				+ "    <p>Already have an account? <a href=\"#\">Sign in</a>.</p>"
				+ "  </div>"
				+ "</div>"
				+ "</form> ");
		pw.println("</body>"
				+ "</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
