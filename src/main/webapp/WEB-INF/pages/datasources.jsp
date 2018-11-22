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
	<style type="text/css">
	#theader{
	height: 33px;
font-size: 18px;
background-color: #DCDCDC;
color: black;
	}
	</style>
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
	<script type="text/javascript">
	function call_modify() {
		document.getElementById("modify").submit();
		
	}</script>
</head>
<body>


	<jsp:include page="bodyHeader.jsp"></jsp:include>

<div >

	<h2>Datasources</h2>

<div style="float:right; width : 200px ; margin-top: -45px; ">
	<form id="create-datasource" action="create-datasource" method="get">
		<button  type="submit" class="btn btn-danger">CREATE DATASOURCE</button>
	</form>
	</div>



	
		<div style="margin-top: 49px;">
			<table  class="table table-striped" style="width:100%; font-size: 16px;margin-top: 80px">
			<thead style="background:black;">
				<tr id="theader">
					<th>Name</th>
					<th>URL</th>
					<th>Database</th>
					<th>Actions</th>
				</tr>
				</thead>
				<c:forEach var="datasource" items="${datasources}"
					varStatus="status">
					<tr>
						<td>${datasource.getName()}</td>
						<td>${datasource.getURL()}</td>
						<td>${datasource.getDatabase()}						
						
						
						</td>
						
						<td><!--<select name="dsActions" id="dsActions">
								<option value="1">Edit DataSource</option>
								<option value="2">Delete DataSource</option>
								</select>-->
								<div style="width:49%; float : left; padding : 5px 15px 5px 5px;">
								<form action="modify-datasource" id="modify" method="get">
								<input type="hidden" id="id" name="id" value='${datasource.getID()}'>
						<button class="col-sm-12 btn btn-primary" type="submit" id="edit">Edit</button>
						</form>
						</div>
						<div style="width:49%; float : left; padding : 5px 5px 5px 5px;">
						<form action="delete-datasource" id="modify" method="get">
								<input type="hidden" id="id" name="id" value='${datasource.getID()}'>
								<button type="submit" class="col-sm-12 btn btn-danger" id="delete">Delete</button>
								</form>
								</div>
							</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form>
	
	



	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type='text/javascript'
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
		</div>
</body>
</html>