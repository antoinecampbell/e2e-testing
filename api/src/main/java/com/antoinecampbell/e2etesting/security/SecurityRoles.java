package com.antoinecampbell.e2etesting.security;

import lombok.Getter;
import lombok.ToString;

/**
 * Security Roles
 */
@Getter
@ToString
public enum SecurityRoles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    SecurityRoles(String role) {
        this.role = role;
    }
}
