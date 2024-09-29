
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
  <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="header-connect">
  <div class="container">
    <div class="row">
      <div class="col-md-5 col-sm-8  col-xs-12">
        <div class="header-half header-call">
          <p>
            <span><i class="pe-7s-call"></i> 0989843097 </span>
            <span><i class="pe-7s-mail"></i> rommifyy@gmail.com</span>
          </p>
        </div>
      </div>
      <div class="col-md-2 col-md-offset-5  col-sm-3 col-sm-offset-1  col-xs-12">
        <div class="header-half header-social">
          <ul class="list-inline">
            <li><a href="#"><i class="fa fa-facebook"></i></a></li>
            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
            <li><a href="#"><i class="fa fa-vine"></i></a></li>
            <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
            <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
            <li><a href="#"><i class="fa fa-instagram"></i></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<!--End top header -->

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
      <a class="navbar-brand" href="index.html" style=" display: contents"><img src="assets/img/logo-web.jpg" alt="" style="max-width: 4em;"></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse yamm" id="navigation">

      <c:if test="${not empty cookie.id}">
        <div class="button navbar-right" style="padding-top: 1.5em; display: flex; justify-content: space-around; width: 15em">
          <c:if test="${cookie.roleId.value == 4}">
            <div class="dropdown ymm-sw">
              <i data-toggle="dropdown" data-hover="dropdown" data-delay="200" class="fa-regular fa-heart dropdown-toggle" style="font-size: 2em"></i>
              <div class="dropdown-menu navbar-nav" style="right: 20em; width: 27em; padding: 1rem 2rem">
                <div class="dropdown-cart-products">
                  <div class="product">
                    <div class="product-cart-details">
                      <h4 class="product-title">
                        <a href="product.html">ok</a>
                      </h4>
                      <span class="cart-product-info">
                                              <span class="cart-product-qty">12</span>
                                                okok
                                        </span>
                    </div><!-- End .product-cart-details -->

                    <figure class="product-image-container">
                      <a href="product.html" class="product-image">
                        <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png" alt="product">
                      </a>
                    </figure>
                  </div>
                </div>
                <div class="dropdown-cart-action">
                  <a href="showcart" class="btn btn-primary">Xem wishlist</a>
                </div>
              </div>
            </div>
          </c:if>

          <div class="dropdown ymm-sw" style="position: relative">
            <div style="border-radius: 5px;width: 10px;height: 10px;position: absolute;background-color: #FDC600;left: 20px;bottom: 22px;"></div>
            <i data-toggle="dropdown" data-hover="dropdown" data-delay="200" class="fa-regular fa-message dropdown-toggle" style="font-size: 2em"></i>
            <div class="dropdown-menu navbar-nav" style="top: 4.4em; right: -3em; width: 23em">
              <div class="chat-list">
                <figure class="chat-image-container">
                  <a href="product.html" class="product-image">
                    <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png" alt="product">
                  </a>
                </figure>
                <div class="chat-info">
                  <h4 class="user-name">
                    <a href="product.html">Pham Quang Linh</a>
                  </h4>
                  <span class="recent-chat">
                                             qua la ok la
                                        </span>
                </div>
              </div>


              <div class="chat-list">
                <figure class="chat-image-container">
                  <a href="product.html" class="product-image">
                    <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png" alt="product">
                  </a>
                </figure>
                <div class="chat-info">
                  <h4 class="user-name">
                    <a href="product.html">Pham Quang Linh</a>
                  </h4>
                  <span class="recent-chat">
                                             qua la ok la
                                        </span>
                </div>
              </div>


            </div>
          </div>
          <div class="dropdown ymm-sw" style="position: relative">
            <div style="border-radius: 5px;width: 10px;height: 10px;position: absolute;background-color: #FDC600;left: 20px;bottom: 22px;"></div>
            <i data-toggle="dropdown" data-hover="dropdown" data-delay="200" class="fa-regular fa-bell dropdown-toggle" style="font-size: 2em"></i>
            <ul class="dropdown-menu navbar-nav" style="top: 4.4em; right: -1.5em; width: 27em; padding-bottom: 1em">
              <li>
                <a href="index-2.html" class="li-no">Thông tin nhà của bạn đã được xác thực</a>
              </li>
              <li>
                <a href="index-3.html" class="li-no">ABC đã bình luận trên bài viết của bạn shcakshka</a>
              </li>
            </ul>
          </div>
          <div class="dropdown ymm-sw">
            <i data-toggle="dropdown" data-hover="dropdown" data-delay="200" class="fa-regular fa-user dropdown-toggle" style="font-size: 2em"></i>
            <ul class="dropdown-menu navbar-nav" style="right: 12em">
              <li>
                <a href="user-profile" class="li-acc-op">Thông tin cá nhân</a>
              </li>
              <c:if test="${cookie.roleId.value ==3}">
                <li>
                  <a href="index-3.html" class="li-acc-op">Quản lí danh sách nhà</a>
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
          <button class="navbar-btn nav-button wow bounceInRight login" onclick=" window.location.href='login'" data-wow-delay="0.4s">Đăng nhập</button>
          <button class="navbar-btn nav-button wow fadeInRight" onclick=" window.location.href='signup'" data-wow-delay="0.5s">Đăng kí</button>
        </div>
      </c:if>

      <ul class="main-nav nav navbar-nav navbar-right">
        <li class="dropdown ymm-sw " data-wow-delay="0.1s">
          <a href="home.jsp">Trang chủ</a>
        </li>

        <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="properties.html">Tất cả nhà</a></li>
        <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="properties.html">Trợ giúp</a></li>
        <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="properties.html">Về chúng tôi</a></li>

      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<!-- End of nav bar -->
<script src="assets/js/modernizr-2.6.2.min.js"></script>
<script src="assets/js/jquery-1.10.2.min.js"></script>
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
