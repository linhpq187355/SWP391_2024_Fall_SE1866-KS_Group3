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
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="assets/css/style.css">
<!------ Include the above in your HEAD tag ---------->
<jsp:include page="header.jsp"/>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
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
        <table>
            <thead>
            <tr>
                <th>
                    STT
                </th>
                <th>
                    Ảnh nhà
                </th>
                <th>
                    Tên nhà
                </th>
                <th>
                    Địa chỉ
                </th>
                <th>
                    Liên hệ
                </th>
                <th>
                    Giá
                </th>
                <th>
                    Thao Tác
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="home" items="${wishlist}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>
                        <c:if test="${!home.images.isEmpty()}">
                            <img class="property-image"
                                 src="${home.images != null && !home.images.isEmpty() ? home.images[0] : 'assets/img/demo/property-1.jpg'}">
                        </c:if>
                    </td>
                    <td>${home.name}</td> <!-- Tên nhà -->
                    <td>${home.address}</td> <!-- Địa chỉ nhà -->
                    <td>
                        <a href="home-detail?id=${home.id}" class="btn btn-primary">Xem nhà</a> <!-- Gán ID người dùng -->
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty prices}">
                                <c:forEach var="price" items="${prices}">
                                    <c:if test="${price.homesId == home.id}">
                                        ${ex:convertPriceToVND(price.price)} VND
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>3000000 VND/Tháng</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form action="delete-wishlist" method="POST" onsubmit="return confirm('Bạn có chắc chắn muốn xóa không?');">
                            <input type="hidden" name="homeId" value="${home.id}"/>
                            <button type="submit" class="wishlist-trash-icon" style="border:none; background:none; cursor:pointer;">
                                <i class="far fa-trash-alt"></i>
                            </button>
                        </form>
                    </td>
                </tr>
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
<jsp:include page="footer.jsp"/>

