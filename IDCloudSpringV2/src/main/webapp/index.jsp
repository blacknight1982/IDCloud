<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
		<META http-equiv="refresh" content="3;URL=/IDCloudSpringV2/inspiration/index">
		
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/inspiration/index" var="inspirationUrl" />
		<a href="${inspirationUrl}">We will automatically redirect you to IDCloud Inspiration</a>
	</body>
</html>
