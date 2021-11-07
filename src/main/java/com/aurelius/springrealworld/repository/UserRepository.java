package com.aurelius.springrealworld.repository;

import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmailEqualsIgnoreCaseAndPasswordEquals(@NonNull String email, @NonNull String password);
}
