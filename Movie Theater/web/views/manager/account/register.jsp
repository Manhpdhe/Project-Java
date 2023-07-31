<%-- 
    Document   : register
    Created on : 17-05-2023, 10:33:36
    Author     : Admin
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>
     <%@include file="/views/util/imports.jsp" %>
</head>

<body class="login-register-page">
    
    <c:set var="userUnsub" scope="request" value="${requestScope.unsubmittedUser}"/>
    
    <div class="form-box login">
        <div class="text">
            <div class="Welcome">
                <h1>Đăng ký tài khoản quản lý</h1>
            </div>
            <br>
            <div class="slogan">
<!--                <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit.</p> -->
            </div>
            
        </div>
        
        <form id="register-form" action="manage/register" method="POST">
    
            <div class="input-box">
                <label>Tên</label><br>
                <input type="text" id="fname" name="firstname" placeholder="Nhập tên..." value ="${userUnsub eq null ? '' : userUnsub.getFirstName()}">
                                <p></p>
            </div>
            <br>
        
            <div class="input-box">
                <label>Họ</label><br>
                <input type="text" id="lname" name="lastname" placeholder="Nhập họ..." value ="${userUnsub eq null ? '' : userUnsub.getLastName()}">
                                    <p></p>

            </div>        
            <br>
            
            <div class="input-box <c:if test="${requestScope.errmsgs.containsKey('duplicateUserName')}">error</c:if>">
                <label>Tên đăng nhập</label><br>
                <input type="text" id="inpUser" name="username" placeholder="Nhập tên đăng nhập..." value ="${userUnsub eq null ? '' : userUnsub.getUserName()}">
                <p><c:if test="${requestScope.errmsgs.containsKey('duplicateUserName')}">${requestScope.errmsgs.get("duplicateUserName")}</c:if></p>

            </div>
            <br>
            <div class="input-box">
                <label>Mật khẩu</label><br>
                <input type="password" id="inpPass" name="password" placeholder=".........">
                                <p></p>

            </div>
            <br> 
            
            <div class="input-box <c:if test="${requestScope.errmsgs.containsKey('duplicateEmail')}">error</c:if>"> 
                <label>Email</label><br>
                <input class="input-box" type="text" id="inpEm" name="email" placeholder="Nhập Email..." value ="${userUnsub eq null ? '' : userUnsub.getEmail()}">
                                <p><c:if test="${requestScope.errmsgs.containsKey('duplicateEmail')}">${requestScope.errmsgs.get("duplicateEmail")}</c:if></p>

            </div>
            <br>
            
            <div class="input-box">
                <label>Giới tính</label><br>
                <select id ="gender" name="gender" value ="${userUnsub eq null ? '' : userUnsub.getGender().getGenderId()}">
                    <c:forEach items="${requestScope.genderList}" var="g">
                        <option value="${g.getGenderId()}">${g.getGenderDesc()}</option>
                    </c:forEach>
                </select>
                                                <p></p>

            </div> 
            <br>
            
            <div class="input-box">
                <label>Ngày, tháng, năm sinh</label><br>
                <input type="date" id="inpDob" name="dob" placeholder="Nhập Ngày Tháng Năm sinh..."
                       <c:if test="${userUnsub ne null}">
                           value ="<fmt:formatDate type="date" value="${userUnsub.getDOB()}" pattern="yyyy-MM-dd" />"
                       </c:if>
                       >
                                <p></p>

            </div>
            <br>
            <div class="input-box">
                <label>Địa chỉ</label><br>
                <input type="text" id="inpAdd" name="address" placeholder="Nhập địa chỉ..." value ="${userUnsub eq null ? '' : userUnsub.getAddress()}" required>
                                <p></p>

            </div>
            <br>
            <div class="input-box <c:if test="${requestScope.errmsgs.containsKey('duplicatePhoneNumber')}">error</c:if>">
                <label>Số điện thoại</label><br>
                <input type="text" id="inpPhone" name="phonenumber" placeholder="Nhập số điện thoại..." value ="${userUnsub eq null ? '' : userUnsub.getPhoneNumber()}" required>
                                <p><c:if test="${requestScope.errmsgs.containsKey('duplicatePhoneNumber')}">${requestScope.errmsgs.get("duplicatePhoneNumber")}</c:if></p>

            </div>
            <br>
            <div class="remember-forgot">
                <label><input id="tac-check" type="checkbox">Tôi đồng ý với các Điều Khoản Sử Dụng</label>
            </div>
            <br>
            <p id="register-error-msg" style="color:red"></p>
            <div>
                <button type="submit" class="btn">Đăng ký</button>
            </div>
            <br>
            <div class="login-register">
                <p>Đã có tài khoản rồi? <a href="login" class="login-link">Đăng nhập</a></p>
            </div>
        </form>
    </div>
    
        
    </div>
    
    <script src="${pageContext.request.contextPath}/static/js/registerIndex.js"></script>

</body>
</html>
