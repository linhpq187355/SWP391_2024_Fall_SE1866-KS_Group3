<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GARO ESTATE | single page</title>
    <meta name="description" content="GARO is a real-estate template">
    <meta name="author" content="Kimarotec">
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
    <link href="assets/css/animate.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="assets/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/icheck.min_all.css">
    <link rel="stylesheet" href="assets/css/price-range.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <link rel="stylesheet" href="assets/css/owl.theme.css">
    <link rel="stylesheet" href="assets/css/owl.transitions.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">
</head>
<body>

<div id="preloader">
    <div id="status">&nbsp;</div>
</div>
<!-- Body content -->


<jsp:include page="header.jsp"/>
<!-- End of nav bar -->

<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h2 class="page-title">Chi tiêt bài đăng </h2>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->

<div class="content-area blog-page padding-top-40" style="background-color: #FCFCFC; padding-bottom: 55px;">
    <%--    <div class="container">--%>
    <%--        <div class="row">--%>
    <%--            <div class="blog-lst col-md-12 pl0">--%>
    <%--                <section id="id-100" class="post single">--%>
    <%--                    <div class="post-header single">--%>
    <%--                        <div class="">--%>
    <%--                            <h2 class="wow fadeInLeft animated">${blogPost.title}</h2>--%>
    <%--                            <div class="title-line wow fadeInRight animated"></div>--%>
    <%--                        </div>--%>
    <%--                        <div class="row wow fadeInRight animated">--%>
    <%--                            <div class="col-sm-6">--%>
    <%--                                <p class="author-category">--%>
    <%--                                    By<a href="#">${authorName}</a>--%>
    <%--                                </p>--%>
    <%--                            </div>--%>
    <%--                            <div class="col-sm-6 right">--%>
    <%--                                <p class="date-comments">--%>
    <%--                                    <a href="single.html"><i class="fa fa-calendar-o"></i> ${blogPost.createdAt}</a>--%>
    <%--                                    <a href="single.html"><i class="fa fa-comment-o"></i> 8 Comments</a>--%>
    <%--                                </p>--%>
    <%--                            </div>--%>
    <%--                        </div>--%>
    <%--                    </div>--%>

    <%--                    <div id="post-content" class="post-body single wow fadeInLeft animated">--%>
    <%--                        <p>${blogPost.content}</p>--%>
    <%--                    </div>--%>

    <%--                </section>--%>


    <%--                <section id="comments" class="comments wow fadeInRight animated">--%>
    <%--                    <h4 class="text-uppercase wow fadeInLeft animated">Comments</h4>--%>

    <%--                    <c:forEach var="comment" items="${comments}">--%>
    <%--                        <div class="row comment">--%>
    <%--                            <div class="col-sm-3 col-md-2 text-center-xs">--%>
    <%--                                <p>--%>
    <%--                                    <img src="assets/img/client-face1.png" class="img-responsive img-circle" alt="">--%>
    <%--                                </p>--%>
    <%--                            </div>--%>
    <%--                            <div class="col-sm-9 col-md-10">--%>
    <%--                                <h5 class="text-uppercase">${comment.userName}</h5>--%>
    <%--                                <p class="posted"><i class="fa fa-clock-o"></i> ${comment.createdAt}</p>--%>
    <%--                                <p>${comment.content}</p>--%>
    <%--                                <p class="reply">--%>
    <%--                                    <a href="javascript:void(0);" onclick="toggleReplyForm(${comment.id});"><i--%>
    <%--                                            class="fa fa-reply"></i> Reply</a>--%>
    <%--                                    <c:if test="${comment.userId == userId || blogPost.authorId == userId}">--%>
    <%--                                <form action="delete-comment" method="post" style="display: inline;">--%>
    <%--                                    <input type="hidden" name="commentId" value="${comment.id}"/>--%>
    <%--                                    <input type="hidden" name="postId" value="${blogPost.id}"/>--%>
    <%--                                    <button type="submit" class="btn btn-danger">Delete</button>--%>
    <%--                                </form>--%>
    <%--                                </c:if>--%>


    <%--                                </p>--%>
    <%--                                <div id="reply-form-${comment.id}" class="reply-form" style="display: none;">--%>
    <%--                                    <form action="add-comment" method="post">--%>
    <%--                                        <input type="hidden" name="postId" value="${param.postId}"/>--%>
    <%--                                        <input type="hidden" name="parentId" value="${comment.id}"/>--%>
    <%--                                        <!-- ID của bình luận gốc -->--%>
    <%--                                        <div class="form-group">--%>
    <%--                                            <label for="replyComment">Reply</label>--%>
    <%--                                            <textarea class="form-control" id="replyComment" name="comment" rows="2"--%>
    <%--                                                      required placeholder="Please enter your reply here..."></textarea>--%>
    <%--                                        </div>--%>
    <%--                                        <button type="submit" class="btn btn-primary">Post Reply</button>--%>
    <%--                                    </form>--%>
    <%--                                </div>--%>
    <%--                            </div>--%>
    <%--                        </div>--%>
    <%--                    </c:forEach>--%>

    <%--                    <c:if test="${empty comments}">--%>
    <%--                        <p>No comments yet. Be the first to comment!</p>--%>
    <%--                    </c:if>--%>
    <%--                </section>--%>

    <%--                <section id="comment-form" class="add-comments">--%>
    <%--                    <h4 class="text-uppercase wow fadeInLeft animated">Leave comment</h4>--%>
    <%--                    <form action="add-comment" method="post">--%>
    <%--                        <input type="hidden" name="postId" value="${param.postId}"/>--%>
    <%--                        <div class="row wow fadeInLeft animated">--%>
    <%--                            <div class="col-sm-12">--%>
    <%--                                <div class="form-group">--%>
    <%--                                    <label for="comment">Comment <span class="required">*</span></label>--%>
    <%--                                    <textarea class="form-control" id="comment" name="comment" rows="4" required--%>
    <%--                                              placeholder="Please enter your comment here..."></textarea>--%>
    <%--                                </div>--%>
    <%--                            </div>--%>
    <%--                        </div>--%>
    <%--                        <div class="row wow fadeInLeft animated">--%>
    <%--                            <div class="col-sm-12 text-right">--%>
    <%--                                <button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i> Post--%>
    <%--                                    comment--%>
    <%--                                </button>--%>
    <%--                            </div>--%>
    <%--                        </div>--%>
    <%--                    </form>--%>

    <%--                </section>--%>

    <%--            </div>--%>
    <%--        </div>--%>

    <%--    </div>--%>
    <%
        // Lấy thông báo từ session
        String successMessage = (String) session.getAttribute("successMessage");

        // Nếu có thông báo, sử dụng JavaScript để hiển thị alert
        if (successMessage != null) {
    %>
    <script>
        // Hiển thị thông báo kiểu alert
        alert("<%= successMessage %>");
    </script>
    <%
            // Xóa thông báo khỏi session sau khi hiển thị
            session.removeAttribute("successMessage");
        }
    %>


    <div class="containerp">
        <div class="content">
            <div class="post-title">
                ${blogPost.title}
            </div>
            <div class="date">
                <p>Ngày tạo: ${formattedCreatedAt}</p>
            </div>
            <div class="author">
                <i class="fas fa-pencil-alt"></i>
                Tác giả: <a href="home-by-user?id=${blogPost.authorId}">${blogPost.authorName}</a>
            </div>
            <div>
                <c:if test="${blogPost.status == 'pending'}">
                    <p style="color: red; font-size: 18px; font-weight: bold; padding: 10px;">
                        Bạn hãy chờ admin duyệt nhé.
                    </p>
                </c:if>
            </div>
            <div class="post-content">
                <p>${blogPost.content}</p>
            </div>
            <%--            <div class="comments-section">--%>
            <%--                <section id="comments" class="comments wow fadeInRight animated">--%>
            <%--                    <h4 class="text-uppercase wow fadeInLeft animated"></h4>--%>

            <%--                    <c:forEach var="comment" items="${comments}">--%>
            <%--                        <div class="row comment">--%>
            <%--                            <div class="col-sm-3 col-md-2 text-center-xs">--%>
            <%--                                <p>--%>
            <%--                                    <img src="assets/img/client-face1.png" class="img-responsive img-circle" alt="">--%>
            <%--                                </p>--%>
            <%--                            </div>--%>
            <%--                            <div class="col-sm-9 col-md-10">--%>
            <%--                                <h5 class="text-uppercase">${comment.userName}</h5>--%>
            <%--                                <p class="posted">--%>
            <%--                                    <i class="fa fa-clock-o"></i>--%>
            <%--                                    <span class="formatted-date" data-created-at="${comment.createdAt}"></span>--%>
            <%--                                </p>--%>
            <%--                                <p>${comment.content}</p>--%>
            <%--                                <c:if test="${comment.userId == userId || blogPost.authorId == userId}">--%>
            <%--                                    <form action="delete-comment" method="post" style="display: inline;">--%>
            <%--                                        <input type="hidden" name="commentId" value="${comment.id}"/>--%>
            <%--                                        <input type="hidden" name="postId" value="${blogPost.id}"/>--%>
            <%--                                        <button type="submit" class="btn btn-danger" title="Xóa" style="border: none; background: none; padding: 0;">--%>
            <%--                                            <i class="fa fa-trash"></i>--%>
            <%--                                        </button>--%>
            <%--                                    </form>--%>
            <%--                                </c:if>--%>


            <%--                                <div id="reply-form-${comment.id}" class="reply-form" style="display: none;">--%>
            <%--                                    <form action="add-comment" method="post">--%>
            <%--                                        <input type="hidden" name="postId" value="${param.postId}"/>--%>
            <%--                                        <input type="hidden" name="parentId" value="${comment.id}"/>--%>
            <%--                                    </form>--%>
            <%--                                </div>--%>
            <%--                            </div>--%>
            <%--                        </div>--%>
            <%--                    </c:forEach>--%>

            <%--                    <c:if test="${empty comments}">--%>
            <%--                        <p>Hãy là người đầu tiên bình luận</p>--%>
            <%--                    </c:if>--%>
            <%--                </section>--%>

            <%--                <section id="comment-form" class="add-comments">--%>
            <%--                    <form action="add-comment" method="post">--%>
            <%--                        <input type="hidden" name="postId" value="${param.postId}"/>--%>
            <%--                        <div class="row wow fadeInLeft animated">--%>
            <%--                            <div class="col-sm-12">--%>
            <%--                                <div class="form-group">--%>
            <%--                                    <label for="comment">Bình luận ở đây<span class="required">*</span></label>--%>
            <%--                                    <textarea class="form-control" id="comment" name="comment" rows="4" required--%>
            <%--                                              placeholder="Please enter your comment here..."></textarea>--%>
            <%--                                </div>--%>
            <%--                            </div>--%>
            <%--                        </div>--%>
            <%--                        <div class="row wow fadeInLeft animated">--%>
            <%--                            <div class="col-sm-12 text-right">--%>
            <%--                                <button type="submit" class="btn btn-primary"><i class="fa fa-comment-o"></i>--%>
            <%--                                </button>--%>
            <%--                            </div>--%>
            <%--                        </div>--%>
            <%--                    </form>--%>

            <%--                </section>--%>
            <%--            </div>--%>
        </div>
        <div class="sidebar">
            <div class="login">
                <button class="header-button" onclick="window.location.href='add-blog'">
                    <span>
                        Tạo bài đăng
                    </span>
                </button>
                <button class="header-button" onclick="window.location.href='user-'">
                    <span>
                        Danh sách bài đăng của bạn
                    </span>
                </button>
            </div>
            <div class="social">
                <form action="view-blog" method="get">
                    <div class="category">
                        <label>
                            <input type="checkbox" name="categoryId" value="1"/> Kinh nghiệm thuê nhà
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="2"/> Giá thuê và So sánh
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="3"/> Tiện nghi và Dịch vụ
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="4"/> Thủ tục thuê nhà
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="5"/> Cẩm nang khu vực
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="6"/> Kinh nghiệm sống chung
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="7"/> Phòng cháy chữa cháy
                        </label>
                        <label>
                            <input type="checkbox" name="categoryId" value="8"/> Mẹo an toàn khi thuê nhà
                        </label>
                    </div>
                    <button class="header-button" type="submit">Tìm Kiếm</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    document.querySelectorAll('.formatted-date').forEach(function (span) {
        const localDateTimeString = span.getAttribute('data-created-at');
        const localDateTime = new Date(localDateTimeString); // Chuyển đổi chuỗi thành Date
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        };
        span.textContent = localDateTime.toLocaleString('en-GB', options); // Định dạng theo kiểu ngày
    });
