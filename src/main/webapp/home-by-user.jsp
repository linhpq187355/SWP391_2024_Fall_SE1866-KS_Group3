<%--
  Created by ManhNC.
  User: Admin
  Date: 9/25/2024
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ex" uri="http://example.com/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Danh sách nhà</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="Kimarotec">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>

    <!-- Place favicon.ico  the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">

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
    <link rel="stylesheet" href="assets/css/clean-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <script src="assets/js/price-range.js"></script>
    <style>
        .pagination a {
            text-decoration: none;
            color: #d18e11; /* Màu mặc định cho liên kết */
            padding: 8px 12px;
            margin: 0 4px;
        }

        .pagination a.active {
            background-color: #d18e11; /* Màu nền khi active */
            color: white; /* Màu chữ khi active */
            border-radius: 4px; /* Bo góc */
        }

        .pagination a:hover {
            background-color: #d18e11; /* Màu nền khi hover */
            color: white; /* Màu chữ khi hover */
        }

        .item-thumb {
            width: 100%;
            height: 250px; /* Chiều cao cố định của khung chứa */
            position: relative;
        }

        .property-image {
            width: 100%; /* Điều chỉnh cho ảnh đầy chiều rộng của thẻ bao quanh */
            height: 100%; /* Hoặc bạn có thể đặt kích thước cố định tùy thuộc vào yêu cầu */
            object-fit: cover; /* Giúp cắt ảnh nếu vượt quá chiều rộng hoặc chiều cao, mà vẫn giữ tỉ lệ */
        }

        /* Đặt chiều rộng cho các dropdown */
        .form-select {
            width: 100%; /* Giúp dropdown chiếm toàn bộ chiều rộng của container */
            max-width: 400px; /* Giới hạn chiều rộng tối đa */
            padding: 10px; /* Thêm khoảng đệm cho dropdown */
            border: 1px solid #ccc; /* Đường viền nhẹ nhàng */
            border-radius: 4px; /* Bo tròn các góc */
            font-size: 14px; /* Kích thước font chữ */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Thêm bóng nhẹ */
            transition: border-color 0.3s; /* Hiệu ứng chuyển đổi cho viền */
        }

        /* Thay đổi màu viền khi dropdown được chọn */
        .form-select:focus {
            border-color: #007bff; /* Màu viền khi đang chọn */
            outline: none; /* Loại bỏ viền mặc định */
        }

        /* Đặt khoảng cách giữa các fieldset */
        fieldset {
            margin-bottom: 15px; /* Khoảng cách giữa các trường */
            border: none; /* Bỏ đường viền fieldset */
        }

        /* Tùy chỉnh tiêu đề cho các fieldset */
        fieldset legend {
            font-weight: bold; /* In đậm tiêu đề */
            margin-bottom: 10px; /* Khoảng cách giữa tiêu đề và dropdown */
        }

        .home-name.two-line {
            display: -webkit-box;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            -webkit-line-clamp: 2;
            line-height: 1.2;
            height: 2.4em;
        }

        .home-name.one-line {
            min-height: 2.4em; /* Đảm bảo rằng tên chỉ chiếm 1 dòng sẽ có thêm 1 dòng trống */

        }

    </style>
    <style>
        .picture-container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .picture {
            width: 150px; /* Tăng chiều rộng avatar */
            height: 150px; /* Tăng chiều cao avatar */
            overflow: hidden;
            border-radius: 50%; /* Tạo hình tròn cho avatar */
            margin-bottom: 10px; /* Khoảng cách với tên và số điện thoại */
        }

        .picture img {
            width: 100%; /* Đảm bảo ảnh lấp đầy khung chứa */
            height: 100%; /* Đảm bảo ảnh lấp đầy khung chứa */
            object-fit: cover; /* Cắt ảnh theo khung chứa */
        }

        .user-info {
            text-align: center;
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
                <h1 class="page-title"></h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<!-- property area -->
<div class="properties-area recent-property" style="background-color: #FFF;">
    <div class="container">
        <div class="row">
            <div class="col-md-3 p0 padding-top-40">
                <div class="blog-asside-right pr0">
                    <div class="panel panel-default sidebar-menu wow fadeInRight animated">
                        <div class="panel-body search-widget">
                            <div class="panel-heading">
                                <div class="picture-container">
                                    <div class="picture">
                                        <c:if test="${empty requestScope.user.avatar}">
                                            <img src="assets/img/user-default-avatar.png" class="picture-src"
                                                 id="wizardPicturePreview" title=""/>
                                        </c:if>
                                        <c:if test="${not empty requestScope.user.avatar}">
                                            <img src="${requestScope.user.avatar}" class="picture-src"
                                                 id="wizardPicturePreview" title=""/>
                                        </c:if>
                                    </div>
                                    <div class="user-info">
                                        <p>${requestScope.user.firstName} ${requestScope.user.lastName}</p>
                                        Giới tính: <span>${requestScope.user.gender}</span>
                                    </div>
                                </div>
                            </div>


                            <fieldset class="padding-5">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label>Sạch sẽ</label>
                                            <div class="slider-container">
                                                <c:if test="${requestScope.preference.cleanliness == 100}">
                                                    <p>Chưa cập nhật</p>
                                                </c:if>
                                                <c:if test="${requestScope.preference.cleanliness != 100}">
                                                    <input disabled type="range" min="1" max="5" step="1"
                                                           value="${requestScope.preference.cleanliness}"
                                                           class="slider"
                                                           oninput="this.style.setProperty('--value', this.value)"
                                                           style="--value: ${requestScope.preference.cleanliness}; width: 250px;"> <!-- Đặt width ở đây -->
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-xs-12">

                                        <div class="form-group">
                                            <label>Uống rượu/bia</label>
                                            <div class="slider-container">
                                                <c:if test="${requestScope.preference.drinking == 100}">
                                                    <p>Chưa cập nhật</p>
                                                </c:if>
                                                <c:if test="${requestScope.preference.drinking != 100}">
                                                    <input disabled type="range" min="1" max="5" step="1"
                                                           value="${requestScope.preference.drinking}"
                                                           class="slider"
                                                           oninput="this.style.setProperty('--value', this.value)"
                                                           style="--value: ${requestScope.preference.drinking};width: 250px;">
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-xs-12">
                                        <label>Hướng ngoại</label>
                                        <div class="slider-container">
                                            <c:if test="${requestScope.preference.interaction ==100}">
                                                <p>Chưa cập nhật</p>
                                            </c:if>
                                            <c:if test="${requestScope.preference.interaction != 100}">
                                                <input disabled type="range" min="1" max="5" step="1"
                                                       value="${requestScope.preference.interaction}"
                                                       class="slider"
                                                       oninput="this.style.setProperty('--value', this.value)"
                                                       style="--value: ${requestScope.preference.interaction};width: 250px;">
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <label>Nấu ăn</label>
                                        <div class="slider-container">
                                            <c:if test="${requestScope.preference.cooking ==100}">
                                                <p>Chưa cập nhật</p>
                                            </c:if>
                                            <c:if test="${requestScope.preference.cooking != 100}">
                                                <input disabled type="range" min="1" max="5" step="1"
                                                       value="${requestScope.preference.cooking}"
                                                       class="slider"
                                                       oninput="this.style.setProperty('--value', this.value)"
                                                       style="--value: ${requestScope.preference.cooking};width: 250px;">
                                            </c:if>
                                        </div>
                                    </div>

                                    <div class="col-xs-12">
                                        <label>Thú cưng</label>
                                        <div class="slider-container">
                                            <c:if test="${requestScope.preference.pet ==100}">
                                                <p>Chưa cập nhật</p>
                                            </c:if>
                                            <c:if test="${requestScope.preference.pet != 100}">
                                                <input disabled type="range" min="1" max="5" step="1"
                                                       value="${requestScope.preference.pet}"
                                                       class="slider"
                                                       oninput="this.style.setProperty('--value', this.value)"
                                                       style="--value: ${requestScope.preference.pet};width: 250px;">
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <label>Hút thuốc</label>
                                        <div class="slider-container">
                                            <c:if test="${requestScope.preference.smoking == 100}">
                                                <p>Chưa cập nhật</p>
                                            </c:if>
                                            <c:if test="${requestScope.preference.smoking != 100}">
                                                <input disabled type="range" min="1" max="5" step="1"
                                                       value="${requestScope.preference.smoking}" class="slider"
                                                       oninput="this.style.setProperty('--value', this.value)"
                                                       style="--value: ${requestScope.preference.smoking};width: 250px;">
                                            </c:if>
                                        </div>
                                    </div>

                                </div>
                            </fieldset>


                            <!-- Hidden inputs để lưu thông tin sắp xếp -->
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-9 pr0 padding-top-40 properties-page">
                <div class="col-md-12 clear">
                    <div class="col-xs-2 layout-switcher">
                        <a class="layout-list" href="javascript:void(0);"> <i class="fa fa-th-list"></i> </a>
                        <a class="layout-grid " href="javascript:void(0);"> <i class="fa fa-th"></i> </a>
                    </div><!--/ .layout-switcher-->
                </div>

                <div class="col-md-12 clear">
                    <div id="list-type" class="proerty-th">
                        <c:forEach items="${requestScope.homes}" var="homes">
                            <div class="col-sm-6 col-md-3 p0">
                                <div class="box-two proerty-item">
                                    <div class="item-thumb">
                                        <!-- Update the href to point to home detail page with home ID -->
                                        <a href="home-detail?id=${homes.id}">
                                            <img class="property-image"
                                                 src="${homes.images != null && !homes.images.isEmpty() ? homes.images[0] : 'assets/img/demo/property-1.jpg'}">
                                        </a>
                                    </div>
                                    <div class="item-entry overflow">
                                        <h5><a href="home-detail?id=${homes.id}" class="home-name">${homes.name}</a>
                                        </h5>
                                        <div class="dot-hr"></div>
                                        <span class="pull-left"><i class="fa-solid fa-chart-area"
                                                                   style="color: #ffa500;"></i> ${homes.area}m²</span>
                                        <c:forEach items="${requestScope.price}" var="price">
                                            <c:if test="${price.homesId == homes.id }">
                                                <span class="proerty-price pull-right" style="color: #ffa500;">${ex:convertPriceToVND(price.price)}</span>

                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="pull-right">
                        <div class="pagination">
                            <ul id="paginationLinks">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<jsp:include page="footer.jsp"/>

<script>
    $(document).ready(function () {
        $('#province').change(function () {
            var provinceId = $(this).val();

            // Gửi yêu cầu AJAX để lấy danh sách quận huyện
            $.ajax({
                url: 'getDistricts', // Địa chỉ đến servlet hoặc API
                type: 'GET',
                data: {provinceId: provinceId},
                success: function (data) {
                    var selectedDistrict = $('#selectedDistrict').val();
                    $('#district').empty(); // Xóa danh sách quận huyện cũ
                    $('#district').append('<option value="" selected>Chọn quận huyện</option>');
                    $.each(data, function (index, district) {
                        var selected = district.id == selectedDistrict;
                        $('#district').append(
                            $('<option></option>').val(district.id).text(district.name).prop('selected', selected)
                        );
                    });
                    if (selectedDistrict) {
                        $('#district').val(selectedDistrict).change(); // Cập nhật giá trị và kích hoạt sự kiện change
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Có lỗi xảy ra:', error);
                }
            });
        });

        // Khi quận huyện được chọn
        $('#district').change(function () {
            var districtId = $(this).val();

            // Gửi yêu cầu AJAX để lấy danh sách phường xã
            $.ajax({
                url: 'getWards', // Địa chỉ đến servlet hoặc API
                type: 'GET',
                data: {districtId: districtId},
                success: function (data) {
                    var selectedWard = $('#selectedWard').val();
                    $('#ward').empty(); // Xóa danh sách phường xã cũ
                    $('#ward').append('<option value="" selected>Chọn phường xã</option>');
                    $.each(data, function (index, ward) {
                        var selected = ward.id == selectedWard;
                        $('#ward').append(
                            $('<option></option>').val(ward.id).text(ward.name).attr('selected', selected)
                        );
                    });
                    if (selectedWard) {
                        $('#ward').val(selectedWard);
                    }
                },
                error: function (xhr, status, error) {
                    console.error('Có lỗi xảy ra:', error);
                }
            });
        });

        // Tự động gọi thay đổi tỉnh thành nếu có giá trị sẵn
        var selectedProvince = $('#province').val();
        if (selectedProvince) {
            $('#province').val(selectedProvince).trigger('change');
        }

        var selectedDistrict = $('#selectedDistrict').val();
        if (selectedDistrict) {
            $('#district').val(selectedDistrict).trigger('change'); // Cũng kích hoạt để cập nhật ward nếu có
        }

        function renderPagination(currentPage) {
            var paginationLinks = document.getElementById('paginationLinks');
            paginationLinks.innerHTML = ''; // Xóa các link cũ

            // Tạo nút Prev
            if (currentPage > 1) {
                paginationLinks.innerHTML += '<li><a href="#" data-page="prev">Prev</a></li>';
            }

            // Tính toán khoảng trang hiển thị
            var startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
            var endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

            // Điều chỉnh nếu endPage chạm giới hạn
            if (endPage - startPage < maxVisiblePages - 1) {
                startPage = Math.max(1, endPage - maxVisiblePages + 1);
            }

            // Tạo các link trang
            for (var i = startPage; i <= endPage; i++) {
                paginationLinks.innerHTML += '<li><a href="#" data-page="' + i + '"' + (i === currentPage ? ' class="active"' : '') + '>' + i + '</a></li>';
            }

            // Tạo nút Next
            if (currentPage < totalPages) {
                paginationLinks.innerHTML += '<li><a href="#" data-page="next">Next</a></li>';
            }
        }

        // Xử lý sự kiện click pagination
        document.getElementById('paginationLinks').addEventListener('click', function (event) {
            event.preventDefault();
            var clickedPage = event.target.getAttribute('data-page');
            var currentPage = parseInt(document.getElementById('currentPage').value);

            if (clickedPage === 'prev') {
                currentPage = currentPage > 1 ? currentPage - 1 : 1;
            } else if (clickedPage === 'next') {
                currentPage = currentPage < totalPages ? currentPage + 1 : totalPages;
            } else {
                currentPage = parseInt(clickedPage);
            }

            // Cập nhật hidden input và render lại pagination
            document.getElementById('currentPage').value = currentPage;
            document.getElementById('targetPage').value = currentPage;
            renderPagination(currentPage);

            // Submit form (nếu muốn tải lại dữ liệu mới)
            document.querySelector('form').submit();
        });

        function updateItemsPerPage() {
            var selectedValue = document.getElementById('items_per_page').value;
            document.getElementById('per_page').value = selectedValue;
            document.querySelector('form').submit();
        }

        $('#items_per_page').on('change', function () {
            updateItemsPerPage();
        });
        var paginationData = document.getElementById('paginationData');
        var totalItems = parseInt(paginationData.getAttribute('data-total-items')); // Tổng số dữ liệu
        var itemsPerPage = parseInt(paginationData.getAttribute('data-items-per-page')); // Số dữ liệu mỗi trang

        // Tính tổng số trang dựa trên số lượng dữ liệu
        var totalPages = Math.ceil(totalItems / itemsPerPage);

        // Số lượng trang tối đa hiển thị liền kề với trang hiện tại
        var maxVisiblePages = 5;


        // Lấy currentPage từ hidden input (không khởi tạo mặc định là 1 nữa)
        var currentPage = parseInt(document.getElementById('currentPage').value) || 1;

        // Khởi tạo pagination với currentPage thực sự
        renderPagination(currentPage);

        function toggleSort(element) {
            // Toggle the data-order attribute
            let orderby = element.getAttribute('data-orderby');
            let currentOrder = element.getAttribute('data-order');
            let newOrder = currentOrder === 'ASC' ? 'DESC' : 'ASC';
            element.setAttribute('data-order', newOrder);
            console.log("Current Order:", currentOrder); // Logging giá trị order cũ
            console.log("New Order:", newOrder); // Logging giá trị order mới
            // Update the sort icon
            let icon = element.querySelector('i');
            if (icon) {
                if (orderby === 'property_date') {
                    // Xử lý đặc biệt cho icon ngày tháng
                    icon.classList.toggle('fa-up-long');  // Chuyển đổi fa-up-long
                    icon.classList.toggle('fa-down-long'); // Chuyển đổi fa-down-long (thêm dòng này)
                } else {
                    // Logic trước đó cho sắp xếp giá (không thay đổi)
                    let iconBase = 'fa-sort-numeric-';
                    icon.classList.remove(iconBase + (newOrder === 'ASC' ? 'desc' : 'asc'));
                    icon.classList.add(iconBase + newOrder.toLowerCase());
                }
            }
            // Cập nhật giá trị cho hidden inputs
            let orderbyInput = document.getElementById("orderbyInput");
            let orderInput = document.getElementById("orderInput");

            if (orderbyInput && orderInput) {
                orderbyInput.value = orderby;
                orderInput.value = newOrder;

                console.log("Updated Orderby:", orderbyInput.value); // Debug giá trị orderby
                console.log("Updated Order:", orderInput.value);     // Debug giá trị order

            } else {
                console.error("Hidden inputs not found"); // Lỗi nếu không tìm thấy input hidden
            }

            // Add 'active' class to the currently clicked button and remove from others
            document.querySelectorAll('.sort-button').forEach(btn => {
                btn.classList.remove('active');
            });
            element.classList.add('active');
            // Submit form sau khi đã xử lý sắp xếp
            document.querySelector('form').submit();
        }

        document.querySelectorAll('.sort-button').forEach(button => {
            button.addEventListener('click', (event) => {
                event.preventDefault(); // Prevent default action if necessary
                toggleSort(button);
            });
        });


        function adjustNameDisplay() {
            var isGridActive = document.querySelector('.layout-grid').classList.contains('active');

            document.querySelectorAll('.home-name').forEach(function (el) {
                var lineHeight = parseFloat(window.getComputedStyle(el).lineHeight);
                var lines = Math.round(el.offsetHeight / lineHeight);

                if (isGridActive) {
                    el.classList.add('two-line'); // Giới hạn hiển thị 2 dòng
                    if (lines === 1) {
                        el.classList.add('one-line'); // Thêm class nếu chỉ có một dòng
                    } else {
                        el.classList.remove('one-line'); // Xóa class nếu có nhiều dòng hơn
                    }
                } else {
                    el.classList.remove('two-line'); // Hiển thị đầy đủ khi không phải chế độ grid
                    el.classList.remove('one-line');
                }
            });
        }

        adjustNameDisplay();
        document.querySelector('.layout-grid').addEventListener('click', function () {
            this.classList.add('active');
            document.querySelector('.layout-list').classList.remove('active');
            adjustNameDisplay(); // Điều chỉnh hiển thị tên
        });

        document.querySelector('.layout-list').addEventListener('click', function () {
            this.classList.add('active');
            document.querySelector('.layout-grid').classList.remove('active');
            adjustNameDisplay(); // Điều chỉnh hiển thị tên
        });

        // Khi tỉnh được chọn

    });

</script>
<%--<style>--%>
<%--    .proerty-list .box-two {--%>
<%--        width: 450px; /* Mỗi mục chiếm toàn bộ chiều ngang */--%>
<%--        display: flex;--%>
<%--        align-items: center;--%>
<%--        margin-bottom: 20px;--%>
<%--    }--%>

<%--    .proerty-list .item-thumb {--%>
<%--        width: 100%; /* Kích thước hình ảnh */--%>
<%--        margin-right: 15px;--%>
<%--    }--%>

<%--    .proerty-list .item-entry {--%>
<%--        width: 70%; /* Kích thước vùng chi tiết */--%>
<%--    }--%>

<%--</style>--%>
</body>
</html>
