<%--
  Created by IntelliJ IDEA.
  User: Duy Long Laptop
  Date: 26-Sep-24
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <title>DashBoard - HomeSharing</title>
  <meta
          content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
          name="viewport"
  />
  <link
          rel="icon"
          href="./assets/img/kaiadmin/favicon.ico"
          type="image/x-icon"
  />
  <base href="${pageContext.request.contextPath}/">
  <!-- Fonts and icons -->
  <script src="./assets/js/plugin/webfont/webfont.min.js"></script>
  <%--    <script src="https://cdn.tailwindcss.com"></script>--%>
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
  <style>
    .btn-custom {
      width: 100%; /* Full width button */
      margin-bottom: 10px; /* Add space between buttons */
    }
  </style>
</head>
<body>
<div class="wrapper">
  <!-- Sidebar -->
  <div class="sidebar" data-background-color="dark">
    <div class="sidebar-logo">
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
            <h4 class="text-section">Components</h4>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#base">
              <i class="fas fa-layer-group"></i>
              <p>Base</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="base">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../components/avatars.html">
                    <span class="sub-item">Avatars</span>
                  </a>
                </li>
                <li>
                  <a href="../components/buttons.html">
                    <span class="sub-item">Buttons</span>
                  </a>
                </li>
                <li>
                  <a href="../components/gridsystem.html">
                    <span class="sub-item">Grid System</span>
                  </a>
                </li>
                <li>
                  <a href="../components/panels.html">
                    <span class="sub-item">Panels</span>
                  </a>
                </li>
                <li>
                  <a href="../components/notifications.html">
                    <span class="sub-item">Notifications</span>
                  </a>
                </li>
                <li>
                  <a href="../components/sweetalert.html">
                    <span class="sub-item">Sweet Alert</span>
                  </a>
                </li>
                <li>
                  <a href="../components/font-awesome-icons.html">
                    <span class="sub-item">Font Awesome Icons</span>
                  </a>
                </li>
                <li>
                  <a href="../components/simple-line-icons.html">
                    <span class="sub-item">Simple Line Icons</span>
                  </a>
                </li>
                <li>
                  <a href="../components/typography.html">
                    <span class="sub-item">Typography</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#sidebarLayouts">
              <i class="fas fa-th-list"></i>
              <p>Sidebar Layouts</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="sidebarLayouts">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../sidebar-style-2.html">
                    <span class="sub-item">Sidebar Style 2</span>
                  </a>
                </li>
                <li>
                  <a href="../icon-menu.html">
                    <span class="sub-item">Icon Menu</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#forms">
              <i class="fas fa-pen-square"></i>
              <p>Forms</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="forms">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../forms/forms.html">
                    <span class="sub-item">Basic Form</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item active submenu">
            <a data-bs-toggle="collapse" href="#tables">
              <i class="fas fa-table"></i>
              <p>Tables</p>
              <span class="caret"></span>
            </a>
            <div class="collapse show" id="tables">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../tables/tables.html">
                    <span class="sub-item">Basic Table</span>
                  </a>
                </li>
                <li class="active">
                  <a href="../tables/datatables.html">
                    <span class="sub-item">Datatables</span>
                  </a>
                </li>
                <li class="active">
                  <a href="report-list">
                    <span class="sub-item">Report Home List</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#maps">
              <i class="fas fa-map-marker-alt"></i>
              <p>Maps</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="maps">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../maps/googlemaps.html">
                    <span class="sub-item">Google Maps</span>
                  </a>
                </li>
                <li>
                  <a href="../maps/jsvectormap.html">
                    <span class="sub-item">Jsvectormap</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#charts">
              <i class="far fa-chart-bar"></i>
              <p>Charts</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="charts">
              <ul class="nav nav-collapse">
                <li>
                  <a href="../charts/charts.html">
                    <span class="sub-item">Chart Js</span>
                  </a>
                </li>
                <li>
                  <a href="../charts/sparkline.html">
                    <span class="sub-item">Sparkline</span>
                  </a>
                </li>
              </ul>
            </div>
          </li>
          <li class="nav-item">
            <a href="../widgets.html">
              <i class="fas fa-desktop"></i>
              <p>Widgets</p>
              <span class="badge badge-success">4</span>
            </a>
          </li>
          <li class="nav-item">
            <a data-bs-toggle="collapse" href="#submenu">
              <i class="fas fa-bars"></i>
              <p>Menu Levels</p>
              <span class="caret"></span>
            </a>
            <div class="collapse" id="submenu">
              <ul class="nav nav-collapse">
                <li>
                  <a data-bs-toggle="collapse" href="#subnav1">
                    <span class="sub-item">Level 1</span>
                    <span class="caret"></span>
                  </a>
                  <div class="collapse" id="subnav1">
                    <ul class="nav nav-collapse subnav">
                      <li>
                        <a href="#">
                          <span class="sub-item">Level 2</span>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                          <span class="sub-item">Level 2</span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </li>
                <li>
                  <a data-bs-toggle="collapse" href="#subnav2">
                    <span class="sub-item">Level 1</span>
                    <span class="caret"></span>
                  </a>
                  <div class="collapse" id="subnav2">
                    <ul class="nav nav-collapse subnav">
                      <li>
                        <a href="#">
                          <span class="sub-item">Level 2</span>
                        </a>
                      </li>
                    </ul>
                  </div>
                </li>
                <li>
                  <a href="#">
                    <span class="sub-item">Level 1</span>
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
          <h3 class="fw-bold mb-3">Tài khoản</h3>
          <ul class="breadcrumbs mb-3">
            <li class="nav-home">
              <a href="#">
                <i class="icon-home"></i>
              </a>
            </li>
            <li class="separator">
              <i class="icon-arrow-right"></i>
            </li>
            <li class="nav-item">
              <a href="#">Bảng biểu</a>
            </li>
            <li class="separator">
              <i class="icon-arrow-right"></i>
            </li>
            <li class="nav-item">
              <a href="#">Cơ sở dữ liệu</a>
            </li>
          </ul>
        </div>
        <div class="row">
          <div class="col-md-12">
            <div class="card">
              <div class="card-header" style="display: flex; justify-content: space-between">
                <h4 class="card-title">Danh sách báo cáo</h4>
              </div>
              <div class="card-body">
                <div class="table-responsive">
                  <table
                          id="basic-datatables"
                          class="display table table-striped table-hover"
                  >
                    <thead>
                    <tr>
                      <th>ID</th>
                      <th>Tiêu đề</th>
                      <th>Mô tả</th>
                      <th>Báo cáo lúc</th>
                      <th>Tạo bởi</th>
                      <th>Tên nhà</th>
                      <th>Lí do</th>
                      <th>Trạng thái</th>
                      <th>Quản lí</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                      <th>ID</th>
                      <th>Tiêu đề</th>
                      <th>Mô tả</th>
                      <th>Báo cáo lúc</th>
                      <th>Tạo bởi</th>
                      <th>Tên nhà</th>
                      <th>Lí do</th>
                      <th>Trạng thái</th>
                      <th>Quản lí</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach items="${requestScope.reports}" var="report">
                      <tr>
                        <td>${report.id}</td>
                        <td>${report.title}</td>
                        <td>${report.description}</td>
                        <td>${report.reportDate}</td>
                        <c:forEach items="${requestScope.users}" var="user">
                          <c:if test="${user.id == report.createdBy}">
                            <td class="text-center text-capitalize">${user.userName}</td>
                          </c:if>
                        </c:forEach>
                        <c:forEach items="${requestScope.homes}" var="home">
                          <c:if test="${report.homeId == home.id}">
                            <td class="text-center text-capitalize">${home.name}</td>
                          </c:if>
                        </c:forEach>
                        <c:forEach items="${requestScope.reportTypes}" var="reportType">
                          <c:if test="${reportType.id == report.reportTypeId}">
                            <td class="text-center text-capitalize">${reportType.reason}</td>
                          </c:if>
                        </c:forEach>
                        <c:if test="${report.status=='active'}">
                          <td class="text-center text-success text-capitalize">${report.status}</td>
                        </c:if>
                        <c:if test="${report.status!='active'}">
                          <td class="text-center text-danger text-capitalize">Solved</td>
                        </c:if>
                        <td class="space-y-4">
