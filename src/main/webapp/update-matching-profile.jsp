
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
    <title>Cập nhật ghép nối</title>

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
    <form action="update-matching-profile" method="post" id="wizard" style="height: 100%;width: 75em">
        <button type="button" class="btn btn-danger" style="position: absolute;right: 26px;top: 40px;" onclick="window.location.href='home-page'">Bỏ qua</button>
        <c:if test="${not empty cookie.id}">
            <c:if test="${cookie.roleId.value == 3}">
                <!-- SECTION 1 -->
                <h2></h2>
                <section style="height: 100%;">
                    <div class="inner">
                        <div class="form-content" >
                            <div class="form-header">
                                <h3>Cập nhật thông tin ghép nối</h3>
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
                                    <select class="form-control" name="howLong">
                                        <option value="">Chọn thời gian</option>
                                        <option value="short" <c:if test="${requestScope.user.duration == 'short'}">selected</c:if>>Ngắn (6 tháng hoặc ít hơn)</option>
                                        <option value="long" <c:if test="${requestScope.user.duration == 'long'}">selected</c:if>>Dài (6 tháng trở lên)</option>
                                    </select>
                                    <span class="error" id="howLongError"></span>
                                </div>
                                <div class="form-holder">
                                    <label>Thời điểm bạn mong muốn chuyển vào (sớm nhất)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="emvdate" type="date" class="form-control" value="${requestScope.user.earliestMoveIn}">
                                    <span class="error" id="emvdateError"></span>
                                </div>
                            </div>
                            <div class="form-row">

                                <div class="form-holder">
                                    <label>Thời điểm bạn mong muốn chuyển vào (muộn nhất)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="lmvdate" type="date" class="form-control" value="${requestScope.user.latestMoveIn}">
                                    <span class="error" id="lmvdateError"></span>
                                </div>
                                <div class="form-holder">
                                    <label style="margin-bottom: 33px">Giá phòng bạn mong muốn (theo tháng)?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div style="display: flex; align-items: center">
                                        <input name="minBudget" type="number" class="form-control" style="width: 30%" value="${requestScope.user.minBudget}">đ <label style="margin: 0 20px">đến</label><input name="maxBudget" type="number" class="form-control" style="width: 30%" value="${requestScope.user.maxBudget}">đ
                                    </div>
                                    <span class="error" id="budgetError"></span>
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
                                <h3>Cập nhật thông tin ghép nối</h3>
                            </div>

                            <p style="padding-bottom: 35px">Hãy điền những thông tin về bạn cùng phòng mà bạn mong muốn tìm kiếm. Chúng tôi sẽ cho bạn những lựa chọn phù hợp</p>


                            <h4 style="text-align: center; text-decoration: underline; margin-bottom: 20px; margin-top: 20px">Thông tin cá nhân</h4>
                            <div class="form-row" style="margin-bottom: 35px">
                                <div class="form-holder">
                                    <label>Ngày sinh của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <input name="dob" type="date" class="form-control" value="${requestScope.user.dob}">
                                    <span class="error" id="dobError"></span>
                                </div>
                                <div class="form-holder">
                                    <label>Giới tính<span style="color: red; margin-left: 10px">*</span></label>
                                    <select class="form-control" name="gender">
                                        <option value="male" <c:if test="${requestScope.user.gender == 'male'}">selected</c:if>>Nam</option>
                                        <option value="female" <c:if test="${requestScope.user.gender == 'female'}">selected</c:if>>Nữ</option>
                                        <option value="dif" <c:if test="${requestScope.user.gender == 'dif'}">selected</c:if>>Khác</option>
                                    </select>
                                    <span class="error" id="genderError"></span>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-broom"></i> Mức độ sạch sẽ của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="cleanlinessSlider" name="cleanliness"  type="range" min="1" max="5" step="1" value="${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''}">
                                        <div class="ano-slider">
                                            <span>Bẩn</span>
                                            <span>Sạch</span>
                                        </div>
                                        <span id="cleanlinessError" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-smoking"></i></i> Mức độ hút thuốc của bạn?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="smokingSlider" name="smoking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''}">
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
                                        <input id="drinkingSlider" name="drinking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''}">
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
                                        <input id="interactionSlider" name="interaction" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''}">
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
                                        <input id="guestSlider" name="guest" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.guest != 100 ? requestScope.preference.guest : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.guest != 100 ? requestScope.preference.guest : ''}">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="guestError" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label><i class="fa-solid fa-fire-burner"></i> Bạn có thích nấu ăn không?<span style="color: red; margin-left: 10px">*</span></label>
                                    <div class="slider-container">
                                        <input id="cookingSlider" name="cooking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''}">
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
                                    <div class="slider-container">
                                        <input id="petSlider" name="pet" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''}">
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
                                <h3>Cập nhật thông tin ghép nối</h3>
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
                                    <label>Mức độ sạch sẽ bạn mong muốn?</label>
                                    <div class="slider-container">
                                        <input id="cleanlinessSlider1" name="cleanliness" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cleanliness != 100 ? requestScope.preference.cleanliness : ''}">
                                        <div class="ano-slider">
                                            <span>Bẩn</span>
                                            <span>Sạch</span>
                                        </div>
                                    </div>
                                    <span id="cleanlinessError1" class="error"></span>
                                </div>
                                <div class="form-holder">
                                    <label>Mức độ hút thuốc của bạn?</label>
                                    <div class="slider-container">
                                        <input id="smokingSlider1" name="smoking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.smoking != 100 ? requestScope.preference.smoking : ''}">
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
                                    <label>Mức độ uống rượu/bia của bạn?</label>
                                    <div class="slider-container">
                                        <input id="drinkingSlider1" name="drinking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.drinking != 100 ? requestScope.preference.drinking : ''}">
                                        <div class="ano-slider">
                                            <span>Không uống</span>
                                            <span>Thường xuyên</span>
                                        </div>
                                        <span id="drinkingError1" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label>Bạn sẽ hòa đồng với bạn cùng phòng?</label>
                                    <div class="slider-container">
                                        <input id="interactionSlider1" name="interaction" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.interaction != 100 ? requestScope.preference.interaction : ''}">
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
                                    <label>Bạn có thoải mái nếu bạn cùng phòng có khách đến chơi?</label>
                                    <div class="slider-container">
                                        <input id="guestSlider1" name="guest" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.guest != 100 ? requestScope.preference.guest : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.guest != 100 ? requestScope.preference.guest : ''}">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="guestError1" class="error"></span>
                                    </div>
                                </div>
                                <div class="form-holder">
                                    <label>Bạn có thích nấu ăn không?</label>
                                    <div class="slider-container">
                                        <input id="cookingSlider1"  name="cooking" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.cooking != 100 ? requestScope.preference.cooking : ''}">
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
                                    <label>Bạn có thoải mái nếu trong phòng nuôi thú cưng?</label>
                                    <div class="slider-container">
                                        <input id="petSlider1" name="pet" readonly type="range" min="1" max="5" step="1" value="${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''}" class="slider" oninput="this.style.setProperty('--value', this.value)" style="--value: ${requestScope.preference.pet != 100 ? requestScope.preference.pet : ''}">
                                        <div class="ano-slider">
                                            <span>Không</span>
                                            <span>Có</span>
                                        </div>
                                        <span id="petError1" class="error"></span>
                                    </div>
                                </div>
                                <input type="text" value="${cookie.roleId.value}" name="roleId" hidden="hidden"/>
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

<!-- JQUERY STEP -->
<script src="assets/js/jquery.steps.js"></script>
<script src="assets/js/main2.js"></script>
<!-- Template created and distributed by Colorlib -->
</body>
</html>
