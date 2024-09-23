<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 22/09/2024
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://unpkg.com/bs-brain@2.0.4/components/registrations/registration-10/assets/css/registration-10.css">
</head>
<body>
<!-- Registration 10 - Bootstrap Brain Component -->
<section class="bg-light py-3 py-md-5 py-xl-8">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                <div class="mb-5">
                    <div class="text-center">
                        <a href="#!" class="btn btn-lg btn-danger">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                                <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                            </svg>
                            <span class="ms-2 fs-6">Đăng kí bằng Google</span>
                        </a>
                    </div>
                </div>
                <div class="card border border-light-subtle rounded-4">
                    <div class="card-body p-3 p-md-4 p-xl-5">
                        <form action="signup" method="post" onsubmit="return validateForm()">
                            <p class="text-center mb-4">Đăng kí bằng email</p>
                            <div class="row gy-3 overflow-hidden">
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" required maxlength="50">
                                        <label for="firstName" class="form-label">Họ</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="text" class="form-control" name="lastName" id="lastName" placeholder="First Name" required maxlength="50">
                                        <label for="lastName" class="form-label">Tên</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="email" class="form-control" name="email" id="email" placeholder="name@example.com" required maxlength="100">
                                        <label for="email" class="form-label">Email</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="password" class="form-control" name="password" id="password" value="" placeholder="Mật Khẩu" required minlength="8" maxlength="50">
                                        <label for="password" class="form-label">Mật Khẩu</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Nhập lại mật khẩu" required minlength="8" maxlength="50">
                                        <label for="confirmPassword" class="form-label">Nhập lại mật khẩu</label>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-floating mb-3">
                                        <select class="form-select" name="role" id="role" required>
                                            <option value="">Bạn muốn</option>
                                            <option value="findRoommate">Tìm roommate</option>
                                            <option value="postRoom">Đăng phòng</option>
                                        </select>
                                        <label for="role" class="form-label">Vai trò</label>
                                    </div>
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
                                        <button class="btn btn-primary btn-lg" type="submit">Đăng kí</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-center mt-4">
                    <p class="m-0 text-secondary text-center">Đã có tài khoản? <a href="login.jsp" class="link-primary text-decoration-none">Đăng nhập</a></p>
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

        // Check if firstName and lastName only contain letters and spaces
        let nameRegex = /^[A-Za-z\s]+$/;
        if (!nameRegex.test(firstName) || !nameRegex.test(lastName)) {
            alert("Họ và tên chỉ được chứa chữ cái và khoảng trắng.");
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
</script>
</body>
</html>
