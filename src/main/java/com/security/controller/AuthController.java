package com.security.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.LoginRequest;
import com.security.dto.RegisterRequest;
import com.security.entity.User;
import com.security.repository.UserRepository;
import com.security.security.JwtUtil;

@RestController
public class AuthController {
    
    private UserRepository repo;
    private PasswordEncoder encoder;
    private JwtUtil jwtUtil;

    public AuthController(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
		this.repo = repo;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {

        if (repo.findByUsername(req.username()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        if (!req.role().startsWith("ROLE_")) {
            throw new RuntimeException("Invalid role");
        }
        
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        user.setRole(req.role());

        repo.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {

    	System.out.println("username verifying");
        User user = repo.findByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        System.out.println("password verifying");
        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        System.out.println("jwt generating");
        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}
