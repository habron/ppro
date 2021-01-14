<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Profil
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active"><a href="#">Profil</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-5">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>
            </div>

            <security:authorize access="hasAnyRole('ROLE_CUSTOMER')">
                <div class="box box-default">
                    <div class="box-header">
                        <h3 class="box-title">Stav účtu: <c:if test="${user.active}" ></c:if><span style="color:green">aktivní do <fmt:formatDate value="${user.activeDate}" pattern="d.M.y" /></span>
                            <c:if test="${!user.active}"><span style="color: red">neaktivní</span></c:if></h3>
                    </div>
                    <div class="box-body">
                        <table class="table table-bordered">
                            <thead>
                                <th>Název</th>
                                <th>Cena</th>
                                <th></th>
                            </thead>
                            <tbody>
                                <c:forEach items="${subscriptions}" var="subscription">
                                    <tr>
                                        <td>${subscription.name}</td>
                                        <td>${subscription.price} Kč</td>
                                        <td>
                                            <form:form method="post" action="/profil/predplatne" modelAttribute="subscribeModel">
                                                <form:input path="subscriptionID" type="hidden" value="${subscription.id}" />
                                                <button type="submit" class="btn btn-info">Prodloužit</button>
                                            </form:form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="box box-default">
                    <div class="box-header">
                        <h3 class="box-title">Historie předplatného</h3>
                    </div>
                    <div class="box-body">
                        <table class="table table-striped">
                            <thead>
                            <th>Název</th>
                            <th>Cena</th>
                            <th>Datum zakoupení</th>
                            </thead>
                            <tbody>
                            <c:forEach items="${user.subscribes}" var="subscribe">
                                <tr>
                                    <td>${subscribe.subscription.name}</td>
                                    <td>${subscribe.price} Kč</td>
                                    <td><fmt:formatDate value="${subscribe.date}" pattern="d.M.y" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </security:authorize>
        </div>
        <div class="col-md-7">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Osobní údaje</h3>
                </div>
                <form:form method="post" action="/profil" modelAttribute="userModel">
                    <div class="box-body">
                        <form:hidden path="addressId" value="${user.addressId}" />
                        <form:hidden path="loginId" value="${user.loginId}" />
                        <div class="form-group">
                            <form:label path="firstname">Jméno</form:label>
                            <form:input path="firstname" value="${user.firstname}" required="required" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <form:label path="lastname">Příjmení</form:label>
                            <form:input path="lastname" value="${user.lastname}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="bornDate">Datum narození</form:label>
                            <form:input path="bornDate" type="date" value="${user.bornDate}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" value="${user.email}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="phone">Telefon</form:label>
                            <form:input path="phone" value="${user.phone}" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="personalIdentificationNumber">Rodné číslo</form:label>
                            <form:input path="personalIdentificationNumber" value="${user.personalIdentificationNumber}" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.city">Město</form:label>
                            <form:input path="address.city" value="${user.address.city}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.country">Stát</form:label>
                            <form:input path="address.country" value="${user.address.country}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.street">Ulice a čp</form:label>
                            <form:input path="address.street" value="${user.address.street}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="address.zip">PSČ</form:label>
                            <form:input path="address.zip" value="${user.address.zip}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="login.username">Uživatelské jméno</form:label>
                            <form:input path="login.username" value="${user.login.username}" required="required" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Uložit</button>
                    </div>
                </form:form>

            </div>
            <security:authorize access="hasAnyRole('ROLE_CUSTOMER')">
            <div class="box box-default">
                <div class="box-body">
                    <form:form method="POST" action="/profil/obrazek" modelAttribute="imageModel" enctype="multipart/form-data">
                       <div class="form-group">
                           <form:label path="file">Vyberte obrázek na identifikační kartu</form:label>
                           <form:input type="file" path="file" accept="image/*" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                           <input type="submit" value="Submit" class="btn btn-primary" />
                        </div>
                    </form:form>
                </div>
                <c:if test="${user.image != null}">
                    <a href="/profil/download">Stáhnout obrázek</a>
                </c:if>
                <div class="box-footer">

                </div>
            </div>
            </security:authorize>
        </div>
    </div>

</section>

<jsp:include page="../layout_footer.jsp" />