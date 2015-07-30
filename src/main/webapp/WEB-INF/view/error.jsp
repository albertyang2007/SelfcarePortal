<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<html>
<head>
<title>Error</title>
</head>
<body>
    <c:out value="${failReason}"/>

	<br><br><a href="index.jsp">return to main</a>
</body>
</html>