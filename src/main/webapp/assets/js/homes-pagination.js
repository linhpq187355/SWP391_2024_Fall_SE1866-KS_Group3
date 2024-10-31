$(document).ready(function () {
    // Function to update the number of items per page and submit the form
    function updateItemsPerPage() {
        var selectedValue = document.getElementById('items_per_page').value; // Get the selected value from the dropdown
        document.getElementById('per_page').value = selectedValue; // Set the hidden input value for items per page
        document.querySelector('form').submit(); // Submit the form to apply the change
    }

    // Event listener for changes in the items per page dropdown
    $('#items_per_page').on('change', function () {
        updateItemsPerPage(); // Call the function to update items per page
    });

    var paginationData = document.getElementById('paginationData'); // Get the pagination data element
    var totalItems = parseInt(paginationData.getAttribute('data-total-items')); // Get the total number of items
    var itemsPerPage = parseInt(paginationData.getAttribute('data-items-per-page')); // Get the number of items per page

    // Calculate the total number of pages based on the total items and items per page
    var totalPages = Math.ceil(totalItems / itemsPerPage);

    // Maximum number of visible pages adjacent to the current page
    var maxVisiblePages = 5;

    // Function to render pagination links
    function renderPagination(currentPage) {
        var paginationLinks = document.getElementById('paginationLinks'); // Get the pagination links container
        paginationLinks.innerHTML = ''; // Clear old links

        // Create Prev button if not on the first page
        if (currentPage > 1) {
            paginationLinks.innerHTML += '<li><a href="#" data-page="prev">Prev</a></li>';
        }

        // Calculate the range of pages to display
        var startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
        var endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

        // Adjust startPage if endPage exceeds the maximum visible pages
        if (endPage - startPage < maxVisiblePages - 1) {
            startPage = Math.max(1, endPage - maxVisiblePages + 1);
        }

        // Create page links
        for (var i = startPage; i <= endPage; i++) {
            paginationLinks.innerHTML += '<li><a href="#" data-page="' + i + '"' + (i === currentPage ? ' class="active"' : '') + '>' + i + '</a></li>';
        }

        // Create Next button if not on the last page
        if (currentPage < totalPages) {
            paginationLinks.innerHTML += '<li><a href="#" data-page="next">Next</a></li>';
        }
    }

    // Event handler for pagination link clicks
    document.getElementById('paginationLinks').addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default link behavior
        var clickedPage = event.target.getAttribute('data-page'); // Get the clicked page value
        var currentPage = parseInt(document.getElementById('currentPage').value); // Get the current page from hidden input

        // Determine the new current page based on the clicked link
        if (clickedPage === 'prev') {
            currentPage = currentPage > 1 ? currentPage - 1 : 1; // Go to previous page
        } else if (clickedPage === 'next') {
            currentPage = currentPage < totalPages ? currentPage + 1 : totalPages; // Go to next page
        } else {
            currentPage = parseInt(clickedPage); // Set to the clicked page number
        }

        // Update hidden input values and re-render pagination
        document.getElementById('currentPage').value = currentPage;
        document.getElementById('targetPage').value = currentPage;
        renderPagination(currentPage);

        // Submit form to load new data
        document.querySelector('form').submit();
    });

    // Get currentPage from hidden input (default to 1 if not set)
    var currentPage = parseInt(document.getElementById('currentPage').value) || 1;

    // Initialize pagination with the actual current page
    renderPagination(currentPage);

    // Function to toggle sorting order when a sort button is clicked
    function toggleSort(element) {
        // Toggle the data-order attribute
        let orderby = element.getAttribute('data-orderby'); // Get the attribute for sorting
        let currentOrder = element.getAttribute('data-order'); // Get the current order (ASC or DESC)
        let newOrder = currentOrder === 'ASC' ? ' DESC' : 'ASC'; // Toggle the order

        element.setAttribute('data-order', newOrder); // Update the data-order attribute

        // Update the sort icon
        let icon = element.querySelector('i'); // Get the icon element
        if (icon) {
            if (orderby === 'property_date') {
                // Special handling for date sorting icon
                icon.classList.toggle('fa-up-long'); // Toggle up/down icon
                icon.classList.toggle('fa-down-long'); // Toggle up/down icon
            } else {
                // Logic for price sorting icon (unchanged)
                let iconBase = 'fa-sort-numeric-';
                icon.classList.remove(iconBase + (newOrder === 'ASC' ? 'desc' : 'asc'));
                icon.classList.add(iconBase + newOrder.toLowerCase());
            }
        }

        // Update hidden input values for orderby and order
        let orderbyInput = document.getElementById("orderbyInput");
        let orderInput = document.getElementById("orderInput");

        if (orderbyInput && orderInput) {
            orderbyInput.value = orderby;
            orderInput.value = newOrder;

            console.log("Updated Orderby:", orderbyInput.value); // Debug orderby value
            console.log("Updated Order:", orderInput.value); // Debug order value
        } else {
            console.error("Hidden inputs not found"); // Error if hidden inputs not found
        }

        // Add 'active' class to the currently clicked button and remove from others
        document.querySelectorAll('.sort-button').forEach(btn => {
            btn.classList.remove('active');
        });
        element.classList.add('active');

        // Submit form after sorting is applied
        document.querySelector('form').submit();
    }

    // Add event listeners to sort buttons
    document.querySelectorAll('.sort-button').forEach(button => {
        button.addEventListener('click', (event) => {
            event.preventDefault(); // Prevent default action if necessary
            toggleSort(button);
        });
    });

    // Function to adjust name display based on layout (grid or list)
    function adjustNameDisplay() {
        var isGridActive = document.querySelector('.layout-grid').classList.contains('active'); // Check if grid layout is active

        if (isGridActive) {
            document.querySelectorAll('.home-name').forEach(function (el) {
                el.classList.add('two-line'); // Add class to limit name to 2 lines in grid layout
            });
        } else {
            document.querySelectorAll('.home-name').forEach(function (el) {
                el.classList.remove('two-line'); // Remove class to show full name in list layout
            });
        }
    }

    // Initialize name display adjustment
    adjustNameDisplay();

    // Event listeners for layout toggle buttons
    document.querySelector('.layout-grid').addEventListener('click', function () {
        this.classList.add('active');
        document.querySelector('.layout-list').classList.remove('active');
        adjustNameDisplay(); // Adjust name display for grid layout
    });

    document.querySelector('.layout-list').addEventListener('click', function () {
        this.classList.add('active');
        document.querySelector('.layout-grid').classList.remove('active');
        adjustNameDisplay(); // Adjust name display for list layout
    });
});