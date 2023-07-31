<%-- 
    Document   : resolve_delete
    Created on : May 27, 2023, 6:37:05 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/admin/movies/delete" method="POST">
            <input type="hidden" name="operationType" value="${requestScope.opType}"/>
            <input type="hidden" name="movie" value="${requestScope.movie.getMid()}"/>
        <p>
            The system detected ${requestScope.numShows} shows that are related to the movie you are trying to delete.
            In order to delete the movie, all shows that are related to such movie will be deleted as well.
            Are you sure you want to continue?
        </p>
        <button type="submit">Yes</button>
        
        </form>
        <button onclick="window.location.href='${pageContext.request.contextPath}/admin/movies'">No</button>
    </body>
</html>
