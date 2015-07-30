<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<c:set var="requestURL" value="${pageContext.request.requestURL}"></c:set>

<c:set var="productlistURL" value="retrieveProduct.jsp"></c:set>
<c:set var="historyorderURL" value="myproductlist.jsp"></c:set>
<c:set var="productlistActive" value=""></c:set>
<c:set var="historyorderActive" value=""></c:set>

<c:if test="${fn:contains(requestURL, productlistURL)}">
 <c:set var="productlistActive" value="class='active'"></c:set>
 <c:set var="historyorderActive" value=""></c:set>  
</c:if>

<c:if test="${fn:contains(requestURL, historyorderURL)}">
 <c:set var="historyorderActive" value="class='active'"></c:set>
 <c:set var="productlistActive" value=""></c:set>  
</c:if>
    
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <h3 class="text-muted">Selfcare Portal</h3>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav" id="left_navbar">
            <li <c:out value="${productlistActive}" escapeXml="false"/>
                id="bar_productlist"><a href="./retrieveProductInfo.do">Products List</a></li>
            <c:if test="${!empty currentUserName}">
              <li <c:out value="${historyorderActive}" escapeXml="false"/> 
                id="bar_historyorder"><a href="./myproductlist.do">Purchase ProductList</a></li>
            </c:if>  
          </ul>
          <ul class="nav navbar-nav navbar-right" id="right_navbar">
            <c:if test="${!empty currentUserName}">
              <li id="currentUser">
                  <a href="#">
                      <c:out value="${currentUserName}"/>
                  </a>
              </li>
            </c:if>
            <c:if test="${empty currentUserName}">
              <li id="bar_login">             
               <form class="navbar-form navbar-right" role="form" method="get" action="./login.do">
               <button name="login_btn" class="btn btn-sm btn-primary">Login</button>
               </form>
              </li>
            </c:if>
            
            <c:if test="${!empty currentUserName}">
             <li id="bar_logout">             
              <form class="navbar-form navbar-right" role="form" method="post" action="./logout.do">
              <button name="logout_btn" class="btn btn-sm btn-primary">Logout</button>
              </form>
             </li>             
            </c:if>
            
            <c:if test="${empty currentUserName}">
            <li id="bar_register">
              <form class="navbar-form navbar-right" role="form" method="get" action="./register.do">
               <button name="register_btn" class="btn btn-sm btn-primary">Register</button>
              </form>
            </li>
            </c:if>
          </ul>
        </div>
      </div>
    </div>