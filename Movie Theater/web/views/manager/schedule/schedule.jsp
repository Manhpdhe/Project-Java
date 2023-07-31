
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Schedule</title>
        <style><%@include file="/static/css/ScheduleCSS/schedule.css" %></style>
    </head>

    <body>
        <header class="header">
            <div style="width: 10%"><img src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg" alt="logo"/></div>
            <form action="schedule" id="select-form">
                <!--<input name="cinema-name" type="text" hidden value="${selectedBranch}">-->
                <input name="week" type="text" hidden value="${selectedDate}">
                <div class="subheader">
                    <select name="cinema-name" id="cinema-select">
                        <c:forEach items="${requestScope.branches}" var="branch">
                            <option value="${branch.branchID}" ${branch.branchID eq selectedBranch ? 'selected' : ''}>${branch.branchName}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>
            <div class="subheader">Ticket Sale & Revenue</div>
            <div class="subheader">${sessionScope.manager.lastName}</div>
        </header>
        <div class="container">
            <div style="display: flex; justify-content: space-around; margin-top: 30px;" class="select-container">

                <form action="schedule" method="GET" id="week-schedule">
                    <input name="cinema-name" type="text" hidden value="${selectedBranch}">
                    <input name="room-id" type="text" hidden value="${roomId}">
                    <select name="week" id="week">
                        <option value="" hidden>Tuần chiếu</option>
                        <option value="02/01 To 08/01" ${"02/01 To 08/01" eq selectedDate ? 'selected' : ''}>02/01 To 08/01</option>
                        <option value="09/01 To 15/01" ${"09/01 To 15/01" eq selectedDate ? 'selected' : ''}>09/01 To 15/01</option>
                        <option value="16/01 To 22/01" ${"16/01 To 22/01" eq selectedDate ? 'selected' : ''}>16/01 To 22/01</option>
                        <option value="23/01 To 29/01" ${"23/01 To 29/01" eq selectedDate ? 'selected' : ''}>23/01 To 29/01</option>
                        <option value="30/01 To 05/02" ${"30/01 To 05/02" eq selectedDate ? 'selected' : ''}>30/01 To 05/02</option>
                        <option value="06/02 To 12/02" ${"06/02 To 12/02" eq selectedDate ? 'selected' : ''}>06/02 To 12/02</option>
                        <option value="13/02 To 19/02" ${"13/02 To 19/02" eq selectedDate ? 'selected' : ''}>13/02 To 19/02</option>
                        <option value="20/02 To 26/02" ${"20/02 To 26/02" eq selectedDate ? 'selected' : ''}>20/02 To 26/02</option>
                        <option value="27/02 To 05/03" ${"27/02 To 05/03" eq selectedDate ? 'selected' : ''}>27/02 To 05/03</option>
                        <option value="06/03 To 12/03" ${"06/03 To 12/03" eq selectedDate ? 'selected' : ''}>06/03 To 12/03</option>
                        <option value="13/03 To 19/03" ${"13/03 To 19/03" eq selectedDate ? 'selected' : ''}>13/03 To 19/03</option>
                        <option value="20/03 To 26/03" ${"20/03 To 26/03" eq selectedDate ? 'selected' : ''}>20/03 To 26/03</option>
                        <option value="27/03 To 02/04" ${"27/03 To 02/04" eq selectedDate ? 'selected' : ''}>27/03 To 02/04</option>
                        <option value="03/04 To 09/04" ${"03/04 To 09/04" eq selectedDate ? 'selected' : ''}>03/04 To 09/04</option>
                        <option value="10/04 To 16/04" ${"10/04 To 16/04" eq selectedDate ? 'selected' : ''}>10/04 To 16/04</option>
                        <option value="17/04 To 23/04" ${"17/04 To 23/04" eq selectedDate ? 'selected' : ''}>17/04 To 23/04</option>
                        <option value="24/04 To 30/04" ${"24/04 To 30/04" eq selectedDate ? 'selected' : ''}>24/04 To 30/04</option>
                        <option value="01/05 To 07/05" ${"01/05 To 07/05" eq selectedDate ? 'selected' : ''}>01/05 To 07/05</option>
                        <option value="08/05 To 14/05" ${"08/05 To 14/05" eq selectedDate ? 'selected' : ''}>08/05 To 14/05</option>
                        <option value="15/05 To 21/05" ${"15/05 To 21/05" eq selectedDate ? 'selected' : ''}>15/05 To 21/05</option>
                        <option value="22/05 To 28/05" ${"22/05 To 28/05" eq selectedDate ? 'selected' : ''}>22/05 To 28/05</option>
                        <option value="29/05 To 04/06" ${"29/05 To 04/06" eq selectedDate ? 'selected' : ''}>29/05 To 04/06</option>
                        <option value="05/06 To 11/06" ${"05/06 To 11/06" eq selectedDate ? 'selected' : ''}>05/06 To 11/06</option>
                        <option value="12/06 To 18/06" ${"12/06 To 18/06" eq selectedDate ? 'selected' : ''}>12/06 To 18/06</option>
                        <option value="19/06 To 25/06" ${"19/06 To 25/06" eq selectedDate ? 'selected' : ''}>19/06 To 25/06</option>
                        <option value="26/06 To 02/07" ${"26/06 To 02/07" eq selectedDate ? 'selected' : ''}>26/06 To 02/07</option>
                        <option value="03/07 To 09/07" ${"03/07 To 09/07" eq selectedDate ? 'selected' : ''}>03/07 To 09/07</option>
                        <option value="10/07 To 16/07" ${"10/07 To 16/07" eq selectedDate ? 'selected' : ''}>10/07 To 16/07</option>
                        <option value="17/07 To 23/07" ${"17/07 To 23/07" eq selectedDate ? 'selected' : ''}>17/07 To 23/07</option>
                        <option value="24/07 To 30/07" ${"24/07 To 30/07" eq selectedDate ? 'selected' : ''}>24/07 To 30/07</option>
                        <option value="31/07 To 06/08" ${"31/07 To 06/08" eq selectedDate ? 'selected' : ''}>31/07 To 06/08</option>
                        <option value="07/08 To 13/08" ${"07/08 To 13/08" eq selectedDate ? 'selected' : ''}>07/08 To 13/08</option>
                        <option value="14/08 To 20/08" ${"14/08 To 20/08" eq selectedDate ? 'selected' : ''}>14/08 To 20/08</option>
                        <option value="21/08 To 27/08" ${"21/08 To 27/08" eq selectedDate ? 'selected' : ''}>21/08 To 27/08</option>
                        <option value="28/08 To 03/09" ${"28/08 To 03/09" eq selectedDate ? 'selected' : ''}>28/08 To 03/09</option>
                        <option value="04/09 To 10/09" ${"04/09 To 10/09" eq selectedDate ? 'selected' : ''}>04/09 To 10/09</option>
                        <option value="11/09 To 17/09" ${"11/09 To 17/09" eq selectedDate ? 'selected' : ''}>11/09 To 17/09</option>
                        <option value="18/09 To 24/09" ${"18/09 To 24/09" eq selectedDate ? 'selected' : ''}>18/09 To 24/09</option>
                        <option value="25/09 To 01/10" ${"25/09 To 01/10" eq selectedDate ? 'selected' : ''}>25/09 To 01/10</option>
                        <option value="02/10 To 08/10" ${"02/10 To 08/10" eq selectedDate ? 'selected' : ''}>02/10 To 08/10</option>
                        <option value="09/10 To 15/10" ${"09/10 To 15/10" eq selectedDate ? 'selected' : ''}>09/10 To 15/10</option>
                        <option value="16/10 To 22/10" ${"16/10 To 22/10" eq selectedDate ? 'selected' : ''}>16/10 To 22/10</option>
                        <option value="23/10 To 29/10" ${"23/10 To 29/10" eq selectedDate ? 'selected' : ''}>23/10 To 29/10</option>
                        <option value="30/10 To 05/11" ${"30/10 To 05/11" eq selectedDate ? 'selected' : ''}>30/10 To 05/11</option>
                        <option value="06/11 To 12/11" ${"06/11 To 12/11" eq selectedDate ? 'selected' : ''}>06/11 To 12/11</option>
                        <option value="13/11 To 19/11" ${"13/11 To 19/11" eq selectedDate ? 'selected' : ''}>13/11 To 19/11</option>
                        <option value="20/11 To 26/11" ${"20/11 To 26/11" eq selectedDate ? 'selected' : ''}>20/11 To 26/11</option>
                        <option value="27/11 To 03/12" ${"27/11 To 03/12" eq selectedDate ? 'selected' : ''}>27/11 To 03/12</option>
                        <option value="04/12 To 10/12" ${"04/12 To 10/12" eq selectedDate ? 'selected' : ''}>04/12 To 10/12</option>
                        <option value="11/12 To 17/12" ${"11/12 To 17/12" eq selectedDate ? 'selected' : ''}>11/12 To 17/12</option>
                        <option value="18/12 To 24/12" ${"18/12 To 24/12" eq selectedDate ? 'selected' : ''}>18/12 To 24/12</option>
                        <option value="25/12 To 31/12" ${"25/12 To 31/12" eq selectedDate ? 'selected' : ''}>25/12 To 31/12</option>
                    </select>
                </form>

                <form action="schedule?cinema-name=${selectedBranch}" method="GET" id="room-form">
                    <!--<input name="cinema-name" type="text" hidden value="${selectedBranch}">-->
                    <input name="week" type="text" hidden value="${selectedDate}">
                    <select name="room-id" id="outer-room-id">
                        <option value="" hidden>-- Choose room --</option>
                        <c:forEach items="${rooms}" var="room">
                            <option value="${room.roomID}" ${roomId eq room.roomID ? 'selected' : ''}>${room.roomName}</option>
                        </c:forEach>
                    </select>
                </form>
                <button id="create-btn" 
                        style="border-radius: 5px; background-color: #619cbc; border: none; color: white;">Tạo lịch</button>
            </div>

            <div class="table-container">
                <div class="movie-container" style="height: 50vh; width: auto; overflow-x: hidden; overflow-y: scroll;">
                    <table>
                        <thead>
                            <tr>
                                <th>Tên phim</th>
                                <th>Thời lượng (Phút)</th>
                                <th>Delay</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.movies}" var="movie">
                                <tr>
                                    <td class="movie-name" draggable="true">${movie.title}</td>
                                    <td class="time">${movie.duration}</td>
                                    <td><input type="number" value="0" min="0" class="delay"/></td>
                                </tr>
                            </c:forEach>
                            <tr id="delete-area">
                                <td colspan="3" style="background-color: red; color: white">Xóa lịch</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <form action="schedule?action=create-schedule&week=${selectedDate}&cinema-name=${selectedBranch}" method="POST" style="width: 100%" id="create-schedule-form">
                    <!--                <div class="schedule-container">-->
                    <input name="room-id" type="text" hidden="true" id="inner-room-id"/>
                    <table border="1" class="schedule-table">
                        <thead>
                            <tr>
                                <th>Thời gian(Giờ)</th>
                                    <c:forEach items="${requestScope.dates.keySet()}" var="date">
                                    <th>Thứ ${date+1 eq 8 ? "Chủ Nhật" : date+1}<br/>${dates.get(date)}</th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach begin="0" end="23" var="time">
                                <tr class="${time}">

                                    <td>${time}</td>
                                    <c:forEach items="${requestScope.dates.keySet()}" var="date">
                                        <td class="td-container" >
                                            <input type="text" value="${requestScope.dates.get(date)}/2023" hidden="true" class="formal-date"/>
                                            <c:forEach items="${shows}" var="show">
                                                <c:if test="${requestScope.dates.get(date) eq show.getTimeSlot().getDate()}">

                                                    <%--<c:forEach items="${shows.get(show).keySet()}" var="showTime">--%>
                                                    <c:if test="${show.getTimeSlot().getOpenTimeInteger() eq time}">
                                                        <div class="custom-div resizeable-div ${show.getMovie().getTitle() != null ? 'change-bg' : ''}" role="button" draggable="true" style="height: ${(show.getTimeSlot().getCloseTimeInteger() - show.getTimeSlot().getOpenTimeInteger())*35}px">
                                                            <input type="text" name="id" value="${show.getShowID()}" class="ShowID" hidden="true">
                                                            <input type="text" name="sid" value="${show.getTimeSlot().getTimeSlotId()}" class="scheduleId" hidden="true">
                                                            <input type="text" name="start-time" value="${show.getTimeSlot().getOpenTimeInteger()}" hidden="true">
                                                            <input type="text" name="end-time" value="${show.getTimeSlot().getCloseTimeInteger()}" hidden="true" class="endTime">
                                                            <input type="text" name="state" value="update" class="state" hidden="true">
                                                            <div class="inner-movie-name">${show.getMovie().getTitle()}</div>
                                                            <input name="date-time" value="${requestScope.dates.get(date)}/2023" hidden="true" type="text">
                                                            <input type="number" name="delay" value="${show.getStartDelay()}" hidden="true" class="movie-delay">
                                                        </div>
                                                    </c:if>
                                                    <%--</c:forEach>--%>

                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>

            </div>
        </div>

    </body>
    <script><%@include file="/static/js/ScheduleController/schedule.js" %></script>

</html>