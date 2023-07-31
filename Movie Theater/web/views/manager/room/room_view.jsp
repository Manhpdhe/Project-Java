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
                <%@include file="/views/util/imports.jsp" %>

    </head>

    <body>
        <div style="margin: 100px"></div>
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
        <c:set var="cinema" value="${requestScope.cinema}"/>
        <div class="head">
            <h1>Rạp chiếu phim ${cinema.branchName}</h1>
            
        </div>
        <div class="directory">Rạp ${cinema.branchName}/Phòng chiếu phim</div>
        <hr class="break-line">

        <div class="cinema-container">
            <!-- <h1 class="naming">Hệ thống rạp:</h1> -->
            <!-- Hiển thị tất cả các rạp chiếu -->
            <c:forEach items="${requestScope.rooms}" var="room">
            <div class="cinema">
                <div class="cinema-img">
                    <img src="https://www.cgv.vn/media/imax/goldclass-3.png" alt="cinema">
                </div>
                <div class="center">
                    <div><a style="color: white" href="${pageContext.request.contextPath}/manager/seat?room=${room.roomID}">Xem sơ đồ phòng</a></div>
                    <div class="space"></div>
                </div>
                <div class="seat-number">
                    <h3>Số lượng ghế:</h3>
                    <span>
                        <c:forEach var="seatNo" items="${requestScope.roomSeats}">
                            <c:if test="${room.roomID eq seatNo.key}">
                                ${seatNo.value}
                            </c:if>
                        </c:forEach>
                    </span>
                </div>
                <div class="seat-number">
                    <h3>Số hàng ghế:</h3><span>${room.numRows}</span>
                </div>
                <div class="seat-number">
                    <h3>Số cột ghế:</h3><span>${room.numCols}</span>
                </div>
                <div class="cinema-name">${room.roomName}</div>
            </div>
            </c:forEach>
            <!-- Kết thúc hiển thị các rạp chiếu  -->
            <!-- Thêm rạp chiếu -->
        </div>
        <div style="margin-left: 70%" class="cinema">
                <a href="#">
                    <div class="cinema-img-button" id="myBtn">
                        <img src="https://cdn4.iconfinder.com/data/icons/web-ui-color/128/Plus-512.png" alt=""><span>Thêm phòng</span>
                    </div>
                </a>
            </div>

    <!-- The Modal -->
    <div id="myModal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h1>Thêm phòng mới</h1>
            <form method="POST" action="${pageContext.request.contextPath}/headmanage/rooms?action=create"  onsubmit="return checkInput();"> 
                <input type="hidden" name="branch-id" value="${cinema.branchID}">
                <div class="name">
                    <div class="input-box">
                        <label>Tên phòng</label><br>
                        <input type="text" id="inpRoomName" name="room-name" placeholder="Nhập tên phòng (max 5 kí tự)..." required>
                    </div>
                </div>
                <br>
                <div class="input-box">
                    <label>Số hàng ghế</label><br>
                    <input type="number" id="inpNumRows" name="num-rows"  placeholder="Nhập số hàng ghế của phòng...">
                </div>
                <br>
                <div class="input-box">
                    <label>Số cột ghế</label><br>
                    <input type="number" id="inpNumCols" name="num-cols"  placeholder="Nhập số cột ghế của phòng...">
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
                    var inpNumRows = document.getElementById('inpNumRows').value;
                    if (inpNumRows < 5 || inpNumRows > 30) {
                        alert('Number of rows in a room is from 5 - 30.');
                        return false;
                    }
<<<<<<< HEAD
                    var inpColRows = document.getElementById('inpColRows').value;
                    if (inpColRows < 5 || inpColRows > 30) {
=======
                    var inpNumCols = document.getElementById('inpColRows').value;
                    if (inpNumCols < 5 || inpNumCols > 30) {
>>>>>>> c1be50536f6cae1ccb0f83023b5322772edfb6cf
                        alert('Number of cols in a room is from 5 - 30.');
                        return false;
                    }
                    return true;
                }
</script>
</body>
</html>
