<%--
  Created by IntelliJ IDEA.
  User: ThangLT
  Date: 11/3/2024
  Time: 12:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Dashboard - Roomify</title>
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport"/>
    <link
            rel="icon"
            href="./assets/img/logo-web.png"
            type="image/x-icon"
    />
    <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>

    <!-- Fonts and icons -->
    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
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
                urls: ["assets/css/fonts.min.css"],
            },
            active: function () {
                sessionStorage.fonts = true;
            },
        });
    </script>

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/dashboard.min.css"/>

    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link rel="stylesheet" href="assets/css/demo.css"/>
    <style>
        body {
            color: #2a2f5b;
        }

        #mapHolder {
            width: 550px;
            height: 250px;
            margin: 0 auto;
        }

        svg {
            padding-right: 80%;
        }

        .card.card-stats {
            transition: transform 0.3s ease;
        }

        .card.card-stats:hover {
            transform: scale(1.05); /* Slight zoom effect */
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2); /* Optional shadow for added effect */
        }

        .modal.fade .modal-dialog {
            transform: scale(0.8); /* Initial small scale */
            transition: transform 0.3s ease, opacity 0.3s ease;
            opacity: 0; /* Start transparent */
        }

        .modal.fade.show .modal-dialog {
            transform: scale(1); /* Full size */
            opacity: 1; /* Fully visible */
        }
    </style>
