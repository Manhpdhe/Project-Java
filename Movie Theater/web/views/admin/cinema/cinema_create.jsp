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
    <title>Cinema Controller</title>
    <style><%@include file="/static/css/CinemaCSS/cinemaController.css" %></style>
</head>

<body>
    <!-- Cái này là thêm rạp nè -->
    <!-- The Modal -->
    <div id="myModal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <span id="close">&times;</span>
            <h1>Thêm rạp chiếu mới</h1>
            <form method="POST" action="${pageContext.request.contextPath}/admin/rooms/add"
                onsubmit="return checkInput();">
                <div class="name">
                    <div class="input-box">
                        <label>Tên rạp chiếu</label><br>
                        <input type="text" id="cinema-name" name="cinema-name" placeholder="Nhập tên rạp chiếu..."
                            required>
                    </div>
                </div>
                <br>
                <div class="input-box">
                    <label>Địa chỉ rạp</label><br>
                    <input type="text" id="cinama-location" name="cinama-location" placeholder="Nhập địa chỉ rạp">
                </div>
                <br>
                <div>
                    <button type="submit" class="btn">Thêm phòng</button>
                </div>
                <br><!-- <br> -->
                <br>
            </form>
        </div>

    </div>
</body>

</html>
