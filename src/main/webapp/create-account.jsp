<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 26-Sep-24
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // Get the current URL
    String currentUrl = request.getRequestURI();
    request.setAttribute("currentUrl", currentUrl);
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>DashBoard - Roomify</title>
    <meta
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            name="viewport"
    />
    <link
            rel="icon"
            href="./assets/img/logo-web.png"
            type="image/x-icon"
    />

    <base href="${pageContext.request.contextPath}/">

    <!-- Fonts and icons -->
    <script src="./assets/js/plugin/webfont/webfont.min.js"></script>
    <script>
        WebFont.load({
            google: {families: ["Public Sans:300,400,500,600,700"]},
            custom: {
                families: [
                    "Font Awesome 5 Solid",
                    "Font Awesome 5 Regular",
                    "Font Awesome 5 Brands",
                    "simple-line-icons",
                ],
                urls: ["./assets/css/fonts.min.css"],
            },
            active: function () {
                sessionStorage.fonts = true;
            },
        });
    </script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="./assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="./assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/dashboard.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"></link>
</head>
<body>
<div class="wrapper">
    <!-- Sidebar -->
    <div class="sidebar" data-background-color="light">
        <div class="sidebar-logo">
            <!-- Logo Header -->
            <div class="logo-header" data-background-color="light">
                <a href="./index.html" class="logo">
                    <img
                            src="./assets/img/logo-light.png"
                            alt="navbar brand"
                            class="navbar-brand"
                            height="65"
                    />
                </a>
                <div class="nav-toggle">
                    <button class="btn btn-toggle toggle-sidebar">
                        <i class="gg-menu-right"></i>
                    </button>
                    <button class="btn btn-toggle sidenav-toggler">
                        <i class="gg-menu-left"></i>
                    </button>
                </div>
                <button class="topbar-toggler more">
                    <i class="gg-more-vertical-alt"></i>
                </button>
            </div>
            <!-- End Logo Header -->
        </div>
        <div class="sidebar-wrapper scrollbar scrollbar-inner">
            <div class="sidebar-content">
                <ul class="nav nav-secondary">
                    <li class="nav-item">
                        <a
                                data-bs-toggle="collapse"
                                href="#dashboard"
                                class="collapsed"
                                aria-expanded="false"
                        >
                            <i class="fas fa-home"></i>
                            <p>Dashboard</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="dashboard">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="../../demo1/index.html">
                                        <span class="sub-item">Dashboard 1</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-section">
                <span class="sidebar-mini-icon">
                  <i class="fa fa-ellipsis-h"></i>
                </span>
                        <h4 class="text-section">Quản Trị</h4>
                    </li>
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#announces">
                            <i class="fas fa-bullhorn"></i>
                            <p>Thông Báo</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="announces">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="../components/avatars.html">
                                        <span class="sub-item">Danh sách thông báo</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="../components/buttons.html">
                                        <span class="sub-item">Đăng thông báo</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#home-mgt">
                            <i class="fas fa-newspaper"></i>
                            <p>Tin Đăng Nhà</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="home-mgt">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="../forms/forms.html">
                                        <span class="sub-item">Danh sách tin đăng</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="../forms/forms.html">
                                        <span class="sub-item">Tạo tin đăng</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item active submenu">
                        <a data-bs-toggle="collapse" href="#acc-mgt2">
                            <i class="fas fa-user"></i>
                            <p>Tài Khoản</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="acc-mgt2">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="dashboard/account-list">
                                        <span class="sub-item">Danh sách tài khoản</span>
                                    </a>
                                </li>
                                <li class="active">
                                    <a href="dashboard/create-account">
                                        <span class="sub-item">Tạo tài khoản</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#report-mgt">
                            <i class="fas fa-flag"></i>
                            <p>Báo Cáo</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="report-mgt">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="dashboard/report-list">
                                        <span class="sub-item">Danh sách báo cáo</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item <c:if test="${currentUrl.contains('/homeSharing/permission-list.jsp')}">active submenu</c:if>">
                        <a data-bs-toggle="collapse" href="#permissions">
                            <i class="fas fa-key"></i>
                            <p>Phân Quyền</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="permissions">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="dashboard/permission-list">
                                        <span class="sub-item">Danh sách quản trị viên</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="dashboard/create-moderator">
                                        <span class="sub-item">Tạo quản trị viên</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#blog-mgt">
                            <i class="fa-brands fa-blogger-b"></i>
                            <p>Blog</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="blog-mgt">
                            <ul class="nav nav-collapse">
                                <li>
                                    <a href="dashboard/blog-list">
                                        <span class="sub-item">Danh sách blog</span>
                                    </a>
                                </li>
                                <li>
                                    <a href="dashboard/create-blog">
                                        <span class="sub-item">Tạo blog</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- End Sidebar -->

    <div class="main-panel">
        <div class="main-header">
            <div class="main-header-logo">
                <!-- Logo Header -->
                <div class="logo-header" data-background-color="dark">
                    <a href="./index.html" class="logo">
                        <img
                                src="./assets/img/kaiadmin/logo_light.svg"
                                alt="navbar brand"
                                class="navbar-brand"
                                height="20"
                        />
                    </a>
                    <div class="nav-toggle">
                        <button class="btn btn-toggle toggle-sidebar">
                            <i class="gg-menu-right"></i>
                        </button>
                        <button class="btn btn-toggle sidenav-toggler">
                            <i class="gg-menu-left"></i>
                        </button>
                    </div>
                    <button class="topbar-toggler more">
                        <i class="gg-more-vertical-alt"></i>
                    </button>
                </div>
                <!-- End Logo Header -->
            </div>
            <!-- Navbar Header -->
            <nav class="navbar navbar-header navbar-header-transparent navbar-expand-lg border-bottom">
                <div class="container-fluid">
                    <nav class="navbar navbar-header-left navbar-expand-lg navbar-form nav-search p-0 d-none d-lg-flex">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <button type="submit" class="btn btn-search pe-1">
                                    <i class="fa fa-search search-icon"></i>
                                </button>
                            </div>
                            <input
                                    type="text"
                                    placeholder="Search ..."
                                    class="form-control"
                            />
                        </div>
                    </nav>

                    <ul class="navbar-nav topbar-nav ms-md-auto align-items-center">
                        <li
                                class="nav-item topbar-icon dropdown hidden-caret d-flex d-lg-none"
                        >
                            <a
                                    class="nav-link dropdown-toggle"
                                    data-bs-toggle="dropdown"
                                    href="#"
                                    role="button"
                                    aria-expanded="false"
                                    aria-haspopup="true"
                            >
                                <i class="fa fa-search">

                                </i>
                            </a>
                            <ul class="dropdown-menu dropdown-search animated fadeIn">
                                <form class="navbar-left navbar-form nav-search">
                                    <div class="input-group">
                                        <input
                                                type="text"
                                                placeholder="Search ..."
                                                class="form-control"
                                        />
                                    </div>
                                </form>
                            </ul>
                        </li>
                        <li class="nav-item topbar-icon dropdown hidden-caret">
                            <a
                                    class="nav-link dropdown-toggle"
                                    href="#"
                                    id="messageDropdown"
                                    role="button"
                                    data-bs-toggle="dropdown"
                                    aria-haspopup="true"
                                    aria-expanded="false"
                            >
                                <i class="fa fa-envelope"></i>
                            </a>
                            <ul
                                    class="dropdown-menu messages-notif-box animated fadeIn"
                                    aria-labelledby="messageDropdown"
                            >
                                <li>
                                    <div
                                            class="dropdown-title d-flex justify-content-between align-items-center"
                                    >
                                        Messages
                                        <a href="#" class="small">Mark all as read</a>
                                    </div>
                                </li>
                                <li>
                                    <div class="message-notif-scroll scrollbar-outer">
                                        <div class="notif-center">
                                            <a href="#">
                                                <div class="notif-img">
                                                    <img
                                                            src="./assets/img/jm_denis.jpg"
                                                            alt="Img Profile"
                                                    />
                                                </div>
                                                <div class="notif-content">
                                                    <span class="subject">Jimmy Denis</span>
                                                    <span class="block"> How are you ? </span>
                                                    <span class="time">5 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-img">
                                                    <img
                                                            src="./assets/img/chadengle.jpg"
                                                            alt="Img Profile"
                                                    />
                                                </div>
                                                <div class="notif-content">
                                                    <span class="subject">Chad</span>
                                                    <span class="block"> Ok, Thanks ! </span>
                                                    <span class="time">12 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-img">
                                                    <img
                                                            src="./assets/img/mlane.jpg"
                                                            alt="Img Profile"
                                                    />
                                                </div>
                                                <div class="notif-content">
                                                    <span class="subject">Jhon Doe</span>
                                                    <span class="block">
                                Ready for the meeting today...
                              </span>
                                                    <span class="time">12 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-img">
                                                    <img
                                                            src="./assets/img/talha.jpg"
                                                            alt="Img Profile"
                                                    />
                                                </div>
                                                <div class="notif-content">
                                                    <span class="subject">Talha</span>
                                                    <span class="block"> Hi, Apa Kabar ? </span>
                                                    <span class="time">17 minutes ago</span>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <a class="see-all" href="javascript:void(0);"
                                    >See all messages<i class="fa fa-angle-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item topbar-icon dropdown hidden-caret">
                            <a
                                    class="nav-link dropdown-toggle"
                                    href="#"
                                    id="notifDropdown"
                                    role="button"
                                    data-bs-toggle="dropdown"
                                    aria-haspopup="true"
                                    aria-expanded="false"
                            >
                                <i class="fa fa-bell"></i>
                                <span class="notification">4</span>
                            </a>
                            <ul
                                    class="dropdown-menu notif-box animated fadeIn"
                                    aria-labelledby="notifDropdown"
                            >
                                <li>
                                    <div class="dropdown-title">
                                        You have 4 new notification
                                    </div>
                                </li>
                                <li>
                                    <div class="notif-scroll scrollbar-outer">
                                        <div class="notif-center">
                                            <a href="#">
                                                <div class="notif-icon notif-primary">
                                                    <i class="fa fa-user-plus"></i>
                                                </div>
                                                <div class="notif-content">
                                                    <span class="block"> New user registered </span>
                                                    <span class="time">5 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-icon notif-success">
                                                    <i class="fa fa-comment"></i>
                                                </div>
                                                <div class="notif-content">
                              <span class="block">
                                Rahmad commented on Admin
                              </span>
                                                    <span class="time">12 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-img">
                                                    <img
                                                            src="./assets/img/profile2.jpg"
                                                            alt="Img Profile"
                                                    />
                                                </div>
                                                <div class="notif-content">
                              <span class="block">
                                Reza send messages to you
                              </span>
                                                    <span class="time">12 minutes ago</span>
                                                </div>
                                            </a>
                                            <a href="#">
                                                <div class="notif-icon notif-danger">
                                                    <i class="fa fa-heart"></i>
                                                </div>
                                                <div class="notif-content">
                                                    <span class="block"> Farrah liked Admin </span>
                                                    <span class="time">17 minutes ago</span>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <a class="see-all" href="javascript:void(0);"
                                    >See all notifications<i class="fa fa-angle-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item topbar-icon dropdown hidden-caret">
                            <a
                                    class="nav-link"
                                    data-bs-toggle="dropdown"
                                    href="#"
                                    aria-expanded="false"
                            >
                                <i class="fas fa-layer-group"></i>
                            </a>
                            <div class="dropdown-menu quick-actions animated fadeIn">
                                <div class="quick-actions-header">
                                    <span class="title mb-1">Quick Actions</span>
                                    <span class="subtitle op-7">Shortcuts</span>
                                </div>
                                <div class="quick-actions-scroll scrollbar-outer">
                                    <div class="quick-actions-items">
                                        <div class="row m-0">
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div class="avatar-item bg-danger rounded-circle">
                                                        <i class="far fa-calendar-alt"></i>
                                                    </div>
                                                    <span class="text">Calendar</span>
                                                </div>
                                            </a>
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div
                                                            class="avatar-item bg-warning rounded-circle"
                                                    >
                                                        <i class="fas fa-map"></i>
                                                    </div>
                                                    <span class="text">Maps</span>
                                                </div>
                                            </a>
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div class="avatar-item bg-info rounded-circle">
                                                        <i class="fas fa-file-excel"></i>
                                                    </div>
                                                    <span class="text">Reports</span>
                                                </div>
                                            </a>
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div
                                                            class="avatar-item bg-success rounded-circle"
                                                    >
                                                        <i class="fas fa-envelope"></i>
                                                    </div>
                                                    <span class="text">Emails</span>
                                                </div>
                                            </a>
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div
                                                            class="avatar-item bg-primary rounded-circle"
                                                    >
                                                        <i class="fas fa-file-invoice-dollar"></i>
                                                    </div>
                                                    <span class="text">Invoice</span>
                                                </div>
                                            </a>
                                            <a class="col-6 col-md-4 p-0" href="#">
                                                <div class="quick-actions-item">
                                                    <div
                                                            class="avatar-item bg-secondary rounded-circle"
                                                    >
                                                        <i class="fas fa-credit-card"></i>
                                                    </div>
                                                    <span class="text">Payments</span>
                                                </div>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>

                        <li class="nav-item topbar-user dropdown hidden-caret">
                            <a
                                    class="dropdown-toggle profile-pic"
                                    data-bs-toggle="dropdown"
                                    href="#"
                                    aria-expanded="false"
                            >
                                <div class="avatar-sm">
                                    <img
                                            src="./assets/img/profile.jpg"
                                            alt="..."
                                            class="avatar-img rounded-circle"
                                    />
                                </div>
                                <span class="profile-username">
                      <span class="op-7">Hi,</span>
                      <span class="fw-bold">Admin</span>
                    </span>
                            </a>
                            <ul class="dropdown-menu dropdown-user animated fadeIn">
                                <div class="dropdown-user-scroll scrollbar-outer">
                                    <li>
                                        <div class="user-box">
                                            <div class="avatar-lg">
                                                <img
                                                        src="./assets/img/profile.jpg"
                                                        alt="image profile"
                                                        class="avatar-img rounded"
                                                />
                                            </div>
                                            <div class="u-text">
                                                <h4>Admin</h4>
                                                <p class="text-muted">hello@example.com</p>
                                                <a
                                                        href="profile.html"
                                                        class="btn btn-xs btn-secondary btn-sm"
                                                >View Profile</a
                                                >
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#">My Profile</a>
                                        <a class="dropdown-item" href="#">My Balance</a>
                                        <a class="dropdown-item" href="#">Inbox</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#">Account Setting</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="logout">Logout</a>
                                    </li>
                                </div>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
            <!-- End Navbar -->
        </div>

        <div class="container">
            <div class="page-inner">
                <div class="page-header">
                    <h3 class="fw-bold mb-3">Tạo tài khoản</h3>
                    <ul class="breadcrumbs mb-3">
                        <li class="nav-home">
                            <a href="dashboard/account-manage">
                                <i class="icon-home"></i>
                            </a>
                        </li>
                        <li class="separator">
                            <i class="icon-arrow-right"></i>
                        </li>
                        <li class="nav-item">
                            <a href="dashboard/account-manage">Tài khoản</a>
                        </li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <form action="dashboard/create-account" method="post" onsubmit="return validateForm()">
                            <div class="card-header">
                                <div class="card-title">Điền các thông tin cần thiết</div>
                                <c:if test="${requestScope.error != null}">
                                    <div class="col-xs-12">
                                        <div class="alert alert-danger">
                                                ${requestScope.error}
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${requestScope.message != null}">
                                    <div class="col-xs-12">
                                        <div class="alert alert-success">
                                                ${requestScope.message}
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6 col-lg-4">
                                        <div class="form-group form-group-default">
                                            <label for="email">Email<span class="text-danger">*</span></label>
                                            <input
                                                    name="email"
                                                    id="email"
                                                    type="email"
                                                    class="form-control"
                                                    required
                                                    value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                                            />
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label for="phone">Số điện thoại</label>
                                            <input
                                                    name="phone"
                                                    id="phone"
                                                    type="tel"
                                                    class="form-control"
                                                    value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>"
                                            />
                                        </div>

                                        <div class="form-group">
                                            <label for="role">Vai trò<span class="text-danger">*</span></label>
                                            <select
                                                    class="form-select form-control"
                                                    id="role"
                                                    name="role"
                                                    required
                                            >
                                                <option value="2" selected>Moderator</option>
                                                <option value="3" >Tenant</option>
                                                <option value="4" >Host</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-6 col-lg-4">
                                        <div class="form-group form-group-default">
                                            <label for="firstName">Họ<span class="text-danger">*</span></label>
                                            <input
                                                    name="firstName"
                                                    id="firstName"
                                                    type="text"
                                                    class="form-control"
                                                    required
                                                    maxlength="50"
                                                    value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>"
                                            />
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label for="lastName">Tên<span class="text-danger">*</span></label>
                                            <input
                                                    name="lastName"
                                                    id="lastName"
                                                    type="text"
                                                    class="form-control"
                                                    required
                                                    maxlength="50"
                                                    value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>"
                                            />
                                        </div>
                                        <div class="form-group">
                                            <label for="dob">Ngày sinh<span class="text-danger">*</span></label>
                                            <input
                                                    type="date"
                                                    class="form-control form-control"
                                                    id="dob"
                                                    name="dob"
                                                    required
                                                    value="<%= request.getAttribute("dob") != null ? request.getAttribute("dob") : "" %>"
                                            />
                                        </div>
                                    </div>
                                    <div class="col-md-6 col-lg-4">
                                        <div class="form-group form-group-default">
                                            <label for="password">Mật khẩu<span class="text-danger">*</span></label>
                                            <input
                                                    id="password"
                                                    name="password"
                                                    type="password"
                                                    class="form-control"
                                                    required
                                            />
                                        </div>
                                        <div class="form-group form-group-default">
                                            <label for="rePassword">Nhập lại Mật khẩu<span class="text-danger">*</span></label>
                                            <input
                                                    id="rePassword"
                                                    name="rePassword"
                                                    type="password"
                                                    class="form-control"
                                                    required
                                            />
                                        </div>
                                        <div class="form-group">
                                            <label for="gender">Giới tính<span class="text-danger">*</span></label><br />
                                            <div class="d-flex">
                                                <div class="form-check" id="gender">
                                                    <input
                                                            class="form-check-input"
                                                            type="radio"
                                                            name="gender"
                                                            id="flexRadioDefault1"
                                                            value="male"
                                                    />
                                                    <label
                                                            class="form-check-label"
                                                            for="flexRadioDefault1"
                                                    >
                                                        Nam
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input
                                                            class="form-check-input"
                                                            type="radio"
                                                            name="gender"
                                                            id="flexRadioDefault2"
                                                            value="female"
                                                            checked
                                                    />
                                                    <label
                                                            class="form-check-label"
                                                            for="flexRadioDefault2"
                                                    >
                                                        Nữ
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="card-action">
                                <button class="btn btn-success" type="submit">Xác nhận</button>
                                <button class="btn btn-danger" type="reset">Hủy</button>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer class="footer">
            <div class="container-fluid d-flex justify-content-between">
                <nav class="pull-left">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" href="home-page">
                                HSW
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"> Help </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"> Licenses </a>
                        </li>
                    </ul>
                </nav>
                <div class="copyright">
                    2024, made with <i class="fa fa-heart heart text-danger"></i> by
                    <a href="http://www.themekita.com">HSW</a>
                </div>
                <div>
                    Distributed by
                    <a target="_blank" href="https://themewagon.com/">ThemeWagon</a>.
                </div>
            </div>
        </footer>
    </div>

