<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="./assets/css/plugins.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <style>
        .main-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
        }

        .main-content h2 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #777777;
        }

        .main-content .image-gallery {
            width: 48%;
        }

        .main-content .image-gallery img {
            width: 100%;
            border-radius: 10px;
            margin-bottom: 10px;
        }

        .main-content .image-gallery .small-images {
            display: flex;
            gap: 10px;
        }

        .main-content .image-gallery .small-images img {
            width: calc(33.33% - 10px);
            height: auto;
        }

        .main-content .description {
            color: #777777;
            width: 48%;
        }

        .main-content .description p {
            color: #777777;
            font-size: 14px;
            line-height: 1.6;
        }

        .main-content .info {
            color: #777777;
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .main-content .info div {
            width: 48%;
        }

        .main-content .info p {
            color: #777777;
            font-size: 14px;
            margin: 5px 0;
        }

        .main-content .info p span {
            color: #777777;
            font-weight: 500;
        }

        .main-content .contact-info p {
            color: #777777;
            font-size: 14px;
            margin: 5px 0;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <!-- Sidebar -->
    <jsp:include page="dashboard-sidebar.jsp"/>
    <!-- End Sidebar -->

    <div class="main-panel">
        <div class="main-header">
            <!-- Navbar Header -->
            <jsp:include page="navbar-admin.jsp"/>
            <!-- End Navbar -->
        </div>

        <div class="container">
            <div class="page-inner">
                <div class="page-header">
                    <h3 class="fw-bold mb-3">Chi tiết bài đăng</h3>
                    <ul class="breadcrumbs mb-3">
                        <li class="nav-home" style="color: black;">
                            <a href="#">
                                <i class="icon-home"></i>
                            </a>
                        </li>
                        <li class="separator" style="color: black;">
                            <i class="icon-arrow-right"></i>
                        </li>
                        <li class="nav-item" style="color: black;">
                            <a href="#">Danh sách tin đăng</a>
                        </li>
                        <li class="separator" style="color: black;">
                            <i class="icon-arrow-right"></i>
                        </li>
                        <li class="nav-item" style="color: black;">
                            <a href="#">Chi tiết</a>
                        </li>
                    </ul>
                </div>
                <div class="main-content">
                    <div class="image-gallery">
                        <img alt="Main Image" height="400"
                             src="${requestScope.homeImages.get(0).imgUrl}"
                             width="600" />
                        <div class="small-images">
                            <img alt="Image 1" height="150"
                                 src="${requestScope.homeImages.get(1).imgUrl}"
                                 width="200" />
                            <img alt="Image 2" height="150"
                                 src="${requestScope.homeImages.get(2).imgUrl}"
                                 width="200" />
                            <img alt="Image 3" height="150"
                                 src="${requestScope.homeImages.get(3).imgUrl}"
                                 width="200" />
                        </div>
                    </div>
                    <div class="description">
                        <h2>
                            Mô tả
                        </h2>
                        <p>
                            ${requestScope.home.homeDescription}
                        </p>
                        <h2>
                            Thông tin chi tiết
                        </h2>
                        <div class="info">
                            <div>
                                <p>
                                <span>
                                    Trạng thái :
                                </span>
                                    <c:if test="${home.status=='active'}">
                                        <span>Hoạt động</span>
                                    </c:if>
                                    <c:if test="${home.status=='pending'}">
                                        <span>Đang chờ</span>
                                    </c:if>
                                    <c:if test="${home.status=='inactive'}">
                                        <span>Vô hiệu hóa</span>
                                    </c:if>
                                </p>
                                <p>
                                <span>
                                    Diện tích (m2) :
                                </span>
                                    ${requestScope.home.area}
                                </p>
                                <p>
                                <span>
                                    Hướng nhà :
                                </span>
                                    ${requestScope.home.orientation}
                                </p>
                                <p>
                                <span>
                                    Phòng ngủ :
                                </span>
                                    ${requestScope.home.numOfBedroom}
                                </p>
                                <p>
                                <span>
                                    Phòng tắm :
                                </span>
                                    ${requestScope.home.numOfBath}
                                </p>
                            </div>
                            <div>
                                <p>
                                <span>
                                    Loại hình nhà ở :
                                </span>
                                    ${requestScope.homeTypes.name}
                                </p>
                                <p>
                                <span>
                                    Tiền thuê/tháng :
                                </span>
                                    ${requestScope.prices.price}
                                </p>
                                <p>
                                <span>
                                    Địa chỉ :
                                </span>
                                    ${requestScope.home.address}
                                </p>
                                <p>
                                <span>
                                    Tiện ích :
                                </span>
                                    <c:forEach items="${requestScope.amenities}" var="amenity">
                                        ${amenity.name},
                                    </c:forEach>
                                </p>
                                <p>
                                <span>
                                    Trang bị PCCC :
                                </span>
                                    <c:forEach items="${requestScope.fireEquipments}" var="fire">
                                        ${fire.name},
                                    </c:forEach>
                                </p>
                            </div>
                        </div>
                        <div class="contact-info">
                            <h2>
                                Thông tin liên hệ
                            </h2>
                            <p>
                            <span>
                                Email :
                            </span>
                                ${requestScope.creator.email}
                            </p>
                            <p>
                            <span>
                                Số điện thoại :
                            </span>
                                ${requestScope.creator.phoneNumber}
                            </p>
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
                                Trang chủ
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"> Hỗ trợ </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"> Điều khoản </a>
                        </li>
                    </ul>
                </nav>
                <div class="copyright">
                    Copyright ©2024, All Right Reserved by Roomify team
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
