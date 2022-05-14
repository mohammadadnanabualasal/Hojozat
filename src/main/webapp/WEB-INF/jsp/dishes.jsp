<%@ page import="com.example.hojozat.entities.RestaurantEntity" %>
<%@ page import="com.example.hojozat.entities.ReservationEntity" %>
<%@ page import="com.example.hojozat.entities.UserEntity" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>
<c:set var="restaurant" value="${requestedRestaurant}"/>
<%
    pageContext.setAttribute("restaurantUser", session.getAttribute("restaurantUser"));
    pageContext.setAttribute("user", session.getAttribute("user"));
    if (session.getAttribute("user") != null && session.getAttribute("requestedRestaurant") != null) {
        pageContext.setAttribute("thereIsReservation",
                ReservationEntity.getReservationsByUserAndRestaurant(((UserEntity) session.getAttribute("user")).getUserId(),
                        ((RestaurantEntity) session.getAttribute("requestedRestaurant")).getId()) != null);
    }else {
        pageContext.setAttribute("thereIsReservation", false);
    }
%>
<c:if test="${requestedRestaurant == null and myRestaurant != null}">
    <c:set var="restaurant" value="${myRestaurant}"/>
</c:if>
<c:forEach var="dish" items="${dishesList}">
    <div class="card">
        <div class="card-header">
            <div class="col-md-6 left-alignment"><h5>${dish.name}</h5></div>
            <c:if test="${myRestaurant != null}">
                <div class="col-md-6 right-alignment"><i class='far fa-edit'
                                                         onclick="location.href='/editDish/${dish.id}'"></i></div>
            </c:if>
        </div>
        <div class="card-img justify-content-center">
            <img class="dishImg" src="/dishImage/${restaurant.id}/${dish.id}"/>
        </div>
        <div class="card-footer">
            <div class="col-md-10 left-alignment" style="display: block">
                <p>Price: ${dish.price} JOD</p><br/>
                <p>${dish.description}</p>
            </div>
            <c:if test="${myRestaurant == null and restaurantUser == null }">
                <div class="col-md-2 right-alignment">
                    <c:choose>
                        <c:when test="${thereIsReservation}">
                            <span class='bi bi-plus-circle' id="numberOf${dish.id}Dish-add"
                                  onclick="chnageNumberOfDishes(true, ${dish.id})">${dish.getNumberOfOrderedDishes(restaurant.id, user.userId, dish.id)}</span>
                            <span class='bi bi-dash-circle' id="numberOf${dish.id}Dish-minus" onclick="chnageNumberOfDishes(false, ${dish.id})"></span>
                        </c:when>
                        <c:otherwise>
                            <span class='bi bi-plus-circle gray' id="numberOf${dish.id}Dish-add">0</span>
                            <span class='bi bi-dash-circle gray' id="numberOf${dish.id}Dish-minus"></span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>
</c:forEach>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script>
    function chnageNumberOfDishes(plus, dishId) {
        var number = document.getElementById('numberOf' + dishId + 'Dish-add').innerText;
        var cartNumber = document.getElementById('cartNumberOfOrders').innerText;
        if (plus) {
            document.getElementById('numberOf' + dishId + 'Dish-add').innerText = ++number;
            $.get("/addDishToCart/" + dishId + "?numberOfDishes=" + number, function () {

            });
            document.getElementById('cartNumberOfOrders').innerText = ++cartNumber;
        } else {
            if (number > 0) {
                $.get("/addDishToCart/" + dishId + "?numberOfDishes=" + (--number), function () {

                });
                document.getElementById('numberOf' + dishId + 'Dish-add').innerText = number;
                document.getElementById('cartNumberOfOrders').innerText = --cartNumber;
            } else if (number == 0) {
                $.get("/addDishToCart/" + dishId + "?numberOfDishes=0", function () {

                });
            }
        }
    }
</script>