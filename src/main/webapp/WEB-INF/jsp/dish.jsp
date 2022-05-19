<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-header">
                <div class="col-md-6 left-alignment"><h5>${dish.name}</h5></div>
                <c:if test="${myRestaurant != null}">
                    <div class="col-md-6 right-alignment"><a href="/removeDish/${dish.id}">Remove Dish</a></div>
                </c:if>
            </div>
            <div class="card-img justify-content-center">
                <img class="dishImg" src="/dishImage/${dish.getRestaurantId()}/${dish.id}"/>
            </div>
            <div class="card-footer">
                <div class="col-md-10 left-alignment" style="display: block">
                    <p>Price: ${dish.price} JOD</p><br/>
                    ${dish.description}
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3"></div>
</div>
<c:import url="footer.jsp"/>