<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
      integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/home.css"/>

<c:import url="header.jsp"/>
<div class="row main-container">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form role="form" class="searchForm">
            <div class="form-group">
                <label for="searchByEmail">
                    Search By Name:
                </label>
                <input type="text" class="form-control" id="searchByEmail" oninput="userAjaxSearch()"/>
            </div>
            <div id="usersSection">
            </div>
        </form>
        <form role="form" class="login-form" action="/addAdmin" method="post">
            <div class="form-group">
                <label for="firstName">
                    First Name
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName" required/>
            </div>
            <div class="form-group">
                <label for="lastName">
                    Last Name
                </label>
                <input type="text" class="form-control" id="lastName"  name="lastName" required/>
            </div>
            <div class="form-group">
                <label for="phone">
                    Phone
                </label>
                <input type="number" class="form-control" id="phone"  name="phone" required/>
            </div>
            <div class="form-group">
                <label for="email">
                    Email address
                </label>
                <input type="email" class="form-control" id="email"  name="email" required/>
            </div>
            <div class="form-group">
                <input type="checkbox" class="form-makeItAdmin" id="makeItAdmin"  name="makeItAdmin"/>
                <label for="makeItAdmin">Make it admin?</label>
            </div>
            <div class="form-group">
                <label for="password">
                    Password
                </label>
                <input type="password" class="form-control" id="password" name="password" required/>
            </div>
            <div class="form-group">
                <label for="confirmPassword">
                    Confirm your Password
                </label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required/>
            </div>
            <button type="submit" class="btn btn-primary">
                Create New User
            </button>
        </form>

    </div>
    <div class="col-md-4"></div>
</div>
<c:import url="footer.jsp"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script>
   function userAjaxSearch(){
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/userSearch",
            data: {'term': document.getElementById("searchByEmail").value},
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (res) {
                document.getElementById("usersSection").innerHTML = res;
            },
            error: function (e) {
                document.getElementById("usersSection").innerHTML = res;
            }
        });
   }
</script>