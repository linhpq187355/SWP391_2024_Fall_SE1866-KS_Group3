<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="http://example.com/functions" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GARO ESTATE | Thông tin phòng</title>
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
    <script src="https://cdn.jsdelivr.net/npm/@splidejs/splide@1.2.0/dist/js/splide.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@splidejs/splide@1.2.0/dist/css/splide.min.css">
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
        .similar-post-section .row {
            display: flex;
            flex-wrap: wrap;
        }

        .similar-post-section .col-md-3 {
            margin-right: 10px; /* Optional spacing between homes */
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
        .apmt-button {
            margin: 5px 0 5px 0;
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
        .apmt-button i {
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
        body {
            background-color: #eee;

        }

        .bdge {
            height: 21px;
            background-color: orange;
            color: #fff;
            font-size: 11px;
            padding: 8px;
            border-radius: 4px;
            line-height: 3px;
        }

        .comments {
            text-decoration: underline;
            text-underline-position: under;
            cursor: pointer;
        }

        .dot {
            height: 7px;
            width: 7px;
            margin-top: 3px;
            background-color: #bbb;
            border-radius: 50%;
            display: inline-block;
        }

        .hit-voting:hover {
            color: blue;
        }

        .hit-voting {
            cursor: pointer;
        }
        /*#map{*/
        /*    width: 100%;*/
        /*    height: 100%;*/
        /*}*/
        .thumbnail_slider {
            max-width: 710px;
            margin: 30px auto;
        }

        .splide__slide {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 400px;
            width: 580px;
            overflow: hidden;
            transition: .2s;
            border-width: 2px !important;
            margin: 10px 4px;
        }

        .splide--nav > .splide__track > .splide__list > .splide__slide.is-active {
            box-shadow: 2px 3px 8px #000000a3;
        }

        .splide__slide img {
            width: auto;
            height: auto;
            margin: auto;
            display: block;
            max-width: 100%;
            max-height: 100%;
        }
        #primary_slider .splide__slide{
            width: 700px !important;
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


                            <div class="thumbnail_slider" style="margin-top: 0">
                                <!-- Primary Slider Start-->
                                <div id="primary_slider">
                                    <div class="splide__track">
                                        <ul class="splide__list">
                                            <c:forEach items="${home.images}" var="image">
                                                <li class="splide__slide" style="border-radius: 15px;">
                                                    <img src="${image}">
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <!-- Primary Slider End-->
                                <!-- Thumbnal Slider Start-->
                                <div id="thumbnail_slider">
                                    <div class="splide__track">
                                        <ul class="splide__list">
                                            <c:forEach items="${home.images}" var="image">
                                                <li class="splide__slide">
                                                    <img src="${image}">
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <!-- Thumbnal Slider End-->
                            </div>


                            <script>
                                // Primary slider.
                                var primarySlider = new Splide('#primary_slider', {
                                    type: 'fade',
                                    heightRatio: 0.5,
                                    pagination: false,
                                    arrows: false,
                                    cover: true,
                                });

                                // Thumbnails slider.
                                var thumbnailSlider = new Splide('#thumbnail_slider', {
                                    rewind: true,
                                    fixedWidth: 100,
                                    fixedHeight: 64,
                                    isNavigation: true,
                                    gap: 10,
                                    focus: 'center',
                                    pagination: false,
                                    cover: true,
                                    breakpoints: {
                                        '600': {
                                            fixedWidth: 66,
                                            fixedHeight: 40,
                                        }
                                    }
                                }).mount();

                                // sync the thumbnails slider as a target of primary slider.
                                primarySlider.sync(thumbnailSlider).mount();
                            </script>



                        </div>
                    </div>
                </div>

                <div class="single-property-wrapper" style="margin-top: 30px">
                    <div class="single-property-header">
                        <h1 class="property-title pull-left" style="letter-spacing: 0;color: #1A1A1A;">${home.name}</h1>
                        <span class="property-price pull-right">
${ex:convertPriceToVND(prices[0].price)} VND/tháng
                        </span>
                    </div>


                    <div class="property-meta entry-meta clearfix">

                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info-icon icon-tag">
                                <img src="assets/img/icon/sale-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label">Trạng thái</span>
                                <span class="property-info-value">${homeTypes[0].status}</span>
                            </span>
                        </div>
                        <div class="col-xs-6 col-sm-3 col-md-3 p-b-15">
                            <span class="property-info icon-area">
                                <img src="assets/img/icon/room-orange.png">
                            </span>
                            <span class="property-info-entry">
                                <span class="property-info-label"> Loại Hình Nhà </span>
                                <span class="property-info-value">${homeTypes[0].name}</span>
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
                            <c:forEach var="amenity" items="${amenities}">
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

                    <%--                    <div class="section property-share">--%>
                    <%--                        <c:choose>--%>
                    <%--                            <c:when test="${not empty userRoleId and userRoleId == 3}">--%>
                    <%--                                <form action="${pageContext.request.contextPath}/Question" method="post">--%>
                    <%--                                    <input type="hidden" name="id" value="${param.id}">--%>
                    <%--                                    <textarea name="content" placeholder="Nhập câu hỏi của bạn..." required></textarea>--%>
                    <%--                                    <button type="submit">Đăng câu hỏi</button>--%>
                    <%--                                </form>--%>
                    <%--                            </c:when>--%>
                    <%--                        </c:choose>--%>

                    <%--                        <<c:forEach var="question" items="${questions}">--%>
                    <%--                        <div class="question"> <!-- Mở div cho câu hỏi -->--%>
                    <%--                            <h3>Câu hỏi từ người dùng: ${question.authorId}</h3>--%>
                    <%--                            <p>${question.content}</p>--%>
                    <%--                            <span>Ngày đăng: ${question.createdAt}</span>--%>

                    <%--                            <c:forEach var="answer" items="${question.answers}">--%>
                    <%--                                <div class="answer">--%>
                    <%--                                    <p>Trả lời: ${answer.content} bởi người dùng ${answer.authorId}</p>--%>
                    <%--                                </div>--%>
                    <%--                            </c:forEach>--%>

                    <%--                            <form action="${pageContext.request.contextPath}/Answer" method="post">--%>
                    <%--                                <input type="hidden" name="questionId" value="${question.questionId}"> <!-- Đảm bảo giá trị đúng -->--%>
                    <%--                                <input type="hidden" name="homeId" value="${param.id}">--%>
                    <%--                                <textarea name="answerContent" placeholder="Nhập câu trả lời của bạn..."></textarea>--%>
                    <%--                                <button type="submit">Gửi trả lời</button>--%>
                    <%--                            </form>--%>
                    <%--                        </div>--%>
                    <%--                    </c:forEach>--%>
                    <%--                    </div>--%>




                    <%--                    <div class="similar-post-section padding-top-40">--%>
                    <%--                        <div class="row"> <!-- Ensure row class is used -->--%>
                    <%--                            <c:forEach var="similarHome" items="${similarHomes}">--%>
                    <%--                                <div class="col-md-3 col-sm-6 col-xs-12"> <!-- Adjust column size based on your needs -->--%>
                    <%--                                    <div id="prop-smlr-slide_0">--%>
                    <%--                                        <div class="box-two proerty-item">--%>
                    <%--                                            <div class="item-thumb">--%>
                    <%--                                                <a href="home-detail?id=${similarHome.id}">--%>
                    <%--                                                    <img src="assets/img/similar/property-1.jpg" alt="${similarHome.address}">--%>
                    <%--                                                </a>--%>
                    <%--                                            </div>--%>
                    <%--                                            <div class="item-entry overflow">--%>
                    <%--                                                <h5><a href="home-detail?id=${similarHome.id}"> ${similarHome.address} </a></h5>--%>
                    <%--                                                <div class="dot-hr"></div>--%>
                    <%--                                                <span class="pull-left"><b> Area :</b> ${similarHome.area}m </span>--%>
                    <%--                                                <span class="proerty-price pull-right">${prices[0].price} VND/month</span>--%>
                    <%--                                            </div>--%>
                    <%--                                        </div>--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </c:forEach>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <%--                    <div class="similar-post-section padding-top-40">--%>
                    <%--                        <c:forEach var="similarHome" items="${similarHomes}">--%>
                    <%--                            <div id="prop-smlr-slide_0">--%>
                    <%--                                <div class="box-two proerty-item">--%>
                    <%--                                    <div class="item-thumb">--%>
                    <%--                                        <a href="home-detail?id=${similarHome.id}">--%>
                    <%--                                            <img src="assets/img/similar/property-1.jpg" alt="${similarHome.address}"> <!-- Replace with actual image -->--%>
                    <%--                                        </a>--%>
                    <%--                                    </div>--%>
                    <%--                                    <div class="item-entry overflow">--%>
                    <%--                                        <h5><a href="home-detail?id=${similarHome.id}"> ${similarHome.address} </a></h5>--%>
                    <%--                                        <div class="dot-hr"></div>--%>
                    <%--                                        <span class="pull-left"><b> Area :</b> ${similarHome.area}m</span> <!-- Replace with actual area -->--%>
                    <%--                                        <span class="proerty-price pull-right">${prices[0].price} VND/month</span> <!-- Adjust price as necessary -->--%>
                    <%--                                    </div>--%>
                    <%--                                </div>--%>
                    <%--                            </div>--%>
                    <%--                        </c:forEach>--%>
                    <%--                    </div>--%>
                    <!-- End video area  -->

                </div>
            </div>


            <div class="col-md-4 p0">
                <aside class="sidebar sidebar-property blog-asside-right" style="background-color: #fff">
                    <div class="dealer-widget" style="background-color: #fff;
    border-radius: 10px;
    border: solid 3px #ccc;
    margin: -5px;">
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
                                                <a class="zalo-button" href="chat-box?userId=${creator.id}" style="font-weight: 100">
                                                    <i class="fas fa-comment"></i>
                                                    Liên Hệ
                                                </a>
                                            </li>
                                            <c:if test="${cookie.roleId.value == 3}">
                                                <li>
                                                    <form action="${sessionScope.isInWishlist ? 'delete-wishlist' : 'add-wishlist'}" method="POST"
                                                          onsubmit="return confirm('${sessionScope.isInWishlist ? 'Bạn có chắc chắn muốn xóa không?' : 'Bạn có chắc chắn muốn thêm vào danh sách yêu thích?'}');">
                                                        <input type="hidden" name="homeId" value="${home.id}">
                                                        <button type="submit" class="like-button">
                                                            <i class="fas fa-heart"></i> ${sessionScope.isInWishlist ? 'Đã thêm' : 'Yêu thích'}
                                                        </button>
                                                    </form>
                                                </li>
                                            </c:if>
                                            <c:if test="${cookie.roleId.value ==3}">
                                                <li>
                                                    <button class="apmt-button" onclick="window.location.href='make-appointment?hostId=${creator.id}&homeId=${home.id}'">
                                                        <i class="fa-solid fa-calendar"></i>
                                                        Đặt lịch hẹn
                                                    </button>
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





                    <%--                    <div id="map"></div>--%>
                    <div class="panel panel-default sidebar-menu similar-property-wdg wow fadeInRight animated" style="    margin: 25px 9px 9px 9px">
                        <div class="panel-heading">
                            <h3 class="panel-title">Bạn cũng có thể thích</h3>
                        </div>
                        <div class="panel-body recent-property-widget">
                            <ul>
                                <!-- Lặp qua danh sách các nhà tương tự -->
                                <c:forEach var="similarHome" items="${similarHomes}">
                                    <c:if test="${similarHome.id != param.id}">
                                        <li>
                                            <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">
                                                <a href="home-detail?id=${similarHome.id}">
                                                    <img src="assets/img/demo/small-property-2.jpg" alt="${similarHome.address}"> <!-- Hình ảnh mẫu, bạn có thể thay đổi -->
                                                </a>
                                            </div>
                                            <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
                                                <h6><a href="home-detail?id=${similarHome.id}">${similarHome.address}</a></h6>
                                                <c:forEach items="${requestScope.priceSimilarHomes}" var="priceSimilarHomes">
                                                    <c:if test="${similarHome.id == priceSimilarHomes.homesId}">
                                                        <span class="property-price"><fmt:formatNumber value="${priceSimilarHomes.price}" type="number" groupingUsed="true"/> VND/tháng</span>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <%--                                                        <div class="panel panel-default sidebar-menu similar-property-wdg wow fadeInRight animated">--%>
                    <%--                                                    <div class="panel-heading">--%>
                    <%--                                                        <h3 class="panel-title">Bạn cũng có thể thích</h3>--%>
                    <%--                                                    </div>--%>
                    <%--                                                    <div class="panel-body recent-property-widget">--%>
                    <%--                                                            <ul>--%>
                    <%--                                                            <li>--%>
                    <%--                                                                <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
                    <%--                                                                    <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>--%>
                    <%--                                                                </div>--%>
                    <%--                                                                <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
                    <%--                                                                    <h6> <a href="home-detail?id=19">long bien ha noi</a></h6>--%>
                    <%--                                                                    <span class="property-price">30000VND/tháng</span>--%>
                    <%--                                                                </div>--%>
                    <%--                                                            </li>--%>
                    <%--                                                        </ul>--%>
                    <%--                                                    </div>--%>
                    <%--                                                            <div class="panel-body recent-property-widget">--%>
                    <%--                                                                <ul>--%>
                    <%--                                                                    <li>--%>
                    <%--                                                                        <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
                    <%--                                                                            <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>--%>
                    <%--                                                                        </div>--%>
                    <%--                                                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
                    <%--                                                                            <h6> <a href="single.html">ha dong ha noi</a></h6>--%>
                    <%--                                                                            <span class="property-price">200000VND/tháng</span>--%>
                    <%--                                                                        </div>--%>
                    <%--                                                                    </li>--%>
                    <%--                                                                </ul>--%>
                    <%--                                                            </div>--%>
                    <%--                                                            <div class="panel-body recent-property-widget">--%>
                    <%--                                                                <ul>--%>
                    <%--                                                                    <li>--%>
                    <%--                                                                        <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
                    <%--                                                                            <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>--%>
                    <%--                                                                        </div>--%>
                    <%--                                                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
                    <%--                                                                            <h6> <a href="single.html">thanh oai ha noi </a></h6>--%>
                    <%--                                                                            <span class="property-price">100000VND/tháng</span>--%>
                    <%--                                                                        </div>--%>
                    <%--                                                                    </li>--%>
                    <%--                                                                </ul>--%>
                    <%--                                                            </div>--%>
                    <%--                                                </div>--%>




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
<script src="https://unpkg.com/leaflet@1.4.0/dist/leaflet.js" integrity="sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg==" crossorigin=""></script>


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

    function showConfirmPopup(homeId, userId) {
        // Hiển thị modal xác nhận
        $('#login-modal').modal('show');

        // Lưu các ID để sử dụng trong xác nhận
        window.currentHomeId = homeId;
        window.currentUserId = userId;
    }

    function confirmAddToWishlist(homeId, userId) {
        // Tạo một yêu cầu POST tới servlet
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "add-to-wishlist", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                alert(xhr.responseText);
            }
        };
        xhr.send("homeId=" + homeId + "&userId=" + userId);
    }
</script>
<script>
    $(document).ready(function () {
        var mapObj = null;
        var defaultCoord = [21.0819, 105.6363];
        var zoomLevel = 15;
        var mapConfig = {
            attributionControl: false,
            center: defaultCoord,
            zoom: zoomLevel,
        };

        mapObj = L.map('map', mapConfig);

        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(mapObj);
    });
</script>
<script>
    function confirmDelete() {
        if (confirm('Bạn có chắc chắn muốn xóa không?')) {
            // Nếu người dùng xác nhận, gửi form
            document.getElementById('delete-form').submit();
        }
    }
</script>
<script>
    function confirmAdd() {
        return confirm('Bạn có chắc chắn muốn thêm vào danh sách yêu thích?');
    }

</script>

</body>
</html>