</head>
<body>
<div class="wrapper sidebar_minimize">
    <!-- Sidebar -->
    <jsp:include page="dashboard-sidebar.jsp"/>
    <!-- End Sidebar -->

    <div class="main-panel">
        <jsp:include page="dashboard-header.jsp"/>

        <div class="container">
            <div class="page-inner">
                <div class="d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4">
                    <div>
                        <h3 class="fw-bold mb-3"><i class="fa-solid fa-gauge"></i> Dashboard</h3>
                        <h6 class="op-7 mb-2">Trang quản trị của Roomify, nơi tổng hợp các số liệu thống kê, trạng thái
                            của website</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round" data-bs-toggle="modal"
                             data-bs-target="#homeDetailModal">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-5">
                                        <div class="icon-big text-center">
                                            <i class="icon-home text-success"></i>
                                        </div>
                                    </div>
                                    <div class="col-7 col-stats">
                                        <div class="numbers">
                                            <p class="card-category">Tổng Số Tin Đăng</p>
                                            <h4 class="card-title">${requestScope.totalHomes} tin</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-5">
                                        <div class="icon-big text-center">
                                            <i class="fas fa-users text-primary"></i>
                                        </div>
                                    </div>
                                    <div class="col-7 col-stats">
                                        <div class="numbers">
                                            <p class="card-category">Tổng Số Người Dùng</p>
                                            <h4 class="card-title">25 người</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-5">
                                        <div class="icon-big text-center">
                                            <i class="fa-brands fa-blogger-b text-danger"></i>
                                        </div>
                                    </div>
                                    <div class="col-7 col-stats">
                                        <div class="numbers">
                                            <p class="card-category">Tổng số blog</p>
                                            <h4 class="card-title">23 bài</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-3">
                        <div class="card card-stats card-round">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-5">
                                        <div class="icon-big text-center">
                                            <i class="fas fa-calendar-check text-warning"></i>
                                        </div>
                                    </div>
                                    <div class="col-7 col-stats">
                                        <div class="numbers">
                                            <p class="card-category">Tổng số cuộc hẹn</p>
                                            <h4 class="card-title">15 cuộc hẹn</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-head-row">
                                    <div class="card-title">Tin đăng mới</div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="container">
                                    <div class="row">
                                        <c:forEach items="${requestScope.latestHomes}" var="home">
                                            <div class="col-sm-6 col-md-3">
                                                <div class="box-two proerty-item"
                                                     style="box-shadow: rgba(50, 50, 93, 0.25) 0 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px; border-radius: 15px; margin: 15px; height: 400px;">
                                                    <div class="item-thumb">
                                                        <a href="home-detail?id=1">
                                                            <img class="property-image"
                                                                 src="assets/img/dashboard/sample-home-img.jpg"
                                                                 alt="Home Image"
                                                                 style="border-top-left-radius: 15px; border-top-right-radius: 15px; width: 100%;">
                                                        </a>
                                                    </div>
                                                    <div class="item-entry overflow"
                                                         style="padding-left: 5%; padding-right: 5%;">
                                                        <h5 style="letter-spacing: 0; height: 35px; margin-top: 10px; margin-bottom: 0px;">
                                                            <a href="home-detail?id=1"><b>${home.name}</b></a>
                                                        </h5>
                                                        <p style="padding: 0; color: #777777;">${home.address}</p>
                                                        <div style="display: flex; align-items: center; border-bottom: 1px solid #484848;">
                                                            <p style="color: #FA6C00; font-size: 20px;"><b>10 triệu</b>
                                                            </p>
                                                            / tháng
                                                        </div>
                                                        <div></div>
                                                        <div
                                                                style="display: flex; justify-content: space-between; font-size: 15px; color: #777777; margin-top: 10px; align-items: center;">
                                                            <div>
                                                                <i class="fa-solid fa-bath"></i> ${home.numOfBath} phòng
                                                            </div>
                                                            <div>
                                                                <i class="fa-solid fa-bed"></i> ${home.numOfBedroom}
                                                                phòng
                                                            </div>
                                                            <div
                                                                    style="border: 1px solid #ccc; border-radius: 5px; height: 35px; width: 35px; display: flex; justify-content: center; align-items: center; font-size: 25px;">
                                                                <i class="fa-regular fa-eye"></i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-head">
                                <div class="card-head-content">
                                    <!-- Image on the left, spanning two rows -->
                                    <img src="assets/img/dashboard/home-request.png" alt="Request Image"
                                         style="width: 100px; border-radius: 10px 0 0 10px;">
                                    <div class="card-img-overlay" style="margin-left: 8%;">
                                        <div class="card-head-row">
                                            <h5 class="card-title">Có ${requestScope.pendingHome} tin đăng đang chờ được
                                                duyệt</h5>
                                            <div class="card-tools">
                                                <ul class="nav nav-pills nav-secondary nav-pills-no-bd nav-sm"
                                                    role="tablist">
                                                    <li class="nav-item">
                                                        <a class="nav-link active" data-bs-toggle="pill"
                                                           href="#pills-week" role="tab"
                                                           aria-selected="false">Xem chi tiết</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>

                                        <p class="card-text">Có một số vấn đề mới đang phát sinh, vui lòng đánh giá và
                                            phê duyệt.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">Các trang truy cập</div>
                                <h6 class="op-7 mb-2">Bảng thể hiện lưu lượng truy cập của các trang trong hệ thống</h6>
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive" style="margin-bottom: 15px;">
                                    <!-- Projects table -->
                                    <table id="basic-datatables" class="table align-items-center mb-0">
                                        <thead class="thead-light">
                                        <tr>
                                            <th scope="col">Đường dẫn</th>
                                            <th scope="col">Tên trang</th>
                                            <th scope="col">Số khách truy cập</th>
                                            <th scope="col">Số thành viên truy cập</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.pageVisits}" var="page">
                                            <tr>
                                                <th scope="row">${page.url}</th>
                                                <td>${page.pageName}</td>
                                                <td>${page.totalVisits}</td>
                                                <td>${page.memberVisits}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">Đặc điểm tính cách</div>
                                <h6 class="op-7 mb-2">Sơ đồ thể hiện mối tương quan giữa tính cách của người tìm thuê và
                                    nhu cầu của
                                    chủ hộ</h6>
                            </div>
                            <div class="card-body">
                                <div class="chart-container">
                                    <canvas id="radarChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row row-card-no-pd">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-head-row card-tools-still-right">
                                    <h4 class="card-title">Tin đăng theo tỉnh thành</h4>
                                    <div class="card-tools">
                                        <button class="btn btn-icon btn-link btn-primary btn-xs">
                                            <span class="fa fa-angle-down"></span>
                                        </button>
                                        <button class="btn btn-icon btn-link btn-primary btn-xs btn-refresh-card">
                                            <span class="fa fa-sync-alt"></span>
                                        </button>
                                        <button class="btn btn-icon btn-link btn-primary btn-xs">
                                            <span class="fa fa-times"></span>
                                        </button>
                                    </div>
                                </div>
                                <p class="card-category">
                                    Bản đồ phân bố tin đăng nhà trên khắp các tỉnh thành Việt Nam
                                </p>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="table-responsive table-hover table-sales">
                                            <table class="table" id="province-datatables">
                                                <thead>
                                                <th>STT</th>
                                                <th>Tỉnh thành phố</th>
                                                <th class="text-center">Số tin đăng</th>
                                                <th class="text-center">Tỉ lệ</th>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${requestScope.provinceRank}" var="province"
                                                           varStatus="status">
                                                    <tr>
                                                        <td>
                                                            <div class="index">${status.index + 1}</div>
                                                        </td>
                                                        <td id="province-name">${province.provinceName}</td>
                                                        <td class="text-center">${province.homeCount}</td>
                                                        <td class="text-center">
                                                            <fmt:formatNumber value="${province.homeRatio}"
                                                                              maxFractionDigits="2"/>%
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mapcontainer" style="overflow-y: auto;height: 350px;">
                                            <div id="vietnammap" class="w-100"
                                                 style="height: 300px; position: relative; overflow: scroll; width: 1000px; margin-top: -430px;">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="card card-round">
                            <div class="card-body">
                                <div class="card-head-row card-tools-still-right">
                                    <div class="card-title">Người dùng mới</div>
                                    <div class="card-tools">
                                        <div class="dropdown">
                                            <button class="btn btn-icon btn-clean me-0" type="button"
                                                    id="dropdownMenuButton"
                                                    data-bs-toggle="dropdown" aria-haspopup="true"
                                                    aria-expanded="false">
                                                <i class="fas fa-ellipsis-h"></i>
                                            </button>
                                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                <a class="dropdown-item" href="#">Danh sách người dùng</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-list py-4">
                                    <c:forEach items="${requestScope.latestUsers}" var="user">
                                        <div class="item-list">
                                            <div class="avatar">
                                                <img src="${user.avatar != null && !user.avatar.isEmpty() ? user.avatar : 'https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg'}" alt="..."
                                                     class="avatar-img rounded-circle"/>
                                            </div>
                                            <div class="info-user ms-3">
                                                <div class="username">${user.email}</div>
                                                <div class="status">${user.roleName}</div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-head-row">
                                    <div class="card-title">Phiếu Hỗ Trợ</div>
                                    <div class="card-tools">
                                        <ul class="nav nav-pills nav-secondary nav-pills-no-bd nav-sm" id="pills-tab"
                                            role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link" id="pills-today" data-bs-toggle="pill"
                                                   href="#pills-today" role="tab"
                                                   aria-selected="true">Theo Ngày</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link active" id="pills-week" data-bs-toggle="pill"
                                                   href="#pills-week" role="tab"
                                                   aria-selected="false">Theo Tuần</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" id="pills-month" data-bs-toggle="pill"
                                                   href="#pills-month" role="tab"
                                                   aria-selected="false">Theo Tháng</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="d-flex">
                                    <div class="avatar avatar-online">
                                        <span class="avatar-title rounded-circle border border-white bg-info">J</span>
                                    </div>
                                    <div class="flex-1 ms-3 pt-1">
                                        <h6 class="text-uppercase fw-bold mb-1">
                                            Nguyễn Hữu Thắng
                                            <span class="text-warning ps-3">chờ duyệt</span>
                                        </h6>
                                        <span class="text-muted">Bài đăng của tôi về nhà trọ ở Quận 1 đã bị từ chối mà không có lời giải thích.</span>
                                    </div>
                                    <div class="float-end pt-1">
                                        <small class="text-muted">8:40 PM</small>
                                    </div>
                                </div>
                                <div class="separator-dashed"></div>
                                <div class="d-flex">
                                    <div class="avatar avatar-offline">
                                        <span class="avatar-title rounded-circle border border-white bg-secondary">P</span>
                                    </div>
                                    <div class="flex-1 ms-3 pt-1">
                                        <h6 class="text-uppercase fw-bold mb-1">
                                            Lung Thị Linh
                                            <span class="text-success ps-3">đang mở</span>
                                        </h6>
                                        <span class="text-muted">Tôi đã liên lạc với người dùng tên Nguyễn Văn An để trao đổi về phòng trọ, nhưng người này có hành vi quấy rối và gửi nhiều tin nhắn không phù hợp.</span>
                                    </div>
                                    <div class="float-end pt-1">
                                        <small class="text-muted">1 Ngày Trước</small>
                                    </div>
                                </div>
                                <div class="separator-dashed"></div>
                                <div class="d-flex">
                                    <div class="avatar avatar-away">
                                        <span class="avatar-title rounded-circle border border-white bg-danger">L</span>
                                    </div>
                                    <div class="flex-1 ms-3 pt-1">
                                        <h6 class="text-uppercase fw-bold mb-1">
                                            Trần Xuân Hoàn
                                            <span class="text-muted ps-3">đã đóng</span>
                                        </h6>
                                        <span class="text-muted">Tôi muốn cập nhật lại mô tả của bài đăng ID #2345 vì đã có thay đổi về giá thuê. Nhờ hỗ trợ chỉnh sửa giúp tôi.</span>
                                    </div>
                                    <div class="float-end pt-1">
                                        <small class="text-muted">2 Days Ago</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="dashboard-footer.jsp"/>
    </div>
