document.addEventListener("DOMContentLoaded", function () {

    // Preenche o modal de edição
    document.querySelectorAll(".edit-category-btn").forEach(button => {
        button.addEventListener("click", function () {
            document.getElementById("edit_category_name_input").value = this.dataset.name;
            document.getElementById("edit_category_id_input").value = this.dataset.id;
        });
    });

    // Alterna status via switch
    document.querySelectorAll(".toggle-category-status").forEach(toggle => {
        toggle.addEventListener("change", function () {
            const categoryId = this.dataset.id;

            fetch("/admin/categories/toggle-status", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({categoryId: categoryId})
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao atualizar status");
                    return response.text();
                })
                .then(status => {
                    const badge = document.getElementById("category-status-" + categoryId);
                    if (badge) {
                        badge.innerText = status;
                        badge.className = status === "Ativo" ? "badge bg-success" : "badge bg-danger";
                    }
                })
                .catch(err => {
                    alert("Erro ao atualizar status: " + err.message);
                    this.checked = !this.checked;
                });
        });
    });

    // Filtro de pesquisa
    const searchInput = document.getElementById("categories_table_search");
    const tableBody = document.getElementById("categories_table_body");

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