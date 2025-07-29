package com.VirtualBankingSystem.UserService.Repository;

import com.VirtualBankingSystem.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryI extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
