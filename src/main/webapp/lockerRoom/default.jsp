<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Šatna
        <small>skříňky</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Šatna</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/satna/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Seznam skříněk</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Číslo</th>
                            <th>Volná</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${lockers}" var="locker">
                            <tr>
                                <td>${locker.id}</td>
                                <td>${locker.number}</td>
                                <td>
                                    <c:if test="${locker.free}">
                                        <i class="fas fa-check" style="color: green;"></i>
                                    </c:if>
                                    <c:if test="${!locker.free}">
                                        <i class="fas fa-ban" style="color: red;"></i>
                                    </c:if>
                                </td>
                                <td><a href="/satna/upravit/${locker.id}"><i class="fas fa-edit"></i></a></td>
                                <td>
                                    <form:form method="post" action="/satna" modelAttribute="lockerModel" id="del${locker.id}">
                                        <form:hidden path="id" value="${locker.id}" />
                                        <form:hidden path="number" value="${locker.number}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${locker.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
                                    </form:form>
                                </td>
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