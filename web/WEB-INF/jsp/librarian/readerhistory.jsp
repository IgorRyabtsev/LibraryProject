<jsp:useBean id="history" scope="request"
             type="java.util.List<java.util.Map.Entry<main.java.controllers.model.Orders,java.util.List<main.java.controllers.model.Author>>>"/>


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
                                <th><fmt:message key='author'/></th>
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
                                    <td><c:forEach items="${hist.value}" var="au">
                                    ${au.name_f} ${au.name_s} ${au.name_p} <br>
                                    </c:forEach>
                                    </td>
                                    <td> <c:if test="${hist.key.return_date==null}">
                                        <a href="/returnbook?id=${hist.key.id_o}&id_i=${hist.key.instance.id_i}"> ${hist.key.instance.book.name_b} </a>
                                        </c:if>
                                        <c:if test="${hist.key.return_date!=null}">
                                             ${hist.key.instance.book.name_b}
                                        </c:if>
                                    </td>
                                    <td> ${hist.key.instance.year_b}</td>
                                    <td> ${hist.key.instance.publish}</td>
                                    <td> ${hist.key.release_date}</td>
                                    <td> ${hist.key.return_date}</td>
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