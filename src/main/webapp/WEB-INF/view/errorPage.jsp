<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<html>
<head>
<title>Error page</title>
</head>
<body>
	<h2>
	<c:out value="${errorTitle}"/>
	</h2>
	<hr />
	<h2>
	<c:out value="${errorType}"/>
	</h2>
	<b>
	<c:out value="${errorMessage}"/>
	</b>
	
	<br><br><a href="index.jsp">return to main</a>
</body>
</html>