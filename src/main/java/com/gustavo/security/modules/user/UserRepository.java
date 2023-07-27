package com.gustavo.security.modules.user;

import com.gustavo.security.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> { }
