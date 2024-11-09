<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 29/10/2024
  Time: 07:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Bảng thông báo</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        .noti-window {
            display: flex;
            width: 100%;
            height: 80vh;
        }
        .barside {
            width: 20%;
            background-color: #b3b3b3;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .barside div {
            width: 100%;
            text-align: center;
            padding: 30px 0; /* Tăng chiều cao của các mục */
            border-bottom: 1px solid white;
            font-size: 20px;
            color: white;
        }
        .barside div:last-child {
            border-bottom: none;
        }
        .headerr {
            width: 80%;
            background-color: #f2f2f2;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
            font-size: 24px;
            font-weight: bold;
            color: #5c4033;
        }
        .contentt {
            display: flex;
            flex-direction: column;
            width: 80%;
            background-color: #f2f2f2;
            padding: 20px;
            position: relative;
        }
        .content-scrollable {
            overflow-y: auto; /* Thêm cuộn dọc cho danh sách thông báo */
            flex-grow: 1; /* Để chiếm tối đa chiều cao còn lại */
            margin-bottom: 10px; /* Tạo khoảng trống giữa nội dung và footer */
        }
        .contentt .item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 0; /* Tăng chiều cao của các mục */
            border-bottom: 1px solid #e0e0e0;
        }
        .contentt .item:last-child {
            border-bottom: none;
        }
        .contentt .item .text {
            color: #5c4033;
        }
        .contentt .item .actions {
            display: flex;
            gap: 10px;
        }
        .contentt .item .actions .detail {
            color: green;
            cursor: pointer;
        }
        .contentt .item .actions .delete {
            color: red;
            cursor: pointer;
        }
        .headerr-title {
            background-color: #FFD700; /* Vàng đậm */
            color: black;
            padding: 20px; /* Tăng chiều cao của tiêu đề */
            font-size: 24px;
            font-weight: bold;
            text-align: center;
        }
        .foot-buttons {
            position: absolute;
            bottom: 20px;
            right: 20px;
            display: flex;
            gap: 10px;
        }
        .foot-buttons button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
        .foot-buttons .mark-read {
            background-color: #4CAF50; /* Màu xanh lá cây */
            color: white;
            border: none;
        }
        .foot-buttons .delete-all {
            background-color: #f44336; /* Màu đỏ */
            color: white;
            border: none;
        }
        .modal {
            display: none; /* Ẩn mặc định */
            position: fixed; /* Ở vị trí cố định */
            z-index: 1; /* Ở trên cùng */
            left: 0;
            top: 0;
            width: 100%; /* Toàn bộ màn hình */
            height: 100%; /* Toàn bộ màn hình */
            overflow: auto; /* Cuộn nếu cần */
            background-color: rgb(0, 0, 0); /* Màu nền đen */
            background-color: rgba(0, 0, 0, 0.4); /* Nền trong suốt */
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% từ trên và ở giữa */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Có thể điều chỉnh kích thước modal */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .active {
            background-color: #ffffff; /* Màu nền cho mục đang được chọn */
            color: #555353; /* Màu chữ */
            font-weight: bold; /* Đậm chữ */
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:if test="${not empty cookie.id}">
    <c:set var="userId" value="${cookie.id.value}" />
</c:if>
<div class="container noti-window">
    <div class="barside">
        <div class="headerr-title">Loại thông báo</div>
        <div class="${type == 'System' ? 'active' : ''}">
            <a href="notification">Hệ thống</a>
        </div>

        <div class="${type == 'Appointment' ? 'active' : ''}">
            <a href="notification?type=Appointment">Lịch hẹn</a>
        </div>

        <div class="${type == 'House' ? 'active' : ''}">
            <a href="notification?type=House">Nhà</a>
        </div>
    </div>
    <div class="contentt">
        <div class="headerr">Danh sách thông báo</div>
        <!-- Danh sách thông báo -->
        <div class="content-scrollable">
        <c:forEach var="notification" items="${notificationListOfType}">
            <div class="item" id="notification-item-${notification.id}">
                <div class="text notification-text" data-type="${type}" id="notification-${notification.id}" style="font-weight: ${notification.status == 'sent' ? 'bold' : 'normal'};">${notification.content}</div>
                <div class="actions">
                    <div class="detail" onclick="showDetail('${notification.content}', '${notification.url}', ${notification.id})">Chi tiết</div>
                    <div class="delete" onclick="deleteNotification(${notification.id})">Xóa</div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty notificationListOfType}">
            <div class="item">
                <div class="text">Bạn chưa có thông báo nào.</div>
            </div>
        </c:if>
        </div>
        <div class="foot-buttons" data-type="${type}">
            <button class="mark-read">Đánh dấu đã đọc</button>
            <button class="delete-all">Xóa tất cả</button>
        </div>
    </div>
</div>
<!-- Modal Popup -->
<div id="notificationModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Chi tiết thông báo</h2>
        <div id="modalContent"></div>
        <a id="modalLink" href="#" target="_blank">Đi tới liên kết</a>
    </div>
</div>
<!-- Modal Xác Nhận Xóa -->
<div id="deleteConfirmationModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeDeleteModal()">&times;</span>
        <h2>Xác nhận xóa</h2>
        <p>Bạn có chắc chắn muốn xóa thông báo này không?</p>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeDeleteModal()">Hủy</button>
            <button type="button" class="btn btn-danger" id="confirmDelete">Xóa</button>
        </div>
    </div>
</div>
<!-- Modal Xác Nhận Xóa Tất Cả -->
<div id="deleteAllConfirmationModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close" onclick="closeDeleteAllModal()">&times;</span>
        <h2>Xác nhận xóa tất cả</h2>
        <p>Bạn có chắc chắn muốn xóa tất cả thông báo không?</p>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeDeleteAllModal()">Hủy</button>
            <button type="button" class="btn btn-danger" onclick="confirmDeleteAll()">Xóa tất cả</button>
        </div>
    </div>
</div>
<script>
    function openDeleteModal() {
        document.getElementById("deleteConfirmationModal").style.display = "block";
    }

    function closeDeleteModal() {
        const modal = document.getElementById("deleteConfirmationModal");
        modal.style.display = "none"; // Ẩn modal và lớp nền
    }
    let notificationIdToDelete = null;
    function showDetail(content, url,notificationId) {
        // Cập nhật trạng thái trước khi mở popup
        updateNotificationStatus(notificationId);
        // Hiện modal popup
        document.getElementById('modalContent').innerText = content;
        document.getElementById('modalLink').href = url;
        document.getElementById('notificationModal').style.display = 'block';
    }

    function closeModal() {
        document.getElementById('notificationModal').style.display = 'none';
    }

    // Khi người dùng nhấn vào bất kỳ đâu bên ngoài modal, đóng modal
    window.onclick = function(event) {
        const modal = document.getElementById('notificationModal');
        if (event.target === modal) {
            closeModal();
        }
    }
    function deleteNotification(notificationId) {
        console.log(notificationId);
        notificationIdToDelete = notificationId; // Lưu ID để sử dụng sau
        console.log(notificationIdToDelete);
        // Hiển thị modal xác nhận
        document.getElementById('deleteConfirmationModal').style.display = 'block';
    }

    // Hiển thị modal khi nhấn nút xóa tất cả
    $('.delete-all').on('click', function() {
        document.getElementById("deleteAllConfirmationModal").style.display = "flex";
    });

    // Đóng modal khi nhấn nút Hủy hoặc dấu X
    function closeDeleteAllModal() {
        document.getElementById("deleteAllConfirmationModal").style.display = "none";
    }

    // Thêm sự kiện cho nút xác nhận xóa trong modal
    $('#confirmDelete').on('click', function() {
        if (notificationIdToDelete !== null) {
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "deleteNotificationServlet", true); // Đường dẫn đến servlet xử lý xóa
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            let noId = notificationIdToDelete;
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        // Xóa thông báo khỏi giao diện
                        const notificationItem = document.getElementById("notification-item-" + noId);
                        console.log("Notification item found:", notificationItem);
                        if (notificationItem) {
                            notificationItem.remove(); // Xóa phần tử thông báo
                            console.log("da xoa thanh cong");
                        } else {
                            console.log("Không tìm thấy phần tử thông báo với ID:", noId);
                        }
                    } else {
                        console.error("Failed to delete notification.");
                    }
                }
            };

            xhr.send("id=" + notificationIdToDelete); // Gửi ID để xóa
            closeDeleteModal(); // Ẩn modal sau khi xác nhận
            notificationIdToDelete = null;// Đặt lại ID
        }
    });

    $(document).ready(function() {
        // Lấy type từ thuộc tính data-type
        let type = $('.foot-buttons').data('type') || 'System';

        // Đánh dấu đã đọc
        $('.mark-read').on('click', function() {
            $.ajax({
                type: 'POST',
                url: 'notification', // Đường dẫn đến servlet
                data: { action: 'markRead', type: type },
                success: function(response) {
                    console.log('All notifications of type', type, 'marked as read.');
                    // Cập nhật font-weight của tất cả các thông báo có type tương ứng
                    $('.notification-text').each(function() {
                        const notificationType = $(this).data('type');
                        if (notificationType === type) {
                            $(this).css('font-weight', 'normal'); // Đặt lại font-weight thành bình thường
                        }
                    });
                },
                error: function(xhr) {
                    console.error('Error marking notifications as read: ' + xhr.responseText);
                }
            });
        });
    });
    function confirmDeleteAll() {
        let type = $('.foot-buttons').data('type') || 'System';
        $.ajax({
            type: 'POST',
            url: 'notification', // Đường dẫn đến servlet
            data: { action: 'deleteAll', type: type },
            success: function(response) {
                console.log('All notifications of type', type, 'deleted.');
                location.reload(); // Reload trang để cập nhật danh sách thông báo
            },
            error: function(xhr) {
                console.error('Error deleting all notifications: ' + xhr.responseText);
            }
        });

        // Đóng modal sau khi gửi yêu cầu
        closeDeleteAllModal();
    }
    function updateNotificationStatus(notificationId) {
        const xhr = new XMLHttpRequest();
        // Sửa lại đường dẫn URL để khớp với servlet
        xhr.open("POST", "updateNotificationStatus", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    console.log("Notification status updated successfully.");
                    // Cập nhật giao diện: loại bỏ in đậm thông báo đã xem
                    const notificationItem = document.getElementById("notification-" + notificationId);
                    if (notificationItem) {
                        notificationItem.style.fontWeight = "normal"; // Đặt lại font-weight thành bình thường
                    }
                } else {
                    console.error("Failed to update notification status.");
                }
            }
        };

        xhr.send("id=" + notificationId);
    }

</script>
</body>
</html>
