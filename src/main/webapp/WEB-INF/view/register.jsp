<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Registration for Selfcare Portal</title>

<link href="css/signin.css" rel="stylesheet">

<%@ include file="frage/navbar_head_meta.txt"%> 
<%@ include file="frage/navbar_head_css.txt"%>

<script src="js/jquery.js"></script>

<script type="text/javascript">
jQuery(function(){
	    var hasError = [false, false, false, false];

        $("#userId").blur(function(){
        	$(".errorUserId").empty().hide();
        	var userId = $("#userId").val();
        	var regRule = /^[a-zA-Z0-9-]+$/;
        	
        	if(!regRule.test(userId)) {   
            	$("#userId").after('<span class="errorUserId">UserId must be character or number.</span>');
            	$(".errorUserId").show();     
            	hasError[0] = true;
            }else{
            	hasError[0] = false;
            }
	    });
        
        $("#nickName").blur(function(){
        	$(".errorNickName").empty().hide();
        	var nickName = $("#nickName").val();
        	var regRule = /^[a-zA-Z0-9-]+$/;
        	
        	if(!regRule.test(nickName)) {   
            	$("#nickName").after('<span class="errorNickName">NickName must be character or number.</span>');
            	$(".errorNickName").show();     
            	hasError[1] = true;
            }else{
            	hasError[1] = false;
            }
	    });
        
        $("#password").blur(function(){
        	$(".errorPassword").empty().hide();
        	var password = $("#password").val();
        	var regRule = /^[a-zA-Z0-9-]+$/;
        	
        	if(!regRule.test(password)) {   
            	$("#password").after('<span class="errorPassword">Password must be character or number.</span>');
            	$(".errorPassword").show();     
            	hasError[2] = true;
            }else{
            	hasError[2] = false;
            }
	    });
        
        $("#confirm_password").blur(function(){
        	$(".errorPassword2").empty().hide();
        	var password = $("#password").val();
        	var confirm_password = $("#confirm_password").val();
        	
        	if(confirm_password != password) {   
            	$("#confirm_password").after('<span class="errorPassword2">Confirm Password must be same.</span>');
            	$(".errorPassword2").show();     
            	hasError[3] = true;
            }else{
            	hasError[3] = false;
            }
	    });

        $("#submit").click(function(){
        	  if(hasError[0] || hasError[1] || hasError[2] || hasError[3]){
            	  //alert("Input data is incorrect!");
            	  return false;
        	  }else{
        		  return true;
              }
        });
	});	
</script>

</head>

<body>
    <!-- Fixed navbar -->
    <jsp:include page="frage/navbar_body_fix_bar.jsp"/>
    
    <!-- please put your content within container -->
	<div class="container">

		<form class="form-signin" method="post" action="./processRegister.do">
			<h3 class="form-signin-heading" align="center" id="resultInfo"><c:out value="${requestScope.resultInfo}"/></h3>
			<label for="userIdInput">User Id:</label>
			<div><input type="text" class="form-control" placeholder="userId" id="userId" name="userId" required autofocus></div>
			<label for="nickNameInput">Nick Name:</label>
			<div><input type="text" class="form-control" placeholder="nickName" id="nickName" name="nickName" required></div>
			<label for="passwordInput">Password:</label>
			<div><input type="password" class="form-control" placeholder="Password" id="password" name="password" required></div>
			<label for="passwordInput2">Confirm Password:</label>
			<div><input type="password" class="form-control" placeholder="Confirm Password" id="confirm_password" required></div>
			<div>
			<button class="btn btn-lg btn-primary" type="submit" id="submit">Register</button>
			<button class="btn btn-lg btn-primary" type="reset" id="reset">Reset</button>
			</div>
		</form>

	</div><!-- /container -->
	
	<!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="frage/navbar_body_js.txt"%>
</body>
</html>
