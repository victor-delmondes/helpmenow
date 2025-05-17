package com.br.helpmenow.repository;

import com.br.helpmenow.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserApp, UUID> {

    Optional<UserApp> findByEmail(String email);

}
