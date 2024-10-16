<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/28/2024
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
        .required{
            color:red;
        }
    </style>
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

                <form id="preferenceForm" action="user-update-profile" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
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
                                    <c:if test="${empty requestScope.user.avatar}">
                                        <img src="assets/img/user-default-avatar.png" class="picture-src" id="wizardPicturePreview" title=""/>
                                    </c:if>
                                    <c:if test="${not empty requestScope.user.avatar}">
                                        <img src="${requestScope.user.avatar}" class="picture-src" id="wizardPicturePreview" title=""/>
                                    </c:if>
                                    <input type="file" id="wizard-picture" name="avatar" accept="image/*">
                                </div>
                                <h6>Chọn ảnh khác</h6>
                            </div>
                        </div>

                        <div class="col-sm-3 padding-top-25">

                            <div class="form-group">
                                <label>Họ <span class="required">*</span></label>
                                <input name="firstname" type="text" class="form-control" value="${requestScope.user.firstName}">
                                <span class="error-message" id="error-firstname"></span>
                            </div>

                            <div class="form-group">
                                <label>Tên <span class="required">*</span></label>
                                <input name="lastname" type="text" class="form-control" value="${requestScope.user.lastName}">
                                <span class="error-message" id="error-lastname"></span>
                            </div>

                            <div class="form-group">
                                <label>Địa chỉ <span class="required">*</span></label>
                                <input name="address" type="text" class="form-control" value="${requestScope.user.address}">
                                <span class="error-message" id="error-address"></span>
                            </div>


                        </div>
                        <div class="col-sm-3 padding-top-25">
                            <div class="form-group">
                                <label>Ngày sinh <span class="required">*</span></label>
                                <input name="dob" type="date" class="form-control" value="${requestScope.user.dob}">
                                <span class="error-message" id="error-dob"></span>
                            </div>
                            <div class="form-group">
                                <label>Giới tính <span class="required">*</span></label>
                                <select name="gender" class="form-control">
                                    <option value="Nam" ${requestScope.user.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                    <option value="Nữ" ${requestScope.user.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                    <option value="Khác" ${requestScope.user.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                                </select>
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
                                    <input id="cleanliness-slider" name="cleanliness" type="range" min="1" max="5" step="1" value="${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''}" class="slider" style="--value: ${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''};">
                                    <input type="hidden" id="cleanlinessStatus" name="cleanlinessStatus" value="${requestScope.preference.cleanliness != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Hút thuốc</label>
                                <div class="slider-container">
                                    <input id="smoking-slider" name="smoking" type="range" min="1" max="5" step="1" value="${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''}" class="slider"  style="--value: ${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''};">
                                    <input type="hidden" id="smokingStatus" name="smokingStatus" value="${requestScope.preference.smoking != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Uống rượu/bia</label>
                                <div class="slider-container">
                                    <input id="drinking-slider" name="drinking" type="range" min="1" max="5" step="1" value="${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''}" class="slider"  style="--value: ${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''};">
                                    <input type="hidden" id="drinkingStatus" name="drinkingStatus" value="${requestScope.preference.drinking != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div class="form-group">
                                <label>Hướng ngoại</label>
                                <div class="slider-container">
                                    <input id="interaction-slider" name="interaction" type="range" min="1" max="5" step="1" value="${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''}" class="slider" style="--value: ${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''};">
                                    <input type="hidden" id="interactionStatus" name="interactionStatus" value="${requestScope.preference.interaction != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Nấu ăn</label>
                                <div class="slider-container">
                                    <input id="cooking-slider" name="cooking" type="range" min="1" max="5" step="1" value="${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''}" class="slider"  style="--value: ${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''};">
                                    <input type="hidden" id="cookingStatus" name="cookingStatus" value="${requestScope.preference.cooking != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Thú cưng</label>
                                <div class="slider-container">
                                    <input id="pet-slider" name="pet" type="range" min="1" max="5" step="1" value="${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''}" class="slider"  style="--value: ${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''};">
                                    <input type="hidden" id="petStatus" name="petStatus" value="${requestScope.preference.pet != 100 ? 'true' : 'false'}">
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-sm-5 col-sm-offset-1">
                        <br>
                        <button type='submit' class='btn btn-finish btn-primary'>Cập nhật</button>
                    </div>

                    <br>
                </form>
                <button class='btn btn-danger' onClick="window.location.href='user-profile'">Hủy</button>

            </div>
        </div><!-- end row -->

    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    document.getElementById("wizard-picture").addEventListener("change", function(event) {
        var input = event.target;
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                // Set the src of the image to the file uploaded
                document.getElementById('wizardPicturePreview').src = e.target.result;
            };

            // Read the image file as a data URL.
            reader.readAsDataURL(input.files[0]);
        }
    });
    document.getElementById('cleanliness-slider').addEventListener('input', function() {
        document.getElementById('cleanlinessStatus').value = 'true';
    });
    document.getElementById('smoking-slider').addEventListener('input', function() {
        document.getElementById('smokingStatus').value = 'true';
    });
    document.getElementById('drinking-slider').addEventListener('input', function() {
        document.getElementById('drinkingStatus').value = 'true';
    });
    document.getElementById('interaction-slider').addEventListener('input', function() {
        document.getElementById('interactionStatus').value = 'true';
    });
    document.getElementById('cooking-slider').addEventListener('input', function() {
        document.getElementById('cookingStatus').value = 'true';
    });
    document.getElementById('pet-slider').addEventListener('input', function() {
        document.getElementById('petStatus').value = 'true';
    });
    function validateForm() {
        // Clear previous error messages
        document.getElementById("error-firstname").innerHTML = "";
        document.getElementById("error-lastname").innerHTML = "";
        document.getElementById("error-address").innerHTML = "";
        document.getElementById("error-dob").innerHTML = "";

        const form = document.forms["preferenceForm"];
        const firstName = form["firstname"].value.trim();
        const lastName = form["lastname"].value.trim();
        const address = form["address"].value.trim();
        const dob = new Date(form["dob"].value);
        const today = new Date();

        let isValid = true;

        // Validate first name
        if (firstName === "") {
            document.getElementById("error-firstname").innerHTML = "Họ không được để trống";
            isValid = false;
        } else if (firstName.length > 50) {
            document.getElementById("error-firstname").innerHTML = "Họ không được vượt quá 50 ký tự";
            isValid = false;
        }

        // Validate last name
        if (lastName === "") {
            document.getElementById("error-lastname").innerHTML = "Tên không được để trống";
            isValid = false;
        } else if (lastName.length > 50) {
            document.getElementById("error-lastname").innerHTML = "Tên không được vượt quá 50 ký tự";
            isValid = false;
        }

        // Validate address
        if (address === "") {
            document.getElementById("error-address").innerHTML = "Địa chỉ không được để trống";
            isValid = false;
        } else if (address.length > 100) {
            document.getElementById("error-address").innerHTML = "Địa chỉ không được vượt quá 100 ký tự";
            isValid = false;
        }

        // Validate date of birth
        if (dob > today) {
            document.getElementById("error-dob").innerHTML = "Ngày sinh không được trong tương lai";
            isValid = false;
        }

        return isValid;
    }
</script>
</body>
</html>
