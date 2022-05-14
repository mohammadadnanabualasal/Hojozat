<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.hojozat.entities.UserEntity" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<%
    pageContext.setAttribute("user", (UserEntity) session.getAttribute("user"));
%>
<link href="/css/header.css" rel="stylesheet">
<div class="row">
    <div class="col-md-12">
        <div class="row header">
            <div class="col-md-4">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/home">Home</a>
                    </li>
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${user != null or restaurantUser != null}">
                                <a class="nav-link" href="/logout">Logout</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="/login">Login</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="nav-item">
                        <c:if test="${user == null and restaurantUser == null}">
                            <a class="nav-link" href="/register">Register</a>
                        </c:if>
                    </li>
                    <c:set var="numberOfOrders">
                        <c:if test="${user.getCartNumberOfOrders() > 0}">${user.getCartNumberOfOrders()}</c:if>
                    </c:set>
                    <li class="nav-item">
                        <c:if test="${user != null}">
                            <a class="nav-link" href="/cart"><i class="fa fa-shopping-cart"><span
                                    id="cartNumberOfOrders"
                                    style="color: red;">${numberOfOrders}</span></i></a>
                        </c:if>
                    </li>
                    <li class="nav-item">
                        <c:if test="${user != null && user.isAdmin.equals('YES')}">
                            <a class="nav-link" href="/admin">Admin Page</a>
                        </c:if>
                    </li>
                    <c:if test="${restaurantUser != null}">
                        <li class="nav-item">
                                <a class="nav-link" href="/reservations">Show Reservations</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="col-md-4 center-alignment">
                <ul class="nav" style="margin-top: -35px;">
                    <li class="nav-item">
                        <div class="input-group rounded">
                            <img class="profile-img-rounded-circle" style="width: 150px;height: 150px;"
                                 alt="Bootstrap Image Preview" src="/profileImage/1"/>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="col-md-4 right-alignment">
                <c:if test="${user != null}">
                    <a class="nav-link" href="/profile">
                        <img class="profile-img-rounded-circle" alt="Bootstrap Image Preview"
                             src="/profileImage/${user.userId}"/>
                    </a>
                </c:if>
                <c:if test="${restaurantUser != null}">
                    <a class="nav-link" href="/myRestaurant">
                        <img class="profile-img-rounded-circle" alt="Bootstrap Image Preview"
                             src="/restaurantImage/${restaurantUser.id}"/>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <ul class="nav">
            <li class="nav-item" style="width: 100%">
                <div class="input-group">
                    <input name="term" id="term" type="search" class="form-control rounded" placeholder="Search"
                           aria-label="Search"
                           aria-describedby="search-addon" value="${term}"/>
                    <span class="input-group-text border-0" id="search-addon"><a href="#" onclick="doSearch()"><i
                            class="fas fa-search"></i></a></span>
                </div>
            </li>
        </ul>
    </div>
    <div class="col-md-4"></div>
</div>
<script>
    function doSearch() {
        let term = document.getElementById("term");
        location.replace('/search?term=' + term.value);
    }
</script>