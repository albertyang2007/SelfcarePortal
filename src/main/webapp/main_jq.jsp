<%@ page import="java.util.*"%>

<%
	String currentUserName = (String) session.getAttribute("currentUserName");
%>
<head>
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						jQuery
								.ajax({
									type : 'POST',
									contentType : 'application/json',
									url : 'http://localhost:8080/ProductCatalog/api/products.do',
									dataType : 'json',
									success : function(data) {
										$.each(data, function(i, item) {
											$('#th_productlist').after(
													"<tr><td>"
															+ +item.productNo
															+ "</td><td>"
															+ item.description
															+ "</td><td>"
															+ item.price
															+ "</td><tr>");
										});

									},
									error : function() {
										alert("Error on posting the product catalog")
									}
								});
					});
</script>
</head>
<html>
<body>
	<p>
		Current User:
		<%
		
		out.println(currentUserName);
	%>
		<br>
	</p>
	<p>All ProductCatalog:</p>

	<table id="tb_productlist" border="1">
		<tr id="th_productlist">
			<th>ProductID</th>
			<th>Description</th>
			<th>Price</th>
		</tr>
	</table>
</body>
</html>