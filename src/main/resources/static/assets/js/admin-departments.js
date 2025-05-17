document.addEventListener("DOMContentLoaded", function () {

    // Preenche o modal de edição
    document.querySelectorAll(".edit-department-btn").forEach(button => {
        button.addEventListener("click", function () {
            document.getElementById("edit_department_name_input").value = this.dataset.name;
            document.getElementById("edit_department_ramal_input").value = this.dataset.extension;
            document.getElementById("edit_department_location_input").value = this.dataset.location;
            document.getElementById("edit_department_id_input").value = this.dataset.id;
        });
    });

    // Alterna status via switch
    document.querySelectorAll(".toggle-department-status").forEach(toggle => {
        toggle.addEventListener("change", function () {
            const departmentId = this.dataset.id;

            fetch("/admin/departments/toggle-status", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({departmentId: departmentId})
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao atualizar status");
                    return response.text(); // Retorna "Ativo" ou "Inativo"
                })
                .then(status => {
                    const badge = document.getElementById("department-status-" + departmentId);
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
});

// Função para filtrar a tabela
document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("departments_table_search");
    const tableBody = document.getElementById("departments_table_body");

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