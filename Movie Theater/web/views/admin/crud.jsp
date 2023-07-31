<%-- 
    Document   : crud
    Created on : 28-05-2023, 12:30:31
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/views/util/imports.jsp" %>
    </head>
    <body>
    <div class="sidebar">
        <header>Admin Name</header>
        <ul>
            <li><a href="#"></a>Room</li>
            <li><a href="#"></a>Movie</li>
            <li><a href="#"></a>Cinema</li>
            <li><a href="#"></a>Seat</li>
            <li><a href="#"></a>User</li>
        </ul>
    </div>
    <div class="dashboard">
        <button class="crt">Create</button>
        <table>
            <thead>
                <tr>
                    <td>RoomID</td>
                    <td>Room Name</td>
                    <td>No of Seats</td>
                    <td>.....</td>
                    <td>.....</td>
                    <td>Update</td>
                    <td>Delete</td>
    
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>RoomID</td>
                    <td>Room Name</td>
                    <td>No of Seats</td>
                    <td>.....</td>
                    <td>.....</td>
                    <td>Update</td>
                    <td>Delete</td>
    
                </tr>
                <tr>
                    <td>RoomID</td>
                    <td>Room Name</td>
                    <td>No of Seats</td>
                    <td>.....</td>
                    <td>.....</td>
                    <td>Update</td>
                    <td>Delete</td>
    
                </tr>
                <tr>
                    <td>RoomID</td>
                    <td>Room Name</td>
                    <td>No of Seats</td>
                    <td>.....</td>
                    <td>.....</td>
                    <td>Update</td>
                    <td>Delete</td>
    
                </tr>
            </tbody>
        </table>
        <div class="pagination">
            <button class="prev">Prev</button>
            <ul>
                <li class="link" value="1">1</li>
                <li class="link" value="2">2</li>
                <li class="link" value="3">3</li>
                <li class="link" value="4">4</li>
                <li class="link" value="5">5</li>
                <li class="link" value="6">6</li>
                <li class="link" value="7">7</li>
            </ul>
            <button class="next">Next</button>
        </div>
    </div>
</body>
</html>
