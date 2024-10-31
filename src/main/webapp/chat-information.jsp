<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 29/10/2024
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thông tin đoạn chat</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
<style>
    .chat-infor-window {
    width: 80%;
    margin: 0 auto;
    padding: 20px;
    }
    .header-2 {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #FFC107;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    }
    .header-2 h1 {
    margin: auto;
    }
    .header-2 .actions {
    display: flex;
    gap: 10px;
    }
    .header-2 .actions button {
    background-color: white;
    color: #FFC107;
    border: none;
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
    }
    .header-2 .actions button:hover {
    background-color: #e0e0e0;
    }
    .profile {
    display: flex;
    align-items: center;
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    margin: 20px 0;
    }
    .profile img {
    border-radius: 50%;
    margin-right: 20px;
    }
    .profile .info {
    flex-grow: 1;
    }
    .profile .info h2 {
    margin: 0;
    }
    .profile .info p {
    margin: 5px 0 0;
    color: #555;
    }
    .profile .actions {
    display: flex;
    gap: 10px;
    }
    .profile .actions button {
    background-color: #FFC107;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    }
    .profile .actions button:hover {
    background-color: #e0a800;
    }
    .stats {
    display: flex;
    justify-content: space-around;
    margin: 20px 0;
    }
    .stats .stat {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    text-align: center;
    width: 30%;
    }
    .stats .stat h2 {
    margin: 0;
    font-size: 2em;
    }
    .stats .stat p {
    margin: 5px 0 0;
    color: #555;
    }
    .content {
    display: flex;
    gap: 20px;
    }
    .content .left, .content .right {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    width: 50%;
    }
    .content .left h2, .content .right h2 {
    margin-top: 0;
    }
    .content .left .search {
    display: flex;
    margin-bottom: 20px;
    }
    .content .left .search input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px 0 0 5px;
    }
    .content .left .search button {
    padding: 10px;
    border: 1px solid #ccc;
    border-left: none;
    background-color: #FFC107;
    color: white;
    border-radius: 0 5px 5px 0;
    cursor: pointer;
    }
    .content .left .search button:hover {
    background-color: #e0a800;
    }
    .content .left .messages {
    max-height: 550px;
    overflow-y: auto;
    }
    .content .left .messages .message {
    padding: 10px;
    border-bottom: 1px solid #ccc;
    }
    .content .left .messages .message p {
    margin: 0;
    }
    .content .right .files {
        max-height: 200px;
        min-height: 70px;
        overflow-y: auto;
        margin-bottom: 20px;
    }
    .content .right .files .file{
    padding: 10px;
    border-bottom: 1px solid #ccc;
    }
    .content .right .files .file p{
    margin: 0;
    }
    .images {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(100px, 1fr)); /* Thiết lập lưới động với kích thước mỗi ô tối thiểu là 200px */
        gap: 10px; /* Khoảng cách giữa các phần tử */
        max-height: 350px; /* Chiều cao tối đa cho khối chứa để có cuộn */
        overflow-y: auto; /* Thêm cuộn dọc nếu vượt quá chiều cao */
        padding: 10px;
        border: 1px solid #ccc; /* Đường viền để phân biệt */
    }
    .image {
        display: flex;
        justify-content: center;
        align-items: center;
        max-width: 100px;
        overflow: hidden;
    }
    .image img, .image video {
        max-width: 100%; /* Đảm bảo hình ảnh/video nằm gọn trong khung */
        max-height: 150px; /* Chiều cao tối đa cho hình ảnh/video */
        border-radius: 5px;
        cursor: pointer;
    }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:set var="User" value="${requestScope.User}"/>
