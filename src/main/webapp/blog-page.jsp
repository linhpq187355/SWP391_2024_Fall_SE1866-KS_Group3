<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Về dự án của chúng tôi</title>
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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Roboto', sans-serif;
    }
</style>
<head>
    <title>
        Blog Cuộc Sống
    </title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&amp;display=swap" rel="stylesheet"/>
    <style>
        .pagination {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .pagination a {
            margin: 0 5px;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-decoration: none;
            color: #000;
        }
        .pagination a.active {
            background-color: #000;
            color: #fff;
        }
        .pagination a:hover {
            background-color: #ddd;
        }
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .header-button {
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #FFD700;
            padding: 10px;
            background-color: white;
            cursor: pointer;
            width: 100%;
            max-width: 300px;
            margin: 20px auto;
            border-radius: 5px;
        }
        .header-button img {
            width: 30px;
            margin-right: 10px;
        }
        .header-button span {
            font-weight: bold;
            color: #FFD700;
        }
        .header {
            background-color: white;
            padding: 10px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 1px solid #ddd;
        }
        .header a {
            text-decoration: none;
            color: black;
            font-weight: bold;
            margin: 0 10px;
        }
        .header a.active {
            color: red;
        }
        .header .logo {
            font-size: 24px;
        }
        .header .search {
            font-size: 20px;
        }
        .nav {
            background-color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            border-bottom: 1px solid #ddd;
        }
        .nav a {
            text-decoration: none;
            color: black;
            margin: 0 10px;
            font-weight: bold;
        }
        /*.content {*/
        /*    display: flex;*/
        /*    padding: 120px;*/
        /*}*/
        /*.main-content {*/
        /*    flex: 3;*/
        /*    margin-right: 20px;*/
        /*}*/
        /*.main-content .post {*/
        /*    background-color: white;*/
        /*    padding: 15px;*/
        /*    margin-bottom: 20px;*/
        /*    border-radius: 10px;*/
        /*    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);*/
        /*    width: calc(50% - 10px);*/
        /*    box-sizing: border-box;*/
        /*}*/
        /*.main-content .post img {*/
        /*    width: 100%;*/
        /*    border-radius: 10px;*/
        /*}*/
        /*.main-content .post h3 {*/
        /*    font-size: 18px;*/
        /*    margin: 10px 0;*/
        /*}*/
        /*.main-content .post p {*/
        /*    font-size: 14px;*/
        /*    color: #555;*/
        /*}*/
        /*.main-content .post-container {*/
        /*    display: flex;*/
        /*    flex-wrap: wrap;*/
        /*    gap: 20px;*/
        /*}*/
        .content {
            display: flex;
            padding: 120px;
        }
        .main-content {
            flex: 3;
            margin-right: 20px;
        }

        .main-content .post {
            background-color: white;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: calc(50% - 10px);
            box-sizing: border-box;
            height: 430px; /* Adjust the height to make it square */
            flex-direction: column;
            justify-content: space-between;
        }
        .main-content .post img {
            width: 100%;
            height: 236px; /* Adjust the height to make it square */
            border-radius: 10px;
            object-fit: cover;
        }
        .main-content .post .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .main-content .post h3 {
            font-size: 18px;
            margin: 10px 0;
        }
        .main-content .post p {
            font-size: 14px;
            color: #555;
        }
        .main-content .post-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .sidebar {
            flex: 1;
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
        .category-box {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        .category-box h2 {
            margin-bottom: 20px;
        }
        .category-box .category {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }
        .category-box .category label {
            margin: 5px 0;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>
<div class="page-head">
    <div class="container">
        <div class="row">
            <div class="page-head-content">
                <h2 class="page-title">Các bài đăng  </h2>
            </div>
        </div>
    </div>
</div>
<div class="content">
    <div class="main-content">
        <div class="post-container">
            <c:if test="${empty blogPosts}">
                <p>No blog posts available.</p>
            </c:if>
            <c:forEach var="blogPost" items="${blogPosts}">
                <c:if test="${blogPost.status == 'approved'}">
                    <div class="post">
                        <a href="blog-detail?postId=${blogPost.id}">
                            <img alt="Illustration of Aquarius and Virgo zodiac signs"
                                 height="400"
                                 src="${not empty blogPost.imagePath ? blogPost.imagePath : 'https://xaydunganthienphat.com.vn/upload/filemanager/mau%20nha/mau%20nha%20cap%204%20mai%20thai%203%20phong%20ngu/mau-nha-cap-4-mai-thai-3-phong-ngu-1-phong-tho-mau-so-2.jpg'}"
                                 width="600"/>
                        </a>
                        <h3>
                            <a href="blog-detail?postId=${blogPost.id}">
                                    ${blogPost.title}
                            </a>
                        </h3>
                        <p>Ngày tạo: ${formattedCreatedAt[blogPosts.indexOf(blogPost)]} <br>
                            Tác giả: <a href="home-by-user?id=${blogPost.authorId}">${authorNames[blogPosts.indexOf(blogPost)]}</a> <br>
                            Mô tả: ${blogPost.shortDescription}
                        </p>

                    </div>
                </c:if>
            </c:forEach>

        </div>
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="view-blog?page=${currentPage - 1}">&laquo; Trang trước</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span class="current-page">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="view-blog?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="view-blog?page=${currentPage + 1}">Trang sau &raquo;</a>
            </c:if>
        </div>
    </div>
    <div class="sidebar">

        <div class="login">
            <button class="header-button" onclick="window.location.href='add-blog'">
                <span>
     Tạo bài đăng
    </span>
            </button>
            <button class="header-button" onclick="window.location.href='user-blog'">
                <span>
     Danh sách bài đăng của bạn
    </span>
            </button>

        </div>
        <div class="category-box">
            <h3>
                Tìm kiếm theo mục
            </h3>
            <form action="view-blog" method="get">
                <div class="category">
                    <label>
                        <input type="checkbox" name="categoryId" value="8"/> Kinh nghiệm thuê nhà
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="9"/> Giá thuê và So sánh
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="10"/> Tiện nghi và Dịch vụ
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="11"/> Thủ tục thuê nhà
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="12"/> Cẩm nang khu vực
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="13"/> Kinh nghiệm sống chung
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="14"/> Phản hồi về chủ nhà
                    </label>
                    <label>
                        <input type="checkbox" name="categoryId" value="15"/> Mẹo an toàn khi thuê nhà
                    </label>
                </div>
                <button class="header-button" type="submit">Tìm Kiếm</button>
            </form>
        </div>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>