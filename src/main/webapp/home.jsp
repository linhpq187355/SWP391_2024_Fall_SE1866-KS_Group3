<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/22/2024
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Roomify | Home page</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="Kimarotec">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>
    <!-- Place favicon.ico  the root directory -->
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
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <script src="https://kit.fontawesome.com/f5cbf3afb2.js" crossorigin="anonymous"></script>
    <style>
        .property-image {
            width: 100%;   /* Điều chỉnh cho ảnh đầy chiều rộng của thẻ bao quanh */
            height: 200px; /* Hoặc bạn có thể đặt kích thước cố định tùy thuộc vào yêu cầu */
            object-fit: cover; /* Giúp cắt ảnh nếu vượt quá chiều rộng hoặc chiều cao, mà vẫn giữ tỉ lệ */
        }
        #searchInput::placeholder {
            color: #777;
        }
    </style>
</head>

<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->

<jsp:include page="header.jsp"/>
<!-- End of nav bar -->

<div class="banner-area row" style="padding: 0 80px;display: flex;align-items: center;">
    <div class="col-md-6">
        <div>
            <h2 style="text-align: center;color: #FA6C00;">CHUNG NHÀ - CHUNG VUI</h2>
            <p style="text-align: center;font-size: 20px;color: #484848;">Nơi kết nối những người cùng chung chí hướng</p>
        </div>
        <div style="display: flex;width: 80%;justify-content: space-between;border: 1px solid #ccc;box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;border-radius: 15px;height: 50px;margin-left: 15%;margin-top: 5%;align-items: center;">
            <i class="fa-solid fa-location-dot" style="font-size: 25px;margin-left: 4%;"></i>
            <form action="searchHomes" method="GET" class="form-inline" style="width: 80%;">
                <input style="border: none;font-size: 17px;font-weight: 900; box-shadow: none;" placeholder="Nhập địa điểm" id="searchInput" type="text" name="name" class="form-control  value="${param.name}">
            </form>
            <i class="fa-solid fa-magnifying-glass" style="font-size: 25px;margin-right: 5%;"></i>
        </div>
    </div>
    <div class="slider-area col-md-6">
            <div id="bg-slider" class="owl-carousel owl-theme" style="margin-top: 75px">
                <img src="assets/img/banner/banner-1.png" alt="banner">
                <img src="assets/img/banner/banner-2.png" alt="banner">
                <img src="assets/img/banner/banner-3.png" alt="banner">
            </div>
    </div>
</div>



<!-- property area -->


<div class="content-area recent-property" style="background-color: #FCFCFC; padding-bottom: 55px;">
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title" style="margin-left: 0">
                <c:choose>
                    <c:when test="${!empty param.name || !empty param.minPrice || !empty param.maxPrice}">
                        <h2>Kết quả tìm kiếm của bạn</h2>

                        <c:if test="${empty homes}">
                            <h2> hiện không khả dụng, vui lòng tìm kiếm lại.</h2>
                        </c:if>
                    </c:when>

                    <c:otherwise>
                        <h2 style="text-align: left; color: #484848; letter-spacing: 0">Bài đăng mới nhất</h2>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <div class="row">
            <div class="proerty-th">
                <c:forEach items="${requestScope.homes}" var="homes">
                    <div class="col-sm-6 col-md-3 p0">
                        <div class="box-two proerty-item" style="box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;border-radius: 15px;margin: 15px;height: 390px;">
                            <div class="item-thumb">
                                <a href="home-detail?id=${homes.id}">
                                    <img class="property-image"
                                         src="${homes.images != null && !homes.images.isEmpty() ? homes.images[0] : 'assets/img/social_big/dribbble_grey.png'}"
                                         alt="Property Image" style="border-top-left-radius: 15px;border-top-right-radius: 15px;">
                                </a>
                            </div>
                            <div class="item-entry overflow">

                                <h5 style="letter-spacing: 0;height: 35px"><a href="home-detail?id=${homes.id}">${homes.name}</a></h5>
                                <div style="display: flex;align-items: center;border-bottom: 1px solid #484848;padding-bottom: 10px;">
                                    <i class="fa-solid fa-location-dot" ></i>
                                    <p style="padding: 0;margin-left: 10px;">${homes.address}</p>
                                </div>
                                <div style="display: flex;justify-content: space-between;font-size: 15px;color: #FA6C00;margin-top: 10px;align-items: center;">
                                    <div>
                                        <i class="fa-solid fa-chart-area"></i> ${homes.area} m<sup>2</sup>
                                    </div>
                                    <div>
                                        <i class="fa-solid fa-dollar-sign"></i>
                                        <c:forEach items="${requestScope.prices}" var="prices">
                                            <c:if test="${prices.homesId == homes.id}">
                                                ${prices.price}
                                            </c:if>
                                        </c:forEach>
                                         đ
                                    </div>
                                    <div style="border: 1px solid #ccc;border-radius: 5px;height: 35px;width: 35px;display: flex;justify-content: center;align-items: center;font-size: 25px;">
                                        <i class="fa-regular fa-heart"></i>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="col-sm-6 col-md-3 p0">
            <div class="box-tree more-proerty text-center">
                <div class="item-tree-icon">
                    <i class="fa fa-th"></i>
                </div>
                <div class="more-entry overflow">
                    <h5><a href="property-1.html" >Chưa ưng ý? </a></h5>
                    <h5 class="tree-sub-ttl">Xem tất cả bài đăng</h5>
                    <button class="btn border-btn more-black" value="All properties">Tất cả bài đăng</button>
                </div>
            </div>
        </div>
    </div>
