<%-- 
    Document   : ListShow
    Created on : May 23, 2023, 8:14:13 PM
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
        <style><%@include file="/static/css/ShowsCSS/ListShows.css"%></style>
    </head>



    <body>
    <body>
        <table>
            <h3><a href="${pageContext.request.contextPath}/admin/shows/add" id="insertshow">
                    <img 
                        src="https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678092-sign-add-64.png"
                        alt="create" 
                        id="add-btn" 
                        style="width: 50px; height: 50px;">
                </a>
            </h3>
            
            <a href="${pageContext.request.contextPath}/admin/movies" id="listmovie">
                <img 
                        src="https://cdn-icons-png.flaticon.com/512/0/340.png"
                        alt="create" 
                        id="add-btn" 
                        style="width: 50px; height: 50px;">
            </a>

            <tr>
                <th>Show</th>
                <th>Movie</th>
                <th>Time Start</th>
                <th>Time End</th>
                <th>Room Name<th>              
                <th>Update</th>               
                <th>Delete</th>               
            </tr>
            <c:forEach items="${requestScope.m}" var="item"> 
                <tr>             
                    
                    <td>${item.getShowID()}</td>

                    <td>${item.getMovie().getTitle()}</td>
                    <td>${item.getStartDelay()}</td>                  
                    <td>${item.getTimeSlot().getRoom().getRoomName()}</td>
                    <td></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/shows/edit?showID=${item.getShowID()}">
                            <img 
                                src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-ios7-refresh-64.png" 
                                alt="create" 
                                id="add-btn" 
                                style="width: 50px; height: 50px;">
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/shows/delete?showID=${item.getShowID()}">
                            <img 
                                src="https://cdn0.iconfinder.com/data/icons/google-material-design-3-0/48/ic_delete_48px-64.png" 
                                alt="create" 
                                id="add-btn" 
                                style="width: 50px; height: 50px;"></a>
                    </td>
                    

                </tr>
            </c:forEach>


        </table>
        <div class="pagination-container">
            <a href="${pageContext.request.contextPath}/admin/shows?page=${requestScope.currentPage-1}" class="pre-next-class">Previous</a>
            <div>

                <c:forEach begin="1" end="${requestScope.lengthPage}" var="page">
                    <a href="${pageContext.request.contextPath}/admin/shows?page=${page-1}" class="page-index">${page}</a>
                </c:forEach>
            </div>
            <a href="${pageContext.request.contextPath}/admin/shows?page=${requestScope.currentPage+1}" class="pre-next-class">Next</a>

        </div>
        <%@include file="/views/util/footer.jsp" %>

    </body>
</body>
</html>
