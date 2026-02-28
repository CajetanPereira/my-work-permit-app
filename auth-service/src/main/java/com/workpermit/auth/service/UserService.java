package com.workpermit.auth.service;

import com.workpermit.auth.dto.RegisterRequest;
import com.workpermit.auth.dto.UserResponse;
import com.workpermit.auth.entity.Role;
import com.workpermit.auth.entity.UserAccount;
import com.workpermit.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserAccount user = new UserAccount();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setLocation(request.location());
        user.setRoles(request.roles() == null || request.roles().isEmpty() ? Set.of(Role.VENDOR) : request.roles());
        return toResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse updateRoles(Long id, Set<Role> roles) {
        UserAccount user = userRepository.findById(id).orElseThrow();
        user.setRoles(roles);
        return toResponse(userRepository.save(user));
    }

    public UserAccount getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    private UserResponse toResponse(UserAccount user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getLocation(), user.getRoles());
    }
}
