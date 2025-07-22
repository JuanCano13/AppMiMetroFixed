document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("registerForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Evita el envío tradicional del formulario

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();

        // Elimina espacios normales, invisibles y caracteres invisibles Unicode del email
        const rawEmail = document.getElementById("email").value;
        const email = rawEmail.replace(/[\u200B-\u200D\uFEFF\s]/g, '').trim();

        const responseMessageElement = document.getElementById("response");

        // Limpia mensajes anteriores al intentar un nuevo registro
        responseMessageElement.innerText = "";
        responseMessageElement.style.color = "";

        // Definición de expresiones regulares para validación
        const usernameRegex = /^[a-zA-Z0-9]{4,20}$/;
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        // Mensajes de depuración en consola
        console.log("🔍 Email ingresado (raw):", rawEmail);
        console.log("📦 Email procesado (trimmed, clean):", email);
        console.log("✅ ¿Es email válido según regex?:", emailRegex.test(email));

        // Validaciones en el lado del cliente
        if (!usernameRegex.test(username)) {
            responseMessageElement.innerText = "El nombre de usuario debe tener entre 4 y 20 caracteres y solo usar letras o números.";
            responseMessageElement.style.color = "red";
            return; // Detiene la ejecución si hay un error
        }

        if (!passwordRegex.test(password)) {
            responseMessageElement.innerText = "La contraseña debe tener al menos 6 caracteres, incluyendo una letra y un número.";
            responseMessageElement.style.color = "red";
            return; // Detiene la ejecución si hay un error
        }

        if (!emailRegex.test(email)) {
            responseMessageElement.innerText = "Por favor, ingresa un correo electrónico válido.";
            responseMessageElement.style.color = "red";
            return; // Detiene la ejecución si hay un error
        }

        // Creación del objeto de usuario para enviar al backend
        const user = { username, password, email };

        // Petición al backend para registrar al usuario
        fetch("http://localhost:8081/api/usuarios", { // Endpoint de registro
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        })
            .then(response => {
                if (response.ok) { // Si la respuesta HTTP es 2xx (éxito)
                    // El backend devuelve una cadena de texto para el éxito
                    return response.text().then(message => {
                        responseMessageElement.innerText = "✅ " + message; // Muestra el mensaje de éxito del backend
                        responseMessageElement.style.color = "green";
                        document.getElementById("registerForm").reset(); // Limpia el formulario

                        // Redireccionar al usuario a la página de login después de un breve retraso
                        setTimeout(() => {
                            window.location.href = "login.html"; // Cambia la URL de la ventana
                        }, 1500); // Redirige después de 1.5 segundos
                    });
                } else { // Si la respuesta HTTP no es 2xx (ej. 409 Conflict, 500 Internal Server Error)
                    const contentType = response.headers.get("content-type");
                    if (contentType && contentType.includes("application/json")) {
                        return response.json().then(errorData => {
                            // Si el error viene con un cuerpo JSON (ej. "El usuario ya existe")
                            responseMessageElement.innerText = "❌ Error: " + (errorData.message || "Error desconocido al registrar usuario.");
                            responseMessageElement.style.color = "red";
                        });
                    } else {
                        return response.text().then(errorText => {
                            // Si el error viene como texto plano (ej. un error no manejado en el backend)
                            responseMessageElement.innerText = "❌ Error: " + errorText;
                            responseMessageElement.style.color = "red";
                        });
                    }
                }
            })
            .catch(error => {
                // Captura errores de red o errores en la promesa
                console.error("Error al conectar con la API:", error);
                responseMessageElement.innerText = "❌ Error al conectar con el servidor. Verifica que el backend esté en ejecución.";
                responseMessageElement.style.color = "red";
            });
    });
});