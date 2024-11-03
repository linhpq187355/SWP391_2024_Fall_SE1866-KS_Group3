<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>
<body>
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
                    <a href="dashboard">
                        <i class="fas fa-home"></i>
                        <p>Dashboard</p>
                    </a>
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
                                <a href="#">
                                    <span class="sub-item">Danh sách thông báo</span>
                                </a>
                            </li>
                            <li>
                                <a href="create-announcement.jsp">
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
                                <a href="home-post-manage">
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
                <li class="nav-item">
                    <a data-bs-toggle="collapse" href="#acc-mgt">
                        <i class="fas fa-user"></i>
                        <p>Tài Khoản</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="acc-mgt">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="account-manage">
                                    <span class="sub-item">Danh sách tài khoản</span>
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
                                <a href="report-home-manage">
                                    <span class="sub-item">Danh sách báo cáo</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="collapse" href="#permissons">
                        <i class="fas fa-key"></i>
                        <p>Phân Quyền</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="permissons">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Danh sách quản trị viên</span>
                                </a>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Tạo quản trị viên</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item submenu">
                    <a data-bs-toggle="collapse" href="#ticket-mgt">
                        <i class="fas fa-ticket-alt"></i>
                        <p>Phiếu Hỗ Trợ</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="ticket-mgt">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Danh sách phiếu</span>
                                </a>
                            </li>
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Tạo phiếu</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>
<body>
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
                    <a href="dashboard">
                        <i class="fas fa-home"></i>
                        <p>Dashboard</p>
                    </a>
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
                                <a href="#">
                                    <span class="sub-item">Danh sách thông báo</span>
                                </a>
                            </li>
                            <li>
                                <a href="create-announcement.jsp">
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
                                <a href="home-post-manage">
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
                <li class="nav-item">
                    <a data-bs-toggle="collapse" href="#acc-mgt">
                        <i class="fas fa-user"></i>
                        <p>Tài Khoản</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="acc-mgt">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="account-manage">
                                    <span class="sub-item">Danh sách tài khoản</span>
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
                                <a href="report-home-manage">
                                    <span class="sub-item">Danh sách báo cáo</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="collapse" href="#permissons">
                        <i class="fas fa-key"></i>
                        <p>Phân Quyền</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="permissons">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Danh sách quản trị viên</span>
                                </a>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Tạo quản trị viên</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item submenu">
                    <a data-bs-toggle="collapse" href="#ticket-mgt">
                        <i class="fas fa-ticket-alt"></i>
                        <p>Phiếu Hỗ Trợ</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="ticket-mgt">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Danh sách phiếu</span>
                                </a>
                            </li>
                            <li>
                                <a href="../forms/forms.html">
                                    <span class="sub-item">Tạo phiếu</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
