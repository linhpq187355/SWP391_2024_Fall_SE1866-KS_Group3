<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/22/2024
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GARO ESTATE | Home page</title>
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
</head>
<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->

<jsp:include page="header.jsp"/>
    <!-- End of nav bar -->

    <div class="slider-area">
    <div class="slider">
    <div id="bg-slider" class="owl-carousel owl-theme">

    <div class="item"><img src="assets/img/slide1/slider-image-2.jpg" alt="The Last of us"></div>
    <div class="item"><img src="assets/img/slide1/slider-image-1.jpg" alt="GTA V"></div>

    </div>
    </div>
    <div class="container slider-content">
    <div class="row">
    <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-sm-12">
    <h2>đã được</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eligendi deserunt deleniti, ullam commodi sit ipsam laboriosam velit adipisci quibusdam aliquam teneturo!</p>

    </div>
    </div>
    </div>
    </div>

    <div class="home-lager-shearch" style="background-color: rgb(252, 252, 252); padding-top: 25px; margin-top: -125px;">
    <div class="container">
    <div class="col-md-12 large-search">
    <div class="search-form wow pulse">
    <form action="" class=" form-inline">
    <div class="col-md-12">
    <div>
    <input type="text" class="form-control" placeholder="Nhập thông tin bạn cần tìm kiếm...">
    </div>
    </div>
    <div class="center">
    <input type="submit" value="" class="btn btn-default btn-lg-sheach">
    </div>
    </form>
    </div>
    </div>
    </div>
    </div>


    <!-- property area -->
    <div class="content-area recent-property" style="background-color: #FCFCFC; padding-bottom: 55px;">
    <div class="container">
    <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
    <!-- /.feature title -->
    <h2>Top submitted property</h2>
    <p>Nulla quis dapibus nisl. Suspendisse ultricies commodo arcu nec pretium. Nullam sed arcu ultricies . </p>
    </div>
    </div>

    <div class="row">
    <div class="proerty-th">
    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-1.html" ><img src="assets/img/demo/property-1.jpg"></a>
    </div>
    <div class="item-entry overflow">
    <h5><a href="property-1.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-2.html" ><img src="assets/img/demo/property-2.jpg"></a>
    </div>
    <div class="item-entry overflow">
    <h5><a href="property-2.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-3.html" ><img src="assets/img/demo/property-3.jpg"></a>

    </div>
    <div class="item-entry overflow">
    <h5><a href="property-3.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-1.html" ><img src="assets/img/demo/property-4.jpg"></a>

    </div>
    <div class="item-entry overflow">
    <h5><a href="property-1.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>


    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-3.html" ><img src="assets/img/demo/property-2.jpg"></a>
    </div>
    <div class="item-entry overflow">
    <h5><a href="property-3.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-2.html" ><img src="assets/img/demo/property-4.jpg"></a>
    </div>
    <div class="item-entry overflow">
    <h5><a href="property-2.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-two proerty-item">
    <div class="item-thumb">
    <a href="property-1.html" ><img src="assets/img/demo/property-3.jpg"></a>
    </div>
    <div class="item-entry overflow">
    <h5><a href="property-1.html" >Super nice villa </a></h5>
    <div class="dot-hr"></div>
    <span class="pull-left"><b>Area :</b> 120m </span>
    <span class="proerty-price pull-right">$ 300,000</span>
    </div>
    </div>
    </div>

    <div class="col-sm-6 col-md-3 p0">
    <div class="box-tree more-proerty text-center">
    <div class="item-tree-icon">
    <i class="fa fa-th"></i>
    </div>
    <div class="more-entry overflow">
    <h5><a href="property-1.html" >CAN'T DECIDE ? </a></h5>
    <h5 class="tree-sub-ttl">Show all properties</h5>
    <button class="btn border-btn more-black" value="All properties">All properties</button>
    </div>
    </div>
    </div>

    </div>
    </div>
    </div>
    </div>

    <!--Welcome area -->
    <div class="Welcome-area">
    <div class="container">
    <div class="row">
    <div class="col-md-12 Welcome-entry col-sm-12">
    <div class="col-md-5 col-md-offset-2 col-sm-6 col-xs-12">
    <div class="welcome_text wow fadeInLeft" data-wow-delay="0.3s" data-wow-offset="100">
    <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
    <!-- /.feature title -->
    <h2>GARO ESTATE </h2>
    </div>
    </div>
    </div>
    </div>
    <div class="col-md-5 col-sm-6 col-xs-12">
    <div class="welcome_services wow fadeInRight" data-wow-delay="0.3s" data-wow-offset="100">
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

    <!--TESTIMONIALS -->
    <div class="testimonial-area recent-property" style="background-color: #FCFCFC; padding-bottom: 15px;">
    <div class="container">
    <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-12 text-center page-title">
    <!-- /.feature title -->
    <h2>Our Customers Said </h2>
    </div>
    </div>

    <div class="row">
    <div class="row testimonial">
    <div class="col-md-12">
    <div id="testimonial-slider">
    <div class="item">
    <div class="client-text">
    <p>Nulla quis dapibus nisl. Suspendisse llam sed arcu ultried arcu ultricies !</p>
    <h4><strong>Ohidul Islam, </strong><i>Web Designer</i></h4>
    </div>
    <div class="client-face wow fadeInRight" data-wow-delay=".9s">
    <img src="assets/img/client-face1.png" alt="">
    </div>
    </div>
    <div class="item">
    <div class="client-text">
    <p>Nulla quis dapibus nisl. Suspendisse llam sed arcu ultried arcu ultricies !</p>
    <h4><strong>Ohidul Islam, </strong><i>Web Designer</i></h4>
    </div>
    <div class="client-face">
    <img src="assets/img/client-face2.png" alt="">
    </div>
    </div>
    <div class="item">
    <div class="client-text">
    <p>Nulla quis dapibus nisl. Suspendisse llam sed arcu ultried arcu ultricies !</p>
    <h4><strong>Ohidul Islam, </strong><i>Web Designer</i></h4>
    </div>
    <div class="client-face">
    <img src="assets/img/client-face1.png" alt="">
    </div>
    </div>
    <div class="item">
    <div class="client-text">
    <p>Nulla quis dapibus nisl. Suspendisse llam sed arcu ultried arcu ultricies !</p>
    <h4><strong>Ohidul Islam, </strong><i>Web Designer</i></h4>
    </div>
    <div class="client-face">
    <img src="assets/img/client-face2.png" alt="">
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
    <h2>You can trust Us </h2>
    </div>
    </div>
    <div class="row">
    <div class="col-md-12 col-xs-12 percent-blocks m-main" data-waypoint-scroll="true">
    <div class="row">
    <div class="col-sm-3 col-xs-6">
    <div class="count-item">
    <div class="count-item-circle">
    <span class="pe-7s-users"></span>
    </div>
    <div class="chart" data-percent="5000">
    <h2 class="percent" id="counter">0</h2>
    <h5>HAPPY CUSTOMER </h5>
    </div>
    </div>
    </div>
    <div class="col-sm-3 col-xs-6">
    <div class="count-item">
    <div class="count-item-circle">
    <span class="pe-7s-home"></span>
    </div>
    <div class="chart" data-percent="12000">
    <h2 class="percent" id="counter1">0</h2>
    <h5>Properties in stock</h5>
    </div>
    </div>
    </div>
    <div class="col-sm-3 col-xs-6">
    <div class="count-item">
    <div class="count-item-circle">
    <span class="pe-7s-flag"></span>
    </div>
    <div class="chart" data-percent="120">
    <h2 class="percent" id="counter2">0</h2>
    <h5>City registered </h5>
    </div>
    </div>
    </div>
    <div class="col-sm-3 col-xs-6">
    <div class="count-item">
    <div class="count-item-circle">
    <span class="pe-7s-graph2"></span>
    </div>
    <div class="chart" data-percent="5000">
    <h2 class="percent" id="counter3">5000</h2>
    <h5>DEALER BRANCHES</h5>
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
    <div class="asks-first">
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
    <p class="asks-call">QUESTIONS? CALL US : <span class="strong"> + 3-123- 424-5700</span></p>
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
    <li><a href="properties.html">Properties</a> </li>
    <li><a href="#">Services</a> </li>
    <li><a href="submit-property.html">Submit property </a></li>
    <li><a href="contact.html">Contact us</a></li>
    <li><a href="faq.html">fqa</a> </li>
    <li><a href="faq.html">Terms </a> </li>
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
    <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
    <h6> <a href="single.html">Add news functions </a></h6>
    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla ...</p>
    </div>
    </li>

    <li>
    <div class="col-md-3 col-sm-4 col-xs-4 blg-thumb p0">
    <a href="single.html">
    <img src="assets/img/demo/small-proerty-2.jpg">
    </a>
    <span class="blg-date">12-12-2016</span>

    </div>
    <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
    <h6> <a href="single.html">Add news functions </a></h6>
    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla ...</p>
    </div>
    </li>

    <li>
    <div class="col-md-3 col-sm-4 col-xs-4 blg-thumb p0">
    <a href="single.html">
    <img src="assets/img/demo/small-proerty-2.jpg">
    </a>
    <span class="blg-date">12-12-2016</span>

    </div>
    <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">
    <h6> <a href="single.html">Add news functions </a></h6>
    <p style="line-height: 17px; padding: 8px 2px;">Lorem ipsum dolor sit amet, nulla ...</p>
    </div>
    </li>


    </ul>
    </div>
    </div>
    <div class="col-md-3 col-sm-6 wow fadeInRight animated">
    <div class="single-footer news-letter">
    <h4>Stay in touch</h4>
    <div class="footer-title-line"></div>
    <p>Lorem ipsum dolor sit amet, nulla suscipit similique quisquam molestias. Vel unde, blanditiis.</p>

    <form>
    <div class="input-group">
    <input class="form-control" type="text" placeholder="E-mail ... ">
    <span class="input-group-btn">
    <button class="btn btn-primary subscribe" type="button"><i class="pe-7s-paper-plane pe-2x"></i></button>
    </span>
    </div>
    <!-- /input-group -->
    </form>

    <div class="social pull-right">
    <ul>
    <li><a class="wow fadeInUp animated" href="https://twitter.com/kimarotec"><i class="fa fa-twitter"></i></a></li>
    <li><a class="wow fadeInUp animated" href="https://www.facebook.com/kimarotec" data-wow-delay="0.2s"><i class="fa fa-facebook"></i></a></li>
    <li><a class="wow fadeInUp animated" href="https://plus.google.com/kimarotec" data-wow-delay="0.3s"><i class="fa fa-google-plus"></i></a></li>
    <li><a class="wow fadeInUp animated" href="https://instagram.com/kimarotec" data-wow-delay="0.4s"><i class="fa fa-instagram"></i></a></li>
    <li><a class="wow fadeInUp animated" href="https://instagram.com/kimarotec" data-wow-delay="0.6s"><i class="fa fa-dribbble"></i></a></li>
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
    <span> (C) <a href="http://www.KimaroTec.com">KimaroTheme</a> , All rights reserved 2016 </span>
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
