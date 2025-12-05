document.getElementById('cadastroForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const url = 'http://localhost:8080/users';
    const messageElement = document.getElementById('message');


    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const grossSalary = "3000.00";
    const cpf = "11111111111";

    const userData = {
        name: name,
        email: email,
        password: password,
        userType: "EMPLOYEE",
        grossSalary: grossSalary,
        cpf: cpf,
        position: "JUNIOR",
        dependents: 0,
        hoursWorkedMonth: 160,
        daysWorked: 20,
        actualVTCost: "0.00",
        degreeUnhealthiness: "NO_DEGREE_UNHEALTHINESS",
        isAdmin: false
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            messageElement.textContent = `✅ Sucesso! Usuário cadastrado.`;
            messageElement.style.color = 'green';
            document.getElementById('cadastroForm').reset();
        } else if (response.status === 400) {

            const errorText = await response.text();

                 if (errorText.includes("already exists") || errorText.includes("Duplicate entry")) {
                messageElement.textContent = `❌ Falha! Usuário (E-mail ou CPF) já cadastrado.`;
                messageElement.style.color = 'orange';
            } else if (errorText.includes("JSON parse error")) {
                messageElement.textContent = `❌ Erro interno de formato de dados. Contate o suporte.`;
                messageElement.style.color = 'red';
            } else {

                messageElement.textContent = `❌ Falha! Verifique se todos os campos estão válidos.`;
                messageElement.style.color = 'orange';
            }

        } else {
            messageElement.textContent = `⚠️ Falha no cadastro(Email já cadastrado). Status: ${response.status}.`;
            messageElement.style.color = 'red';
        }
    } catch (error) {
        messageElement.textContent = `🚨 Erro de conexão: Verifique se o Backend está ativo (porta 8080) e se o CORS está no UserController.`;
        messageElement.style.color = 'red';
        console.error('Erro na requisição:', error);
    }
});