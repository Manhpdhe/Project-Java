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
        <select class="select-movie">
            <option value="">--Choose movie to add--</option>
            <option value="1">Spider-Man: In to the Spider-verse</option>
            <option value="2">The Little Mermaid</option>
            <option value="3">Fast and Furious X</option>
            <option value="4">Sisu</option>
        </select>
        <br>
        <div class="btn">
            <img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-plus-circled-256.png" alt="">
        </div>
    </div>

    <div class="movie-table">
        <table class="list" id="movieList">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Poster</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="">Transformer: Beast Rising</a></td>
                    <td>140 minutes</td>
                    <td><img src="https://www.cgv.vn/media/catalog/product/cache/1/thumbnail/240x388/c88460ec71d04fa96e628a21494d2fd3/7/0/700x1000_.jpg" alt=""></td>
                    <td><a href="#">Now Showing</a></td>
                </tr>
                <tr>
                    <td><a href="">Elemental</a></td>
                    <td>109 minutes</td>
                    <td><img src="https://files.betacorp.vn/files/media%2fimages%2f2023%2f06%2f01%2fpt-xscnt-110827-010623-53.jpg" alt=""></td>
                    <td>Coming Soon</a></td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
