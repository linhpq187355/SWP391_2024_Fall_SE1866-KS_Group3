<html>
<head>
    <title>Real Estate Search</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff;
        }
        .container {
            background-color: #f1c40f;
            padding: 10px;
        }
        .tabs {
            display: flex;
        }
        .tab {
            padding: 10px 20px;
            background-color: #f1c40f;
            color: white;
            cursor: pointer;
        }
        .tab.active {
            background-color: #d4ac0d;
        }
        .tab.inactive {
            background-color: #fff3cd;
            color: #d4ac0d;
        }
        .search-bar {
            display: flex;
            align-items: center;
            background-color: #f0f0f0;
            padding: 10px;
            border-radius: 5px;
            margin-top: 10px;
        }
        .search-bar input {
            border: none;
            outline: none;
            flex: 1;
            padding: 10px;
            margin-left: 10px;
        }
        .search-bar button {
            background-color: #f1c40f;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
        }
        .filters {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
        .filter {
            background-color: #d4ac0d;
            color: white;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
            position: relative;
        }
        .filter .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f1c40f;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .filter .dropdown-content a {
            color: white;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }
        .filter .dropdown-content a:hover {
            background-color: #d4ac0d;
        }
        .filter:hover .dropdown-content {
            display: block;
        }
        .toggle-switch {
            display: flex;
            align-items: center;
            margin-left: auto;
        }
        .toggle-switch input {
            display: none;
        }
        .toggle-switch label {
            background-color: #ccc;
            border-radius: 15px;
            cursor: pointer;
            display: inline-block;
            height: 20px;
            position: relative;
            width: 40px;
        }
        .toggle-switch label::after {
            background-color: white;
            border-radius: 50%;
            content: '';
            height: 16px;
            left: 2px;
            position: absolute;
            top: 2px;
            transition: 0.3s;
            width: 16px;
        }
        .toggle-switch input:checked + label {
            background-color: #2ecc71;
        }
        .toggle-switch input:checked + label::after {
            transform: translateX(20px);
        }
        .dropdown {
            position: relative;
            display: inline-block;
        }
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f1c40f;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .dropdown-content a {
            color: white;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }
        .dropdown-content a:hover {
            background-color: #d4ac0d;
        }
        .dropdown:hover .dropdown-content {
            display: block;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="tabs">
            <div class="tab active">Nhà đất bán</div>
            <div class="tab inactive">Nhà đất cho thuê</div>
            <div class="tab inactive">Dự án</div>
            <div class="toggle-switch">
                <input type="checkbox" id="toggle">
                <label for="toggle"></label>
                <span style="color: white; margin-left: 10px;">Dùng phiên bản Tìm kiếm mới</span>
            </div>
        </div>
        <div class="search-bar">
            <div class="dropdown">
                <i class="fas fa-map-marker-alt"></i>
                <span>Hà Nội</span>
                <i class="fas fa-chevron-down"></i>
                <div class="dropdown-content">
                    <a href="#">Hà Nội</a>
                    <a href="#">Hồ Chí Minh</a>
                    <a href="#">Đà Nẵng</a>
                    <a href="#">Hải Phòng</a>
                </div>
            </div>
            <input type="text" placeholder="Nhập tối đa 5 địa điểm, dự án. Ví dụ: Quận Hoàn Kiếm, Quận Đống Đa">
            <button>Tìm kiếm</button>
        </div>
        <div class="filters">
            <div class="filter">Loại nhà đất</div>
            <div class="filter">
                Mức giá
                <div class="dropdown-content">
                    <a href="#">Dưới 1 tỷ</a>
                    <a href="#">1 - 2 tỷ</a>
                    <a href="#">2 - 3 tỷ</a>
                    <a href="#">Trên 3 tỷ</a>
                </div>
            </div>
            <div class="filter">
                Diện tích
                <div class="dropdown-content">
                    <a href="#">Dưới 50m²</a>
                    <a href="#">50 - 100m²</a>
                    <a href="#">100 - 200m²</a>
                    <a href="#">Trên 200m²</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>