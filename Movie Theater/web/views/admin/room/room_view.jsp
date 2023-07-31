<%-- 
    Document   : room_insert
    Created on : May 19, 2023, 1:54:05 PM
    Author     : CaoThuLuDau
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Cinema</title>
        <style><%@include file="/static/css/CinemaCSS/cinemaController.css" %></style>
    </head>

    <body>
        <div class="head">
            <h1>Rạp chiếu phim</h1>
            <div class="cinema">
                <a href="#">
                    <div class="cinema-img-button" id="myBtn">
                        <img src="https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Plus-512.png" alt="">
                    </div>
                </a>
            </div>
        </div>
        <div class="directory">Rạp Cineplex/Phòng chiếu phim</div>
        <hr class="break-line">

        <div class="cinema-container">
            <!-- <h1 class="naming">Hệ thống rạp:</h1> -->
            <!-- Hiển thị tất cả các rạp chiếu -->

            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div>Xem sơ đồ phòng</div>
                    <div class="space"></div>
                    <div>Chỉnh sửa ghế</div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3><span>60</span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>10</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>06</span>
                </div>
                <div class="cinema-name">Phòng E21-C</div>
            </div>
            <!-- Kết thúc hiển thị các rạp chiếu  -->
            <!-- Thêm rạp chiếu -->
        </div>
        

    <!-- The Modal -->
    <div id="myModal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h1>Thêm phòng mới</h1>
            <form method="POST" action="${pageContext.request.contextPath}/admin/rooms/add"  onsubmit="return checkInput();"> 
                <div class="name">
                    <div class="input-box">
                        <label>Tên phòng</label><br>
                        <input type="text" id="inpRoomName" name="roomname" placeholder="Nhập tên phòng (max 5 kí tự)..." required>
                    </div>
                </div>
                <br>
                <div class="input-box">
                    <label>Số hàng ghế</label><br>
                    <input type="number" id="inpNoOfSeats" name="noOfSeats"  placeholder="Nhập số hàng ghế của phòng...">
                </div>
                <br>
                <div class="input-box">
                    <label>Số cột ghế</label><br>
                    <input type="number" id="inpNoOfSeats" name="noOfSeats"  placeholder="Nhập số cột ghế của phòng...">
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
<script><%@include file="/static/js/CinemaController/cinemaController.js" %></script>
</html>

<script>
                function checkInput() {
                    var inpRname = document.getElementById('inpRoomName').value;
                    if (inpRname.length > 5) {
                        alert('Room name is 5-letters length maximum.');
                        return false;
                    }
                    var inpNoOfSeats = document.getElementById('inpNoOfSeats').value;
                    if (inpNoOfSeats < 20 || inpNoOfSeats > 80) {
                        alert('Number of seats in a room is from 20 - 80.');
                        return false;
                    }

                    return true;
                }
</script>
</body>
</html>
