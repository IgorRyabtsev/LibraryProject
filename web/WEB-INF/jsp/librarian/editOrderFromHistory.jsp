<jsp:useBean id="order" scope="request"
             type="main.java.controllers.model.Orders"/>

<jsp:useBean id="message" scope="request"
             class="java.lang.String"/>

<c:if test="${user_session.role==null || user_session.role.equals('user')}">
    <c:redirect url="/jsp/main.jsp"></c:redirect>
</c:if>

<c:if test="${!message.equals('')}">
    <h1> <fmt:message key='${message}'/> </h1>
</c:if>


<h1><fmt:message key="order.edit"/></h1>

<div class="row">
    <div class="col-md-6">
        <div class="col-lg-12">
            <div class="panel panel-success">
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example2">
                        <thead>
                        <tr>
                            <th><h3><fmt:message key="orders.info"/></h3></th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <p>	<br>
                                    <fmt:message key="reader"/> : ${order.reader.namer_f} ${order.reader.namer_s} ${order.reader.namer_p}  <br>
                                    <fmt:message key="orders.bookname"/>: ${order.instance.book.name_b} <br>
                                    <fmt:message key="orders.publish"/>: ${order.instance.publish} <br>
                                    <fmt:message key="orders.cost"/>: ${order.instance.cost} <br>
                                    <fmt:message key="orders.year"/>: ${order.instance.year_b} <br>
                                    <fmt:message key="orders.comments"/>: ${order.instance.comments} <br>
                                    <fmt:message key="release_date"/>: ${order.release_date} <br>
                                    <fmt:message key="return_date"/>: ${order.return_date} <br>
                                    <c:if test="${order.return_date!=null}">
                                        <form name="orderBook" id="order" action="/deleteOrderFromHistory?id=${order.id_o}" method="post">
                                            <button type="submit" class="btn btn-info btn-lg btn-block"><fmt:message key="order.delete"/></button>
                                        </form>
                                    </c:if>

                                    <form name="orderBook" id="order" action="/editOrderFromHistory?id=${order.id_o}" method="post">

                                        <fmt:message key="release_date"/>:
                                        <div class="form-group">
                                            <input type="date" class="form-control" name="release_date" id="release_date">
                                        </div>

                                        <c:if test="${order.return_date!=null}">
                                            <fmt:message key="return_date"/>
                                            <div class="form-group">
                                                <input type="date" class="form-control" name="return_date" id="return_date">
                                            </div>
                                        </c:if>
                                        <button type="submit" class="btn btn-info btn-lg btn-block"><fmt:message key="order.edit_button"/></button>
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