$.validator.addMethod('futureDate', function(value, element) {
    var date = new Date(value);
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if (date >= today) {
        return true;
    } else {
        return false;
    }
}, 'Ngày chuyển đến không được là ngày trong quá khứ');

$.validator.addMethod('withinOneMonth', function(value, element) {
    var date = new Date(value);
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    var oneMonthLater = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    if (date <= oneMonthLater) {
        return true;
    } else {
        return false;
    }
}, 'Ngày chuyển đến phải trong vòng 1 tháng kể từ ngày hiện tại');

$(document).ready(function() {
    // Validate form fields
    $('#wizardProperty form').validate({
        rules: {
            'home-type': {
                required: true
            },
            'home-name': {
                required: true,
                minlength: 20,
                maxlength: 99
            },
            'province': {
                required: true
            },
            'district': {
                required: true
            },
            'ward': {
                required: true
            },
            'address-detail': {
                required: true,
                minlength: 15,
                maxlength: 255
            },
            'home-description': {
                required: true,
                minlength: 30,
                maxlength: 3000
            },
            'tenant-description': {
                required: true,
                minlength: 30,
                maxlength: 3000
            },
            'area': {
                required: true,
                number: true,
                min: 4,
                max: 1000
            },
            'leaseDuration': {
                required: true,
                number: true,
                min: 1,
                max: 12
            },
            'moveInDate': {
                required: true,
                date: true,
                futureDate: true,
                withinOneMonth: true
            },
            'numOfBedroom': {
                required: true,
                number: true,
                min: 1,
                max: 20
            },
            'numOfBath': {
                required: true,
                number: true,
                min: 1,
                max: 20
            },
            'price': {
                required: true,
                number: true,
                min: 1000000,
                max: 100000000
            },
            'amentityIds': {
                required: true,
                minlength: 1
            },
            'fireEquipIds': {
                required: true,
                minlength: 1
            },
            'images': {
                required: true,
                accept: 'image/*'
            }
        },
        messages: {
            'home-type': {
                required: 'Vui lòng chọn loại hình nhà ở'
            },
            'home-name': {
                required: 'Vui lòng nhập tên nơi ở',
                minlength: 'Tên nơi ở phải có ít nhất 5 ký tự',
                maxlength: 'Tên nơi ở không được quá 255 ký tự'
            },
            'province': {
                required: 'Vui lòng chọn tỉnh thành'
            },
            'district': {
                required: 'Vui lòng chọn quận huyện'
            },
            'ward': {
                required: 'Vui lòng chọn phường xã'
            },
            'address-detail': {
                required: 'Vui lòng nhập địa chỉ cụ thể',
                minlength: 'Địa chỉ cụ thể phải có ít nhất 5 ký tự',
                maxlength: 'Địa chỉ cụ thể không được quá 255 ký tự'
            },
            'latitude': {
                required: 'Vui lòng nhập vĩ độ',
                number: 'Vĩ độ phải là số'
            },
            'longitude': {
                required: 'Vui lòng nhập kinh độ',
                number: 'Kinh độ phải là số'
            },
            'home-description': {
                required: 'Vui lòng nhập mô tả về nơi ở',
                minlength: 'Mô tả về nơi ở phải có ít nhất 30 ký tự',
                maxlength: 'Mô tả về nơi ở không được quá 3000 ký tự'
            },
            'tenant-description': {
                required: 'Vui lòng nhập mô tả về người bạn muốn ở ghép',
                minlength: 'Mô tả về người bạn muốn ở ghép phải có ít nhất 30 ký tự',
                maxlength: 'Mô tả về người bạn muốn ở ghép không được quá 3000 ký tự'
            },
            'area': {
                required: 'Vui lòng nhập diện tích nơi ở',
                number: 'Diện tích nơi ở phải là số',
                min: 'Diện tích nơi ở phải từ 10 mét vuông trở lên',
                max: 'Diện tích nơi ở không được quá 1000 mét vuông'
            },
            'leaseDuration': {
                required: 'Vui lòng nhập thời gian cho ở ghép',
                number: 'Thời gian cho ở ghép phải là số',
                min: 'Thời gian cho ở ghép phải từ 1 tháng trở lên',
                max: 'Thời gian cho ở ghép không được quá 12 tháng'
            },
            'moveInDate': {
                required: 'Vui lòng nhập ngày chuyển đến',
                date: 'Ngày chuyển đến phải ở định dạng date',
                futureDate: 'Ngày chuyển đến không được là ngày trong quá khứ',
                withinOneMonth: 'Ngày chuyển đến phải trong vòng 1 tháng kể từ ngày hiện tại'
            },
            'numOfBedroom': {
                required: 'Vui lòng nhập số phòng ngủ',
                number: 'Số phòng ngủ phải là số',
                min: 'Số phòng ngủ phải từ 1 trở lên',
                max: 'Số phòng ngủ không được quá 10'
            },
            'numOfBath': {
                required: 'Vui lòng nhập số nhà vệ sinh',
                number: 'Số nhà vệ sinh phải là số',
                min: 'Số nhà vệ sinh phải từ 1 trở lên',
                max: 'Số nhà vệ sinh không được quá 10'
            },
            'price': {
                required: 'Vui lòng nhập mức giá',
                number: 'Mức giá phải là số',
                min: 'Mức giá phải từ 1.000.000 đồng trở lên',
                max: 'Mức giá không được quá 100.000.000 đồng'
            },
            'amentityIds': {
                required: 'Vui lòng chọn ít nhất một tiện ích'
            },
            'fireEquipIds': {
                required: 'Vui lòng chọn ít nhất một thiết bị phòng cháy chữa cháy'
            },
            'images': {
                required: 'Vui lòng chọn hình ảnh',
                accept: 'Hình ảnh phải là file ảnh'
            }
        }
    });
});