</div>

    <c:if test="${cookie.roleId.value ==3 || empty cookie.id}">
        <div class="content-area recent-property" style="background-color: #FCFCFC; padding-bottom: 55px;">
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
                    <h2>Phù hợp với bạn</h2>
                </div>
            </div>
            <c:if test="${not empty cookie.id}">
                <c:if test="${requestScope.preference.cleanliness != 100}">
                    <div style="position: absolute;top: 2410px;left: 1120px;border: solid 1px;border-radius: 8.5px;padding: 5px;color: darkgoldenrod;cursor: pointer;" onclick="window.location.href='update-matching-profile'">
                        Thay đổi thông tin ghép nối
                    </div>
                    <c:if test="${not empty requestScope.matchingHomes}">
                        <div class="row">
                            <div class="proerty-th">
                                <c:forEach items="${requestScope.matchingHomes}" var="matchingHomes">
                                    <div class="col-sm-6 col-md-3 p0">
                                        <div class="box-two proerty-item">
                                            <div class="item-thumb">
                                                <!-- Update the href to point to home detail page with home ID -->
                                                <a href="home-detail?id=${matchingHomes.id}"><img src="assets/img/demo/property-1.jpg"></a>
                                            </div>
                                            <div class="item-entry overflow">
                                                <h5><a href="home-detail?id=${matchingHomes.id}">${matchingHomes.address}</a></h5>
                                                <div class="dot-hr"></div>
                                                <span class="pull-left"><b>Area :</b> ${matchingHomes.area}</span>
                                                <c:forEach items="${requestScope.matchingHomePrice}" var="matchingHomePrice">
                                                    <c:if test="${matchingHomePrice.homesId == matchingHomes.id}">
                                                        <span class="proerty-price pull-right">${matchingHomePrice.price}</span>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="col-sm-6 col-md-3 p0">
                            <div class="box-tree more-proerty text-center">
                                <div class="item-tree-icon">
                                    <i class="fa fa-th"></i>
                                </div>
                                <div class="more-entry overflow">
                                    <h5><a href="property-1.html" >Chưa ưng ý? </a></h5>
                                    <h5 class="tree-sub-ttl">Xem tất cả bài đăng</h5>
                                    <button class="btn border-btn more-black" value="All properties">Tất cả bài đăng</button>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${empty requestScope.matchingHomes}">
                        <h4 style="text-align: center;">Rất tiếc! Chưa có bài đăng nào phù hợp với bạn. Bạn có thể lọc bài đăng <a>tại đây</a></h4>
                    </c:if>
                </c:if>
                <c:if test="${requestScope.preference.cleanliness == 100}">
                    <h4 style="text-align: center">Bạn chưa cài đặt thông tin ghép nối, vui lòng cài đặt <a href="matching">tại đây</a></h4>
                </c:if>
            </c:if>
            <c:if test="${empty cookie.id}">
                <h4 style="text-align: center">Bạn chưa đăng nhập, vui lòng đăng nhập <a href="login">tại đây</a></h4>
            </c:if>
        </div>
    </div>
    </c:if>




