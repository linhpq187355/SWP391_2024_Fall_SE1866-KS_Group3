<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/25/2024
  Time: 10:32 PM
  To change this template use File | Settings | File Templates.
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
        crossorigin="" />
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
            <!-- <div class="wizard-header">
              <h3>
                ĐĂNG TIN Ở GHÉP <br>
                <small>Vui lòng điền các thông tin về nơi bạn đang thuê ở biểu mẫu dưới đây.</small>
              </h3>
            </div> -->

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
                      <select name="home-type" id="home-type" class="form-control" aria-label="VD nhà nguyên căn">
                        <option value="volvo">Studio</option>
                        <option value="saab">Dorm</option>
                        <option value="mercedes">Home</option>
                      </select>
                    </div>
                  </div>

                  <div class="col-sm-4">
                    <div class="form-group">
                      <label>Tỉnh/thành phố <small>(required)</small></label><br>
                      <select id="province" class="selectpicker" data-live-search="true"
                              data-live-search-style="begins" title="Chọn tỉnh/thành phố">
                        <option value="hn">Hà Nội</option>
                        <option value="tphcm">HCM</option>
                        <option value="hue">Huế</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label>Quận/huyện <small>(required)</small></label><br>
                      <select id="district" name="district" class="selectpicker" data-live-search="true"
                              data-live-search-style="begins" title="Chọn quận/huyện">
                        <option value="hn">Thành phố TQ</option>
                        <option value="tphcm">HCM</option>
                        <option value="hue">Huế</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label>Xã/phường <small>(required)</small></label><br>
                      <select name="ward" id="ward" class="selectpicker" data-live-search="true"
                              data-live-search-style="begins" title="Chọn xã/phường">
                        <option value="hn">Phường Ỷ La</option>
                        <option value="tphcm">HCM</option>
                        <option value="hue">Huế</option>
                      </select>
                    </div>
                  </div>

                  <div class="col-sm-12">
                    <div class="form-group">
                      <label>Địa chỉ trên tin đăng <small>(required)</small></label>
                      <input name="address-detail" type="text" class="form-control"
                             placeholder="Phường Ỷ La, thành phố TQ, tỉnh TQ">
                    </div>
                  </div>

                  <div class="col-sm-12">
                    <div class="form-group">
                      <label>Vị trí trên bản đồ <small>(required)</small></label>
                      <div id="map"></div>
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
                        <label>Mô tả về nơi ở của bạn:</label>
                        <textarea name="discrition" class="form-control"></textarea>
                      </div>
                      <div class="form-group">
                        <label>Mô tả về người bạn muốn ở ghép:</label>
                        <textarea name="discrition" class="form-control"></textarea>
                      </div>
                    </div>
                  </div>

                  <div class="col-sm-12">
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>Diện tích nơi ở (mét vuông) :</label>
                        <input type="number" class="form-control" id="area" name="area" step="0.01" min="10" max="1000" required>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>Hướng nhà :</label>
                        <select id="lunchBegins" class="selectpicker" data-live-search="true"
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
                        <label>Thời gian cho ở ghép (theo tháng) :</label>
                        <input type="number" class="form-control" id="leaseDuration" name="leaseDuration" min="1" max="12" required>
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
                        <input type="number" class="form-control" id="numOfBedroom" name="numOfBedroom" min="1" max="10" required>
                      </div>
                    </div>
                    <div class="col-sm-4">

                      <div class="form-group">
                        <label>Số nhà vệ sinh :</label>
                        <input type="number" class="form-control" id="numOfBath" name="numOfBath" min="1" max="10" required>
                      </div>
                    </div>
                    <div class="col-sm-4">

                      <div class="form-group">
                        <label>Mức giá theo tháng (VND) :</label>
                        <input type="number" class="form-control" id="price" name="price" min="1000000" step="20000000" required>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 padding-top-15">
                    <h5>Tiện ích nơi ở: </h5>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Swimming Pool
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> 2 Stories
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Emergency Exit
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Fire Place
                          </label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 padding-bottom-15">
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Laundry Room
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Jog Path
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Ceilings
                          </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <div class="checkbox">
                          <label>
                            <input type="checkbox"> Dual Sinks
                          </label>
                        </div>
                      </div>
                    </div>
                  </div>
                  <br>
                </div>
              </div>
              <!-- End step 2 -->

              <div class="tab-pane" id="step3">
                <h4 class="info-text">HÌNH ẢNH</h4>
                <div class="row">
                  <div class="col-sm-12">
                    <div class="upload__box">
                      <div class="upload__btn-box">
                        <label class="upload__btn">
                          <p>Chọn ảnh: </p>
                          <input type="file" multiple="" data-max_length="20" class="upload__inputfile">
                          <p>Đăng tối thiểu 3 ảnh, tối đa 24 ảnh với tất cả các loại tin</p>
                        </label>
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

                      <div class="checkbox">
                        <label>
                          <input type="checkbox" /> <strong>Tôi đồng ý các điều khoản và điều kiện.</strong>
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
                <input type='button' class='btn btn-next btn-primary' name='next' value='Next' />
                <input type='button' class='btn btn-finish btn-primary ' name='finish' value='Finish' />
              </div>

              <div class="pull-left">
                <input type='button' class='btn btn-previous btn-default' name='previous' value='Previous' />
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

</body>
</html>
