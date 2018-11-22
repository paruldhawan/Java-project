<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type='text/javascript'
        src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type='text/javascript'
        src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script type="text/javascript">
	var count=0;
	function addTextBox()
	{
		
		var divHtml='</br><input type=\"text\" name=\"address\"/></br>';
		console.log(divHtml);
		$("#addAddress").append(divHtml);
		
	}
	</script>
</head>
<body>
	<jsp:include page="bodyHeader.jsp"></jsp:include>
	<h1>Create Notification Method</h1>
	<form id="notification" action="save" method="post">
		<table class="table table-striped" style="text-align: center">
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Type:</td>
				<td><select type="select" name="type">
						<option>Email</option>
						<option>Slack</option>
				</select></td>
			</tr>
			<tr id="addAddress">
				<td>Address:</td>
				<td><input type="text" name="address" /></td>
				<td><button type="button" name="addaddress" onclick="addTextBox()">Add More Address</button></td>
			</tr>
			
			<tr>
				<td colspan="2" style="text-align: center">
					<button type="submit" class="btn btn-success">Create
						Notification Method</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>