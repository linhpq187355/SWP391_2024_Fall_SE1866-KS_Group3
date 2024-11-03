<%--
  Created by IntelliJ IDEA.
  User: feedc
  Date: 10/10/2024
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ex" uri="http://example.com/functions" %>
<link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
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
                <h1 class="page-title">Danh sách blog của bạn</h1>
            </div>
        </div>
    </div>
</div>
<div class="wishlist-cart-wrap">
    <%--    <div class="container">--%>
    <%--        <div class="row">--%>
    <%--            <div class="col-md-12">--%>
    <%--                <div class="wishlist-main-heading wishlist-mb-10"></div>--%>
    <%--                <div class="wishlist-table-wishlist">--%>
    <%--                    <table cellpadding="0" cellspacing="0" border="0" width="100%">--%>
    <%--                        <thead>--%>
    <%--                        <tr>--%>
    <%--                            <th width="5%">STT</th> <!-- Thêm cột số thứ tự -->--%>
    <%--                            <th width="50%">Tiêu đề</th>--%>
    <%--                            <th width="20%">Ngày tạo</th>--%>
    <%--                            <th width="10%">Trạng thái</th>--%>
    <%--                            <th width="10%">Quản lý</th>--%>
    <%--                        </tr>--%>
    <%--                        </thead>--%>
    <%--                        <tbody>--%>
    <%--                        <c:forEach var="post" items="${posts}" varStatus="status">--%>
    <%--                            <tr>--%>
    <%--                                <td>${status.index + 1}</td> <!-- Hiển thị số thứ tự -->--%>
    <%--                                <td>${post.title}</td> <!-- Sửa từ "tilte" thành "title" -->--%>
    <%--                                <td>--%>
    <%--                              ${post.createdAt}--%>
    <%--                                </td>--%>
    <%--                                <td>${post.status}</td>--%>
    <%--                                <td>--%>
    <%--                                    <form action="view-blog" method="POST" onsubmit="return confirm('Bạn có chắc chắn muốn thực hiện hành động này không?');">--%>
    <%--                                        <input type="hidden" name="postId" value="${post.id}"/>--%>
    <%--                                        <input type="hidden" name="authorId" value="${post.authorId}"/> <!-- Nếu cần -->--%>
    <%--                                        <button type="submit" name="action" value="delete" class="btn btn-danger">--%>
    <%--                                            Xóa--%>
    <%--                                        </button>--%>
    <%--                                    </form>--%>
    <%--                                <td>--%>
    <%--                                    <a href="update-blog?postId=${post.id}" class="btn btn-danger">Cập nhật</a>--%>
    <%--                                </td>--%>
    <%--                                </td>--%>
    <%--                            </tr>--%>
    <%--                        </c:forEach>--%>
    <%--                        </tbody>--%>
    <%--                    </table>--%>
    <%--                </div>--%>
    <%--            </div>--%>

    <%--            <!-- Phần phân trang -->--%>
    <%--            <div id="postsContainer"></div>--%>
    <%--            <div id="paginationContainer"></div>--%>
    <%--    </div>--%>
    <%--</div>--%>


    <div class="container">
        <div class="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header" style="display: flex; justify-content: space-between">
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table
                                        id="basic-datatables"
                                        class="display table table-striped table-hover "
                                >
                                    <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên</th>
                                        <th>Tạo lúc</th>
                                        <th>Trạng thái</th>
                                        <th>Quản lí</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="post" items="${posts}" varStatus="status">
                                        <c:if test="${post.status != 'DELETED'}"> <!-- Kiểm tra trạng thái -->
                                            <tr>
                                                <td>${status.index + 1}</td>
                                                <td>${post.title}</td>
                                                <td>
                                                        ${post.createdAt}
                                                </td>
                                                <td>${post.status}</td>
                                                <td>
                                                    <form action="view-blog" method="POST" onsubmit="return confirm('Bạn có chắc chắn muốn thực hiện hành động này không?');"
                                                          style="display: inline;">
                                                        <input type="hidden" name="postId" value="${post.id}"/>
                                                        <input type="hidden" name="authorId" value="${post.authorId}"/>
                                                        <!-- Nếu cần -->
                                                        <button type="submit" name="action" value="delete"
                                                                class="btn btn-danger"
                                                                style="border: none; background: none;">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                    </form>
                                                    <a href="update-blog?postId=${post.id}" class="btn btn-primary"
                                                       style="margin-left: 5px;">
                                                        <i class="fas fa-edit"></i> <!-- Biểu tượng bút chì -->
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>
</body>
<style>
    .table>tbody>tr>td,.table>tbody>tr>th,.table>tfoot>tr>td,.table>tfoot>tr>th,.table>thead>tr>td,.table>thead>tr>th {
        padding: 22px;
        line-height: 1.42857143;
        vertical-align: top;
        border-top: 1px solid #ddd
    }
</style>
<script>
    function confirmDelete() {
        if (confirm('Bạn có chắc chắn muốn xóa không?')) {
            // Nếu người dùng xác nhận, gửi form
            document.getElementById('delete-form').submit();
        }
    }
</script>
<script>
    const blogPosts = ${blogPosts}; // Danh sách bài viết từ server
    const pageSize = 5; // Số bài viết mỗi trang
    let currentPage = 1;

    function displayPosts(page) {
        const startIndex = (page - 1) * pageSize;
        const endIndex = Math.min(startIndex + pageSize, blogPosts.length);
        const postsContainer = document.getElementById('postsContainer');

        // Làm sạch danh sách hiện tại
        postsContainer.innerHTML = '';

        // Hiển thị các bài viết trong khoảng startIndex đến endIndex
        for (let i = startIndex; i < endIndex; i++) {
            const post = blogPosts[i];
            postsContainer.innerHTML += `
                <div class="blog-post">
                    <h3>${post.title}</h3>
                    <p>${post.content}</p>
                    <p>Author: ${post.authorName}</p>
                </div>
            `;
        }

        // Cập nhật nút điều hướng
        updatePagination();
    }

    function updatePagination() {
        const totalPages = Math.ceil(blogPosts.length / pageSize);
        const paginationContainer = document.getElementById('paginationContainer');
        paginationContainer.innerHTML = '';

        // Thêm nút Previous
        if (currentPage > 1) {
            paginationContainer.innerHTML += `<button onclick="changePage(${currentPage - 1})">Previous</button>`;
        }

        // Thêm nút cho mỗi trang
        for (let i = 1; i <= totalPages; i++) {
            if (i === currentPage) {
                paginationContainer.innerHTML += `<strong>${i}</strong>`;
            } else {
                paginationContainer.innerHTML += `<button onclick="changePage(${i})">${i}</button>`;
            }
        }

        // Thêm nút Next
        if (currentPage < totalPages) {
            paginationContainer.innerHTML += `<button onclick="changePage(${currentPage + 1})">Next</button>`;
        }
    }

    function changePage(page) {
        currentPage = page;
        displayPosts(currentPage);
    }

    // Bắt đầu hiển thị các bài viết khi trang được tải
    document.addEventListener('DOMContentLoaded', () => {
        displayPosts(currentPage);
    });
</script>

<jsp:include page="footer.jsp"/>

