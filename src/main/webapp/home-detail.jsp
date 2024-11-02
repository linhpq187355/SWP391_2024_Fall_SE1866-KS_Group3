<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .similar-post-section .row {
            display: flex;
            flex-wrap: wrap;
        }

        .similar-post-section .col-md-3 {
            margin-right: 10px; /* Optional spacing between homes */
        }
        .phone-button {
            margin: 5px 0 5px 0;
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
        .zalo-button i, .like-button i {
            margin-right: 10px;
        }
        /*#map{*/
        /*    width: 100%;*/
        /*    height: 100%;*/
        /*}*/
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
                            <div class="favorite-and-print">
                                <a class="add-to-fav" href="#login-modal" data-toggle="modal">
                                    <i class="fa fa-star-o"></i>
                                </a>
                                <a class="printer-icon" href="javascript:window.print()">
                                    <i class="fa fa-print"></i>
                                </a>
                            </div>

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
                        <span class="property-price pull-right"><fmt:formatNumber value="${prices[0].price}" type="number" groupingUsed="true"/> VND/tháng</span>
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
                <aside class="sidebar sidebar-property blog-asside-right" style="background-color: #fff; padding-top: 3px">
                    <div class="dealer-widget" style="background-color: #fff;border-radius: 10px;box-shadow: rgba(0, 0, 0, 0.18) 0px 2px 4px;margin: -5px;">
                        <div class="dealer-content">
                            <div class="inner-wrapper">
                                <div class="clear">
                                    <div class="col-xs-4 col-sm-4 dealer-face" style="padding: 0">
                                        <a href="">
                                            <img src="assets/img/client-face1.png" class="img-circle">
                                        </a>
                                    </div>
                                    <div class="col-xs-8 col-sm-8 " style="padding:0">
                                        <h3 class="dealer-name" style="color: black">
                                            <a href="">${creator.firstName} ${creator.lastName}</a>
                                        </h3>
                                        <div class="dealer-email" style="color: black; display: flex; align-items: center">
                                            <i class="fa-solid fa-envelope"></i>
                                            <p style="padding: 0 0 5px 10px;">${creator.email}</p>
                                        </div>
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
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                </aside>
            </div>
        </div>

    </div>
</div>


<!-- Footer area-->
<jsp:include page="footer.jsp"/>

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