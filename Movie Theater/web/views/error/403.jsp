<%-- 
    Document   : 403
    Created on : May 18, 2023, 10:02:20 AM
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
        <div style="width: 30%; height: 30%; margin: 0 auto; margin-top: 10%" class="">
            <img src="${pageContext.request.contextPath}/static/images/groovy-403.svg">
        </div>
        <h1 style="text-align: center">Cũng biết mò mẫm đấy...</h1>
        <h3 style="text-align: center; color: gray">Bạn không có quyền truy cập vào chức năng này.</h3>
        <h3 style="text-align: center; color: gray">Nếu đây là một sự sai sót, hãy liên hệ với bộ phận quản trị hệ thống.</h3>
        <%@include file="/views/util/footer.jsp" %>
        <%@include file="/views/util/footer.jsp" %>
    </body>
</html>
