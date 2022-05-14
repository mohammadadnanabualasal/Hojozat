<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>
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
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <div class="row">
                <img class="restaurantImg img-full" src="https://media.timeout.com/images/105846896/750/422/image.jpg"/>
            </div>
            <div class="row justify-content-center">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link ${activeTables} tablinks" aria-current="page"
                           onclick="openTab(event, 'tables')"><h3>
                            Tables</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${menuActive} tablinks" onclick="openTab(event, 'menu')"><h3>Menu</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link tablinks" onclick="openTab(event, 'about')"><h3>About the Restaurant</h3></a>
                    </li>
                </ul>
            </div>
            <div class="row tabcontent" id="menu" style="display: ${displayMenu};">
                <div><h4>Add new Dish</h4> <span class='bi bi-plus-circle restaurant-page' id="add-dish"
                                                 onclick="location.href='/addDish'"></span>
                </div>
                <c:import url="dishes.jsp"/>
            </div>
            <div class="row tabcontent" id="tables" style="display: ${displayTables};">
                <p>
                    change the serving hours:
                </p>
                <form action="/updateRestaurantInfo">
                    <div class="form-group">

                        <label for="fromTime">
                            From
                        </label>
                        <select name="fromTime" id="fromTime" class="form-select form-select-lg mb-3"
                                aria-label="select the time">
                            <c:forEach begin="0" end="23" var="hour">
                                <c:set var="isSelected">
                                    <c:if test="${hour eq myRestaurant.servingFromTime.getHours()}">selected</c:if>
                                </c:set>
                                <option value="${hour}" ${isSelected}>${hour}:00</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">

                        <label for="toTime">
                            To
                        </label>
                        <select name="toTime" id="toTime" class="form-select form-select-lg mb-3"
                                aria-label="select the time">
                            <c:forEach begin="0" end="23" var="hour">
                                <c:set var="isSelected">
                                    <c:if test="${hour eq myRestaurant.servingToTime.getHours()}">selected</c:if>
                                </c:set>
                                <option value="${hour}" ${isSelected}>${hour}:00</option>
                            </c:forEach>
                        </select>
                    </div>
                    <p class="error-message">${timeError}</p>
                    <div class="form-group">
                        <label for="numberOfTables">Number Of Tables:</label>
                        <input type="number" min="1" max="10" id="numberOfTables" name="numberOfTables" value="${myRestaurant.tablesNumber}">
                    </div>
                    <div class="form-group">
                        <c:set var="checkedFamiliesSection">
                            <c:if test="${myRestaurant.familiesSection eq 'YES'}">checked</c:if>
                        </c:set>
                        <label for="familiesSection">Families Section?</label>
                        <input name="familiesSection" id="familiesSection" type="checkbox" ${checkedFamiliesSection}/>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                </form>
            </div>
            <div class="row tabcontent" id="about" style="display: none;">
                <p>${myRestaurant.about}</p>
                <p>
                    Location: ${myRestaurant.location}<br/>
                    Serving hours: ${myRestaurant.getServingFromTime()} To ${myRestaurant.getServingToTime()}<br/>
                    Phone number: ${myRestaurant.phone}<br/>
                    Families section? ${myRestaurant.familiesSection}<br/>
                </p>
            </div>
        </div>
        <div class="col-md-4">
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
