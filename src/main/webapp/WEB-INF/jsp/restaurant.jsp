<%@ page import="com.example.hojozat.entities.RestaurantEntity" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<%
    pageContext.setAttribute("restaurant", session.getAttribute("requestedRestaurant"));
    pageContext.setAttribute("restaurantUser", session.getAttribute("restaurantUser"));
%>
<c:set var="displayTables" value="none"/>
<c:set var="activeTables" value=""/>
<c:set var="displayMenu" value="block"/>
<c:set var="menuActive" value="active"/>
<c:if test="${tableTab}">
    <c:set var="displayTables" value="block"/>
    <c:set var="activeTables" value="active"/>
    <c:set value="" var="menuActive"/>
    <c:set var="displayMenu" value="none"/>
</c:if>

<c:import url="header.jsp"/>
<div class="col-md-12 restaurantPage">
    <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-6">
            <div class="row"><h3>${restaurant.name}</h3></div>
            <div class="row">
                <img class="restaurantImg img-full" src="/restaurantImage/${restaurant.id}"/>
            </div>
            <div class="row justify-content-center">
                <ul class="nav">
                    <c:if test="${restaurantUser == null}">
                        <li class="nav-item">
                        <a class="nav-link ${activeTables} tablinks" aria-current="page"
                           onclick="openTab(event, 'tables')"><h3>
                            Tables</h3></a>
                    </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link ${menuActive} tablinks" onclick="openTab(event, 'menu')"><h3>Menu</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link tablinks" onclick="openTab(event, 'about')"><h3>About the Restaurant</h3></a>
                    </li>
                </ul>
            </div>
            <div class="row tabcontent" id="menu" style="display: ${displayMenu};">
                <c:import url="dishes.jsp"/>
            </div>
            <div class="row tabcontent" id="tables" style="display: ${displayTables};">
                <p>
                    reserve your table now:
                </p>
                <form action="/reserve">
                    <div class="form-group">

                        <label for="fromTime">
                            Time
                        </label>
                        <c:choose>
                            <c:when test="${fn:length(servingHoursList) gt 0}">
                                <select name="fromTime" id="fromTime" class="form-select form-select-lg mb-3"
                                        aria-label="select the time">
                                    <c:set var="servingToTime" value="${restaurant.servingToTime}"/>
                                    <c:if test="${restaurant.servingToTime eq '00:00:00'}">
                                        <c:set var="servingToTime" value="24:00:00"/>
                                    </c:if>
                                    <c:forEach items="${servingHoursList}" var="hour">
                                        <option selected>${hour}:00 to ${hour +1}:00</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <p class="warning">you can't reserve now.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <p class="error-message">${timeError}</p>
                    <input name="restaurantId" type="number" value="${restaurant.id}" hidden>
                    <div class="form-group">
                        <label for="numberOfPersons">Number Of Persons:</label>
                        <input type="number" min="1" max="10" id="numberOfPersons" name="numberOfPersons" value="1" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Reserve</button>
                    </div>
                </form>
            </div>
            <div class="row tabcontent" id="about" style="display: none;">
                <p>${restaurant.about}</p>
                <p>
                    Location: ${restaurant.location}<br/>
                    Serving hours: ${restaurant.getServingFromTime()} To ${restaurant.getServingToTime()}<br/>
                    Phone number: ${restaurant.phone}<br/>
                    Families section? ${restaurant.familiesSection}<br/>
                </p>
            </div>
        </div>
        <div class="col-md-3">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
<script>
    function openTab(evt, tab) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(tab).style.display = "block";
        evt.currentTarget.className += " active";
    }
</script>
