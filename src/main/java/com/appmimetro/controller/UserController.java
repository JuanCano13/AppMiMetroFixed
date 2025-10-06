package com.appmimetro.controller;

import com.appmimetro.model.Usuario;
import com.appmimetro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Registro de usuario - ACTUALIZADO con validaciones
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {
        try {
            // Validar que los campos obligatorios no sean nulos o vacíos
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre de usuario es requerido");
            }

            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La contraseña es requerida");
            }

            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El email es requerido");
            }

            // Verificar si el usuario ya existe
            if (userService.existsByUsername(usuario.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
            }

            // Guardar el usuario
            userService.saveUser(usuario);

            // Respuesta de éxito
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario registrado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Manejo de errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    // Inicio de sesión - MEJORADO con más validaciones
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario user) {
        Map<String, Object> response = new HashMap<>();

        // Validar campos de entrada
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            response.put("message", "El nombre de usuario es requerido");
            return ResponseEntity.badRequest().body(response);
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            response.put("message", "La contraseña es requerida");
            return ResponseEntity.badRequest().body(response);
        }

        // Buscar usuario
        Usuario existingUser = userService.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            response.put("message", "Inicio de sesión exitoso");
            response.put("username", existingUser.getUsername());
            response.put("id", existingUser.getId()); // Asumiendo que tu modelo tiene un ID
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Actualizar usuario - MEJORADO con validaciones
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            // Validar que el usuario exista
            Optional<Usuario> existingUser = userService.updateUser(id, usuario);

            if (existingUser.isPresent()) {
                return ResponseEntity.ok(existingUser.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario - MEJORADO con manejo de errores
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            // Verificar si el usuario existe antes de eliminar
            if (userService.getUserById(id).isPresent()) {
                userService.deleteUser(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Endpoint adicional para verificar si un usuario existe
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsernameExists(@PathVariable String username) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", userService.existsByUsername(username));
        return ResponseEntity.ok(response);
    }
}