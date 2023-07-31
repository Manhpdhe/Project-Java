<%-- 
    Document   : ListMovie
    Created on : May 20, 2023, 4:07:54 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <div style="color: red;" id="msg"><h2>${sessionScope.msg}</h2></div>


        <table style="    width: 100vw;">
            <tr><div class="header-container">     
                <h3><a href="${pageContext.request.contextPath}/manage/schedule" id="listshow">
                        <button class="button rounded-button-01">
                            <i class="gg-calendar-dates"></i>
                            Schedule
                        </button>
                </h3>
                <h3><a href="${pageContext.request.contextPath}/headmanage/movie/add" id="insertmovie" ">
                        <button class="button rounded-button-01">
                            <i class="gg-add-r"></i>
                            Add New Movie
                        </button>
                    </a>
                </h3>
            </div></tr>

        <thead>

        <th>Poster</th>
        <th>Movie</th>
        <th>Release Date</th>
        <th>Duration</th>
        <th>Rating</th>               
        <th>View</th>    
        <th colspan="2">Action</th>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.m}" var="item">
            <tr>                
                <td><img src="${pageContext.request.contextPath}/file?filename=${item.getPoster()}" class="poster"></td>
                <td>${item.getTitle()}</td>
                <td><fmt:formatDate value="${item.getRedob()}" pattern="dd/MM/YYYY"/></td>
                <td>${item.getDuration()}</td>

                <td>${item.getRate().getRatingLabel()}</td>


                <td>${item.getView()}</td>

                <td>
                    <a href="${pageContext.request.contextPath}/headmanage/movie/update?mid=${item.getMid()}">
                        <img 
                            src="https://icons.veryicon.com/png/o/miscellaneous/linear-small-icon/edit-246.png" 
                            alt="create" 
                            id="add-btn" 
                            style="width: 50px; height: 50px;">
                    </a>


                    <a href="${pageContext.request.contextPath}/headmanage/movie/delete?mid=${item.getMid()}">
                        <img 
                            src="https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_delete_48px-64.png" 
                            alt="create" 
                            id="add-btn" 
                            style="width: 50px; height: 50px;">
                    </a>
                </td>

            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="pagination-container">
    <a href="${pageContext.request.contextPath}/headmanage/movie?page=${requestScope.currentPage-1}" class="pre-next-class"><i class="gg-push-chevron-left"></i></a>
    <div>

        <c:forEach begin="1" end="${requestScope.lengthPage}" var="page">
            <a href="${pageContext.request.contextPath}/headmanage/movie?page=${page-1}" class="page-index">${page}</a>
        </c:forEach>
    </div>
    <a href="${pageContext.request.contextPath}/headmanage/movie?page=${requestScope.currentPage+1}" class="pre-next-class"><i class="gg-push-chevron-right"></i></a>

</div>

<style>
    /* Assuming your button class already has the necessary styles */
    .button {
        display: inline-flex;
        align-items: center; /* Align items vertically in the center */
    }

    /* Adjust icon and text spacing as needed */
    .button i {
        margin-right: 5px; /* Add space between icon and text */
    }

    /* Optional: If you want to remove default button styles */
    .button {
        border: none;
        background: none;
        padding: 0; /* Remove padding to eliminate extra spacing */
        cursor: pointer;
        text-decoration: none; /* Remove underline for anchor inside button */
        color: inherit; /* Inherit text color from parent */
    }

    /* Optional: Apply any other styles to the button as needed */
    /* For example, to create a rounded button with a background color */
    .rounded-button-01 {
        border-radius: 25px;
        background-color: #619CBC; /* Replace with your desired color */
        color: #fff; /* Replace with your desired text color */
        padding: 10px 20px;
        font-size: 16px;
    }

</style>
<%@include file="/views/util/footer.jsp" %>

</body>
</html>
