package com.workpermit.auth.controller;

import com.workpermit.auth.dto.AuthRequest;
import com.workpermit.auth.dto.AuthResponse;
import com.workpermit.auth.dto.RegisterRequest;
import com.workpermit.auth.dto.UserResponse;
import com.workpermit.auth.security.JwtService;
import com.workpermit.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = (User) auth.getPrincipal();
        Set<com.workpermit.auth.entity.Role> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(value -> value.replace("ROLE_", ""))
                .map(com.workpermit.auth.entity.Role::valueOf)
                .collect(Collectors.toSet());
        String token = jwtService.generateToken(user.getUsername(), roles);
        return new AuthResponse(token, user.getUsername());
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public java.util.List<UserResponse> users() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateRoles(@PathVariable Long id,
                                    @RequestBody Set<com.workpermit.auth.entity.Role> roles) {
        return userService.updateRoles(id, roles);
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal(expression = "username") String username) {
        var user = userService.getByUsername(username);
        return new UserResponse(user.getId(), user.getUsername(), user.getLocation(), user.getRoles());
    }
}
