<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 22/09/2024
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html  lang="vi">
<head>
    <title>Đăng ký</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        .error-message {
            color: red;
            font-size: 0.875em;
            margin-top: 0.25rem;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<section class="bg-light">
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
                <div class="panel panel-default">
                    <div class="panel-heading text-center">
                        <h3 class="text-muted">Nhập thông tin của bạn để đăng ký</h3>
                        <p class="text-muted">Sau khi đăng kí thành công, mã xác thực sẽ được gửi vào email của bạn.</p>
                    </div>
                    <div class="panel-body">
                        <form action="signup" method="post" onsubmit="return validateForm()">
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
                            <div class="form-group">
                                <label for="firstName">Họ <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Họ"
                                       value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>" required maxlength="50">
                            </div>
                            <div class="form-group">
                                <label for="lastName">Tên <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Tên"
                                       value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>" required maxlength="50">
                            </div>
                            <div class="form-group">
                                <label for="email">Email <span class="text-danger">*</span></label>
                                <input type="email" class="form-control" name="email" id="email" placeholder="name@example.com" required maxlength="100">
                            </div>
                            <div class="form-group">
                                <label for="password">Mật Khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <input type="password" class="form-control" name="password" id="password" placeholder="Mật Khẩu" required minlength="8" maxlength="50">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-default" onclick="togglePassword('password')">
                                            <i class="glyphicon glyphicon-eye-open"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword">Nhập lại mật khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu" required minlength="8" maxlength="50">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-default" onclick="togglePassword('confirmPassword')">
                                            <i class="glyphicon glyphicon-eye-open"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="role">Vai trò <span class="text-danger">*</span></label>
                                <select class="form-control" name="role" id="role" required>
                                    <option value="">Bạn muốn</option>
                                    <option value="findRoommate">Tìm roommate</option>
                                    <option value="postRoom">Đăng phòng</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="" name="iAgree" id="iAgree" required> Tôi đồng ý với <a href="#!" class="text-primary">các điều khoản, điều kiện của trang web.</a>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary btn-block" type="submit">Đăng kí</button>
                            </div>
                        </form>
                        <hr>
                           <p class="text-center">Đã có tài khoản? <a href="login.jsp" class="text-primary">Đăng nhập</a></p>
                        <p class="text-center">Hoặc đăng kí với</p>
                        <div class="text-center">
                            <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid
&redirect_uri=http://localhost:9999/homeSharing/sign-up-google
&response_type=code
&client_id=66002425643-qfqed6dbl9lj6nbhv8ejd5qci14bv4ks.apps.googleusercontent.com
&approval_prompt=force" class="btn btn-default">
                                <i class="glyphicon glyphicon-google"></i> Google
                            </a>
<%--                            <a href="#!" class="btn btn-default">--%>
<%--                                <i class="glyphicon glyphicon-facebook"></i> Facebook--%>
<%--                            </a>--%>
<%--                            <a href="#!" class="btn btn-default">--%>
<%--                                <i class="glyphicon glyphicon-twitter"></i> Twitter--%>
<%--                            </a>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
<script>
    function validateForm() {
        let firstName = document.getElementById("firstName").value.trim();
        let lastName = document.getElementById("lastName").value.trim();
        let email = document.getElementById("email").value.trim();
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirmPassword").value;
        let role = document.getElementById("role").value;

        // Check for required fields and ensure they're not just spaces
        if (firstName === "" || lastName === "" || email === "" || password === "" || confirmPassword === ""|| role === "") {
            alert("Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.");
            return false;
        }

        // Check if firstName and lastName only contain letters and spaces (supports Vietnamese characters)
        let nameRegex = /^[\p{L}\s]+$/u;

        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            alert("Names can only contain letters and spaces.");
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

        // Check if passwords match
        if (password !== confirmPassword) {
            alert("Mật khẩu không khớp!");
            return false;
        }

        // Check if a role is selected
        if (role === "") {
            alert("Vui lòng chọn một vai trò!");
            return false;
        }

        // Update trimmed values
        document.getElementById("firstName").value = firstName;
        document.getElementById("lastName").value = lastName;
        document.getElementById("email").value = email;

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
            icon.classList.remove("glyphicon-eye-open");
            icon.classList.add("glyphicon-eye-close");
        } else {
            field.type = "password";
            icon.classList.remove("glyphicon-eye-close");
            icon.classList.add("glyphicon-eye-open");
        }
    }
</script>
</body>
</html>
