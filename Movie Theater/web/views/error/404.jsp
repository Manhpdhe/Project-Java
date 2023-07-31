<%-- 
    Document   : 404
    Created on : May 18, 2023, 10:02:05 AM 
    Author     : Admin
--%>

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
        <div style="width: 40%; height: 40%; margin: 0 auto; margin-top: 10%" class="">
            <img src="${pageContext.request.contextPath}/static/images/groovy-404.svg">
        </div>
        <h1 style="text-align: center">Hơn cả chùa Bà Đanh...</h1>
        <h3 style="text-align: center; color: gray">Không tìm thấy nội dung bạn cần tìm!</h3>
        <%@include file="/views/util/footer.jsp" %>
    </body>
</html>
