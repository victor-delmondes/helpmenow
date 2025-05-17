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

//script do modal de detalhes do usuário
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".view-user-btn").forEach(button => {
        button.addEventListener("click", function () {
            document.getElementById("user_id_datails").innerText = this.dataset.userId;
            document.getElementById("user_name_datails").innerText = this.dataset.userName;
            document.getElementById("user_email_datails").innerText = this.dataset.userEmail;
            document.getElementById("user_type_datails").innerText = this.dataset.userType;
            document.getElementById("user_department_datails").innerText = this.dataset.departmentName;
        });
    });
});

//script do modal de edição de usuário
document.addEventListener("DOMContentLoaded", function () {
    const editButtons = document.querySelectorAll(".edit-user-btn");

    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            document.getElementById("edit_user_id_input").value = this.dataset.userId;
            document.getElementById("edit_user_name_input").value = this.dataset.userName;
            document.getElementById("edit_user_email_input").value = this.dataset.userEmail;
            document.getElementById("edit_type_user_select").value = this.dataset.userType;
            document.getElementById("edit_department_user_select").value = this.dataset.departmentId;
            document.getElementById("change_password_user_id").value = this.dataset.userId;
        });
    });
});

