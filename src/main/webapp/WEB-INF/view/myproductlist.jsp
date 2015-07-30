<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="frage/navbar_head_meta.txt"%> 
<%@ include file="frage/navbar_head_css.txt"%>

    <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/mergecell.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Purchase Product List</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
    <!-- Fixed navbar -->
    <jsp:include page="frage/navbar_body_fix_bar.jsp"/>

 <div class="container"> 
    <div class="jumbotron">
      <div class="container">
        <c:choose>
	 		<c:when test="${requestSource == 'purchase'}">
	 		   <h2>Purchased successfully!</h2>
        	   <p>Make more fun in shopping!</p>
	 		</c:when>
        	<c:otherwise>
        	   <c:choose>
        	     <c:when test="${empty myorderlist}">
        	       <h2>Your Purchased list is empty!</h2>
        	       <p>Action now to do your funny shopping!</p>
        	       <p><a href="./retrieveProductInfo.do" class="btn btn-primary btn-large">Go to shopping&raquo;</a></p>
        	     </c:when>
        	     <c:otherwise>
        		   <h2>Your Purchased Product History List</h2>
       			   <p>Make more fun in shopping!</p>
       			</c:otherwise>
       			</c:choose>
       		</c:otherwise>
	   </c:choose>  
      </div>
    </div>
	

<div class="container">
   <form name="productList" method="post" action="./purchase.do" >	
   <table class="table table-hover" width="100%" border="1" id="table_order">
    
  <tr>
    <td width="8%" bgcolor="#66FFFF"><div align="center">Order ID </div></td>
    <td width="15%" bgcolor="#66FFFF"><div align="center">Purchase Date </div></td>
    <td width="11%" bgcolor="#66FFFF"><div align="center">Product NO</div></td>
    <td width="40%" bgcolor="#66FFFF"><div align="center">Product Description </div></td>
    <td width="15%" bgcolor="#66FFFF"><div align="center">Price</div></td>
    <td width="8%" bgcolor="#66FFFF"><div align="center">Reorder Mark </div></td>
  </tr>

    <c:forEach items="${myorderlist}" var="order" varStatus="ordervar">

      <c:forEach items="${order.products}" var="product" varStatus="productvar">
        <tr> 
          <td>${order.orderId}</td>
          <td>${order.createDate}</td>
          <td>${product.productNo}</td>          
          <td>${product.description}</td>
          <td>${product.price}</td>
          <td>
		  <c:choose>
		    <c:when test="${requestSource == 'purchase'}">
                <input type="checkbox" name="checkbox" value="checkbox" disabled="disabled">
			</c:when>
        	<c:otherwise>
        		<input type="checkbox" name="productID" value="${product.productNo}">
       		</c:otherwise>
	       </c:choose>  
          </td>
        </tr>
      </c:forEach>
    </c:forEach> 
</table>
     <c:if test="${requestSource != 'purchase'}">
     <input type="submit" class="btn btn-primary" name="Submit" value="Reorder">
     </c:if>
     </form>
</div>
</div> <!-- /container -->

 
 <c:if test="${requestSource != 'purchase'}">
    <div class="container">
	  <ul class="pagination">
		<c:choose>
		<c:when test = "${currentpage==1}">
        	<li class="disabled"><span>&laquo;</span></li>            
        </c:when>
        <c:otherwise>
        	<li><a href="myproductlist.do?page=1">&laquo;</a></li>
        </c:otherwise>
        </c:choose>
        <c:if test ="${currentpage > 1}">	
			<li><a href="myproductlist.do?page=${currentpage-1}">previous</a></li>
		</c:if>
		<c:forEach begin="1" end="${totalpage}" varStatus="pagenum">
		    <c:choose>
		    <c:when test = "${currentpage == pagenum.index}">
		       <li  class="active"><span>${currentpage}</span></li> 
		    </c:when>
		    <c:otherwise>
		       <li><a href="myproductlist.do?page=${pagenum.index}">${pagenum.index}</a></li>    
		    </c:otherwise>
		    </c:choose>
		</c:forEach>
  		<c:if test ="${currentpage < totalpage}">	
			<li><a href="myproductlist.do?page=${currentpage+1}">next</a></li>
		</c:if>
        <c:choose>	
       	 	<c:when test = "${currentpage==totalpage}">
        		<li class="disabled"><span>&raquo;</span></li>            
        	</c:when>
        	<c:otherwise>
        		<li><a href="myproductlist.do?page=${totalpage}">&raquo;</a></li>
        	</c:otherwise>
        </c:choose>
	</ul>	 		
	   <p>Total Pages:${totalpage}&nbsp;&nbsp;&nbsp;Current Page:${currentpage}</p>
    </div>
</c:if>


<script>
  	$("#table_order").rowspan({td:2});
   	$("#table_order").rowspan({td:1});
</script>

    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="frage/navbar_body_js.txt"%>
</body>
</html>