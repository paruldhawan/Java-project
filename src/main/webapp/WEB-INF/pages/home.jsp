<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 
<link rel="stylesheet" href="<c:url value="/resources/mycss/style.css" />" type="text/css" media="all" />
<link rel="stylesheet" href="<c:url value="/resources/mycss/style1.css" />" type="text/css" media="all" />
-->
<link rel="stylesheet" href= "<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" >
</head>
<body>

<jsp:include page="bodyHeader.jsp"></jsp:include>

<%String username=request.getSession().getAttribute("username").toString(); %>
<h2>All Alarms</h2>

<form action="alarm">
<div align="center">
<table class="table table-striped" style="width:100%; font-size: 16px;margin-top: 80px">
<tr>
                <th>Alarm Id</th>
                <th>Alarm Name</th>
                <th>Description</th>
                <th>Notification</th>
           
                </tr>
                <c:forEach var="alarm" items="${listAlarms}" varStatus="status">
                <tr>
                <td>${alarm.id}</td>
                    <td>${alarm.alarm_name}</td>
                    <td>${alarm.alarm_desc}</td>
                    <td>${alarm.notification_enabled}</td>
                    
                    </tr>
                    </c:forEach>
                </table>
                </div>
                </form>
                


<script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type='text/javascript' src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>