<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.homesharing.model.BlogPost" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GARO ESTATE | Left sidebare blog page</title>
    <meta name="description" content="company is a real-estate template">
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
<style>
    .image {
        width: 300px; /* Kích thước cố định cho div chứa ảnh */
        height: 200px; /* Kích thước cố định cho div chứa ảnh */
        overflow: hidden; /* Ẩn phần ảnh thừa ra ngoài */
    }

    .fixed-size {
        width: 100%; /* Ảnh chiếm toàn bộ chiều rộng của container */
        height: auto; /* Giữ tỷ lệ của ảnh */
        object-fit: cover; /* Đảm bảo ảnh không bị méo và được cắt nếu cần */
        display: block; /* Đảm bảo không có khoảng trống dưới ảnh */
    }
    .button-42 {
        background-color: #FF7E31; /* Màu nền mặc định */
        background-image: linear-gradient(-180deg, #FF7E31, #E62C03);
        border-radius: 6px;
        box-shadow: rgba(0, 0, 0, 0.1) 0 2px 4px;
        color: #FFFFFF;
        cursor: pointer;
        display: inline-block;
        font-family: Inter,-apple-system,system-ui,Roboto,"Helvetica Neue",Arial,sans-serif;
        height: 40px;
        line-height: 40px;
        outline: 0;
        overflow: hidden;
        padding: 0 20px;
        pointer-events: auto;
        position: relative;
        text-align: center;
        touch-action: manipulation;
        user-select: none;
        -webkit-user-select: none;
        vertical-align: top;
        white-space: nowrap;
        width: 100%;
        z-index: 9;
        border: 0;
        transition: box-shadow .2s;
    }

    .button-42:hover {
        box-shadow: rgba(253, 76, 0, 0.5) 0 3px 8px;
    }


</style>
<jsp:include page="header.jsp"/>

<!-- End of nav bar -->

<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h1 class="page-title">FAQ PAge</h1>
            </div>
        </div>
    </div>
</div>
<!-- End page header -->
<c:if test="${not empty sessionScope.successMessage}">
    <div class="alert alert-success">
        ${sessionScope.successMessage}
        <c:set var="sessionScope.successMessage" value="" /> <!-- Xóa thông báo sau khi hiển thị -->
    </div>
</c:if>
<div class="content-area blog-page padding-top-40" style="background-color: #FCFCFC; padding-bottom: 55px;">
    <div class="container">
        <div class="row">

            <div class="blog-asside-right col-md-3">
                <div class="panel panel-default sidebar-menu wow fadeInRight animated" >
                    <div class="panel-heading">
                        <h3 class="panel-title">Nội quy viết blog</h3>
                    </div>
                    <div class="panel-body text-widget">
                        <p>
                            1. Nội dung chất lượng: Bài viết cần phải có nội dung rõ ràng, chất lượng và mang lại giá trị cho người đọc.
                        </p>
                        <p>
                            2. Không sao chép: Không được sao chép nội dung từ các nguồn khác mà không có sự cho phép. Tôn trọng bản quyền của tác giả.
                        </p>
                        <p>
                            3. Ngôn ngữ lịch sự: Sử dụng ngôn ngữ lịch sự, không chửi bới hay có lời lẽ xúc phạm đến người khác.
                        </p>
                        <p>
                            4. Chủ đề phù hợp: Bài viết cần phải liên quan đến chủ đề của blog và phù hợp với đối tượng độc giả.
                        </p>
                        <p>
                            5. Kiểm tra chính tả: Trước khi đăng bài, hãy kiểm tra chính tả và ngữ pháp để đảm bảo bài viết rõ ràng và dễ hiểu.
                        </p>
                    </div>

                </div>

                <div class="panel panel-default sidebar-menu wow  fadeInRight animated">
                    <div class="panel-heading">
                        <button class="button-42" role="button" onclick="window.location.href='add-blog'">
                            Tạo Bài Viết
                        </button>
                    </div>
                    <div class="panel-body">

                    </div>
                </div>
            </div>
            <div class="blog-lst col-md-9">
                <c:forEach var="blogPost" items="${blogPosts}">
                    <section class="post">
                        <div class="text-center padding-b-50">
                            <h2 class="wow fadeInLeft animated">${blogPost.title}</h2>
                            <div class="title-line wow fadeInRight animated"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6">
                                <p class="author-category">
                                    By <a href="blog-detail?postId=${blogPost.id}">${authorNames[blogPosts.indexOf(blogPost)]}</a>
                                </p>
                            </div>
                            <div class="col-sm-6 right">
                                <p class="date-comments">
                                    <a href="single.html"><i class="fa fa-calendar-o"></i> ${blogPost.createdAt}</a>
                                        <%--                                            <a href="single.html"><i class="fa fa-comment-o"></i> ${blogPost.commentCount} Comments</a> <!-- Giả định rằng bạn có một trường commentCount trong BlogPost -->--%>
                                </p>
                            </div>
                        </div>
                        <p>${blogPost.shortDescription}</p>
                        <p class="read-more">
                            <a href="blog-detail?postId=${blogPost.id}" class="btn btn-default btn-border">Đọc tiếp</a>
                        </p>
                    </section>
                </c:forEach>
            </div>


        </div>

    </div>

</div>


<jsp:include page="footer.jsp"/>
</body>
</html>