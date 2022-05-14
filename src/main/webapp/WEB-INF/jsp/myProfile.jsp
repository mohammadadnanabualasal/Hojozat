<%@ page import="com.example.hojozat.entities.UserEntity" %>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link rel="stylesheet" href="/css/profile.css"/>
<link rel="stylesheet" href="/css/home.css"/>
<%
    pageContext.setAttribute("user", (UserEntity) session.getAttribute("user"));
%>
<c:import url="header.jsp"/>
<div class="container-fluid main-container">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4 container">
                    <div class="row">
                        <div class="text-center image-center"><img src="/profileImage/${user.userId}" width="200" height="200" class="rounded-circle"> </div>
                    </div>
                    <div class="row" style="display: block;">
                        <span style="display: block">${user.firstName} ${user.lastName}</span>
                        <span style="display: block">Email ${user.email}</span>
                        <span style="display: block">Phone Number: ${user.phone}</span>
                        <span style="display: block;"><a style="color: red" href="/removeMyAccount">Remove My Account</a></span>
                    </div>
                </div>
                <div class="col-md-4">
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
