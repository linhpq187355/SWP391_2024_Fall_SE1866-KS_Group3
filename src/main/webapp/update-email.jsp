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
    <title>Đổi mail</title>
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
                        <h3 class="text-muted">Nhập email mới của bạn</h3>
                        <p class="text-muted">Sau khi thay đổi, mã xác thực sẽ được gửi về email mới của bạn.</p>
                    </div>
                    <div class="panel-body">
                        <form action="user-update-email" method="post" onsubmit="return validateForm()">
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
                                <label for="old-email">Email cũ<span class="text-danger">*</span></label>
                                <input type="email" class="form-control" name="old-email" id="old-email" placeholder="name@example.com"
                                       value="${sessionScope.oldEmail}" readonly maxlength="100">
                            </div>
                            <div class="form-group">
                                <label for="email">Email mới<span class="text-danger">*</span></label>
                                <input type="email" class="form-control" name="email" id="email" placeholder="name@example.com" required maxlength="100">
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
        let email = document.getElementById("email").value.trim();
        let oldEmail = document.getElementById("old-email").value.trim();
        // Check for required fields and ensure they're not just spaces
        if (email === "") {
            alert("Tất cả các trường là bắt buộc và không thể để trống hoặc chỉ chứa khoảng trắng.");
            return false;
        }
        // Validate email
        let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("Vui lòng nhập một địa chỉ email hợp lệ!");
            return false;
        }

        // Check if passwords match
        if (email === oldEmail) {
            alert("Email mới phải khác email cũ!");
            return false;
        }

        document.getElementById("email").value = email;

        return true;
    }
</script>
</body>
</html>
