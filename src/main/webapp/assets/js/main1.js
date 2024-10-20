$(function() {
    $("#wizard").steps({
        headerTag: "h2",
        bodyTag: "section",
        transitionEffect: "fade",
        enableAllSteps: true,
        transitionEffectSpeed: 500,
        labels: {
            finish: "Xác nhận",
            next: "Tiếp",
            previous: "Trở lại"
        },
        onFinished: function(event, currentIndex) {
            // Gọi hàm kiểm tra trước khi gửi form
            const isValid = validateForm();
            if (isValid) {
                $("#wizard").submit();
            }
        }
    });

    $('.wizard > .steps li a').click(function() {
        $(this).parent().addClass('checked');
        $(this).parent().prevAll().addClass('checked');
        $(this).parent().nextAll().removeClass('checked');
    });

    $('.forward').click(function() {
        $("#wizard").steps('next');
    });
    $('.backward').click(function() {
        $("#wizard").steps('previous');
    });

    // Select Dropdown
    $('html').click(function() {
        $('.select .dropdown').hide();
    });
    $('.select').click(function(event) {
        event.stopPropagation();
    });
    $('.select .select-control').click(function() {
        $(this).parent().next().toggle();
    });
    $('.select .dropdown li').click(function() {
        $(this).parent().toggle();
        var text = $(this).attr('rel');
        $(this).parent().prev().find('div').text(text);
    });
});

let isCleanlinessAdjusted = false;
let isSmokingAdjusted = false;
let isDrinkingAdjusted = false;
let isInteractionAdjusted = false;
let isGuestAdjusted = false;
let isCookingAdjusted = false;
let isPetAdjusted = false;

let isCleanlinessAdjusted1 = false;
let isSmokingAdjusted1 = false;
let isDrinkingAdjusted1 = false;
let isInteractionAdjusted1 = false;
let isGuestAdjusted1 = false;
let isCookingAdjusted1 = false;
let isPetAdjusted1 = false;

