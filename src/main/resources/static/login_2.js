document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const responseMessageElement = document.getElementById("response");

    responseMessageElement.innerText = "";
    responseMessageElement.style.color = "";

    console.log("🔐 Intentando login con:", username);

    const user = { username, password };

    fetch("http://localhost:8081/api/usuarios/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            console.log("📨 Respuesta recibida. Status:", response.status);

            // Verificar si la respuesta es JSON
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json().then(data => {
                    return { data, status: response.status, ok: response.ok };
                });
            } else {
                return response.text().then(text => {
                    return {
                        data: { message: text },
                        status: response.status,
                        ok: response.ok
                    };
                });
            }
        })
        .then(({data, status, ok}) => {
            console.log("📊 Datos procesados:", data);

            if (ok && status === 200) {
                // ✅ Login exitoso - VERIFICACIÓN EXPLÍCITA DEL STATUS 200
                responseMessageElement.innerText = "✅ " + data.message;
                responseMessageElement.style.color = "green";

                // Guardar en localStorage
                if (data.username) {
                    localStorage.setItem('username', data.username);
                    console.log("💾 Usuario guardado:", data.username);
                }
                if (data.id) {
                    localStorage.setItem('userId', data.id);
                }

                console.log("🚀 Redirigiendo a dashboard...");
                // Redirigir inmediatamente - sin timeout
                window.location.href = "dashboard.html";

            } else {
                // ❌ Login fallido
                responseMessageElement.innerText = "❌ " + (data.message || "Error de autenticación");
                responseMessageElement.style.color = "red";
                console.error("Error de login:", data);
            }
        })
        .catch(error => {
            console.error("💥 Error de conexión:", error);
            responseMessageElement.innerText = "❌ Error de conexión: " + error.message;
            responseMessageElement.style.color = "red";
        });
});