<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">

    <div class="col-md-3"></div>
    <div class="col-md-6">
        <p>
        <h3><a class="admin-link" href="/restaurants">Restaurants</a></h3><br/>
        <h3><a class="admin-link" href="/users">Users</a></h3><br/>
        <h3><a class="admin-link" href="/addRestaurant">Add New Restaurant</a></h3><br/>
        <h3><a class="admin-link" href="/addAdmin">Add New Admin</a></h3><br/>
        <h3><a class="admin-link" href="/showAdmins">Show Admins</a></h3><br/>
        </p>
    </div>
    <div class="col-md-3"></div>
</div>
<c:import url="footer.jsp"/>