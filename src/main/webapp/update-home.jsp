<%--
  Created by IntelliJ IDEA.
  User: ThangLT
  Date: 10/19/2024
  Time: 9:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<%--<div id="preloader">--%>
<%--    <div id="status">&nbsp;</div>--%>
<%--</div>--%>
<!-- Body content -->

<jsp:include page="header.jsp"/>

<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title">Cập nhật tin đăng</h1>
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
                        <div class="tab-content">
                            <div class="tab-pane" id="step1">
                                <div class="row p-b-15  ">
                                    <h4 class="info-text"> THÔNG TIN CƠ BẢN</h4>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Loại hình nhà ở <small>(*)</small></label><br>
                                            <select name="home-type" id="home-type" class="form-control"
                                                    required>
                                                <c:forEach var="hometype" items="${hometypes}">
                                                    <option value="${hometype.id}"
                                                            <c:if test="${hometype.id == home.homeTypeId}">selected</c:if>>
                                                            ${hometype.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Tên nơi ở của bạn <small>(*)</small></label>
                                            <input name="home-name" value="${home.name}" type="text"
                                                   class="form-control" required>
                                        </div>
                                    </div>
                                    <div style="margin-left: 15px;">
                                        <select class="form-select form-select-sm mb-3" name="province" id="province"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn tỉnh thành</option>
                                            <c:forEach var="province" items="${provinces}">
                                                <option value="${province.id}"
                                                        <c:if test="${province.id == selectedProvince.id}">selected</c:if>>
                                                        ${province.name}
                                                </option>
                                            </c:forEach>
                                        </select>

                                        <select class="form-select form-select-sm mb-3" name="district" id="district"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn quận huyện</option>
                                        </select>

                                        <select class="form-select form-select-sm" name="ward" id="ward"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn phường xã</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Địa chỉ cụ thể <small>(*)</small></label>
                                            <input name="address-detail" type="text" class="form-control"
                                                   value="${home.address}" required>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Vui lòng chọn vị trí trên bản đồ</label>
                                            <div id="map"></div>
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
                                                          required>${home.homeDescription}</textarea>
                                            </div>
                                            <div class="form-group">
                                                <label>Mô tả về người bạn muốn ở ghép <small>(*)</small></label>
                                                <textarea name="tenant-description" class="form-control"
                                                          required>${home.tenantDescription}</textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Diện tích nơi ở (mét vuông) <small>(*)</small></label>
                                                <input type="number" class="form-control" id="area" name="area"
                                                       step="0.01" min="10" max="1000" value="${home.area}" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Hướng nhà :</label>
                                                <select id="direction" name="direction" class="selectpicker"
                                                        data-live-search="true"
                                                        data-live-search-style="begins" title="Chọn hướng nhà">
                                                    <option ${home.orientation == 'Hướng Đông' ? 'selected' : ''}>Hướng
                                                        Đông
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Tây' ? 'selected' : ''}>Hướng
                                                        Tây
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Nam' ? 'selected' : ''}>Hướng
                                                        Nam
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Bắc' ? 'selected' : ''}>Hướng
                                                        Bắc
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Tây Bắc' ? 'selected' : ''}>Hướng
                                                        Tây Bắc
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Đông Bắc' ? 'selected' : ''}>
                                                        Hướng Đông Bắc
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Đông Nam' ? 'selected' : ''}>
                                                        Hướng Đông Nam
                                                    </option>
                                                    <option ${home.orientation == 'Hướng Tây Nam' ? 'selected' : ''}>Hướng
                                                        Tây Nam
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Thời gian cho ở ghép <small>(*)</small></label>
                                                <input type="number" class="form-control" id="leaseDuration"
                                                       name="leaseDuration" placeholder="Chọn thời gian theo tháng"
                                                       min="1" max="12" value="${home.leaseDuration}" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Ngày chuyển đến <small>(*)</small></label>
                                                <input type="date" id="moveInDate" name="moveInDate"
                                                       value="${home.moveInDate}" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 padding-top-15">
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Số phòng ngủ <small>(*)</small></label>
                                                <input type="number" class="form-control" id="numOfBedroom"
                                                       name="numOfBedroom" value="${home.numOfBedroom}" min="1" max="10"
                                                       required>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">

                                            <div class="form-group">
                                                <label>Số nhà vệ sinh <small>(*)</small></label>
                                                <input type="number" class="form-control" id="numOfBath"
                                                       name="numOfBath" min="1" max="10" value="${home.numOfBath}"
                                                       required>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">

                                            <div class="form-group">
                                                <label>Mức giá theo tháng (VND) <small>(*)</small></label>
                                                <input type="number" class="form-control" id="price" name="price"
                                                       value="${home.price}" min="1000000" step="20000000" required>
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
                                                                   value="${amentity.id}"
                                                            <c:if test="${home.amentities.contains(amentity)}">
                                                                   checked</c:if>>
                                                                ${amentity.name}
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
                                                                   value="${fireEquip.id}"
                                                                   <c:if test="${home.fireEquipments.contains(fireEquip)}">checked</c:if>>
                                                                ${fireEquip.name}
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
                                            <div id="imagePreview" class="mt-3">
                                                <c:forEach var="image" items="${images}">
                                                    <img src="${image.url}" alt="${image.name}" width="100" height="100">
                                                </c:forEach>
                                            </div>
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
                                                    <input type="checkbox" required/> <strong>Tôi đồng ý các điều khoản
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

<div class="footer-area">
    <div class="footer">
        <div class="container">
            <div class="row">

                <div class="col-md-3 col-sm-6 wow fadeInRight animated">
                    <div class="single-footer">
                        <h4>About us </h4>
                        <div class="footer-title-line"></div>

                        <img src="assets/img/footer-logo.png" alt="" class="wow pulse" data-wow-delay="1s">
                        <p>Lorem ipsum dolor cum necessitatibus su quisquam molestias. Vel unde, blanditiis.</p>
                        <ul class="footer-adress">
                            <li><i class="pe-7s-map-marker strong"> </i> FPT university</li>
                            <li><i class="pe-7s-mail strong"> </i> rommifyy@gmail.com</li>
                            <li><i class="pe-7s-call strong"> </i> 0989843097</li>
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
                            <li><a href="about-us.jsp">About Us</a></li>
                            <li><a href="terms.jsp">Terms </a></li>
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
<jsp:include page="footer.jsp"/>
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
