package com.workpermit.auth.dto;

import com.workpermit.auth.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RegisterRequest(@NotBlank String username,
                              @NotBlank String password,
                              @NotBlank String location,
                              Set<Role> roles) {}
