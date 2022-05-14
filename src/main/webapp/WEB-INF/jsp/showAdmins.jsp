<%@ page import="com.example.hojozat.entities.UserEntity" %>
<%@ page import="java.util.List" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>
<%
    pageContext.setAttribute("users", (List<UserEntity>)session.getAttribute("users"));
%>
<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4">

        <c:forEach items="${users}" var="user">
            <div class="card">
                <div   class="card-header" style="display: block;font-size: smaller;">
                    <div class="row">
                        <div class="col-md-6 left-alignment"><h5>${user.firstName} ${user.lastName}</h5></div>
                        <div class="col-md-6 right-alignment"><a href="/removeUser/${user.userId}">Remove the user</a></div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 right-alignment"><a href="/removeUser/${user.userId}">Remove Administration Role</a></div>
                    </div>
                </div>
                <div class="user-card-info">
                    <div class="user-img left-alignment">
                        <img class="userImg" src="/profileImage/${user.userId}"/>
                    </div>
                    <div class="right-alignment">
                        <p style="margin: 10px">
                            <span style="display: block">${user.email}</span>
                            <span style="display: block">${user.phone}</span>
                            <c:if test="${user.isAdmin.equals('YES')}">
                                <span style="display: block; color: red">Admin</span>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
    <div class="col-md-4"></div>
</div>
<c:import url="footer.jsp"/>