<%@ page import="com.example.hojozat.entities.UserEntity" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/profile.css"/>
<link rel="stylesheet" href="/css/home.css"/>
<%
    pageContext.setAttribute("user", (UserEntity)session.getAttribute("requestedUser"));
%>
<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4 container">
        <div class="row">
            <div class="text-center image-center"><img src="/profileImage/${user.userId}" width="200" height="200" class="rounded-circle"> </div>
        </div>
        <div class="row" style="display: block;">
            <span style="display: block">${user.firstName} ${user.lastName}</span>
            <span style="display: block">Email ${user.email}</span>
            <span style="display: block">Phone Number: ${user.phone}</span>
        </div>
    </div>
    <div class="col-md-4"></div>
</div>
<c:import url="footer.jsp"/>