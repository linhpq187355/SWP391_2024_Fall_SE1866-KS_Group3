
<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 10/7/2024
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cài đặt ghép nối</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="colorlib.com">

    <!-- STYLE CSS -->
    <link rel="stylesheet" href="assets/css/style1.css">
    <style>
        .error{
            color:red
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="wrapper">
    <form action="matching" method="post" id="wizard" style="height: 100%;width: 75em">
        <button type="button" class="btn btn-danger" style="position: absolute;right: 26px;top: 40px;" onclick="window.location.href='home-page'">Bỏ qua</button>
        <c:if test="${not empty cookie.id}">
            <c:if test="${cookie.roleId.value == 3}">
                <!-- SECTION 1 -->
                <h2></h2>
                <section style="height: 100%;">
                    <div class="inner">
                        <div class="form-content" >
                            <div class="form-header">
                                <h3>Thông tin ghép nối</h3>
                            </div>
                            <c:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger" role="alert" style="width: 50%;margin: auto;">
                                        ${requestScope.error}
                                </div>
                            </c:if>
                            <c:if test="${not empty requestScope.message}">
                                <div class="alert alert-success" role="alert" style="width: 50%;margin: auto;" id="successMessage">
                                        ${requestScope.message} - Chuyển hướng trong <span id="countdown">3</span> giây...
                                </div>
                                <script>
                                    let countdown = 3; // Bắt đầu từ 3 giây
                                    const countdownElement = document.getElementById('countdown');

                                    // Hàm đếm ngược
                                    const intervalId = setInterval(() => {
                                        countdown--;
                                        countdownElement.textContent = countdown;

                                        if (countdown === 0) {
                                            clearInterval(intervalId); // Dừng đếm ngược
                                            window.location.href = 'home-page'; // Chuyển hướng về trang login
                                        }
                                    }, 1000); // Giảm mỗi giây
                                </script>
                            </c:if>
                            <c:if test="${empty requestScope.error}">
                                <c:if test="${empty requestScope.message}">
                                    <p style="padding-bottom: 35px">Hãy điền những thông tin cá nhân và phòng mà bạn mong muốn tìm kiếm. Chúng tôi sẽ cho bạn những lựa chọn phù hợp</p>
                                </c:if>
                            </c:if>
                            <h4 style="text-align: center; text-decoration: underline; margin-bottom: 45px; margin-top: 20px">Phòng mong muốn</h4>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label>Bạn mong muốn tìm bạn cùng phòng trong bao lâu?<span style="color: red; margin-left: 10px">*</span></label>
                                    <select class="form-control" name="howLong" required>
                                        <option value="">Chọn thời gian</option>
                                        <option value="short">Ngắn (6 tháng hoặc ít hơn)</option>
                                        <option value="long">Dài (6 tháng trở lên)</option>
                                    </select>
                                    <span class="error" id="howLongError"></span>
                                </div>
                                <div class="form-holder">
                                    <label>Thời điểm bạn mong muốn chuyển vào (sớm nhất)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="emvdate" type="date" class="form-control" required>
                                    <span class="error" id="emvdateError"></span>
                                </div>
                            </div>
                            <div class="form-row">

                                <div class="form-holder">
                                    <label>Thời điểm bạn mong muốn chuyển vào (muộn nhất)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="lmvdate" type="date" class="form-control" required style="margin-top: 28px;">
                                    <span class="error" id="lmvdateError"></span>
                                </div>
                                <div class="form-holder">
                                    <label style="margin-bottom: 33px">Giá phòng bạn mong muốn (theo tháng)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div style="display: flex; align-items: center">
                                        <input name="minBudget" type="number" class="form-control" style="width: 30%; margin-right: 10px" required>  đ <label style="margin: 0 20px">đến</label><input name="maxBudget" type="number" class="form-control" style="width: 30%;margin-right: 10px" required>đ

                                    </div>
                                    <span class="error" id="budgetError"></span>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label>Khu vực bạn muốn tìm phòng?<span style="color: red; margin-left: 10px">*</span></label>
                                    <select class="form-control" name="prefProvince" required>
                                        <option value="">Chọn khu vực</option>
                                        <c:forEach items="${requestScope.provinceList}" var="provinceList">
                                            <option value="${provinceList.id}">${provinceList.name}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="error" id="provinceError"></span>
                                </div>

                            </div>
                        </div>
                    </div>
                </section>

                <!-- SECTION 2 -->
                <h2></h2>
                <section>
                    <div class="inner">
                        <div class="form-content">
                            <div class="form-header">
                                <h3>Thông tin ghép nối</h3>
                            </div>

                            <p style="padding-bottom: 35px">Hãy điền những thông tin về bạn cùng phòng mà bạn mong muốn tìm kiếm. Chúng tôi sẽ cho bạn những lựa chọn phù hợp</p>


                            <h4 style="text-align: center; text-decoration: underline; margin-bottom: 20px; margin-top: 20px">Thông tin cá nhân</h4>
                            <div class="form-row" style="margin-bottom: 20px">
                                <div class="form-holder">
                                    <label>Ngày sinh của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="dob" type="date" class="form-control" required>
                                    <span class="error" id="dobError"></span>
                                </div>
                                <div class="form-holder">
                                    <label>Giới tính<span style="color: red; margin-left: 10px">*</span></label>
                                    <select class="form-control" name="gender" required>
                                        <option value="">Chọn giới tính</option>
                                        <option value="male">Nam</option>
                                        <option value="female">Nữ</option>
                                        <option value="dif">Khác</option>
                                    </select>
                                    <span class="error" id="genderError"></span>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-broom"></i> Mức độ sạch sẽ của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="cleanlinessSlider" name="cleanliness" readonly type="range" min="1" max="5" step="1" value="" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value:''" required>
                                        <div class="ano-slider">
                                            <span>Bẩn</span>
                                            <span>Sạch</span>
                                        </div>
                                        <span id="cleanlinessError" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-smoking"></i> Mức độ hút thuốc của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="smokingSlider" name="smoking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không hút</span>
                                            <span>Thường xuyên</span>
                                        </div>
                                        <span id="smokingError" class="error"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-beer-mug-empty"></i> Mức độ uống rượu/bia của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="drinkingSlider" name="drinking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không uống</span>
                                            <span>Thường xuyên</span>
                                        </div>
                                        <span id="drinkingError" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-user-group"></i> Bạn sẽ hòa đồng với bạn cùng phòng?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="interactionSlider" name="interaction" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="interactionError" class="error"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-person-booth"></i> Bạn có thoải mái nếu bạn cùng phòng có khách đến chơi?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="guestSlider" name="guest" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="guestError" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-fire-burner"></i> Bạn có thích nấu ăn không?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container" style="margin-bottom: 10px">
                                        <input id="cookingSlider" name="cooking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>

                                    </div>
                                    <span id="cookingError" class="error"></span>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-dog"></i> Bạn có thoải mái nếu trong phòng nuôi thú cưng?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container" style="margin-bottom: 10px">
                                        <input id="petSlider" name="pet" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" required>
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                    </div>
                                    <span id="petError" class="error"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </c:if>
            <c:if test="${cookie.roleId.value == 4}">
                <h2></h2>
                <section>
                    <div class="inner">
                        <div class="form-content">
                            <div class="form-header">
                                <h3>Thông tin ghép nối</h3>
                            </div>
                            <c:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger" role="alert" style="width: 50%;margin: auto;">
                                        ${requestScope.error}
                                </div>
                            </c:if>
                            <c:if test="${not empty requestScope.message}">
                                <div class="alert alert-success" role="alert" style="width: 50%;margin: auto;" id="successMessage">
                                        ${requestScope.message} - Chuyển hướng trong <span id="countdown">3</span> giây...
                                </div>
                                <script>
                                    let countdown = 3; // Bắt đầu từ 3 giây
                                    const countdownElement = document.getElementById('countdown');

                                    // Hàm đếm ngược
                                    const intervalId = setInterval(() => {
                                        countdown--;
                                        countdownElement.textContent = countdown;

                                        if (countdown === 0) {
                                            clearInterval(intervalId); // Dừng đếm ngược
                                            window.location.href = 'home-page'; // Chuyển hướng về trang login
                                        }
                                    }, 1000); // Giảm mỗi giây
                                </script>
                            </c:if>
                            <c:if test="${empty requestScope.error}">
                                <c:if test="${empty requestScope.message}">
                                    <p style="padding-bottom: 80px">Hãy điền những thông tin về bạn cùng phòng mà bạn mong muốn tìm kiếm. Chúng tôi sẽ giúp bạn tìm kiếm nhanh hơn.</p>
                                </c:if>
                            </c:if>
                            <div class="form-row" style="margin-bottom: 70px">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-broom"></i> Mức độ sạch sẽ bạn mong muốn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="cleanlinessSlider1" name="cleanliness" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" >
                                        <div class="ano-slider">
                                            <span>Bẩn</span>
                                            <span>Sạch</span>
                                        </div>
                                        <span id="cleanlinessError1" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-smoking"></i> Mức độ hút thuốc của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="smokingSlider1" name="smoking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)">
                                        <div class="ano-slider">
                                            <span>Không hút</span>
                                            <span>Thường xuyên</span>
                                        </div>
                                        <span id="smokingError1" class="error"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row" style="margin-bottom: 70px">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-beer-mug-empty"></i> Mức độ uống rượu/bia của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="drinkingSlider1" name="drinking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)" >
                                        <div class="ano-slider">
                                            <span>Không uống</span>
                                            <span>Thường xuyên</span>
                                        </div>
                                        <span id="drinkingError1" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-user-group"></i> Bạn sẽ hòa đồng với bạn cùng phòng?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="interactionSlider1" name="interaction" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="interactionError1" class="error"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row" style="margin-bottom: 70px">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-person-booth"></i> Bạn có thoải mái nếu bạn cùng phòng có khách đến chơi?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="guestSlider1" name="guest" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="guestError1" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-fire-burner"></i> Bạn có thích nấu ăn không?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="cookingSlider1" name="cooking" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="cookingError1" class="error"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row" style="margin-bottom: 70px">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-dog"></i> Bạn có thoải mái nếu trong phòng nuôi thú cưng?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="petSlider1" name="pet" readonly type="range" min="1" max="5" step="1" class="slider" oninput="this.style.setProperty('--value', this.value)">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="petError1" class="error"></span>
                                    </div>
                                    <input type="text" value="${cookie.roleId.value}" name="roleId" hidden="hidden"/>
                                </div>
                            </div>
                        </div>
                    </div>

                </section>
            </c:if>
        </c:if>
        <c:if test="${empty cookie.id}">
            <div style="height: 100%;font-size: 30px;display: flex;align-items: center;justify-content: center;">Bạn chưa đăng nhập, vui lòng đăng nhập tại <a href="login.jsp" style="padding-left: 10px"> đây</a></div>
        </c:if>
        <input type="text" value="${cookie.roleId.value}" name="roleId" hidden="hidden"/>
        <input type="text" value="${cookie.id.value}" name="userId" hidden="hidden"/>
    </form>
</div>
<jsp:include page="footer.jsp"/>


<!-- JQUERY -->
<script src="assets/js/jquery-3.3.1.min.js"></script>
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

<!-- JQUERY STEP -->
<script src="assets/js/jquery.steps.js"></script>
<script src="assets/js/main1.js"></script>
<!-- Template created and distributed by Colorlib -->
</body>
</html>
