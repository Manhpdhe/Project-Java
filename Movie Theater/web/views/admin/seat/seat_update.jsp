<%-- 
    Document   : Seat_UpdateSeat
    Created on : 21-May-2023, 15:01:06
    Author     : NGUYEN THANH LUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>

        <!-- The update Modal -->
        <div id="update-modal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
                <!--<span class="close">&times;</span>-->
                <form action="${pageContext.request.contextPath}/admin/seats?action=update" method="POST">
                    <input type="text" required name="seatId" value="${sessionScope.seat.seatID}" hidden/>
                    <label for="rowNo">RowNo</label>
                    <input type="text" required id="rowNo" name="rowNo" value="${sessionScope.seat.rowNo}"/>
                    <label for="seatNo">SeatNo</label>
                    <input type="number" required id="seatNo" name="seatNo" value="${sessionScope.seat.seatNo}"/>
                    <label for="roomId">RoomID</label>
                    <select name="roomId" required id="roomId">
                        <option value="${sessionScope.seat.roomID}" selected hidden>${sessionScope.seat.roomID}</option>
                        <c:forEach items="${sessionScope.listRoomId}" var="roomId">
                            <option value="${roomId}">${roomId}</option>
                        </c:forEach>
                    </select>
                    <div style="color: red">${requestScope.msg}</div>
                    <input type="submit" value="UPDATE"/>
                </form>
            </div>

        </div>

    </body>
</html>
