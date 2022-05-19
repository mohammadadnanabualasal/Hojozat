<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>


<c:import url="header.jsp"/>
<div class="container-fluid login-page main-container">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-3">
                </div>
                <div class="col-md-6 login-continer">
                    <form role="form" class="login-form" action="/doRegister" method="post" enctype="multipart/form-data">
                        <div class="form-group">

                            <label for="firstName">
                                First Name
                            </label>
                            <input name="firstName" type="text" class="form-control" id="firstName" required/>
                        </div>
                        <div class="form-group">

                            <label for="lastName">
                                Last Name
                            </label>
                            <input name="lastName" type="text" class="form-control" id="lastName" required/>
                        </div>
                        <div class="form-group">

                            <label for="exampleInputEmail1">
                                Email address
                            </label>
                            <input name="email" type="email" class="form-control" id="exampleInputEmail1" required/>
                        </div>
                        <div class="form-group">
                            <label for="phone">
                                Phone Number
                            </label>
                            <input name="phone" type="number" class="form-control" id="phone" required/>
                        </div>
                        <div class="form-group">
                            <input id="imageFile" name="imageFile" type="file" class="form-control-file" multiple>
                        </div>
                        <div class="form-group">
                            <label for="password">
                                Password
                            </label>
                            <input name="password" type="password" class="form-control" id="password" required/>
                        </div>

                        <div class="form-group">

                            <label for="confirmPassword">
                                Confirm your Password
                            </label>
                            <input name="confirmPassword" type="password" class="form-control" id="confirmPassword" required/>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            Register
                        </button>
                    </form>
                </div>
                <div class="col-md-3">
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>