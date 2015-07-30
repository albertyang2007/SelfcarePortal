<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<%@ include file="frage/navbar_head_meta.txt"%> 
    <%@ include file="frage/navbar_head_css.txt"%>
	<title>Purchase Fails - empty order</title>
	
	<!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
	<script type="text/javascript" src="js/bootstrap.js"></script>    	
	<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>
	<div class="container">
		<div class="jumbotron">
			<div class="container">
				<h2>Sorry ... Purchase Failed!!!</h2>
				<p>Reason: <c:out value="${failReason}" /></p>
				<br /> 
				<a href="./retrieveProductInfo.do" class="btn btn-primary btn-large">return to main</a>
			</div>
		</div>
	</div>
</body>
</html>