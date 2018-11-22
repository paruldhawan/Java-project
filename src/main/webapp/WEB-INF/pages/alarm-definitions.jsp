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
	media="all" />-->
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
	
</head>
<body>

	<jsp:include page="bodyHeader.jsp"></jsp:include>

	<h2>Alarm Definitions</h2>


	<form id="create-alarmdefinition" action="create-alarmdefinition"
		method="get" style="float: right; width: 224px;">
		<button type="submit" class="btn btn-danger">CREATE ALARM DEFINITION</button>
	</form>


		<div align="center">
			<table class="table table-striped" style="width:100%; font-size: 16px;margin-top: 80px">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Enabled</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="alarmdefinition" items="${alarmdefinitions}" 	varStatus="status">
					   
					<tr>
						<td>${alarmdefinition.getName()}</td>
						<td>${alarmdefinition.getDescription()}</td>
						<td>${alarmdefinition.isEnabled()}</td>
						<td>
						<form action="modify-alarmdefinition" method="get">
						<button type="submit">Modify Alarm Definition</button></form>
						</td>
						<td>
						<form action="delete-alarmdefinition" method="get">
						<input type="hidden" id="id" name="id" value='${alarmdefinition.getId()}'>
						<button type="submit">Delete Alarm Definition</button>
						</form>
						</td>
						
					</tr>
				</c:forEach>
			</table>
		</div>
	



	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript'
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>