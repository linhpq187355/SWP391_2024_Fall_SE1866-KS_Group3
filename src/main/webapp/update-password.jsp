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
    <title>Cập nhật mật khẩu</title>
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
                        <h3 class="text-muted">Cập nhật mật khẩu</h3
                        <p class="text-muted">Nếu quên mật khẩu cũ, hãy đăng xuất và click vào Quên mật khẩu để đặt lại.</p>
                    </div>
                    <div class="panel-body">
                        <form action="user-update-password" method="post" onsubmit="return validateForm()">
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
                            <c:if test="${sessionScope.hadPass == 0}">
                                <div class="form-group">
                                    <label for="old-password">Nhập Mật Khẩu Cũ<span class="text-danger">*</span></label>
                                    <div class="input-group">
                                        <input type="password" class="form-control" name="old-password" id="old-password" placeholder="Mật Khẩu Cũ" required minlength="8" maxlength="50">
                                        <span class="input-group-btn">
                                        <button type="button" class="btn btn-default" onclick="togglePassword('old-password')">
                                            <i class="glyphicon glyphicon-eye-open"></i>
                                        </button>
                                    </span>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label for="password">Mật Khẩu Mới<span class="text-danger">*</span></label>
                                <div class="input-group">
                                    <input type="password" class="form-control" name="password" id="password" placeholder="Mật Khẩu Mới" required minlength="8" maxlength="50">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-default" onclick="togglePassword('password')">
                                            <i class="glyphicon glyphicon-eye-open"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword">Nhập lại mật khẩu mới<span class="text-danger">*</span></label>
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
                                <button class="btn btn-primary btn-block" type="submit">Xác nhận</button>
                            </div>
                        </form>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
<script>
    function validateForm() {
        let password = document.getElementById("password").value;
        let oldPasswordField = document.getElementById("old-password");
        let oldPassword = oldPasswordField ? oldPasswordField.value.trim() : ""; // Kiểm tra trường oldPassword

        let confirmPassword = document.getElementById("confirmPassword").value;

        // Kiểm tra nếu cần nhập mật khẩu cũ nhưng không có giá trị
        if (oldPasswordField && oldPassword === "") {
            alert("Mật khẩu cũ không thể để trống.");
            return false;
        }

        // Check for required fields and ensure they're not just spaces
        if (password.trim() === "" || confirmPassword.trim() === "") {
            alert("Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.");
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
