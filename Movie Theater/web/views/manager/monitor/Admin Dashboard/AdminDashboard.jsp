<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <style>
            <%@include file="/static/css/MonitorCSS/Admin Dashboard/AdminDashboard.css"%>
        </style>
        <%@include file="/views/util/imports.jsp" %>

    </head>

    <body>
        <header class="header">
            <!--<a href="${pageContext.request.contextPath}/home">-->
            <img  src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg"> 
            <!--</a>--> 
            <a href="${pageContext.request.contextPath}/headmanage/revenue">Ticket Sales & Revenue</a>
            <!--<div>Ticket Sales & Revenue</div>-->

            <div>Admin name</div>
        </header>

        <div class="container">
            <div>
                <h2>Groovy Cineplex's Infor</h2>
            </div>

            <div class="table-container">
                <div>
                    <h3>Number of Cinemas</h3>
                    <h1>${requestScope.branchNumber}</h1>
                </div>
                <div style="border-left: 1px solid black;">
                    <h3>Number of Rooms</h3>
                    <h1>${requestScope.roomNumber}</h1>
                </div>
                <div style="border-left: 1px solid black;">
                    <h3>Number of Customers</h3>
                    <h1>${requestScope.cusNumber}</h1>
                </div>
            </div>

            <div style="margin-top: 10px;">
                <h2>Website Performance</h2>

                <h3 style="margin-left: 15%; display: inline-block;" class="chart-website-button">
                    Numbers of Buyers 
                </h3>
                <h3 style="margin-left: 10px; display: inline-block;" class="chart-website-button">
                    Numbers of Tickets Sales
                </h3>
            </div>
            <form action="${pageContext.request.contextPath}/headmanage/dashboard" method="POST">
                <input type='hidden' name='action' value='changeBuyersViewType'>
                <div style="display: flex; align-items: center;margin-top: 10px">
                    <div style="text-align: right;">
                        <select name="viewType">
                            <option value="7D">Past 7 days</option>
                            <option value="YM">This year (by month period)</option>
                            <option value="YQ">This year (by quarter period)</option>
                            <option value="7DV7D">Past 7 days versus previous 7 days</option>
                        </select>
                        <button type="submit">Confirm</button>
                    </div>
                </div>
            </form>

            <div class="web-preformance-chart">
                <canvas id="lineChart"></canvas>
            </div>
            <div style="margin-top: 10px;" class="revenue">
                <h2>Revenue</h2>


                <div style="text-align: right">
                    <form action="${pageContext.request.contextPath}/headmanage/dashboard" method="POST">
                        <input type='hidden' name='action' value='changeRevenuesViewType'>
                        <select name="viewType">
                            <option value="7D">Past 7 days</option>
                            <option value="YM">This year (by month period)</option>
                            <option value="YQ">This year (by quarter period)</option>
                            <option value="7DV7D">Past 7 days versus previous 7 days</option>
                        </select>
                        <button type="submit">Confirm</button>
                    </form>
                </div>
            </div>

            <div class="revenue-chart">
                <canvas id="column-chart-1"></canvas>
            </div>
            <div style="margin-top: 10px;" class="top-movies">
                <h2>Top 5 highest watched movies</h2>
                <div style="text-align: right;">
                    <form action="${pageContext.request.contextPath}/headmanage/dashboard" method="POST">
                        <input type='hidden' name='action' value='changeMoviesViewType'>
                        <select name="viewType">
                            <option value="W">This week</option>
                            <option value="M">This month</option>
                            <option value="Q">This quarter</option>
                            <option value="Y">This year</option>
                        </select>
                        <button type="submit">Confirm</button>
                    </form>
                </div>
            </div>
            <div class="top-movies-chart">
                <canvas id="top-movies-chart"></canvas>
            </div>
        </div>  
    </body>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>


        // Dữ liệu biểu đồ
        var buyersData = [
        <c:forEach items="${requestScope.buyers}" var="entry" varStatus="status">
        { value: ${entry.value}, label: '${entry.key}' } ${!status.last ? ',' : ''}
        </c:forEach>
        ];
        var revenuesData = [
        <c:forEach items="${requestScope.revenues}" var="entry" varStatus="status">
        { value: ${entry.value}, label: '${entry.key}' } ${!status.last ? ',' : ''}
        </c:forEach>
        ];
        var moviesData = [
        <c:forEach items="${requestScope.top5Movies}" var="entry" varStatus="status">
        { value: ${entry.value}, label: '${entry.key}' } ${!status.last ? ',' : ''}
        </c:forEach>
        ];
        // Biểu đồ buyers
        const ctx = document.getElementById('lineChart').getContext('2d');
        // Sort the buyersData array based on the date
        buyersData.sort((a, b) => new Date(a.label) - new Date(b.label));
        const chart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: buyersData.map(entry => entry.label),
                datasets: [{
                        label: 'Buyers',
                        data: buyersData.map(entry => entry.value),
                        barThickness: 45
                    }],
            },
            options: {
                // maintainAspectRatio: false,
                responsive: true
            }
        });

        // Biểu đồ revenue
        const col = document.getElementById('column-chart-1').getContext('2d');
        // Sort the buyersData array based on the date
        revenuesData.sort((a, b) => new Date(a.label) - new Date(b.label));
        const colChart = new Chart(col, {
            type: 'bar',
            data: {
                labels: revenuesData.map(entry => entry.label),
                datasets: [{
                        label: 'Revenue (vnd)',
                        data: revenuesData.map(entry => entry.value),
                        barThickness: 45
                    }],
            },
            options: {
                // maintainAspectRatio: false,
                responsive: true
            }
        });


        //Biểu đồ top 5 movie
        const movieChart = document.getElementById('top-movies-chart').getContext('2d');
        const topMovieChart = new Chart(movieChart, {
            type: 'bar',
            data: {
                labels: moviesData.map(entry => entry.label),
                datasets: [{
                        label: 'TicketSale',
                        data: moviesData.map(entry => entry.value),
                        barThickness: 45
                    }],
            },
            options: {
                // maintainAspectRatio: false,
                responsive: true
            }
        });
    </script>

</html>