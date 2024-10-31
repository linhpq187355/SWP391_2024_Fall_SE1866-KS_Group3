<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 26/09/2024
  Time: 08:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thông báo</title>
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<section class="py-3 py-md-5 min-vh-100 d-flex justify-content-center align-items-center">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="text-center">
                    <h3 class="h2 mb-2">Thông báo quan trọng!</h3>
                    <p class="mb-5">
                        <c:if test="${not empty notificationMessage}">
                            ${notificationMessage}
                        </c:if>
                    </p>
                    <form action="login.jsp" method="get">
                        <button type="submit" class="btn btn-dark rounded-pill px-5 fs-6 m-0">Đưa tôi về trang đăng nhập</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
