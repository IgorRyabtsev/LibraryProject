<jsp:useBean id="instances" scope="request" type="java.util.List<java.util.Map<main.java.controllers.model.Instance, java.util.List<main.java.controllers.model.Author>> >"/>
<br><h1><fmt:message key='List_of_books'/></h1><br>

    <div class="row">
        <div class="col-md-8">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-success">

                        <div class="panel-body">

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                <tr>
                                    <th><fmt:message key='author'/></th>
                                    <th><fmt:message key='book'/></th>
                                    <th><fmt:message key='year'/></th>
                                    <th><fmt:message key='publish'/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${instances}" var="inst">
                                    <c:forEach items="${inst}" var="insta">
                                        <tr>
                                            <td><c:forEach items="${insta.value}" var="au">
                                                ${au.name_f} ${au.name_s} ${au.name_p} <br>
                                            </c:forEach>
                                            </td>
                                            <td>${insta.key.book.name_b}</td>
                                            <td>${insta.key.year_b}</td>
                                            <td>${insta.key.publish}</td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
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
                                    <c:if test="${user_session.role == null}">
                                        <h4> <fmt:message key='allbook.info'/></h4>
                                    </c:if>
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
        <!-- /.col-md-4 -->
    </div>
<!-- /.row -->
