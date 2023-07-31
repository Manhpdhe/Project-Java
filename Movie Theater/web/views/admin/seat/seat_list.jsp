<%-- 
    Document   : seat.
    Created on : 20-May-2023, 16:49:01
    Author     : NGUYEN THANH LUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Seat Controller</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/SeatCSS/Seat.css"%></style>
    </head>
    <body>
        <%--<%@include file="/views/util/header.jsp" %>--%>
        <div class="header-container">
            <h3>Seat Table</h3>
            <div>
                <div>
                    <img 
                        src="https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678092-sign-add-64.png" 
                        alt="create"
                        id="add-btn"
                        />
                </div>
            </div>
        </div>
        <div class="flex-container">
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>SeatID</th>
                            <th>RowNo</th>
                            <th>SeatNo</th>
                            <th>RoomID</th>
                            <th>Confirm Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.allSeats}" var="seat">
                            <tr>
                        <form action="${pageContext.request.contextPath}/admin/seats?action=update&seatId=${seat.seatID}" method="POST" class="update-form">
                            <td>${seat.seatID}</td>
                            <td><input type="text" value="${seat.rowNo}" name="rowNo" class="rowNo"></td>
                            <td><input type="text" value="${seat.seatNo}" name="seatNo" class="seatNo"></td>
                            <td><input type="text" value="${seat.roomID}" name="roomID" class="roomID"></td>
                            <td>
                                <div
                                    class="update-btn"
                                    >
                                    <img 
                                        src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-ios7-refresh-64.png" 
                                        class="update-img"
                                        alt="alt"/>    
                                </div    >
                            </td>
                            <td>
                                <a 
                                    href="${pageContext.request.contextPath}/admin/seats?action=delete&seatId=${seat.seatID}"

                                    >
                                    <img 
                                        src="https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_delete_48px-64.png" 
                                        class="delete-img"
                                        alt="alt"/>    
                                </a>
                            </td>
                        </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="page-index-container">
                    <a href="${pageContext.request.contextPath}/admin/seats?page=${requestScope.currentPage-1}" class="pre-next-class">Previous</a>
                    <div>
                        <c:forEach begin="1" end="${requestScope.lengthPage}" var="page">
                            <a href="${pageContext.request.contextPath}/admin/seats?page=${page-1}" class="page-index">${page}</a>
                        </c:forEach>
                    </div>
                    <a href="${pageContext.request.contextPath}/admin/seats?page=${requestScope.currentPage+1}" class="pre-next-class">Next</a>

                </div>
            </div>
        </div>
        <jsp:include page="/views/admin/seat/seat_create.jsp" />  
        <%@include file="/views/util/footer.jsp" %>
        <script>
            <%@include file="/static/js/seat.js"%>
        </script>
    </body>
</html>
