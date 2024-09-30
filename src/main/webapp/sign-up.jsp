<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 22/09/2024
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html  lang="vi">
<head>
    <title>Đăng ký</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
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
<section class="bg-light py-3 py-md-5">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                <div class="bg-white p-4 p-md-5 rounded shadow-sm">
                    <div class="mb-5 text-center">
                        <h2 class="h3">Đăng ký</h2>
                        <h3 class="fs-6 fw-normal text-secondary m-0">Nhập thông tin của bạn để đăng ký</h3>
                        <p class="text-secondary">Sau khi đăng kí thành công, hãy click vào đường link được gửi trong email để xác thực tài khoản.</p>
                    </div>
                    <form action="signup" method="post" onsubmit="return validateForm()">
                        <div class="row gy-3 gy-md-4 overflow-hidden">
                            <div class="col-12">
                                <label for="firstName" class="form-label">Họ <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Họ" required maxlength="50">
                            </div>
                            <div class="col-12">
                                <label for="lastName" class="form-label">Tên <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Tên" required maxlength="50">
                            </div>
                            <div class="col-12">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <input type="email" class="form-control" name="email" id="email" placeholder="name@example.com" required maxlength="100">
                            </div>
                            <div class="col-12 position-relative">
                                <label for="password" class="form-label">Mật Khẩu <span class="text-danger">*</span></label>
                                <div class="position-relative">
                                    <input type="password" class="form-control" name="password" id="password" placeholder="Mật Khẩu" required minlength="8" maxlength="50">
                                    <button type="button" class="btn btn-link position-absolute end-0 top-50 translate-middle-y me-2" onclick="togglePassword('password')">
                                        <i class="bi bi-eye"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-12 position-relative">
                                <label for="confirmPassword" class="form-label">Nhập lại mật khẩu <span class="text-danger">*</span></label>
                                <div class="position-relative">
                                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu" required minlength="8" maxlength="50">
                                    <button type="button" class="btn btn-link position-absolute end-0 top-50 translate-middle-y me-2" onclick="togglePassword('confirmPassword')">
                                        <i class="bi bi-eye"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-12">
                                <label for="role" class="form-label">Vai trò <span class="text-danger">*</span></label>
                                <select class="form-select" name="role" id="role" required>
                                    <option value="">Bạn muốn</option>
                                    <option value="findRoommate">Tìm roommate</option>
                                    <option value="postRoom">Đăng phòng</option>
                                </select>
                            </div>
                            <div class="col-12">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="" name="iAgree" id="iAgree" required>
                                    <label class="form-check-label text-secondary" for="iAgree">
                                        Tôi đồng ý với <a href="#!" class="link-primary text-decoration-none">các điều khoản, điều kiện của trang web.</a>
                                    </label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="d-grid">
                                    <button class="btn btn-lg btn-primary" type="submit">Đăng kí</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="row mt-4">
                        <div class="col-12">
                            <hr class="mt-4 mb-4 border-secondary-subtle">
                            <p class="m-0 text-secondary text-center">Đã có tài khoản? <a href="login.jsp" class="link-primary text-decoration-none">Đăng nhập</a></p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <p class="mt-5 mb-4">Hoặc đăng nhập với</p>
                            <div class="d-flex gap-3 flex-column flex-md-row">
                                <a href="#!" class="btn btn-outline-primary">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                                        <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                                    </svg>
                                    <span class="ms-2 fs-6">Google</span>
                                </a>
                                <a href="#!" class="btn btn-outline-primary">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-facebook" viewBox="0 0 16 16">
                                        <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z" />
                                    </svg>
                                    <span class="ms-2 fs-6">Facebook</span>
                                </a>
                                <a href="#!" class="btn btn-outline-primary">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-twitter" viewBox="0 0 16 16">
                                        <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.006-.422A6.685 6.685 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.325 9.325 0 0 1-6.767-3.429 3.289 3.289 0 0 0 1.018 4.382A3.323 3.323 0 0 1 .64 6.575v.045a3.288 3.288 0 0 0 2.632 3.218 3.203 3.203 0 0 1-.865.115 3.23 3.23 0 0 1-.614-.057 3.283 3.283 0 0 0 3.067 2.277A6.588 6.588 0 0 1 .78 13.58a6.32 6.32 0 0 1-.78-.045A9.344 9.344 0 0 0 5.026 15z" />
                                    </svg>
                                    <span class="ms-2 fs-6">Twitter</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
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
