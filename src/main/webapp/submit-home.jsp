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
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css"
          integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
          crossorigin=""/>
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
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
    <style>
        #map {
            width: 100%;
            height: 60%;
        }

        label {
            color: darkgrey;
        }
    </style>
</head>
<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->

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
                    <form action="submit-home" method="post">
                        <ul>
                            <li><a href="#step1" data-toggle="tab">Bước 1 </a></li>
                            <li><a href="#step2" data-toggle="tab">Bước 2 </a></li>
                            <li><a href="#step3" data-toggle="tab">Bước 3 </a></li>
                            <li><a href="#step4" data-toggle="tab">Hoàn tất </a></li>
                        </ul>

                        <div class="tab-content">

                            <div class="tab-pane" id="step1">
                                <div class="row p-b-15  ">
                                    <h4 class="info-text"> THÔNG TIN CƠ BẢN</h4>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Loại hình nhà ở <small>(required)</small></label><br>
                                            <select name="home-type" id="home-type" class="form-control"
                                                    aria-label="VD nhà nguyên căn" required>
                                                <c:forEach var="hometype" items="${homeTypes}">
                                                    <option value="${hometype.id}">${hometype.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Tên nơi ở của bạn <small>(required)</small></label>
                                            <input name="home-name" type="text" class="form-control" required>
                                        </div>
                                    </div>
                                    <div style="margin-left: 15px;">
                                        <select class="form-select form-select-sm mb-3" id="city"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn tỉnh thành</option>
                                            <c:forEach var="province" items="${provinces}">
                                                <option value="${province.id}">${province.name}</option>
                                            </c:forEach>
                                        </select>

                                        <select class="form-select form-select-sm mb-3" id="district"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn quận huyện</option>
                                        </select>

                                        <select class="form-select form-select-sm" id="ward"
                                                aria-label=".form-select-sm">
                                            <option value="" selected>Chọn phường xã</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>Địa chỉ cụ thể <small>(required)</small></label>
                                            <input name="address-detail" type="text" class="form-control" required>
                                        </div>
                                    </div>

                                    <%--                  <div class="col-sm-12">--%>
                                    <%--                    <div class="form-group">--%>
                                    <%--                      <label>Vị trí trên bản đồ <small>(required)</small></label>--%>
                                    <%--                      <div id="map"></div>--%>
                                    <%--                    </div>--%>
                                    <%--                  </div>--%>
                                </div>
                            </div>
                            <!--  End step 1 -->

                            <div class="tab-pane" id="step2">
                                <h4 class="info-text">THÔNG TIN NƠI Ở</h4>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label>Mô tả về nơi ở của bạn:</label>
                                                <textarea name="home-description" class="form-control"
                                                          required></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label>Mô tả về người bạn muốn ở ghép:</label>
                                                <textarea name="tenant-description" class="form-control"
                                                          required></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Diện tích nơi ở (mét vuông) :</label>
                                                <input type="number" class="form-control" id="area" name="area"
                                                       step="0.01" min="10" max="1000" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Hướng nhà :</label>
                                                <select id="direction" name="direction" class="selectpicker"
                                                        data-live-search="true"
                                                        data-live-search-style="begins" title="Chọn hướng nhà" required>
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
                                                <label>Thời gian cho ở ghép :</label>
                                                <input type="number" class="form-control" id="leaseDuration"
                                                       name="leaseDuration" placeholder="Chọn thời gian theo tháng"
                                                       min="1" max="12" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-3">
                                            <div class="form-group">
                                                <label>Ngày chuyển đến :</label>
                                                <input type="date" id="moveInDate" name="moveInDate" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-12 padding-top-15">
                                        <div class="col-sm-4">
                                            <div class="form-group">
                                                <label>Số phòng ngủ :</label>
                                                <input type="number" class="form-control" id="numOfBedroom"
                                                       name="numOfBedroom" min="1" max="10" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">

                                            <div class="form-group">
                                                <label>Số nhà vệ sinh :</label>
                                                <input type="number" class="form-control" id="numOfBath"
                                                       name="numOfBath" min="1" max="10" required>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">

                                            <div class="form-group">
                                                <label>Mức giá theo tháng (VND) :</label>
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

                            <%--              <div class="tab-pane" id="step3">--%>
                            <%--                <h4 class="info-text">HÌNH ẢNH</h4>--%>
                            <%--                <div class="row">--%>
                            <%--                  <div class="col-sm-12">--%>
                            <%--                    <div class="form-group">--%>
                            <%--                      <label for="imageUpload">Chọn hình ảnh <small>(có thể chọn nhiều hình ảnh)</small></label>--%>
                            <%--                      <input type="file" id="imageUpload" name="imageUpload"  class="form-control" multiple accept="image/*">--%>
                            <%--                    </div>--%>
                            <%--                  </div>--%>
                            <%--                </div>--%>
                            <%--              </div>--%>
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
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox"/> <strong>Tôi đồng ý các điều khoản và điều
                                                    kiện.</strong>
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
<script src="https://unpkg.com/leaflet@1.4.0/dist/leaflet.js"
        integrity="sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg=="
        crossorigin=""></script>
<script>
    $(document).ready(function () {
        var mapObj = null;
        var defaultCoord = [21.0819, 105.6363]; // coord mặc định, Hà Nội
        var zoomLevel = 16; // Mức phóng to bản đồ
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
<script>
    $(document).ready(function() {
        $('#province').change(function() {
            let provinceId = $(this).val();
            if (provinceId) {
                $.get('LocationServlet', {action: 'getDistricts', parentId: provinceId}, function(data) {
                    $('#district').empty().append('<option value="">Chọn quận huyện</option>');
                    $('#ward').empty().append('<option value="">Chọn phường xã</option>');
                    $.each(data, function(index, item) {
                        $('#district').append($('<option></option>').val(item.id).text(item.name));
                    });
                });
            } else {
                $('#district').empty().append('<option value="">Chọn quận huyện</option>');
                $('#ward').empty().append('<option value="">Chọn phường xã</option>');
            }
        });

        $('#district').change(function() {
            let districtId = $(this).val();
            if (districtId) {
                $.get('LocationServlet', {action: 'getWards', parentId: districtId}, function(data) {
                    $('#ward').empty().append('<option value="">Chọn phường xã</option>');
                    $.each(data, function(index, item) {
                        $('#ward').append($('<option></option>').val(item.id).text(item.name));
                    });
                });
            } else {
                $('#ward').empty().append('<option value="">Chọn phường xã</option>');
            }
        });
    });
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
<script src="assets/js/administrative-territory.js"></script>
<script src="assets/js/submisson-form-validation.js"></script>
</body>
</html>
