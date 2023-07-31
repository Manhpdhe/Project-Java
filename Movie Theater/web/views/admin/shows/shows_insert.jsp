<%-- 
    Document   : InsertShows
    Created on : May 24, 2023, 1:27:58 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/ShowsCSS/InsertShow.css"%></style>
    </head>
    <body>

        <form action="${pageContext.request.contextPath}/admin/shows/add" method="POST">  
            <br> Time Start:<input type="date" name="startTime">
            <br> Time end:<input type="date" name="endTime">
            <br> Movie Name: <select id ="movieID" name="movieID">
                <c:forEach items="${requestScope.movieCtx}" var="rate">
                    <option value="${rate.getMid()}" 
                            <c:if test="${rate.getMid() == requestScope.show.getMovie().getMid()}">

                            </c:if>>${rate.getTitle()}</option>
                </c:forEach>
            </select>


            <!--<input type="text" name="movieID">-->
            <br> Room: <input type="text" name="roomID">
            <br> <input type="submit" name="submit" value="Add">
        </form>
    </body>
</html>
