<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Typy místností
        <small>přehled</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Místnosti</a></li>
        <li class="active"><a href="#">Typy místností</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">

                        <li><a href="/typy-mistnosti/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                        <li class="header">Menu</li>
                        <li><a href="/mistnosti"><i class="fa fa-link"></i> <span>Místnosti</span></a></li>
                        <li class="active"><a href="/typy-mistnosti"><i class="fa fa-link"></i> <span>Typy místností</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Typy místností</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Název</th>
                            <th>Popis</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${roomTypes}" var="roomType">
                            <tr>
                                <td>${roomType.id}</td>
                                <td>${roomType.name}</td>
                                <td>${roomType.description}</td>
                                <td><a href="/typy-mistnosti/upravit/${roomType.id}"><i class="fas fa-edit"></i></a></td>
                                <td>
                                    <form:form method="post" action="/typy-mistnosti" modelAttribute="roomTypeModel" id="del${roomType.id}">
                                        <form:hidden path="id" value="${roomType.id}" />
                                        <form:hidden path="name" value="${roomType.name}" />
                                        <form:hidden path="description" value="${roomType.description}" />
                                        <form:hidden path="operatingRules" value="${roomType.operatingRules}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${roomType.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
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