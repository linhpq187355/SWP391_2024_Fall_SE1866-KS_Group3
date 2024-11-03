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
    <title>Nhập OTP</title>
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
                    <div class="panel-heading text-center">
                        <h3 class="text-muted">Nhập mã xác thực được gửi qua email của bạn.</h3>
                    </div>
                    <div class="panel-body">
                        <form action="verify-new" method="post" onsubmit="return validateForm()">
                            <c:if test="${requestScope.error != null}">
                                <div class="col-xs-12">
                                    <div class="alert alert-danger">
                                            ${requestScope.error}
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label for="otp">OTP <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" name="otp" id="otp" placeholder="Tên" required maxlength="6" minlength="6">
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary btn-block" type="submit">Xác nhận</button>
                            </div>
                        </form>
                        <hr>
                        <!-- Thêm liên kết Gửi lại OTP -->
                        <div class="text-center">
                            <p>Không nhận được OTP?</p>
                            <a href="resend-otp-new-email?userId=${sessionScope.userId}&email=${sessionScope.email}" class="btn btn-link"  id="resendOtpLink" style="pointer-events: none; opacity: 0.5;">Gửi lại OTP (<span id="countdown">60</span>s)</a>
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
    $(document).ready(function () {
        var countdownElement = document.getElementById('countdown');
        var resendOtpLink = document.getElementById('resendOtpLink');
        var countdownTime = 60; // Thời gian đếm ngược, 60 giây

        var countdownInterval = setInterval(function () {
            countdownTime--;
            countdownElement.textContent = countdownTime;

            if (countdownTime <= 0) {
                clearInterval(countdownInterval);
                // Kích hoạt lại liên kết "Gửi lại OTP"
                resendOtpLink.classList.remove('disabled');  // Loại bỏ lớp disabled
                resendOtpLink.style.pointerEvents = 'auto';  // Bật lại sự kiện click
                resendOtpLink.textContent = 'Gửi lại OTP';  // Thay đổi văn bản liên kết
            }
        }, 1000);
    });
    function validateForm() {
        let otp = document.getElementById("otp").value;
        // Check otp length
        if (otp.length !== 6) {
            Swal.fire({
                icon: 'error',
                title: 'OTP có 6 kí tự'
            });
            return false;
        }
        return true;
    }
</script>
</body>
</html>
