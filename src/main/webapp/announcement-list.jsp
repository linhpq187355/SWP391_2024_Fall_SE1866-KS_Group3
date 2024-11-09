<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <style>
        .icon-custom {
            font-size: 24px;
            color: #ffa500;
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
                    <h3 class="fw-bold mb-3">Danh sách thông báo</h3>
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
                            <a href="#">Bảng biểu</a>
                        </li>
                        <li class="separator" style="color: black;">
                            <i class="icon-arrow-right"></i>
                        </li>
                        <li class="nav-item" style="color: black;">
                            <a href="#">Cơ sở dữ liệu</a>
                        </li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header" style="display: flex; justify-content: space-between">
                                <h4 class="card-title">Danh sách thông báo</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table
                                            id="basic-datatables"
                                            class="display table table-striped table-hover">
                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tiêu đề</th>
                                            <th>Thể loại</th>
                                            <th>Tạo lúc</th>
                                            <th>Tạo bởi</th>
                                            <th>Trạng thái</th>
                                            <th>Quản lí</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tiêu đề</th>
                                            <th>Thể loại</th>
                                            <th>Tạo lúc</th>
                                            <th>Tạo bởi</th>
                                            <th>Trạng thái</th>
                                            <th>Quản lí</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach items="${requestScope.announcements}" var="announcement">
                                            <tr>
                                                <td>${announcement.id}</td>
                                                <td>${announcement.title}</td>
                                                <c:forEach items="${requestScope.announcementTypes}" var="type">
                                                    <c:if test="${type.id == announcement.announcementTypeId}">
                                                        <td class="text-center">${type.typeName}</td>
                                                    </c:if>
                                                </c:forEach>
                                                <td>${announcement.createdDate}</td>
                                                <c:forEach items="${requestScope.users}" var="user">
                                                    <c:if test="${user.id == announcement.createdBy}">
                                                        <td class="text-center">${user.email}</td>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${announcement.status=='active'}">
                                                    <td class="text-center text-success">Hoạt động</td>
                                                </c:if>
                                                <c:if test="${announcement.status=='pending'}">
                                                    <td class="text-center" style="color: #5A6376">Đang chờ</td>
                                                </c:if>
                                                <c:if test="${announcement.status=='inactive'}">
                                                    <td class="text-center text-danger">Vô hiệu hóa</td>
                                                </c:if>
                                                <td>
                                                    <span style="display: flex; align-items: center; justify-content: center; gap: 10px">
                                                        <a href="home-post-detail?id=${announcement.id}" title="Update announcement">
                                                            <i class="fas fa-pen icon-custom"></i>
                                                        </a>
                                                        <a href="approve?homeId=${announcement.id}" title="Approve announcement">
                                                            <i class="fas fa-check-double icon-custom"></i>
                                                        </a>
                                                        <a href="reject?homeId=${announcement.id}" title="Reject announcement">
                                                            <i class="fas fa-ban icon-custom"></i>
                                                        </a>
                                                    </span>
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