</div>
<!--   Core JS Files   -->
<script src="./assets/js/core/jquery-3.7.1.min.js"></script>
<script src="./assets/js/core/popper.min.js"></script>
<script src="./assets/js/core/bootstrap.min.js"></script>

<!-- jQuery Scrollbar -->
<script src="./assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
<!-- Datatables -->
<script src="./assets/js/plugin/datatables/datatables.min.js"></script>
<!-- Kaiadmin JS -->
<script src="./assets/js/kaiadmin.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    $(document).ready(function () {
        // $("#basic-datatables").DataTable({});

        $("#basic-datatables").DataTable({
            pageLength: 10,
            initComplete: function () {
                this.api()
                    .columns()
                    .every(function () {
                        var column = this;
                        var select = $(
                            '<select class="form-select"><option value=""></option></select>'
                        )
                            .appendTo($(column.footer()).empty())
                            .on("change", function () {
                                var val = $.fn.dataTable.util.escapeRegex($(this).val());

                                column
                                    .search(val ? "^" + val + "$" : "", true, false)
                                    .draw();
                            });

                        column
                            .data()
                            .unique()
                            .sort()
                            .each(function (d, j) {
                                select.append(
                                    '<option value="' + d + '">' + d + "</option>"
                                );
                            });
                    });
            },
        });

        // Add Row
        $("#add-row").DataTable({
            pageLength: 5,
        });

        var action =
            '<td> <div class="form-button-action"> <button type="button" data-bs-toggle="tooltip" title="" class="btn btn-link btn-primary btn-lg" data-original-title="Edit Task"> <i class="fa fa-edit"></i> </button> <button type="button" data-bs-toggle="tooltip" title="" class="btn btn-link btn-danger" data-original-title="Remove"> <i class="fa fa-times"></i> </button> </div> </td>';

        $("#addRowButton").click(function () {
            $("#add-row")
                .dataTable()
                .fnAddData([
                    $("#addName").val(),
                    $("#addPosition").val(),
                    $("#addOffice").val(),
                    action,
                ]);
            $("#addRowModal").modal("hide");
        });
    });
