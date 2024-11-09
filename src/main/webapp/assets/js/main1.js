$(function() {
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
            const isValid = validateForm(isCleanlinessAdjusted, isSmokingAdjusted, isDrinkingAdjusted,isInteractionAdjusted,isGuestAdjusted,isCookingAdjusted,isPetAdjusted,isCleanlinessAdjusted1, isSmokingAdjusted1, isDrinkingAdjusted1,isInteractionAdjusted1,isGuestAdjusted1,isCookingAdjusted1,isPetAdjusted1);
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

    const cleanlinessSlider = document.getElementById('cleanlinessSlider');
    cleanlinessSlider.addEventListener('input', function() {
        isCleanlinessAdjusted = true;
    });

    const smokingSlider = document.getElementById('smokingSlider');
    smokingSlider.addEventListener('input', function() {
        isSmokingAdjusted = true;
    });

    const drinkingSlider = document.getElementById('drinkingSlider');
    drinkingSlider.addEventListener('input', function() {
        isDrinkingAdjusted = true;
    });

    const interactionSlider = document.getElementById('interactionSlider');
    interactionSlider.addEventListener('input', function() {
        isInteractionAdjusted = true;
    });

    const guestSlider = document.getElementById('guestSlider');
    guestSlider.addEventListener('input', function() {
        isGuestAdjusted = true;
    });

    const cookingSlider = document.getElementById('cookingSlider');
    cookingSlider.addEventListener('input', function() {
        isCookingAdjusted = true;
    });

    const petSlider = document.getElementById('petSlider');
    petSlider.addEventListener('input', function() {
        isPetAdjusted = true;
    });

    const cleanlinessSlider1 = document.getElementById('cleanlinessSlider1');
    cleanlinessSlider1.addEventListener('input', function() {
        isCleanlinessAdjusted1 = true;
    });

    const smokingSlider1 = document.getElementById('smokingSlider1');
    smokingSlider1.addEventListener('input', function() {
        isSmokingAdjusted1 = true;
    });

    const drinkingSlider1 = document.getElementById('drinkingSlider1');
    drinkingSlider1.addEventListener('input', function() {
        isDrinkingAdjusted1 = true;
    });

    const interactionSlider1 = document.getElementById('interactionSlider1');
    interactionSlider1.addEventListener('input', function() {
        isInteractionAdjusted1 = true;
    });

    const guestSlider1 = document.getElementById('guestSlider1');
    guestSlider1.addEventListener('input', function() {
        isGuestAdjusted1 = true;
    });

    const cookingSlider1 = document.getElementById('cookingSlider1');
    cookingSlider1.addEventListener('input', function() {
        isCookingAdjusted1 = true;
    });

    const petSlider1 = document.getElementById('petSlider1');
    petSlider1.addEventListener('input', function() {
        isPetAdjusted1 = true;
    });
});

let isCleanlinessAdjustedTenant = false;
let isSmokingAdjustedTenant = false;
let isDrinkingAdjustedTenant = false;
let isInteractionAdjustedTenant = false;
let isGuestAdjustedTenant = false;
let isCookingAdjustedTenant = false;
let isPetAdjustedTenant = false;


let isCleanlinessAdjustedTenant1 = false;
let isSmokingAdjustedTenant1 = false;
let isDrinkingAdjustedTenant1 = false;
let isInteractionAdjustedTenant1 = false;
let isGuestAdjustedTenant1 = false;
let isCookingAdjustedTenant1 = false;
let isPetAdjustedTenant1 = false;

