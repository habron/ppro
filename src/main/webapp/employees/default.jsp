<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Zaměstnanci
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Zaměstnanci</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/zamestnanci/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Seznam zaměstnanců</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Příjmení</th>
                            <th>Jméno</th>
                            <th>Email</th>
                            <th>Telefon</th>
                            <th>Role</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${employees}" var="employee">
                            <tr>
                                <td>${employee.lastname}</td>
                                <td>${employee.firstname}</td>
                                <td>${employee.email}</td>
                                <td>${employee.phone}</td>
                                <td>${employee.role.name}</td>
                                <td><a href="/zamestnanci/upravit/${employee.id}"><i class="fas fa-edit"></i></a></td>
                                <td>
                                    <form:form method="post" action="/zamestnanci" modelAttribute="employeeModel" id="del${employee.id}">
                                        <form:hidden path="id" value="${employee.id}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${employee.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
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