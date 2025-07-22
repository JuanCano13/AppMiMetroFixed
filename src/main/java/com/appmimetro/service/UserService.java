package com.appmimetro.service;

import com.appmimetro.model.Usuario;
import com.appmimetro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Guardar usuario (registro)
    public Usuario saveUser(Usuario user) {
        return userRepository.save(user);
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<Usuario> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Actualizar usuario - usando Optional para encajar con .map(...)
    public Optional<Usuario> updateUser(Long id, Usuario userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        });
    }

    // Eliminar usuario
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Buscar por username
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Verificar si un usuario ya existe por su username
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
