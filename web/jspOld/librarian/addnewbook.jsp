<jsp:useBean id="message" scope="request"
             class="java.lang.String"/>

<c:if test="${!message.equals('')}">
    <h1><fmt:message key="${message}"/></h1>
</c:if>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header"><fmt:message key="order.addnewbook"/></h1>
    </div>
</div>
<div class="row ">
    <div class="col-md-6">
        <br>
        <form name="sentMessage" id="contactForm" action="/addnewbook" method="post">

            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="allbooks.namef"/>:</label>
                    <input type="text" name="namef" class="form-control" id="namef" required
                           data-validation-required-message="Please enter first number.">
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="allbooks.names"/>:</label>
                    <input type="text" name="names" class="form-control" id="names" required
                           data-validation-required-message="Please enter second name.">

                    <p class="help-block"></p>
                </div>
            </div>

            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="allbooks.namep"/>:</label>
                    <input type="text" name="namep" class="form-control" id="namep">

                    <p class="help-block"></p>
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="yearb"/>:</label>
                    <input type="text" name="yearbirth" class="form-control" id="yearbirth" ">

                    <p class="help-block"></p>
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="orders.bookname"/>:</label>
                    <input type="text" name="bookname" class="form-control" id="bookname" required
                           data-validation-required-message="Please enter bookname.">
                    <p class="help-block"></p>
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="orders.year"/>:</label>
                    <input type="text" name="year" class="form-control" id="year" required
                           data-validation-required-message="Please enter book year.">
                    <p class="help-block"></p>
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="orders.publish"/>:</label>
                    <input type="text" name="publish" class="form-control" id="publish" required
                           data-validation-required-message="Please enter publsh.">
                    <p class="help-block"></p>
                </div>
            </div>
            <div class="control-group form-group">
                <div class="controls">
                    <label><fmt:message key="orders.cost"/>:</label>
                    <input type="text" name="cost" class="form-control" id="cost" required
                           data-validation-required-message="Please enter cost.">
                    <p class="help-block"></p>
                </div>
            </div>

            <div id="success"></div>
            <!-- For success/fail messages -->
            <button type="submit" class="btn btn-info"><fmt:message key="order.addnewbook_button"/></button>
        </form>
    </div>


    <div class="col-md-6">
        <img class="img-responsive img-rounded" src="../img/mainPageFont55.png" alt="Central library">
    </div>

</div>

