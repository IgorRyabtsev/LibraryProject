<div class="container">

    <form class="form-signin" action="/login" method="post">
        <h2 class="form-signin-heading"><fmt:message key="lohin.login_please"/></h2>
        <label for="inputEmail" class="sr-only"><fmt:message key="lohin.email"/></label>
        <input type="text" id="inputEmail" size="25" class="form-control" placeholder="email"  name="email" required autofocus>
        <label for="inputPassword" class="sr-only"><fmt:message key="lohin.password"/></label>
        <input type="password" id="inputPassword" class="form-control" placeholder="password" name="password" required>

        <button class="btn btn-lg btn-block btn-info" size="25" type="submit"><fmt:message key="lohin.login"/></button>
    </form>

</div> <!-- /container -->