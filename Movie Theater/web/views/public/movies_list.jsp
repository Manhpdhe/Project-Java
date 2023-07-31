<%-- 
    Document   : moviesList
    Created on : 10-06-2023, 00:47:11
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--requetScope for user-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/styleMoviesList.css" %></style>
    </head>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <div class="space"></div>

        <div class="search" style="display: flex; justify-content: center;">
           
            <form action="search" method="GET" id="search-form">
                <input value="${txtSearch}" type="text" name="txt" placeholder="Search">
<!--                <select name="filter" onchange="autoSubmit()">
                    <option value="-1">All Branches</option>
                    <c:forEach var="branch" items="${branchList}">
                        <option value="${branch.branchID}" ${branchId == branch.branchID ? 'selected' : ''}>${branch.location}</option>
                    </c:forEach>
                </select>-->
            </form>
        </div>




        <div class="title">
            <div class="movies">
                    <div>FILM COMING SOON</div>
            </div>
        </div>

        <div class="movies-container">
            <div class="flex-container">
                <c:forEach items="${requestScope.movieList}" var="item">
                    <div class="card">
                        <div href="${pageContext.request.contextPath}/detailsmovie?mid=${item.getMid()} class="card">
                            <img  src="${pageContext.request.contextPath}/file?filename=${item.getPoster()}" alt="Avatar">
                            <div class="container">
                                <h3><a style="text-decoration: none;" href="${pageContext.request.contextPath}/detailsmovie?mid=${item.getMid()}" title="View Product">${item.getTitle()}</a></h3>
                                <p>Time: ${item.getDuration()} minutes</p>
                                <p>Rated: ${item.getRate().getRatingLabel()}</p>
                            </div>
                        </div>     
                    </div>
                </c:forEach>
            </div>
        </div>       

        <div>
            <%@include file="/views/util/footer.jsp" %>
        </div>
    </body>
    <script>
        const form = document.querySelector('#search-form');
        function autoSubmit() {
            form.submit();
        }
    </script>
</html>
