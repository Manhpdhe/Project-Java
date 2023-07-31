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
        <form action="${pageContext.request.contextPath}/payment" method="POST" style="text-align: center;">
            <input type="hidden" name="orderType" value="${requestScope.orderType}"/>
            <input type="hidden" name="amount" value="${requestScope.amount}"/>
            <input type="hidden" name="bankCode" value="${requestScope.bankCode}"/>
            <input type="hidden" name="language" value="${requestScope.language}"/>
                <h1>Một bước nữa để hoàn thành đặt vé!</h1> 
                <button type="submit" style="margin: 0 auto;" class="btn cancel">Hoàn thành thanh toán qua VNPAY</button>
            </form>
        <div style="margin: 15%;"></div>
        <%@include file="/views/util/footer.jsp" %>
    </body>
    <script>
        
    </script>
</html>
