document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        if (validateForm()) {
            this.submit();
        }
    });
    function validateForm() {
        let isValid = true;
        // Step 1 validation
        const homeType = document.getElementById('home-type');
        const city = document.getElementById('city');
        const district = document.getElementById('district');
        const ward = document.getElementById('ward');
        const addressDetail = document.querySelector('input[name="address-detail"]');
        if (!homeType.value) {
            alert('Vui lòng chọn loại hình nhà ở tại bước 1');
            isValid = false;
        }
        if (!city.value || !district.value || !ward.value) {
            alert('Vui lòng chọn đầy đủ tỉnh thành, quận huyện, phường xã tại bước 1');
            isValid = false;
        }
        if (!addressDetail.value.trim()) {
            alert('Vui lòng nhập địa chỉ cụ thể tại bước 1');
            isValid = false;
        }
        // Step 2 validation
        const area = document.getElementById('area');
        const leaseDuration = document.getElementById('leaseDuration');
        const moveInDate = document.getElementById('moveInDate');
        const numOfBedroom = document.getElementById('numOfBedroom');
        const numOfBath = document.getElementById('numOfBath');
        const price = document.getElementById('price');
        if (isNaN(area.value) || area.value < 10 || area.value > 1000) {
            alert('Diện tích phải từ 10 đến 1000 mét vuông');
            isValid = false;
        }
        if (isNaN(leaseDuration.value) || leaseDuration.value < 1 || leaseDuration.value > 12) {
            alert('Thời gian cho ở ghép phải từ 1 đến 12 tháng');
            isValid = false;
        }
        if (!moveInDate.value) {
            alert('Vui lòng chọn ngày chuyển đến');
            isValid = false;
        } else {
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            const selectedDate = new Date(moveInDate.value);
            if (selectedDate <= today) {
                alert('Ngày chuyển đến phải là ngày trong tương lai');
                isValid = false;
            }
        }
        if (isNaN(numOfBedroom.value) || numOfBedroom.value < 1 || numOfBedroom.value > 10) {
            alert('Số phòng ngủ phải từ 1 đến 10');
            isValid = false;
        }
        if (isNaN(numOfBath.value) || numOfBath.value < 1 || numOfBath.value > 10) {
            alert('Số nhà vệ sinh phải từ 1 đến 10');
            isValid = false;
        }
        if (isNaN(price.value) || price.value < 1000000) {
            alert('Mức giá phải từ 1,000,000 VND trở lên');
            isValid = false;
        }
        // Step 3 validation
        // const imageUpload = document.getElementById('imageUpload');
        // if (imageUpload.files.length === 0) {
        //     alert('Vui lòng chọn ít nhất một hình ảnh');
        //     isValid = false;
        // }
        // Step 4 validation
        const agreeTerms = document.querySelector('input[type="checkbox"]');
        if (!agreeTerms.checked) {
            alert('Để đăng tin, vui lòng đồng ý với điều khoản và điều kiện');
            isValid = false;
        }
        return isValid;
    }
});