<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Skříňka
        <small>nová</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/mistnosti">Šatna</a></li>
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

                        <li><a href="/satna"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Nová skříňka</h3>
                </div>
                <form:form method="post" action="/satna/pridat" modelAttribute="locker" id="lockerForm" onsubmit="return validate();">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="number">Číslo</form:label>
                            <form:input path="number" required="required" cssClass="form-control" />
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

        var number = document.forms["lockerForm"]["number"].value;
        if (isNaN(number)) {
            alert("Číslo musí být číslo");
            return false;
        }

        return true;
    }
</script>

<jsp:include page="../layout_footer.jsp" />