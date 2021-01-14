<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Skříňka
        <small>upravit</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/mistnosti">Šatna</a></li>
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
                        <li><a href="/satna"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>
                    </ul>
                </div>
            </div>

            <div class="box box-default">
                <form:form method="post" action="/satna/zakaznik/${locker.id}" modelAttribute="historyModel">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="timeTo">Počet hodin:</form:label>
                            <form:input path="timeTo" type="number" required="required" cssClass="form-control" value="${currentTime}" />
                        </div>
                        <div class="form-group">
                            <form:label path="userID">Zákazník:</form:label>
                            <form:select path="userID" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte zákazníka ---"/>
                                <c:forEach items="${customers}" var="customer">
                                    <option value="${customer.id}">${customer.firstname} ${customer.lastname}</option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>
                    <div class="box-footer">
                        <c:if test="${locker.free}">
                            <button type="submit" class="btn btn-primary">Přidat</button>
                        </c:if>
                        <c:if test="${!locker.free}">
                            <button type="submit" disabled class="btn btn-primary">Přidat</button>
                        </c:if>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Upravit skříňku</h3>
                </div>
                <form:form method="post" action="/satna/upravit/${locker.id}" modelAttribute="lockerModel" id="lockerForm" onsubmit="return validate();">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="number">Číslo</form:label>
                            <form:input path="number" required="required" value="${locker.number}" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Uložit</button>
                    </div>
                </form:form>

            </div>

            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Historie</h3>
                </div>

                <div class="box-body">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Zákazník</th>
                                <th>Od</th>
                                <th>Na hodin</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${locker.lockerHistory}" var="history">
                                <tr>
                                    <td>${history.user.firstname} ${history.user.lastname}</td>
                                    <td><fmt:formatDate value="${history.date}" pattern="d.M.y HH:mm" /></td>
                                    <td>${history.timeTo}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

</section>

<script>
    function validate() {

        var number = document.forms["lockerForm"]["number"].value;
        if (isNaN(number)) {
            alert("Číslo musí být číslo");
            return false;
        }

        return true;
    }
</script>

<jsp:include page="../layout_footer.jsp" />