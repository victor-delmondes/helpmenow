//Script JS para capturar o toggle e fazer requisição
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".toggle-user-status").forEach(toggle => {
        toggle.addEventListener("change", function () {
            const userId = this.dataset.userId;

            fetch("/admin/users/toggle-status", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams({userId: userId})
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao atualizar status");
                    return response.text(); // "Ativo" ou "Inativo"
                })
                .then(status => {
                    const badge = document.getElementById("user-status-" + userId);
                    if (badge) {
                        badge.innerText = status;
                        badge.className = status === "Ativo" ? "badge bg-success" : "badge bg-danger";
                    }
                })
                .catch(err => {
                    alert("Erro ao alterar status: " + err.message);
                    this.checked = !this.checked; // desfaz visualmente
                });
        });
    });
});

//Script JS para capturar o input de busca e filtrar a tabela
document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("users_table_search");
    const tableBody = document.getElementById("users_table_body");

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


