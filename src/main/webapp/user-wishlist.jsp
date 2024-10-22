<%--
  Created by IntelliJ IDEA.
  User: feedc
  Date: 10/10/2024
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="assets/css/style.css">
<!------ Include the above in your HEAD tag ---------->
<style>
    .wishlist-cart-wrap {
        padding: 40px 0;
        font-family: 'Open Sans', sans-serif;
    }

    .wishlist-main-heading {
        font-size: 19px;
        margin-bottom: 20px;
    }

    .wishlist-table-wishlist table {
        width: 100%;
    }

    .wishlist-table-wishlist thead {
        border-bottom: 1px solid #e5e5e5;
        margin-bottom: 5px;
    }

    .wishlist-table-wishlist thead tr th {
        padding: 8px 0 18px;
        color: #484848;
        font-size: 15px;
        font-weight: 400;
    }

    .wishlist-table-wishlist tr td {
        padding: 25px 0;
        vertical-align: middle;
    }

    .wishlist-img-product {
        width: 72px;
        float: left;
        margin-left: 8px;
        margin-right: 31px;
        line-height: 63px;
    }

    .wishlist-img-product img {
        width: 100%;
    }

    .wishlist-name-product {
        font-size: 15px;
        color: #484848;
        padding-top: 8px;
        line-height: 24px;
        width: 50%;
    }

    .wishlist-price {
        font-weight: 600;
    }

    .wishlist-quanlity {
        position: relative;
    }

    .wishlist-total {
        font-size: 24px;
        font-weight: 600;
        color: #8660e9;
    }

    .wishlist-display-flex {
        display: flex;
    }

    .wishlist-align-center {
        align-items: center;
    }

    .wishlist-round-black-btn {
        border-radius: 25px;
        background: #212529;
        color: #fff;
        padding: 5px 20px;
        display: inline-block;
        border: solid 2px #212529;
        transition: all 0.5s ease-in-out 0s;
        cursor: pointer;
        font-size: 14px;
    }

    .wishlist-round-black-btn:hover,
    .wishlist-round-black-btn:focus {
        background: transparent;
        color: #212529;
        text-decoration: none;
    }

    .wishlist-mb-10 {
        margin-bottom: 10px !important;
    }

    .wishlist-mt-30 {
        margin-top: 30px !important;
    }

    .wishlist-d-block {
        display: block;
    }

    .wishlist-custom-form label {
        font-size: 14px;
        line-height: 14px;
    }

    .wishlist-pretty.p-default {
        margin-bottom: 15px;
    }

    .wishlist-pretty input:checked ~ .state.p-primary-o label:before,
    .wishlist-pretty.p-toggle .state.p-primary-o label:before {
        border-color: #8660e9;
    }

    .wishlist-pretty.p-default:not(.p-fill) input:checked ~ .state.p-primary-o label:after {
        background-color: #8660e9 !important;
    }

    .wishlist-main-heading.wishlist-border-b {
        border-bottom: solid 1px #ededed;
        padding-bottom: 15px;
        margin-bottom: 20px !important;
    }

    .wishlist-custom-form .wishlist-pretty .state label {
        padding-left: 6px;
    }

    .wishlist-custom-form .wishlist-pretty .state label:before {
        top: 1px;
    }

    .wishlist-custom-form .wishlist-pretty .state label:after {
        top: 1px;
    }

    .wishlist-custom-form .form-control {
        font-size: 14px;
        height: 38px;
    }

    .wishlist-custom-form .form-control:focus {
        box-shadow: none;
    }

    .wishlist-custom-form textarea.form-control {
        height: auto;
    }

    .wishlist-mt-40 {
        margin-top: 40px !important;
    }

    .wishlist-in-stock-box {
        background: #ff0000;
        font-size: 12px;
        text-align: center;
        border-radius: 25px;
        padding: 4px 15px;
        display: inline-block;
        color: #fff;
    }

    .wishlist-trash-icon {
        font-size: 20px;
        color: #212529;
    }

</style>


<body>
<jsp:include page="header.jsp"/>

<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title">Danh sách yêu thích</h1>
            </div>
        </div>
    </div>
</div>
<div class="wishlist-cart-wrap">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="wishlist-main-heading wishlist-mb-10"></div>
                <div class="wishlist-table-wishlist">
                    <table cellpadding="0" cellspacing="0" border="0" width="100%">
                        <thead>
                        <tr>
                            <th width="20%">Ảnh</th>
                            <th width="30%">Tên nhà</th>
                            <th width="10%">Địa chỉ</th>
                            <th width="30%">Giá</th>
                            <th width="10%"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="home" items="${wishlist}">
                            <tr>
                                <div>
                                    <td width="20%">
                                        <div class="wishlist-img-product">
                                            <img src="assets/img/property-1/property2.jpg"/>
                                        </div>
                                    </td>
                                    <td width="10%" class="wishlist-address">
                                            ${home.name}
                                    </td>
                                    <td width="30%" class="wishlist-address">
                                            ${home.address}
                                    </td>
                                    <td width="10%" class="wishlist-price">
                                        <c:choose>
                                            <c:when test="${not empty prices}">
                                                <c:forEach var="price" items="${prices}">
                                                    <c:if test="${price.homesId == home.id}">
                                                        ${price.price} VND
                                                    </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                3000000 VND/Tháng
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                    <td width="10%" class="text-center">
                                        <form action="delete-wishlist" method="POST"
                                              onsubmit="return confirm('Bạn có chắc chắn muốn xóa không?');">
                                            <input type="hidden" name="homeId" value="${home.id}"/>
                                            <button type="submit" class="wishlist-trash-icon"
                                                    style="border:none; background:none; cursor:pointer;">
                                                <i class="far fa-trash-alt"></i>
                                            </button>
                                        </form>
                                    </td>
                                </div>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- Phần phân trang -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="?page=${currentPage - 1}">Prev</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span>${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="?page=${currentPage + 1}">Next</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    function confirmDelete() {
        if (confirm('Bạn có chắc chắn muốn xóa không?')) {
            // Nếu người dùng xác nhận, gửi form
            document.getElementById('delete-form').submit();
        }
    }
</script>
<jsp:include page="footer.jsp"/>

