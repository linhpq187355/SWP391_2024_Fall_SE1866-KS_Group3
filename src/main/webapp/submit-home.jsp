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
        .overlay1 {
            display: block;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4); /* Darken slightly */
            backdrop-filter: blur(5px); /* Blur effect */
            z-index: 999;
        }
        #popupPref{
            position: fixed;
            transform: translate(-50%, -50%);
            z-index: 1000;
            top: 50%;
            left: 50%;
            background-color: #fff;
            height: 200px;
            width: 50%;
            border-radius: 15px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->

<jsp:include page="header.jsp"/>


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
<c:if test="${requestScope.preference.cleanliness == 100}">
    <div class="overlay1" id="overlay"></div>
    <div class="container" id="popupPref">
        <h2 style="color: #1A1A1A">Thông báo</h2>
        <p style="margin-top: 10px;font-size: 18px;text-align: center;">Bạn chưa cài đặt thông tin ghép nối. Để được đăng tin, vui lòng cài đặt thông tin ghép nối <span style="color: #FA8600; cursor: pointer" onclick="window.location.href='matching'">tại đây</span></p>
    </div>
</c:if>
<c:if test="${requestScope.preference.cleanliness != 100}">
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
</c:if>


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
