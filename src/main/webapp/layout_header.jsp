<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title}</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome 5 -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/css/skins/skin-black.min.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/css/styles.css">



</head>
<body class="skin-black layout-top-nav">
    <div class="wrapper">
        <header class="main-header">
            <nav class="navbar navbar-static-top">
                <div class="container">
                    <div class="navbar-header">
                        <a href="/" class="navbar-brand"><b>Tak</b>ŠUP</a>
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                            <i class="fa fa-bars"></i>
                        </button>
                    </div>
                    <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
                        <ul class="nav navbar-nav">
                            <li <c:if test="${title == 'Homepage'}">class="active"</c:if>><a href="/">Homepage</a></li>
                            <security:authorize access="isAuthenticated()">
                                <security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'INSTRUCTOR')">
                                    <li <c:if test="${title == 'Místnosti'}">class="active"</c:if>><a href="/mistnosti">Místnosti</a></li>
                                </security:authorize>
                                <security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')">
                                    <li <c:if test="${title == 'Předplatné'}">class="active"</c:if>><a href="/predplatne">Předplatné</a></li>
                                    <li <c:if test="${title == 'Šatna'}">class="active"</c:if>><a href="/satna">Šatna</a></li>
                                </security:authorize>
                                        <li <c:if test="${title == 'Události'}">class="active"</c:if>><a href="/udalosti">Události</a></li>
                                <security:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')">
                                    <li <c:if test="${title == 'Zaměstnanci'}">class="active"</c:if>><a href="/zamestnanci">Zaměstnanci</a></li>
                                    <li <c:if test="${title == 'Zákazníci'}">class="active"</c:if>><a href="/zakaznici">Zákazníci</a></li>
                                </security:authorize>
                            </security:authorize>
                        </ul>
                    </div>
                    <security:authorize access="isAuthenticated()">
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- Messages: style can be found in dropdown.less-->
                            <li class="dropdown messages-menu">
                                <!-- Menu toggle button -->
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-envelope-o"></i>
                                    <span class="label label-success">4</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 4 messages</li>
                                    <li>
                                        <!-- inner menu: contains the messages -->
                                        <ul class="menu">
                                            <li><!-- start message -->
                                                <a href="#">
                                                    <div class="pull-left">
                                                        <!-- User Image -->
                                                        <img src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                                                    </div>
                                                    <!-- Message title and timestamp -->
                                                    <h4>
                                                        Support Team
                                                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                                    </h4>
                                                    <!-- The message -->
                                                    <p>Why not buy a new awesome theme?</p>
                                                </a>
                                            </li>
                                            <!-- end message -->
                                        </ul>
                                        <!-- /.menu -->
                                    </li>
                                    <li class="footer"><a href="#">See All Messages</a></li>
                                </ul>
                            </li>
                            <!-- /.messages-menu -->

                            <!-- Notifications Menu -->
                            <li class="dropdown notifications-menu">
                                <!-- Menu toggle button -->
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-bell-o"></i>
                                    <span class="label label-warning">10</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 10 notifications</li>
                                    <li>
                                        <!-- Inner Menu: contains the notifications -->
                                        <ul class="menu">
                                            <li><!-- start notification -->
                                                <a href="#">
                                                    <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                                </a>
                                            </li>
                                            <!-- end notification -->
                                        </ul>
                                    </li>
                                    <li class="footer"><a href="#">View all</a></li>
                                </ul>
                            </li>
                            <!-- Tasks Menu -->
                            <li class="dropdown tasks-menu">
                                <!-- Menu Toggle Button -->
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="fa fa-flag-o"></i>
                                    <span class="label label-danger">9</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="header">You have 9 tasks</li>
                                    <li>
                                        <!-- Inner menu: contains the tasks -->
                                        <ul class="menu">
                                            <li><!-- Task item -->
                                                <a href="#">
                                                    <!-- Task title and progress text -->
                                                    <h3>
                                                        Design some buttons
                                                        <small class="pull-right">20%</small>
                                                    </h3>
                                                    <!-- The progress bar -->
                                                    <div class="progress xs">
                                                        <!-- Change the css width attribute to simulate progress -->
                                                        <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                                                            <span class="sr-only">20% Complete</span>
                                                        </div>
                                                    </div>
                                                </a>
                                            </li>
                                            <!-- end task item -->
                                        </ul>
                                    </li>
                                    <li class="footer">
                                        <a href="#">View all tasks</a>
                                    </li>
                                </ul>
                            </li>
                            <!-- User Account Menu -->
                            <li class="dropdown user user-menu">
                                <!-- Menu Toggle Button -->
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <!-- The user image in the navbar-->
                                    <img src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/img/user2-160x160.jpg" class="user-image" alt="User Image">
                                    <!-- hidden-xs hides the username on small devices so only the image appears. -->
                                    <span class="hidden-xs">
                                        <security:authentication property="principal.userDetails.firstname" /> <security:authentication property="principal.userDetails.lastname" />
                                    </span>
                                </a>
                                <ul class="dropdown-menu">
                                    <!-- The user image in the menu -->
                                    <li class="user-header">
                                        <img src="<c:if test="${isAction}">../</c:if><c:if test="${isParam}">../</c:if>resources/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                                        <p>
                                            <security:authentication property="principal.userDetails.firstname" /> <security:authentication property="principal.userDetails.lastname" />
                                        </p>
                                    </li>
                                    <!-- Menu Footer-->
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <a href="/profil" class="btn btn-default btn-flat">Profil</a>
                                        </div>
                                        <div class="pull-right">
                                            <a href="/doLogout" class="btn btn-default btn-flat">Odhlásit se</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    </security:authorize>
                    <security:authorize access="!isAuthenticated()">
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="/login">Přihlásit se</a>
                            </li>
                            <li>
                                <a href="/register">Registrovat se</a>
                            </li>
                        </ul>
                    </div>
                    </security:authorize>
                </div>
            </nav>
        </header>

        <div class="content-wrapper">
