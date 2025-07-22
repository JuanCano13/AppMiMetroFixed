package com.appmimetro.controller;

import com.appmimetro.model.Usuario;
import com.appmimetro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; // Necesitas importar esto
import java.util.Map;     // Necesitas importar esto
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Asegúrate de que esta anotación esté presente si usas CORS
public class UserController {

    @Autowired
    private UserService userService;

    // Registro de usuario
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody Usuario usuario) {
        if (userService.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
        userService.saveUser(usuario);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // Inicio de sesión - MODIFICADO
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Usuario user) {
        Usuario existingUser = userService.findByUsername(user.getUsername());
        Map<String, String> response = new HashMap<>(); // Para la respuesta JSON

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            response.put("message", "Inicio de sesión exitoso");
            response.put("username", existingUser.getUsername()); // Añadir el nombre de usuario
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        return userService.updateUser(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}