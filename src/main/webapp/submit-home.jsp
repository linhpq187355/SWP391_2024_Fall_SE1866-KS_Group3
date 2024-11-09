<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  User: ThangLT
  Date: 9/25/2024
  Time: 10:32 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Đăng tin nhà</title>
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
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link href="css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="assets/css/upload-img.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/wizard.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <link href="assets/dist/imageuploadify.min.css" rel="stylesheet">
    <br type="_moz">
    <style>
        #map {
            width: 100%;
            height: 60%;
        }

        img {
            max-width: 50%;
        }

        label {
            color: darkgrey;
        }

        .preview-image {
            max-width: 200px;
            max-height: 200px;
            margin: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 5px;
        }
    </style>
</head>
<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->

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
            <a class="navbar-brand" href="home-page" style=" display: contents"><img src="assets/img/logo-web.jpg"
                                                                                     alt="" style="max-width: 4em;"></a>
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
                                                <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png"
                                                     alt="product">
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
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-message dropdown-toggle" style="font-size: 2em"></i>
                        <div class="dropdown-menu navbar-nav" style="top: 4.4em; right: -3em; width: 23em">
                            <div class="chat-list">
                                <figure class="chat-image-container">
                                    <a href="product.html" class="product-image">
                                        <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png"
                                             alt="product">
                                    </a>
                                </figure>
                                <div class="chat-info">
                                    <h4 class="user-name">
                                        <a href="product.html">Pham Quang Linh</a>
                                    </h4>
                                    <span class="recent-chat">
                                             Ok chốt vậy nhé
                                        </span>
                                </div>
                            </div>


                            <div class="chat-list">
                                <figure class="chat-image-container">
                                    <a href="product.html" class="product-image">
                                        <img src="https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png"
                                             alt="product">
                                    </a>
                                </figure>
                                <div class="chat-info">
                                    <h4 class="user-name">
                                        <a href="product.html">Pham Quang Linh</a>
                                    </h4>
                                    <span class="recent-chat">
                                             Bạn đã tìm được người chưa?
                                        </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="dropdown ymm-sw" style="position: relative">
                        <div style="border-radius: 5px;width: 10px;height: 10px;position: absolute;background-color: #FDC600;left: 20px;bottom: 22px;"></div>
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-bell dropdown-toggle" style="font-size: 2em"></i>
                        <ul class="dropdown-menu navbar-nav"
                            style="top: 4.4em; right: -1.5em; width: 27em; padding-bottom: 1em">
                            <li>
                                <a href="index-2.html" class="li-no">Thông tin nhà của bạn đã được xác thực</a>
                            </li>
                            <li>
                                <a href="index-3.html" class="li-no">ABC đã bình luận trên bài viết của bạn
                                    shcakshka</a>
                            </li>
                        </ul>
                    </div>
                    <div class="dropdown ymm-sw">
                        <i data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="fa-regular fa-user dropdown-toggle" style="font-size: 2em"></i>
                        <ul class="dropdown-menu navbar-nav" style="right: 12em">
                            <li>
                                <a href="user-profile" class="li-acc-op">Thông tin cá nhân</a>
                            </li>
                            <li>
                                <a href="user-security" class="li-acc-op">Mật khẩu và bảo mật</a>
                            </li>
                            <c:if test="${cookie.roleId.value ==4}">
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
                    <button class="navbar-btn nav-button wow bounceInRight login"
                            onclick=" window.location.href='login'" data-wow-delay="0.4s">Đăng nhập
                    </button>
                    <button class="navbar-btn nav-button wow fadeInRight" onclick=" window.location.href='signup'"
                            data-wow-delay="0.5s">Đăng kí
                    </button>
                </div>
            </c:if>

            <ul class="main-nav nav navbar-nav navbar-right">
                <li class="dropdown ymm-sw " data-wow-delay="0.1s">
                    <a href="home-page">Trang chủ</a>
                </li>

                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="home-page">Danh sách nhà</a></li>
                <%--                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="terms.jsp">Trợ giúp</a></li>--%>
                <%--                <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="about-us.jsp">Về chúng tôi</a></li>--%>
                <c:if test="${cookie.roleId.value ==4}">
                    <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="submit-home">Đăng bài</a></li>
                    <li class="wow fadeInDown" data-wow-delay="0.1s"><a class="" href="my-home">Bài đăng của tôi</a>
                    </li>
                </c:if>

            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title">Đăng tin ở ghép</h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<!-- property area -->
