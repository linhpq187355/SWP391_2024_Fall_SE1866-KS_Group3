<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/29/2024
  Time: 8:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Chỉnh sửa lịch hẹn</title>
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
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 70%;
            margin: 30px;
        }
        h2 {
            font-size: 18px;
            margin-bottom: 20px;
        }
        .calendar .selected {
            background-color: #ffa500; /* Màu vàng */
            color: #fff;
            border-radius: 25px;
        }

        .calendar .today {
            background-color: #ffa500; /* Màu vàng */
            color: #fff;
            border-radius: 25px;
        }

        .calendar, .time-picker {
            margin-bottom: 20px;
        }
        .calendar table, .time-picker table {
            width: 100%;
            border-collapse: collapse;
        }
        .calendar th, .calendar td, .time-picker td {
            text-align: center;
            padding: 10px;
        }
        .calendar th {
            color: #888;
        }
        .calendar td {
            cursor: pointer;
        }
        .time-picker .time-slot {
            background-color: #f5f5f5;
            border-radius: 5px;
            cursor: pointer;
            width: 190px;
            margin: 10px;
            display: inline-block;
        }
        .time-picker .selected {
            background-color: #ffa500;
            color: #fff;
        }
        .time-picker .disabled {
            background-color: #e0e0e0;
            color: #888;
            cursor: not-allowed;
        }
        .footer {
            display: flex;
            justify-content: space-around;
            align-items: center;
            margin-top: 30px;
        }
        .footer .info {
            font-size: 12px;
            color: #888;
        }
        .footer button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .footer .back {
            background-color: #d3d3d3;
            color: #000;
        }
        .footer .continue {
            background-color: #ffcc00;
            color: #000;
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
            width: 300px;
            display: none; /* Hidden by default */
            text-align: center;
            z-index: 1000;
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
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="content-area" style="display: flex; justify-content: center">
    <div class="container1">
        <h2 style="font-size: 30px;color: #000;margin-left: 30px;">Chọn ngày đặt lịch<span style="color: red;margin-left: 10px">*</span></h2>
        <div class="calendar" style="box-shadow: rgba(0, 0, 0, 0.20) 0px 5px 18px;border-radius: 15px;width: 85%;margin: auto;margin-top: 35px;padding: 15px; margin-bottom: 50px;">
            <table id="calendar-table">
                <thead>
                <tr>
                    <th colspan="7" id="calendar-header">
                        <select id="month-select" onchange="updateCalendar()" style="border: none;background-color: #f1f1f1;color: #ffa500;border-radius: 10px;width: 135px;padding: 7px;}"></select>
                        <select id="year-select" onchange="updateCalendar()" style="border: none;margin-left: 15px;"></select>
                    </th>
                </tr>
                <tr>
                    <th>Su</th>
                    <th>M</th>
                    <th>Tu</th>
                    <th>W</th>
                    <th>Th</th>
                    <th>F</th>
                    <th>Sa</th>
                </tr>
                </thead>
                <tbody id="calendar-body">
                </tbody>
            </table>
        </div>
        <h2 style="font-size: 30px;margin-bottom: 40px;color: #000;margin-left: 30px;">Chọn thời gian<span style="color: red;margin-left: 10px">*</span></h2>
        <div class="time-picker" style="width: 91%; margin: auto">
            <table>
                <tr>
                    <td class="time-slot">05:30 - 07:30</td>
                    <td class="time-slot">06:00 - 08:00</td>
                    <td class="time-slot">06:30 - 08:30</td>
                    <td class="time-slot">07:00 - 09:00</td>
                </tr>
                <tr>
                    <td class="time-slot">07:30 - 09:30</td>
                    <td class="time-slot">08:00 - 10:00</td>
                    <td class="time-slot">08:30 - 10:30</td>
                    <td class="time-slot">09:00 - 11:00</td>
                </tr>
                <tr>
                    <td class="time-slot">09:30 - 11:30</td>
                    <td class="time-slot">10:00 - 12:00</td>
                    <td class="time-slot">10:30 - 12:30</td>
                    <td class="time-slot">11:00 - 13:00</td>
                </tr>
                <tr>
                    <td class="time-slot">11:30 - 13:30</td>
                    <td class="time-slot">12:00 - 14:00</td>
                    <td class="time-slot">12:30 - 14:30</td>
                    <td class="time-slot">13:00 - 15:00</td>
                </tr>
                <tr>
                    <td class="time-slot">13:30 - 15:30</td>
                    <td class="time-slot">14:00 - 16:00</td>
                    <td class="time-slot">14:30 - 16:30</td>
                    <td class="time-slot">15:00 - 17:00</td>
                </tr>
                <tr>
                    <td class="time-slot">15:30 - 17:30</td>
                    <td class="time-slot">16:00 - 18:00</td>
                    <td class="time-slot">16:30 - 18:30</td>
                    <td class="time-slot">17:00 - 19:00</td>
                </tr>
                <tr>
                    <td class="time-slot">17:30 - 19:30</td>
                    <td class="time-slot">18:00 - 20:00</td>
                    <td class="time-slot">18:30 - 20:30</td>
                    <td class="time-slot">19:00 - 21:00</td>
                </tr>
                <tr>
                    <td class="time-slot">19:30 - 21:30</td>
                    <td class="time-slot">20:00 - 22:00</td>
                    <td class="time-slot">20:30 - 22:30</td>
                    <td class="time-slot">21:00 - 23:00</td>
                </tr>
            </table>
        </div>

        <form id="appointmentForm" action="edit-appointment" method="POST">
            <input type="hidden" name="selectedDate" id="hiddenDate">
            <input type="hidden" name="selectedMonth" id="hiddenMonth">
            <input type="hidden" name="selectedYear" id="hiddenYear">
            <input type="hidden" name="selectedTime" id="hiddenTime">
            <input type="hidden" name="aptmId" value="${requestScope.appointment.id}">
            <h2 style="margin-bottom: 30px; color: #000;margin-left: 30px;">Ghi chú<span style="color: red;margin-left: 10px">*</span></h2>
            <div class="apmt-note" style="width: 83%; margin: auto">
                <textarea name="note" placeholder="Thêm ghi chú" style="width: 100%;padding: 10px;height: 200px;border-radius: 15px;box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;border: none;">${requestScope.appointment.note}</textarea>
            </div>
            <div class="footer">
                <div class="info">
                    <i class="fas fa-info-circle"></i> Thời gian tính theo giờ Việt Nam
                </div>
                <div style="display: flex;flex-direction: row-reverse;width: 20%;justify-content: space-between;">
                    <div>
                        <button type="button" class="continue" onclick="saveAppointment()">Xác nhận</button>
                    </div>
                    <div>
                        <button style="background-color: #ff2b2b;color: #fff;" type="button" class="cancel" onclick="window.location.href='appointment-tenant-list?appointmentId=${requestScope.appointment.id}'">Hủy</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<!-- Popup HTML -->
<div class="overlay1" id="overlay1"></div>
<div class="popup" id="popup">
    <h4>Lỗi</h4>
    <p>Bạn phải chọn cả ngày và giờ trước khi tiếp tục.</p>
    <button onclick="closePopup()">Đóng</button>
</div>

<div class="overlay1" id="overlay2"></div>
<div class="popup" id="popup2">
    <h4>Lỗi</h4>
    <p>Vui lòng chọn ngày/thời gian sau thời điểm hiện tại!</p>
    <button onclick="closePopup2()">Đóng</button>
</div>





    <div class="overlay1" id="overlay4"></div>
    <div class="popup" id="popup4">
        <h4>Lỗi</h4>
        <p>${requestScope.error}</p>
        <div style="display: flex; justify-content: space-around">
            <button onclick="closePopup4()" style="width: 180px; background-color: #ccc">Đóng</button>
        </div>
    </div>
<c:if test="${not empty requestScope.message}">
    <div class="overlay1" id="overlay5" style="display: block"></div>
    <div class="popup" id="popup5" style="display: block">
        <h4 style="color: #00c853">Thành công</h4>
        <p>${requestScope.message}</p>
        Chuyển hướng trong <span id="countdown">3</span> giây...
    </div>
    <script>
        // Thiết lập thời gian đếm ngược
        let countdown = 3;
        const countdownElement = document.getElementById('countdown');

        // Hàm đếm ngược và cập nhật nội dung
        const timer = setInterval(() => {
            countdown--;
            countdownElement.textContent = countdown;

            if (countdown <= 0) {
                clearInterval(timer);
                window.location.href = "appointment-tenant-list?appointmentId=${requestScope.id}"; // Đường dẫn bạn muốn chuyển hướng tới
            }
        }, 1000);
    </script>
</c:if>



<jsp:include page="footer.jsp"/>
<script>
    const appointments = [
        <c:forEach var="appointment" items="${requestScope.hostAppointment}">
        {
            startDate: "${appointment.startDate}",
            endDate: "${appointment.endDate}",
            status: "${appointment.status}"
        },
        </c:forEach>
    ];
    const appointments2 = [
        <c:forEach var="appointment" items="${requestScope.tenantAppointment}">
        {
            startDate: "${appointment.startDate}",
            endDate: "${appointment.endDate}",
            status: "${appointment.status}"
        },
        </c:forEach>
    ];
    console.log(appointments2);
    function parseDate(dateString) {
        return new Date(dateString);
    }
    function updateTimePicker(selectedDate) {
        const timeSlots = document.querySelectorAll('.time-picker .time-slot');


        timeSlots.forEach(slot => {
            // Reset trạng thái cho từng ô
            slot.classList.remove('disabled');
            slot.removeAttribute('title');

            // Lấy khoảng thời gian của ô hiện tại
            const [startTime, endTime] = slot.textContent.split(' - ');

            const slotStart = new Date(selectedDate+"T"+startTime+":00");
            const slotEnd = new Date(selectedDate+"T"+endTime+":00");

            console.log("Slot Start:", slotStart);
            console.log("Slot End:", slotEnd);

            // Kiểm tra xem ô thời gian có trùng với appointment nào không
            for (const appointment of appointments) {

                const appointmentStart = parseDate(appointment.startDate);
                const appointmentEnd = parseDate(appointment.endDate);
                if(appointmentStart.getDate() === parseDate(selectedDate+"T"+"00"+":00").getDate() && appointment.status === "accepted"){
                    if (
                        (slotStart >= appointmentStart && slotStart < appointmentEnd) ||
                        (slotEnd > appointmentStart && slotEnd <= appointmentEnd) ||
                        (slotStart <= appointmentStart && slotEnd >= appointmentEnd)
                    ) {
                        slot.setAttribute('title','Đã có lịch hẹn ở thời gian này!')
                        slot.classList.add('disabled');
                        break;
                    }
                }


            }
            for (const appointment of appointments2) {

                const appointmentStart = parseDate(appointment.startDate);
                const appointmentEnd = parseDate(appointment.endDate);
                if(appointmentStart.getDate() === parseDate(selectedDate+"T"+"00"+":00").getDate() && appointment.status === "accepted"){
                    if (
                        (slotStart >= appointmentStart && slotStart < appointmentEnd) ||
                        (slotEnd > appointmentStart && slotEnd <= appointmentEnd) ||
                        (slotStart <= appointmentStart && slotEnd >= appointmentEnd)
                    ) {
                        slot.setAttribute('title','Đã có lịch hẹn ở thời gian này!')
                        slot.classList.add('disabled');
                        break;
                    }
                }


            }
        });
    }

    function onDateSelected(day, month, year) {
        const paddedMonth = String(month + 1).padStart(2, '0');
        const paddedDay = String(day).padStart(2, '0');
        const selectedDate = year + "-" + paddedMonth + "-" + paddedDay;
        console.log("Selected Date:", selectedDate);
        console.log(paddedMonth);
        console.log(year);
        updateTimePicker(selectedDate);
    }
    document.addEventListener('DOMContentLoaded', function () {
        const today = new Date();
        const currentDay = today.getDate();
        const currentMonth = today.getMonth(); // Lưu ý: getMonth() trả về giá trị từ 0 (tháng 1) đến 11 (tháng 12)
        const currentYear = today.getFullYear();

        // Gọi hàm onDateSelected để thiết lập và hiển thị lịch cho ngày hôm nay
        onDateSelected(currentDay, currentMonth, currentYear);

        // Thêm sự kiện click vào các ô thời gian
        const timeSlots = document.querySelectorAll('.time-picker .time-slot');
        timeSlots.forEach(slot => {
            slot.addEventListener('click', function () {
                // Xóa lớp selected khỏi tất cả các ô thời gian
                timeSlots.forEach(s => s.classList.remove('selected'));

                // Thêm lớp selected vào ô thời gian đã chọn
                slot.classList.add('selected');
            });
        });
    });

    function populateMonthAndYearDropdowns() {
        const monthSelect = document.getElementById('month-select');
        const yearSelect = document.getElementById('year-select');
        const currentYear = new Date().getFullYear();
        const currentMonth = new Date().getMonth();

        // Điền dữ liệu cho tháng
        const months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
        months.forEach((month, index) => {
            let option = document.createElement('option');
            option.value = index; // Tháng từ 0 đến 11
            option.text = month;
            if (index === currentMonth) {
                option.selected = true; // Chọn tháng hiện tại
            }
            monthSelect.appendChild(option);
        });

        // Điền dữ liệu cho năm (5 năm trước và 5 năm sau)
        for (let year = currentYear - 5; year <= currentYear + 5; year++) {
            let option = document.createElement('option');
            option.value = year;
            option.text = year;
            if (year === currentYear) {
                option.selected = true; // Chọn năm hiện tại
            }
            yearSelect.appendChild(option);
        }
    }

    let selectedDate = null;

    function generateCalendar(selectedMonth = null, selectedYear = null) {
        const calendarBody = document.getElementById('calendar-body');

        // Lấy giá trị của tháng và năm từ dropdowns nếu chưa có giá trị truyền vào
        const monthSelect = document.getElementById('month-select');
        const yearSelect = document.getElementById('year-select');

        const today = new Date();
        const currentMonth = selectedMonth !== null ? selectedMonth : parseInt(monthSelect.value);
        const currentYear = selectedYear !== null ? selectedYear : parseInt(yearSelect.value);

        const firstDay = new Date(currentYear, currentMonth, 1).getDay();
        const lastDate = new Date(currentYear, currentMonth + 1, 0).getDate();

        const todayDate = today.getDate();
        const isCurrentMonth = currentMonth === today.getMonth() && currentYear === today.getFullYear();

        calendarBody.innerHTML = '';

        let date = 1;
        for (let i = 0; i < 6; i++) {
            let row = document.createElement('tr');
            for (let j = 0; j < 7; j++) {
                let cell = document.createElement('td');
                if (i === 0 && j < firstDay) {
                    cell.classList.add('disabled');
                    cell.innerHTML = '';
                } else if (date > lastDate) {
                    cell.classList.add('disabled');
                    cell.innerHTML = '';
                } else {
                    cell.innerHTML = date;
                    // Đánh dấu ngày được chọn
                    if (selectedDate && selectedDate.year === currentYear && selectedDate.month === currentMonth && selectedDate.day === date) {
                        cell.classList.add('selected');
                    }
                    // Nếu chưa có ngày nào được chọn, đánh dấu ngày hôm nay
                    else if (!selectedDate && isCurrentMonth && date === todayDate) {
                        cell.classList.add('selected');
                    }

                    cell.addEventListener('click', function() {
                        document.querySelectorAll('.calendar td').forEach(td => td.classList.remove('selected', 'today'));
                        cell.classList.add('selected');

                        // Lưu ngày đã chọn
                        selectedDate = {
                            day: date,
                            month: currentMonth,
                            year: currentYear
                        };
                        const selectedDateElement = document.querySelector('.calendar .selected');
                        const selectedDate2 = selectedDateElement ? selectedDateElement.innerText : null;
                        onDateSelected(selectedDate2,currentMonth,currentYear);
                    });
                    date++;
                }
                row.appendChild(cell);
            }
            calendarBody.appendChild(row);
        }
    }

    function updateCalendar() {
        const selectedMonth = parseInt(document.getElementById('month-select').value);
        const selectedYear = parseInt(document.getElementById('year-select').value);
        generateCalendar(selectedMonth, selectedYear);
    }

    // Khởi tạo lịch và dropdowns
    populateMonthAndYearDropdowns();
    generateCalendar();


    function saveAppointment() {
        const selectedDateElement = document.querySelector('.calendar .selected');
        const selectedDate = selectedDateElement ? selectedDateElement.innerText : null;
        const selectedMonth = document.getElementById('month-select').selectedOptions[0].value;
        const selectedYear = document.getElementById('year-select').value;
        const timeElement = document.querySelector('.time-picker .selected');
        const selectedTime = timeElement ? timeElement.innerText : null;

        const today = new Date();
        const currentHour = today.getHours();
        const currentMinute = today.getMinutes();

        const todayDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());

        if (selectedDate && selectedTime) {

            const selectedFullDate = new Date(selectedYear, selectedMonth, selectedDate);

            if (selectedFullDate < todayDate) {
                showPopup2();
                return;
            }

            if (selectedFullDate.getTime() === todayDate.getTime()){
                const selectedTimeParts = selectedTime.split(' ')[0].split(':');
                const selectedStartHour = parseInt(selectedTimeParts[0]);
                const selectedStartMinute = parseInt(selectedTimeParts[1] || 0);

                if (selectedStartHour < currentHour || (selectedStartHour === currentHour && selectedStartMinute <= currentMinute)) {
                    showPopup2();
                    return;
                }
            }
            // Gán chuỗi fullDate vào hiddenDate
            document.getElementById('hiddenDate').value = selectedDate;
            document.getElementById('hiddenMonth').value = selectedMonth;
            document.getElementById('hiddenYear').value = selectedYear;
            document.getElementById('hiddenTime').value = selectedTime;

            document.getElementById('appointmentForm').submit();
        } else {
            showPopup();
        }
    }


    function showPopup() {
        document.getElementById('popup').style.display = 'block';
        document.getElementById('overlay').style.display = 'block';
    }

    function closePopup() {
        document.getElementById('popup').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

    function showPopup2() {
        document.getElementById('popup2').style.display = 'block';
        document.getElementById('overlay').style.display = 'block';
    }

    function closePopup2() {
        document.getElementById('popup2').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }


    function closePopup4() {
        document.getElementById('popup4').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

</script>
</body>
</html>
