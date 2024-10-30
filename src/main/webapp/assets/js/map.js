let mapObj;
let marker;
let selectedLat;
let selectedLon;

$(document).ready(function () {

    $('#province').change(function () {
        resetDropdown('#district', 'Chọn quận huyện');
        resetDropdown('#ward', 'Chọn phường xã');
        console.log("province bar interacted");
        var provinceId = $(this).val();
        if (provinceId) {
            loadDistricts(provinceId);
        }
    });

    $('#district').change(function () {
        resetDropdown('#ward', 'Chọn phường xã');
        var districtId = $(this).val();
        if (districtId) {
            loadWards(districtId);
        }
    });

    $('#ward').change(function () {
        initializeMap();
    });

    // Add event listener for the Search button
    $('#searchAddress').click(function (e) {
        console.log("clicked search address");
        e.preventDefault(); // Prevent form submission or page refresh

        var addressDetail = $('input[name="address-detail"]').val();

        if (addressDetail) {
            searchAddress(addressDetail);
        } else {
            alert('Vui lòng nhập đầy đủ thông tin để tìm kiếm');
        }
    });
});

function resetDropdown(selector, defaultText) {
    $(selector).empty();
    $(selector).append($('<option></option>').val('').text(defaultText));
}

function loadDistricts(provinceId) {
    $.get('LocationServlet', {action: 'getDistricts', parentId: provinceId}, function (data) {
        resetDropdown('#district', 'Chọn quận huyện');
        $.each(data, function (index, district) {
            $('#district').append($('<option></option>').val(district.id).text(district.name));
        });
    });
}

function loadWards(districtId) {
    $.get('LocationServlet', {action: 'getWards', parentId: districtId}, function (data) {
        resetDropdown('#ward', 'Chọn phường xã');
        $.each(data, function (index, ward) {
            $('#ward').append($('<option></option>').val(ward.id).text(ward.name));
        });
    });
}

var initialLat = document.getElementById('latitude').value;
var initialLon = document.getElementById('longitude').value;
console.log(initialLon);
console.log(initialLat);

function loadMap() {
    var defaultCoord = [initialLat || 21.0819, initialLon || 105.6363]; // coord mặc định, Hà Nội
    var zoomLevel = 16; // Mức phóng to bản đồ
    var mapConfig = {
        attributionControl: false, // để ko hiện watermark nữa, nếu bị liên hệ đòi thì nhớ open nha
        center: defaultCoord, // vị trí map mặc định hiện tại
        zoom: zoomLevel,
    };

    mapObj = L.map('map', mapConfig);

    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mapObj);
}


function initializeMap() {
    var city = $('#province').val();
    var district = $('#district').val();
    var ward = $('#ward').val();
    var street = $('#address-detail').val();

    if (!city || !district || !ward) {
        // If any of these are missing, just load the map with initial coordinates
        loadMapWithCoordinates(initialLat, initialLon);
        return;
    }

    var url = 'https://nominatim.openstreetmap.org/search';
    var params = {
        'format': 'json',
        'limit': 1,
        'q': ward + ', ' + district + ', ' + city + ', Việt Nam',
        'accept-language': 'vn',
        'countrycodes': 'vn'
    };

    $.ajax({
        type: 'GET',
        url: url,
        data: params,
        dataType: 'json',
        success: function (data) {
            if (data.length > 0) {
                var lat = data[0].lat;
                var lon = data[0].lon;

                if (mapObj) {
                    mapObj.remove();
                }

                var coord = [lat, lon];
                var zoomLevel = 16;
                var mapConfig = {
                    attributionControl: false,
                    center: coord,
                    zoom: zoomLevel,
                };

                mapObj = L.map('map', mapConfig);

                L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(mapObj);

                marker = L.marker([lat, lon], {draggable: true}).addTo(mapObj);

                marker.on('dragend', function (event) {
                    var position = marker.getLatLng();
                    updateSelectedLocation(position.lat, position.lng);
                });

                mapObj.on('click', function (e) {
                    marker.setLatLng(e.latlng);
                    updateSelectedLocation(e.latlng.lat, e.latlng.lng);
                });
            }
        }
    });
}

function searchAddress(addressDetail) {
    var url = 'https://nominatim.openstreetmap.org/search';
    var params = {
        'format': 'json',
        'limit': 1,
        'q': addressDetail + ', Việt Nam',
        'accept-language': 'vn',
        'countrycodes': 'vn'
    };

    $.ajax({
        type: 'GET',
        url: url,
        data: params,
        dataType: 'json',
        success: function (data) {
            if (data.length > 0) {
                var lat = data[0].lat;
                var lon = data[0].lon;
                loadMapWithCoordinates(lat, lon);
            } else {
                alert('Không tìm thấy địa chỉ');
            }
        },
        error: function () {
            alert('Đã xảy ra lỗi khi tìm kiếm địa chỉ');
        }
    });
}

function loadMapWithCoordinates(lat, lon) {
    if (mapObj) {
        mapObj.remove(); // Remove existing map instance if already exists
    }

    var coord = [lat, lon];
    var zoomLevel = 16;
    var mapConfig = {
        attributionControl: false,
        center: coord,
        zoom: zoomLevel
    };

    mapObj = L.map('map', mapConfig);

    L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(mapObj);

    // Add draggable marker
    marker = L.marker([lat, lon], {draggable: true}).addTo(mapObj);

    marker.on('dragend', function (event) {
        var position = marker.getLatLng();
        updateSelectedLocation(position.lat, position.lng);
    });

    mapObj.on('click', function (e) {
        marker.setLatLng(e.latlng);
        updateSelectedLocation(e.latlng.lat, e.latlng.lng);
    });

    // Update hidden latitude and longitude fields
    updateSelectedLocation(lat, lon);
}

function updateSelectedLocation(lat, lon) {
    selectedLat = lat;
    selectedLon = lon;
    //$('#selected-location').text('Latitude: ' + lat.toFixed(6) + ', Longitude: ' + lon.toFixed(6));
    $('#latitude').val(lat.toFixed(6));
    $('#longitude').val(lon.toFixed(6));
}
