<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Události
        <small>nová</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/udalosti">Události</a></li>
        <li class="active"><a href="#">Přidat</a></li>
    </ol>
</section>
<section class="content container-fluid">
    <jsp:include page="../flash_messages.jsp" />
    <div class="row">
        <div class="col-md-3">
            <div class="box box-default">
                <div class="box-body">
                    <ul class="sidebar-menu">
                        <li><a href="/udalosti"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Nová událost</h3>
                </div>
                <form:form method="post" action="/udalosti/pridat" modelAttribute="event" id="eventForm" onsubmit="return validate();">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="name">Název</form:label>
                            <form:input path="name" required="required" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <form:label path="description">Popis</form:label>
                            <form:textarea path="description" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="dateFrom">Od</form:label>
                            <form:input path="dateFrom" type="date" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="dateTo">Do</form:label>
                            <form:input path="dateTo" type="date" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Kapacita</form:label>
                            <form:input path="capacity" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="roomId">Místnost</form:label>
                            <form:select path="roomId" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte místnost ---"/>
                                <form:options items="${roomsId}"/>
                            </form:select>

                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Přidat</button>
                    </div>
                </form:form>

            </div>
        </div>
    </div>

</section>

<script>
    function validate() {
        var select = document.forms["eventForm"]["roomId"].value;
        if (select == 0) {
            alert("Vyberte místnost");
            return false;
        }
        var number = document.forms["eventForm"]["capacity"].value;
        if (isNaN(number)) {
            alert("Kapacita musí být číslo");
            return false;
        }

        return true;
    }
</script>

<jsp:include page="../layout_footer.jsp" />