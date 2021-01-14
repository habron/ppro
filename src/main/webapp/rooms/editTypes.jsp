<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Místnosti
        <small>upravit typ</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/typy-mistnosti">Místnosti</a></li>
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

                        <li><a href="/typy-mistnosti"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Upravit typ místnosti</h3>
                </div>
                <form:form method="post" action="/typy-mistnosti/upravit/${roomType.id}" modelAttribute="roomTypeModel">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="name">Název</form:label>
                            <form:input path="name" required="required" cssClass="form-control" value="${roomType.name}" />
                        </div>
                        <div class="form-group">
                            <form:label path="description">Popis</form:label>
                            <form:input path="description" cssClass="form-control" value="${roomType.description}" />
                        </div>
                        <div class="form-group">
                            <form:label path="operatingRules">Provozní řád</form:label>
                            <form:textarea path="operatingRules" cssClass="form-control" rows="10" value="${roomType.operatingRules}" />
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

<jsp:include page="../layout_footer.jsp" />