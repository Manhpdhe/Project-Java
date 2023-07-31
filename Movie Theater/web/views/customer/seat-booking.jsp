<%-- 
    Document   : seatBooking
    Created on : 10-06-2023, 00:49:42
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--requetScope for customer-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/styleSeatBooking.css" %>></style>

        <!--<link rel="stylesheet" href="styleSeatBooking.css">-->
    </head>
    <c:set var="room" scope="request" value="${requestScope.showInfo.getTimeSlot().getRoom()}"/>
    <c:set var="schedule" scope="request" value="${requestScope.showInfo.getTimeSlot()}"/>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <!--<div style="height: 30vh"></div>-->
        <div class="space"></div>
        <div class="movie-container">
            <label>Pick a movie:</label>
            <select id="movie">
                <option value="10">Avengers: Endgame ($10)</option>
                <option value="12">Joker ($12)</option>
                <option value="8">Toy Story 4 ($8)</option>
                <option value="9">The Lion King ($9)</option>
            </select>
        </div>

        <ul class="showcase">
            <li>
                <div class="seat-blank" id="disable"></div>
                <small>Blank</small>
            </li>
            <li>
                <div class="seat default" id="normal"></div>
                <small>Normal Seat</small>
            </li>
            <li>
                <div class="seat vip" id="vip"></div>
                <small>VIP Seat</small>
            </li>
            <li>
                <div class="seat selected" id="selected"></div>
                <small>Selected Seat</small>
            </li>
            <li>
                <div class="seat occupied" id="occupied"></div>
                <small>Occupied Seat</small>
            </li>
        </ul>
        <div class ="screen-container">
            <div class="screen"></div>

        </div>
        <c:if test="${requestScope.errmsg ne null}">
            <p style="color:red">${requestScope.errmsg}</p>
        </c:if>
        <div class="container">


            <c:forEach begin="0" end="${room.getNumRows()-1}" varStatus="i">
                <div class="row">
                    <div class="row-mark">${requestScope.rowLetters[i.index]}</div>
                    <c:forEach begin="0" end="${room.getNumCols()-1}" varStatus="k">
                        <c:choose>
                            <c:when test="${requestScope.seatMatrix[i.index][k.index] ne null}">
                                <c:choose>
                                    <c:when test="${requestScope.ticketMatrix[i.index][k.index] eq null}">
                                        <c:choose>
                                            <c:when test="${requestScope.seatMatrix[i.index][k.index].getSeatType().getSeatTypeName() eq 'VIP'}">
                                                <div data-row="${requestScope.rowLetters[i.index]}" data-col="${k.index}" data-seatid="${requestScope.seatMatrix[i.index][k.index].getSeatId()}" data-tickettype="${requestScope.ticketPrices.get(requestScope.seatMatrix[i.index][k.index].getSeatType().getSeatTypeId()).getTicketPriceId()}" data-price="${requestScope.ticketPrices.get(requestScope.seatMatrix[i.index][k.index].getSeatType().getSeatTypeId()).getPrice()}" class="seat vip row-${requestScope.rowLetters[i.index]} col-${k.index}"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <div data-row="${requestScope.rowLetters[i.index]}" data-col="${k.index}" data-seatid="${requestScope.seatMatrix[i.index][k.index].getSeatId()}" data-tickettype="${requestScope.ticketPrices.get(requestScope.seatMatrix[i.index][k.index].getSeatType().getSeatTypeId()).getTicketPriceId()}" data-price="${requestScope.ticketPrices.get(requestScope.seatMatrix[i.index][k.index].getSeatType().getSeatTypeId()).getPrice()}" class="seat default row-${requestScope.rowLetters[i.index]} col-${k.index+1}"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="seat occupied default row-${requestScope.rowLetters[i.index]} col-${k.index+1}"></div>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <div class="seat-blank default row-${requestScope.rowLetters[i.index]} col-${k.index+1}"></div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:forEach>
            
        </div>

        <button class="open-button" onclick="openForm()">Pay Now</button>

        <!-- The form -->
        <div class="form-popup" id="myForm">
            <form action="${pageContext.request.contextPath}/book" method="POST" class="form-container">
                <input type="hidden" name="show" value="${requestScope.showInfo.getShowID()}"/>
                <input type="hidden" name="timeRng" value="${requestScope.timeStart} - ${requestScope.timeEnd}"/>
                <div id="seatList">
                    <input type="hidden" id="seatList" name="bookedSeats" value=""/> 
                </div>
                <h1>Ticket Confirmation</h1>
                <h2>You’re booking tickets for:</h2>
                <p><span>Cinema:</span> ${room.getBranch().getBranchName()}</p>
                <p><span>Movie:</span> ${requestScope.showInfo.getMovie().getTitle()}</p>
                <p><span>Time:</span> ${requestScope.timeStart} - ${requestScope.timeEnd} </p>
                <p><span>Number of ticket(s) booked:</span><span id="count"> 0</span></p>
                <!-- <p><span>Combo:</span> 2 popcorns, 2 Coca</p> -->
                <p class="text">
                    Total: <input name="amount" readonly="readonly" id="total" value="0"></input><span>VND</span> 
                </p>

                <button type="submit" class="btn">Pay Now</button>
                <button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
            </form>
        </div>

        <div class="second-form-popup" id="my2ndForm">
            <form action="home" class="second-form-container">
                <h1>Order Successfull!</h1>
                <h2>Your ticket have been ordered successfully</h2>     
                <p>If you have any trouble, please contact us at <a href="#">hoidap@groovycineplex.vn</a></p> 
                <p>or hotline: <span style="font-weight: bold;">0123456789</span></p>  
                <p>View your booking history <a href="">here</a></p>   
                <button type="button" class="btn cancel" onclick="close2ndForm()">Back to Homepage</button>
            </form>
        </div>
        <div>
            <%@include file="/views/util/footer.jsp" %>
        </div>>

    </body>
    <script><%@include file="/static/js/seatBooking.js" %></script>

</html>