<%--                          <a href="ban?userId=${user.id}"--%>
<%--                             onclick="banAccount('${user.id}')">--%>
<%--                            <button class="btn btn-danger btn-custom text-white font-bold py-2 px-6 w-32 rounded-lg flex items-center justify-center space-x-2 shadow-lg transform transition-transform duration-200 hover:scale-105 hover:bg-red-600">--%>
<%--                              <i class="fas fa-ban"></i>--%>
<%--                              <span>Ban</span>--%>
<%--                            </button>--%>
<%--                          </a>--%>
<%--                          <a href="activate?userId=${user.id}"--%>
<%--                             onclick="activateAccount('${user.id}')">--%>
<%--                            <button class="btn btn-success btn-custom text-white font-bold py-2 px-6 w-32 rounded-lg flex items-center justify-center space-x-2 shadow-lg transform transition-transform duration-200 hover:scale-105 hover:bg-green-600">--%>
<%--                              <i class="fas fa-check"></i>--%>
<%--                              <span>Activate</span>--%>
<%--                            </button>--%>
<%--                          </a>--%>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
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
  function activateAccount(id) {
    if (confirm("Are you sure to activate this account?")) {
      window.location = "activate?userId=" + id;
      // If confirmed, announce the success
      alert("Confirmed request");
    } else {
      // If not confirmed, announce failure or do nothing
      alert("Account activation canceled.");
    }
  }

  function banAccount(id) {
    if (confirm("Are you sure to ban this account?")) {
      window.location = "ban?userId=" + id;
      // If confirmed, announce the success
      alert("Confirmed request");
    } else {
      // If not confirmed, announce failure or do nothing
      alert("Account ban canceled.");
    }
  }
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
