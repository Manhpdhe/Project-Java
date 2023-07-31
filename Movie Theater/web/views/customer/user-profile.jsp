<%-- 
    Document   : user-profile
    Created on : 29-06-2023, 22:20:17
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/userProfile.css" %></style>
        <%@include file="/views/util/imports.jsp" %>


    </head>
    <body>
        <div class="container2">
            <div class="side-bar">

                <a href="${pageContext.request.contextPath}/home">
                    <img  src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg"> 
                </a>                               
                <ul>
                    <li><a href="${pageContext.request.contextPath}/profile">Profile</a><br></li>
                    <li><a href="${pageContext.request.contextPath}/profile/membership">Membership</a><br></li>
                    <li><a href="${pageContext.request.contextPath}/profile/history">Booking History</a><br></li>
                </ul>
            </div>

            <div class="form-box2 login">
                <form action="${pageContext.request.contextPath}/profile" method="post">
                    <input type="hidden" name="id" value="${sessionScope.customer.getCustomerId()}"/>
                    <div class="mem-info">
                        <p>Card Level: Student</p>
                        <p>Points: 250,000</p>
                    </div>
                    <c:if test="${requestScope.errmsg ne null}">
                        <p style="color:red">${requestScope.errmsg}</p>
                    </c:if>
                    <c:if test="${requestScope.infomsg ne null}">
                        <p style="color:blue">${requestScope.infomsg}</p>
                    </c:if>

                    <div class="name">
                        <div class="input-box2">
                            <label>First Name</label><br>
                            <input class="input-area" type="text" value="${sessionScope.customer.getFirstName()}" name="fname" id="fname" placeholder="Enter your first name" required="">
                            <p></p>
                        </div>
                        <br>
                        <div class="input-box2">
                            <label>Last Name</label><br>
                            <input class="input-area" type="text" value="${sessionScope.customer.getLastName()}" name="lname" id="lname" placeholder="Enter your last name" required="">
                            <p></p>
                        </div>

                        <br>
                    </div>

                    <div class="input-box2">
                        <label>Account name</label><br>
                        <input readonly class="input-area" type="text" value="${sessionScope.customer.getAccountName()}" name="inpUser" id="inpUser" placeholder="Enter your username" required="">
                        <p></p>
                    </div>
                    <br>

                    <div class="input-box2">
                        <label>Email</label><br>
                        <input class="input-area" type="email" value="${sessionScope.customer.getEmail()}" name="inpEm" id="inpEm" placeholder="Enter your e-mail" required="">
                        <p></p>
                    </div>
                    <br>
                    <div class="input-box2">
                        <label>Gender</label><br>
                        <select id="gender" value="gender" name="gender">
                            <c:forEach items="${requestScope.genderList}" var="g">
                                <option value="${g.getGenderId()}">${g.getGenderName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br>
                    <div class="input-box2">
                        <label>Date of Birth</label><br>
                        <input readonly type="date" name="inpDob" id="inpDob" value="${sessionScope.customer.getDOB()}" placeholder="Enter your Date of Birth" placeholder="Nhập Ngày Tháng Năm sinh...">
                        <p></p>
                    </div>
                    <br>

                    <div class="input-box2">
                        <label>Address</label><br>
                        <input class="input-area" type="text" value="${sessionScope.customer.getAddress()}" name="inpAdd" id="inpAdd" placeholder="Enter your address">
                        <p></p>
                    </div>
                    <br>
                    <div class="input-box2">
                        <label>Phone Number</label><br>
                        <input class="input-area" type="text" value="${sessionScope.customer.getPhoneNumber()}" name="inpPhone" id="inpPhone" placeholder="Enter your phone number" required="">
                        <p></p>
                    </div>
                    <br>
                    <div class="change-pass">
                        <label><input type="checkbox" name="checkChange" id="changePassCheckbox" onclick="togglePasswordFields()">I want to change password</label>
                    </div>
                    <br>
                    <div class="input-box2 hidden" id="oldPassBox">
                        <label>Old Password</label><br>
                        <input class="input-area" type="password" name="inpOldPass" id="inpOldPass" placeholder="Enter your old password">
                        <p></p>
                    </div>
                    <br>
                    <div class="input-box2 hidden" id="newPassBox">
                        <label>New Password</label><br>
                        <input class="input-area" type="password" name="inpNewPass" id="inpNewPass" placeholder="Enter your new password">
                        <p></p>
                    </div>
                    <br>
                    <button type="submit" class="btn2">Update</button>
                </form>
            </div>
        </div>
    </body>
    <script>
        function togglePasswordFields() {
            var changePassCheckbox = document.getElementById("changePassCheckbox");
            var oldPassBox = document.getElementById("oldPassBox");
            var newPassBox = document.getElementById("newPassBox");

            if (changePassCheckbox.checked) {
                oldPassBox.classList.remove("hidden");
                newPassBox.classList.remove("hidden");
            } else {
                oldPassBox.classList.add("hidden");
                newPassBox.classList.add("hidden");
            }
        }
    </script>
</html>
