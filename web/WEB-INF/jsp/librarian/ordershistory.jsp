<jsp:useBean id="history" scope="request"
             type="java.util.List<main.java.controllers.model.Orders>"/>


<c:if test="${user_session.role==null || user_session.role.equals('user')}">
    <c:redirect url="/jsp/main.jsp"></c:redirect>
</c:if>

<h2><fmt:message key='userhistory'/></h2>
<div class="row">
    <div class="col-md-8">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th><fmt:message key='reader'/></th>
                                <th><fmt:message key='book'/></th>
                                <th><fmt:message key='year'/></th>
                                <th><fmt:message key='publish'/></th>
                                <th><fmt:message key='release_date'/></th>
                                <th><fmt:message key='return_date'/></th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${history}" var="hist">
                                <tr>
                                    <td> ${hist.reader.namer_f} ${hist.reader.namer_s} ${hist.reader.namer_p}</td>
                                    <td> ${hist.instance.book.name_b}</td>
                                    <td> ${hist.instance.year_b}</td>
                                    <td> ${hist.instance.publish}</td>
                                    <td> ${hist.release_date}</td>
                                    <td> ${hist.return_date}</td>
                                </tr>
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

    <!-- /.col-md-4 -->
</div>
<!-- /.row -->