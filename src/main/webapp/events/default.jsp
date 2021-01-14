<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Události
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Události</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/udalosti/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Události</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Název</th>
                            <th>Od</th>
                            <th>Do</th>
                            <th>Instruktor</th>
                            <th>Přihlášeno</th>
                            <th>Místnost</th>

                        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER')">
                            <th>&nbsp;</th>
                        </security:authorize>
                        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_INSTRUCTOR')">
                            <th>&nbsp;</th>
                        </security:authorize>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${events}" var="event">
                            <tr>
                                <td>${event.id}</td>
                                <td><a href="/udalosti/detail/${event.id}" title="Detail události">${event.name}</a></td>
                                <td><fmt:formatDate value="${event.dateFrom}" pattern="d.M.y" /></td>
                                <td><fmt:formatDate value="${event.dateTo}" pattern="d.M.y" /></td>
                                <td>${event.userCreate.firstname} ${event.userCreate.lastname}</td>
                                <td>${event.loggedUsersCount} / ${event.capacity}</td>
                                <td>${event.room.roomType.name}</td>
                                <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER', 'ROLE_INSTRUCTOR')">
                                <td><a href="/udalosti/upravit/${event.id}"><i class="fas fa-edit"></i></a></td>
                                </security:authorize>
                                <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')">
                                    <td>
                                        <form:form method="post" action="/udalosti" modelAttribute="eventModel" id="del${event.id}">
                                            <form:hidden path="id" value="${event.id}" />
                                            <a href="javascript:{}" onclick="document.getElementById('del${event.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
                                        </form:form>
                                    </td>
                                </security:authorize>
                                <security:authorize access="hasAnyRole('ROLE_CUSTOMER')">
                                    <td>
                                        <form:form method="post" action="/udalosti" modelAttribute="eventModel" id="del${event.id}">
                                            <form:hidden path="id" value="${event.id}" />
                                            <c:if test="${event.capacity > event.loggedUsersCount || event.signedIn}">
                                                <a href="javascript:{}" class="btn btn-primary" onclick="document.getElementById('del${event.id}').submit();">
                                                    <c:if test="${event.signedIn}">
                                                        Odhlásit se
                                                    </c:if>
                                                    <c:if test="${!event.signedIn}">
                                                        Přihlásit se
                                                    </c:if>
                                                </a>
                                            </c:if>
                                            <c:if test="${event.capacity <= event.loggedUsersCount && !event.signedIn}">
                                                <button disabled class="btn btn-primary" title="Plná kapacita">
                                                    Přihlásit se
                                                </button>
                                            </c:if>
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