<jsp:useBean id="reader" scope="request"
             type="main.java.controllers.model.Reader"/>

<c:if test="${user_session.role==null || user_session.role.equals('user')}">
    <c:redirect url="/jsp/main.jsp"></c:redirect>
</c:if>

<br>
<h1><fmt:message key='librarianstatus'/>:</h1>
<div class="row">
    <div class="col-md-6">
        <div class="col-lg-12">
            <div class="panel panel-success">
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example2">
                        <thead>
                        <tr>
                            <th><h3><fmt:message key='info'/>:</h3></th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <p>	<br>
                                    <fmt:message key='reader'/>: ${reader.namer_f} ${reader.namer_s} ${reader.namer_p}<br>
                                    <fmt:message key='email'/>: ${reader.email} <br>
                                    <fmt:message key='yearb'/>: ${reader.year} <br>
                                <form name="deleteBook" id="order" action="/addlibrarianform?id=${reader.id_r}" method="post">
                                    <button type="submit" class="btn btn-info btn-lg btn-block"><fmt:message key='makelibrarian'/></button>
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <img class="img-responsive img-rounded" src="../img/manPageFont6.jpg" alt="Central library">
    </div>
</div>