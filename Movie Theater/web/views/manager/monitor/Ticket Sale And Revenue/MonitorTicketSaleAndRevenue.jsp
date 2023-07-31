<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Monitor Ticket Sale And Revenue</title>
        <style>
            <%@include file="/static/css/MonitorCSS/TicketSaleAndRevenue/MonitorTicketSale.css"%>
        </style>
                <%@include file="/views/util/imports.jsp" %>

    </head>

    <body>
        <header class="header">
            <img  src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg"> 
            <div>
                <form action="${pageContext.request.contextPath}/headmanage/revenue" method="post">
                    <select name="cinema-id" id="cinema-select">
                        <option value="allBranch">
                            All Cinema
                        </option>
                        <c:forEach items="${requestScope.branchList}" var="branch">
                            <option value="${branch.branchID}">
                                ${branch.branchName}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit">Confirm</button>
                </form>
            </div>
            <div>Ticket Sale & Revenue</div>
<!--            <div>Caothuludau</div>-->
        </header>
                    <div style="margin:100px"></div>
        <div class="container">
            <h3>Total Tickets Sales of Whole System
<!--                <img src="https://cdn-icons-png.flaticon.com/128/9678/9678508.png" alt="down">
                <img src="https://cdn-icons-png.flaticon.com/128/608/608336.png" alt="up">-->
            </h3>
            <small>${requestScope.trend}</small>
            <h3>${requestScope.totalTicketSale}</h3>
        </div>
        <div id="chart-wrapper">
            <canvas id="chart">

            </canvas>
        </div>
    </body>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        const height = document.getElementById('chart');
        console.log('height:' + height.offsetHeight);
        // Dữ liệu biểu đồ
        var data = [
        <c:forEach items="${requestScope.monthRevenues}" var="entry" varStatus="status">
        { value: ${entry.value}, label: '${entry.key}' } ${!status.last ? ',' : ''}
        </c:forEach>
        ];
        // Tạo biểu đồ
        const ctx = document.getElementById('chart').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.map(entry => entry.label),
                datasets: [
                    {
                        label: "Revenue",
                        data: data.map(entry => entry.value),
                        barThickness: 45
                    }
                ],

            },
            options: {
                // maintainAspectRatio: false,
                responsive: true
            }
        });
    </script>

</html>
