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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib prefix="ex" uri="http://example.com/functions" %>
<link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="assets/css/style.css">

<!-- jQuery (chỉ sử dụng phiên bản này) -->
<script src="assets/js/core/jquery-3.7.1.min.js"></script>

<!-- Bootstrap JS -->
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="./assets/js/core/popper.min.js"></script>
<script src="./assets/js/core/bootstrap.min.js"></script>

<!-- jQuery Scrollbar -->
<script src="./assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>

<!-- DataTables -->
<script src="./assets/js/plugin/datatables/datatables.min.js"></script>

<!-- Kaiadmin JS -->
<script src="./assets/js/kaiadmin.min.js"></script>

<!------ Include the above in your HEAD tag ---------->
<jsp:include page="header.jsp"/>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
    }
    /* Sắp xếp mặc định (khi chưa nhấn) */
    table.dataTable thead .sorting:after {
        content: "↑↓"; /* Bạn có thể thay thế mũi tên nếu cần */
        color: #000;
    }

    /* Sắp xếp tăng dần */
    table.dataTable thead .sorting_asc:after {
        content: "↑"; /* Mũi tên chỉ hướng lên */
    }

    /* Sắp xếp giảm dần */
    table.dataTable thead .sorting_desc:after {
        content: "↓"; /* Mũi tên chỉ hướng xuống */
    }

    /* Không thể sắp xếp (disabled) */
    table.dataTable thead .sorting_asc_disabled:after,
    table.dataTable thead .sorting_desc_disabled:after {
        content: "→"; /* Mũi tên disabled */
        color: #ccc;
    }
    header, footer {
        background-color: #d32f2f;
        color: white;
        text-align: center;
        width: 100%;
        padding: 10px 0;
    }
    .content-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
    }
    .content-wrapper {
        width: 80%;
        margin-top: 20px;
        background-color: #f9f9f9;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    .content-wrapper table {
        width: 100%;
        border-collapse: collapse;
    }
    .content-wrapper th, .content-wrapper td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: center;
    }
    .content-wrapper th {
        background-color: darkgoldenrod;
        color: white;
    }
    .content-wrapper tr:nth-child(even) {
        background-color: #f2f2f2;
    }
    .content-wrapper tr:hover {
        background-color: #ddd;
    }
    .content-wrapper .pagination {
        display: flex;
        justify-content: center;
        margin-top: 20px;
    }
    .content-wrapper .pagination a {
        color: black;
        padding: 8px 16px;
        text-decoration: none;
        border: 1px solid #ddd;
        margin: 0 4px;
    }
    .content-wrapper .pagination a:hover {
        background-color: #ddd;
    }
    .content-wrapper .pagination a.active {
        background-color: darkgoldenrod;
        color: white;
        border: 1px solid darkgoldenrod;
    }
    .content-wrapper img {
        width: 100px;
        height: 60px;
        object-fit: cover;
    }
</style>
}
<body>

<div class="content-container">
    <div class="content-wrapper">
        <table id="basic-datatables">
            <thead>
            <tr>
                <th>
                    STT
                </th>
                <th>
                    ẢNH nhà
                </th>
                <th>
                    Tiêu đề
                </th>
                <th>
                    Tạo lúc
                </th>
                <th>
                    Trạng thái
                </th>
                <th>
                    Xem blog
                </th>
                <th>
                    Quản lý
                </th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
            <tbody>
            <c:forEach var="post" items="${posts}" varStatus="status">
                <c:if test="${post.status != 'DELETED'}">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>
                            <img class="property-image"
                                 src="${not empty post.imagePath ? post.imagePath : 'assets/img/demo/property-1.jpg'}">
                        </td>
                        <td>${post.title}</td>
                        <td>
                            <c:if test="${not empty post.createdAt}">
                                <span class="formatted-date" data-created-at="${post.createdAt}"></span>
                            </c:if>
                        </td>
                        <td>${post.status}</td>
                        <td>
                            <a href="blog-detail?postId=${post.id}" class="btn btn-primary">Xem Blog</a> <!-- Gán ID người dùng -->
                        </td>
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
                               style="  margin-left: 5px;">
                                <i class="fas fa-edit"></i> <!-- Biểu tượng bút chì -->
                            </a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
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
</body>

<script>
    function confirmDelete() {
        if (confirm('Bạn có chắc chắn muốn xóa không?')) {
            // Nếu người dùng xác nhận, gửi form
            document.getElementById('delete-form').submit();
        }
    }
</script>
<script>
    $(document).ready(function () {
        $("#basic-datatables").DataTable({
            "paging": false,  // Tắt phân trang
            "searching": false,  // Tắt tìm kiếm
            "ordering": true,  // Bật tính năng sắp xếp
            "info": false  // Tắt thông tin trang
        });
    });
</script>
<script>
    document.querySelectorAll('.formatted-date').forEach(function(span) {
        const localDateTimeString = span.getAttribute('data-created-at');
        const localDateTime = new Date(localDateTimeString); // Chuyển đổi chuỗi thành Date
        const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: false };
        span.textContent = localDateTime.toLocaleString('en-GB', options); // Định dạng theo kiểu ngày
    });
</script>

<jsp:include page="footer.jsp"/>

