<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>

    <%@ include file="frage/navbar_head_meta.txt"%> 
    <%@ include file="frage/navbar_head_css.txt"%>

<title>Selfcare Portal</title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
	<script type="text/javascript" src="js/bootstrap.js"></script>    	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/retrieveProduct.js"></script>
</head>


<body>

 <!-- Fixed navbar -->
 <jsp:include page="frage/navbar_body_fix_bar.jsp"/>

 <br/>
 <div class="container"> 
     <div class="jumbotron">
        <h2>Welcome To SelfCare Portal !</h2>
        <c:choose>
	 		<c:when test="${currentUserName!=null}">
	 		   <p>Nice To See You Again, <c:out value="${currentUserName}"/></p>
	 		   <p><a href="./myproductlist.do" class="btn btn-primary btn-large">View My PurChased List &raquo;</a></p>
	 		</c:when>
        	<c:otherwise>
        		<p>If you are already our member, please <a href="./login.do" class="btn btn-primary btn-large">Login Right Now &raquo;</a></p>              
        		<p>If you don't have an ID, you can <a href="./register.do" class="btn btn-primary btn-large">Register Right Now &raquo;</a></p>
       		</c:otherwise>
	   </c:choose>         
 </div>	
     
  <div class="row">
        <div class="span3">
          <h3><em>Hot Sale!</em></h3>
          <p>Everyone Loves It! </p>
          <p><strong><c:out value ="${hotProducts[0].description}"/></strong> </p>
          <p>Cost Only:  <strong><c:out value ="${hotProducts[0].price}"/> </strong></p>          
          <c:choose>
	 		<c:when test="${currentUserName!=null}">
	 		<form method="post" action="./purchase.do?productID=${hotProducts[0].productNo}">
         	 <input type="submit" class="btn btn-primary" id="purchaseHot1" name="purchaseHot1" value="Buy Now!">
         	 </form>
	 		</c:when>
        	<c:otherwise>
        	  <a href="./login.do" class="btn btn-primary btn-large">Login To Buy</a>
       		</c:otherwise>
          </c:choose>          
        </div>
        <div class="span3">
          <h3><em>50% OFF!</em></h3>
          <p>Hurry Up! 2 Days Left! </p>
          <p><strong><c:out value ="${hotProducts[1].description}"/></strong> </p>
          <p>Cost Only: <strong> <c:out value ="${hotProducts[1].price}"/> </strong></p>
          <c:choose>
	 		<c:when test="${currentUserName!=null}">
	 		<form method="post" action="./purchase.do?productID=${hotProducts[1].productNo}">
         	 <input type="submit" class="btn btn-primary" id="purchaseHot2" name="purchaseHot2" value="Buy Now!">
         	 </form>
	 		</c:when>
        	<c:otherwise>
        	  <a href="./login.do" class="btn btn-primary btn-large">Login To Buy</a>
       		</c:otherwise>
          </c:choose> 
       </div>
        <div class="span3">
          <h3><em>Recommended!</em></h3>
          <p>100% Satisfied! Don't Hesitate!</p>
          <p><strong><c:out value ="${hotProducts[2].description}"/> </strong></p>
          <p>Cost Only:  <strong><c:out value ="${hotProducts[2].price}"/></strong> </p>
          <c:choose>
	 		<c:when test="${currentUserName!=null}">
	 		<form method="post" action="./purchase.do?productID=${hotProducts[2].productNo}">
         	 <input type="submit" class="btn btn-primary" id="purchaseHot3" name="purchaseHot3" value="Buy Now!">
         	 </form>
	 		</c:when>
        	<c:otherwise>
        	  <a href="./login.do" class="btn btn-primary btn-large">Login To Buy</a>
       		</c:otherwise>
          </c:choose> 
       </div>
    </div>

    <p></p>
    
	<form name="productList" method="post" action="./purchase.do" >		
	<table class="table table-striped" width="90%" border="1" id="table_product"> 
  	<tr>
    	<td width="10%" bgcolor="#66FFFF"><div align="center">&nbsp;</div></td>
    	<td width="20%" bgcolor="#66FFFF"><div align="center">Product NO</div></td>
    	<td width="45%" bgcolor="#66FFFF"><div align="center">Product Description </div></td>
   	 	<td width="20%" bgcolor="#66FFFF"><div align="center">Price</div></td>
  	</tr>
		<c:forEach items ="${products}" var="product">
	<tr>
		<c:choose>
	      <c:when test = "${currentUserName!=null}">
	      	<td><input type="checkbox" name="productID" value="${product.productNo}"></td>
	      </c:when>
	      <c:otherwise>
	      	<td><input type="checkbox" disabled = "disabled"></td>
	      </c:otherwise>
	    </c:choose>
		  <td>${product.productNo}</td>
          <td>${product.description}</td>
          <td>${product.price}</td>
    </tr>  	
		</c:forEach>
	</table>		
		<p>
  		<c:choose>
	 		<c:when test="${currentUserName!=null}">
	 		   <button type="button" class="btn btn-default" id="selectAll">Select All</button>
  			   <button type="button" class="btn btn-default" id="deselectAll">Deselect All</button>
               <input type="submit" class="btn btn-primary disabled "data-loading-text="Buy Selected Products" id="purchaseItems" name="purchase" value="Buy Selected Products">
	 		</c:when>
        	<c:otherwise>
        		<p><strong>Want all the amazing Products?</strong>  Please <a href="./login.do" class="btn btn-primary btn-large">Login</a> Or <a href="./register.do" class="btn btn-primary btn-large">Register</a></p>       		        		
       		</c:otherwise>
	   </c:choose>  
  		</p>
  	</form>	
	<ul class="pagination">
		<c:choose>
		<c:when test = "${currentPage==1}">
        	<li class="disabled"><span>&laquo;</span></li>            
        </c:when>
        <c:otherwise>
        	<li><a href="retrieveProductInfo.do?page=1">&laquo;</a></li>
        </c:otherwise>
        </c:choose>
          <c:if test = "${currentPage>2}">	
          	<li><a href="retrieveProductInfo.do?page=${currentPage-2}">${currentPage-2}</a></li>
          </c:if>
          <c:if test = "${currentPage>1}">	
 		  	<li><a href="retrieveProductInfo.do?page=${currentPage-1}">${currentPage-1}</a></li>
 		  </c:if>
  		  	<li  class="active"><span>${currentPage}</span></li> 
  		  <c:if test = "${currentPage<totalPage}">
  		  	<li><a href="retrieveProductInfo.do?page=${currentPage+1}">${currentPage+1}</a></li>
  		  </c:if>
  		  <c:if test = "${currentPage<totalPage-1}">
  		  	<li><a href="retrieveProductInfo.do?page=${currentPage+2}">${currentPage+2}</a></li>
  		  </c:if>
        <c:choose>	
       	 	<c:when test = "${currentPage==totalPage}">
        		<li class="disabled"><span>&raquo;</span></li>            
        	</c:when>
        	<c:otherwise>
        		<li><a href="retrieveProductInfo.do?page=${totalPage}">&raquo;</a></li>
        	</c:otherwise>
        </c:choose>
	</ul>	 		
	
      <footer>
        <p>&copy; /// </p>
      </footer>
 </div>
  
   <!-- Placed at the end of the document so the pages load faster -->
   <%@ include file="frage/navbar_body_js.txt"%>
</body>
</html>