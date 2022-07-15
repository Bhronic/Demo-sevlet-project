<%@page import="com.demo.model.User"%>
<%@ page errorPage="error.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*,java.sql.*,com.db.connection.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style>
tr, th, td { "
	border: 1pxsolid #f7a20c;
	"
}
</style>
</head>
<body style="background-color: #face97;">
	<div class="container-fluid">
		<%
		response.setContentType("text/html");

		//HttpSession session1 = request.getSession(false);
		//String name=(String)session.getAttribute("user");  
		//out.print("Hello "+session.getAttribute("user").toString());  
		//User user = new User(Integer.parseInt((session.getAttribute("id").toString())),
		//session.getAttribute("name").toString(), session.getAttribute("emailId").toString());
		//out.println(user.getName() + "  " + user.getEmailId());

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
		%>
		<div class="row">
			<div class="col-sm-8">
				Welcome
				<%=rs.getString(3)%></div>
			<div class="col-sm-4">
				<a href="/Demo/login"><button type="button"
						class="btnbtn-success">Logout</button></a>
			</div>
		</div>
		<%
		}
		int totalRecords = 3;
		PreparedStatement ps = con.prepareStatement("select * from user limit 0,3");
		ResultSet result = ps.executeQuery();
		%>
		<center>
			<table class="table">
				<tr class="info">
					<th>Id</th>
					<th>Email Id</th>
					<th>Name</th>
					<th colspan="2">Action</th>
				</tr>

				<%
				while (result.next()) {
				%>
				<tr class="success">
					<td><%=result.getInt(1)%></td>
					<td><%=result.getString(2)%></td>
					<td><%=result.getString(3)%></td>
					<td><a href="/Demo/edit?id=<%=result.getInt(1)%> ">edit</a></td>
					<td><a href="/Demo/delete?id=<%=result.getInt(1)%>">delete</a></td>
				</tr>
				<%
				}
				} catch (Exception e) {
				e.printStackTrace();
				}
				%>
			</table>
		</center>


	</div>


</body>
</html>