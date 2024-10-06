<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 30/09/2024
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Đăng nhập</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <script>
            function togglePasswordVisibility() {
                const passwordInput = document.getElementById('password');
                const eyeIcon = document.getElementById('eye-icon');
                if (passwordInput.type === 'password') {
                    passwordInput.type = 'text';
                    eyeIcon.classList.remove('fa-eye');
                    eyeIcon.classList.add('fa-eye-slash');
                } else {
                    passwordInput.type = 'password';
                    eyeIcon.classList.remove('fa-eye-slash');
                    eyeIcon.classList.add('fa-eye');
                }
            }
        </script>
    </head>
    <body class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="bg-white p-8 rounded-lg shadow-md w-96">
        <form action="staff-login" method="post" onsubmit="return validateForm()">
            <div id="error-message" class="mb-4 text-red-500 text-sm font-bold">
                <script>
                    // Simulating requestScope error message
                    const errorMessage = "${requestScope.error != null ? requestScope.error : ''}"; // Replace this with actual requestScope error message
                    if (errorMessage) {
                        document.getElementById('error-message').textContent = errorMessage;
                    }
                </script>
            </div>
            <div class="mb-4 relative">
                <label class="absolute -top-3 left-3 bg-white px-1 text-gray-700 text-sm font-bold" for="email">Email</label>
                <input name="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="email" type="email"
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required maxlength="100">
            </div>
            <div class="mb-6 relative">
                <label class="absolute -top-3 left-3 bg-white px-1 text-gray-700 text-sm font-bold" for="password">Password</label>
                <input name="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" required minlength="8" maxlength="50">
                <i id="eye-icon" class="fas fa-eye absolute right-3 top-3 text-gray-500 cursor-pointer" onclick="togglePasswordVisibility()"></i>
            </div>
            <div class="flex items-center justify-between">
                <button class="bg-gray-800 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
                    Login
                </button>
            </div>
            <div class="mt-4 text-center">
                <a class="inline-block align-baseline font-bold text-sm text-gray-500 hover:text-gray-800" href="login">
                    Bạn không phải nhân viên?
                </a>
            </div>
        </form>
    </div>
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
</script>
</body>
</html>
