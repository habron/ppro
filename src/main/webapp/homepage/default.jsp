<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Top Navigation
        <small>Example 2.0</small>
    </h1>
    <ol class="breadcrumb">
        <li class="active"><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Blank Box</h3>
                </div>
                <div class="box-body">
                    The great content goes here
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Přehled událostí</h3>
                </div>
                <div class="box-body">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Název</th>
                                <th>Popis</th>
                                <th>Od</th>
                                <th>Do</th>
                                <th>Místnost</th>
                                <th>Volná místa</th>
                                <th>Instruktor</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${events}" var="event">
                            <tr>
                                <td>${event.name}</td>
                                <td>${event.description}</td>
                                <td><fmt:formatDate value="${event.dateFrom}" pattern="d.M.y" /></td>
                                <td><fmt:formatDate value="${event.dateTo}" pattern="d.M.y" /></td>
                                <td>${event.room.roomType.name}</td>
                                <td>${event.capacity}</td>
                                <td>${event.userCreate.firstname}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>

            </div>
        </div>
    </div>

</section>

<jsp:include page="../layout_footer.jsp" />