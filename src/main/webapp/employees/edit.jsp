<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Zaměstnanci
        <small>upravit</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/zamestnanci">Zaměstnanci</a></li>
        <li class="active"><a href="#">Upravit</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/zamestnanci"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Zaměstnanec</h3>
                </div>
                <form:form method="post" action="/zamestnanci/upravit/${employee.id}" modelAttribute="employeeModel" onsubmit="return validate()" id="employeeForm">
                    <div class="box-body">
                        <form:hidden path="addressId" value="${employee.addressId}" />
                        <form:hidden path="loginId" value="${employee.loginId}" />
                        <div class="form-group">
                            <form:label path="firstname">Jméno</form:label>
                            <form:input path="firstname" value="${employee.firstname}" required="required" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <form:label path="lastname">Příjmení</form:label>
                            <form:input path="lastname" value="${employee.lastname}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="bornDate">Datum narození</form:label>
                            <form:input path="bornDate" type="date" value="${employee.bornDate}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" value="${employee.email}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="phone">Telefon</form:label>
                            <form:input path="phone" value="${employee.phone}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="personalIdentificationNumber">Rodné číslo</form:label>
                            <form:input path="personalIdentificationNumber" value="${employee.personalIdentificationNumber}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.city">Město</form:label>
                            <form:input path="address.city" value="${employee.address.city}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.country">Stát</form:label>
                            <form:input path="address.country" value="${employee.address.country}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.street">Ulice a čp</form:label>
                            <form:input path="address.street" value="${employee.address.street}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.zip">PSČ</form:label>
                            <form:input path="address.zip" value="${employee.address.zip}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="login.username">Uživatelské jméno</form:label>
                            <form:input path="login.username" value="${employee.login.username}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="role.id">Role</form:label>
                            <form:select path="role.id" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte roli ---"/>
                                <c:forEach items="${roles}" var="type"  varStatus="status">
                                    <c:choose>
                                        <c:when test="${type.key == employee.role.id}">
                                            <option value="${type.key}" selected="true">${type.value}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.key}">${type.value}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </form:select>

                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Uložit</button>
                    </div>
                </form:form>

            </div>
        </div>
    </div>

</section>

<script>
    function validate() {
        var select = document.forms["employeeForm"]["role.id"].value;
        if (select == 0) {
            alert("Vyberte roli");
            return false;
        }

        return true;
    }
</script>

<jsp:include page="../layout_footer.jsp" />