<%-- 
    Document   : InsertMovie
    Created on : May 20, 2023, 5:36:41 PM
    Author     : HP
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style><%@include file="/static/css/MoviesCSS/InsertMovie.css"%></style>
    </head>
    <%@include file="/views/util/imports.jsp" %>
</head>
<body>
    <div>
        <%@include file="/views/util/header.jsp" %>
    </div>
    <form action="${pageContext.request.contextPath}/headmanage/movie/add" method="POST"  enctype="multipart/form-data">
        <br> Title: <input id="inpTitle" type="text" name="title">
        <br> Redob: <input id="inpDate" type="date" name="redob">
        <br> Duration: <input id="inpDur" type="number" name="duration" style="width: 340px; height: 40px;">
        <br>  Rate: <select id ="rate" name="ratingid" >
            <c:forEach items="${requestScope.rateCtx}" var="item">
                <option value="${item.getRatingID()}">${item.getRatingDesc()}</option>
            </c:forEach>
        </select> 
        <br> Description: <input id="inpDesc" type="text" name="mdesc">
        <br> Poster:  <div id="poster-container" ondrop="handleFileSelect(event)" ondragover="handleDragOver(event)">
            Drag and drop images here
            <input id="inpFile" type="file" name="poster">
        </div>
        <input type="text" id="poster" name="poster" style="display: none;">
        <img id="poster-preview" src="#" alt="Poster Preview">
        <br> View: <input id="inpView" type="text" name="view" >
        <br> Language: <select id="language" name="languageid">
            <c:forEach items="${requestScope.languageCtx}" var="item">
                <option value="${item.getLanguageId()}">${item.getLanguageName()}</option>
            </c:forEach>
        </select>
        <br>Video Movie: <input id="inpUrl" type="text" name="url">
        <!-- comment  <br> Background: <div id="poster-container" ondrop="handleFileSelect(event)" ondragover="handleDragOver(event)">
            Drag and drop images here
            <input type="file" name="bg"> 
        </div>-->
        <br> <input id="sub" type="submit" name="submit" value="Add">
        <button class="button rounded-button-01"><a href="${pageContext.request.contextPath}/headmanage/movie" style="text-decoration: none; color: white;">Back</a></button>
    </form>






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
    <div>
        <%@include file="/views/util/footer.jsp" %> 
    </div>
</body>

</html>
