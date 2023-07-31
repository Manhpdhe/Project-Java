<%-- 
    Document   : book_confirmation
    Created on : Jun 15, 2023, 10:38:30 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>
        <%@include file="/views/util/header.jsp" %>
        <div style="margin: 15%;"></div>
        <form action="${pageContext.request.contextPath}/home" method="POST" style="text-align: center;">
                <h1>Order Successfull!</h1>
                <h2>Your ticket have been ordered successfully</h2>     
                <p>If you have any trouble, please contact us at <a href="#">hoidap@groovycineplex.vn</a></p> 
                <p>or hotline: <span style="font-weight: bold;">0123456789</span></p>  
                <p>View your booking history <a href="">here</a></p>   
                <button type="submit" style="margin: 0 auto;" class="btn cancel">Back to Homepage</button>
            </form>
        <div style="margin: 15%;"></div>
        <%@include file="/views/util/footer.jsp" %>
    </body>
</html>
