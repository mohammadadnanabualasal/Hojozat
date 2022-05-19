<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/login.css"/>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="container-fluid main-container">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-3">
                </div>
                <div class="col-md-6">
                    <form role="form" action="/doLogin">
                        <div class="form-group">

                            <label for="exampleInputEmail1">
                                Email address
                            </label>
                            <input name="email" type="email" class="form-control" id="exampleInputEmail1" required/>
                        </div>
                        <div class="form-group">

                            <label for="exampleInputPassword1">
                                Password
                            </label>
                            <input name="password" type="password" class="form-control" id="exampleInputPassword1" required/>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            Login
                        </button>
                    </form>
                    <br/>
                    <br/>
                    <h6><a href="/restaurantLogin">Restaurant Login</a></h6>
                </div>
                <div class="col-md-3">
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>