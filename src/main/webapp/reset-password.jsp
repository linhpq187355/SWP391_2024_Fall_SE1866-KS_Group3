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
    <title>Title</title>
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="bg-light py-3 py-md-5">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                <div class="bg-white p-4 p-md-5 rounded shadow-sm">
                    <div class="row gy-3 mb-5">
                        <div class="col-12">
                            <div class="text-center">
                                <h2>Nhập mật khẩu mới</h2>
                            </div>
                        </div>
                        <div class="col-12">
                            <h2 class="fs-6 fw-normal text-center text-secondary m-0 px-md-5">Vui lòng nhập mật khẩu mới.</h2>
                            <c:if test="${not empty requestScope.error}">
                                <div class="col-md-12">
                                    <div class="alert alert-danger" role="alert" id="errorMessage">
                                            ${requestScope.error}
                                    </div>
                                </div>
                            </c:if>
                        </div>

                    </div>
                    <form action="reset-password" method="post">
                        <div class="row gy-3 gy-md-4 overflow-hidden">
                            <div class="col-12">
                                <label for="pass" class="form-label">Mật khẩu mới <span class="text-danger">*</span></label>
                                <div class="input-group">
                  <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                      <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                    </svg>
                  </span>
                                    <input type="password" class="form-control" name="pass" id="pass" required>
                                    <span class="input-group-text" id="togglePassword1" style="cursor: pointer;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                    <path d="M8 3C4.5 3 1.5 6 0 8c1.5 2 4.5 5 8 5s6.5-3 8-5c-1.5-2-4.5-5-8-5zM1.5 8C2.6 9.4 5 12 8 12s5.4-2.6 6.5-4c-1.1-1.4-3.5-4-6.5-4S2.6 6.6 1.5 8z"/>
                    <path d="M8 10.5a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5z"/>
                    <path d="M8 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg>
            </span>
                                </div>
                                <div id="pass-error" class="text-danger" style="display: none;">Mật khẩu phải từ 8 đến 50 ký tự.</div>
                            </div>
                            <div class="col-12">
                                <label for="re_pass" class="form-label">Nhập lại mật khẩu <span class="text-danger">*</span></label>
                                <div class="input-group">
                  <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                      <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                    </svg>
                  </span>
                                    <input type="password" class="form-control" name="re_pass" id="re_pass" required>
                                    <span class="input-group-text" id="togglePassword2" style="cursor: pointer;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                    <path d="M8 3C4.5 3 1.5 6 0 8c1.5 2 4.5 5 8 5s6.5-3 8-5c-1.5-2-4.5-5-8-5zM1.5 8C2.6 9.4 5 12 8 12s5.4-2.6 6.5-4c-1.1-1.4-3.5-4-6.5-4S2.6 6.6 1.5 8z"/>
                    <path d="M8 10.5a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5z"/>
                    <path d="M8 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg>
            </span>
                                </div>
                                <div id="re_pass-error" class="text-danger" style="display: none;">Mật khẩu không khớp.</div>
                            </div>
                            <input type="text" value="${requestScope.userId}" name="id" hidden="hidden"/>
                            <div class="col-12">
                                <div class="d-grid">
                                    <button class="btn btn-primary btn-lg" type="submit" id="submitBtn">Reset Password</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // Hàm để chuyển đổi hiển thị mật khẩu
    function togglePasswordVisibility(inputId, toggleId) {
        const input = document.getElementById(inputId);
        const toggle = document.getElementById(toggleId);

        // Thay đổi loại input giữa 'password' và 'text'
        if (input.type === "password") {
            input.type = "text"; // Hiện mật khẩu
            toggle.innerHTML = `
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                    <path d="M8 3C4.5 3 1.5 6 0 8c1.5 2 4.5 5 8 5s6.5-3 8-5c-1.5-2-4.5-5-8-5zM1.5 8C2.6 9.4 5 12 8 12s5.4-2.6 6.5-4c-1.1-1.4-3.5-4-6.5-4S2.6 6.6 1.5 8z"/>
                    <path d="M8 10.5a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5z"/>
                    <path d="M8 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg>`;
        } else {
            input.type = "password"; // Ẩn mật khẩu
            toggle.innerHTML = `
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                    <path d="M8 3C4.5 3 1.5 6 0 8c1.5 2 4.5 5 8 5s6.5-3 8-5c-1.5-2-4.5-5-8-5zM1.5 8C2.6 9.4 5 12 8 12s5.4-2.6 6.5-4c-1.1-1.4-3.5-4-6.5-4S2.6 6.6 1.5 8z"/>
                    <path d="M8 10.5a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5z"/>
                    <path d="M8 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                </svg>`;
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