<div class="content-area submit-property" style="background-color: #FCFCFC;">&nbsp;
    <div class="container">
        <div class="clearfix">
            <div class="wizard-container">
                <div class="wizard-card ct-wizard-orange" id="wizardProperty">
                    <form action="submit-home" method="post" enctype="multipart/form-data">
                        <ul>
                            <li><a href="#step1" data-toggle="tab">Bước 1 </a></li>
                            <li><a href="#step2" data-toggle="tab">Bước 2 </a></li>
                            <li><a href="#step3" data-toggle="tab">Bước 3 </a></li>
                            <li><a href="#step4" data-toggle="tab">Hoàn tất </a></li>
                        </ul>

                        <div class="tab-content">
                            <div class="tab-pane" id="step1">
                                <div class="row p-b-15 ">
                                    <h4 class="info-text"> THÔNG TIN CƠ BẢN</h4>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Loại hình nhà ở <small>(*)</small></label><br>
                                            <select name="home-type" id="home-type" class="form-control"
                                                    required>
                                                <option value="" selected>Chọn loại hình nhà ở</option>
                                                <c:forEach var="hometype" items="${homeTypes}">
                                                    <option value="${hometype.id}">${hometype.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Tên nơi ở của bạn <small>(*)</small></label>
                                            <input name="home-name" type="text" class="form-control" required>
                                        </div>
                                    </div>
                                    <div class="col-sm-4">
                                        <div class="form-group">
                                            <select name="province" id="province"
                                                    class="form-select form-select-sm mb-3"
                                                    data-live-search="true" data-live-search-style="begins"
                                                    title="Chọn tỉnh thành"
                                                    style="width: 100%;height: 40px;">
                                                <option value="" selected>Chọn tỉnh thành</option>
                                                <c:forEach var="province" items="${provinces}">
                                                    <option value="${province.id}">${province.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-4">
                                        <div class="form-group">
                                            <select class="form-select form-select-sm mb-3"
                                                    data-live-search="true" data-live-search-style="begins"
                                                    name="district"
                                                    id="district"
                                                    aria-label=".form-select-sm"
                                                    style="width: 100%;height: 40px;">
                                                <option value="" selected>Chọn quận huyện</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-4">
                                        <div class="form-group">
                                            <select class="form-select form-select-sm mb-3"
                                                    data-live-search="true" data-live-search-style="begins"
                                                    name="ward"
                                                    id="ward"
                                                    aria-label=".form-select-sm"
                                                    style="width: 100%;height: 40px;" title="Chọn phường xã">
                                                <option value="" selected>Chọn phường xã</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-10">
                                        <div class="form-group">
                                            <label>Địa chỉ cụ thể <small>(*)</small></label>
                                            <input name="address-detail" id="address-detail" type="text"
                                                   class="form-control"
                                                   value="${home.address}" required>
                                        </div>
                                    </div>
                                    <div class="col-sm-2">
                                        <div class="form-group">
                                            <button class="btn btn-danger" id="searchAddress"
                                                    style="width: 100%;margin-top: 35px;">
                                                <i class="fa-solid fa-magnifying-glass" style="color: #ff0000;"></i> Tìm
                                                kiếm
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Vui lòng chọn vị trí trên bản đồ</label>
                                            <div id="map"></div>
                                            <%--                                            <div id="selected-location"></div>--%>
                                            <input type="hidden" name="latitude" id="latitude" value="0">
                                            <input type="hidden" name="longitude" id="longitude" value="0">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--  End step 1 -->

                            <div class="tab-pane" id="step2">
                                <h4 class="info-text">THÔNG TIN NƠI Ở</h4>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label>Mô tả về nơi ở của bạn <small>(*)</small></label>
                                                <textarea name="home-description" class="form-control"
                                                          required></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label>Mô tả về người bạn muốn ở ghép <small>(*)</small></label>
                                                <textarea name="tenant-description" class="form-control"
                                                          required></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Diện tích nơi ở (mét vuông) <small>(*)</small></label>
                                                <input type="number" class="form-control" id="area" name="area"
                                                       step="0.01" min="10" max="1000" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Hướng nhà :</label>
                                                <select id="direction" name="direction" class="selectpicker"
                                                        data-live-search="true"
                                                        data-live-search-style="begins" title="Chọn hướng nhà">
                                                    <option>Hướng Đông</option>
                                                    <option>Hướng Tây</option>
                                                    <option>Hướng Nam</option>
                                                    <option>Hướng Bắc</option>
                                                    <option>Hướng Tây Bắc</option>
                                                    <option>Hướng Đông Bắc</option>
                                                    <option>Hướng Đông Nam</option>
                                                    <option>Hướng Tây Nam</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Thời gian cho ở ghép <small>(*)</small></label>
                                                <input type="number" class="form-control" id="leaseDuration"
                                                       name="leaseDuration" placeholder="Chọn thời gian theo tháng"
                                                       min="1" max="12" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Ngày chuyển đến <small>(*)</small></label>
                                                <input type="date" id="moveInDate" name="moveInDate" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 padding-top-15">
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Số phòng ngủ <small>(*)</small></label>
                                                <input type="number" class="form-control" id="numOfBedroom"
                                                       name="numOfBedroom" min="1" max="10" required/>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Số nhà vệ sinh <small>(*)</small></label>
                                                <input type="number" class="form-control" id="numOfBath"
                                                       name="numOfBath" min="1" max="10" required/>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Mức giá theo tháng (VND) <small>(*)</small></label>
                                                <input type="number" class="form-control" id="price" name="price"
                                                       min="1000000" step="20000000" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 padding-top-15">
                                        <h5 style="padding-left: 15px;">Tiện ích nơi ở: </h5>
                                        <c:forEach var="amentity" items="${amentities}">
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <div class="checkbox">
                                                        <label>
                                                            <input type="checkbox" name="amentityIds"
                                                                   value="${amentity.id}"> ${amentity.name}
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <br>
                                    <div class="col-sm-12 padding-top-15">
                                        <h5 style="padding-left: 15px;">Phòng cháy chữa cháy: </h5>
                                        <c:forEach var="fireEquip" items="${fireEquipments}">
                                            <div class="col-sm-3">
                                                <div class="form-group">
                                                    <div class="checkbox">
                                                        <label>
                                                            <input type="checkbox" name="fireEquipIds"
                                                                   value="${fireEquip.id}"> ${fireEquip.name}
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <!-- End step 2 -->

                            <div class="tab-pane" id="step3">
                                <h4 class="info-text">HÌNH ẢNH</h4>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="container" style="margin-top: 30px;">
                                            <label for="imageUpload">Chọn hình ảnh <small>(có thể chọn nhiều
                                                ảnh)</small></label>
                                            <input type="file" id="imageUpload" name="images" accept="image/*" multiple>
                                            <div id="imagePreview" class="mt-3"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--  End step 3 -->


                            <div class="tab-pane" id="step4">
                                <h4 class="info-text"> Điều Khoản và Điều Kiện </h4>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="">
                                            <p>
                                                Website Roommify là một nền tảng kết nối những người đang có
                                                nhu cầu tìm kiếm roommate. Sử dụng website này đồng nghĩa với việc
                                                bạn đồng ý tuân thủ các điều khoản và điều kiện của chúng tôi.
                                            </p>
                                            <div>
                                                <label>
                                                    <input type="checkbox"/> <strong>Tôi đồng ý các điều khoản
                                                    và điều kiện.</strong>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--  End step 4 -->
                        </div>

                        <div class="wizard-footer">
                            <div class="pull-right">
                                <input type='button' class='btn btn-next btn-primary' name='next' value='Tiếp'/>
                                <input type='submit' class='btn btn-finish btn-primary' name='finish' value='Đăng tin'/>
                            </div>

                            <div class="pull-left">
                                <input type='button' class='btn btn-previous btn-default' name='previous'
                                       value='Quay về'/>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </form>
                </div>
                <!-- End submit form -->
            </div>
        </div>
    </div>
</div>

<script src="assets/js/vendor/modernizr-2.6.2.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="assets/js//jquery-1.10.2.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-select.min.js"></script>
<script src="assets/js/bootstrap-hover-dropdown.js"></script>
<script src="assets/js/easypiechart.min.js"></script>
<script src="assets/js/jquery.easypiechart.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/wow.js"></script>
<script src="assets/js/icheck.min.js"></script>
<script src="assets/js/upload-img.js"></script>
<script src="assets/js/price-range.js"></script>
<script src="assets/js/jquery.bootstrap.wizard.js" type="text/javascript"></script>
<script src="assets/js/jquery.validate.min.js"></script>
<script src="assets/js/wizard.js"></script>
<script src="assets/js/main.js"></script>
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
        integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
<script src="assets/js/map.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
<script type="text/javascript" src="assets/dist/imageuploadify.min.js"></script>
<script src="assets/js/img-preview.js"></script>
<script src="assets/js/submisson-form-validation.js"></script>
</body>
</html>
