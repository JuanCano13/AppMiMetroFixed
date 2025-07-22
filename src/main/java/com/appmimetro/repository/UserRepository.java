package com.appmimetro.repository;

import com.appmimetro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);
    boolean existsByUsername(String username);
}


