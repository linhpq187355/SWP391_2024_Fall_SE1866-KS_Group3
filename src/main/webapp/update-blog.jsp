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
    <script src="ckeditor/ckeditor.js"></script>
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
<style>
    .require {
        color: #666;
    }
    label small {
        color: #999;
        font-weight: normal;
    }
</style>
<body>
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

            </div>




            <div class="blog-lst col-md-9">
                <form id="blog-form" action="update-blog" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="postId" value="${blogPost.id}" />

                    <!-- Title Field -->
                    <div class="form-group">
                        <label for="title">Tiêu đề <span class="require">*</span></label>
                        <input type="text" class="form-control" id="title" name="title" value="${blogPost.title}" required />
                        <div id="title-error" class="text-danger" style="display:none;">Vui lòng nhập tiêu đề.</div>
                    </div>

                    <!-- Image Upload Field -->
                    <div class="form-group">
                        <label for="images">Ảnh tiêu đề</label>
                        <input type="file" class="form-control" id="images" name="images" accept="image/*" />
                    </div>

                    <!-- Short Description Field -->
                    <div class="form-group">
                        <label for="shortDescription">Mô tả ngắn:</label>
                        <textarea id="shortDescription" name="shortDescription" style="width: 100%; height: 200px;">${blogPost.shortDescription}</textarea>
                        <div id="shortDescription-error" class="text-danger" style="display:none;">Vui lòng nhập mô tả ngắn.</div>

                    </div>

                    <!-- Category Selection -->
                    <div class="form-group">
                        <label style="color: #ccac00;">Chọn thể loại:</label><br>
                        <c:forEach var="category" items="${categories}">
                            <input type="checkbox" id="category_${category.id}" name="categoryIds" value="${category.id}"
                                    <c:forEach var="selectedCategory" items="${selectedCategories}">
                                        <c:if test="${category.id == selectedCategory.id}">
                                            checked
                                        </c:if>
                                    </c:forEach>
                            />
                            <label for="category_${category.id}" style="color: goldenrod;">${category.name}</label><br />
                        </c:forEach>
                        <div id="category-error" class="text-danger" style="display:none;">Vui lòng chọn ít nhất một thể loại.</div>

                    </div>

                    <!-- Content Field -->
                    <div class="form-group">
                        <label for="content">Bài Viết:</label>
                        <textarea id="content" name="content" style="width: 100%; height: 200px;">${blogPost.content}</textarea>
                    </div>

                    <input type="hidden" name="status" id="status">

                    <!-- Submit and Action Buttons -->
                    <div class="form-group">
                        <button type="button" class="btn btn-primary" onclick="confirmAction('create')">Cập nhật</button>
                        <button type="button" class="btn btn-default" onclick="confirmAction('drafted')">Lưu</button>
                    </div>
                </form>
            </div>

            </div>

    </div>

</div>

<jsp:include page="footer.jsp"/>
<script>
    CKEDITOR.replace('content');

    // Cập nhật giá trị của textarea khi gửi biểu mẫu
    document.querySelector('form').addEventListener('submit', function (event) {
        for (const instance in CKEDITOR.instances) {
            CKEDITOR.instances[instance].updateElement(); // Cập nhật nội dung CKEditor vào textarea
        }

        // Kiểm tra giá trị của textarea
        const content = document.getElementById('content').value;
        if (!content.trim()) {
            event.preventDefault(); // Ngăn chặn gửi biểu mẫu nếu nội dung trống
            alert("Content is required.");
        }
    });
</script>
<script>
    function confirmAction(action) {
        // Lấy giá trị các trường input
        let title = document.getElementById('title').value;
        let shortDescription = document.getElementById('shortDescription').value;
        let categoryChecked = document.querySelectorAll('input[name="categoryIds"]:checked').length;

        // Ẩn thông báo lỗi trước khi kiểm tra
        document.getElementById('title-error').style.display = 'none';
        document.getElementById('shortDescription-error').style.display = 'none';
        document.getElementById('category-error').style.display = 'none';

        // Kiểm tra từng trường
        if (!title) {
            document.getElementById('title-error').style.display = 'block';
            alert("Vui lòng nhập tiêu đề.");
            return; // Dừng lại nếu chưa nhập tiêu đề
        }

        if (!shortDescription) {
            document.getElementById('shortDescription-error').style.display = 'block';
            alert("Vui lòng nhập mô tả ngắn.");
            return; // Dừng lại nếu chưa nhập mô tả ngắn
        }

        if (categoryChecked === 0) {
            document.getElementById('category-error').style.display = 'block';
            alert("Vui lòng chọn ít nhất một thể loại.");
            return; // Dừng lại nếu chưa chọn thể loại
        }



        // Nếu tất cả đều hợp lệ, tiến hành submit
        let statusValue = action === 'create' ? 'pending' : 'drafted';
        document.querySelector('input[name="status"]').value = statusValue;

        let message;
        if (action === 'create') {
            message = 'Bạn có chắc chắn muốn tạo bài viết không?';
        } else if (action === 'drafted') {
            message = 'Bạn có chắc chắn muốn lưu lại dưới dạng nháp không?';
        }

        if (confirm(message)) {
            document.getElementById('blog-form').submit(); // Gửi form nếu đã đủ điều kiện
        }
    }
</script>

</body>
</html>