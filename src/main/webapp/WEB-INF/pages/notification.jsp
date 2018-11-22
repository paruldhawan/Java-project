<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />">
<script type="text/javascript">
	function call_modify() {
		document.getElementById("modify").submit();

	}
</script>
</head>
<jsp:include page="bodyHeader.jsp"></jsp:include>
<form id="createNotification" action="CreateNotification" method="post"
	style="float: right; width: 224px;">
	<button type="submit" class="btn btn-danger">Create
		Notification</button>
</form>

<div align="center">
	<table class="table table-striped"
		style="width: 100%; font-size: 16px; margin-top: 80px">
		<tr>

			<th>Name</th>
			<th>Type</th>
			<th>Address</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="notification" items="${notifications}"
			varStatus="status">
			<tr>
				<td>${notification.getName()}</td>
				<td>${notification.getType()}</td>
				<td>${notification.getAddress()}</td>
				<td>
					<div style="width: 49%; float: left; padding: 5px 15px 5px 5px;">
						<form action="modify-notification" id="modify" method="get">
							<input type="hidden" id="id" name="id"
								value='${notification.getId()}'>
							<button class="col-sm-12 btn btn-primary" type="submit" id="edit">Edit</button>
						</form>
					</div>
					<div style="width: 49%; float: left; padding: 5px 5px 5px 5px;">
						<form action="delete-notification" method="get">
							<input type="hidden" id="id" name="id"
								value='${notification.getId()}'>
							<button type="submit" class="col-sm-12 btn btn-danger" id="id">Delete</button>
						</form>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

</body>

</html>