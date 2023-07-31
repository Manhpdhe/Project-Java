<%-- 
    Document   : payment_result
    Created on : Jun 21, 2023, 11:19:18 AM
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
        <form action="${pageContext.request.contextPath}/home" method="GET" style="text-align: center;">
                <h1>Order 
                    <c:choose>
                        <c:when test="${requestScope.success}">
                            Successfull!
                        </c:when>
                        <c:otherwise>
                            Cancelled!
                        </c:otherwise>
                    </c:choose>
                </h1> 
                <button type="submit" style="margin: 0 auto;" class="btn cancel">Back to Homepage</button>
            </form>
        <div style="margin: 15%;"></div>
        <%@include file="/views/util/footer.jsp" %>
    </body>
</html>
