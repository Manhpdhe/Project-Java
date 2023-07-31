<%-- 
    Document   : manage_cinema_movie
    Created on : 14-06-2023, 20:09:05
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/styleManageCinemaMovie.css" %></style>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <div class="add-movie">
        
        <form method="POST" action="${pageContext.request.contextPath}/headmanage/cinemamovies?action=insert">
            <input type="hidden" name="branch-id" value="${requestScope.branchID}">
            <select name="selectedMovie" class="select-movie">
                <option value="">--Choose movie to add--</option>
                <c:forEach items="${requestScope.movies}" var="movie">
                    <option value="${movie.mid}">${movie.title}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn"><img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-plus-circled-256.png" alt=""></button>
        </form>
        <br>
    </div>

    <div class="movie-table">
        <table class="list" id="movieList">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Poster</th>
                    <th>Number of shows</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="showCounts" value="${requestScope.showCountOfMovies}"/>
                <c:forEach items="${requestScope.moviesOfCinema}" var="movie">
                <tr>
                    <td><a href="">${movie.title}</a></td>
                    <td>${movie.duration}</td>
                    <td><img src="${movie.poster}" alt="${movie.title}'s poster"></td>
                    <td>
                        <a href="#">
                        <c:forEach var="showCount" items="${showCounts}">
                             <c:if test="${showCount.key eq movie.mid}">
                                ${showCount.value}
                            </c:if>
                        </c:forEach>
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
