<%-- 
    Document   : seat.
    Created on : 20-May-2023, 16:49:01
    Author     : NGUYEN THANH LUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Seat Booking</title>
        <style><%@include file="/static/css/SeatCSS/seatController.css" %></style>
    </head>

    <body>
        <ul class="showcase">
            <li>
                <div class="seat-blank" id="disable"></div>
                <small>Delete seat</small>
            </li>
            <li>
                <div class="seat selected" id="normal"></div>
                <small>Normal Seat</small>
            </li>
            <li>
                <div class="seat occupied" id="gold"></div>
                <small>VIP Seat</small>
            </li>
        </ul>
        <div class="container">
            <div class="screen"></div>
            <form data-numrow="${requestScope.room.getNumRows()}" data-numcol="${requestScope.room.getNumCols()}" action="seat" method="POST" class="form">
                <input value="${requestScope.room.getRoomID()}" type="text" name="roomId" hidden/>
                <input value="${requestScope.room.getNumRows()}" type="text" name="numRows" hidden/>
                <input value="${requestScope.room.getNumCols()}" type="text" name="numCols" hidden/>
                <c:forEach begin="0" end="${requestScope.room.getNumRows()-1}" var="row">
                    <div class="row">
                        <div data-row="${row}" class="row-mark">${row}</div>
                        <c:forEach begin="0" end="${requestScope.room.getNumCols()-1}" var="col">
                            <c:if test="${requestScope.seatMatrix[row][col] eq null}">
                                <div class="seat seat-blank row-${row} col-${col}">  
                                        <input value="-1" type="text" name="seatId" hidden class="seatID"/>
                                        <input type="text" hidden value="-1" name="seatType"/>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.seatMatrix[row][col].getSeatType().getSeatTypeId() eq 1}">
                                <div class="seat default normal row-${row} col-${col}">
                                        <input value="${requestScope.seats[row][col].getSeatId()}" type="text" name="seatId" class="seatID" hidden/>
                                        <input type="text" hidden value="1" name="seatType" class="seatType"/>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.seatMatrix[row][col].getSeatType().getSeatTypeId() eq 2}">
                                <div class="seat default vip row-${row} col-${col}">
                                        <input value="${requestScope.seats[row][col].getSeatId()}" type="text" name="seatId" class="seatID" hidden/>
                                        <input type="text" hidden value="2" name="seatType" class="seatType"/>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:forEach>
                
              
            <div class="row-container">
                <div class="row">
                    <!-- <div class="row-mark"></div> -->
                    <c:forEach begin="0" end="${requestScope.room.getNumCols()-1}" var="col">
                        <div data-col="${col}" class="col-mark seat-col">${col}</div>
                    </c:forEach>
                </div>
            </div>
                <input type="submit" value="Submit" id="submit-button"/>
                </form> 

        </div>
        <form method="POST" id="update-form"></form>
    </body>
    <script><%@include file="/static/js/seatController/seatController.js" %></script>

</html>