</script>
<script type="text/javascript">
    function validateForm() {
        let firstName = document.getElementById("firstName").value.trim();
        let lastName = document.getElementById("lastName").value.trim();
        let email = document.getElementById("email").value.trim();
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("rePassword").value;
        let role = document.getElementById("role").value;

        // Check if a gender is selected
        let genderMale = document.getElementById("flexRadioDefault1").checked;
        let genderFemale = document.getElementById("flexRadioDefault2").checked;

        let phone = document.getElementById("phone").value.trim();

        if (phone !== "") { // Optional: chỉ kiểm tra nếu người dùng nhập số điện thoại
            let phoneRegex = /((09|03|07|08|05)+([0-9]{8})\b)/g; // This regex matches Vietnamese phone numbers
            if (!phoneRegex.test(phone)) {
                Swal.fire({
                    icon: 'error',
                    title: 'Vui lòng nhập một số điện thoại hợp lệ!'
                });
                return false;
            }
        }

        if (!genderMale && !genderFemale) {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng chọn giới tính!'
            });
            return false;
        }

        let dob = document.getElementById("dob").value;

        if (dob === "") {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng nhập ngày sinh!'
            });
            return false;
        }

// Optional: Check if dob is a valid date and not in the future
        let dobDate = new Date(dob);
        let currentDate = new Date();

        if (dobDate > currentDate) {
            Swal.fire({
                icon: 'error',
                title: 'Ngày sinh không thể lớn hơn ngày hiện tại!'
            });
            return false;
        }


        // Check for required fields and ensure they're not just spaces
        if (firstName === "" || lastName === "" || email === "" || password === "" || confirmPassword === ""|| role === "") {
            Swal.fire({
                icon: 'error',
                title: 'Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.'
            });
            return false;
        }

        // Check if firstName and lastName only contain letters and spaces (supports Vietnamese characters)
        let nameRegex = /^[\p{L}\s]+$/u;

        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            Swal.fire({
                icon: 'error',
                title: 'Họ và tên chỉ chứa chữ cái và khoảng trắng.'
            });
            return false;
        }

        // Validate email
        let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng nhập một địa chỉ email hợp lệ!'
            });
            return false;
        }

        // Check password length
        if (password.length < 8) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu phải có ít nhất 8 ký tự!'
            });
            return false;
        }

        // Check if passwords match
        if (password !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu không khớp!'
            });
            return false;
        }

        if (password.trim() !== password || confirmPassword.trim() !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu không được chứa khoảng trắng ở đầu hoặc cuối.'
            });
            return false;
        }


        // Check if a role is selected
        if (role === "") {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng chọn một vai trò!'
            });
            return false;
        }

        // Update trimmed values
        document.getElementById("firstName").value = firstName;
        document.getElementById("lastName").value = lastName;
        document.getElementById("email").value = email;

        return true;
    }
</script>
<%
    String message = (String) session.getAttribute("message");
    String messageType = (String) session.getAttribute("messageType");
    if (message != null && messageType != null) {
%>
<script type="text/javascript">
    Swal.fire({
        icon: '<%= messageType %>',
        title: '<%= message %>'
    });
</script>
<%
        // Sau khi hiển thị thông báo, xóa nó khỏi session để tránh hiển thị lại khi trang được làm mới
        session.removeAttribute("message");
        session.removeAttribute("messageType");
    }
%>
</body>
</html>
