document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = {
        OPEN: document.getElementById("open_filter"),
        IN_PROGRESS: document.getElementById("in_progress_filter"),
        RESOLVED: document.getElementById("resolved_filter"),
    };

    const rows = document.querySelectorAll("#tickets_body tr");
    const noResultsMessage = document.getElementById("no_tickets_message");

    function applyFilter() {
        let anyVisible = false;

        rows.forEach(row => {
            const status = row.getAttribute("data-status");
            if (checkboxes[status]?.checked) {
                row.style.display = "";
                anyVisible = true;
            } else {
                row.style.display = "none";
            }
        });

        noResultsMessage.style.display = anyVisible ? "none" : "block";
    }

    // Aplica o filtro ao carregar a página
    applyFilter();

    // Aplica o filtro sempre que qualquer checkbox mudar
    Object.values(checkboxes).forEach(cb => {
        cb.addEventListener("change", applyFilter);
    });
});

// Filtro de pesquisa
const searchInput = document.getElementById("table_search");
const tableBody = document.getElementById("tickets_body");
searchInput.addEventListener("input", function () {
    const searchTerm = this.value.toLowerCase();
    const rows = tableBody.querySelectorAll("tr");

    rows.forEach(row => {
        const rowText = row.innerText.toLowerCase();
        const matches = rowText.includes(searchTerm);
        row.style.display = matches ? "" : "none";
    });
});