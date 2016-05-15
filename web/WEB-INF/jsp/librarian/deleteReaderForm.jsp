<jsp:useBean id="orders" scope="request"
             type="java.util.List<main.java.controllers.model.Orders>"/>

<jsp:useBean id="reader" scope="request"
             class="java.lang.String"/>

<jsp:useBean id="message" scope="request"
             class="java.lang.String"/>


<c:if test="${!message.equals('')}">
    <h1> <fmt:message key='${message}'/> </h1>
</c:if>

<h1><fmt:message key="deleteUser"/>:</h1>
<div class="row">
    <div class="col-md-6">
        <div class="col-lg-12">
            <div class="panel panel-success">
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-hover" id="dataTables-example2">
                        <thead>
                        <tr>
                            <th><h2> <fmt:message key="booksToTaking"/> </h2></th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                <c:if test="${!(orders.size()==0)}">
                                    <c:forEach items="${orders}" var="order">
                                        <h3> <fmt:message key="book"/>: </h3>
                                        <p>
                                            <fmt:message key="orders.bookname"/>: ${order.instance.book.name_b} <br>
                                            <fmt:message key="orders.publish"/>: ${order.instance.publish} <br>
                                            <fmt:message key="orders.cost"/>: ${order.instance.cost} <br>
                                            <fmt:message key="orders.year"/>: ${order.instance.year_b} <br>
                                            <fmt:message key="orders.comments"/>: ${order.instance.comments} <br>
                                            <fmt:message key="release_date"/>: ${order.release_date} <br>
                                            <fmt:message key="return_date"/>: ${order.return_date} <br>
                                        </p>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${(orders.size()==0)}">
                                    <h3> <fmt:message key="allBooksInLibrary"/> <br> </h3>
                                    <form name="orderBook" id="order" action="/deleteReaderForm?id=${reader}" method="post">
                                        <br>
                                        <button type="submit" class="btn btn-info btn-lg btn-block"><fmt:message key="deleteUser"/></button>
                                    </form>
                                </c:if>
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