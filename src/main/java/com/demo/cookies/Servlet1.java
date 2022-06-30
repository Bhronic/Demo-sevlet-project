package com.demo.cookies;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			String n = request.getParameter("userName");
			String email = request.getParameter("email");
			out.print("Welcome " + n);

			Cookie ck = new Cookie("uname", n);// creating cookie object
			response.addCookie(ck);// adding cookie in the response
			
			Cookie ck1 = new Cookie("email", email);// creating cookie object
			response.addCookie(ck1);// adding cookie in the response
			
			// creating submit button
			out.print("<form action='servlet2' method=\"post\">");
			out.print("<input type='submit' value='go'>");
			out.print("</form>");

			out.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
