<%-- 
    Document   : UpdateMovie
    Created on : May 20, 2023, 4:39:45 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/MoviesCSS/UpdateMovie.css"%></style>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/admin/movies/edit" method="post" enctype="multipart/form-data">
            <br> ID: <input name="mid" value="${requestScope.movieUpdate.getMid()}" readonly="">
            <br> Movie Name: <input type="text" name="title" value="${requestScope.movieUpdate.getTitle()}">
            <br> Start Time: <input type="date" name="redob" value="${requestScope.movieUpdate.getRedob()}">
            <br> Duration: <input type="text" name="duration" value="${requestScope.movieUpdate.getDuration()}">
            <br> Rate: <select id ="rate" name="ratingID">
                <c:forEach items="${requestScope.rateList}" var="rate">
                    <option value="${rate.getRatingID()}" 
                            <c:if test="${rate.getRatingID() eq requestScope.movieUpdated.getRate().getRatingID()}">
                                selected="selected"
                            </c:if>>${rate.getRatingLabel()}</option>
                </c:forEach>
            </select>   
            <br> Description: <input type="text" name="mdesc" value="${requestScope.movieUpdate.getMdesc()}">
            <input type="hidden" name="prevFile" value="${requestScope.movieUpdate.getPoster()}"/>
            Poster:  <div id="poster-container" ondrop="handleFileSelect(event)" ondragover="handleDragOver(event)">
                Drag and drop images here
                <input type="file" name="poster">
            </div>
            <input type="text" id="poster" name="poster" style="display: none;">
            <img id="poster-preview" src="#" alt="Poster Preview">
            <!-- <br> Poster <input type="file" name="poster">-->
            <br> View: <input type="text" name="view" value="${requestScope.movieUpdate.getView()}">
            <br> <input type="submit" name="submit" value="Update">
        </form>
    </body>

    <script>
        function handleFileSelect(evt) {
            evt.stopPropagation();
            evt.preventDefault();

            var files = evt.dataTransfer.files; // Get the list of files dropped

            // Process each file
            for (var i = 0, f; f = files[i]; i++) {
                // Perform additional actions here with each file, such as displaying a preview or uploading it

                // Set the value of the input field to the file name
                var inputElement = document.getElementById('poster');
                inputElement.value = f.name;

                // Display a preview of the file (optional)
                var reader = new FileReader();
                reader.onload = function (e) {
                    var previewElement = document.getElementById('poster-preview');
                    previewElement.src = e.target.result;
                    previewElement.style.display = 'block';
                };
                reader.readAsDataURL(f);
            }
        }

        function handleDragOver(evt) {
            evt.stopPropagation();
            evt.preventDefault();
            evt.dataTransfer.dropEffect = 'copy'; // Show the "copy" cursor when dragging over
        }

// Set up event listeners
        var posterContainer = document.getElementById('poster-container');
        posterContainer.addEventListener('dragover', handleDragOver, false);
        posterContainer.addEventListener('drop', handleFileSelect, false);
    </script>
</html>
