<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form role="form" class="login-form" action="/addDish" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="dishName">
                    Dish Name
                </label>
                <input name="dishName" type="text" class="form-control" id="dishName" />
            </div>
            <div class="form-group">
                <label for="price">
                    Price
                </label>
                <input name="price" type="number" class="form-control" id="price" step="0.01"/>
            </div>
            <div class="form-group">
                <label for="description">
                    Description
                </label>
                <input name="description" type="text" class="form-control" id="description" />
            </div>
            <div class="form-group">
                <input id="imageFile" name="imageFile" type="file" class="form-control-file" multiple>
            </div>
            <button type="submit" class="btn btn-primary">
                Add
            </button>
        </form>
    </div>
    <div class="col-md-4"></div>
</div>
<c:import url="footer.jsp"/>