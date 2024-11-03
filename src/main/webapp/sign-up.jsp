<%--
  Created by IntelliJ IDEA.
  User: ManhNC
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
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">
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
                    <!-- Gói tiêu đề, form và đăng ký với Google trong một thẻ div -->
                    <div>
                        <div class="panel-heading text-center">
                            <h2 class="text-muted" style="margin-top: 70px" >Đăng kí</h2>
                            <p class="text-muted">Vui lòng nhập thông tin của bạn để đăng kí tài khoản.</p>
                        </div>
                        <div class="panel-body">
                            <form action="signup" method="post" onsubmit="return validateForm()">
                                <!-- Thông báo lỗi và thành công -->
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

                                <!-- Bước 1: Thông tin cá nhân -->
                                <div id="step1" class="panelContent">
                                    <div class="form-group" style="display: flex; justify-content: space-between">
                                        <div style="width: 47%">
                                            <label for="firstName">Họ <span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Họ"
                                                   value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>" required maxlength="50">
                                        </div>
                                        <div style="width: 47%">
                                            <label for="lastName">Tên <span class="text-danger">*</span></label>
                                            <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Tên"
                                                   value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>" required maxlength="50">
                                        </div>
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
                                    <button type="button" class="btn btn-primary btn-block" onclick="showStep2()">Tiếp theo</button>
                                </div>
                                <!-- Bước 2: Vai trò và điều khoản -->
                                <div id="step2" style="display: none;">
                                    <div style="display: flex;justify-content: center; margin-top: 10em;">
                                        <img src="assets/img/OBJECTS.svg" alt="Role Image" width="190" height="170">
                                    </div>
                                    <div style="text-align: center; color: #1A1A1A; font-size: 32px; font-weight: 700; text-transform: capitalize; line-height: 48px; word-wrap: break-word; margin-top: 20px">Vai Trò</div>
                                    <div style="width: 100%; text-align: center; color: #4D4D4D; font-size: 20px; font-weight: 500; line-height: 30px; word-wrap: break-word">Vui lòng chọn vai trò phù hợp với nhu cầu của bạn.<br/>Mỗi vai trò sẽ có các chức năng và quyền hạn khác nhau.</div>
                                    <div class="form-group">
                                        <div style="display: flex; justify-content: space-between;     margin-top: 40px;">
                                            <div style="width: 80%; margin: 10px; background: white; border-radius: 10px; border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); display: flex; align-items: start; justify-content: space-between;">
                                                <div style="display: flex; align-items: flex-start;     width: 90%;">
                                                    <img src="assets/img/Frame.svg" style="width: 40px; height: auto; margin-right: 10px;">
                                                    <div>
                                                        <h4 style="font-weight: 800; margin: 0;">Người Thuê</h4>
                                                        <p style="margin: 0; color: #555; font-size: 0.9em;">Chưa có nhà, đang cần tìm nhà và người ở ghép.</p>
                                                    </div>
                                                </div>
                                                <input type="radio" name="role" value="findRoommate" required>
                                            </div>
                                            <div style="width: 80%;margin: 10px; background: white; border-radius: 10px; border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); display: flex; align-items: start; justify-content: space-between;">
                                                <div style="display: flex; align-items: flex-start;     width: 90%;">
                                                    <img src="assets/img/Frame.svg" style="width: 40px; height: auto; margin-right: 10px;">
                                                    <div>
                                                        <h4 style="font-weight: 800; margin: 0;">Đăng phòng</h4>
                                                        <p style="margin: 0; color: #555; font-size: 0.9em;">Đã có nhà, đang cần tìm người ở ghép, có thể tạo tin đăng.
                                                        </p>
                                                    </div>
                                                </div>
                                                <input type="radio" name="role" value="postRoom" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" value="" name="iAgree" id="iAgree" required> Tôi đồng ý với
                                                <a href="terms.jsp" class="text-primary">các điều khoản, điều kiện của trang web.</a>
                                            </label>
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-secondary btn-block" onclick="showStep1()">Quay lại</button>
                                    <button class="btn btn-primary btn-block" type="submit">Đăng kí</button>
                                </div>
                            </form>
                            <div class="panelContent">
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
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
<script>
    function showStep2() {
        if (validateStep1()) {
            const panels = document.getElementsByClassName("panelContent");
            for (let i = 0; i < panels.length; i++) {
                panels[i].style.display = "none";
            }
            document.getElementById("step2").style.display = "block"; // Hiển thị phần tiếp theo
        }
    }

    function showStep1() {
        const panels = document.getElementsByClassName("panelContent");
        for (let i = 0; i < panels.length; i++) {
            panels[i].style.display = "block";
        }
        document.getElementById("step2").style.display = "none"; // Ẩn phần tiếp theo
    }
    function validateStep1() {
        let firstName = document.getElementById("firstName").value.trim();
        let lastName = document.getElementById("lastName").value.trim();
        let email = document.getElementById("email").value.trim();
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirmPassword").value;

        let nameRegex = /^[\p{L}\s]+$/u;
        let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (firstName === "" || lastName === "" || email === "" || password === "" || confirmPassword === "") {
            Swal.fire({
                icon: 'error',
                title: 'Tất cả các trường là bắt buộc và không thể để trống.'
            });
            return false;
        }
        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            Swal.fire({
                icon: 'error',
                title: 'Họ và tên chỉ chứa chữ cái và khoảng trắng.'
            });
            return false;
        }
        if (!emailRegex.test(email)) {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng nhập địa chỉ email hợp lệ!'
            });
            return false;
        }
        if (password.length < 8) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu phải có ít nhất 8 ký tự!'
            });
            return false;
        }
        if (password !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu không khớp!'
            });
            return false;
        }

        return true;
    }
    function validateForm() {
        console.log("error");
        let firstName = document.getElementById("firstName").value.trim();
        let lastName = document.getElementById("lastName").value.trim();
        let email = document.getElementById("email").value.trim();
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirmPassword").value;
        let role = document.getElementById("role").value;

        // Check for required fields and ensure they're not just spaces
        if (firstName === "" || lastName === "" || email === "" || password === "" || confirmPassword === ""|| role === "") {
            Swal.fire({
                icon: 'error',
                title: 'Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.'
            });
            return false;
        }

        // Check if firstName and lastName only contain letters and spaces (supports Vietnamese characters)
        let nameRegex = /^[\p{L}\s]+$/u;

        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            Swal.fire({
                icon: 'error',
                title: 'Names can only contain letters and spaces.'
            });
            return false;
        }

        // Validate email
        let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng nhập một địa chỉ email hợp lệ!'
            });
            return false;
        }

        // Check password length
        if (password.length < 8) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu phải có ít nhất 8 ký tự!'
            });
            return false;
        }

        // Check if passwords match
        if (password !== confirmPassword) {
            Swal.fire({
                icon: 'error',
                title: 'Mật khẩu không khớp!'
            });
            return false;
        }

        // Check if a role is selected
        if (role === "") {
            Swal.fire({
                icon: 'error',
                title: 'Vui lòng chọn một vai trò!'
            });
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
