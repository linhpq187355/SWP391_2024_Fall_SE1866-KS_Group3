<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/25/2024
  Time: 10:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Submit your home</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="ThangLT">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">

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
    <link rel="stylesheet" href="assets/css/wizard.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
    <style>
        .chat-info{
            max-width: 250px; /* Đặt chiều rộng tối đa cho tin nhắn */
            overflow: hidden; /* Ẩn phần nội dung vượt quá chiều rộng */
            white-space: nowrap; /* Không xuống dòng */
            text-overflow: ellipsis; /* Thêm dấu ... cho nội dung cắt ngắn */
        }
        .notification-content {
            display: block; /* Đảm bảo mỗi phần tử hiển thị trong một khối riêng */
            max-width: 250px; /* Thay đổi giá trị này tùy thuộc vào chiều cao mong muốn */
            overflow: hidden; /* Ẩn phần nội dung không nằm trong khối */
            text-overflow: ellipsis; /* Thêm dấu "..." vào cuối nội dung nếu bị cắt */
            white-space: nowrap; /* Ngăn không cho nội dung xuống dòng */
        }
        .notification-title {
            font-size: 1.2em; /* Thay đổi kích thước phông chữ cho tiêu đề */
            font-weight: bold; /* Đặt tiêu đề thành in đậm */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-default ">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navigation">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="home-page" style=" display: contents"><img src="assets/img/logo-light.png"
                                                                                     alt="" style="max-width: 9em;"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse yamm" id="navigation">

            <c:if test="${not empty cookie.id}">
                <div class="button navbar-right"
                     style="padding-top: 1.5em; display: flex; justify-content: space-around; width: 15em">
                    <c:if test="${cookie.roleId.value == 3}">
                        <div class="dropdown ymm-sw">
                            <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                               class="fa-regular fa-heart dropdown-toggle" style="font-size: 2em"></i>
                            <div class="dropdown-menu navbar-nav" style="right: 20em; width: 27em; padding: 1rem 2rem">
                                <div class="dropdown-cart-action">
                                    <a href="user-wishlist" class="btn btn-primary">Xem wishlist</a>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <div class="dropdown ymm-sw" style="position: relative">
                        <c:if test="${requestScope.countNewMessage > 0}">
                            <span id = "new-message-count" style="width: 15px; height: 15px; position: absolute; left: 25px; bottom: 25px; color: #ffffff; font-size: 12px; font-weight: bold; line-height: 15px; text-align: center; border-radius: 7.5px; background-color: #FDC600;">${requestScope.countNewMessage}</span>
                        </c:if>
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-message dropdown-toggle" style="font-size: 2em"></i>
                        <div class="dropdown-menu navbar-nav" style="top: 4.4em; right: -3em; width: 23em">
                            <c:if test="${not empty requestScope.listUserConversation}">
                                <c:forEach items="${requestScope.listUserConversation.entrySet()}" var="entry">
                                    <c:set var="user" value="${entry.key}" /> <!-- Lấy User từ entry -->
                                    <c:set var="reply" value="${entry.value}" /> <!-- Lấy Reply từ entry -->

                                    <div class="chat-list">
                                        <figure class="chat-image-container">
                                            <a href="chat-box?userId=${user.id}" class="product-image">
                                                <img src="${user.avatar != null ? user.avatar : 'https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png'}"
                                                     alt="product">
                                            </a>
                                        </figure>
                                        <div class="chat-info">
                                            <h4 class="user-name">
                                                <a href="chat-box?userId=${user.id}">${user.firstName} ${user.lastName}</a>
                                            </h4>
                                            <span class="recent-chat" style="${reply.status == 'sent' ? 'font-weight: bold;' : ''}"> <%-- Add font-weight-bold class if status is 'sent' --%>
                                            <c:choose>
                                                <c:when test="${not empty reply.contentType}">
                                                    <c:if test="${reply.userId == userIdFromCookie}">
                                                        Bạn:
                                                    </c:if>
                                                    <c:choose>
                                                        <c:when test="${reply.contentType == 'image'}">
                                                            Đã gửi ảnh
                                                        </c:when>
                                                        <c:otherwise>
                                                            Đã gửi ${reply.contentType}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${reply.userId == userIdFromCookie}">
                                                        Bạn:
                                                    </c:if>
                                                    ${reply.text}
                                                </c:otherwise>
                                            </c:choose>
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>

                        </div>
                    </div>
                    <div class="dropdown ymm-sw" style="position: relative">
                        <c:if test="${unreadCount > 0}">
                            <span id = "new-notification-count" style="width: 15px; height: 15px; position: absolute; left: 25px; bottom: 25px; color: #ffffff; font-size: 12px; font-weight: bold; line-height: 15px; text-align: center; border-radius: 7.5px; background-color: #FDC600;">${unreadCount}</span>
                        </c:if>
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-bell dropdown-toggle" style="font-size: 2em"></i>
                        <!-- Dropdown danh sách thông báo -->
                        <ul class="dropdown-menu navbar-nav" style="top: 4.4em; right: -1.5em; width: 27em; padding-bottom: 1em">
                            <c:forEach var="notification" items="${notifications}">
                                <li>
                                    <a href="notification?type=${notification.type}" class="li-no" style="<c:if test='${notification.status == "sent"}'>font-weight: bold;</c:if>">
                                        <span class="notification-title">${notification.title}</span><br>
                                        <span class="notification-content">${notification.content}</span>
                                    </a>
                                </li>
                            </c:forEach>
                            <c:if test="${notifications.isEmpty()}">
                                <li><a class="li-no">Không có thông báo mới</a></li>
                            </c:if>
                            <li><a href="notification" class="li-no" style="font-weight: normal;">Tất cả thông báo</a></li>
                        </ul>
                    </div>
                    <div class="dropdown ymm-sw">
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-user dropdown-toggle" style="font-size: 2em"></i>
                        <ul class="dropdown-menu navbar-nav" style="right: 12em">
                            <li>
                                <a href="user-profile" class="li-acc-op">Thông tin cá nhân</a>
                            </li>
                       <c:if test="${cookie.roleId.value ==3}">
                            <li>
                                <a href="user-wishlist" class="li-acc-op">Danh sách yêu thích</a>
                            </li>
                       </c:if>
                            <c:if test="${cookie.roleId.value ==4}">
                                <li>
                                    <a href="user-wishlist" class="li-acc-op">Quản lý yêu thích</a>
                                </li>
                            </c:if>
                            <li>
                                <a href="user-blog" class="li-acc-op">Quản lí Blog</a>
                            </li>
                            <li>
                                <a href="user-security" class="li-acc-op">Mật khẩu và bảo mật</a>
                            </li>
                            <c:if test="${cookie.roleId.value == 4}">
                                <c:if test="${requestScope.preference.cleanliness != 100}">
                                    <li>
                                        <a href="update-matching-profile" class="li-acc-op">Thay đổi thông tin ghép nối</a>
                                    </li>
                                </c:if>
                                <c:if test="${requestScope.preference.cleanliness == 100}">
                                    <li>
                                        <a href="matching" class="li-acc-op">Cài đặt thông tin ghép nối</a>
                                    </li>
                                </c:if>
                            </c:if>
                            <c:if test="${cookie.roleId.value ==4}">
                                <li>
                                    <a href="my-home" class="li-acc-op">Quản lí danh sách nhà</a>
                                </li>
                            </c:if>
                            <c:if test="${cookie.roleId.value ==4}">
                                <li>
                                    <a href="appointment-host-manage" class="li-acc-op">Quản lí lịch hẹn</a>
                                </li>
                            </c:if>
                            <c:if test="${cookie.roleId.value ==3}">
                                <li>
                                    <a href="appointment-tenant-list" class="li-acc-op">Lịch hẹn của tôi</a>
                                </li>
                            </c:if>
                            <c:if test="${cookie.roleId.value ==1}">
                                <li>
                                    <a href="dashboard" class="li-acc-op">Dashboard</a>
                                </li>
                            </c:if>

                            <li>
                                <a href="logout" class="li-acc-op">Đăng xuất</a>
                            </li>

                        </ul>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty cookie.id}">
                <div class="button navbar-right">
                    <button class="navbar-btn nav-button wow bounceInRight login"
                            onclick=" window.location.href='login'" data-wow-delay="0.4s">Đăng nhập
                    </button>
                    <button class="navbar-btn nav-button wow fadeInRight" onclick=" window.location.href='signup'"
                            data-wow-dela   y="0.5s">Đăng kí
                    </button>
                </div>
            </c:if>

            <ul class="main-nav nav navbar-nav navbar-right">
                <li class="dropdown ymm-sw " data-wow-delay="0.1s">
                    <a href="home-page">Trang chủ</a>
                </li>
                <c:if test="${cookie.roleId.value != 0}">
                    <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="view-blog">Diễn Đàn</a></li>
                </c:if>
                <c:if test="${cookie.roleId.value ==3}">
                    <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="user-list">Tìm bạn phù hợp</a>
                    </li>
                </c:if>
                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="home-list">Tất cả nhà</a></li>
                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="terms.jsp">Trợ giúp</a></li>
<%--                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="about-us.jsp">Về chúng tôi</a></li>--%>
                <c:if test="${cookie.roleId.value ==4}">
                    <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="submit-home">Đăng bài</a></li>
                </c:if>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<!-- End of nav bar -->
<script src="assets/js/modernizr-2.6.2.min.js"></script>
<script src="assets/js/core/jquery-3.7.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-select.min.js"></script>
<script src="assets/js/bootstrap-hover-dropdown.js"></script>
<script src="assets/js/easypiechart.min.js"></script>
<script src="assets/js/jquery.easypiechart.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/wow.js"></script>
<script src="assets/js/icheck.min.js"></script>
<script src="assets/js/price-range.js"></script>
<script src="assets/js/main.js"></script>
</body>
</html>