</div>

<%--Modals session--%>
<div class="modal fade" id="homeDetailModal" tabindex="-1" aria-labelledby="homeDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userDetailModalLabel">Thống kê tin đăng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Tổng số tin đăng: </strong> ${requestScope.totalHomes}</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Số tin đăng đã được
                            duyệt:</strong> ${requestScope.activeHome} bài</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Số tin đăng đang chờ
                            duyệt:</strong> ${requestScope.inactiveHome} bài</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Số tin đã đăng trong tháng
                            này:</strong> ${requestScope.homesInMonth} bài</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Loại hình nhà ở phổ biến
                            nhất:</strong> ${requestScope.popularHomeType}</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Thời gian cho ở ghép trung
                            bình:</strong> ${requestScope.avgLeaseDuration} tháng</h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><strong>Số chủ hộ cho ở ghép trong tháng:</strong>
                            ${requestScope.homesInMonth} người</h6>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>


<%--End modals session--%>

<!-- Core JS Files -->
<script src="assets/js/core/jquery-3.7.1.min.js"></script>
<script src="assets/js/core/popper.min.js"></script>
<script src="assets/js/core/bootstrap.min.js"></script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.3.0/raphael.min.js"></script>
<script src="assets/js/scale.raphael.js"></script>
<script src="assets/js/vietnam.js"></script>
<script src="assets/js/vietnam-map.js"></script>

