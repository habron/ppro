<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Předplatné
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Předplatné</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/predplatne/pridat"><i class="fa fa-plus"></i> <span>Přidat</span></a></li>
                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Seznam předplatného</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Název</th>
                            <th>Cena</th>
                            <th>Doba platnosti (dny)</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${subscriptions}" var="subscription">
                            <tr>
                                <td>${subscription.id}</td>
                                <td>${subscription.name}</td>
                                <td>${subscription.price} Kč</td>
                                <td>${subscription.days}</td>
                                <td><a href="/predplatne/upravit/${subscription.id}"><i class="fas fa-edit"></i></a></td>
                                <td>
                                    <form:form method="post" action="/predplatne" modelAttribute="subscriptionModel" id="del${subscription.id}">
                                        <form:hidden path="id" value="${subscription.id}" />
                                        <a href="javascript:{}" onclick="document.getElementById('del${subscription.id}').submit();"><i class="fas fa-trash-alt red"></i></a>
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