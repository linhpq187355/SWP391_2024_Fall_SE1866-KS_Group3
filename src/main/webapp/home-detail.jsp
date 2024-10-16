<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GARO ESTATE | Property page</title>
    <meta name="description" content="company is a real-estate template">
    <meta name="author" content="Kimarotec">
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
    <link href="assets/css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/lightslider.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <style>
        .card {
            background-color: #7f7f7f;
            width: 300px;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .profile-img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            margin-bottom: 10px;
        }
        .name {
            font-size: 18px;
            color: #a0a0a0;
            margin-bottom: 20px;
        }
        .phone-button {
            background-color: #00c853;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            width: 100%;
            font-size: 16px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .phone-button i {
            margin-right: 10px;
        }
        .zalo-button, .like-button {
            background-color: white;
            color: black;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            font-size: 16px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .zalo-button i, .like-button i {
            margin-right: 10px;
            }
        #map{
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<jsp:include page="header.jsp"/>

<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->


<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title"></h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<!-- property area -->

<div class="content-area single-property" style="background-color: #FCFCFC;">&nbsp;
    <div class="container">
        <div class="clearfix padding-top-40">
            <div class="col-md-8 single-property-content prp-style-1">
                <div class="row">
                    <div class="light-slide-item">
                        <div class="clearfix">

                            <ul id="image-gallery" class="gallery list-unstyled cS-hidden">
                                <li data-thumb="assets/img/property-1/property1.jpg">
                                    <img src="assets/img/property-1/property1.jpg"/>
                                </li>
                                <li data-thumb="assets/img/property-1/property2.jpg">
                                    <img src="assets/img/property-1/property2.jpg"/>
                                </li>
                                <li data-thumb="assets/img/property-1/property3.jpg">
                                    <img src="assets/img/property-1/property3.jpg"/>
                                </li>
                                <li data-thumb="assets/img/property-1/property4.jpg">
                                    <img src="assets/img/property-1/property4.jpg"/>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="single-property-wrapper">
                    <div class="single-property-header">
                        <h1 class="property-title pull-left">${home.name}</h1>
                        <span class="property-price pull-right"> Giá Thuê:  ${prices[0].price}VND/tháng</span>
                    </div>

                    <div class="property-meta entry-meta clearfix">

                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info-icon icon-tag">
                                <img src="assets/img/icon/sale-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label">Trạng thái</span>
                                <span class="property-info-value">${hometypes[0].status}</span>
                            </span>
                        </div>
                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info icon-area">
                                <img src="assets/img/icon/room-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label"> Loại Hình Nhà </span>
                                <span class="property-info-value">${hometypes[0].name}</span>
                            </span>
                        </div>
                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info icon-area">
                                <img src="assets/img/icon/room-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label"> Thời Gian Ở Ghép </span>
                                <span class="property-info-value">${home.leaseDuration}/tháng</span>
                            </span>
                        </div>
                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
    <span class="property-info icon-area">
        <img src="assets/img/icon/room-orange.png">
    </span>
                            <span class="property-info-entry">
        <span class="property-info-label">Thời Gian Chuyển Đến</span>
        <span class="property-info-value">${formattedMoveInDate}</span> <!-- Sử dụng formattedMoveInDate -->
    </span>
                        </div>

                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info icon-area">
                                <img src="assets/img/icon/room-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label">Diện tích</span>
                                <span class="property-info-value">${home.area} <b
                                        class="property-info-unit">Sq Ft</b></span>
                            </span>
                        </div>

                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info-icon icon-bed">
                                <img src="assets/img/icon/bed-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label">Phòng Ngủ</span>
                                <span class="property-info-value">${home.numOfBedroom}</span>
                            </span>
                        </div>

                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info-icon icon-bath">
                                <img src="assets/img/icon/os-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label">Phòng tắm</span>
                                <span class="property-info-value">${home.numOfBath}</span>
                            </span>
                        </div>
                        <c:if test="${not empty home.orientation}">
                            <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
        <span class="property-info-icon icon-bath">
            <img src="assets/img/icon/os-orange.png">
        </span>
                                <span class="property-info-entry">
            <span class="property-info-label">Hướng Nhà</span>
            <span class="property-info-value">${home.orientation}</span>
        </span>
                            </div>
                        </c:if>


                    </div>
                    <!-- .property-meta -->

                    <div class="section">
                        <h4 class="s-property-title">Giới Thiệu</h4>
                        <div class="s-property-content">
                            <p>${home.homeDescription != null ? home.homeDescription : "No description available."}</p>
                        </div>
                    </div>
                    <div class="section">
                        <h4 class="s-property-title">Địa Chỉ Cụ Thể</h4>
                        <div class="s-property-content">
                            <p>${home.address}</p>
                        </div>
                    </div>
                    <div class="section">
                        <h4 class="s-property-title">Mong muốn Bạn Cùng Phòng</h4>
                        <div class="s-property-content">
                            <p>${home.tenantDescription}</p>
                        </div>
                    </div>
                    <!-- End description area -->

                    <div class="section property-features">
                        <h4 class="s-property-title">Các tiện ích hiện có</h4>
                        <ul>
                            <c:forEach var="amenity" items="${amentities}">
                                <li>
                                    <a href="home-detail.jsp">${amenity.name}</a>
                                </li>                            </c:forEach>
                        </ul>
                    </div>

                    <div class="section property-features">
                        <h4 class="s-property-title">Phương tiện phòng cháy chữa cháy </h4>
                        <ul>
                            <c:forEach var="fireEquipments" items="${fireEquipments}">
                                <li>
                                    <a href="home-detail.jsp">${fireEquipments.name}</a>
                                </li>                            </c:forEach>
                        </ul>
                    </div>


                    <!-- End additional-details area  -->


                    <div class="section property-video">
                        <h4 class="s-property-title">Property Video</h4>
                        <div class="video-thumb">
                            <a class="video-popup" href="yout" title="Virtual Tour">
                                <img src="assets/img/property-video.jpg" class="img-responsive wp-post-image"
                                     alt="Exterior">
                            </a>
                        </div>
                    </div>
                    <!-- End video area  -->


                    <div class="section property-share">
                        <h4 class="s-property-title">Share width your friends </h4>
                        <div class="roperty-social">
                            <ul>
                                <li><a title="Share this on dribbble " href="#"><img
                                        src="assets/img/social_big/dribbble_grey.png"></a></li>
                                <li><a title="Share this on facebok " href="#"><img
                                        src="assets/img/social_big/facebook_grey.png"></a></li>
                                <li><a title="Share this on delicious " href="#"><img
                                        src="assets/img/social_big/delicious_grey.png"></a></li>
                                <li><a title="Share this on tumblr " href="#"><img
                                        src="assets/img/social_big/tumblr_grey.png"></a></li>
                                <li><a title="Share this on digg " href="#"><img
                                        src="assets/img/social_big/digg_grey.png"></a></li>
                                <li><a title="Share this on twitter " href="#"><img
                                        src="assets/img/social_big/twitter_grey.png"></a></li>
                                <li><a title="Share this on linkedin " href="#"><img
                                        src="assets/img/social_big/linkedin_grey.png"></a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- End video area  -->

                </div>
            </div>


            <div class="col-md-4 p0">
                <aside class="sidebar sidebar-property blog-asside-right">
                    <div class="dealer-widget">
                        <div class="dealer-content">
                            <div class="inner-wrapper">
                                <div class="clear">
                                    <div class="col-xs-4 col-sm-4 dealer-face">
                                        <a href="">
                                            <img src="assets/img/client-face1.png" class="img-circle">
                                        </a>
                                    </div>
                                    <div class="col-xs-8 col-sm-8 ">
                                        <h3 class="dealer-name">
                                            <a href="">${creator.firstName} ${creator.lastName}</a>
                                        </h3>

                                    </div>


                                <div class="clear">
                                    <ul class="dealer-contacts">
                                        <li>
                                            <button class="phone-button">
                                                <i class="fas fa-phone"></i>
                                                ${creator.phoneNumber}
                                            </button>
                                        </li>
                                        <li>
                                            <button class="zalo-button">
                                                <i class="fas fa-comment"></i>
                                                Liên Hệ
                                            </button>
                                        </li>
                                        <c:if test="${cookie.roleId.value ==3}">
                                        <li>
                                            <form action="add-wishlist" method="POST">
                                                <input type="hidden" name="homeId" value="${home.id}">
                                                    <button type="submit" class="like-button" >
                                                    <i class="fas fa-heart"></i> Yêu thích
                                                </button>
                                            </form>
                                        </li>
                                        </c:if>
                                    </ul>
                                    </div>
                                    <div>
                                        <c:if test="${not empty sessionScope.message}">
                                            <div class="alert alert-info">${sessionScope.message}</div>
                                            <%-- Xóa thông điệp sau khi hiển thị --%>
                                            <c:remove var="message" />
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>



                    <!-- Success Modal -->
                    <!-- Success Modal -->
                    <div class="modal fade" id="success-modal" tabindex="-1" role="dialog" aria-labelledby="success-modal-label" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="success-modal-label">Thông báo</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="success-message">
                                    <!-- Success message will be inserted here -->
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                </div>
                            </div>
                        </div>
                    </div>





                    <div id="map"></div>
                                                        <div class="panel panel-default sidebar-menu similar-property-wdg wow fadeInRight animated">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Similar Properties</h3>
                                                    </div>
                                                    <div class="panel-body recent-property-widget">
                                                            <ul>
                                                            <li>
                                                                <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">
                                                                    <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>
                                                                    <span class="property-seeker">
                                                                        <b class="b-1">A</b>
                                                                        <b class="b-2">S</b>
                                                                    </span>
                                                                </div>
                                                                <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
                                                                    <h6> <a href="single.html">Super nice villa </a></h6>
                                                                    <span class="property-price">3000000$</span>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="col-md-3 col-sm-3  col-xs-3 blg-thumb p0">
                                                                    <a href="single.html"><img src="assets/img/demo/small-property-1.jpg"></a>
                                                                    <span class="property-seeker">
                                                                        <b class="b-1">A</b>
                                                                        <b class="b-2">S</b>
                                                                    </span>
                                                                </div>
                                                                <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
                                                                    <h6> <a href="single.html">Super nice villa </a></h6>
                                                                    <span class="property-price">3000000$</span>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">
                                                                    <a href="single.html"><img src="assets/img/demo/small-property-3.jpg"></a>
                                                                    <span class="property-seeker">
                                                                        <b class="b-1">A</b>
                                                                        <b class="b-2">S</b>
                                                                    </span>
                                                                </div>
                                                                <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
                                                                    <h6> <a href="single.html">Super nice villa </a></h6>
                                                                    <span class="property-price">3000000$</span>
                                                                </div>
                                                            </li>

                                                            <li>
                                                                <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">
                                                                    <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>
                                                                    <span class="property-seeker">
                                                                        <b class="b-1">A</b>
                                                                        <b class="b-2">S</b>
                                                                    </span>
                                                                </div>
                                                                <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
                                                                    <h6> <a href="single.html">Super nice villa </a></h6>
                                                                    <span class="property-price">3000000$</span>
                                                                </div>
                                                            </li>

                                                        </ul>
                                                    </div>
                                                </div>


                                                <div class="panel panel-default sidebar-menu wow fadeInRight animated" >
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Smart search</h3>
                                                    </div>
                                                    <div class="panel-body search-widget">
                                                        <form action="" class=" form-inline">
                                                            <fieldset>
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <input type="text" class="form-control" placeholder="Key word">
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset>
                                                                <div class="row">
                                                                    <div class="col-xs-6">

                                                                        <select id="lunchBegins" class="selectpicker" data-live-search="true" data-live-search-style="begins" title="Select Your City">

                                                                            <option>New york, CA</option>
                                                                            <option>Paris</option>
                                                                            <option>Casablanca</option>
                                                                            <option>Tokyo</option>
                                                                            <option>Marraekch</option>
                                                                            <option>kyoto , shibua</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="col-xs-6">

                                                                        <select id="basic" class="selectpicker show-tick form-control">
                                                                            <option> -Status- </option>
                                                                            <option>Rent </option>
                                                                            <option>Boy</option>
                                                                            <option>used</option>

                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <label for="price-range">Price range ($):</label>
                                                                        <input type="text" class="span2" value="" data-slider-min="0"
                                                                               data-slider-max="600" data-slider-step="5"
                                                                               data-slider-value="[0,450]" id="price-range" ><br />
                                                                        <b class="pull-left color">2000$</b>
                                                                        <b class="pull-right color">100000$</b>
                                                                    </div>
                                                                    <div class="col-xs-6">
                                                                        <label for="property-geo">Property geo (m2) :</label>
                                                                        <input type="text" class="span2" value="" data-slider-min="0"
                                                                               data-slider-max="600" data-slider-step="5"
                                                                               data-slider-value="[50,450]" id="property-geo" ><br />
                                                                        <b class="pull-left color">40m</b>
                                                                        <b class="pull-right color">12000m</b>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <label for="price-range">Min baths :</label>
                                                                        <input type="text" class="span2" value="" data-slider-min="0"
                                                                               data-slider-max="600" data-slider-step="5"
                                                                               data-slider-value="[250,450]" id="min-baths" ><br />
                                                                        <b class="pull-left color">1</b>
                                                                        <b class="pull-right color">120</b>
                                                                    </div>

                                                                    <div class="col-xs-6">
                                                                        <label for="property-geo">Min bed :</label>
                                                                        <input type="text" class="span2" value="" data-slider-min="0"
                                                                               data-slider-max="600" data-slider-step="5"
                                                                               data-slider-value="[250,450]" id="min-bed" ><br />
                                                                        <b class="pull-left color">1</b>
                                                                        <b class="pull-right color">120</b>

                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label> <input type="checkbox" checked> Fire Place</label>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label> <input type="checkbox"> Dual Sinks</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label> <input type="checkbox" checked> Swimming Pool</label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label> <input type="checkbox" checked> 2 Stories </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label><input type="checkbox"> Laundry Room </label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label> <input type="checkbox"> Emergency Exit</label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label>  <input type="checkbox" checked> Jog Path </label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-6">
                                                                        <div class="checkbox">
                                                                            <label>  <input type="checkbox"> 26' Ceilings </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset class="padding-5">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <div class="checkbox">
                                                                            <label>  <input type="checkbox"> Hurricane Shutters </label>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </fieldset>

                                                            <fieldset >
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <input class="button btn largesearch-btn" value="Search" type="submit">
                                                                    </div>
                                                                </div>
                                                            </fieldset>
                                                        </form>
                                                    </div>
                                                </div>

                </aside>
            </div>
        </div>

    </div>
</div>


<!-- Footer area-->
<div class="footer-area">

    <div class=" footer">
        <div class="container">
            <div class="row">

                <div class="col-md-3 col-sm-6 wow fadeInRight animated">
                    <div class="single-footer">
                        <h4>About us </h4>
                        <div class="footer-title-line"></div>

                        <img src="assets/img/footer-logo.png" alt="" class="wow pulse" data-wow-delay="1s">
                        <p>Lorem ipsum dolor cum necessitatibus su quisquam molestias. Vel unde, blanditiis.</p>
                        <ul class="footer-adress">
                            <li><i class="pe-7s-map-marker strong"> </i> 9089 your adress her</li>
                            <li><i class="pe-7s-mail strong"> </i> email@yourcompany.com</li>
                            <li><i class="pe-7s-call strong"> </i> +1 908 967 5906</li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 wow fadeInRight animated">
                    <div class="single-footer">
                        <h4>Quick links </h4>
                        <div class="footer-title-line"></div>
                        <ul class="footer-menu">
                            <li><a href="properties.html">Properties</a></li>
                            <li><a href="#">Services</a></li>
                            <li><a href="submit-property.html">Submit property </a></li>
                            <li><a href="contact.html">Contact us</a></li>
                            <li><a href="faq.html">fqa</a></li>
                            <li><a href="faq.html">Terms </a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 wow fadeInRight animated">
                    <div class="single-footer">
                        <h4>Last News</h4>
                        <div class="footer-title-line"></div>
                        <ul class="footer-blog">
                            <li>
                                <div class="col-md-3 col-sm-4 col-xs-4 blg-thumb p0">
                                    <a href="single.html">
                                        <img src="assets/img/demo/small-proerty-2.jpg">
                                    </a>
                                    <span class="blg-date">12-12-2016</span>

                                </div>
                                <div class="col-md-8  col-sm-8 col-xs-8  blg-entry">
                                    <h6><a href="single.html">Add news functions </a></h6>
                                    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla
                                        ...</p>
                                </div>
                            </li>

                            <li>
                                <div class="col-md-3 col-sm-4 col-xs-4 blg-thumb p0">
                                    <a href="single.html">
                                        <img src="assets/img/demo/small-proerty-2.jpg">
                                    </a>
                                    <span class="blg-date">12-12-2016</span>

                                </div>
                                <div class="col-md-8  col-sm-8 col-xs-8  blg-entry">
                                    <h6><a href="single.html">Add news functions </a></h6>
                                    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla
                                        ...</p>
                                </div>
                            </li>

                            <li>
                                <div class="col-md-3 col-sm-4 col-xs-4 blg-thumb p0">
                                    <a href="single.html">
                                        <img src="assets/img/demo/small-proerty-2.jpg">
                                    </a>
                                    <span class="blg-date">12-12-2016</span>

                                </div>
                                <div class="col-md-8  col-sm-8 col-xs-8  blg-entry">
                                    <h6><a href="single.html">Add news functions </a></h6>
                                    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla
                                        ...</p>
                                </div>
                            </li>


                        </ul>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6 wow fadeInRight animated">
                    <div class="single-footer news-letter">
                        <h4>Stay in touch</h4>
                        <div class="footer-title-line"></div>
                        <p>Lorem ipsum dolor sit amet, nulla suscipit similique quisquam molestias. Vel unde,
                            blanditiis.</p>

                        <form>
                            <div class="input-group">
                                <input class="form-control" type="text" placeholder="E-mail ... ">
                                <span class="input-group-btn">
                                            <button class="btn btn-primary subscribe" type="button"><i
                                                    class="pe-7s-paper-plane pe-2x"></i></button>
                                        </span>
                            </div>
                            <!-- /input-group -->
                        </form>

                        <div class="social pull-right">
                            <ul>
                                <li><a class="wow fadeInUp animated" href="https://twitter.com/kimarotec"><i
                                        class="fa fa-twitter"></i></a></li>
                                <li><a class="wow fadeInUp animated" href="https://www.facebook.com/kimarotec"
                                       data-wow-delay="0.2s"><i class="fa fa-facebook"></i></a></li>
                                <li><a class="wow fadeInUp animated" href="https://plus.google.com/kimarotec"
                                       data-wow-delay="0.3s"><i class="fa fa-google-plus"></i></a></li>
                                <li><a class="wow fadeInUp animated" href="https://instagram.com/kimarotec"
                                       data-wow-delay="0.4s"><i class="fa fa-instagram"></i></a></li>
                                <li><a class="wow fadeInUp animated" href="https://instagram.com/kimarotec"
                                       data-wow-delay="0.6s"><i class="fa fa-dribbble"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="footer-copy text-center">
        <div class="container">
            <div class="row">
                <div class="pull-left">
                    <span> (C) <a href="http://www.KimaroTec.com">KimaroTheme</a> , All rights reserved 2016  </span>
                </div>
                <div class="bottom-menu pull-right">
                    <ul>
                        <li><a class="wow fadeInUp animated" href="#" data-wow-delay="0.2s">Home</a></li>
                        <li><a class="wow fadeInUp animated" href="#" data-wow-delay="0.3s">Property</a></li>
                        <li><a class="wow fadeInUp animated" href="#" data-wow-delay="0.4s">Faq</a></li>
                        <li><a class="wow fadeInUp animated" href="#" data-wow-delay="0.6s">Contact</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/vendor/modernizr-2.6.2.min.js"></script>
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
<script type="text/javascript" src="assets/js/lightslider.min.js"></script>
<script src="assets/js/main.js"></script>

<script>
    $(document).ready(function () {

        $('#image-gallery').lightSlider({
            gallery: true,
            item: 1,
            thumbItem: 9,
            slideMargin: 0,
            speed: 500,
            auto: true,
            loop: true,
            onSliderLoad: function () {
                $('#image-gallery').removeClass('cS-hidden');
            }
        });
    });


</script>
<script>
    function showAlert(message) {
        alert(message);
    }
</script>
<script>
    $(document).ready(function () {
        var mapObj = null;
        var defaultCoord = [21.0819, 105.6363]; // coord mặc định, Hà Nội
        var zoomLevel = 15; // Mức phóng to bản đồ
        var mapConfig = {
            attributionControl: false, // để ko hiện watermark nữa, nếu bị liên hệ đòi thì nhớ open nha
            center: defaultCoord, // vị trí map mặc định hiện tại
            zoom: zoomLevel,
        };

        mapObj = L.map('map', mapConfig);

        // thêm tile để map có thể hoạt động, xài free từ OSM
        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(mapObj);
    });
</script>



</body>
</html>