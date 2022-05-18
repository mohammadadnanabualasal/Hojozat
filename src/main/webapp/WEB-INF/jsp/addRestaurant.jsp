<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form role="form" class="login-form" action="/addRestaurant" method="post" enctype="multipart/form-data">
            <div class="form-group">

                <label for="restaurantName">
                    Restaurant Name
                </label>
                <input name="restaurantName" type="text" class="form-control" id="restaurantName" required/>
            </div>
            <div class="form-group">

                <label for="restaurantEmail">
                    Email address
                </label>
                <input name="restaurantEmail" type="email" class="form-control" id="restaurantEmail" required/>
            </div>
            <div class="form-group">

                <label for="restaurantPhoneNumber">
                    Restaurant Phone Number
                </label>
                <input name="restaurantPhoneNumber" type="text" class="form-control" id="restaurantPhoneNumber" required/>
            </div>
            <div class="form-group">

                <label for="restaurantLocation">
                    Restaurant Location
                </label>
                <input name="restaurantLocation" type="text" class="form-control" id="restaurantLocation" required/>
            </div>
            <p> Serving Hours:</p>
            <div class="form-group">

                <label for="fromTime">
                    From
                </label>
                <input name="fromTime" type="time" class="form-control" id="fromTime" required/>
            </div>
            <div class="form-group">

                <label for="toTime">
                    To
                </label>
                <input name="toTime" type="time" class="form-control" id="toTime" required/>
            </div>
            <div class="form-group">
                <input name="familiesSection" type="checkbox" id="familiesSection" />
                <label for="familiesSection">
                    Families Section?
                </label>
            </div>
            <div class="form-group">
                <label for="numberOfTables">
                    Number of Available Tables
                </label>
                <input name="numberOfTables" type="number" id="numberOfTables" min="1" required/>
            </div>
            <div class="form-group">
                <label for="about">
                    About the Restaurant:
                </label>
                <br/>
                <textarea name="about" id="about" cols="80" rows="10" required></textarea>
            </div>
            <div class="form-group">
                <input id="imageFile" name="imageFile" type="file" class="form-control-file" multiple>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">
                    Password
                </label>
                <input name="password" type="password" class="form-control" id="exampleInputPassword1" required/>
            </div>
            <div class="form-group">
                <label for="confirmPassword">
                    Confirm your Password
                </label>
                <input name="confirmPassword" type="password" class="form-control" id="confirmPassword" required/>
            </div>
            <button type="submit" class="btn btn-primary">
                Add
            </button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<c:import url="footer.jsp"/>