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
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
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
            width: 100%;   /* Điều chỉnh cho ảnh đầy chiều rộng của thẻ bao quanh */
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
                <h1 class="page-title">Danh sách nhà</h1>
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
                        <div class="panel-heading">
                            <h3 class="panel-title" style="border-bottom: solid 3px #ffa500;">Tìm kiếm thông minh</h3>
                        </div>
                        <div class="panel-body search-widget">
                            <form action="home-list" method="get" id="filterForm" class=" form-inline">
                                <fieldset>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input type="text" name="keyword" class="form-control" placeholder="&#xF002; Từ khóa" style="font-family:Arial, FontAwesome"
                                                   value="${searchParams.keyword}" maxlength="50">
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <select name="homeType" id="homeType"
                                                    class="form-select form-select-sm mb-3" style="font-family:Arial, FontAwesome">
                                                <option value="" ${searchParams.homeType == null ? 'selected' : ''}>Chọn
                                                    kiểu nhà
                                                </option>
                                                <c:forEach var="homeType" items="${homeTypes}">
                                                    <option value="${homeType.id}"
                                                            <c:if test="${homeType.id == searchParams.homeType}">selected</c:if>>${homeType.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <select name="province" id="province"
                                                    class="form-select form-select-sm mb-3">
                                                <option value="" ${searchParams.province == null ? 'selected' : ''}>Chọn
                                                    tỉnh thành
                                                </option>
                                                <c:forEach var="province" items="${provinces}">
                                                    <option value="${province.id}" ${province.id == searchParams.province ? 'selected' : ''}>${province.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <input type="hidden" id="selectedDistrict" value="${searchParams.district}">
                                <input type="hidden" id="selectedWard" value="${searchParams.ward}">
                                <fieldset>
                                    <div class="row">
                                        <div class="col-xs-12" style="padding-bottom: 10px;">
                                            <select name="district" id="district"
                                                    class="form-select form-select-sm mb-3">
                                                <option value="" selected>Chọn quận huyện</option>
                                            </select>
                                        </div>
                                        <div class="col-xs-12">
                                            <select class="form-select form-select-sm" name="ward" id="ward">
                                                <option value="" selected>Chọn phường xã</option>
                                            </select>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset class="padding-5">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <label for="price-range">Giá (nghìn VND):</label>
                                            <!-- Ép giá trị minPrice và maxPrice về số nguyên sau khi chia cho 1000 -->
                                            <input type="hidden" name="minPrice"
                                                   value="${minPrice != null ? minPrice : 0}"/>
                                            <input type="hidden" name="maxPrice"
                                                   value="${maxPrice != null ? maxPrice : 10000000}"/>
                                            <input type="text" class="span2" name="priceRange"
                                                   value="${searchParams.priceRange != null ? searchParams.priceRange : ''}"
                                                   data-slider-min="${minPrice != null ? (minPrice / 1000).intValue() : 0}"
                                                   data-slider-max="${maxPrice != null ? (maxPrice / 1000).intValue() : 10000}"
                                                   data-slider-step="5"
                                            <c:choose>
                                            <c:when test="${searchParams.priceRange != null}">
                                                   data-slider-value="[${searchParams.priceRange}]"
                                            </c:when>
                                            <c:otherwise>
                                                   data-slider-value="[${minPrice != null ? (minPrice / 1000).intValue() : 0}, ${maxPrice != null ? (maxPrice / 1000).intValue() : 10000}]"
                                            </c:otherwise>
                                            </c:choose>
                                                   id="price-range">
                                            <br/>
                                            <b class="pull-left color">${minPrice != null ? (minPrice / 1000).intValue() : 0}</b>
                                            <b class="pull-right color">${maxPrice != null ? (maxPrice / 1000).intValue() : 10000}</b>

                                        </div>
                                        <div class="col-xs-12">
                                            <label for="property-geo">Diện tích (m²) :</label>
                                            <!-- Sử dụng phương thức setScale(1) để làm tròn đến 1 chữ số thập phân -->
                                            <input type="text" class="span2" name="areaRange"
                                                   value="${searchParams.areaRange != null ? searchParams.areaRange : ''}"
                                                   data-slider-min="${minArea != null ? minArea.intValue() : 0}"
                                                   data-slider-max="${maxArea != null ? maxArea.intValue() : 600}"
                                                   data-slider-step="1"
                                            <c:choose>
                                            <c:when test="${searchParams.areaRange != null}">
                                                   data-slider-value="[${searchParams.areaRange}]"
                                            </c:when>
                                            <c:otherwise>
                                                   data-slider-value="[${minArea != null ? minArea.intValue() : 0}, ${maxArea != null ? maxArea.intValue() : 600}]"
                                            </c:otherwise>
                                            </c:choose>
                                                   id="property-geo"><br/>
                                            <b class="pull-left color">${minArea != null ? minArea.intValue() : 0}
                                                m²</b>
                                            <b class="pull-right color">${maxArea != null ? maxArea.intValue() : 600.0}
                                                m²</b>
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset class="padding-5">
                                    <div class="row">
                                        <div class="col-xs-6">
                                            <label for="min-baths">Số phòng tắm :</label>
                                            <input type="text" class="span2" name="bathRange"
                                                   value="${searchParams.bathRange != null ? searchParams.bathRange : ''}"
                                                   data-slider-min="${minBath != null ? minBath : 0}"
                                                   data-slider-max="${maxBath != null ? maxBath : 10}"
                                                   data-slider-step="1"
                                            <c:choose>
                                            <c:when test="${searchParams.bathRange != null}">
                                                   data-slider-value="[${searchParams.bathRange}]"
                                            </c:when>
                                            <c:otherwise>
                                                   data-slider-value="[${minBath != null ? minBath : 0}, ${maxBath != null ? maxBath : 10}]"
                                            </c:otherwise>
                                            </c:choose>
                                                   id="min-baths"><br/>
                                            <b class="pull-left color">${minBath != null ? minBath : 0}</b>
                                            <b class="pull-right color">${maxBath != null ? maxBath : 10}</b>
                                        </div>

                                        <div class="col-xs-6">
                                            <label for="min-bed">Số phòng ngủ :</label>
                                            <input type="text" class="span2" name="bedRange"
                                                   value="${searchParams.bedRange != null ? searchParams.bedRange : ''}"
                                                   data-slider-min="${minBed != null ? minBed : 0}"
                                                   data-slider-max="${maxBed != null ? maxBed : 10}"
                                                   data-slider-step="1"
                                            <c:choose>
                                            <c:when test="${searchParams.bedRange != null}">
                                                   data-slider-value="[${searchParams.bedRange}]"
                                            </c:when>
                                            <c:otherwise>
                                                   data-slider-value="[${minBed != null ? minBed : 0}, ${maxBed != null ? maxBed : 10}]"
                                            </c:otherwise>
                                            </c:choose>
                                                   id="min-bed"><br/>
                                            <b class="pull-left color">${minBed != null ? minBed : 0}</b>
                                            <b class="pull-right color">${maxBed != null ? maxBed : 10}</b>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset class="padding-5">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <label><strong>Tiện ích:</strong></label>
                                        </div>
                                        <c:forEach var="amentity" items="${listAmen}">
                                            <div class="col-xs-6">
                                                <div class="checkbox">
                                                    <label>
                                                        <!-- Lặp qua danh sách amenities để kiểm tra -->
                                                        <c:set var="isChecked" value="false"/>
                                                        <c:if test="${searchParams.amenities != null}">
                                                            <c:forEach var="amenity" items="${searchParams.amenities}">
                                                                <c:if test="${amenity == amentity.id}">
                                                                    <c:set var="isChecked" value="true"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>

                                                        <input type="checkbox" name="amenities[]" value="${amentity.id}"
                                                               <c:if test="${isChecked}">checked</c:if>>
                                                            ${amentity.name}
                                                    </label>
                                                </div>
                                            </div>
                                        </c:forEach>

                                    </div>
                                </fieldset>

                                <fieldset class="padding-5">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <label><strong>Trang bị PCCC:</strong></label>
                                        </div>
                                        <c:forEach var="fireEquipment" items="${listFire}">
                                            <div class="col-xs-12">
                                                <div class="checkbox">
                                                    <label>
                                                        <!-- Lặp qua danh sách fireEquipments để kiểm tra -->
                                                        <c:set var="isChecked" value="false"/>
                                                        <c:if test="${searchParams.fireEquipments != null}">
                                                            <c:forEach var="fireE"
                                                                       items="${searchParams.fireEquipments}">
                                                                <c:if test="${fireE == fireEquipment.id}">
                                                                    <c:set var="isChecked" value="true"/>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        <input type="checkbox" name="fireEquipments[]"
                                                               value="${fireEquipment.id}"
                                                               <c:if test="${isChecked}">checked</c:if>>
                                                            ${fireEquipment.name}
                                                    </label>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </fieldset>
                                <!-- Hidden inputs để lưu thông tin sắp xếp -->
                                <c:set var="orderby"
                                       value="${searchParams.orderby != null ? searchParams.orderby : 'property_date'}"/>
                                <c:set var="order" value="${searchParams.order != null ? searchParams.order : 'ASC'}"/>
                                <c:set var="currentPage"
                                       value="${searchParams.targetPage != null ? searchParams.targetPage : '1'}"/>
                                <input type="hidden" name="orderby" id="orderbyInput" value="${orderby}">
                                <input type="hidden" name="order" id="orderInput" value="${order}">
                                <input type="hidden" id="currentPage" name="currentPage" value="${currentPage}">
                                <input type="hidden" id="targetPage" name="targetPage" value="${currentPage}">
                                <input type="hidden" name="per_page" id="per_page"
                                       value="${searchParams.per_page != null ? searchParams.per_page : '12'}">
                                <div id="paginationData"
                                     data-total-items="${totalHomes != null ? totalHomes : '25'}"
                                     data-items-per-page="${searchParams.per_page != null ? searchParams.per_page : '12'}">
                                </div>
                                <fieldset>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input class="button btn largesearch-btn" value="Tìm kiếm" type="submit">
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                    </div>

<%--                        <div class="panel panel-default sidebar-menu wow fadeInRight animated">--%>
<%--                            <div class="panel-heading">--%>
<%--                                <h3 class="panel-title">Đề xuất</h3>--%>
<%--                            </div>--%>
<%--                            <div class="panel-body recent-property-widget">--%>
<%--                                <ul>--%>
<%--                                    <li>--%>
<%--                                        <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
<%--                                            <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>--%>
<%--                                            <span class="property-seeker">--%>
<%--                                                    <b class="b-1">A</b>--%>
<%--                                                    <b class="b-2">S</b>--%>
<%--                                            </span>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
<%--                                            <h6> <a href="single.html">Super nice villa </a></h6>--%>
<%--                                            <span class="property-price">3000000$</span>--%>
<%--                                        </div>--%>
<%--                                    </li>--%>
<%--                                    <li>--%>
<%--                                        <div class="col-md-3 col-sm-3  col-xs-3 blg-thumb p0">--%>
<%--                                            <a href="single.html"><img src="assets/img/demo/small-property-1.jpg"></a>--%>
<%--                                            <span class="property-seeker">--%>
<%--                                                    <b class="b-1">A</b>--%>
<%--                                                    <b class="b-2">S</b>--%>
<%--                                                </span>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
<%--                                            <h6> <a href="single.html">Super nice villa </a></h6>--%>
<%--                                            <span class="property-price">3000000$</span>--%>
<%--                                        </div>--%>
<%--                                    </li>--%>
<%--                                    <li>--%>
<%--                                        <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
<%--                                            <a href="single.html"><img src="assets/img/demo/small-property-3.jpg"></a>--%>
<%--                                            <span class="property-seeker">--%>
<%--                                                    <b class="b-1">A</b>--%>
<%--                                                    <b class="b-2">S</b>--%>
<%--                                                </span>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
<%--                                            <h6> <a href="single.html">Super nice villa </a></h6>--%>
<%--                                            <span class="property-price">3000000$</span>--%>
<%--                                        </div>--%>
<%--                                    </li>--%>

<%--                                    <li>--%>
<%--                                        <div class="col-md-3 col-sm-3 col-xs-3 blg-thumb p0">--%>
<%--                                            <a href="single.html"><img src="assets/img/demo/small-property-2.jpg"></a>--%>
<%--                                            <span class="property-seeker">--%>
<%--                                                    <b class="b-1">A</b>--%>
<%--                                                    <b class="b-2">S</b>--%>
<%--                                            </span>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-8 col-sm-8 col-xs-8 blg-entry">--%>
<%--                                            <h6> <a href="single.html">Super nice villa </a></h6>--%>
<%--                                            <span class="property-price">3000000$</span>--%>
<%--                                        </div>--%>
<%--                                    </li>--%>
<%--                                </ul>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                    </div>
                </div>

            <div class="col-md-9 pr0 padding-top-40 properties-page">
                <div class="col-md-12 clear">
                    <div class="col-xs-10 page-subheader sorting pl0">
                        <ul class="sort-by-list">
                            <li class="active">
                                <button type="button"
                                        class="sort-button order_by_date ${orderby == 'property_date' ? 'active' : ''}"
                                        data-orderby="property_date"
                                        data-order="${orderby == 'property_date' ? order : 'ASC'}">
                                    Ngày đăng <i
                                        class="fa ${orderby == 'property_date' ? (order == 'ASC' ? 'fa-up-long' : 'fa-down-long') : 'fa-sort-amount-asc'}"></i>
                                </button>
                            </li>
                            <li>
                                <button type="button"
                                        class="sort-button order_by_price ${orderby == 'property_price' ? 'active' : ''}"
                                        data-orderby="property_price"
                                        data-order="${orderby == 'property_price' ? order : 'DESC'}">
                                    Giá nhà <i
                                        class="fa ${orderby == 'property_price' ? (order == 'ASC' ? 'fa-sort-numeric-asc' : 'fa-sort-numeric-desc') : 'fa-sort-numeric-desc'}"></i>
                                </button>
                            </li>
                        </ul>

                        <c:set var="per_page" value="${searchParams.per_page != null ? searchParams.per_page : '12'}"/>

                        <div class="items-per-page pull-right">
                            <label for="items_per_page"><b>Số nhà mỗi trang :</b></label>
                            <div class="sel">
                                <select id="items_per_page" name="per_page">
                                    <option value="4" ${per_page == 4 ? 'selected="selected"' : ''}>4</option>
                                    <option value="8" ${per_page == 8 ? 'selected="selected"' : ''}>8</option>
                                    <option value="12" ${per_page == 12 ? 'selected="selected"' : ''}>12</option>
                                    <option value="16" ${per_page == 16 ? 'selected="selected"' : ''}>16</option>
                                    <option value="20" ${per_page == 20 ? 'selected="selected"' : ''}>20</option>
                                    <option value="32" ${per_page == 32 ? 'selected="selected"' : ''}>32</option>
                                    <option value="48" ${per_page == 48 ? 'selected="selected"' : ''}>48</option>
                                    <option value="64" ${per_page == 64 ? 'selected="selected"' : ''}>64</option>
                                </select>
                            </div><!--/ .sel-->
                        </div><!--/ .items-per-page-->
                    </div>

                    <div class="col-xs-2 layout-switcher">
                        <a class="layout-list" href="javascript:void(0);"> <i class="fa fa-th-list"></i> </a>
                        <a class="layout-grid active" href="javascript:void(0);"> <i class="fa fa-th"></i> </a>
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
                                        <span class="pull-left"><i class="fa-solid fa-chart-area" style="color: #ffa500;"></i> ${homes.area}m²</span>
                                        <span class="proerty-price pull-right" style="color: #ffa500;">${ex:convertPriceToVND(homes.price)}</span>
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

<jsp:include page="footer.jsp"/>

<script>
    $(document).ready(function () {
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

                document.querySelectorAll('.home-name').forEach(function(el) {
                    // Kiểm tra chiều cao để xác định xem tên có bao nhiêu dòng
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
            document.querySelector('.layout-grid').addEventListener('click', function() {
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
    });

</script>
</body>
</html>
