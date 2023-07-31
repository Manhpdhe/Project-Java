<%-- 
    Document   : UpdateShow
    Created on : May 24, 2023, 2:15:12 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/views/util/imports.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/ShowsCSS/UpdateShow.css"%></style>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/admin/shows/edit?showID=${requestScope.showUpdate.getShowID()}" method="post">

            <br> Time Start: <input type="date" name="startTime" value="${requestScope.showUpdate.getStartTime()}">
            <br> Time End: <input type="date" name="endTime" value="${requestScope.showUpdate.getEndTime()}">
            <br> Name Movie: <select id ="mid" name="mid">
                <c:forEach items="${requestScope.movieList}" var="rate">
                    <option value="${rate.getMid()}" 
                            <c:if test="${rate.getMid() == requestScope.showUpdated.getMovie().getMid()}">

                            </c:if>>${rate.getTitle()}</option>
                </c:forEach>
            </select>


            <!-- comment  <input type="text" name="mid" value="${requestScope.showUpdate.getMovie().getMid()}">-->
            <br> Room: <input type="text" name="roomID" value="${requestScope.showUpdate.getRoom().getRoomName()}">


            <br> <input type="submit" name="submit" value="Update">
        </form>
    </body>
</html>
