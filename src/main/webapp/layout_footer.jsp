<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

        </div>

        <footer class="main-footer">
            <div class="container">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 0.4.2
                </div>
                <strong>Copyright © 2019 <a href="https://habrondrej.cz">Ondřej Habr</a>.</strong> All rights
                reserved.
            </div>
        </footer>

    </div>



<!-- jQuery 3 -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- datepicker -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/js/adminlte.min.js"></script>
<script src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/js/scripts.js"></script>
</body>
</html>
