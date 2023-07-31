<%-- 
    Document   : seat
    Created on : 12-Jun-2023, 23:10:44
    Author     : NGUYEN THANH LUAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seat Booking</title>
    <link rel="stylesheet" href="styleSeatBooking.css">
    <style><%@include file="/static/css/SeatCSS/seatController.css" %></style>
</head>

<body>

    <div class="space"></div>
    <div class="movie-container">
        <label>Pick a movie:</label>
        <select id="movie">
            <option value="10">Avengers: Endgame ($10)</option>
            <option value="12">Joker ($12)</option>
            <option value="8">Toy Story 4 ($8)</option>
            <option value="9">The Lion King ($9)</option>
        </select>
    </div>

    <ul class="showcase">
        <li>
            <div class="seat-blank" id="disable"></div>
            <small>Delete seat</small>
        </li>
        <li>
            <div class="seat selected" id="normal"></div>
            <small>Normal Seat</small>
        </li>
        <li>
            <div class="seat occupied" id="gold"></div>
            <small>Gold Seat</small>
        </li>
    </ul>

    <div class="container">
        <div class="screen"></div>

        <div class="row">
            <div class="row-mark">A</div>
            <div class="seat-blank default row-A col-1"></div>
            <div class="seat-blank default row-A col-2"></div>
            <div class="seat default row-A col-3"></div>
            <div class="seat default row-A col-4"></div>
            <div class="seat default row-A col-5"></div>
            <div class="seat default row-A col-6"></div>
            <div class="seat default row-A col-7"></div>
            <div class="seat default row-A col-8"></div>
            <div class="seat default row-A col-9"></div>
            <div class="seat default row-A col-10"></div>
            <div class="seat default row-A col-11"></div>
            <div class="seat default row-A col-12"></div>
            <div class="seat default row-A col-13"></div>
            <div class="seat default row-A col-14"></div>
            <div class="seat default row-A col-15"></div>
            <div class="seat default row-A col-16"></div>
            <div class="seat-blank default row-A col-17"></div>
            <div class="seat-blank default row-A col-18"></div>

        </div>
        
        <div class="row-container">
            <div class="row">
                <!-- <div class="row-mark"></div> -->
                <div class="seat-col">1</div>
                <div class="seat-col">2</div>
                <div class="seat-col">3</div>
                <div class="seat-col">4</div>
                <div class="seat-col">5</div>
                <div class="seat-col">6</div>
                <div class="seat-col">7</div>
                <div class="seat-col">8</div>
                <div class="seat-col">9</div>
                <div class="seat-col">10</div>
                <div class="seat-col">11</div>
                <div class="seat-col">12</div>
                <div class="seat-col">13</div>
                <div class="seat-col">14</div>
                <div class="seat-col">15</div>
                <div class="seat-col">16</div>
                <div class="seat-col">17</div>
                <div class="seat-col">18</div>
            </div>
        </div>

    </div>
</body>
<script><%@include file="/static/js/seatController/seatController.js" %></script>

</html>
