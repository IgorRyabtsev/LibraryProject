<jsp:useBean id="history" scope="request"
             type="java.util.List<main.java.controllers.model.Orders>"/>



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
                                        <td>
                                            <a href="/editOrderFromHistory?id=${hist.id_o}"> ${hist.instance.book.name_b} </a>
                                        </td>
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
                            <th><fmt:message key='allbooks.search'/></th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <div >
                                    <form name="searchReaders" id="contactForm" action="/searchbyemailForHistory" method="post">
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