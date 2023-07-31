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
    <div class="modal cinema-view">

        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h1>Thông tin rạp</h1>
            <form>
                <h3>Tên rạp*</h3>
                <div><input type="text" name="name" value="Rạp chiếu Thái Nguyên" class="infor-box"></div>
                <h3>Địa chỉ*</h3>
                <div><input type="text" name="location" value="Thái Nguyên" class="infor-box"></div>
                <button type="submit" class="submit-btn">Cập nhật thông tin rạp</button>
            </form>
        </div>

    </div>
    </body>
</html>
