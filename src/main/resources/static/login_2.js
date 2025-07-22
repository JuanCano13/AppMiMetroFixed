document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita el envío tradicional del formulario

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const responseMessageElement = document.getElementById("response");

    // Limpia mensajes anteriores
    responseMessageElement.innerText = "";
    responseMessageElement.style.color = "";

    // Validación básica (puedes ajustar las regex si es necesario)
    const usernameRegex = /^[a-zA-Z0-9]{4,20}$/;
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

    if (!usernameRegex.test(username)) {
        responseMessageElement.innerText = "El nombre de usuario debe tener entre 4 y 20 caracteres y solo usar letras o números.";
        responseMessageElement.style.color = "red";
        return;
    }

    if (!passwordRegex.test(password)) {
        responseMessageElement.innerText = "La contraseña debe tener al menos 6 caracteres, incluyendo una letra y un número.";
        responseMessageElement.style.color = "red";
        return;
    }

    // Datos del usuario para enviar al backend
    const user = { username, password };

    fetch("http://localhost:8081/api/usuarios/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json().then(data => {
                    if (response.ok) { // Si la respuesta HTTP es 2xx (éxito)
                        responseMessageElement.innerText = "✅ " + data.message; // Mostrar el mensaje del backend sin "Error:"
                        responseMessageElement.style.color = "green";
                        localStorage.setItem('username', data.username);
                        window.location.href = "dashboard.html";
                    } else { // Si la respuesta HTTP no es 2xx (ej. 401 Unauthorized, 409 Conflict)
                        responseMessageElement.innerText = "❌ Error: " + (data.message || "Credenciales inválidas.");
                        responseMessageElement.style.color = "red";
                    }
                });
            } else { // Si la respuesta no es JSON, trátala como texto
                return response.text().then(text => {
                    // Aquí se manejaría si el backend devuelve un texto plano inesperado
                    // Por ejemplo, si el backend está devolviendo "Usuario registrado correctamente"
                    // como texto plano para un 200 OK en el endpoint de REGISTRO,
                    // y accidentalmente llamamos a ese endpoint en lugar del de LOGIN.
                    // Pero con los cambios anteriores, esto debería ser raro para el LOGIN.
                    responseMessageElement.innerText = "❌ Error inesperado: " + text;
                    responseMessageElement.style.color = "red";
                });
            }
        })
        .catch(error => {
            console.error("Error al conectar con la API:", error);
            responseMessageElement.innerText = "❌ Error al conectar con el servidor. Verifica que el backend esté en ejecución.";
            responseMessageElement.style.color = "red";
        });
});