<%-- 
    Document   : Seat_CreateSeat
    Created on : 21-May-2023, 16:02:20
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
        <!-- The Modal -->
        <div id="create-modal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <h3>Create Seat</h3>
                <span class="close">&times;</span>
                <form action="${pageContext.request.contextPath}/admin/seats?action=create" method="POST">
                    <label for="rowNo">RowNo</label>
                    <input type="text" id="rowNo" required name="rowNo" value="${requestScope.seat.rowNo}"/>
                    <label for="seatNo">SeatNo</label>
                    <input type="number" id="seatNo" required name="seatNo" value="${requestScope.seat.seatNo}"/>
                    <label for="roomId">RoomID</label>
                    <select name="roomId" id="roomId" required>
                        <c:forEach items="${requestScope.listRoomId}" var="roomId">
                            <option value="${roomId}">${roomId}</option>
                        </c:forEach>
                    </select>
                    <div style="color: red">${requestScope.msg}</div>
                    <input type="submit" value="CREATE"/>
                </form>
            </div>

        </div>
    </body>
</html>
