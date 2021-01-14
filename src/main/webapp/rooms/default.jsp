<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Místnosti
        <small>přehled</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Místnosti</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">

                        <li><a href="/mistnosti/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                        <li class="header">Menu</li>
                        <li class="active"><a href="/mistnosti"><i class="fa fa-link"></i> <span>Místnosti</span></a></li>
                        <li><a href="/typy-mistnosti"><i class="fa fa-link"></i> <span>Typy místností</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Seznam místností</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Název</th>
                            <th>Kapacita</th>
                            <th>&nbsp;</th>
                        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                            <th>&nbsp;</th>
                        </security:authorize>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${rooms}" var="room">
                            <tr>
                                <td>${room.id}</td>
                                <td>${room.roomType.name}</td>
                                <td>${room.capacity}</td>
                                <td><a href="/mistnosti/upravit/${room.id}"><i class="fas fa-edit"></i></a></td>
                                <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                                <td>
                                    <form:form method="post" action="/mistnosti" modelAttribute="roomModel" id="del${room.id}">
                                        <form:hidden path="id" value="${room.id}" />
                                        <form:hidden path="capacity" value="${room.capacity}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${room.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
                                    </form:form>
                                </td>
                                </security:authorize>
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