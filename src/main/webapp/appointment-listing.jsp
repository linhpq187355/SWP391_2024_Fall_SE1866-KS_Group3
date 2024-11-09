<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/27/2024
  Time: 4:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Quản lí lịch hẹn</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="ThangLT">
    <meta name="keyword" content="html5, css, bootstrap, property, real-estate theme , bootstrap template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,800' rel='stylesheet' type='text/css'>

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <link rel="stylesheet" href="assets/css/normalize.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/fontello.css">
    <link href="assets/fonts/icon-7-stroke/css/pe-icon-7-stroke.css" rel="stylesheet">
    <link href="assets/fonts/icon-7-stroke/css/helper.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/wizard.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
    <style>
        .container1 {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            font-size: 2rem;
            font-weight: 600;
            color: #333;
        }
        .btn-group {
            display: flex;
            gap: 10px;
            margin: 20px 0;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            font-weight: 600;
        }
        .btn-primary {
            background-color: #4a56e2;
            color: white;
        }
        .btn-secondary {
            background-color: #f1f3f5;
            color: #333;
        }
        .btn-secondary:hover {
            background-color: #e2e6ea;
        }
        .btn-primary:hover {
            background-color: #3b47c1;
        }
        .tabs {
            display: flex;
            gap: 40px;
            margin: 20px 0;
        }
        .tab {
            font-size: 1.5rem;
            font-weight: 600;
            color: #6c757d;
            cursor: pointer;
        }
        .tab.active {
            color: #4a56e2;
        }
        .event-card {
            height: 120px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px 20px 20px 0;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .event-date {
            margin-left: 20px;
            width: 70px;
            font-size: 2.5rem;
            font-weight: 600;
            color: #6c63ff;
            text-align: center;
        }
        .event-date span {
            display: block;
            font-size: 1.5rem;
            color: #6c757d;
        }
        .event-details {
            flex-grow: 1;
            margin-left: 20px;
            border-left: 1px solid #edeef2;
            padding-left: 20px;
        }
        .event-title {
            font-size: 2.3rem;
            font-weight: 600;
            color: #333;
        }
        .event-status {
            background-color: #e9ecef;
            color: #6c757d;
            padding: 0 10px;
            border-radius: 5px;
            font-size: 0.9rem;
            margin-left: 10px;
            display: none;
        }
        .event-meta {
            font-size: 1.5rem;
            color: #6c757d;
            margin-top: 5px;
        }
        .popup {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            width: 600px;
            display: none;
            text-align: center;
            z-index: 1000;
            max-height: 600px;
            overflow-y: auto;
        }
        .popup h4 {
            color: #ff0000;
            font-size: 18px;
            margin-bottom: 10px;
        }
        .popup p {
            color: #333;
            margin-bottom: 20px;
        }
        .popup button {
            background-color: #ffa500;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .overlay1 {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4); /* Darken slightly */
            backdrop-filter: blur(5px); /* Blur effect */
            display: none; /* Hidden by default */
            z-index: 999;
        }
        .xheader {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 20px;
            border-bottom: 1px solid #ccc;
        }
        .xheader h2 {
            margin: 0;
            font-size: 25px;
            color: #000;
        }
        .xheader .xclose {
            font-size: 20px;
            cursor: pointer;
        }
        .xform-group {
            display: flex;
            justify-content: space-between;
            flex-direction: column;
            align-items: baseline;
            margin: 0 15px;
        }
        .xform-group label {
            display: block;
            margin-bottom: 5px;
            font-size: 17px;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div style="width: 80%;margin: auto;">
    <h2 style="margin-left: 15px;color: #000;">Lịch hẹn của tôi</h2>
    <div class="btn-group" style="margin-left: 35px;margin-top: 10px;">
        <button class="btn btn-primary" onclick="window.location.href='appointment-tenant-list'">Danh sách</button>
        <button class="btn btn-secondary" onclick="window.location.href='calender-appointment.jsp'">Lịch</button>
    </div>
</div>
<div class="container1" style="background-color: #f5f6fa;width: 100%;padding: 10px 150px 10px 150px;">

    <div class="tabs">
        <div class="tab active" data-filter="all">Tất cả</div>
        <div class="tab" data-filter="tenantPending">Chờ xác nhận</div>
        <div class="tab" data-filter="hostPending">Chờ phản hồi</div>
        <div class="tab" data-filter="rejected">Đã từ chối</div>
        <div class="tab" data-filter="accepted">Đã chấp nhận</div>
        <div class="tab" data-filter="cancelled">Đã hủy</div>
        <div class="tab" data-filter="expired">Đã quá hạn</div>
    </div>
    <c:forEach items="${requestScope.appointmentList}" var="appointmentList" >
        <c:forEach items="${requestScope.hostList}" var="hostList">
            <c:if test="${hostList.id == appointmentList.hostId}">
                <c:forEach items="${requestScope.homeList}" var="homeList">
                    <c:if test="${homeList.id == appointmentList.homeId}">
                        <div class="event-card" data-status="${appointmentList.status}" onclick="showPopup('${appointmentList.status}', '${homeList.name}', '${homeList.address}', '${appointmentList.startDate}', '${appointmentList.endDate}', '${hostList.firstName}','${hostList.lastName}', '${hostList.phoneNumber}', '${hostList.email}','${appointmentList.note}','${appointmentList.rejectReason}','${appointmentList.id}','${appointmentList.cancelReason}')">
                            <div style="
                            <c:if test="${appointmentList.status == 'tenantPending' || appointmentList.status == 'hostPending'}">
                                    background-color: #ccc;
                            </c:if>
                            <c:if test="${appointmentList.status == 'accepted'}">
                                    background-color: limegreen;
                            </c:if>
                            <c:if test="${appointmentList.status == 'rejected'}">
                                    background-color: red;
                            </c:if>
                            <c:if test="${appointmentList.status == 'cancelled' || appointmentList.status == 'expired'}">
                                    background-color: #111111;
                            </c:if>

                                    width: 10px;height: 100%;"></div>
                            <div class="event-date">
                                    ${fn:substring(appointmentList.startDate, 8, 10)} <span>T${fn:substring(appointmentList.startDate, 5, 7)}/${fn:substring(appointmentList.startDate, 0, 4)}</span>
                            </div>


                                    <div class="event-details">
                                        <div class="event-title">
                                                ${homeList.name}

                                            <span class="event-status upcoming-status" data-start-date="${appointmentList.startDate}" data-status="${appointmentList.status}">Sắp tới</span>
                                        </div>
                                        <div class="event-meta">
                                            <i class="far fa-clock"></i> ${fn:substring(appointmentList.startDate, 11, 16)} - ${fn:substring(appointmentList.endDate, 11, 16)} &nbsp;&nbsp;
                                            <i class="fas fa-map-marker-alt"></i> ${homeList.address}
                                        </div>
                                    </div>


                            <div style="width: 25%;border-left: 1px solid #edeef2;padding-left: 10px;font-size: 1.3em;height: 100%;max-height: 150px; overflow-y: auto;">
                                <i class="fa-regular fa-note-sticky" style="margin-right: 5px;"></i>
                                    ${appointmentList.note}
                            </div>

                            <c:if test="${appointmentList.status == 'tenantPending'}">
                                <div style="background-color: #ccc; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
                                    Chờ xác nhận
                                </div>
                            </c:if>
                            <c:if test="${appointmentList.status == 'hostPending'}">
                                <div style="background-color: #ccc; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
                                    Chờ phản hồi
                                </div>
                            </c:if>
                            <c:if test="${appointmentList.status == 'accepted'}">
                                <div style="background-color: limegreen; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
                                    Đã chấp nhận
                                </div>
                            </c:if>
                            <c:if test="${appointmentList.status == 'rejected'}">
                                <div style="background-color: red; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
                                    Đã từ chối
                                </div>
                            </c:if>
                            <c:if test="${appointmentList.status == 'cancelled'}">
                                <div style="background-color: #111111; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
                                    Đã hủy
                                </div>
                            </c:if>
                            <c:if test="${appointmentList.status == 'expired'}">
                                <div style="background-color: #111111; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
                                    Đã quá hạn
                                </div>
                            </c:if>

                        </div>
                    </c:if>
                </c:forEach>
            </c:if>

        </c:forEach>

    </c:forEach>
    <div id="paginationControls" style="text-align: center; margin-top: 20px;">
        <button onclick="changePage(-1)">Trang trước</button>
        <span id="currentPage">1</span> / <span id="totalPages">1</span>
        <button onclick="changePage(1)">Trang sau</button>
    </div>


</div>

<div class="overlay1" id="overlay"></div>
<div class="popup" id="popup">
    <div class="xheader">
        <h2>
            Thông tin chi tiết lịch hẹn
        </h2>
        <div id="pTenantPending" style="display: none;background-color: #ccc; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
            Chờ xác nhận
        </div>
        <div id="pHostPending" style="display: none;background-color: #ccc; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
            Chờ phản hồi
        </div>
        <div id="pAccept" style="display: none;background-color: limegreen; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;margin-left: 20px;">
            Đã chấp nhận
        </div>
        <div id="pReject" style="display: none;background-color: red; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
            Đã từ chối
        </div>
        <div id="pCancelled" style="display: none;background-color: #111111; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
            Đã hủy
        </div>
        <div id="pExpired" style="display: none;background-color: #111111; color: #fff;padding: 5px 10px;border-radius: 10px;margin-right: 20px;width: 109px;text-align: center;margin-left: 20px;">
            Đã quá hạn
        </div>
        <span class="xclose">
              ×
             </span>
    </div>


    <div class="xform-group" style="margin-top: 20px">
            <label>
                Tên phòng/nhà:
            </label>
            <p id="pHomeName" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group">
            <label>
                Địa chỉ:
            </label>
            <p id="pHomeAddress" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group">
            <label>
                Thời gian:
            </label>
            <p id="pTime" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group">
            <label>
                Tên chủ trọ:
            </label>
            <p id="pHostName" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group">
        <label>
            SĐT:
        </label>
        <p id="pHostPhone" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group">
        <label>
            Email:
        </label>
        <p id="pHostEmail" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group" id="dNote" style="display: none">
        <label>
            Ghi chú:
        </label>
        <p id="pNote" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group" id="dReason" style="display: none">
        <label>
            Lí do từ chối:
        </label>
        <p id="pRejectReason" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-group" id="dCancelReason" style="display: none">
        <label>
            Lí do hủy:
        </label>
        <p id="pCancelReason" style="margin: 0;font-size: 18px;padding-left: 15px;"></p>
    </div>
    <div class="xform-actions" style="display: none; justify-content: space-around" id="aptm-action">
        <button id="pEdit">
            Chỉnh sửa
        </button>
        <button id="pCancel" style="background-color: red" onclick="showPopup3()">
            Hủy lịch hẹn
        </button>
    </div>
    <div class="xform-actions" style="display: none; justify-content: space-around" id="aptm-tenantPending-action">
        <button id="pTenantAccept">
            Đồng ý
        </button>
        <button id="pTenentCancel" style="background-color: red" onclick="showPopup6()">
            Không đồng ý
        </button>
    </div>
</div>

<div class="overlay1" id="overlay"></div>
<div class="popup" id="popup3" style="width: 501px">
    <h4>Hủy lịch hẹn</h4>
    <div style="display: flex;flex-direction: column; width: 300px; margin: auto;align-items: baseline">
        <label>Lí do<span style="color: red">*</span> : </label>
        <textarea name="cancelReason" placeholder="Lí do hủy" required style="margin: 0 0 20px 0;width: 300px;height: 120px"></textarea>
        <p id="eCancelReason" style="color: red;display: none">Vui lòng nhập lí do hủy lịch hẹn!</p>
    </div>

    <p style="margin-bottom: 0;">Bạn có chắc chắn muốn hủy lịch hẹn?</p>
    <div style="display: flex;justify-content: space-around">
        <button onclick="closePopup3()" style="background-color: #ccc">Không</button>
        <button onclick="cancelAppointment()">Có</button>
    </div>
</div>

<div class="overlay1" id="overlay"></div>
<div class="popup" id="popup6" style="width: 501px">
    <h4>Từ chối lịch hẹn</h4>
    <div style="display: flex;flex-direction: column; width: 300px; margin: auto;align-items: baseline">
        <label>Lí do<span style="color: red">*</span> : </label>
        <textarea name="rejectReason" placeholder="Lí do từ chối" required style="margin: 0 0 20px 0;width: 300px;height: 120px"></textarea>
        <p id="eRejectReason" style="color: red;display: none">Vui lòng nhập lí do từ chối lịch hẹn!</p>
    </div>

    <p style="margin-bottom: 0;">Bạn có chắc chắn muốn từ chối lịch hẹn?</p>
    <div style="display: flex;justify-content: space-around">
        <button onclick="closePopup6()" style="background-color: #ccc">Không</button>
        <button onclick="rejectAppointment()">Có</button>
    </div>
</div>

<c:if test="${not empty requestScope.aError}">
    <div class="overlay1" id="overlay7" style="display: block"></div>
    <div class="popup" id="popup7" style="display:block;">
        <h4>Lỗi</h4>
        <p>Chấp nhận thay đổi lịch hẹn không thành công </p>
        <div style="display: flex; justify-content: space-around">
            <button onclick="closePopup7()" style="width: 180px; background-color: #ccc">Đóng</button>
        </div>
    </div>
</c:if>
<c:if test="${not empty requestScope.aMess}">
    <div class="overlay1" id="overlay8" style="display: block"></div>
    <div class="popup" id="popup8" style="display:block;">
        <h4 style="color: #00c853">Thành công</h4>
        <p>Chấp nhận thay đổi lịch hẹn thành công </p>
        Chuyển hướng trong <span id="countdown1">3</span> giây...
    </div>
    <script>
        // Thiết lập thời gian đếm ngược
        let countdown = 3;
        const countdownElement = document.getElementById('countdown1');

        // Hàm đếm ngược và cập nhật nội dung
        const timer = setInterval(() => {
            countdown--;
            countdownElement.textContent = countdown;

            if (countdown <= 0) {
                clearInterval(timer);
                window.location.href = "appointment-tenant-list"; // Đường dẫn bạn muốn chuyển hướng tới
            }
        }, 1000);
    </script>
</c:if>

<c:if test="${not empty requestScope.cancelError}">
    <div class="overlay1" id="overlay4" style="display: block"></div>
    <div class="popup" id="popup4" style="display: block;width: 300px">
        <h4>Lỗi</h4>
        <p>Hủy lịch hẹn không thành công.</p>
        <button onclick="closePopup4()">Đóng</button>
    </div>
</c:if>

<c:if test="${not empty requestScope.cancelMessage}">
    <div class="overlay1" id="overlay5" style="display: block"></div>
    <div class="popup" id="popup5" style="display: block;width: 300px">
        <h4 style="color: #00c853">Thành công</h4>
        <p>Hủy lịch hẹn thành công.</p>
        <button onclick="closePopup5()">Đóng</button>
    </div>
</c:if>


<script>

    window.onload = function() {
        // Lấy thông tin appointment từ requestScope
        const appointmentStatus = '${requestScope.appointment.status}';
        const homeName = '${requestScope.home.name}';
        const homeAddress = '${requestScope.home.address}';
        const appointmentStartDate = '${requestScope.appointment.startDate}';
        const appointmentEndDate = '${requestScope.appointment.endDate}';
        const hostFirstName = '${requestScope.host.firstName}';
        const hostLastName = '${requestScope.host.lastName}';
        const hostPhoneNumber = '${requestScope.host.phoneNumber}';
        const hostEmail = '${requestScope.host.email}';
        const appointmentNote = '${requestScope.appointment.note}';
        const appointmentRejectReason = '${requestScope.appointment.rejectReason}';
        const appointmentId = '${requestScope.appointment.id}';
        const appointmentCancelReason = '${requestScope.appointment.cancelReason}';

        // Gọi hàm showPopup với thông tin của appointment
        if(appointmentId){
            showPopup(appointmentStatus, homeName, homeAddress, appointmentStartDate, appointmentEndDate, hostFirstName,
                hostLastName,hostPhoneNumber,hostEmail,appointmentNote,appointmentRejectReason,appointmentId,appointmentCancelReason);
        }


    };

    document.addEventListener("DOMContentLoaded", function() {
        const today = new Date();
        const upcomingStatusElements = document.querySelectorAll(".upcoming-status");

        upcomingStatusElements.forEach(element => {
            const startDateStr = element.getAttribute("data-start-date");
            const statusStr = element.getAttribute("data-status");
            const startDate = new Date(startDateStr);

            // Tính khoảng cách ngày giữa hôm nay và ngày hẹn
            const diffInDays = Math.floor((startDate - today) / (1000 * 60 * 60 * 24));

            // Nếu ngày hẹn trong vòng 3 ngày tới, hiển thị "Upcoming"
            if (diffInDays >= 0 && diffInDays <= 3 && statusStr === 'accepted') {
                element.style.display = 'inline-block';
            }
        });
    });

    const appointmentsPerPage = 5;
    const eventCards = Array.from(document.querySelectorAll('.event-card'));
    let currentPage = 1;
    let filteredCards = [...eventCards];

    function updatePagination() {
        const totalPages = Math.ceil(filteredCards.length / appointmentsPerPage);
        document.getElementById('totalPages').textContent = totalPages;

        eventCards.forEach(card => card.style.display = 'none');

        filteredCards.forEach((card, index) => {
            if (index >= (currentPage - 1) * appointmentsPerPage && index < currentPage * appointmentsPerPage) {
                card.style.display = 'flex';
            }
        });
        document.getElementById('currentPage').textContent = currentPage;
        document.getElementById('paginationControls').style.display = totalPages > 1 ? 'block' : 'none';
    }

    function changePage(direction) {
        const totalPages = Math.ceil(filteredCards.length / appointmentsPerPage);
        currentPage = Math.min(Math.max(currentPage + direction, 1), totalPages);
        updatePagination();
    }

    // Lọc theo trạng thái và phân trang
    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', () => {
            document.querySelector('.tab.active').classList.remove('active');
            tab.classList.add('active');

            const filter = tab.getAttribute('data-filter');
            filteredCards = eventCards.filter(card => filter === 'all' || card.getAttribute('data-status') === filter);

            currentPage = 1;
            updatePagination();
        });
    });

    updatePagination();

    let currentAppointmentId = null;

    function showPopup(status, homeName, address, startTime, endTime, firstName,lastName, ownerPhone, ownerEmail,note,rejectReason,appointmentId, cancelReason) {
        const day = startTime.substring(8, 10); // "YYYY-MM-DD"
        const month = startTime.substring(5, 7);
        const year = startTime.substring(0, 4);
        const startTimePart = startTime.substring(11, 16); // "HH:MM"
        const endTimePart = endTime.substring(11, 16); // "HH:MM"

        currentAppointmentId = appointmentId;

        // Gán các giá trị cho phần tử trong popup
        document.getElementById("pHomeName").textContent = homeName;
        document.getElementById("pHomeAddress").textContent = address;
        document.getElementById("pTime").textContent = startTimePart + " - "+  endTimePart +" | "+ day +" - "+ month +" - "+ year;
        document.getElementById("pHostName").textContent = firstName + " " + lastName;
        document.getElementById("pHostPhone").textContent = ownerPhone ? ownerPhone : "Đang cập nhật";
        document.getElementById("pHostEmail").textContent = ownerEmail;
        document.getElementById("pNote").textContent = note || "";
        document.getElementById("pRejectReason").textContent = rejectReason || "";
        document.getElementById("pCancelReason").textContent = cancelReason || "";

        // Mặc định ẩn các phần tử ghi chú và lý do từ chối
        document.getElementById("dNote").style.display = "none";
        document.getElementById("dReason").style.display = "none";
        document.getElementById("dCancelReason").style.display = "none";
        document.getElementById("aptm-action").style.display = "none";
        document.getElementById("aptm-tenantPending-action").style.display = "none";

        if(status === "hostPending"){
            document.getElementById("aptm-action").style.display = "flex";
        }
        if(status === "tenantPending"){
            document.getElementById("aptm-tenantPending-action").style.display = "flex";
        }

        // Hiển thị các phần tử này nếu có dữ liệu
        if (note) {
            document.getElementById("dNote").style.display = "flex";
        }
        if (rejectReason) {
            document.getElementById("dReason").style.display = "flex";
        }
        if (cancelReason) {
            document.getElementById("dCancelReason").style.display = "flex";
        }

        document.getElementById("pTenantPending").style.display="none";
        document.getElementById("pHostPending").style.display="none";
        document.getElementById("pAccept").style.display="none";
        document.getElementById("pReject").style.display="none";
        document.getElementById("pCancelled").style.display="none";
        document.getElementById("pExpired").style.display="none";

        if(status === "tenantPending"){
            document.getElementById("pTenantPending").style.display="block";
        } else {
            if(status === "accepted"){
                document.getElementById("pAccept").style.display="block";
            } else {
                if(status === "rejected"){
                    document.getElementById("pReject").style.display="block";
                } else {
                    if(status === "cancelled"){
                        document.getElementById("pCancelled").style.display="block";
                    } else {
                        if(status === "hostPending"){
                            document.getElementById("pHostPending").style.display="block";
                        } else {
                            if(status === "expired"){
                                document.getElementById("pExpired").style.display="block";
                            }
                        }
                    }
                }
            }
        }

        // Hiển thị popup và overlay
        document.getElementById("overlay").style.display = "block";
        document.getElementById("popup").style.display = "block";
    }

    // Đóng popup khi bấm nút "Đóng" hoặc overlay
    document.querySelector("#popup .xclose").onclick = closePopup;
    document.querySelector("#popup .xform-actions button").onclick = closePopup;
    document.getElementById("overlay").onclick = closePopup;

    function closePopup() {
        document.getElementById("overlay").style.display = "none";
        document.getElementById("popup").style.display = "none";
    }

    function closePopup4() {
        document.getElementById("overlay4").style.display = "none";
        document.getElementById("popup4").style.display = "none";
    }

    function closePopup5() {
        document.getElementById("overlay5").style.display = "none";
        document.getElementById("popup5").style.display = "none";
    }

    function cancelAppointment() {
        const cancelReason = document.querySelector("textarea[name='cancelReason']").value.trim();
        if (!cancelReason) {
            document.getElementById("eCancelReason").style.display="inline-block";
            return;
        }
        if (currentAppointmentId) {
            window.location.href = "cancel-appointment?appointmentId=" + currentAppointmentId +"&reason="+cancelReason+"&host=0";
        }
    }

    function rejectAppointment() {
        const cancelReason = document.querySelector("textarea[name='rejectReason']").value.trim();
        if (!cancelReason) {
            document.getElementById("eRejectReason").style.display="inline-block";
            return;
        }
        if (currentAppointmentId) {
            window.location.href = "cancel-appointment?appointmentId=" + currentAppointmentId +"&reason="+cancelReason+"&host=0";
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("pEdit").addEventListener("click", function(event) {
            event.stopPropagation();
            updateAppointment(event);
        });
    });

    function updateAppointment(event) {
        event.preventDefault(); // Prevents form submission
        if (currentAppointmentId) {
            window.location.href = "edit-appointment?id=" + currentAppointmentId;
        }
    }


    function showPopup3() {
        document.getElementById("popup3").style.display = "block";
    }

    function closePopup3() {
        document.getElementById("popup3").style.display = "none";
    }

    function showPopup6() {
        document.getElementById("popup6").style.display = "block";
    }

    function closePopup6() {
        document.getElementById("popup6").style.display = "none";
    }

    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("pTenantAccept").addEventListener("click", function(event) {
            event.stopPropagation();
            acceptAppointment(event);
        });
    });


    function acceptAppointment(event) {
        event.preventDefault(); // Prevents form submission
        if (currentAppointmentId) {
            window.location.href = "accept-reject-appointment?id=" + currentAppointmentId+"&status=accepted"+"&host=0";
        }
    }

    function closePopup7() {
        document.getElementById("overlay7").style.display = "none";
        document.getElementById("popup7").style.display = "none";
    }

</script>
<jsp:include page="footer.jsp"/>
</body>
</html>
