<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 22/09/2024
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        .content-section {
            margin-bottom: 40px; /* Tạo khoảng cách giữa section và footer */
        }
        label {
            text-align: left;
            display: block !important;/* Đảm bảo nhãn nằm trên đầu input */
            margin-left: 10px; /* Điều chỉnh khoảng cách theo ý bạn */
        }
        .form-group {
            position: relative; /* Đặt vị trí cha cho nút con */
        }

        .form-control {
            padding-right: 40px; /* Dành không gian cho biểu tượng con mắt */
        }

        .toggle-password {
            position: absolute;
            bottom: 0; /* Căn chỉnh từ phía trên của trường input */
            right: 5px; /* Khoảng cách từ cạnh phải của ô input */
            height: 55%; /* Đảm bảo chiều cao của nút bằng với chiều cao của input */
            display: flex;
            align-items: center; /* Căn chỉnh biểu tượng con mắt theo chiều dọc của input */
            padding: 0;
            border: none;
            background: transparent;
            cursor: pointer;
            z-index: 2;
        }
        .checkbox {
            margin-top: 0 !important;
            label{
                padding-left: 0 !important;
            }
        }
    </style>
</head>
<body>
<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Login 11 - Bootstrap Brain Component -->
<jsp:include page="header.jsp"/>
<section class="py-30 py-md-50 py-lg-80 content-section">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <div class="m-b-50 text-center">
                    <h2 class="h1 font-weight-bold">Đăng nhập</h2>
                    <p>Chưa có tài khoản? <a href="sign-up.jsp">Đăng ký</a></p>
                </div>
            </div>
        </div>
        <div class="row text-center">
            <div class="col-xs-12 col-lg-10 col-lg-offset-1">
                <div class="row">
                    <div class="col-xs-12 col-lg-5">
                        <form action="login" method="post" onsubmit="return validateForm()">
                            <div class="row">
                                <c:if test="${requestScope.error != null}">
                                    <div class="col-xs-12">
                                        <div class="alert alert-danger">
                                                ${requestScope.error}
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${requestScope.message != null}">
                                    <div class="col-xs-12">
                                        <div class="alert alert-success">
                                                ${requestScope.message}
                                        </div>
                                    </div>
                                </c:if>
                                <div class="col-xs-12">
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" name="email" id="email" placeholder="ten@example.com"
                                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required maxlength="100">
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="form-group position-relative">
                                        <label for="password">Mật khẩu</label>
                                        <input type="password" class="form-control" name="password" id="password" placeholder="Mật khẩu" required minlength="8" maxlength="50">
                                        <button type="button" class="btn btn-link position-absolute toggle-password" onclick="togglePassword('password')">
                                            <i class="bi bi-eye"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-7 text-left">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="remember_me"> Ghi nhớ đăng nhập
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="text-end">
                                                <a href="forgot-password" class="text-muted">Quên mật khẩu?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <button class="btn btn-primary btn-lg btn-block" type="submit">Đăng nhập</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-xs-12 col-lg-2">
                        <div class="separator">
                            <span>hoặc</span>
                        </div>
                    </div>
                    <div class="col-xs-12 col-lg-5">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid
&redirect_uri=http://localhost:9999/homeSharing/sign-up-google
&response_type=code
&client_id=415667748014-s26phr0538d31vn3o169a1l85m1e1fa3.apps.googleusercontent.com
&approval_prompt=force" class="btn btn-lg btn-danger btn-block">
                            <i class="glyphicon glyphicon-log-in"></i>
                            <span>Đăng nhập bằng Google</span>
                        </a>
                        <a href="staff-login.jsp" class="btn btn-lg btn-primary btn-block">
                            <i class="glyphicon glyphicon-user"></i>
                            <span>Bạn là nhân viên?</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="footer.jsp"/>
<script>
    function validateForm() {
        let email = document.getElementById("email").value.trim();
        let password = document.getElementById("password").value;

        // Check for required fields and ensure they're not just spaces
        if (email === "" || password === "") {
            alert("Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.");
            return false;
        }

        // Validate email
        let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("Vui lòng nhập một địa chỉ email hợp lệ!");
            return false;
        }

        // Check password length
        if (password.length < 8) {
            alert("Mật khẩu phải có ít nhất 8 ký tự!");
            return false;
        }

        return true;
    }
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        const type = field.getAttribute('type');
        field.setAttribute('type', type === 'password' ? 'text' : 'password');
        const button = field.parentElement.querySelector('button');
        const icon = button.querySelector('i');
        if (type === "password") {
            field.type = "text";
            icon.classList.remove("bi-eye");
            icon.classList.add("bi-eye-slash");
        } else {
            field.type = "password";
            icon.classList.remove("bi-eye-slash");
            icon.classList.add("bi-eye");
        }
    }
</script>
</body>
</html>
