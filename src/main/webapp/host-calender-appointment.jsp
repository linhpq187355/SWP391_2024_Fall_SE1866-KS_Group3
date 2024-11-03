<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/31/2024
  Time: 9:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Lịch</title>
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
    <link href='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.css' rel='stylesheet' />
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@5.10.1/main.min.js'></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .fc-event-time {
            display: none;
        }
        .fc-toolbar-title {
            text-transform: capitalize;
        }
        .fc-event-title{
            text-wrap: wrap;
            margin: 5px 0;
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
        .fc-next-button{
            background-color: #ffa500 !important;
            border: none !important;
            border-left: 1px solid #ccc !important;
        }
        .fc-next-button:hover{
            color: #0b0b0b !important;
        }
        .fc-prev-button{
            background-color: #ffa500 !important;
            border: none !important;
        }
        .fc-prev-button:hover{
            color: #0b0b0b !important;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div style="width: 80%;margin: auto;padding-bottom: 50px;border-bottom: 2px solid #ccc;">
    <h2 style="margin-left: 15px;color: #000;">Lịch hẹn của tôi</h2>
    <div class="btn-group" style="margin-left: 35px;margin-top: 10px;">
        <button class="btn btn-secondary" onclick="window.location.href='appointment-host-manage'">Danh sách</button>
        <button class="btn btn-primary" onclick="window.location.href='host-calender-appointment.jsp'">Lịch</button>
    </div>
</div>
<div style="display: flex;justify-content: space-evenly;width: 60%;position: absolute;top: 240px;right: 8%">
    <div style="display: flex;align-items: center;">
        <div style="background-color: #ccc;width: 10px;height: 10px;border-radius: 5px;"></div>
        <p style="padding: 0;padding-left: 10px;font-size: 18px;color: #ccc">Chờ xác nhận | Chờ phản hồi</p>
    </div>
    <div style="display: flex;align-items: center;">
        <div style="background-color: red;width: 10px;height: 10px;border-radius: 5px;"></div>
        <p style="padding: 0;padding-left: 10px;font-size: 18px;color: red">Đã từ chối</p>
    </div>
    <div style="display: flex;align-items: center;">
        <div style="background-color: limegreen;width: 10px;height: 10px;border-radius: 5px;"></div>
        <p style="padding: 0;padding-left: 10px;font-size: 18px;color: limegreen">Đã chấp nhận</p>
    </div>
    <div style="display: flex;align-items: center;">
        <div style="background-color: #111111;width: 10px;height: 10px;border-radius: 5px;"></div>
        <p style="padding: 0;padding-left: 10px;font-size: 18px;color: #111">Đã hủy | Đã quá hạn</p>
    </div>
</div>
<div id="calendar" style="width: 80%;margin: auto;margin-top: 50px;margin-bottom: 50px;"></div>

<div class="overlay1" id="overlay" style="display: none"></div>
<div class="popup" id="popup" style="display: none">
    <div class="xheader">
        <h2>
            Thông tin chi tiết lịch hẹn
        </h2>

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
            Tên người thuê:
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
    <div class="xform-actions" style="justify-content: space-around" id="aptm-action">
        <button id="pEdit" onclick="closePopup()">
            Đóng
        </button>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        var backButton = document.getElementById('backButton');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            locale: 'vi',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            buttonText: {
                today: 'Hôm nay',
                month: 'Tháng',
                week: 'Tuần',
                day: 'Ngày'
            },
            titleFormat: { year: 'numeric', month: 'long' },
            events: function(fetchInfo, successCallback, failureCallback) {
                $.ajax({
                    url: 'host-calendar',
                    method: 'GET',
                    dataType: 'json',
                    success: function(data) {
                        var appointmentList = data.appointmentList;
                        var homeList = data.homeList;
                        var tenantList = data.tenantList;
                        var events = appointmentList.map(function(appointment) {
                            var startTime = appointment.startDate;
                            var endTime = appointment.endDate;

                            // Tìm tên của ngôi nhà dựa trên homeId của appointment
                            var home = homeList.find(function(h) {
                                return h.id === appointment.homeId;
                            });
                            var tenant = tenantList.find(function(ho) {
                                return ho.id === appointment.tenantId;
                            });

                            var homeName = home ? home.name : "Không có tên nhà"; // Kiểm tra nếu tìm thấy tên nhà

                            return {
                                id: appointment.id,
                                title: homeName + " | " + startTime.substring(11, 16) + " - " + endTime.substring(11, 16),
                                start: startTime, // Dùng full timestamp cho start
                                end: endTime,     // Dùng full timestamp cho end
                                color: appointment.status === 'accepted' ? 'limegreen' :
                                    appointment.status === 'rejected' ? 'red' :
                                        (appointment.status === 'hostPending' || appointment.status === 'tenantPending') ? '#ccc' :
                                            (appointment.status === 'cancelled' || appointment.status === 'expired') ? '#111' :
                                                'blue',
                                extendedProps: {
                                    roomName: homeName,
                                    address: home.address, // Giả sử bạn có địa chỉ ở đây
                                    tenantName: tenant.firstName + " " + tenant.lastName, // Tên chủ trọ
                                    phone: tenant.phoneNumber, // SĐT
                                    email: tenant.email, // Email
                                    note: appointment.note, // Ghi chú
                                    apmtTime: startTime.substring(11, 16) + " - " + endTime.substring(11, 16) + " | " + startTime.substring(8, 10) + " - " + startTime.substring(5, 7)+ " - " + startTime.substring(0, 4)
                                }
                            };
                        });
                        successCallback(events);
                    },

                    error: function() {
                        alert('Failed to load calendar events');
                    }
                });
            },
            dateClick: function(info) {
                calendar.changeView('timeGridDay', info.dateStr);
                backButton.style.display = 'inline';
            },
            eventClick: function(info) {
                // Lấy thông tin từ đối tượng sự kiện
                var event = info.event;

                // Giả sử bạn đã thêm các thuộc tính bổ sung vào đối tượng sự kiện
                var roomName = event.extendedProps.roomName; // Tên phòng/nhà
                var address = event.extendedProps.address;   // Địa chỉ
                var tenantName = event.extendedProps.tenantName;
                var phone = event.extendedProps.phone;       // SĐT
                var email = event.extendedProps.email;       // Email
                var note = event.extendedProps.note;         // Ghi chú
                var pTime = event.extendedProps.apmtTime;

                // Cập nhật nội dung thông tin chi tiết
                document.getElementById('pHomeName').textContent = roomName || "Không có thông tin";
                document.getElementById('pHomeAddress').textContent = address || "Không có thông tin";
                document.getElementById('pTime').textContent = pTime;
                document.getElementById('pHostName').textContent = tenantName || "Không có thông tin";
                document.getElementById('pHostPhone').textContent = phone || "Không có thông tin";
                document.getElementById('pHostEmail').textContent = email || "Không có thông tin";
                document.getElementById('pNote').textContent = note || "Không có thông tin";
                if(cancelReason){
                    document.getElementById('pCancelReason').textContent = cancelReason;
                    document.getElementById('dCancelReason').style.display = "flex";
                }
                if(rejectReason){
                    document.getElementById('pRejectReason').textContent = rejectReason;
                    document.getElementById('dReason').style.display = "flex";
                }


                // Hiển thị phần thông tin chi tiết
                document.getElementById('overlay').style.display = 'block';
                document.getElementById('popup').style.display = 'block';
            },
        });

        calendar.render();

        window.goBackToMonthView = function() {
            calendar.changeView('dayGridMonth');
            backButton.style.display = 'none';
        };
    });
    function closePopup() {
        document.getElementById("overlay").style.display = "none";
        document.getElementById("popup").style.display = "none";
    }
</script>
</body>
</html>