function validateForm() {

    // Lấy các giá trị từ form
    const roleId = parseInt(document.querySelector('input[name="roleId"]').value);
    if( roleId === 3){
        const howLong = document.querySelector('select[name="howLong"]').value;
        const prefProv = document.querySelector('select[name="prefProvince"]').value;
        const emvdate = new Date(document.querySelector('input[name="emvdate"]').value);
        const lmvdate = new Date(document.querySelector('input[name="lmvdate"]').value);
        const minBudget = parseFloat(document.querySelector('input[name="minBudget"]').value);
        const maxBudget = parseFloat(document.querySelector('input[name="maxBudget"]').value);
        const dob = new Date(document.querySelector('input[name="dob"]').value);
        const gender = document.querySelector('select[name="gender"]').value;
        const today = new Date();

        const cleanlinessSlider = document.getElementById('cleanlinessSlider');
        const smokingSlider = document.getElementById('smokingSlider');
        const drinkingSlider = document.getElementById('drinkingSlider');
        const interactionSlider = document.getElementById('interactionSlider');
        const guestSlider = document.getElementById('guestSlider');
        const cookingSlider = document.getElementById('cookingSlider');
        const petSlider = document.getElementById('petSlider');

        document.querySelectorAll('.error').forEach(span => span.textContent = '');

        let isValid = true;
        let firstErrorSection = null; // Biến để lưu section đầu tiên có lỗi

        // Kiểm tra các trường bắt buộc và cập nhật thông báo lỗi
        if (howLong === '') {
            document.getElementById('howLongError').textContent = 'Vui lòng chọn thời gian bạn muốn tìm bạn cùng phòng.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        }
        if (isNaN(emvdate.getTime())) {
            document.getElementById('emvdateError').textContent = 'Vui lòng chọn thời điểm bạn muốn chuyển vào (sớm nhất).';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        } else if (emvdate < today) {
            document.getElementById('emvdateError').textContent = 'Ngày chuyển vào sớm nhất phải sau hôm nay.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        }
        if (isNaN(lmvdate.getTime())) {
            document.getElementById('lmvdateError').textContent = 'Vui lòng chọn thời điểm bạn muốn chuyển vào (muộn nhất).';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        } else if (lmvdate < emvdate) {
            document.getElementById('lmvdateError').textContent = 'Ngày chuyển vào muộn nhất phải sau ngày chuyển vào sớm nhất.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        }
        if (isNaN(minBudget) || isNaN(maxBudget)) {
            document.getElementById('budgetError').textContent = 'Vui lòng nhập giá phòng bạn mong muốn.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0;
        } else if (minBudget > maxBudget) {
            document.getElementById('budgetError').textContent = 'Giá phòng tối thiểu không được lớn hơn giá phòng tối đa.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0;
        } else if (minBudget <= 0 || maxBudget <= 0) {
            document.getElementById('budgetError').textContent = 'Giá phòng phải lớn hơn 0. ';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0;
        } else if (minBudget.toString().length > 9 || maxBudget.toString().length > 9) {
            document.getElementById('budgetError').textContent = 'Giá phòng tối thiểu và tối đa không được vượt quá 9 chữ số.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0;
        }
        if (prefProv === '') {
            document.getElementById('provinceError').textContent = 'Vui lòng chọn khu vực bạn muốn tìm bạn cùng phòng.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        }
        if (isNaN(dob.getTime()) || dob > today) {
            document.getElementById('dobError').textContent = 'Ngày sinh không được trong tương lai.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1; // Chuyển đến section 2
        }
        if (gender === '') {
            document.getElementById('genderError').textContent = 'Vui lòng chọn giới tính.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1; // Chuyển đến section 0
        }
        // Khi người dùng tương tác với slider, cập nhật trạng thái
        cleanlinessSlider.addEventListener('input', function() {
            isCleanlinessAdjusted = true;
        });
        smokingSlider.addEventListener('input', function() {
            isSmokingAdjusted = true;
        });
        drinkingSlider.addEventListener('input', function() {
            isDrinkingAdjusted = true;
        });
        interactionSlider.addEventListener('input', function() {
            isInteractionAdjusted = true;
        });
        guestSlider.addEventListener('input', function() {
            isGuestAdjusted= true;
        });
        cookingSlider.addEventListener('input', function() {
            isCookingAdjusted = true;
        });
        petSlider.addEventListener('input', function() {
            isPetAdjusted = true;
        });

        if (isCleanlinessAdjusted === false) {
            document.getElementById('cleanlinessError').textContent = 'Vui lòng điều chỉnh mức độ sạch sẽ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isSmokingAdjusted === false) {
            document.getElementById('smokingError').textContent = 'Vui lòng điều chỉnh mức độ hút thuốc.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isDrinkingAdjusted === false) {
            document.getElementById('drinkingError').textContent = 'Vui lòng điều chỉnh mức độ uống rượu/bia.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isInteractionAdjusted === false) {
            document.getElementById('interactionError').textContent = 'Vui lòng điều chỉnh mức độ thân thiện.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isGuestAdjusted === false) {
            document.getElementById('guestError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isCookingAdjusted === false) {
            document.getElementById('cookingError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isPetAdjusted === false) {
            document.getElementById('petError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }

        // Nếu có lỗi, chuyển đến section đầu tiên có lỗi
        if (!isValid && firstErrorSection === 0) {
            $("#wizard").steps('previous'); // Chuyển đến section có lỗi
            return false;
        }

        return isValid;
    } else {
        if(roleId === 4){
            const cleanlinessSlider1 = document.getElementById('cleanlinessSlider1');
            const smokingSlider1 = document.getElementById('smokingSlider1');
            const drinkingSlider1 = document.getElementById('drinkingSlider1');
            const interactionSlider1 = document.getElementById('interactionSlider1');
            const guestSlider1 = document.getElementById('guestSlider1');
            const cookingSlider1 = document.getElementById('cookingSlider1');
            const petSlider1 = document.getElementById('petSlider1');

            document.querySelectorAll('.error').forEach(span => span.textContent = '');

            let isValid = true;
            let firstErrorSection = null; // Biến để lưu section đầu tiên có lỗi

            cleanlinessSlider1.addEventListener('input', function() {
                isCleanlinessAdjusted1 = true;
            });
            smokingSlider1.addEventListener('input', function() {
                isSmokingAdjusted1 = true;
            });
            drinkingSlider1.addEventListener('input', function() {
                isDrinkingAdjusted1 = true;
            });
            interactionSlider1.addEventListener('input', function() {
                isInteractionAdjusted1 = true;
            });
            guestSlider1.addEventListener('input', function() {
                isGuestAdjusted1= true;
            });
            cookingSlider1.addEventListener('input', function() {
                isCookingAdjusted1 = true;
            });
            petSlider1.addEventListener('input', function() {
                isPetAdjusted1 = true;
            });

            if (isCleanlinessAdjusted1 === false) {
                document.getElementById('cleanlinessError1').textContent = 'Vui lòng điều chỉnh mức độ sạch sẽ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isSmokingAdjusted1 === false) {
                document.getElementById('smokingError1').textContent = 'Vui lòng điều chỉnh mức độ hút thuốc.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isDrinkingAdjusted1 === false) {
                document.getElementById('drinkingError1').textContent = 'Vui lòng điều chỉnh mức độ uống rượu/bia.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isInteractionAdjusted1 === false) {
                document.getElementById('interactionError1').textContent = 'Vui lòng điều chỉnh mức độ thân thiện.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isGuestAdjusted1 === false) {
                document.getElementById('guestError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isCookingAdjusted1 === false) {
                document.getElementById('cookingError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isPetAdjusted1 === false) {
                document.getElementById('petError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }

            // Nếu có lỗi, chuyển đến section đầu tiên có lỗi
            if (!isValid && firstErrorSection === 0) {
                $("#wizard").steps('previous'); // Chuyển đến section có lỗi
                return false;
            }

            return isValid;
        }

    }

}