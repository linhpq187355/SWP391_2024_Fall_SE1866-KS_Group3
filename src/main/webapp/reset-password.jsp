<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/29/2024
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Đặt lại mật khẩu</title>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>
    <!-- Place favicon.ico  the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <link rel="stylesheet" href="assets/css/normalize.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/fontello.css">
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="assets/css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="bg-light py-3 py-md-5">
    <div class="container">
        <div class="row justify-content-md-center" style="display: flex;justify-content: center;">
            <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                <div class="bg-white p-4 p-md-5 rounded shadow-sm">
                    <div class="row gy-3 mb-5" style="margin-bottom: 30px">
                        <div class="col-12">
                            <div class="text-center">
                                <h2>Nhập mật khẩu mới</h2>
                            </div>
                        </div>
                        <div class="col-12">
                            <h5 class="fs-6 fw-normal text-center text-secondary m-0 px-md-5">Vui lòng nhập mật khẩu mới.</h5>
                            <c:if test="${not empty requestScope.message}">
                                <div class="col-md-12">
                                    <div class="alert alert-success" role="alert" id="message">
                                            ${requestScope.message}
                                    </div>
                                </div>
                                <div class="text-center mt-3">
                                    <h4>Chuyển hướng trong <span id="countdown">3</span> giây...</h4>
                                </div>
                                <script>
                                    let countdown = 3; // Bắt đầu từ 3 giây
                                    const countdownElement = document.getElementById('countdown');

                                    // Hàm đếm ngược
                                    const intervalId = setInterval(() => {
                                        countdown--;
                                        countdownElement.textContent = countdown;

                                        if (countdown === 0) {
                                            clearInterval(intervalId); // Dừng đếm ngược
                                            window.location.href = 'login.jsp'; // Chuyển hướng về trang login
                                        }
                                    }, 1000); // Giảm mỗi giây
                                </script>
                            </c:if>
                            <c:if test="${not empty requestScope.error}">
                                <div class="col-md-12">
                                    <div class="alert alert-danger" role="alert" id="errorMessage">
                                            ${requestScope.error}
                                    </div>
                                </div>
                            </c:if>
                        </div>

                    </div>
                    <c:if test="${empty requestScope.message}">
                        <form action="reset-password" method="post">
                            <div class="row gy-3 gy-md-4 overflow-hidden">
                                <div class="col-12" style="margin: 20px 0;">
                                    <label for="pass" class="form-label">Mật khẩu mới <span class="text-danger">*</span></label>
                                    <div class="input-group" style="display: flex; align-items: center; padding: 0 10px;margin: 10px 0;">
                                        <i class="fa-solid fa-lock"></i>
                                        <input type="password" class="form-control" name="pass" id="pass" required style="margin: 0 10px;">
                                        <i class="fa-regular fa-eye" id="togglePassword1" style="cursor: pointer;"></i>
                                    </div>
                                    <div id="pass-error" class="text-danger" style="display: none;">Mật khẩu phải từ 8 đến 50 ký tự.</div>
                                </div>
                                <div class="col-12" style="margin: 20px 0;">
                                    <label for="re_pass" class="form-label">Nhập lại mật khẩu <span class="text-danger">*</span></label>
                                    <div class="input-group" style="display: flex; align-items: center; padding: 0 10px;margin: 10px 0;">
                                        <i class="fa-solid fa-lock"></i>
                                        <input type="password" class="form-control" name="re_pass" id="re_pass" required style="margin: 0 10px;">
                                        <i class="fa-regular fa-eye" id="togglePassword2" style="cursor: pointer;"></i>
                                    </div>
                                    <div id="re_pass-error" class="text-danger" style="display: none;">Mật khẩu không khớp.</div>
                                </div>
                                <input type="text" value="${requestScope.userId}" name="id" hidden="hidden"/>
                                <div class="col-12" style="display: flex; justify-content: center; margin: 20 0 50 0">
                                    <div class="d-grid">
                                        <button class="btn btn-primary btn-lg" type="submit" id="submitBtn">Reset Password</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    function togglePasswordVisibility(inputId, toggleId) {
        const input = document.getElementById(inputId);
        const toggle = document.getElementById(toggleId);

        // Thay đổi loại input giữa 'password' và 'text'
        if (input.type === "password") {
            input.type = "text"; // Hiện mật khẩu
            toggle.classList.remove("fa-eye");
            toggle.classList.add("fa-eye-slash"); // Thay đổi icon thành eye-slash
        } else {
            input.type = "password"; // Ẩn mật khẩu
            toggle.classList.remove("fa-eye-slash");
            toggle.classList.add("fa-eye"); // Thay đổi icon thành eye
        }
    }

    // Thêm sự kiện click vào các biểu tượng mắt
    document.getElementById('togglePassword1').addEventListener('click', function() {
        togglePasswordVisibility('pass', 'togglePassword1');
    });

    document.getElementById('togglePassword2').addEventListener('click', function() {
        togglePasswordVisibility('re_pass', 'togglePassword2');
    });

    document.getElementById('submitBtn').addEventListener('click', function(event) {
        const password = document.getElementById('pass').value;
        const confirmPassword = document.getElementById('re_pass').value;
        let isValid = true;

        // Xóa thông báo lỗi trước đó
        document.getElementById('pass-error').style.display = 'none';
        document.getElementById('re_pass-error').style.display = 'none';

        // Kiểm tra độ dài mật khẩu
        if (password.length < 8 || password.length > 50) {
            document.getElementById('pass-error').style.display = 'block';
            isValid = false;
        }

        // Kiểm tra mật khẩu không có dấu cách
        if (password.includes(' ')) {
            document.getElementById('pass-error').textContent = "Mật khẩu không được có dấu cách.";
            document.getElementById('pass-error').style.display = 'block';
            isValid = false;
        }

        // Kiểm tra mật khẩu xác nhận
        if (password !== confirmPassword) {
            document.getElementById('re_pass-error').style.display = 'block';
            isValid = false;
        }

        // Ngăn không cho gửi form nếu không hợp lệ
        if (!isValid) {
            event.preventDefault();
        }

    });
</script>
</body>
</html>
