document.getElementById('imageUpload').addEventListener('change', function (event) {
    var preview = document.getElementById('imagePreview');
    preview.innerHTML = ''; // Clear previous previews

    if (this.files) {
        [].forEach.call(this.files, function (file) {
            if (/^image\//.test(file.type)) {
                var img = document.createElement('img');
                img.classList.add('preview-image');
                img.file = file;
                preview.appendChild(img);

                var reader = new FileReader();
                reader.onload = (function (aImg) {
                    return function (e) {
                        aImg.src = e.target.result;
                    };
                })(img);
                reader.readAsDataURL(file);
            }
        });
    }
});