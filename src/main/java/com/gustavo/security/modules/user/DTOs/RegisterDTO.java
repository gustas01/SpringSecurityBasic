package com.gustavo.security.modules.user.DTOs;

import com.gustavo.security.modules.user.entities.Role;

public record RegisterDTO(String name, String username, String password, Role role) {
}
