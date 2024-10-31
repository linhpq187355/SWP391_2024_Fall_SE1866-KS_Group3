<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 22/09/2024
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html  lang="vi">
<head>
    <title>Phòng chat</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        :root {
            --chat-item-padding: 10px;
            --avatar-size: 40px;
            --header-avatar-size: 50px;
            --border-radius: 5px;
            --message-image-max-size: 200px;
            --gap: 8px;
            --suggestion-padding: 5px 10px;
            --chat-list-width: 40px;
        }

        .error-message {
            color: red;
            font-size: 0.875em;
            margin-top: 0.25rem;
        }
        .cchat-container {
            display: flex;
            height: 100vh;
        }

        .cchat-list {
            padding: 0;
            width: 30%;
            display: flex;
            flex-direction: column;
            background-color: #f0f0f0;
            border-right: 1px solid #ccc;

        }
        .cchat-list .search-bar {
            padding: 10px;
            background-color: #ffcc66;
        }
        .cchat-list .search-bar input {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
        }
        .cchat-list .cchat-item {
            display: flex;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #ccc;
        }
        .cchat-list .cchat-item:hover {
            background-color: #ffffff;
        }
        .cchat-item.active {
            background-color: #ffffff;
        }
        .cchat-list .cchat-item img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .cchat-list .cchat-item .cchat-info {
            flex-grow: 1;
        }
        .cchat-list .cchat-item .cchat-info .name {
            font-weight: bold;
        }
        .cchat-list .cchat-item .cchat-info .message {
            color: #666;
            max-width: 250px; /* Đặt chiều rộng tối đa cho tin nhắn */
            overflow: hidden; /* Ẩn phần nội dung vượt quá chiều rộng */
            white-space: nowrap; /* Không xuống dòng */
            text-overflow: ellipsis; /* Thêm dấu ... cho nội dung cắt ngắn */
        }
        .cchat-list .cchat-item .time {
            color: #666;
        }
        .cchat-window {
            width: 70%;
            display: flex;
            flex-direction: column;
        }
        .cchat-window .header {
            padding: 10px;
            background-color: #ccc;
            display: flex;
            align-items: center;
        }
        .cchat-window .header img {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .cchat-window .header .name {
            font-weight: bold;
            font-size: 1.2em;
        }
        .cchat-window .header .status {
            color: #666;
        }
        .cchat-window .messages {
            flex-grow: 1;
            padding: 10px;
            max-height: calc(100vh - 150px);
            height: 400px;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
        }
        .cchat-window .messages .message {
            display: flex;
            margin-bottom: 5px;
        }
        .seen-status {
            align-self: flex-end;
            margin-top: auto; /* Giúp "Đã xem" luôn nằm cuối cùng của danh sách */
            padding: 0 5px;
            color: gray;
            max-height: 18px;
            font-size: 12px;
        }
        .cchat-window .messages .message.sent {
            justify-content: flex-end;
        }
        .cchat-window .messages .message .text {
            max-width: 60%;
            padding: 10px;
            border-radius: 10px;
            background-color: #ccc;
        }
        .cchat-window .messages .message.sent .text {
            background-color: #e0e0e0;
        }
        .cchat-window .messages .message .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .messages .message {
            display: flex;
            margin-bottom: 5px;
            align-items: center;
        }
        .messages .message.sent {
            flex-direction: row-reverse !important;
            justify-content: flex-start !important;
        }
        .messages .message.received {
            justify-content: flex-start;
        }
        .messages .message .text {
            max-width: 60%;
            padding: 10px;
            border-radius: 10px;
            background-color: #ccc;
        }
        /* CSS cho ảnh trong message */
        .message-image {
            max-width: 200px;
            max-height: 200px;
        }
        .messages .message.sent .text {
            background-color: #e0e0e0;
            margin-left: 10px;
        }
        .messages .message .time, .messages .message .status {
            margin-left: 10px;
            font-size: 0.8em;
            color: #666;
        }

        .message {
            cursor: pointer;
        }
        .message .details {
            font-size: 12px;
            color: gray;
        }

        .avatar {
            display: flex;
            justify-content: flex-end; /* Avatar sẽ được căn phải */
            margin-top: 5px; /* Khoảng cách giữa tin nhắn và avatar */
        }

        .avatar img {
            border-radius: 50%; /* Avatar hình tròn */
        }

        .cchat-window .input-bar {
            padding: 10px;
            display: flex;
            align-items: center;
            border-top: 1px solid #ccc;
        }
        .cchat-window .input-bar input {
            flex-grow: 1;
            padding: 10px;
            border: none;
            border-radius: 20px;
            margin-right: 10px;
        }
        .cchat-window .input-bar button {
            background: none;
            border: none;
            font-size: 1.5em;
            color: #666;
            margin-right: 10px;
        }
        /* Hiển thị details bên trái nếu tin nhắn là received */
        .message.received .details {
            text-align: left;
            margin-left: 10px;
        }

        /* Hiển thị details bên phải nếu tin nhắn là sent */
        .message.sent .details {
            text-align: right;
            margin-right: 10px;
        }
        @media (max-width: 768px) {
            .cchat-container {
                flex-direction: row; /* Thay đổi thành row */
                height: auto; /* Tự động điều chỉnh chiều cao */
            }

            .cchat-list {
                width: 10%; /* 10% cho avatar */
                height: auto; /* Tự động điều chỉnh chiều cao */
                border-right: 1px solid #ccc;
                border-bottom: none; /* Bỏ border-bottom */
                display: flex;
                flex-direction: column; /* Avatar xếp chồng lên nhau */
                align-items: center;
                padding: var(--chat-item-padding) 0; /* Padding top/bottom */
            }

            .cchat-list .search-bar {
                display: none; /* Ẩn search bar */
            }

            .cchat-list .cchat-item {
                display: flex;
                align-items: center;
                justify-content: center; /* Căn giữa avatar */
                border-bottom: none; /* Bỏ border-bottom */
            }

            .cchat-list .cchat-item img {
                width: var(--avatar-size);
                height: var(--avatar-size);
                border-radius: 50%;
                margin: 0;
            }

            .cchat-list .cchat-item .cchat-info {
                display: none; /* Ẩn thông tin người dùng */
            }
            .cchat-list .cchat-item .time {
                display: none; /* Ẩn thông tin người dùng */
            }
            .cchat-window {
                width: 90%; /* 90% cho chat window */
                height: auto;
                flex: 1;
            }

            .messages {
                height: calc(88vh - 13em); /* Chiều cao message chiếm gần như toàn bộ màn hình, trừ input và header*/
            }

            .input-bar {
                flex-wrap: nowrap;
            }
        }
        .suggestions-container {
            display: flex;
            overflow-x: auto;
            padding: 10px;
            gap: 8px;
        }
        .suggestion-item {
            background-color: #f0f0f0;
            border: 1px solid #ccc;
            border-radius: 16px;
            padding: 5px 10px;
            cursor: pointer;
            white-space: nowrap;
        }
        .file-preview {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .file-preview img {
            max-width: 100px;
            max-height: 100px;
            margin-right: 10px;
        }

        .file-preview .file-name {
            max-width: 200px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        /* Style cho icon đính kèm file */
        .input-bar label[for="fileInput"] i {
            font-size: 1.5em;
            color: #666;
            margin-right: 10px;
            cursor: pointer; /* Thêm cursor pointer để cho thấy có thể click */
        }

        /* Style cho icon đính kèm ảnh */
        .input-bar label[for="mediaInput"] i {
            font-size: 1.5em;
            color: #666;
            margin-right: 10px;
            cursor: pointer; /* Thêm cursor pointer để cho thấy có thể click */
        }
        #filePreviewContainer{
            display: flex;
            flex-direction: row;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
    <c:if test="${not empty cookie.id}">
        <c:set var="sendId" value="${cookie.id.value}" />
    </c:if>
    <c:if test="${not empty requestScope.replies}">
        <c:set var="latestReply" value="${requestScope.replies[requestScope.replies.size() - 1]}" />
    </c:if>
<section class="bg-light">
    <c:set var="User" value="${requestScope.User}"/>
    <div class="cchat-container">
        <div class="cchat-list">
            <div class="search-bar">
                <input placeholder="Tìm kiếm trong đoạn chat" type="text"/>
            </div>
            <div class="chat-items">
                 <c:if test="${not empty requestScope.listUserConversation}">
        <c:forEach items="${requestScope.listUserConversation.entrySet()}" var="entry">
            <c:set var="user" value="${entry.key}" />
            <c:set var="reply" value="${entry.value}" />
            <div class="cchat-item ${user.id == User.id ? 'active' : ''}" data-user-id="${user.id}">
                <a href="chat-box?userId=${user.id}" class="cchat-item-link">
                <img alt="User profile" height="40"
                     src="${user.avatar != null ? user.avatar : 'https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png'}"
                     width="40"/>
                </a>
                <div class="cchat-info">
                    <a href="chat-box?userId=${user.id}" class="cchat-item-link">
                    <div class="name">
                        ${user.firstName} ${user.lastName}
                    </div>
                    </a>
                    <input type="hidden" id="cId" value="${reply.conversationId}" />
                    <div class="message">
                        <c:choose>
                            <c:when test="${not empty reply.contentType}">
                                <c:if test="${reply.userId == sendId}">
                                    Bạn:
                                </c:if>
                                <c:choose>
                                    <c:when test="${reply.contentType == 'image'}">
                                        Đã gửi ảnh
                                    </c:when>
                                    <c:otherwise>
                                        Đã gửi ${reply.contentType}
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${reply.userId == sendId}">
                                    Bạn:
                                </c:if>
                                ${reply.text}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="time">
                    <!-- Thay đổi giá trị thời gian theo dữ liệu của bạn -->
                    ${reply.timeAgo} <!-- Giả sử bạn muốn hiển thị thời gian gửi tin nhắn -->
                </div>
            </div>
        </c:forEach>
    </c:if>
            </div>
        </div>
        <div class="cchat-window" data-user-id="${User.id}">
                <div class="header" style="justify-content: space-between;">
<div style="display: flex;">
    <img alt="User profile" height="50"
         src="${User.avatar != null ? User.avatar : 'https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png'}"
         width="50"/>
    <div>
        <div class="name">
            ${User.firstName} ${User.lastName}
        </div>
        <div class="status" id="status-chat">
            Đang offline
        </div>
    </div>
</div>
                    <a href="chat-information?userId=${User.id}">
                        <i class="fas fa-info-circle" style="font-size: xx-large;"></i>
                    </a>
            </div>
            <div class="messages" id="messages">
                <c:if test="${not empty requestScope.replies}">
                    <c:forEach items="${requestScope.replies}" var="reply">
                        <div class="message ${reply.userId == User.id ? 'received' : 'sent'}"  onclick="toggleDetails(this)">
                            <c:if test="${reply.userId == User.id}">
                                <img alt="User profile" height="40"
                                     src="${User.avatar != null ? User.avatar : 'https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png'}"
                                     class="avatar"/>
                            </c:if>
                            <c:choose>
                                <c:when test="${empty reply.text or reply.text == ''}">
                                    <c:if test="${not empty reply.contentType and not empty reply.contentUrl}">
                                        <c:choose>
                                            <c:when test="${reply.contentType == 'image'}">
                                                <img src="${reply.contentUrl}" alt="Attached Image" class="message-image" onclick="showImage('${reply.contentUrl}')">
                                            </c:when>
                                            <c:when test="${reply.contentType == 'video'}">
                                                <video src="${reply.contentUrl}" controls style="max-width: 200px;"></video>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${reply.contentUrl}" target="_blank" class="file-link">${reply.contentUrl}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <div class="text">
                                        ${reply.text}
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- Phần thời gian và trạng thái sẽ bị ẩn đi ban đầu -->
                            <div class="details" style="display: none;">
                                <div class="time">
                                    ${reply.time}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <div class="seen-status" id="seen-status" style="display: none;">Đã xem</div>
            </div>
            <!-- Thanh chứa câu hỏi gợi ý -->
            <div id="suggestions-container" class="suggestions-container">
                <!-- Các câu hỏi gợi ý -->
                <button class="suggestion-item" onclick="fillSuggestion('Giá thuê phòng là bao nhiêu?')">Giá thuê phòng là bao nhiêu?</button>
                <button class="suggestion-item" onclick="fillSuggestion('Có phòng nào cho 2 người không?')">Có phòng nào cho 2 người không?</button>
                <button class="suggestion-item" onclick="fillSuggestion('Có gần siêu thị không?')">Có gần siêu thị không?</button>
                <button class="suggestion-item" onclick="fillSuggestion('Điều kiện vệ sinh ra sao?')">Điều kiện vệ sinh ra sao?</button>
                <!-- Thêm câu hỏi gợi ý khác -->
            </div>
            <div class="input-bar" style="
                        flex-direction: column;
                        flex-flow: wrap;
                    ">
                <!-- Thay form gửi tin nhắn thành việc xử lý bằng JavaScript -->
                <div id="filePreviewContainer"></div>
                <div style="
                        width: 100%;
                        display: flex;
                        justify-content: center;
                        ">
                    <label for="fileInput" style="margin: auto;">
                        <i class="fas fa-paperclip"></i> <!-- Icon đính kèm file -->
                    </label>
                    <input type="file" id="fileInput" style="display: none;" multiple onchange="previewFiles(this.files)">
                    <label for="mediaInput" style="margin: auto;">
                        <i class="fas fa-image"></i> <!-- Icon đính kèm ảnh/video -->
                    </label>
                    <input type="file" id="mediaInput" accept="image/*,video/*" style="display: none;" multiple onchange="previewFiles(this.files)">
                    <input id="replyText" placeholder="Aa" type="text" required/>
                    <input type="hidden" id="userId" value="${User.id}"/> <!-- ID của người gửi -->
                    <input type="hidden" id="sendId" value="${sendId}" />
                    <input type="hidden" id="messageStatus" value="${requestScope.messageStatus != null ? requestScope.messageStatus : 'sent'}" />
                    <input type="hidden" id="conversationId" value="${requestScope.conversationId}"/> <!-- ID của cuộc trò chuyện -->
                    <button onclick="sendMessage()">
                        <i class="fas fa-paper-plane"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<%
    String message = (String) session.getAttribute("message");
    String messageType = (String) session.getAttribute("messageType");
    if (message != null && messageType != null) {
%>
<script type="text/javascript">
    Swal.fire({
        icon: '<%= messageType %>',
        title: '<%= message %>'
    });
</script>
<%
        // Sau khi hiển thị thông báo, xóa nó khỏi session để tránh hiển thị lại khi trang được làm mới
        session.removeAttribute("message");
        session.removeAttribute("messageType");
    }
%>
<script type="text/javascript">
    let ws;
    let autoReplySent = false;
    let conversationId = document.getElementById("conversationId").value;
    let receivedId = document.getElementById("userId").value;
    let sendId = document.getElementById("sendId").value;
    let messagesContainer = document.getElementById("messages");

    function connect() {
        let intervalId;
        ws = new WebSocket("ws://localhost:9999/homeSharing/chat");
        const seenStatus = document.getElementById("seen-status");
        ws.onmessage = function(event) {
            let data = JSON.parse(event.data);
            if(data.type === "seen") {
                console.log("received: "+data.received);
                console.log("sent: " + sendId);
                if(data.received === sendId){
                    seenStatus.style.display = 'flex';
                }
            } else if (data.type === "start") {
                if(data.send === receivedId) {
                    updateStatus("Đang hoạt động");
                }
            } else if (data.type === "no") {
                updateStatus("Đang offline");
            } else if (data.type === "yes") {
                updateStatus("Đang hoạt động");
            } else {
                // Hiển thị tin nhắn nhận được hoặc gửi đi
                if(data.conversationId === conversationId) {
                    if (data.replies && data.replies.length > 0) {
                        data.replies.forEach(reply => {
                            let messages = document.createElement("div");
                            messages.classList.add("message");
                            messages.classList.add(data.received === receivedId ? "sent" : "received");
                            messages.setAttribute("onclick", "toggleDetails(this)");
                            // Kiểm tra và xử lý avatar nếu là người nhận
                            if (data.received !== receivedId) {
                                updateStatus("Đang hoạt động");
                                let avatar = document.createElement("img");
                                avatar.height = 40;
                                avatar.width = 40;
                                avatar.classList.add("avatar");
                                avatar.src = "${User.avatar != null ? User.avatar : 'https://file.hstatic.net/200000020602/file/top-nhung-loai-hoa-dep-nhat__6__aba5ffa9c7324c1da0440565d915bb1d_grande.png'}";
                                messages.appendChild(avatar);
                                ws.send(JSON.stringify({ received: receivedId, send: sendId, conversationId: conversationId, message: "", type: "seen" }));
                                document.getElementById("seen-status").style.display = 'none';
                            }
                            // Nếu là tin nhắn văn bản
                            if (reply.text && reply.text.trim() !== "") {
                                let textDiv = document.createElement("div");
                                textDiv.classList.add("text");
                                textDiv.textContent = reply.text;
                                messages.appendChild(textDiv);
                                // Thêm phần thời gian và trạng thái, đặt ban đầu là ẩn
                                let detailsDiv = document.createElement("div");
                                detailsDiv.classList.add("details");
                                detailsDiv.style.display = "none";

                                let timeDiv = document.createElement("div");
                                timeDiv.classList.add("time");
                                timeDiv.textContent = reply.time;
                                detailsDiv.appendChild(timeDiv);

                                messages.appendChild(detailsDiv);
                                // Thêm tin nhắn vào khung chat
                                if (messagesContainer) {
                                    messagesContainer.appendChild(messages);
                                    document.getElementById('messages').appendChild(seenStatus);
                                } else {
                                    console.error("messagesContainer không tìm thấy!");
                                }
                            } else if (reply.contentType && reply.contentUrl) {
                                let fileDiv = document.createElement("div");
                                if (reply.contentType === "image") {
                                    let img = document.createElement("img");
                                    img.src = reply.contentUrl;
                                    img.alt = "image";
                                    img.style.maxWidth = "200px";
                                    img.style.maxHeight = "200px";
                                    img.onclick = function() { showImage(reply.contentUrl); };
                                    // Đợi ảnh tải xong trước khi thêm vào khung chat
                                    img.onload = function() {
                                        fileDiv.appendChild(img);
                                        messages.appendChild(fileDiv);
                                        // Thêm phần thời gian và trạng thái, đặt ban đầu là ẩn
                                        let detailsDiv = document.createElement("div");
                                        detailsDiv.classList.add("details");
                                        detailsDiv.style.display = "none";

                                        let timeDiv = document.createElement("div");
                                        timeDiv.classList.add("time");
                                        timeDiv.textContent = reply.time;
                                        detailsDiv.appendChild(timeDiv);

                                        messages.appendChild(detailsDiv);
                                        if (messagesContainer) {
                                            messagesContainer.appendChild(messages);
                                            document.getElementById('messages').appendChild(seenStatus);
                                        }
                                    };
                                } else if (reply.contentType === "video") {
                                    let video = document.createElement("video");
                                    video.src = reply.contentUrl;
                                    video.controls = true;
                                    video.style.maxWidth = "200px";
                                    // Đợi video tải xong trước khi thêm vào khung chat
                                    video.onloadeddata = function() {
                                        fileDiv.appendChild(video);
                                        messages.appendChild(fileDiv);
                                        // Thêm phần thời gian và trạng thái, đặt ban đầu là ẩn
                                        let detailsDiv = document.createElement("div");
                                        detailsDiv.classList.add("details");
                                        detailsDiv.style.display = "none";

                                        let timeDiv = document.createElement("div");
                                        timeDiv.classList.add("time");
                                        timeDiv.textContent = reply.time;
                                        detailsDiv.appendChild(timeDiv);

                                        messages.appendChild(detailsDiv);
                                        if (messagesContainer) {
                                            messagesContainer.appendChild(messages);
                                            document.getElementById('messages').appendChild(seenStatus);
                                        }
                                    };
                                } else {
                                    let fileLink = document.createElement("a");
                                    fileLink.href = reply.contentUrl;
                                    let nameFile = reply.contentUrl.substring(reply.contentUrl.lastIndexOf('/') + 1);
                                    fileLink.textContent = nameFile;
                                    fileLink.classList.add("file-link");
                                    fileLink.target = "_blank";
                                    fileDiv.appendChild(fileLink);
                                    messages.appendChild(fileDiv);
                                    // Thêm phần thời gian và trạng thái, đặt ban đầu là ẩn
                                    let detailsDiv = document.createElement("div");
                                    detailsDiv.classList.add("details");
                                    detailsDiv.style.display = "none";

                                    let timeDiv = document.createElement("div");
                                    timeDiv.classList.add("time");
                                    timeDiv.textContent = reply.time;
                                    detailsDiv.appendChild(timeDiv);

                                    messages.appendChild(detailsDiv);
                                    if (messagesContainer) {
                                        messagesContainer.appendChild(messages);
                                        document.getElementById('messages').appendChild(seenStatus);
                                    }
                                }
                            }
                        });
                        messagesContainer.scrollTop = messagesContainer.scrollHeight;
                    }
                    // Cập nhật danh sách chat
                    updateChatList(data);
                }
            }
        };

        ws.onopen = function() {
            console.log("connection open");
            ws.send(JSON.stringify({ received: receivedId,send: sendId, conversationId: conversationId, message: "", type: "start" }));
            // Kiểm tra và gửi "seen" nếu cần thiết
            checkAndSendSeenStatus();
        };

        ws.onclose = function() {
            console.log("connection close");
        };
    }

    function checkOnline() {
        console.log("This function runs every 30 seconds.");
        ws.send(JSON.stringify({ received: receivedId,send: sendId, conversationId: conversationId, message: "", type: "ask" }));
    }

    // Gọi hàm myFunction mỗi 30 giây (30000 milliseconds)
    setInterval(checkOnline, 30000);

    function updateStatus(status) {
        // Tìm phần tử hiển thị trạng thái của userId và cập nhật
        let statusElement = document.getElementById("status-chat");
        if (statusElement) {
            statusElement.textContent = status;
        }
    }

    function previewFiles(files) {
        const previewContainer = document.getElementById('filePreviewContainer');
        previewContainer.innerHTML = ''; // Xóa các preview cũ

        for (const file of files) {
            const preview = document.createElement('div');
            preview.classList.add('file-preview');

            if (file.type.startsWith('image/')) {
                const img = document.createElement('img');
                img.src = URL.createObjectURL(file);
                preview.appendChild(img);
            } else if (file.type.startsWith('video/')) {
                // Hiển thị video
                const video = document.createElement('video');
                video.src = URL.createObjectURL(file);
                video.controls = true; // Thêm điều khiển phát video
                preview.appendChild(video);
            } else {
                // Chỉ hiển thị tên file nếu không phải là ảnh hoặc video
                const fileName = document.createElement('span');
                fileName.classList.add('file-name');
                fileName.textContent = file.name;
                preview.appendChild(fileName);
            }

            // Thêm nút xóa file
            const removeButton = document.createElement('button');
            removeButton.textContent = 'x';
            removeButton.onclick = () => {
                previewContainer.removeChild(preview);
                // Xóa file khỏi input (cần xử lý riêng cho fileInput và imageInput)

                const fileInput = document.getElementById('fileInput');
                const imageInput = document.getElementById('mediaInput');

                const dt = new DataTransfer()
                let updatedFilesInput;

                if(fileInput.files && Array.from(fileInput.files).includes(file)){
                    updatedFilesInput = fileInput;
                } else {
                    updatedFilesInput = mediaInput;
                }
                Array.from(updatedFilesInput.files).forEach(f => {
                    if (f !== file) dt.items.add(f)
                })
                updatedFilesInput.files = dt.files // Assign to the real input

            };
            preview.appendChild(removeButton);


            previewContainer.appendChild(preview);
        }
    }

    async function uploadFiles(files) {
        const formData = new FormData();

        // Thêm tất cả file vào FormData
        for (const file of files) {
            formData.append("files", file);
        }

        try {
            const response = await fetch("${pageContext.request.contextPath}/upload-file-chat", { // Thêm context path
                method: "POST",
                body: formData,
                onUploadProgress: (event) => {
                    // Xử lý tiến trình upload
                    const progress = Math.round((event.loaded / event.total) * 100);
                    console.log(`Upload progress: ${progress}%`);
                    // Hiển thị tiến trình trên giao diện (nếu cần)
                }
            });


            if (!response.ok) {
                const errorData = await response.json(); // Lấy thông tin lỗi từ server nếu có
                throw new Error(errorData.message || "Upload failed");
            }

            return await response.json();
        } catch (error) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi upload file',
                text: error.message
            });
            console.error("Error uploading files:", error);
            return null;
        }
    }

    function updateChatList(data) {
        const chatItems = document.querySelectorAll('.cchat-item');
        const chatItem = Array.from(chatItems).find(item => {
            const cIdInput = item.querySelector('input#cId');
            return cIdInput && cIdInput.value === data.conversationId;
        });
        if (chatItem) {
            const messageDiv = chatItem.querySelector(".message");

            let prefix = "";
            if (data.send === sendId) {
                prefix = "Bạn: ";
            }
            if(data.files && data.files.length > 0) {
                messageDiv.textContent = prefix + "Đã gửi tệp tin.";
            } else {
                messageDiv.textContent = prefix + data.message;
            }

            const timeDiv = chatItem.querySelector(".time");
            timeDiv.textContent = formatTimeAgo(new Date()); // Hàm formatTimeAgo cần được định nghĩa

            // Đưa cchat-item lên đầu danh sách (tùy chọn)
            const chatList = chatItem.parentNode;
            chatList.insertBefore(chatItem, chatList.firstChild);

        } else {
            // Xử lý trường hợp không tìm thấy cchat-item (ví dụ: tạo mới)
            console.error("Không tìm thấy .cchat-item cho userId:", data.conversationId);
        }

    }

    // Hàm formatTimeAgo (cần được triển khai) - Ví dụ:
    function formatTimeAgo(date) {
        const now = new Date();
        const diff = now.getTime() - date.getTime();
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);

        if (seconds < 60) {
            return `${seconds} giây trước`;
        } else if (minutes < 60) {
            return `${minutes} phút trước`;
        } else if (hours < 24) {
            return `${hours} giờ trước`;
        } else {
            return date.toLocaleDateString(); // Hoặc định dạng ngày khác
        }

    }

    async function sendMessage() {
        const message = document.getElementById("replyText").value;
        const fileInputs = document.querySelectorAll('input[type="file"]');
        const files = [];

        // Lấy tất cả các file từ các input file
        fileInputs.forEach(input => {
            if (input.files.length > 0) {
                files.push(...input.files);
            }
        });
        let type = "message";
        if (message.trim() !== "" || files.length > 0) {
            // Nếu có file, gọi hàm uploadFiles
            let fileData = [];
            if (files.length > 0) {
                fileData = await uploadFiles(files);
                if (!fileData) {
                    console.error("File upload failed");
                    return;
                }
            }
            // Gửi tin nhắn
            const messageData = {
                received: receivedId,
                send: sendId,
                conversationId: conversationId,
                message: message,
                type: type,
                files: fileData // Thêm danh sách fileData vào messageData
            };
            ws.send(JSON.stringify(messageData));
            document.getElementById("replyText").value = "";
            document.getElementById("seen-status").style.display = 'none';
            const roleId = ${User.rolesId};
            if (roleId != null && roleId === 4 && autoReplySent === false) {
                const autoReplyMessage = "Cảm ơn bạn đã liên hệ! Chủ nhà sẽ phản hồi tin nhắn của bạn trong vòng 24 giờ.";
                ws.send(JSON.stringify({
                    received: sendId,
                    send: receivedId,
                    conversationId: conversationId,
                    message: autoReplyMessage,
                    type: type
                }));
                autoReplySent = true;
            }
            // Reset lại input file và text sau khi gửi
            document.getElementById("replyText").value = "";
            document.getElementById("seen-status").style.display = 'none';
            // Xóa preview và reset input file
            const previewContainer = document.getElementById('filePreviewContainer');
            previewContainer.innerHTML = '';
            fileInputs.forEach(input => { input.value = ''; });

        }
    }

    function disconnect() {
        if (ws != null) {
            ws.close();
        }
    }

    function toggleDetails(element) {
        // Tìm phần tử chi tiết (details) bên trong thẻ div của tin nhắn
        const details = element.querySelector('.details');

        // Kiểm tra và thay đổi thuộc tính display
        if (details.style.display === 'none') {
            details.style.display = 'block'; // Hiển thị phần thời gian
        } else {
            details.style.display = 'none'; // Ẩn phần thời gian
        }
    }
    function checkAndSendSeenStatus() {
        // Gán `latestReply` vào biến JavaScript
        const latestReply = {
            id: "${latestReply.id}",
            userId: "${latestReply.userId}"
        };

        if (latestReply && latestReply.userId !== sendId) {
            ws.send(JSON.stringify({ received: receivedId, send: sendId, conversationId: conversationId, message: "", type: "seen" }));
            document.getElementById("seen-status").style.display = 'none';
        }
    }
    function handleSeenStatus() {
        const messageStatusElement = document.getElementById("messageStatus");
        const seenStatus = document.getElementById("seen-status");
        document.getElementById('messages').scrollTop = document.getElementById('messages').scrollHeight;
        // Kiểm tra xem các phần tử có tồn tại không
        if (messageStatusElement && seenStatus) {
            const messageStatus = messageStatusElement.value;

            if (messageStatus === "seen") {
                seenStatus.style.display = 'flex';
                console.log(messageStatus);
            } else {
                seenStatus.style.display = 'none'; // Ẩn nếu không phải "seen"
                console.log(messageStatus);
            }
        } else {
            console.warn("Không tìm thấy messageStatus hoặc seenStatus trong DOM.");
        }
    }

    window.onload = function () {
        checkHost();
        handleSeenStatus();
        connect(); // Gọi hàm connect như cũ
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    };
    // Lắng nghe sự kiện "keypress" trên ô input
    document.getElementById("replyText").addEventListener("keypress", function(event) {
        // Kiểm tra nếu phím Enter (mã phím 13) được nhấn
        if (event.key === "Enter") {
            event.preventDefault(); // Ngăn không cho Enter tạo dòng mới trong ô input
            sendMessage();
        }
    });
    function checkHost() {
        const roleId = ${User.rolesId};
        if(roleId == null || roleId !== 4) {
            document.getElementById('suggestions-container').style.display = 'none';
        }
    }
    function fillSuggestion(question) {
        // Điền câu hỏi vào ô nhập liệu
        document.getElementById('replyText').value = question;

        // Ẩn thanh chứa câu hỏi sau khi chọn
        document.getElementById('suggestions-container').style.display = 'none';
    }

    function resetSuggestions() {
        // Hiển thị lại thanh chứa câu hỏi (nếu muốn hiện lại khi người dùng xoá nội dung ô input)
        document.getElementById('suggestions-container').style.display = 'flex';
    }

    // Nếu muốn hiển thị lại thanh gợi ý khi xóa ô nhập liệu
    document.getElementById('replyText').addEventListener('input', function () {
        const roleId = ${User.rolesId};
        if (this.value === '' && roleId === 4) {
            resetSuggestions();
        }
    });
    const searchInput = document.querySelector('.search-bar input');
    const chatItems = document.querySelectorAll('.cchat-item');

    searchInput.addEventListener('input', function () {
        const searchTerm = this.value.toLowerCase();

        chatItems.forEach(function (item) {
            const nameElement = item.querySelector('.name');
            const nameText = nameElement.textContent.toLowerCase();

            if (nameText.includes(searchTerm)) {
                item.style.display = ""; // Hiện thị nếu khớp
            } else {
                item.style.display = "none"; // Ẩn nếu không khớp
            }
        });
    });

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
