package com.security.dto;

public record LoginRequest(
        String username,
        String password
) {}
