$(document).ready(function() {
    var paper = new ScaleRaphael('vietnammap', '1200', '1400');
    paper.scaleAll(1);
    
    paper.setStart();

    // Get the province name from the table
    var provinceNameCell = $('#province-name');
    var provinceName = provinceNameCell.text().trim(); // Get the text of the province name

    // Function to create provinces
    function createProvince(pathData, province) {
        var p = paper.path(pathData);
        var fillColor = "#DFDFDF"; // Default fill color

        // Check if the province name matches
        if (vietnam.names[province] === provinceName) {
            fillColor = "#6861CE"; // Change fill color to blue if it matches
        }
        // Set attributes for the province
        p.attr({
            stroke: "#FEFEFE",
            fill: fillColor,
            title: vietnam.names[province],
            "stroke-width": 0.2,
            "stroke-linejoin": "round",
            "stroke-opacity": 0.25
        });

        // Add click event for province
        p[0].onclick = function() {
            // You can uncomment the line below to alert the province name
            // alert(province);
        };

        return p; // Return the province path object
    }

    // Create provinces from the shapes
    for (var province in vietnam.shapes) {
        createProvince(vietnam.shapes[province], province);
    }

    var vn = paper.setFinish();

    // Hover effects
    var over = function() {
        this.c = this.c || this.attr("fill");
        this.stop().animate({ fill: "#AAA" }, 100);
    };

    var out = function() {
        this.stop().animate({ fill: this.c }, 100);
    };

    vn.hover(over, out);
});