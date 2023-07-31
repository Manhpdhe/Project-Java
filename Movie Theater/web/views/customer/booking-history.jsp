<%-- 
    Document   : booking-history
    Created on : 15-07-2023, 10:25:17
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
        <style><%@include file="/static/css/bookingHistory.css" %></style>
    </head>
    <body>
        <div class="container">
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
            <div class="dashboard">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Movie</th>
                            <th>Time Range</th>
                            <th>Order Date</th>
                            <th>List of Seats</th>
                            <th>Room</th>
                            <th>Cinema Branch</th>
                            <th>Total Price</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.history}" var="h">
                            <tr>
                                <td>${h.getTicketHistoryId()}</td>
                                <td>${h.getMovieName()}</td>
                                <td>${h.getTimeString()}</td>
                                <td>${h.getOrderTime()}</td>
                                <td>${h.getSeatsString()}</td>
                                <td>${h.getRoomName()}</td>
                                <td>${h.getBranchName()}</td>
                                <td>${h.getTotalPrice()}đ</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${h.getPaymentStatus() eq 1}">
                                            <label style="color: green">Completed</label>
                                        </c:when>
                                        <c:when test="${h.getPaymentStatus() eq 2}">
                                            <label style="color: blue">Pending <br/>
                                                <a href="${h.getPaymentLink()}">Click here to finish payment</a>)
                                            </label>
                                        </c:when>
                                        <c:when test="${h.getPaymentStatus() eq 3}">
                                            <label style="color: red">Cancelled</label>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </body>
</html>
