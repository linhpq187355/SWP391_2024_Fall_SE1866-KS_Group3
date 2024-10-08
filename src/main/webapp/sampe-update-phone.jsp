<%-- Created by IntelliJ IDEA.
  User: LNV
  Date: 22/09/2024
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <title>Cập nhật SĐT</title>
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
                        <h3 class="text-muted">Cập nhật số điện thoại của bạn.</h3>
                    </div>
                    <div class="panel-body">
                        <form id="phone-form" onsubmit="return sendOTP();">
                            <c:if test="${requestScope.error != null}">
                                <div class="col-xs-12">
                                    <div class="alert alert-danger">
                                            ${requestScope.error}
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label for="phone">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" class="form-control" name="phone" id="phone" placeholder="Nhập số điện thoại" required>
                                <small class="error-message" id="phone-error" style="display:none;">Số điện thoại không hợp lệ. Hãy nhập số điện thoại Việt Nam hợp lệ (10 chữ số, bắt đầu bằng 0).</small>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary btn-block" type="submit">Xác nhận</button>
                            </div>
                        </form>
                        <hr>
                        <!-- Form xác thực mã OTP -->
                        <div class="form-group" id="otp-container" style="display:none;">
                            <label for="otp">Nhập mã OTP <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" name="otp" id="otp" placeholder="Nhập mã OTP" required>
                            <div class="form-group">
                                <button class="btn btn-success btn-block" onclick="verifyOTP()">Xác thực OTP</button>
                            </div>
                        </div>

                        <!-- Form ẩn để gửi userId và phone về servlet sau khi xác thực OTP thành công -->
                        <form id="otp-success-form" action="update-phone" method="post" style="display: none;">
                            <input type="hidden" name="userId" id="hidden-userId" value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : "" %>">
                            <input type="hidden" name="phone" id="hidden-phone" value="">
                        </form>

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
        session.removeAttribute("message");
        session.removeAttribute("messageType");
    }
%>

<script type="module">
    import { initializeApp } from "https://www.gstatic.com/firebasejs/10.14.0/firebase-app.js";
    import { getAuth, RecaptchaVerifier, signInWithPhoneNumber } from "https://www.gstatic.com/firebasejs/10.14.0/firebase-auth.js";

    const firebaseConfig = {
        apiKey: "AIzaSyBF8pie6GFUoeS9u-wnyjPJS3729cfQLsU",
        authDomain: "home-sharing-3c037.firebaseapp.com",
        projectId: "home-sharing-3c037",
        storageBucket: "home-sharing-3c037.appspot.com",
        messagingSenderId: "590597565624",
        appId: "1:590597565624:web:06541a684771e2ec1a984d",
        measurementId: "G-F21HLF58FZ"
    };

    const app = initializeApp(firebaseConfig);
    const auth = getAuth(app);

    async function sendOTP() {
        let phone = document.getElementById("phone").value;
        let phoneRegex = /^(0[3|5|7|8|9])+([0-9]{8})$/;

        if (!phoneRegex.test(phone)) {
            document.getElementById("phone-error").style.display = "block";
            return false;
        } else {
            document.getElementById("phone-error").style.display = "none";
        }

        phone = '+84' + phone.slice(1);

        try {
            const appVerifier = new RecaptchaVerifier('phone-form', {
                size: 'invisible',
                callback: (response) => {
                    console.log('Recaptcha completed!');
                }
            }, auth);

            const confirmationResult = await signInWithPhoneNumber(auth, phone, appVerifier);
            window.confirmationResult = confirmationResult;

            Swal.fire({
                icon: 'success',
                title: 'Mã OTP đã được gửi!',
                text: 'Vui lòng kiểm tra số điện thoại của bạn.'
            });
            document.getElementById("otp-container").style.display = "block";

        } catch (error) {
            Swal.fire({
                icon: 'error',
                title: 'Gửi OTP thất bại',
                text: 'Vui lòng thử lại.'
            });
        }
        return false;
    }

    async function verifyOTP() {
        const maxAttempts = 3;
        let otpAttempts = parseInt('<%= session.getAttribute("otpAttempts") %>');

        if (otpAttempts >= maxAttempts) {
            Swal.fire({
                icon: 'error',
                title: 'Bạn đã nhập quá số lần OTP cho phép!',
                text: 'Vui lòng thử lại sau hoặc liên hệ hỗ trợ.'
            });
            return false;
        }

        const code = document.getElementById("otp").value;

        try {
            const result = await confirmationResult.confirm(code);
            const user = result.user;

            const phone = document.getElementById("phone").value;
            document.getElementById("hidden-phone").value = phone;

            <%
                session.setAttribute("otpAttempts", 0);
            %>

            document.getElementById("otp-success-form").submit();

        } catch (error) {
            otpAttempts++;
            fetch("updateOtpAttempts", {
                method: "POST",
                body: JSON.stringify({ attempts: otpAttempts }),
                headers: {
                    "Content-Type": "application/json"
                }
            });

            Swal.fire({
                icon: 'error',
                title: 'Xác thực OTP thất bại',
                text: 'Mã OTP không đúng. Bạn còn ' + (maxAttempts - otpAttempts) + ' lần thử.'
            });
        }
    }
</script>

</body>
</html>
