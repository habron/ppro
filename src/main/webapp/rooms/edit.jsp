<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Místnosti
        <small>upravit</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/mistnosti">Místnosti</a></li>
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

                        <li><a href="/mistnosti"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Upravit místnost</h3>
                </div>
                <form:form method="post" action="/mistnosti/upravit/${room.id}" modelAttribute="roomModel" onsubmit="return validate();">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="roomTypeId">Typ místnosti</form:label>
                            <form:select path="roomTypeId" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte typ ---"/>
                                <c:forEach items="${roomTypesId}" var="type"  varStatus="status">
                                    <c:choose>
                                        <c:when test="${type.key == room.roomType.id}">
                                            <option value="${type.key}" selected="true">${type.value}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${type.key}">${type.value}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </form:select>

                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Kapacita</form:label>
                            <form:input path="capacity" required="required" value="${room.capacity}" cssClass="form-control" />
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Uložit</button>
                    </div>
                </form:form>

            </div>
            <div class="box box-default">
                <div class="box-header">
                    <h3 class="box-title">Detail</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <th>Popis</th>
                            <td>${room.roomType.description}</td>
                        </tr>
                        <tr>
                            <th>Provozní řád</th>
                            <td>${room.roomType.operatingRules}</td>
                        </tr>
                    </table>
                </div>

            </div>
        </div>
    </div>

</section>

<script>
    function validate() {
        var select = document.forms["roomForm"]["roomTypeId"].value;
        if (select == 0) {
            alert("Vyberte typ místnosti");
            return false;
        }
        var capacity = document.forms["roomForm"]["capacity"].value;
        if (isNaN(capacity)) {
            alert("Kapacita musí být číslo");
            return false;
        }

        return true;
    }
</script>

<jsp:include page="../layout_footer.jsp" />