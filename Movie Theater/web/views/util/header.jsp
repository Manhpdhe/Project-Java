<%-- 
    Document   : header
    Created on : 17-05-2023, 10:34:08
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<header class="header">
        <div style="cursor: pointer" onclick="goTo('${pageContext.request.contextPath}/home')" class="logo">
            <img src="${pageContext.request.contextPath}/static/images/groovy_cine_full_logo.svg">
        </div>
           
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/branchinfo">Cinema</a>
            <a href="${pageContext.request.contextPath}/movielist">Schedule</a>
            <a href="${pageContext.request.contextPath}/views/public/member_Benefit.jsp">Membership Benefits</a>
            <c:if test="${sessionScope.customer ne null}">
                <c:choose>
                    <c:when test="${sessionScope.customer.isVerified()}">
                        <button onclick="toggleUserBox()" class="btnLogin"><i>${sessionScope.customer.getAccountName()}</i></button>
                    </c:when>
                    <c:otherwise>
                        <button onclick="goTo('${pageContext.request.contextPath}/login')" class="btnLogin">Sign in/Register</button>
                    </c:otherwise>
                </c:choose>  
            </c:if>
            <c:if test="${sessionScope.customer eq null}">
                <button onclick="goTo('${pageContext.request.contextPath}/login')" class="btnLogin">Sign in/Register</button>
            </c:if>
            
        </nav>
        
        
    </header>
     <c:if test="${sessionScope.customer ne null}">
                <div style="display: none" id="infobox">
                    <a href="${pageContext.request.contextPath}/profile"><p>View Profile</p></a>
                    <a href="${pageContext.request.contextPath}/logout"><p>Log out</p></a>
                </div>  
            </c:if>   
        <script>
            function goTo(loc) {
                window.location.href = loc;
            }
            
            var inf = document.getElementById('infobox');
            function toggleUserBox() {
                if(inf.style.display == "none") {
                    inf.style.display = "block";
                } else {
                    inf.style.display = "none";
                }
            }
        </script>
