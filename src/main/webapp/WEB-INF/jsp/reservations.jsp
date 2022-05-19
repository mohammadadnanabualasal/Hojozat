<%@ page import="com.example.hojozat.entities.RestaurantEntity" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="col-md-12 main-container">
    <div class="row">
        <div class="col-md-3">
        </div>
        <div class="col-md-6">
            <c:forEach var="reservation" items="${reservations}">
                <div class="reservationCard">

                    <h4>
                        <a href="/profile/${reservation.getUser().userId}">${reservation.getUser().firstName} ${reservation.getUser().lastName}</a>
                    </h4>
                    <h6>${reservation.time} ${reservation.numberOfPersons} Persons</h6>
                    <c:forEach items="${reservation.getAllOrders()}" var="order">
                        <div class="card-header">
                            <div class="col-md-6 left-alignment"><h5><a
                                    href="/showDish/${order.getDish().id}">${order.getDish().name}</a></h5></div>
                            <div class="col-md-6 right-alignment">
                                    ${order.quantity} Items
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${reservation.getAllOrders().size() eq 0}">
                        <div class="card-header">
                            No Orders
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-3">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