function validateForm(isCleanlinessAdjusted,isSmokingAdjusted,isDrinkingAdjusted,isInteractionAdjusted,isGuestAdjusted,isCookingAdjusted,isPetAdjusted,isCleanlinessAdjusted1, isSmokingAdjusted1, isDrinkingAdjusted1,isInteractionAdjusted1,isGuestAdjusted1,isCookingAdjusted1,isPetAdjusted1) {
    let isCleanlinessAdjustedOut = isCleanlinessAdjusted;
    let isSmokingAdjustedOut = isSmokingAdjusted;
    let isDrinkingAdjustedOut = isDrinkingAdjusted;
    let isInteractionAdjustedOut = isInteractionAdjusted;
    let isGuestAdjustedOut = isGuestAdjusted;
    let isCookingAdjustedOut = isCookingAdjusted;
    let isPetAdjustedOut = isPetAdjusted;

    let isCleanlinessAdjustedOut1 = isCleanlinessAdjusted1;
    let isSmokingAdjustedOut1 = isSmokingAdjusted1;
    let isDrinkingAdjustedOut1 = isDrinkingAdjusted1;
    let isInteractionAdjustedOut1 = isInteractionAdjusted1;
    let isGuestAdjustedOut1 = isGuestAdjusted1;
    let isCookingAdjustedOut1 = isCookingAdjusted1;
    let isPetAdjustedOut1 = isPetAdjusted1;

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
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 2
        }
        if (gender === '') {
            document.getElementById('genderError').textContent = 'Vui lòng chọn giới tính.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 0; // Chuyển đến section 0
        }
        // Khi người dùng tương tác với slider, cập nhật trạng thái

        cleanlinessSlider.addEventListener('input', function() {
            isCleanlinessAdjustedTenant = true;
        });
        smokingSlider.addEventListener('input', function() {
            isSmokingAdjustedTenant= true;
            console.log("oke");
        });
        drinkingSlider.addEventListener('input', function() {
            isDrinkingAdjustedTenant = true;
        });
        interactionSlider.addEventListener('input', function() {
            isInteractionAdjustedTenant = true;
        });
        guestSlider.addEventListener('input', function() {
            isGuestAdjustedTenant = true;
        });
        cookingSlider.addEventListener('input', function() {
            isCookingAdjustedTenant = true;
        });
        petSlider.addEventListener('input', function() {
            isPetAdjustedTenant = true;
        });

        if (isCleanlinessAdjustedOut === false && isCleanlinessAdjustedTenant === false) {
            document.getElementById('cleanlinessError').textContent = 'Vui lòng điều chỉnh mức độ sạch sẽ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 1;
        }
        if (isSmokingAdjustedOut === false && isSmokingAdjustedTenant === false) {
            document.getElementById('smokingError').textContent = 'Vui lòng điều chỉnh mức độ hút thuốc.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 2;
        }
        if (isDrinkingAdjustedOut === false && isDrinkingAdjustedTenant === false) {
            document.getElementById('drinkingError').textContent = 'Vui lòng điều chỉnh mức độ uống rượu/bia.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 3;
        }
        if (isInteractionAdjustedOut === false && isInteractionAdjustedTenant === false) {
            document.getElementById('interactionError').textContent = 'Vui lòng điều chỉnh mức độ thân thiện.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 4;
        }
        if (isGuestAdjustedOut === false && isGuestAdjustedTenant === false) {
            document.getElementById('guestError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 5;
        }
        if (isCookingAdjustedOut === false && isCookingAdjustedTenant === false) {
            document.getElementById('cookingError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 6;
        }
        if (isPetAdjustedOut === false && isPetAdjustedTenant === false) {
            document.getElementById('petError').textContent = 'Vui lòng điều chỉnh mức độ.';
            isValid = false;
            if (firstErrorSection === null) firstErrorSection = 7;
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
                isCleanlinessAdjustedTenant1 = true;
            });
            smokingSlider1.addEventListener('input', function() {
                isSmokingAdjustedTenant1 = true;
            });
            drinkingSlider1.addEventListener('input', function() {
                isDrinkingAdjustedTenant1 = true;
            });
            interactionSlider1.addEventListener('input', function() {
                isInteractionAdjustedTenant1 = true;
            });
            guestSlider1.addEventListener('input', function() {
                isGuestAdjustedTenant1= true;
            });
            cookingSlider1.addEventListener('input', function() {
                isCookingAdjustedTenant1 = true;
            });
            petSlider1.addEventListener('input', function() {
                isPetAdjustedTenant1 = true;
            });

            if (isCleanlinessAdjustedOut1 === false && isCleanlinessAdjustedTenant1 === false) {
                document.getElementById('cleanlinessError1').textContent = 'Vui lòng điều chỉnh mức độ sạch sẽ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 0;
            }
            if (isSmokingAdjustedOut1 === false && isSmokingAdjustedTenant1 === false) {
                document.getElementById('smokingError1').textContent = 'Vui lòng điều chỉnh mức độ hút thuốc.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 1;
            }
            if (isDrinkingAdjustedOut1 === false && isDrinkingAdjustedTenant1 === false) {
                document.getElementById('drinkingError1').textContent = 'Vui lòng điều chỉnh mức độ uống rượu/bia.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 2;
            }
            if (isInteractionAdjustedOut1 === false && isInteractionAdjustedTenant1 === false) {
                document.getElementById('interactionError1').textContent = 'Vui lòng điều chỉnh mức độ thân thiện.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 3;
            }
            if (isGuestAdjustedOut1 === false && isGuestAdjustedTenant1 === false) {
                document.getElementById('guestError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 4;
            }
            if (isCookingAdjustedOut1 === false && isCookingAdjustedTenant1 === false) {
                document.getElementById('cookingError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 5;
            }
            if (isPetAdjustedOut1 === false && isPetAdjustedTenant1 === false) {
                document.getElementById('petError1').textContent = 'Vui lòng điều chỉnh mức độ.';
                isValid = false;
                if (firstErrorSection === null) firstErrorSection = 6;
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