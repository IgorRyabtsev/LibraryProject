<jsp:useBean id="allReaders" scope="request"
             type="java.util.List<main.java.controllers.model.Reader>"/>


<h2><fmt:message key='readers'/></h2>
<div class="row">
    <div class="col-md-8">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-success">
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th><fmt:message key='reader'/> </th>
                                <th><fmt:message key='yearb'/></th>
                                <th><fmt:message key='email'/></th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${allReaders}" var="reader">
                                <tr>
                                    <td> <a href="/addlibrarianform?id=${reader.id_r}"> ${reader.namer_f} ${reader.namer_s} ${reader.namer_p} </a></td>
                                    <td> ${reader.year}</td>
                                    <td> ${reader.email}</td>
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