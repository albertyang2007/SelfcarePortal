<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/signin.css" rel="stylesheet">
    
    <%@ include file="frage/navbar_head_meta.txt"%> 
    <%@ include file="frage/navbar_head_css.txt"%>
    
<title>Login for Selfcare Portal</title>

</head>
<body>

    <!-- Fixed navbar -->
    <jsp:include page="frage/navbar_body_fix_bar.jsp"/>

    <!-- please put your content within container -->
	<div class="container">

		<form class="form-signin" method="post" id="registerForm" action="./processLogin.do">
			<h3 class="form-signin-heading" align="center" id="resultInfo"><c:out value="${requestScope.resultInfo}"/></h3>
			<label for="userIdInput">User Id:</label>
			<div><input type="text" class="form-control" placeholder="UserId" id="username" name="username" required autofocus></div>
			<label for="passwordInput">Password:</label>
			<div><input type="password" class="form-control" placeholder="Password" id="password" name="password" required></div>			
			<button class="btn btn-lg btn-primary" type="submit" id="submit">Login</button>
			<button class="btn btn-lg btn-primary" type="reset" id="reset">Reset</button>
		</form>
	</div><!-- /container -->
	
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="frage/navbar_body_js.txt"%>
</body>
</html>
