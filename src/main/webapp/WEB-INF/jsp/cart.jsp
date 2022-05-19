<%@ page import="com.example.hojozat.entities.UserEntity" %>
<%@ page import="java.util.List" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>
<%
    pageContext.setAttribute("user", (UserEntity)session.getAttribute("user"));
%>
<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <c:forEach var="reservation" items="${reservations}">
            <div class="row">
                <div class="col-md-6 left-alignment"><h3><a href="/restaurant/${reservation.restaurantId}">${reservation.getRestaurantName()}</a></h3></div>
                <div class="col-md-6 right-alignment"><a style="color: red" href="/cancelReservation/${reservation.id}">Cancel Reservation</a></div>
            </div>
            <h5>Today ${reservation.time}</h5>
            <h5>${reservation.numberOfPersons} Persons</h5>
            <c:forEach items="${reservation.getAllOrders()}" var="order">
                <div class="card">
                    <div class="card-header">
                        <div class="col-md-6 left-alignment"><h5>${order.getDish().name}</h5></div>
                        <div class="col-md-6 right-alignment"></div>
                    </div>
                    <div class="card-img justify-content-center">
                        <img class="dishImg" src="/dishImage/${reservation.restaurantId}/${order.getDish().id}"/>
                    </div>
                    <div class="card-footer">
                        <div class="col-md-10 left-alignment">${order.getDish().description}</div>
<%--                        <div class="col-md-2 right-alignment"><h5>${order.quantity} Items</h5></div>--%>
                        <span class='bi bi-plus-circle' id="numberOf${order.getDish().id}Dish-add"
                              onclick="chnageNumberOfDishes(true, ${order.getDish().id})">${order.getDish().getNumberOfOrderedDishes(reservation.restaurantId, user.userId, order.getDish().id)}</span>
                        <span class='bi bi-dash-circle' id="numberOf${order.getDish().id}Dish-minus" onclick="chnageNumberOfDishes(false, ${order.getDish().id})"></span>
                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </div>
    <div class="col-md-3"></div>
</div>
<c:import url="footer.jsp"/>
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