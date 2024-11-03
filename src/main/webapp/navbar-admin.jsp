<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                        placeholder="Tìm kiếm..."
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
                                    placeholder="Tìm kiếm..."
                                    class="form-control"
                            />
                        </div>
                    </form>
                </ul>
            </li>
            <%--      <li class="nav-item topbar-icon dropdown hidden-caret">--%>
            <%--        <a--%>
            <%--                class="nav-link dropdown-toggle"--%>
            <%--                href="#"--%>
            <%--                id="messageDropdown"--%>
            <%--                role="button"--%>
            <%--                data-bs-toggle="dropdown"--%>
            <%--                aria-haspopup="true"--%>
            <%--                aria-expanded="false"--%>
            <%--        >--%>
            <%--          <i class="fa fa-envelope"></i>--%>
            <%--        </a>--%>
            <%--      </li>--%>
            <li class="nav-item topbar-user dropdown hidden-caret">
                <a
                        class="dropdown-toggle profile-pic"
                        data-bs-toggle="dropdown"
                        href="#"
                        aria-expanded="false"
                >
                    <div class="avatar-sm">
                        <img
                                src="assets/img/logo_1.png"
                                alt="Admin avatar"
                                class="avatar-img rounded-circle"
                        />
                    </div>
                    <span class="profile-username">
                      <span class="op-7">Xin chào,</span>
                      <span class="fw-bold">Quản trị viên</span>
                    </span>
                </a>
                <ul class="dropdown-menu dropdown-user animated fadeIn">
                    <div class="dropdown-user-scroll scrollbar-outer">
                        <li>
                            <div class="user-box">
                                <div class="avatar-lg">
                                    <img
                                            src="assets/img/logo_1.png"
                                            alt="image profile"
                                            class="avatar-img rounded"
                                    />
                                </div>
                                <div class="u-text">
                                    <h4>Quản trị viên</h4>
                                    <p class="text-muted">${fn:replace(cookie.email.value, '%40', '@')}</p>
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
</body>
</html>
