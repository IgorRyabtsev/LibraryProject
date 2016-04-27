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

                                        <c:if test="${user_session.role .equals('user')}">
                                        <td><a href="/orderbook?id=${insta.key.id_i}"> ${insta.key.book.name_b} </a></td>
                                        </c:if>
                                        <c:if test="${user_session.role .equals('librarian')}">
                                            <td><a href="/deleteinstance?id=${insta.key.id_i}"> ${insta.key.book.name_b} </a></td>
                                        </c:if>

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
                            <th><fmt:message key='allbooks.search'/></th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <div >
                                    <form name="searchbooks" id="contactForm" action="/searchbook" method="post">
                                        <div class="form-group">
                                            <fmt:message key='author'/>:<br>
                                            <label class="control-label" for="namef" id=""><fmt:message key='allbooks.namef'/></label>
                                            <input class="form-control input-sm" type="text" id="namef" name="namef">
                                            <label class="control-label" for="names"><fmt:message key='allbooks.names'/></label>
                                            <input class="form-control input-sm" type="text" id="names" name="names">
                                            <label class="control-label" for="namep"><fmt:message key='allbooks.namep'/></label>
                                            <input class="form-control input-sm" type="text" id="namep" name="namep">
                                            <br>
                                            <fmt:message key='book'/>:<br>
                                            <label class="control-label" for="bookname"><fmt:message key='allbooks.bookname'/></label>
                                            <input class="form-control input-sm" type="text" id="bookname" name="bookname">
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
