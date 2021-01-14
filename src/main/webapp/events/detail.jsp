<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        </div>
        <div class="col-md-9">
            <div class="box box-default">
                <div class="box-header with-border">
                    <h3 class="box-title">Upravit událost</h3>
                </div>

                <div class="box-body">
                    <table class="table table-bordered">
                        <tr>
                            <th>Název:</th>
                            <td>${event.name}</td>
                        </tr>
                        <tr>
                            <th>Popis:</th>
                            <td>${event.description}</td>
                        </tr>
                        <tr>
                            <th>Instruktor:</th>
                            <td>${event.userCreate.firstname} ${event.userCreate.lastname}</td>
                        </tr>
                        <tr>
                            <th>Od:</th>
                            <td><fmt:formatDate value="${event.dateFrom}" pattern="d.M.y" /></td>
                        </tr>
                        <tr>
                            <th>Do:</th>
                            <td><fmt:formatDate value="${event.dateTo}" pattern="d.M.y" /></td>
                        </tr>
                        <tr>
                            <th>Přihlášeno:</th>
                            <td>${event.loggedUsersCount} / ${event.capacity}</td>
                        </tr>
                        <tr>
                            <th>Místnost:</th>
                            <td>${event.room.roomType.name}</td>
                        </tr>
                    </table>
                </div>
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
                        <div class="direct-chat-msg">
                            <div class="direct-chat-info clearfix">
                                <span class="direct-chat-name pull-left">${note.user.firstname} ${note.user.lastname}</span>
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
            </div>
        </div>
    </div>

</section>

<jsp:include page="../layout_footer.jsp" />