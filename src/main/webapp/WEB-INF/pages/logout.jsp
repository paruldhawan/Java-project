<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
</head>
<body>
	<div id="fixedheader">Snapdeal</div>
	<h1>CLICK HERE</h1>
	<div id="fixedfooter">Copyright 2015 protected</div>
	<%
		session.setAttribute("username", null);
		session.invalidate();
		response.sendRedirect("login");
	%>

</body>
</html>