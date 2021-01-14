<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<c:if test="${!empty flashes}">
<div class="container-fluid">
    <div class="row">
        <div class="col">

            <c:forEach items="${flashes}" var="flash">
            <div class="alert alert-${flash.type} alert-dismissible">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <c:choose>
                    <c:when test="${flash.type == 'danger'}">
                        <i class="icon fa fa-ban"></i>
                    </c:when>
                    <c:when test="${flash.type == 'warning'}">
                        <i class="icon fa fa-warning"></i>
                    </c:when>
                    <c:when test="${flash.type == 'info'}">
                        <i class="icon fa fa-info"></i>
                    </c:when>
                    <c:when test="${flash.type == 'success'}">
                        <i class="icon fa fa-check"></i>
                    </c:when>
                </c:choose>
                    ${flash.message}
            </div>
            </c:forEach>

        </div>
    </div>
</div>
</c:if>

