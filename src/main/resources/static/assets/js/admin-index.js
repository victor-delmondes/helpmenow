//Script JS para capturar o input de busca e filtrar a tabela
document.addEventListener("DOMContentLoaded", function () {
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
});

//---------------------------------------------------------------------------------------------------------------

//Script JS para capturar os checkbox e filtrar a tabela
document.addEventListener("DOMContentLoaded", function () {
    const checkboxes = document.querySelectorAll('.form-check-input');
    const rows = document.querySelectorAll('#ticket_table_body tr');
    const noResultsMsg = document.getElementById("no_tickets_message");

    const statusMap = {
        open_filter: "OPEN",
        in_progress_filter: "IN_PROGRESS",
        resolved_filter: "RESOLVED"
    };

    function filterTable() {
        const activeStatuses = [];

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                const status = statusMap[checkbox.id];
                if (status) activeStatuses.push(status);
            }
        });

        let visibleCount = 0;

        rows.forEach(row => {
            const statusCell = row.querySelector(".ticket-status");
            const rowStatus = statusCell?.innerText?.trim().toUpperCase();
            const shouldShow = activeStatuses.includes(rowStatus);
            row.style.display = shouldShow ? "" : "none";
            if (shouldShow) visibleCount++;
        });

        // Exibe a mensagem se nenhuma linha visível
        noResultsMsg.style.display = visibleCount === 0 ? "block" : "none";
    }

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", filterTable);
    });

    filterTable(); // Executa assim que a página carregar
});

//---------------------------------------------------------------------------------------------------------------

