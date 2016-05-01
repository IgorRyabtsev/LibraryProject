<jsp:useBean id="allReaders" scope="request"
             type="java.util.List<main.java.controllers.model.Reader>"/>

<c:if test="${user_session.role==null || user_session.role.equals('user')}">
    <c:redirect url="/jsp/main.jsp"></c:redirect>
</c:if>

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
                                    <td> <a href="/readerhistory?id=${reader.id_r}"> ${reader.namer_f} ${reader.namer_s} ${reader.namer_p} </a></td>
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
                            <th><fmt:message key='allbooks.search'/></th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <div >
                                    <form name="searchReaders" id="contactForm" action="/searchbyemail" method="post">
                                        <div class="form-group">
                                            <label class="control-label" for="email" id=""><fmt:message key='email'/></label>
                                            <input class="form-control input-sm" type="text" id="email" name="email">
                                        </div>
                                        <button type="submit" class="btn btn-info"><fmt:message key='allbooks.search'/></button>
                                    </form>
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