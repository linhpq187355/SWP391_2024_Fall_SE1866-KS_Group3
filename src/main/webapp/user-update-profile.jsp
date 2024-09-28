<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/28/2024
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Submit your home</title>
    <meta name="description" content="GARO is a real-estate template">
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
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/wizard.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title">Xin chào : <span class="orange strong">${requestScope.user.firstName} ${requestScope.user.lastName}</span></h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<!-- property area -->
<div class="content-area user-profiel" style="background-color: #FCFCFC;">&nbsp;
    <div class="container">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1 profiel-container">

                <form action="" method="">
                    <div class="profiel-header">
                        <h3>
                            <b>Thông tin của bạn</b>
                        </h3>
                        <hr>
                    </div>

                    <div class="clear">
                        <div class="col-sm-3 col-sm-offset-1">
                            <div class="picture-container">
                                <div class="picture">
                                    <img src="assets/img/user-default-avatar.png" class="picture-src" id="wizardPicturePreview" title=""/>
                                    <input type="file" id="wizard-picture">
                                </div>
                                <h6>Chọn ảnh khác</h6>
                            </div>
                        </div>

                        <div class="col-sm-3 padding-top-25">

                            <div class="form-group">
                                <label>Họ</label>
                                <input name="firstname" type="text" class="form-control" value="${requestScope.user.firstName}">

                            </div>

                            <div class="form-group">
                                <label>Tên</label>
                                <input name="lastname" type="text" class="form-control" value="${requestScope.user.lastName}">

                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <input name="email" type="email" class="form-control" value="${requestScope.user.email}">
                            </div>
                            <div class="form-group">
                                <label>SĐT</label>
                                <input name="phone" type="tel" class="form-control" value="${requestScope.user.phoneNumber}">
                            </div>
                        </div>
                        <div class="col-sm-3 padding-top-25">
                            <div class="form-group">
                                <input name="dob" type="date" class="form-control" value="${requestScope.user.phoneNumber}">
                            </div>
                            <div class="form-group">
                                <label>Xác thực</label>
                                <c:if test="${requestScope.user.isVerified() == false}">
                                    <p>Chưa xác thực</p>
                                </c:if>
                                <c:if test="${requestScope.user.isVerified() == true}">
                                    <p>Đã xác thực <i class="fa-solid fa-circle-check"></i></p>
                                </c:if>
                            </div>
                        </div>

                    </div>

                    <div class="clear">
                        <br>
                        <hr>
                        <br>
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="form-group">
                                <label>Sạch sẽ</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.cleanliness}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cleanliness};">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Hút thuốc</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.smoking}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.smoking};">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Uống rượu/bia</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.drinking}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.drinking};">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="form-group">
                                <label>Hướng ngoại</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.interaction}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.interaction};">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Nấu ăn</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.cooking}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cooking};">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Thú cưng</label>
                                <div class="slider-container">
                                    <input disabled type="range" min="1" max="5" step="1" value="${requestScope.preference.pet}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.pet};">
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-sm-5 col-sm-offset-1">
                        <br>
                        <input type='button' class='btn btn-finish btn-primary' name='Chỉnh sửa thông tin' value='Chỉnh sửa thông tin' />
                    </div>
                    <br>
                </form>

            </div>
        </div><!-- end row -->

    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
