<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layout_header.jsp" />
<section class="content-header">
    <h1>
        Události
        <small>upravit</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="/"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="/udalosti">Události</a></li>
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
                        <li><a href="/udalosti"><i class="fa fa-arrow-left"></i> <span>Zpět</span></a></li>

                    </ul>
                </div>

            </div>
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Přihlášení uživatelé</h3>
                </div>
                <div class="box-body">
                    <ol>
                        <c:forEach items="${event.loginUsers}" var="loginUser" >
                            <li>${loginUser.lastname} ${loginUser.firstname}</li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
            <div class="box box-default">
                <div class="box-header with-border">
                    <h2 class="box-title">Historie</h2>
                </div>
                <div class="box-body" style="max-height: 302px; overflow-y: auto">
                    <c:forEach items="${event.history}" var="history">
                        <p><fmt:formatDate value="${history.date}" pattern="d.M.y H:m" /> - ${history.text}</p>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Upravit událost</h3>
                </div>
                <form:form method="post" action="/udalosti/upravit/${event.id}" modelAttribute="eventModel" id="eventForm" onsubmit="return validate();">
                    <div class="box-body">
                        <div class="form-group">
                            <form:label path="name">Název</form:label>
                            <form:input path="name" required="required" value="${event.name}" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <form:label path="description">Popis</form:label>
                            <form:textarea path="description" value="${event.description}" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="dateFrom">Od</form:label>
                            <form:input path="dateFrom" type="date" value="${event.dateFrom}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="dateTo">Do</form:label>
                            <form:input path="dateTo" type="date" value="${event.dateTo}" required="required" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Kapacita</form:label>
                            <form:input path="capacity" required="required" value="${event.capacity}" cssClass="form-control" />
                        </div>
                        <div class="form-group">
                            <form:label path="roomId">Místnost</form:label>
                            <form:select path="roomId" required="required" cssClass="form-control" multiple="false">
                                <form:option value="0" label="--- Vyberte místnost ---"/>
                                <c:forEach items="${roomsId}" var="type"  varStatus="status">
                                    <c:choose>
                                        <c:when test="${type.key == event.roomId}">
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
            <div class="box box-warning direct-chat direct-chat-warning">
                <div class="box-header with-border">
                    <h3 class="box-title">Poznámky</h3>
                </div>
                <div class="box-body">
                        <!-- Conversations are loaded here -->
                    <div class="direct-chat-messages">
                        <!-- Message. Default to the left -->
                        <c:forEach items="${event.notes}" var="note">
                        <div class="direct-chat-msg right">
                            <div class="direct-chat-info clearfix">
                                <span class="direct-chat-name pull-left">${note.employee.firstname} ${note.employee.lastname}</span>
                                <span class="direct-chat-timestamp pull-right"><fmt:formatDate value="${note.date}" pattern="d.M.y H:m" /></span>
                            </div>
                            <!-- /.direct-chat-info -->
                            <div class="direct-chat-text">
                                ${note.text}
                            </div>
                            <!-- /.direct-chat-text -->
                        </div>
                        </c:forEach>
                        <!-- /.direct-chat-msg -->
                    </div>
                </div>
                <div class="box-footer">
                    <form:form method="post" action="/udalosti/poznamka/${event.id}" modelAttribute="noteModel">
                        <div class="input-group">
                            <form:input path="text" required="required" placeholder="Napište poznámku..." cssClass="form-control" />
                            <span class="input-group-btn">
                            <button type="submit" class="btn btn-warning btn-flat">Odeslat</button>
                          </span>
                        </div>
                    </form:form>
                </div>
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