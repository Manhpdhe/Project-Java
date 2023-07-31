<%-- 
Document   : Cinema_UpdateCinema
Created on : 21-May-2023, 15:01:06
Author     : NGUYEN THANH LUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <style><%@include file="/static/css/CinemaCSS/cinemaController.css" %></style>
        <%@include file="/views/util/imports.jsp" %>
    </head>

    <body>
        <header style="padding: 0px 20px" class="header">
            <img style="width: 17%" src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg"> 
            <div>
                <form action="${pageContext.request.contextPath}/headmanage/revenue" method="post">
                    <select name="cinema-id" id="cinema-select">
                        <option value="allBranch">
                            All Cinema
                        </option>
                        <c:forEach items="${requestScope.branchList}" var="branch">
                            <option value="${branch.branchID}">
                                ${branch.branchName}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit">Confirm</button>
                </form>
            </div>
            <div>Ticket Sale & Revenue</div>
            <div>Admin name</div>

            <!--            <div>Caothuludau</div>-->
        </header>
        <div class="head">
            <h1>Quản lý rạp Cineplex</h1>

        </div>

        <hr class="break-line">
        <c:if test="${requestScope.msg ne null}">
            <p class="msg-box">${requestScope.msg}</p>
        </c:if>
        <!--<hr class="break-line">-->
        <div style="margin-left: 70%; margin-top: 30px" class="cinema">
            <a href="#">
                <div class="cinema-img-button" id="myBtn">
                    <img src="https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Plus-512.png" alt=""><span>Thêm rạp</span>
                </div>
            </a>
        </div>
        <div class="cinema-container">

            <!-- <h1 class="naming">Hệ thống rạp:</h1> -->
            <!-- Hiển thị tất cả các rạp chiếu -->
            <c:forEach items="${requestScope.cinemas}" var="cinema">
                <div class="cinema">
                    <div class="cinema-img">
                        <a href="${pageContext.request.contextPath}/headmanage/rooms?branchID=${cinema.branchID}">
                            <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                                 alt="cinema">
                        </a>
                    </div>
                    <div class="center">
                        <div class="cinema-infor">Thông tin rạp</div>
                        <div class="space"></div>
                        <div>Quản lý rạp</div>
                    </div>
                    <div class="cinema-name">${cinema.branchName}</div>
                </div>
            </c:forEach>
            <!-- Kết thúc khu vực đó -->
        </div>


        <%@include file="cinema_update.jsp" %>
        <%@include file="cinema_create.jsp" %>

    </body>
    <script><%@include file="/static/js/CinemaController/cinemaController.js" %></script>

</html>