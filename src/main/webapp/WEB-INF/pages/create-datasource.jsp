<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 
<link rel="stylesheet"
	href="<c:url value="/resources/mycss/style.css" />" type="text/css"
	media="all" />
<link rel="stylesheet"
	href="<c:url value="/resources/mycss/style1.css" />" type="text/css"
	media="all" />
	 -->
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
</head>
<body>

	<jsp:include page="bodyHeader.jsp"></jsp:include>

	<h2>Create Datasources</h2>


	<div>
		<form action="create-datasource" method="POST">
			<table class="table table-striped" style=" text-align: center">

				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Url:</td>
					<td><input type="text" name="url" /></td>
				</tr>
				<tr>
					<td>Database:</td>
					<td><input type="text" name="database" /></td>
				</tr>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" /></td>
				</tr>	
					<tr>
					<td colspan="2"  style="text-align: center">
					<button type="submit" class="btn btn-success">Add</button>
					</td>
				</tr>
			
			</table>
			
		</form>
	</div>



	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript'
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>