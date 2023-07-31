<%-- 
    Document   : homepage
    Created on : 10-06-2023, 00:36:43
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--requetScope for user-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
        <style><%@include file="/static/css/styleHomepage.css" %></style>
    </head>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <div class="space"></div>
        <div class="slideshow-container">

            <!-- Full-width images with number and caption text -->
            <div class="mySlides fade">
                <img src="https://images2.thanhnien.vn/528068263637045248/2023/5/8/gotg-03-16835392503471762382911.png" style="width:100%">
            </div>

            <div class="mySlides fade">
                <img src="https://images2.thanhnien.vn/zoom/600_315/528068263637045248/2023/5/7/sisu-1683462175070725811663-0-0-628-1200-crop-168346218906936070082.jpg" 
                     style="width:100%">
            </div>

            <div class="mySlides fade">
                <img src="https://cdnmedia.baotintuc.vn/Upload/rGkvwNpj74Z1EcpzQ6ltA/files/2023/05/tuan4/phim-22523.jpg" style="width:100%">
            </div>

            <div class="mySlides fade">
                <img src="https://www.thepinknews.com/wp-content/uploads/2023/05/First-reviews-for-The-Little-Mermaid-are-rolling-in.-Disney.jpg?w=1584&h=832&crop=1" style="width:100%">
            </div>

            <div class="mySlides fade">
                <img src="https://ghienreview.com/wp-content/uploads/2018/12/Ghienreview-Spiderverse-cover-1024x633.jpg" style="width:100%">
            </div>

            <!-- Next and previous buttons -->
            <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
            <a class="next" onclick="plusSlides(1)">&#10095;</a>
        </div>
        <br>

        <!-- The dots/circles -->
        <div style="text-align:center">
            <span class="dot" onclick="currentSlide(1)"></span>
            <span class="dot" onclick="currentSlide(2)"></span>
            <span class="dot" onclick="currentSlide(3)"></span>
            <span class="dot" onclick="currentSlide(4)"></span>
            <span class="dot" onclick="currentSlide(5)"></span>

        </div>

        <div class="title">
            <!--
            <div class="movies">
                <div>FILM COMING SOON</div>
                <div>MOVIES SHOWING</div>
            </div>
            -->

        </div>

        <div class="movies-container">
            <div class="flex-container">
                <c:forEach items="${requestScope.movieList}" var="item">
                    <div class="card">
                        <img src="${pageContext.request.contextPath}/file?filename=${item.getPoster()}" alt="Avatar">
                        <div class="container">
                            <h3><a style="text-decoration: none;" href="${pageContext.request.contextPath}/detailsmovie?mid=${item.getMid()}" title="View Product">${item.getTitle()}</a></h3>
                            <p>Time: ${item.getDuration()} minutes</p>                           
                            <p>Rated: ${item.getRate().getRatingLabel()}</p>
                        </div>

                    </div>     
                </c:forEach>
            </div>
        </div>

        <div>
            <%@include file="/views/util/footer.jsp" %>
        </div>
    </body>
    <script><%@include file="/static/js/homepage.js" %></script>
</html>
