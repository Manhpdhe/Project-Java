<%-- 
    Document   : login
    Created on : 17-05-2023, 10:33:29
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body class="login-register-page">

    <div class="form-box login">
        <div class="text">
            <div class="Welcome">
                <h1>Đăng nhập quản lý</h1>
                <!-- <h3>Lorem ipsum dolor sit, amet consectetur adipisicing elit.</h3>  -->
            </div>
            <br>
            <div class="slogan">
                <!-- <h1>Welcome Back!</h1> -->
                <!--<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit.</p> -->
            </div>
        </div>
        <br>
        
        <c:if test="${requestScope.errmsg ne null}">
                <p style="color:red">${requestScope.errmsg}</p>
        </c:if>
                <c:if test="${requestScope.warnmsg ne null}">
                <p style="color:orange">${requestScope.warnmsg}</p>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/managerlogin" method="POST">
            <input type="hidden" name="destination" value="${requestScope.destination}" />
            <div class="input-box">
                <label>Tên đăng nhập</label><br>
                <input type="text" id="inpUser" name="username" placeholder="Nhập tên đăng nhập..." required>
            </div>
            <br>
            <div class="input-box">
                <label>Mật khẩu</label><br>
                <input type="password" id="inpPass" name="password" placeholder="Nhập mật khẩu..." required>
            </div>
            <br>
            <div class="remember-forgot">
                <label><input type="checkbox" name="rememberuser"> Ghi nhớ tôi</label>
                <a href="#">Quên mật khẩu?</a>
            </div>
            <br>

            <div>
                <button type="submit" class="btn">Đăng nhập</button>
            </div>
            <br>
            <div class="login-register">
                <p>Chưa có tài khoản? <a href="register" class="register-link">Đăng ký</a> ngay!</p>
            </div>
        </form>
    </div>
</body>
</html>
