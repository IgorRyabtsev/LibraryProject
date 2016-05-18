<jsp:useBean id="messageSignUp" scope="request"
             class="java.lang.String"/>

<c:if test="${!messageSignUp.equals('')}">
    <h1><fmt:message key="${messageSignUp}"/></h1>
</c:if>

<div class="row">
        <div class="col-lg-12">
            <h1 class="page-header"><fmt:message key="registration"/></h1>
        </div>
    </div>
    <div class="row ">
        <div class="col-md-6">
            <h3><fmt:message key="registration.about_you"/>.</h3>

            <form name="sentMessage" id="contactForm" action="/signup" method="post">
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.namef"/>:</label>
                        <input type="text" name="namef" class="form-control" id="namef" required
                               data-validation-required-message="Please enter your first number.">
                    </div>
                </div>
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.names"/>:</label>
                        <input type="text" name="names" class="form-control" id="names" required
                               data-validation-required-message="Please enter your second name.">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.namep"/>:</label>
                        <input type="text" name="namep" class="form-control" id="namep" required
                               data-validation-required-message="Please enter your name patronic.">

                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.year"/>:</label>
                        <input type="text" name="year" class="form-control" id="year" required
                               data-validation-required-message="Please enter your birth year.">
                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.email"/>:</label>
                        <input type="text" name="email" class="form-control" id="email" required
                               data-validation-required-message="Please enter your email.">
                        <p class="help-block"></p>
                    </div>
                </div>
                <div class="control-group form-group">
                    <div class="controls">
                        <label><fmt:message key="registration.password"/>:</label>
                        <input type="password" name="password" class="form-control" id="password" required
                               data-validation-required-message="Please enter your password.">
                    </div>
                </div>
                <div id="success"></div>
                <button type="submit" class="btn btn-info">Register</button>
            </form>
        </div>

        <div class="col-md-2">
        </div>

        <div class="col-md-4">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example2">
                            <thead>
                            <tr>
                                <th><h3><fmt:message key='allbook.info.header'/></h3></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <div >
                                        <p>	<br>
                                            <fmt:message key='allbook.adress'/><br>
                                        </p>
                                        <p>	<br>
                                            <fmt:message key='allbook.street'/><br>
                                        </p>
                                        <p>	<br>
                                            <fmt:message key='allbook.number'/><br>
                                        </p>
                                        <p>	<br>
                                            <fmt:message key='allbook.email'/><br>
                                        </p>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

