<%-- 
    Document   : membership
    Created on : Jun 29, 2023, 9:00:16 PM
    Author     : HP
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style>
            <%@include file="/static/css/CustomerCSS/MemberShip/memberShip.css" %>
            <%@include file="/static/css/CustomerCSS/MemberShip/membership-done.css" %>
        </style>
    </head>
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
        <div class="header-membership">
            <h2>Membership Progress</h2>  
            <h2 class="my-card">My Card</h2>
            <%-- <b style="text-align: center;" class="text-my-card">You haven't registered for membership yet. <a href="#">Register Here</a></b> --%>
        </div>
        <div class="summary-membercard">
            <div class="upgrade-account-type">
                <div class="upgrade-member"><img src="https://www.cgv.vn/skin//frontend/cgv/default/images/bg-cgv/Member-milestone.png" width="32px" alt=""></div>
                <div class="upgrade-vip"><img src="https://www.cgv.vn/skin//frontend/cgv/default/images/bg-cgv/VIP-milestone.png" width="43px" alt=""></div>
                <div class="upgrade-vvip"><img src="https://www.cgv.vn/skin//frontend/cgv/default/images/bg-cgv/VVIP-milestone.png" width="40px" alt=""></div>
            </div>
            <div class="upgrade-card">
                <div id="myProgress">
                    <div class="line-center-grade"></div>
                    <div id="myBar" style="width: 0%; left: 0;"></div>
                </div>
            </div>
            <div class="upgrade-account-amount" style="font-size: 14px;">
                <div class="upgrade-member">0</div>
                <div class="upgrade-vip">4,000,000</div>
                <div class="upgrade-vvip">8,000,000</div>
            </div>
        </div>       
        <c:if test="${requestScope.membership ne null}">
            <div class="card-container">
                <div class="front" style="margin: 0 25%">
                    <div class="image">
                        <span style="color: white; font-weight: bolder;">MEMBERSHIP CARD</span>
                        <span style="color: white; font-weight: bolder;">LEVEL: MEMBER</span>
                    </div>
                    <div class="card-number-box">${requestScope.membership.getCardNumber()}</div>
                    <div class="flexbox">
                        <div></div>
                        <div class="box">
                            <span>Name Member</span>
                            <div class="card-holder-name">${requestScope.membership.getCustomer().getLastName()} ${requestScope.membership.getCustomer().getFirstName()}</div>
                        </div>
                        <div class="box-1">
                            <span>Register Date</span>
                            <div> <fmt:formatDate value="${requestScope.membership.getRegisterDate()}" pattern="dd/MM/yyyy"/></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope.membership eq null}">
            <b class="text-my-card">You haven't registered for membership yet. <a
                    href="${pageContext.request.contextPath}/profile/membership/register"
                    style="color:#619cbc;">Register Here</a></b>
        </c:if>
    </div>
                
</body>
<script><%@include file="/static/js/MemberShip/membership.js" %></script>
</html>
