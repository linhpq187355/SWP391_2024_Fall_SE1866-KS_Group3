<%--
  Created by IntelliJ IDEA.
  User: LNV
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
                    <div class="panel-heading text-center">
                        <h3 class="text-muted">Chọn vai trò</h3>
                    </div>
                    <div class="panel-body">
                        <form action="set-role" method="post" onsubmit="return validateForm()">
                            <div class="form-group">
                                <label for="role">Vai trò <span class="text-danger">*</span></label>
                                <select class="form-control" name="role" id="role" required>
                                    <option value="">Bạn muốn</option>
                                    <option value="findRoommate">Tìm roommate</option>
                                    <option value="postRoom">Đăng phòng</option>
                                </select>
                            </div>
<%--                            <div class="form-group">--%>
<%--                                <div class="checkbox">--%>
<%--                                    <label>--%>
<%--                                        <input type="checkbox" value="" name="iAgree" id="iAgree" required> Tôi đồng ý với <a href="#!" class="text-primary">các điều khoản, điều kiện của trang web.</a>--%>
<%--                                    </label>--%>
<%--                                </div>--%>
<%--                            </div>--%>
                            <div class="form-group">
                                <button class="btn btn-primary btn-block" type="submit">Xác nhận</button>
                            </div>
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
