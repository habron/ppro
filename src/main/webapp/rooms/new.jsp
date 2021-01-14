<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Místnosti
        <small>nová</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/mistnosti">Místnosti</a></li>
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

                        <li><a href="/mistnosti"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Nová místnost</h3>
                </div>
                <form:form method="post" action="/mistnosti/pridat" modelAttribute="room" id="roomForm" onsubmit="return validate()">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="roomTypeId">Typ místnosti</form:label>
                            <form:select path="roomTypeId" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte typ ---"/>
                                <form:options items="${roomTypesId}"/>
                            </form:select>

                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Kapacita</form:label>
                            <form:input path="capacity" required="required" cssClass="form-control" />
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