<!--Welcome area -->
<div class="Welcome-area">
    <div class="container">
        <div class="row">
            <div class="col-md-12 Welcome-entry  col-sm-12">
                <div class="col-md-5 col-md-offset-2 col-sm-6 col-xs-12">
                    <div class="welcome_text wow fadeInLeft" data-wow-delay="0.3s" data-wow-offset="100">
                        <div class="row">
                            <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
                                <!-- /.feature title -->
                                <h2>Roomify </h2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 col-sm-6 col-xs-12">
                    <div  class="welcome_services wow fadeInRight" data-wow-delay="0.3s" data-wow-offset="100">
                        <div class="row">
                            <div class="col-xs-6 m-padding">
                                <div class="welcome-estate">
                                    <div class="welcome-icon">
                                        <i class="pe-7s-home pe-4x"></i>
                                    </div>
                                    <h3>Any property</h3>
                                </div>
                            </div>
                            <div class="col-xs-6 m-padding">
                                <div class="welcome-estate">
                                    <div class="welcome-icon">
                                        <i class="pe-7s-users pe-4x"></i>
                                    </div>
                                    <h3>More Clients</h3>
                                </div>
                            </div>


                            <div class="col-xs-12 text-center">
                                <i class="welcome-circle"></i>
                            </div>

                            <div class="col-xs-6 m-padding">
                                <div class="welcome-estate">
                                    <div class="welcome-icon">
                                        <i class="pe-7s-notebook pe-4x"></i>
                                    </div>
                                    <h3>Easy to use</h3>
                                </div>
                            </div>
                            <div class="col-xs-6 m-padding">
                                <div class="welcome-estate">
                                    <div class="welcome-icon">
                                        <i class="pe-7s-help2 pe-4x"></i>
                                    </div>
                                    <h3>Any help </h3>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Count area -->
<div class="count-area">
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
                <!-- /.feature title -->
                <h2>Những con số ấn tượng </h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-xs-12 percent-blocks m-main" data-waypoint-scroll="true">
                <div class="row">
                    <div class="col-sm-4 col-xs-6">
                        <div class="count-item">
                            <div class="count-item-circle">
                                <span class="pe-7s-users"></span>
                            </div>
                            <div class="chart" data-percent="5000">
                                <h2>${requestScope.totalUser}</h2>
                                <h5>Người dùng </h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-xs-6">
                        <div class="count-item">
                            <div class="count-item-circle">
                                <span class="pe-7s-home"></span>
                            </div>
                            <div class="chart" data-percent="12000">
                                <h2>${requestScope.totalHomes}</h2>
                                <h5>Bài đăng </h5>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-xs-6">
                        <div class="count-item">
                            <div class="count-item-circle">
                                <span class="pe-7s-flag"></span>
                            </div>
                            <div class="chart" data-percent="120">
                                <h2 class="percent" id="counter2">0</h2>
                                <h5>Tỉnh thành </h5>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- boy-sale area -->
<div class="boy-sale-area">
    <div class="container">
        <div class="row">

            <div class="col-md-6 col-sm-10 col-sm-offset-1 col-md-offset-0 col-xs-12">
                <div class="asks-first">
                    <div class="asks-first-circle">
                        <span class="fa fa-search"></span>
                    </div>
                    <div class="asks-first-info">
                        <h2>ARE YOU LOOKING FOR A Property?</h2>
                        <p> varius od lio eget conseq uat blandit, lorem auglue comm lodo nisl no us nibh mas lsa</p>
                    </div>
                    <div class="asks-first-arrow">
                        <a href="properties.html"><span class="fa fa-angle-right"></span></a>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-10 col-sm-offset-1 col-xs-12 col-md-offset-0">
                <div  class="asks-first">
                    <div class="asks-first-circle">
                        <span class="fa fa-usd"></span>
                    </div>
                    <div class="asks-first-info">
                        <h2>DO YOU WANT TO SELL A Property?</h2>
                        <p> varius od lio eget conseq uat blandit, lorem auglue comm lodo nisl no us nibh mas lsa</p>
                    </div>
                    <div class="asks-first-arrow">
                        <a href="properties.html"><span class="fa fa-angle-right"></span></a>
                    </div>
                </div>
            </div>
            <div class="col-xs-12">
                <p  class="asks-call">QUESTIONS? CALL US  : <span class="strong"> + 3-123- 424-5700</span></p>
            </div>
        </div>
    </div>
</div>


<!-- Footer area-->
<jsp:include page="footer.jsp"/>

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
        function validateSearchInput() {
            let searchInput = document.getElementById("searchInput").value.trim();

            // Lấy phần tử để hiển thị thông báo lỗi
            let errorMessage = document.getElementById("error-message");

            errorMessage.textContent = "";

            if (searchInput.length > 100) {
                errorMessage.textContent = "Đầu vào không được vượt quá 100 ký tự.";
                return false;
            }

            let inputRegex = /^[a-zA-Z0-9\s]+$/;
            if (!inputRegex.test(searchInput)) {
                errorMessage.textContent = "Đầu vào chỉ được phép chứa chữ cái và số.";
                return false;
            }

            document.getElementById("searchInput").value = searchInput;

            return true;
        }
    </script>
<script>
    function updatePriceValues() {
        // Lấy giá trị từ slider
        var priceRange = document.getElementById('price-range').value;
        var values = priceRange.split(','); // Tách giá trị

        // Cập nhật các input ẩn
        document.getElementById('minPrice').value = values[0];
        document.getElementById('maxPrice').value = values[1];
    }
</script>
</body>
</html>
