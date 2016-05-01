<jsp:useBean id="instAuth" scope="request"
             type="java.util.Map.Entry<main.java.controllers.model.Instance, java.util.List<main.java.controllers.model.Author>>"/>

<h1><fmt:message key="instance.delete"/>:</h1>
<div class="row">
    <div class="col-md-6">
        <div class="col-lg-12">
            <div class="panel panel-success">
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example2">
                        <thead>
                        <tr>
                            <th><h3><fmt:message key="orders.information"/></h3></th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <p>	<br>
                                    <fmt:message key="orders.bookname"/>: ${instAuth.key.book.name_b} <br>
                                    <fmt:message key="orders.publish"/>: ${instAuth.key.publish} <br>
                                    <fmt:message key="orders.cost"/>: ${instAuth.key.cost} <br>
                                    <fmt:message key="orders.year"/>: ${instAuth.key.year_b} <br>
                                    <fmt:message key="orders.comments"/>: ${instAuth.key.comments} <br>
                                    <fmt:message key="orders.authors"/>:
                                    <c:forEach items="${instAuth.value}" var="authors">
                                        ${authors.name_f} ${authors.name_s} ${authors.name_p} <br>
                                    </c:forEach>
                                <form name="orderBook" id="order" action="/deleteinstance?id=${instAuth.key.id_i}" method="post">
                                    <button type="submit" class="btn btn-info btn-lg btn-block"><fmt:message key="delete"/></button>
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <img class="img-responsive img-rounded" src="../img/mainPageFont55.png" alt="Central library">
    </div>
</div>