<div class="chat-infor-window">
    <div class="header-2">
        <div class="actions">
            <button onclick="goBack()">
                <i class="fas fa-arrow-left" style="font-size: x-large;">
                </i>
            </button>
        </div>
        <h1>
            Quản lý tin nhắn
        </h1>
    </div>
    <div class="profile">
        <img alt="User avatar" height="100" src="${User.avatar != null ? User.avatar : 'https://storage.googleapis.com/a1aa/image/oRjCj8SFHfWPTizQoCa6MRn8NyEvs6ZJ0uYtBbTXmoMnbz1JA.jpg'}" width="100"/>
        <div class="info">
            <h2>
                ${User.firstName} ${User.lastName}
            </h2>
            <p>
                ${User.email}
            </p>
        </div>
        <div class="actions">
            <button>
                Xem hồ sơ
            </button>
            <button>
                <i class="fas fa-ban">
                </i>
                Chặn
            </button>
            <button>
                <i class="fas fa-flag">
                </i>
                Báo cáo
            </button>
        </div>
    </div>
    <div class="stats">
        <div class="stat">
            <h2>
                <c:if test="${textReplyCount != null}">
                    ${textReplyCount}
                </c:if>
                <c:if test="${textReplyCount == null}">
                    0
                </c:if>
            </h2>
            <p>
                Tin nhắn văn bản
            </p>
        </div>
        <div class="stat">
            <h2>
                <c:if test="${mediaReplyCount != null}">
                    ${mediaReplyCount}
                </c:if>
                <c:if test="${mediaReplyCount == null}">
                    0
                </c:if>
            </h2>
            <p>
                Ảnh/video
            </p>
        </div>
        <div class="stat">
            <h2>
                <c:if test="${fileReplyCount != null}">
                    ${fileReplyCount}
                </c:if>
                <c:if test="${fileReplyCount == null}">
                    0
                </c:if>
            </h2>
            <p>
                Files
            </p>
        </div>
    </div>
    <div class="content">
        <div class="left">
            <h2>
                Tin nhắn văn bản
            </h2>
            <div class="search">
                <input id="searchInput" placeholder="Tìm kiếm tin nhắn..." type="text" onkeyup="searchMessages()"/>
                <button  onclick="searchMessages()">
                    <i class="fas fa-search">
                    </i>
                </button>
            </div>
            <div class="messages" id="messagesContainer">
                <c:if test="${not empty requestScope.textReplies}">
                    <c:forEach items="${requestScope.textReplies}" var="reply">
                        <div class="message">
                    <p>
                        <strong>
                            <c:if test="${reply.userId != User.id}">
                                Bạn:
                            </c:if>
                            <c:if test="${reply.userId == User.id}">
                                ${User.firstName} ${User.lastName}:
                            </c:if>
                        </strong>
                        <span class="messageText">${reply.text}</span>
                    </p>
                </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="right">
            <h2>
                Files
            </h2>
            <div class="files">
                <c:if test="${not empty requestScope.fileReplies}">
                    <c:forEach items="${requestScope.fileReplies}" var="reply">
                        <div class="file">
                            <a href="${reply.contentUrl}" target="_blank" class="file-link">${reply.contentUrl}</a>
                </div>
                    </c:forEach>
                </c:if>
            </div>
            <h2>
                Ảnh/video
            </h2>
            <div class="images">
                <c:if test="${not empty requestScope.mediaReplies}">
                    <c:forEach items="${requestScope.mediaReplies}" var="reply">
                        <div class="image">
                            <p>
                                <c:choose>
                                    <c:when test="${reply.contentType == 'image'}">
                                        <img src="${reply.contentUrl}" alt="Attached Image" onclick="showImage('${reply.contentUrl}')">
                                    </c:when>
                                    <c:when test="${reply.contentType == 'video'}">
                                        <video src="${reply.contentUrl}" controls style="max-width: 200px;"></video>
                                    </c:when>
                                </c:choose>
                            </p>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    function goBack() {
        window.location.href = "${pageContext.request.contextPath}/chat-box?userId=${User.id}";
    }

    function searchMessages() {
        const searchQuery = document.getElementById("searchInput").value.toLowerCase();
        const messages = document.querySelectorAll("#messagesContainer .message");

        messages.forEach(message => {
            const messageText = message.querySelector(".messageText").textContent.toLowerCase();
            if (messageText.includes(searchQuery)) {
                message.style.display = ""; // Hiển thị lại tin nhắn khớp với tìm kiếm
            } else {
                message.style.display = "none"; // Ẩn tin nhắn không khớp
            }
        });
    }
    const fileLinks = document.querySelectorAll('.file-link');
    fileLinks.forEach(link => {
        // Cắt chuỗi bằng JavaScript
        let url = link.href; // Lấy href của thẻ <a>
        let fileName = url.substring(url.lastIndexOf('/') + 1);
        // Giải mã URL
        fileName = decodeURIComponent(fileName); // Hoặc decodeURI(fileName)
        link.textContent = fileName;
    });
    function showImage(imageUrl) {
        const modal = document.createElement('div');
        modal.style.position = 'fixed';
        modal.style.top = '0';
        modal.style.left = '0';
        modal.style.width = '100%';
        modal.style.height = '100%';
        modal.style.backgroundColor = 'rgba(0, 0, 0, 0.5)'; // Nền mờ
        modal.style.display = 'flex';
        modal.style.justifyContent = 'center';
        modal.style.alignItems = 'center';
        modal.style.zIndex = '1000'; // Đảm bảo popup nằm trên cùng

        const img = document.createElement('img');
        img.src = imageUrl;
        img.style.maxWidth = '90%';
        img.style.maxHeight = '90%';

        modal.appendChild(img);
        document.body.appendChild(modal);

        // Đóng popup khi click vào nền mờ hoặc ảnh
        modal.addEventListener('click', () => {
            document.body.removeChild(modal);
        });
    }
</script>
</body>
</html>
