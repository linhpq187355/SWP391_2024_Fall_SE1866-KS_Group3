<%--
  Created by IntelliJ IDEA.
  User: LNV
  Date: 25/09/2024
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="https://unpkg.com/bs-brain@2.0.4/components/error-404s/error-404-1/assets/css/error-404-1.css">
</head>
<body>
<!-- Error 404 Template 1 - Bootstrap Brain Component -->
<section class="py-3 py-md-5 min-vh-100 d-flex justify-content-center align-items-center">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="text-center">
                    <h2 class="d-flex justify-content-center align-items-center gap-2 mb-4">
                        <span class="display-1 fw-bold">4</span>
                        <i class="bi bi-exclamation-circle-fill text-danger display-4"></i>
                        <span class="display-1 fw-bold bsb-flip-h">4</span>
                    </h2>
                    <h3 class="h2 mb-2">Oops! You're lost.</h3>
                    <p class="mb-5">The page you are looking for was not found.</p>
                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-danger">${requestScope.error}</div>
                    </c:if>
                    <a class="btn bsb-btn-5xl btn-dark rounded-pill px-5 fs-6 m-0" href="#!" role="button">Back to Home</a>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>