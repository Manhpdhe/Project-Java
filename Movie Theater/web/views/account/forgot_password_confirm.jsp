<%-- 
    Document   : forgot_password_confirm
    Created on : 30-06-2023, 17:18:51
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/forgot_password_confirm.css" %></style>
    </head>
    <body>
        <div class="full-container">
            <div class="container">
                <form action="${pageContext.request.contextPath}/recover" method="post">
                    <div class="input-box" id="newPassBox">
                        <label>New Password</label><br>
                        <input class="input-area" type="password" name="newPass" id="inpNewPass" placeholder="Enter your new password" required="">
                        <p></p>
                    </div>
                    <div class="input-box" id="oldPassBox">
                        <label>Confirm Password</label><br>
                        <input class="input-area" type="password" name="confPass" id="confPass" placeholder="Confirm your password" required="">
                        <p></p>
                    </div>
                    <c:if test="${requestScope.errmsg != null}">
                        <p style="color: red">${requestScope.errmsg}</p>
                    </c:if> 
                    <c:if test="${requestScope.infomsg != null}">
                        <p>${requestScope.infomsg}</p>
                    </c:if> 
                    <div>
                        <button type="submit" class="btn">Continue</button>
                </form>
            </div>

            <a href="${pageContext.request.contextPath}/login" class="info">Back to Sign in</a>

        </div>
        <div class="image"><img
                src="https://images.unsplash.com/photo-1617914309185-9e63b3badfca?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80">
        </div>
    </div>

</body>
</html>
