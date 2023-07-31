<%--
    Document   : movieDetail
    Created on : 10-06-2023, 00:40:57
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--requetScope for user-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Groovy Cineplex</title>
        <%@include file="/views/util/imports.jsp" %>
        <style>
            <%@include file="/static/css/styleMovieDetail.css" %>
            .date-picker-a.selected-date span {
                font-weight: 900;
                color: black;
            }
        </style>
    </head>
    <body>
        <div>
            <%@include file="/views/util/header.jsp" %>
        </div>
        <div class="space"></div>
        <div class="detail-container" style="position: sticky;">
            <div class="trailer">               

                <iframe  width="700" height="445" src="${movie.getURL()}" allow="autoplay; encrypted-media" allowfullscreen></iframe>


                <!-- <button class="cta">Book Now</button> -->
            </div>

            <div class="detail">
                <h2>${movie.getTitle()}</h2>
                <!-- <br> -->
                <c:forEach items="${direct}" var="item" varStatus="loop">
                    <p><span>Director:</span> ${item.getDirector().getFirstName()} ${item.getDirector().getLastName()}<c:if test="${!loop.last}"></c:if>
                        </p>
                </c:forEach>
                <p><span>Cast:</span>
                    <c:forEach items="${castList}" var="item" varStatus="loop">
                        ${item.getActor().getFirstName()} ${item.getActor().getLastName()}<c:if test="${!loop.last}">, </c:if>
                    </c:forEach>
                </p>
                <p><span>Genre:</span>
                    <c:forEach items="${classifyList}" var="item" varStatus="loop">
                        ${item.getGenre().getGenreName()}<c:if test="${!loop.last}">, </c:if>
                    </c:forEach>
                </p>
                <p><span>Running Time:</span> ${movie.getDuration()} minutes</p>
                <p><span>Language:</span> ${movie.getLanguage().getLanguageName()}</p>
                <p><span>Realease Date:</span> ${movie.getRedob()}</p>
                <p><span>Rated:</span> (${movie.getRate().getRatingLabel()}) - ${movie.getRate().getRatingDesc()}</p>
                <p><span>Description:</span> ${movie.getMdesc()}</p>
            </div>
        </div>

        <h2 id="dropdown-title">Muốn có trải nghiệm tốt nhất? Đặt vé dưới đây.</h2>
        <form action="detailsmovie" method="GET" style="margin-left: 45%" class="location">  
            <input name="mid" hidden="true" value="${mid}">
            <select id="location" name="branchId" onchange="submitForm()">
                <option hidden="true" value="0">-- Hãy chọn rạp phim --</option>
                <c:forEach items="${requestScope.branchs}" var="branch">
                    <option value="${branch.branchID}" ${branchId eq branch.branchID ? 'selected' : ''}>
                        ${branch.branchName}
                    </option>
                </c:forEach>
            </select>
        </form>
        <div class="time-picker" style="position: sticky; display: flex; justify-content: center" id="time-picker">
            <hr/>
            <c:forEach items="${date}" var="item">
                <div class="sub-time-picker">
                    <h4 style="text-align: center"><fmt:formatDate value="${item.date}" pattern="dd/MM"/></h4>
                    <c:if test="${item.getShedules().size() eq 0}">
                        <div style="color: red;">Không có suất chiếu</div>
                    </c:if>
                    <c:forEach items="${item.getShedules()}" var="schedule">
                        <button style="margin: 10px 0">
                            <a style="text-decoration: none;" href="${pageContext.request.contextPath}/book?show=${schedule.showID}">
                                <fmt:formatDate value="${schedule.getTimeSlot().getStartTime()}" pattern="H:mm"/>
                            </a>
                        </button>
                    </c:forEach>
                </div>
                <hr/>
            </c:forEach>
        </div>
        <script>
            // Lấy phần tử dropdown title và các phần tử chứa nội dung dropdown
            var dropdownTitle = document.getElementById("dropdown-title");
            var locationElement = document.getElementById("location");
            var slideshowContainer = document.getElementById("slideshow-container");
            var timePicker = document.getElementById("time-picker");

            // Lưu trạng thái ban đầu của các phần tử
            var slideshowContainerDisplay = slideshowContainer.style.display;
            var timePickerDisplay = timePicker.style.display;

            // Ẩn slideshow container và time picker khi trang được tải lại
            slideshowContainer.style.display = "none";
            timePicker.style.display = "none";

            // Lưu trạng thái hiện tại của dropdown
            var isDropdownOpen = false;

            // Lưu trạng thái hiện tại của location
            var isLocationSelected = false;

            // Thêm sự kiện click cho phần tử dropdown title
            dropdownTitle.addEventListener("click", function () {
                // Kiểm tra trạng thái hiện tại của dropdown
                if (isDropdownOpen) {
                    // Nếu dropdown đã mở, thu lại các phần tử
                    locationElement.style.display = "none";
                    slideshowContainer.style.display = "none";
                    timePicker.style.display = "none";
                    isDropdownOpen = false;
                } else {
                    // Nếu dropdown đang đóng, mở phần tử location
                    locationElement.style.display = "block";
                    isDropdownOpen = true;
                }

                // Ẩn dòng chữ "Không có xuất chiếu hôm này" khi thu lại giờ chiếu
                var existingMessage = document.querySelector(".no-showings-message");
                if (existingMessage) {
                    existingMessage.style.display = "none";
                }
            });

            // Thêm sự kiện change cho phần tử location
            locationElement.addEventListener("change", function () {
                var selectedValue = parseInt(this.value);

                if (selectedValue === 0) {
                    // Nếu chọn rạp phim là "-- Hãy chọn rạp phim --"
                    slideshowContainer.style.display = "none";
                    timePicker.style.display = "none";
                    isLocationSelected = false;
                } else {
                    // Nếu chọn rạp phim là 1, 2 hoặc 3
                    slideshowContainer.style.display = slideshowContainerDisplay;
                    timePicker.style.display = timePickerDisplay;
                    isLocationSelected = true;
                }
            });

            // Lấy danh sách các phần tử date-picker-a
            var datePickerElements = document.getElementsByClassName("date-picker-a");

            // Thêm sự kiện click cho các phần tử date-picker-a
            // Tìm vị trí trong đoạn mã JavaScript khi phần tử date-picker-a được click
            for (var i = 0; i < datePickerElements.length; i++) {
                datePickerElements[i].addEventListener("click", function () {
                    var dateText = this.getElementsByTagName("span")[0].innerText;

                    // Loại bỏ in đậm cho tất cả các phần tử date-picker-a
                    for (var j = 0; j < datePickerElements.length; j++) {
                        datePickerElements[j].classList.remove("selected-date");
                    }

                    // In đậm ngày đang được chọn
                    this.classList.add("selected-date");

                    // Kiểm tra nếu ngày là 8/6, 12/6 hoặc 16/6
                    if (dateText === "8/6" || dateText === "12/6" || dateText === "16/6") {
                        // Hiển thị dòng chữ "Không có xuất chiếu hôm này" nếu đã chọn location
                        if (isLocationSelected) {
                            var existingMessage = document.querySelector(".no-showings-message");
                            if (existingMessage) {
                                existingMessage.style.display = "block";
                            } else {
                                var noShowingsMessage = document.createElement("p");
                                noShowingsMessage.innerText = "Không có xuất chiếu hôm nay";
                                noShowingsMessage.classList.add("no-showings-message");

                                var currentElement = this;
                                while (
                                        currentElement.nextElementSibling &&
                                        currentElement.nextElementSibling.classList.contains("date-picker-a")
                                        ) {
                                    currentElement = currentElement.nextElementSibling;
                                }
                                currentElement.parentNode.insertBefore(noShowingsMessage, currentElement.nextSibling);
                            }
                        }
                        timePicker.style.display = "none";
                    } else {
                        // Nếu ngày không phải là 8/6, 12/6 hoặc 16/6
                        var existingMessage = document.querySelector(".no-showings-message");
                        if (existingMessage) {
                            existingMessage.style.display = "none";
                        }

                        if (isLocationSelected) {
                            timePicker.style.display = timePickerDisplay;
                        }
                    }
                });
            }

            function submitForm() {
                const form = document.getElementsByClassName('location')[0];
                form.submit();
            }
        </script>
    </body>
    <div>
        <%@include file="/views/util/footer.jsp" %>
    </div>
</html>

