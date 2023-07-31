<%-- 
    Document   : ListMovie
    Created on : May 20, 2023, 4:07:54 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/MoviesCSS/ListMovies.css"%></style>

    </head>
    <body>
        <div style="color: red;" id="msg"><h2>${sessionScope.msg}</h2></div>
        <div class="header-container">     
            <h3><a href="${pageContext.request.contextPath}/admin/shows" id="listshow">
                    <img 
                        src="https://cdn.pixabay.com/photo/2017/06/10/07/18/list-2389219_1280.png" 
                        alt="create" 
                        id="add-btn" 
                        style="width: 50px; height: 50px;">
                </a>
            </h3>
            <h3><a href="${pageContext.request.contextPath}/admin/movies/add" id="insertmovie" ">
                    <img 
                        src="https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678092-sign-add-64.png" 
                        alt="create" 
                        id="add-btn" 
                        style="width: 50px; height: 50px;">
                </a>
            </h3>
        </div>

        <table>

            <tr>
                <th>Title</th>
                <th>Redob</th>
                <th>Duration</th>
                <th>Rate<th>
                <th>Poster</th>
                <th>Views</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${requestScope.m}" var="item">
                <tr>                
                    <td>${item.getTitle()}</td>
                    <td>${item.getRedob()}</td>
                    <td>${item.getDuration()}</td>

                    <td>${item.getRate().getRatingLabel()}</td>
                    <td></td>
                    <td><img src="${pageContext.request.contextPath}/file?filename=${item.getPoster()}" class="poster"></td>
                    <td>${item.getView()}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/movies/edit?mid=${item.getMid()}">
                            <img 
                                src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-ios7-refresh-64.png" 
                                alt="create" 
                                id="add-btn" 
                                style="width: 50px; height: 50px;">
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/movies/delete?mid=${item.getMid()}">
                            <img 
                                src="https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_delete_48px-64.png" 
                                alt="create" 
                                id="add-btn" 
                                style="width: 50px; height: 50px;">
                        </a>
                    </td>
                </tr>
            </c:forEach>

        </table>
        <div class="pagination-container">
            <a href="${pageContext.request.contextPath}/admin/movies?page=${requestScope.currentPage-1}" class="pre-next-class">Previous</a>
            <div>

                <c:forEach begin="1" end="${requestScope.lengthPage}" var="page">
                    <a href="${pageContext.request.contextPath}/admin/movies?page=${page-1}" class="page-index">${page}</a>
                </c:forEach>
            </div>
            <a href="${pageContext.request.contextPath}/admin/movies?page=${requestScope.currentPage+1}" class="pre-next-class">Next</a>

        </div>

        <%@include file="/views/util/footer.jsp" %>

    </body>
</html>
