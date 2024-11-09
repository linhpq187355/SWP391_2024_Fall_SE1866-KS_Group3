<%--
  Author: ThangLT
  Date: 01-Nov-24
  Time: 1:58 PM
--%>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>

    <style>
        /* Custom animation for modal */
        .modal.fade .modal-dialog {
            transform: translateY(-50px);
            opacity: 0;
            transition: transform 0.3s ease, opacity 0.3s ease;
        }

        .modal.show .modal-dialog {
            transform: translateY(0);
            opacity: 1;
        }

        .modal-header {
            background-color: #f8f9fa; /* Light background for header */
        }

        .modal-footer {
            background-color: #f8f9fa; /* Light background for footer */
        }

        .modal-body h6 {
            border-bottom: 1px solid #e9ecef; /* Optional: Add a bottom border for separation */
            padding-bottom: 10px; /* Optional: Add padding for better spacing */
        }
    </style>
</head>
<body style="color: black;">
<div class="wrapper">
    <!-- Sidebar -->
    <jsp:include page="dashboard-sidebar.jsp"/>
    <!-- End Sidebar -->

    <div class="main-panel">
        <jsp:include page="dashboard-header.jsp"/>

        <div class="container">
            <div class="page-inner">
                <div class="page-header">
                    <h3 class="fw-bold mb-3">Phân Quyền</h3>
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
                            <form action="${pageContext.request.contextPath}/dashboard/permission-update" method="post">
                                <input type="hidden" name="userId" value="${requestScope.selectedUser.id}" />
                                <div class="card-header" style="display: flex; justify-content: space-between">
                                    <h4 class="card-title">Cấp quyền</h4>
                                    <button class="btn btn-secondary" type="submit">
                                    <span class="btn-label">
                                      <i class="fas fa-floppy-disk"></i>
                                    </span>
                                        Lưu thông tin
                                    </button>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table id="basic-datatables" class="display table table-striped">
                                            <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Mã cấp quyền</th>
                                                <th>Mô tả</th>
                                                <th class="text-center">Cho phép</th>
                                                <th class="text-center">Từ chối</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${requestScope.permissionList}" var="perm">
                                                <tr>
                                                    <td>${perm.id}</td>
                                                    <td>${perm.name}</td>
                                                    <td class="text-capitalize">${perm.description}</td>
                                                    <td class="text-center">
                                                        <c:set var="isChecked" value="false"/>
                                                        <c:if test="${not empty requestScope.userPermission}">
                                                            <c:forEach items="${requestScope.userPermission}" var="userPerm">
                                                                <c:if test="${userPerm.name == perm.name}">
                                                                    <c:set var="isChecked" value="true"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        <input type="radio" name="${perm.name}" value="allow" <c:if test="${isChecked}">checked</c:if> >
                                                    </td>
                                                    <td class="text-center">
                                                        <input type="radio" name="${perm.name}" value="reject" <c:if test="${!isChecked}">checked</c:if> >
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
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
<script src="./assets/js/kaiadmin.min.js"></script>
<script>
    $(document).ready(function () {
        // $("#basic-datatables").DataTable({});
        $(document).ready(function () {
            $("#basic-datatables").DataTable({
                pageLength: 10,
                initComplete: function () {
                    this.api()
                        .columns([2, 3])
                        .every(function (index) {
                            var column = this;

                            // Define labels based on column index
                            var labels = [
                                "ID",
                                "Email",
                                "Vai trò",
                                "Trạng thái",
                                "Thao tác"
                            ];

                            // Create a container with label and select element
                            var footerContainer = $('<div>')
                                .append('<i class="fas fa-filter"></i>' + '<span>' + labels[index] + ": " + '</span>')
                                .append('<select class="form-select"><option value="">' + "Tất Cả" + '</option></select>')
                                .appendTo($(column.footer()).empty());

                            // Populate the select element with unique column data
                            footerContainer
                                .find('select')
                                .on("change", function () {
                                    var val = $.fn.dataTable.util.escapeRegex($(this).val());
                                    column
                                        .search(val ? "^" + val + "$" : "", true, false)
                                        .draw();
                                });

                            // Populate dropdown options
                            column
                                .data()
                                .unique()
                                .sort()
                                .each(function (d) {
                                    footerContainer.find('select').append(
                                        '<option value="' + d + '">' + d + "</option>"
                                    );
                                });
                        });
                },
            });
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
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Attach click event to all user detail icons
        const userDetailLinks = document.querySelectorAll('.user-detail');

        userDetailLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault(); // Prevent default anchor behavior

                // Get user details from data attributes
                const userId = this.getAttribute('data-id');
                const userEmail = this.getAttribute('data-email');
                const userRole = this.getAttribute('data-role');
                const userStatus = this.getAttribute('data-status');
                const userPhoneNum = this.getAttribute('data-phone-num') || "Chưa có";
                const userAddress = this.getAttribute('data-address') || "Chưa có";

                // Populate modal with user details
                document.getElementById('userId').textContent = userId;
                document.getElementById('userEmail').textContent = userEmail;
                document.getElementById('userRole').textContent = userRole;
                document.getElementById('userStatus').textContent = userStatus;
                document.getElementById('userPhoneNum').textContent = userPhoneNum;
                document.getElementById('userAddress').textContent = userAddress;
                // Show the modal
                const userDetailModal = new bootstrap.Modal(document.getElementById('userDetailModal'));
                userDetailModal.show();
            });
        });
    });
</script>
</body>
</html>
