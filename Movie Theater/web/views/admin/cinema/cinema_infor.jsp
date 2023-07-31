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
    <link rel="stylesheet" href="./CSS/add-cinema-infor.css">
    <style><%@include file="/static/css/CinemaCSS/cinemaController.css" %></style>
     <%@include file="/views/util/imports.jsp" %>
</head>

<body>
    <div class="head">
        <h1>Rạp Cineplex</h1>
        <div class="cinema">
            <a href="#">
                <div class="cinema-img-button" id="myBtn">
                    <img src="https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Plus-512.png" alt="">
                </div>
            </a>
        </div>
    </div>
    
    <hr class="break-line">

    <div class="cinema-container">
        <!-- <h1 class="naming">Hệ thống rạp:</h1> -->
        <!-- Hiển thị tất cả các rạp chiếu -->
        <!-- Khu vực dùng để foreach -->
        <div class="cinema">
            <div class="cinema-img">
                <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                    alt="cinema">
            </div>
            <div class="center">
                <div class="cinema-infor">Thông tin rạp</div>
                <div class="space"></div>
                <div>Chỉnh sửa ghế</div>
            </div>
            <div class="cinema-name">Rạp Cineplex Thái Nguyên</div>
        </div>
        <!-- Kết thúc khu vực đó -->
        <div class="cinema">
            <div class="cinema-img">
                <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                    alt="cinema">
            </div>
            <div class="center">
                <div class="cinema-infor">Thông tin rạp</div>
                <div class="space"></div>
                <div>Chỉnh sửa ghế</div>
            </div>
            <div class="cinema-name">Rạp Cineplex Thái Nguyên</div>
        </div>
        <div class="cinema">
            <div class="cinema-img">
                <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                    alt="cinema">
            </div>
            <div class="center">
                <div>Thông tin rạp</div>
                <div class="space"></div>
                <div>Chỉnh sửa</div>
            </div>
            <div class="cinema-name">Rạp Cineplex Thái Nguyên</div>
        </div>
        <div class="cinema">
            <div class="cinema-img">
                <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                    alt="cinema">
            </div>
            <div class="center">
                <div>Thông tin rạp</div>
                <div class="space"></div>
                <div>Chỉnh sửa</div>
            </div>
            <div class="cinema-name">Rạp Cineplex Thái Nguyên</div>
        </div>
        <div class="cinema">
            <div class="cinema-img">
                <img src="https://files.betacorp.vn/files/ecm/2018/07/04/35359362-242694916502971-7052850785574453248-n-103924-040718-45.png"
                    alt="cinema">
            </div>
            <div class="center">
                <div>Thông tin rạp</div>
                <div class="space"></div>
                <div>Chỉnh sửa ghế</div>
            </div>
            <div class="cinema-name">Rạp Cineplex Thái Nguyên</div>
        </div>
        <!-- Kết thúc hiển thị các rạp chiếu  -->
    </div>
   

    <%@include file="cinema_update.jsp" %>
    <%@include file="cinema_create.jsp" %>
    
</body>
<script><%@include file="/static/js/CinemaController/cinemaController.js" %> </script>

</html>