<%-- 
    Document   : cinemaInfo
    Created on : 10-06-2023, 00:43:59
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
        <style><%@include file="/static/css/styleCinemaInfo.css" %></style>
    </head>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <div class="space"></div>

        <div class="slideshow-container">
            <div class="slide-dot">

                <div class="mySlides fade">
                    <img src="static/images/1.png" style="width:100%">
                </div>
                <div class="mySlides fade">
                    <img src="static/images/2.png" 
                         style="width:100%">
                </div>
                <div class="mySlides fade">
                    <img src="static/images/3.png" style="width:100%">
                </div>
                <div class="mySlides fade">
                    <img src="static/images/4.png" style="width:100%">
                </div>
                <div style="text-align:center">
                    <span class="dot" onclick="currentSlide(1)"></span>
                    <span class="dot" onclick="currentSlide(2)"></span>
                    <span class="dot" onclick="currentSlide(3)"></span>
                    <span class="dot" onclick="currentSlide(4)"></span>
                </div>
                <div  class="location">
                    <select id="location" name="location" onchange="showInfo()">
                        <option value="0">Groovy Hà Nội</option>
                        <option value="1">Groovy Sài Gòn</option>
                        <option value="2">Groovy Quảng Ninh</option>
                    </select>
                </div>
            </div>
            <div id="info-text-Hà-Nội" class="info-text">
                <p>Thành lập vào tháng 12 năm 2014, Groovy Hà Nội có vị trí trung tâm. Rạp tự hào là rạp phim tư nhân duy nhất và đầu tiên sở hữu hệ thống phòng chiếu phim đạt chuẩn 
                    Hollywood tại TP. Hà Nội.</p>
                <p>Rạp được trang bị hệ thống máy chiếu, phòng chiếu hiện đại với 100% nhập khẩu từ nước ngoài, với 4 
                    phòng chiếu tương được 234 ghế ngồi. Hệ thống âm thanh Dolby 7.1 và hệ thống cách âm chuẩn quốc tế đảm 
                    bảo chất lượng âm thanh sống động nhất cho từng thước phim bom tấn.</p>
                <br>
                <div class="add-hotline">
                    <p><span style="font-weight: bold">Address:</span> Groovy Hà Nội, TP Hà Nội</p>
                    <p><span style="font-weight: bold">Hotline:</span> 0123456789</p>
                </div>
            </div>
            <div id="info-text-Sài-Gòn" class="info-text" style="display: none;">
                <p>Thành lập vào tháng 12 năm 2021, Groovy Sài Gòn có vị trí trung tâm. Rạp tự hào là rạp phim tư nhân duy nhất và đầu tiên sở hữu hệ thống phòng chiếu phim đạt chuẩn 
                    Hollywood tại Tỉnh Sài Gòn.</p>
                <p>Rạp được trang bị hệ thống máy chiếu, phòng chiếu hiện đại với 100% nhập khẩu từ nước ngoài, với 4 
                    phòng chiếu tương được 300 ghế ngồi. Hệ thống âm thanh Dolby 7.1 và hệ thống cách âm chuẩn quốc tế đảm 
                    bảo chất lượng âm thanh sống động nhất cho từng thước phim bom tấn.</p>
                <br>
                <div class="add-hotline">
                    <p><span style="font-weight: bold">Address:</span> Groovy Sài Gòn, Tỉnh Sài Gòn</p>
                    <p><span style="font-weight: bold">Hotline:</span> 9876543210</p>
                </div>
            </div>
            <div id="info-text-Quảng-Ninh" class="info-text" style="display: none;">
                <p>Thành lập vào tháng 1 năm 2023, Groovy Quảng Ninh có vị trí trung tâm. Rạp tự hào là rạp phim tư nhân duy nhất và đầu tiên sở hữu hệ thống phòng chiếu phim đạt chuẩn 
                    Hollywood tại Tỉnh Quảng Ninh.</p>
                <p>Rạp được trang bị hệ thống máy chiếu, phòng chiếu hiện đại với 100% nhập khẩu từ nước ngoài, với 4 
                    phòng chiếu tương được 200 ghế ngồi. Hệ thống âm thanh Dolby 7.1 và hệ thống cách âm chuẩn quốc tế đảm 
                    bảo chất lượng âm thanh sống động nhất cho từng thước phim bom tấn.</p>
                <br>
                <div class="add-hotline">
                    <p><span style="font-weight: bold">Address:</span> Groovy Quảng Ninh, Tỉnh Quảng Ninh</p>
                    <p><span style="font-weight: bold">Hotline:</span> 1357902468</p>
                </div>
            </div>

        </div>

        <br>
        <!-- The dots/circles -->
        <div class="title">
            <div class="movies">
                <div>MOVIES SHOWING</div>
            </div>

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
    <script><%@include file="/static/js/cinemaInfo.js" %></script>
    <script>
                    function showInfo() {
                        var select = document.getElementById("location");
                        var selectedOption = select.value;
                        var infoHaNoi = document.getElementById("info-text-Hà-Nội");
                        var infoSaiGon = document.getElementById("info-text-Sài-Gòn");
                        var infoQuangNinh = document.getElementById("info-text-Quảng-Ninh");
                        console.log(selectedOption);
                        if (selectedOption === "0") {
                            infoHaNoi.style.display = "block";
                            infoSaiGon.style.display = "none";
                            infoQuangNinh.style.display = "none";
                        } else if (selectedOption === "1") {
                            infoHaNoi.style.display = "none";
                            infoSaiGon.style.display = "block";
                            infoQuangNinh.style.display = "none";
                        } else if (selectedOption === "2") {
                            infoHaNoi.style.display = "none";
                            infoSaiGon.style.display = "none";
                            infoQuangNinh.style.display = "block";
                        }
                    }
    </script>
</html>
