<%-- 
    Document   : Cinema_UpdateCinema
    Created on : 21-May-2023, 15:01:06
    Author     : NGUYEN THANH LUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cinema</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>
        <!-- Cái này vừa dùng update vừa dùng in thông tin rạp nhé -->
        <!-- The Modal -->
        <c:forEach items="${requestScope.cinemas}" var="cinema">
            <div class="modal cinema-view">
                <!-- Modal content -->
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h1>Thông tin rạp</h1>
                    <form method="POST" action="${pageContext.request.contextPath}/headmanage/cinemas?action=update">
                        <input type="hidden" name="cinema-id" value="${cinema.branchID}">
                        <h3>Tên rạp*</h3>
                        <div><input type="text" name="cinema-name" value="${cinema.branchName}" class="infor-box" placeholder="Groovy Cineplex An Giang..." required></div>
                        <h3>Địa chỉ*</h3>
                        <div><input type="text" name="cinema-location" value="${cinema.location}" class="infor-box" placeholder="Tòa nhà Bicotex, Phú Nhuận..." required></div>
                        <button type="submit" class="submit-btn">Cập nhật thông tin rạp</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </body>
</html>