</script>
<style>
    .containerp {
        display: flex;
        justify-content: space-between;
        padding: 20px;
    }

    .content {
        width: 75%;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .sidebar {
        width: 20%;
    }

    .sidebar .login, .sidebar .social {
        background-color: white;
        padding: 15px;
        margin-bottom: 20px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .sidebar .login h3, .sidebar .social h3 {
        font-size: 18px;
        margin-bottom: 10px;
    }

    .sidebar .login input {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }

    .sidebar .login button {
        width: 100%;
        padding: 10px;
        background-color: #d27e04;
        color: white;
        border: none;
        border-radius: 5px;
        font-weight: bold;
        margin-bottom: 10px;
    }

    .sidebar .login .forgot-password {
        text-align: center;
        margin-top: 10px;
    }

    .sidebar .login .forgot-password a {
        color: red;
        text-decoration: none;
    }

    .sidebar .social .icons {
        display: flex;
        justify-content: space-around;
        margin-bottom: 10px;
    }

    .sidebar .social .icons a {
        font-size: 24px;
        color: black;
    }

    .sidebar .social .facebook-page {
        text-align: center;
    }

    .sidebar .social .facebook-page iframe {
        width: 100%;
        border: none;
    }

    .sidebar .social .subscribe {
        text-align: center;
        margin-top: 10px;
    }

    .sidebar .social .subscribe button {
        background-color: red;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        font-weight: bold;
    }

    .post-title {
        text-align: center;
        font-size: 24px;
        font-weight: bold;
        color: #333;
    }

    .post-meta {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: bold;
        margin-bottom: 20px;
    }

    .post-meta .date {
        font-size: 14px;
        color: #999;
    }

    .post-meta .author {
        font-size: 16px;
        color: #333;
    }

    .comments-section {
        margin-top: 20px;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
</style>

<!-- Footer area-->
<jsp:include page="footer.jsp"/>
<script src="assets/js/modernizr-2.6.2.min.js"></script>

<script src="assets/js/jquery-1.10.2.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bootstrap-select.min.js"></script>
<script src="assets/js/bootstrap-hover-dropdown.js"></script>

<script src="assets/js/easypiechart.min.js"></script>
<script src="assets/js/jquery.easypiechart.min.js"></script>

<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/wow.js"></script>

<script src="assets/js/icheck.min.js"></script>
<script src="assets/js/price-range.js"></script>

<script src="assets/js/main.js"></script>

</body>
<script>
    function toggleReplyForm(commentId) {
        var replyForm = document.getElementById("reply-form-" + commentId);
        if (replyForm.style.display === "none") {
            replyForm.style.display = "block"; // Hiển thị biểu mẫu trả lời
        } else {
            replyForm.style.display = "none"; // Ẩn biểu mẫu trả lời
        }
    }

    // Hàm xác nhận trước khi xóa bình luận
    function confirmDelete(commentId) {
        return confirm("Are you sure you want to delete this comment?");
    }

    // Thêm sự kiện xác nhận xóa cho các nút xóa bình luận
    document.querySelectorAll('.btn-danger').forEach(function (button) {
        button.addEventListener('click', function (event) {
            if (!confirmDelete(this.previousElementSibling.value)) {
                event.preventDefault(); // Ngăn không cho form gửi nếu người dùng không xác nhận
            }
        });
    });
    }
</script>
</html>

