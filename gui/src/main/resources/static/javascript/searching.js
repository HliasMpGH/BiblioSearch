function toggleProductOptions(button) {
    const productOptions = button.parentElement.nextElementSibling;
    if (productOptions.style.display === "block") {
        productOptions.style.display = "none";
    } else {
        productOptions.style.display = "block";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const filterToggleButton = document.querySelector(".filter-toggle-button");
    const filterOptions = document.querySelector(".filter-options");

    filterToggleButton.addEventListener("click", function () {
        if (filterOptions.style.display === "block") {
            filterOptions.style.display = "none";
        } else {
            filterOptions.style.display = "block";
        }
    });
});
