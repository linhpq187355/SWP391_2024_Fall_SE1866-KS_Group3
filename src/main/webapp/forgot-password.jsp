<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/29/2024
  Time: 3:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quên mật khẩu</title>

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
        <div class="row justify-content-md-center" style="display: flex; justify-content: center">
            <div class="col-12 col-md-11 col-lg-8 col-xl-7 col-xxl-6">
                <div class="bg-white p-4 p-md-5 rounded shadow-sm">
                    <div class="row gy-3 mb-5">
                        <div class="col-12">
                            <div class="text-center">
                                <h2>Quên mật khẩu</h2>
                            </div>
                        </div>
                        <div class="col-12" style="margin-bottom: 30px">
                            <h5 class="fs-6 fw-normal text-center text-secondary m-0 px-md-5">Vui lòng nhập email liên kết với tài khoản của bạn.</h5>
                            <c:if test="${not empty requestScope.message}">
                                <div class="col-md-12">
                                    <div class="alert alert-success" role="alert" id="successMessage">
                                            ${requestScope.message}
                                    </div>
                                </div>
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
                    <form action="forgot-password" method="post">
                        <div class="row gy-3 gy-md-4 overflow-hidden">
                            <div class="col-12" style="margin-bottom: 30px">
                                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                <div class="input-group" style="display: flex">
                  <span class="input-group-text" style="display: flex; align-items: center; margin-left: 10px">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
                      <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z" />
                    </svg>
                  </span>
                                    <input type="email" class="form-control" name="email" id="email" required style="margin-left: 20px; border-left: 1px solid #DADADA">
                                </div>
                            </div>
                            <div id="error-message" class="text-danger" style="display: none;"></div>
                            <div class="col-12" style="display: flex; justify-content: center;margin-bottom: 50px;}">
                                <div class="d-grid">
                                    <button class="btn btn-primary btn-lg" type="submit" onclick="validateEmail()">Reset Password</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    function validateEmail() {
        const emailInput = document.getElementById('email');
        const errorMessageDiv = document.getElementById('error-message');
        const email = emailInput.value.trim();

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailPattern.test(email)) {
            errorMessageDiv.textContent = 'Địa chỉ email không hợp lệ!';
            errorMessageDiv.style.display = 'block';
            emailInput.focus();
            return false;
        } else {
            errorMessageDiv.style.display = 'none';
        }
    }
</script>
</body>
</html>
