<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Zákazníci
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Zákazníci</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/zakaznici/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Seznam zákazníků</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Příjmení</th>
                            <th>Jméno</th>
                            <th>Email</th>
                            <th>Telefon</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${customers}" var="customer">
                            <tr>
                                <td>${customer.lastname}</td>
                                <td>${customer.firstname}</td>
                                <td>${customer.email}</td>
                                <td>${customer.phone}</td>
                                <td><a href="/zakaznici/upravit/${customer.id}"><i class="fas fa-edit"></i></a></td>
                                <td>
                                    <form:form method="post" action="/zakaznici" modelAttribute="customerModel" id="del${customer.id}">
                                        <form:hidden path="id" value="${customer.id}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${customer.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
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