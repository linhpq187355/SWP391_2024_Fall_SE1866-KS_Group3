<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/26/2024
  Time: 6:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bảo mật</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="ManhNC">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <link rel="stylesheet" href="assets/css/normalize.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/fontello.css">
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/wizard.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">

    <style>
        label{
            font-weight: bold;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="page-head">
        <div class="container">
            <div class="row">
                <div class="page-head-content">
                    <h1 class="page-title">Xin chào : <span class="orange strong">${requestScope.user.firstName} ${requestScope.user.lastName}</span></h1>
                </div>
            </div>
        </div>
    </div>
    <!-- End page header -->

    <!-- property area -->
    <div class="content-area user-profiel" style="background-color: #FCFCFC;">&nbsp;
        <div class="container">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1 profiel-container">


                        <div class="profiel-header">
                            <h3>
                                <b>Thông tin của bạn</b>
                            </h3>
                            <hr>

                        </div>

                        <div class="clear">
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

                            <div class="col-sm-3 col-sm-offset-1">
                                <div class="picture-container">
                                    <div class="picture">
                                        <c:if test="${empty requestScope.user.avatar}">
                                            <img src="assets/img/user-default-avatar.png" class="picture-src" id="wizardPicturePreview" title=""/>
                                        </c:if>
                                        <c:if test="${not empty requestScope.user.avatar}">
                                            <img src="${requestScope.user.avatar}" class="picture-src" id="wizardPicturePreview" title=""/>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 padding-top-25">
                                <div class="form-group">
                                    <label>Họ và tên</label>
                                    <p>${requestScope.user.firstName} ${requestScope.user.lastName}</p>
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <p>${requestScope.user.email}</p>
                                    <button onclick="window.location.href='user-update-email'" class='btn btn-finish btn-primary'>Cập nhật Email</button>
                                </div>
<%--                                <div class="form-group">--%>
<%--                                    <label>SĐT</label>--%>
<%--                                    <c:if test="${not empty requestScope.user.phoneNumber}">--%>
<%--                                        <p>${requestScope.user.phoneNumber}</p>--%>
<%--                                    </c:if>--%>
<%--                                    <c:if test="${empty requestScope.user.phoneNumber}">--%>
<%--                                        <p>Chưa cập nhật</p>--%>
<%--                                    </c:if>--%>
<%--                                    <button onclick="window.location.href='user-update-phone'" class='btn btn-finish btn-primary'>Cập nhật</button>--%>
<%--                                </div>--%>
                                <div class="form-group">
                                    <button onclick="window.location.href='user-update-password'" class='btn btn-finish btn-primary'>Cập nhật mật khẩu</button>
                                </div>
                            </div>
                        </div>
                        <br>


                </div>
            </div><!-- end row -->

        </div>
    </div>
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
        // Function to hide messages after 3 seconds
        setTimeout(function() {
            var successMessage = document.getElementById("successMessage");
            var errorMessage = document.getElementById("errorMessage");
            if (successMessage) {
                successMessage.style.display = "none"; // Hide success message
            }
            if (errorMessage) {
                errorMessage.style.display = "none"; // Hide error message
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    </script>
</body>
</html>
