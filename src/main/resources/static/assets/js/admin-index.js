
    const rows = Array.from(document.querySelectorAll("#tickets_body tr"));
    const searchInput = document.getElementById("table_search");
    const openFilter = document.getElementById("open_filter");
    const progressFilter = document.getElementById("in_progress_filter");
    const resolvedFilter = document.getElementById("closed_filter");
    const paginationContainer = document.querySelector("#table_pagination .pagination");

    const itemsPerPage = 10;
    let currentPage = 1;

    function applyFilters() {
    const search = searchInput.value.toLowerCase();
    const filters = [];

    if (openFilter.checked) filters.push("OPEN");
    if (progressFilter.checked) filters.push("IN_PROGRESS");
    if (resolvedFilter.checked) filters.push("RESOLVED");

    const visibleRows = rows.filter(row => {
    const status = row.dataset.status;
    const title = row.dataset.title.toLowerCase();
    const user = row.dataset.user.toLowerCase();
    const category = row.dataset.category.toLowerCase();

    const matchesStatus = filters.includes(status);
    const matchesSearch =
    title.includes(search) ||
    user.includes(search) ||
    category.includes(search);

    return matchesStatus && matchesSearch;
});

    paginate(visibleRows);
}

    function paginate(filteredRows) {
    const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
    const start = (currentPage - 1) * itemsPerPage;
    const end = start + itemsPerPage;

    // Oculta todas
    rows.forEach(row => row.style.display = "none");

    // Mostra as da página atual
    filteredRows.slice(start, end).forEach(row => row.style.display = "");

    // Atualiza paginação
    renderPagination(totalPages);
}

    function renderPagination(totalPages) {
    paginationContainer.innerHTML = "";

    const createPage = (i, label = null, disabled = false, active = false) => {
    const li = document.createElement("li");
    li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;
    const a = document.createElement("a");
    a.className = "page-link";
    a.href = "#";
    a.textContent = label ?? i;
    a.addEventListener("click", e => {
    e.preventDefault();
    if (!disabled) {
    currentPage = i;
    applyFilters();
}
});
    li.appendChild(a);
    return li;
};

    paginationContainer.appendChild(createPage(currentPage - 1, "«", currentPage === 1));
    for (let i = 1; i <= totalPages; i++) {
    paginationContainer.appendChild(createPage(i, null, false, i === currentPage));
}
    paginationContainer.appendChild(createPage(currentPage + 1, "»", currentPage === totalPages));
}

    // Eventos
    searchInput.addEventListener("input", () => {
    currentPage = 1;
    applyFilters();
});

    [openFilter, progressFilter, resolvedFilter].forEach(filter => {
    filter.addEventListener("change", () => {
        currentPage = 1;
        applyFilters();
    });
});

    // Inicializar
    applyFilters();

