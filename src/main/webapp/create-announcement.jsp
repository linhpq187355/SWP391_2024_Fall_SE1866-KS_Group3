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
        .control-label{
            color: #777777 !important;
        }
        .form-control{
            color: #777777 !important;
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
                    <h3 class="fw-bold mb-3">Tài khoản</h3>
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
                            <form action="create-announcement" method="post" onsubmit="return validateForm()">
                                <div class="card-header" style="display: flex; justify-content: space-between">
                                    <div class="card-title">Mẫu thông báo</div>
                                </div>
                                <div class="card-body">
                                    <div class="form-group">
                                        <label for="announcementTypeId" class="control-label">Loại thông báo<span class="text-danger">*</span></label>
                                        <select class="form-control" id="announcementTypeId" name="announcementTypeId" required>
                                            <option value="-1" disabled selected>Chọn loại thông báo</option>
                                            <c:forEach items="${requestScope.announcementTypes}" var="type">
                                                <option value="${type.id}">${type.typeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="title" class="control-label">Tiêu đề<span class="text-danger">*</span></label>
                                        <input type="text" class="form-control" id="title" name="title" required minlength="5" maxlength="127">
                                    </div>
                                    <div class="form-group">
                                        <label for="content" class="control-label">Nội dung<span class="text-danger">*</span></label>
                                        <textarea class="form-control" id="content" name="content" rows="3"
                                                  required></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">Trạng thái<span class="text-danger">*</span></label>
                                        <select class="form-control" id="announcementStatus" name="announcementStatus" required>
                                            <option value="active" selected>Hoạt động</option>
                                            <option value="pending" >Chờ</option>
                                        </select>
                                    </div>

                                </div>
                                <div class="card-action">
                                    <button class="btn btn-success" style="padding-left:18px; padding-right: 18px" type="submit">Tạo</button>
                                    <button class="btn btn-danger" style="padding-left:18px; padding-right: 18px" type="reset">Hủy</button>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="footer-admin.jsp"/>
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
<script type="text/javascript">
    function validateForm() {
        let title = document.getElementById("title").value.trim();
        let content = document.getElementById("content").value.trim();
        let announcementTypeId = document.getElementById("announcementTypeId").value;
        let announcementStatus = document.getElementById("announcementStatus").value;


        if (announcementTypeId===-1) {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng chọn loại thông báo'
            });
            return false;
        }


        // Check for required fields and ensure they're not just spaces
        if (title === "" || content === "") {
            Swal.fire({
                icon: 'error',
                title: 'Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.'
            });
            return false;
        }


        // Check password length
        if (title.length < 5 || title.length>512) {
            Swal.fire({
                icon: 'error',
                title: 'Nhập trong khoảng từ 5 đến 512 kí tự'
            });
            return false;
        }

        // Check if a status is selected
        if (announcementStatus === "") {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng chọn trạng thái thông báo'
            });
            return false;
        }

        // Update trimmed values
        document.getElementById("title").value = title;
        document.getElementById("content").value = content;
        return true;
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
