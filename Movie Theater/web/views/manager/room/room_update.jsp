<%-- 
    Document   : room_update
    Created on : May 22, 2023, 9:50:42 PM
    Author     : CaoThuLuDau
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room Update</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>
        <%--<%@include file="/views/util/header.jsp" %>--%>

        <!-- The Modal -->
        <div id="update-room" class="room">

            <!-- Modal content -->
            <div class="insert-room">
                <span class="close">&times;</span>
                <form action="${pageContext.request.contextPath}/admin/rooms/edit" method="POST" onsubmit="return checkInput();"> 
            <input type="hidden" id="inpRID" name="roomID" value=${requestScope.roomToBeUpdated.getRoomID()}>
            <div class="name">
                <div class="input-box">
                    <label>Tên phòng</label><br>
                    <input type="text" id="inpRname" name="roomName" placeholder="Nhập tên phòng (max 5 kí tự)..." 
                           value="${requestScope.roomToBeUpdated.getRoomName()}" required>
                </div>
            </div>
            <br>
            <div class="input-box">
                <label>Số lượng ghế</label><br>
                <input type="number" id="inpNoOfSeats" name="noOfSeats" placeholder="Nhập số lượng ghế của phòng..."
                       value=${requestScope.roomToBeUpdated.getNoOfSeats()} >
            </div>
            <br>
            <div class="input-box">
                <label>Rạp chiếu</label><br>
                <select id ="branch" name="branchID">
                    <c:forEach items="${requestScope.branchList}" var="b">
                        <option value="${b.getBranchID()}" <c:if test="${b.getBranchID() == requestScope.roomToBeUpdated.getBranch().getBranchID()}">selected</c:if>>${b.getBranchName()}</option>
                    </c:forEach>
                </select>   
            </div> 
            <br>
            <br>
            <div>
                <button type="submit" class="btn">Sửa thông tin phòng</button>
            </div>
        </form>
            </div>

        </div>
        <%--<%@include file="/views/util/footer.jsp" %>--%>
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
