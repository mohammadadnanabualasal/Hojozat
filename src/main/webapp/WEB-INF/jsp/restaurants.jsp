<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <div id="restaurant-section">
            <c:forEach var="restaurant" items="${restaurants}" begin="${(pageNumber*pageSize)}"
                       end="${(pageNumber +1 )*pageSize - 1}">
                <div class="card" onclick="location.href='/restaurant/${restaurant.id}';">
                    <div class="card-header">
                        <div class="col-md-6 left-alignment"><h5>${restaurant.name}</h5></div>
                        <div class="col-md-6 right-alignment"><a href="/removeRestaurant/${restaurant.id}">Remove the Restaurant</a></div>
                    </div>
                    <div class="card-img justify-content-center">
                        <img class="card-restaurant-img"
                             src="https://media.timeout.com/images/105846896/750/422/image.jpg"/>
                    </div>
                    <div class="card-footer">
                        <div class="col-md-12 left-alignment">
                            <p style="margin: 10px">
                                <span style="display: block">Email: <b>${restaurant.email}</b></span>
                                <span style="display: block">Phone Number: <b>${restaurant.phone}</b></span>
                                <span style="display: block">Location: <b>${restaurant.location}</b></span>
                                <span style="display: block">Families section? <b
                                        style="color: green">${restaurant.familiesSection}</b></span>
                                <span style="display: block">Serving hours: <b>${restaurant.getServingFromTime()} To ${restaurant.getServingToTime()}</b></span>
                                <span style="display: block">Number of available Tables <b>${restaurant.tablesNumber}</b></span>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-12 justify-content-center">
            <c:if test="${restaurants.size() gt pageSize}">
                <nav class="justify-content-center">
                    <ul class="pagination">
                        <c:forEach var="number" begin="1" end="${pages}">
                            <li class="page-item">
                                <a class="page-link" href="/restaurants?pageNumber=${number}">${number}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>
<div class="col-md-4"></div>
<c:import url="footer.jsp"/>