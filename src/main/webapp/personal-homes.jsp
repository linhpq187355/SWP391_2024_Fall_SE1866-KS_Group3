<%--
  User: Lam Tien Thang
  Date: 10/9/2024
  Time: 12:48 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="assets/css/homes-pagination.css">
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
                <h1 class="page-title">Bài đăng của tôi</h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<!-- property area -->
<div class="properties-area recent-property" style="background-color: #FFF;">
    <div class="container">
        <div class="row">
            <div class="col-md-9 pr-30 padding-top-40 properties-page user-properties">
                <div class="blog-asside-right pr0">
                    <div class="panel panel-default sidebar-menu wow fadeInRight animated">
                        <div class="panel-body search-widget">
                            <form action="my-home" method="get" id="filterForm" class="form-inline">
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
                            </form>
                        </div>
                    </div>
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
                                    Tầm giá <i
                                        class="fa ${orderby == 'property_price' ? (order == 'ASC' ? 'fa-sort-numeric-asc' : 'fa-sort-numeric-desc') : 'fa-sort-numeric-desc'}"></i>
                                </button>
                            </li>
                        </ul>

                        <c:set var="per_page" value="${searchParams.per_page != null ? searchParams.per_page : '12'}"/>

                        <div class="items-per-page pull-right" style="margin-right: -20%;">
                            <label for="items_per_page" ><b>Số nhà mỗi trang :</b></label>
                            <div class="sel">
                                <select id="items_per_page" name="per_page">
                                    <option value="3" ${per_page == 3 ? 'selected="selected"' : ''}>3</option>
                                    <option value="6" ${per_page == 6 ? 'selected="selected"' : ''}>6</option>
                                    <option value="9" ${per_page == 9 ? 'selected="selected"' : ''}>9</option>
                                    <option value="12" ${per_page == 12 ? 'selected="selected"' : ''}>12</option>
                                    <option value="15" ${per_page == 15 ? 'selected="selected"' : ''}>15</option>
                                    <option value="30" ${per_page == 30 ? 'selected="selected"' : ''}>30</option>
                                    <option value="45" ${per_page == 45 ? 'selected="selected"' : ''}>45</option>
                                    <option value="60" ${per_page == 60 ? 'selected="selected"' : ''}>60</option>
                                </select>
                            </div><!--/ .sel-->
                        </div><!--/ .items-per-page-->
                    </div>
                </div>

                <div class="section">
                    <div id="list-type" class="proerty-th-list">
                        <c:forEach items="${requestScope.homes}" var="homes">
                            <div class="col-md-4 p0">
                                <div class="box-two proerty-item" style="border-radius: 15px;display: flex;width: 1035px;">
                                    <div class="item-thumb">
                                        <!-- Update the href to point to home detail page with home ID -->
                                        <a href="home-detail?id=${homes.id}">
                                            <img class="property-image"
                                                 src="${homes.images != null && !homes.images.isEmpty() ? homes.images[0] : 'assets/img/demo/property-1.jpg'}" style="border-radius: 15px;">
                                        </a>
                                    </div>
                                    <div class="item-entry overflow">
                                        <h5><a href="home-detail?id=${homes.id}" class="home-name">${homes.name}</a>
                                        </h5>
                                        <div class="dot-hr"></div>
                                        <span class="pull-left"><i class="fa-solid fa-location-dot" style="color: #5f676d;"></i>
                                                &nbsp;&nbsp;${homes.address}</span>
                                        <span class="proerty-price pull-right" style="color: #ff7800;">${ex:convertPriceToVND(homes.price)}</span>
                                        <p class="pull-left" style="padding-bottom: 0px;padding-top: 0px;">
                                            <i class="fa-solid fa-chart-area" style="color: #5f676d;"></i>&nbsp;&nbsp;
                                            <b>Diện tích
                                            : </b>${homes.area} m²</p>
                                        <p style="display: none; padding-top: 0px;">
                                            <i class="fa-solid fa-calendar-days"></i>&nbsp;&nbsp;<b>Ngày đăng : </b>
                                            <fmt:parseDate value="${homes.createdDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                        </p>
                                        <div class="property-icon" style="padding-left: 10px;padding-right: 10px;">
                                            <b>(${homes.numOfBedroom})</b>&nbsp;<img src="assets/img/icon/bed.png">&nbsp;
                                            &nbsp;<b>(${homes.numOfBath})</b>&nbsp;<img src="assets/img/icon/shawer.png">

                                            <div class="dealer-action pull-right">
                                                <a href="update-home?id=${homes.id}" class="button">Sửa </a>
                                                <a href="delete-home?id=${homes.id}" class="button delete_user_car">Xóa</a>
                                                <a href="home-detail?id=${homes.id}" class="button">Xem</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div style="display: flex; align-items: center">
                                        <div style="background-color: #FA8600;
    color: #fff;
    border-radius: 15px;
    height: 40px;
    width: 80px;
    margin-right: 10px;
    display: flex;
    justify-content: center;
    align-items: center;">
                                            ${homes.status}
                                        </div>
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

<script src="assets/js/homes-pagination.js"></script>
</body>
</html>