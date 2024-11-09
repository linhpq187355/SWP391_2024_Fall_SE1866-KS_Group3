<%--
  Author: ThangLT
  Date: 01-Nov-24
  Time: 1:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
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
    <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
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

    <div class="main-panel">
        <%--        Put the header in here--%>
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
                            <a href="#">Phân quyền</a>
                        </li>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header" style="display: flex; justify-content: space-between">
                                <h4 class="card-title">Danh sách người dùng</h4>
                                <button class="btn btn-secondary"
                                        onclick="window.location.href='dashboard/create-account'">
                        <span class="btn-label">
                          <i class="fa fa-plus"></i>
                        </span>
                                    Tạo tài khoản
                                </button>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table id="basic-datatables" class="display table table-striped">

                                        <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Email</th>
                                            <th>Vai trò</th>
                                            <th>Trạng thái</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                        </tfoot>

                                        <tbody>
                                        <c:forEach items="${requestScope.userList}" var="user">
                                            <tr>
                                                <td>${user.id}</td>
                                                <td>${user.email}</td>
                                                <td class="text-capitalize">${user.roleName}</td>
                                                <c:if test="${user.status=='active'}">
                                                    <td class="text-success text-capitalize">${user.status}</td>
                                                </c:if>
                                                <c:if test="${user.status!='active'}">
                                                    <td class="text-danger text-capitalize">Inactive</td>
                                                </c:if>
                                                <td class="space-y-4 text-center">
                                                    <a href="#" class="user-detail"
                                                       data-id="${user.id}"
                                                       data-full-name="${user.firstName} +' ' + ${user.lastName}"
                                                       data-email="${user.email}"
                                                       data-role="${user.roleName}"
                                                       data-status="${user.status}"
                                                       data-phone-num="${user.phoneNumber}"
                                                       data-dob="${user.dob}"
                                                       data-address="${user.address}"
                                                    >
                                                        <i class="fas fa-eye fa-lg" style="color: #fa8650;"></i>
                                                    </a>
                                                    <a href="dashboard/permission-update?userId=${user.id}">
                                                        <i class="fas fa-key fa-lg" style="color: #fa8650;"></i>
                                                    </a>
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
        <jsp:include page="dashboard-footer.jsp"/>

    </div>
</div>

<div class="modal fade" id="userDetailModal" tabindex="-1" aria-labelledby="userDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userDetailModalLabel">Chi tiết tài khoản</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-hashtag"></i> <strong>UID:</strong> <span
                                id="userId"></span></h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-envelope"></i> <strong>Email:</strong> <span
                                id="userEmail"></span></h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-id-badge"></i> <strong>Vai trò:</strong> <span
                                id="userRole"></span></h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-signal"></i> <strong>Trạng thái:</strong> <span
                                id="userStatus"></span></h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-phone"></i> <strong>SĐT:</strong> <span
                                id="userPhoneNum"></span></h6>
                    </div>
                    <div class="col-12 mb-3">
                        <h6 class="text-muted"><i class="fas fa-map"></i> <strong>Địa chỉ:</strong> <span
                                id="userAddress"></span></h6>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<!--   Core JS Files   -->
<script src="./assets/js/core/jquery-3.7.1.min.js"></script>
<script src="./assets/js/core/popper.min.js"></script>
<script src="./assets/js/core/bootstrap.min.js"></script>

<script src="./assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
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
