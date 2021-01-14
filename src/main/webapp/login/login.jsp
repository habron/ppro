<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Přihlásit se</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="../resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome 5 -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <!-- Ionicons -->
    <link rel="stylesheet" href="../resources/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../resources/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="../resources/css/skins/skin-black.min.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="../resources/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="../resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">


</head>
<body class="hold-transition login-page">

<div class="login-box">
    <div class="login-logo">
        <h1>Tak ŠUP</h1>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">Přihlaste se</p>
        <c:if test="${not empty error}">
            <p class="login-box-msg" style="color:red">Chybné přihlašovací údaje!</p>
        </c:if>
        <form method="post" action="/doLogin">

            <div class="form-group has-feedback">
                <input id="username" name="username" required type="text" class="form-control" placeholder="Uživatelské jméno" />
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input id="password" name="password" required class="form-control" type="password" placeholder="Heslo" />
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <button type="submit" class="btn btn-primary btn-block btn-flat">Přihlásit se</button>

        </form>

        <a href="/register">Registrovat se</a>
    </div>
</div>

<!-- jQuery 3 -->
<script src="../resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="../resources/bower_components/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="../resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- datepicker -->
<script src="../resources/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="../resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="../resources/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="../resources/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="../resources/js/adminlte.min.js"></script>
</body>
</html>


