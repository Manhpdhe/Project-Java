<%-- 
    Document   : account-veri
    Created on : 27-06-2023, 13:15:19
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
        <style><%@include file="/static/css/styleAccountVeri.css" %></style>

    </head>
    <body>
        <div class="full-container">
            <div class="container">
                <h2>Verify Your Account</h2>
                <p>
                    We emailed you the six digit code to ${sessionScope.customer.getEmail()} <br />
                    Enter the code below to confirm your email address
                </p>
                <form action="${pageContext.request.contextPath}/verify" method="post">
                    <input type="hidden" name="vertype" value="${requestScope.vertype}">
                    <div class="code-container">
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                        <input type="number" name="otp" class="code" placeholder="0" min="0" max="9" required>
                    </div>

                    <c:if test="${requestScope.errmsg != null}">
                        <p style="color: red">${requestScope.errmsg}</p>
                    </c:if> 
                    <c:if test="${requestScope.infomsg != null}">
                        <p>${requestScope.infomsg}</p>
                    </c:if> 

                    <div>
                        <button type="submit" class="btn btn-primary">Verify</button>
                    </div>
                </form>


                <small class="info">
                    If you didn't receive a code !! <a href="${pageContext.request.contextPath}/verify?resend=1"> RESEND</a>
                </small>

            </div>
            <div class="image"><img
                    src="https://images.unsplash.com/photo-1617914309185-9e63b3badfca?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80">
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/static/js/accountVeri.js"></script>
    </body>
</html>
