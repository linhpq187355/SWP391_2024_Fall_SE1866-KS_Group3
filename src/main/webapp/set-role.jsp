<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 22/09/2024
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html  lang="vi">
<head>
    <title>Chọn vai trò</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="assets/img/logo-web.jpg" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <style>
        .error-message {
            color: red;
            font-size: 0.875em;
            margin-top: 0.25rem;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<section class="bg-light">
    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form action="set-role" method="post" onsubmit="return validateForm()">
                            <div style="display: flex;justify-content: center; margin-top: 10em;">
                                <img src="assets/img/OBJECTS.svg" alt="Role Image" width="190" height="170">
                            </div>
                            <div style="text-align: center; color: #1A1A1A; font-size: 32px; font-weight: 700; text-transform: capitalize; line-height: 48px; word-wrap: break-word; margin-top: 20px">Vai Trò</div>
                            <div style="width: 100%; text-align: center; color: #4D4D4D; font-size: 20px; font-weight: 500; line-height: 30px; word-wrap: break-word">Vui lòng chọn vai trò phù hợp với nhu cầu của bạn.<br/>Mỗi vai trò sẽ có các chức năng và quyền hạn khác nhau.</div>
                            <div class="form-group">
                                <div style="display: flex; justify-content: space-between;     margin-top: 40px;">
                                    <div style="width: 80%; margin: 10px; background: white; border-radius: 10px; border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); display: flex; align-items: start; justify-content: space-between;">
                                        <div style="display: flex; align-items: flex-start;     width: 90%;">
                                            <img src="assets/img/Frame.svg" style="width: 40px; height: auto; margin-right: 10px;">
                                            <div>
                                                <h4 style="font-weight: 800; margin: 0;">Người Thuê</h4>
                                                <p style="margin: 0; color: #555; font-size: 0.9em;">Chưa có nhà, đang cần tìm nhà và người ở ghép.</p>
                                            </div>
                                        </div>
                                        <input type="radio" name="role" value="findRoommate" required>
                                    </div>
                                    <div style="width: 80%;margin: 10px; background: white; border-radius: 10px; border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); display: flex; align-items: start; justify-content: space-between;">
                                        <div style="display: flex; align-items: flex-start;     width: 90%;">
                                            <img src="assets/img/Frame.svg" style="width: 40px; height: auto; margin-right: 10px;">
                                            <div>
                                                <h4 style="font-weight: 800; margin: 0;">Đăng phòng</h4>
                                                <p style="margin: 0; color: #555; font-size: 0.9em;">Đã có nhà, đang cần tìm người ở ghép, có thể tạo tin đăng.
                                                </p>
                                            </div>
                                        </div>
                                        <input type="radio" name="role" value="postRoom" required>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="" name="iAgree" id="iAgree" required> Tôi đồng ý với
                                        <a href="terms.jsp" class="text-primary">các điều khoản, điều kiện của trang web.</a>
                                    </label>
                                </div>
                            </div>
                            <button class="btn btn-primary btn-block" type="submit">Đăng kí</button>
                        </form>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
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
<script>
    function validateForm() {
        let role = document.getElementById("role").value;
        // Check if a role is selected
        if (role === "") {
            alert("Vui lòng chọn một vai trò!");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
