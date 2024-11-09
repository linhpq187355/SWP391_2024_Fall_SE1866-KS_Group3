<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // Get the current URL
    String currentUrl = request.getRequestURI();
    request.setAttribute("currentUrl", currentUrl);
%>
<div class="sidebar" data-background-color="light">
    <div class="sidebar-logo">
        <!-- Logo Header -->
        <div class="logo-header" data-background-color="light">
            <a href="dashboard" class="logo">
                <img
                        src="./assets/img/logo-light.png"
                        alt="navbar brand"
                        class="navbar-brand"
                        height="65"
                />
            </a>
            <div class="nav-toggle">
                <button class="btn btn-toggle toggle-sidebar" style="color: #777777 !important;">
                    <i class="gg-menu-right"></i>
                </button>
                <button class="btn btn-toggle sidenav-toggler" style="color: #777777 !important;">
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
                                <a href="dashboard/announcement-list">
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
                    <a href="dashboard/home-list">
                        <i class="fas fa-newspaper"></i>
                        <p>Tin Đăng Nhà</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a data-bs-toggle="collapse" href="#acc-mgt">
                        <i class="fas fa-user"></i>
                        <p>Tài khoản</p>
                        <span class="caret"></span>
                    </a>
                    <div class="collapse" id="acc-mgt">
                        <ul class="nav nav-collapse">
                            <li>
                                <a href="dashboard/account-list">
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
                                <a href="dashboard/report-list">
                                    <span class="sub-item">Danh sách báo cáo</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item <c:if test="${currentUrl.contains('/homeSharing/permission-list.jsp')}">active submenu</c:if>">
                    <a href="dashboard/permission-list">
                        <i class="fas fa-key"></i>
                        <p>Phân Quyền</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="dashboard/blog-list">
                        <i class="fa-brands fa-blogger-b"></i>
                        <p>Blog</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<!-- End Sidebar -->