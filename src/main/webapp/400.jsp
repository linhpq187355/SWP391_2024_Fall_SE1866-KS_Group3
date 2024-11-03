<%--
  Created by IntelliJ IDEA.
  User: ManhNC
  Date: 25/09/2024
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>400 Bad Request</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        body {
            background-color: #fa8c0f;
            color: #333;
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            text-align: center;
        }
        .container h1 {
            font-size: 10em;
            margin: 0;
        }
        .container h2 {
            font-size: 2em;
            margin: 0;
        }
        .container p {
            font-size: 1.2em;
            margin: 20px 0;
        }
        .container a {
            text-decoration: none;
            color: #333;
            font-size: 1.2em;
            border: 2px solid #333;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }
        .container a:hover {
            background-color: #333;
            color: #FFD700;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>400</h1>
    <p>Yêu cầu của bạn không hợp lệ.</p>
    <p>Vui lòng kiểm tra lại thông tin bạn đã nhập và thử lại.</p>
    <a href="<%= request.getContextPath() %>/home-page">Quay lại trang chính</a>
</div>
</body>
</html>