<!-- jQuery Scrollbar -->
<script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>

<!-- Chart JS -->
<script src="assets/js/plugin/chart.js/chart.min.js"></script>

<!-- jQuery Sparkline -->
<script src="assets/js/plugin/jquery.sparkline/jquery.sparkline.min.js"></script>

<!-- Chart Circle -->
<script src="assets/js/plugin/chart-circle/circles.min.js"></script>

<!-- Datatables -->
<script src="assets/js/plugin/datatables/datatables.min.js"></script>

<!-- Bootstrap Notify -->
<script src="assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>

<!-- jQuery Vector Maps -->
<script src="assets/js/plugin/jsvectormap/jsvectormap.min.js"></script>
<script src="assets/js/plugin/jsvectormap/world.js"></script>

<!-- Sweet Alert -->
<script src="assets/js/plugin/sweetalert/sweetalert.min.js"></script>

<!-- Kaiadmin JS -->
<script src="assets/js/kaiadmin.min.js"></script>

<script>
    var radarChart = document.getElementById("radarChart").getContext("2d");
    var hostAvg = JSON.parse('${hostAvg}');
    var tenantAvg = JSON.parse('${tenantAvg}');

    console.log("Host Average:", hostAvg);
    console.log("Tenant Average:", tenantAvg);

    // Transform map data to ensure it's in the correct JS object format
    var tenantData = [
        tenantAvg.avgSmoking || 0,
        tenantAvg.avgDrinking || 0,
        tenantAvg.avgCleanliness || 0,
        tenantAvg.avgPet || 0,
        tenantAvg.avgInteraction || 0,
        tenantAvg.avgCooking || 0,
        tenantAvg.avgGuests || 0
    ];

    var hostData = [
        hostAvg.avgSmoking || 0,
        hostAvg.avgDrinking || 0,
        hostAvg.avgCleanliness || 0,
        hostAvg.avgPet || 0,
        hostAvg.avgInteraction || 0,
        hostAvg.avgCooking || 0,
        hostAvg.avgGuests || 0
    ];


    var myRadarChart = new Chart(radarChart, {
        type: "radar",
        data: {
            labels: ["Thuốc lá", "Rượu bia", "Sạch sẽ", "Thú cưng", "Xã giao", "Nấu ăn", "Khách mời"],
            datasets: [
                {
                    data: tenantData,
                    borderColor: "#1d7af3",
                    backgroundColor: "rgba(29, 122, 243, 0.25)",
                    pointBackgroundColor: "#1d7af3",
                    pointHoverRadius: 4,
                    pointRadius: 3,
                    label: "Người tìm thuê"
                },
                {
                    data: hostData,
                    borderColor: "#FA6C00",
                    backgroundColor: "rgba(250, 108, 0, 0.25)",
                    pointBackgroundColor: "#FA6C00",
                    pointHoverRadius: 4,
                    pointRadius: 3,
                    label: "Chủ hộ"
                }
            ],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            legend: {
                position: "bottom",
            },
        },
    });
</script>

<script>
    $(document).ready(function () {
        $('#basic-datatables').DataTable({
            "pageLength": 5, // Set default items per page
            "lengthMenu": [5, 10, 25, 50, 100], // Options for items per page
            "paging": true, // Enable pagination
            "lengthChange": true, // Allow changing the number of records per page
            "searching": true, // Enable searching
            "ordering": true, // Enable sorting
            "info": true, // Show table information
            "autoWidth": false // Disable auto width calculation
        });
        $('#province-datatables').DataTable({
            "pageLength": 5, // Set default items per page
            "lengthMenu": [5, 10, 25, 50, 100], // Options for items per page
            "paging": true, // Enable pagination
            "lengthChange": true, // Allow changing the number of records per page
            "searching": true, // Enable searching
            "ordering": true, // Enable sorting
            "info": true, // Show table information
            "autoWidth": false // Disable auto width calculation
        });
    });
</script>
</body>
</html>
